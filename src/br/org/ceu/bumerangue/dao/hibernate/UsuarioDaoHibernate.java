package br.org.ceu.bumerangue.dao.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import br.org.ceu.bumerangue.dao.UsuarioDao;
import br.org.ceu.bumerangue.entity.ElementoDominio;
import br.org.ceu.bumerangue.entity.Permissao;
import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.entity.criterias.PesquisaUsuarioCriteria;
import br.org.ceu.bumerangue.service.exceptions.BumerangueErrorRuntimeException;
import br.org.ceu.bumerangue.util.ValidationRules;

public class UsuarioDaoHibernate extends BaseDaoHibernate implements UsuarioDao {
 
	public Usuario get(Serializable id) {
    	return (Usuario)super.get(Usuario.class, id);
    }

	public Usuario getPorNome(String nome){
		Map map = new HashMap();
    	map.put("nome", nome);
    	List list = super.findByCriteria(DetachedCriteria.forClass(Usuario.class).add(Restrictions.allEq(map)));
    	return (list.isEmpty() ? null : (Usuario)list.get(0));    	
	}

	public List<Usuario> findUsuarios(PesquisaUsuarioCriteria helper){
        String classe = helper.getClasseUsuario() != null ? helper.getClasseUsuario().getName() : "Usuario";
		if(ValidationRules.isInformed(helper.getNomeModulo())){
			//caso o módulo não tenha usuários especializados, retorna uma lista vazia
			if(Usuario.getInstance(helper.getNomeModulo()) == null) return new ArrayList<Usuario>();

			classe = Usuario.getInstance(helper.getNomeModulo()).getClass().getName();
		}
        
        String hql = "select u from "+classe+" as u where 1 = 1";
        ArrayList<Boolean> args = new ArrayList<Boolean>();
        if(ValidationRules.isInformed(helper.getNome())) hql += " and upper(u.nome) like '%"+helper.getNome().toUpperCase()+"%'";
        if(ValidationRules.isInformed(helper.getNomeCompleto())) hql += " and upper(u.nomeCompleto) like '%"+helper.getNomeCompleto().toUpperCase()+"%'";
        if(ValidationRules.isInformed(helper.getEmail())) hql += " and upper(u.email) like '%"+helper.getEmail().toUpperCase()+"%'";
        if(helper.getAtivo() != null){ hql += " and u.ativo = ?"; args.add(helper.getAtivo());}
        if(helper.getBloqueado() != null){ hql += " and u.bloqueado= ?"; args.add(helper.getBloqueado());}
        if(ValidationRules.isInformed(helper.getNomePermissao())) hql += " and u.permissoes.nome = '"+helper.getNomePermissao()+"'";
        if(ValidationRules.isInformed(helper.getIdTipoPermissao())){
        	Permissao permissao = null;
        	try{
        		ElementoDominio tipoPermissao = (ElementoDominio)super.get(ElementoDominio.class,helper.getIdTipoPermissao());
        		
        		Usuario usuario = null;
        		if(helper.getClasseUsuario() != null)
        			usuario = (Usuario)helper.getClasseUsuario().newInstance();
        		else if (helper.getNomeModulo() != null)
        			usuario = Usuario.getInstance(helper.getNomeModulo()).getClass().newInstance();
        		else
        			usuario = new Usuario();
        		
        		permissao = Permissao.getPermissao(tipoPermissao.getCodigo(),usuario);
        		if(permissao == null) permissao = new Permissao();
        	}catch (Exception e) {
				throw new BumerangueErrorRuntimeException(e.getMessage());
			}
        	hql += " and u.permissoes.nome = '"+permissao.getNome()+"'";
        }
        hql += " order by u.nomeCompleto";
      
        List<Usuario> usuarios = super.find(hql,args.toArray());
        return usuarios;
	}
}
 
