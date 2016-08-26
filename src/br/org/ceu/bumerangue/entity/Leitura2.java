package br.org.ceu.bumerangue.entity;


public class Leitura2 extends ComponenteMissa {
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
	public Leitura2(){}
	public Leitura2(String id){ this.id = id; }

	//
	// Métodos de negócio
	//
	public String getNome(){
		return "Leitura";
	}

	//
	// Métodos acessores default
	//	
}