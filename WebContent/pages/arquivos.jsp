<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b" %>
<%@ taglib uri="http://displaytag.sf.net/el" prefix="display"%>

<form action="manterArquivo.action?method=listar" method="post" enctype="multipart/form-data">

	<c:if test="${b:isUserInRole(S_C.usuarioLogado,'1,4,7,10')}" >
		<table class='tabelaFormFundo' cellspacing='1'>
			<tr class='linhaFundo'>
				<td width="30%">Módulo</td>
				<td>
					<select name="nomeModuloSelecionado" >
						<option value="" />
						<c:forEach var="elemento" items="${S_C.usuarioLogado.nomesModulosAdmin}">
							<option value="${elemento}" ${elemento eq nomeModuloSelecionado ? "selected='selected'" : ""}>${elemento}</option> 
						</c:forEach>
			        </select>
				</td>
			</tr>
			<tr class='linhaFundo'>
				<td>Acessível para a permissão</td>
				<td>
					<select name="codigoTipoPermissao" >
						<option value="" />
						<c:forEach var="elemento" items="${tipoPermissoes}">
							<option value="${elemento.codigo}" ${elemento.codigo eq codigoTipoPermissao ? "selected='selected'" : ""}>${elemento.descricao}</option> 
						</c:forEach>
			        </select>
				</td>
			</tr>
			<tr class='linhaFundo'>
				<td>Nome para o arquivo</td>
				<td><input type="text" size="70" name="nomeArquivo" value="${nomeArquivo}" ></td>
			</tr>
			<tr class='linhaFundo'>
				<td>&nbsp;</td>
				<td><input type="file" size="70" name="uploadFile" ></td>
			</tr>
		</table>
		<table class='tabelaForm'>
			<tr><td>&nbsp;</td></tr>
			<tr class='linhaBotao'>
				<td><input type="button" value="Enviar" class="botao" onclick="submete('manterArquivo.action?method=upload')"></td>
			</tr>
		</table>
		
	</c:if>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
	</table>
	
	<c:if test="${empty S_C.arquivos}" >
		<table class='tabelaForm'>
			<tr class="textoNegrito"><td>Nenhum arquivo disponível.</td></tr>
		</table>
	</c:if>
	<c:if test="${not empty S_C.arquivos}" >
		<display:table cellspacing='1' id="arquivo" name="sessionScope.S_C.arquivos" requestURI="/manterArquivo.action?paginacao=true" decorator="br.org.ceu.bumerangue.util.DisplaytagDecorator">
			<display:caption class="displayCaption" >${b:size(S_C.arquivos)} arquivos disponíveis.</display:caption>
			<display:column headerClass="linhaFundoTitulo" property="caminhoRelativoDecorator" title="Nome" sortable="true" />
			<display:column headerClass="linhaFundoTitulo" property="ultimaModificacao" title="Modificado em" sortable="true" />
			<display:column headerClass="linhaFundoTitulo" property="tamanhoFormatado" title="Tamanho (KB)" sortable="true" sortProperty="tamanho" />
			<display:column headerClass="linhaFundoTitulo" property="acessivelPara" title="Acessível para" sortable="true" />
			<c:if test="${b:isUserInRole(S_C.usuarioLogado,'1,4,7,10')}" >
				<display:column headerClass="linhaFundoTitulo" property="excluirArquivoDecorator" title=" "/>
			</c:if>
		</display:table>
	</c:if>

</form>