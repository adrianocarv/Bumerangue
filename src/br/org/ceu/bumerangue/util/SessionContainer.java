package br.org.ceu.bumerangue.util;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import br.org.ceu.bumerangue.entity.Emprestimo;
import br.org.ceu.bumerangue.entity.ObjetoBumerangue;
import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.entity.suporte.Arquivo;
import br.org.ceu.bumerangue.entity.suporte.ElementoVerificacaoCodigo;
import br.org.ceu.bumerangue.entity.suporte.ResultadoVerificacaoCodigo;
import br.org.ceu.bumerangue.entity.suporte.deploy.VersionEnum;

/**
 * Agregador de objetos da Session
 * 
 */
public class SessionContainer implements HttpSessionListener {

	public static final String SESSION_CONTAINER_ATTRIBUTE_NAME = "S_C";

	//atributo usados na tela template.jsp para configurar o quadro padrão de mensagens da aplicação.
	public static final String REQUEST_ATTRIBUTE_MESSAGES = "messages";
	public static final String REQUEST_ATTRIBUTE_MESSAGE_ICO_FILE_NAME = "icoMsgFileName";
	public static final String REQUEST_ATTRIBUTE_MESSAGE_ICO_ERROR = "icoMsgError.jpg";
	public static final String REQUEST_ATTRIBUTE_MESSAGE_ICO_WARN = "icoMsgWarn.jpg";
	public static final String REQUEST_ATTRIBUTE_MESSAGE_ICO_SUCCESS = "icoMsgSuccess.gif";
	
	//atributo usado nas telas de listagem com DisplayTag,<br>
	//passado como parâmetro no atributo 'requestURI' da tag 'display:table'.
	public static final String REQUEST_ATTRIBUTE_PAGINACAO = "paginacao";

	//atributos usados na tela template.jsp
	public static final String SESSION_ATTRIBUTE_HIDE_TEMPLATE = "hideTemplate";
	public static final String SESSION_ATTRIBUTE_URL_CSS = "urlCSS";

	/* Usuario logado */
    private Usuario usuarioLogado;
    public Usuario getUsuarioLogado() { return usuarioLogado; }
    public void setUsuarioLogado(Usuario usuarioLogado) { this.usuarioLogado = usuarioLogado; }
    
    /* Versão */
    public String getVersao() { return VersionEnum.VERSAO.value(); }
    
	/* Notificações */
    private Map<String,String> notificacoes;
    public Map<String,String> getNotificacoes() { return notificacoes; }
    public void setNotificacoes(Map<String,String> notificacoes) { this.notificacoes = notificacoes; }

    /* Template */
    private boolean hideTemplate;
    public boolean getHideTemplate() { return hideTemplate; }
    public void setHideTemplate(boolean hideTemplate) { this.hideTemplate = hideTemplate; }

    private String urlCSS;
    public String getUrlCSS() { return urlCSS; }
    public void setUrlCSS(String urlCSS) { this.urlCSS = urlCSS; }
    
    /* Listas e objetos usados nas páginas de listagem com DisplayTag */
    //objetosBumerangue
    private List<ObjetoBumerangue> objetosBumerangue;
    public List<ObjetoBumerangue> getObjetosBumerangue() { return objetosBumerangue; }
    public void setObjetosBumerangue(List<ObjetoBumerangue> objetosBumerangue) { this.objetosBumerangue = objetosBumerangue; }
    
    //emprestimos
    private List<Emprestimo> emprestimos;
    public List<Emprestimo> getEmprestimos() { return emprestimos; }
    public void setEmprestimos(List<Emprestimo> emprestimos) { this.emprestimos = emprestimos; }

    //usuarios
    private List<Usuario> usuarios;
    public List<Usuario> getUsuarios() { return usuarios; }
    public void setUsuarios(List<Usuario> usuarios) { this.usuarios = usuarios; }

    //arquivos
    private List<Arquivo> arquivos;
    public List<Arquivo> getArquivos() { return arquivos; }
    public void setArquivos(List<Arquivo> arquivos) { this.arquivos = arquivos; }

    //elementoVerificacaoCodigos
    private List<ElementoVerificacaoCodigo> elementoVerificacaoCodigos;
    public List<ElementoVerificacaoCodigo> getElementoVerificacaoCodigos() { return elementoVerificacaoCodigos; }
    public void setElementoVerificacaoCodigos(List<ElementoVerificacaoCodigo> elementoVerificacaoCodigos) { this.elementoVerificacaoCodigos = elementoVerificacaoCodigos; }
    
    //resultadoVerificacaoCodigo
    private ResultadoVerificacaoCodigo resultadoVerificacaoCodigo;
    public ResultadoVerificacaoCodigo getResultadoVerificacaoCodigo() { return resultadoVerificacaoCodigo; }
    public void setResultadoVerificacaoCodigo(ResultadoVerificacaoCodigo resultadoVerificacaoCodigo) { this.resultadoVerificacaoCodigo = resultadoVerificacaoCodigo; }

    /* Listas usadas no componente Autocompletar do DWR */
    //listaAutoCompletar1
    private List<? extends Object> listaAutoCompletar1;
    public List<? extends Object> getListaAutoCompletar1() { return listaAutoCompletar1; }
    public void setListaAutoCompletar1(List<? extends Object> listaAutoCompletar1) { this.listaAutoCompletar1 = listaAutoCompletar1; }

    //listaAutoCompletar2
    private List<? extends Object> listaAutoCompletar2;
    public List<? extends Object> getListaAutoCompletar2() { return listaAutoCompletar2; }
    public void setListaAutoCompletar2(List<? extends Object> listaAutoCompletar2) { this.listaAutoCompletar2 = listaAutoCompletar2; }

    //
	// Constructors
	//
	public SessionContainer(){}
	public void sessionCreated(HttpSessionEvent arg0) {}
	public void sessionDestroyed(HttpSessionEvent arg0) {}
}
