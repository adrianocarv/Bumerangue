package br.org.ceu.bumerangue.dao;

import java.io.Serializable;
import java.util.List;

import br.org.ceu.bumerangue.entity.Missa;
import br.org.ceu.bumerangue.entity.PlanoMissa;

public interface PlanoMissaDao extends BaseDao {
 
	public PlanoMissa get(Serializable id);
	public PlanoMissa getPorAnoMes(String anoMes);
	public Missa getMissa(Serializable id);
	public List<PlanoMissa> findPlanosMissa(String anoMes, String idSituacao);
}
 
