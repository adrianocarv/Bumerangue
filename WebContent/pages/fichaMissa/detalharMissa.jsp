<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b"%>

<form action="mantermissa.action?method=detalhar" method="post">

	<table class='tabelaForm'>
		<tr><td class="textoNegrito">Missa</td></tr>
	</table>
	<table class='tabelaFormFundo' cellspacing='1'>
		<tr class='linhaFundo'>
			<td>Dia: ${missa.diaFormatado}</td>
		</tr>
	</table>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr><td class="textoNegrito">Liturgia da Palavra</td></tr>
	</table>
	<table class='tabelaFormFundo' cellspacing='1'>
		<tr class='linhaFundo' onMouseOver="campoLinkOver(this)"; onMouseOut="campoLinkOut(this)"; style='cursor:hand' class='semLink' onclick="window.open('manterGrupoComponenteMissa.action?method=editarPre&id=${missa.liturgiaPalavra.id}&tipo=1')"; >
			<td>${missa.liturgiaPalavra.chaveDescricao}</td>
		</tr>
	</table>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
	</table>
	<table class='tabelaFormFundo' cellspacing='1'>
		<tr class='linhaFundoTitulo'>
			<td>1� Leitura - P�ginas nos missais</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>${missa.liturgiaPalavra.leitura1.paginasMissais}</td>
		</tr>
	</table>
	
	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
	</table>
	<table class='tabelaFormFundo' cellspacing='1'>
		<tr class='linhaFundoTitulo'>
			<td>Salmo Responsorial - P�ginas nos missais</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>${missa.liturgiaPalavra.salmoResponsorial.paginasMissais}</td>
		</tr>
	</table>
	
	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
	</table>
	<table class='tabelaFormFundo' cellspacing='1'>
		<tr class='linhaFundoTitulo'>
			<td>2� Leitura - P�ginas nos missais</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>${missa.liturgiaPalavra.leitura2.paginasMissais}</td>
		</tr>
	</table>
	
	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
	</table>
	<table class='tabelaFormFundo' cellspacing='1'>
		<tr class='linhaFundoTitulo'>
			<td>Aclama��o ao Evangelho - P�ginas nos missais</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>${missa.liturgiaPalavra.aclamacaoEvangelho.paginasMissais}</td>
		</tr>
	</table>
	
	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
	</table>
	<table class='tabelaFormFundo' cellspacing='1'>
		<tr class='linhaFundoTitulo'>
			<td>Evangelho - P�ginas nos missais</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>${missa.liturgiaPalavra.evangelho.paginasMissais}</td>
		</tr>
	</table>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr><td class="textoNegrito">Ora��o</td></tr>
	</table>
	<table class='tabelaFormFundo' cellspacing='1'>
		<tr class='linhaFundo' onMouseOver="campoLinkOver(this)"; onMouseOut="campoLinkOut(this)"; style='cursor:hand' class='semLink' onclick="window.open('manterGrupoComponenteMissa.action?method=editarPre&id=${missa.oracao.id}&tipo=2')"; >
			<td>${missa.oracao.chaveDescricao}</td>
		</tr>
	</table>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
	</table>
	<table class='tabelaFormFundo' cellspacing='1'>
		<tr class='linhaFundoTitulo'>
			<td>Intr�ito - P�ginas nos missais</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>${missa.oracao.introitoRelativo.paginasMissais}</td>
		</tr>
	</table>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
	</table>
	<table class='tabelaFormFundo' cellspacing='1'>
		<tr class='linhaFundoTitulo'>
			<td>Pref�cio: ${grupoComponenteMissa.prefacioRelativo.descricao} - P�ginas nos missais</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>${missa.oracao.prefacioRelativo.paginasMissais}</td>
		</tr>
	</table>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
	</table>
	<table class='tabelaFormFundo' cellspacing='1'>
		<tr class='linhaFundoTitulo'>
			<td>Ant�fona de Comunh�o - P�ginas nos missais</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>${missa.oracao.antifonaComunhaoRelativo.paginasMissais}</td>
		</tr>
	</table>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr><td class="textoNegrito">Ficha de Missa</td></tr>
	</table>
	<table class='tabelaFormFundo' cellspacing='1'>
	   <tr class='linhaFundo'>
			<td width="30%">Titulo da Liturgia da Palavra</td>
			<td>${missa.tituloLiturgiaPalavra}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Titulo da Ora��o</td>
			<td>${missa.tituloOracao}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>P�gina da Missa</td>
			<td>${missa.paginaMissa}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Pref�cio</td>
			<td>${missa.prefacio}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>P�gina da 1� Leitura</td>
			<td>${missa.paginaLeitura1}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>P�gina do Salmo Responsorial</td>
			<td>${missa.paginaSalmoResponsorial}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>P�gina da 2� Leitura</td>
			<td>${missa.paginaLeitura2}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>P�gina do Evangelho</td>
			<td>${missa.paginaEvangelho}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Intr�ito</td>
			<td>${missa.introito}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Salmo Responsorial</td>
			<td>${missa.salmoResponsorial}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Aclama��o ao Evangelho</td>
			<td>${missa.aclamacaoEvangelho}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Ant�fona de Comunh�o</td>
			<td>${missa.antifonaComunhao}</td>
		</tr>
	</table>


	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr class='linhaBotao'>
			<td colspan="3"><input type="button" value="Voltar" class="botao" onclick="submete('manterPlanoMissa.action?method=detalhar')"></td>
			<td><input type="hidden" name="id" value="${missa.planoMissa.id}"></td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr class='linhaBotao'>
			<td><input type="button" value="Exportar Plano de Missa" class="botao" onclick="submete('exportarFichaMissa.action?method=exportarFichasMissa&idMissa=${missa.id}')"></td>
			<td>A4<input type="radio" name="formato" value="A4" checked="checked"> &nbsp;&nbsp; A6<input type="radio" name="formato" value="A6"></td>
			<td><img border="0" title="PDF" src="resources/img/icoPDF.jpg" /><input type="radio" name="tipoExportacao" value="1" title="PDF" checked="checked"> &nbsp;&nbsp; <img border="0" title="RTF" src="resources/img/icoRTF.jpg" /><input type="radio" name="tipoExportacao" value="4" title="RTF" ></td>
		</tr>
	</table>

</form>
