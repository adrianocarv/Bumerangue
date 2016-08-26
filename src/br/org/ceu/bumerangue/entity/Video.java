package br.org.ceu.bumerangue.entity;

import java.util.Date;

import br.org.ceu.bumerangue.util.Utils;

public class Video extends ObjetoBumerangue {
	private static final long serialVersionUID = 1L;


	//
	// Atributos persistentes
	//
	private ElementoDominio categoria;
	private String codigoCaixa;
	private String localidade;
	private Date data;
	private Boolean legendado = Boolean.FALSE;
	private Boolean dublado = Boolean.FALSE;
	private String publico;
	private Integer duracaoMinutos;
	private ElementoDominio midia;
	private String palavrasChaves;
	private ElementoDominio observacoes;
	private String observacoesGerais;
	private String localizacaoPI;

	//
	// Atributos de auditoria
	//

	//
	// Outros Atributos
	//
	
	//
	// Contrutores
	//
	public Video(){}
	public Video(String id){ this.id = id; }

	//
	// Métodos de negócio
	//
	/**
	 * Seta o valor do código no padrão: 99999
	 * @param codigo
	 */
	public void setCodigoFormatado(String codigo) {
		super.setCodigo(Utils.insertFragmment(codigo,"0",5-codigo.length(),true));
	}

	//
	// Métodos acessores default
	//	
	public ElementoDominio getCategoria() {
		return categoria;
	}

	public void setCategoria(ElementoDominio categoria) {
		this.categoria = categoria;
	}

	public String getCodigoCaixa() {
		return codigoCaixa;
	}

	public void setCodigoCaixa(String codigoCaixa) {
		this.codigoCaixa = codigoCaixa;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Boolean getDublado() {
		return dublado;
	}

	public void setDublado(Boolean dublado) {
		this.dublado = dublado;
	}

	public Integer getDuracaoMinutos() {
		return duracaoMinutos;
	}

	public void setDuracaoMinutos(Integer duracaoMinutos) {
		this.duracaoMinutos = duracaoMinutos;
	}

	public Boolean getLegendado() {
		return legendado;
	}

	public void setLegendado(Boolean legendado) {
		this.legendado = legendado;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getLocalizacaoPI() {
		return localizacaoPI;
	}

	public void setLocalizacaoPI(String localizacaoPI) {
		this.localizacaoPI = localizacaoPI;
	}

	public ElementoDominio getMidia() {
		return midia;
	}

	public void setMidia(ElementoDominio midia) {
		this.midia = midia;
	}

	public ElementoDominio getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(ElementoDominio observacoes) {
		this.observacoes = observacoes;
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

	public String getPublico() {
		return publico;
	}

	public void setPublico(String publico) {
		this.publico = publico;
	}
}
