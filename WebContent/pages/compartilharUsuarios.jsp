<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b"%>

<script type="text/javascript">window.onload=function(){focusField("nome");}</script>

<form action="compartilharUsuario.action?method=pesquisar" method="post">
	<table class='tabelaFormFundo' cellspacing='1'>
		<tr class='linhaFundo'>
			<td width="30%">Nome</td>
			<td><input type="text" id="nome" name="nome" value="${pesquisaUsuarioCriteria.nome}"></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Nome completo</td>
			<td><input type="text" size="70" name="nomeCompleto" value="${pesquisaUsuarioCriteria.nomeCompleto}"></td>
		</tr>
		<tr class='linhaFundo'>
			<td>E-mail</td>
			<td><input type="text" size="70" name="email" value="${pesquisaUsuarioCriteria.email}"></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Módulo</td>
			<td>
				<select name="nomeModulo">
					<option value="" />
					<c:forEach var="elemento" items="${nomesModulos}">
						<c:if test="${elemento eq pesquisaUsuarioCriteria.nomeModulo}" >  
							<option value="${elemento}" selected="selected">${elemento}</option> 
						</c:if>
						<c:if test="${elemento ne pesquisaUsuarioCriteria.nomeModulo}" >  
							<option value="${elemento}">${elemento}</option> 
						</c:if>
					</c:forEach>
		        </select>
			</td>
		</tr>
		<tr class='linhaFundo'>
			<td class="textoNegrito" >Compartilhar com o módulo</td>
			<td class="textoNegrito" >
				<select name="nomeModuloSelecionado" onchange="submete('compartilharUsuario.action?method=alterarModuloSelecionado')"; >
					<option value="" />
					<c:forEach var="elemento" items="${S_C.usuarioLogado.nomesModulosAdmin}">
						<c:if test="${elemento eq pesquisaUsuarioCriteria.nomeModuloSelecionado}" >
							<option value="${elemento}" selected="selected">${elemento}</option> 
						</c:if>
						<c:if test="${elemento ne pesquisaUsuarioCriteria.nomeModuloSelecionado}" >  
							<option value="${elemento}">${elemento}</option> 
						</c:if>
					</c:forEach>
		        </select>
			</td>
		</tr>
	</table>
	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr class='linhaBotao'>
			<td><input type="submit" value="Pesquisar" class="botao" ></td>
		</tr>
	</table>

	<c:if test="${usuariosParaCompartilhar != null}" >  
		<table class='tabelaForm'>
			<tr><td class="textoNegrito">Usúarios para compartilhar</td></tr>
		</table>
		<table class='tabelaFormFundo' cellspacing='1'>
		   <tr class='linhaFundoTituloSemNegrito'>
			   <td colspan="2">${b:size(usuariosParaCompartilhar)} registros encontrados.</td>
				<td align="right" >Selecionar para todos:</td>
				<td>
					<select name="codigoTipoPermissaoTodos" onchange="submete('compartilharUsuario.action?method=selecionarTipoPermissaoParaTodos')"; >
						<option value="0" />
						<c:forEach var="elemento" items="${tipoPermissoes}">
							<c:if test="${codigoTipoPermissaoTodos eq elemento.codigo}" >
								<option value="${elemento.codigo}" selected="selected">${elemento.descricao}</option> 
							</c:if>
							<c:if test="${codigoTipoPermissaoTodos ne elemento.codigo}" >
								<option value="${elemento.codigo}">${elemento.descricao}</option> 
							</c:if>
						</c:forEach>
					</select>
				</td>
		   </tr>
		   <tr class='linhaFundoTitulo'>
				<td>Nome</td>
				<td>Nome completo</td>
				<td>Tipo</td>
				<td>Tipo de Permissão</td>
			</tr>
			<c:forEach var="usuario" items="${usuariosParaCompartilhar}">
			   <tr class='linhaFundo'>
					<td>${usuario.nome}</td>
					<td>${usuario.nomeCompleto}</td>
					<td>${usuario.class.simpleName}</td>
					<td>
						<select name="idUsuario_codigoTipoPermissao">
							<option value="${usuario.id}_" />
							<c:forEach var="elemento" items="${tipoPermissoes}">
								<c:if test="${codigoTipoPermissaoTodos eq elemento.codigo || ( empty codigoTipoPermissaoTodos && b:isUserInRoleModulo(usuario, elemento.codigo, pesquisaUsuarioCriteria.nomeModuloSelecionado) ) }" >  
									<option value="${usuario.id}_${elemento.codigo}" selected="selected">${elemento.descricao}</option> 
								</c:if>
								<c:if test="${codigoTipoPermissaoTodos ne elemento.codigo && ( not empty codigoTipoPermissaoTodos || !b:isUserInRoleModulo(usuario, elemento.codigo, pesquisaUsuarioCriteria.nomeModuloSelecionado) ) }" >  
									<option value="${usuario.id}_${elemento.codigo}">${elemento.descricao}</option> 
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
			</c:forEach>
		</table>
		<table class='tabelaForm'>
			<tr><td>&nbsp;</td></tr>
			<tr class='linhaBotao'>
				<td><input type="button" value="Salvar" class="botao" onclick="submete('compartilharUsuario.action?method=compartilhar')"></td>
			</tr>
		</table>
	</c:if>

	<c:if test="${usuariosModulo != null}" >  
		<table class='tabelaForm'>
			<tr><td>&nbsp;</td></tr>
			<tr><td class="textoNegrito">Usuários do módulo selecionado</td></tr>
		</table>
		<table class='tabelaFormFundo' cellspacing='1'>
		   <tr class='linhaFundoTituloSemNegrito'><td colspan="5">${b:size(usuariosModulo)} registros encontrados.</td></tr>
		   <tr class='linhaFundoTitulo'>
				<td>Nome</td>
				<td>Nome completo</td>
				<td>Permissão</td>
				<td>Ativo</td>
				<td>Bloqueado</td>
			</tr>
			<c:forEach var="usuario" items="${usuariosModulo}">
			   <tr class='linhaFundo' onMouseOver="campoLinkOver(this)"; onMouseOut="campoLinkOut(this)"; style='cursor:hand' class='semLink' onclick="submete('compartilharUsuario.action?method=selecionarUsuarioModuloSelecionado&id=${usuario.id}')"; >
					<td>${usuario.nome}</td>
					<td>${usuario.nomeCompleto}</td>
					<td>${b:getTipoPermissao(usuario).descricao}</td>
					<td>${b:getFormatado(usuario.ativo)}</td>
					<td>${b:getFormatado(usuario.bloqueado)}</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</form>