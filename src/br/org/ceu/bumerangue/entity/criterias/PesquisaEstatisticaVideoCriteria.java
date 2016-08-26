package br.org.ceu.bumerangue.entity.criterias;

public class PesquisaEstatisticaVideoCriteria extends PesquisaObjetoCriteria {
 
	private String codigo;
	 
	private String midia;
	 
	private String localidade;
	 
	private String idCentro;
	 
	private String dataIniEmprestimo;
	 
	private String dataFimEmprestimo;
	 
	private Boolean agruparCentro;
	 
	private Boolean agruparVideo;
	 
	private int agruparPeriodo;

	public boolean isEmpty(){
		return false;
	}

	public Boolean getAgruparCentro() {
		return agruparCentro;
	}

	public void setAgruparCentro(Boolean agruparCentro) {
		this.agruparCentro = agruparCentro;
	}

	public int getAgruparPeriodo() {
		return agruparPeriodo;
	}

	public void setAgruparPeriodo(int agruparPeriodo) {
		this.agruparPeriodo = agruparPeriodo;
	}

	public Boolean getAgruparVideo() {
		return agruparVideo;
	}

	public void setAgruparVideo(Boolean agruparVideo) {
		this.agruparVideo = agruparVideo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDataFimEmprestimo() {
		return dataFimEmprestimo;
	}

	public void setDataFimEmprestimo(String dataFimEmprestimo) {
		this.dataFimEmprestimo = dataFimEmprestimo;
	}

	public String getDataIniEmprestimo() {
		return dataIniEmprestimo;
	}

	public void setDataIniEmprestimo(String dataIniEmprestimo) {
		this.dataIniEmprestimo = dataIniEmprestimo;
	}

	public String getIdCentro() {
		return idCentro;
	}

	public void setIdCentro(String idCentro) {
		this.idCentro = idCentro;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getMidia() {
		return midia;
	}

	public void setMidia(String midia) {
		this.midia = midia;
	}
}
 
