package br.org.ceu.bumerangue.entity;


public class SalmoResponsorial extends ComponenteMissa {
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
	public SalmoResponsorial(){}
	public SalmoResponsorial(String id){ this.id = id; }

	//
	// M�todos de neg�cio
	//
	public String getNome(){
		return "Salmo Responsorial";
	}

	//
	// M�todos acessores default
	//	
}