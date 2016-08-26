package br.org.ceu.bumerangue.controller.dwr;

import java.util.List;

/**
 * - Classe utilit�ria que centraliza todos os m�todos expostos para o DWR.<br>
 * - Esta classe � mapeada no arquivo dwr.xml<br>
 * - As fun��es javaScript, que acessam estes m�todos encontram-se no arquivo: dwrUtils.js<br>
 * - Por raz�es de seguran�a, cada m�todo desta classe n�o cont�m a l�gica de implementa��o.
 *   Apenas realiza uma chamada a um m�todo (com o mesmo nome) da classe DWRUtilsSandBox.
 *   Assim, a classe DWRUtilsSandBox � q que deve cont�m as regras de neg�cio.
 * - Os m�todos desta classe est�o agrupados por coment�rios, os quais informam uma funcionalidade AJAX.
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
