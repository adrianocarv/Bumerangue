<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b"%>

	<c:if test="${compartimento != null}" >
		<script type="text/javascript">window.onload=function(){focusField("descricao");}</script>
	</c:if>

<form action="manterLocalizacaoFisica.action?method=listarPre" method="post">
	<table class='tabelaFormFundo' cellspacing='1'>
		<tr class='linhaFundo'>
			<td>Módulo</td>
			<td>
				<select name="nomeModuloSelecionado" onchange="submete('manterLocalizacaoFisica.action?method=alterarModuloSelecionado')"; >
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
			<tr><td class="textoNegrito">Compartimento</td></tr>
		</table>
		<table class='tabelaFormFundo' cellspacing='1'>
			<tr class='linhaFundo'>
				<td>Descrição</td>
				<td><input type="text" size="70" name="descricao" id="descricao" value="${elementoDominio.descricao}" ></td>
			</tr>
			<tr class='linhaFundo'>
				<td>Quantidade</td>
				<td><input type="text" maxlength="3" name="personalizado1" onkeypress="somenteNumeros()" value="${elementoDominio.personalizado1}"></td>
			</tr>
			<tr class='linhaFundo'>
				<td>Direção</td>
				<td>
					<select name="personalizado2">
						<option />
						<c:forEach var="elemento" items="${direcoes}">
							<option value="${elemento.id}" ${elemento.id eq elementoDominio.personalizado2.id ? "selected='selected'" : "" }>${elemento.descricao}</option> 
						</c:forEach>
			        </select>
				</td>
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
					<td><input type="button" value="Inserir" class="botao" onclick="submete('manterLocalizacaoFisica.action?method=inserirElementoDominio')"></td>
				</c:if>
				<c:if test="${elementoDominio.salvo}">
					<td><input type="button" value="Salvar" class="botao" onclick="submete('manterLocalizacaoFisica.action?method=editarElementoDominio')"></td>
				</c:if>
				<td><input type="button" value="Excluir" class="botao" onclick="submete('manterLocalizacaoFisica.action?method=excluirElementoDominio')"></td>
				<td><input type="button" value="Cancelar" class="botao" onclick="submete('manterLocalizacaoFisica.action?method=listarPre')"></td>
				<td><input type="hidden" name="id" value="${elementoDominio.id}"></td>
				<td><input type="hidden" name="idDominio" value="${elementoDominio.dominio.id}"></td>
			</tr>
		</table>
		<table class='tabelaForm'>
			<tr><td>&nbsp;</td></tr>
		</table>	
	</c:if>

	<c:if test="${dominio != null}" >
		<c:if test="${elementoDominio == null}" >
			<table class='tabelaFormFundo' cellspacing='1'>
				<tr class='linhaFundo'>
					<td>Campos de ordenação</td>
					<td><input type="text" size="50" name="personalizado2" value="${dominio.personalizado2}" ></td>
				</tr>
				<tr class='linhaFundo'>
					<td colspan="2">
						&nbsp;&nbsp;Atributos do ObjetoBumerangue do módulo dispostos na sintaxe de uma cláusula order by (HQL).<br>
						&nbsp;&nbsp;Ex: categoria.descricao, midia.descricao desc, data, codigo.<br>&nbsp;
					</td>
				</tr>
				<tr class='linhaFundo'>
					<td>Grupo de compartimentos</td>
					<td><input type="text" size="50" name="personalizado3" value="${dominio.personalizado3}" ></td>
				<tr class='linhaFundo'>
					<td colspan="2">
						&nbsp;&nbsp;Valor com duas partes separadas por '-' (hífem).<br>
						&nbsp;&nbsp;Parte 1: Quantidade das primeiras letras do valor atributo. Essas letras serão comparadas com primeiras letras (mesma quantidade) da descrição do compartimento.<br>
						&nbsp;&nbsp;&nbsp;&nbsp;Se o valor da 1ª parte for um '*' será considerado todo valor do atributo.<br>
						&nbsp;&nbsp;Parte 2: Nome do atributo do ObjetoBumerangue do módulo que será o grupo de compartimento.<br>
						&nbsp;&nbsp;Ex: 2 - categoria<br>
					</td>
				</tr>
				</tr>
			</table>
			<table class='tabelaForm'>
				<tr><td>&nbsp;</td></tr>
				<tr class='linhaBotao'>
					<td><input type="button" value="Salvar" class="botao" onclick="submete('manterLocalizacaoFisica.action?method=editarDominio&id=${dominio.id}')"></td>
				</tr>
			</table>
		</c:if>
		<table class='tabelaForm'>
			<tr><td>&nbsp;</td></tr>
			<tr><td class="textoNegrito">Compartimentos</td></tr>
		</table>	
		<table class='tabelaFormFundo' cellspacing='1'>
			<tr class='linhaFundoTituloSemNegrito'><td colspan="5">${b:size(dominio.elementosDominio)} registros encontrados.</td></tr>
			<tr class='linhaFundoTitulo'>
				<td width="20">Elemento</td>
				<td>Descrição</td>
				<td>Quantidade</td>
				<td>Direção</td>
				<td>Fora de uso</td>
			</tr>
			<c:forEach var="elementoDominio" items="${dominio.elementosDominio}">
				<tr class='linhaFundo'>
					<td><input type="radio" name="id" value="${elementoDominio.id}" >
					<td onMouseOver="campoLinkOver(this)"; onMouseOut="campoLinkOut(this)"; style='cursor:hand' class='semLink' onclick="submete('manterLocalizacaoFisica.action?method=selecionarElementoDominio&id=${elementoDominio.id}')"; >${elementoDominio.descricao}</td>
					<td>${elementoDominio.personalizado1}</td>
					<td>${elementoDominio.personalizado2.descricao}</td>
					<td>${b:getFormatado(elementoDominio.foraUso)}</td>
				</tr>
			</c:forEach>
		</table>
		<table class='tabelaForm'>
			<tr class='linhaBotao'>
				<td onMouseOver="campoLinkOver(this)"; onMouseOut="campoLinkOut(this)"; style='cursor:hand' class='semLink' onclick="submete('manterLocalizacaoFisica.action?method=trocarPosicaoElementoDominio&direcao=1&idDominio=${dominio.id}')";>Mover p/ cima</td>
				<td onMouseOver="campoLinkOver(this)"; onMouseOut="campoLinkOut(this)"; style='cursor:hand' class='semLink' onclick="submete('manterLocalizacaoFisica.action?method=trocarPosicaoElementoDominio&direcao=2&idDominio=${dominio.id}')";>Mover p/ baixo</td>
				<td onMouseOver="campoLinkOver(this)"; onMouseOut="campoLinkOut(this)"; style='cursor:hand' class='semLink' onclick="submete('manterLocalizacaoFisica.action?method=trocarPosicaoElementoDominio&direcao=3&idDominio=${dominio.id}')";>Mover p/ primeira posição</td>
				<td onMouseOver="campoLinkOver(this)"; onMouseOut="campoLinkOut(this)"; style='cursor:hand' class='semLink' onclick="submete('manterLocalizacaoFisica.action?method=trocarPosicaoElementoDominio&direcao=4&idDominio=${dominio.id}')";>Mover p/ última posição</td>
			</tr>
			<tr><td>&nbsp;</td></tr>
			<tr class='linhaBotao'>
				<td><input type="button" value="Novo" class="botao" onclick="submete('manterLocalizacaoFisica.action?method=selecionarElementoDominio&idDominio=${dominio.id}')"></td>
			</tr>
			<tr><td>&nbsp;</td></tr>
		</table>	
	</c:if>

</form>