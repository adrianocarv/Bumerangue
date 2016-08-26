package br.org.ceu.bumerangue.plugins.fichaMissa.controller.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import br.org.ceu.bumerangue.controller.web.MultiActionBaseController;
import br.org.ceu.bumerangue.entity.Dominio;
import br.org.ceu.bumerangue.entity.ElementoDominio;
import br.org.ceu.bumerangue.entity.GrupoComponenteMissa;
import br.org.ceu.bumerangue.entity.Oracao;
import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.entity.suporte.deploy.Deploy;
import br.org.ceu.bumerangue.service.FichaMissaService;
import br.org.ceu.bumerangue.service.exceptions.BumerangueRuntimeException;
import br.org.ceu.bumerangue.service.exceptions.BumerangueSuccessRuntimeException;

public class ManterGrupoComponenteMissaController extends MultiActionBaseController {
	
	/** Service */
	private FichaMissaService fichaMissaService;
	public void setFichaMissaService(FichaMissaService fichaMissaService){ this.fichaMissaService = fichaMissaService; }
	
	public boolean isPertencenteModuloDisponivel(){
		return Deploy.isModuloDisponivel(Deploy.MODULO_FICHA_MISSA);
	}

	public boolean isMetodoAutorizado(){
		return true;
	}

	public ModelAndView validateMulti(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	public ModelAndView pesquisarPre(HttpServletRequest request, HttpServletResponse response) {
		addObject("tipo",getParam("tipo"));
		return new ModelAndView("fichaMissaPesquisarGrupoComponenteMissa");
	}

	public ModelAndView pesquisar(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		Integer tipo = getParamAsInteger("tipo");

		List<? extends GrupoComponenteMissa> grupoComponentesMissa = new ArrayList<GrupoComponenteMissa>();
		
		if(tipo == 1)
			grupoComponentesMissa = fichaMissaService.findLiturgiasPalavra(usuario, getParam("chave"));
		else if (tipo == 2)
			grupoComponentesMissa = fichaMissaService.findOracoes(usuario, getParam("chave"));

		addObject("grupoComponentesMissa",grupoComponentesMissa);
		addObject("tipo",getParam("tipo"));
		return new ModelAndView("fichaMissaPesquisarGrupoComponenteMissa");
	}

	public ModelAndView editarPre(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		String id = request.getAttribute("id") != null ? request.getAttribute("id")+"" : getParam("id");

		Integer tipo = getParamAsInteger("tipo");
		
		//recupera do banco
		GrupoComponenteMissa grupoComponenteMissa = fichaMissaService.getGrupoComponenteMissa(usuario,id);
		
		//se ainda não tiver no banco, instancia um objeto novo
		if(grupoComponenteMissa == null){
			grupoComponenteMissa = GrupoComponenteMissa.getInstance(tipo);
		}else{
			grupoComponenteMissa.setLinhaComandoCadastro(grupoComponenteMissa.getLinhaComandoCadastroPadrao());
		}

		//verifica se o objeto deve ser atualizado com os dados do form
		if(request.getAttribute(super.UPDATE_FROM_FORM) != null) updateFromForm(grupoComponenteMissa) ;		
		
		//coloca os objetos no request
		Dominio festaMovel = fichaMissaService.getDominio(usuario,Dominio.FICHA_MISSA_FESTA_MOVEL.getCodigo());
		List<Oracao> oracoesVotivas = fichaMissaService.findOracoesVotivas(usuario);

		addObject("festasMoveis",festaMovel.getElementosDominioEmUso());
		addObject("oracoesVotivas",oracoesVotivas);
		addObject("grupoComponenteMissa",grupoComponenteMissa);
		addObject("tipo",getParam("tipo"));

		if(tipo == 1)
			return new ModelAndView("fichaMissaEditarLiturgiaPalavra");
		else if (tipo == 2){
			//carrega a oração votiva, se for o caso
			Oracao oracaoVotiva = ((Oracao)grupoComponenteMissa).getOracaoVotiva();
			if( oracaoVotiva != null ){
				oracaoVotiva = (Oracao)fichaMissaService.getGrupoComponenteMissa(usuario, oracaoVotiva.getId());
				((Oracao)grupoComponenteMissa).setOracaoVotiva(oracaoVotiva);
			}
			
			return new ModelAndView("fichaMissaEditarOracao");
		}
 
		return null;
	}

	public ModelAndView inserir(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();

		Integer tipo = getParamAsInteger("tipo");
		GrupoComponenteMissa grupoComponenteMissa = GrupoComponenteMissa.getInstance(tipo);

		updateFromForm(grupoComponenteMissa);

		try{
			fichaMissaService.inserirGrupoComponenteMissa(usuario,grupoComponenteMissa);
		}catch (BumerangueRuntimeException e) {
			super.addError(e.getMessage());
			request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			return editarPre(request,response);
		}

		super.addSuccess(BumerangueSuccessRuntimeException.INSERCAO_REALIZADA);
		request.setAttribute("id",grupoComponenteMissa.getId());
		return this.editarPre(request, response);
	}

	public ModelAndView editar(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		
		Integer tipo = getParamAsInteger("tipo");
		GrupoComponenteMissa grupoComponenteMissa = GrupoComponenteMissa.getInstance(tipo);
		updateFromForm(grupoComponenteMissa);

		try{
			fichaMissaService.editarGrupoComponenteMissa(usuario,grupoComponenteMissa);
		}catch (BumerangueRuntimeException e) {
			super.addError(e.getMessage());
			request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			return editarPre(request,response);
		}
		
		super.addSuccess(BumerangueSuccessRuntimeException.ALTERACAO_REALIZADA);
		return this.editarPre(request, response);
	}

	public ModelAndView excluir(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		
		Integer tipo = getParamAsInteger("tipo");
		GrupoComponenteMissa grupoComponenteMissa = GrupoComponenteMissa.getInstance(tipo);
		grupoComponenteMissa.setId(getParam("id"));
		
		fichaMissaService.excluirGrupoComponenteMissa(usuario,grupoComponenteMissa);

		super.addSuccess(BumerangueSuccessRuntimeException.EXCLUSAO_REALIZADA);
    	return this.pesquisarPre(request, response);
	}

	public ModelAndView validar(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		String id = request.getAttribute("id") != null ? request.getAttribute("id")+"" : getParam("id");

		Integer tipo = getParamAsInteger("tipo");
		
		//recupera do banco
		GrupoComponenteMissa grupoComponenteMissa = fichaMissaService.getGrupoComponenteMissa(usuario,id);

		//validar
		if(!grupoComponenteMissa.isValido()){
			super.addError(grupoComponenteMissa.getErrosValidacao());
		}else{
			super.addSuccess("A "+grupoComponenteMissa.getNome()+" está válida.");
		}

		return this.editarPre(request, response);
	}

	private void updateFromForm(GrupoComponenteMissa grupoComponenteMissa) {
		grupoComponenteMissa.setId(getParam("id"));
		grupoComponenteMissa.setChave(getParam("chave"));
		grupoComponenteMissa.setDescricaoChave(getParam("descricaoChave"));
		grupoComponenteMissa.setFestaMovel(new ElementoDominio(getParam("idFestaMovel")));
		if(grupoComponenteMissa instanceof Oracao)
			((Oracao)grupoComponenteMissa).setOracaoVotiva(new Oracao(getParam("idOracaoVotiva")));
		grupoComponenteMissa.setLinhaComandoCadastro(getParam("linhaComandoCadastro"));
	}
}