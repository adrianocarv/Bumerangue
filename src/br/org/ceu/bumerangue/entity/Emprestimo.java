package br.org.ceu.bumerangue.entity;

import java.util.Date;

public class Emprestimo extends BaseEntity{
	private static final long serialVersionUID = 1L;
	

	//
	// Atributos persistentes
	//
	private ObjetoBumerangue objetoBumerangue;
	private Date dataReserva;
	private Usuario usuarioRealizouReserva;
	private String observacoesReserva;
	private Date dataEmprestimo;
	private Usuario usuarioEmprestimo;
	private Usuario usuarioRealizouEmprestimo;
	private String observacoesEmprestimo;
	private Date dataDevolucao;
	private Usuario usuarioRealizouDevolucao;
	private String observacoesDevolucao;
	private String tipoAtividadeVideo;
	private String publicoVideo;
	private Integer numeroAssistentesVideo;

	//
	// Atributos de auditoria
	//
	private Date dataCriacao;
	private Usuario usuarioCriacao;
	private String ipCriacao;
	private Date dataUltimaAlteracao;
	private Usuario usuarioUltimaAlteracao;
	private String ipUltimaAlteracao;

	//
	// Outros Atributos
	//
	
	//
	// Contrutores
	//
	public Emprestimo(){}
	public Emprestimo(String id){ this.id = id; }

	//
	// M�todos de neg�cio
	//
	public boolean getIsDevolucao(){
		return this.dataDevolucao != null;
	}
	public boolean getIsReserva(){
		return this.dataDevolucao == null && this.dataEmprestimo == null && this.dataReserva != null;
	}
	public boolean getIsEmprestimo(){
		return this.dataDevolucao == null && this.dataEmprestimo != null;
	}
	public String getSituacao(){
		if(this.dataDevolucao != null) return "Devolução";
		if(this.dataEmprestimo != null) return "Empréstimo";
		if(this.dataReserva != null)return "Reserva";
		
		return "Desconhecido";
	}

	//
	// M�todos acessores default
	//	
	public Date getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public Date getDataEmprestimo() {
		return dataEmprestimo;
	}

	public void setDataEmprestimo(Date dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}

	public Date getDataReserva() {
		return dataReserva;
	}

	public void setDataReserva(Date dataReserva) {
		this.dataReserva = dataReserva;
	}

	public Integer getNumeroAssistentesVideo() {
		return numeroAssistentesVideo;
	}

	public void setNumeroAssistentesVideo(Integer numeroAssistentesVideo) {
		this.numeroAssistentesVideo = numeroAssistentesVideo;
	}

	public ObjetoBumerangue getObjetoBumerangue() {
		return objetoBumerangue;
	}

	public void setObjetoBumerangue(ObjetoBumerangue objetoBumerangue) {
		this.objetoBumerangue = objetoBumerangue;
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

	public String getPublicoVideo() {
		return publicoVideo;
	}

	public void setPublicoVideo(String publicoVideo) {
		this.publicoVideo = publicoVideo;
	}

	public String getTipoAtividadeVideo() {
		return tipoAtividadeVideo;
	}

	public void setTipoAtividadeVideo(String tipoAtividadeVideo) {
		this.tipoAtividadeVideo = tipoAtividadeVideo;
	}

	public Usuario getUsuarioEmprestimo() {
		return usuarioEmprestimo;
	}

	public void setUsuarioEmprestimo(Usuario usuarioEmprestimo) {
		this.usuarioEmprestimo = usuarioEmprestimo;
	}

	public Usuario getUsuarioRealizouDevolucao() {
		return usuarioRealizouDevolucao;
	}

	public void setUsuarioRealizouDevolucao(Usuario usuarioRealizouDevolucao) {
		this.usuarioRealizouDevolucao = usuarioRealizouDevolucao;
	}

	public Usuario getUsuarioRealizouEmprestimo() {
		return usuarioRealizouEmprestimo;
	}

	public void setUsuarioRealizouEmprestimo(Usuario usuarioRealizouEmprestimo) {
		this.usuarioRealizouEmprestimo = usuarioRealizouEmprestimo;
	}

	public Usuario getUsuarioRealizouReserva() {
		return usuarioRealizouReserva;
	}

	public void setUsuarioRealizouReserva(Usuario usuarioRealizouReserva) {
		this.usuarioRealizouReserva = usuarioRealizouReserva;
	}
	public Date getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	public Date getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}
	public void setDataUltimaAlteracao(Date dataUltimaAlteracao) {
		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}
	public String getIpCriacao() {
		return ipCriacao;
	}
	public void setIpCriacao(String ipCriacao) {
		this.ipCriacao = ipCriacao;
	}
	public String getIpUltimaAlteracao() {
		return ipUltimaAlteracao;
	}
	public void setIpUltimaAlteracao(String ipUltimaAlteracao) {
		this.ipUltimaAlteracao = ipUltimaAlteracao;
	}
	public Usuario getUsuarioCriacao() {
		return usuarioCriacao;
	}
	public void setUsuarioCriacao(Usuario usuarioCriacao) {
		this.usuarioCriacao = usuarioCriacao;
	}
	public Usuario getUsuarioUltimaAlteracao() {
		return usuarioUltimaAlteracao;
	}
	public void setUsuarioUltimaAlteracao(Usuario usuarioUltimaAlteracao) {
		this.usuarioUltimaAlteracao = usuarioUltimaAlteracao;
	}
}
