package br.org.ceu.bumerangue.entity;


public class Oracao extends GrupoComponenteMissa {
	private static final long serialVersionUID = 1L;

	//
	// Atributos persistentes
	//
	private Introito introito;
	private Prefacio prefacio;
	private AntifonaComunhao antifonaComunhao;
	private Oracao oracaoVotiva;
	
	//
	// Atributos de auditoria
	//

	//
	// Outros Atributos
	//
	
	//
	// Contrutores
	//
	public Oracao(){}
	public Oracao(String id){ this.id = id; }

	//
	// M�todos de neg�cio
	//
	public boolean isValido(){
		if(!isChaveValida())
			return false;

		if(this.getIntroitoRelativo() == null){
			this.setErrosValidacao("O Intr�ito deve ser informado.");
			return false;
		}else if(!this.getIntroitoRelativo().isValido()){
			this.setErrosValidacao("Intr�ito: "+this.getIntroitoRelativo().getErrosValidacao());
			return false;
		}
		if(this.getPrefacioRelativo() == null){
			this.setErrosValidacao("O Pref�cio deve ser informado.");
			return false;
		}else if(!this.getPrefacioRelativo().isValido()){
			this.setErrosValidacao("Pref�cio: "+this.getPrefacioRelativo().getErrosValidacao());
			return false;
		}
		if(this.getAntifonaComunhaoRelativo() == null){
			this.setErrosValidacao("A Ant�fona de Comunh�o deve ser informada.");
			return false;
		}else if(!this.getAntifonaComunhaoRelativo().isValido()){
			this.setErrosValidacao("Ant�fona de Comunh�o: "+this.getAntifonaComunhaoRelativo().getErrosValidacao());
			return false;
		}

		return true;
	}

	public boolean isPropria(){
		return this.getChave() != null && this.getChave().length() >=7 && this.getChave().toUpperCase().charAt(6) == 'P';
	}
	
	public void addComponente(ComponenteMissa componenteMissa){
		if(componenteMissa == null) return;
		
		if(componenteMissa instanceof Introito){
			this.setIntroito((Introito)componenteMissa);
		}else if (componenteMissa instanceof Prefacio){
			this.setPrefacio((Prefacio)componenteMissa);
		}else if (componenteMissa instanceof AntifonaComunhao){
			this.setAntifonaComunhao((AntifonaComunhao)componenteMissa);
		}
	}

	public void removeComponente(ComponenteMissa componenteMissa){
		if(componenteMissa == null) return;
		
		if(componenteMissa instanceof Introito){
			this.setIntroito(null);
		}else if (componenteMissa instanceof Prefacio){
			this.setPrefacio(null);
		}else if (componenteMissa instanceof AntifonaComunhao){
			this.setAntifonaComunhao(null);
		}
	}

	public String getNome(){
		return "Ora��o";
	}

	public String getLinhaComandoCadastroPadrao(){
		return "IT mp( & )+ mc( & ) * PF mp( & )+ mc( & ) * AC mp( & )+ mc( & )";
	}


	public Introito getIntroitoRelativo(){
		return this.oracaoVotiva == null ? this.getIntroito() : this.oracaoVotiva.getIntroito(); 
	}

	public Prefacio getPrefacioRelativo(){
		//quanto ao Pref�cio, � relativo tamb�m de acordo com o PrefacioCompartilhado
		Prefacio prefacio = this.oracaoVotiva == null ? this.getPrefacio() : this.oracaoVotiva.getPrefacio();
		if(prefacio == null)
			return null;
		return prefacio.getPrefacioCompartilhado() == null ? prefacio : prefacio.getPrefacioCompartilhado(); 
	}

	public AntifonaComunhao getAntifonaComunhaoRelativo(){
		return this.oracaoVotiva == null ? this.getAntifonaComunhao() : this.oracaoVotiva.getAntifonaComunhao(); 
	}

	//
	// M�todos acessores default
	//
	public AntifonaComunhao getAntifonaComunhao() {
		return antifonaComunhao;
	}
	public void setAntifonaComunhao(AntifonaComunhao antifonaComunhao) {
		this.antifonaComunhao = antifonaComunhao;
	}
	public Introito getIntroito() {
		return introito;
	}
	public void setIntroito(Introito introito) {
		this.introito = introito;
	}
	public Prefacio getPrefacio() {
		return prefacio;
	}
	public void setPrefacio(Prefacio prefacio) {
		this.prefacio = prefacio;
	}
	public Oracao getOracaoVotiva() {
		return oracaoVotiva;
	}
	public void setOracaoVotiva(Oracao oracaoVotiva) {
		this.oracaoVotiva = oracaoVotiva;
	}
}