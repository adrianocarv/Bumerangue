package br.org.ceu.bumerangue.entity.suporte;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import br.org.ceu.bumerangue.entity.BaseEntity;
import br.org.ceu.bumerangue.entity.Permissao;
import br.org.ceu.bumerangue.service.impl.BaseServiceImpl;

public class Arquivo extends BaseEntity implements Comparable {
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
    private File file;
    private Permissao permissao;
    private boolean compartilhadoTodasPermissoes;

	//
	// Contrutores
	//
    public Arquivo(File file, Permissao permissao, boolean compartilhadoTodasPermissoes){
    	this.file = file;
    	this.permissao = permissao;
    	this.compartilhadoTodasPermissoes = compartilhadoTodasPermissoes && permissao != null;
    }
    
	//
	// Métodos de negócio
	//
    public String getCaminhoRelativo(){
    	return BaseServiceImpl.getPathRelativo(file.getAbsolutePath());
    }

    public String getUltimaModificacao(){
    	return file == null && file.isFile() ? "" : new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(file.lastModified());
    }

    public Long getTamanho(){
    	return file == null || !file.isFile() ? null : file.length();
    }
    
    public String getTamanhoFormatado(){
    	if(file == null || !file.isFile())
    		return "";

    	NumberFormat formatter = new DecimalFormat("#,###,##0.00");
    	String tamanho = formatter.format(new Double(file.length())/1000);
    	return tamanho;
    }
    
    public String getAcessivelPara(){
    	if( this.getPermissao() == null )
    		return "";
    	String acessivelPara = this.getPermissao().getNome();
    	if (this.isCompartilhadoTodasPermissoes()){
    		if(this.getPermissao().isAdmin())
    			acessivelPara = "Administradores de todos os módulos";
    		else if (this.getPermissao().isAvancado())
    			acessivelPara = "Usuários Avançados de todos os módulos";
    		else if (this.getPermissao().isBasico())
    			acessivelPara = "Usuários Básicos de todos os módulos";
    	}
    	return acessivelPara;
    }

	public int compareTo(Object o) {
		if(o == null || !(o instanceof Arquivo) )
			return -1;
		return ((Arquivo)o).getFile().getName().compareTo(this.getFile().getName());
	}
    
    //
	// Métodos acessores default
	//
	public File getFile() {
		return file;
	}

	public Permissao getPermissao() {
		return permissao;
	}

	public boolean isCompartilhadoTodasPermissoes() {
		return compartilhadoTodasPermissoes;
	}
	
}
