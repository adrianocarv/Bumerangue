package br.org.ceu.bumerangue.controller.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import br.org.ceu.bumerangue.service.exceptions.BumerangueErrorRuntimeException;

public abstract class MultiActionBaseController extends BaseController {

	/**
	 * Atributos usados para ser o nome e o valor do atributo de request. Estes atributos ser�o do tipo String.
	 */
	protected String UPDATE_FROM_FORM = "UPDATE_FROM_FORM";
	protected String UPDATE_FROM_FORM2 = "UPDATE_FROM_FORM2";
	protected String UPDATE_FROM_FORM3 = "UPDATE_FROM_FORM3";
	protected String UPDATE_FROM_FORM4 = "UPDATE_FROM_FORM4";
	protected String UPDATE_FROM_FORM5 = "UPDATE_FROM_FORM5";

	protected abstract ModelAndView validateMulti(HttpServletRequest request, HttpServletResponse response);

	protected final void validate(){};

	/**
	 * Complementa a seguran�a de acesso aos m�todos dos controllers.<br>
	 * Realiza a restri��o considerando os m�dulos dispon�vies no Deploy da aplica��o.
	 * @author Adriano Carvalho
	 * @return
	 */
	protected abstract boolean isPertencenteModuloDisponivel();

	/**
	 * Completa a seguran�a declarativa realizada no web.xml<br>
	 * Declarativamente � definido quais permiss�es podem acessar os controllers,<br>
	 * por�m programaticamente � definido, dentre as permiss�es que acessam o controller,<br>
	 * quais m�todos cada permiss�o pode acessar.
	 * @author Adriano Carvalho
	 * @return
	 */
	protected abstract boolean isMetodoAutorizado();

	public ModelAndView execute() throws Exception {
		//realiza a restri��o considerando os m�dulos dispon�vies no Deploy da aplica��o.
		if(!isPertencenteModuloDisponivel()){
			throw new BumerangueErrorRuntimeException(getParam("method")+": é uma função pertencente a um módulo indisponível.");
		}
		
		//realiza a autoriza��o no n�vel de m�todo, complementando a autoriza��o no web.xml
		if(!isMetodoAutorizado()){
			throw new BumerangueErrorRuntimeException("é necessário ter permissão para acessar a função: "+getParam("method"));
		}

		return invokeNamedMethod(getParam("method"), getRequest(), getResponse());
	}
}
