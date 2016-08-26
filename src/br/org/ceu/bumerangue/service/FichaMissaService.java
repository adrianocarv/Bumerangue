package br.org.ceu.bumerangue.service;

import java.util.List;

import br.org.ceu.bumerangue.entity.ComponenteMissa;
import br.org.ceu.bumerangue.entity.Dominio;
import br.org.ceu.bumerangue.entity.GrupoComponenteMissa;
import br.org.ceu.bumerangue.entity.LiturgiaPalavra;
import br.org.ceu.bumerangue.entity.Missa;
import br.org.ceu.bumerangue.entity.Oracao;
import br.org.ceu.bumerangue.entity.PlanoMissa;
import br.org.ceu.bumerangue.entity.Prefacio;
import br.org.ceu.bumerangue.entity.ReferenciaMissal;
import br.org.ceu.bumerangue.entity.Usuario;

public interface FichaMissaService extends BaseService {

	public List<LiturgiaPalavra> findLiturgiasPalavra(Usuario usuarioLogado, String chave);

	public List<Oracao> findOracoes(Usuario usuarioLogado, String chave);
	
	public GrupoComponenteMissa getGrupoComponenteMissa(Usuario usuarioLogado, String idGrupoComponenteMissa);
	
	public List<Oracao> findOracoesVotivas(Usuario usuarioLogado);
	
	public void inserirGrupoComponenteMissa(Usuario usuarioLogado, GrupoComponenteMissa grupoComponenteMissa);
	
	public void editarGrupoComponenteMissa(Usuario usuarioLogado, GrupoComponenteMissa grupoComponenteMissa);
	
	public void excluirGrupoComponenteMissa(Usuario usuarioLogado, GrupoComponenteMissa grupoComponenteMissa);

	public ComponenteMissa getComponenteMissa(Usuario usuarioLogado, String idComponenteMissa);
	
	public List<Prefacio> findPrefaciosCompartilhados(Usuario usuarioLogado);

	public void inserirComponenteMissa(Usuario usuarioLogado, ComponenteMissa componenteMissa);
	
	public void editarComponenteMissa(Usuario usuarioLogado, ComponenteMissa componenteMissa);
	
	public void excluirComponenteMissa(Usuario usuarioLogado, ComponenteMissa componenteMissa);

	public ReferenciaMissal getReferenciaMissal(Usuario usuarioLogado, String idReferenciaMissal);
	
	public void inserirReferenciaMissal(Usuario usuarioLogado, ReferenciaMissal referenciaMissal);
	
	public void editarReferenciaMissal(Usuario usuarioLogado, ReferenciaMissal referenciaMissal);
	
	public void excluirReferenciaMissal(Usuario usuarioLogado, ReferenciaMissal referenciaMissal);

	public List<PlanoMissa> findPlanosMissa(Usuario usuarioLogado, String anoMes, String idSituacao);

	public PlanoMissa getPlanoMissa(Usuario usuarioLogado, String idPlanoMissa);
	
	public Missa getMissa(Usuario usuarioLogado, String idMissa);

	public void inserirPlanoMissa(Usuario usuarioLogado, PlanoMissa planoMissa);
	
	public void editarPlanoMissa(Usuario usuarioLogado, List<String> listaIdMissa_chaveLiturgiaPalavra, List<String> listaIdMissa_chaveOracao);
	
	public void excluirPlanoMissa(Usuario usuarioLogado, PlanoMissa planoMissa);

	public void aprovarPlanoMissa(Usuario usuarioLogado, PlanoMissa planoMissa);

	public void alterarPlanoMissaParaEmAprovacao(Usuario usuarioLogado, PlanoMissa planoMissa);

	public Dominio getDominio(Usuario usuarioLogado, Integer codigo);
	
	public boolean existeArquivoPlanoMissa(PlanoMissa planoMissa);

	public String getPathRelativoArquivoPlanoMissa(PlanoMissa planoMissa);
	
}
