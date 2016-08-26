package br.org.ceu.bumerangue.entity;


public class Introito extends ComponenteMissa {
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
	public Introito(){}
	public Introito(String id){ this.id = id; }

	//
	// Métodos de negócio
	//
	public String getNome(){
		return "Intróito";
	}

	//
	// Métodos acessores default
	//	
}