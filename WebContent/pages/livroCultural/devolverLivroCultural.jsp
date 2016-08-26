<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b"%>

<script type="text/javascript">window.onload=function(){focusField("dataDevolucao");}</script>

<form action="manterLivroCultural.action?method=devolver" method="post">

	<table class='tabelaForm'>
		<tr><td class="textoNegrito">Livro Cultural</td></tr>
	</table>
	<table class='tabelaFormFundo' cellspacing='1'>
	   <tr class='linhaFundo'>
			<td width="30%">Código</td>
			<td>${livroCultural.codigo}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Título</td>
			<td>${livroCultural.titulo}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Autor</td>
			<td>${livroCultural.autor}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Encarregado da devolução</td>
			<td>${S_C.usuarioLogado.nomeFormatado}</td>
		</tr>
	</table>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr><td class="textoNegrito">Devolução</td></tr>
	</table>
	<table class='tabelaFormFundo' cellspacing='1'>
		<tr class='linhaFundo'>
			<td width="30%">Usuário</td>
			<td>${livroCultural.emprestimoAtual.usuarioEmprestimo.nomeFormatado}</td>
		</tr>
		<tr class='linhaFundo'>
			<td>Data da devolução</td>
			<td><input type="text" maxlength="10" id="dataDevolucao" name="dataDevolucao" value="${b:getFormatado(devolucao.dataDevolucao)}" onkeypress="fmtKeyMascara('DD/MM/YYYY',dataDevolucao,dataDevolucao,this)" ></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Observações da devolução</td>
			<td><TEXTAREA cols="70" rows="3" name="observacoesDevolucao">${devolucao.observacoesDevolucao}</TEXTAREA></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Observações Gerais do livro</td>
			<td><TEXTAREA cols="70" rows="3" name="observacoesGerais">${livroCultural.observacoesGerais}</TEXTAREA></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Palavras-chaves</td>
			<td><TEXTAREA cols="70" rows="3" name="palavrasChaves">${livroCultural.palavrasChaves}</TEXTAREA></td>
		</tr>
	</table>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr class='linhaBotao'>
			<td><input type="submit" value="Devolver" class="botao"></td>
			<td><input type="button" value="Voltar" class="botao" onclick="submete('manterLivroCultural.action?method=detalhar')"></td>
			<td><input type="hidden" name="id" value="${livroCultural.id}"></td>
		</tr>
	</table>
</form>