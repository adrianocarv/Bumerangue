package br.org.ceu.bumerangue.controller.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.service.AdministracaoService;
import br.org.ceu.bumerangue.service.exceptions.BumerangueRuntimeException;
import br.org.ceu.bumerangue.service.exceptions.BumerangueSuccessRuntimeException;
import br.org.ceu.bumerangue.util.Utils;

public class ManterConfiguracaoController extends MultiActionBaseController {
	
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
		return null;
	}

	public ModelAndView listarPre(HttpServletRequest request, HttpServletResponse response) {
		
		String nomeModuloSelecionado = getParam("nomeModuloSelecionado");
		
		//verifica se o objeto deve ser atualizado com os dados do form
		if(request.getAttribute(super.UPDATE_FROM_FORM) != null){
			Usuario usuario = getSessionContainer().getUsuarioLogado();
			Map<String,String> configuracoes = administracaoService.findConfiguracoes(usuario, nomeModuloSelecionado);
			addObject("configuracoes",configuracoes);
		}

		addObject("nomeModuloSelecionado",nomeModuloSelecionado);
		
		return new ModelAndView("configuracoes");
	}

	public ModelAndView alterarModuloSelecionado(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
		return this.listarPre(request,response);
	}

	public ModelAndView salvar(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		
		String[] chaves = request.getParameterValues("chaves");
		String[] valores = request.getParameterValues("valores");
		
		try{
			administracaoService.salvarConfiguracoes(usuario, getParam("nomeModuloSelecionado"), chaves, valores);
		}catch (BumerangueRuntimeException e) {
			super.addError(e.getMessage());
			request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			return listarPre(request,response);
		}
		
		super.addSuccess(BumerangueSuccessRuntimeException.ALTERACAO_REALIZADA);
		request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
		return this.listarPre(request, response);
	}
}