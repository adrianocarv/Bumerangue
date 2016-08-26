package br.org.ceu.bumerangue.service.listeners;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import br.org.ceu.bumerangue.util.SessionContainer;

public class LogoutListener implements HttpSessionListener{

	public void sessionCreated(HttpSessionEvent httpSessionEvent) {}

	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		//Retira o atributo usuario logado da session.
    	SessionContainer sc = (SessionContainer)httpSessionEvent.getSession().getAttribute(SessionContainer.SESSION_CONTAINER_ATTRIBUTE_NAME);
    	if(sc != null && sc.getUsuarioLogado() != null) sc.setUsuarioLogado(null);
	}
}
