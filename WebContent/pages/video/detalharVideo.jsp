<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b"%>

<form action="manterVideo.action?method=pesquisar" method="post">

	<table class='tabelaForm'>
		<tr><td class="textoNegrito">Vídeo</td></tr>
	</table>
	<table class='tabelaFormFundo' cellspacing='1'>
	   <tr class='linhaFundo'>
			<td width="30%">Código</td>
			<td>${video.codigo}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Categoria</td>
			<td>${video.categoria.descricao}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Código da caixa</td>
			<td>${video.codigoCaixa}</td>
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
			<td>Dublado</td>
			<td>${b:getFormatado(video.dublado)}</td>
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
	   <tr class='linhaFundo'>
			<td>Palavras-chaves</td>
			<td>${video.palavrasChaves}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Observações</td>
			<td style="color:red">${video.observacoes.descricao}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Observações gerais</td>
			<td>${video.observacoesGerais}</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Localização nas publicações internas</td>
			<td>${video.localizacaoPI}</td>
		</tr>
	   <!-- admin -->
		<c:if test="${b:isUserInRole(S_C.usuarioLogado,'1')}" >
		   <tr class='linhaFundo'>
				<td>Localização física</td>
				<td>${video.localizacaoFisica}</td>
			</tr>
		   <tr class='linhaFundo'>
				<td>Localização física anterior</td>
				<td>${video.localizacaoFisicaAnterior}</td>
			</tr>
		</c:if>
	   <tr class='linhaFundo'>
			<td>Fora de uso</td>
			<td>${b:getFormatado(video.foraUso)}</td>
		</tr>
	</table>

	<c:if test="${video.isReservado}" >
		<table class='tabelaForm'>
			<tr><td>&nbsp;</td></tr>
			<tr><td class="textoNegrito">Reservado</td></tr>
		</table>
	   <!-- avançado -->
	   <c:if test="${!b:isUserInRole(S_C.usuarioLogado,'3') || S_C.usuarioLogado.id == video.emprestimoAtual.usuarioEmprestimo.id}" >
			<table class='tabelaFormFundo' cellspacing='1'>
			   <tr class='linhaFundo'>
					<td width="30%">Reservado para o Centro</td>
					<td>${video.emprestimoAtual.usuarioEmprestimo.nomeFormatado}</td>
				</tr>
			   <tr class='linhaFundo'>
					<td>Data da reserva</td>
					<td>${b:getFormatado(video.emprestimoAtual.dataReserva)}</td>
				</tr>
			   <tr class='linhaFundo'>
					<td>Quem reservou</td>
					<td>${video.emprestimoAtual.usuarioRealizouReserva.nome}</td>
				</tr>
				<tr class='linhaFundo'>
					<td>Observações da reserva</td>
					<td>${video.emprestimoAtual.observacoesReserva}</td>
				</tr>
			</table>
		</c:if>
	</c:if>

	<c:if test="${video.isEmprestado}" >
		<table class='tabelaForm'>
			<tr><td>&nbsp;</td></tr>
			<tr><td class="textoNegrito">Emprestado</td></tr>
		</table>
	   <!-- avan?ado -->
	   <c:if test="${!b:isUserInRole(S_C.usuarioLogado,'3') || S_C.usuarioLogado.id == video.emprestimoAtual.usuarioEmprestimo.id}" >
			<table class='tabelaFormFundo' cellspacing='1'>
			   <tr class='linhaFundo'>
					<td width="30%">Emprestado para o Centro</td>
					<td>${video.emprestimoAtual.usuarioEmprestimo.nomeFormatado}</td>
				</tr>
			   <tr class='linhaFundo'>
					<td>Data do empréstimo</td>
					<td>${b:getFormatado(video.emprestimoAtual.dataEmprestimo)}</td>
				</tr>
			   <tr class='linhaFundo'>
					<td>Quem emprestou</td>
					<td>${video.emprestimoAtual.usuarioRealizouEmprestimo.nomeFormatado}</td>
				</tr>
				<tr class='linhaFundo'>
					<td>Observações do empréstimo</td>
					<td>${video.emprestimoAtual.observacoesEmprestimo}</td>
				</tr>
			</table>
		</c:if>
	</c:if>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr class='linhaBotao'>
			<c:if test="${b:isUserInRole(S_C.usuarioLogado,'1')}" >
				<td><input type="button" value="Editar" class="botao" onclick="submete('manterVideo.action?method=editarPre')"></td>

				<c:if test="${video.isDisponivel || video.isReservado}" >
					<td><input type="button" value="Emprestar" class="botao" onclick="submete('manterVideo.action?method=emprestarPre')"></td>
				</c:if>
				<td><input type="button" value="Excluir" class="botao" onclick="submete('manterVideo.action?method=excluir')"></td>
				<td><input type="button" value="Copiar" class="botao" onclick="submete('manterVideo.action?method=copiar')"></td>
			</c:if>

			<c:if test="${video.isDisponivel}" >
				<td><input type="button" value="Reservar" class="botao" onclick="submete('manterVideo.action?method=reservarPre')"></td>
			</c:if>
			<c:if test="${video.isReservado}" >
				<td><input type="button" value="Cancelar reserva" class="botao" onclick="submete('manterVideo.action?method=cancelarReserva')"></td>
			</c:if>

			<td><input type="button" value="Voltar" class="botao" onclick="submete('manterVideo.action?method=pesquisarPre')"></td>
			<td><input type="hidden" name="id" value="${video.id}"></td>
		</tr>
		<c:if test="${b:isUserInRole(S_C.usuarioLogado,'1') && video.isEmprestado}" >
			<tr class='linhaBotao'>
				<td><input type="button" value="Devolver" class="botao" onclick="submete('manterVideo.action?method=devolverPre')"></td>
				<td><input type="button" value="Cancelar empréstimo" class="botao" onclick="submete('manterVideo.action?method=cancelarEmprestimo')"></td>
				<td><input type="button" value="Emitir ficha de empréstimo" class="botao" onclick="submete('relatorioVideo.action?method=emitirFichaEmprestimo')"></td>
			</tr>
		</c:if>
	</table>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
	</table>
   	<!-- avan?ado -->
	<c:if test="${!b:isUserInRole(S_C.usuarioLogado,'3')}" >
		<c:if test="${b:size(video.emprestimos) > 0}" >  
			<table class='tabelaFormFundo' cellspacing='1'>
			   <tr class='linhaFundoTituloSemNegrito'><td colspan="5">${b:size(video.emprestimos)} Empréstimos.</td></tr>
			   <tr class='linhaFundoTitulo'>
					<td>Centro</td>
					<td>Data da reserva</td>
					<td>Data do empréstimo</td>
					<td>Data da devolução</td>
					<td>Situação</td>
				</tr>
				<c:forEach var="emprestimo" items="${video.emprestimos}">
				   <tr class='linhaFundo' onMouseOver="campoLinkOver(this)"; onMouseOut="campoLinkOut(this)"; style='cursor:hand' class='semLink' onclick="submete('relatorioVideo.action?method=detalharEmprestimo&id=${emprestimo.id}')";>
						<td>${emprestimo.usuarioEmprestimo.nomeFormatado}</td>
						<td>${b:getFormatado(emprestimo.dataReserva)}</td>
						<td>${b:getFormatado(emprestimo.dataEmprestimo)}</td>
						<td>${b:getFormatado(emprestimo.dataDevolucao)}</td>
						<td>${emprestimo.situacao}</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
	</c:if>
</form>
