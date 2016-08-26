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
	
	//módulos da aplicação. Bumerangue representa a aplicação como um todo.
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
	// Métodos de negócio
	//
	public static void loadModulosDisponiveis(){
		String modulosDisponiveis = Utils.getResourceMessage("bmg.deploy.modulosDisponiveis");
		modulosDisponiveis = StringUtils.trim(modulosDisponiveis);
		MODULOS_DISPONIVEIS = StringUtils.split(modulosDisponiveis,',');
	}
	
	
	/**
	 * Retorna true, se o módulo estiver disponível.
	 * @param nomeModulo
	 * @return
	 */
	public static boolean isModuloDisponivel(String nomeModulo){
		if (MODULOS_DISPONIVEIS.length == 1 && MODULOS_DISPONIVEIS[0].equals(TODOS_MODULOS_DISPONIVEIS))
			return true;
		return Arrays.asList(MODULOS_DISPONIVEIS).contains(nomeModulo);
	}

	/**
	 * Retorna true, se o módulo correspondente ao da permissão estiver disponível.
	 * @param permissao
	 * @return
	 */
	public static boolean isModuloDisponivel(Permissao permissao){
		return isModuloDisponivel(permissao.getNomeModulo());
	}

	/**
	 * Retorna uma lista de String correspondente aos nomes dos módulos da aplicação. 
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
	 * Retorna um Map com a lista de nomes dos módulos da aplicação, em que a chave é a sigla do módulo. 
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
	 * Retorna uma lista de String correspondente aos nomes dos módulos disponívies na aplicação. 
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
	 * Retorna o nome do módulo, realizando a conversão, a partir do tipo de Usuário.<br>
	 * O nome do módulo pode ser obtido como constante da classe Permissão.<br>
	 * Retorna null, caso a conversão não se aplicar.
	 * @author Adriano Carvalho
	 * @param usuario
	 * @return
	 */
	public static String getNomeModuloPorUsuario(Usuario usuario){
		//condição especial quando os usuários de um módulo que não têm ObjetoBumerangue correspondente
		if(usuario instanceof UsuarioFichaMissa)
			return Deploy.MODULO_FICHA_MISSA;

		return getNomeModuloPorObjetoBumerangue(usuario.getObjetoBumerangueCorrepospondente());
	}

	/**
	 * Retorna o nome do módulo, realizando a conversão, a partir do tipo de ObjetoBumerangue.<br>
	 * Retorna null, caso a conversão não se aplicar.
	 * @author Adriano Carvalho
	 * @param usuario
	 * @return
	 */
	public static String getNomeModuloPorObjetoBumerangue(ObjetoBumerangue objetoBumerangue){
		//TODO É POSSIVEL QUES ESTE MÉTODO FIQUE DINAMICO
		if (objetoBumerangue instanceof Video) return Deploy.MODULO_VIDEO;
		if (objetoBumerangue instanceof LivroCultural) return Deploy.MODULO_LIVRO_CULTURAL;
		if (objetoBumerangue instanceof FilmeComercial) return Deploy.MODULO_FILME_COMERCIAL;
		return null;
	}

	/**
	 * Retorna o tipo de ObjetoBumerangue, realizando a conversão, a partir do nome do módulo.<br>
	 * Retorna null, caso a conversão não se aplicar.
	 * @author Adriano Carvalho
	 * @param usuario
	 * @return
	 */
	public static ObjetoBumerangue getObjetoBumeranguePorNomeModulo(String nomeModulo){
		//TODO É POSSIVEL QUES ESTE MÉTODO FIQUE DINAMICO
		if (Deploy.MODULO_VIDEO.equals(nomeModulo)) return new Video();
		if (Deploy.MODULO_LIVRO_CULTURAL.equals(nomeModulo)) return new LivroCultural();
		if (Deploy.MODULO_FILME_COMERCIAL.equals(nomeModulo)) return new FilmeComercial();
		return null;
	}
	
	//
	// Métodos acessores default
	//	
}
