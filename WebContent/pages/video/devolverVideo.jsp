<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b"%>

<script type="text/javascript">window.onload=function(){focusField("dataDevolucao");}</script>

<form action="manterVideo.action?method=devolver" method="post">

	<table class='tabelaForm'>
		<tr><td class="textoNegrito">V�deo</td></tr>
	</table>
	<table class='tabelaFormFundo' cellspacing='1'>
	   <tr class='linhaFundo'>
			<td width="30%">C�digo</td>
			<td>${video.codigo}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>T�tulo</td>
			<td>${video.titulo}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Localidade</td>
			<td>${video.localidade}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Data</td>
			<td>${b:getFormatado(video.data)}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Legendado</td>
			<td>${b:getFormatado(video.legendado)}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>P�blico</td>
			<td>${video.publico}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Dura��o</td>
			<td>${video.duracaoMinutos}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>M�dia</td>
			<td>${video.midia.descricao}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Encarregado da devolu��o</td>
			<td>${S_C.usuarioLogado.nomeFormatado}</td>
		</tr>
	</table>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr><td class="textoNegrito">Devolu��o</td></tr>
	</table>
	<table class='tabelaFormFundo' cellspacing='1'>
		<tr class='linhaFundo'>
			<td width="30%">Centro</td>
			<td>${video.emprestimoAtual.usuarioEmprestimo.nomeFormatado}</td>
		</tr>
		<tr class='linhaFundo'>
			<td>Data da devolu��o</td>
			<td><input type="text" maxlength="10" id="dataDevolucao" name="dataDevolucao" value="${b:getFormatado(devolucao.dataDevolucao)}" onkeypress="fmtKeyMascara('DD/MM/YYYY',dataDevolucao,dataDevolucao,this)" ></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Observa��es da devolu��o</td>
			<td><TEXTAREA cols="70" rows="3" name="observacoesDevolucao">${devolucao.observacoesDevolucao}</TEXTAREA></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Tipo de atividade</td>
			<td><input type="text" name="tipoAtividadeVideo" value="${devolucao.tipoAtividadeVideo}" ></td>
		</tr>
		<tr class='linhaFundo'>
			<td>P�blico</td>
			<td><input type="text" name="publicoVideo" value="${devolucao.publicoVideo}" ></td>
		</tr>
		<tr class='linhaFundo'>
			<td>N�mero de assistentes</td>
			<td><input type="text" maxlength="3" name="numeroAssistentesVideo" value="${devolucao.numeroAssistentesVideo}" onkeypress="somenteNumeros()" ></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Observa��es gerais do v�deo</td>
			<td><TEXTAREA cols="70" rows="3" name="observacoesGerais">${video.observacoesGerais}</TEXTAREA></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Palavras-chaves</td>
			<td><TEXTAREA cols="70" rows="3" name="palavrasChaves">${video.palavrasChaves}</TEXTAREA></td>
		</tr>
	</table>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr class='linhaBotao'>
			<td><input type="submit" value="Devolver" class="botao"></td>
			<td><input type="button" value="Voltar" class="botao" onclick="submete('manterVideo.action?method=detalhar')"></td>
			<td><input type="hidden" name="id" value="${video.id}"></td>
		</tr>
	</table>
</form>