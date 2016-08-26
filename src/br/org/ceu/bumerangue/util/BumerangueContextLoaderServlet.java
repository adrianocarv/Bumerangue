package br.org.ceu.bumerangue.util;

import javax.servlet.ServletException;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ContextLoaderServlet;

public class BumerangueContextLoaderServlet extends ContextLoaderServlet implements ApplicationContextAware{

	// -- ApplicationContext que fornece acesso aos beans do contexto do Spring ----------------------------
	protected ApplicationContext appContext;
	final public void setApplicationContext(ApplicationContext applicationContext) throws BeansException { appContext = applicationContext; }
	// -----------------------------------------------------------------------------------------------------
	
	private static final long serialVersionUID = 1L;

	
	public void init() throws ServletException {
		super.init();
	}
}
