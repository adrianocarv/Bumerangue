package br.org.ceu.bumerangue.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.org.ceu.bumerangue.dao.DominioDao;
import br.org.ceu.bumerangue.dao.EmprestimoDao;
import br.org.ceu.bumerangue.dao.ObjetoBumerangueDao;
import br.org.ceu.bumerangue.dao.PermissaoDao;
import br.org.ceu.bumerangue.dao.UsuarioDao;
import br.org.ceu.bumerangue.entity.BaseEntity;
import br.org.ceu.bumerangue.entity.Dominio;
import br.org.ceu.bumerangue.entity.ElementoDominio;
import br.org.ceu.bumerangue.entity.Emprestimo;
import br.org.ceu.bumerangue.entity.FilmeComercial;
import br.org.ceu.bumerangue.entity.LivroCultural;
import br.org.ceu.bumerangue.entity.ObjetoBumerangue;
import br.org.ceu.bumerangue.entity.Permissao;
import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.entity.Video;
import br.org.ceu.bumerangue.entity.criterias.PesquisaFilmeComercialCriteria;
import br.org.ceu.bumerangue.entity.criterias.PesquisaHistoricoLivroCulturalCriteria;
import br.org.ceu.bumerangue.entity.criterias.PesquisaHistoricoVideoCriteria;
import br.org.ceu.bumerangue.entity.criterias.PesquisaLivroCulturalCriteria;
import br.org.ceu.bumerangue.entity.criterias.PesquisaUsuarioCriteria;
import br.org.ceu.bumerangue.entity.criterias.PesquisaVideoCriteria;
import br.org.ceu.bumerangue.entity.suporte.Arquivo;
import br.org.ceu.bumerangue.entity.suporte.deploy.Deploy;
import br.org.ceu.bumerangue.service.BumerangueService;
import br.org.ceu.bumerangue.service.MensagemService;
import br.org.ceu.bumerangue.service.exceptions.BumerangueAlertRuntimeException;
import br.org.ceu.bumerangue.service.exceptions.BumerangueErrorRuntimeException;
import br.org.ceu.bumerangue.util.DateUtil;
import br.org.ceu.bumerangue.util.MD5;
import br.org.ceu.bumerangue.util.Utils;
import br.org.ceu.bumerangue.util.ValidationRules;

public class BumerangueServiceImpl extends BaseServiceImpl implements BumerangueService {
    private MensagemService mensagemService;
    public void setMensagemService(MensagemService mensagemService) { this.mensagemService = mensagemService;}

    private EmprestimoDao emprestimoDao;
    public void setEmprestimoDao(EmprestimoDao emprestimoDao) { this.emprestimoDao = emprestimoDao; }
 
    private ObjetoBumerangueDao objetoBumerangueDao;
    public void setObjetoBumerangueDao(ObjetoBumerangueDao objetoBumerangueDao) { this.objetoBumerangueDao = objetoBumerangueDao; }

    private PermissaoDao permissaoDao;
    public void setPermissaoDao(PermissaoDao permissaoDao) { this.permissaoDao = permissaoDao; }

    private UsuarioDao usuarioDao;
    public void setUsuarioDao(UsuarioDao usuarioDao) { this.usuarioDao = usuarioDao; }

    private DominioDao dominioDao;
    public void setDominioDao(DominioDao dominioDao) { this.dominioDao = dominioDao; }

	public Usuario verificarLogin(String nome){
    	Usuario usuario = usuarioDao.getPorNome(nome);
		
		//o usuário deve está ativo e desbloqueado
		if(!usuario.getAtivo().booleanValue()) throw new BumerangueErrorRuntimeException("Este usuário está desativado.");
		if(usuario.getBloqueado().booleanValue()) throw new BumerangueErrorRuntimeException("Este usuário está bloqueado."); 

		//autenticação conferida, zera o número de tentativas com erradas.
		usuario.setNumeroTentativasErradas(new Integer(0));
		
		return usuario;
	}

	public int registrarErroLogin(String nome){
		Usuario usuario = usuarioDao.getPorNome(nome);

		//se a tentativa de login errada for com um usuário válido,
		//incrementa o número de tentativas com erro.
		//E se atingiu o número de tentativas com erro, bloqueia o usuário. 
		if(usuario != null){
			int erros = usuario.getNumeroTentativasErradas().intValue() + 1;
			if(erros >= Integer.parseInt(Utils.getResourceMessage("bmg.manterSeguranca.numeroMaximoTentativasErradas"))){
				usuario.setBloqueado(Boolean.TRUE);

				mensagemService.enviarMailBloqueioUsuario(usuario);
				return 1;
			}else{
				usuario.setNumeroTentativasErradas(new Integer(erros));
				return 2;
			}
		}else{
			return 3;
		}
	}

	public void trocarSenhaUsuario(Usuario usuarioLogado, String senhaAtual, String novaSenha, String novaSenhaConfirma) {
		usuarioLogado = usuarioDao.get(usuarioLogado.getId());
		
		//a senha atual informada deve conferir com a senha do usuário
		if(!usuarioLogado.getSenha().equals(MD5.crypt(senhaAtual))) throw new BumerangueErrorRuntimeException("A senha atual não confere.");
		
		//a nova senha deve conferir com a confirmação
		if(!novaSenha.equals(novaSenhaConfirma)) throw new BumerangueErrorRuntimeException("A confirmação da nova senha não confere.");
		
		usuarioLogado.setSenhaCript(novaSenha);

		//seta os atributos de auditoria		
		editarAtributosAuditoria(usuarioLogado, usuarioLogado, false);
	}
	 
	public void reiniciarSenhaUsuario(Usuario usuarioLogado, Usuario usuario) {
		usuario = usuarioDao.get(usuario.getId());
		usuario.setSenhaCript(Utils.getResourceMessage("bmg.manterSeguranca.senhaPadrao"));

		//seta os atributos de auditoria		
		editarAtributosAuditoria(usuario, usuarioLogado, false);

		mensagemService.enviarMailReinicioSenha(usuario);
	}

	public void desbloquearUsuario(Usuario usuarioLogado, Usuario usuario){
		usuario = usuarioDao.get(usuario.getId());
		usuario.setBloqueado(Boolean.FALSE);
		usuario.setNumeroTentativasErradas(new Integer(0));

		//seta os atributos de auditoria		
		editarAtributosAuditoria(usuario, usuarioLogado, false);

		mensagemService.enviarMailDesbloqueioUsuario(usuario);
	}

	public Usuario lembrarSenha(String nomeUsuario, String emailUsuario){
		Usuario usuario = null;

		//valida nomeUsuario
		if(ValidationRules.isInformed(nomeUsuario)){
			usuario = usuarioDao.getPorNome(nomeUsuario);
			if(usuario == null)
				throw new BumerangueErrorRuntimeException("Usuário não encontrado com nome: "+nomeUsuario);
			if(ValidationRules.isInformed(emailUsuario) && !emailUsuario.equals(usuario.getEmail()))
				throw new BumerangueErrorRuntimeException("O usuário '"+usuario.getNome()+"' não está cadastrado com o e-mail '"+emailUsuario+"'.");
				
		//valida emailUsuario
		}else if(ValidationRules.isInformed(emailUsuario)){
			PesquisaUsuarioCriteria criteria = new PesquisaUsuarioCriteria();
			criteria.setEmail(emailUsuario);
			List<Usuario> usuarios = usuarioDao.findUsuarios(criteria);
			if(usuarios.size() == 0)
				throw new BumerangueErrorRuntimeException("Não existe nenhum usuário cadastrado com o email '"+emailUsuario+"'.");
			else if(usuarios.size() > 1){
				String nomes = "";
				for(Usuario usuario2 : usuarios){
					nomes += "<br>"+usuario2.getNome();
				}
				String msg = "O email '"+emailUsuario+"' está definido para os "+usuarios.size()+" seguintes usuários:";
				msg += nomes + "<br>Informe de qual usuário você deseja receber uma nova senha.";
				throw new BumerangueErrorRuntimeException(msg);
			}else{
				usuario = usuarios.get(0);
			}
		}

		mensagemService.enviarMailLembreteSenha(usuario);
		return usuario;
	}

	public Usuario lembrarSenhaConfirmacao(String idUsuario, String senhaCript){
		Usuario usuario = usuarioDao.get(idUsuario);

		if(!usuario.getSenha().equals(senhaCript))
			throw new BumerangueAlertRuntimeException("A senha do usuário '"+usuario.getNome()+"' agora está diferente de quando foi solicitada uma nova senha.<br>Utilize a senha atual para entrar no sistema, ou solicite outra, por meio da função \"esqueci minha senha\" acessível pela tela de login.");

		this.reiniciarSenhaUsuario(null,usuario);
		
		return usuario;
	}

	public void comentarSugerir(Usuario usuarioLogado, String nomeRemetente, String emailRemetente, String textoComentario){
		mensagemService.enviarMailComentarioSugestao(usuarioLogado, nomeRemetente, emailRemetente, textoComentario);
	}

	public Usuario getUsuarioPorNome(String nome){
		return usuarioDao.getPorNome(nome);
	}

	public List findUsuarios(Usuario usuarioLogado, Class classeUsuario, boolean isAtivo, Permissao permissao){
		PesquisaUsuarioCriteria helper = new PesquisaUsuarioCriteria();
		if(classeUsuario != null)helper.setClasseUsuario(classeUsuario);
		helper.setAtivo(new Boolean(isAtivo));
		helper.setNomePermissao(permissao.getNome());
		return usuarioDao.findUsuarios(helper);
	}

	public List pesquisarVideos(Usuario usuarioLogado, PesquisaVideoCriteria pesquisaVideoCriteria) {
		return objetoBumerangueDao.findVideos(pesquisaVideoCriteria);
	}
	 
	public void inserirObjetoBumerangue(Usuario usuarioLogado, ObjetoBumerangue objetoBumerangue) {
		objetoBumerangue.setId(null);
		
		if (objetoBumerangue instanceof Video) {
			this.inserirVideo(usuarioLogado,(Video)objetoBumerangue);
		}else if (objetoBumerangue instanceof FilmeComercial) {
			this.inserirFilmeComercial(usuarioLogado,(FilmeComercial)objetoBumerangue);
		}else if (objetoBumerangue instanceof LivroCultural){
			this.inserirLivroCultural(usuarioLogado, (LivroCultural)objetoBumerangue);
		}
	}
	 
	private void inserirVideo(Usuario usuarioLogado, Video video) {
		//verifica se existe outro vídeo com o mesmo código.
		Video videoUnico = objetoBumerangueDao.getVideoPorCodigo(video.getCodigo());
		if(videoUnico != null)
			throw new BumerangueErrorRuntimeException("Já existe outro vídeo com o código: "+video.getCodigo()+".");

		//trata os domínios
		if(video.getCategoria() == null || !ValidationRules.isInformed(video.getCategoria().getId()) ) video.setCategoria(null);
		if(video.getMidia() == null || !ValidationRules.isInformed(video.getMidia().getId())) video.setMidia(null);
		if(video.getObservacoes() == null || !ValidationRules.isInformed(video.getObservacoes().getId())) video.setObservacoes(null);

		//seta os atributos de auditoria		
		editarAtributosAuditoria(video, usuarioLogado, true);

		objetoBumerangueDao.saveOrUpdate(video);
	}

	private void inserirFilmeComercial(Usuario usuarioLogado, FilmeComercial filmeComercial) {
		//verifica se existe outro filme com o mesmo código.
		FilmeComercial filmeComercialUnico = objetoBumerangueDao.getFilmeComercialPorCodigo(filmeComercial.getCodigo());
		if(filmeComercialUnico != null)
			throw new BumerangueErrorRuntimeException("Já existe outro filme com o código: "+filmeComercial.getCodigo()+".");

		//trata os domínios
		if(filmeComercial.getPublico() == null || !ValidationRules.isInformed(filmeComercial.getPublico().getId()) ) filmeComercial.setPublico(null);
		if(filmeComercial.getCategoria() == null || !ValidationRules.isInformed(filmeComercial.getCategoria().getId())) filmeComercial.setCategoria(null);

		//seta os atributos de auditoria		
		editarAtributosAuditoria(filmeComercial, usuarioLogado, true);

		//manualmente, pois o editarAtributosAuditoria está com problema 
		filmeComercial.setDataCriacao(new Date());
		
		objetoBumerangueDao.saveOrUpdate(filmeComercial);
	}
	
	private void inserirLivroCultural(Usuario usuarioLogado, LivroCultural livroCultural){
		//verifica se existe outro livro com o mesmo código
		LivroCultural livroCulturalUnico = objetoBumerangueDao.getLivroCulturalPorCodigo(livroCultural.getCodigo());
		if(livroCulturalUnico != null)
			throw new BumerangueErrorRuntimeException("Já existe outro livro com o código: "+livroCultural.getCodigo()+".");
		
		//seta os atributos de auditoria
		editarAtributosAuditoria(livroCultural, usuarioLogado, true);
		
		objetoBumerangueDao.saveOrUpdate(livroCultural);
	}

	public ObjetoBumerangue copiarObjetoBumerangue(Usuario usuarioLogado, ObjetoBumerangue objetoBumerangue) {
		objetoBumerangue = objetoBumerangueDao.get(objetoBumerangue.getId());
		ObjetoBumerangue novoObjetoBumerangue = new ObjetoBumerangue();
		
		if (objetoBumerangue instanceof LivroCultural){
			LivroCultural novoL = new LivroCultural();
			objetoBumerangueDao.saveOrUpdate(novoL);
			this.editarLivroCultural(usuarioLogado, (LivroCultural)objetoBumerangue, novoL);
			novoL.setTitulo(objetoBumerangue.getTitulo()+" (cópia) ");
			novoObjetoBumerangue=novoL;
		}
		if (objetoBumerangue instanceof Video) {
			Video novo = new Video();
			objetoBumerangueDao.saveOrUpdate(novo);
			this.editarVideo(usuarioLogado,(Video)objetoBumerangue,novo);
			long proximoCodigo = new Long(objetoBumerangueDao.maxAttribute(Video.class,"codigo")).longValue() + 1;
			novo.setCodigoFormatado(proximoCodigo+"");
			novo.setTitulo(novo.getTitulo()+" (cópia)");
			novoObjetoBumerangue = novo;
		}

		//seta os atributos de auditoria		
		editarAtributosAuditoria(novoObjetoBumerangue, usuarioLogado, true);

		return novoObjetoBumerangue; 
	}

	public void editarObjetoBumerangue(Usuario usuarioLogado, ObjetoBumerangue objetoBumerangue) {
		ObjetoBumerangue bd = objetoBumerangueDao.get(objetoBumerangue.getId());
		
		//caso a alteração seja no campo foraUso para true, só permite se não tiver emprestado, nem reservado.
		if(objetoBumerangue.getForaUso().booleanValue() && (bd.getIsEmprestado() || bd.getIsReservado()))
			throw new BumerangueErrorRuntimeException("Objeto não pode ficar fora de uso, quando está emprestado ou reservado.");
		
		if (objetoBumerangue instanceof Video) {
			this.editarVideo(usuarioLogado,(Video)objetoBumerangue,(Video)bd);
		}else if (objetoBumerangue instanceof FilmeComercial) {
			this.editarFilmeComercial(usuarioLogado,(FilmeComercial)objetoBumerangue,(FilmeComercial)bd);
		}else if (objetoBumerangue instanceof LivroCultural) {
			this.editarLivroCultural(usuarioLogado, (LivroCultural)objetoBumerangue, (LivroCultural)bd);
		}

		//seta os atributos de auditoria		
		editarAtributosAuditoria(bd, usuarioLogado, false);
	}
	
	private void editarVideo(Usuario usuarioLogado, Video video, Video videoBD) {
		//verifica se existe outro vídeo com o mesmo código.
		Video videoUnico = objetoBumerangueDao.getVideoPorCodigo(video.getCodigo());
		if(videoUnico != null && !videoUnico.getId().equals(video.getId()))
			throw new BumerangueErrorRuntimeException("Já existe outro vídeo com o código: "+video.getCodigo()+".");

		videoBD.setTitulo(video.getTitulo());
		videoBD.setCodigoFormatado(video.getCodigo());
		if(video.getCategoria() != null && ValidationRules.isInformed(video.getCategoria().getId()))videoBD.setCategoria(video.getCategoria());
		else videoBD.setCategoria(null);
		videoBD.setCodigoCaixa(video.getCodigoCaixa());
		videoBD.setLocalidade(video.getLocalidade());
		videoBD.setData(video.getData());
		videoBD.setLegendado(video.getLegendado());
		videoBD.setDublado(video.getDublado());
		videoBD.setPublico(video.getPublico());
		videoBD.setDuracaoMinutos(video.getDuracaoMinutos());
		if(video.getMidia() != null && ValidationRules.isInformed(video.getMidia().getId())) videoBD.setMidia(video.getMidia());
		else videoBD.setMidia(null);
		videoBD.setPalavrasChaves(video.getPalavrasChaves());
		if(video.getObservacoes() != null && ValidationRules.isInformed(video.getObservacoes().getId())) videoBD.setObservacoes(video.getObservacoes());
		else videoBD.setObservacoes(null);
		videoBD.setObservacoesGerais(video.getObservacoesGerais());
		videoBD.setLocalizacaoPI(video.getLocalizacaoPI());
		videoBD.setForaUso(video.getForaUso());
	} 
	
	private void editarLivroCultural(Usuario usuarioLogado, LivroCultural livroCultural, LivroCultural livroCulturalBD) {
		//verifica se existe outro vídeo com o mesmo código.
		LivroCultural livroCulturalUnico = objetoBumerangueDao.getLivroCulturalPorCodigo(livroCultural.getCodigo());
		if(livroCulturalUnico != null && !livroCulturalUnico.getId().equals(livroCultural.getId()))
			throw new BumerangueErrorRuntimeException("Já existe outro livro cultural com o código: "+livroCultural.getCodigo()+".");

		livroCulturalBD.setCodigo(livroCultural.getCodigo());
		livroCulturalBD.setTitulo(livroCultural.getTitulo());
		livroCulturalBD.setAutor(livroCultural.getAutor());
		if(livroCultural.getIdioma() != null && ValidationRules.isInformed(livroCultural.getIdioma().getId())) livroCulturalBD.setIdioma(livroCultural.getIdioma());
		else livroCulturalBD.setIdioma(null);
		livroCulturalBD.setPalavrasChaves(livroCultural.getPalavrasChaves());
		livroCulturalBD.setObservacoesGerais(livroCultural.getObservacoesGerais());
		livroCulturalBD.setForaUso(livroCultural.getForaUso());
	}

	private void editarFilmeComercial(Usuario usuarioLogado, FilmeComercial filmeComercial, FilmeComercial filmeComercialBD) {
		//verifica se existe outro filme com o mesmo código.
		FilmeComercial filmeComercialUnico = objetoBumerangueDao.getFilmeComercialPorCodigo(filmeComercial.getCodigo());
		if(filmeComercialUnico != null && !filmeComercialUnico.getId().equals(filmeComercial.getId()))
			throw new BumerangueErrorRuntimeException("Já existe outro filme com o código: "+filmeComercial.getCodigo()+".");

		filmeComercialBD.setTitulo(filmeComercial.getTitulo());
		filmeComercialBD.setCodigoFormatado(filmeComercial.getCodigo());
		filmeComercialBD.setTituloOriginal(filmeComercial.getTituloOriginal());
		if(filmeComercial.getPublico() != null && ValidationRules.isInformed(filmeComercial.getPublico().getId()))filmeComercialBD.setPublico(filmeComercial.getPublico());
		else filmeComercialBD.setPublico(null);
		filmeComercialBD.setDuracaoMinutos(filmeComercial.getDuracaoMinutos());
		filmeComercialBD.setAno(filmeComercial.getAno());
		if(filmeComercial.getCategoria() != null && ValidationRules.isInformed(filmeComercial.getCategoria().getId()))filmeComercialBD.setCategoria(filmeComercial.getCategoria());
		else filmeComercialBD.setCategoria(null);
		filmeComercialBD.setDiretor(filmeComercial.getDiretor());
		filmeComercialBD.setAtoresObservacoes(filmeComercial.getAtoresObservacoes());
		filmeComercialBD.setLinkSinopse(filmeComercial.getLinkSinopse());
		filmeComercialBD.setLinksDownload(filmeComercial.getLinksDownload());
	}

	public void excluirObjetoBumerangue(Usuario usuarioLogado, ObjetoBumerangue objetoBumerangue) {
		objetoBumerangue = objetoBumerangueDao.get(objetoBumerangue.getId());

		//a exclusão só é permitida se o objeto não tiver emprestado, nem reservado.
		if(objetoBumerangue.getIsEmprestado() || objetoBumerangue.getIsReservado())
			throw new BumerangueErrorRuntimeException("Objeto não pode ser excluído, quando está emprestado ou reservado.");
		
		//se tiver emprétimos realizados, o objeto é colocado como fora de uso, senão é excluído.
		if(!objetoBumerangue.getEmprestimos().isEmpty()){
			objetoBumerangue.setForaUso(Boolean.TRUE);

			//seta os atributos de auditoria		
			editarAtributosAuditoria(objetoBumerangue, usuarioLogado, false);
		}
		else objetoBumerangueDao.delete(objetoBumerangue);
	}
	 
	public ObjetoBumerangue getObjetoBumerangue(Usuario usuarioLogado, String idObjetoBumerangue) {
		return objetoBumerangueDao.getSubclass(idObjetoBumerangue);
	}
	 
	public String getProximoCodigoVideo() {
		long proximoCodigo = new Long(objetoBumerangueDao.maxAttribute(Video.class,"codigo")).longValue() + 1;
		return proximoCodigo+"";
	}

	public void reservarObjetoBumerangue(Usuario usuarioLogado, ObjetoBumerangue objetoBumerangue, Emprestimo reserva) {
		objetoBumerangue = objetoBumerangueDao.get(objetoBumerangue.getId());

		//verifica se está disponível.
		if(!objetoBumerangue.getIsDisponivel()) throw new BumerangueErrorRuntimeException("Objeto não está disponível.");

		//verifica se o usuário é admin ou usuário da reserva
		if(!usuarioLogado.isInRole(ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_ADMIN) && !reserva.getUsuarioEmprestimo().equals(usuarioLogado))
			throw new BumerangueErrorRuntimeException("A reserva s� pode ser feita pelo Administrador ou pelo usuário da reserva.");

		reserva.setDataReserva(new Date());
		reserva.setUsuarioRealizouReserva(usuarioLogado);
		emprestimoDao.saveOrUpdate(reserva);

		objetoBumerangue.setEmprestimoAtual(reserva);
		objetoBumerangue.addEmprestimo(reserva);

		//seta os atributos de auditoria
		//TODO remover este comentário, após consertar o erro: CA fazendo reserva
		//editarAtributosAuditoria(reserva, usuarioLogado, true);
		
		mensagemService.enviarMailReserva(objetoBumerangue);
	}
	 
	public void emprestarObjetoBumerangue(Usuario usuarioLogado, ObjetoBumerangue objetoBumerangue, Emprestimo emprestimo) {
		objetoBumerangue = objetoBumerangueDao.get(objetoBumerangue.getId());

		//verifica se está disponível para empréstimo.
		if(!objetoBumerangue.getIsDisponivel() && !objetoBumerangue.getIsReservado()) throw new BumerangueErrorRuntimeException("Objeto não está disponível.");

		//a data do empréstimo não pode ser depois de hoje
		if(DateUtil.compareTodayDay(emprestimo.getDataEmprestimo()) < 0)
			 throw new BumerangueErrorRuntimeException("A data do empréstimo não pode ser posterior a hoje.");
		
		emprestimo.setUsuarioRealizouEmprestimo(usuarioLogado);

		//se o objeto estiver reservado, seta os valores do empréstimo
		if(objetoBumerangue.getIsReservado()){
			//verifica se o usuário do empréstimo é o mesmo da reserva.
			if(!emprestimo.getUsuarioEmprestimo().equals(objetoBumerangue.getEmprestimoAtual().getUsuarioEmprestimo()))
				throw new BumerangueErrorRuntimeException("O empréstimo deve ser feito para o mesmo usuário que fez a reserva.");

			//a data do empréstimo não pode ser antes da data de reserva 
			if(DateUtil.compareDay(emprestimo.getDataEmprestimo(),objetoBumerangue.getEmprestimoAtual().getDataReserva()) < 0)
				 throw new BumerangueErrorRuntimeException("A data do empréstimo não pode ser anterior à data de reserva.");

			objetoBumerangue.getEmprestimoAtual().setUsuarioEmprestimo(emprestimo.getUsuarioEmprestimo());
			objetoBumerangue.getEmprestimoAtual().setUsuarioRealizouEmprestimo(emprestimo.getUsuarioRealizouEmprestimo());
			objetoBumerangue.getEmprestimoAtual().setDataEmprestimo(emprestimo.getDataEmprestimo());
			objetoBumerangue.getEmprestimoAtual().setObservacoesEmprestimo(emprestimo.getObservacoesEmprestimo());

			//seta os atributos de auditoria		
			//TODO remover este comentário, após consertar o erro: CA fazendo reserva
			//editarAtributosAuditoria(objetoBumerangue.getEmprestimoAtual(), usuarioLogado, false);
		}else{
			//seta os atributos de auditoria		
			//TODO remover este comentário, após consertar o erro: CA fazendo reserva
			//editarAtributosAuditoria(emprestimo, usuarioLogado, true);

			emprestimoDao.saveOrUpdate(emprestimo);

			objetoBumerangue.setEmprestimoAtual(emprestimo);
			objetoBumerangue.addEmprestimo(emprestimo);
		}
	}
	 
	public void devolverObjetoBumerangue(Usuario usuarioLogado, ObjetoBumerangue objetoBumerangue, Emprestimo devolucao) {
		ObjetoBumerangue objetoBumerangueBD = objetoBumerangueDao.get(objetoBumerangue.getId());

		// verifica se está emprestado.
		if(!objetoBumerangueBD.getIsEmprestado()) throw new BumerangueErrorRuntimeException("Objeto deve estar emprestado.");

		// a data da devolução não pode ser depois de hoje, nem antes da data do
		// empréstimo
		if( DateUtil.compareTodayDay(devolucao.getDataDevolucao()) < 0 ||
			DateUtil.compareDay(devolucao.getDataDevolucao(),objetoBumerangueBD.getEmprestimoAtual().getDataEmprestimo()) < 0 )
			 throw new BumerangueErrorRuntimeException("A data da devolução não pode ser posterior a hoje, nem anterior à data do empréstimo.");

		Emprestimo emprestimoAtual = objetoBumerangueBD.getEmprestimoAtual();
		emprestimoAtual.setDataDevolucao(devolucao.getDataDevolucao());
		emprestimoAtual.setUsuarioRealizouDevolucao(usuarioLogado);
		emprestimoAtual.setObservacoesDevolucao(devolucao.getObservacoesDevolucao());
		emprestimoAtual.setTipoAtividadeVideo(devolucao.getTipoAtividadeVideo());
		emprestimoAtual.setPublicoVideo(devolucao.getPublicoVideo());
		emprestimoAtual.setNumeroAssistentesVideo(devolucao.getNumeroAssistentesVideo());
		
		// removo o empréstimo atual
		objetoBumerangueBD.setEmprestimoAtual(null);
		
		// salva os dados no caso de vídeos
		if (objetoBumerangueBD instanceof Video) {
			((Video)objetoBumerangueBD).setObservacoesGerais(((Video)objetoBumerangue).getObservacoesGerais());
			((Video)objetoBumerangueBD).setPalavrasChaves(((Video)objetoBumerangue).getPalavrasChaves());

			//seta os atributos de auditoria		
			//TODO remover este comentário, após consertar o erro: CA fazendo reserva
			//editarAtributosAuditoria(objetoBumerangueBD, usuarioLogado, false);
		}else if (objetoBumerangueBD instanceof LivroCultural) {
			((LivroCultural)objetoBumerangueBD).setObservacoesGerais(((LivroCultural)objetoBumerangue).getObservacoesGerais());
			((LivroCultural)objetoBumerangueBD).setPalavrasChaves(((LivroCultural)objetoBumerangue).getPalavrasChaves());

			//seta os atributos de auditoria		
			//TODO remover este comentário, após consertar o erro: CA fazendo reserva
			//editarAtributosAuditoria(objetoBumerangueBD, usuarioLogado, false);
		}

		//seta os atributos de auditoria		
		//TODO remover este comentário, após consertar o erro: CA fazendo reserva
		//editarAtributosAuditoria(emprestimoAtual, usuarioLogado, false);
	}
	 
	public void cancelarReservaObjetoBumerangue(Usuario usuarioLogado, ObjetoBumerangue objetoBumerangue) {
		objetoBumerangue = objetoBumerangueDao.get(objetoBumerangue.getId());
		usuarioLogado = usuarioDao.get(usuarioLogado.getId());

		//verifica se está reservado.
		if(!objetoBumerangue.getIsReservado()) throw new BumerangueErrorRuntimeException("Objeto deve estar reservado.");
		
		//verifica se o usuário é admin ou quem reservou
		if(!usuarioLogado.isInRole(ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_ADMIN) && !objetoBumerangue.getEmprestimoAtual().getUsuarioEmprestimo().equals(usuarioLogado))
			throw new BumerangueErrorRuntimeException("Cancelamento só pode ser feito pelo Administrador ou por quem fez a reserva.");

		Emprestimo emprestimoAtual = objetoBumerangue.getEmprestimoAtual();
		objetoBumerangue.setEmprestimoAtual(null);
		emprestimoDao.delete(emprestimoAtual);
	}
	 
	public void cancelarEmprestimoObjetoBumerangue(Usuario usuarioLogado, ObjetoBumerangue objetoBumerangue) {
		objetoBumerangue = objetoBumerangueDao.get(objetoBumerangue.getId());

		//verifica se está emprestado.
		if(!objetoBumerangue.getIsEmprestado()) throw new BumerangueErrorRuntimeException("Objeto deve estar emprestado.");
		
		objetoBumerangue.getEmprestimoAtual().setUsuarioRealizouEmprestimo(null);
		objetoBumerangue.getEmprestimoAtual().setDataEmprestimo(null);
		objetoBumerangue.getEmprestimoAtual().setObservacoesEmprestimo(null);
		
		//seta os atributos de auditoria		
		//TODO remover este comentário, após consertar o erro: CA fazendo reserva
		//editarAtributosAuditoria(objetoBumerangue.getEmprestimoAtual(), usuarioLogado, false);

		//Caso não esteja reservado, após o cancelamento do empréstimo, exclui fisicamente o empréstimo.
		if(!objetoBumerangue.getIsReservado()){
			Emprestimo emprestimoAtual = objetoBumerangue.getEmprestimoAtual();
			objetoBumerangue.setEmprestimoAtual(null);
			emprestimoDao.delete(emprestimoAtual);
		}
	}
	 
	public void editarDevolucaoObjetoBumerangue(Usuario usuarioLogado, Emprestimo devolucao){
		Emprestimo devolucaoBD = emprestimoDao.get(devolucao.getId());

		// verifica se � devolução.
		if(!devolucaoBD.getIsDevolucao()) throw new BumerangueErrorRuntimeException("Empréstimo deve ser uma devolução.");

		// a data da devolução não pode ser depois de hoje, nem antes da data do empréstimo
		if( DateUtil.compareTodayDay(devolucao.getDataDevolucao()) < 0 ||
			DateUtil.compareDay(devolucao.getDataDevolucao(),devolucaoBD.getDataEmprestimo()) < 0 )
			 throw new BumerangueErrorRuntimeException("A data da devolução não pode ser posterior a hoje, nem anterior à data do empréstimo.");

		devolucaoBD.setDataDevolucao(devolucao.getDataDevolucao());
		devolucaoBD.setUsuarioRealizouDevolucao(usuarioLogado);
		devolucaoBD.setObservacoesDevolucao(devolucao.getObservacoesDevolucao());
		devolucaoBD.setTipoAtividadeVideo(devolucao.getTipoAtividadeVideo());
		devolucaoBD.setPublicoVideo(devolucao.getPublicoVideo());
		devolucaoBD.setNumeroAssistentesVideo(devolucao.getNumeroAssistentesVideo());

		//seta os atributos de auditoria
		//TODO remover este comentário, após consertar o erro: CA fazendo reserva
		//editarAtributosAuditoria(devolucaoBD, usuarioLogado, false);
	}

	public void cancelarDevolucaoObjetoBumerangue(Usuario usuarioLogado, Emprestimo devolucao){
		Emprestimo devolucaoBD = emprestimoDao.get(devolucao.getId());

		// verifica se é devolução.
		if(!devolucaoBD.getIsDevolucao()) throw new BumerangueErrorRuntimeException("Empréstimo deve ser uma devolução.");

		devolucaoBD.setDataDevolucao(null);
		devolucaoBD.setUsuarioRealizouDevolucao(null);
		devolucaoBD.setObservacoesDevolucao(null);
		devolucaoBD.setTipoAtividadeVideo(null);
		devolucaoBD.setPublicoVideo(null);
		devolucaoBD.setNumeroAssistentesVideo(null);
		
		//seta os atributos de auditoria
		//TODO remover este comentário, após consertar o erro: CA fazendo reserva
		//editarAtributosAuditoria(devolucaoBD, usuarioLogado, false);

		// seto a devolução ao objeto bumerangue, agora esta como empréstimo atual
		devolucaoBD.getObjetoBumerangue().setEmprestimoAtual(devolucaoBD);
	}

	public List<Emprestimo> pesquisarHistoricoEmprestimosVideo(Usuario usuarioLogado, PesquisaHistoricoVideoCriteria pesquisaHistoricoVideoCriteria) {
		return emprestimoDao.findEmprestimos(pesquisaHistoricoVideoCriteria);
	}
	 
	public List pesquisarEstatisticaEmprestimosVideo(Usuario usuarioLogado, PesquisaHistoricoVideoCriteria pesquisaHistoricoVideoCriteria) {
		return new ArrayList();
	}
	 
	public List<Emprestimo> pesquisarHistoricoEmprestimosLivroCultural(Usuario usuarioLogado, PesquisaHistoricoLivroCulturalCriteria pesquisaHistoricoLivroCulturalCriteria){
		return emprestimoDao.findEmprestimos(pesquisaHistoricoLivroCulturalCriteria);
	}

	public Emprestimo getEmprestimo(Usuario usuarioLogado, String idEmprestimo){
		return emprestimoDao.get(idEmprestimo);
	}

	public void inserirUsuario(Usuario usuarioLogado, Usuario usuario) {
		//verifica se existe outro usuário com o mesmo nome.
		Usuario usuarioUnico = usuarioDao.getPorNome(usuario.getNome());
		if(usuarioUnico != null)
			throw new BumerangueErrorRuntimeException("Já existe outro usuário com o nome: "+usuario.getNome()+".");

		usuario.setId(null);
		usuario.setSenhaCript(Utils.getResourceMessage("bmg.manterSeguranca.senhaPadrao"));
		
		//seta os atributos de auditoria
		editarAtributosAuditoria(usuario, usuarioLogado, true);

		usuarioDao.saveOrUpdate(usuario);
		usuario.setCodigoTipoPermissao(ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_BASICO.getCodigo());
		atualizaPermissoes(usuario);
	}
	 
	public void editarUsuario(Usuario usuarioLogado, Usuario usuario) {
		//verifica se existe outro usuário com o mesmo nome.
		Usuario usuarioUnico = usuarioDao.getPorNome(usuario.getNome());
		if(usuarioUnico != null && !usuarioUnico.getId().equals(usuario.getId()))
			throw new BumerangueErrorRuntimeException("Já existe outro usuário com o nome: "+usuario.getNome()+".");

		Usuario bd = usuarioDao.get(usuario.getId());
		
		bd.setNome(usuario.getNome());
		bd.setNomeCompleto(usuario.getNomeCompleto());
		bd.setEmail(usuario.getEmail());
		bd.setTelefone(usuario.getTelefone());
		bd.setAtivo(usuario.getAtivo());
		bd.setCodigoTipoPermissao(usuario.getCodigoTipoPermissao());

		atualizaPermissoes(bd);

		//seta os atributos de auditoria
		editarAtributosAuditoria(bd, usuarioLogado, false);
	}
	 
	private void atualizaPermissoes(Usuario usuario){
		usuario = usuarioDao.get(usuario.getId());
		
		//não atualiza as permissões, se o usuário for o admin. Uma vez que este recebe todas as permissões de admin ao iniciar a aplicação.
		if(usuario.isAdmin()) return;
		
		Permissao permissaoBasico = null;
		Permissao permissaoAvancado = null;
		Permissao permissaoAdmin = null;

		//carrega as permissões, de acordo com o tipo de usuário
		for(Permissao permissao : Permissao.getPermissoesPorUsuario(usuario)){
			if(permissao.isAdmin()) permissaoAdmin = permissao;
			else if(permissao.isAvancado()) permissaoAvancado = permissao;
			else if(permissao.isBasico()) permissaoBasico = permissao;
		}
		
		usuario.removePermissao(permissaoBasico);
		usuario.removePermissao(permissaoAvancado);
		usuario.removePermissao(permissaoAdmin);
		if(usuario.getCodigoTipoPermissao().equals(ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_BASICO.getCodigo())){
			permissaoBasico = permissaoDao.getPorNome(permissaoBasico.getNome());
			usuario.addPermissao(permissaoBasico);
		} else if(usuario.getCodigoTipoPermissao().equals(ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_AVANCADO.getCodigo())){
			permissaoAvancado = permissaoDao.getPorNome(permissaoAvancado.getNome());
			usuario.addPermissao(permissaoAvancado);
		} else if(usuario.getCodigoTipoPermissao().equals(ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_ADMIN.getCodigo())){
			permissaoAdmin = permissaoDao.getPorNome(permissaoAdmin.getNome());
			usuario.addPermissao(permissaoAdmin);
		}
	}

	public void excluirUsuario(Usuario usuarioLogado, Usuario usuario) {
		usuario = usuarioDao.get(usuario.getId());
		
		//o usuário admin não pode ser excluído.
		if(usuario.isAdmin()) throw new BumerangueAlertRuntimeException("O usuário admin não pode ser excluído.");

		//se tiver participando de emprétimos, o usuário é colocado como desativado, senão é excluído.
		if(!emprestimoDao.findReferenciasUsuario(usuario).isEmpty()){
			usuario.setAtivo(Boolean.FALSE);

			//seta os atributos de auditoria
			editarAtributosAuditoria(usuario, usuarioLogado, false);
		}
		else objetoBumerangueDao.delete(usuario);
	}
	 
	public Usuario getUsuario(Usuario usuarioLogado, String idUsuario) {
		return usuarioDao.get(idUsuario);
	}
	 
	public Dominio getDominio(Usuario usuarioLogado, Integer codigo){
		return dominioDao.get(codigo);
	}

	public List pesquisarFilmesComerciais(Usuario usuarioLogado, PesquisaFilmeComercialCriteria pesquisaFilmeComercialCriteria){
		return objetoBumerangueDao.findFilmesComerciais(pesquisaFilmeComercialCriteria);
	}

	public String getProximoCodigoFilmeComercial() {
		long proximoCodigo = new Long(objetoBumerangueDao.maxAttribute(FilmeComercial.class,"codigo")).longValue() + 1;
		return proximoCodigo+"";
	}

	public List pesquisarLivrosCulturais(Usuario usuarioLogado, PesquisaLivroCulturalCriteria pesquisaLivroCulturalCriteria){
		return objetoBumerangueDao.findLivrosCulturais(pesquisaLivroCulturalCriteria);
	}
	
	public List<Usuario> pesquisarUsuarios(Usuario usuarioLogado, PesquisaUsuarioCriteria pesquisaUsuarioCriteria){
		List<Usuario> usuarios = usuarioDao.findUsuarios(pesquisaUsuarioCriteria);

		//seleciona os usuários que o usuário logado tem permissão como admin
		List<Usuario> usuariosSelecionados = new ArrayList<Usuario>();
		List<String> modulosPermitidos = usuarioLogado.getNomesModulosAdmin();
		for(Usuario usuario : usuarios){
			for(String moduloPermitido : modulosPermitidos){
				if(moduloPermitido.equals(Deploy.getNomeModuloPorUsuario(usuario)))
					usuariosSelecionados.add(usuario);
			}
		}
		
		return usuariosSelecionados;
	}
	
	public List<Usuario> pesquisarUsuariosCompartilhados(Usuario usuarioLogado, PesquisaUsuarioCriteria pesquisaUsuarioCriteria, String nomeModulo){
		//novo critério para não alterar as informações do parametro
		PesquisaUsuarioCriteria pesquisaUsuarioCriteriaDAO = null;
		try {
			pesquisaUsuarioCriteriaDAO = (PesquisaUsuarioCriteria)BeanUtils.cloneBean(pesquisaUsuarioCriteria);

		} catch (Exception e) {
			throw new BumerangueErrorRuntimeException(e.getMessage());
		}

		//recupera todos os usuários em cada uma das opções do módulo
		pesquisaUsuarioCriteriaDAO.setClasseUsuario(null);
		pesquisaUsuarioCriteriaDAO.setNomeModulo(null);
		List<Usuario> usuariosCompartilhados = new ArrayList<Usuario>();
		for(Permissao permissao : Permissao.getPermissoesPorModulo(nomeModulo)){
			pesquisaUsuarioCriteriaDAO.setNomePermissao(permissao.getNome());
			usuariosCompartilhados.addAll(usuarioDao.findUsuarios(pesquisaUsuarioCriteriaDAO));
		}
		
		//remove da lista filtrada todos os usuários correspondentes ao módulo
		Usuario usuarioModulo = Usuario.getInstance(nomeModulo);
		if(usuarioModulo != null){
			List<Usuario> usuariosDistintos = new ArrayList<Usuario>();
			for(Usuario usuario : usuariosCompartilhados){
				if(usuario.getClass().getSimpleName().equals(usuarioModulo.getClass().getSimpleName())) continue;
				usuariosDistintos.add(usuario);
			}
			usuariosCompartilhados = usuariosDistintos;
		}

		return usuariosCompartilhados;
	}
	
	public List<Arquivo> listarArquivos(Usuario usuarioLogado){
		//Set para evitar elementos duplicados
		Collection<Arquivo> arquivos = new TreeSet<Arquivo>();
		
		//Se o usuário é o Admin, adiciona os arquivos na raiz do diretório de compartilhamento (bumerangue).
		if(usuarioLogado.isAdmin())
			arquivos.addAll(this.getArquivosCompartilhadosPeloUsuarioAdmin(null));
		
		//para cada permissão do usuário, adiciona os arquivos visíveis.
		for(Permissao permissao : usuarioLogado.getPermissoes()){
			if(Deploy.isModuloDisponivel(permissao))
				arquivos.addAll(this.getArquivos(permissao));
		}

		return new ArrayList<Arquivo>(arquivos);
	}

	private List<Arquivo> getArquivosCompartilhadosPeloUsuarioAdmin(Permissao permissao){
		List<Arquivo> arquivos = new ArrayList<Arquivo>();

		File dir = new File(super.getPathDiretorioArquivosUsuarioAdmin(permissao == null ? null : permissao.getTipoPermissao()));
		
		if(!dir.isDirectory())
			throw new BumerangueErrorRuntimeException("Diretório não encontrado: "+super.getPathRelativoDiretorioArquivosUsuarioAdmin(permissao == null ? null : permissao.getTipoPermissao()));
		
		//adiciona os arquivos da permissão
		for(File file : dir.listFiles()){
			if(!file.isFile()) continue;
			arquivos.add(new Arquivo(file, permissao, true));
		}
		
		return arquivos;
	}

	private List<Arquivo> getArquivos(Permissao permissao){
		List<Arquivo> arquivos = new ArrayList<Arquivo>();

		File dir = new File(super.getPathDiretorioArquivos(permissao));
		
		if(!dir.isDirectory())
			throw new BumerangueErrorRuntimeException("Diretório não encontrado: "+super.getPathRelativoDiretorioArquivos(permissao));
		
		//adiciona os arquivos compartilhados para o tipo da permissão
		arquivos.addAll(this.getArquivosCompartilhadosPeloUsuarioAdmin(permissao));

		//adiciona os arquivos da permissão
		for(File file : dir.listFiles()){
			arquivos.add(new Arquivo(file, permissao, false));
		}
		
		//permissão tem visibilidade acumulativa em outras permissões: admin > avancado > basico
		if(permissao.isAdmin()){
			Permissao avancado = Permissao.getPermissao(ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_AVANCADO.getCodigo(),permissao.getNomeModulo());
			if(avancado != null)
				arquivos.addAll(this.getArquivos(avancado));
		}else if(permissao.isAvancado()){
			Permissao basico = Permissao.getPermissao(ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_BASICO.getCodigo(),permissao.getNomeModulo());
			if(basico != null)
				arquivos.addAll(this.getArquivos(basico));
		}

		return arquivos;
	}

	public void uploadArquivo(Usuario usuarioLogado, String nomeModulo, Integer codigoTipoPermissao, String nomeArquivo, MultipartFile uploadFile){
		//verifica se o usuário tem permissão
		boolean acesso = true;
		Permissao permissaoAdmin = codigoTipoPermissao == null ? null : Permissao.getPermissao(ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_ADMIN.getCodigo(), nomeModulo);
		if(Deploy.MODULO_BUMERANGUE.equals(nomeModulo) && !usuarioLogado.isAdmin())
			acesso = false;
		else{
			if(!usuarioLogado.isAdmin() && (!usuarioLogado.isInRole(permissaoAdmin) ))
				acesso = false;
		}
		if(!acesso)
			throw new BumerangueErrorRuntimeException("Você não tem permissão para enviar o arquivo: "+nomeArquivo);

		String path = "";
		if(Deploy.MODULO_BUMERANGUE.equals(nomeModulo)){
			ElementoDominio tipoPermissao = dominioDao.getElementoDominio(Dominio.BUMERANGUE_TIPO_PERMISSAO.getCodigo(),codigoTipoPermissao);
			path = super.getPathDiretorioArquivosUsuarioAdmin(tipoPermissao);
		}else{
			Permissao permissao = Permissao.getPermissao(codigoTipoPermissao, nomeModulo);
			path = super.getPathDiretorioArquivos(permissao);
		}
		nomeArquivo = File.separator+Utils.getNormalizedString(nomeArquivo);
		
		File file = new File(path+nomeArquivo);
		
		//caso o arquivo já exista, salva uma cópia na pasta admin do módulo, ou na pasta raiz do bumerangue.
		if(file.isFile()){
			String targetPath = "";
			if(Deploy.MODULO_BUMERANGUE.equals(nomeModulo))
				targetPath = super.getPathDiretorioArquivosUsuarioAdmin(null);
			else
				targetPath = super.getPathDiretorioArquivos(permissaoAdmin);
			Utils.copyFile(file.getAbsolutePath(),targetPath+nomeArquivo+"_"+System.currentTimeMillis());
		}
		
		try {
			uploadFile.transferTo(file);
		} catch (Exception e) {
			new BumerangueErrorRuntimeException(e.getClass().getName()+": "+e.getMessage());
		}
	}
		
	public void excluirArquivo(Usuario usuarioLogado, String numeroPermissao, String nomeArquivo, boolean compartilhadoTodasPermissoes){
		//verifica se o usuário tem permissão
		Permissao permissao = permissaoDao.getPorNumero(numeroPermissao);
		Permissao permissaoAdmin = numeroPermissao == null ? null : Permissao.getPermissao(ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_ADMIN.getCodigo(), permissao.getNomeModulo());
		if(!usuarioLogado.isAdmin() && (!usuarioLogado.isInRole(permissaoAdmin) || compartilhadoTodasPermissoes ))
			throw new BumerangueErrorRuntimeException("Você não tem permissão para excluir o arquivo: "+nomeArquivo);
		
		String path = "";
		if(numeroPermissao == null || compartilhadoTodasPermissoes)
			path = super.getPathDiretorioArquivosUsuarioAdmin(permissao == null ? null : permissao.getTipoPermissao());
		else
			path = super.getPathDiretorioArquivos(permissao);
		path += File.separator+nomeArquivo; 
			
		new File(path).delete();
	}

	public Map<String, String> getNotificacoes(Usuario usuarioLogado){
		Map<String, String> notificacoes = new HashMap<String,String>();
		
		//sobre a necessidade de boot
		Dominio dominioTipoPermissao = dominioDao.get(Dominio.BUMERANGUE_TIPO_PERMISSAO.getCodigo());
		if(!StringUtils.isBlank(dominioTipoPermissao.getPersonalizado1()))
			notificacoes.put("boot","Algumas configurações foram alteradas, as quais requerem o reinício da Aplicação para entrarem em vigor. "+dominioTipoPermissao.getPersonalizado1());
		
		return notificacoes;
	}

	/**
	 * Método para setar os atributos de auditoria.
	 * 
	 * @param baseEntity objeto instancia de BaseEntity que deve conter os atributos de auditoria
	 * @param usuarioLogado usuarioLogado
	 * @param criacao se true, seta os atributos de auditoria de criação, caso contrário seta os atributos de auditoria de edição
	 */
	void editarAtributosAuditoria(BaseEntity baseEntity, Usuario usuarioLogado, boolean criacao){
		if(true) return;
		
		if(usuarioLogado == null) return; 
		if (!(baseEntity instanceof BaseEntity)) return;
		try {
			if (criacao){
				BeanUtils.setProperty(baseEntity, "dataCriacao", new Date());
				BeanUtils.setProperty(baseEntity, "usuarioCriacao", new Usuario(usuarioLogado.getId()));
				BeanUtils.setProperty(baseEntity, "ipCriacao", usuarioLogado.getIp());
			}else{
				BeanUtils.setProperty(baseEntity, "dataUltimaAlteracao", new Date());
				BeanUtils.setProperty(baseEntity, "usuarioUltimaAlteracao", new Usuario(usuarioLogado.getId()));
				BeanUtils.setProperty(baseEntity, "ipUltimaAlteracao", usuarioLogado.getIp());
			}
		} catch (Exception e) {
			throw new BumerangueErrorRuntimeException("Erro ao setar atributos de auditoria. Objeto informado não possui as propriedades de auditoria. <br>"+e.getMessage());
		} 
	}
}