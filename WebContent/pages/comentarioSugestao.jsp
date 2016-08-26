<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>

<c:if test="${not empty S_C.usuarioLogado}" >
	<script type="text/javascript">window.onload=function(){focusField("textoComentario");}</script>
</c:if>
<c:if test="${empty S_C.usuarioLogado}" >
	<script type="text/javascript">window.onload=function(){focusField("nomeRemetente");}</script>
</c:if>

<form action="manterSegurancaLivreAcesso.action?method=comentarSugerir" method="post">
	<table class='tabelaForm'>
		<tr><td class="textoNegrito">Espaço aberto para envio de sugestões, dúvidas, etc.</td></tr>
		<tr><td>&nbsp;</td></tr>
	</table>
	<table class='tabelaFormFundo' cellspacing='1'>
	   <tr class='linhaFundo'>
			<td>Nome</td>
			<c:if test="${not empty S_C.usuarioLogado}" >
				<td><input type="text" id="nomeRemetente" size="70" name="nomeRemetente"  disabled="disabled" value="${S_C.usuarioLogado.nomeFormatado}"></td>
			</c:if>
			<c:if test="${empty S_C.usuarioLogado}" >
				<td><input type="text" id="nomeRemetente" size="70" name="nomeRemetente" value="${nomeRemetente}"></td>
			</c:if>
		</tr>
	   <tr class='linhaFundo'>
			<td>E-mail</td>
			<c:if test="${not empty S_C.usuarioLogado}" >
				<td><input type="text" id="emailRemetente" size="70" name="emailRemetente" disabled="disabled" value="${S_C.usuarioLogado.email}"></td>
			</c:if>
			<c:if test="${empty S_C.usuarioLogado}" >
				<td><input type="text" id="emailRemetente" size="70" name="emailRemetente" value="${emailRemetente}"></td>
			</c:if>
		</tr>
		<tr class='linhaFundo'>
			<td>Mensagem</td>
			<td><TEXTAREA cols="100" rows="5" id="textoComentario"  name="textoComentario">${textoComentario}</TEXTAREA></td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Para</td>
			<td><b><i>${emailAdmin}</i></b></td>
		</tr>
	</table>
	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr class='linhaBotao'>
			<td><input type="submit" value="Enviar" class="botao" ></td>
		</tr>
	</table>
</form>
