package br.org.ceu.bumerangue.service;

import br.org.ceu.bumerangue.entity.ObjetoBumerangue;
import br.org.ceu.bumerangue.entity.PlanoMissa;
import br.org.ceu.bumerangue.entity.Usuario;


public interface MensagemService extends BaseService {

	public void enviarMailReserva(ObjetoBumerangue objetoBumerangue);

	public void enviarMailDesbloqueioUsuario(Usuario usuario);
	
	public void enviarMailReinicioSenha(Usuario usuario);
	
	public void enviarMailBloqueioUsuario(Usuario usuario);
	
	public void enviarMailLembreteNaoDevolvidos();
	
	public void enviarMailLembreteSenha(Usuario usuario);
	
	public void enviarMailComentarioSugestao(Usuario usuarioLogado, String nomeRemetente, String emailRemetente, String textoComentario);
	
	public void enviarMailAprovacaoPlanoMissa(PlanoMissa planoMissa);

}
