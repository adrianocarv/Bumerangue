package br.org.ceu.bumerangue.entity.suporte.deploy;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import br.org.ceu.bumerangue.entity.BaseEntity;
import br.org.ceu.bumerangue.entity.FilmeComercial;
import br.org.ceu.bumerangue.entity.LivroCultural;
import br.org.ceu.bumerangue.entity.ObjetoBumerangue;
import br.org.ceu.bumerangue.entity.Permissao;
import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.entity.UsuarioFichaMissa;
import br.org.ceu.bumerangue.entity.Video;
import br.org.ceu.bumerangue.util.Utils;

public class Deploy extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	//m�dulos da aplica��o. Bumerangue representa a aplica��o como um todo.
	public static final String TODOS_MODULOS_DISPONIVEIS = "TODOS_MODULOS_DISPONIVEIS";
	public static final String MODULO_BUMERANGUE = "BUMERANGUE";
	public static final String MODULO_VIDEO = "VIDEO";
	public static final String MODULO_LIVRO_CULTURAL = "LIVRO_CULTURAL";
	public static final String MODULO_FILME_COMERCIAL = "FILME_COMERCIAL";
	public static final String MODULO_FICHA_MISSA = "FICHA_MISSA";
	
	private static String[] MODULOS_DISPONIVEIS = new String[]{};
	
	//
	// Atributos persistentes
	//

	//
	// Atributos de auditoria
	//

	//
	// Outros Atributos
	//
	
	//
	// Contrutores
	//

	//
	// M�todos de neg�cio
	//
	public static void loadModulosDisponiveis(){
		String modulosDisponiveis = Utils.getResourceMessage("bmg.deploy.modulosDisponiveis");
		modulosDisponiveis = StringUtils.trim(modulosDisponiveis);
		MODULOS_DISPONIVEIS = StringUtils.split(modulosDisponiveis,',');
	}
	
	
	/**
	 * Retorna true, se o m�dulo estiver dispon�vel.
	 * @param nomeModulo
	 * @return
	 */
	public static boolean isModuloDisponivel(String nomeModulo){
		if (MODULOS_DISPONIVEIS.length == 1 && MODULOS_DISPONIVEIS[0].equals(TODOS_MODULOS_DISPONIVEIS))
			return true;
		return Arrays.asList(MODULOS_DISPONIVEIS).contains(nomeModulo);
	}

	/**
	 * Retorna true, se o m�dulo correspondente ao da permiss�o estiver dispon�vel.
	 * @param permissao
	 * @return
	 */
	public static boolean isModuloDisponivel(Permissao permissao){
		return isModuloDisponivel(permissao.getNomeModulo());
	}

	/**
	 * Retorna uma lista de String correspondente aos nomes dos m�dulos da aplica��o. 
	 * @author Adriano Carvalho
	 * @param numerosRole
	 * @return
	 */
	private static List<String> getNomesModulos(){
		Field[] fields = Utils.getArrayConstantes(Deploy.class, String.class);
		List<String> nomesModulos = new ArrayList<String>(); 
		for(Field field : fields){
			if(field.getName().startsWith("MODULO_"))
				try{
					nomesModulos.add(field.get(new Deploy())+"");
				}catch (Exception e) {
					e.printStackTrace();
				}
		}
		return nomesModulos;
	}

	/**
	 * Retorna um Map com a lista de nomes dos m�dulos da aplica��o, em que a chave � a sigla do m�dulo. 
	 * @author Adriano Carvalho
	 * @param numerosRole
	 * @return
	 */
	public static Map<String,String> getSiglasNomesModulos(){
		Map<String,String> siglasNomesModulos = new HashMap<String,String>();

		siglasNomesModulos.put("OB","MODULO_BUMERANGUE");
		siglasNomesModulos.put("VD","MODULO_VIDEO");
		siglasNomesModulos.put("LC","MODULO_LIVRO_CULTURAL");
		siglasNomesModulos.put("FC","MODULO_FILME_COMERCIAL");
		siglasNomesModulos.put("FM","MODULO_FICHA_MISSA");

		return siglasNomesModulos;
	}
	
	/**
	 * Retorna uma lista de String correspondente aos nomes dos m�dulos dispon�vies na aplica��o. 
	 * @author Adriano Carvalho
	 * @param numerosRole
	 * @return
	 */
	public static List<String> getNomesModulosDisponiveis(){
		List<String> nomesModulos = new ArrayList<String>(); 
		for(String nomeModulo : getNomesModulos()){
			if(isModuloDisponivel(nomeModulo))
				nomesModulos.add(nomeModulo);
		}
		return nomesModulos;
	}
	
	/**
	 * Retorna o nome do m�dulo, realizando a convers�o, a partir do tipo de Usu�rio.<br>
	 * O nome do m�dulo pode ser obtido como constante da classe Permiss�o.<br>
	 * Retorna null, caso a convers�o n�o se aplicar.
	 * @author Adriano Carvalho
	 * @param usuario
	 * @return
	 */
	public static String getNomeModuloPorUsuario(Usuario usuario){
		//condi��o especial quando os usu�rios de um m�dulo que n�o t�m ObjetoBumerangue correspondente
		if(usuario instanceof UsuarioFichaMissa)
			return Deploy.MODULO_FICHA_MISSA;

		return getNomeModuloPorObjetoBumerangue(usuario.getObjetoBumerangueCorrepospondente());
	}

	/**
	 * Retorna o nome do m�dulo, realizando a convers�o, a partir do tipo de ObjetoBumerangue.<br>
	 * Retorna null, caso a convers�o n�o se aplicar.
	 * @author Adriano Carvalho
	 * @param usuario
	 * @return
	 */
	public static String getNomeModuloPorObjetoBumerangue(ObjetoBumerangue objetoBumerangue){
		//TODO � POSSIVEL QUES ESTE M�TODO FIQUE DINAMICO
		if (objetoBumerangue instanceof Video) return Deploy.MODULO_VIDEO;
		if (objetoBumerangue instanceof LivroCultural) return Deploy.MODULO_LIVRO_CULTURAL;
		if (objetoBumerangue instanceof FilmeComercial) return Deploy.MODULO_FILME_COMERCIAL;
		return null;
	}

	/**
	 * Retorna o tipo de ObjetoBumerangue, realizando a convers�o, a partir do nome do m�dulo.<br>
	 * Retorna null, caso a convers�o n�o se aplicar.
	 * @author Adriano Carvalho
	 * @param usuario
	 * @return
	 */
	public static ObjetoBumerangue getObjetoBumeranguePorNomeModulo(String nomeModulo){
		//TODO � POSSIVEL QUES ESTE M�TODO FIQUE DINAMICO
		if (Deploy.MODULO_VIDEO.equals(nomeModulo)) return new Video();
		if (Deploy.MODULO_LIVRO_CULTURAL.equals(nomeModulo)) return new LivroCultural();
		if (Deploy.MODULO_FILME_COMERCIAL.equals(nomeModulo)) return new FilmeComercial();
		return null;
	}
	
	//
	// M�todos acessores default
	//	
}
