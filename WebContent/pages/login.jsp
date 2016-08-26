<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<script type="text/javascript">window.onload=function(){focusField("j_username");}</script>

<SCRIPT language="JavaScript">
var browserName = navigator.appName;
if ( browserName.indexOf("Explorer") >= 0 ) {
    window.location.href="index.jsp?navegadorIncompativel=true";
}
</SCRIPT>

<meta http-equiv="Cache-Control" content="no-store,no-cache,must-revalidate">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="-1">

<form action="j_security_check" method="post">
	<table class='tabelaFormFundo' cellspacing='1'>
		<c:if test="${bypass == null}">	
		   <tr class='linhaFundo'>
				<td>Usuário</td>
				<td><input type="text" id="j_username" name="j_username"></td>
			</tr>
		   <tr class='linhaFundo'>
				<td>Senha</td>
				<td><input type="password" name="j_password"></td>
			</tr>
		</c:if>
		<c:if test="${bypass == 'true'}">	
			<input type="hidden" id="j_username" name="j_username" value="${j_username}">
			<input type="hidden" name="j_password" value="${j_password}">
			<input type="hidden" name="url" value="${url}">

			<script>document.forms[0].submit();</script>

		</c:if>
	</table>
	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr class='linhaBotao'>
			<td>
				<input type="submit" value="Entrar" class="botao" >
				<br><br>
				<a href="manterSegurancaLivreAcesso.action?method=lembrarSenhaPre"><u>Esqueci minha senha</u></a>
			</td>
		</tr>
	</table>
</form>