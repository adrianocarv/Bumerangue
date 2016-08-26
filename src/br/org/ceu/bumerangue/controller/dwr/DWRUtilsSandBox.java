package br.org.ceu.bumerangue.controller.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.directwebremoting.WebContextFactory;
import org.springframework.util.ReflectionUtils;

import br.org.ceu.bumerangue.util.Utils;

/**
 * - Classe utilit�ria que centraliza a implemeta��o de todos os m�todos contido na classe DWRUtils.java.<br>
 * - Os m�todos desta classe est�o agrupados por coment�rios, os quais informam uma funcionalidade AJAX.
 * @author Adriano Carvalho
 */
public class DWRUtilsSandBox {

	//singleton
	private static DWRUtilsSandBox sandBox = null;
	private DWRUtilsSandBox(){}
	public static DWRUtilsSandBox getInstance(){
		if(sandBox == null) sandBox = new DWRUtilsSandBox();
		return sandBox;
	}
	
	/**---------------------------------------------------------------------------------
	Funcionalidade - Autocompletar
	-----------------------------------------------------------------------------------*/

	/**
	 * Retorna a lista de VOAutoCompletar, os quais v�m dos elementos da lista 'nomeLista', em que<br>
	 * seu elementos possuem o valor do m�todo get'atributoValor' come�ado com 'valorFiltro'.<br>
	 * Cada elemento da lista de retorno � um VOAutoCompletar,<br>
	 * no qual ter� o atributo valor = get'atributoValor' do elemento da lista 'nomeLista' e <br>
	 * o atributo descricao = get'atributoDescricao' do elemento da lista 'nomeLista'.
	 * @author Adriano Carvalho
	 * @param valorFiltro
	 * @param nomeLista A composi��o do nome parte do nome de um atributo da session, <br>
	 *                  permitindo navega��o entresos objetos, separando-os com .(ponto), <br>
	 *                  at� chegar no objeto correpondente a lista. 
	 * @param atributoValor
	 * @param atributoDescricao
	 * @return
	 */
	protected List<VOAutoCompletar> autoCompletarBuscar(String valorFiltro, String nomeLista, String atributoValor, String atributoDescricao){
		//recupera a lista pelo nome, a partir do escopo.
		List<VOAutoCompletar> lista = new ArrayList<VOAutoCompletar>();
		List listaCompleta = getLista(nomeLista);
		
		for (Object obj : listaCompleta) {
			Object valor = Utils.getAtributo(obj, atributoValor);
			if (valor != null && valor.toString().toLowerCase().startsWith(valorFiltro.toLowerCase())) {
				Object descricao = Utils.getAtributo(obj, atributoDescricao);
				lista.add(new VOAutoCompletar(valor+"", descricao+""));
			}
		}
		
		return lista;
	}

	/**
	 * Retona true, caso o 'valorTextField' seja = get'atributoValor' de algum elemento da lista 'nomeLista'.<br>
	 * 'nomeLista' � um atributo na sesion.
	 * @author Adriano Carvalho
	 * @param valorTextField
	 * @param nomeLista A composi��o do nome parte do nome de um atributo da session, <br>
	 *                  permitindo navega��o entresos objetos, separando-os com .(ponto), <br>
	 *                  at� chegar no objeto correpondente a lista. 
	 * @param atributoValor
	 * @return
	 */
	protected boolean autoCompletarIsSelecaoOK(String valorTextField, String nomeLista, String atributoValor){
		if(valorTextField == null || valorTextField.trim().equals("")) return false;
			
		//recupera a lista pelo nome, a partir do escopo.
		List listaCompleta = getLista(nomeLista);
		
		for (Object obj : listaCompleta) {
			Object valor = Utils.getAtributo(obj, atributoValor);
			if (valor != null && valorTextField.equals(valor.toString())) return true;
		}
		return false;
	}

	/**
	 * Retorna a lista de elementos da lista 'nomeLista'.<br>
	 * Usa-se reflex�o para navegar entre os objetos separados por .(ponto). 
	 * @author Adriano Carvalho
	 * @param nomeLista A composi��o do nome parte do nome de um atributo da session, <br>
	 *                  permitindo navega��o entresos objetos, separando-os com .(ponto), <br>
	 *                  at� chegar no objeto correpondente a lista. 
	 * @return
	 */
	private List getLista(String nomeLista){
		List lista = new ArrayList();
		if(nomeLista == null) return lista;

		String[] atributos = StringUtils.replace(nomeLista,".",";").split(";");
		String nomePrimeiroAtributo = atributos[0];

		Object primeiroAtributo = WebContextFactory.get().getHttpServletRequest().getSession().getAttribute(nomePrimeiroAtributo);

		if(primeiroAtributo == null) return lista;
		
		if(atributos.length == 1)
			lista = (List) primeiroAtributo;
		else{
			String nomeProximosAtributos = nomeLista.substring(nomeLista.indexOf(".")+1);
			lista = new ArrayList((Collection)Utils.getAtributo(primeiroAtributo, nomeProximosAtributos));
		}
		return lista;
	}

}
