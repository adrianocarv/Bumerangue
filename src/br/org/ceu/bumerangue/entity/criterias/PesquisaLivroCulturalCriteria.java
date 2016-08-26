package br.org.ceu.bumerangue.entity.criterias;

import br.org.ceu.bumerangue.util.ValidationRules;

public class PesquisaLivroCulturalCriteria extends PesquisaObjetoCriteria {
 
	private String codigo;
	private String titulo;
	private String autor;
	private String idioma;
	private String palavrasChaves;
	private String observacoesGerais;
	private String situacao;
	private String usuarioEmprestimo;

	public boolean isEmpty(){
		boolean isEmpty = 
		(
		!ValidationRules.isInformed(this.codigo) &&
		!ValidationRules.isInformed(this.titulo) &&
		!ValidationRules.isInformed(this.autor) &&
		!ValidationRules.isInformed(this.idioma) &&
		!ValidationRules.isInformed(this.palavrasChaves) &&
		!ValidationRules.isInformed(this.observacoesGerais) &&
		!ValidationRules.isInformed(this.situacao) &&
		!ValidationRules.isInformed(this.usuarioEmprestimo));
		return isEmpty;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getUsuarioEmprestimo() {
		return usuarioEmprestimo;
	}

	public void setUsuarioEmprestimo(String usuarioEmprestimo) {
		this.usuarioEmprestimo = usuarioEmprestimo;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * 1 - Disponível<br>
 	 * 3 - Emprestado<br>
	 * 0 - Todos
	 * @param situacao
	 */
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
}
