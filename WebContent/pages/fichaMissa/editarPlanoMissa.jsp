<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b"%>

<form action="manterPlanoMissa.action?method=detalhar" method="post">
	<table class='tabelaFormFundo' cellspacing='1'>
		<tr class='linhaFundo'>
			<td width="30%">Ano/Mês</td>
			<td>${planoMissa.anoMesFormatado}</td>
		</tr>
		<tr class='linhaFundo'>
			<td>Situação</td>
			<td>${planoMissa.situacao.descricao}</td>
		</tr>
	</table>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr><td class="textoNegrito">Missas</td></tr>
	</table>
	<table class='tabelaFormFundo' cellspacing='1'>
	   <tr class='linhaFundoTitulo'>
			<td>Dia</td>
			<td colspan="2">Liturgia da Palavra</td>
			<td colspan="2">Oração</td>
		</tr>
		<c:forEach var="missa" items="${planoMissa.missas}">
		   <tr class='linhaFundo'>
				<td onMouseOver="campoLinkOver(this)"; onMouseOut="campoLinkOut(this)"; style='cursor:hand' class='semLink' onclick="submete('manterPlanoMissa.action?method=detalharMissa&id=${missa.id}')";>${missa.dia}</td>
				<td>
					<input type="text" id="${missa.id}_chaveLiturgiaPalavra" name="${missa.id}_chaveLiturgiaPalavra" value="${missa.liturgiaPalavra.chave}" onkeyup="buscar(this,'S_C.listaAutoCompletar1','chave','descricao','resultado_liturgia_palavra_${missa.id}')">
					<div id="resultado_liturgia_palavra_${missa.id}">
				</td>
				<td><div id="selecao_resultado_liturgia_palavra_${missa.id}" />${missa.liturgiaPalavra.descricao}</td>
				<td>
					<input type="text" id="${missa.id}_chaveOracao" name="${missa.id}_chaveOracao" value="${missa.oracao.chave}" onkeyup="buscar(this,'S_C.listaAutoCompletar2','chave','descricao','resultado_oracao_${missa.id}')">
					<div id="resultado_oracao_${missa.id}">
				</td>
				<td><div id="selecao_resultado_oracao_${missa.id}" />${missa.oracao.descricao}</td>
			</tr>
		</c:forEach>
	</table>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr class='linhaBotao'>
			<td><input type="button" value="Salvar" class="botao" onclick="submete('manterPlanoMissa.action?method=editar')"></td>
			<td><input type="button" value="Voltar" class="botao" onclick="submete('manterPlanoMissa.action?method=detalhar')"></td>
			<td><input type="hidden" name="id" value="${planoMissa.id}"></td>
		</tr>
	</table>
</form>