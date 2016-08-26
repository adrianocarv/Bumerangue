<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b"%>

<script type="text/javascript">window.onload=function(){focusField("dataDevolucao");}</script>

<form action="manterVideo.action?method=editarDevolucao" method="post">

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
	</table>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr><td class="textoNegrito">${emprestimo.situacao}</td></tr>
	</table>
	<table class='tabelaFormFundo' cellspacing='1'>
		<tr class='linhaFundo'>
			<td width="30%">Centro</td>
			<td>${emprestimo.usuarioEmprestimo.nomeFormatado}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Situa��o</td>
			<td>${emprestimo.situacao}</td>
		</tr>		
	   <tr class='linhaFundo'>
			<td>Data da reserva</td>
			<td>${b:getFormatado(emprestimo.dataReserva)}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Observa��es da reserva</td>
			<td>${emprestimo.observacoesReserva}</td>
		</tr>															
		<tr class='linhaFundo'>
			<td>Usu�rio que realizou a reserva</td>
			<td>${emprestimo.usuarioRealizouReserva.nomeFormatado}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Data do empr�stimo</td>
			<td>${b:getFormatado(emprestimo.dataEmprestimo)}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Observa��es do empr�stimo</td>
			<td>${emprestimo.observacoesEmprestimo}</td>
		</tr>															
		<tr class='linhaFundo'>
			<td>Usu�rio que realizou o empr�stimo</td>
			<td>${emprestimo.usuarioRealizouEmprestimo.nomeFormatado}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Data da devolu��o</td>
			<td><input type="text" maxlength="10" id="dataDevolucao" name="dataDevolucao" value="${b:getFormatado(emprestimo.dataDevolucao)}" onkeypress="fmtKeyMascara('DD/MM/YYYY',dataDevolucao,dataDevolucao,this)" ></td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Observa��es da devolu��o</td>
			<td><TEXTAREA cols="70" rows="3" name="observacoesDevolucao">${emprestimo.observacoesDevolucao}</TEXTAREA></td>
		</tr>															
		<tr class='linhaFundo'>
			<td>Usu�rio que realizou a devolu��o</td>
			<td>${emprestimo.usuarioRealizouDevolucao.nomeFormatado}</td>
		</tr>
		<tr class='linhaFundo'>
			<td>Tipo de atividade</td>
			<td><input type="text" name="tipoAtividadeVideo" value="${emprestimo.tipoAtividadeVideo}"></td>
		</tr>
		<tr class='linhaFundo'>
			<td>P�blico</td>
			<td><input type="text" name="publicoVideo" value="${emprestimo.publicoVideo}" ></td>
		</tr>
		<tr class='linhaFundo'>
			<td>N�mero de assistentes</td>
			<td><input type="text" maxlength="3" name="numeroAssistentesVideo" onkeypress="somenteNumeros()" value="${emprestimo.numeroAssistentesVideo}"></td>
		</tr>
	</table>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr class='linhaBotao'>
			<td><input type="submit" value="Salvar" class="botao"></td>
			<td><input type="button" value="Voltar" class="botao" onclick="submete('relatorioVideo.action?method=detalharEmprestimo')"></td>
			<td><input type="hidden" name="id" value="${emprestimo.id}"></td>
		</tr>
	</table>
</form>