<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>

<form action="manterVideoPesquisarController.action" method="post">
	<table class='tabelaForm' cellspacing='1'>
	   <tr class='linha'>
			<td onMouseOver="campoLinkOver(this)"; onMouseOut="campoLinkOut(this)"; style='cursor:hand' class='semLink' onclick="submete('relatorioVideo.action?method=pesquisarPre&tipo=historico')";>Hist�rico dos Empr�stimos</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
	   <tr class='linha'>
			<td onMouseOver="campoLinkOver(this)"; onMouseOut="campoLinkOut(this)"; style='cursor:hand' class='semLink' onclick="submete('relatorioVideo.action?method=pesquisarPre&tipo=estatistica')";>Estat�stica dos Empr�stimos</td>
		</tr>
	</table>
</form>