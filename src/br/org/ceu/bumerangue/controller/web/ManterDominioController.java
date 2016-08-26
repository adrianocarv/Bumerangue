package br.org.ceu.bumerangue.controller.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import br.org.ceu.bumerangue.entity.Dominio;
import br.org.ceu.bumerangue.entity.ElementoDominio;
import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.service.AdministracaoService;
import br.org.ceu.bumerangue.service.exceptions.BumerangueRuntimeException;
import br.org.ceu.bumerangue.service.exceptions.BumerangueSuccessRuntimeException;

public class ManterDominioController extends MultiActionBaseController {
	
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
		if (getParam("method").equals("editarElementoDominio") || getParam("method").equals("inserirElementoDominio")) {
	    	super.isInformed("descricao","Descrição");

			//só retorna, se existir erro
			if (!getErrorMessages().isEmpty()){
				request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
				return this.selecionarElementoDominio(request,response);
			}
		}else if (getParam("method").equals("trocarPosicaoElementoDominio")) {
		    	super.isInformed("id","Elemento");

				//só retorna, se existir erro
				if (!getErrorMessages().isEmpty()){
					request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
					return this.selecionar(request,response);
				}
			}
		
		return null;
	}

	public ModelAndView listarPre(HttpServletRequest request, HttpServletResponse response) {
		
		String nomeModuloSelecionado = getParam("nomeModuloSelecionado");
		
		List dominios = Dominio.getDominiosPorModulo(nomeModuloSelecionado);
		addObject("dominios",dominios);

		addObject("nomeModuloSelecionado",nomeModuloSelecionado);
		
		return new ModelAndView("dominios");
	}

	public ModelAndView alterarModuloSelecionado(HttpServletRequest request, HttpServletResponse response) {
		return this.listarPre(request,response);
	}

	public ModelAndView selecionar(HttpServletRequest request, HttpServletResponse response) {
		//recupera pelo código
		Dominio dominio = administracaoService.getDominio(getSessionContainer().getUsuarioLogado(),getParamAsInteger("codigo"));
		//recupera pelo id, caso não haja por código
		if(dominio == null)
			dominio = administracaoService.getDominio(getSessionContainer().getUsuarioLogado(), getParam("idDominio"));

		addObject("dominio",dominio);

		return listarPre(request,response);
	}

	public ModelAndView selecionarElementoDominio(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		String id = request.getAttribute("id") != null ? request.getAttribute("id")+"" : getParam("id");

		//recupera do banco
		ElementoDominio elementoDominio = administracaoService.getElementoDominio(usuario,id);
		
		//se ainda não tiver no banco, instancia um objeto novo
		if(elementoDominio == null){
			Dominio dominio = administracaoService.getDominio(getSessionContainer().getUsuarioLogado(), getParam("idDominio"));
			elementoDominio = new ElementoDominio();
			elementoDominio.setDominio(dominio);
		}

		//verifica se o objeto deve ser atualizado com os dados do form
		if(request.getAttribute(super.UPDATE_FROM_FORM) != null){
			elementoDominio.setDescricao(getParam("descricao"));
			elementoDominio.setForaUso(getParamAsBoolean("foraUso"));
		}
		
		addObject("dominio",elementoDominio.getDominio());
		addObject("elementoDominio",elementoDominio);

		return listarPre(request,response);
	}

	public ModelAndView inserirElementoDominio(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();

		ElementoDominio elementoDominio = new ElementoDominio();
		elementoDominio.setDominio(new Dominio(getParam("idDominio")));
		elementoDominio.setDescricao(getParam("descricao"));
		elementoDominio.setForaUso(getParamAsBoolean("foraUso"));

		try{
			administracaoService.inserirElementoDominio(usuario,elementoDominio);
		}catch (BumerangueRuntimeException e) {
			super.addError(e.getMessage());
			request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			return selecionarElementoDominio(request,response);
		}

		super.addSuccess(BumerangueSuccessRuntimeException.INSERCAO_REALIZADA);
		return this.selecionar(request, response);
	}

	public ModelAndView editarElementoDominio(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		
		ElementoDominio elementoDominio = new ElementoDominio(getParam("id"));
		elementoDominio.setDominio(new Dominio(getParam("idDominio")));
		elementoDominio.setDescricao(getParam("descricao"));
		elementoDominio.setForaUso(getParamAsBoolean("foraUso"));

		try{
			administracaoService.editarElementoDominio(usuario,elementoDominio);
		}catch (BumerangueRuntimeException e) {
			super.addError(e.getMessage());
			request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			return selecionarElementoDominio(request,response);
		}
		
		super.addSuccess(BumerangueSuccessRuntimeException.ALTERACAO_REALIZADA);
		return this.selecionar(request, response);
	}
	
	public ModelAndView excluirElementoDominio(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		
		ElementoDominio elementoDominio = new ElementoDominio(getParam("id"));
		elementoDominio.setDominio(new Dominio(getParam("idDominio")));

		try{
			administracaoService.excluirElementoDominio(usuario,elementoDominio);
		}catch (BumerangueRuntimeException e) {
			super.addError(e.getMessage());
			request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			return selecionarElementoDominio(request,response);
		}
		
		super.addSuccess(BumerangueSuccessRuntimeException.EXCLUSAO_REALIZADA);
		return this.selecionar(request, response);
	}
	
	public ModelAndView trocarPosicaoElementoDominio(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();

		ElementoDominio elementoDominio = new ElementoDominio(getParam("id"));
		Dominio dominio = new Dominio(getParam("idDominio"));
		Integer direcao = getParamAsInteger("direcao");
		Integer posicaoDestino = getParamAsInteger("posicaoDestino");

		try{
			administracaoService.trocarPosicaoElementoDominio(usuario,elementoDominio,dominio,direcao,posicaoDestino);
		}catch (BumerangueRuntimeException e) {
			super.addError(e.getMessage());
			request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			return selecionar(request,response);
		}
		
		return this.selecionar(request, response);
	}
	
}