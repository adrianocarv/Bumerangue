package br.org.ceu.bumerangue.entity;


public class Prefacio extends ComponenteMissa {
	private static final long serialVersionUID = 1L;

	//
	// Atributos persistentes
	//
	private Boolean proprio = Boolean.TRUE;
	private Boolean compartilhado = Boolean.FALSE;
	private String descricao;
	private Prefacio prefacioCompartilhado;

	//
	// Atributos de auditoria
	//

	//
	// Outros Atributos
	//
	
	//
	// Contrutores
	//
	public Prefacio(){}
	public Prefacio(String id){ this.id = id; }

	//
	// Métodos de negócio
	//
	public String getNome(){
		return "Prefácio";
	}

	//
	// Métodos acessores default
	//	
	public Boolean getCompartilhado() {
		return compartilhado;
	}
	public void setCompartilhado(Boolean compartilhado) {
		this.compartilhado = compartilhado;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Prefacio getPrefacioCompartilhado() {
		return prefacioCompartilhado;
	}
	public void setPrefacioCompartilhado(Prefacio prefacioCompartilhado) {
		this.prefacioCompartilhado = prefacioCompartilhado;
	}
	public Boolean getProprio() {
		return proprio;
	}
	public void setProprio(Boolean proprio) {
		this.proprio = proprio;
	}
}