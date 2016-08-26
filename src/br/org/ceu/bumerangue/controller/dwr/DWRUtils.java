package br.org.ceu.bumerangue.controller.dwr;

import java.util.List;

/**
 * - Classe utilitária que centraliza todos os métodos expostos para o DWR.<br>
 * - Esta classe é mapeada no arquivo dwr.xml<br>
 * - As funções javaScript, que acessam estes métodos encontram-se no arquivo: dwrUtils.js<br>
 * - Por razões de segurança, cada método desta classe não contém a lógica de implementação.
 *   Apenas realiza uma chamada a um método (com o mesmo nome) da classe DWRUtilsSandBox.
 *   Assim, a classe DWRUtilsSandBox é q que deve contém as regras de negócio.
 * - Os métodos desta classe estão agrupados por comentários, os quais informam uma funcionalidade AJAX.
 * @author Adriano Carvalho
 */
public class DWRUtils {

	/**---------------------------------------------------------------------------------
	Funcionalidade - Autocompletar
	-----------------------------------------------------------------------------------*/

	/* (non-Javadoc)
	 * @see com.vf.bvf.contabil.util.dwr.DWRUtilsSandBox#autoCompletarBuscar(String valorFiltro, String nomeLista, String atributoValor, String atributoDescricao)
	 */
	public List<VOAutoCompletar> autoCompletarBuscar(String valorFiltro, String nomeLista, String atributoValor, String atributoDescricao){
		return DWRUtilsSandBox.getInstance().autoCompletarBuscar(valorFiltro, nomeLista, atributoValor, atributoDescricao);
	}

	/* (non-Javadoc)
	 * @see com.vf.bvf.contabil.util.dwr.DWRUtilsSandBox#autoCompletarIsSelecaoOK(String valorTextField, String nomeLista, String atributoValor)
	 */
	public boolean autoCompletarIsSelecaoOK(String valorTextField, String nomeLista, String atributoValor){
		return DWRUtilsSandBox.getInstance().autoCompletarIsSelecaoOK(valorTextField, nomeLista, atributoValor);
	}

}
