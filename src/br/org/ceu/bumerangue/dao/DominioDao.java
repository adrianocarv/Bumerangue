package br.org.ceu.bumerangue.dao;

import java.io.Serializable;

import br.org.ceu.bumerangue.entity.Dominio;
import br.org.ceu.bumerangue.entity.ElementoDominio;

public interface DominioDao extends BaseDao {
 
	public Dominio get(Serializable id);
	public Dominio get(Integer codigo);
	public ElementoDominio getElementoDominio(Integer codigoDominio, Integer codigoElementoDominio);
}
