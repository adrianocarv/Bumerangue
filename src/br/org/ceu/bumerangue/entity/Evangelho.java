package br.org.ceu.bumerangue.entity;


public class Evangelho extends ComponenteMissa {
	private static final long serialVersionUID = 1L;

	//
	// Atributos persistentes
	//

	//
	// Atributos de auditoria
	//

	//
	// Outros Atributos
	//
	
	//
	// Contrutores
	//
	public Evangelho(){}
	public Evangelho(String id){ this.id = id; }

	//
	// M�todos de neg�cio
	//
	public String getNome(){
		return "Evangelho";
	}

	//
	// M�todos acessores default
	//	
}