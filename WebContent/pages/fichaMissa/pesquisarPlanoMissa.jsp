<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b"%>
<%@ taglib uri="http://displaytag.sf.net/el" prefix="display"%>

<script type="text/javascript">window.onload=function(){focusField("anoMes");}</script>

<form action="manterPlanoMissa.action?method=pesquisar" method="post">
	<table class='tabelaFormFundo' cellspacing='1'>
		<tr class='linhaFundo'>
			<td width="30%">Ano/Mês</td>
			<td><input type="text" id="anoMes" name="anoMes" ></td>
		</tr>
		<tr class='linhaFundo'>
			<td width="30%">Situação</td>
			<td>
				<select name="idSituacao" >
					<option value="" />
					<c:forEach var="elemento" items="${situacoesPlanoMissa}">
						<option value="${elemento.id}" ${elemento.id eq idSituacao ? "selected='selected'" : ""}>${elemento.descricao}</option> 
					</c:forEach>
		        </select>
			</td>
		</tr>
	</table>
	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr class='linhaBotao'>
			<td><input type="submit" value="Pesquisar" class="botao"></td>
			<td><input type="button" value="Novo" class="botao" onclick="submete('manterPlanoMissa.action?method=inserir')"></td>
		</tr>
		<tr><td>&nbsp;</td></tr>
	</table>

	<c:if test="${planosMissa != null}" >  
		<table class='tabelaFormFundo' cellspacing='1'>
		   <tr class='linhaFundoTituloSemNegrito'><td colspan="2">${b:size(planosMissa)} registros encontrados.</td></tr>
		   <tr class='linhaFundoTitulo'>
				<td>Ano/Mês</td>
				<td>Situação</td>
			</tr>
			<c:forEach var="planoMissa" items="${planosMissa}">
			   <tr class='linhaFundo' onMouseOver="campoLinkOver(this)"; onMouseOut="campoLinkOut(this)"; style='cursor:hand' class='semLink' onclick="submete('manterPlanoMissa.action?method=detalhar&id=${planoMissa.id}')"; >
					<td>${planoMissa.anoMesFormatado}</td>
					<td>${planoMissa.situacao.descricao}</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>

</form>