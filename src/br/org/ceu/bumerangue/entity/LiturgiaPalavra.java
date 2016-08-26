package br.org.ceu.bumerangue.entity;



public class LiturgiaPalavra extends GrupoComponenteMissa {
	private static final long serialVersionUID = 1L;

	//
	// Atributos persistentes
	//
	private Leitura1 leitura1;
	private SalmoResponsorial salmoResponsorial;
	private Leitura2 leitura2;
	private AclamacaoEvangelho aclamacaoEvangelho;
	private Evangelho evangelho;

	//
	// Atributos de auditoria
	//

	//
	// Outros Atributos
	//
	
	//
	// Contrutores
	//
	public LiturgiaPalavra(){}
	public LiturgiaPalavra(String id){ this.id = id; }

	//
	// Métodos de negócio
	//
	public boolean isValido(){
		if(!isChaveValida())
			return false;
		
		if(this.getLeitura1() == null){
			this.setErrosValidacao("A 1ª Leitura deve ser informada.");
			return false;
		}else if(!this.getLeitura1().isValido()){
			this.setErrosValidacao("1ª Leitura: "+this.getLeitura1().getErrosValidacao());
			return false;
		}
		
		if(this.getSalmoResponsorial() == null){
			this.setErrosValidacao("O Salmo Responsorial deve ser informado.");
			return false;
		}else if(!this.getSalmoResponsorial().isValido()){
			this.setErrosValidacao("Salmo Responsorial: "+this.getSalmoResponsorial().getErrosValidacao());
			return false;
		}
		
		if(this.getLeitura2() != null){
			if(!this.getLeitura2().isValido()){
				this.setErrosValidacao("2ª Leitura: "+this.getLeitura2().getErrosValidacao());
				return false;
			}
		}
		
		if(this.getAclamacaoEvangelho() == null){
			this.setErrosValidacao("A Aclamação ao Evangelho deve ser informada.");
			return false;
		}else if(!this.getAclamacaoEvangelho().isValido()){
			this.setErrosValidacao("Aclamação as Evangelho: "+this.getAclamacaoEvangelho().getErrosValidacao());
			return false;
		}
		
		if(this.getEvangelho() == null){
			this.setErrosValidacao("O Evangelho deve ser informada.");
			return false;
		}else if(!this.getEvangelho().isValido()){
			this.setErrosValidacao("Evangelho: "+this.getEvangelho().getErrosValidacao());
			return false;
		}
		
		return true;
	}
	
	public void addComponente(ComponenteMissa componenteMissa){
		if(componenteMissa == null) return;
		
		if(componenteMissa instanceof Leitura1){
			this.setLeitura1((Leitura1)componenteMissa);
		}else if (componenteMissa instanceof SalmoResponsorial){
			this.setSalmoResponsorial((SalmoResponsorial)componenteMissa);
		}else if (componenteMissa instanceof Leitura2){
			this.setLeitura2((Leitura2)componenteMissa);
		}else if (componenteMissa instanceof AclamacaoEvangelho){
			this.setAclamacaoEvangelho((AclamacaoEvangelho)componenteMissa);
		}else if (componenteMissa instanceof Evangelho){
			this.setEvangelho((Evangelho)componenteMissa);
		}
	}

	public void removeComponente(ComponenteMissa componenteMissa){
		if(componenteMissa == null) return;
		
		if(componenteMissa instanceof Leitura1){
			this.setLeitura1(null);
		}else if (componenteMissa instanceof SalmoResponsorial){
			this.setSalmoResponsorial(null);
		}else if (componenteMissa instanceof Leitura2){
			this.setLeitura2(null);
		}else if (componenteMissa instanceof AclamacaoEvangelho){
			this.setAclamacaoEvangelho(null);
		}else if (componenteMissa instanceof Evangelho){
			this.setEvangelho(null);
		}
	}

	public String getNome(){
		return "Liturgia da Palavra";
	}

	public String getLinhaComandoCadastroPadrao(){
		return "L1 mp( & )+ mc( & ) * PS mp( & )+ mc( & ) * L2 mp( & )+ mc( & ) * AE mp( & )+ mc( & ) * EV mp( & )+ mc( & )";
	}

	//
	// Métodos acessores default
	//	
	public AclamacaoEvangelho getAclamacaoEvangelho() {
		return aclamacaoEvangelho;
	}
	public void setAclamacaoEvangelho(AclamacaoEvangelho aclamacaoEvangelho) {
		this.aclamacaoEvangelho = aclamacaoEvangelho;
	}
	public Evangelho getEvangelho() {
		return evangelho;
	}
	public void setEvangelho(Evangelho evangelho) {
		this.evangelho = evangelho;
	}
	public Leitura1 getLeitura1() {
		return leitura1;
	}
	public void setLeitura1(Leitura1 leitura1) {
		this.leitura1 = leitura1;
	}
	public Leitura2 getLeitura2() {
		return leitura2;
	}
	public void setLeitura2(Leitura2 leitura2) {
		this.leitura2 = leitura2;
	}
	public SalmoResponsorial getSalmoResponsorial() {
		return salmoResponsorial;
	}
	public void setSalmoResponsorial(SalmoResponsorial salmoResponsorial) {
		this.salmoResponsorial = salmoResponsorial;
	}
}