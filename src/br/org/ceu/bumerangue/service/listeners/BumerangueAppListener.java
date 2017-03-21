package br.org.ceu.bumerangue.service.listeners;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.MessageSourceAccessor;

import br.org.ceu.bumerangue.dao.DominioDao;
import br.org.ceu.bumerangue.dao.PermissaoDao;
import br.org.ceu.bumerangue.dao.UsuarioDao;
import br.org.ceu.bumerangue.entity.Dominio;
import br.org.ceu.bumerangue.entity.ElementoDominio;
import br.org.ceu.bumerangue.entity.Permissao;
import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.entity.suporte.deploy.Deploy;
import br.org.ceu.bumerangue.entity.suporte.deploy.VersionEnum;

public final class BumerangueAppListener implements ApplicationListener, ApplicationContextAware, MessageSourceAware  {

	// -- ApplicationContext que fornece acesso aos beans do contexto do Spring ----------------------------
	protected ApplicationContext appContext;
	final public void setApplicationContext(ApplicationContext applicationContext) throws BeansException { appContext = applicationContext; }
	// -----------------------------------------------------------------------------------------------------
	
	// -- Spring MessageSource (i18n) ----------------------------------------------------------------------
	protected MessageSourceAccessor messageSourceAccessor;
    public void setMessageSource(MessageSource messageSource) { this.messageSourceAccessor = new MessageSourceAccessor(messageSource);}
	// -----------------------------------------------------------------------------------------------------
	
	
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	public void onApplicationEvent(ApplicationEvent event) {

		/* ContextRefreshedEvent */
		if (event instanceof ContextRefreshedEvent && ((ContextRefreshedEvent)event).getApplicationContext().equals(appContext)) {

			System.out.println("\n\nEntrou no onApplicationEvent do BumerangueAppListener (versão " + VersionEnum.VERSAO.value() + " de " + VersionEnum.DATA_VERSAO.value() + ")\n\n");
    		Deploy.loadModulosDisponiveis();
			
//			1. cria o admin
			criaAdmin();
//			2. cria as permissoes
			criaPermissoes();
//			3. atribui permissoes de admin para o admin
			atribuiPermissoesAdminADMIN();
//			4. cria os dominios
			criaDominios();
//			5. criar os elementos dominio do dominio BUMERANGUE_TIPO_PERMISSAO
			criaElementosDominioTipoPermissao();
//			6. criar os elementos dominio do dominio FICHA_MISSA_SITUACAO_PLANO_MISSA
			criaElementosDominioSituacaoPlanoMissa();
//			7. criar a view bmg_permissao_usuario_view
			criaPermissaoUsuarioView();
//			8. remover a notifica��o de que o rein�cio da aplica��o fora necess�rio
			removerNotificacaoNecessidadeBoot();
		}
	}

	/**
	 * Cria o usu�rio ADMIN, se este n�o existir.
	 * @author Adriano Carvalho
	 * @return
	 */
	private void criaAdmin(){
		UsuarioDao usuarioDao = (UsuarioDao)appContext.getBean("usuarioDao");
		Usuario admin = usuarioDao.getPorNome(Usuario.NOME_USUARIO_ADMIN);
		if(admin == null){
			admin = new Usuario();
			admin.setNome(Usuario.NOME_USUARIO_ADMIN);
			admin.setNomeCompleto("Administrador com todas as permiss�es admin");
			admin.setSenhaCript(Usuario.SENHA_PADRAO_USUARIO_ADMIN);

			usuarioDao.saveOrUpdate(admin);
		}
	}

	/**
	 * Cria todas as permiss�es da aplica��o, para as que n�o estejam ainda no banco de dados.<br>
	 * Estas s�o definidas como constantes da classe Permissao.
	 * @author Adriano Carvalho
	 */
	private void criaPermissoes(){
		PermissaoDao permissaoDao = (PermissaoDao)appContext.getBean("permissaoDao");
		for(Permissao permissao : Permissao.getPermissoes()){
			Permissao permissaoBanco = permissaoDao.getPorNome(permissao.getNome());
			if(permissaoBanco == null){
				permissaoDao.saveOrUpdate(permissao);
			}
		}
	}

	/**
	 * Atribui todas as permiss�es da aplica��o do tipo Admin para o usu�rio ADMIN, caso este n�o possua.
	 * @author Adriano Carvalho
	 */
	private void atribuiPermissoesAdminADMIN(){
		PermissaoDao permissaoDao = (PermissaoDao)appContext.getBean("permissaoDao");
		UsuarioDao usuarioDao = (UsuarioDao)appContext.getBean("usuarioDao");
		Usuario admin = usuarioDao.getPorNome(Usuario.NOME_USUARIO_ADMIN);
		boolean atualiza = false;
		for(Permissao permissao : Permissao.getPermissoes()){
			if(permissao.isAdmin()){
				//verifica se o Admin tem a permiss�o, independente dos M�dulos dispon�veis
				//se j� tiver, n�o precisa adicionar.
				boolean jaTem = false;
				for (Permissao permissaoAdmin : admin.getPermissoes()){
					if(permissaoAdmin.getNome().equals(permissao.getNome())){
						jaTem = true;
						break; // sai do loop interno
					}
				}
				if(jaTem) continue;

				Permissao permissaoBanco = permissaoDao.getPorNome(permissao.getNome());
				admin.addPermissao(permissaoBanco);
				atualiza = true;
			}
		}
		if(atualiza)
			usuarioDao.saveOrUpdate(admin);
	}
	
	/**
	 * Cria todos os dom�nios da aplica��o, para os que n�o estejam ainda no banco de dados.<br>
	 * Estes s�o definidos como constantes da classe Dominio.
	 * @author Adriano Carvalho
	 */
	private void criaDominios(){
		DominioDao dominioDao = (DominioDao)appContext.getBean("dominioDao");
		for(Dominio dominio : Dominio.getDominios()){
			Dominio dominioBanco = dominioDao.get(dominio.getCodigo());
			if(dominioBanco == null){
				dominioDao.saveOrUpdate(dominio);
			}
		}
	}
	
	/**
	 * Cria todos os elementos do dom�nio BUMERANGUE_TIPO_PERMISSAO, para os que n�o estejam ainda no banco de dados.<br>
	 * Estes s�o definidos como constantes da classe ElementoDominio.
	 * @author Adriano Carvalho
	 */
	private void criaElementosDominioTipoPermissao(){
		DominioDao dominioDao = (DominioDao)appContext.getBean("dominioDao");

		//constantes s�o recuperadas de forma est�tica, pois n�o � previsto inclus�es outros de tipos de permiss�o.
		List<ElementoDominio> elementosDominioTipoPermissao = new ArrayList<ElementoDominio>();
		elementosDominioTipoPermissao.add(ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_ADMIN);
		elementosDominioTipoPermissao.add(ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_AVANCADO);
		elementosDominioTipoPermissao.add(ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_BASICO);
		
		Dominio tipoPermissao = dominioDao.get(Dominio.BUMERANGUE_TIPO_PERMISSAO.getCodigo());
		boolean atualiza = false;
		for(ElementoDominio elementoDominio : elementosDominioTipoPermissao){
			boolean persistente = false;
			for(ElementoDominio elementoDominioBanco : tipoPermissao.getElementosDominio()){
				if(elementoDominioBanco.getCodigo().intValue() == elementoDominio.getCodigo().intValue()){
					persistente = true;
					break;
				}
			}
			if(!persistente){
				tipoPermissao.getElementosDominio().add(elementoDominio);
				atualiza = true;
			}
		}
		if(atualiza)
			dominioDao.saveOrUpdate(tipoPermissao);
		
	}

	/**
	 * Cria todos os elemento do dom�nio FICHA_MISSA_SITUACAO_PLANO_MISSA, para os que n�o estejam ainda no banco de dados.<br>
	 * Estes s�o definidos como constantes da classe ElementoDominio.
	 * @author Adriano Carvalho
	 */
	private void criaElementosDominioSituacaoPlanoMissa(){
		DominioDao dominioDao = (DominioDao)appContext.getBean("dominioDao");

		//constantes s�o recuperadas de forma est�tica, pois n�o � previsto inclus�es outros de tipos de permiss�o.
		List<ElementoDominio> elementosDominioSituacaoPlanoMissa = new ArrayList<ElementoDominio>();
		elementosDominioSituacaoPlanoMissa.add(ElementoDominio.FICHA_MISSA_SITUACAO_PLANO_MISSA_EM_APROVACAO);
		elementosDominioSituacaoPlanoMissa.add(ElementoDominio.FICHA_MISSA_SITUACAO_PLANO_MISSA_APROVADO);
		
		Dominio situacaoPlanoMissa = dominioDao.get(Dominio.FICHA_MISSA_SITUACAO_PLANO_MISSA.getCodigo());
		boolean atualiza = false;
		for(ElementoDominio elementoDominio : elementosDominioSituacaoPlanoMissa){
			boolean persistente = false;
			for(ElementoDominio elementoDominioBanco : situacaoPlanoMissa.getElementosDominio()){
				if(elementoDominioBanco.getCodigo().intValue() == elementoDominio.getCodigo().intValue()){
					persistente = true;
					break;
				}
			}
			if(!persistente){
				situacaoPlanoMissa.getElementosDominio().add(elementoDominio);
				atualiza = true;
			}
		}
		if(atualiza)
			dominioDao.saveOrUpdate(situacaoPlanoMissa);
		
	}

	/**
	 * Cria a view bmg_permissao_usuario_view usada na seguran�a declarativa pelo containner Web.
	 * @author Adriano Carvalho
	 */
	private void criaPermissaoUsuarioView(){
		PermissaoDao permissaoDao = (PermissaoDao)appContext.getBean("permissaoDao");
		permissaoDao.criarPermissaoUsuarioView();
	}

	/**
	 * Remove a notifica��o de que o rein�cio da aplica��o fora necess�rio.
	 * @author Adriano Carvalho
	 */
	private void removerNotificacaoNecessidadeBoot(){
		DominioDao dominioDao = (DominioDao)appContext.getBean("dominioDao");

		Dominio dominioTipoPermissao = dominioDao.get(Dominio.BUMERANGUE_TIPO_PERMISSAO.getCodigo());
		dominioTipoPermissao.setPersonalizado1(null);
		
		dominioDao.saveOrUpdate(dominioTipoPermissao);
	}
}
