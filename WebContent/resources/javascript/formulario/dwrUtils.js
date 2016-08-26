/**
	Javascript com as fun??es necess?rias para a??es usando AJAX com DWR.
	As fun??es de cada funcionalidade dispon?vel est?o agrupadas por coment?rios.
	Os m?todos Java expostos para o DWR encontram-se na classe: DWRUtils.java
*/

	//Representa??o das classes usadas, as quais s?o mapeadas no arquivo WEB-INF\dwr.xml
	document.write("<script type='text/javascript' src='/bumerangue/dwr/interface/DWRUtils.js'></script>");

	//Importa??o dos arquivos javascripts (*.js) do dwr
	document.write("<script type='text/javascript' src='/bumerangue/dwr/engine.js'></script>");
	document.write("<script type='text/javascript' src='/bumerangue/dwr/util.js'></script>");


	/**---------------------------------------------------------------------------------
		Funcionalidade - Autocompletar
	-----------------------------------------------------------------------------------*/

	/**
	Fun??o usada para autocompletar um textField, a partir do valor digitado. Chamada no atributo onkeyup.
	Realiza a pesquisa quando o valor do campoHtml tiver mais de 2 caracteres.
	campoHtml -> Elemento html correpondente ao textField. Ex: this
	nomeLista -> Nome do objeto do tipo List, contido num atributo do escopo session. Exemplos de valores:
				 atributoNaSession.nomeObjeto.nomeObjetoList = List result = atributoNaSession.getNomeObjeto().nomeObjetoList();
	atributoValor -> Nome do m?todo get (sem o get) do Objeto contido na lista.
	                 O retorno (convertido em String) do m?todo get<atributoValor> ser? usado como crit?rio de compara??o para filtrar a lista
	                 e setado no valor do textField.
	atributoDescricao -> Nome do m?todo get (sem o get) do Objeto contido na lista.
	                     O retorno (convertido em String) do m?todo get<atributoDescricao> ser? usado na descri??o do resultado da pesquisa
	nomeDivResultado -> O nome da div, na qual ser? reinderizado o resultado do filtro.

	IMPORTANTE: Caso haja uma div chamada: 'selecao_'<nomeDivResultado>, receber? a descri??o do elemento da lista selecionado.
	*/
	function buscar(campoHtml, nomeLista, atributoValor, atributoDescricao, nomeDivResultado) {
		//alert('buscar: '+campoHtml+', '+nomeLista+', '+atributoValor+', '+atributoDescricao+', '+nomeDivResultado);
		if(campoHtml.value.length < 1 ){
			resultadoAutoCompletar(nomeDivResultado,"","none");
			return;
		}
		useLoadingMessage();
				
		DWRUtils.autoCompletarBuscar(campoHtml.value, nomeLista, atributoValor, atributoDescricao, function(data){
			var elementos = "";
			for (var i = 0; i < data.length; i++) {
				elementos += "<a href='javascript:seleciona(\"" + data[i].valor + "\",\"" + data[i].descricao + "\",\"" + campoHtml.name + "\",\"" + nomeDivResultado + "\")'>" + data[i].descricao + "</a><br>";
			}
			if(elementos != ""){
				resultadoAutoCompletar(nomeDivResultado,elementos,"block");
			}else{
				resultadoAutoCompletar(nomeDivResultado,elementos,"none");
			}
		});
	}
	
	/**
	Fun??o usada para validar o textField usado com recurso de autocompletar. Chamada no atributo onblur.
	Partindo do campo textField e da lista, o campo recebe o valor vazio, caso seu valor n?o seja um elemento da lista.
	campoHtml -> Elemento html correpondente ao textField. Ex: this
	nomeLista -> Nome do objeto do tipo List, contido num atributo do escopo session. Exemplos de valores:
				 atributoNaSession.nomeObjeto.nomeObjetoList = List result = atributoNaSession.getNomeObjeto().nomeObjetoList();
	atributoValor -> Nome do m?todo get (sem o get) do Objeto contido na lista.
	                 O retorno (convertido em String) do m?todo get<atributoValor> ser? usado como
	                 crit?rio de compara??o para verificar a presen?a do valor do textField na lista.
	*/
	function validaSelecao(campoHtml, nomeLista, atributoValor) {
		//alert('validaSelecao: '+campoHtml+', '+nomeLista+', '+atributoValor);
		isOK = false;
		if(campoHtml.value.length >= 3){
			DWRUtils.autoCompletarIsSelecaoOK(campoHtml.value, nomeLista, atributoValor, function(isSelecaoOK){
				if(isSelecaoOK){
					isOK = true
				}
			});
		}
		if(!isOK){
			campoHtml.value = "";
		}
		return isOK;
	}

	/**
	Fun??o usada internamente para montar o link de cada elemento da lista.
	*/
	function seleciona(valor, descricao, nomeCampoHtml, nomeDivResultado) {
		//alert('seleciona: '+valor+', '+descricao+', '+nomeCampoHtml+', '+nomeDivResultado);
		$(nomeCampoHtml).value = valor;
		resultadoAutoCompletar(nomeDivResultado,"","none");
		//exibe descricao do elemenento selecionado
		if($("selecao_"+nomeDivResultado) != null)
			$("selecao_"+nomeDivResultado).innerHTML = descricao;
	}
	
	/**
	Fun??o usada internamente para montar o link de cada elemento da lista.
	*/
	function resultadoAutoCompletar(nomeDivResultado,elementos,display){
		//alert('resultadoAutoCompletar: '+nomeDivResultado+', '+elementos+', '+display);
		var resultadoAutoCompletar = "";
		resultadoAutoCompletar += "<div id=\"resultadoAutoCompletar\" style=\"position: absolute; left: 300; width: 400; height: 300; overflow:auto; display:"+display+"\">"
		resultadoAutoCompletar += "	<table bgcolor=\"#DDDDDD\" border=\"0\" style=\"border:1px solid #000000\">"
		resultadoAutoCompletar += "		<td>"
		resultadoAutoCompletar += "			<font color=\"blue\" class=\"txtazule11b\">"
		resultadoAutoCompletar += "				"+elementos
		resultadoAutoCompletar += "			</font>"
		resultadoAutoCompletar += "		</td>"
		resultadoAutoCompletar += "	</table>"
		resultadoAutoCompletar += "</div>"
		$(nomeDivResultado).innerHTML = resultadoAutoCompletar;
		//limpa descricao do elemenento selecionado
		if($("selecao_"+nomeDivResultado) != null)
			$("selecao_"+nomeDivResultado).innerHTML = "";
	}
	
	/**---------------------------------------------------------------------------------
	Fun??es utilit?rias usadas internamente pelas outras fun??es.
	-----------------------------------------------------------------------------------*/

	/**
	Fun??o usada para mostrar o estado de aguarde, enquanto o browser tiver em estado de espera.
	*/
	function useLoadingMessage(message) {
	  //alert('useLoadingMessage: '+message);
	  var loadingMessage;
	  if (message) loadingMessage = message;
	  else loadingMessage = "Aguarde Carregando...";
	
	  dwr.engine.setPreHook(function() {
	    var disabledZone = $('disabledZone');
	    if (!disabledZone) {
	      disabledZone = document.createElement('div');
	      disabledZone.setAttribute('id', 'disabledZone');
	      disabledZone.style.position = "absolute";
	      disabledZone.style.zIndex = "1000";
	      disabledZone.style.left = "0px";
	      disabledZone.style.top = "0px";
	      disabledZone.style.width = "100%";
	      disabledZone.style.height = "100%";
	      document.body.appendChild(disabledZone);
	      var messageZone = document.createElement('div');
	      messageZone.setAttribute('id', 'messageZone');
	      messageZone.style.position = "absolute";
	      messageZone.style.top = "0px";
	      messageZone.style.right = "0px";
	      messageZone.style.background = "red";
	      messageZone.style.color = "white";
	      messageZone.style.fontFamily = "Arial,Helvetica,sans-serif";
	      messageZone.style.padding = "4px";
	      disabledZone.appendChild(messageZone);
	      var text = document.createTextNode(loadingMessage);
	      messageZone.appendChild(text);
	    }
	    else {
	      $('messageZone').innerHTML = loadingMessage;
	      disabledZone.style.visibility = 'visible';
	    }
	  });
	
	  dwr.engine.setPostHook(function() {
	    $('disabledZone').style.visibility = 'hidden';
	  });
	}
	
	