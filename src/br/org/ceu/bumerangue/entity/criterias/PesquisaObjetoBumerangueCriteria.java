package br.org.ceu.bumerangue.entity.criterias;


public class PesquisaObjetoBumerangueCriteria extends PesquisaObjetoCriteria {
 
	public static final Integer MODO_CADASTRADO = 1;
	public static final Integer MODO_NAO_CADASTRADO = 2;
	public static final Integer MODO_TODOS = 0;
	
	private String nomeModulo;
	private String codigoIni;
	private String codigoFim;
	private Boolean foraUso;
	private Integer modo;
	private String fragmentoSequencial;
	private String separadorCodigos;
	private String fragmentoSubstituicao;
	private String serieCodigos;

	public boolean isEmpty(){
		return false;
	}

	public String getCodigoFim() {
		return codigoFim;
	}

	public void setCodigoFim(String codigoFim) {
		this.codigoFim = codigoFim;
	}

	public String getCodigoIni() {
		return codigoIni;
	}

	public void setCodigoIni(String codigoIni) {
		this.codigoIni = codigoIni;
	}

	public Boolean getForaUso() {
		return foraUso;
	}

	public void setForaUso(Boolean foraUso) {
		this.foraUso = foraUso;
	}

	public Integer getModo() {
		return modo;
	}

	public void setModo(Integer modo) {
		this.modo = modo;
	}

	public String getNomeModulo() {
		return nomeModulo;
	}

	public void setNomeModulo(String nomeModulo) {
		this.nomeModulo = nomeModulo;
	}

	public String getSeparadorCodigos() {
		return separadorCodigos;
	}

	public void setSeparadorCodigos(String separadorCodigos) {
		this.separadorCodigos = separadorCodigos;
	}

	public String getSerieCodigos() {
		return serieCodigos;
	}

	public void setSerieCodigos(String serieCodigos) {
		this.serieCodigos = serieCodigos;
	}

	public String getFragmentoSequencial() {
		return fragmentoSequencial;
	}

	public void setFragmentoSequencial(String fragmentoSequencial) {
		this.fragmentoSequencial = fragmentoSequencial;
	}

	public String getFragmentoSubstituicao() {
		return fragmentoSubstituicao;
	}

	public void setFragmentoSubstituicao(String fragmentoSubstituicao) {
		this.fragmentoSubstituicao = fragmentoSubstituicao;
	}
	
}
