package br.org.ceu.bumerangue.entity;


public class AclamacaoEvangelho extends ComponenteMissa {
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
	public AclamacaoEvangelho(){}
	public AclamacaoEvangelho(String id){ this.id = id; }

	//
	// M�todos de neg�cio
	//
	public String getNome(){
		return "Aclamação ao Evangelho";
	}

	//
	// M�todos acessores default
	//	
}