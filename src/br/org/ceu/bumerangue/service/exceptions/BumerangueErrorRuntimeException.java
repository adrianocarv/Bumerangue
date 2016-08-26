package br.org.ceu.bumerangue.service.exceptions;

/**
 * Exception do tipo Erro
 */
public class BumerangueErrorRuntimeException extends BumerangueRuntimeException {

	private static final long serialVersionUID = 1L;

	public BumerangueErrorRuntimeException(String msg, Throwable ex) {
		super(msg, ex);
		// TODO Auto-generated constructor stub
	}

	public BumerangueErrorRuntimeException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

}
