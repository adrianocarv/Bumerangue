package br.org.ceu.bumerangue.plugins.fichaMissa.controller.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import br.org.ceu.bumerangue.controller.web.MultiActionBaseController;
import br.org.ceu.bumerangue.entity.ComponenteMissa;
import br.org.ceu.bumerangue.entity.Dominio;
import br.org.ceu.bumerangue.entity.ElementoDominio;
import br.org.ceu.bumerangue.entity.GrupoComponenteMissa;
import br.org.ceu.bumerangue.entity.ReferenciaMissal;
import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.entity.suporte.deploy.Deploy;
import br.org.ceu.bumerangue.service.FichaMissaService;
import br.org.ceu.bumerangue.service.exceptions.BumerangueRuntimeException;
import br.org.ceu.bumerangue.service.exceptions.BumerangueSuccessRuntimeException;
import br.org.ceu.bumerangue.util.ValidationRules;

public class ManterReferenciaMissalController extends MultiActionBaseController {
	
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
		if (getParam("method").equals("editar") || getParam("method").equals("inserir")) {
			super.isInformed("idEdicaoIdioma","Idioma / Edição");
			super.isInformed("pagina","Página");

			//carrrega o form só se existir erro
			if (!getErrorMessages().isEmpty()){
				request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			}
			return this.editarPre(request,response);			
		}
		return null;
	}

	public ModelAndView editarPre(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		String id = request.getAttribute("idReferenciaMissal") != null ? request.getAttribute("idReferenciaMissal")+"" : getParam("idReferenciaMissal");

		//recupera do banco
		ReferenciaMissal referenciaMissal = fichaMissaService.getReferenciaMissal(usuario,id);
		
		//se ainda não tiver no banco, instancia um objeto novo
		if(referenciaMissal == null){
			referenciaMissal = new ReferenciaMissal();
		}

		//verifica se o objeto deve ser atualizado com os dados do form
		if(request.getAttribute(super.UPDATE_FROM_FORM) != null) updateFromForm(referenciaMissal) ;		
		
		//coloca os objetos no request
		GrupoComponenteMissa grupoComponenteMissa = fichaMissaService.getGrupoComponenteMissa(usuario,getParam("id"));
		ComponenteMissa componenteMissa = fichaMissaService.getComponenteMissa(usuario,getParam("idComponente"));
		Dominio edicoesIdiomasMissais = fichaMissaService.getDominio(usuario, Dominio.FICHA_MISSA_EDICAO_IDIOMA_MISSAL.getCodigo());
		
		addObject("grupoComponenteMissa",grupoComponenteMissa);
		addObject("componenteMissa",componenteMissa);
		addObject("referenciaMissal",referenciaMissal);
		addObject("edicoesIdiomasMissais",edicoesIdiomasMissais.getElementosDominio());

		addObject("id",getParam("id"));
		addObject("tipo",getParam("tipo"));
		addObject("idComponente",getParam("idComponente"));
		addObject("tipoComponente",getParam("tipoComponente"));
		return new ModelAndView("fichaMissaEditarComponenteMissa");
	}

	public ModelAndView inserir(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();

		ReferenciaMissal referenciaMissal = new ReferenciaMissal();
		updateFromForm(referenciaMissal);

		try{
			fichaMissaService.inserirReferenciaMissal(usuario,referenciaMissal);
		}catch (BumerangueRuntimeException e) {
			super.addError(e.getMessage());
			request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			return editarPre(request,response);
		}

		super.addSuccess(BumerangueSuccessRuntimeException.INSERCAO_REALIZADA);
		request.setAttribute("idReferenciaMissal",referenciaMissal.getId());
		return this.editarPre(request, response);
	}

	public ModelAndView editar(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		
		ReferenciaMissal referenciaMissal = new ReferenciaMissal();
		updateFromForm(referenciaMissal);

		try{
			fichaMissaService.editarReferenciaMissal(usuario,referenciaMissal);
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
		
		ReferenciaMissal referenciaMissal = new ReferenciaMissal(getParam("idReferenciaMissal"));
		
		fichaMissaService.excluirReferenciaMissal(usuario,referenciaMissal);

		super.addSuccess(BumerangueSuccessRuntimeException.EXCLUSAO_REALIZADA);
    	return this.editarPre(request, response);
	}

	private void updateFromForm(ReferenciaMissal referenciaMissal) {
		referenciaMissal.setComponenteMissa(ComponenteMissa.getInstance(getParamAsInteger("tipoComponente")));
		referenciaMissal.getComponenteMissa().setId(getParam("idComponente"));
		referenciaMissal.setId(getParam("idReferenciaMissal"));
		referenciaMissal.setEdicaoIdioma(new ElementoDominio(getParam("idEdicaoIdioma")));
		referenciaMissal.setPagina(getParam("pagina"));
		referenciaMissal.setTexto(getParam("texto"));
		referenciaMissal.setEnderecoEscrituras(getParam("enderecoEscrituras"));
	}
	
}