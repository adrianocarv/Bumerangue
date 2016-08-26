package br.org.ceu.bumerangue.entity;


public class LivroCultural extends ObjetoBumerangue {
	private static final long serialVersionUID = 1L;

	//
	// Atributos persistentes
	//
	private String autor;
	private ElementoDominio idioma;
	private String palavrasChaves;
	private String observacoesGerais;
	
	//
	// Atributos de auditoria
	//

	//
	// Outros Atributos
	//
	
	//
	// Contrutores
	//
	public LivroCultural(){}
	public LivroCultural(String id){ this.id = id; }

	//
	// Métodos de negócio
	//

	//
	// Métodos acessores default
	//	
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public ElementoDominio getIdioma() {
		return idioma;
	}
	public void setIdioma(ElementoDominio idioma) {
		this.idioma = idioma;
	}
	public String getObservacoesGerais() {
		return observacoesGerais;
	}
	public void setObservacoesGerais(String observacoesGerais) {
		this.observacoesGerais = observacoesGerais;
	}
	public String getPalavrasChaves() {
		return palavrasChaves;
	}
	public void setPalavrasChaves(String palavrasChaves) {
		this.palavrasChaves = palavrasChaves;
	}
}
