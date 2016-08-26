package br.org.ceu.bumerangue.entity.criterias;

public class PesquisaHistoricoLivroCulturalCriteria extends PesquisaObjetoCriteria {
	private String codigo;
	private String autor;
	private String dataEmprestimoIni;
	private String dataEmprestimoFim;
	private String observacoesEmprestimo;
	private String dataDevolucaoIni;
	private String dataDevolucaoFim;
	private String observacoesDevolucao;
	private String situacao;
	private String usuarioEmprestimo;
	
	public boolean isEmpty(){
		return false;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getDataEmprestimoIni() {
		return dataEmprestimoIni;
	}

	public void setDataEmprestimoIni(String dataEmprestimoIni) {
		this.dataEmprestimoIni = dataEmprestimoIni;
	}

	public String getDataEmprestimoFim() {
		return dataEmprestimoFim;
	}

	public void setDataEmprestimoFim(String dataEmprestimoFim) {
		this.dataEmprestimoFim = dataEmprestimoFim;
	}

	public String getObservacoesEmprestimo() {
		return observacoesEmprestimo;
	}

	public void setObservacoesEmprestimo(String observacoesEmprestimo) {
		this.observacoesEmprestimo = observacoesEmprestimo;
	}

	public String getDataDevolucaoIni() {
		return dataDevolucaoIni;
	}

	public void setDataDevolucaoIni(String dataDevolucaoIni) {
		this.dataDevolucaoIni = dataDevolucaoIni;
	}

	public String getDataDevolucaoFim() {
		return dataDevolucaoFim;
	}

	public void setDataDevolucaoFim(String dataDevolucaoFim) {
		this.dataDevolucaoFim = dataDevolucaoFim;
	}

	public String getObservacoesDevolucao() {
		return observacoesDevolucao;
	}

	public void setObservacoesDevolucao(String observacoesDevolucao) {
		this.observacoesDevolucao = observacoesDevolucao;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getUsuarioEmprestimo() {
		return usuarioEmprestimo;
	}

	public void setUsuarioEmprestimo(String usuarioEmprestimo) {
		this.usuarioEmprestimo = usuarioEmprestimo;
	}
}
 
