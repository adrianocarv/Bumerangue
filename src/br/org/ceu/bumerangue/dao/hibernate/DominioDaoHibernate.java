package br.org.ceu.bumerangue.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import br.org.ceu.bumerangue.dao.DominioDao;
import br.org.ceu.bumerangue.entity.Dominio;
import br.org.ceu.bumerangue.entity.ElementoDominio;

public class DominioDaoHibernate extends BaseDaoHibernate implements DominioDao {

	public Dominio get(Serializable id) {
    	return (Dominio)super.get(Dominio.class, id);
    }
 
	public Dominio get(Integer codigo){
		String hql = "from Dominio where codigo = ?";
		List list = super.find(hql,new Object[]{codigo});
		return list.isEmpty() ? null : (Dominio)list.get(0);
	}

	public ElementoDominio getElementoDominio(Integer codigoDominio, Integer codigoElementoDominio){
		String hql = "from ElementoDominio as ed where ed.dominio.codigo = ? and ed.codigo = ?";
		List list = super.find(hql,new Object[]{codigoDominio, codigoElementoDominio});
		return list.isEmpty() ? null : (ElementoDominio)list.get(0);
	}

}
 
