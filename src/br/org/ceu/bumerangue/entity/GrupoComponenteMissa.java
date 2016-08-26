package br.org.ceu.bumerangue.entity;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import br.org.ceu.bumerangue.entity.helpers.GrupoComponenteMissaHelper;


public abstract class GrupoComponenteMissa extends BaseEntity {
	private static final long serialVersionUID = 1L;

	//
	// Atributos persistentes
	//
	private String chave;
	private String descricaoChave;
	private ElementoDominio festaMovel;
	
	//
	// Atributos de auditoria
	//
	private Date dataCriacao;
	private Usuario usuarioCriacao;
	private String ipCriacao;
	private Date dataUltimaAlteracao;
	private Usuario usuarioUltimaAlteracao;
	private String ipUltimaAlteracao;

	//
	// Outros Atributos
	//
	private String errosValidacao;
	private String errosValidacaoChave;
	private String linhaComandoCadastro = getLinhaComandoCadastroPadrao();
	
	//
	// Contrutores
	//
	public GrupoComponenteMissa(){}
	public GrupoComponenteMissa(String id){ super.id = id; }

	//
	// Métodos de negócio
	//
	public abstract boolean isValido();
	
	public abstract void addComponente(ComponenteMissa componenteMissa);

	public abstract void removeComponente(ComponenteMissa componenteMissa);

	public abstract String getNome();

	public boolean isSalvo(){
		return !StringUtils.isBlank(this.getId());
	}

	public boolean isObrigatoria(){
		return this.getChave() != null && this.getChave().length() >=6 && this.getChave().toUpperCase().charAt(5) == 'O';
	}

	public String getChaveDescricao(){
		return this.getChave()+" - "+this.getDescricao();
	}

	public boolean isChaveCalendarioAnoLetivo(){
		return !StringUtils.isBlank(getChave()) && chave.toUpperCase().startsWith("A");
	}

	public boolean isChaveCalendarioLiturgico(){
		return !StringUtils.isBlank(getChave()) && chave.toUpperCase().startsWith("L");
	}

	public boolean isChaveFestaMovel(){
		return !StringUtils.isBlank(getChave()) && chave.toUpperCase().startsWith("F");
	}
	
	public boolean isChaveOracaoVotiva(){
		return !StringUtils.isBlank(getChave()) && chave.toUpperCase().startsWith("V");
	}

	public boolean isChaveValida(){
		return GrupoComponenteMissaHelper.getInstance().isChaveValida(this);
	}

	public boolean isCampoChaveDescricaoChaveRequerido(){
		return this instanceof Oracao && (this.isChaveCalendarioAnoLetivo() || this.isChaveOracaoVotiva());
	}
	
	public boolean isCampoChaveFestaMovelRequerido(){
		return this.isChaveFestaMovel();
	}
	
	public boolean isCampoChaveOracaoVotivaRequerido(){
		return this instanceof Oracao && this.isChaveCalendarioAnoLetivo() && !this.isChaveOracaoCalendarioAnoLetivoPropria();
	}
	
	public boolean isChaveObrigatoria(){
		return this.isChaveFestaMovel() || (this.isChaveCalendarioAnoLetivo() && chave.length() > 5 && chave.toUpperCase().charAt(5) == 'O');
	}

	public boolean isChaveOracaoCalendarioAnoLetivoPropria(){
		return this instanceof Oracao && this.isChaveCalendarioAnoLetivo() && chave.length() > 6 && chave.toUpperCase().charAt(6) == 'P';
	}

	public boolean isChaveOracaoCalendarioAnoLetivoNaoPropria(){
		return this instanceof Oracao && this.isChaveCalendarioAnoLetivo() && chave.length() > 6 && chave.toUpperCase().charAt(6) == 'L';
	}

	public void concatenaDescricaoComplementarChave(){
		if(this.isChaveFestaMovel())
			this.setChave(this.getChave().substring(0,1)+"-"+this.getFestaMovel().getDescricao());
		else if(this instanceof Oracao && this.isChaveCalendarioAnoLetivo())
			this.setChave(this.getChave().substring(0,7)+"-"+this.getDescricaoChave());
		else if(this.isChaveOracaoVotiva())
			this.setChave(this.getChave().substring(0,1)+"-"+this.getDescricaoChave());
	}

	public String getDescricao(){
		if(!this.isChaveValida())
			return "";
			
		if(this.isChaveCalendarioAnoLetivo()){
			String descricao = getLegendaDD()+getLegendaMM();
			if(this instanceof Oracao)
				descricao += " "+getDescricaoComplementarChave()+getLegendaP();
			return descricao;
		}
		if(this.isChaveCalendarioLiturgico()){
			String descricao = getLegendaD()+getLegendaSS()+getLegendaTT();
			if(this instanceof LiturgiaPalavra)
				descricao += getLegendaAno();
			return descricao;
		}
		if(this.isChaveFestaMovel()){
			return getDescricaoComplementarChave();
		}
		if(this.isChaveOracaoVotiva()){
			return getDescricaoComplementarChave();
		}
		return "";
	}
	
	public String getDescricaoComplementarChave(){
		String legenda = this.getChave();
		legenda = legenda.substring(legenda.indexOf('-')+1);
		return legenda;
	}

	public String getLinhaComandoCadastroPadrao(){
		return "";
	}

	private String getLegendaMM() {
		String legenda = " de ";
		switch (Integer.parseInt(this.getChave().substring(1, 3))) {
		case 1:
			return legenda + "Janeiro";
		case 2:
			return legenda + "Fevereiro";
		case 3:
			return legenda + "Março";
		case 4:
			return legenda + "Abril";
		case 5:
			return legenda + "Maio";
		case 6:
			return legenda + "Junho";
		case 7:
			return legenda + "Julho";
		case 8:
			return legenda + "Agosto";
		case 9:
			return legenda + "Setembro";
		case 10:
			return legenda + "Outubro";
		case 11:
			return legenda + "Novembro";
		case 12:
			return legenda + "Dezembro";
		}
		return legenda;
	}
	
	private String getLegendaDD(){
		String legenda = " "+this.getChave().substring(3,5);
		return legenda.startsWith(" 0") ? legenda.substring(2) : legenda;
	}

	public String getLegendaP(){
		String legenda = "";

		if(this.isChaveOracaoCalendarioAnoLetivoPropria())
			legenda = " - Missa Própria";
		else if(this.isChaveOracaoCalendarioAnoLetivoNaoPropria())
			legenda = " - Missa "+((Oracao)this).getOracaoVotiva().getDescricao();

		return legenda;
	}

	private String getLegendaSS(){
		String legenda = " "+this.getChave().substring(1,3)+"ª semana";
		legenda = legenda.startsWith(" 0") ? " "+legenda.substring(2) : legenda;
		return legenda;
	}

	private String getLegendaD(){
		String legenda = " da";
		switch (this.getChave().substring(3,4).toCharArray()[0]) {
		case 'A':
		case 'B':
		case 'C':
		case 'D':
			return "Domingo"+legenda;
		case '2':
			return "Segunda-feira"+legenda;
		case '3':
			return "Terça-feira"+legenda;
		case '4':
			return "Quarta-feira"+legenda;
		case '5':
			return "Quinta-feira"+legenda;
		case '6':
			return "Sexta-feira"+legenda;
		case 'S':
			return "Sábado"+legenda;
		}
		return legenda;
	}

	private String getLegendaTT(){
		String legenda = "";
		switch (this.getChave().substring(5,6).toCharArray()[0]) {
		case 'C':
			return legenda+" do Tempo Comum";
		case 'A':
			return legenda+" do Advento";
		case 'N':
			return legenda+"Natal";
		case 'Q':
			return legenda+" da Quaresma";
		case 'P':
			return legenda+" da Páscoa";
		}
		return legenda;
	}

	private String getLegendaAno(){
		String legenda = "";
		char d = this.getChave().toUpperCase().charAt(3);
		if(d == 'A' || d == 'B' || d == 'C')
			legenda += " ("+d+")";
		else if(this.getChave().length() == 7){
			char par = this.getChave().toUpperCase().charAt(6);
			legenda += " ("+ (par == 'I' ? "I" : "II") +")";
		}

		return legenda;
	}

	public static GrupoComponenteMissa getInstance(int tipo){
		if(tipo == 1)
			return new LiturgiaPalavra();
		else if (tipo == 2)
			return new Oracao();
		return null;
	}

	public static GrupoComponenteMissa getInstance(String id, int tipo){
		GrupoComponenteMissa grupoComponenteMissa = getInstance(tipo);
		
		if(grupoComponenteMissa == null) return null;
		
		grupoComponenteMissa.setId(id);
		return grupoComponenteMissa;
	}

	//
	// Métodos acessores default
	//
	public String getErrosValidacao() {
		return errosValidacao;
	}
	public String getErrosValidacaoChave() {
		return errosValidacaoChave;
	}
	public String getChave() {
		return chave;
	}
	public void setChave(String chave) {
		this.chave = chave;
	}
	public Date getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	public Date getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}
	public void setDataUltimaAlteracao(Date dataUltimaAlteracao) {
		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}
	public String getIpCriacao() {
		return ipCriacao;
	}
	public void setIpCriacao(String ipCriacao) {
		this.ipCriacao = ipCriacao;
	}
	public String getIpUltimaAlteracao() {
		return ipUltimaAlteracao;
	}
	public void setIpUltimaAlteracao(String ipUltimaAlteracao) {
		this.ipUltimaAlteracao = ipUltimaAlteracao;
	}
	public Usuario getUsuarioCriacao() {
		return usuarioCriacao;
	}
	public void setUsuarioCriacao(Usuario usuarioCriacao) {
		this.usuarioCriacao = usuarioCriacao;
	}
	public Usuario getUsuarioUltimaAlteracao() {
		return usuarioUltimaAlteracao;
	}
	public void setUsuarioUltimaAlteracao(Usuario usuarioUltimaAlteracao) {
		this.usuarioUltimaAlteracao = usuarioUltimaAlteracao;
	}
	public String getDescricaoChave() {
		return descricaoChave;
	}
	public void setDescricaoChave(String descricaoChave) {
		this.descricaoChave = descricaoChave;
	}
	public ElementoDominio getFestaMovel() {
		return festaMovel;
	}
	public void setFestaMovel(ElementoDominio festaMovel) {
		this.festaMovel = festaMovel;
	}
	protected void setErrosValidacao(String errosValidacao) {
		this.errosValidacao = errosValidacao;
	}
	public void setErrosValidacaoChave(String errosValidacaoChave) {
		this.errosValidacaoChave = errosValidacaoChave;
	}
	public String getLinhaComandoCadastro() {
		return linhaComandoCadastro;
	}
	public void setLinhaComandoCadastro(String linhaComandoCadastro) {
		this.linhaComandoCadastro = linhaComandoCadastro;
	}
}