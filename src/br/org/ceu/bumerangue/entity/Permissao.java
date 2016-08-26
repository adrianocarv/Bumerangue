package br.org.ceu.bumerangue.entity;

import java.util.ArrayList;
import java.util.List;

import br.org.ceu.bumerangue.entity.suporte.deploy.Deploy;
import br.org.ceu.bumerangue.util.Utils;

public class Permissao extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	//permiss�es da aplica��o: o atributo nome do objeto da constante deve terminar com o nome do m�dulo.
	//VIDEO
	public static final Permissao ADMIN_VIDEO = new Permissao("1","ADMIN_"+Deploy.MODULO_VIDEO, "Administração dos vídeos");
	public static final Permissao AVANCADO_VIDEO = new Permissao("2","AVANCADO_"+Deploy.MODULO_VIDEO, "Usuário avançado dos vídeos");
	public static final Permissao BASICO_VIDEO = new Permissao("3","BASICO_"+Deploy.MODULO_VIDEO, "Usuário comum dos vídeos");

	//LIVRO_CULTURAL
	public static final Permissao ADMIN_LIVRO_CULTURAL = new Permissao("4","ADMIN_"+Deploy.MODULO_LIVRO_CULTURAL, "Administração dos livros culturais");
	public static final Permissao AVANCADO_LIVRO_CULTURAL = new Permissao("5","AVANCADO_"+Deploy.MODULO_LIVRO_CULTURAL, "Usuário avançado dos livros culturais");
	public static final Permissao BASICO_LIVRO_CULTURAL = new Permissao("6","BASICO_"+Deploy.MODULO_LIVRO_CULTURAL, "Usuário comum dos livros culturais");
	
	//FILME_COMERCIAL
	public static final Permissao ADMIN_FILME_COMERCIAL = new Permissao("7","ADMIN_"+Deploy.MODULO_FILME_COMERCIAL, "Administração dos filmes comerciais");

	public static final Permissao BASICO_FILME_COMERCIAL = new Permissao("9","BASICO_"+Deploy.MODULO_FILME_COMERCIAL, "Usuário comum dos filmes comerciais");

	//FICHA_MISSA
	public static final Permissao ADMIN_FICHA_MISSA = new Permissao("10","ADMIN_"+Deploy.MODULO_FICHA_MISSA, "Administração das fichas de missa");
	public static final Permissao AVANCADO_FICHA_MISSA = new Permissao("11","AVANCADO_"+Deploy.MODULO_FICHA_MISSA, "Usuário avan�ado das fichas de missa");
	public static final Permissao BASICO_FICHA_MISSA = new Permissao("12","BASICO_"+Deploy.MODULO_FICHA_MISSA, "Usuário comum das fichas de missa");

	//
	// Atributos persistentes
	//
	private String nome;
	private String descricao;
	private String numero;
	private List usuarios;

	//
	// Atributos de auditoria
	//

	//
	// Outros Atributos
	//
	
	//
	// Contrutores
	//
	public Permissao(){}
	public Permissao(String id){ this.id = id; }
	public Permissao(String numero, String nome, String descricao){
		this.numero = numero;
		this.nome = nome;
		this.descricao = descricao;
	}

	//
	// M�todos de neg�cio
	//
	/**
	 * Retorna true, caso seja uma permiss�o admin.
	 * @author Adriano Carvalho
	 * @return
	 */
	public boolean isAdmin(){
		return getNome().startsWith("ADMIN_");
	}

	/**
	 * Retorna true, caso seja uma permiss�o avan�ada.
	 * @author Adriano Carvalho
	 * @return
	 */
	public boolean isAvancado(){
		return getNome().startsWith("AVANCADO_");
	}

	/**
	 * Retorna true, caso seja uma permiss�o b�sica.
	 * @author Adriano Carvalho
	 * @return
	 */
	public boolean isBasico(){
		return getNome().startsWith("BASICO_");
	}

	/**
	 * Retorna o nome do m�dulo, que cont�m esta permiss�o. 
	 * @return
	 */
	public String getNomeModulo(){
		for(String nomeModulo : Deploy.getNomesModulosDisponiveis()){
			if (this.getNome().endsWith(nomeModulo) ) return nomeModulo;
		}
		return null;
	}
	
	/**
	 * Retorna o tipo de permiss�o correspondente.
	 * @return
	 */
	public ElementoDominio getTipoPermissao(){
		if(this.isAdmin())
			return ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_ADMIN;
		if(this.isAvancado())
			return ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_AVANCADO;
		if(this.isBasico())
			return ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_BASICO;
		return null;
	}
	
	/**
	 * Cada permissao deve ter uma constante. Retorna a lista dessas constantes.
	 * @author Adriano Carvalho
	 * @return
	 */
	public static List<Permissao> getPermissoes(){
		return Utils.getConstantes(Permissao.class,Permissao.class);
	}

	/**
	 * Retorna a lista de constantes, correpondentes as Permissoes, para o tipo do usuario.
	 * @author Adriano Carvalho
	 * @param usuario
	 * @return
	 */
	public static List<Permissao> getPermissoesPorUsuario(Usuario usuario){
		return getPermissoesPorModulo(Deploy.getNomeModuloPorUsuario(usuario));
	}
	
	/**
	 * Retorna a lista de constantes, correpondentes as Permissoes, para o tipo do objetoBumerangue.
	 * @author Adriano Carvalho
	 * @param objetoBumerangue
	 * @return
	 */
	public static List<Permissao> getPermissoesPorObjetoBumerangue(ObjetoBumerangue objetoBumerangue){
		return getPermissoesPorModulo(Deploy.getNomeModuloPorObjetoBumerangue(objetoBumerangue));
	}

	/**
	 * Retorna a lista de constantes por m�dulo.<br>
	 * O nome do m�dulo pode ser obtido como constante da classe Permiss�o.
	 * @author Adriano Carvalho
	 * @param nomeModulo
	 * @return
	 */
	public static List<Permissao> getPermissoesPorModulo(String nomeModulo){
		List<Permissao> constantes = new ArrayList<Permissao>();
		for(Permissao permissao : getPermissoes()){
			if(permissao.getNome().endsWith("_"+nomeModulo))
				constantes.add(permissao);
		}
		return constantes;
	}

	/**
	 * Retorna a Permiss�o (definada como constante nesta Classe), de acordo com o tipo da permiss�o (dom�nio)<br>
	 * e com o nome do m�dulo.
	 * @author Adriano Carvalho
	 * @param codigoTipoPermissao
	 * @param nomeModulo
	 * @return
	 */
	public static Permissao getPermissao(Integer codigoTipoPermissao, String nomeModulo){
		for(Permissao permissao : getPermissoesPorModulo(nomeModulo)){
			if(ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_ADMIN.getCodigo().intValue() == codigoTipoPermissao.intValue() && permissao.isAdmin())
				return permissao;
			if(ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_AVANCADO.getCodigo().intValue() == codigoTipoPermissao.intValue() && permissao.isAvancado())
				return permissao;
			if(ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_BASICO.getCodigo().intValue() == codigoTipoPermissao.intValue() && permissao.isBasico())
				return permissao;
		}
		return null;
	}

	/**
	 * Retorna a Permiss�o (definada como constante neste Classe), de acordo com o tipo da permiss�o (dom�nio)<br>
	 * e com o tipo da instancia da classe Usuario.
	 * @param codigoTipoPermissao
	 * @param usuario
	 * @return null, caso n�o se aplique.
	 */
	public static Permissao getPermissao(Integer codigoTipoPermissao, Usuario usuario){
		return getPermissao(codigoTipoPermissao, Deploy.getNomeModuloPorUsuario(usuario));
	}

	/**
	 * Retorna a Permiss�o (definada como constante neste Classe), de acordo com o tipo da permiss�o (dom�nio)<br>
	 * e com o tipo da instancia da classe ObjetoBumerangue.
	 * @param codigoTipoPermissao
	 * @param usuario
	 * @return null, caso n�o se aplique.
	 */
	public static Permissao getPermissao(Integer codigoTipoPermissao, ObjetoBumerangue objetoBumerangue){
		return getPermissao(codigoTipoPermissao, Deploy.getNomeModuloPorObjetoBumerangue(objetoBumerangue));
	}

	//
	// M�todos acessores default
	//	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List usuarios) {
		this.usuarios = usuarios;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
}
