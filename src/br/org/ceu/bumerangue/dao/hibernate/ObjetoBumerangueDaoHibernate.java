package br.org.ceu.bumerangue.dao.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.org.ceu.bumerangue.dao.ObjetoBumerangueDao;
import br.org.ceu.bumerangue.entity.ElementoDominio;
import br.org.ceu.bumerangue.entity.FilmeComercial;
import br.org.ceu.bumerangue.entity.LivroCultural;
import br.org.ceu.bumerangue.entity.ObjetoBumerangue;
import br.org.ceu.bumerangue.entity.Video;
import br.org.ceu.bumerangue.entity.criterias.PesquisaFilmeComercialCriteria;
import br.org.ceu.bumerangue.entity.criterias.PesquisaLivroCulturalCriteria;
import br.org.ceu.bumerangue.entity.criterias.PesquisaObjetoBumerangueCriteria;
import br.org.ceu.bumerangue.entity.criterias.PesquisaVideoCriteria;
import br.org.ceu.bumerangue.entity.suporte.deploy.Deploy;
import br.org.ceu.bumerangue.util.DateUtil;
import br.org.ceu.bumerangue.util.ValidationRules;

public class ObjetoBumerangueDaoHibernate extends BaseDaoHibernate implements ObjetoBumerangueDao {

	public ObjetoBumerangue get(Serializable id) {
    	return (ObjetoBumerangue)super.get(ObjetoBumerangue.class, id);
    }

    public ObjetoBumerangue getSubclass(Serializable id){
    	ObjetoBumerangue  objetoBumerangue = null;

    	objetoBumerangue = (ObjetoBumerangue)super.get(Video.class, id);
    	objetoBumerangue = (ObjetoBumerangue)super.get(LivroCultural.class, id);
    	objetoBumerangue = (ObjetoBumerangue)super.get(FilmeComercial.class, id);

    	return objetoBumerangue;
    }

    public List findVideos(PesquisaVideoCriteria helper){
        DetachedCriteria criteria = DetachedCriteria.forClass(Video.class);
        if(ValidationRules.isInformed(helper.getCodigo())) criteria.add(Restrictions.eq("codigo",helper.getCodigo()).ignoreCase());
        if(ValidationRules.isInformed(helper.getCategoria())) criteria.add(Restrictions.eq("categoria.id",helper.getCategoria()));
        if(ValidationRules.isInformed(helper.getCodigoCaixa())) criteria.add(Restrictions.like("codigoCaixa","%" + helper.getCodigoCaixa() + "%").ignoreCase());
        if(ValidationRules.isInformed(helper.getTitulo())) criteria.add(Restrictions.like("titulo","%" + helper.getTitulo() + "%").ignoreCase());
        if(ValidationRules.isInformed(helper.getLocalidade())) criteria.add(Restrictions.like("localidade","%" + helper.getLocalidade() + "%").ignoreCase());
        if(ValidationRules.isInformed(helper.getDataIni())) criteria.add(Restrictions.ge("data",DateUtil.getDate(helper.getDataIni(),"dd/MM/yyyy")));
        if(ValidationRules.isInformed(helper.getDataFim())) criteria.add(Restrictions.le("data",DateUtil.getDate(helper.getDataFim(),"dd/MM/yyyy")));
        if(helper.getLegendado() != null && helper.getLegendado().booleanValue()) criteria.add(Restrictions.eq("legendado",Boolean.TRUE));
        if(ValidationRules.isInformed(helper.getMidia())) criteria.add(Restrictions.eq("midia.id",helper.getMidia()));
        if(ValidationRules.isInformed(helper.getPalavrasChaves())) criteria.add(Restrictions.like("palavrasChaves","%" + helper.getPalavrasChaves() + "%").ignoreCase());
        if(ValidationRules.isInformed(helper.getObservacoesGerais())) criteria.add(Restrictions.like("observacoesGerais","%" + helper.getObservacoesGerais() + "%").ignoreCase());
        if(ValidationRules.isInformed(helper.getSituacao())){
        	//1 - Dispon�vel
        	if(helper.getSituacao().equals("1")){
        		criteria.add(Restrictions.ne("foraUso",Boolean.TRUE));
        		criteria.add(Restrictions.isNull("emprestimoAtual"));
           	//2 - Reservado / 3 - Emprestado
        	}else if(helper.getSituacao().equals("2") || helper.getSituacao().equals("3"))
        		criteria.add(Restrictions.isNotNull("emprestimoAtual"));
        }
       	if(ValidationRules.isInformed(helper.getUsuarioEmprestimo()))criteria.add(Restrictions.isNotNull("emprestimoAtual"));
        if(ValidationRules.isInformed(helper.getLocalizacaoFisica())) criteria.add(Restrictions.like("localizacaoFisica","%" + helper.getLocalizacaoFisica() + "%").ignoreCase());

        criteria.addOrder(Order.asc("midia"));
        criteria.addOrder(Order.asc("categoria"));
        criteria.addOrder(Order.asc("codigo"));

        List videos = super.findByCriteria(criteria);

		//caso necess�rio, filtra a listagem pela situa��o e pelo empr�stimo atual
		if( (helper.getSituacao() != null && (helper.getSituacao().equals("2") || helper.getSituacao().equals("3") ) ) || ValidationRules.isInformed(helper.getUsuarioEmprestimo()) ){
			List videosFiltrados = new ArrayList();
			boolean filtraUsuarioEmprestimo = ValidationRules.isInformed(helper.getUsuarioEmprestimo());
    		for (int i = 0; i < videos.size(); i++) {
				Video video = (Video)videos.get(i);

				String idUsuarioEmprestimo = video.getEmprestimoAtual().getUsuarioEmprestimo().getId();
				if( filtraUsuarioEmprestimo && !idUsuarioEmprestimo.equals(helper.getUsuarioEmprestimo()) ) continue;

				if(ValidationRules.isInformed(helper.getSituacao())){
			    	//2 - Reservado
					if(helper.getSituacao().equals("2") && video.getEmprestimoAtual().getDataEmprestimo() == null)
						videosFiltrados.add(video);
			       	//3 - Emprestado
					else if(helper.getSituacao().equals("3") && video.getEmprestimoAtual().getDataEmprestimo() != null)
						videosFiltrados.add(video);
		    	}else videosFiltrados.add(video);
			}
			videos = videosFiltrados;
    	}
		return videos;
	}

	public ObjetoBumerangue getPorNome(String nome){
		Map map = new HashMap();
    	map.put("titulo", nome);
    	List list = super.findByCriteria(DetachedCriteria.forClass(ObjetoBumerangue.class).add(Restrictions.allEq(map)));
    	return (list.isEmpty() ? null : (ObjetoBumerangue)list.get(0));    	
	}

	public Video getVideoPorCodigo(String codigo){
		//formata o c�digo
		Video video = new Video();
		video.setCodigoFormatado(codigo);
		
		Map map = new HashMap();
    	map.put("codigo", video.getCodigo());
    	List list = super.findByCriteria(DetachedCriteria.forClass(Video.class).add(Restrictions.allEq(map)));
    	return (list.isEmpty() ? null : (Video)list.get(0));
	}

    public List findNaoDevolvidosAte(int numeroDiasAntesHoje){
    	return new ArrayList();
    }

    public List findFilmesComerciais(PesquisaFilmeComercialCriteria criteria){
    	String hql = "select f from FilmeComercial as f where 1 = 1";
        if(ValidationRules.isInformed(criteria.getTitulo())) hql += " and ( upper(f.titulo) like '%"+criteria.getTitulo().toUpperCase()+"%' or upper(f.tituloOriginal) like '%"+criteria.getTitulo().toUpperCase()+"%' )";
        if(ValidationRules.isInformed(criteria.getPublico())) hql += " and f.publico.id = '"+criteria.getPublico()+"'";
        if(ValidationRules.isInformed(criteria.getDuracaoMinutosIni())) hql += " and f.duracaoMinutos >= '"+criteria.getDuracaoMinutosIni()+"'";
        if(ValidationRules.isInformed(criteria.getDuracaoMinutosFim())) hql += " and f.duracaoMinutos <= '"+criteria.getDuracaoMinutosFim()+"'";
        if(ValidationRules.isInformed(criteria.getAnoIni())) hql += " and f.ano >= '"+criteria.getAnoIni()+"'";
        if(ValidationRules.isInformed(criteria.getAnoFim())) hql += " and f.ano <= '"+criteria.getAnoFim()+"'";
        if(ValidationRules.isInformed(criteria.getCategoria())) hql += " and f.categoria.id = '"+criteria.getCategoria()+"'";
        if(ValidationRules.isInformed(criteria.getDiretor())) hql += " and upper(f.diretor) like '%"+criteria.getDiretor().toUpperCase()+"%'";
        if(ValidationRules.isInformed(criteria.getAtoresObservacoes())) hql += " and upper(f.atoresObservacoes) like '%"+criteria.getAtoresObservacoes().toUpperCase()+"%'";
        if(criteria.getDisponivelDownload() != null && criteria.getDisponivelDownload().booleanValue()) hql += " and (f.linksDownload is not null and f.linksDownload <> '')";

        hql += " order by f.dataCriacao desc, titulo";
        List filmesComerciais = super.find(hql);

        //trata o rownum e a ordena��o
        if(ValidationRules.isInformed(criteria.getNumeroMaisRecentes())){
            int numeroMaisRecentes = Integer.parseInt(criteria.getNumeroMaisRecentes());
            if(filmesComerciais.size() > numeroMaisRecentes){
            	Collection filmesRecentes = filmesComerciais.subList(0,numeroMaisRecentes);
            	filmesComerciais = new ArrayList(filmesRecentes);
            }
        }

        return filmesComerciais;
    }
    
	public FilmeComercial getFilmeComercialPorCodigo(String codigo){
		Map map = new HashMap();
    	map.put("codigo", codigo);
    	List list = super.findByCriteria(DetachedCriteria.forClass(FilmeComercial.class).add(Restrictions.allEq(map)));
    	return (list.isEmpty() ? null : (FilmeComercial)list.get(0));
	}
	
	public LivroCultural getLivroCulturalPorCodigo(String codigo){
		Map map = new HashMap();
    	map.put("codigo", codigo);
    	List list = super.findByCriteria(DetachedCriteria.forClass(LivroCultural.class).add(Restrictions.allEq(map)));
    	return (list.isEmpty() ? null : (LivroCultural)list.get(0));
	}

	public List<ObjetoBumerangue> findLivrosCulturais(PesquisaLivroCulturalCriteria helper){
        DetachedCriteria criteria = DetachedCriteria.forClass(LivroCultural.class);
        if(ValidationRules.isInformed(helper.getCodigo())) criteria.add(Restrictions.like("codigo","%"+helper.getCodigo()+"%").ignoreCase());
        if(ValidationRules.isInformed(helper.getTitulo())) criteria.add(Restrictions.like("titulo","%" + helper.getTitulo() + "%").ignoreCase());
        if(ValidationRules.isInformed(helper.getAutor())) criteria.add(Restrictions.like("autor","%" + helper.getAutor() + "%").ignoreCase());
        if(ValidationRules.isInformed(helper.getIdioma())) criteria.add(Restrictions.eq("idioma.id",helper.getIdioma()));
        if(ValidationRules.isInformed(helper.getPalavrasChaves())) criteria.add(Restrictions.like("palavrasChaves","%" + helper.getPalavrasChaves() + "%").ignoreCase());
        if(ValidationRules.isInformed(helper.getObservacoesGerais())) criteria.add(Restrictions.like("observacoesGerais","%" + helper.getObservacoesGerais() + "%").ignoreCase());
        if(ValidationRules.isInformed(helper.getSituacao())){
        	//1 - Dispon�vel
        	if(helper.getSituacao().equals("1")){
        		criteria.add(Restrictions.ne("foraUso",Boolean.TRUE));
        		criteria.add(Restrictions.isNull("emprestimoAtual"));
           	//3 - Emprestado
        	}else if(helper.getSituacao().equals("3"))
        		criteria.add(Restrictions.isNotNull("emprestimoAtual"));
        }
       	if(ValidationRules.isInformed(helper.getUsuarioEmprestimo()))criteria.add(Restrictions.isNotNull("emprestimoAtual"));

        criteria.addOrder(Order.asc("autor"));
        criteria.addOrder(Order.asc("titulo"));

		List<ObjetoBumerangue> livrosCulturais = super.findByCriteria(criteria);

		//caso necess�rio, filtra a listagem pela situa��o e pelo empr�stimo atual
		if( (helper.getSituacao() != null && helper.getSituacao().equals("3") ) || ValidationRules.isInformed(helper.getUsuarioEmprestimo()) ){
			List<ObjetoBumerangue> livrosCulturaisFiltrados = new ArrayList<ObjetoBumerangue>();
			boolean filtraUsuarioEmprestimo = ValidationRules.isInformed(helper.getUsuarioEmprestimo());
    		for (int i = 0; i < livrosCulturais.size(); i++) {
				LivroCultural livroCultural = (LivroCultural)livrosCulturais.get(i);

				String idUsuarioEmprestimo = livroCultural.getEmprestimoAtual().getUsuarioEmprestimo().getId();
				if( filtraUsuarioEmprestimo && !idUsuarioEmprestimo.equals(helper.getUsuarioEmprestimo()) ) continue;

				if(ValidationRules.isInformed(helper.getSituacao())){
			       	//3 - Emprestado
					if(helper.getSituacao().equals("3") && livroCultural.getEmprestimoAtual().getDataEmprestimo() != null)
						livrosCulturaisFiltrados.add(livroCultural);
		    	}else livrosCulturaisFiltrados.add(livroCultural);
			}
			livrosCulturais = livrosCulturaisFiltrados;
    	}
		
		if(livrosCulturais.size() < 30)
			for (ObjetoBumerangue livro : livrosCulturais)
				livro.setExibeImagem(true);
		
		return livrosCulturais;
    }

    public Integer getTotalObjetosBumerangue(ObjetoBumerangue objetoBumerangue){
    	if(objetoBumerangue == null) return null;

    	String hql = "select count(*) from "+objetoBumerangue.getClass().getName()+" as o where o.foraUso = ?";
        Integer totalObjetosBumerangue = (Integer)super.find(hql, Boolean.FALSE).get(0);
        return totalObjetosBumerangue;
    }

    public List<ObjetoBumerangue> findObjetosBumerangueOrdenadosPor(ObjetoBumerangue objetoBumerangue, String camposOrdenacao){
    	if(objetoBumerangue == null) return new ArrayList<ObjetoBumerangue>();
		
    	//monta a cl�usula order by, a partir de 'campoOrdenacao', colocando o alias 
    	String orderBy = "o.id";
    	boolean first = true;
    	for(String campoOrdenacao : camposOrdenacao.split(",")){
    		if(first){
    			orderBy = "o."+campoOrdenacao.trim();
    			first = false;
    		}else
    			orderBy += ", o."+campoOrdenacao.trim();
    	}
    	
    	String hql = "select o from "+objetoBumerangue.getClass().getName()+" as o where o.foraUso = ? order by "+orderBy;
    	List<ObjetoBumerangue> objetosBumerangue = super.find(hql, new Object[]{Boolean.FALSE});
        return objetosBumerangue;
    }

    public Integer getTotalObjetosBumerangueSemLocalizacao(ObjetoBumerangue objetoBumerangue){
    	if(objetoBumerangue == null) return null;

    	String hql = "select count(*) from "+objetoBumerangue.getClass().getName()+" as o where o.foraUso = ? and o.localizacaoFisica is null";
        Integer totalObjetosBumerangueSemLocalizacao = (Integer)super.find(hql, Boolean.FALSE).get(0);
        return totalObjetosBumerangueSemLocalizacao;
    }

    public List<ObjetoBumerangue> findObjetosBumerangueSemEspacoCompartimentos(ObjetoBumerangue objetoBumerangue){
    	if(objetoBumerangue == null) return null;

    	String hql = "select o from "+objetoBumerangue.getClass().getName()+" as o where o.foraUso = ? and o.localizacaoFisica = ?";
        List<ObjetoBumerangue> objetosBumerangue = super.find(hql, new Object[]{Boolean.FALSE,ElementoDominio.SEM_ESPACO_COMPARTIMENTO});
        return objetosBumerangue;
    }

    public List<ObjetoBumerangue> findObjetosBumerangueAtualizados(ObjetoBumerangue objetoBumerangue){
    	if(objetoBumerangue == null) return null;

    	String hql = "select o from "+objetoBumerangue.getClass().getName()+" as o where o.foraUso = ? and o.localizacaoFisica != o.localizacaoFisicaAnterior";
        List<ObjetoBumerangue> objetosBumerangue = super.find(hql, Boolean.FALSE);
        return objetosBumerangue;
    }

    public List<ObjetoBumerangue> findObjetosBumerangue(PesquisaObjetoBumerangueCriteria criteria){
    	ObjetoBumerangue objetoBumerangue = Deploy.getObjetoBumeranguePorNomeModulo(criteria.getNomeModulo());
    	if(objetoBumerangue == null)
    		return new ArrayList<ObjetoBumerangue>();

    	String hql = "select o from "+objetoBumerangue.getClass().getName()+" as o where 1 = 1";
        ArrayList args = new ArrayList();
        if(ValidationRules.isInformed(criteria.getCodigoIni())) hql += " and upper(o.codigo) >= '"+criteria.getCodigoIni()+"'";
        if(ValidationRules.isInformed(criteria.getCodigoFim())) hql += " and upper(o.codigo) <= '"+criteria.getCodigoFim()+"'";
        if(criteria.getForaUso() != null){ hql += " and o.foraUso = ?"; args.add(criteria.getForaUso());}

        hql += " order by o.codigo";
        List<ObjetoBumerangue> objetosBumerangue = super.find(hql,args.toArray());

    	return objetosBumerangue;
    }

}
