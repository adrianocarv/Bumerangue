package br.org.ceu.bumerangue.entity.criterias;

import br.org.ceu.bumerangue.util.ValidationRules;

public class PesquisaFilmeComercialCriteria extends PesquisaObjetoCriteria {
 
	private String titulo;
	private String publico;
	private String duracaoMinutosIni;
	private String duracaoMinutosFim;
	private String anoIni;
	private String anoFim;
	private String categoria;
	private String diretor;
	private String atoresObservacoes;
	private String numeroMaisRecentes;
	private Boolean disponivelDownload;

	public boolean isEmpty(){
		boolean isEmpty = 
		(
		!ValidationRules.isInformed(this.titulo) &&
		!ValidationRules.isInformed(this.publico) &&
		!ValidationRules.isInformed(this.duracaoMinutosIni) &&
		!ValidationRules.isInformed(this.duracaoMinutosFim) &&
		!ValidationRules.isInformed(this.anoIni) &&
		!ValidationRules.isInformed(this.anoFim) &&
		!ValidationRules.isInformed(this.categoria) &&
		!ValidationRules.isInformed(this.diretor) &&
		!ValidationRules.isInformed(this.atoresObservacoes) &&
		!ValidationRules.isInformed(this.numeroMaisRecentes)) &&
		!(this.disponivelDownload != null && this.disponivelDownload.booleanValue());
		return isEmpty;
	}

	public String getAnoFim() {
		return anoFim;
	}

	public void setAnoFim(String anoFim) {
		this.anoFim = anoFim;
	}

	public String getAnoIni() {
		return anoIni;
	}

	public void setAnoIni(String anoIni) {
		this.anoIni = anoIni;
	}

	public String getAtoresObservacoes() {
		return atoresObservacoes;
	}

	public void setAtoresObservacoes(String atoresObservacoes) {
		this.atoresObservacoes = atoresObservacoes;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getDiretor() {
		return diretor;
	}

	public void setDiretor(String diretor) {
		this.diretor = diretor;
	}

	public String getDuracaoMinutosFim() {
		return duracaoMinutosFim;
	}

	public void setDuracaoMinutosFim(String duracaoMinutosFim) {
		this.duracaoMinutosFim = duracaoMinutosFim;
	}

	public String getDuracaoMinutosIni() {
		return duracaoMinutosIni;
	}

	public void setDuracaoMinutosIni(String duracaoMinutosIni) {
		this.duracaoMinutosIni = duracaoMinutosIni;
	}

	public String getPublico() {
		return publico;
	}

	public void setPublico(String publico) {
		this.publico = publico;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getNumeroMaisRecentes() {
		return numeroMaisRecentes;
	}

	public void setNumeroMaisRecentes(String numeroMaisRecentes) {
		this.numeroMaisRecentes = numeroMaisRecentes;
	}

	public Boolean getDisponivelDownload() {
		return disponivelDownload;
	}

	public void setDisponivelDownload(Boolean disponivelDownload) {
		this.disponivelDownload = disponivelDownload;
	}
	
}
