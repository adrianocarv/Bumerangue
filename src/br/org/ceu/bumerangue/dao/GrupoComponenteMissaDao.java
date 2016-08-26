package br.org.ceu.bumerangue.dao;

import java.io.Serializable;
import java.util.List;

import br.org.ceu.bumerangue.entity.GrupoComponenteMissa;
import br.org.ceu.bumerangue.entity.LiturgiaPalavra;
import br.org.ceu.bumerangue.entity.Oracao;

public interface GrupoComponenteMissaDao extends BaseDao {
 
	public GrupoComponenteMissa get(Serializable id);

	public LiturgiaPalavra getLiturgiaPalavraPorChave(String chave);
	
	public Oracao getOracaoPorChave(String chave);

	public List<LiturgiaPalavra> findLiturgiasPalavra(String chave);
	
	public List<Oracao> findOracoes(String chave);
	
	public List<Oracao> findOracoesVotivas();
}
 
