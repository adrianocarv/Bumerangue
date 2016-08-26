	//Realiza a submi??o do formul?rio passando destino como par?metro 
	function submete(destino)
	{
		document.forms[0].action = destino;
		document.forms[0].submit();
	}
	
	//Realiza a submi??o do pop-up passando destino como par?metro 
	function submetePopUp(destino){
		window.opener.document.forms[0].action = destino;
		window.opener.document.forms[0].submit();
		window.close();
	}
	
	//Abre nova janela submetendo com os valores atuais da pagina
	function abreRelatorioSubmetendo (destino){
		novaJanela = window.open (destino,'popupPrint','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes, width=700, height=550')
		novaJanela.document.forms[0].action = destino;
		alert('novaJanela.document.forms[0].action = ' + destino);
		novaJanela.document.forms[0].submit();
	}

	//Redireciona como um link o destino, abrindo-o no formato de relat?rio
	function abreRelatorio(destino)
	{
		window.open(destino,'popupPrint','toolbar=no,location=no,directories=no,status=no,menubar=yes,scrollbars=yes,resizable=yes, width=700, height=550')
	}

	//Funciona da mesma maneira que a fun??o 'abreRelatorio', podendo passar mais 1 par?mento
	function abreRelatorio1(destino,parName, parValue)
	{
		abreRelatorio(destino+'&'+parName+'='+parValue)
	}

	//Funciona da mesma maneira que a fun??o 'abreRelatorio', podendo passar mais 2 par?mentos
	function abreRelatorio2(destino,parName, parValue, parName2, parValue2)
	{
		abreRelatorio(destino+'&'+parName+'='+parValue+'&'+parName2+'='+parValue2)
	}

	//Funciona da mesma maneira que a fun??o 'abreRelatorio', podendo passar mais 1 par?mento
	//do tipo bot?o de radio (document.forms[0].nomeDoBotao)
	function abreRelatorio1Radio(destino,parName, botaoRadio)
	{
		parValue = getValorSelecionadoBotaoRadio(botaoRadio);
		abreRelatorio(destino+'&'+parName+'='+parValue)
	}

	//Redireciona como um link o destino, abrindo-o no formato de popUp
	function abrePopUp(destino)
	{
		window.open(destino,'popupPrint','toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes, width=700, height=550')
	}

	//Realiza a submi??o do formul?rio,
	//por?m com uma janela intermedi?ria de confirma??o composta por uma mensagem passada como par?mento
	function confirmacao(mensagem)
	{
		if( confirm(mensagem) ) {
			document.forms[0].submit();
			return true ;
		} else {
			return false;
		}
	}	

	//Realiza a submi??o do formul?rio passando o destino como par?mento,
	//por?m com uma janela intermedi?ria de confirma??o composta por uma mensagem passada como par?mento
	function confirmacao1(destino,mensagem)
	{
		document.forms[0].action = destino;
		confirmacao(mensagem);
	}

	//Realiza a submi??o do formul?rio para o destino apontado pelo atributo action
	//por?m com uma janela intermedi?ria confirmando o cancelamento
	function confirmaCancelamento() {
	   confirmacao('Confirma o cancelamento?')
	}		

	//Realiza a submi??o do formul?rio passando destino como par?mento,
	//por?m com uma janela intermedi?ria confirmando o cancelamento
	function confirmaCancelamento1(destino) {
		confirmacao1(destino, 'Confirma o cancelamento?')
	}

	//Realiza a submi??o do formul?rio para o destino apontado pelo atributo action
	//por?m com uma janela intermedi?ria confirmando a exclus?o
	function confirmaExclusao() {
		confirmacao('Confirma a exclus?o?')
	}

	//Realiza a submi??o do formul?rio passando destino como par?mento,
	//por?m com uma janela intermedi?ria confirmando a exclus?o
	function confirmaExclusao1(destino) {
		confirmacao1(destino, 'Confirma a exclus?o?')
	}

	//Realiza a submi??o do formul?rio para o destino apontado pelo atributo action
	//por?m com uma janela intermedi?ria confirmando a inclus?o
	function confirmaInclusao() {
		return confirmacao('Confirma a inclus?o')
	}

	//Realiza a submi??o do formul?rio passando destino como par?mento,
	//por?m com uma janela intermedi?ria confirmando a inclus?o
	function confirmaInclusao1(destino) {
		return confirmacao1(destino, 'Confirma a inclus?o?')
	}

	//Realiza a submi??o do formul?rio para o destino apontado pelo atributo action
	//por?m com uma janela intermedi?ria confirmando a altera??o
	function confirmaAlteracao() {
		return confirmacao('Confirma a altera??o?')
	}

	//Realiza a submi??o do formul?rio passando destino como par?mento,
	//por?m com uma janela intermedi?ria confirmando a altera??o
	function confirmaAlteracao1(destino) {
		return confirmacao1(destino, 'Confirma a altera??o?')
	}

	//Realiza a submi??o do formul?rio para o destino apontado pelo atributo action
	//por?m com uma janela intermedi?ria confirmando a transfer?ncia
	function confirmaTransferencia() {
		confirmacao('Confirma a transfer?ncia?')
	}

	//Realiza a submi??o do formul?rio passando destino como par?mento,
	//por?m com uma janela intermedi?ria confirmando a transfer?ncia
	function confirmaTransferencia1(destino) {
		confirmacao1(destino,'Confirma a transfer?ncia?')
	}

	//Realiza a submi??o do formul?rio para o destino apontado pelo atributo action
	//por?m com uma janela intermedi?ria confirmando o desativamento
	function confirmaDesativamento() {
		confirmacao('Confirma o desativamento?')
	}

	//Realiza a submi??o do formul?rio passando destino como par?mento,
	//por?m com uma janela intermedi?ria confirmando o desativamento
	function confirmaDesativamento1(destino) {
		confirmacao1(destino,'Confirma o desativamento?')
	}

	//Realiza a submi??o do formul?rio para o destino apontado pelo atributo action
	//por?m com uma janela intermedi?ria confirmando o reativamento
	function confirmaReativamento() {
		confirmacao('Confirma o reativamento?')
	}

	//Realiza a submi??o do formul?rio passando destino como par?mento,
	//por?m com uma janela intermedi?ria confirmando o reativamento
	function confirmaReativamento1(destino) {
		confirmacao1(destino,'Confirma o reativamento?')
	}

	//Realiza a submi??o do formul?rio para o destino apontado pelo atributo action
	//por?m com uma janela intermedi?ria confirmando a associa??o
	function confirmaAssociacao() {
		confirmacao('Confirma a associa??o?')
	}

	//Realiza a submi??o do formul?rio passando destino como par?mento,
	//por?m com uma janela intermedi?ria confirmando a associa??o
	function confirmaAssociacao1(destino) {
		confirmacao1(destino,'Confirma a associa??o?')
	}
	
	//Realiza a submi??o do formul?rio para o destino apontado pelo atributo action
	//por?m com uma janela intermedi?ria confirmando a associa??o
	function confirmaTrocarSenha() {
		confirmacao('Confirma troca de senha?')
	}

	//Realiza a submi??o do formul?rio passando destino como par?mento,
	//por?m com uma janela intermedi?ria confirmando a associa??o
	function confirmaTrocarSenha1(destino) {
		confirmacao1(destino,'Confirma troca de senha?')
	}

	//Realiza a submi??o do formul?rio para o destino apontado pelo atributo action
	//por?m com uma janela intermedi?ria confirmando a desassocia??o
	function confirmaDesassociacao() {
		confirmacao('Confirma a desassocia??o?')
	}

	//Realiza a submi??o do formul?rio passando destino como par?mento,
	//por?m com uma janela intermedi?ria confirmando a desassocia??o
	function confirmaDesassociacao1(destino) {
		confirmacao1(destino,'Confirma a desassocia??o?')
	}

	//Realiza a submi??o do formul?rio para o destino apontado pelo atributo action
	//por?m com uma janela intermedi?ria confirmando a troca
	function confirmaTroca() {
		confirmacao('Confirma a troca?')
	}

	//Realiza a submi??o do formul?rio passando destino como par?mento,
	//por?m com uma janela intermedi?ria confirmando a troca
	function confirmaTroca1(destino) {
		confirmacao1(destino,'Confirma a troca?')
	}
	
	//Realiza a submi??o do formul?rio para o destino apontado pelo atributo action
	//por?m com uma janela intermedi?ria confirmando a restaura??o
	function confirmaRestauracao() {
		confirmacao('Confirma a restaura??o?')
	}

	//Realiza a submi??o do formul?rio passando destino como par?mento,
	//por?m com uma janela intermedi?ria confirmando a restaura??o
	function confirmaRestauracao1(destino) {
		confirmacao1(destino,'Confirma a restaura??o?')
	}	
	
	//Realiza a submi??o do formul?rio para o destino apontado pelo atributo action
	//por?m com uma janela intermedi?ria confirmando o bloqueio
	function confirmaBloqueio() {
		confirmacao('Confirma o bloqueio?')
	}

	//Realiza a submi??o do formul?rio passando destino como par?mento,
	//por?m com uma janela intermedi?ria confirmando o bloqueio
	function confirmaBloqueio1(destino) {
		confirmacao1(destino,'Confirma o bloqueio?')
	}	
	
	//Realiza a submi??o do formul?rio para o destino apontado pelo atributo action
	//por?m com uma janela intermedi?ria confirmando o desbloqueio
	function confirmaDesbloqueio() {
		confirmacao('Confirma o desbloqueio?')
	}

	//Realiza a submi??o do formul?rio passando destino como par?mento,
	//por?m com uma janela intermedi?ria confirmando o desbloqueio
	function confirmaDesbloqueio1(destino) {
		confirmacao1(destino,'Confirma o desbloqueio?')
	}	

	//retorna o valor do bot?o de radio (document.forms[0].nomeDoBotao) passado como par?mento.
	//retorna uma string vazia, caso o radio nao tenha sido selecionado
	function getValorSelecionadoBotaoRadio( botaoRadio ) {
		for( i = 0; i < botaoRadio.length; i++ ) {
			if( botaoRadio[i].checked ) {
				return botaoRadio[i].value ;
			}
		}
		return '';
	}
	
	//conjunto de fun??es usadas para escrever um texto num local determinado da p?gina
	function MM_findObj(n, d) { //v4.01
	  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
	    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
	  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
	  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
	  if(!x && d.getElementById) x=d.getElementById(n); return x;
	}
	function MM_setTextOfLayer(objName,x,newText) { //v4.01
	  if ((obj=MM_findObj(objName))!=null) with (obj)
	    if (document.layers) {document.write(unescape(newText)); document.close();}
	    else innerHTML = unescape(newText);
	}
	/////

	//fun??o usada nas c?lulas de link das tabelas com preenchimento de fundo
	function campoLinkOver(form)
	{
		form.style.backgroundColor="#FFFFFF";
	}

	//fun??o usada nas c?lulas de link das tabelas com preenchimento de fundo
	function campoLinkOut(form)
	{
		form.style.backgroundColor="";
	}
	
	//Essa fun??o deve ser usada no atributo onclick dos elementos do tipo checkbox.
	//Serve para varrer uma lista de checkbox em hierarquia e marcar/desmarcar os filhos correspondentes ao checkbox selecionado.
	//Essa intera??o realiza compara??es no atributo value do checkbox, o qual deve ter o valor seguindo o padr?o: codigoGrupo_valorChaveDoObjeto_outros valores
	//O par?metro objetoCheck corresponde ao objeto checkbox clicado.	
	//O par?metro valorCheck deve ser composto do seguinte padr?o: codigoGrupo_valorChaveDoObjeto
	//codigoGrupo = corresponde ao valor do n?vel na hierarquia.
	//valorChaveDoObjeto = corresponde ao valor qu indentifica o checkbox na listagem.
	function selecionarGrupo(objetoCheck,valorCheck) {
		nomeOpcao = objetoCheck.name;
		isChecado = objetoCheck.checked;
		codigoGrupo = valorCheck.substring(0,valorCheck.indexOf('_'));
		encontrouChecado = false;
			for(i = 0; i < document.forms[0][nomeOpcao].length; i++) {
				objetoAtual = document.forms[0][nomeOpcao][i];
				valorAtual = objetoAtual.value;
				grupoAtual = valorAtual.substring(0,valorAtual.indexOf('_'));
				if(!encontrouChecado && valorAtual == objetoCheck.value)
					encontrouChecado = true;
				if(encontrouChecado && valorAtual != objetoCheck.value){
				  	if(codigoGrupo >= grupoAtual)break;
			   		objetoAtual.checked = isChecado;
				} 
			 }
	}

	// PAR?METRO NOME: ? passado o nome ou property do conjunto de checkbox a serem alterados.
	// 		a op??o NOME existe por poder haver dois ou mais conjuntos de checkbox na tela.
	//nome.substring(0,3) pega os primeiros 3 caracteres passado como nome e verifica se ? igual aos tres caracteres dentro do for.
	//	foi usado o substring por em alguns casos o checkbox vir como: chk + codigoObjeto, nesse caso verificamos apenas os 3 primeiros caracteres.
	// SE O PAR?METRO OPCAO for igual a true: o script seleciona todos os checkbox.
    // SE O PAR?METRO OPCAO for igual a false: o script limpa todos os checkbox.
	function selecionarTodosChecbox(nome,opcao){
		marcarDesmarcar = opcao;
		for(i = 0; i < document.forms[0].elements.length; i++){
			if(document.forms[0].elements[i].type == 'checkbox' && document.forms[0].elements[i].name.substring(0,3) == nome.substring(0,3))
				document.forms[0].elements[i].checked = marcarDesmarcar;
		}
	}
	
	//fun??o usada para colocar o foco num determinado campo do formul?rio.
	function focusField(field) {
		document.getElementById(field).focus();
	}
	
	//fun??o usada para exibir a div passada como par?metro.
	function exibeDiv(nomeDiv, exibe) {
		if(exibe)
			document.getElementById(nomeDiv).style.display = "block";
		else
			document.getElementById(nomeDiv).style.display = "none";
	}
	