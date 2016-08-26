<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b"%>

<form action="manterVideo.action?method=editarDevolucaoPre" method="post">

	<table class='tabelaForm'>
		<tr><td class="textoNegrito">Vídeo</td></tr>
	</table>
	<table class='tabelaFormFundo' cellspacing='1'>
	   <tr class='linhaFundo'>
			<td width="30%">Código</td>
			<td>${video.codigo}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Título</td>
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
			<td>Público</td>
			<td>${video.publico}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Duração</td>
			<td>${video.duracaoMinutos}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Mídia</td>
			<td>${video.midia.descricao}</td>
		</tr>
	</table>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr><td class="textoNegrito">${emprestimo.situacao}</td></tr>
	</table>
	<table class='tabelaFormFundo' cellspacing='1'>
		<!-- avançado -->
		<c:if test="${!b:isUserInRole(S_C.usuarioLogado,'3') || S_C.usuarioLogado.id == emprestimo.usuarioEmprestimo.id}" >
			<tr class='linhaFundo'>
				<td width="30%">Centro</td>
				<td>${emprestimo.usuarioEmprestimo.nomeFormatado}</td>
			</tr>
		</c:if>
		<tr class='linhaFundo'>
			<td>Situação</td>
			<td>${emprestimo.situacao}</td>
		</tr>		
		<tr class='linhaFundo'>
			<td>Data da reserva</td>
			<td>${b:getFormatado(emprestimo.dataReserva)}</td>
		</tr>
		<!-- avançado -->
		<c:if test="${!b:isUserInRole(S_C.usuarioLogado,'3') || S_C.usuarioLogado.id == emprestimo.usuarioEmprestimo.id}" >
			<tr class='linhaFundo'>
				<td>Observações da reserva</td>
				<td>${emprestimo.observacoesReserva}</td>
			</tr>															
		</c:if>
		<tr class='linhaFundo'>
			<td>Usuário que realizou a reserva</td>
			<td>${emprestimo.usuarioRealizouReserva.nomeFormatado}</td>
		</tr>
		<tr class='linhaFundo'>
			<td>Data do empréstimo</td>
			<td>${b:getFormatado(emprestimo.dataEmprestimo)}</td>
		</tr>
		<!-- avançado -->
		<c:if test="${!b:isUserInRole(S_C.usuarioLogado,'3') || S_C.usuarioLogado.id == emprestimo.usuarioEmprestimo.id}" >
			<tr class='linhaFundo'>
				<td>Observações do empréstimo</td>
				<td>${emprestimo.observacoesEmprestimo}</td>
			</tr>															
		</c:if>
		<tr class='linhaFundo'>
			<td>Usuário que realizou o empréstimo</td>
			<td>${emprestimo.usuarioRealizouEmprestimo.nomeFormatado}</td>
		</tr>
		<tr class='linhaFundo'>
			<td>Data da devolução</td>
			<td>${b:getFormatado(emprestimo.dataDevolucao)}</td>
		</tr>
		<!-- avançado -->
		<c:if test="${!b:isUserInRole(S_C.usuarioLogado,'3') || S_C.usuarioLogado.id == emprestimo.usuarioEmprestimo.id}" >
		   	<tr class='linhaFundo'>
				<td>Observações da devolução</td>
				<td>${emprestimo.observacoesDevolucao}</td>
			</tr>															
			<tr class='linhaFundo'>
				<td>Usuário que realizou a devolução</td>
				<td>${emprestimo.usuarioRealizouDevolucao.nomeFormatado}</td>
			</tr>
			<tr class='linhaFundo'>
				<td>Tipo de atividade</td>
				<td>${emprestimo.tipoAtividadeVideo}</td>
			</tr>
			<tr class='linhaFundo'>
				<td>Público</td>
				<td>${emprestimo.publicoVideo}</td>
			</tr>
			<tr class='linhaFundo'>
				<td>Número de assistentes</td>
				<td>${emprestimo.numeroAssistentesVideo}</td>
			</tr>
		</c:if>
	</table>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr class='linhaBotao'>
			<c:if test="${b:isUserInRole(S_C.usuarioLogado,'1') && emprestimo.isDevolucao}" >
				<td><input type="submit" value="Editar" class="botao"></td>
				<td><input type="button" value="Cancelar devolução" class="botao" onclick="submete('manterVideo.action?method=cancelarDevolucao')"></td>
			</c:if>
			<td><input type="button" value="Detalhar Vídeo" class="botao" onclick="submete('manterVideo.action?method=detalhar&id=${video.id}')"></td>
			<td><input type="button" value="Voltar" class="botao" onclick="submete('relatorioVideo.action?method=pesquisarPre&tipo=historico')"></td>
			<td><input type="hidden" name="id" value="${emprestimo.id}"></td>
		</tr>
	</table>
</form>