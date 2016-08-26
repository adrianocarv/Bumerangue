package br.org.ceu.bumerangue.controller.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import br.org.ceu.bumerangue.entity.Dominio;
import br.org.ceu.bumerangue.entity.ObjetoBumerangue;
import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.entity.criterias.PesquisaObjetoBumerangueCriteria;
import br.org.ceu.bumerangue.service.AdministracaoService;
import br.org.ceu.bumerangue.util.SessionContainer;
import br.org.ceu.bumerangue.util.ValidationRules;

public class PesquisarCodigoController extends MultiActionBaseController {
	
	/** Service */
	private AdministracaoService administracaoService;
	public void setAdministracaoService(AdministracaoService administracaoService){ this.administracaoService = administracaoService; }
	
	public boolean isPertencenteModuloDisponivel(){
		return true;
	}

	public boolean isMetodoAutorizado(){
		return true;
	}

	public ModelAndView validateMulti(HttpServletRequest request, HttpServletResponse response) {
		if (getParam("method").equals("pesquisar")) {
			isInformed("nomeModuloSelecionado","Módulo");
			if(!ValidationRules.isInformed(getParam("codigoIni")) && !ValidationRules.isInformed(getParam("codigoFim")))
				addError("O campo 'Código inicial' ou 'Código final' deve ser informado.");
			
			if(getParamAsInteger("modo").intValue() != PesquisaObjetoBumerangueCriteria.MODO_CADASTRADO.intValue())
				isInformed("fragmentoSequencial","Fragmento do código que representa o sequêncial");
				
			
			//carrrega o form s� se existir erro
			if (!getErrorMessages().isEmpty()){
				request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			}
	    	return this.pesquisarPre(request,response);			
		}

		return null;
	}

	public ModelAndView pesquisarPre(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuarioLogado = getSessionContainer().getUsuarioLogado();
		PesquisaObjetoBumerangueCriteria pesquisaObjetoBumerangueCriteria = new PesquisaObjetoBumerangueCriteria();
		
		//verifica se o objeto deve ser atualizado com os dados do form
		if(request.getAttribute(super.UPDATE_FROM_FORM) != null){
			this.updateFromForm(pesquisaObjetoBumerangueCriteria) ;
		}else{
			pesquisaObjetoBumerangueCriteria.setNomeModulo(getParam("nomeModuloSelecionado"));
			//recupera os valores padr�o no dominio, se for o caso
			Dominio localizacaoFisica = Dominio.getLocalizacaoFisica(pesquisaObjetoBumerangueCriteria.getNomeModulo());
			localizacaoFisica = localizacaoFisica == null ? null : administracaoService.getDominio(usuarioLogado, Dominio.getLocalizacaoFisica(pesquisaObjetoBumerangueCriteria.getNomeModulo()).getCodigo());
			if(localizacaoFisica != null){
				pesquisaObjetoBumerangueCriteria.setFragmentoSequencial(localizacaoFisica.getPersonalizado4());
			}
		}

		//limpa a listagem, se for o caso.
		if(!getParamAsBoolean(SessionContainer.REQUEST_ATTRIBUTE_PAGINACAO).booleanValue()){
			getSessionContainer().setObjetosBumerangue(null);
		}

		addObject("pesquisaObjetoBumerangueCriteria",pesquisaObjetoBumerangueCriteria);
		
		return new ModelAndView("pesquisarCodigos");
	}

	public ModelAndView pesquisar(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuarioLogado = getSessionContainer().getUsuarioLogado();
		PesquisaObjetoBumerangueCriteria pesquisaObjetoBumerangueCriteria = new PesquisaObjetoBumerangueCriteria();
		this.updateFromForm(pesquisaObjetoBumerangueCriteria);		

		List<ObjetoBumerangue> objetosBumerangue = new ArrayList<ObjetoBumerangue>();
		//evita ir novamente no banco.
		if(!getParamAsBoolean(SessionContainer.REQUEST_ATTRIBUTE_PAGINACAO).booleanValue()){
			objetosBumerangue = administracaoService.pesquisarObjetosBumerangue(usuarioLogado,pesquisaObjetoBumerangueCriteria);
			getSessionContainer().setObjetosBumerangue(objetosBumerangue);

			//atualiza os par�metros no dominio, se for o caso
			Dominio localizacaoFisica = Dominio.getLocalizacaoFisica(pesquisaObjetoBumerangueCriteria.getNomeModulo());
			localizacaoFisica = localizacaoFisica == null ? null : administracaoService.getDominio(usuarioLogado, Dominio.getLocalizacaoFisica(pesquisaObjetoBumerangueCriteria.getNomeModulo()).getCodigo());
			if(localizacaoFisica != null){
				localizacaoFisica.setPersonalizado4(pesquisaObjetoBumerangueCriteria.getFragmentoSequencial());
				administracaoService.editarDominio(usuarioLogado, localizacaoFisica);
			}
		}
		
		addObject("pesquisaObjetoBumerangueCriteria",pesquisaObjetoBumerangueCriteria);

		return new ModelAndView("pesquisarCodigos");
	}

	public ModelAndView alterarModuloSelecionado(HttpServletRequest request, HttpServletResponse response) {
		return this.pesquisarPre(request,response);
	}

	private void updateFromForm(PesquisaObjetoBumerangueCriteria pesquisaObjetoBumerangueCriteria) {
		pesquisaObjetoBumerangueCriteria.setNomeModulo(getParam("nomeModuloSelecionado"));
		pesquisaObjetoBumerangueCriteria.setCodigoIni(getParam("codigoIni"));
		pesquisaObjetoBumerangueCriteria.setCodigoFim(getParam("codigoFim"));
		pesquisaObjetoBumerangueCriteria.setForaUso(getParamAsBoolean("foraUso"));
		pesquisaObjetoBumerangueCriteria.setModo(getParamAsInteger("modo"));
		pesquisaObjetoBumerangueCriteria.setFragmentoSequencial(getParam("fragmentoSequencial"));
	}
}