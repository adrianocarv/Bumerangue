package br.org.ceu.bumerangue.controller.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import br.org.ceu.bumerangue.entity.Dominio;
import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.entity.criterias.PesquisaUsuarioCriteria;
import br.org.ceu.bumerangue.service.BumerangueService;
import br.org.ceu.bumerangue.service.exceptions.BumerangueAlertRuntimeException;
import br.org.ceu.bumerangue.service.exceptions.BumerangueRuntimeException;
import br.org.ceu.bumerangue.service.exceptions.BumerangueSuccessRuntimeException;
import br.org.ceu.bumerangue.util.SessionContainer;
import br.org.ceu.bumerangue.util.ValidationRules;

public class ManterUsuarioController extends MultiActionBaseController {
	
	/** Service */
	private BumerangueService bumerangueService;
	public void setBumerangueService(BumerangueService bumerangueService){ this.bumerangueService = bumerangueService; }
	
	public boolean isPertencenteModuloDisponivel(){
		return true;
	}

	public boolean isMetodoAutorizado(){
		return true;
	}

	public ModelAndView validateMulti(HttpServletRequest request, HttpServletResponse response) {
		if (getParam("method").equals("editar") || getParam("method").equals("inserir")) {
	    	super.isInformed("nome","Nome");
	    	super.isInformed("nomeCompleto","Nome completo");
	    	if(super.isInformed("email","E-mail"))
	    		super.isValidEmails("email","E-mail");
			if(getParam("method").equals("inserir")) super.isInformed("nomeModulo","Módulo");
	    	if(getParam("method").equals("editar"))super.isInformed("codigoTipoPermissao","Tipo de permissão");

			//s� retorna, se existir erro
			if (!getErrorMessages().isEmpty()){
				request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
				return this.selecionar(request,response);
			}
		}else if (getParam("method").equals("pesquisar")) {
			//carrrega o form s� se existir erro
			if (!getErrorMessages().isEmpty()){
				request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			}
	    	return this.pesquisarPre(request,response);			
		}

		return null;
	}

	public ModelAndView pesquisarPre(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		PesquisaUsuarioCriteria pesquisaUsuarioCriteria = new PesquisaUsuarioCriteria();
		
		//verifica se o objeto deve ser atualizado com os dados do form
		if(request.getAttribute(super.UPDATE_FROM_FORM) != null) this.updateFromForm(pesquisaUsuarioCriteria) ;		

		//coloca os objetos no request
		Dominio tipoPermissoes = bumerangueService.getDominio(usuario,Dominio.BUMERANGUE_TIPO_PERMISSAO.getCodigo());

		//limpa a listagem, se for o caso.
		if(!getParamAsBoolean(SessionContainer.REQUEST_ATTRIBUTE_PAGINACAO).booleanValue()){
			getSessionContainer().setUsuarios(null);
		}

		addObject("tipoPermissoes",tipoPermissoes.getElementosDominio());
		addObject("pesquisaUsuarioCriteria",pesquisaUsuarioCriteria);
		
		return new ModelAndView("pesquisarUsuario");
	}

	public ModelAndView pesquisar(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuarioLogado = getSessionContainer().getUsuarioLogado();
		PesquisaUsuarioCriteria pesquisaUsuarioCriteria = new PesquisaUsuarioCriteria();
		this.updateFromForm(pesquisaUsuarioCriteria);		

		if(pesquisaUsuarioCriteria.isEmpty())
			throw new BumerangueAlertRuntimeException("é necessário informar pelos menos 1 campo.");
		if(!StringUtils.isBlank(pesquisaUsuarioCriteria.getIdTipoPermissao()) && StringUtils.isBlank(pesquisaUsuarioCriteria.getNomeModulo()))
			throw new BumerangueAlertRuntimeException("Ao selecionar o 'Tipo de Permiss�o', é necessário informar o 'Módulo'.");
		
		List<Usuario> usuarios = new ArrayList<Usuario>();
		//evita ir novamente no banco.
		if(!getParamAsBoolean(SessionContainer.REQUEST_ATTRIBUTE_PAGINACAO).booleanValue()){
			usuarios = bumerangueService.pesquisarUsuarios(usuarioLogado,pesquisaUsuarioCriteria);
			getSessionContainer().setUsuarios(usuarios);
		}
		
		//se retornar apenas um elemento, vai direto para tela de detalhes
		if(usuarios.size() == 1){
			Usuario usuario = bumerangueService.getUsuario(usuarioLogado,usuarios.get(0).getId());
			request.setAttribute("id",usuario.getId());
			return selecionar(request,response);
		}
		
		List<Usuario> usuariosCompartilhados = new ArrayList<Usuario>();
		if(ValidationRules.isInformed(pesquisaUsuarioCriteria.getNomeModulo()))
			usuariosCompartilhados = bumerangueService.pesquisarUsuariosCompartilhados(usuarioLogado,pesquisaUsuarioCriteria,pesquisaUsuarioCriteria.getNomeModulo());

		addObject("usuariosCompartilhados",usuariosCompartilhados);
		addObject("pesquisaUsuarioCriteria",pesquisaUsuarioCriteria);

		return new ModelAndView("pesquisarUsuario");
	}

	public ModelAndView selecionar(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuarioLogado = getSessionContainer().getUsuarioLogado();
		String id = request.getAttribute("id") != null ? request.getAttribute("id")+"" : getParam("id");

		//recupera do banco
		Usuario usuario = (Usuario)bumerangueService.getUsuario(usuarioLogado,id);
		
		//se ainda n�o tiver no banco, instancia um objeto novo
		if(usuario == null) usuario = new Usuario();

		//verifica se o objeto deve ser atualizado com os dados do form
		if(request.getAttribute(super.UPDATE_FROM_FORM) != null) updateFromForm(usuario) ;		
		
		//coloca os objetos no request
		Dominio tipoPermissoes = bumerangueService.getDominio(usuarioLogado,Dominio.BUMERANGUE_TIPO_PERMISSAO.getCodigo());

		usuario.setCodigoTipoPermissao(usuario.getTipoPermissaoTipoUsuario().getCodigo());
		addObject("usuario",usuario);
		addObject("tipoPermissoes",tipoPermissoes.getElementosDominio());

		return new ModelAndView("editarUsuario");
	}

	public ModelAndView editar(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuarioLogado = getSessionContainer().getUsuarioLogado();
		
		Usuario usuario = new Usuario();
		updateFromForm(usuario);

		try{
			bumerangueService.editarUsuario(usuarioLogado,usuario);
		}catch (BumerangueRuntimeException e) {
			super.addError(e.getMessage());
			request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			return selecionar(request,response);
		}
		
		super.addSuccess(BumerangueSuccessRuntimeException.ALTERACAO_REALIZADA);
		return this.selecionar(request, response);
	}

	public ModelAndView inserir(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuarioLogado = getSessionContainer().getUsuarioLogado();

		Usuario usuario = Usuario.getInstance(getParam("nomeModulo"));
		updateFromForm(usuario);

		try{
			bumerangueService.inserirUsuario(usuarioLogado,usuario);
		}catch (BumerangueRuntimeException e) {
			super.addError(e.getMessage());
			request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			return selecionar(request,response);
		}

		super.addSuccess(BumerangueSuccessRuntimeException.INSERCAO_REALIZADA);
		request.setAttribute("id",usuario.getId());
		return this.selecionar(request, response);
	}

	public ModelAndView excluir(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuarioLogado = getSessionContainer().getUsuarioLogado();
		
		Usuario usuario = new Usuario();
		usuario.setId(getParam("id"));
		
		bumerangueService.excluirUsuario(usuarioLogado,usuario);

		//verifica se foi exclu�do, ou ficou desativado
		usuario = bumerangueService.getUsuario(usuarioLogado,usuario.getId());
    	if(usuario != null) throw new BumerangueAlertRuntimeException("O usuário foi colocado como desativado, por participar de empréstimos.");

		super.addSuccess(BumerangueSuccessRuntimeException.EXCLUSAO_REALIZADA);
    	return this.pesquisarPre(request, response);
	}

	public ModelAndView reiniciarSenha(HttpServletRequest request, HttpServletResponse response) {
		
		bumerangueService.reiniciarSenhaUsuario(getSessionContainer().getUsuarioLogado(),new Usuario(getParam("id")));
		
		super.addSuccess(BumerangueSuccessRuntimeException.SENHA_REINICIADA);
		return this.selecionar(request, response);
	}

	public ModelAndView desbloquearUsuario(HttpServletRequest request, HttpServletResponse response) {
		bumerangueService.desbloquearUsuario(getSessionContainer().getUsuarioLogado(),new Usuario(getParam("id")));
		
		super.addSuccess(BumerangueSuccessRuntimeException.DESBLOQUEIO_REALIZADO);
		return this.selecionar(request, response);
	}
	 
	private void updateFromForm(Usuario usuario) {
		usuario.setId(getParam("id"));
		usuario.setNome(getParam("nome"));
		usuario.setNomeCompleto(getParam("nomeCompleto"));
		usuario.setEmail(getParam("email"));
		usuario.setTelefone(getParam("telefone"));
		usuario.setAtivo(getParamAsBoolean("ativo"));
		usuario.setNomeModulo(getParam("nomeModulo"));
		usuario.setCodigoTipoPermissao(getParamAsInteger("codigoTipoPermissao"));
	}

	private void updateFromForm(PesquisaUsuarioCriteria pesquisaUsuarioCriteria) {
		pesquisaUsuarioCriteria.setNome(getParam("nome"));
		pesquisaUsuarioCriteria.setNomeCompleto(getParam("nomeCompleto"));
		pesquisaUsuarioCriteria.setEmail(getParam("email"));
		pesquisaUsuarioCriteria.setAtivo(getParamAsBoolean("ativo"));
		pesquisaUsuarioCriteria.setBloqueado(getParamAsBoolean("bloqueado"));
		pesquisaUsuarioCriteria.setIdTipoPermissao(getParam("idTipoPermissao"));
		pesquisaUsuarioCriteria.setNomeModulo(getParam("nomeModulo"));
	}
}