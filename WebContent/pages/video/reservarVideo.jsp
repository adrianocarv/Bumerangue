<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b"%>

<script type="text/javascript">window.onload=function(){focusField("idCentro");}</script>

<form action="manterVideo.action?method=reservar" method="post">

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
			<td>Encarregado da reserva</td>
			<td>${S_C.usuarioLogado.nomeFormatado}</td>
		</tr>
	</table>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr><td class="textoNegrito">Reserva</td></tr>
	</table>
	<table class='tabelaFormFundo' cellspacing='1'>
		<tr class='linhaFundo'>
			<td width="30%">Centro</td>
			<td>
				<select id="idCentro" name="idCentro">
					<option />
					<c:forEach var="elemento" items="${centros}">
						<c:if test="${elemento.id eq reserva.usuarioEmprestimo.id}" >  
							<option value="${elemento.id}" selected="selected">${elemento.nomeFormatado}</option> 
						</c:if>
						<c:if test="${elemento.id ne reserva.usuarioEmprestimo.id}" >
							<option value="${elemento.id}" >${elemento.nomeFormatado}</option> 
						</c:if>
					</c:forEach>
		        </select>
			</td>
		</tr>
		<tr class='linhaFundo'>
			<td>Observa��es da reserva</td>
			<td><TEXTAREA cols="70" rows="3" name="observacoesReserva">${reserva.observacoesReserva}</TEXTAREA></td>
		</tr>
	</table>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr class='linhaBotao'>
			<td><input type="submit" value="Reservar" class="botao"></td>
			<td><input type="button" value="Voltar" class="botao" onclick="submete('manterVideo.action?method=detalhar')"></td>
			<td><input type="hidden" name="id" value="${video.id}"></td>
		</tr>
	</table>
</form>