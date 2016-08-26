package br.org.ceu.bumerangue.entity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.commons.lang.StringUtils;

import br.org.ceu.bumerangue.util.Utils;

public class FilmeComercial extends ObjetoBumerangue {
	private static final long serialVersionUID = 1L;

	//
	// Atributos persistentes
	//
	private String tituloOriginal;
	private ElementoDominio publico;
	private Integer duracaoMinutos;
	private Integer ano;
	private ElementoDominio categoria;
	private String diretor;
	private String atoresObservacoes;
	private String linkSinopse;
	private String linksDownload;

	//
	// Atributos de auditoria
	//

	//
	// Outros Atributos
	//
	
	//
	// Contrutores
	//
	public FilmeComercial(){}
	public FilmeComercial(String id){ this.id = id; }

	//
	// M�todos de neg�cio
	//
	/**
	 * Seta o valor do c�digo no padr�o: 99999
	 * @param codigo
	 */
	public void setCodigoFormatado(String codigo) {
		super.setCodigo(Utils.insertFragmment(codigo,"0",5-codigo.length(),true));
	}

	public String getLinkSinopseDecorator(){
		return this.getLinkSinopse();
	}

	public String[] getLinksDownloadAsArray(){
		String[] links = StringUtils.split(this.getLinksDownload().trim(), ';');
		return links;
	}
	
	public boolean isDownloadUnico(){
		return this.getLinksDownload() != null && this.getLinksDownloadAsArray().length == 1;
	}
	
	public boolean isDownloadMultiplo(){
		return this.getLinksDownload() != null && this.getLinksDownloadAsArray().length > 1;
	}
	
	private boolean isDownloadPro(){
		return this.getLinksDownload() != null && StringUtils.contains(this.getLinksDownloadAsArray()[0], "pro/");
	}
	
	public String getURLDownload(int parte) throws Exception {
		
		//padr�o novo
		if(this.isDownloadPro())
			return this.getLinksDownloadAsArray()[parte];
		
		String urlDownload = null;
		URL url = new URL(this.getLinksDownloadAsArray()[parte]);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

		String line = "";
		String strIni = "<a id=\"download_button\" href=\"";
		String strFim = "\" onclick";
		
		System.out.println("strIni: " + strIni);
		System.out.println("strFim: " + strFim);
		
		//String font = "";
		while((line = in.readLine()) != null){
			//font += line + "\n";

			int ini = line.indexOf(strIni);

			if(ini < 0 )
				continue;

			System.out.println("\n Encontrou: " + strIni + "\n");
			int fim = line.indexOf(strFim, ini);
			urlDownload = StringUtils.substring(line, ini + strIni.length(), fim);
			break;
		}
		in.close();

		urlDownload = urlDownload == null ? this.getLinksDownloadAsArray()[parte] : urlDownload;
		
		return urlDownload;
	}
	
	//
	// M�todos acessores default
	//	
	public Integer getAno() {
		return ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	public String getAtoresObservacoes() {
		return atoresObservacoes;
	}
	public void setAtoresObservacoes(String atoresObservacoes) {
		this.atoresObservacoes = atoresObservacoes;
	}
	public ElementoDominio getCategoria() {
		return categoria;
	}
	public void setCategoria(ElementoDominio categoria) {
		this.categoria = categoria;
	}
	public String getDiretor() {
		return diretor;
	}
	public void setDiretor(String diretor) {
		this.diretor = diretor;
	}
	public Integer getDuracaoMinutos() {
		return duracaoMinutos;
	}
	public void setDuracaoMinutos(Integer duracaoMinutos) {
		this.duracaoMinutos = duracaoMinutos;
	}
	public String getLinkSinopse() {
		return linkSinopse;
	}
	public void setLinkSinopse(String linkSinopse) {
		this.linkSinopse = linkSinopse;
	}
	public ElementoDominio getPublico() {
		return publico;
	}
	public void setPublico(ElementoDominio publico) {
		this.publico = publico;
	}
	public String getTituloOriginal() {
		return tituloOriginal;
	}
	public void setTituloOriginal(String tituloOriginal) {
		this.tituloOriginal = tituloOriginal;
	}
	public String getLinksDownload() {
		return linksDownload;
	}
	public void setLinksDownload(String linksDownload) {
		this.linksDownload = linksDownload;
	}
}
