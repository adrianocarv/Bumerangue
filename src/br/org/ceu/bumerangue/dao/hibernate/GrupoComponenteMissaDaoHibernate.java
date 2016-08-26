package br.org.ceu.bumerangue.dao.hibernate;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import br.org.ceu.bumerangue.dao.GrupoComponenteMissaDao;
import br.org.ceu.bumerangue.entity.GrupoComponenteMissa;
import br.org.ceu.bumerangue.entity.LiturgiaPalavra;
import br.org.ceu.bumerangue.entity.Oracao;
import br.org.ceu.bumerangue.util.ValidationRules;

public class GrupoComponenteMissaDaoHibernate extends BaseDaoHibernate implements GrupoComponenteMissaDao {
 
	public GrupoComponenteMissa get(Serializable id) {
    	return (GrupoComponenteMissa)super.get(GrupoComponenteMissa.class, id);
    }

	public LiturgiaPalavra getLiturgiaPalavraPorChave(String chave){
		Map<String,String> map = new HashMap<String,String>();
    	map.put("chave", chave);
    	List list = super.findByCriteria(DetachedCriteria.forClass(LiturgiaPalavra.class).add(Restrictions.allEq(map)));
    	return (list.isEmpty() ? null : (LiturgiaPalavra)list.get(0));    	
	}
	
	public Oracao getOracaoPorChave(String chave){
		Map<String,String> map = new HashMap<String,String>();
    	map.put("chave", chave);
    	List list = super.findByCriteria(DetachedCriteria.forClass(Oracao.class).add(Restrictions.allEq(map)));
    	return (list.isEmpty() ? null : (Oracao)list.get(0));    	
	}
	
	public List<LiturgiaPalavra> findLiturgiasPalavra(String chave){
    	String hql = "select o from "+LiturgiaPalavra.class.getName()+" as o where 1 = 1";
        if(ValidationRules.isInformed(chave)) hql += " and ( upper(o.chave) like '%"+chave+"%' )";

        hql += " order by o.chave";
        List<LiturgiaPalavra> liturgiasPalavra = super.find(hql);

        return liturgiasPalavra;
	}

	public List<Oracao> findOracoes(String chave){
    	String hql = "select o from Oracao as o where 1 = 1";
        if(ValidationRules.isInformed(chave)) hql += " and ( upper(o.chave) like '%"+chave+"%' )";

        hql += " order by o.chave";
        List<Oracao> oracoes = super.find(hql);

        return oracoes;
	}

	public List<Oracao> findOracoesVotivas(){
    	String hql = "select o from Oracao as o where upper(o.chave) like 'V%'";

        hql += " order by o.chave";
        List<Oracao> oracoesVotivas = super.find(hql);

        return oracoesVotivas;
	}

}
 
