<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b"%>

<script type="text/javascript">window.onload=function(){focusField("idCentro");}</script>

<form action="manterVideo.action?method=emprestar" method="post">

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
	   <tr class='linhaFundo'>
			<td>Encarregado do empréstimo</td>
			<td>${S_C.usuarioLogado.nomeFormatado}</td>
		</tr>
	</table>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr><td class="textoNegrito">Empréstimo</td></tr>
	</table>
	<table class='tabelaFormFundo' cellspacing='1'>
		<tr class='linhaFundo'>
			<td width="30%">Centro</td>
			<td>
				<select id="idCentro" name="idCentro">
					<c:if test="${video.isReservado}">
						<option value="${video.emprestimoAtual.usuarioEmprestimo.id}" >${video.emprestimoAtual.usuarioEmprestimo.nomeFormatado}</option> 
					</c:if>
					<c:if test="${!video.isReservado}">
						<option />
						<c:forEach var="elemento" items="${centros}">
							<c:if test="${elemento.id eq emprestimo.usuarioEmprestimo.id}" >  
								<option value="${elemento.id}" selected="selected">${elemento.nomeFormatado}</option> 
							</c:if>
							<c:if test="${elemento.id ne emprestimo.usuarioEmprestimo.id}" >
								<option value="${elemento.id}" >${elemento.nomeFormatado}</option> 
							</c:if>
						</c:forEach>
					</c:if>
		        </select>
			</td>
		</tr>
		<tr class='linhaFundo'>
			<td>Data do empréstimo</td>
			<td><input type="text" maxlength="10" name="dataEmprestimo" value="${b:getFormatado(emprestimo.dataEmprestimo)}" onkeypress="fmtKeyMascara('DD/MM/YYYY',dataEmprestimo,dataEmprestimo,this)" ></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Observações do empréstimo</td>
			<td><TEXTAREA cols="70" rows="3" name="observacoesEmprestimo">${emprestimo.observacoesEmprestimo}</TEXTAREA></td>
		</tr>
	</table>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr class='linhaBotao'>
			<td><input type="submit" value="Emprestar" class="botao"></td>
			<td><input type="button" value="Voltar" class="botao" onclick="submete('manterVideo.action?method=detalhar')"></td>
			<td><input type="hidden" name="id" value="${video.id}"></td>
		</tr>
	</table>
</form>