package br.org.ceu.bumerangue.entity;


public class AntifonaComunhao extends ComponenteMissa {
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
	public AntifonaComunhao(){}
	public AntifonaComunhao(String id){ this.id = id; }

	//
	// M�todos de neg�cio
	//
	public String getNome(){
		return "Antífona de Comunhão";
	}

	//
	// M�todos acessores default
	//	
}