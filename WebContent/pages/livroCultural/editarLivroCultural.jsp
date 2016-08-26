<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b"%>

<script type="text/javascript">window.onload=function(){focusField("codigo");}</script>

<form action="manterLivroCultural.action?method=editar" method="post">

	<table class='tabelaFormFundo' cellspacing='1'>
	   <tr class='linhaFundo'>
			<td rowspan="7" align="center"><img src="${livroCultural.imagem}" width="300"/></td>
			<td>Código</td>
			<td width="35%"><input type="text" maxlength="8" id="codigo" name="codigo" value="${livroCultural.codigo}"></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Título</td>
			<td><input type="text" size="70" name="titulo" value="${livroCultural.titulo}"></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Autor</td>
			<td><input type="text" size="70" name="autor" value="${livroCultural.autor}"></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Idioma</td>
			<td>
				<select name="idIdioma">
					<option />
					<c:forEach var="elemento" items="${idiomas}">
						<c:if test="${elemento.id eq livroCultural.idioma.id}" >  
							<option value="${elemento.id}"  selected="selected">${elemento.descricao}</option>
						</c:if>
						<c:if test="${elemento.id ne livroCultural.idioma.id}" >
							<option value="${elemento.id}">${elemento.descricao}</option> 
						</c:if>
					</c:forEach>
		        </select>
			</td>
		</tr>
		<tr class='linhaFundo'>
			<td>Palavras-chaves</td>
			<td><TEXTAREA cols="70" rows="3" name="palavrasChaves">${livroCultural.palavrasChaves}</TEXTAREA></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Observações gerais</td>
			<td><TEXTAREA cols="70" rows="3" name="observacoesGerais">${livroCultural.observacoesGerais}</TEXTAREA></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Fora de uso</td>
			<c:if test="${livroCultural.foraUso == 'TRUE'}">
				<td><input type="checkbox" name="foraUso" value="true" checked="checked"></td>
			</c:if>
			<c:if test="${livroCultural.foraUso != 'TRUE'}">
				<td><input type="checkbox" name="foraUso" value="true"></td>
			</c:if>
		</tr>
	</table>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr class='linhaBotao'>
			<c:if test="${!livroCultural.salvo}">
				<td><input type="button" value="Inserir" class="botao" onclick="submete('manterLivroCultural.action?method=inserir')"></td>
				<td><input type="button" value="Voltar" class="botao" onclick="submete('manterLivroCultural.action?method=pesquisarPre')"></td>
			</c:if>
			<c:if test="${livroCultural.salvo}">
				<td><input type="submit" value="Salvar" class="botao" ></td>
				<td><input type="button" value="Voltar" class="botao" onclick="submete('manterLivroCultural.action?method=detalhar')"></td>
			</c:if>
			<td><input type="hidden" name="id" value="${livroCultural.id}"></td>
		</tr>
	</table>
</form>