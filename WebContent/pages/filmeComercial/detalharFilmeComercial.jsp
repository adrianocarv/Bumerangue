<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b"%>

<form action="manterFilmeComercial.action?method=pesquisar" method="post">

	<table class='tabelaFormFundo' cellspacing='1'>
		<tr class='linhaFundo'>
			<td width="30%">Título</td>
			<td>${filmeComercial.titulo}</td>
		</tr>
		<tr class='linhaFundo'>
			<td>Título original</td>
			<td>${filmeComercial.tituloOriginal}</td>
		</tr>
		<tr class='linhaFundo'>
			<td>Público</td>
			<td>${filmeComercial.publico.descricao}</td>
		</tr>
		<tr class='linhaFundo'>
			<td>Duração</td>
			<td>${filmeComercial.duracaoMinutos}</td>
		</tr>
		<tr class='linhaFundo'>
			<td>Ano</td>
			<td>${filmeComercial.ano}</td>
		</tr>
		<tr class='linhaFundo'>
			<td>Categoria</td>
			<td>${filmeComercial.categoria.descricao}</td>
		</tr>
		<tr class='linhaFundo'>
			<td>Diretor</td>
			<td>${filmeComercial.diretor}</td>
		</tr>
		<tr class='linhaFundo'>
			<td>Atores / Observações</td>
			<td>${filmeComercial.atoresObservacoes}</td>
		</tr>
		<tr class='linhaFundo'>
			<td>Link da sinopse</td>
			<td onMouseOver="campoLinkOver(this)"; onMouseOut="campoLinkOut(this)"; style='cursor:hand' class='truqueLink' onclick="window.open('${filmeComercial.linkSinopse}','teste')"; >${filmeComercial.linkSinopse}</td>
		</tr>
		<tr class='linhaFundo'>
			<td>Data da inclusão</td>
			<td>${b:getFormatado(filmeComercial.dataCriacao)}</td>
		</tr>
	</table>
	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
			<tr>
				<c:if test="${b:isUserInRole(S_C.usuarioLogado,'7-9') && filmeComercial.downloadMultiplo}" >
					<c:forEach var="link" items="${filmeComercial.linksDownloadAsArray}" varStatus="status">
						<c:if test="${status.first}">
							<tr><td class="textoNegrito">Download disponível em...</td></tr>
						</c:if>
						<tr><td><a target="_blank" href="manterFilmeComercial.action?method=baixar&id=${filmeComercial.id}&parte=${status.index}" >Parte ${status.count}</a></td></tr>
						<c:if test="${status.last}">
							<tr><td class="textoNegrito">... ${status.count} partes.</td></tr>
						</c:if>
					</c:forEach>
				</c:if>
				<c:if test="${b:isUserInRole(S_C.usuarioLogado,'7-9') && filmeComercial.downloadUnico}" >
					<!-- tr><td colspan="2" align="center"><a href="manterFilmeComercial.action?method=baixar&id=${filmeComercial.id}&parte=0" target="_blank"><img src="resources/img/download.jpg" /></a></td></tr -->
					<tr><td colspan="2" align="center"><a href="${filmeComercial.linksDownload}" target="_blank"><img src="resources/img/download.jpg" /></a></td></tr>
				</c:if>
			</tr>
		<tr><td>&nbsp;</td></tr>
		<tr class='linhaBotao'>
			<c:if test="${b:isUserInRole(S_C.usuarioLogado,'7')}" >
				<td><input type="button" value="Editar" class="botao" onclick="submete('manterFilmeComercial.action?method=editarPre')"></td>
				<td><input type="button" value="Excluir" class="botao" onclick="submete('manterFilmeComercial.action?method=excluir')"></td>
			</c:if>
			<td><input type="button" value="Voltar" class="botao" onclick="history.back()"></td>
			<td><input type="hidden" name="id" value="${filmeComercial.id}"></td>
		</tr>
	</table>
<br><br>
	<c:if test="${b:isUserInRole(S_C.usuarioLogado,'7-9') && filmeComercial.downloadMultiplo}" >
		<table class='tabelaForm'>
			<tr><td width="50%" align="center"><a href="http://www.youtube.com/watch?v=mCA7ij_WSPc" target="_blank"><img src="http://i2.ytimg.com/vi/mCA7ij_WSPc/default.jpg" /></a></td><td align="center"><a href="http://www.youtube.com/watch?v=3drxs2ss6hI" target="_blank"><img src="http://i4.ytimg.com/vi/3drxs2ss6hI/default.jpg" /></a></td></tr>
			<tr><td align="center"><a href="http://www.youtube.com/watch?v=mCA7ij_WSPc" target="_blank"><b>Como baixar os filmes no Bumerangue</b></a></td><td align="center"><a href="http://www.youtube.com/watch?v=3drxs2ss6hI" target="_blank"><b>Como instalar o Winrar</b></a></td></tr>
		</table>
	</c:if>

</form>
