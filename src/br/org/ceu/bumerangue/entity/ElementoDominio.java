package br.org.ceu.bumerangue.entity;



public class ElementoDominio extends BaseEntity {
	private static final long serialVersionUID = 1L;

	//constantes relacionadas com LOCALIZACAO_FISICA
	public static final String SEM_ESPACO_COMPARTIMENTO = "SEM_ESPACO";
	
	//elementos do dom�nio BUMERANGUE_TIPO_PERMISSAO: o nome da constante deve come�ar com o nome do dom�nio.
	public static final ElementoDominio BUMERANGUE_TIPO_PERMISSAO_ADMIN = new ElementoDominio(1, "admin");
	public static final ElementoDominio BUMERANGUE_TIPO_PERMISSAO_AVANCADO = new ElementoDominio(2, "avançado");
	public static final ElementoDominio BUMERANGUE_TIPO_PERMISSAO_BASICO = new ElementoDominio(3, "básico");

	//elementos do dom�nio FICHA_MISSA_SITUACAO_PLANO_MISSA: o nome da constante deve come�ar com o nome do dom�nio.
	public static final ElementoDominio FICHA_MISSA_SITUACAO_PLANO_MISSA_EM_APROVACAO = new ElementoDominio(1, "em aprovação");
	public static final ElementoDominio FICHA_MISSA_SITUACAO_PLANO_MISSA_APROVADO = new ElementoDominio(2, "aprovado");

	//
	// Atributos persistentes
	//
	private Integer codigo;
	private String descricao;
	private Boolean foraUso = Boolean.FALSE;
	private String personalizado1;			// do dom�nio LOCALIZACAO_FISICA, atributo Quantidade
	private ElementoDominio personalizado2;	// do dom�nio LOCALIZACAO_FISICA, atributo Dire��o
	private String personalizado3;
	private Dominio dominio;

	//
	// Atributos de auditoria
	//

	//
	// Outros Atributos
	//
	
	//
	// Contrutores
	//
	public ElementoDominio(){}
	public ElementoDominio(String id){ this.id = id; }
	public ElementoDominio(Integer codigo, String descricao){
		this.codigo = codigo;
		this.descricao = descricao;
	}

	//
	// M�todos de neg�cio
	//
	/**
	 * Verifica foi salvo, ou seja, o mesmo n�o deve possuir o nome chave.
	 * @author Adriano Carvalho
	 * @return
	 */
	public boolean isSalvo(){
		return !(this.getId() == null || this.getId().equals("") || this.getId().equals(new ElementoDominio().getId()));
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

	public Dominio getDominio() {
		return dominio;
	}

	public void setDominio(Dominio dominio) {
		this.dominio = dominio;
	}
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public Boolean getForaUso() {
		return foraUso;
	}
	public void setForaUso(Boolean foraUso) {
		this.foraUso = foraUso;
	}
	public String getPersonalizado1() {
		return personalizado1;
	}
	public void setPersonalizado1(String personalizado1) {
		this.personalizado1 = personalizado1;
	}
	public ElementoDominio getPersonalizado2() {
		return personalizado2;
	}
	public void setPersonalizado2(ElementoDominio personalizado2) {
		this.personalizado2 = personalizado2;
	}
	public String getPersonalizado3() {
		return personalizado3;
	}
	public void setPersonalizado3(String personalizado3) {
		this.personalizado3 = personalizado3;
	}
	
}
 
