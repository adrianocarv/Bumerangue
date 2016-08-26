package br.org.ceu.bumerangue.dao.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import br.org.ceu.bumerangue.dao.ConfiguracaoDao;
import br.org.ceu.bumerangue.dao.hibernate.BaseDaoHibernate;
import br.org.ceu.bumerangue.entity.suporte.deploy.Deploy;
import br.org.ceu.bumerangue.service.exceptions.BumerangueErrorRuntimeException;
import br.org.ceu.bumerangue.util.Utils;

public class ConfiguracaoDaoProperties extends BaseDaoHibernate implements ConfiguracaoDao {

	public Properties findConfiguracoes(String nomeModulo, String realPath){
		Properties configuracoes = new Properties();
        try {
        	String caminhoArquivoConfiguracao = this.getCaminhoArquivoConfiguracao(nomeModulo, realPath);
        	configuracoes.load(new FileInputStream(caminhoArquivoConfiguracao));
	    } catch (Exception e) {
	            new BumerangueErrorRuntimeException(e.getMessage());
	    }
		return configuracoes;
	}

	public void update(String nomeModulo, Properties configuracoes, String realPath){
        FileOutputStream fileOutputStream;
        try {
        	String caminhoArquivoConfiguracao = this.getCaminhoArquivoConfiguracao(nomeModulo, realPath);
            fileOutputStream = new FileOutputStream(caminhoArquivoConfiguracao);
            configuracoes.store(fileOutputStream,"");
			fileOutputStream.close();
        } catch (Exception e) {
                new BumerangueErrorRuntimeException(e.getMessage());
        }		
	}

	private String getCaminhoArquivoConfiguracao(String nomeModulo, String realPath){
		String prefixo = realPath+"/WEB-INF/config/resources/";
		prefixo = StringUtils.replace(prefixo,"\\",File.separator);
		prefixo = StringUtils.replace(prefixo,"/",File.separator);
		String sufixo = "-config.properties";

		if(Deploy.MODULO_BUMERANGUE.equals(nomeModulo))
			sufixo = "bumerangue" + sufixo;
		else
			sufixo = "plugin-" + Utils.getNomeModuloToJavaConvention(nomeModulo) + sufixo;
		
		String caminhoArquivoConfiguracao = prefixo+sufixo;

		return caminhoArquivoConfiguracao;
	}
}
 
