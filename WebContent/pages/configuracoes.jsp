<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b"%>

<form action="manterConfiguracao.action?method=listarPre" method="post">
	<table class='tabelaFormFundo' cellspacing='1'>
		<tr class='linhaFundo'>
			<td>Módulo</td>
			<td>
				<select name="nomeModuloSelecionado" onchange="submete('manterConfiguracao.action?method=alterarModuloSelecionado')"; >
					<option value="" />
					<c:forEach var="elemento" items="${S_C.usuarioLogado.nomesModulosAdmin}">
						<option value="${elemento}" ${elemento eq nomeModuloSelecionado ? "selected='selected'" : ""}>${elemento}</option> 
					</c:forEach>
		        </select>
			</td>
		</tr>
	</table>

	<c:if test="${configuracoes != null}" >
		<table class='tabelaForm'>
			<tr><td>&nbsp;</td></tr>
		</table>
		<table class='tabelaFormFundo' cellspacing='1'>
		   <tr class='linhaFundoTitulo'>
				<td>Propriedade</td>
				<td>Descrição</td>
				<td>Valor</td>
			</tr>
			<c:forEach var="configuracao" items="${configuracoes}">
				<c:if test="${b:isChaveConfiguracao(nomeModuloSelecionado,configuracao.key)}" >
				   <tr class='linhaFundo'>
						<td>${configuracao.key}</td>
						<td>${b:getDescricaoChaveConfiguracao(configuracoes,configuracao.key)}</td>
						<td><input type="text" size="30" name="valores" value="${configuracoes[configuracao.key]}" ></td>
						<input type="hidden" name="chaves" value="${configuracao.key}">
					</tr>
				</c:if>
			</c:forEach>
		</table>
		<table class='tabelaForm'>
			<tr><td>&nbsp;</td></tr>
			<tr class='linhaBotao'>
				<td><input type="button" value="Salvar" class="botao" onclick="submete('manterConfiguracao.action?method=salvar')"></td>
			</tr>
		</table>	
	</c:if>
</form>