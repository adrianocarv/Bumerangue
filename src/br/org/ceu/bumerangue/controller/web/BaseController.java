package br.org.ceu.bumerangue.controller.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import br.org.ceu.bumerangue.service.exceptions.BumerangueAlertRuntimeException;
import br.org.ceu.bumerangue.service.exceptions.BumerangueErrorRuntimeException;
import br.org.ceu.bumerangue.service.exceptions.BumerangueRuntimeException;
import br.org.ceu.bumerangue.service.exceptions.BumerangueSuccessRuntimeException;
import br.org.ceu.bumerangue.util.ControllerHelper;
import br.org.ceu.bumerangue.util.SessionContainer;
import br.org.ceu.bumerangue.util.TokenUtils;
import br.org.ceu.bumerangue.util.Utils;
import br.org.ceu.bumerangue.util.ValidationRules;

/**
 * Classe base para todos os Controllers.
 * 
 */
public abstract class BaseController extends MultiActionController implements MessageSourceAware {

	// -- Logger para esta classe e suas subclasses
    // -------------------------------------------------------
    protected final Log logger = LogFactory.getLog(getClass());

    // -----------------------------------------------------------------------------------------------------

    // -- Thread Local para errorMessages + getter/setter
    // -------------------------------------------------
    private static final ThreadLocal threadErrorMessages = new ThreadLocal();

    protected static List getErrorMessages() {
        return (List) threadErrorMessages.get();
    }

    private static void setErrorMessages(List errorMessages) {
        threadErrorMessages.set(errorMessages);
    }

    // -----------------------------------------------------------------------------------------------------

    // -- Thread Local para Objetos do Model + getter/setter
    // -----------------------------------------------
    private static final ThreadLocal threadModelObjects = new ThreadLocal();

    private static Map getModelObjects() {
        return (Map) threadModelObjects.get();
    }

    private static void setModelObjects(Map modelObjects) {
        threadModelObjects.set(modelObjects);
    }

    // -----------------------------------------------------------------------------------------------------
    // -- Resolver para Multipart (upload)
    // ----------------------------------------------------------------------
    private static MultipartResolver multipartResolver;
    public void setMultipartResolver(MultipartResolver multipartResolver) { this.multipartResolver = multipartResolver; }

    // -----------------------------------------------------------------------------------------------------

    // -- Thread Local para Request + getter/setter
    // --------------------------------------------------------
    private static final ThreadLocal threadRequest = new ThreadLocal();

    protected static HttpServletRequest getRequest() {
        return (HttpServletRequest) threadRequest.get();
    }

    private static void setRequest(HttpServletRequest request){
        if(multipartResolver.isMultipart(request))
        {
            try {
                request = multipartResolver.resolveMultipart(request);
            }
            catch(MaxUploadSizeExceededException e){
                throw new BumerangueErrorRuntimeException("Tamanho méximo de arquivo excedido");
            } catch (MultipartException e) {
                throw new BumerangueErrorRuntimeException("Erro ao setar multipartRequest");
            }
        }
        
        threadRequest.set(request);
    }

    // -----------------------------------------------------------------------------------------------------

    // -- Thread Local para Response + getter/setter
    // --------------------------------------------------------
    private static final ThreadLocal threadResponse = new ThreadLocal();

    protected static HttpServletResponse getResponse() {
        return (HttpServletResponse) threadResponse.get();
    }

    private static void setResponse(HttpServletResponse response) {
        threadResponse.set(response);
    }

    // -----------------------------------------------------------------------------------------------------

    // -- Spring MessageSource (i18n)
    // ----------------------------------------------------------------------
    protected MessageSourceAccessor messageSourceAccessor;

    public void setMessageSource(MessageSource messageSource) {
        this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
    }

    // -----------------------------------------------------------------------------------------------------

    // -- Controller Helper
    // --------------------------------------------------------------------------------
    protected ControllerHelper controllerHelper;

    public void setControllerHelper(ControllerHelper controllerHelper) {
        this.controllerHelper = controllerHelper;
    }

    // -----------------------------------------------------------------------------------------------------

    private Utils utils;

    public void setUtils(Utils u) {
        this.utils = u;
    }

    /**
     * Recupera o SessionContainer da sessão
     */
    protected SessionContainer getSessionContainer() {
		SessionContainer sc = (SessionContainer) getRequest().getSession().getAttribute(SessionContainer.SESSION_CONTAINER_ATTRIBUTE_NAME);

		/* Se aindanão existir um sessionContainer na sessão, cria um novo. */
		if (sc == null) {
			sc = new SessionContainer();
			getRequest().getSession().setAttribute(SessionContainer.SESSION_CONTAINER_ATTRIBUTE_NAME, sc);
		}
		return sc;
	}

    /**
	 * Salva o arquivo de upload, especificando um diretorio de destino.
	 * 
	 * @param htmlFieldName
	 *            nome do campo html que contem o nome do arquivo do upload
	 * @param folderDest
	 *            diretério de destino para onde seré transferido o arquivo
	 * @param maxSize
	 *            tamanho méximo, em bytes, do arquivo de upload. Se for 0,
	 *            ignora o tamanho maximo.
	 * @throws UploadException
	 * @throws MaxUploadSizeExceededException
	 */
    protected void saveUploadedFile(String htmlFieldName, File folderDest,
            long maxSize) {

        try {
            MultipartFile file = new CommonsMultipartResolver()
                    .resolveMultipart(getRequest()).getFile(htmlFieldName);
            if (file != null) {
                file.transferTo(folderDest);

                /* verifica se o tamanho do arquivo é menor ou igual a maxSize */
                if (maxSize != 0 && file.getSize() <= maxSize) {
                    throw new MaxUploadSizeExceededException(maxSize);
                }
            }

        } catch (Exception e) {
            throw new BumerangueErrorRuntimeException(
                    "Ocorreu um problema ao salvar o arquivo '" + htmlFieldName
                            + "' de upload", e);
        }

    }

    /**
     * 
     * 
     */
    protected MultipartFile getFile(String htmlFieldName) {
        MultipartFile file = null;
        try {
            file = new CommonsMultipartResolver()
                    .resolveMultipart(getRequest()).getFile(htmlFieldName);

        } catch (Exception e) {
            throw new BumerangueErrorRuntimeException(
                    "Ocorreu um problema ao salvar o arquivo '" + htmlFieldName
                            + "' de upload", e);
        }
        return file;
    }

    /**
     * 
     * 
     */
    protected void saveFile(MultipartFile file, String path) {
        try {
            file.transferTo(new File(path));
        } catch (IOException e) {
            throw new BumerangueErrorRuntimeException(
                    "Ocorreu um problema ao salvar o arquivo de upload", e);
        }

    }

    private boolean authRequired;

    public void setAuthRequired(boolean authRequired) {
        this.authRequired = authRequired;
    }

    public boolean getAuthRequired() {
        return this.authRequired;
    }

    /**
     * Método Entry-Point chamado pelo Spring MVC.
     */
	public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ModelAndView modelAndView = null;

        /* Inicializa os ThreadLocal */
        setErrorMessages(Collections.EMPTY_LIST);
        setModelObjects(Collections.EMPTY_MAP);
        setRequest(request);
        setResponse(response);

        /* Valida Token, caso exista */
        if (request.getParameter(TokenUtils.TOKEN_KEY) != null) {
        	//é necessério validar? 
            if (!isTokenValid()) {
                throw new BumerangueErrorRuntimeException("Token Invélido.");
            } else {
                resetToken();
            }
        }

        /* Faz a validação dos parametros. Casonão tenha erro, executa a action */
    	//captura alguma excesséo de negócio, caso haja.
        try{
    		/* Faz a validação dos parametros */
    		validate();
    		ModelAndView validateMulti = validateMulti(request,response);
    		if(validateMulti != null) this.validationViewName = validateMulti.getViewName();
            if (getErrorMessages().isEmpty()){
            	modelAndView = execute();
            }else if(this.getValidationViewName() != null)
            	modelAndView = new ModelAndView(this.getValidationViewName());
        }catch (Exception e) {
            addError(e.getMessage()+"");

            if (e instanceof BumerangueAlertRuntimeException) messageType = 2;
            else if (e instanceof BumerangueSuccessRuntimeException) messageType = 3;
            if(ValidationRules.isInformed(this.getValidationViewName())) modelAndView = new ModelAndView(this.getValidationViewName());

            if ( !(e instanceof BumerangueRuntimeException) ) {
            	e.printStackTrace();
            	modelAndView =  null;
			}
        }

        if (modelAndView == null) modelAndView = new ModelAndView("error");

        /*
         * Gera token SEMPRE. Se precisar desta funcionalidade, basta a pagina
         * guardar e reenviar o token.
         */
        addObject("TOKEN_KEY", TokenUtils.TOKEN_KEY);
        addObject("TRANSACTION_TOKEN_KEY", saveToken());

        /* Adiciona as mensagens do list para o modelAndView */
        if (!getErrorMessages().isEmpty()) {
        	modelAndView.addObject(SessionContainer.REQUEST_ATTRIBUTE_MESSAGES, getErrorMessages());
        	modelAndView.addObject(SessionContainer.REQUEST_ATTRIBUTE_MESSAGE_ICO_FILE_NAME, SessionContainer.REQUEST_ATTRIBUTE_MESSAGE_ICO_ERROR);
        	if (messageType == 2 ) modelAndView.addObject(SessionContainer.REQUEST_ATTRIBUTE_MESSAGE_ICO_FILE_NAME, SessionContainer.REQUEST_ATTRIBUTE_MESSAGE_ICO_WARN);
        	else if (messageType == 3 ) modelAndView.addObject(SessionContainer.REQUEST_ATTRIBUTE_MESSAGE_ICO_FILE_NAME, SessionContainer.REQUEST_ATTRIBUTE_MESSAGE_ICO_SUCCESS);
        }

        addObject("req", request);
        addObject("utils", utils);

        /* Adiciona os objetos do map para o modelAndView */
        modelAndView.addAllObjects(getModelObjects());
        return modelAndView;
    }

    private String validationViewName;

    public String getValidationViewName() {
        return validationViewName;
    }

    public void setValidationViewName(String validationViewName) {
        this.validationViewName = validationViewName;
    }

    private int messageType = 1;

    /**
	 * Método para validaééo da Action (Single)
     */
    protected abstract void validate();

	/**
	 * Validação obrigatória para Controllers MultiActionBaseController
	 * @author Adriano Carvalho
	 * @param request
	 * @param response
	 * @return
	 */
	protected ModelAndView validateMulti(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

    /**
     * Método abstrato Entry-Point que as subclasses devem implementar
     */
    public abstract ModelAndView execute() throws Exception;

    /**
     * Adiciona uma Mensagem de Erro.
     * @param message
     */
    protected void addError(String message) {
    	messageType = 1;
    	this.addMessage(message);
    }

    /**
     * Adiciona uma Mensagem de Alerta.
     * @param message
     */
    protected void addAlert(String message) {
    	messageType = 2;
    	this.addMessage(message);
    }

    /**
     * Adiciona uma Mensagem de Sucesso.
     * @param message
     */
    protected void addSuccess(String message) {
    	messageType = 3;
    	this.addMessage(message);
    }

    /**
     * Adiciona uma Mensagem.
     * @param message
     */
    private void addMessage(String message) {

        /* Se for a primeira chamada (lista vazia), inicializa a lista. */
        if (getErrorMessages().isEmpty()) {
            setErrorMessages(new ArrayList());
        }

        /* adiciona o erro! */
        getErrorMessages().add(message);
    }

    /**
     * Adiciona um objeto no model.
     * 
     * @param param
     *            nome do atributo.
     * @param valor
     *            do atributo.
     */
    protected void addObject(String key, Object value) {

        /* Se for a primeira chamada (EMPTY_MAP), inicializa o map. */
        if (getModelObjects().isEmpty()) {
            setModelObjects(new HashMap());
        }

        /* adiciona o objeto! */
        getModelObjects().put(key, value);

    }

    //
    // Helpers para i18n
    //

    /**
     * Recupera mensagem de um Resource Bundle através de sua chave
     * 
     * @param key
     *            chave da mensagem
     * @return mensagem recuperada do Resource Bundle, ou null caso a mensagem
     *        não seja encontrada.
     */
    protected String getResourceMessage(String key) {

        return getResourceMessage(key, null);

    }

    /**
     * Recupera mensagem de um Resource Bundle através de sua chave, e faz o
     * binding de um parametro
     * 
     * @param key
     *            chave da mensagem
     * @return mensagem recuperada do Resource Bundle, ou null caso a mensagem
     *        não seja encontrada.
     */
    protected String getResourceMessage(String key, Object param) {

        return getResourceMessage(key, new Object[] { param });

    }

    /**
     * Recupera mensagem de um Resource Bundle através de sua chave, e faz o
     * binding dos parametros
     * 
     * @param key
     *            chave da mensagem
     * @return mensagem recuperada do Resource Bundle, ou null caso a mensagem
     *        não seja encontrada.
     */
    protected String getResourceMessage(String key, Object[] params) {

        if (key == null) {
            return null;
        }

        String msg = null;
        if (params == null || params.length == 0) {
            msg = messageSourceAccessor.getMessage(key);
        } else {
            msg = messageSourceAccessor.getMessage(key, params);
        }

        return msg;

    }

    /**
     * Método conveniente para recuperar uma mensagem como Long.
     * 
     * @param key
     *            chave da mensagem
     * @return mensagem recuperada do Resource Bundle como Long, ou null caso a
     *         mensagemnão seja encontrada.
     */
    protected Long getResourceMessageAsLong(String key) {

        String s = getResourceMessage(key);

        if (s != null) {
            return new Long(s);
        } else {
            return null;
        }

    }

    //
    // Métodos convenientes para manipulaééo com Tokens
    //

    protected String generateToken() {
        return controllerHelper.getTokenUtils().generateToken(getRequest());
    }

    protected boolean isTokenValid() {
        return controllerHelper.getTokenUtils().isTokenValid(getRequest(),
                false);
    }

    protected boolean isTokenValid(boolean reset) {
        return controllerHelper.getTokenUtils().isTokenValid(getRequest(),
                reset);
    }

    protected void resetToken() {
        controllerHelper.getTokenUtils().resetToken(getRequest());
    }

    protected String saveToken() {
        return controllerHelper.getTokenUtils().saveToken(getRequest());
    }

    //
    // Métodos convenientes para recuperaééo/atribuiééo de dados do Request
    //

    /**
     * Recupera o valor de um campo do request como String.
     * 
     * @param param
     *            nome do campo do request.
     * @return valor do campo.
     * @throws DataFormatterException
     *             se ocorrer algum problema durante a recuperaééo de valor do
     *             campo do request.
     */
    protected String getParam(String param) {
        return controllerHelper.getRequestParamUtils().getRequestParameter(
                getRequest(), param);
    }
    
    protected String getAlias(String baseName) {
        return controllerHelper.getRequestParamUtils().getAliasFromURL(
                getRequest(), baseName);
    }

    /**
     * Recupera o valor de um campo do request como String. Se este valor for
     * igual ao valor informado no parametro nullValue, retorna null.
     * 
     * @param param
     *            nome do campo do request.
     * @param nullValue
     *            valor que indica a condiééo para que retorne null.
     * @return valor do campo.
     * @throws DataFormatterException
     *             se ocorrer algum problema durante a recuperaééo de valor do
     *             campo do request.
     */
    protected String getParam(String param, String nullValue) {
        return controllerHelper.getRequestParamUtils().getRequestParameter(
                getRequest(), param, nullValue);
    }

    /**
     * Recupera o valor de um ou mais campos do request como List.
     * 
     * @param param
     *            nome do campo do request.
     * @return valor do campo.
     * @throws DataFormatterException
     *             se ocorrer algum problema durante a recuperaééo de valor do
     *             campo do request.
     */
    protected List getParamList(String param) {
        return controllerHelper.getRequestParamUtils().getRequestParameterList(
                getRequest(), param);
    }

    /**
     * Recupera o valor de um campo do request como Double.
     * 
     * @param param
     *            nome do campo do request.
     * @return valor do campo convertido em Double ou null caso o campo esteja
     *         vazio.
     * @throws DataFormatterException
     *             se ocorrer algum problema durante a recuperaééo de valor do
     *             campo do request.
     */
    protected Double getParamAsDouble(String param) {
        return controllerHelper.getRequestParamUtils()
                .getRequestParameterAsDouble(getRequest(), param);
    }

    /**
     * Recupera o valor de um campo do request como Integer.
     * 
     * @param param
     *            nome do campo do request.
     * @return valor do campo convertido em Integer ou null caso o campo esteja
     *         vazio.
     * @throws DataFormatterException
     *             se ocorrer algum problema durante a recuperaééo de valor do
     *             campo do request.
     */
    protected Integer getParamAsInteger(String param) {
        return controllerHelper.getRequestParamUtils()
                .getRequestParameterAsInteger(getRequest(), param);
    }

    /**
     * Recupera o valor de um campo do request como Long.
     * 
     * @param param
     *            nome do campo do request.
     * @return valor do campo convertido em Long ou null caso o campo esteja
     *         vazio.
     * @throws DataFormatterException
     *             se ocorrer algum problema durante a recuperaééo de valor do
     *             campo do request.
     */
    protected Long getParamAsLong(String param) {
        return controllerHelper.getRequestParamUtils()
                .getRequestParameterAsLong(getRequest(), param);
    }

    /**
     * Recupera o valor de um campo do request como Long. Se este valor for
     * igual ao valor informado no parametro nullValue, retorna null.
     * 
     * @param param
     *            nome do campo do request.
     * @param nullValue
     *            valor que indica a condiééo para que retorne null.
     * @return valor do campo convertido em Long, ou null, caso o valor
     *         encontrado no campo seja igual. ao valor do parametrto nullValue.
     * @throws DataFormatterException
     *             se ocorrer algum problema durante a recuperaééo de valor do
     *             campo do request.
     */
    protected Long getParamAsLong(String param, String nullValue) {
        return controllerHelper.getRequestParamUtils()
                .getRequestParameterAsLong(getRequest(), param, nullValue);
    }

    /**
     * Recupera um objeto java.util.Date através de um campo string que contém
     * uma data, e, opcionalmente, através de um outro campo que contém um
     * horario. O pattern da data e da hora utilizado no parse seré aquele
     * definido no controller-config como pattern.date e pattern.time,
     * respectivamente.
     * 
     * @param paramDate
     *            nome do campo do request que contém a data.
     * @param paramTime
     *            nome do campo do request que contém as horas.
     * @return valor dos campos convertidos em java.util.Date ou null caso o
     *         campo esteja vazio.
     * @throws DataFormatterException
     *             se ocorrer algum problema durante a recuperaééo de valor do
     *             campo do request.
     */
    protected Date getParamAsDateAndTime(String paramDate, String paramTime) {
        return controllerHelper.getRequestParamUtils()
                .getRequestParameterAsDate(getRequest(), paramDate, paramTime,
                        getResourceMessage("pattern.date"),
                        getResourceMessage("pattern.time"));
    }

    /**
     * Recupera um objeto java.util.Date através de um campo string que contém
     * uma data. O pattern da data utilizado no parse seré aquele definido no
     * controller-config como pattern.date.
     * 
     * @param paramDate
     *            nome do campo do request que contém a data.
     * @param full
     *            flag que indica se deve preencher o horario com valor cheio
     *            (23:59:59.999).
     * @return valor do campo convertido em java.util.Date, ou null caso o campo
     *         esteja vazio.
     * @throws DataFormatterException
     *             se ocorrer algum problema durante a recuperaééo do valor.
     */
    protected Date getParamAsDate(String paramDate, boolean full) {
        return controllerHelper.getRequestParamUtils()
                .getRequestParameterAsDate(getRequest(), paramDate,
                        getResourceMessage("pattern.date"), full);
    }

    /**
     * Recupera um objeto java.util.Date através de um campo string que contém
     * uma data. O pattern da data utilizado no parse seré aquele definido no
     * controller-config como pattern.date. Ps.: Néo preenche o horario com
     * valor cheio (23:59:59.999).
     * 
     * @param paramDate
     *            nome do campo do request que contém a data.
     * @return valor do campo convertido em java.util.Date ou null caso o campo
     *         esteja vazio.
     * @throws DataFormatterException
     *             se ocorrer algum problema durante a recuperaééo de valor do
     *             campo do request.
     */
    protected Date getParamAsDate(String paramDate) {
        return controllerHelper.getRequestParamUtils()
                .getRequestParameterAsDate(getRequest(), paramDate,
                        getResourceMessage("pattern.date"));
    }

    /**
     * Recupera um objeto java.util.Date através de um campo string que contém
     * uma data. O pattern da data utilizado no parse seré o valor da key
     * (passado por parametro) definido no controller-config. Ps.: Néo preenche
     * o horario com valor cheio (23:59:59.999).
     * 
     * @param paramDate
     *            nome do campo do request que contém a data.
     * @param datePatternKey
     *            o nome da chave do controller-config que contém o pattern
     * @return valor do campo convertido em java.util.Date ou null caso o campo
     *         esteja vazio.
     * @throws DataFormatterException
     *             se ocorrer algum problema durante a recuperaééo de valor do
     *             campo do request.
     */
    protected Date getParamAsDate(String paramDate, String datePatternKey) {
        return controllerHelper.getRequestParamUtils()
                .getRequestParameterAsDate(getRequest(), paramDate,
                        getResourceMessage(datePatternKey));
    }

    /**
     * Recupera um objeto java.util.Date através de um campo string que contém
     * uma data, e, opcionalmente, através de um outro campo que contém um
     * horario. O pattern de data/hora utilizado no parse seré o valor das keys
     * (passado por parametro) definido no controller-config.
     * 
     * @param paramDate
     *            nome do campo do request que contém a data.
     * @param paramTime
     *            nome do campo do request que contém as horas.
     * @return valor dos campos convertidos em java.util.Date ou null caso o
     *         campo esteja vazio.
     * @throws DataFormatterException
     *             se ocorrer algum problema durante a recuperaééo de valor do
     *             campo do request.
     */
    protected Date getParamAsDateAndTime(String paramDate, String paramTime,
            String datePatternKey, String timePatternKey) {
        return controllerHelper.getRequestParamUtils()
                .getRequestParameterAsDate(getRequest(), paramDate, paramTime,
                        getResourceMessage(datePatternKey),
                        getResourceMessage(timePatternKey));
    }

    /**
     * Recupera o valor de um campo do request como Boolean.
     * 
     * @param param
     *            nome do campo do request.
     * @return valor do campo convertido em Boolean ou null caso o campo esteja
     *         vazio.
     * @throws DataFormatterException
     *             se ocorrer algum problema durante a recuperaééo de valor do
     *             campo do request.
     */
    protected Boolean getParamAsBoolean(String param) {
        return controllerHelper.getRequestParamUtils()
                .getRequestParameterAsBoolean(getRequest(), param);
    }
    
    protected MultipartFile getParamAsFile(String param) {
        return controllerHelper.getRequestParamUtils()
        .getRequestParameterAsFile(getRequest(), param);
    }
    

    /**
     * addiciona um atributo no Request.
     * 
     * @param param
     *            nome do atributo.
     * @param valor
     *            do atributo.
     */
    protected void addReqAttr(String param, Object value) {
        controllerHelper.getRequestParamUtils().setRequestAttribute(
                getRequest(), param, value);
    }

    //------------------------------------------------------------------------------
	/** Métodos Usados para validaééo dos formulérios da camada de apresentaééo */
	//------------------------------------------------------------------------------
    
    // ------------------------------------------------------------------------------
    /**
     * Verifica se o valor informado é diferente de null e diferente de branco
     * Se a validacaonão estiver de acordo, adiciona um erro.
     */
    // ------------------------------------------------------------------------------
    protected boolean isInformed(String fieldName, String fieldAlias) {
        String value = getParam(fieldName);
        boolean ok = ValidationRules.isInformed(value);
        if (!ok)
            addError(getResourceMessage("errors.required", fieldAlias));
        return ok;
    }

	//------------------------------------------------------------------------------
	/** Verifica se o valor tem a largura compreendida entre os valores min e max (extremidades).
	 *  Para desconsiderar a validaééo de qualquer uma dessas extremidades, use um valor negativo (-1) no parémetro.
	 *  Se a validacaonão estiver de acordo, adiciona um erro. */
	//------------------------------------------------------------------------------
    protected boolean isValidRange( String fieldName, int min, int max, String fieldAlias) {
        String value = getParam(fieldName);
		boolean ok = ValidationRules.isValidRange(value,min,max) ;
		if( !ok ) {
		    if(min >= 0 && max >= 0){
	            addError(getResourceMessage("errors.rangelength", new Object[]{fieldAlias,min+"",max+""}));
		    }else if(min >= 0){
	            addError(getResourceMessage("errors.minlength", new Object[]{fieldAlias,min+""}));
		    }else if(max >= 0){
	            addError(getResourceMessage("errors.maxlength", new Object[]{fieldAlias,max+""}));
		    }else{
		        return true;
		    }
		}		
		return ok;
	}	
    
	//------------------------------------------------------------------------------
	/** Verifica se o valor esté compreendido entre os valores min e max (extremidades).
	 *  Para desconsiderar a validaééo de qualquer uma dessas extremidades, use um valor null no parémetro.
	 *  Se a validacaonão estiver de acordo, adiciona um erro. */
	//------------------------------------------------------------------------------
    protected boolean isValidInterval( String fieldName, Double min, Double max, String fieldAlias) {
        String value = getParam(fieldName);
		boolean ok = ValidationRules.isValidInterval(value,min,max) ;
		if( !ok ) {
		    if(min != null && max != null){
	            addError(getResourceMessage("errors.rangevalue", new Object[]{fieldAlias,min+"",max+""}));
		    }else if(min != null){
	            addError(getResourceMessage("errors.minvalue", new Object[]{fieldAlias,min+""}));
		    }else if(max != null){
	            addError(getResourceMessage("errors.maxvalue", new Object[]{fieldAlias,max+""}));
		    }else{
		        return true;
		    }
		}		
		return ok;
	}	

	//------------------------------------------------------------------------------
	/** Verifica se o valor informado é némero inteiro.
	 *  Se a validacaonão estiver de acordo, adiciona um erro. */
	//------------------------------------------------------------------------------
    protected boolean isInteger( String fieldName, String fieldAlias ) {
        String value = getParam(fieldName);
		boolean ok = ValidationRules.isInteger(value) ;
		if( !ok ) {
            addError(getResourceMessage("errors.integer", fieldAlias));
		}		
		return ok ;
	}	

	//------------------------------------------------------------------------------
	/** Verifica se o valor informado é némero decimal.
	 *  Se a validacaonão estiver de acordo, adiciona um erro. */
	//------------------------------------------------------------------------------
    protected boolean isFloat( String fieldName, String fieldAlias ) {
        String value = getParam(fieldName);
		boolean ok = ValidationRules.isFloat(value) ;
		if( !ok ) {
            addError(getResourceMessage("errors.float", fieldAlias));
		}		
		return ok ;
	}	

	//------------------------------------------------------------------------------
	/** Verifica se o valor informado é uma data no formato dd/MM/yyyy.
	 *  Se a validacaonão estiver de acordo, adiciona um erro. */
	//------------------------------------------------------------------------------
	protected boolean isDateDDMMYYYY( String fieldName, String fieldAlias ) {
        String value = getParam(fieldName);
		boolean ok = ValidationRules.isDateDDMMYYYY(value) ;
		if( !ok ) {
            addError(getResourceMessage("errors.date", fieldAlias));
		}		
		return ok ;
	}		

	//------------------------------------------------------------------------------
	/** Verifica se o e-mail é valido. Se a
	 * validação não estiver de acordo, adiciona um erro.
	 */
	//------------------------------------------------------------------------------
	protected boolean isValidEmail(String fieldName, String fieldAlias ) {
		String value = getParam(fieldName);
		boolean ok = ValidationRules.isValidEmail(value);
		if (!ok) {
			addError(getResourceMessage("errors.email", fieldAlias));
		}
		return ok;
	}

	//------------------------------------------------------------------------------
	/** Verifica se os e-mails (separados por , ou ;) são validos. Se a
	 * validação não estiver de acordo, adiciona um erro.
	 */
	//------------------------------------------------------------------------------
	protected boolean isValidEmails(String fieldName, String fieldAlias ) {
		String value = getParam(fieldName);

		value = StringUtils.replace(value,";",",");
		String[] emails = StringUtils.split(value,',');
		boolean ok = false;
		for(String email : emails){
			ok = ValidationRules.isValidEmail(email.trim());
			if (!ok) {
				addError(getResourceMessage("errors.emails", new String[]{fieldAlias, email}));
				return ok;
			}
		}
		return ok;
	}
}
