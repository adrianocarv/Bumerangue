<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b"%>
<%@ taglib uri="http://displaytag.sf.net/el" prefix="display"%>
<%@ taglib uri="/WEB-INF/taglib/fn.tld" prefix="fn"%>

<script type="text/javascript">window.onload=function(){focusField("codigo");}</script>

<form action="manterLivroCultural.action?method=pesquisar" method="post">
	<table class='tabelaFormFundo' cellspacing='1'>
	   <tr class='linhaFundo'>
			<td width="30%">Código</td>
			<td><input type="text" id="codigo" name="codigo" value="${pesquisaLivroCulturalCriteria.codigo}" ></td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Título</td>
			<td><input type="text" size="70" name="titulo" value="${pesquisaLivroCulturalCriteria.titulo}"></td>
		</tr>															
	   <tr class='linhaFundo'>
			<td>Autor</td>
			<td><input type="text" size="70" name="autor" value="${pesquisaLivroCulturalCriteria.autor}"></td>
		</tr>															
	   <tr class='linhaFundo'>
			<td>Idioma</td>
			<td>
				<select name="idIdioma">
					<option />
					<c:forEach var="elemento" items="${idiomas}">
						<c:if test="${elemento.id eq pesquisaLivroCulturalCriteria.idioma}" >  
							<option value="${elemento.id}"  selected="selected">${elemento.descricao}</option>
						</c:if>
						<c:if test="${elemento.id ne pesquisaLivroCulturalCriteria.idioma}" >
							<option value="${elemento.id}">${elemento.descricao}</option> 
						</c:if>
					</c:forEach>
		        </select>
			</td>
		</tr>		
	   <tr class='linhaFundo'>
			<td>Palavras-chaves</td>
			<td><input type="text" size="70" name="palavrasChaves" value="${pesquisaLivroCulturalCriteria.palavrasChaves}"></td>
		</tr>		
	   <tr class='linhaFundo'>
			<td>Observações gerais</td>
			<td><input type="text" size="70" name="observacoesGerais" value="${pesquisaLivroCulturalCriteria.observacoesGerais}"></td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Situação</td>
			<td>
				<select name="idSituacao">
					<option />
					<c:if test="${pesquisaLivroCulturalCriteria.situacao eq '1'}" >  
						<option value="1" selected="selected">Disponível</option>
					</c:if>
					<c:if test="${pesquisaLivroCulturalCriteria.situacao ne '1'}" >  
						<option value="1" >Disponível</option>
					</c:if>
					<c:if test="${pesquisaLivroCulturalCriteria.situacao eq '3'}" >  
						<option value="3" selected="selected">Emprestado</option>
					</c:if>
					<c:if test="${pesquisaLivroCulturalCriteria.situacao ne '3'}" >  
						<option value="3" >Emprestado</option>
					</c:if>
					<c:if test="${pesquisaLivroCulturalCriteria.situacao eq '0'}" >  
						<option value="0" selected="selected">Todos</option>
					</c:if>
					<c:if test="${pesquisaLivroCulturalCriteria.situacao ne '0'}" >  
						<option value="0" >Todos</option>
					</c:if>
		        </select>
			</td>
		</tr>		
	   <tr class='linhaFundo'>
			<td>Emprestado para</td>
			<td>
				<select name="idUsuarioEmprestimo">
					<option />
					<c:forEach var="elemento" items="${usuariosBibliotecaCultura}">
						<c:if test="${elemento.id eq pesquisaLivroCulturalCriteria.usuarioEmprestimo}" >  
							<option value="${elemento.id}" selected="selected">${elemento.nomeFormatado}</option> 
						</c:if>
						<c:if test="${elemento.id ne pesquisaLivroCulturalCriteria.usuarioEmprestimo}" >
							<option value="${elemento.id}" >${elemento.nomeFormatado}</option> 
						</c:if>
					</c:forEach>
		        </select>
			</td>
		</tr>		
	</table>
	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr class='linhaBotao'>
			<td><input type="submit" value="Pesquisar" class="botao"></td>
			<c:if test="${b:isUserInRole(S_C.usuarioLogado,'4')}" >
				<td><input type="button" value="Novo" class="botao" onclick="submete('manterLivroCultural.action?method=editarPre')"></td>
			</c:if>
		</tr>
		<tr><td>&nbsp;</td></tr>
	</table>

	<display:table cellspacing='1' id="livroCultural" name="sessionScope.S_C.objetosBumerangue" requestURI="/manterLivroCultural.action?paginacao=false" export="true" pagesize="${b:getTamanhoPaginaListagem()}" >
		<c:if test="${S_C.objetosBumerangue != null}" >
			<display:setProperty name="basic.msg.empty_list" value="${b:getMensagemListagemVazia()}" />
		</c:if>
		<c:if test="${livroCultural.exibeImagem}" >
			<display:column headerClass="linhaFundoTitulo" style="text-align:center" url="/manterLivroCultural.action?method=detalhar" paramId="id" paramProperty="id" ><img src="${livroCultural.imagem}" width="110" border-width="thin medium thick 10px" /></display:column>	
		</c:if>
		<display:column headerClass="linhaFundoTitulo" property="codigo" title="Código" sortable="true" url="/manterLivroCultural.action?method=detalhar" paramId="id" paramProperty="id" />
		<display:column headerClass="linhaFundoTitulo" property="titulo" title="Título" sortable="true" />
		<display:column headerClass="linhaFundoTitulo" property="autor" title="Autor" sortable="true" />
		<display:column headerClass="linhaFundoTitulo" property="idioma.descricao" title="Idioma" sortable="true" />
		<display:column headerClass="linhaFundoTitulo" property="situacao" title="Situação" sortable="true" />
	</display:table>

</form>