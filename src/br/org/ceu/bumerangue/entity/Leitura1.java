package br.org.ceu.bumerangue.entity;


public class Leitura1 extends ComponenteMissa {
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
	public Leitura1(){}
	public Leitura1(String id){ this.id = id; }

	//
	// M�todos de neg�cio
	//
	public String getNome(){
		return "Leitura";
	}

	//
	// M�todos acessores default
	//	
}