package br.org.ceu.bumerangue.entity.criterias;

import br.org.ceu.bumerangue.util.Utils;
import br.org.ceu.bumerangue.util.ValidationRules;

public class PesquisaVideoCriteria extends PesquisaObjetoCriteria {
 
	private String codigo;
	private String categoria;
	private String codigoCaixa;
	private String titulo;
	private String localidade;
	private String dataIni;
	private String dataFim;
	private Boolean legendado;
	private String midia;
	private String palavrasChaves;
	private String observacoesGerais;
	private String situacao;
	private String usuarioEmprestimo;
	private String localizacaoFisica;

	public boolean isEmpty(){
		boolean isEmpty = 
		(
		!ValidationRules.isInformed(this.codigo) &&
		!ValidationRules.isInformed(this.categoria) &&
		!ValidationRules.isInformed(this.codigoCaixa) &&
		!ValidationRules.isInformed(this.titulo) &&
		!ValidationRules.isInformed(this.localidade) &&
		!ValidationRules.isInformed(this.dataIni) &&
		!ValidationRules.isInformed(this.dataFim) &&
		!(this.legendado != null && this.legendado.booleanValue()) &&
		!ValidationRules.isInformed(this.midia) &&
		!ValidationRules.isInformed(this.palavrasChaves) &&
		!ValidationRules.isInformed(this.observacoesGerais) &&
		!ValidationRules.isInformed(this.situacao) &&
		!ValidationRules.isInformed(this.usuarioEmprestimo) &&
		!ValidationRules.isInformed(this.localizacaoFisica));
		return isEmpty;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = Utils.insertFragmment(codigo,"0",5-codigo.length(),true);
		if(this.codigo.equals("00000")) this.codigo = null;
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

	public String getMidia() {
		return midia;
	}

	public void setMidia(String midia) {
		this.midia = midia;
	}

	public String getPalavrasChaves() {
		return palavrasChaves;
	}

	public void setPalavrasChaves(String palavrasChaves) {
		this.palavrasChaves = palavrasChaves;
	}

	public String getObservacoesGerais() {
		return observacoesGerais;
	}

	public void setObservacoesGerais(String observacoesGerais) {
		this.observacoesGerais = observacoesGerais;
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

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getCodigoCaixa() {
		return codigoCaixa;
	}

	public void setCodigoCaixa(String codigoCaixa) {
		this.codigoCaixa = codigoCaixa;
	}

	public String getDataFim() {
		return dataFim;
	}

	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}

	public String getDataIni() {
		return dataIni;
	}

	public void setDataIni(String dataIni) {
		this.dataIni = dataIni;
	}
	
	public String getLocalizacaoFisica() {
		return localizacaoFisica;
	}

	public void setLocalizacaoFisica(String localizacaoFisica) {
		this.localizacaoFisica = localizacaoFisica;
	}

	/**
	 * 1 - Disponível<br>
	 * 2 - Reservado<br>
	 * 3 - Emprestado<br>
	 * 0 - Todos
	 * @param situacao
	 */
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
}
