package br.org.ceu.bumerangue.entity;

import java.util.Date;

import org.apache.commons.lang.StringUtils;


public class ReferenciaMissal extends BaseEntity {
	private static final long serialVersionUID = 1L;

	//
	// Atributos persistentes
	//
	private String texto;
	private String pagina;
	private ElementoDominio edicaoIdioma;
	private String enderecoEscrituras;
	private ComponenteMissa componenteMissa;
	
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
	
	//
	// Contrutores
	//
	public ReferenciaMissal(){}
	public ReferenciaMissal(String id){ this.id = id; }

	//
	// M�todos de neg�cio
	//
	public boolean isSalvo(){
		return !StringUtils.isBlank(this.getId());
	}
	public boolean isValido(){
		//campos requeridos
		if(this.getEdicaoIdioma() == null){
			this.setErrosValidacao("A Edi��o/Idioma deve ser informada.");
			return false;
		}
		if( StringUtils.isBlank(this.getPagina())){
			this.setErrosValidacao("A p�gina deve ser informada.");
			return false;
		}
		
		//Formata��o do atributo p�gina
		if(!this.getPagina().toLowerCase().trim().startsWith("n.")){
			//n�mero
			if(!StringUtils.isNumeric(this.getPagina())){
				this.setErrosValidacao("A p�gina deve 'n�mero' ou 'n. n�mero'.");
				return false;
			}
		}else{
			//n. n�mero
			String numero = this.getPagina().trim().substring(2).trim();
			if(!StringUtils.isNumeric(numero)){
				this.setErrosValidacao("A p�gina deve 'n�mero' ou 'n. n�mero'.");
				return false;
			}
		}
		
		//Implementar - Formata��o do endere�o nas escrituras
		
		return true;
	}

	//
	// M�todos acessores default
	//
	public String getErrosValidacao() {
		return errosValidacao;
	}
	public ElementoDominio getEdicaoIdioma() {
		return edicaoIdioma;
	}
	public void setEdicaoIdioma(ElementoDominio edicaoIdioma) {
		this.edicaoIdioma = edicaoIdioma;
	}
	public String getPagina() {
		return pagina;
	}
	public void setPagina(String pagina) {
		this.pagina = pagina;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
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
	public String getEnderecoEscrituras() {
		return enderecoEscrituras;
	}
	public void setEnderecoEscrituras(String enderecoEscrituras) {
		this.enderecoEscrituras = enderecoEscrituras;
	}
	public ComponenteMissa getComponenteMissa() {
		return componenteMissa;
	}
	public void setComponenteMissa(ComponenteMissa componenteMissa) {
		this.componenteMissa = componenteMissa;
	}
	public void setErrosValidacao(String errosValidacao) {
		this.errosValidacao = errosValidacao;
	}
	
}