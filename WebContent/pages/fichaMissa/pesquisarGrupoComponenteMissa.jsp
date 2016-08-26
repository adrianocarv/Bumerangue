<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b"%>
<%@ taglib uri="http://displaytag.sf.net/el" prefix="display"%>

<script type="text/javascript">window.onload=function(){focusField("chave");}</script>

<form action="manterGrupoComponenteMissa.action?method=pesquisar" method="post">
	<table class='tabelaFormFundo' cellspacing='1'>
	   <tr class='linhaFundo'>
			<td width="30%">Chave</td>
			<td><input type="text" id="chave" name="chave" ></td>
		</tr>
	</table>
	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr class='linhaBotao'>
			<td><input type="submit" value="Pesquisar" class="botao"></td>
			<td><input type="button" value="Novo" class="botao" onclick="submete('manterGrupoComponenteMissa.action?method=editarPre')"></td>
			<td><input type="hidden" name="tipo" value="${tipo}"></td>
		</tr>
		<tr><td>&nbsp;</td></tr>
	</table>

	<c:if test="${grupoComponentesMissa != null}" >  
		<table class='tabelaFormFundo' cellspacing='1'>
		   <tr class='linhaFundoTituloSemNegrito'><td colspan="2">${b:size(grupoComponentesMissa)} registros encontrados.</td></tr>
		   <tr class='linhaFundoTitulo'>
				<td>Chave</td>
				<td>Descricao</td>
			</tr>
			<c:forEach var="grupoComponenteMissa" items="${grupoComponentesMissa}">
			   <tr class='linhaFundo' onMouseOver="campoLinkOver(this)"; onMouseOut="campoLinkOut(this)"; style='cursor:hand' class='semLink' onclick="submete('manterGrupoComponenteMissa.action?method=editarPre&id=${grupoComponenteMissa.id}')"; >
					<td>${grupoComponenteMissa.chave}</td>
					<td>${grupoComponenteMissa.descricao}</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>

</form>