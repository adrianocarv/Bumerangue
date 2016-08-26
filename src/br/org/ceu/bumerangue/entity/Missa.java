package br.org.ceu.bumerangue.entity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ibm.icu.text.SimpleDateFormat;


public class Missa extends BaseEntity {
	private static final long serialVersionUID = 1L;

	//
	// Atributos persistentes
	//
	private Integer dia;
	private LiturgiaPalavra liturgiaPalavra;
	private Oracao oracao;
	private PlanoMissa planoMissa;

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
	
	//
	// Contrutores
	//
	public Missa(){}
	public Missa(String id){ this.id = id; }

	//
	// Métodos de negócio
	//
	public boolean isValida(){
		return isLiturgiaPalavraValida() && isOracaoValida() /*&& isLiturgiaPalavraOracaoCorrespondentes()*/;
	}

	private boolean isLiturgiaPalavraValida(){
		if(this.getLiturgiaPalavra() == null){
			this.setErrosValidacao("A Liturgia da Palavra deve ser informada.");
			return false;
		}else if(!this.getLiturgiaPalavra().isValido()){
			this.setErrosValidacao("Liturgia da Palavra: "+this.getLiturgiaPalavra().getErrosValidacao());
			return false;
		}
		return true;
	}

	private boolean isOracaoValida(){
		if(this.getOracao() == null){
			this.setErrosValidacao("A Oração deve ser informada.");
			return false;
		}else if(!this.getOracao().isValido()){
			this.setErrosValidacao("Oração: "+this.getOracao().getErrosValidacao());
			return false;
		}
		return true;
	}

	private boolean isLiturgiaPalavraOracaoCorrespondentes(){
		
		//AO - Ano Letivo e Obrigatória
		if(this.getLiturgiaPalavra().isChaveCalendarioAnoLetivo() && this.getLiturgiaPalavra().isObrigatoria()){
			//AOP - Ano Letivo, Obrigatória e Própria
			if(this.getOracao().isChaveCalendarioAnoLetivo() && this.getOracao().isObrigatoria() && this.getOracao().isPropria())
				return true;
			//AOL - Ano Letivo, Obrigatória e Não Própria
			if(this.getOracao().isChaveCalendarioAnoLetivo() && this.getOracao().isObrigatoria() && !this.getOracao().isPropria())
				return true;
		}
		//AL - Ano Letivo e Livre
		else if(this.getLiturgiaPalavra().isChaveCalendarioAnoLetivo() && this.getLiturgiaPalavra().getChave().toUpperCase().charAt(5) == 'L'){
			//ALP - Ano Letivo, Livre e Própria
			if(this.getOracao().isChaveCalendarioAnoLetivo() && !this.getOracao().isObrigatoria() && this.getOracao().isPropria())
				return true;
			//ALL - Ano Letivo, Livre e Não Própria
			if(this.getOracao().isChaveCalendarioAnoLetivo() && !this.getOracao().isObrigatoria() && !this.getOracao().isPropria())
				return true;
			
		}
		//L - Calendário Litúrgico
		else if(this.getLiturgiaPalavra().isChaveCalendarioLiturgico()){
			//AOP - Ano Letivo, Obrigatória e Própria
			if(this.getOracao().isChaveCalendarioAnoLetivo() && this.getOracao().isObrigatoria() && this.getOracao().isPropria())
				return true;
			//AOL - Ano Letivo, Obrigatória e Não Própria
			if(this.getOracao().isChaveCalendarioAnoLetivo() && this.getOracao().isObrigatoria() && !this.getOracao().isPropria())
				return true;
			//ALP - Ano Letivo, Livre e Própria
			if(this.getOracao().isChaveCalendarioAnoLetivo() && !this.getOracao().isObrigatoria() && this.getOracao().isPropria())
				return true;
			//ALL - Ano Letivo, Livre e Não Própria
			if(this.getOracao().isChaveCalendarioAnoLetivo() && !this.getOracao().isObrigatoria() && !this.getOracao().isPropria())
				return true;
			//L - Calendário Litúrgico
			if(this.getOracao().isChaveCalendarioLiturgico())
				return true;
			//V - Votiva
			if(this.getOracao().isChaveOracaoVotiva())
				return true;
		}
		//F - Festa Móvel
		else if(this.getLiturgiaPalavra().isChaveFestaMovel()){
			//F - Festa Móvel
			if(this.getOracao().isChaveFestaMovel())
				return true;
		}

		this.setErrosValidacao("A Liturgia da Palavra e a Oração não se correspondem.");
		return false;
	}

	public String getTituloLiturgiaPalavra(){
		if(!this.isValida()) return this.getErrosValidacao();

		//Exibido quando LP for do tipo L
		if(!this.getLiturgiaPalavra().isChaveCalendarioLiturgico())
			return "";
		
		return this.getLiturgiaPalavra().getDescricao();
	}
	
	public String getTituloOracao(){
		if(!this.isValida()) return "";

		//Exibido quando OR não for do tipo L
		if(this.getOracao().isChaveCalendarioLiturgico())
			return "";

		String tituloOracao = this.getOracao().getDescricaoComplementarChave();
		if(this.getOracao().isChaveCalendarioAnoLetivo()){
			tituloOracao += this.getOracao().getLegendaP();
		}

		return tituloOracao;
	}
	
	public String getPrefacio(){
		if(!this.isValida()) return "";

		return this.getOracao().getPrefacioRelativo().getDescricao()+" - "+this.getPaginasReferencias(this.getOracao().getPrefacioRelativo().getReferenciasMissais());
	}
	
	public String getPaginaMissa(){
		if(!this.isValida()) return "";

		return this.getPaginasReferencias(this.getOracao().getIntroitoRelativo().getReferenciasMissais());
	}
	
	public String getPaginaLeitura1(){
		if(!this.isValida()) return "";
		
		return "1Lt - "+this.getPaginasReferencias(this.getLiturgiaPalavra().getLeitura1().getReferenciasMissais());
	}
	
	public String getPaginaSalmoResponsorial(){
		if(!this.isValida()) return "";
		
		return "Sl - "+this.getPaginasReferencias(this.getLiturgiaPalavra().getSalmoResponsorial().getReferenciasMissais());
	}

	public String getPaginaLeitura2(){
		if(!this.isValida()) return "";
		if(this.getLiturgiaPalavra().getLeitura2() == null) return ""; 
		
		return "2Lt - "+this.getPaginasReferencias(this.getLiturgiaPalavra().getLeitura2().getReferenciasMissais());
	}

	public String getPaginaEvangelho(){
		if(!this.isValida()) return "";
		
		return "Ev - "+this.getPaginasReferencias(this.getLiturgiaPalavra().getEvangelho().getReferenciasMissais());
	}

	private String getPaginasReferencias(List<ReferenciaMissal> referenciasMissais){
		//ordena as referencias baseado na ordem dos idiomas
		List<ReferenciaMissal> referenciasMissaisOrdenadas = new ArrayList<ReferenciaMissal>();
		for(ElementoDominio edicaoIdioma : this.getPlanoMissa().getEdicoesIdiomasOrdenados()){
			for(ReferenciaMissal referenciaMissal : referenciasMissais){
				if( referenciaMissal.getEdicaoIdioma().getId().equals(edicaoIdioma.getId()) )
					referenciasMissaisOrdenadas.add(referenciaMissal);
			}
		}
		
		String paginasReferencias = "";
		for(ReferenciaMissal referenciaMissal : referenciasMissaisOrdenadas){
			if(!paginasReferencias.equals(""))
				paginasReferencias += "\t";
			paginasReferencias += referenciaMissal.getEdicaoIdioma().getDescricao()+": "+referenciaMissal.getPagina();
		}
		return paginasReferencias;
	}

	public String getIntroito(){
		if(!this.isValida()) return "";

		return this.getOracao().getIntroitoRelativo().getTextoLatim();
	}
	
	public String getSalmoResponsorial(){
		if(!this.isValida()) return "";

		return this.getLiturgiaPalavra().getSalmoResponsorial().getTextoLatim();
	}
	
	public String getAclamacaoEvangelho(){
		if(!this.isValida()) return "";

		return this.getLiturgiaPalavra().getAclamacaoEvangelho().getTextoLatim();
	}
	
	public String getAntifonaComunhao(){
		if(!this.isValida()) return "";

		return this.getOracao().getAntifonaComunhaoRelativo().getTextoLatim();
	}
	
	public String getDiaFormatado(){
		//calcula o dia da semana
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddyyyyMM");
		Date date;
		try {
			date = dateFormat.parse(this.getDia()+this.getPlanoMissa().getAnoMes());
		} catch (ParseException e) {
			return "";
		}

		String[] dias = {"Domingo","2ª Feira","3ª Feira","4ª Feira","5ª Feira","6ª Feira","Sábado"};
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dia = calendar.get(Calendar.DAY_OF_WEEK);
		String diaSemana = dias[dia-1];

		return diaSemana+", "+this.getDia()+"/"+this.getPlanoMissa().getMesAnoFormatado();
	}

	//
	// Métodos acessores default
	//
	public String getErrosValidacao() {
		return errosValidacao;
	}
	public void setErrosValidacao(String errosValidacao) {
		this.errosValidacao = errosValidacao;
	}
	public Integer getDia() {
		return dia;
	}
	public void setDia(Integer dia) {
		this.dia = dia;
	}
	public LiturgiaPalavra getLiturgiaPalavra() {
		return liturgiaPalavra;
	}
	public void setLiturgiaPalavra(LiturgiaPalavra liturgiaPalavra) {
		this.liturgiaPalavra = liturgiaPalavra;
	}
	public Oracao getOracao() {
		return oracao;
	}
	public void setOracao(Oracao oracao) {
		this.oracao = oracao;
	}
	public PlanoMissa getPlanoMissa() {
		return planoMissa;
	}
	public void setPlanoMissa(PlanoMissa planoMissa) {
		this.planoMissa = planoMissa;
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
}