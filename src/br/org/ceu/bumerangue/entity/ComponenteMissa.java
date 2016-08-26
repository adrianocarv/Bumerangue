package br.org.ceu.bumerangue.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import br.org.ceu.bumerangue.service.exceptions.BumerangueErrorRuntimeException;
import br.org.ceu.bumerangue.util.ValidationRules;


public abstract class ComponenteMissa extends BaseEntity {
	private static final long serialVersionUID = 1L;

	//
	// Atributos persistentes
	//
	private String textoLatim;
	private List<ReferenciaMissal> referenciasMissais = new ArrayList<ReferenciaMissal>();

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
	private GrupoComponenteMissa grupoComponenteMissa; 
	
	//
	// Contrutores
	//
	public ComponenteMissa(){}
	public ComponenteMissa(String id){ super.id = id; }

	//
	// Métodos de negócio
	//
	public boolean isSalvo(){
		return !StringUtils.isBlank(this.getId());
	}

	public boolean isValido(){
		//verifica o preenchimento do campo textoLatim
		if(!this.isTextoLatimValido())
			return false;
		
		//valida os campos do prefácio
		if(!this.isCamposPrefacioValidos())
			return false;

		if(this.getReferenciasMissais().size() <= 0){
			this.setErrosValidacao("Deve haver pelo menos uma referência no missal cadastrada.");
			return false;
		}

		for(ReferenciaMissal referenciaMissal : this.getReferenciasMissaisRelativo()){
			if(!referenciaMissal.isValido()){
				this.setErrosValidacao("Referência no missal: "+referenciaMissal.getErrosValidacao());
				return false;
			}
		}
		
		return true;
	}

	public boolean isTextoLatimValido(){
		if (this instanceof Introito
				|| this instanceof SalmoResponsorial
				|| this instanceof AclamacaoEvangelho
				|| this instanceof AntifonaComunhao) {
			if(!ValidationRules.isInformed(this.getTextoLatim())){
				this.setErrosValidacao("O campo Texto em latim deve ser informado.");
				return false;
			}
		}
		return true;
	}
	
	public boolean isCamposPrefacioValidos(){
		//desconsidera outros tipos de componente
		if( !(this instanceof Prefacio) )
			return true;

		Prefacio prefacio = (Prefacio)this;
		
		boolean isDescricaoPrefacioRequerido = prefacio.isDescricaoPrefacioRequerido();
		boolean isPrefacioCompartilhadoRequerido = prefacio.isPrefacioCompartilhadoRequerido();

		boolean isDescricaoPrefacioInformado = !StringUtils.isBlank(prefacio.getDescricao());
		boolean isPrefacioCompartilhadoInformado = prefacio.getPrefacioCompartilhado() != null && ValidationRules.isInformed(prefacio.getPrefacioCompartilhado().getId());
		boolean isTextoLatimInformado = !StringUtils.isBlank(prefacio.getTextoLatim());
		
		if( (isDescricaoPrefacioRequerido  && !isDescricaoPrefacioInformado) || (!isDescricaoPrefacioRequerido && isDescricaoPrefacioInformado) ){
			this.setErrosValidacao("O campo 'Descrição' "+ (isDescricaoPrefacioRequerido ? "deve" : "não deve") +" ser informado.");
			return false;
		}

		if( (isPrefacioCompartilhadoRequerido && !isPrefacioCompartilhadoInformado) || (!isPrefacioCompartilhadoRequerido && isPrefacioCompartilhadoInformado) ){
			this.setErrosValidacao("O campo 'Prefácio Compartilhado' "+ (isPrefacioCompartilhadoRequerido ? "deve" : "não deve") +" ser informado.");
			return false;
		}

		//valida o texto em latim
		if( (isPrefacioCompartilhadoRequerido && isTextoLatimInformado)){
			this.setErrosValidacao("O campo 'Texto em latim' não deve ser informado.");
			return false;
		}

		//valida os checks
		if(prefacio.getProprio() && prefacio.getCompartilhado()){
			this.setErrosValidacao("O prefácio não pode ser Próprio e Compartilhado ao mesmo tempo.");
			return false;
		}
		
		return true;
	}

	public String getPaginasMissais(){
		if(this.getReferenciasMissaisRelativo().isEmpty()) return "sem referências";
		
		String paginasMissais = "";
		boolean primeiro = true;
		for(ReferenciaMissal referenciaMissal : this.getReferenciasMissaisRelativo()){
			if(!primeiro)
				paginasMissais += ", ";
			paginasMissais += referenciaMissal.getPagina() + " " + referenciaMissal.getEdicaoIdioma().getDescricao();
			primeiro = false;
		}
		return paginasMissais;
	}
	
	public boolean isDescricaoPrefacioRequerido(){
		if( !(this instanceof Prefacio) )
			return false;
		Prefacio prefacio = (Prefacio)this;
		return prefacio.getProprio() || prefacio.getCompartilhado();  
	}

	public boolean isPrefacioCompartilhadoRequerido(){
		if( !(this instanceof Prefacio) )
			return false;
		Prefacio prefacio = (Prefacio)this;
		return !prefacio.getProprio() && !prefacio.getCompartilhado();  
	}

	public boolean isPrefacio(){
		return this instanceof Prefacio;
	}

	public String getTextoLatimRelativo(){
		return ( this instanceof Prefacio && ((Prefacio)this).getPrefacioCompartilhado() != null ) ? ((Prefacio)this).getPrefacioCompartilhado().getTextoLatim() : this.getTextoLatim();
	}
	
	public List<ReferenciaMissal> getReferenciasMissaisRelativo(){
		return this instanceof Prefacio && ((Prefacio)this).getPrefacioCompartilhado() != null ? ((Prefacio)this).getPrefacioCompartilhado().getReferenciasMissais() : this.getReferenciasMissais();
	}
	
	public abstract String getNome();
	
	public static ComponenteMissa getInstance(int tipoComponente){
		switch (tipoComponente){
			case 1: return new Leitura1();
			case 2: return new SalmoResponsorial();
			case 3: return new Leitura2();
			case 4: return new AclamacaoEvangelho();
			case 5: return new Evangelho();
			case 6: return new Introito();
			case 7: return new Prefacio();
			case 8: return new AntifonaComunhao();
			default : return null;
		}
	}

	//
	// Métodos acessores default
	//	
	public String getErrosValidacao() {
		return errosValidacao;
	}

	public List<ReferenciaMissal> getReferenciasMissais() {
		return referenciasMissais;
	}

	public void setReferenciasMissais(List<ReferenciaMissal> referenciasMissais) {
		this.referenciasMissais = referenciasMissais;
	}

	public String getTextoLatim() {
		return textoLatim;
	}

	public void setTextoLatim(String textoLatim) {
		this.textoLatim = textoLatim;
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
	public GrupoComponenteMissa getGrupoComponenteMissa() {
		return grupoComponenteMissa;
	}
	public void setGrupoComponenteMissa(GrupoComponenteMissa grupoComponenteMissa) {
		this.grupoComponenteMissa = grupoComponenteMissa;
	}
	public void setErrosValidacao(String errosValidacao) {
		this.errosValidacao = errosValidacao;
	}
	
}