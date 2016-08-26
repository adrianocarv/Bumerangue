package br.org.ceu.bumerangue.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import br.org.ceu.bumerangue.entity.Dominio;
import br.org.ceu.bumerangue.entity.Emprestimo;
import br.org.ceu.bumerangue.entity.ObjetoBumerangue;
import br.org.ceu.bumerangue.entity.Permissao;
import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.entity.criterias.PesquisaFilmeComercialCriteria;
import br.org.ceu.bumerangue.entity.criterias.PesquisaHistoricoLivroCulturalCriteria;
import br.org.ceu.bumerangue.entity.criterias.PesquisaHistoricoVideoCriteria;
import br.org.ceu.bumerangue.entity.criterias.PesquisaLivroCulturalCriteria;
import br.org.ceu.bumerangue.entity.criterias.PesquisaUsuarioCriteria;
import br.org.ceu.bumerangue.entity.criterias.PesquisaVideoCriteria;
import br.org.ceu.bumerangue.entity.suporte.Arquivo;

public interface BumerangueService extends BaseService {
 
	public Usuario verificarLogin(String nome);

	public int registrarErroLogin(String nome);

	public void trocarSenhaUsuario(Usuario usuarioLogado, String senhaAtual, String novaSenha, String novaSenhaConfirma);
	 
	public void reiniciarSenhaUsuario(Usuario usuarioLogado, Usuario usuario);

	public void desbloquearUsuario(Usuario usuarioLogado, Usuario usuario);

	public Usuario lembrarSenha(String nomeUsuario, String emailUsuario);

	public Usuario lembrarSenhaConfirmacao(String idUsuario, String senhaCript);

	public void comentarSugerir(Usuario usuarioLogado, String nomeRemetente, String emailRemetente, String textoComentario);

	public Usuario getUsuarioPorNome(String nome);
	 
	public List findUsuarios(Usuario usuarioLogado, Class classeUsuario, boolean isAtivo, Permissao permissao);

	public List pesquisarVideos(Usuario usuarioLogado, PesquisaVideoCriteria pesquisaVideoCriteria);
	 
	public void inserirObjetoBumerangue(Usuario usuarioLogado, ObjetoBumerangue objetoBumerangue);
	 
	public ObjetoBumerangue copiarObjetoBumerangue(Usuario usuarioLogado, ObjetoBumerangue objetoBumerangue);

	public void editarObjetoBumerangue(Usuario usuarioLogado, ObjetoBumerangue objetoBumerangue);
	 
	public void excluirObjetoBumerangue(Usuario usuarioLogado, ObjetoBumerangue objetoBumerangue);
	 
	public ObjetoBumerangue getObjetoBumerangue(Usuario usuarioLogado, String idObjetoBumerangue);
	 
	public String getProximoCodigoVideo();

	public void reservarObjetoBumerangue(Usuario usuarioLogado, ObjetoBumerangue objetoBumerangue, Emprestimo reserva);
	 
	public void emprestarObjetoBumerangue(Usuario usuarioLogado, ObjetoBumerangue objetoBumerangue, Emprestimo emprestimo);
	 
	public void devolverObjetoBumerangue(Usuario usuarioLogado, ObjetoBumerangue objetoBumerangue, Emprestimo devolucao);
	 
	public void cancelarReservaObjetoBumerangue(Usuario usuarioLogado, ObjetoBumerangue objetoBumerangue);
	 
	public void cancelarEmprestimoObjetoBumerangue(Usuario usuarioLogado, ObjetoBumerangue objetoBumerangue);
	 
	public void editarDevolucaoObjetoBumerangue(Usuario usuarioLogado, Emprestimo devolucao);

	public void cancelarDevolucaoObjetoBumerangue(Usuario usuarioLogado, Emprestimo devolucao);

	public List<Emprestimo> pesquisarHistoricoEmprestimosVideo(Usuario usuarioLogado, PesquisaHistoricoVideoCriteria pesquisaHistoricoVideoCriteria);
	 
	public List pesquisarEstatisticaEmprestimosVideo(Usuario usuarioLogado, PesquisaHistoricoVideoCriteria pesquisaHistoricoVideoCriteria);
	 
	public List<Emprestimo> pesquisarHistoricoEmprestimosLivroCultural(Usuario usuarioLogado, PesquisaHistoricoLivroCulturalCriteria pesquisaHistoricoLivroCulturalCriteria);

	public Emprestimo getEmprestimo(Usuario usuarioLogado, String idEmprestimo);

	public void inserirUsuario(Usuario usuarioLogado, Usuario usuario);
	 
	public void editarUsuario(Usuario usuarioLogado, Usuario usuario);
	 
	public void excluirUsuario(Usuario usuarioLogado, Usuario usuario);
	 
	public Usuario getUsuario(Usuario usuarioLogado, String idUsuario);
	 
	public Dominio getDominio(Usuario usuarioLogado, Integer codigo);

	public List pesquisarFilmesComerciais(Usuario usuarioLogado, PesquisaFilmeComercialCriteria pesquisaFilmeComercialCriteria);

	public String getProximoCodigoFilmeComercial();
	
	public List pesquisarLivrosCulturais(Usuario usuarioLogado, PesquisaLivroCulturalCriteria pesquisaLivroCulturalCriteria);

	public List<Usuario> pesquisarUsuarios(Usuario usuarioLogado, PesquisaUsuarioCriteria pesquisaUsuarioCriteria);

	public List<Usuario> pesquisarUsuariosCompartilhados(Usuario usuarioLogado, PesquisaUsuarioCriteria pesquisaUsuarioCriteria, String nomeModulo);

	public List<Arquivo> listarArquivos(Usuario usuarioLogado);

	public void uploadArquivo(Usuario usuarioLogado, String nomeModulo, Integer codigoTipoPermissao, String nomeArquivo, MultipartFile uploadFile);
	
	public void excluirArquivo(Usuario usuarioLogado, String numeroPermissao, String nomeArquivo, boolean compartilhadoTodasPermissoes);
	
	public Map<String, String> getNotificacoes(Usuario usuarioLogado);
}
 
