<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b"%>

<form action="manterLivroCultural.action?method=pesquisar" method="post">

	<table class='tabelaForm'>
		<tr><td class="textoNegrito">Livro Cultural</td></tr>
	</table>
	<table class='tabelaFormFundo' cellspacing='1'>
	   <tr class='linhaFundo'>
			<td rowspan="7" align="center"><img src="${livroCultural.imagem}" width="300"/></td>
			<td>Código</td>
			<td width="35%">${livroCultural.codigo}</td>
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
			<td>Idioma</td>
			<td>${livroCultural.idioma.descricao}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Palavras-chaves</td>
			<td>${livroCultural.palavrasChaves}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Observações Gerais</td>
			<td>${livroCultural.observacoesGerais}</td>
		</tr>

	   <tr class='linhaFundo'>
			<td>Fora de uso</td>
			<td>${b:getFormatado(livroCultural.foraUso)}</td>
		</tr>
	</table>

	<c:if test="${livroCultural.isEmprestado}" >
		<table class='tabelaForm'>
			<tr><td>&nbsp;</td></tr>
			<tr><td class="textoNegrito">Emprestado</td></tr>
		</table>
	   <!-- avançado -->
	   <c:if test="${!b:isUserInRole(S_C.usuarioLogado,'6') || S_C.usuarioLogado.id == livroCultural.emprestimoAtual.usuarioEmprestimo.id}" >
			<table class='tabelaFormFundo' cellspacing='1'>
			   <tr class='linhaFundo'>
					<td width="30%">Emprestado para</td>
					<td>${livroCultural.emprestimoAtual.usuarioEmprestimo.nomeFormatado}</td>
				</tr>
			   <tr class='linhaFundo'>
					<td>Data do empréstimo</td>
					<td>${b:getFormatado(livroCultural.emprestimoAtual.dataEmprestimo)}</td>
				</tr>
			   <tr class='linhaFundo'>
					<td>Quem emprestou</td>
					<td>${livroCultural.emprestimoAtual.usuarioRealizouEmprestimo.nomeFormatado}</td>
				</tr>
				<tr class='linhaFundo'>
					<td>Observações do empréstimo</td>
					<td>${livroCultural.emprestimoAtual.observacoesEmprestimo}</td>
				</tr>
			</table>
		</c:if>
	</c:if>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr class='linhaBotao'>
			<c:if test="${b:isUserInRole(S_C.usuarioLogado,'4')}" >
				<td><input type="button" value="Editar" class="botao" onclick="submete('manterLivroCultural.action?method=editarPre')"></td>

				<c:if test="${livroCultural.isDisponivel}" >
					<td><input type="button" value="Emprestar" class="botao" onclick="submete('manterLivroCultural.action?method=emprestarPre&id=${livroCultural.id}')"></td>
				</c:if>
				<c:if test="${livroCultural.isEmprestado}" >
					<td><input type="button" value="Devolver" class="botao" onclick="submete('manterLivroCultural.action?method=devolverPre')"></td>
					<td><input type="button" value="Cancelar empréstimo" class="botao" onclick="submete('manterLivroCultural.action?method=cancelarEmprestimo')"></td>
				</c:if>
				<td><input type="button" value="Excluir" class="botao" onclick="submete('manterLivroCultural.action?method=excluir')"></td>
				<td><input type="button" value="Copiar" class="botao" onclick="submete('manterLivroCultural.action?method=copiar')"></td>
			</c:if>

			<c:if test="${livroCultural.isReservado}" >
				<td><input type="button" value="Emitir ficha de empréstimo" class="botao" onclick="submete('manterLivroCultural.action?method=editarPre')"></td>
				<td><input type="button" value="Cancelar reserva" class="botao" onclick="submete('manterLivroCultural.action?method=cancelarReserva')"></td>
			</c:if>

			<td><input type="button" value="Voltar" class="botao" onclick="submete('manterLivroCultural.action?method=pesquisarPre')"></td>
			<td><input type="hidden" name="id" value="${livroCultural.id}"></td>
		</tr>
	</table>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
	</table>
   	<!-- avançado -->
	<c:if test="${!b:isUserInRole(S_C.usuarioLogado,'6')}" >
		<c:if test="${b:size(livroCultural.emprestimos) > 0}" >  
			<table class='tabelaFormFundo' cellspacing='1'>
			   <tr class='linhaFundoTituloSemNegrito'><td colspan="5">${b:size(livroCultural.emprestimos)} Empréstimos.</td></tr>
			   <tr class='linhaFundoTitulo'>
					<td>Usuário</td>
					<td>Data do empréstimo</td>
					<td>Data da devolução</td>
					<td>Situação</td>
				</tr>
				<c:forEach var="emprestimo" items="${livroCultural.emprestimos}">
				   <tr class='linhaFundo' onMouseOver="campoLinkOver(this)"; onMouseOut="campoLinkOut(this)"; style='cursor:hand' class='semLink' onclick="submete('relatorioLivroCultural.action?method=detalharEmprestimo&id=${emprestimo.id}')";>
						<td>${emprestimo.usuarioEmprestimo.nomeFormatado}</td>
						<td>${b:getFormatado(emprestimo.dataEmprestimo)}</td>
						<td>${b:getFormatado(emprestimo.dataDevolucao)}</td>
						<td>${emprestimo.situacao}</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
	</c:if>
</form>
