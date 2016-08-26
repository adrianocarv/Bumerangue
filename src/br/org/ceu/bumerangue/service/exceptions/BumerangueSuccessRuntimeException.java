package br.org.ceu.bumerangue.service.exceptions;


/**
 * Exception do tipo Sucesso
 */
public class BumerangueSuccessRuntimeException extends BumerangueRuntimeException {

	private static final long serialVersionUID = 1L;

	public static final String DESBLOQUEIO_REALIZADO = "Desbloqueio realizado.";
	public static final String SENHA_REINICIADA = "Senha reiniciada.";
	public static final String INSERCAO_REALIZADA = "Inserção realizada.";
	public static final String ALTERACAO_REALIZADA = "Alteração realizada.";
	public static final String EXCLUSAO_REALIZADA = "Exclusão realizada.";
	public static final String COPIA_REALIZADA = "Cópia realizada.";
	public static final String RESERVA_REALIZADA = "Reserva realizada.";
	public static final String EMPRESTIMO_REALIZADO = "Empréstimo realizado.";
	public static final String DEVOLUCAO_REALIZADA = "Devolução realizada.";
	public static final String CANCELAMENTO_REALIZADO = "Cancelamento realizado.";
	public static final String COMPARTILHAMENTO_REALIZADO = "Compartilhamento realizado.";
	public static final String APROVACAO_REALIZADA = "Aprovação realizada.";
	public static final String ENVIO_REALIZADO = "Envio realizado.";

	public BumerangueSuccessRuntimeException(String msg, Throwable ex) {
		super(msg, ex);
		// TODO Auto-generated constructor stub
	}

	public BumerangueSuccessRuntimeException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

}
