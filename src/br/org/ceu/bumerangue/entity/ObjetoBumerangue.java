package br.org.ceu.bumerangue.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import br.org.ceu.bumerangue.util.Utils;

public class ObjetoBumerangue extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	//
	// Atributos persistentes
	//
	private String codigo;
	private String titulo;
	private Boolean foraUso = Boolean.FALSE;
	private String localizacaoFisica;
	private String localizacaoFisicaAnterior;
	private List emprestimos = new ArrayList();
	private Emprestimo emprestimoAtual;

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
	private boolean exibeImagem = false;
	
	//
	// Contrutores
	//
	public ObjetoBumerangue(){}
	public ObjetoBumerangue(String id){ this.id = id; }

	//
	// Métodos de negócio
	//
	/**
	 * Verifica foi salvo, ou seja, o mesmo n�o deve possuir o nome chave.
	 * @author Adriano Carvalho
	 * @return
	 */
	public boolean isSalvo(){
		return !(this.getId() == null || this.getId().equals("") || this.getId().equals(new ObjetoBumerangue().getId()));
	}
	
	public void addEmprestimo(Emprestimo emprestimo){
		if(emprestimos == null) emprestimos = new ArrayList();
		emprestimos.add(emprestimo);
	}

	public void removeEmprestimo(Emprestimo emprestimo){
		if(emprestimos != null) emprestimos.remove(emprestimo);
	}

	public boolean getIsDisponivel(){
		return !this.getIsReservado() && !this.getIsEmprestado() && !this.getForaUso().booleanValue();
	}

	public boolean getIsReservado(){
		if(this.emprestimoAtual == null) return false;
		return this.emprestimoAtual.getDataEmprestimo() ==  null && this.emprestimoAtual.getDataReserva() != null;
	}

	public boolean getIsEmprestado(){
		if(this.emprestimoAtual == null) return false;
		return this.emprestimoAtual.getDataEmprestimo() !=  null;
	}

	public String getSituacao(){
		try{
		if(this.getIsReservado()) return "Reservado";
		if(this.getIsEmprestado()) return "Emprestado";
		if(this.getForaUso().booleanValue()) return "Fora de uso";
		}catch (Exception e) {
			System.out.println("\n\nProblema obj id: " + this.getId() + " - " + this.getTitulo() + "\n\n");
		}
		return "Disponível";
	}
	
	public Long getFragmentoSequencialCodigo(String intervalos){
		try{
			boolean temFim = intervalos.indexOf(',') != -1;
			int ini = temFim ? Integer.valueOf(intervalos.substring(0,intervalos.indexOf(','))) : Integer.valueOf(intervalos);
			int fim = temFim ? Integer.valueOf(intervalos.substring(intervalos.indexOf(',')+1)) : -1;
			
			String fragmento = temFim ? this.getCodigo().substring(ini,fim+1) : this.getCodigo().substring(ini);
			return Long.valueOf(fragmento);
			
		}catch (Exception e) {
			return null;
		}
	}
	/**
	 * Retorna true, se este objeto participa do 'Grupo de compartimentos' da Localiza��o F�sica correpondente ao<br>
	 * 'compartimento' passado como par�metro.<br>
	 * Para isso � levado em considera��o das regras e padr�o do campo 'grupo de compartimentos'.
	 * @param compartimento
	 * @return
	 */
	public boolean isParticipanteGrupoCompartimento(ElementoDominio compartimento){
		if(compartimento == null || compartimento.getDominio() == null) return false;

		Dominio localizacaoFisica = compartimento.getDominio();
		String grupoCompartimentos = localizacaoFisica.getPersonalizado3();
		String totalPrefixo = grupoCompartimentos.split("-")[0].trim();
		String atributo = grupoCompartimentos.split("-")[1].trim();
		
		String valorAtributo = Utils.getAtributo(this,atributo)+"";
		
		if(StringUtils.isBlank(valorAtributo))
			return false;
		
		boolean ok = false;
		if("*".equals(totalPrefixo)) totalPrefixo = valorAtributo.length()+"";
		int largura = Integer.parseInt(totalPrefixo);
		
		if(largura > valorAtributo.length() || largura > compartimento.getDescricao().length())
			return false;
		
		String prefixoValorAtributo = valorAtributo.substring(0,largura);
		String prefixoDescricaoCompartimento = compartimento.getDescricao().substring(0,largura);
		ok = prefixoDescricaoCompartimento.equalsIgnoreCase(prefixoValorAtributo);
		
		return ok;
	}

	public String getImagem(){
		String url = this.getURLDropbox() + this.getCodigo() + ".jpg";
		return url; 
		//return Utils.isValidDropboxURL(url) ? url : this.getURLDropbox() + "/nf.png";
	}

	private String getURLDropbox(){
		String url = "http://dl.dropbox.com/u/" + Utils.getResourceMessage("bmg.dropbox.uid") + "/" + Utils.getResourceMessage("bmg.dropbox.instal") + "/imagem_objeto/";
		return url;
	}

	//
	// M�todos acessores default
	//	
	public Emprestimo getEmprestimoAtual() {
		return emprestimoAtual;
	}

	public void setEmprestimoAtual(Emprestimo emprestimoAtual) {
		this.emprestimoAtual = emprestimoAtual;
	}

	public List getEmprestimos() {
		return emprestimos;
	}

	public void setEmprestimos(List emprestimos) {
		this.emprestimos = emprestimos;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Boolean getForaUso() {
		return foraUso;
	}

	public void setForaUso(Boolean foraUso) {
		this.foraUso = foraUso;
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
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getLocalizacaoFisica() {
		return localizacaoFisica;
	}
	public void setLocalizacaoFisica(String localizacaoFisica) {
		this.localizacaoFisica = localizacaoFisica;
	}
	public String getLocalizacaoFisicaAnterior() {
		return localizacaoFisicaAnterior;
	}
	public void setLocalizacaoFisicaAnterior(String localizacaoFisicaAnterior) {
		this.localizacaoFisicaAnterior = localizacaoFisicaAnterior;
	}
	public boolean isExibeImagem() {
		return exibeImagem;
	}
	public void setExibeImagem(boolean exibeImagem) {
		this.exibeImagem = exibeImagem;
	}
}
 
