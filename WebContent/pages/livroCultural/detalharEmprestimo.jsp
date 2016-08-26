<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b"%>

<form action="manterLivroCultural.action?method=editarDevolucaoPre" method="post">

	<table class='tabelaForm'>
		<tr><td class="textoNegrito">Livro</td></tr>
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
	</table>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr><td class="textoNegrito">${emprestimo.situacao}</td></tr>
	</table>
	<table class='tabelaFormFundo' cellspacing='1'>
		<!-- avançado -->
		<c:if test="${!b:isUserInRole(S_C.usuarioLogado,'6') || S_C.usuarioLogado.id == emprestimo.usuarioEmprestimo.id}" >
			<tr class='linhaFundo'>
				<td width="30%">Usuário</td>
				<td>${emprestimo.usuarioEmprestimo.nomeFormatado}</td>
			</tr>
		</c:if>
		<tr class='linhaFundo'>
			<td>Situação</td>
			<td>${emprestimo.situacao}</td>
		</tr>		
		<!-- avançado -->
		<tr class='linhaFundo'>
			<td>Data do empréstimo</td>
			<td>${b:getFormatado(emprestimo.dataEmprestimo)}</td>
		</tr>
		<!-- avançado -->
		<c:if test="${!b:isUserInRole(S_C.usuarioLogado,'6') || S_C.usuarioLogado.id == emprestimo.usuarioEmprestimo.id}" >
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
		<c:if test="${!b:isUserInRole(S_C.usuarioLogado,'6') || S_C.usuarioLogado.id == emprestimo.usuarioEmprestimo.id}" >
		   	<tr class='linhaFundo'>
				<td>Observações da devolução</td>
				<td>${emprestimo.observacoesDevolucao}</td>
			</tr>															
			<tr class='linhaFundo'>
				<td>Usuário que realizou a devolução</td>
				<td>${emprestimo.usuarioRealizouDevolucao.nomeFormatado}</td>
			</tr>
		</c:if>
	</table>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr class='linhaBotao'>
			<c:if test="${b:isUserInRole(S_C.usuarioLogado,'4') && emprestimo.isDevolucao}" >
				<td><input type="submit" value="Editar" class="botao"></td>
				<td><input type="button" value="Cancelar devolução" class="botao" onclick="submete('manterLivroCultural.action?method=cancelarDevolucao')"></td>
			</c:if>
			<td><input type="button" value="Detalhar Livro" class="botao" onclick="submete('manterLivroCultural.action?method=detalhar&id=${livroCultural.id}')"></td>
			<td><input type="button" value="Voltar" class="botao" onclick="submete('relatorioLivroCultural.action?method=pesquisarPre&tipo=historico')"></td>
			<td><input type="hidden" name="id" value="${emprestimo.id}"></td>
		</tr>
	</table>
</form>