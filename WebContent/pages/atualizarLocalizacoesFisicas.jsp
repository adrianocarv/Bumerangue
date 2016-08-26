<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b"%>
<%@ taglib uri="http://displaytag.sf.net/el" prefix="display"%>

<form action="manterLocalizacaoFisica.action?method=atualizarPre" method="post">
	<table class='tabelaFormFundo' cellspacing='1'>
		<tr class='linhaFundo'>
			<td>Módulo</td>
			<td>
				<select name="nomeModuloSelecionado" onchange="submete('manterLocalizacaoFisica.action?method=atualizarPre')"; >
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

	<c:if test="${localizacaoFisicaInfo != null}" >
		<table class='tabelaForm'>
			<tr><td class="textoNegrito">Informações</td></tr>
		</table>
		<table class='tabelaFormFundo' cellspacing='1'>
			<tr class='linhaFundo'>
				<td width="50%" >Campos de ordenação</td>
				<td>${localizacaoFisicaInfo.camposOrdenacao}</td>
			</tr>
			<tr class='linhaFundo'>
				<td>Grupo de compartimentos</td>
				<td>${localizacaoFisicaInfo.grupoCompartimentos}</td>
			</tr>
			<tr class='linhaFundo'>
				<td>Total de compartimentos em uso</td>
				<td>${localizacaoFisicaInfo.totalCompartimentos}</td>
			</tr>
			<tr class='linhaFundo'>
				<td>Total de objetos em uso</td>
				<td>${localizacaoFisicaInfo.totalObjetosBumerangue}</td>
			</tr>
			<tr class='linhaFundo'>
				<td>Objetos sem localização</td>
				<td>${localizacaoFisicaInfo.totalObjetosBumerangueSemLocalizacao}</td>
			</tr>
			<tr class='linhaFundo'>
				<td>Objetos sem espaço nos compartimentos</td>
				<td>${localizacaoFisicaInfo.totalObjetosBumerangueSemEspacoCompartimentos}</td>
			</tr>
			<tr class='linhaFundo'>
				<td>Espaços disponíveis</td>
				<td>${localizacaoFisicaInfo.totalEspacosDisponiviesCompartimentos}</td>
			</tr>
			<tr class='linhaFundo'>
				<td>Objetos atualizados</td>
				<td>${localizacaoFisicaInfo.totalObjetosBumerangueAtualizados}</td>
			</tr>
			<tr class='linhaFundo'>
				<td>Data da última atualização</td>
				<td>${localizacaoFisicaInfo.dataUltimaAtualizacao}</td>
			</tr>
		</table>
		<table class='tabelaForm'>
			<tr><td>&nbsp;</td></tr>
			<tr class='linhaBotao'>
				<td><input type="button" value="Atualizar" class="botao" onclick="submete('manterLocalizacaoFisica.action?method=atualizar')"></td>
			</tr>
			<tr><td>&nbsp;</td></tr>
		</table>
	</c:if>

	<display:table cellspacing='1' id="objetoBumerangue" name="sessionScope.S_C.objetosBumerangue" requestURI="/manterLocalizacaoFisica.action?paginacao=true" export="true" pagesize="${b:getTamanhoPaginaListagem()}" >
		<display:caption class="displayCaption" >Objetos atualizados</display:caption>
		<display:column headerClass="linhaFundoTitulo" property="codigo" title="Código" sortable="true" />
		<display:column headerClass="linhaFundoTitulo" property="titulo" title="Título" sortable="true" />
		<display:column headerClass="linhaFundoTitulo" property="localizacaoFisica" title="Localização física" sortable="true" />
		<display:column headerClass="linhaFundoTitulo" property="localizacaoFisicaAnterior" title="Localização física anterior" sortable="true" />
	</display:table>

	<c:if test="${not empty localizacaoFisicaInfo.objetosBumerangueSemEspacoCompartimentos}" >
		<table class='tabelaForm'>
			<tr><td>&nbsp;</td></tr>
			<tr><td class="textoNegrito">Objetos sem espaço nos compartimentos</td></tr>
		</table>
		<table class='tabelaFormFundo' cellspacing='1'>
			<tr class='linhaFundoTituloSemNegrito'><td colspan="2">${b:size(localizacaoFisicaInfo.objetosBumerangueSemEspacoCompartimentos)} registros encontrados.</td></tr>
			<tr class='linhaFundoTitulo'>
				<td>Código</td>
				<td>Título</td>
			</tr>
			<c:forEach var="objetoBumerangue" items="${localizacaoFisicaInfo.objetosBumerangueSemEspacoCompartimentos}">
				<tr class='linhaFundo'>
					<td>${objetoBumerangue.codigo}</td>
					<td>${objetoBumerangue.titulo}</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>

</form>