package br.org.ceu.bumerangue.service.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Classe Base de todas as Runtime Exceptions que permite encadear as chamadas.
 * O construtor default foi omitido para forçar a passagem de uma descrição de erro. 
 */
public abstract class BumerangueRuntimeException extends RuntimeException {

		private Throwable cause;

		public BumerangueRuntimeException(String msg) {
			super(msg);
		}

		public BumerangueRuntimeException(String msg, Throwable ex) {
			super(msg);
			this.cause = ex;
		}

		public Throwable getCause() {
			return (this.cause == this ? null : this.cause);
		}

		public String getMessage() {
			if (getCause() == null) {
				return super.getMessage();
			}
			else {
				return super.getMessage() + "; nested exception is " + getCause().getClass().getName() +
						": " + getCause().getMessage();
			}
		}

		public void printStackTrace(PrintStream ps) {
			if (getCause() == null) {
				super.printStackTrace(ps);
			}
			else {
				ps.println(this);
				getCause().printStackTrace(ps);
			}
		}

		public void printStackTrace(PrintWriter pw) {
			if (getCause() == null) {
				super.printStackTrace(pw);
			}
			else {
				pw.println(this);
				getCause().printStackTrace(pw);
			}
		}

		public boolean contains(Class exClass) {
			if (exClass == null) {
				return false;
			}
			Throwable ex = this;
			while (ex != null) {
				if (exClass.isInstance(ex)) {
					return true;
				}
				if (ex instanceof BumerangueRuntimeException) {
					// Cast is necessary on JDK 1.3, where Throwable does not
					// provide a "getCause" method itself.
					ex = ((BumerangueRuntimeException) ex).getCause();
				}
				else {
					ex = null;
				}
			}
			return false;
		}

}
