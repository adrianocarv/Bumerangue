package br.org.ceu.bumerangue.dao;

import java.util.Properties;

public interface ConfiguracaoDao {
 
	public Properties findConfiguracoes(String nomeModulo, String realPath);

	public void update(String nomeModulo, Properties configuracoes, String realPath);

}
 
