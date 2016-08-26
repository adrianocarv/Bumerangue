package br.org.ceu.bumerangue.controller.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.service.BumerangueService;
import br.org.ceu.bumerangue.service.exceptions.BumerangueAlertRuntimeException;
import br.org.ceu.bumerangue.service.exceptions.BumerangueSuccessRuntimeException;
import br.org.ceu.bumerangue.service.impl.BaseServiceImpl;
import br.org.ceu.bumerangue.util.Utils;
import br.org.ceu.bumerangue.util.ValidationRules;

public class ManterSegurancaController extends MultiActionBaseController {
	
	/** Service */
	private BumerangueService bumerangueService;
	public void setBumerangueService(BumerangueService bumerangueService){ this.bumerangueService = bumerangueService; }
	
	public boolean isPertencenteModuloDisponivel(){
		return true;
	}

	public boolean isMetodoAutorizado(){
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		if(usuario == null || !usuario.isInRole("1,4,7,10")){

			//métodos autorizados para usuários não logados
			String metodosAutorizados = ",apresentacaoPre,logar,logarErro,logarPre,lembrarSenhaPre,lembrarSenha,lembrarSenhaConfirmacao,comentarSugerirPre,comentarSugerir,";

			//métodos autorizados para usuários avançados e básicos. acumula os do usuário não logado.
			if(usuario != null)
				metodosAutorizados += "sair,trocarSenha,trocarSenhaPre,";

			String metodo = ","+getParam("method")+",";
			return metodosAutorizados.indexOf(metodo) != -1;
		}
		return true;
	}

	public ModelAndView validateMulti(HttpServletRequest request, HttpServletResponse response) {
		if (getParam("method").equals("trocarSenha")) {
	    	super.isInformed("novaSenha","Nova senha");
	    	super.isInformed("novaSenhaConfirma","Confirmação da nova senha");
			return this.trocarSenhaPre(request,response);
		}else if (getParam("method").equals("lembrarSenha")) {
			if(!ValidationRules.isInformed(getParam("nome")) && !ValidationRules.isInformed(getParam("email")))
				super.addError("é necessário informar o nome ou o e-mail do usuário.");
			else if(ValidationRules.isInformed(getParam("email")))
				super.isValidEmail("email","E-mail");
			return this.lembrarSenhaPre(request,response);
		}else if (getParam("method").equals("comentarSugerir")) {
			if(getSessionContainer().getUsuarioLogado() == null){
				super.isInformed("nomeRemetente","Nome");
				if(super.isInformed("emailRemetente","E-mail"))
					super.isValidEmail("emailRemetente","E-mail");
			}
			super.isInformed("textoComentario","Mensagem");
			return this.comentarSugerirPre(request,response);
		}else if (getParam("method").equals("editarUsuarioAdmin")) {
	    	super.isInformed("nomeCompleto","Nome completo");
	    	if(super.isInformed("email","E-mail"))
	    		super.isValidEmail("email","E-mail");

			//só retorna, se existir erro
			if (!getErrorMessages().isEmpty()){
				request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
				return this.selecionarUsuarioAdmin(request,response);
			}
		}
		return null;
	}

	public ModelAndView apresentacaoPre(HttpServletRequest request, HttpServletResponse response) {
		
		//Caso o usuário esteja logado, recupera as notificações, seta na session e lança os alertas
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		if(usuario != null){
			Map<String,String> notificacoes = bumerangueService.getNotificacoes(usuario);
			getSessionContainer().setNotificacoes(notificacoes);
			for(String key : notificacoes.keySet()){
				super.addAlert(notificacoes.get(key));
			}
		}
		
		//se for informada, usa a url informada ao bypassar o login
		if("true".equalsIgnoreCase(request.getParameter("bypass")) && request.getParameter("url") != null){
			try {
				request.getRequestDispatcher(request.getParameter("url")).forward(request,response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return new ModelAndView("apresentacao");
	}

	public ModelAndView logarPre(HttpServletRequest request, HttpServletResponse response) {
		
		//vaibiliza bypassar o login
		if("true".equalsIgnoreCase(request.getParameter("bypass"))){
			request.setAttribute("bypass", "true");
			request.setAttribute("j_username", request.getParameter("j_username"));
			request.setAttribute("j_password", request.getParameter("j_password"));
			request.setAttribute("url", request.getParameter("url"));
		}
		
		return new ModelAndView("login");
	}

	public ModelAndView logarErro(HttpServletRequest request, HttpServletResponse response) {

		//seta o caminho WebRealPath para a camada Service
		BaseServiceImpl.setWebRealPath(Utils.getRealPath(request, ""));
		
		int status = bumerangueService.registrarErroLogin(getParam("j_username"));
		if(status == 1)
			addError("Usuário bloqueado, por ter atingido o número máximo de tentativas consecutivas de login errada. Entre em contato com o administrador do Bumerangue.");
		else if(status == 2)
			addAlert("Senha não confere. Após a "+super.getResourceMessage("bmg.manterSeguranca.numeroMaximoTentativasErradas")+"ª tentativa consecutiva de login errada, este usuário será bloqueado.");
		else if(status == 3)
			addError("Usuário não encontrado.");

		return new ModelAndView("loginErro");
	}

	public ModelAndView logar(HttpServletRequest request, HttpServletResponse response) {
		//apenas direciona para página de apresentação
		return apresentacaoPre(request,response);
	}

	public ModelAndView sair(HttpServletRequest request, HttpServletResponse response) {
		
		getRequest().getSession().invalidate();

		return apresentacaoPre(request,response);
	}

	public ModelAndView trocarSenhaPre(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("trocarSenha");
	}

	public ModelAndView trocarSenha(HttpServletRequest request, HttpServletResponse response) {
		
		bumerangueService.trocarSenhaUsuario(getSessionContainer().getUsuarioLogado(),getParam("senhaAtual"),getParam("novaSenha"),getParam("novaSenhaConfirma"));
		
		super.addSuccess(BumerangueSuccessRuntimeException.ALTERACAO_REALIZADA);
		return apresentacaoPre(request,response);
	}

	public ModelAndView menuAdministracao(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("administracao");
	}

	public ModelAndView lembrarSenhaPre(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("lembreteSenha");
	}

	public ModelAndView lembrarSenha(HttpServletRequest request, HttpServletResponse response) {

		Usuario usuario = null;
		try{
			usuario = bumerangueService.lembrarSenha(getParam("nome"),getParam("email"));
		}catch (RuntimeException e) {
			super.addError(e.getMessage());
			return new ModelAndView("lembreteSenha");
		}

		super.addSuccess("Um e-mail foi enviado para '"+usuario.getEmail()+"'. É necessário acessá-lo para confirmar o envio da sua nova senha.");
		return new ModelAndView("lembreteSenha");
	}
	
	public ModelAndView lembrarSenhaConfirmacao(HttpServletRequest request, HttpServletResponse response) {
		
		Usuario usuario = null;
		try{
			usuario = bumerangueService.lembrarSenhaConfirmacao(getParam("idUsuario"),getParam("id5"));
		}catch (BumerangueAlertRuntimeException e) {
			super.addAlert(e.getMessage());
			return new ModelAndView("lembreteSenha");
		}
		
		addSuccess("A nova senha do usuário '"+usuario.getNome()+"' foi enviada para o e-mail '"+usuario.getEmail()+"'.");
		return new ModelAndView("lembreteSenha");
	}

	public ModelAndView comentarSugerirPre(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("nomeRemetente",getParam("nomeRemetente"));
		request.setAttribute("emailRemetente",getParam("emailRemetente"));
		request.setAttribute("textoComentario",getParam("textoComentario"));
		request.setAttribute("nomeRemetente",getParam("nomeRemetente"));
		request.setAttribute("emailAdmin",bumerangueService.getUsuarioPorNome(Usuario.NOME_USUARIO_ADMIN).getEmail());
		return new ModelAndView("comentarioSugestao");
	}

	public ModelAndView comentarSugerir(HttpServletRequest request, HttpServletResponse response) {

		bumerangueService.comentarSugerir(getSessionContainer().getUsuarioLogado(),getParam("nomeRemetente"),getParam("emailRemetente"),getParam("textoComentario"));

		addSuccess("Mensagem enviada com sucesso. A equipe Bumerangue agradece a contribuição.");
		return apresentacaoPre(request,response);
	}

	public ModelAndView selecionarUsuarioAdmin(HttpServletRequest request, HttpServletResponse response) {
		//recupera do banco
		Usuario usuarioAdmin = bumerangueService.getUsuarioPorNome(Usuario.NOME_USUARIO_ADMIN);
		
		//verifica se o objeto deve ser atualizado com os dados do form
		if(request.getAttribute(super.UPDATE_FROM_FORM) != null) updateFromForm(usuarioAdmin) ;		
		
		addObject("usuarioAdmin",usuarioAdmin);

		return new ModelAndView("editarUsuarioAdmin");
	}

	public ModelAndView editarUsuarioAdmin(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		
		Usuario usuarioAdmin = new Usuario();
		updateFromForm(usuarioAdmin);

		bumerangueService.editarUsuario(usuario,usuarioAdmin);
		
		super.addSuccess(BumerangueSuccessRuntimeException.ALTERACAO_REALIZADA);
		return this.menuAdministracao(request, response);
	}
	
	private void updateFromForm(Usuario usuarioAdmin) {
		usuarioAdmin.setId(getParam("id"));
		usuarioAdmin.setNome(Usuario.NOME_USUARIO_ADMIN);
		usuarioAdmin.setNomeCompleto(getParam("nomeCompleto"));
		usuarioAdmin.setEmail(getParam("email"));
		usuarioAdmin.setTelefone(getParam("telefone"));
	}

}