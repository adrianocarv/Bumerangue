package br.org.ceu.bumerangue.entity.suporte;

import br.org.ceu.bumerangue.entity.BaseEntity;
import br.org.ceu.bumerangue.entity.ObjetoBumerangue;

public class ElementoVerificacaoCodigo extends BaseEntity {
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
    private int index;
    private ObjetoBumerangue objetoBumerangueCadastrado;
    private ObjetoBumerangue objetoBumerangueInformado;
    private boolean foraOrdem;
    private int posicaoCorreta = -1;

	//
	// Contrutores
	//
    public ElementoVerificacaoCodigo(ObjetoBumerangue objetoBumerangueCadastrado, ObjetoBumerangue objetoBumerangueInformado, int index){
    	this.objetoBumerangueCadastrado = objetoBumerangueCadastrado;
    	this.objetoBumerangueInformado = objetoBumerangueInformado;
    	this.index = index;
    }
    
	//
	// Métodos de negócio
	//
    public String getResultado(){
    	if(isForaOrdem()) return "FORA DE ORDEM";
    	if(isOK()) return "OK";
    	if(isOKEmprestado()) return "OK, porém emprestado";
    	if(isDivergente()) return "Divergente";
    	if(isDivergenteEmprestado()) return "Divergente, porém emprestado";
    	return "";
    }

	public String getPosicaoCorretaFormatado() {
		return posicaoCorreta == -1 ? "" : posicaoCorreta+"º";
	}

	public String getSituacao(){
    	return objetoBumerangueCadastrado == null ? "" : objetoBumerangueCadastrado.getSituacao();
    }

    public String getTitulo(){
    	return objetoBumerangueCadastrado == null ? "" : objetoBumerangueCadastrado.getTitulo();
    }

    public String getLocalizacaoFisica(){
    	return objetoBumerangueCadastrado == null ? "" : objetoBumerangueCadastrado.getLocalizacaoFisica();
    }

    public boolean isOK(){
    	return objetoBumerangueCadastrado != null && objetoBumerangueInformado != null && !objetoBumerangueCadastrado.getIsEmprestado();
    }
    
    public boolean isOKEmprestado(){
    	return objetoBumerangueCadastrado != null && objetoBumerangueInformado != null && objetoBumerangueCadastrado.getIsEmprestado();
    }

    public boolean isDivergente(){
    	return objetoBumerangueCadastrado == null || objetoBumerangueInformado == null && !objetoBumerangueCadastrado.getIsEmprestado();
    }

    public boolean isDivergenteEmprestado(){
    	return objetoBumerangueCadastrado == null || objetoBumerangueInformado == null && objetoBumerangueCadastrado.getIsEmprestado();
    }

	public void setObjetoBumerangueInformado(ObjetoBumerangue objetoBumerangueInformado, int posicaoCorreta) {
		this.objetoBumerangueInformado = objetoBumerangueInformado;
		this.foraOrdem = true;
		this.posicaoCorreta = posicaoCorreta;
	}
	
    //
	// Métodos acessores default
	//
	public int getIndex() {
		return index;
	}

	public ObjetoBumerangue getObjetoBumerangueCadastrado() {
		return objetoBumerangueCadastrado;
	}

	public ObjetoBumerangue getObjetoBumerangueInformado() {
		return objetoBumerangueInformado;
	}

	public boolean isForaOrdem() {
		return foraOrdem;
	}

	public int getPosicaoCorreta() {
		return posicaoCorreta;
	}

	public void setForaOrdem(boolean foraOrdem) {
		this.foraOrdem = foraOrdem;
	}
	
}
