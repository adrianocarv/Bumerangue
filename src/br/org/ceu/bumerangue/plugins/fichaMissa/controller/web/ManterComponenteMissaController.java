package br.org.ceu.bumerangue.plugins.fichaMissa.controller.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import br.org.ceu.bumerangue.controller.web.MultiActionBaseController;
import br.org.ceu.bumerangue.entity.ComponenteMissa;
import br.org.ceu.bumerangue.entity.GrupoComponenteMissa;
import br.org.ceu.bumerangue.entity.Oracao;
import br.org.ceu.bumerangue.entity.Prefacio;
import br.org.ceu.bumerangue.entity.ReferenciaMissal;
import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.entity.suporte.deploy.Deploy;
import br.org.ceu.bumerangue.service.FichaMissaService;
import br.org.ceu.bumerangue.service.exceptions.BumerangueRuntimeException;
import br.org.ceu.bumerangue.service.exceptions.BumerangueSuccessRuntimeException;
import br.org.ceu.bumerangue.util.ValidationRules;

public class ManterComponenteMissaController extends MultiActionBaseController {
	
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

	public ModelAndView editarPre(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		String id = request.getAttribute("idComponente") != null ? request.getAttribute("idComponente")+"" : getParam("idComponente");

		Integer tipoComponente = getParamAsInteger("tipoComponente");
		
		//recupera do banco
		ComponenteMissa componenteMissa = fichaMissaService.getComponenteMissa(usuario,id);
		
		//se ainda não tiver no banco, instancia um objeto novo
		if(componenteMissa == null){
			componenteMissa = ComponenteMissa.getInstance(tipoComponente);
		}

		//verifica se o objeto deve ser atualizado com os dados do form
		if(request.getAttribute(super.UPDATE_FROM_FORM) != null) updateFromForm(componenteMissa) ;		
		
		//coloca os objetos no request
		GrupoComponenteMissa grupoComponenteMissa = fichaMissaService.getGrupoComponenteMissa(usuario,getParam("id"));
		List<Prefacio> prefaciosCompartilhados = fichaMissaService.findPrefaciosCompartilhados(usuario);

		addObject("grupoComponenteMissa",grupoComponenteMissa);
		addObject("componenteMissa",componenteMissa);
		addObject("prefaciosCompartilhados",prefaciosCompartilhados);
		addObject("id",getParam("id"));
		addObject("tipo",getParam("tipo"));
		addObject("tipoComponente",getParam("tipoComponente"));

		//carrega o prefacio compartilhado, se for o caso
		if(componenteMissa instanceof Prefacio){
			Prefacio prefacioCompartilhado = ((Prefacio)componenteMissa).getPrefacioCompartilhado();
			if( prefacioCompartilhado != null ){
				prefacioCompartilhado = (Prefacio)fichaMissaService.getComponenteMissa(usuario, prefacioCompartilhado.getId());
				((Prefacio)componenteMissa).setPrefacioCompartilhado(prefacioCompartilhado);
			}
		}
		
		return new ModelAndView("fichaMissaEditarComponenteMissa");
	}

	public ModelAndView inserir(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();

		Integer tipoComponente = getParamAsInteger("tipoComponente");
		ComponenteMissa componenteMissa = ComponenteMissa.getInstance(tipoComponente);
		updateFromForm(componenteMissa);

		try{
			fichaMissaService.inserirComponenteMissa(usuario,componenteMissa);
		}catch (BumerangueRuntimeException e) {
			super.addError(e.getMessage());
			request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			return editarPre(request,response);
		}

		super.addSuccess(BumerangueSuccessRuntimeException.INSERCAO_REALIZADA);
		request.setAttribute("idComponente",componenteMissa.getId());
		return this.editarPre(request, response);
	}

	public ModelAndView editar(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		
		Integer tipoComponente = getParamAsInteger("tipoComponente");
		ComponenteMissa componenteMissa = ComponenteMissa.getInstance(tipoComponente);
		updateFromForm(componenteMissa);

		try{
			fichaMissaService.editarComponenteMissa(usuario,componenteMissa);
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
		
		Integer tipoComponente = getParamAsInteger("tipoComponente");
		ComponenteMissa componenteMissa = ComponenteMissa.getInstance(tipoComponente);
		updateFromForm(componenteMissa);
		
		fichaMissaService.excluirComponenteMissa(usuario,componenteMissa);

		super.addSuccess(BumerangueSuccessRuntimeException.EXCLUSAO_REALIZADA);
    	return this.editarPre(request, response);
	}

	private void updateFromForm(ComponenteMissa componenteMissa) {
		componenteMissa.setGrupoComponenteMissa(GrupoComponenteMissa.getInstance(getParamAsInteger("tipo")));
		componenteMissa.getGrupoComponenteMissa().setId(getParam("id"));
		componenteMissa.setId(getParam("idComponente"));
		componenteMissa.setTextoLatim(getParam("textoLatim"));
		if( componenteMissa instanceof Prefacio ){
			((Prefacio)componenteMissa).setProprio(getParamAsBoolean("proprio"));
			((Prefacio)componenteMissa).setCompartilhado(getParamAsBoolean("compartilhado"));
			((Prefacio)componenteMissa).setDescricao(getParam("descricao"));
			((Prefacio)componenteMissa).setPrefacioCompartilhado(new Prefacio(getParam("idPrefacioCompartilhado")));
		}
	}
}