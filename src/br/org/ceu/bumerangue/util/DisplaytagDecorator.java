package br.org.ceu.bumerangue.util;


import org.displaytag.decorator.TableDecorator;

import br.org.ceu.bumerangue.entity.FilmeComercial;
import br.org.ceu.bumerangue.entity.suporte.Arquivo;
import br.org.ceu.bumerangue.entity.suporte.ElementoVerificacaoCodigo;

public class DisplaytagDecorator extends TableDecorator {

	public String getLinkSinopseDecorator(){
		FilmeComercial filmeComercial = (FilmeComercial)getCurrentRowObject();
		if(!ValidationRules.isInformed(filmeComercial.getLinkSinopse()))
			return "";
		String linkSinopse = "<p onMouseOver=\"campoLinkOver(this)\"; onMouseOut=\"campoLinkOut(this)\"; style='cursor:hand' class='semLink' onclick=\"window.open('"+filmeComercial.getLinkSinopse()+"','teste')\"; >sinopse</td>";
        return linkSinopse;
	}

	public String getCaminhoRelativoDecorator()	{
		Arquivo arquivo = (Arquivo)getCurrentRowObject();
		String path = "<a href=\""+arquivo.getCaminhoRelativo()+"\">"+arquivo.getFile().getName()+"</a>";
		return path;
	}
	
	public String getExcluirArquivoDecorator()	{
		Arquivo arquivo = (Arquivo)getCurrentRowObject();
		String numeroPermissao = arquivo.getPermissao() == null ? "" : "&numeroPermissao="+arquivo.getPermissao().getNumero();
		String nomeArquivo = "&fileName="+arquivo.getFile().getName();
		String compartilhadoTodasPermissoes = "&compartilhadoTodasPermissoes="+arquivo.isCompartilhadoTodasPermissoes();
		
		String path = "<p onMouseOver=\"campoLinkOver(this)\"; onMouseOut=\"campoLinkOut(this)\"; style='cursor:hand' class='semLink' onclick=\"confirmaExclusao1('manterArquivo.action?method=delete"+numeroPermissao+nomeArquivo+compartilhadoTodasPermissoes+"')\"; >excluir</td>";
		return path;
	}

}