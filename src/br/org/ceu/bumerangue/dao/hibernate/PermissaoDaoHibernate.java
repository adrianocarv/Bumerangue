package br.org.ceu.bumerangue.dao.hibernate;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.org.ceu.bumerangue.dao.PermissaoDao;
import br.org.ceu.bumerangue.entity.Permissao;

public class PermissaoDaoHibernate extends BaseDaoHibernate implements PermissaoDao {
 
	public Permissao get(Serializable id) {
    	return (Permissao)super.get(Permissao.class, id);
    }

	public Permissao getPorNome(String nome){
		String hql = "from Permissao where nome = '"+nome+"'";
    	List list = super.find(hql);
    	return (list.isEmpty() ? null : (Permissao)list.get(0));    	
	}

	public Permissao getPorNumero(String numero){
		String hql = "from Permissao where numero = '"+numero+"'";
    	List list = super.find(hql);
    	return (list.isEmpty() ? null : (Permissao)list.get(0));    	
	}

	public void criarPermissaoUsuarioView(){
		StringBuffer sql = new StringBuffer("");
		sql.append(" CREATE VIEW bmg_permissao_usuario_view AS ");
		sql.append(" select u.nome, p.nome as nome_permissao ");
		sql.append(" from BMG_PERMISSAO_USUARIO as pu, BMG_USUARIO as u, BMG_PERMISSAO as p ");
		sql.append(" where pu.id_usuario = u.id ");
		sql.append(" and pu.id_permissao = p.id ");
		Connection conn = super.getSession().connection();
		try{
			conn.createStatement().execute(sql.toString());
		}catch (Exception e) {
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
	}

}
 
