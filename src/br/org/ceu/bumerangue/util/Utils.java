package br.org.ceu.bumerangue.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;

import br.org.ceu.bumerangue.entity.ElementoDominio;
import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.entity.suporte.Configuracao;
import br.org.ceu.bumerangue.entity.suporte.deploy.Deploy;

import com.ibm.icu.text.Normalizer;

public class Utils {

    // -- Spring MessageSource (i18n)
    // ----------------------------------------------------------------------
    private static MessageSourceAccessor messageSourceAccessor;

    public void setMessageSource(MessageSource messageSource) {
        Utils.messageSourceAccessor = new MessageSourceAccessor(messageSource);
    }

    // -----------------------------------------------------------------------------------------------------

    public String getSiteRoot() {
        return getResourceMessage("system.config.url");
    }

    //
    // Helpers para i18n
    //

    public static String URLEncode(String s) {
        if (s == null)
            return null;
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return s;
        }
    }

    public static Collection sort(Collection c, String params) {
        List newCollection = new ArrayList(c);
        try {
            ComparatorChain cc = new ComparatorChain();
            String parameters[] = params.split(",");
            Class clazz = null;

            if (c.toArray().length > 0) {
                clazz = c.toArray()[0].getClass();

            } else {
                return c;
            }

            for (int i = 0; i < parameters.length; i++) {
                String p[] = parameters[i].trim().split(" ");
                final String param = p[0];

                boolean rev = (p.length > 1 && p[1].equalsIgnoreCase("desc"));
                Method method = null;
                String param1 = null;
                String param2 = null;
                try {
                    if (param.indexOf(".") > -1) {
                        String[] objParams = param.split("[.]");
                        param1 = objParams[0];
                        method = clazz.getMethod("get"
                                + StringUtils.capitalize(param1), null);
                        param2 = objParams[1];
                    } else {
                        method = clazz.getMethod("get"
                                + StringUtils.capitalize(param), null);
                    }
                } catch (SecurityException e) {
                    System.out.println("Exception::sort:SecurityException");
                } catch (NoSuchMethodException e) {
                    System.out.println("Exception::sort:NoSuchMethodException");
                }

                final Method m = method;
                final String subMethod = param2;

                Comparator comp = new Comparator() {
                    public int compare(Object o1, Object o2) {
                        try {
                            if (o1 == null && o2 == null) {
                                return 0;
                            }
                            if (o1 == null) {
                                return -1;
                            }
                            if (o2 == null) {
                                return 1;
                            }
                            Method m1 = o1.getClass().getMethod(m.getName(),
                                    null);
                            Method m2 = o2.getClass().getMethod(m.getName(),
                                    null);
                            if (m1 == null && m2 == null) {
                                return 0;
                            }
                            if (m1 == null) {
                                return -1;
                            }
                            if (m2 == null) {
                                return 1;
                            }
                            if (subMethod != null) {
                                o1 = m1.invoke(o1, null);
                                o2 = m2.invoke(o2, null);
                                m1 = o1.getClass().getMethod(subMethod, null);
                                m2 = o2.getClass().getMethod(subMethod, null);

                            }
                            if (m1.getReturnType() == String.class) {
                                return ((String) m1.invoke(o1, null))
                                        .compareToIgnoreCase(((String) m2
                                                .invoke(o2, null)));
                            } else if (m1.getReturnType() == Date.class) {
                                Date date1 = (Date) m1.invoke(o1, null);
                                Date date2 = (Date) m2.invoke(o2, null);

                                if (date1 == null && date2 == null) {
                                    return 0;
                                }
                                if (date1 == null) {
                                    return -1;
                                }
                                if (date2 == null) {
                                    return 1;
                                }

                                long dateValue1 = date1.getTime();
                                long dateValue2 = date2.getTime();

                                int returnValue;
                                if (dateValue1 == dateValue2) {
                                    // mod dates are equal - rely on the
                                    // 'natural' ordering
                                    returnValue = date1.compareTo(date2);
                                } else {
                                    if (dateValue1 < dateValue2)
                                        returnValue = -1;
                                    else
                                        returnValue = 1;
                                }
                                return returnValue;
                            } else if (m1.getReturnType() == Integer.class
                                    || m1.getReturnType() == Integer.TYPE) {
                                Integer ret1 = (Integer) m1.invoke(o1, null);
                                Integer ret2 = (Integer) m2.invoke(o2, null);

                                if (ret1 == null && ret2 == null) {
                                    return 0;
                                }
                                if (ret1 == null) {
                                    return -1;
                                }
                                if (ret2 == null) {
                                    return 1;
                                }
                                return ret1.compareTo(ret2);
                            } else if (m1.getReturnType() == Long.class
                                    || m1.getReturnType() == Long.TYPE) {
                                Long ret1 = (Long) m1.invoke(o1, null);
                                Long ret2 = (Long) m2.invoke(o2, null);

                                if (ret1 == null && ret2 == null) {
                                    return 0;
                                }
                                if (ret1 == null) {
                                    return -1;
                                }
                                if (ret2 == null) {
                                    return 1;
                                }
                                return ret1.compareTo(ret2);
                            } else {
                                return 0;
                            }
                        } catch (IllegalArgumentException e) {
                            System.out
                                    .println("Exception::ComparatorChain.compare:IllegalArgumentException");
                            return 0;
                        } catch (SecurityException e) {
                            System.out
                                    .println("Exception::ComparatorChain.compare:SecurityException");
                            return 0;
                        } catch (IllegalAccessException e) {
                            System.out
                                    .println("Exception::ComparatorChain.compare:IllegalAccessException");
                            return 0;
                        } catch (InvocationTargetException e) {
                            System.out
                                    .println("Exception::ComparatorChain.compare:InvocationTargetException");
                            return 0;
                        } catch (NullPointerException e) {
                            System.out
                                    .println("Exception::ComparatorChain.compare:NullPointerException");
                            return 0;
                        } catch (NoSuchMethodException e) {
                            System.out
                                    .println("Exception::ComparatorChain.compare:NoSuchMethodException");
                            return 0;
                        }
                    }
                };
                cc.addComparator(comp, rev);
            }
            Collections.sort(newCollection, cc);
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
        }
        return newCollection;
    }

    /**
     * Recupera mensagem de um Resource Bundle atrav�s de sua chave
     * 
     * @param key
     *            chave da mensagem
     * @return mensagem recuperada do Resource Bundle, ou null caso a mensagem
     *         n�o seja encontrada.
     */
    public static final String getResourceMessage(String key) {

        return getResourceMessage(key, null);

    }

    /**
     * Recupera mensagem de um Resource Bundle atrav�s de sua chave, e faz o
     * binding de um parametro
     * 
     * @param key
     *            chave da mensagem
     * @return mensagem recuperada do Resource Bundle, ou null caso a mensagem
     *         n�o seja encontrada.
     */
    public static final String getResourceMessage(String key, Object param) {

        return getResourceMessage(key, new Object[] { param });

    }

    /**
     * Recupera mensagem de um Resource Bundle atrav�s de sua chave, e faz o
     * binding dos parametros
     * 
     * @param key
     *            chave da mensagem
     * @return mensagem recuperada do Resource Bundle, ou null caso a mensagem
     *         n�o seja encontrada.
     */
    public static final String getResourceMessage(String key, Object[] params) {

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

    public static String formatDate(Date d, String s) {
        String ret = "";
        SimpleDateFormat formatter = new SimpleDateFormat(s);
        ret = formatter.format(d);
        return ret;
    }

    public static String getRealPath(HttpServletRequest request, String path)
    {
        return getRealPath(request.getSession(), path);
    }

    public static String getRealPath(HttpSession session, String path)
    {
        return getRealPath(session.getServletContext(), path);
    }

    public static String getRealPath(ServletContext servletContext, String path)
    {
        return servletContext.getRealPath(path);
    }    
    
    public static boolean isValidPath(String path) {
        File file = new File(path);
        return (file.isFile() || file.isDirectory());
    }

    // Deletes all files and subdirectories under dir.
    // Returns true if all deletions were successful.
    // If a deletion fails, the method stops attempting to delete and returns
    // false.
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }

    /**
     * Copia o arquivo de um lugar para outro no sistema de arquivos.
     * @param sourcePath
     * @param targetPath
     */
    public static void copyFile(String sourcePath, String targetPath) {
	    File inputFile = new File(sourcePath);
	    File outputFile = new File(targetPath);

	    try {
			FileInputStream in = new FileInputStream(inputFile);
			FileOutputStream out = new FileOutputStream(outputFile);
			int c;

			while ((c = in.read()) != -1)
			  out.write(c);

			in.close();
			out.close();
		} catch (IOException e) {
			throw new SecurityException(e.getMessage());
		}
   	}

	/**
	 * Retorna o valor recebido na formata��o padr�o a ser exibido na camada de apresenta��o. 
	 * @param value
	 * @return
	 */
	public static String getFormatted(Object value){
		String retorno = "";
		if (value == null) return retorno;
		
		if (value instanceof Date) {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			format.setLenient(true);
			return format.format((Date)value);
		}else if (value instanceof Boolean) {
			return ((Boolean)value).booleanValue() ? "Sim" : "Não";
		}
		else{
			return value.toString();
		}
	}
	
	/**
	 * Retorna true, se o usu�rio tiver alguma role na seq��ncia de n�meros informada.
	 * @param usuario
	 * @param numerosRoles
	 * @return
	 */
	public static boolean isUserInRole(Usuario usuario, String numerosRoles){
		if(usuario == null) return false;
		return usuario.isInRole(numerosRoles);
	}

	/**
	 * Retorna true, se o usu�rio tiver o tipo de permiss�o no m�dulo passados como par�metro.
	 * @param codigoTipoPermissao
	 * @param nomeModulo
	 * @return
	 */
	public static boolean isUserInRoleModulo(Usuario usuario, Integer codigoTipoPermissao, String nomeModulo){
		if(usuario == null) return false;
		return usuario.isInRole(codigoTipoPermissao,nomeModulo);
	}

	/**
	 * Insere a quantidade necess�ria de fragmment a esquerda ou a direita da de source, conforme os par�metros
	 * @param source
	 * @param fragmment
	 * @param quant
	 * @param left true para inserir a esquerda, false a direita
	 * @return
	 */
	public static String insertFragmment(String source, String fragmment, int quant, boolean left) {
		String changedSource = source;
		for(int i=0; i < quant; i++){
			if (left){
				changedSource = fragmment + changedSource;
			} else{
				changedSource += fragmment;
			}
		}
		return changedSource;
	}

	/**
	 * Retorna a quantidade de elementos de uma colection, null se a cole��o for null. Para ser usuado na camada de apresenta��o. 
	 * @param collection
	 * @return
	 */
	public static String size(Collection collection){
		return collection == null ? "null" : collection.size()+"";
	}

	/**
	 * Retorna a quantidade de elementos de um map, null se a map for null. Para ser usuado na camada de apresenta��o. 
	 * @param map
	 * @return
	 */
	public static String size(Map map){
		return map == null ? "null" : map.size()+"";
	}

	/**
	 * Retorna o ElementoDom�nio correspondente ao tipo de permiss�o em rela��o ao tipo de usu�rio passado como par�metro,<br>
	 * sendo este uma subclasse de Usuario, contendo uma permiss�o b�sica, avan�ada ou admin.<br>
	 * Caso n�o encontre a permiss�o, retorna uma inst�ncia vazia de ElementoDominio.
	 * @param usuario
	 * @return
	 */
	public static ElementoDominio getTipoPermissao(Usuario usuario){
		if(usuario == null) return new ElementoDominio();
		return usuario.getTipoPermissaoTipoUsuario();
	}

	/**
	 * Retorna o ElementoDom�nio correspondente ao tipo de permiss�o que o usu�rio tem no 'nomeModulo' informado,<br>
	 * Caso n�o encontre a permiss�o, retorna uma inst�ncia vazia de ElementoDominio.
	 * @param usuario
	 * @param nomeModulo
	 * @return
	 */
	public static ElementoDominio getTipoPermissaoModulo(Usuario usuario, String nomeModulo){
		if(usuario == null) return new ElementoDominio();
		return usuario.getTipoPermissaoModulo(nomeModulo);
	}

	/**
	 * Retorna o nome do arquivo correspondente ao logotipo da aplica��o.<br>
	 * A sele��o do nome se baseia nos pares de datas definidos na propriedade: bmg.apresentacao.logotipo.intervalos<br>
	 * @return
	 */
	public static String getNomeArquivoLogotipo(){
		//bmg.apresentacao.logotipo.intervalos = 10/12,06/12;07/09,07/09
		String key = getResourceMessage("bmg.apresentacao.logotipo.intervalos");
		String[] intervalos = key.split(";");
		try{
			for (int i = 0; i < intervalos.length; i++) {
				String intervalo = intervalos[i];
				String ini = intervalo.split(",")[0]+"/"+Calendar.getInstance().get(Calendar.YEAR);
				String fim = intervalo.split(",")[1]+"/"+Calendar.getInstance().get(Calendar.YEAR);
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				dateFormat.setLenient(false);
				Date dataIni = dateFormat.parse(ini);
				Date dataFim = dateFormat.parse(fim);
				if(dataIni.compareTo(dataFim) <= 0 ){
					if(DateUtil.compareTodayDay(dataIni) >= 0 && DateUtil.compareTodayDay(dataFim) <= 0 )
						return "logo"+(i+1)+".gif";
				}else{
					if(DateUtil.compareTodayDay(dataIni) >= 0 || DateUtil.compareTodayDay(dataFim) <= 0 )
						return "logo"+(i+1)+".gif";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "logo.gif";
	}

	/**
	 * Retorna a descri��o, definida como propriedade do plugin, do m�dulo passado como par�metro.
	 * @author Adriano Carvalho
	 * @param classe
	 * @param tipoConstante
	 * @return
	 */
	public static String getDescricaoModulo(String nomeModulo){
		return getResourceMessage("plugin."+getNomeModuloToJavaConvention(nomeModulo)+".modulo.nome");
	}
	
	/**
	 * @param nomeModulo
	 * @return
	 */
	public static String getNomeModuloToJavaConvention(String nomeModulo){
		String nomeModuloToJavaConvention = "";
		String[] palavras = nomeModulo.split("_");
		for(String palavra : palavras){
			nomeModuloToJavaConvention += StringUtils.capitalise(palavra.toLowerCase());
		}
		nomeModuloToJavaConvention = StringUtils.uncapitalise(nomeModuloToJavaConvention);
		return nomeModuloToJavaConvention;
	}
	
	/**
	 * Retorna true, se o m�dulo estiver dispon�vel.
	 * @param nomeModulo
	 * @return
	 */
	public static boolean isModuloDisponivel(String nomeModulo){
		return Deploy.isModuloDisponivel(nomeModulo);
	}

	/**
	 * Retorna true, se a chave da configura��o do m�dulo estiver no formato de chave.
	 * @author Adriano Carvalho
	 * @param nomeModulo
	 * @param chave
	 * @return
	 */
	public static boolean isChaveConfiguracao(String nomeModulo, String chave){
		if(!isPropriedadeConfiguracao(nomeModulo, chave)) return false;
		
		if(chave.endsWith(Configuracao.SUFIXO_DESCRICAO_CHAVE)) return false;

		if(chave.endsWith(Configuracao.SUFIXO_VALIDACAO_CHAVE)) return false;
		
		return true;
	}

	/**
	 * Retorna a descri��o da chave da configura��o.
	 * @param configuracoes
	 * @param chave
	 * @return
	 */
	public static String getDescricaoChaveConfiguracao(Map<String,String> configuracoes, String chave){
		if (!configuracoes.containsKey(chave+Configuracao.SUFIXO_DESCRICAO_CHAVE)) return "SEM DESCRIÇÃO";
		if (!configuracoes.containsKey(chave+Configuracao.SUFIXO_VALIDACAO_CHAVE)) return "SEM VALIDAÇÃO";
		String descricao = configuracoes.get(chave+Configuracao.SUFIXO_DESCRICAO_CHAVE);
		return StringUtils.isBlank(descricao) ? "DESCREVER" : descricao;
	}

	/**
	 * Retorna true, se a chave eh uma propriedade de configuracao no modulo.
	 * @author Adriano Carvalho
	 * @param nomeModulo
	 * @param chave
	 * @return
	 */
	public static boolean isPropriedadeConfiguracao(String nomeModulo, String chave){
		if(StringUtils.isBlank(chave)) return false;
		
		if(Deploy.MODULO_BUMERANGUE.equals(nomeModulo)){
			if(!chave.startsWith("bmg.")) return false;
		}else{
			if(!chave.startsWith("plugin."+getNomeModuloToJavaConvention(nomeModulo)+".")) return false;
		}
		
		return true;
	}
	
	/**
	 * Retorna a quantidade de registros apresentado em cada p�gina da listagem.
	 * @author Adriano Carvalho
	 * @return
	 */
	public static int getTamanhoPaginaListagem(){
		String tamanhoPaginaListagem = getResourceMessage("bmg.listagem.tamanhoPagina");
		return StringUtils.isBlank(tamanhoPaginaListagem) ? 0 : Integer.parseInt(tamanhoPaginaListagem);
	}
	
	/**
	 * Retorna a mensagem informando que a lista est� vazia.
	 * @author Adriano Carvalho
	 * @return
	 */
	public static String getMensagemListagemVazia(){
		String mensagemListagemVazia = getResourceMessage("bmg.listagem.msg.emptyList");
		return mensagemListagemVazia;
	}
	
	/**
	 * Retorna uma lista de contantes, de um determinado tipo, de uma classe.<br>
	 * Os crit�rios que caracterizam uma constante s�o: atributo membro da 'classe' public + static + final.<br>
	 * Al�m disso, estes atributos devem ser do tipo 'tipoConstante'.
	 * @author Adriano Carvalho
	 * @param classe
	 * @param tipoConstante
	 * @return
	 */
	public static List getConstantes(Class classe, Class tipoConstante){
		List<Object> constantes =  new ArrayList<Object>();
		Field[] fields = getArrayConstantes(classe, tipoConstante);
		for(Field field : fields){
			try{
				constantes.add(field.get(classe.newInstance()));
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return constantes;
	}

	/**
	 * Retorna um array de Field correspondetes as contantes, de um determinado tipo, de uma classe.<br>
	 * Os crit�rios que caracterizam uma constante s�o: atributo membro da 'classe' public + static + final.<br>
	 * Al�m disso, estes atributos devem ser do tipo 'tipoConstante'.
	 * @author Adriano Carvalho
	 * @param classe
	 * @param tipoConstante
	 * @return
	 */
	public static Field[] getArrayConstantes(Class classe, Class tipoConstante){
		List<Field> constantes =  new ArrayList<Field>();
		Field[] fields = classe.getDeclaredFields();
		for(Field field : fields){
			if(Modifier.PUBLIC + Modifier.STATIC + Modifier.FINAL == field.getModifiers()){
				try{
					if(tipoConstante.getName().equals(field.getType().getName()))
						constantes.add(field);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return (Field[]) constantes.toArray(new Field[constantes.size()]);
	}
	
	/**
	 * Considerando que nomeAtributos s�o os atributos, separados por .(ponto) do objeto 'objeto', <br>
	 * ap�s navegar em cada atributo, retorna o resultado do m�todo get'�ltimo atributo'.
	 * @author Adriano Carvalho
	 * @param objeto
	 * @param nomeAtributos
	 * @return
	 */
	public static Object getAtributo(Object objeto, String nomeAtributos){
		if(objeto == null || nomeAtributos == null) return null;

		String[] atributos = StringUtils.replace(nomeAtributos,".",";").split(";");
		String nomePrimeiroAtributo = atributos[0];

		Object obj = null;
		try{
			obj = objeto.getClass().getMethod("get"+StringUtils.capitalize(nomePrimeiroAtributo), null).invoke(objeto, null);
		}catch (Exception e) {
			return null;
		}

		if(atributos.length == 1){
			return obj;
		}else{
			String nomeProximosAtributos = nomeAtributos.substring(nomeAtributos.indexOf(".")+1);
			return getAtributo(obj, nomeProximosAtributos);
		}
	}
	
	/**
	 * Retorna true, caso o 'objeto' tenha um atributo com o nome passado como par�metro.<br>
	 * Considera apenas o 1� n�vel, caso seja um 'nomeAtributo' com v�rios n�veis separados por ponto. 
	 * @param objeto
	 * @param nomeAtributo
	 * @return
	 */
	public static boolean isAtributoExistente(Object objeto, String nomeAtributo){
		if(objeto == null || StringUtils.isBlank(nomeAtributo)) return false;
		
		//considera s� o 1� n�vel 
		int ponto = nomeAtributo.indexOf('.');
		String atributo = ponto != -1 ? nomeAtributo.substring(0,ponto) : nomeAtributo;
		
		for(Method method : objeto.getClass().getMethods()){
			if(method.getName().equals("get"+StringUtils.capitalize(atributo)))
				return true;
		}
		return false;
	}

    /**
     * Remove espacos, acentos e caracteres especiais da String.
     * @param s
     * @return
     */
    public static String getNormalizedString(String s) {
        // passa para caixa baixa
        s = s.toLowerCase(); 
        // remove acento
        s = removeAccent(s); 
        // remove espacos em branco
        s = s.replaceAll(" ","_"); 
        // remove caracteres especiais, exceto '_' e '-'
        s = s.replaceAll("[^\\w&&[^-_\\.]]",""); 
        return s;
    }
	
	/**
	 * Retorna true, caso a url exista
	 * @param s
	 * @return string normalizada
	 */
	public static boolean isValidDropboxURL(String urlPath) {
		try {
			
			urlPath = resolveUrl(urlPath);
			
			URL u = new URL(urlPath); 
			HttpURLConnection huc = (HttpURLConnection) u.openConnection();
			huc.setRequestMethod("GET");
			huc.connect();
			return huc.getResponseCode() == HttpURLConnection.HTTP_OK ? false : true; //http://pt.wikipedia.org/wiki/Anexo:Lista_de_c%C3%B3digos_de_status_HTTP
		} catch (Exception e) {
			return false;
		}
		
		//		try {
//            URL yahoo = new URL(urlPath);
//            URLConnection yc = yahoo.openConnection();
//            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
//            in.close();
//		} catch (Exception e) {
//			return false;
//		}
//		return true;
	}
	
	private static String resolveUrl(String url) {
		  try {
		    HttpURLConnection con = (HttpURLConnection) (new URL(url).openConnection());
		    con.setInstanceFollowRedirects(true);
		    con.connect();
		    int responseCode = con.getResponseCode();
		 
		    if (String.valueOf(responseCode).startsWith("3")) {
		      return con.getHeaderField("Location");
		    }
		  } catch (IOException e) {
		    return url;
		  }
		 
		  return url;
		}
	
	/**
	 * Recebe uma String e a retorna, removendo acentos
	 * @param s
	 * @return string normalizada
	 */
	private static String removeAccent(String s) {
		String temp = Normalizer.normalize(s, Normalizer.NFD, 0);
		return temp.replaceAll("[^\\p{ASCII}]", "");
	}

}
