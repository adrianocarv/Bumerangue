package br.org.ceu.bumerangue.dao.hibernate;

import java.io.Serializable;

import br.org.ceu.bumerangue.dao.ReferenciaMissalDao;
import br.org.ceu.bumerangue.entity.ReferenciaMissal;

public class ReferenciaMissalDaoHibernate extends BaseDaoHibernate implements ReferenciaMissalDao {
 
	public ReferenciaMissal get(Serializable id) {
    	return (ReferenciaMissal)super.get(ReferenciaMissal.class, id);
    }

}
 
