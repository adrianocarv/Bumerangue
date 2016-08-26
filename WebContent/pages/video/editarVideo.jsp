<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b"%>

<script type="text/javascript">window.onload=function(){focusField("codigo");}</script>

<form action="manterVideo.action?method=editar" method="post">

	<table class='tabelaFormFundo' cellspacing='1'>
		<tr class='linhaFundo'>
			<td width="30%">Código</td>
			<td><input type="text" maxlength="5" id="codigo" name="codigo" onkeypress="somenteNumeros()" value="${video.codigo}"></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Categoria</td>
			<td>
				<select name="idCategoria">
					<option />
					<c:forEach var="elemento" items="${categorias}">
						<c:if test="${elemento.id eq video.categoria.id}" >  
							<option value="${elemento.id}" selected="selected">${elemento.descricao}</option> 
						</c:if>
						<c:if test="${elemento.id ne video.categoria.id}" >  
							<option value="${elemento.id}">${elemento.descricao}</option> 
						</c:if>
					</c:forEach>
		        </select>
			</td>
		</tr>
		<tr class='linhaFundo'>
			<td>Código da caixa</td>
			<td><input type="text" name="codigoCaixa" value="${video.codigoCaixa}"></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Título</td>
			<td><input type="text" size="70" name="titulo" value="${video.titulo}"></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Localidade</td>
			<td><input type="text" size="70" name="localidade" value="${video.localidade}"></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Data</td>
			<td><input type="text" maxlength="10" name="data" onkeypress="fmtKeyMascara('DD/MM/YYYY',data,data,this)" value="${b:getFormatado(video.data)}"></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Legendado</td>
			<c:if test="${video.legendado == 'TRUE'}">
				<td><input type="checkbox" name="legendado" value="true" checked="checked"></td>
			</c:if>
			<c:if test="${video.legendado != 'TRUE'}">
				<td><input type="checkbox" name="legendado" value="true"></td>
			</c:if>
		</tr>
		<tr class='linhaFundo'>
			<td>Dublado</td>
			<c:if test="${video.dublado == 'TRUE'}">
				<td><input type="checkbox" name="dublado" value="true" checked="checked"></td>
			</c:if>
			<c:if test="${video.dublado != 'TRUE'}">
				<td><input type="checkbox" name="dublado" value="true"></td>
			</c:if>
		</tr>
		<tr class='linhaFundo'>
			<td>Público</td>
			<td><input type="text" name="publico" value="${video.publico}"></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Duração (minutos)</td>
			<td><input type="text" maxlength="3" name="duracaoMinutos" onkeypress="somenteNumeros()" value="${video.duracaoMinutos}"></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Mídia</td>
			<td>
				<select name="idMidia">
					<option />
					<c:forEach var="elemento" items="${midias}">
						<c:if test="${elemento.id eq video.midia.id}" >  
							<option value="${elemento.id}" selected="selected">${elemento.descricao}</option> 
						</c:if>
						<c:if test="${elemento.id ne video.midia.id}" >  
							<option value="${elemento.id}">${elemento.descricao}</option> 
						</c:if>
					</c:forEach>
		        </select>
			</td>
		</tr>
		<tr class='linhaFundo'>
			<td>Palavras-chaves</td>
			<td><TEXTAREA cols="70" rows="3" name="palavrasChaves">${video.palavrasChaves}</TEXTAREA></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Observações</td>
			<td>
				<select name="idObservacoes">
					<option />
					<c:forEach var="elemento" items="${observacoes}">
						<c:if test="${elemento.id eq video.observacoes.id}" >  
							<option value="${elemento.id}" selected="selected">${elemento.descricao}</option> 
						</c:if>
						<c:if test="${elemento.id ne video.observacoes.id}" >  
							<option value="${elemento.id}">${elemento.descricao}</option> 
						</c:if>
					</c:forEach>
		        </select>
			</td>
		</tr>
		<tr class='linhaFundo'>
			<td>Observações gerais</td>
			<td><TEXTAREA cols="70" rows="3" name="observacoesGerais">${video.observacoesGerais}</TEXTAREA></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Localização nas publicações internas</td>
			<td><input type="text" size="40" name="localizacaoPI" value="${video.localizacaoPI}"></td>
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
			<c:if test="${video.foraUso == 'TRUE'}">
				<td><input type="checkbox" name="foraUso" value="true" checked="checked"></td>
			</c:if>
			<c:if test="${video.foraUso != 'TRUE'}">
				<td><input type="checkbox" name="foraUso" value="true"></td>
			</c:if>
		</tr>
	</table>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr class='linhaBotao'>
			<c:if test="${!video.salvo}">
				<td><input type="button" value="Inserir" class="botao" onclick="submete('manterVideo.action?method=inserir')"></td>
				<td><input type="button" value="Voltar" class="botao" onclick="submete('manterVideo.action?method=pesquisarPre')"></td>
			</c:if>
			<c:if test="${video.salvo}">
				<td><input type="submit" value="Salvar" class="botao" ></td>
				<td><input type="button" value="Voltar" class="botao" onclick="submete('manterVideo.action?method=detalhar')"></td>
			</c:if>
			<td><input type="hidden" name="id" value="${video.id}"></td>
		</tr>
	</table>
</form>