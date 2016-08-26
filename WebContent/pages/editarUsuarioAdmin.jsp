<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b"%>

<script type="text/javascript">window.onload=function(){focusField("nome");}</script>

<form action="manterSeguranca.action?method=selecionarUsuarioAdmin" method="post">
	<table class='tabelaFormFundo' cellspacing='1'>
		<tr class='linhaFundo'>
			<td width="30%">Nome</td>
			<td>${usuarioAdmin.nome}</td>
		</tr>
		<tr class='linhaFundo'>
			<td>Nome completo</td>
			<td><input type="text" size="70" name="nomeCompleto" value="${usuarioAdmin.nomeCompleto}"></td>
		</tr>
		<tr class='linhaFundo'>
			<td>E-mail</td>
			<td><input type="text" size="70" name="email" value="${usuarioAdmin.email}"></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Telefone</td>
			<td><input type="text" size="70" name="telefone" value="${usuarioAdmin.telefone}"></td>
		</tr>
	</table>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr class="linhaBotao">
			<td><input type="button" value="Salvar" class="botao" onclick="submete('manterSeguranca.action?method=editarUsuarioAdmin')"></td>
			<td><input type="button" value="Cancelar" class="botao" onclick="submete('manterSeguranca.action?method=menuAdministracao')"></td>
			<td><input type="hidden" name="id" value="${usuarioAdmin.id}"></td>
		</tr>
	</table>
</form>