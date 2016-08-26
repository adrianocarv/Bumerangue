package br.org.ceu.bumerangue.dao;

import java.io.Serializable;

import br.org.ceu.bumerangue.entity.ReferenciaMissal;

public interface ReferenciaMissalDao extends BaseDao {
 
	public ReferenciaMissal get(Serializable id);

}
 
