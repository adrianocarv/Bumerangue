package br.org.ceu.bumerangue.entity.suporte;

import java.util.ArrayList;
import java.util.List;

import br.org.ceu.bumerangue.entity.BaseEntity;
import br.org.ceu.bumerangue.entity.ObjetoBumerangue;
import br.org.ceu.bumerangue.util.Utils;

public class ResultadoVerificacaoCodigo extends BaseEntity {
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
    private List<ElementoVerificacaoCodigo> elementosVerificacaoCodigo = new ArrayList<ElementoVerificacaoCodigo>();
    private Integer totalInformado = 0;
    private Integer totalCadastrado = 0;
    private Integer totalOK = 0;
   	private Integer totalOKEmprestado = 0;
   	private Integer totalDivergente = 0;
   	private Integer totalDivergenteEmprestado = 0;
   	private Integer totalForaOrdem = 0;

	//
	// Contrutores
	//
    
	//
	// Métodos de negócio
	//
    public void addElementoVerificacaoCodigo(ObjetoBumerangue objetoBumerangueCadastrado, ObjetoBumerangue objetoBumerangueInformado){
    	this.elementosVerificacaoCodigo.add(new ElementoVerificacaoCodigo(objetoBumerangueCadastrado, objetoBumerangueInformado, elementosVerificacaoCodigo.size()+1));
    }
    
    public void calcularTotais(){
    	for(ElementoVerificacaoCodigo elementoVerificacaoCodigo : elementosVerificacaoCodigo){
    		if(elementoVerificacaoCodigo.getObjetoBumerangueCadastrado() != null) totalCadastrado++;
    		if(elementoVerificacaoCodigo.getObjetoBumerangueInformado() != null) totalInformado++;
    		if(elementoVerificacaoCodigo.isForaOrdem()) totalForaOrdem++;

    		if(elementoVerificacaoCodigo.isOK()) totalOK++;
    		else if(elementoVerificacaoCodigo.isOKEmprestado()) totalOKEmprestado++;
    		else if(elementoVerificacaoCodigo.isDivergente()) totalDivergente++;
    		else if(elementoVerificacaoCodigo.isDivergenteEmprestado()) totalDivergenteEmprestado++;
    	}
    }
    
    public List<ObjetoBumerangue> getObjetosBumerangueCadastrados(){
    	List<ObjetoBumerangue> objetosBumerangueInformados = new ArrayList<ObjetoBumerangue>();
    	for(ElementoVerificacaoCodigo elementoVerificacaoCodigo : this.getElementosVerificacaoCodigo()){
    		ObjetoBumerangue objetoBumerangue = elementoVerificacaoCodigo.getObjetoBumerangueCadastrado();
    		if(objetoBumerangue != null)
    			objetosBumerangueInformados.add(objetoBumerangue);
    	}
    	return objetosBumerangueInformados;
    }

    public List<ObjetoBumerangue> getObjetosBumerangueInformados(){
    	List<ObjetoBumerangue> objetosBumerangueInformados = new ArrayList<ObjetoBumerangue>();
    	for(ElementoVerificacaoCodigo elementoVerificacaoCodigo : this.getElementosVerificacaoCodigo()){
    		ObjetoBumerangue objetoBumerangue = elementoVerificacaoCodigo.getObjetoBumerangueInformado();
    		if(objetoBumerangue != null)
    			objetosBumerangueInformados.add(objetoBumerangue);
    	}
    	return objetosBumerangueInformados;
    }
    
    public List<ObjetoBumerangue> getObjetosBumerangueInformadosOrdenados(){
    	List<ObjetoBumerangue> objetosBumerangueInformados = this.getObjetosBumerangueInformados();
    	objetosBumerangueInformados = new ArrayList<ObjetoBumerangue>(Utils.sort(objetosBumerangueInformados,"codigo"));
    	return objetosBumerangueInformados;
    }

    public void setObjetoBumerangueInformado(ObjetoBumerangue objetoBumerangueInformado, int posicaoCorreta){
    	if(objetoBumerangueInformado == null)
    		return;
    	for(ElementoVerificacaoCodigo elementoVerificacaoCodigo : this.getElementosVerificacaoCodigo()){
    		ObjetoBumerangue objetoBumerangue = elementoVerificacaoCodigo.getObjetoBumerangueInformado();
    		if(objetoBumerangue != null && objetoBumerangue.getCodigo().equalsIgnoreCase(objetoBumerangueInformado.getCodigo())){
    			elementoVerificacaoCodigo.setObjetoBumerangueInformado(objetoBumerangueInformado, posicaoCorreta);
    			break;
    		}
    	}
    }
    
	//
	// Métodos acessores default
	//
	public List<ElementoVerificacaoCodigo> getElementosVerificacaoCodigo() {
		return elementosVerificacaoCodigo;
	}

	public void setElementosVerificacaoCodigo(List<ElementoVerificacaoCodigo> elementosVerificacaoCodigo) {
		this.elementosVerificacaoCodigo = elementosVerificacaoCodigo;
	}

	public Integer getTotalCadastrado() {
		return totalCadastrado;
	}

	public Integer getTotalDivergente() {
		return totalDivergente;
	}

	public Integer getTotalDivergenteEmprestado() {
		return totalDivergenteEmprestado;
	}

	public Integer getTotalInformado() {
		return totalInformado;
	}

	public Integer getTotalOK() {
		return totalOK;
	}

	public Integer getTotalOKEmprestado() {
		return totalOKEmprestado;
	}

	public Integer getTotalForaOrdem() {
		return totalForaOrdem;
	}
	
}
