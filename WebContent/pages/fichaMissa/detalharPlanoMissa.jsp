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
		<c:if test="${empty pathRelativoArquivoPlanoMissa}" >
			<tr><td >Arquivo do Plano de Missa ainda não disponível</td></tr>
		</c:if>
		<c:if test="${not empty pathRelativoArquivoPlanoMissa}" >
			<tr><td class="textoNegrito"><a href="${pathRelativoArquivoPlanoMissa}">Baixe aqui o Arquivo do Plano de Missa</a></td></tr>
		</c:if>
		<tr><td>&nbsp;</td></tr>
		<tr><td class="textoNegrito">Missas</td></tr>
	</table>
	<table class='tabelaFormFundo' cellspacing='1'>
	   <tr class='linhaFundoTitulo'>
			<td>Dia</td>
			<td>Liturgia da Palavra</td>
			<td>Oração</td>
		</tr>
		<c:forEach var="missa" items="${planoMissa.missas}">
		   <tr class='linhaFundo' onMouseOver="campoLinkOver(this)"; onMouseOut="campoLinkOut(this)"; style='cursor:hand' class='semLink' onclick="submete('manterPlanoMissa.action?method=detalharMissa&id=${missa.id}')"; >
				<td>${missa.dia}</td>
				<td>${missa.liturgiaPalavra.chaveDescricao}</td>
				<td>${missa.oracao.chaveDescricao}</td>
			</tr>
		</c:forEach>
	</table>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr class='linhaBotao'>
			<c:if test="${b:isUserInRole(S_C.usuarioLogado,'10')}">
				<td><input type="button" value="Editar" class="botao" onclick="submete('manterPlanoMissa.action?method=editarPre')"></td>
				<td><input type="button" value="Validar" class="botao" onclick="submete('manterPlanoMissa.action?method=validar')"></td>

				<c:if test="${planoMissa.aprovado}">
					<td><input type="button" value="Alterar para 'Em Aprovação'" class="botao" onclick="submete('manterPlanoMissa.action?method=alterarParaEmAprovacao')"></td>
				</c:if>

				<c:if test="${planoMissa.emAprovacao}">
					<td><input type="button" value="Aprovar" class="botao" onclick="submete('manterPlanoMissa.action?method=aprovar')"></td>
				</c:if>

				<td><input type="button" value="Excluir" class="botao" onclick="submete('manterPlanoMissa.action?method=excluir')"></td>
			</c:if>

			<td><input type="button" value="Voltar" class="botao" onclick="submete('manterPlanoMissa.action?method=pesquisarPre')"></td>
			<td><input type="hidden" name="id" value="${planoMissa.id}"></td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr class='linhaBotao'>
			<td><input type="button" value="Exportar Plano de Missa" class="botao" onclick="submete('exportarFichaMissa.action?method=exportarFichasMissa')"></td>
			<td>A4<input type="radio" name="formato" value="A4" checked="checked"> &nbsp;&nbsp; A6<input type="radio" name="formato" value="A6"></td>
			<td><img border="0" title="PDF" src="resources/img/icoPDF.jpg" /><input type="radio" name="tipoExportacao" value="1" title="PDF" checked="checked"> &nbsp;&nbsp; <img border="0" title="RTF" src="resources/img/icoRTF.jpg" /><input type="radio" name="tipoExportacao" value="4" title="RTF" ></td>
		</tr>
	</table>
</form>