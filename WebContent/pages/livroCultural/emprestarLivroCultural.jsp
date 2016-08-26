<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b"%>

<script type="text/javascript">window.onload=function(){focusField("idCentro");}</script>

<form action="manterLivroCultural.action?method=emprestar" method="post">

	<table class='tabelaForm'>
		<tr><td class="textoNegrito">Livro Cultural</td></tr>
	</table>
	<table class='tabelaFormFundo' cellspacing='1'>
	   <tr class='linhaFundo'>
			<td width="30%">C�digo</td>
			<td>${livroCultural.codigo}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>T�tulo</td>
			<td>${livroCultural.titulo}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Encarregado do empr�stimo</td>
			<td>${S_C.usuarioLogado.nomeFormatado}</td>
		</tr>
	</table>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr><td class="textoNegrito">Empr�stimo</td></tr>
	</table>
	<table class='tabelaFormFundo' cellspacing='1'>
		<tr class='linhaFundo'>
			<td width="30%">Usu�rio</td>
			<td>
				<select id="idUsuarioBibliotecaCultural" name="idUsuarioBibliotecaCultural">
					<option/>
					<c:forEach var="elemento" items="${usuarios}">
						<c:if test="${elemento.id eq emprestimo.usuarioEmprestimo.id}" >  
							<option value="${elemento.id}" selected="selected">${elemento.nomeFormatado}</option> 
						</c:if>
						<c:if test="${elemento.id ne emprestimo.usuarioEmprestimo.id}" >
							<option value="${elemento.id}" >${elemento.nomeFormatado}</option> 
						</c:if>
					</c:forEach>
		        </select>
			</td>
		</tr>
		<tr class='linhaFundo'>
			<td>Data do empr�stimo</td>
			<td><input type="text" maxlength="10" name="dataEmprestimo" value="${b:getFormatado(emprestimo.dataEmprestimo)}" onkeypress="fmtKeyMascara('DD/MM/YYYY',dataEmprestimo,dataEmprestimo,this)" ></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Observa��es do empr�stimo</td>
			<td><TEXTAREA cols="70" rows="3" name="observacoesEmprestimo">${emprestimo.observacoesEmprestimo}</TEXTAREA></td>
		</tr>
	</table>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr class='linhaBotao'>
			<td><input type="submit" value="Emprestar" class="botao"></td>
			<td><input type="button" value="Voltar" class="botao" onclick="submete('manterLivroCultural.action?method=detalhar')"></td>
			<td><input type="hidden" name="id" value="${livroCultural.id}"></td>
		</tr>
	</table>
</form>