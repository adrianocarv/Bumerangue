<%@ page contentType="text/html; charset=ISO-8859-1" %>
<script type="text/javascript">window.onload=function(){focusField("nome");}</script>

<form action="manterSegurancaLivreAcesso.action?method=lembrarSenha" method="post">
	<table class='tabelaForm'>
		<tr><td class="textoNegrito">Informe o nome ou e-mail do usuário no sistema, para receber uma nova senha.</td></tr>
		<tr><td>&nbsp;</td></tr>
	</table>
	<table class='tabelaFormFundo' cellspacing='1'>
	   <tr class='linhaFundo'>
			<td>Nome do usuário</td>
			<td><input type="text" id="nome" name="nome"></td>
		</tr>
	   <tr class='linhaFundo'>
			<td>E-mail do usuário</td>
			<td><input type="text" size="70" name="email"></td>
		</tr>
	</table>
	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr class='linhaBotao'>
			<td><input type="submit" value="Enviar" class="botao" ></td>
		</tr>
	</table>
</form>
