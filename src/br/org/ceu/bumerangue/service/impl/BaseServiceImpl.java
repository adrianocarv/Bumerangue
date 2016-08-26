package br.org.ceu.bumerangue.service.impl;

import java.io.File;

import org.apache.commons.lang.StringUtils;

import br.org.ceu.bumerangue.entity.ElementoDominio;
import br.org.ceu.bumerangue.entity.Permissao;
import br.org.ceu.bumerangue.entity.suporte.deploy.Deploy;
import br.org.ceu.bumerangue.service.BaseService;
import br.org.ceu.bumerangue.util.Utils;

public abstract class BaseServiceImpl implements BaseService{

	private static String WEB_REAL_PATH = null;
	
	public static void setWebRealPath(String webRealPath){
		if(WEB_REAL_PATH == null)
			WEB_REAL_PATH = webRealPath;
	}
	
	public static String getPathRelativo(String pathAbsoluto){
		String path = StringUtils.replaceOnce(pathAbsoluto,WEB_REAL_PATH+File.separator,"");
		path = StringUtils.replace(path,"\\","/");
		return path;
	}

	protected String getWebRealPath(){
		return WEB_REAL_PATH;
	}

	protected String getPathDiretorioArquivos(Permissao permissao){
		String path = WEB_REAL_PATH +File.separator+"arquivos"+File.separator+permissao.getNomeModulo().toLowerCase()+File.separator;
		path += this.getPathDiretorioPermissaoArquivos(permissao.getTipoPermissao());
		
		return path;
	}

	protected String getPathDiretorioArquivosUsuarioAdmin(ElementoDominio tipoPermissao){
		String path = WEB_REAL_PATH +File.separator+"arquivos"+File.separator+Deploy.MODULO_BUMERANGUE.toLowerCase();

		if(tipoPermissao != null)
			path = path + File.separator + this.getPathDiretorioPermissaoArquivos(tipoPermissao);
		
		return path;
	}

	protected String getPathRelativoDiretorioArquivos(Permissao permissao){
		return getPathRelativo(this.getPathDiretorioArquivos(permissao));
	}

	protected String getPathRelativoDiretorioArquivosUsuarioAdmin(ElementoDominio tipoPermissao){
		return getPathRelativo(this.getPathDiretorioArquivosUsuarioAdmin(tipoPermissao));
	}

	private String getPathDiretorioPermissaoArquivos(ElementoDominio tipoPermissao){
		return tipoPermissao == null ? "" : Utils.getNormalizedString(tipoPermissao.getDescricao());
//		if(permissao == null)
//			return "";
//
//		String dir = "";
//		if(permissao.isAdmin()) dir += ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_ADMIN.getDescricao();
//		else if(permissao.isAvancado()) dir += ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_AVANCADO.getDescricao();
//		else if(permissao.isBasico()) dir += ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_BASICO.getDescricao();
//		dir = Utils.getNormalizedString(dir);
//		
//		return dir;
	}
}
