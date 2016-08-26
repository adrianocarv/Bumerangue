package br.org.ceu.bumerangue.controller.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import br.org.ceu.bumerangue.entity.Dominio;
import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.entity.criterias.PesquisaUsuarioCriteria;
import br.org.ceu.bumerangue.entity.suporte.deploy.Deploy;
import br.org.ceu.bumerangue.service.AdministracaoService;
import br.org.ceu.bumerangue.service.exceptions.BumerangueAlertRuntimeException;
import br.org.ceu.bumerangue.service.exceptions.BumerangueErrorRuntimeException;
import br.org.ceu.bumerangue.service.exceptions.BumerangueSuccessRuntimeException;

public class CompartilharUsuarioController extends MultiActionBaseController {
	
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
	    	super.isInformed("nomeModuloSelecionado","Compartilhar com o módulo");

			//s� retorna, se existir erro
			if (!getErrorMessages().isEmpty()){
				request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			}
			return this.compartilharPre(request,response);
		}else if (getParam("method").equals("selecionarTipoPermissaoParaTodos")) {
			return this.compartilharPre(request,response);
		}

		return null;
	}

	public ModelAndView compartilharPre(HttpServletRequest request, HttpServletResponse response) {
		PesquisaUsuarioCriteria pesquisaUsuarioCriteria = new PesquisaUsuarioCriteria();
		
		//verifica se o objeto deve ser atualizado com os dados do form
		if(request.getAttribute(super.UPDATE_FROM_FORM) != null) this.updateFromForm(pesquisaUsuarioCriteria) ;		

		//coloca os objetos no request
		addObject("pesquisaUsuarioCriteria",pesquisaUsuarioCriteria);
		addObject("nomesModulos",Deploy.getNomesModulosDisponiveis());
		return new ModelAndView("compartilharUsuarios");
	}
	
	public ModelAndView pesquisar(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuarioLogado = getSessionContainer().getUsuarioLogado();
		PesquisaUsuarioCriteria pesquisaUsuarioCriteria = new PesquisaUsuarioCriteria();
		this.updateFromForm(pesquisaUsuarioCriteria);		

		if(pesquisaUsuarioCriteria.isEmpty()) throw new BumerangueAlertRuntimeException("é necessário informar pelos menos 1 campo.");
		
		Dominio tipoPermissoes = administracaoService.getDominio(usuarioLogado,Dominio.BUMERANGUE_TIPO_PERMISSAO.getCodigo());
		List<Usuario> usuariosParaCompartilhar = administracaoService.pesquisarUsuariosParaCompartilhar(usuarioLogado,pesquisaUsuarioCriteria);
		List<Usuario> usuariosModulo = administracaoService.pesquisarUsuariosModulo(usuarioLogado,pesquisaUsuarioCriteria);

		addObject("pesquisaUsuarioCriteria",pesquisaUsuarioCriteria);
		addObject("tipoPermissoes",tipoPermissoes.getElementosDominio());
		addObject("usuariosParaCompartilhar",usuariosParaCompartilhar);
		addObject("usuariosModulo",usuariosModulo);

		return new ModelAndView("compartilharUsuarios");
	}

	public ModelAndView compartilhar(HttpServletRequest request, HttpServletResponse response) {
		
		String[] arrayIdUsuario_codigoTipoPermissao = request.getParameterValues("idUsuario_codigoTipoPermissao");
		administracaoService.compartilharUsuarios(getSessionContainer().getUsuarioLogado(),arrayIdUsuario_codigoTipoPermissao,getParam("nomeModuloSelecionado"));
		
		super.addSuccess(BumerangueSuccessRuntimeException.COMPARTILHAMENTO_REALIZADO);
		request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
		return this.compartilharPre(request,response);
	}

	public ModelAndView alterarModuloSelecionado(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
		return this.compartilharPre(request,response);
	}

	public ModelAndView selecionarTipoPermissaoParaTodos(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("codigoTipoPermissaoTodos",getParam("codigoTipoPermissaoTodos"));
		return this.pesquisar(request,response);
	}

	public ModelAndView selecionarUsuarioModuloSelecionado(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = administracaoService.getUsuario(getSessionContainer().getUsuarioLogado(),getParam("id"));
		String urlDestino = "manter"+usuario.getClass().getSimpleName()+".action?method=selecionar&id="+usuario.getId();

		try {
			request.getRequestDispatcher(urlDestino).forward(request,response);
		} catch (Exception e) {
			throw new BumerangueErrorRuntimeException(e.getMessage());
		}

		return new ModelAndView("compartilharUsuarios");
	}
	
	private void updateFromForm(PesquisaUsuarioCriteria pesquisaUsuarioCriteria) {
		pesquisaUsuarioCriteria.setNome(getParam("nome"));
		pesquisaUsuarioCriteria.setNomeCompleto(getParam("nomeCompleto"));
		pesquisaUsuarioCriteria.setEmail(getParam("email"));
		pesquisaUsuarioCriteria.setNomeModulo(getParam("nomeModulo"));
		pesquisaUsuarioCriteria.setNomeModuloSelecionado(getParam("nomeModuloSelecionado"));
	}
}