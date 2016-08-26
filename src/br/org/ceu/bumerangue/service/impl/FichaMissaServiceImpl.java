package br.org.ceu.bumerangue.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import br.org.ceu.bumerangue.dao.ComponenteMissaDao;
import br.org.ceu.bumerangue.dao.DominioDao;
import br.org.ceu.bumerangue.dao.GrupoComponenteMissaDao;
import br.org.ceu.bumerangue.dao.PlanoMissaDao;
import br.org.ceu.bumerangue.dao.ReferenciaMissalDao;
import br.org.ceu.bumerangue.entity.AclamacaoEvangelho;
import br.org.ceu.bumerangue.entity.AntifonaComunhao;
import br.org.ceu.bumerangue.entity.ComponenteMissa;
import br.org.ceu.bumerangue.entity.Dominio;
import br.org.ceu.bumerangue.entity.ElementoDominio;
import br.org.ceu.bumerangue.entity.Evangelho;
import br.org.ceu.bumerangue.entity.GrupoComponenteMissa;
import br.org.ceu.bumerangue.entity.Introito;
import br.org.ceu.bumerangue.entity.Leitura1;
import br.org.ceu.bumerangue.entity.Leitura2;
import br.org.ceu.bumerangue.entity.LiturgiaPalavra;
import br.org.ceu.bumerangue.entity.Missa;
import br.org.ceu.bumerangue.entity.Oracao;
import br.org.ceu.bumerangue.entity.Permissao;
import br.org.ceu.bumerangue.entity.PlanoMissa;
import br.org.ceu.bumerangue.entity.Prefacio;
import br.org.ceu.bumerangue.entity.ReferenciaMissal;
import br.org.ceu.bumerangue.entity.SalmoResponsorial;
import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.service.FichaMissaService;
import br.org.ceu.bumerangue.service.MensagemService;
import br.org.ceu.bumerangue.service.exceptions.BumerangueErrorRuntimeException;
import br.org.ceu.bumerangue.util.ValidationRules;

public class FichaMissaServiceImpl extends BaseServiceImpl implements FichaMissaService {
    private MensagemService mensagemService;
    public void setMensagemService(MensagemService mensagemService) { this.mensagemService = mensagemService;}

	private GrupoComponenteMissaDao grupoComponenteMissaDao;
    public void setGrupoComponenteMissaDao(GrupoComponenteMissaDao grupoComponenteMissaDao) { this.grupoComponenteMissaDao = grupoComponenteMissaDao; }

    private ComponenteMissaDao componenteMissaDao;
    public void setComponenteMissaDao(ComponenteMissaDao componenteMissaDao) { this.componenteMissaDao = componenteMissaDao; }

    private ReferenciaMissalDao referenciaMissalDao;
    public void setReferenciaMissalDao(ReferenciaMissalDao referenciaMissalDao) { this.referenciaMissalDao = referenciaMissalDao; }

    private PlanoMissaDao planoMissaDao;
    public void setPlanoMissaDao(PlanoMissaDao planoMissaDao) { this.planoMissaDao = planoMissaDao; }
   
    private DominioDao dominioDao;
    public void setDominioDao(DominioDao dominioDao) { this.dominioDao = dominioDao; }

    public List<LiturgiaPalavra> findLiturgiasPalavra(Usuario usuarioLogado, String chave){
		return grupoComponenteMissaDao.findLiturgiasPalavra(chave);
	}

	public List<Oracao> findOracoes(Usuario usuarioLogado, String chave){
		return grupoComponenteMissaDao.findOracoes(chave);
	}
	
	public GrupoComponenteMissa getGrupoComponenteMissa(Usuario usuarioLogado, String idGrupoComponenteMissa){
		return grupoComponenteMissaDao.get(idGrupoComponenteMissa);
	}
	
	public List<Oracao> findOracoesVotivas(Usuario usuarioLogado){
		return grupoComponenteMissaDao.findOracoesVotivas();
	}
	
	public void inserirGrupoComponenteMissa(Usuario usuarioLogado, GrupoComponenteMissa grupoComponenteMissa){
		//valida a chave
		if(!grupoComponenteMissa.isChaveValida())
			throw new BumerangueErrorRuntimeException(grupoComponenteMissa.getErrosValidacaoChave());
		
		grupoComponenteMissa.setChave(grupoComponenteMissa.getChave().toUpperCase());
		grupoComponenteMissa.setId(null);
		if(grupoComponenteMissa.getFestaMovel() == null || !ValidationRules.isInformed(grupoComponenteMissa.getFestaMovel().getId()) ) grupoComponenteMissa.setFestaMovel(null);
		if(grupoComponenteMissa instanceof Oracao){
			if( ((Oracao)grupoComponenteMissa).getOracaoVotiva() == null || !ValidationRules.isInformed(((Oracao)grupoComponenteMissa).getOracaoVotiva().getId())) ((Oracao)grupoComponenteMissa).setOracaoVotiva(null);
		}
		
		//concatena a chave com a descrição complementar da chave, se tiver
		//recupera a festa móvel persistente, para obter a descrição
		if(grupoComponenteMissa.getFestaMovel() != null){
			ElementoDominio festaMovelBD = null;
			for(ElementoDominio festaMovel : dominioDao.get(Dominio.FICHA_MISSA_FESTA_MOVEL.getCodigo()).getElementosDominioEmUso()){
				if(festaMovel.getId().equals(grupoComponenteMissa.getFestaMovel().getId())){
					festaMovelBD = festaMovel;
					break;
				}
			}
			grupoComponenteMissa.setFestaMovel(festaMovelBD);
		}
		grupoComponenteMissa.concatenaDescricaoComplementarChave();

		//verifica se existe outro grupo com a mesma chave.
		GrupoComponenteMissa grupoComponenteMissaUnico = null;
		if(grupoComponenteMissa instanceof LiturgiaPalavra)
			grupoComponenteMissaUnico = grupoComponenteMissaDao.getLiturgiaPalavraPorChave(grupoComponenteMissa.getChave());
		else
			grupoComponenteMissaUnico = grupoComponenteMissaDao.getOracaoPorChave(grupoComponenteMissa.getChave());
		if(grupoComponenteMissaUnico != null)
			throw new BumerangueErrorRuntimeException("Já existe outro elemento com a chave: "+grupoComponenteMissa.getChave()+".");

		//seta os atributos de auditoria
		new BumerangueServiceImpl().editarAtributosAuditoria(grupoComponenteMissa, usuarioLogado, true);

		grupoComponenteMissaDao.saveOrUpdate(grupoComponenteMissa);
	}
	
	public void editarGrupoComponenteMissa(Usuario usuarioLogado, GrupoComponenteMissa grupoComponenteMissa){
		//valida a chave
		if(!grupoComponenteMissa.isChaveValida())
			throw new BumerangueErrorRuntimeException(grupoComponenteMissa.getErrosValidacaoChave());

		grupoComponenteMissa.setChave(grupoComponenteMissa.getChave().toUpperCase());
		if(grupoComponenteMissa.getFestaMovel() == null || !ValidationRules.isInformed(grupoComponenteMissa.getFestaMovel().getId()) ) grupoComponenteMissa.setFestaMovel(null);
		if(grupoComponenteMissa instanceof Oracao){
			if( ((Oracao)grupoComponenteMissa).getOracaoVotiva() == null || !ValidationRules.isInformed(((Oracao)grupoComponenteMissa).getOracaoVotiva().getId())) ((Oracao)grupoComponenteMissa).setOracaoVotiva(null);
		}
		
		//concatena a chave com a descrição complementar da chave, se tiver
		//recupera a festa móvel persistente, para obter a descrição
		if(grupoComponenteMissa.getFestaMovel() != null){
			ElementoDominio festaMovelBD = null;
			for(ElementoDominio festaMovel : dominioDao.get(Dominio.FICHA_MISSA_FESTA_MOVEL.getCodigo()).getElementosDominioEmUso()){
				if(festaMovel.getId().equals(grupoComponenteMissa.getFestaMovel().getId())){
					festaMovelBD = festaMovel;
					break;
				}
			}
			grupoComponenteMissa.setFestaMovel(festaMovelBD);
		}
		grupoComponenteMissa.concatenaDescricaoComplementarChave();

		
		//verifica se existe outro grupo com a mesma chave.
		GrupoComponenteMissa grupoComponenteMissaUnico = null;
		if(grupoComponenteMissa instanceof LiturgiaPalavra)
			grupoComponenteMissaUnico = grupoComponenteMissaDao.getLiturgiaPalavraPorChave(grupoComponenteMissa.getChave());
		else
			grupoComponenteMissaUnico = grupoComponenteMissaDao.getOracaoPorChave(grupoComponenteMissa.getChave());
		if(grupoComponenteMissaUnico != null && !grupoComponenteMissaUnico.getId().equals(grupoComponenteMissa.getId()))
			throw new BumerangueErrorRuntimeException("Já existe outro elemento com a chave: "+grupoComponenteMissa.getChave()+".");

		GrupoComponenteMissa bd = grupoComponenteMissaDao.get(grupoComponenteMissa.getId());
		bd.setChave(grupoComponenteMissa.getChave());
		bd.setDescricaoChave(grupoComponenteMissa.getDescricaoChave());
		bd.setFestaMovel(grupoComponenteMissa.getFestaMovel());
		if(grupoComponenteMissa instanceof Oracao){
			((Oracao)bd).setOracaoVotiva(((Oracao)grupoComponenteMissa).getOracaoVotiva());
		}
		bd.setLinhaComandoCadastro(grupoComponenteMissa.getLinhaComandoCadastro());

		//realiza o cadastro por linha de comando
		this.cadastrarGrupoPorLinhaComando(bd);
		
		//seta os atributos de auditoria
		//TODO corrigirnew BumerangueServiceImpl().editarAtributosAuditoria(bd, usuarioLogado, false);
	}
	
	private void cadastrarGrupoPorLinhaComando(GrupoComponenteMissa grupoComponenteMissa){
		if(StringUtils.isBlank(grupoComponenteMissa.getLinhaComandoCadastro()))
			return;
		String[] fragmentosComponentes = StringUtils.split(grupoComponenteMissa.getLinhaComandoCadastro(),"*");
		
		for(String fragmentoComponente : fragmentosComponentes){
			this.cadastrarComponentePorLinhaComando(grupoComponenteMissa,fragmentoComponente);
		}
	}
	
	private void cadastrarComponentePorLinhaComando(GrupoComponenteMissa grupoComponenteMissa, String fragmentoComponente){
		fragmentoComponente = fragmentoComponente.trim();
		
		//valida
		String[] siglas = grupoComponenteMissa instanceof LiturgiaPalavra ? new String[] {"L1","PS","L2","AE","EV"} : new String[] {"IT","PF","AC"};
		String msg = " Padrão:<br>"+grupoComponenteMissa.getLinhaComandoCadastroPadrao();
		if(fragmentoComponente.length() < 2)
			throw new BumerangueErrorRuntimeException("Linha de comando inválida."+msg);
		boolean siglaValida = false;
		String sigla = fragmentoComponente.substring(0,2);
		for(String siglaAux : siglas){
			if(siglaAux.equalsIgnoreCase(sigla)){
				siglaValida = true;
				break;
			}
		}
		if(!siglaValida)
			throw new BumerangueErrorRuntimeException("Sigla do componente inválida: "+sigla+"."+msg);
		
		//cadastra o componente, se for o caso
		ComponenteMissa componenteMissa = this.getComponenteMissaPorSiglaLinhaComanho(grupoComponenteMissa, sigla);
		
		//cadastra as referencias
		String[] fragmentosReferencias = StringUtils.split(fragmentoComponente.substring(2),"+");
		
		for(String fragmentoReferencia : fragmentosReferencias){
			this.cadastrarRefenciaPorLinhaComando(componenteMissa,fragmentoReferencia);
		}
	}

	private ComponenteMissa getComponenteMissaPorSiglaLinhaComanho(GrupoComponenteMissa grupoComponenteMissa, String sigla){
		ComponenteMissa componenteMissa = null;
		if(sigla.equalsIgnoreCase("L1")){
			//se não tiver o componente, cadastra
			if( ((LiturgiaPalavra)grupoComponenteMissa).getLeitura1() == null ){
				((LiturgiaPalavra)grupoComponenteMissa).setLeitura1(new Leitura1());
				grupoComponenteMissaDao.saveOrUpdate(grupoComponenteMissa);
			}
			componenteMissa = ((LiturgiaPalavra)grupoComponenteMissa).getLeitura1();
		}else if(sigla.equalsIgnoreCase("PS")){
			//se não tiver o componente, cadastra
			if( ((LiturgiaPalavra)grupoComponenteMissa).getSalmoResponsorial() == null ){
				((LiturgiaPalavra)grupoComponenteMissa).setSalmoResponsorial(new SalmoResponsorial());
				grupoComponenteMissaDao.saveOrUpdate(grupoComponenteMissa);
			}
			componenteMissa = ((LiturgiaPalavra)grupoComponenteMissa).getSalmoResponsorial();
		}else if(sigla.equalsIgnoreCase("L2")){
			//se não tiver o componente, cadastra
			if( ((LiturgiaPalavra)grupoComponenteMissa).getLeitura2() == null ){
				((LiturgiaPalavra)grupoComponenteMissa).setLeitura2(new Leitura2());
				grupoComponenteMissaDao.saveOrUpdate(grupoComponenteMissa);
			}
			componenteMissa = ((LiturgiaPalavra)grupoComponenteMissa).getLeitura2();
		}else if(sigla.equalsIgnoreCase("AE")){
			//se não tiver o componente, cadastra
			if( ((LiturgiaPalavra)grupoComponenteMissa).getAclamacaoEvangelho() == null ){
				((LiturgiaPalavra)grupoComponenteMissa).setAclamacaoEvangelho(new AclamacaoEvangelho());
				grupoComponenteMissaDao.saveOrUpdate(grupoComponenteMissa);
			}
			componenteMissa = ((LiturgiaPalavra)grupoComponenteMissa).getAclamacaoEvangelho();
		}else if(sigla.equalsIgnoreCase("EV")){
			//se não tiver o componente, cadastra
			if( ((LiturgiaPalavra)grupoComponenteMissa).getEvangelho() == null ){
				((LiturgiaPalavra)grupoComponenteMissa).setEvangelho(new Evangelho());
				grupoComponenteMissaDao.saveOrUpdate(grupoComponenteMissa);
			}
			componenteMissa = ((LiturgiaPalavra)grupoComponenteMissa).getEvangelho();
		}else if(sigla.equalsIgnoreCase("IT")){
			//se não tiver o componente, cadastra
			if( ((Oracao)grupoComponenteMissa).getIntroito() == null ){
				((Oracao)grupoComponenteMissa).setIntroito(new Introito());
				grupoComponenteMissaDao.saveOrUpdate(grupoComponenteMissa);
			}
			componenteMissa = ((Oracao)grupoComponenteMissa).getIntroito();
		}else if(sigla.equalsIgnoreCase("PF")){
			//se não tiver o componente, cadastra
			if( ((Oracao)grupoComponenteMissa).getPrefacio() == null ){
				((Oracao)grupoComponenteMissa).setPrefacio(new Prefacio());
				grupoComponenteMissaDao.saveOrUpdate(grupoComponenteMissa);
			}
			componenteMissa = ((Oracao)grupoComponenteMissa).getPrefacio();
		}else if(sigla.equalsIgnoreCase("AC")){
			//se não tiver o componente, cadastra
			if( ((Oracao)grupoComponenteMissa).getAntifonaComunhao() == null ){
				((Oracao)grupoComponenteMissa).setAntifonaComunhao(new AntifonaComunhao());
				grupoComponenteMissaDao.saveOrUpdate(grupoComponenteMissa);
			}
			componenteMissa = ((Oracao)grupoComponenteMissa).getAntifonaComunhao();
		}
		return componenteMissa;
	}
	
	private void cadastrarRefenciaPorLinhaComando(ComponenteMissa componenteMissa, String fragmentoReferencia){
		fragmentoReferencia = fragmentoReferencia.trim();

		//valida sigla
		String[] siglas = {"mp","mc"};
		String msg = " Padrão:<br>mp( página & endereço nas escrituras) + mc( página & endereço nas escrituras)";
		if(fragmentoReferencia.length() < 2)
			throw new BumerangueErrorRuntimeException("Referência inválida."+msg);
		boolean siglaValida = false;
		String sigla = fragmentoReferencia.substring(0,2);
		for(String siglaAux : siglas){
			if(siglaAux.equalsIgnoreCase(sigla)){
				siglaValida = true;
				break;
			}
		}
		if(!siglaValida)
			throw new BumerangueErrorRuntimeException("Sigla da referência inválida: "+sigla+"."+msg);
		
		//valida pagina e endereço nas escrituras
		String[] paginaEndereco = StringUtils.split(fragmentoReferencia.substring(2),"&");
		if(paginaEndereco.length > 2 || paginaEndereco.length < 1 )
			throw new BumerangueErrorRuntimeException("Página e endereço nas escrituras inválidos."+msg);

		//cadastra a referência, se for o caso
		String pagina = paginaEndereco[0];
		String endereco = paginaEndereco.length == 2 ? paginaEndereco[1] : null;
		
		pagina = StringUtils.replace(pagina,"(","");
		pagina = StringUtils.replace(pagina,")","").trim();
		endereco = StringUtils.replace(endereco,"(","");
		endereco = StringUtils.replace(endereco,")","");
		if(endereco != null) endereco = endereco.trim();
		
		
		ElementoDominio idioma = null;
		List<ElementoDominio> idiomas = dominioDao.get(Dominio.FICHA_MISSA_EDICAO_IDIOMA_MISSAL.getCodigo()).getElementosDominioEmUso();
		for(ElementoDominio idiomaAux : idiomas){
			if(idiomaAux.getDescricao().equalsIgnoreCase(sigla)){
				idioma = idiomaAux;
				break;
			}
		}
		ReferenciaMissal referenciaMissal = null;
		for(ReferenciaMissal referenciaMissalAux : componenteMissa.getReferenciasMissais()){
			if(referenciaMissalAux.getEdicaoIdioma().getDescricao().equalsIgnoreCase(sigla)){
				referenciaMissal = referenciaMissalAux;
				break;
			}
		}
		
		if(referenciaMissal == null){
			referenciaMissal = new ReferenciaMissal();
			referenciaMissal.setEdicaoIdioma(idioma);
			componenteMissa.getReferenciasMissais().add(referenciaMissal);
		}
		
		if(!StringUtils.isBlank(pagina)) referenciaMissal.setPagina(pagina);
		if(!StringUtils.isBlank(endereco)) referenciaMissal.setEnderecoEscrituras(endereco);
		
		componenteMissaDao.saveOrUpdate(componenteMissa);
	}

	public void excluirGrupoComponenteMissa(Usuario usuarioLogado, GrupoComponenteMissa grupoComponenteMissa){
		grupoComponenteMissa = grupoComponenteMissaDao.get(grupoComponenteMissa.getId());
		grupoComponenteMissaDao.delete(grupoComponenteMissa);
	}

	public ComponenteMissa getComponenteMissa(Usuario usuarioLogado, String idComponenteMissa){
		return componenteMissaDao.get(idComponenteMissa);
	}
	
	public List<Prefacio> findPrefaciosCompartilhados(Usuario usuarioLogado){
		return componenteMissaDao.findPrefaciosCompartilhados();
	}

	public void inserirComponenteMissa(Usuario usuarioLogado, ComponenteMissa componenteMissa){
		//verifica o preenchimento do campo textoLatim
		if(!componenteMissa.isTextoLatimValido()){
			throw new BumerangueErrorRuntimeException(componenteMissa.getErrosValidacao());
		}
		
		//valida os campos do prefácio
		if(!componenteMissa.isCamposPrefacioValidos()){
			throw new BumerangueErrorRuntimeException(componenteMissa.getErrosValidacao());
		}

		GrupoComponenteMissa grupoComponenteMissa = grupoComponenteMissaDao.get(componenteMissa.getGrupoComponenteMissa().getId());

		componenteMissa.setId(null);
		if(componenteMissa instanceof Prefacio){
			if( ((Prefacio)componenteMissa).getPrefacioCompartilhado() == null || !ValidationRules.isInformed(((Prefacio)componenteMissa).getPrefacioCompartilhado().getId())) ((Prefacio)componenteMissa).setPrefacioCompartilhado(null);
		}

		//seta os atributos de auditoria
		new BumerangueServiceImpl().editarAtributosAuditoria(componenteMissa, usuarioLogado, true);

		grupoComponenteMissa.addComponente(componenteMissa);
		
		grupoComponenteMissaDao.saveOrUpdate(grupoComponenteMissa);
	}
	
	public void editarComponenteMissa(Usuario usuarioLogado, ComponenteMissa componenteMissa){
		//verifica o preenchimento do campo textoLatim
		if(!componenteMissa.isTextoLatimValido()){
			throw new BumerangueErrorRuntimeException(componenteMissa.getErrosValidacao());
		}
		
		//valida os campos do prefácio
		if(!componenteMissa.isCamposPrefacioValidos()){
			throw new BumerangueErrorRuntimeException(componenteMissa.getErrosValidacao());
		}
		
		ComponenteMissa bd = componenteMissaDao.get(componenteMissa.getId());
		bd.setTextoLatim(componenteMissa.getTextoLatim());
		if(componenteMissa instanceof Prefacio){
			((Prefacio)bd).setProprio( ((Prefacio)componenteMissa).getProprio() );
			((Prefacio)bd).setCompartilhado( ((Prefacio)componenteMissa).getCompartilhado() );
			((Prefacio)bd).setDescricao( ((Prefacio)componenteMissa).getDescricao() );
			if( ((Prefacio)componenteMissa).getPrefacioCompartilhado() == null || !ValidationRules.isInformed(((Prefacio)componenteMissa).getPrefacioCompartilhado().getId())) ((Prefacio)componenteMissa).setPrefacioCompartilhado(null);
			((Prefacio)bd).setPrefacioCompartilhado(((Prefacio)componenteMissa).getPrefacioCompartilhado());
		}

		//seta os atributos de auditoria
		new BumerangueServiceImpl().editarAtributosAuditoria(bd, usuarioLogado, false);
	}
	
	public void excluirComponenteMissa(Usuario usuarioLogado, ComponenteMissa componenteMissa){
		GrupoComponenteMissa grupoComponenteMissa = grupoComponenteMissaDao.get(componenteMissa.getGrupoComponenteMissa().getId());

		grupoComponenteMissa.removeComponente(componenteMissa);

		//seta os atributos de auditoria
		new BumerangueServiceImpl().editarAtributosAuditoria(grupoComponenteMissa, usuarioLogado, false);

		grupoComponenteMissaDao.saveOrUpdate(grupoComponenteMissa);

		componenteMissa = componenteMissaDao.get(componenteMissa.getId());
		componenteMissaDao.delete(componenteMissa);
	}

	public ReferenciaMissal getReferenciaMissal(Usuario usuarioLogado, String idReferenciaMissal){
		return referenciaMissalDao.get(idReferenciaMissal);
	}
	
	public void inserirReferenciaMissal(Usuario usuarioLogado, ReferenciaMissal referenciaMissal){
		ComponenteMissa componenteMissa = componenteMissaDao.get(referenciaMissal.getComponenteMissa().getId());

		//verifica se já existe outra referencia com o mesmo idioma/edição
		for(ReferenciaMissal ref : componenteMissa.getReferenciasMissais()){
			if( ref.getEdicaoIdioma().getId().equals(referenciaMissal.getEdicaoIdioma().getId()) ){
				throw new BumerangueErrorRuntimeException("Já existe uma referência para o 'Idioma / Edição' "+ref.getEdicaoIdioma().getDescricao());
			}
		}
		
		referenciaMissal.setId(null);
		if(referenciaMissal.getEdicaoIdioma() == null || !ValidationRules.isInformed(referenciaMissal.getEdicaoIdioma().getId()) ) referenciaMissal.setEdicaoIdioma(null);

		//seta os atributos de auditoria
		new BumerangueServiceImpl().editarAtributosAuditoria(referenciaMissal, usuarioLogado, true);

		componenteMissa.getReferenciasMissais().add(referenciaMissal);
		
		grupoComponenteMissaDao.saveOrUpdate(componenteMissa);
	}
	
	public void editarReferenciaMissal(Usuario usuarioLogado, ReferenciaMissal referenciaMissal){
		ComponenteMissa componenteMissa = componenteMissaDao.get(referenciaMissal.getComponenteMissa().getId());

		//verifica se já existe outra referencia com o mesmo idioma/edição
		for(ReferenciaMissal ref : componenteMissa.getReferenciasMissais()){
			if( !referenciaMissal.getId().equals(ref.getId()) && ref.getEdicaoIdioma().getId().equals(referenciaMissal.getEdicaoIdioma().getId()) ){
				throw new BumerangueErrorRuntimeException("Já existe uma referência para o 'Idioma / Edição' "+ref.getEdicaoIdioma().getDescricao());
			}
		}

		ReferenciaMissal bd = referenciaMissalDao.get(referenciaMissal.getId());
		bd.setEdicaoIdioma(referenciaMissal.getEdicaoIdioma());
		bd.setPagina(referenciaMissal.getPagina());
		bd.setTexto(referenciaMissal.getTexto());
		bd.setEnderecoEscrituras(referenciaMissal.getEnderecoEscrituras());
		
		//seta os atributos de auditoria
		new BumerangueServiceImpl().editarAtributosAuditoria(bd, usuarioLogado, false);
	}
	
	public void excluirReferenciaMissal(Usuario usuarioLogado, ReferenciaMissal referenciaMissal){
		referenciaMissal = referenciaMissalDao.get(referenciaMissal.getId());
		referenciaMissalDao.delete(referenciaMissal);
	}

	public List<PlanoMissa> findPlanosMissa(Usuario usuarioLogado, String anoMes, String idSituacao){
		return planoMissaDao.findPlanosMissa(anoMes, idSituacao);
	}

	public PlanoMissa getPlanoMissa(Usuario usuarioLogado, String idPlanoMissa){
		PlanoMissa planoMissa = planoMissaDao.get(idPlanoMissa);

		//seta os idiomas cadastrados 
		if(planoMissa != null){
			Dominio edicaoIdioma = dominioDao.get(Dominio.FICHA_MISSA_EDICAO_IDIOMA_MISSAL.getCodigo());
			planoMissa.setEdicoesIdiomasOrdenados(edicaoIdioma.getElementosDominio());
		}
			
		return planoMissa;
	}
	
	public Missa getMissa(Usuario usuarioLogado, String idMissa){
		Missa missa = planoMissaDao.getMissa(idMissa);

		//seta os idiomas cadastrados 
		if(missa != null){
			Dominio edicaoIdioma = dominioDao.get(Dominio.FICHA_MISSA_EDICAO_IDIOMA_MISSAL.getCodigo());
			missa.getPlanoMissa().setEdicoesIdiomasOrdenados(edicaoIdioma.getElementosDominio());
		}

		return missa;
	}

	public void inserirPlanoMissa(Usuario usuarioLogado, PlanoMissa planoMissa){
		//valida ano/mês no formato: yyyymm
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
		dateFormat.setLenient(false);
		try{
			dateFormat.parse(planoMissa.getAnoMes());
		}catch (Exception e) {
			throw new BumerangueErrorRuntimeException(planoMissa.getAnoMes()+" deve estar no formato aaaamm.");
		}
		//verifica se já existe algum plano de missa com o mesmo ano/mês
		if(planoMissaDao.getPorAnoMes(planoMissa.getAnoMes()) != null){
			throw new BumerangueErrorRuntimeException("Já existe um Plano de Missa para o Ano/Mês: "+planoMissa.getAnoMesFormatado());
		}
		
		//carrega a lista de missas para os dias do mês
		List<Missa> missas = this.getMissasParaMes(planoMissa.getAnoMes());
		
		ElementoDominio situacaoEmAprovacao = dominioDao.getElementoDominio(Dominio.FICHA_MISSA_SITUACAO_PLANO_MISSA.getCodigo(), ElementoDominio.FICHA_MISSA_SITUACAO_PLANO_MISSA_EM_APROVACAO.getCodigo());
		
		planoMissa.setMissas(missas);
		planoMissa.setSituacao(situacaoEmAprovacao);
		planoMissaDao.saveOrUpdate(planoMissa);
	}
	
	private List<Missa> getMissasParaMes(String anoMes){
		int ano = Integer.parseInt(anoMes.substring(0,4));
		int mes = Integer.parseInt(anoMes.substring(4,anoMes.length()));
		Calendar calendar = Calendar.getInstance();
		calendar.set(ano, mes-1, 1);
		int ultimoDia = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); 
		
		List<Missa> missasParaMes = new ArrayList<Missa>();
		for (int i = 1; i <= ultimoDia; i++) {
			Missa missa = this.getMissaParaDia(i, mes, ano);
			missasParaMes.add(missa);
		}
		
		return missasParaMes;
	}
	
	private Missa getMissaParaDia(int dia, int mes, int ano){
//		LiturgiaPalavra liturgiaPalavra = (LiturgiaPalavra)grupoComponenteMissaDao.get("4028808f160055d90116006a62110020");
//		Oracao oracao = (Oracao)grupoComponenteMissaDao.get("4028808f160055d90116006c9e500025");
		LiturgiaPalavra liturgiaPalavra = null;
		Oracao oracao = null;

		Missa missa = new Missa();
		missa.setDia(dia);
		missa.setLiturgiaPalavra(liturgiaPalavra);
		missa.setOracao(oracao);
		
		return missa;
	}
	
	
	public void editarPlanoMissa(Usuario usuarioLogado, List<String> listaIdMissa_chaveLiturgiaPalavra, List<String> listaIdMissa_chaveOracao){
		//atualiza as chaves das LiturgiaPalavras
		for(String idMissa_chaveLiturgiaPalavra : listaIdMissa_chaveLiturgiaPalavra){
			String idMissa = idMissa_chaveLiturgiaPalavra.split("_")[0];
			Missa missa = planoMissaDao.getMissa(idMissa);

			//se não for informado a chave, seta null
			if(idMissa_chaveLiturgiaPalavra.endsWith("_")){
				missa.setLiturgiaPalavra(null);
				continue;
			}

			String chave = idMissa_chaveLiturgiaPalavra.split("_")[1];
			LiturgiaPalavra liturgiaPalavra = grupoComponenteMissaDao.getLiturgiaPalavraPorChave(chave);
			missa.setLiturgiaPalavra(liturgiaPalavra);
		}

		//atualiza as chaves das Orações
		for(String idMissa_chaveOracao : listaIdMissa_chaveOracao){
			String idMissa = idMissa_chaveOracao.split("_")[0];
			Missa missa = planoMissaDao.getMissa(idMissa);

			//se não for informado a chave, seta null
			if(idMissa_chaveOracao.endsWith("_")){
				missa.setOracao(null);
				continue;
			}

			String chave = idMissa_chaveOracao.split("_")[1];
			Oracao oracao = grupoComponenteMissaDao.getOracaoPorChave(chave);
			missa.setOracao(oracao);
		}
	}
	
	public void excluirPlanoMissa(Usuario usuarioLogado, PlanoMissa planoMissa){
		planoMissa = planoMissaDao.get(planoMissa.getId());
		planoMissaDao.delete(planoMissa);
	}

	public void aprovarPlanoMissa(Usuario usuarioLogado, PlanoMissa planoMissa){
		planoMissa = planoMissaDao.get(planoMissa.getId());

		//só aprova, se estiver 'em aprovação'
		if(!planoMissa.isEmAprovacao()){
			throw new BumerangueErrorRuntimeException("O plano de missa deve estar 'em aprovação'.");
		}
		
		//só aprova, se existir o arquivo do Plano de Missa
		if(!this.existeArquivoPlanoMissa(planoMissa)){
			throw new BumerangueErrorRuntimeException("O arquivo do plano de Missa não foi encontrado em: "+this.getPathRelativoArquivoPlanoMissa(planoMissa));
		}

		//verifica se o Plano é valido
		if(!planoMissa.isValido()){
			throw new BumerangueErrorRuntimeException(planoMissa.getErrosValidacao());
		}
		
		ElementoDominio situacaoAprovado = dominioDao.getElementoDominio(Dominio.FICHA_MISSA_SITUACAO_PLANO_MISSA.getCodigo(), ElementoDominio.FICHA_MISSA_SITUACAO_PLANO_MISSA_APROVADO.getCodigo());
		planoMissa.setSituacao(situacaoAprovado);
		
		mensagemService.enviarMailAprovacaoPlanoMissa(planoMissa);
	}

	public void alterarPlanoMissaParaEmAprovacao(Usuario usuarioLogado, PlanoMissa planoMissa){
		planoMissa = planoMissaDao.get(planoMissa.getId());

		//só faz, se estiver 'aprovado'
		if(!planoMissa.isAprovado()){
			throw new BumerangueErrorRuntimeException("O plano de missa deve estar 'aprovado'.");
		}
		
		ElementoDominio situacaoAprovado = dominioDao.getElementoDominio(Dominio.FICHA_MISSA_SITUACAO_PLANO_MISSA.getCodigo(), ElementoDominio.FICHA_MISSA_SITUACAO_PLANO_MISSA_EM_APROVACAO.getCodigo());
		planoMissa.setSituacao(situacaoAprovado);
	}

	public Dominio getDominio(Usuario usuarioLogado, Integer codigo){
		return dominioDao.get(codigo);
	}
	
	public boolean existeArquivoPlanoMissa(PlanoMissa planoMissa){
		String pathArquivoPlanoMissa = this.getPathArquivoPlanoMissa(planoMissa);
		return new File(pathArquivoPlanoMissa).isFile();
	}

	public String getPathRelativoArquivoPlanoMissa(PlanoMissa planoMissa){
		return super.getPathRelativoDiretorioArquivos(Permissao.BASICO_FICHA_MISSA)+"/plano_"+planoMissa.getAnoMes()+".zip";
	}

	private String getPathArquivoPlanoMissa(PlanoMissa planoMissa){
		return super.getPathDiretorioArquivos(Permissao.BASICO_FICHA_MISSA)+"/plano_"+planoMissa.getAnoMes()+".zip";
	}
}