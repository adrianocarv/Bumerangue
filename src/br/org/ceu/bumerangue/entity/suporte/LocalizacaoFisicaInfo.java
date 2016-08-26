package br.org.ceu.bumerangue.entity.suporte;

import java.util.ArrayList;
import java.util.List;

import br.org.ceu.bumerangue.entity.BaseEntity;
import br.org.ceu.bumerangue.entity.ObjetoBumerangue;

public class LocalizacaoFisicaInfo extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	//
	// Atributos persistentes
	//

	//
	// Atributos de auditoria
	//

	//
	// Outros Atributos
	//
    private String camposOrdenacao;
    private String grupoCompartimentos;
	private Integer totalCompartimentos;
	private Integer totalObjetosBumerangue;
	private Integer totalObjetosBumerangueSemLocalizacao;
    private List<ObjetoBumerangue> objetosBumerangueSemEspacoCompartimentos = new ArrayList<ObjetoBumerangue>();
    private List<ObjetoBumerangue> objetosBumerangueAtualizados = new ArrayList<ObjetoBumerangue>();
    private String dataUltimaAtualizacao;

	//
	// Contrutores
	//

	//
	// Métodos de negócio
	//
	public Integer getTotalObjetosBumerangueAtualizados() {
		return objetosBumerangueAtualizados.size();
	}
	public Integer getTotalObjetosBumerangueSemEspacoCompartimentos() {
		return objetosBumerangueSemEspacoCompartimentos.size();
	}
	public Integer getTotalEspacosDisponiviesCompartimentos() {
		return totalCompartimentos - totalObjetosBumerangue;
	}

	//
	// Métodos acessores default
	//
    public String getDataUltimaAtualizacao() {
		return dataUltimaAtualizacao;
	}
	public void setDataUltimaAtualizacao(String dataUltimaAtualizacao) {
		this.dataUltimaAtualizacao = dataUltimaAtualizacao;
	}
	public List<ObjetoBumerangue> getObjetosBumerangueAtualizados() {
		return objetosBumerangueAtualizados;
	}
	public void setObjetosBumerangueAtualizados(List<ObjetoBumerangue> objetosBumerangueAtualizados) {
		this.objetosBumerangueAtualizados = objetosBumerangueAtualizados;
	}
	public List<ObjetoBumerangue> getObjetosBumerangueSemEspacoCompartimentos() {
		return objetosBumerangueSemEspacoCompartimentos;
	}
	public void setObjetosBumerangueSemEspacoCompartimentos(List<ObjetoBumerangue> objetosBumerangueSemEspacoCompartimentos) {
		this.objetosBumerangueSemEspacoCompartimentos = objetosBumerangueSemEspacoCompartimentos;
	}
	public Integer getTotalObjetosBumerangueSemLocalizacao() {
		return totalObjetosBumerangueSemLocalizacao;
	}
	public void setTotalObjetosBumerangueSemLocalizacao(Integer totalObjetosBumerangueSemLocalizacao) {
		this.totalObjetosBumerangueSemLocalizacao = totalObjetosBumerangueSemLocalizacao;
	}
	public Integer getTotalObjetosBumerangue() {
		return totalObjetosBumerangue;
	}
	public void setTotalObjetosBumerangue(Integer totalObjetosBumerangue) {
		this.totalObjetosBumerangue = totalObjetosBumerangue;
	}
	public String getCamposOrdenacao() {
		return camposOrdenacao;
	}
	public void setCamposOrdenacao(String camposOrdenacao) {
		this.camposOrdenacao = camposOrdenacao;
	}
	public String getGrupoCompartimentos() {
		return grupoCompartimentos;
	}
	public void setGrupoCompartimentos(String grupoCompartimentos) {
		this.grupoCompartimentos = grupoCompartimentos;
	}
	public Integer getTotalCompartimentos() {
		return totalCompartimentos;
	}
	public void setTotalCompartimentos(Integer totalCompartimentos) {
		this.totalCompartimentos = totalCompartimentos;
	}
}
