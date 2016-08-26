<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>

<script type="text/javascript">window.onload=function(){focusField("senhaAtual");}</script>

<form action="manterSeguranca.action?method=trocarSenha" method="post">
	<table class='tabelaFormFundo' cellspacing='1'>
		<tr class='linhaFundo'>
			<td width="30%">Senha atual</td>
			<td><input type="password" id="senhaAtual" name="senhaAtual" ></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Nova senha</td>
			<td><input type="password" name="novaSenha" ></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Confirmação da nova senha</td>
			<td><input type="password" name="novaSenhaConfirma" ></td>
			</tr>
		</table>

		<table class='tabelaForm'>
			<tr><td>&nbsp;</td></tr>
			<tr class='linhaBotao'>
				<td><input type="submit" value="Trocar Senha" class="botao" ></td>
			</tr>
		</table>
</form>