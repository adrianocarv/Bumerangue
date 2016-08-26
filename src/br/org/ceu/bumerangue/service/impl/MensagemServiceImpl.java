package br.org.ceu.bumerangue.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import br.org.ceu.bumerangue.dao.UsuarioDao;
import br.org.ceu.bumerangue.entity.Centro;
import br.org.ceu.bumerangue.entity.ElementoDominio;
import br.org.ceu.bumerangue.entity.ObjetoBumerangue;
import br.org.ceu.bumerangue.entity.Permissao;
import br.org.ceu.bumerangue.entity.PlanoMissa;
import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.entity.UsuarioFichaMissa;
import br.org.ceu.bumerangue.entity.criterias.PesquisaUsuarioCriteria;
import br.org.ceu.bumerangue.entity.suporte.deploy.Deploy;
import br.org.ceu.bumerangue.service.MensagemService;
import br.org.ceu.bumerangue.service.exceptions.BumerangueErrorRuntimeException;
import br.org.ceu.bumerangue.util.MMSimpleMailMessage;
import br.org.ceu.bumerangue.util.Utils;
import br.org.ceu.bumerangue.util.ValidationRules;

public class MensagemServiceImpl extends BaseServiceImpl implements MensagemService {
	
	//e-mails enviados automaticamente, quando ocorrerem os eventos:
	private static final String TIPO_EMAIL_RESERVA = "Reserva";
	private static final String TIPO_EMAIL_DESBLOQUEIO_USUARIO = "DesbloqueioUsuario";
	private static final String TIPO_EMAIL_REINICIO_SENHA = "ReinicioSenha";
	private static final String TIPO_EMAIL_BLOQUEIO_USUARIO = "BloqueioUsuario";
	private static final String TIPO_EMAIL_LEMBRETE_NAO_DEVOLVIDOS = "LembreteNaoDevolvido";
	private static final String TIPO_EMAIL_LEMBRETE_SENHA = "LembreteSenha";
	private static final String TIPO_EMAIL_COMENTARIO_SUGESTAO = "ComentarioSugestao";
	private static final String TIPO_EMAIL_APROVACAO_PLANO_MISSA = "AprovacaoPlanoMissa";
	
	private JavaMailSender javaMailSender;
    public void setJavaMailSender(SimpleMailMessage simpleMailMessage) { this.simpleMailMessage = simpleMailMessage;}

    private SimpleMailMessage simpleMailMessage;
    public void setBaseMailSender(JavaMailSender javaMailSender) { this.javaMailSender = javaMailSender;}

    private UsuarioDao usuarioDao;
    public void setUsuarioDao(UsuarioDao usuarioDao) { this.usuarioDao = usuarioDao; }

	public void enviarMailReserva(ObjetoBumerangue objetoBumerangue){
		if(!objetoBumerangue.getIsReservado()) return;
		if(!this.habilitadoEnvioEmail(objetoBumerangue,TIPO_EMAIL_RESERVA)) return;

		Usuario usuarioEmprestimo = usuarioDao.get(objetoBumerangue.getEmprestimoAtual().getUsuarioEmprestimo().getId());
		
		String remetente = this.getRemetente(objetoBumerangue);
		String assunto = this.getAssunto(objetoBumerangue,TIPO_EMAIL_RESERVA);
		String texto = this.getTexto(objetoBumerangue,TIPO_EMAIL_RESERVA);
		List<String> para = new ArrayList<String>();
		para.addAll(usuarioEmprestimo.getEmails());
		para.addAll(this.getEmailsUsuariosAtivos(Permissao.getPermissao(ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_ADMIN.getCodigo(), objetoBumerangue)));
		
		MMSimpleMailMessage mail = new MMSimpleMailMessage(true,remetente,listParaArrayString(para),assunto,texto);
		this.sendMail(mail);
	}

	public void enviarMailDesbloqueioUsuario(Usuario usuario){
		if(!this.habilitadoEnvioEmail(usuario,TIPO_EMAIL_DESBLOQUEIO_USUARIO)) return;

		String remetente = this.getRemetente(usuario);
		String assunto = this.getAssunto(usuario,TIPO_EMAIL_DESBLOQUEIO_USUARIO);
		String texto = this.getTexto(usuario,TIPO_EMAIL_DESBLOQUEIO_USUARIO);
		List<String> para = new ArrayList<String>();
		para.addAll(usuario.getEmails());
		para.addAll(this.getEmailsUsuariosAtivos(Permissao.getPermissao(ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_ADMIN.getCodigo(), usuario)));

		MMSimpleMailMessage mail = new MMSimpleMailMessage(true,remetente,listParaArrayString(para),assunto,texto);
		this.sendMail(mail);
	}
	
	public void enviarMailReinicioSenha(Usuario usuario){
		if(!this.habilitadoEnvioEmail(usuario,TIPO_EMAIL_REINICIO_SENHA)) return;

		String remetente = this.getRemetente(usuario);
		String assunto = this.getAssunto(usuario,TIPO_EMAIL_REINICIO_SENHA);
		String texto = this.getTexto(usuario,TIPO_EMAIL_REINICIO_SENHA);
		List<String> para = new ArrayList<String>();
		para.addAll(usuario.getEmails());
		para.addAll(this.getEmailsUsuariosAtivos(Permissao.getPermissao(ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_ADMIN.getCodigo(), usuario)));

		MMSimpleMailMessage mail = new MMSimpleMailMessage(true,remetente,listParaArrayString(para),assunto,texto);
		this.sendMail(mail);
	}

	public void enviarMailBloqueioUsuario(Usuario usuario){
		if(!this.habilitadoEnvioEmail(usuario,TIPO_EMAIL_BLOQUEIO_USUARIO)) return;

		String remetente = this.getRemetente(usuario);
		String assunto = this.getAssunto(usuario,TIPO_EMAIL_BLOQUEIO_USUARIO);
		String texto = this.getTexto(usuario,TIPO_EMAIL_BLOQUEIO_USUARIO);
		List<String> para = new ArrayList<String>();
		if(usuario.isAdmin())
			para.add(usuario.getEmail());
		else
			para.addAll(this.getEmailsUsuariosAtivos(Permissao.getPermissao(ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_ADMIN.getCodigo(), usuario)));

		MMSimpleMailMessage mail = new MMSimpleMailMessage(true,remetente,listParaArrayString(para),assunto,texto);
		this.sendMail(mail);
	}

	public void enviarMailLembreteNaoDevolvidos(){
		
	}

	public void enviarMailLembreteSenha(Usuario usuario){

		String remetente = this.getRemetente(usuario);
		String assunto = this.getAssunto(usuario,TIPO_EMAIL_LEMBRETE_SENHA);
		String texto = this.getTexto(usuario,TIPO_EMAIL_LEMBRETE_SENHA);
		List<String> para = new ArrayList<String>();
		para.addAll(usuario.getEmails());
		para.addAll(this.getEmailsUsuariosAtivos(Permissao.getPermissao(ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_ADMIN.getCodigo(), usuario)));

		MMSimpleMailMessage mail = new MMSimpleMailMessage(true,remetente,listParaArrayString(para),assunto,texto);
		this.sendMail(mail);
	}
	
	public void enviarMailComentarioSugestao(Usuario usuarioLogado, String nomeRemetente, String emailRemetente, String textoComentario){
		List<String> para = new ArrayList<String>();
		if(usuarioLogado == null){
			usuarioLogado = new Centro();
			usuarioLogado.setNomeCompleto(nomeRemetente);
			usuarioLogado.setNome("não logado");
			usuarioLogado.setEmail(emailRemetente);
		}else{
			para.addAll(this.getEmailsUsuariosAtivos(Permissao.getPermissao(ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_ADMIN.getCodigo(), usuarioLogado)));
		}
		textoComentario = "<b>"+usuarioLogado.getNomeFormatado()+"</b> - escreveu:<br><br>"+textoComentario;
		
		String remetente = usuarioLogado.getEmails().get(0);
		String assunto = this.getAssunto(usuarioLogado,TIPO_EMAIL_COMENTARIO_SUGESTAO);
		String texto = this.getTexto(usuarioLogado,textoComentario,TIPO_EMAIL_COMENTARIO_SUGESTAO);
		para.addAll(usuarioDao.getPorNome(Usuario.NOME_USUARIO_ADMIN).getEmails());

		MMSimpleMailMessage mail = new MMSimpleMailMessage(true,remetente,listParaArrayString(para),assunto,texto);
		this.sendMail(mail);
	}
	
	public void enviarMailAprovacaoPlanoMissa(PlanoMissa planoMissa){
		if(!this.habilitadoEnvioEmail(new UsuarioFichaMissa(),TIPO_EMAIL_APROVACAO_PLANO_MISSA)) return;

		String remetente = this.getRemetente(new UsuarioFichaMissa());
		String assunto = this.getAssunto(planoMissa,TIPO_EMAIL_APROVACAO_PLANO_MISSA);

		//recupera todos os usu�rios com permiss�es para o m�dulo ficha de missa
		List<Usuario> usuariosFichaMissa = new ArrayList<Usuario>();
		PesquisaUsuarioCriteria criteria = new PesquisaUsuarioCriteria();
		//add admins
		criteria.setNomePermissao(Permissao.ADMIN_FICHA_MISSA.getNome());
		usuariosFichaMissa.addAll(usuarioDao.findUsuarios(criteria));
		//add avan�ados
		criteria.setNomePermissao(Permissao.AVANCADO_FICHA_MISSA.getNome());
		usuariosFichaMissa.addAll(usuarioDao.findUsuarios(criteria));
		//add basicos
		criteria.setNomePermissao(Permissao.BASICO_FICHA_MISSA.getNome());
		usuariosFichaMissa.addAll(usuarioDao.findUsuarios(criteria));
		
		//para cada usu�rio, envia um e-mail notificando a aprova��o do plano
		for(Usuario usuario : usuariosFichaMissa){
			String texto = this.getTexto(planoMissa,usuario,TIPO_EMAIL_APROVACAO_PLANO_MISSA);
			List<String> para = new ArrayList<String>(usuario.getEmails());
			MMSimpleMailMessage mail = new MMSimpleMailMessage(true,remetente,listParaArrayString(para),assunto,texto);
			this.sendMail(mail);
		}
	}

	private List<String> getEmailsUsuariosAtivos(Permissao permissao){
		if(permissao == null)
			return new ArrayList<String>();

		ArrayList<String> emailsUsuarios = new ArrayList<String>();
		PesquisaUsuarioCriteria pesquisaUsuarioCriteria = new PesquisaUsuarioCriteria();
		pesquisaUsuarioCriteria.setAtivo(Boolean.TRUE);
		pesquisaUsuarioCriteria.setNomePermissao(permissao.getNome());
		List usuarios = usuarioDao.findUsuarios(pesquisaUsuarioCriteria);
		for (int i = 0; i < usuarios.size(); i++) {
			Usuario usuario = (Usuario)usuarios.get(i);
			if(ValidationRules.isInformed(usuario.getEmail()))
				emailsUsuarios.addAll(usuario.getEmails());
			
		}
		return emailsUsuarios;
	}

	private void sendMail(MMSimpleMailMessage mailMessage) {
		try {
			// Objeto javaMailSender contem o setup para configura��o de conta de e-mail
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			// Objeto Helper do Spring que auxilia na popula��o de um MimeMessage 
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "ISO-8859-1");
						
			if (mailMessage.getTo()  != null && mailMessage.getTo()[0]  != null) messageHelper.setTo(mailMessage.getTo());
			if (mailMessage.getBcc() != null && mailMessage.getBcc()[0] != null) messageHelper.setBcc(mailMessage.getBcc());
			if (mailMessage.getCc()  != null && mailMessage.getCc()[0]  != null) messageHelper.setCc(mailMessage.getCc());
			
			messageHelper.setFrom   (mailMessage.getFrom()   );
			messageHelper.setSubject(mailMessage.getSubject());
			
			//complementa o corpo da mensagem com o remetente
			String text = mailMessage.getText() + (mailMessage.isHtmlMessage() ? "<br><br>" : "\n\n");
			text += "e-mail enviado por: " + mailMessage.getFrom();
			messageHelper.setText   (text,mailMessage.isHtmlMessage());
			
			javaMailSender.send(mimeMessage);
			
		} catch (Exception e) {
			throw new BumerangueErrorRuntimeException(e.getMessage());
		}
	}
	
	private String[] listParaArrayString(List list){
		//converte o List para String[]
		String[] strPara = new String[list.size()];
		for (int i = 0; i < list.size(); i++)
			if(list.get(i) != null)strPara[i] = (String)list.get(i);
		return strPara;
	}

	private String getPrefixoChaveMensagem(Usuario usuario){
		String nomeModulo = usuario.isAdmin() ? Deploy.MODULO_VIDEO : Deploy.getNomeModuloPorUsuario(usuario);
		return "plugin."+Utils.getNomeModuloToJavaConvention(nomeModulo)+".mensagem";
	}
	private String getPrefixoChaveMensagem(ObjetoBumerangue objetoBumerangue){
		return "plugin."+Utils.getNomeModuloToJavaConvention(Deploy.getNomeModuloPorObjetoBumerangue(objetoBumerangue))+".mensagem";
	}

	private boolean habilitadoEnvioEmail(Usuario usuario, String tipoEmail){
		return Boolean.parseBoolean(Utils.getResourceMessage(getPrefixoChaveMensagem(usuario)+".enviaEmail"+tipoEmail));
	}
	private boolean habilitadoEnvioEmail(ObjetoBumerangue objetoBumerangue, String tipoEmail){
		return Boolean.parseBoolean(Utils.getResourceMessage(getPrefixoChaveMensagem(objetoBumerangue)+".enviaEmail"+tipoEmail));
	}

	private String getRemetente(Usuario usuario){
		return Utils.getResourceMessage(getPrefixoChaveMensagem(usuario)+".remetente");
	}
	private String getRemetente(ObjetoBumerangue objetoBumerangue){
		return Utils.getResourceMessage(getPrefixoChaveMensagem(objetoBumerangue)+".remetente");
	}

	private String getAssunto(Usuario usuario, String tipoEmail){
		String assunto = this.getPrefixoAssuntoTipoEmail(tipoEmail)+usuario.getNomeFormatado();
		return assunto;
	}
	private String getAssunto(ObjetoBumerangue objetoBumerangue, String tipoEmail){
		String assunto = this.getPrefixoAssuntoTipoEmail(tipoEmail)+objetoBumerangue.getTitulo();
		return assunto;
	}
	private String getAssunto(PlanoMissa planoMissa, String tipoEmail){
		String assunto = this.getPrefixoAssuntoTipoEmail(tipoEmail)+planoMissa.getAnoMesFormatado();
		return assunto;
	}

	private String getPrefixoAssuntoTipoEmail(String tipoEmail){
		String assuntoTipoEmail = "Bumerangue - ";
		if(TIPO_EMAIL_RESERVA.equals(tipoEmail)) assuntoTipoEmail += "Reserva";
		else if(TIPO_EMAIL_DESBLOQUEIO_USUARIO.equals(tipoEmail)) assuntoTipoEmail += "Desbloqueio do usuário";
		else if(TIPO_EMAIL_BLOQUEIO_USUARIO.equals(tipoEmail)) assuntoTipoEmail += "Bloqueio do usuário";
		else if(TIPO_EMAIL_REINICIO_SENHA.equals(tipoEmail)) assuntoTipoEmail += "Senha reiniciada para o usuário";
		else if(TIPO_EMAIL_LEMBRETE_SENHA.equals(tipoEmail)) assuntoTipoEmail += "Confirmação de nova senha";
		else if(TIPO_EMAIL_COMENTARIO_SUGESTAO.equals(tipoEmail)) assuntoTipoEmail += "Fale conosco";
		else if(TIPO_EMAIL_APROVACAO_PLANO_MISSA.equals(tipoEmail)) assuntoTipoEmail += "Plano de missa aprovado";
		return assuntoTipoEmail+": ";
		
	}

	private String getTexto(Usuario usuario, String tipoEmail){
		String fonteTexto = this.getFonteTexto(Deploy.getNomeModuloPorUsuario(usuario),tipoEmail);
		Map<String,String> elementosEL = this.getElementosEL(fonteTexto);
		
		//itera no Map, carregando os valores correspondentes �s chaves
		for(String key :elementosEL.keySet()){
			if(this.isElementoELReservado(key)){
				String[] args = null;
				if(key.equals("${urlConfirmacaoLembreteSenha}"))
					args = new String[] {usuario.getId(),usuario.getSenha()};
				elementosEL.put(key,this.getValorElementoELReservado(key,args));
			}else if(key.startsWith("${usuario"))
				elementosEL.put(key,this.getValorElementoEL(usuario,key));
		}

		return this.getFonteTextoElementosELSubstituidos(fonteTexto,elementosEL);
	}

	 private String getTexto(Usuario usuario, String textoComentario, String tipoEmail) {
		String fonteTexto = this.getFonteTexto(Deploy.getNomeModuloPorUsuario(usuario), tipoEmail);
		Map<String, String> elementosEL = this.getElementosEL(fonteTexto);

		// itera no Map, carregando os valores correspondentes �s chaves
		for (String key : elementosEL.keySet()) {
			if (this.isElementoELReservado(key)) {
				String[] args = null;
				if (key.equals("${comentarioSugestao}"))
					args = new String[] { textoComentario };
				elementosEL.put(key, this.getValorElementoELReservado(key, args));
			} else if (key.startsWith("${usuario"))
				elementosEL.put(key, this.getValorElementoEL(usuario, key));
		}

		return this.getFonteTextoElementosELSubstituidos(fonteTexto, elementosEL);
	}

	 private String getTexto(PlanoMissa planoMissa, Usuario usuario, String tipoEmail) {
		String fonteTexto = this.getFonteTexto(Deploy.MODULO_FICHA_MISSA, tipoEmail);
		Map<String, String> elementosEL = this.getElementosEL(fonteTexto);

		// itera no Map, carregando os valores correspondentes �s chaves
		for (String key : elementosEL.keySet()) {
			if (this.isElementoELReservado(key)) {
				String[] args = null;
				if(key.equals("${urlPlanoMissa}"))
					args = new String[] {planoMissa.getId()};
				elementosEL.put(key, this.getValorElementoELReservado(key, args));
			}else if (key.startsWith("${planoMissa")){
				elementosEL.put(key, this.getValorElementoEL(planoMissa, key));
			}else if (key.startsWith("${usuario")){
				elementosEL.put(key, this.getValorElementoEL(usuario, key));
			}
		}
		return this.getFonteTextoElementosELSubstituidos(fonteTexto, elementosEL);
	}

	 /**
	 * 
	 * @author Adriano Carvalho
	 * @param objetoBumerangue
	 * @param tipoEmail
	 * @return
	 */
	private String getTexto(ObjetoBumerangue objetoBumerangue, String tipoEmail){
		String fonteTexto = this.getFonteTexto(Deploy.getNomeModuloPorObjetoBumerangue(objetoBumerangue),tipoEmail);
		Map<String,String> elementosEL = this.getElementosEL(fonteTexto);
		
		//itera no Map, carregando os valores correspondentes �s chaves
		for(String key :elementosEL.keySet()){
			if(key.startsWith("${objetoBumerangue.emprestimoAtual.usuarioEmprestimo")){
				String prefixo = "${objetoBumerangue.emprestimoAtual.usuarioEmprestimo";
				Usuario usuarioEmprestimo = usuarioDao.get(this.getValorElementoEL(objetoBumerangue,prefixo+".id}"));
				String atributoUsuario = key.substring(prefixo.length());
				elementosEL.put(key,this.getValorElementoEL(usuarioEmprestimo,"${"+atributoUsuario));
			}else if(this.isElementoELReservado(key)){
				String[] args = null;
				if(key.equals("${urlObjetoBumerangue}"))
					args = new String[] {objetoBumerangue.getClass().getSimpleName(),objetoBumerangue.getId()};
				elementosEL.put(key,this.getValorElementoELReservado(key,args));
			}else if(key.startsWith("${objetoBumerangue")){
				elementosEL.put(key,this.getValorElementoEL(objetoBumerangue,key));
			}
		}

		return this.getFonteTextoElementosELSubstituidos(fonteTexto,elementosEL);
	}

	/**
	 * Retorna o 'fonteTexto' alterado, sustituindo as chaves contidas no Map 'elementosEL' por seus valores. 
	 * @author Adriano Carvalho
	 * @param fonteTexto
	 * @param elementosEL
	 * @return
	 */
	private String getFonteTextoElementosELSubstituidos(String fonteTexto, Map<String,String> elementosEL){
		String novoTexto = fonteTexto;
		for(String key : elementosEL.keySet()){
			novoTexto = StringUtils.replace(novoTexto,key,elementosEL.get(key));
		}
		return novoTexto;
	}
	
	/**
	 * Retorna o c�digo fonte (html) correspondente ao corpo do e-mail,<br>
	 * de acordo com o m�dulo espec�fico ou com o fonte padr�o e o 'tipoEmail'.<br>
	 * O fonte � capaz de trabalhar com uma sintaxe especial, semelhante a EL. Ex: ${objetoBumerangue.titulo}.<br>
	 * Logo, de acordo com os objetos dispon�veis na p�gina, seus valores ser�o colocados formatados.<br>
	 * Caso a interpreta��o da sintaxe d� algum problema, sar� colocado um valor vazio no local. 
	 * @author Adriano Carvalho
	 * @param objetoBumerangue
	 * @param tipoEmail
	 * @return
	 */
	private String getFonteTexto(String nomeModulo, String tipoEmail){
		String prefixo = super.getWebRealPath()+"/templateMail/";
		prefixo = StringUtils.replace(prefixo,"\\",File.separator);
		prefixo = StringUtils.replace(prefixo,"/",File.separator);
		String sufixo = tipoEmail+".html";
		String templateMailPath = nomeModulo == null ? "" : prefixo+Utils.getNomeModuloToJavaConvention(nomeModulo)+sufixo;
		//1� tenta recuperar o template de e-mail, customizado pelo m�dulo.
		File file = new File(templateMailPath);
		//Se n�o tiver recupera o template padr�o para todos os m�dulos
		if(!file.isFile()){
			templateMailPath = prefixo+"padrao"+sufixo;
			file = new File(templateMailPath);
		}

		StringBuffer fonteTexto = new StringBuffer("");
		try {
			InputStream inputStream = new FileInputStream(file);

			InputStreamReader isr = new InputStreamReader(inputStream);
			BufferedReader conteudo = new BufferedReader( isr );

			String linha = null;
			while ((linha = conteudo.readLine()) != null){
				fonteTexto.append(linha);
			}
			conteudo.close();
			isr.close();
			inputStream.close();
		} catch (Exception e) {
			throw new BumerangueErrorRuntimeException(e.getMessage());
		}
		return fonteTexto.toString();
	}

	/**
	 * Retorna um Map, no qual suas chaves s�o os elementos EL encontrados no 'fonteTexto'.<br>
	 * Ex: O fonte com 2 elementos EL, o retorno ser� uma Map com as chaves:<br>
	 *     ${objetoBumerangue.titulo} e ${objetoBumerangue.emprestimoAtual.usuarioEmprestimo.nomeFormatado}
	 * @author Adriano Carvalho
	 * @param fonteTexto
	 * @return
	 */
	private Map<String,String> getElementosEL(String fonteTexto){
		Map<String,String> elementosEL = new HashMap<String,String>(); 
		String[] chaves = StringUtils.split(fonteTexto,"${");
		for(String key : chaves){
			if(StringUtils.indexOf(key,'}') == -1) continue;
			key = "${"+key.substring(0,key.indexOf('}')+1);
			elementosEL.put(key,"");
		}
		return elementosEL;
	}

	/**
	 * Retorna o valor do elemento EL em 'objeto', a partir da 'chaveElementoEL'.<br>
	 * Usa-se reflex�o para navegar entre os objetos separados por .(ponto).<br>
	 * O valor atributo � formatado e convertido amigavelmente para String, da� retornado.<br>
	 * Ex1: para a chave ${objetoBumerangue.emprestimoAtual.usuarioEmprestimo.nomeFormatado}, retorna:<br>
	 *      objeto.getEmprestimoAtual().getUsuarioEmprestimo().getNomeFormatado(), convertido em String.<br>
	 * Ex2: para a chave ${objetoBumerangue}, retorna:<br>
	 *      objeto, convertido em String.<br>
	 * @author Adriano Carvalho
	 * @param chaveElementoEL
	 * @return
	 */
	private String getValorElementoEL(Object objeto, String chaveElementoEL){
		String nomeAtributos = chaveElementoEL.substring(2,chaveElementoEL.length()-1);
		String[] atributos = StringUtils.replace(nomeAtributos,".",";").split(";");
		String nomePrimeiroAtributo = atributos[0];

		Object valor = null;
		if(atributos.length == 1)
			valor = objeto;
		else{
			String nomeProximosAtributos = nomeAtributos.substring(nomeAtributos.indexOf(".")+1);
			valor = Utils.getAtributo(objeto, nomeProximosAtributos);
		}
		return Utils.getFormatted(valor);
	}
	
	/**
	 * Retorna o valor do elemento EL reservado.<br>
	 * O valor atributo � formatado e convertido amigavelmente para String, da� retornado.<br>
	 * Os elementos EL reservados s�o:<br>
	 * ${url}<br>
	 * ${urlReal}<br>
	 * ${urlLogo}<br>
	 * ${urlObjetoBumerangue}, arg[0] = 'Nome da Classe do Objeto Bumerangue', arg[1] = 'id do objetoBumarengue'<br>
	 * ${urlConfirmacaoLembreteSenha}, arg[0] = 'id do Usu�rio', arg[1] = 'senha criptografada'<br>
	 * ${senhaPadraoUsuario}<br>
	 * ${comentarioSugestao}, arg[0] = 'Texto do coment�rio'
	 * @author Adriano Carvalho
	 * @param chaveElementoELReservado
	 * @param args
	 * @return
	 */
	private String getValorElementoELReservado(String chaveElementoELReservado, String[] args){
		String valor = Utils.getResourceMessage("bmg.apresentacao.urlReal");

		if(chaveElementoELReservado.equals("${url}")){
			valor = Utils.getResourceMessage("bmg.apresentacao.url");
		}else if(chaveElementoELReservado.equals("${urlReal}")){
		}else if(chaveElementoELReservado.equals("${urlLogo}")){
			valor += "/resources/img/logo0.gif";
		}else if(chaveElementoELReservado.equals("${urlObjetoBumerangue}")){
			valor += "/manter"+args[0]+".action?method=detalhar&id="+args[1];
		}else if(chaveElementoELReservado.equals("${urlConfirmacaoLembreteSenha}")){
			valor += "/manterSegurancaLivreAcesso.action?method=lembrarSenhaConfirmacao&idUsuario="+args[0]+"&id5="+args[1];
		}else if(chaveElementoELReservado.equals("${urlPlanoMissa}")){
			valor += "/manterPlanoMissa.action?method=detalhar&id="+args[0];
		}else if(chaveElementoELReservado.equals("${senhaPadraoUsuario}")){
			valor = Utils.getResourceMessage("bmg.manterSeguranca.senhaPadrao");
		}else if(chaveElementoELReservado.equals("${comentarioSugestao}")){
			valor = args[0];
		}else{
			valor = "";
		}

		return valor;
	}

	/**
	 * Retorna true, caso seja a chave de um elemento EL reservado.
	 * @param chaveElementoEL
	 * @return
	 */
	private boolean isElementoELReservado(String chaveElementoEL){
		return chaveElementoEL.equals("${url}")
				|| chaveElementoEL.equals("${urlReal}")
				|| chaveElementoEL.equals("${urlLogo}")
				|| chaveElementoEL.equals("${urlObjetoBumerangue}")
				|| chaveElementoEL.equals("${urlPlanoMissa}")
				|| chaveElementoEL.equals("${urlConfirmacaoLembreteSenha}")
				|| chaveElementoEL.equals("${senhaPadraoUsuario}")
				|| chaveElementoEL.equals("${comentarioSugestao}");
	}

}