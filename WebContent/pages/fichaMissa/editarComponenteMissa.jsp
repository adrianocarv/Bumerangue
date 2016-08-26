<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b"%>

<script type="text/javascript">window.onload=function(){focusField("textoLatim");}</script>

<form action="manterComponenteMissa.action?method=editarPre" method="post">

	<table class='tabelaForm'>
		<tr><td class="textoNegrito">${grupoComponenteMissa.nome}: ${grupoComponenteMissa.chave} - ${grupoComponenteMissa.descricao}</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td class="textoNegrito">${componenteMissa.nome}</td></tr>
	</table>

	<c:if test="${referenciaMissal == null}" >
		<table class='tabelaForm'>
			<tr><td>&nbsp;</td></tr>
		</table>
		<table class='tabelaFormFundo' cellspacing='1'>
			
			<!--  Se for Prefacio -->
			<c:if test="${componenteMissa.prefacio}" >
				<tr class='linhaFundo'>
					<td width="30%">Próprio</td>
					<td><input type="checkbox" name="proprio" value="true" ${componenteMissa.proprio == 'TRUE' ? "checked='checked'" : ""}></td>
				</tr>
				<tr class='linhaFundo'>
					<td>Compartilhado</td>
					<td><input type="checkbox" name="compartilhado" value="true" ${componenteMissa.compartilhado == 'TRUE' ? "checked='checked'" : ""}></td>
				</tr>
				<c:if test="${componenteMissa.descricaoPrefacioRequerido}">
					<tr class='linhaFundo'>
						<td>Descrição</td>
						<td><input type="text" size="70" id="descricao" name="descricao" value="${componenteMissa.descricao}"></td>
					</tr>
				</c:if>
				<c:if test="${componenteMissa.prefacioCompartilhadoRequerido}">
					<tr class='linhaFundo'>
						<td>Prefácio Compartilhado</td>
						<td>
							<select name="idPrefacioCompartilhado">
								<option />
								<c:forEach var="elemento" items="${prefaciosCompartilhados}">
									<option value="${elemento.id}" ${elemento.id eq componenteMissa.prefacioCompartilhado.id ? "selected='selected'" : ""}>${elemento.descricao}</option> 
								</c:forEach>
					        </select>
						</td>
					</tr>
				</c:if>
				<c:if test="${!componenteMissa.prefacioCompartilhadoRequerido}">
				   <tr class='linhaFundo'>
						<td width="30%">Texto em latim</td>
						<td><TEXTAREA cols="70" rows="3" name="textoLatim">${componenteMissa.textoLatim}</TEXTAREA></td>
					</tr>
				</c:if>
				<c:if test="${not empty componenteMissa.prefacioCompartilhado}">
				   <tr class='linhaFundo'>
						<td width="30%">Texto em latim</td>
						<td>${componenteMissa.textoLatimRelativo}</td>
					</tr>
				</c:if>
			</c:if>

			<!--  Se não for Prefacio -->
			<c:if test="${!componenteMissa.prefacio}" >
			   <tr class='linhaFundo'>
					<td width="30%">Texto em latim</td>
					<td><TEXTAREA cols="70" rows="3" name="textoLatim">${componenteMissa.textoLatim}</TEXTAREA></td>
				</tr>
			</c:if>

		</table>
	</c:if>

	<c:if test="${referenciaMissal != null}" >
		<table class='tabelaForm'>
			<tr><td>&nbsp;</td></tr>
			<tr><td class="textoNegrito">Referência no Missal</td></tr>
		</table>
		<table class='tabelaFormFundo' cellspacing='1'>
			<tr class='linhaFundo'>
				<td width="30%">Idioma / Edição</td>
				<td>
					<select name="idEdicaoIdioma" >
						<option value="" />
						<c:forEach var="elemento" items="${edicoesIdiomasMissais}">
							<option value="${elemento.id}" ${elemento.id eq referenciaMissal.edicaoIdioma.id ? "selected='selected'" : ""}>${elemento.descricao}</option> 
						</c:forEach>
			        </select>
				</td>
			</tr>
			<tr class='linhaFundo'>
				<td>Página</td>
				<td><input type="text" name="pagina" value="${referenciaMissal.pagina}" ></td>
			</tr>
			<tr class='linhaFundo'>
				<td>Texto</td>
				<td><TEXTAREA cols="70" rows="3" name="texto">${referenciaMissal.texto}</TEXTAREA></td>
			</tr>
			<tr class='linhaFundo'>
				<td>Endereço nas Escrituras</td>
				<td><input type="text" name="enderecoEscrituras" value="${referenciaMissal.enderecoEscrituras}" ></td>
			</tr>
		</table>
		<table class='tabelaForm'>
			<tr><td>&nbsp;</td></tr>
			<tr class='linhaBotao'>
				<c:if test="${!referenciaMissal.salvo}">
					<td><input type="button" value="Inserir" class="botao" onclick="submete('manterReferenciaMissal.action?method=inserir')"></td>
				</c:if>
				<c:if test="${referenciaMissal.salvo}">
					<td><input type="button" value="Salvar" class="botao" onclick="submete('manterReferenciaMissal.action?method=editar')"></td>
				</c:if>
				<td><input type="button" value="Excluir" class="botao" onclick="submete('manterReferenciaMissal.action?method=excluir')"></td>
				<td><input type="button" value="Cancelar" class="botao" onclick="submete('manterComponenteMissa.action?method=editarPre')"></td>
				<td><input type="hidden" name="idReferenciaMissal" value="${referenciaMissal.id}"></td>
			</tr>
		</table>
	</c:if>

	<c:if test="${componenteMissa.salvo}">
		<table class='tabelaForm'>
			<tr><td>&nbsp;</td></tr>
			<c:if test="${componenteMissa.prefacio && not empty componenteMissa.prefacioCompartilhado}">
				<tr><td>Referências nos missais</td></tr>
			</c:if>
			<c:if test="${!componenteMissa.prefacio || empty componenteMissa.prefacioCompartilhado}">
				<tr><td>Referências nos missais&nbsp;<input type="button" value="Nova" class="botao" onclick="submete('manterReferenciaMissal.action?method=editarPre')"></td></tr>
			</c:if>
		</table>
		<table class='tabelaFormFundo' cellspacing='1'>
			<tr class='linhaFundo'>
				<td>Idioma / Edição</td>
				<td>Página</td>
				<td>Texto</td>
				<td>Encereço nas Escrituras</td>
			</tr>
			<c:forEach var="elemento" items="${componenteMissa.referenciasMissaisRelativo}">
				<c:if test="${componenteMissa.prefacio && not empty componenteMissa.prefacioCompartilhado}">
				   <tr class='linhaFundo'>
				</c:if>
				<c:if test="${!componenteMissa.prefacio || empty componenteMissa.prefacioCompartilhado}">
				   <tr class='linhaFundo' onMouseOver="campoLinkOver(this)"; onMouseOut="campoLinkOut(this)"; style='cursor:hand' class='semLink' onclick="submete('manterReferenciaMissal.action?method=editarPre&idReferenciaMissal=${elemento.id}')"; >
				</c:if>
					<td>${elemento.edicaoIdioma.descricao}</td>
					<td>${elemento.pagina}</td>
					<td>${elemento.texto}</td>
					<td>${elemento.enderecoEscrituras}</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr class='linhaBotao'>
			<c:if test="${referenciaMissal == null}" >
				<c:if test="${!componenteMissa.salvo}">
					<td><input type="button" value="Inserir" class="botao" onclick="submete('manterComponenteMissa.action?method=inserir')"></td>
				</c:if>
				<c:if test="${componenteMissa.salvo}">
					<td><input type="button" value="Salvar" class="botao" onclick="submete('manterComponenteMissa.action?method=editar')"></td>
					<td><input type="button" value="Excluir" class="botao" onclick="submete('manterComponenteMissa.action?method=excluir')"></td>
				</c:if>
				<td><input type="button" value="Voltar" class="botao" onclick="submete('manterGrupoComponenteMissa.action?method=editarPre')"></td>
			</c:if>
			<td><input type="hidden" name="id" value="${id}"></td>
			<td><input type="hidden" name="tipo" value="${tipo}"></td>
			<td><input type="hidden" name="idComponente" value="${componenteMissa.id}"></td>
			<td><input type="hidden" name="tipoComponente" value="${tipoComponente}"></td>
		</tr>
	</table>

</form>