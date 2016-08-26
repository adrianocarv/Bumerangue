package br.org.ceu.bumerangue.dao;

import java.io.Serializable;
import java.util.List;

import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.entity.criterias.PesquisaUsuarioCriteria;

public interface UsuarioDao extends BaseDao {
 
	public Usuario get(Serializable id);
	public Usuario getPorNome(String nome);
	public List<Usuario> findUsuarios(PesquisaUsuarioCriteria helper);

}
 
