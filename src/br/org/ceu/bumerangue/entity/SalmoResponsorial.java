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
	// Métodos de negócio
	//
	public String getNome(){
		return "Salmo Responsorial";
	}

	//
	// Métodos acessores default
	//	
}