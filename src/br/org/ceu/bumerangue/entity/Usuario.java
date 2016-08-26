package br.org.ceu.bumerangue.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import br.org.ceu.bumerangue.entity.suporte.deploy.Deploy;
import br.org.ceu.bumerangue.util.MD5;

public class Usuario extends BaseEntity {
	private static final long serialVersionUID = 1L;
	public static final String NOME_USUARIO_ADMIN = "admin";
	public static final String SENHA_PADRAO_USUARIO_ADMIN = "admin";

	//
	// Atributos persistentes
	//
	private String nome;
	private String nomeCompleto;
	private String senha;
	private String email;
	private String telefone;
	private Boolean ativo = Boolean.TRUE;
	private Boolean bloqueado = Boolean.FALSE;
	private Integer numeroTentativasErradas = new Integer(0);
	private List<Permissao> permissoes = new ArrayList<Permissao>();
	private List emprestimos;

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
	private Integer codigoTipoPermissao;
	private String nomeModulo;
	private String ip;
	private HttpSession httpSession;
	
	//
	// Contrutores
	//
	public Usuario(){}
	public Usuario(String id){ this.id = id; }

	//
	// M�todos de neg�cio
	//
	/**
	 * Verifica foi salvo, ou seja, o mesmo n�o deve possuir o nome chave.
	 * @author Adriano Carvalho
	 * @return
	 */
	public boolean isSalvo(){
		return !(this.getId() == null || this.getId().equals("") || this.getId().equals(new Usuario().getId()));
	}
	
	/**
	 * Retorna true, caso o usu�rio seja o ADMIN.
	 * @author Adriano Carvalho
	 * @return
	 */
	public boolean isAdmin(){
		return this.getNome().equals(NOME_USUARIO_ADMIN);
	}
	
	/**
	 * Retorna true, se o usu�rio tiver alguma role na seq��ncia de n�meros informada.<br>
	 * O padr�o para numerosRoles pode ser:<1>
	 * a. Uma seg��ncia de n�mero separados por ,(v�rgula).<br>
	 * b. Um intervalo entre 2 n�meros separado por - (h�fen).<br>
	 * A correspond�ncia de n�meros para role (permiss�o) � a seguinte:<br>
	 * 1  = ADMIN_VIDEO<br>
	 * 2  = AVANCADO_VIDEO<br>
	 * 3  = BASICO_VIDEO<br>
	 * 4  = ADMIN_LIVRO_CULTURAL<br>
	 * 5  = AVANCADO_LIVRO_CULTURAL<br>
	 * 6  = BASICO_LIVRO_CULTURAL<br>
	 * 7  = ADMIN_FILME_COMERCIAL<br>
	 * 9  = ADMIN_FILME_COMERCIAL<br>
	 * 10 = BASICO_FICHA_MISSA<br>
	 * 11 = AVANCADO_FICHA_MISSA<br>
	 * 12 = BASICO_FICHA_MISSA
	 * @param numerosRoles
	 * @return
	 */
	public boolean isInRole(String numerosRoles){
		String[] arrayNumerosRoles = this.getArrayNumerosRoles(numerosRoles);
		for (int i = 0; i < arrayNumerosRoles.length; i++) {
			String numeroRole = arrayNumerosRoles[i];
			for (int j = 0; j < this.getPermissoes().size(); j++) {
				Permissao permissao = (Permissao)this.getPermissoes().get(j);
				if(permissao.getNumero().equals(numeroRole)){

					//verifica a disponibilidade em rela��o ao M�dulo
					if(!Deploy.isModuloDisponivel(permissao))
						continue;
					
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Retorna true, se o usu�rio tiver a permissao informada.
	 * @param permissao
	 * @return
	 */
	public boolean isInRole(Permissao permissao){
		for (Permissao permissao2 : this.getPermissoes()){
			if(permissao2.getNome().equals(permissao.getNome())){
				
				//verifica a disponibilidade em rela��o ao M�dulo
				if(!Deploy.isModuloDisponivel(permissao2)) return false;
				
				return true;
			}
		}
		return false;
	}

	/**
	 * Retorna true,  se o usu�rio em rela��o ao tipo tiver o tipo de permiss�o informado.
	 * @author Adriano Carvalho
	 * @param tipoPermissao
	 * @return
	 */
	public boolean isInRole(ElementoDominio tipoPermissao){
		return this.getTipoPermissaoTipoUsuario().getCodigo().intValue() == tipoPermissao.getCodigo().intValue();
	}

	/**
	 * Retorna true, se o usu�rio tiver o tipo de permiss�o no m�dulo passados como par�metro.
	 * @param codigoTipoPermissao
	 * @param nomeModulo
	 * @return
	 */
	public boolean isInRole(Integer codigoTipoPermissao, String nomeModulo){
		for(String nomeModuloUsuario : this.getNomesModulos(codigoTipoPermissao)){
			if(nomeModuloUsuario.equals(nomeModulo)){
				
				//verifica a disponibilidade em rela��o ao M�dulo
				if(!Deploy.isModuloDisponivel(nomeModulo)) return false;

				return true;
			}
		}
		return false;
	}

	/**
	 * Retorna o ElementoDom�nio correspondente ao tipo de permiss�o em relac�o ao tipo deste usu�rio,<br>
	 * sendo este uma subclasse de Usuario, contendo uma permiss�o b�sica, avan�ada ou admin.<br>
	 * Caso n�o encontre a permiss�o, retorna uma inst�ncia vazia de ElementoDominio.
	 * @return
	 */
	public ElementoDominio getTipoPermissaoTipoUsuario(){
		for(Permissao permissao : Permissao.getPermissoesPorUsuario(this)){
			if(this.isInRole(permissao)){
				if(permissao.isAdmin()) return ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_ADMIN;
				else if(permissao.isAvancado()) return ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_AVANCADO;
				else if(permissao.isBasico()) return ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_BASICO;
			}
		}
		return new ElementoDominio();
	}

	/**
	 * Retorna o ElementoDom�nio correspondente ao tipo de permiss�o que o usu�rio tem no 'nomeModulo' informado,<br>
	 * Caso n�o encontre a permiss�o, retorna uma inst�ncia vazia de ElementoDominio.
	 * @param nomeModulo
	 * @return
	 */
	public ElementoDominio getTipoPermissaoModulo(String nomeModulo){
		for(Permissao permissao : Permissao.getPermissoesPorModulo(nomeModulo)){
			if(this.isInRole(permissao)){
				if(permissao.isAdmin()) return ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_ADMIN;
				else if(permissao.isAvancado()) return ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_AVANCADO;
				else if(permissao.isBasico()) return ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_BASICO;
			}
		}
		return new ElementoDominio();
	}

	/**
	 * Retorna o ObjetoBumerangue especializado correspondente ao tipo deste usu�rio,<br>
	 * retorna null, caso a correpond�ncia n�o se aplicar.
	 * @author Adriano Carvalho
	 * @return
	 */
	public ObjetoBumerangue getObjetoBumerangueCorrepospondente(){
		if (this instanceof Centro) return new Video();
		if (this instanceof UsuarioBibliotecaCultural) return new LivroCultural();
		return null;
	}

	/**
	 * Retorna um array de String com os n�meros das roles, de acordo com o papr�metro numerosRoles.
	 * @param numerosRoles
	 * @return
	 */
	private String[] getArrayNumerosRoles(String numerosRoles){
		if(numerosRoles.indexOf('-') != -1){
			String strIni = numerosRoles.substring(0,numerosRoles.indexOf('-'));
			String strFim = numerosRoles.substring(numerosRoles.indexOf('-')+1);
			int ini = Integer.parseInt(strIni);
			int fim = Integer.parseInt(strFim);
			String[] intervalo = new String[fim-ini+1];
			for (int i = ini, j = 0; i <= fim ; i++, j++) { intervalo[j] = i+""; }
			return intervalo;
		}
		return numerosRoles.split(",");
	}

	/**
	 * Retorna a lista de m�dulos, os quais o usu�rio participa com o o 'tipoPermissao'.
	 * @param codigoTipoPermissao
	 * @return
	 */
	public List<String> getNomesModulos(Integer codigoTipoPermissao){
		List<String> nomesModulos = new ArrayList<String>();
		for(Permissao permissao : this.getPermissoes()){
			
			String nomeModulo = permissao.getNomeModulo();
			if(nomeModulo == null)
				continue;
			
			if(permissao.isAdmin() && ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_ADMIN.getCodigo().intValue() == codigoTipoPermissao.intValue())
				nomesModulos.add(permissao.getNomeModulo());
			else if (permissao.isAvancado() && ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_AVANCADO.getCodigo().intValue() == codigoTipoPermissao.intValue())
				nomesModulos.add(permissao.getNomeModulo());
			else if (permissao.isBasico() && ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_BASICO.getCodigo().intValue() == codigoTipoPermissao.intValue())
				nomesModulos.add(permissao.getNomeModulo());
		}
		return nomesModulos;
	}
	
	/**
	 * Retorna a lista de m�dulos, os quais o usu�rio participa como ADMIN.<br>
	 * Caso o usu�rio seja o ADMIN e o m�dulo BUMERANGUE esteja diopon�vel, adiciona na lista o m�dulo BUMERANGUE.
	 * @param codigoTipoPermissao
	 * @return
	 */
	public List<String> getNomesModulosAdmin(){
		List<String> nomesModulosAdmin = this.getNomesModulos(ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_ADMIN.getCodigo());
		if(isAdmin() && Deploy.isModuloDisponivel(Deploy.MODULO_BUMERANGUE))
			nomesModulosAdmin.add(Deploy.MODULO_BUMERANGUE);
		return nomesModulosAdmin;
	}
	
	public void addPermissao(Permissao permissao){
		if(permissoes == null) permissoes = new ArrayList<Permissao>();
		permissoes.add(permissao);
	}

	public void removePermissao(Permissao permissao){
		for(Permissao permissaoUsuario : this.getPermissoes()){
			if(permissao.getNumero().equals(permissaoUsuario.getNumero())){
				permissoes.remove(permissaoUsuario);
				break;
			}
		}
	}

	public String getNomeFormatado(){
		return this.getNomeCompleto() +" ("+this.getNome()+")";
	}

	public void setSenhaCript(String senhaNaoCriptografada) {
		this.senha = MD5.crypt(senhaNaoCriptografada);
	}

	public List<String> getEmails(){
		String email = StringUtils.replace(this.getEmail(),";",",");
		String[] mails = StringUtils.split(email,',');
		List<String> emails = new ArrayList<String>();
		for (String emailAux : mails) {
			emails.add(emailAux.trim());
		}
		return emails;
	}

	/**
	 * Retorna uma inst�ncia de Usu�rio especializada, de acordo com o nome do m�dulo.<br>
	 * Restorna null, caso n�o haja correspond�ncia do nome do m�dulo com algum Usu�rio especializado (subclasse).
	 * @param nomeModulo
	 * @return
	 */
	public static Usuario getInstance(String nomeModulo){
		//TODO � POSSIVEL QUES ESTE M�TODO FIQUE DINAMICO
		if (Deploy.MODULO_VIDEO.equals(nomeModulo)) return new Centro();
		if (Deploy.MODULO_LIVRO_CULTURAL.equals(nomeModulo)) return new UsuarioBibliotecaCultural();
		if (Deploy.MODULO_FILME_COMERCIAL.equals(nomeModulo)) return new UsuarioFilmeComercial();
		if (Deploy.MODULO_FICHA_MISSA.equals(nomeModulo)) return new UsuarioFichaMissa();
		return null;
	}

	//
	// M�todos acessores default
	//	
	public Boolean getAtivo() {
		return ativo;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	public Boolean getBloqueado() {
		return bloqueado;
	}
	public void setBloqueado(Boolean bloqueado) {
		this.bloqueado = bloqueado;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List getEmprestimos() {
		return emprestimos;
	}
	public void setEmprestimos(List emprestimos) {
		this.emprestimos = emprestimos;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNomeCompleto() {
		return nomeCompleto;
	}
	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
	public List<Permissao> getPermissoes() {
		return permissoes;
	}
	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
			this.senha = senha;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public Integer getNumeroTentativasErradas() {
		return numeroTentativasErradas;
	}
	public void setNumeroTentativasErradas(Integer numeroTentativasErradas) {
		this.numeroTentativasErradas = numeroTentativasErradas;
	}
	public Integer getCodigoTipoPermissao() {
		return codigoTipoPermissao;
	}
	public void setCodigoTipoPermissao(Integer codigoTipoPermissao) {
		this.codigoTipoPermissao = codigoTipoPermissao;
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
	public HttpSession getHttpSession() {
		return httpSession;
	}
	public void setHttpSession(HttpSession httpSession) {
		this.httpSession = httpSession;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getNomeModulo() {
		return nomeModulo;
	}
	public void setNomeModulo(String nomeModulo) {
		this.nomeModulo = nomeModulo;
	}
}
