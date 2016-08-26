package br.org.ceu.bumerangue.dao.hibernate;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import br.org.ceu.bumerangue.dao.PlanoMissaDao;
import br.org.ceu.bumerangue.entity.LiturgiaPalavra;
import br.org.ceu.bumerangue.entity.Missa;
import br.org.ceu.bumerangue.entity.PlanoMissa;
import br.org.ceu.bumerangue.util.ValidationRules;

public class PlanoMissaDaoHibernate extends BaseDaoHibernate implements PlanoMissaDao {
 
	public PlanoMissa get(Serializable id) {
    	return (PlanoMissa)super.get(PlanoMissa.class, id);
    }

	public PlanoMissa getPorAnoMes(String anoMes){
		Map<String,String> map = new HashMap<String,String>();
    	map.put("anoMes", anoMes);
    	List list = super.findByCriteria(DetachedCriteria.forClass(PlanoMissa.class).add(Restrictions.allEq(map)));
    	return (list.isEmpty() ? null : (PlanoMissa)list.get(0));    	
	}

	public Missa getMissa(Serializable id) {
    	return (Missa)super.get(Missa.class, id);
    }

	public List<PlanoMissa> findPlanosMissa(String anoMes, String idSituacao){
    	String hql = "select p from PlanoMissa as p where 1 = 1";
        if(ValidationRules.isInformed(anoMes)) hql += " and ( upper(p.anoMes) like '%"+anoMes+"%' )";
        if(ValidationRules.isInformed(idSituacao)) hql += " and p.situacao.id = '"+idSituacao+"'";

        hql += " order by p.anoMes desc";
        List<PlanoMissa> planosMissa = super.find(hql);

        return planosMissa;
	}
	
}
 
