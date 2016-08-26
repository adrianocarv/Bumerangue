package br.org.ceu.bumerangue.entity.criterias;

import br.org.ceu.bumerangue.entity.Usuario;


public class PesquisaUsuarioCriteria extends PesquisaObjetoCriteria {
 
	private Class<Usuario> classeUsuario;
	private String nomeModulo;
	private String nome;
	private String nomeCompleto;
	private String email;
	private Boolean ativo;
	private Boolean bloqueado;
	private String nomeModuloSelecionado;
	private String nomePermissao;
	private String idTipoPermissao;

	public boolean isEmpty(){
		return false;
	}

	public String getNomePermissao() {
		return nomePermissao;
	}

	public void setNomePermissao(String nomePermissao) {
		this.nomePermissao = nomePermissao;
	}

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

	public Class<Usuario> getClasseUsuario() {
		return classeUsuario;
	}

	public void setClasseUsuario(Class<Usuario> classeUsuario) {
		this.classeUsuario = classeUsuario;
	}

	public String getIdTipoPermissao() {
		return idTipoPermissao;
	}

	public void setIdTipoPermissao(String idTipoPermissao) {
		this.idTipoPermissao = idTipoPermissao;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNomeModulo() {
		return nomeModulo;
	}

	public void setNomeModulo(String nomeModulo) {
		this.nomeModulo = nomeModulo;
	}

	public String getNomeModuloSelecionado() {
		return nomeModuloSelecionado;
	}

	public void setNomeModuloSelecionado(String nomeModuloSelecionado) {
		this.nomeModuloSelecionado = nomeModuloSelecionado;
	}
}