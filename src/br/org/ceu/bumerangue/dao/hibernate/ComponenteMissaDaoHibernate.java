package br.org.ceu.bumerangue.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import br.org.ceu.bumerangue.dao.ComponenteMissaDao;
import br.org.ceu.bumerangue.entity.ComponenteMissa;
import br.org.ceu.bumerangue.entity.Prefacio;

public class ComponenteMissaDaoHibernate extends BaseDaoHibernate implements ComponenteMissaDao {
 
	public ComponenteMissa get(Serializable id) {
    	return (ComponenteMissa)super.get(ComponenteMissa.class, id);
    }

	public List<Prefacio> findPrefaciosCompartilhados(){
    	String hql = "select o from Prefacio as o where o.compartilhado = ?";

        hql += " order by o.descricao";
        List<Prefacio> prefaciosCompartilhados = super.find(hql,Boolean.TRUE);

        return prefaciosCompartilhados;
	}
}
 
