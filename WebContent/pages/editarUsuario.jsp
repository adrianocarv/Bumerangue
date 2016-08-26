<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b"%>

<script type="text/javascript">window.onload=function(){focusField("nome");}</script>

<form action="manterUsuario.action?method=editar" method="post">
	<table class='tabelaFormFundo' cellspacing='1'>
		<tr class='linhaFundo'>
			<td width="30%">Nome</td>
			<td><input type="text" id="nome" name="nome" value="${usuario.nome}"></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Nome completo</td>
			<td><input type="text" size="70" name="nomeCompleto" value="${usuario.nomeCompleto}"></td>
		</tr>
		<tr class='linhaFundo'>
			<td>E-mails (separados por , ou ;)</td>
			<td><input type="text" size="70" name="email" value="${usuario.email}"></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Telefone</td>
			<td><input type="text" size="70" name="telefone" value="${usuario.telefone}"></td>
		</tr>
		<c:if test="${!usuario.salvo}">
			<tr class='linhaFundo'>
				<td>Módulo</td>
				<td>
					<select name="nomeModulo" >
						<option value="" />
						<c:forEach var="elemento" items="${S_C.usuarioLogado.nomesModulosAdmin}">
							<option value="${elemento}" ${elemento eq usuario.nomeModulo ? "selected='selected'" : ""}>${elemento}</option> 
						</c:forEach>
			        </select>
				</td>
			</tr>
		</c:if>
		<c:if test="${usuario.salvo}">
			<tr class='linhaFundo'>
				<td>Tipo</td>
				<td>usuario.class.simpleName</td>
			</tr>
			<tr class='linhaFundo'>
				<td>Tipo de permissão</td>
				<td>
					<select name="codigoTipoPermissao">
						<c:forEach var="elemento" items="${tipoPermissoes}">
							<c:if test="${elemento.codigo eq usuario.codigoTipoPermissao}" >  
								<option value="${elemento.codigo}" selected="selected">${elemento.descricao}</option> 
							</c:if>
							<c:if test="${elemento.codigo ne usuario.codigoTipoPermissao}" >  
								<option value="${elemento.codigo}">${elemento.descricao}</option> 
							</c:if>
						</c:forEach>
			        </select>
				</td>
			</tr>
		</c:if>
		<tr class='linhaFundo'>
			<td>Ativo</td>
			<c:if test="${usuario.ativo == 'TRUE'}">
				<td><input type="checkbox" name="ativo" value="true" checked="checked"></td>
			</c:if>
			<c:if test="${usuario.ativo != 'TRUE'}">
				<td><input type="checkbox" name="ativo" value="true"></td>
			</c:if>
		</tr>
		<tr class='linhaFundo'>
			<td>Bloqueado</td>
			<td>${usuario.bloqueado}</td>
		</tr>
	</table>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr class='linhaBotao'>
			<c:if test="${!usuario.salvo}">
				<td><input type="button" value="Inserir" class="botao" onclick="submete('manterUsuario.action?method=inserir')"></td>
			</c:if>
			<c:if test="${usuario.salvo}">
				<td><input type="button" value="Salvar" class="botao" onclick="submete('manterUsuario.action?method=editar')"></td>
				<td><input type="button" value="Reiniciar Senha" class="botao" onclick="submete('manterUsuario.action?method=reiniciarSenha')"></td>
				<td><input type="button" value="Excluir" class="botao" onclick="submete('manterUsuario.action?method=excluir')"></td>
			</c:if>
			<c:if test="${usuario.bloqueado}">
				<td><input type="button" value="Desbloquear" class="botao" onclick="submete('manterUsuario.action?method=desbloquearUsuario')"></td>
			</c:if>
			<td><input type="button" value="Cancelar" class="botao" onclick="submete('manterUsuario.action?method=pesquisarPre')"></td>
			<td><input type="hidden" name="id" value="${usuario.id}"></td>
		</tr>
	</table>
</form>