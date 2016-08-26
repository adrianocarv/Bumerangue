<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b" %>
<%@ taglib uri="http://displaytag.sf.net/el" prefix="display"%>

<script type="text/javascript">window.onload=function(){focusField("nomeModuloSelecionado");}</script>

<form action="verificarCodigo.action?method=verificar" method="post" >

	<table class='tabelaFormFundo' cellspacing='1'>
		<tr class='linhaFundo'>
			<td width="30%">Módulo</td>
			<td>
				<select name="nomeModuloSelecionado" onchange="submete('verificarCodigo.action?method=alterarModuloSelecionado')"; >
					<option value="" />
					<c:forEach var="elemento" items="${S_C.usuarioLogado.nomesModulosAdmin}">
						<option value="${elemento}" ${elemento eq pesquisaObjetoBumerangueCriteria.nomeModulo ? "selected='selected'" : ""}>${elemento}</option> 
					</c:forEach>
		        </select>
			</td>
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
			<td>Separador de códigos<br>(Ex. \n\n -> 2 parágrafos entres os códigos)</td>
			<td><input type="text" name="separadorCodigos" value="${pesquisaObjetoBumerangueCriteria.separadorCodigos}" ></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Substituir caracteres nos códigos<br>( Ex. ;&/ * 22&33 O Código 22AB;001 será 32AB/001 )</td>
			<td><input type="text" size="70" name="fragmentoSubstituicao" value="${pesquisaObjetoBumerangueCriteria.fragmentoSubstituicao}" ></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Informe os códigos</td>
			<td><TEXTAREA cols="70" rows="10" name="serieCodigos">${pesquisaObjetoBumerangueCriteria.serieCodigos}</TEXTAREA></td>
		</tr>
	</table>
	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr class='linhaBotao'>
			<td><input type="button" value="Verificar" class="botao" onclick="submete('verificarCodigo.action?method=verificar')"></td>
		</tr>
	</table>
		
	<c:if test="${not empty S_C.resultadoVerificacaoCodigo.elementosVerificacaoCodigo}">
		<table class='tabelaForm'>
			<tr><td>&nbsp;</td></tr>
			<tr><td class="textoNegrito">Resultado</td></tr>
		</table>
		<table class='tabelaFormFundo' cellspacing='1'>
			<tr class='linhaFundo'>
				<td width="30%">Informados</td>
				<td>${S_C.resultadoVerificacaoCodigo.totalInformado}</td>
			</tr>
			<tr class='linhaFundo'>
				<td>Cadastrados</td>
				<td>${S_C.resultadoVerificacaoCodigo.totalCadastrado}</td>
			</tr>
			<tr class='linhaFundo'>
				<td>OKs</td>
				<td>${S_C.resultadoVerificacaoCodigo.totalOK}</td>
			</tr>
			<tr class='linhaFundo'>
				<td>OKs, porém emprestados</td>
				<td>${S_C.resultadoVerificacaoCodigo.totalOKEmprestado}</td>
			</tr>
			<tr class='linhaFundo'>
				<td>Divergentes</td>
				<td>${S_C.resultadoVerificacaoCodigo.totalDivergente}</td>
			</tr>
			<tr class='linhaFundo'>
				<td>Divergentes, porém emprestados</td>
				<td>${S_C.resultadoVerificacaoCodigo.totalDivergenteEmprestado}</td>
			</tr>
			<tr class='linhaFundo'>
				<td>Fora de ordem</td>
				<td>${S_C.resultadoVerificacaoCodigo.totalForaOrdem}</td>
			</tr>
		</table>
 	</c:if>
 	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
	</table>
	
	<display:table cellspacing='1' id="elementoVerificacaoCodigo" name="sessionScope.S_C.resultadoVerificacaoCodigo.elementosVerificacaoCodigo" requestURI="/verificarCodigo.action?paginacao=true" export="true" >
		<display:caption class="displayCaption" >${b:size(S_C.resultadoVerificacaoCodigo.elementosVerificacaoCodigo)} registros.</display:caption>
		<c:if test="${S_C.resultadoVerificacaoCodigo.elementosVerificacaoCodigo != null}" >
			<display:setProperty name="basic.msg.empty_list" value="${b:getMensagemListagemVazia()}" />
		</c:if>
		<display:column headerClass="linhaFundoTitulo" property="index" title="Seq." sortable="true" />
		<display:column headerClass="linhaFundoTitulo" property="objetoBumerangueCadastrado.codigo" title="Código cadastrado" sortable="true" />
		<display:column headerClass="linhaFundoTitulo" property="objetoBumerangueInformado.codigo" title="Código informado" sortable="true" />
		<c:if test="${!elementoVerificacaoCodigo.foraOrdem && (elementoVerificacaoCodigo.divergente || elementoVerificacaoCodigo.OKEmprestado)}">
			<display:column headerClass="linhaFundoTitulo" style='background-color:#FFFF66' property="resultado" title="Resultado" sortable="true" />
		</c:if>
		<c:if test="${elementoVerificacaoCodigo.foraOrdem}">
			<display:column headerClass="linhaFundoTitulo" style='background-color:#FFCC66' property="resultado" title="Resultado" sortable="true" />
		</c:if>
		<c:if test="${!elementoVerificacaoCodigo.foraOrdem && !elementoVerificacaoCodigo.divergente && !elementoVerificacaoCodigo.OKEmprestado}">
			<display:column headerClass="linhaFundoTitulo" property="resultado" title="Resultado" sortable="true" />
		</c:if>
		<c:if test="${S_C.resultadoVerificacaoCodigo.totalForaOrdem > 0}">
			<display:column headerClass="linhaFundoTitulo" property="posicaoCorretaFormatado" title="Posição correta (informados)" sortable="true" />
		</c:if>
		<display:column headerClass="linhaFundoTitulo" property="titulo" title="Título" sortable="true" />
		<display:column headerClass="linhaFundoTitulo" property="localizacaoFisica" title="Localização Física" sortable="true" />
	</display:table>

	<c:if test="${S_C.resultadoVerificacaoCodigo.totalForaOrdem > 0}">
		<table class='tabelaForm'>
			<tr><td>&nbsp;</td></tr>
			<tr class='linhaBotao'>
				<td><input type="button" value="Reordenar" class="botao" onclick="submete('verificarCodigo.action?method=reordenar')"></td>
			</tr>
		</table>
	</c:if>
</form>