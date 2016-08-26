package br.org.ceu.bumerangue.service.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.entity.suporte.deploy.Deploy;
import br.org.ceu.bumerangue.service.BumerangueService;
import br.org.ceu.bumerangue.service.impl.BaseServiceImpl;
import br.org.ceu.bumerangue.util.BumerangueContextHelper;
import br.org.ceu.bumerangue.util.MD5;
import br.org.ceu.bumerangue.util.SessionContainer;
import br.org.ceu.bumerangue.util.Utils;
import br.org.ceu.bumerangue.util.ValidationRules;

/**
 * Intercepta todos a requisi��es, a fim de manter o atributo usu�rio logado na session.<br>
 * Alem disso, realiza as instu��es de neg�cio relacionadas ao login do usu�rio na aplica��o. 
 * @author Adriano
 */
public class LoginFilter implements Filter{

	public void init(FilterConfig filterConfig){}
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	HttpServletRequest httpReq = (HttpServletRequest)request;

    	//verifica se o usu�rio efetuou login e n�o foi colocado como atributo na session
    	SessionContainer sc = (SessionContainer)httpReq.getSession().getAttribute(SessionContainer.SESSION_CONTAINER_ATTRIBUTE_NAME);
    	if(httpReq.getUserPrincipal() != null && (sc == null || sc.getUsuarioLogado() == null) ){
	    	//Instru��es para login do usu�rio
	    	BumerangueService bumerangueService = (BumerangueService) BumerangueContextHelper.getInstance().getApplicationContext().getBean("bumerangueService");
	
	    	String nome = httpReq.getUserPrincipal().getName();
			
	    	//no caso de algum problema para logar, ser� lan�ada uma excess�o.
	    	//Excepcionalmente, pelo fato se ser um filter, o fluxo de mensagem recebe um tratamento especial.
	    	try{
	    		Usuario usuario = bumerangueService.verificarLogin(nome);
	    		usuario.setIp(httpReq.getRemoteHost()); // seta o ip do usu�rio
	    		usuario.setHttpSession(httpReq.getSession()); // seta a sess�o do usu�rio
	    		sc.setUsuarioLogado(usuario);
	    	}catch (Exception e) {
				httpReq.getSession().invalidate();
				httpReq.setAttribute(SessionContainer.REQUEST_ATTRIBUTE_MESSAGES, new String[]{e.getMessage()});
	        	httpReq.setAttribute(SessionContainer.REQUEST_ATTRIBUTE_MESSAGE_ICO_FILE_NAME, SessionContainer.REQUEST_ATTRIBUTE_MESSAGE_ICO_ERROR);
			}
    	}
    
		//seta o caminho WebRealPath para a camada Service
		BaseServiceImpl.setWebRealPath(Utils.getRealPath(httpReq, ""));
		
    	//tratamento especial para o usu�rio ADMIN
    	if(sc != null) validaDadosAdmin(httpReq, response, sc.getUsuarioLogado());

    	chain.doFilter(request,response);
    }
    
    /**
     * Verifica se o usu�rio � o ADMIN.
     * Caso seja, n�o permite que o mesmo esteja com a senha padr�o de ADMIN, nem sem e-mail.
     * @param request
     * @param response
     * @param usuarioLogado
     */
    private void validaDadosAdmin(HttpServletRequest request, ServletResponse response, Usuario usuarioLogado) throws IOException, ServletException{
    	if(usuarioLogado == null || !usuarioLogado.isAdmin()) return;

    	String msg = "";
		String urlRequisicao = request.getServletPath() + "?method=" + request.getParameter("method");
		String urlDestino = "";
		boolean redireciona = false;

		//verifica se est� com a senha padr�o, se sim, encaminha para a tela de troca de senha
		if( usuarioLogado.getSenha().equals( MD5.crypt(Usuario.SENHA_PADRAO_USUARIO_ADMIN) ) ){
			//n�o critica, se a requisi��o for para as urls:
			if(!urlRequisicao.equals("/manterSeguranca.action?method=trocarSenha") && !urlRequisicao.equals("/manterSeguranca.action?method=sair")){
    			msg = "A senha do usu�rio ADMIN deve ser trocada para outra diferente da senha padr�o do ADMIN. Depois disso, � necess�rio logar novamente na aplica��o.";
    			urlDestino = "manterSeguranca.action?method=trocarSenhaPre";
    			redireciona =  true;
			}
		}

		//verifica se est� sem e-mail, se sim, encaminha para a tela de altera��o de usu�rio.
		if( !redireciona && !ValidationRules.isInformed(usuarioLogado.getEmail()) ){
			//n�o critica, se a requisi��o for para as urls:
			if(!urlRequisicao.equals("/manterSeguranca.action?method=editarUsuarioAdmin") && !urlRequisicao.equals("/manterSeguranca.action?method=sair")){
    			msg = "O e-mail do usu�rio ADMIN presica ser informado. Depois disso, é necessário logar novamente na aplicação.";
    			urlDestino = "manterSeguranca.action?method=selecionarUsuarioAdmin&id="+usuarioLogado.getId();
    			redireciona =  true;
			}
		}

		//realiza o redirecionamento, se for o caso.
		if(redireciona){
			List messages = new ArrayList();
			messages.add(msg);
			request.setAttribute(SessionContainer.REQUEST_ATTRIBUTE_MESSAGES,messages);
        	request.setAttribute(SessionContainer.REQUEST_ATTRIBUTE_MESSAGE_ICO_FILE_NAME, SessionContainer.REQUEST_ATTRIBUTE_MESSAGE_ICO_WARN);
			request.getRequestDispatcher(urlDestino).forward(request,response);
		}
    }
    
    public void destroy(){}
    }    
