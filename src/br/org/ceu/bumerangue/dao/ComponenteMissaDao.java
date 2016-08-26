package br.org.ceu.bumerangue.dao;

import java.io.Serializable;
import java.util.List;

import br.org.ceu.bumerangue.entity.ComponenteMissa;
import br.org.ceu.bumerangue.entity.Oracao;
import br.org.ceu.bumerangue.entity.Prefacio;

public interface ComponenteMissaDao extends BaseDao {
 
	public ComponenteMissa get(Serializable id);

	public List<Prefacio> findPrefaciosCompartilhados();
}
 
