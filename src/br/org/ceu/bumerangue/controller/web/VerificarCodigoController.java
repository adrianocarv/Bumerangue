package br.org.ceu.bumerangue.controller.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import br.org.ceu.bumerangue.entity.Dominio;
import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.entity.criterias.PesquisaObjetoBumerangueCriteria;
import br.org.ceu.bumerangue.entity.suporte.ResultadoVerificacaoCodigo;
import br.org.ceu.bumerangue.service.AdministracaoService;
import br.org.ceu.bumerangue.service.exceptions.BumerangueErrorRuntimeException;
import br.org.ceu.bumerangue.util.SessionContainer;

public class VerificarCodigoController extends MultiActionBaseController {
	
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
			isInformed("separadorCodigos","Separador de códigos");
			isInformed("serieCodigos","Códigos");
			
			//carrrega o form s� se existir erro
			if (!getErrorMessages().isEmpty()){
				request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			}
	    	return this.verificarPre(request,response);			
		}

		return null;
	}

	public ModelAndView verificarPre(HttpServletRequest request, HttpServletResponse response) {
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
				pesquisaObjetoBumerangueCriteria.setSeparadorCodigos(localizacaoFisica.getPersonalizado5());
				pesquisaObjetoBumerangueCriteria.setFragmentoSubstituicao(localizacaoFisica.getPersonalizado6());
			}
		}

		//limpa a listagem, se for o caso.
		if(!getParamAsBoolean(SessionContainer.REQUEST_ATTRIBUTE_PAGINACAO).booleanValue()){
			getSessionContainer().setResultadoVerificacaoCodigo(null);
		}

		addObject("pesquisaObjetoBumerangueCriteria",pesquisaObjetoBumerangueCriteria);
		
		return new ModelAndView("verificarCodigos");
	}

	public ModelAndView verificar(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuarioLogado = getSessionContainer().getUsuarioLogado();
		PesquisaObjetoBumerangueCriteria pesquisaObjetoBumerangueCriteria = new PesquisaObjetoBumerangueCriteria();
		this.updateFromForm(pesquisaObjetoBumerangueCriteria);		

		ResultadoVerificacaoCodigo resultadoVerificacaoCodigo = new ResultadoVerificacaoCodigo();
		//evita ir novamente no banco.
		if(!getParamAsBoolean(SessionContainer.REQUEST_ATTRIBUTE_PAGINACAO).booleanValue()){
			try{
				resultadoVerificacaoCodigo = administracaoService.verificarCodigos(usuarioLogado,pesquisaObjetoBumerangueCriteria);
				getSessionContainer().setResultadoVerificacaoCodigo(resultadoVerificacaoCodigo);
				
				//atualiza os par�metros no dominio, se for o caso
				Dominio localizacaoFisica = Dominio.getLocalizacaoFisica(pesquisaObjetoBumerangueCriteria.getNomeModulo());
				localizacaoFisica = localizacaoFisica == null ? null : administracaoService.getDominio(usuarioLogado, Dominio.getLocalizacaoFisica(pesquisaObjetoBumerangueCriteria.getNomeModulo()).getCodigo());
				if(localizacaoFisica != null){
					localizacaoFisica.setPersonalizado5(pesquisaObjetoBumerangueCriteria.getSeparadorCodigos());
					localizacaoFisica.setPersonalizado6(pesquisaObjetoBumerangueCriteria.getFragmentoSubstituicao());
					administracaoService.editarDominio(usuarioLogado, localizacaoFisica);
				}
			}catch (BumerangueErrorRuntimeException e) {
				super.addError(e.getMessage());
				request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
				return this.verificarPre(request, response);
			}
		}
		
		addObject("pesquisaObjetoBumerangueCriteria",pesquisaObjetoBumerangueCriteria);

		return new ModelAndView("verificarCodigos");
	}

	public ModelAndView alterarModuloSelecionado(HttpServletRequest request, HttpServletResponse response) {
		return this.verificarPre(request,response);
	}

	public ModelAndView reordenar(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuarioLogado = getSessionContainer().getUsuarioLogado();
		PesquisaObjetoBumerangueCriteria pesquisaObjetoBumerangueCriteria = new PesquisaObjetoBumerangueCriteria();
		this.updateFromForm(pesquisaObjetoBumerangueCriteria);		

		ResultadoVerificacaoCodigo resultadoVerificacaoCodigo = new ResultadoVerificacaoCodigo();
		//evita ir novamente no banco.
		if(!getParamAsBoolean(SessionContainer.REQUEST_ATTRIBUTE_PAGINACAO).booleanValue()){
			resultadoVerificacaoCodigo = administracaoService.reordenarCodigosInformados(usuarioLogado, pesquisaObjetoBumerangueCriteria);
			getSessionContainer().setResultadoVerificacaoCodigo(resultadoVerificacaoCodigo);
		}
		
		addObject("pesquisaObjetoBumerangueCriteria",pesquisaObjetoBumerangueCriteria);
		
		return new ModelAndView("verificarCodigos");
	}
	
	private void updateFromForm(PesquisaObjetoBumerangueCriteria pesquisaObjetoBumerangueCriteria) {
		pesquisaObjetoBumerangueCriteria.setNomeModulo(getParam("nomeModuloSelecionado"));
		pesquisaObjetoBumerangueCriteria.setForaUso(getParamAsBoolean("foraUso"));
		pesquisaObjetoBumerangueCriteria.setSeparadorCodigos(getParam("separadorCodigos"));
		pesquisaObjetoBumerangueCriteria.setFragmentoSubstituicao(getParam("fragmentoSubstituicao"));
		pesquisaObjetoBumerangueCriteria.setSerieCodigos(getParam("serieCodigos"));
	}
}