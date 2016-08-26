<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b" %>

	<c:if test="${elementoDominio != null}" >
		<script type="text/javascript">window.onload=function(){focusField("descricao");}</script>
	</c:if>

<form action="manterDominio.action?method=listarPre" method="post">
	<table class='tabelaFormFundo' cellspacing='1'>
		<tr class='linhaFundo'>
			<td>Módulo</td>
			<td>
				<select name="nomeModuloSelecionado" onchange="submete('manterDominio.action?method=alterarModuloSelecionado')"; >
					<option value="" />
					<c:forEach var="elemento" items="${S_C.usuarioLogado.nomesModulosAdmin}">
						<option value="${elemento}" ${elemento eq nomeModuloSelecionado ? "selected='selected'" : ""}>${elemento}</option> 
					</c:forEach>
		        </select>
			</td>
		</tr>
	</table>
	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
	</table>

	<c:if test="${elementoDominio != null}" >
		<table class='tabelaForm'>
			<tr><td class="textoNegrito">Elemento selecionado do Domínio ${elementoDominio.dominio.descricaoFormatada}</td></tr>
		</table>
		<table class='tabelaFormFundo' cellspacing='1'>
			<tr class='linhaFundo'>
				<td>Descrição</td>
				<td><input type="text" size="70" name="descricao" id="descricao" value="${elementoDominio.descricao}" ></td>
			</tr>
			<tr class='linhaFundo'>
				<td>Fora de uso</td>
				<td><input type="checkbox" name="foraUso" value="true" ${elementoDominio.foraUso == 'TRUE' ? "checked='checked'" : ""}></td>
			</tr>
		</table>
		<table class='tabelaForm'>
			<tr><td>&nbsp;</td></tr>
			<tr class='linhaBotao'>
				<c:if test="${!elementoDominio.salvo}">
					<td><input type="button" value="Inserir" class="botao" onclick="submete('manterDominio.action?method=inserirElementoDominio')"></td>
				</c:if>
				<c:if test="${elementoDominio.salvo}">
					<td><input type="button" value="Salvar" class="botao" onclick="submete('manterDominio.action?method=editarElementoDominio')"></td>
				</c:if>
				<td><input type="button" value="Excluir" class="botao" onclick="submete('manterDominio.action?method=excluirElementoDominio')"></td>
				<td><input type="button" value="Cancelar" class="botao" onclick="submete('manterDominio.action?method=selecionar')"></td>
				<td><input type="hidden" name="id" value="${elementoDominio.id}"></td>
				<td><input type="hidden" name="idDominio" value="${elementoDominio.dominio.id}"></td>
			</tr>
		</table>
		<table class='tabelaForm'>
			<tr><td>&nbsp;</td></tr>
		</table>	
	</c:if>
	
	<c:if test="${dominio != null}" >
		<table class='tabelaForm'>
			<tr><td class="textoNegrito">Domínio selecionado ${dominio.descricaoFormatada}, elementos:</td></tr>
		</table>
		<table class='tabelaFormFundo' cellspacing='1'>
			<tr class='linhaFundoTituloSemNegrito'><td colspan="3">${b:size(dominio.elementosDominio)} registros encontrados.</td></tr>
			<tr class='linhaFundoTitulo'>
				<td width="20">Elemento</td>
				<td>Descrição</td>
				<td>Fora de uso</td>
			</tr>
			<c:forEach var="elementoDominio" items="${dominio.elementosDominio}">
			   <tr class='linhaFundo'>
					<td><input type="radio" name="id" value="${elementoDominio.id}" >
					<td onMouseOver="campoLinkOver(this)"; onMouseOut="campoLinkOut(this)"; style='cursor:hand' class='semLink' onclick="submete('manterDominio.action?method=selecionarElementoDominio&id=${elementoDominio.id}')"; >${elementoDominio.descricao}</td>
					<td>${b:getFormatado(elementoDominio.foraUso)}</td>
				</tr>
			</c:forEach>
		</table>
		<table class='tabelaForm'>
			<tr class='linhaBotao'>
				<td onMouseOver="campoLinkOver(this)"; onMouseOut="campoLinkOut(this)"; style='cursor:hand' class='semLink' onclick="submete('manterDominio.action?method=trocarPosicaoElementoDominio&direcao=1&idDominio=${dominio.id}')";>Mover p/ cima</td>
				<td onMouseOver="campoLinkOver(this)"; onMouseOut="campoLinkOut(this)"; style='cursor:hand' class='semLink' onclick="submete('manterDominio.action?method=trocarPosicaoElementoDominio&direcao=2&idDominio=${dominio.id}')";>Mover p/ baixo</td>
				<td onMouseOver="campoLinkOver(this)"; onMouseOut="campoLinkOut(this)"; style='cursor:hand' class='semLink' onclick="submete('manterDominio.action?method=trocarPosicaoElementoDominio&direcao=3&idDominio=${dominio.id}')";>Mover p/ primeira posição</td>
				<td onMouseOver="campoLinkOver(this)"; onMouseOut="campoLinkOut(this)"; style='cursor:hand' class='semLink' onclick="submete('manterDominio.action?method=trocarPosicaoElementoDominio&direcao=4&idDominio=${dominio.id}')";>Mover p/ última posição</td>
			</tr>
			<tr><td>&nbsp;</td></tr>
			<tr class='linhaBotao'>
				<td><input type="button" value="Novo" class="botao" onclick="submete('manterDominio.action?method=selecionarElementoDominio&idDominio=${dominio.id}')"></td>
			</tr>
			<tr><td>&nbsp;</td></tr>
		</table>	
	</c:if>

	<table class='tabelaForm'>
		<tr><td class="textoNegrito">Domínios</td></tr>
	</table>
	<table class='tabelaFormFundo' cellspacing='1'>
	   <tr class='linhaFundoTituloSemNegrito'><td colspan="2">${b:size(dominios)} registros encontrados.</td></tr>
	   <tr class='linhaFundoTitulo'>
			<td>Código</td>
			<td>Descrição</td>
		</tr>
		<c:forEach var="dominio" items="${dominios}">
		   <tr class='linhaFundo' onMouseOver="campoLinkOver(this)"; onMouseOut="campoLinkOut(this)"; style='cursor:hand' class='semLink' onclick="submete('manterDominio.action?method=selecionar&codigo=${dominio.codigo}')"; >
				<td>${dominio.codigo}</td>
				<td>${dominio.descricao}</td>
			</tr>
		</c:forEach>
	</table>

</form>