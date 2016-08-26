package br.org.ceu.bumerangue.dao;

import java.io.Serializable;

import br.org.ceu.bumerangue.entity.Permissao;

public interface PermissaoDao extends BaseDao {
 
	public Permissao get(Serializable id);
	public Permissao getPorNome(String nome);
	public Permissao getPorNumero(String numero);
	public void criarPermissaoUsuarioView();
}
 
