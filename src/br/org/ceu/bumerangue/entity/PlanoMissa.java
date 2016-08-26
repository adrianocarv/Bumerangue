package br.org.ceu.bumerangue.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;


public class PlanoMissa extends BaseEntity {
	private static final long serialVersionUID = 1L;

	//
	// Atributos persistentes
	//
	private String anoMes;
	private ElementoDominio situacao;
	private List<Missa> missas = new ArrayList<Missa>();
	
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
	private String errosValidacao;
	private List<ElementoDominio> edicoesIdiomasOrdenados = new ArrayList<ElementoDominio>();
	
	//
	// Contrutores
	//
	public PlanoMissa(){}
	public PlanoMissa(String id){ this.id = id; }

	//
	// Métodos de negócio
	//
	public boolean isValido(){
		for(Missa missa : this.getMissas()){
			if(!missa.isValida()){
				this.setErrosValidacao("A missa do dia "+missa.getDia()+": "+missa.getErrosValidacao());
				return false;
			}
		}
		return true;
	}

	public boolean isEmAprovacao(){
		return this.getSituacao() != null && this.getSituacao().getCodigo() == ElementoDominio.FICHA_MISSA_SITUACAO_PLANO_MISSA_EM_APROVACAO.getCodigo().intValue();
	}

	public boolean isAprovado(){
		return this.getSituacao() != null && this.getSituacao().getCodigo() == ElementoDominio.FICHA_MISSA_SITUACAO_PLANO_MISSA_APROVADO.getCodigo().intValue();
	}

	public String getAnoMesFormatado() {
		String anoMes =this.getAnoMes();
		if(StringUtils.isBlank(anoMes) || anoMes.length() != 6)
			return "";
		return anoMes.substring(0,4)+"/"+anoMes.substring(4);
	}

	public String getMesAnoFormatado() {
		String anoMes =this.getAnoMes();
		if(StringUtils.isBlank(anoMes) || anoMes.length() != 6)
			return "";
		return anoMes.substring(4)+"/"+anoMes.substring(0,4);
	}

	//
	// Métodos acessores default
	//
	public String getErrosValidacao() {
		return errosValidacao;
	}
	public void setErrosValidacao(String errosValidacao) {
		this.errosValidacao = errosValidacao;
	}
	public String getAnoMes() {
		return anoMes;
	}
	public void setAnoMes(String anoMes) {
		this.anoMes = anoMes;
	}
	public List<Missa> getMissas() {
		return missas;
	}
	public void setMissas(List<Missa> missas) {
		this.missas = missas;
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
	public ElementoDominio getSituacao() {
		return situacao;
	}
	public void setSituacao(ElementoDominio situacao) {
		this.situacao = situacao;
	}
	public List<ElementoDominio> getEdicoesIdiomasOrdenados() {
		return edicoesIdiomasOrdenados;
	}
	public void setEdicoesIdiomasOrdenados(
			List<ElementoDominio> edicoesIdiomasOrdenados) {
		this.edicoesIdiomasOrdenados = edicoesIdiomasOrdenados;
	}
	
}