<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>

<form action="manterVideoPesquisarController.action" method="post">
	<table class='tabelaForm' cellspacing='1'>
	   <tr class='linha'>
			<td onMouseOver="campoLinkOver(this)"; onMouseOut="campoLinkOut(this)"; style='cursor:hand' class='semLink' onclick="submete('relatorioVideo.action?method=pesquisarPre&tipo=historico')";>Histórico dos Empréstimos</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
	   <tr class='linha'>
			<td onMouseOver="campoLinkOver(this)"; onMouseOut="campoLinkOut(this)"; style='cursor:hand' class='semLink' onclick="submete('relatorioVideo.action?method=pesquisarPre&tipo=estatistica')";>Estatística dos Empréstimos</td>
		</tr>
	</table>
</form>