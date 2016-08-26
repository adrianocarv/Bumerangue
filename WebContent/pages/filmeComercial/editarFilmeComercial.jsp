<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b"%>

<script type="text/javascript">window.onload=function(){focusField("codigo");}</script>

<form action="manterFilmeComercial.action?method=editar" method="post">

	<table class='tabelaFormFundo' cellspacing='1'>
		<tr class='linhaFundo'>
			<td width="30%">Código</td>
			<td><input type="text" maxlength="5" id="codigo" name="codigo" onkeypress="somenteNumeros()" value="${filmeComercial.codigo}"></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Título</td>
			<td><input type="text" size="70" name="titulo" value="${filmeComercial.titulo}"></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Título original</td>
			<td><input type="text" size="70" name="tituloOriginal" value="${filmeComercial.tituloOriginal}"></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Público</td>
			<td>
				<select name="idPublico">
					<option />
					<c:forEach var="elemento" items="${publicos}">
						<c:if test="${elemento.id eq filmeComercial.publico.id}" >  
							<option value="${elemento.id}" selected="selected">${elemento.descricao}</option> 
						</c:if>
						<c:if test="${elemento.id ne filmeComercial.publico.id}" >  
							<option value="${elemento.id}">${elemento.descricao}</option> 
						</c:if>
					</c:forEach>
		        </select>
			</td>
		</tr>
		<tr class='linhaFundo'>
			<td>Duração</td>
			<td><input type="text" maxlength="3" name="duracaoMinutos" onkeypress="somenteNumeros()" value="${filmeComercial.duracaoMinutos}"></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Ano</td>
			<td><input type="text" maxlength="4" name="ano" onkeypress="somenteNumeros()" value="${filmeComercial.ano}"></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Categoria</td>
			<td>
				<select name="idCategoria">
					<option />
					<c:forEach var="elemento" items="${categorias}">
						<c:if test="${elemento.id eq filmeComercial.categoria.id}" >  
							<option value="${elemento.id}" selected="selected">${elemento.descricao}</option> 
						</c:if>
						<c:if test="${elemento.id ne filmeComercial.categoria.id}" >  
							<option value="${elemento.id}">${elemento.descricao}</option> 
						</c:if>
					</c:forEach>
		        </select>
			</td>
		</tr>
		<tr class='linhaFundo'>
			<td>Diretor</td>
			<td><input type="text" size="70" name="diretor" value="${filmeComercial.diretor}"></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Atores / Observações</td>
			<td><TEXTAREA cols="70" rows="3" name="atoresObservacoes">${filmeComercial.atoresObservacoes}</TEXTAREA></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Link da sinopse</td>
			<td><input type="text" size="70" name="linkSinopse" value="${filmeComercial.linkSinopse}"></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Link para download</td>
			<td><input type="text" size="70" name="linksDownload" value="${filmeComercial.linksDownload}"></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Data da inclusão</td>
			<td>${b:getFormatado(filmeComercial.dataCriacao)}</td>
		</tr>
		<!--
		<tr class='linhaFundo'>
			<td>Links para download</td>
			<td><input type="text" size="70" name="linksDownload" value="${filmeComercial.linksDownload}"></td>
		</tr>
		-->
	</table>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr class='linhaBotao'>
			<c:if test="${!filmeComercial.salvo}">
				<td><input type="button" value="Inserir" class="botao" onclick="submete('manterFilmeComercial.action?method=inserir')"></td>
				<td><input type="button" value="Voltar" class="botao" onclick="submete('manterFilmeComercial.action?method=pesquisarPre')"></td>
			</c:if>
			<c:if test="${filmeComercial.salvo}">
				<td><input type="submit" value="Salvar" class="botao" ></td>
				<td><input type="button" value="Voltar" class="botao" onclick="submete('manterFilmeComercial.action?method=detalhar')"></td>
			</c:if>
			<td><input type="hidden" name="id" value="${filmeComercial.id}"></td>
		</tr>
	</table>
</form>