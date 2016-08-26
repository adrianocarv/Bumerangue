package br.org.ceu.bumerangue.dao;

import java.io.Serializable;
import java.util.List;

import br.org.ceu.bumerangue.entity.FilmeComercial;
import br.org.ceu.bumerangue.entity.LivroCultural;
import br.org.ceu.bumerangue.entity.ObjetoBumerangue;
import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.entity.Video;
import br.org.ceu.bumerangue.entity.criterias.PesquisaFilmeComercialCriteria;
import br.org.ceu.bumerangue.entity.criterias.PesquisaLivroCulturalCriteria;
import br.org.ceu.bumerangue.entity.criterias.PesquisaObjetoBumerangueCriteria;
import br.org.ceu.bumerangue.entity.criterias.PesquisaVideoCriteria;

public interface ObjetoBumerangueDao extends BaseDao {
 
	public ObjetoBumerangue get(Serializable id);
    public ObjetoBumerangue getSubclass(Serializable id);
    public List findVideos(PesquisaVideoCriteria pesquisaVideoCriteria);
	public ObjetoBumerangue getPorNome(String nome);
	public Video getVideoPorCodigo(String codigo);
    public List findNaoDevolvidosAte(int numeroDiasAntesHoje);
    public List findFilmesComerciais(PesquisaFilmeComercialCriteria pesquisaFilmeComercialCriteria);
	public FilmeComercial getFilmeComercialPorCodigo(String codigo);
	public LivroCultural getLivroCulturalPorCodigo(String codigo);
    public List findLivrosCulturais(PesquisaLivroCulturalCriteria pesquisaLivroCulturalCriteria);

    /**
     * Retorna o total de ObjetosBumerangue (em uso) corespondentes ao tipo de 'objetoBumerangue'.
     * @author Adriano Carvalho
     * @param objetoBumerangue
     * @param nomeModulo
     * @return
     */
    public Integer getTotalObjetosBumerangue(ObjetoBumerangue objetoBumerangue);

   	/**
     * Retorna a lista de ObjetosBumerangue (em uso) corespondentes ao tipo de 'objetoBumerangue'<br>
     * ordenados por 'camposOrdenacao'.
     * @param classeObjetoBumerangue
     * @param camposOrdenacao
     * @return
     */
    public List<ObjetoBumerangue> findObjetosBumerangueOrdenadosPor(ObjetoBumerangue objetoBumerangue, String camposOrdenacao);

    /**
     * Retorna o total de ObjetosBumerangue (em uso) corespondentes ao tipo de 'objetoBumerangue'<br>
     * com o atributo localizacaoFisica vazio.
     * @author Adriano Carvalho
     * @param objetoBumerangue
     * @return
     */
    public Integer getTotalObjetosBumerangueSemLocalizacao(ObjetoBumerangue objetoBumerangue);

    /**
     * Retorna a lista de ObjetosBumerangue (em uso) corespondentes ao tipo de 'objetoBumerangue'<br>
     * com o atributo localizacaoFisica marcado com SEM_ESPACO.
     * @author Adriano Carvalho
     * @param objetoBumerangue
     * @return
     */
    public List<ObjetoBumerangue> findObjetosBumerangueSemEspacoCompartimentos(ObjetoBumerangue objetoBumerangue);

    /**
     * Retorna a lista de ObjetosBumerangue (em uso) corespondentes ao tipo de 'objetoBumerangue'<br>
     * com o atributo localizacaoFisica diferente do atributo localizacaoFisicaAnterior.
     * @author Adriano Carvalho
     * @param objetoBumerangue
     * @return
     */
    public List<ObjetoBumerangue> findObjetosBumerangueAtualizados(ObjetoBumerangue objetoBumerangue);
    
    public List<ObjetoBumerangue> findObjetosBumerangue(PesquisaObjetoBumerangueCriteria pesquisaObjetoBumerangueCriteria);
}
