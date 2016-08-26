<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b" %>
<%@ taglib uri="http://displaytag.sf.net/el" prefix="display"%>

<script type="text/javascript">window.onload=function(){focusField("nomeModuloSelecionado");}</script>

<form action="pesquisarCodigo.action?method=pesquisar" method="post" >

	<table class='tabelaFormFundo' cellspacing='1'>
		<tr class='linhaFundo'>
			<td width="30%">Módulo</td>
			<td>
				<select name="nomeModuloSelecionado" onchange="submete('pesquisarCodigo.action?method=alterarModuloSelecionado')"; >
					<option value="" />
					<c:forEach var="elemento" items="${S_C.usuarioLogado.nomesModulosAdmin}">
						<option value="${elemento}" ${elemento eq pesquisaObjetoBumerangueCriteria.nomeModulo ? "selected='selected'" : ""}>${elemento}</option> 
					</c:forEach>
		        </select>
			</td>
		</tr>
		<tr class='linhaFundo'>
			<td>Fragmento do código que representa o sequêncial<br>(Ex. 3,6 -> 0004 do código AB-0004)</td>
			<td><input type="text" name="fragmentoSequencial" value="${pesquisaObjetoBumerangueCriteria.fragmentoSequencial}" ></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Código inicial</td>
			<td><input type="text" name="codigoIni" value="${pesquisaObjetoBumerangueCriteria.codigoIni}" ></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Código final</td>
			<td><input type="text" name="codigoFim" value="${pesquisaObjetoBumerangueCriteria.codigoFim}" ></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Fora de uso</td>
			<td>
				<select name="foraUso">
					<option />
					<option value="true" ${pesquisaObjetoBumerangueCriteria.foraUso == 'TRUE' ? "selected='selected'" : ""} >sim</option> 
					<option value="false" ${pesquisaObjetoBumerangueCriteria.foraUso == 'FALSE' ? "selected='selected'" : ""} >não</option> 
		        </select>
			</td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Modo</td>
			<td>
				<select name="modo">
					<option value="1" ${pesquisaObjetoBumerangueCriteria.modo eq 1 ? "selected='selected'" : ""} >Cadastrados no intervalo</option>
					<option value="2" ${pesquisaObjetoBumerangueCriteria.modo eq 2 ? "selected='selected'" : ""} >Não cadastrados no intervalo</option>
					<option value="0" ${pesquisaObjetoBumerangueCriteria.modo eq 0 ? "selected='selected'" : ""} >Todos</option>
		        </select>
			</td>
		</tr>		
	</table>
	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr class='linhaBotao'>
			<td><input type="button" value="Pesquisar" class="botao" onclick="submete('pesquisarCodigo.action?method=pesquisar')"></td>
		</tr>
	</table>
		
	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
	</table>
	
	<display:table cellspacing='1' id="objetoBumerangue" name="sessionScope.S_C.objetosBumerangue" requestURI="/pesquisarCodigo.action?paginacao=true" export="true" >
		<display:caption class="displayCaption" >${b:size(S_C.objetosBumerangue)} Objetos encontrados.</display:caption>
		<c:if test="${S_C.objetosBumerangue != null}" >
			<display:setProperty name="basic.msg.empty_list" value="${b:getMensagemListagemVazia()}" />
		</c:if>
		<display:column headerClass="linhaFundoTitulo" property="codigo" title="Código" sortable="true" />
		<display:column headerClass="linhaFundoTitulo" property="titulo" title="Título" sortable="true" />
		<display:column headerClass="linhaFundoTitulo" value="${empty objetoBumerangue.titulo ? 'Não' : ''}" title="Cadastrado no intervalo" sortable="true" />
		<display:column headerClass="linhaFundoTitulo" property="situacao" title="Situação" sortable="true" />
	</display:table>
</form>