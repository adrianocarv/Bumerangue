package br.org.ceu.bumerangue.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;

/**
 * Interface base de todos os DAOs
 * 
 */
public interface BaseDao{

	public void update(Object object);

    public void saveOrUpdate(Object object);
    
    public void saveOrUpdateAll(Collection collection);

    public void merge(Object object);

    public void delete(Object object);

    public void delete(Class clazz, Serializable id);
	
    public Object get(Class clazz, Serializable id);

    public List find(String hql);
    
    public List find(String hql, Object value);

    public List find(String hql, Object[] values);

    public List findByCriteria(DetachedCriteria detachedCriteria);
    	
    public List loadAll(Class clazz);

    public long countObjects(Class clazz);

    public long countObjects(Criteria criteria, Class clazz);
    
    public String maxAttribute(final Class clazz, final String attribute);
}
