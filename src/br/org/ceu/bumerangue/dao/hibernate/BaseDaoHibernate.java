package br.org.ceu.bumerangue.dao.hibernate;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import br.org.ceu.bumerangue.dao.BaseDao;



/**
 * Classe base de todas as implementações de DAO usando Hibernate. 
 * 
 */
public class BaseDaoHibernate extends HibernateDaoSupport implements BaseDao{
	
	// -- Commons Logging -----------------------------------------------------
	public transient final Log log = LogFactory.getLog(getClass());
	// ------------------------------------------------------------------------

    public void update(Object object) {
        getHibernateTemplate().update(object);
        getHibernateTemplate().flush();
    }

    public void saveOrUpdate(Object object) {
        getHibernateTemplate().saveOrUpdate(object);
        getHibernateTemplate().flush();
    }
    
    public void saveOrUpdateAll(Collection collection) {
    	getHibernateTemplate().saveOrUpdateAll(collection);
    	getHibernateTemplate().flush();
    }

    public void merge(Object object) {
    	getHibernateTemplate().merge(object);
    	getHibernateTemplate().flush();
    }

    public void delete(Object object) {
        getHibernateTemplate().delete(object);        
        getHibernateTemplate().flush();
    }

    public void delete(Class clazz, Serializable id) {
        getHibernateTemplate().delete(get(clazz, id));        
        getHibernateTemplate().flush();
    }
	
    public Object get(Class clazz, Serializable id) {
    	if(id == null) return null;
    	Object object = getHibernateTemplate().get(clazz, id);
        return object;
    }

    public List find(String hql) {
        List list = getHibernateTemplate().find(hql);
        return list;
    }
    
    public List find(String hql, Object value) {
    	List list = getHibernateTemplate().find(hql,value);
        return list;
    }

    public List find(String hql, Object[] values) {
    	List list = getHibernateTemplate().find(hql,values);
        return list;
    }

    public List findByCriteria(DetachedCriteria detachedCriteria) {
    	List list = getHibernateTemplate().findByCriteria(detachedCriteria);
        return list;
    }
    
    public List loadAll(Class clazz) {
        return getHibernateTemplate().loadAll(clazz);        
    }

    public long countObjects(final Class clazz){
		 Integer count = (Integer)getHibernateTemplate().execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return ((Integer) session.createQuery("select count(*) from object in class " + clazz).iterate().next()) ;
		    }
		  });
		 return count.longValue();
    }

    public long countObjects(Criteria criteria, final Class clazz){
		 Integer count = (Integer)getHibernateTemplate().execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException {

		    	return ((Integer) session.createQuery("select count(*) from object in class " + clazz).iterate().next()) ;
		    	
		    }
		  });
		 return count.longValue();
   }

    public String maxAttribute(Class clazz, String attribute){
    	String hql = "select max(tb."+attribute+") from "+clazz.getCanonicalName()+" as tb";
    	List list = getHibernateTemplate().find(hql);
    	return list.get(0) == null ? "0" : list.get(0)+"";
   }
}
