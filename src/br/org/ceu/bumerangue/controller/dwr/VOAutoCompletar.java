package br.org.ceu.bumerangue.controller.dwr;

public class VOAutoCompletar {

	private String valor;
	private String descricao;

	public VOAutoCompletar(){}
	public VOAutoCompletar(String valor, String descricao){
		this.valor = valor;
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
}
