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
	// M�todos de neg�cio
	//
	public String getNome(){
		return "Intr�ito";
	}

	//
	// M�todos acessores default
	//	
}