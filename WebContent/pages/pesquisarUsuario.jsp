<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b"%>
<%@ taglib uri="http://displaytag.sf.net/el" prefix="display"%>

<script type="text/javascript">window.onload=function(){focusField("nome");}</script>

<form action="manterUsuario.action?method=pesquisar" method="post">
	<table class='tabelaFormFundo' cellspacing='1'>
	   <tr class='linhaFundo'>
			<td width="30%">Nome</td>
			<td><input type="text" id="nome" name="nome" value="${pesquisaUsuarioCriteria.nome}" ></td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Nome completo</td>
			<td><input type="text" size="70" name="nomeCompleto" value="${pesquisaUsuarioCriteria.nomeCompleto}" ></td>
		</tr>
	   <tr class='linhaFundo'>
			<td>E-mail</td>
			<td><input type="text" size="70" name="email" value="${pesquisaUsuarioCriteria.email}" ></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Ativo</td>
			<td>
				<select name="ativo">
					<option value="" />
					<c:if test="${pesquisaUsuarioCriteria.ativo == 'TRUE'}">
						<option value="true" selected="selected">sim</option> 
					</c:if>
					<c:if test="${pesquisaUsuarioCriteria.ativo != 'TRUE'}">
						<option value="true">sim</option> 
					</c:if>
					<c:if test="${pesquisaUsuarioCriteria.ativo == 'FALSE'}">
						<option value="false" selected="selected">não</option> 
					</c:if>
					<c:if test="${pesquisaUsuarioCriteria.ativo != 'FALSE'}">
						<option value="false">não</option> 
					</c:if>
		        </select>
			</td>
		</tr>
		<tr class='linhaFundo'>
			<td>Bloqueado</td>
			<td>
				<select name="bloqueado">
					<option value="" />
					<c:if test="${pesquisaUsuarioCriteria.bloqueado == 'TRUE'}">
						<option value="true" selected="selected">sim</option> 
					</c:if>
					<c:if test="${pesquisaUsuarioCriteria.bloqueado != 'TRUE'}">
						<option value="true">sim</option> 
					</c:if>
					<c:if test="${pesquisaUsuarioCriteria.bloqueado == 'FALSE'}">
						<option value="false" selected="selected">não</option> 
					</c:if>
					<c:if test="${pesquisaUsuarioCriteria.bloqueado != 'FALSE'}">
						<option value="false">não</option> 
					</c:if>
		        </select>
			</td>
		</tr>
		<tr class='linhaFundo'>
			<td>Módulo</td>
			<td>
				<select name="nomeModulo" >
					<option value="" />
					<c:forEach var="elemento" items="${S_C.usuarioLogado.nomesModulosAdmin}">
						<option value="${elemento}" ${elemento eq pesquisaUsuarioCriteria.nomeModulo ? "selected='selected'" : ""}>${elemento}</option> 
					</c:forEach>
		        </select>
			</td>
		</tr>
		<tr class='linhaFundo'>
			<td>Tipo de permissão</td>
			<td>
				<select name="idTipoPermissao">
					<option value="" />
					<c:forEach var="elemento" items="${tipoPermissoes}">
						<c:if test="${elemento.id == pesquisaUsuarioCriteria.idTipoPermissao}" >  
							<option value="${elemento.id}" selected="selected">${elemento.descricao}</option> 
						</c:if>
						<c:if test="${elemento.id != pesquisaUsuarioCriteria.idTipoPermissao}" >  
							<option value="${elemento.id}">${elemento.descricao}</option> 
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
			<td><input type="button" value="Novo" class="botao" onclick="submete('manterUsuario.action?method=selecionar')"></td>
		</tr>
		<tr><td>&nbsp;</td></tr>
	</table>

	<display:table cellspacing='1' id="usuario" name="sessionScope.S_C.usuarios" requestURI="/manterUsuario.action?paginacao=true" export="true" pagesize="${b:getTamanhoPaginaListagem()}" >
		<display:caption class="displayCaption" >Usuários</display:caption>
		<c:if test="${S_C.usuarios != null}" >
			<display:setProperty name="basic.msg.empty_list" value="${b:getMensagemListagemVazia()}" />
		</c:if>
		<display:column headerClass="linhaFundoTitulo" property="nome" title="Nome" sortable="true" url="/manterUsuario.action?method=selecionar" paramId="id" paramProperty="id" />
		<display:column headerClass="linhaFundoTitulo" property="nomeCompleto" title="Nome completo" sortable="true" />
		<display:column headerClass="linhaFundoTitulo" property="class.simpleName" title="Tipo" sortable="true" />
		<display:column headerClass="linhaFundoTitulo" value="${b:getTipoPermissao(usuario).descricao}" title="Permissão" sortable="true" />
		<display:column headerClass="linhaFundoTitulo" value="${b:getFormatado(usuario.ativo)}" title="Ativo" sortable="true" />
		<display:column headerClass="linhaFundoTitulo" value="${b:getFormatado(usuario.bloqueado)}" title="Bloqueado" sortable="true" />
		<display:column headerClass="linhaFundoTitulo" property="email" title="E-mails" sortable="true" />
	</display:table>

	<c:if test="${usuariosCompartilhados != null}" >  
		<table class='tabelaForm'>
			<tr><td>&nbsp;</td></tr>
			<tr><td class="textoNegrito">Usuários compartilhados</td></tr>
		</table>
		<table class='tabelaFormFundo' cellspacing='1'>
		   <tr class='linhaFundoTituloSemNegrito'><td colspan="6">${b:size(usuariosCompartilhados)} registros encontrados.</td></tr>
		   <tr class='linhaFundoTitulo'>
				<td>Nome</td>
				<td>Nome completo</td>
				<td>Tipo</td>
				<td>Permissão</td>
				<td>Ativo</td>
				<td>Bloqueado</td>
			</tr>
			<c:forEach var="usuario" items="${usuariosCompartilhados}">
			   <tr class='linhaFundo'>
					<td>${usuario.nome}</td>
					<td>${usuario.nomeCompleto}</td>
					<td>${usuario.class.simpleName}</td>
					<td>${b:getTipoPermissaoModulo(usuario,pesquisaUsuarioCriteria.nomeModulo).descricao}</td>
					<td>${b:getFormatado(usuario.ativo)}</td>
					<td>${b:getFormatado(usuario.bloqueado)}</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</form>