package br.org.ceu.bumerangue.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;

import br.org.ceu.bumerangue.service.exceptions.BumerangueErrorRuntimeException;

/**
 * Classe Helper que contém métodos para auxiliar na recuperação de dados do
 * Request.
 * 
 */
public class RequestParamUtils {

	private MultipartResolver multipartResolver;

	public void setMultipartResolver(MultipartResolver multipartResolver) {
		this.multipartResolver = multipartResolver;
	}

	/**
	 * Armazena um objeto no request.
	 * 
	 * @param request
	 *            a requisição HTTP que estiver sendo processada.
	 * @param name
	 *            nome do atributo.
	 * @param obj
	 *            objeto.
	 */
	public void setRequestAttribute(HttpServletRequest request, String name, Object obj) {
		request.setAttribute(name, obj);
	}

	public String getAliasFromURL(HttpServletRequest request, String keyName) {
		String key = null;
		String url = request.getRequestURL().toString() + "|";

		String regExpr = "/(\\w+)(\\.mmp)";
		Pattern pat;

		pat = Pattern.compile(regExpr, Pattern.CASE_INSENSITIVE);

		Matcher matcher = pat.matcher(url);

		if (matcher.find()) {
			key = matcher.group(1);
		}
		return key;

	}

	/**
	 * Recupera um objeto do request.
	 * 
	 * @param request
	 *            a requisição HTTP que estiver sendo processada.
	 * @param name
	 *            nome do atributo.
	 * @return objeto recuperado através do nome do atributo, no request.
	 */
	public Object getRequestAttribute(HttpServletRequest request, String name) {

		return request.getAttribute(name);

	}

	/**
	 * Obtem um valor do Request Parameter.
	 * 
	 * @param request
	 *            a requisição HTTP que estiver sendo processada.
	 * @param name
	 *            nome do parametro do request.
	 * @return objeto recuperado do parametro.
	 */
	public String getRequestParameter(HttpServletRequest request, String name) {
		return request.getParameter(name);
	}

	/**
	 * Obtem um valor do Request Parameter.
	 * 
	 * @param request
	 *            a requisição HTTP que estiver sendo processada.
	 * @param name
	 *            nome do parametro do request.
	 * @return objeto recuperado do parametro.
	 */
	public List getRequestParameterList(HttpServletRequest request, String name) {

		return Arrays.asList(request.getParameterValues(name));
	}

	/**
	 * Obtem um valor do Request Parameter. Se este valor for igual ao valor
	 * informado no parametro nullValue, retorna null.
	 * 
	 * @param request
	 *            a requisição HTTP que estiver sendo processada.
	 * @param name
	 *            nome do parametro do request.
	 * @param nullValue
	 *            valor que indica a condição para que retorne null.
	 * @return objeto recuperado do parametro.
	 */
	public String getRequestParameter(HttpServletRequest request, String name, String nullValue) {

		String value = getRequestParameter(request, name);

		if (nullValue.equals(value)) {
			value = null;
		}

		return value;
	}

	/**
	 * Recupera o valor de um campo do form como Double.
	 * 
	 * @param form
	 *            o bean ActionForm para a requisição atual.
	 * @param param
	 *            nome do campo do form.
	 * @return valor do campo convertido em Double ou null caso o campo esteja
	 *         vazio ou se ocorrer algum problema durante a recuperação de valor
	 *         do campo do form.
	 */
	public Double getRequestParameterAsDouble(HttpServletRequest request, String name) {

		Double value = null;
		String s = getRequestParameter(request, name);

		try {
			if (!(s == null) && s.trim().length() > 0) {
				value = new Double(s);
			}
			return value;
		} catch (Exception ex) {
			return null;
		}

	}

	/**
	 * Recupera o valor de um campo do form como Integer.
	 * 
	 * @param form
	 *            o bean ActionForm para a requisição atual.
	 * @param param
	 *            nome do campo do form.
	 * @return valor do campo convertido em Integer ou null caso o campo esteja
	 *         vazio.
	 * @throws DataFormatterException
	 *             se ocorrer algum problema durante a recuperação de valor do
	 *             campo do form.
	 */
	public Integer getRequestParameterAsInteger(HttpServletRequest request, String name) {
		Integer value = null;
		;
		String strValue;
		try {
			strValue = getRequestParameter(request, name);
			if (!(strValue == null) && !(strValue.trim().equals("")))
				value = new Integer(strValue);
		} catch (Exception ex) {
			return null;
		}

		return value;

	}

	/**
	 * Recupera o valor de um campo do form como Long.
	 * 
	 * @param form
	 *            o bean ActionForm para a requisição atual.
	 * @param param
	 *            nome do campo do form.
	 * @return valor do campo convertido em Long.
	 * @throws DataFormatterException
	 *             se ocorrer algum problema durante a recuperação de valor do
	 *             campo do form.
	 */
	public Long getRequestParameterAsLong(HttpServletRequest request, String name) {
		return getRequestParameterAsLong(request, name, "");

	}

	/**
	 * Recupera o valor de um campo do form como Long. Se este valor for igual
	 * ao valor informado no parametrro nullValue, retorna null.
	 * 
	 * @param form
	 *            o bean ActionForm para a requisição atual.
	 * @param param
	 *            nome do campo do form.
	 * @param nullValue
	 *            valor que indica a condição para que retorne null.
	 * @return valor do campo convertido em Long, ou null, caso o valor
	 *         encontrado no campo seja igual. ao valor do parametrto nullValue.
	 * @throws DataFormatterException
	 *             se ocorrer algum problema durante a recuperação de valor do
	 *             campo do form.
	 */
	public Long getRequestParameterAsLong(HttpServletRequest request, String name, String nullValue) {
		Long value = null;
		String strValue;
		try {

			strValue = getRequestParameter(request, name);

			if (strValue.length() > 0 && !nullValue.equals(strValue)) {
				/*
				 * Sera convertido somente se... - o tamanho do valor for maior
				 * que zero - o valor nao for o mesmo passado no parametrto
				 * nullValue
				 */
				value = new Long(strValue);
			}

		} catch (Exception ex) {
			return null;
		}

		return value;

	}

	/**
	 * Recupera um objeto java.util.Date através de um campo string que contém
	 * uma data.
	 * 
	 * @param form
	 *            o bean ActionForm para a requisição atual.
	 * @param request
	 *            a requisição HTTP que estiver sendo processada.
	 * @param paramDate
	 *            o nome do campo do form que contém a data.
	 * @param datePattern
	 *            o pattern da data.
	 * @param full
	 *            a flag que indica se deve preencher o horario com valor cheio
	 *            (23:59:59.999).
	 * @return o valor do campo convertido em java.util.Date ou null caso o
	 *         campo esteja vazio ou ocorrer algum problema durante a
	 *         recuperação de valor do campo do form ou na conversão da string
	 *         date para java.util.Date.
	 */
	public Date getRequestParameterAsDate(HttpServletRequest request, String paramDate, String datePattern, boolean full) {

		return getRequestParameterAsDate(request, paramDate, null, full, datePattern, null);

	}

	/**
	 * Recupera um objeto java.util.Date através de um campo string que contém
	 * uma data.<br/> Não preenche o horario com valor cheio (23:59:59.999)
	 * 
	 * @param form
	 *            o bean ActionForm para a requisição atual.
	 * @param request
	 *            a requisição HTTP que estiver sendo processada.
	 * @param paramDate
	 *            o nome do campo do form que contém a data.
	 * @param datePattern
	 *            o pattern da data.
	 * @return o valor do campo convertido em java.util.Date ou null caso o
	 *         campo esteja vazio ou se ocorrer algum problema durante a
	 *         recuperação de valor do campo do form ou na conversão da string
	 *         date para java.util.Date.
	 */

	public Date getRequestParameterAsDate(HttpServletRequest request, String paramDate, String datePattern) {

		return getRequestParameterAsDate(request, paramDate, datePattern, false);

	}

	/**
	 * Recupera um objeto java.util.Date for através de um campo string que
	 * contém uma data, e, opcionalmente, através de um outro campo que contém
	 * um horario.
	 * 
	 * @param form
	 *            o bean ActionForm para a requisição atual.
	 * @param request
	 *            a requisição HTTP que estiver sendo processada.
	 * @param paramDate
	 *            o nome do campo do form que contém a data.
	 * @param paramTime
	 *            o nome do campo do form que contém o horário.
	 * @param datePattern
	 *            o pattern da data.
	 * @param timePattern
	 *            o pattern do horario.
	 * @return o valor dos campos (data e horario) convertidos em java.util.Date
	 *         ou null caso o campo esteja vazio.
	 * @throws DataFormatterException
	 *             se ocorrer algum problema durante a recuperação de valor dos
	 *             campos do form ou na conversão da string date + string time
	 *             para java.util.Date.
	 */
	public Date getRequestParameterAsDate(HttpServletRequest request, String paramDate, String paramTime, String datePattern, String timePattern) {

		return getRequestParameterAsDate(request, paramDate, paramTime, false, datePattern, timePattern);

	}

	/**
	 * Recupera um objeto java.util.Date for através de um campo string que
	 * contém uma data, e, opcionalmente, através de um outro campo que contém
	 * um horario. Se preferir preencher o horario por completo, passar true no
	 * parametro 'full'.<br/> Se full=true e paramTime!=null, a primeira terá
	 * prioridade.
	 * 
	 * @info esse método deve ser private para evitar confusão de full=trtue e
	 *       paramTime!=null
	 * 
	 * @param form
	 *            o bean ActionForm para a requisição atual.
	 * @param request
	 *            a requisição HTTP que estiver sendo processada.
	 * @param paramDate
	 *            o nome do campo do form que contém a data.
	 * @param paramTime
	 *            o nome do campo do form que contém o horário.
	 * @param datePattern
	 *            o pattern da data.
	 * @param timePattern
	 *            o pattern do horario.
	 * @param full
	 *            a flag que indica se deve preencher o horario com valor cheio
	 *            (23:59:59.999).
	 * @return o valor dos campos (data e horario) convertidos em java.util.Date
	 *         ou null caso o campo esteja vazio ou se ocorrer algum problema
	 *         durante a recuperação de valor dos campos do form ou na conversão
	 *         da string date + string time para java.util.Date.
	 */
	private Date getRequestParameterAsDate(HttpServletRequest request, String paramDate, String paramTime, boolean full, String datePattern,
			String timePattern) {

		String sDate = getRequestParameter(request, paramDate);

		if (sDate == null || sDate.length() <= 0) {
			return null;
		}

		String strToParse = sDate;
		String pattern = datePattern;
		if (!full && paramTime != null) {
			String sTime = getRequestParameter(request, paramTime);
			pattern += " " + timePattern; // Adiciona no pattern o time
			strToParse += " " + sTime;
		}

		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		formatter.setLenient(false);
		Date date = null;

		try {
			date = formatter.parse(strToParse);
		} catch (ParseException ex) {
			return null;
		}

		if (date != null && full) {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(new Date(date.getTime()));
			calendar.add(Calendar.HOUR, 23);
			calendar.add(Calendar.MINUTE, 59);
			calendar.add(Calendar.SECOND, 59);
			calendar.add(Calendar.MILLISECOND, 999);
			date = calendar.getTime();
		}

		return date;

	}

	/**
	 * Recupera o valor de um campo do form como Boolean.
	 * 
	 * @param form
	 *            o bean ActionForm para a requisição atual.
	 * @param param
	 *            nome do campo do form.
	 * @return valor do campo convertido em Boolean ou FALSE caso o campo esteja
	 *         vazio.
	 * @throws DataFormatterException
	 *             se ocorrer algum problema durante a recuperação de valor do
	 *             campo do form.
	 */
	public Boolean getRequestParameterAsBoolean(HttpServletRequest request, String param) {
		Boolean value = null;
		String strValue;
		try {

			strValue = getRequestParameter(request, param);

			if (strValue == null)
				value = Boolean.FALSE;
			else if (strValue.length() > 0)
				value = new Boolean(strValue);

		} catch (Exception ex) {
			throw new BumerangueErrorRuntimeException("Erro na conversão de dados do parametro '" + param + "' do request.", ex);
		}

		return value;

	}

	public MultipartFile getRequestParameterAsFile(HttpServletRequest request, String param) {
		MultipartFile file = null;
		if (multipartResolver.isMultipart(request)) {
			file = ((MultipartHttpServletRequest) request).getFile(param);
			if ("".equals(file.getOriginalFilename())) {
				file = null;
			}
		}
		return file;
	}

}
