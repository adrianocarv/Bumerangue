package br.org.ceu.bumerangue.entity;

import java.util.ArrayList;
import java.util.List;

import br.org.ceu.bumerangue.entity.suporte.deploy.Deploy;
import br.org.ceu.bumerangue.util.Utils;

public class Dominio extends BaseEntity {
	private static final long serialVersionUID = 1L;

	//dominios da aplicação: o atributo a descrição do objeto da constante deve iniciar com o nome do módulo.
	//VIDEO
	public static final Dominio VIDEO_OBSERVACOES = new Dominio(1, Deploy.MODULO_VIDEO+"_OBSERVACOES");
	public static final Dominio VIDEO_CATEGORIA = new Dominio(2, Deploy.MODULO_VIDEO+"_CATEGORIA");
	public static final Dominio VIDEO_MIDIA = new Dominio(3, Deploy.MODULO_VIDEO+"_MIDIA");
	public static final Dominio VIDEO_LOCALIZACAO_FISICA = new Dominio(8, Deploy.MODULO_VIDEO+"_LOCALIZACAO_FISICA");

	//BUMERANGUE
	public static final Dominio BUMERANGUE_TIPO_PERMISSAO = new Dominio(4, Deploy.MODULO_BUMERANGUE+"_TIPO_PERMISSAO");
	public static final Dominio BUMERANGUE_LOCALIZACAO_FISICA_DIRECAO = new Dominio(10, Deploy.MODULO_BUMERANGUE+"_LOCALIZACAO_FISICA_DIRECAO");

	//LIVRO_CULTURAL
	public static final Dominio LIVRO_CULTURAL_IDIOMA = new Dominio(7, Deploy.MODULO_LIVRO_CULTURAL+"_IDIOMA");
	public static final Dominio LIVRO_CULTURAL_LOCALIZACAO_FISICA = new Dominio(9, Deploy.MODULO_LIVRO_CULTURAL+"_LOCALIZACAO_FISICA");

	//FILME_COMERCIAL
	public static final Dominio FILME_COMERCIAL_CATEGORIA = new Dominio(5, Deploy.MODULO_FILME_COMERCIAL+"_CATEGORIA");
	public static final Dominio FILME_COMERCIAL_PUBLICO = new Dominio(6, Deploy.MODULO_FILME_COMERCIAL+"_PUBLICO");

	//FICHA_MISSA
	public static final Dominio FICHA_MISSA_EDICAO_IDIOMA_MISSAL = new Dominio(11, Deploy.MODULO_FICHA_MISSA+"_EDICAO_IDIOMA_MISSAL");
	public static final Dominio FICHA_MISSA_FESTA_MOVEL = new Dominio(12, Deploy.MODULO_FICHA_MISSA+"_FESTA_MOVEL");
	public static final Dominio FICHA_MISSA_SITUACAO_PLANO_MISSA = new Dominio(13, Deploy.MODULO_FICHA_MISSA+"_SITUACAO_PLANO_MISSA");

	//
	// Atributos persistentes
	//
	private Integer codigo;
	private String descricao;
	private String personalizado1;	// para LOCALIZACAO_FISICA, DataUltimaAtualizacao
									// para BUMERANGUE_TIPO_PERMISSAO, notificação sobre a necessidade de reiniciar o aplicativo.
	private String personalizado2;	// para LOCALIZACAO_FISICA, Campos de ordenação
	private String personalizado3;	// para LOCALIZACAO_FISICA, Grupo de compartimentos
	private String personalizado4;	// para LOCALIZACAO_FISICA, na pesquisa com código, fragmentoSequencial
	private String personalizado5;	// para LOCALIZACAO_FISICA, na verificação de códigos, separadorCodigos
	private String personalizado6;	// para LOCALIZACAO_FISICA, na verificação de códigos, fragmentoSubstituicao
	private List<ElementoDominio> elementosDominio = new ArrayList<ElementoDominio>();

	//
	// Atributos de auditoria
	//

	//
	// Outros Atributos
	//
	
	//
	// Contrutores
	//
	public Dominio(){}
	public Dominio(String id){ this.id = id; }
	public Dominio(Integer codigo, String descricao){
		this.codigo = codigo;
		this.descricao = descricao;
	}

	//
	// Métodos de negócio
	//
	/**
	 * Cada dominio deve ter uma constante. Retorna a lista dessas constantes.
	 * @author Adriano Carvalho
	 * @return
	 */
	public static List<Dominio> getDominios(){
		return Utils.getConstantes(Dominio.class,Dominio.class);
	}

	/**
	 * Retorna a lista de constantes por módulo.<br>
	 * O nome do módulo pode ser obtido como constante da classe Permissão.
	 * @author Adriano Carvalho
	 * @param nomeModulo
	 * @return
	 */
	public static List<Dominio> getDominiosPorModulo(String nomeModulo){
		List<Dominio> constantes = new ArrayList<Dominio>();
		for(Dominio dominio : getDominios()){
			if(dominio.getDescricao().startsWith(nomeModulo+"_"))
				constantes.add(dominio);
		}
		return constantes;
	}

	/**
	 * Retorna o localização física correspondente ao módulo informado.<br>
	 * Retorna null, caso nada encontrado.
	 * @author Adriano Carvalho
	 * @param nomeModulo
	 * @return
	 */
	public static Dominio getLocalizacaoFisica(String nomeModulo){
		for(Dominio dominio : getDominiosPorModulo(nomeModulo)){
			if(dominio.getDescricao().endsWith("_LOCALIZACAO_FISICA"))
				return dominio;
		}
		return null;
	}
	
	public String getDescricaoFormatada(){
		return this.getCodigo() +" - "+this.getDescricao();
	}
	
	public List<ElementoDominio> getElementosDominioEmUso() {
		List<ElementoDominio> elementosDominioEmUso = new ArrayList<ElementoDominio>();
		for(ElementoDominio elementoDominio : this.getElementosDominio()){
			if(!elementoDominio.getForaUso().booleanValue())
				elementosDominioEmUso.add(elementoDominio);
		}
		return elementosDominioEmUso;
	}

	public boolean isLocalizacaoFisica(){
		return this.getDescricao() != null && this.getDescricao().endsWith("_LOCALIZACAO_FISICA");
	}
	
	//
	// Métodos acessores default
	//	
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<ElementoDominio> getElementosDominio() {
		return elementosDominio;
	}

	public void setElementosDominio(List<ElementoDominio> elementosDominio) {
		this.elementosDominio = elementosDominio;
	}
	public String getPersonalizado1() {
		return personalizado1;
	}
	public void setPersonalizado1(String personalizado1) {
		this.personalizado1 = personalizado1;
	}
	public String getPersonalizado2() {
		return personalizado2;
	}
	public void setPersonalizado2(String personalizado2) {
		this.personalizado2 = personalizado2;
	}
	public String getPersonalizado3() {
		return personalizado3;
	}
	public void setPersonalizado3(String personalizado3) {
		this.personalizado3 = personalizado3;
	}
	public String getPersonalizado4() {
		return personalizado4;
	}
	public void setPersonalizado4(String personalizado4) {
		this.personalizado4 = personalizado4;
	}
	public String getPersonalizado5() {
		return personalizado5;
	}
	public void setPersonalizado5(String personalizado5) {
		this.personalizado5 = personalizado5;
	}
	public String getPersonalizado6() {
		return personalizado6;
	}
	public void setPersonalizado6(String personalizado6) {
		this.personalizado6 = personalizado6;
	}
}
 
