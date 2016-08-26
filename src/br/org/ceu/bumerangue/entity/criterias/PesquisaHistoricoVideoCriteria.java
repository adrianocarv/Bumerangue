package br.org.ceu.bumerangue.entity.criterias;

import br.org.ceu.bumerangue.util.Utils;
import br.org.ceu.bumerangue.util.ValidationRules;

public class PesquisaHistoricoVideoCriteria extends PesquisaObjetoCriteria {
 
	private String codigo;
	private String dataVideoIni;
	private String dataVideoFim;
	private String dataReservaIni;
	private String dataReservaFim;
	private String observacoesReserva;
	private String dataEmprestimoIni;
	private String dataEmprestimoFim;
	private String observacoesEmprestimo;
	private String dataDevolucaoIni;
	private String dataDevolucaoFim;
	private String observacoesDevolucao;
	private String situacao;
	private String usuarioEmprestimo;

	public boolean isEmpty(){
		boolean isEmpty = 
		(
		!ValidationRules.isInformed(this.codigo) &&
		!ValidationRules.isInformed(this.dataVideoIni) &&
		!ValidationRules.isInformed(this.dataVideoFim) &&
		!ValidationRules.isInformed(this.dataReservaIni) &&
		!ValidationRules.isInformed(this.dataReservaFim) &&
		!ValidationRules.isInformed(this.observacoesReserva) &&
		!ValidationRules.isInformed(this.dataEmprestimoIni) &&
		!ValidationRules.isInformed(this.dataEmprestimoFim) &&
		!ValidationRules.isInformed(this.observacoesEmprestimo) &&
		!ValidationRules.isInformed(this.dataDevolucaoIni) &&
		!ValidationRules.isInformed(this.dataDevolucaoFim) &&
		!ValidationRules.isInformed(this.observacoesDevolucao) &&
		!ValidationRules.isInformed(this.situacao) &&
		!ValidationRules.isInformed(this.usuarioEmprestimo));
		return isEmpty;
	}

	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = Utils.insertFragmment(codigo,"0",5-codigo.length(),true);
		if(this.codigo.equals("00000")) this.codigo = null;
	}
	public String getDataDevolucaoFim() {
		return dataDevolucaoFim;
	}
	public void setDataDevolucaoFim(String dataDevolucaoFim) {
		this.dataDevolucaoFim = dataDevolucaoFim;
	}
	public String getDataDevolucaoIni() {
		return dataDevolucaoIni;
	}
	public void setDataDevolucaoIni(String dataDevolucaoIni) {
		this.dataDevolucaoIni = dataDevolucaoIni;
	}
	public String getDataEmprestimoFim() {
		return dataEmprestimoFim;
	}
	public void setDataEmprestimoFim(String dataEmprestimoFim) {
		this.dataEmprestimoFim = dataEmprestimoFim;
	}
	public String getDataEmprestimoIni() {
		return dataEmprestimoIni;
	}
	public void setDataEmprestimoIni(String dataEmprestimoIni) {
		this.dataEmprestimoIni = dataEmprestimoIni;
	}
	public String getDataReservaFim() {
		return dataReservaFim;
	}
	public void setDataReservaFim(String dataReservaFim) {
		this.dataReservaFim = dataReservaFim;
	}
	public String getDataReservaIni() {
		return dataReservaIni;
	}
	public void setDataReservaIni(String dataReservaIni) {
		this.dataReservaIni = dataReservaIni;
	}
	public String getDataVideoFim() {
		return dataVideoFim;
	}
	public void setDataVideoFim(String dataVideoFim) {
		this.dataVideoFim = dataVideoFim;
	}
	public String getDataVideoIni() {
		return dataVideoIni;
	}
	public void setDataVideoIni(String dataVideoIni) {
		this.dataVideoIni = dataVideoIni;
	}
	public String getObservacoesDevolucao() {
		return observacoesDevolucao;
	}
	public void setObservacoesDevolucao(String observacoesDevolucao) {
		this.observacoesDevolucao = observacoesDevolucao;
	}
	public String getObservacoesEmprestimo() {
		return observacoesEmprestimo;
	}
	public void setObservacoesEmprestimo(String observacoesEmprestimo) {
		this.observacoesEmprestimo = observacoesEmprestimo;
	}
	public String getObservacoesReserva() {
		return observacoesReserva;
	}
	public void setObservacoesReserva(String observacoesReserva) {
		this.observacoesReserva = observacoesReserva;
	}
	public String getSituacao() {
		return situacao;
	}
	/**
	 * 1 - Devolução<br>
	 * 2 - Reserva<br>
	 * 3 - Empréstimo<br>
	 * 0 - Todos
	 * @param situacao
	 */
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
