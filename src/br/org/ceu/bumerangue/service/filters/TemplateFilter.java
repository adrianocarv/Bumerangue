package br.org.ceu.bumerangue.service.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import br.org.ceu.bumerangue.util.SessionContainer;

/**
 * Intercepta todos a requisições, a fim de manter o atributo wrapperTemplate atualizado na session.<br>
 * @author Adriano
 */
public class TemplateFilter implements Filter{

	public void init(FilterConfig filterConfig){}
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	HttpServletRequest httpReq = (HttpServletRequest)request;

    	//verifica se o atributo hideTemplate foi informado como parâmetro do request, então atualiza o SessionContainer
    	if(httpReq.getParameter(SessionContainer.SESSION_ATTRIBUTE_HIDE_TEMPLATE) != null ){

        	SessionContainer sc = this.getSessionContainer(httpReq);

        	boolean hideTemplate = new Boolean(httpReq.getParameter(SessionContainer.SESSION_ATTRIBUTE_HIDE_TEMPLATE)); 
        	
        	sc.setHideTemplate(hideTemplate);
    	}

    	//verifica se o atributo urlCSS foi informado como parâmetro do request, então atualiza o SessionContainer
    	if(httpReq.getParameter(SessionContainer.SESSION_ATTRIBUTE_URL_CSS) != null ){

        	SessionContainer sc = this.getSessionContainer(httpReq);

        	String urlCSS = httpReq.getParameter(SessionContainer.SESSION_ATTRIBUTE_URL_CSS); 

        	sc.setUrlCSS(urlCSS);
    	}

		chain.doFilter(request,response);
    }

    private SessionContainer getSessionContainer(HttpServletRequest httpReq) {
		SessionContainer sc = (SessionContainer) httpReq.getSession().getAttribute(SessionContainer.SESSION_CONTAINER_ATTRIBUTE_NAME);

		/* Se ainda não existir um sessionContainer na sessão, cria um novo. */
		if (sc == null) {
			sc = new SessionContainer();
			httpReq.getSession().setAttribute(SessionContainer.SESSION_CONTAINER_ATTRIBUTE_NAME, sc);
		}
		return sc;
	}
    
    public void destroy(){}
    }    
