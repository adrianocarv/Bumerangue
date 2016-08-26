<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b"%>

<script type="text/javascript">window.onload=function(){focusField("chave");}</script>

<form action="manterGrupoComponenteMissa.action?method=editarPre" method="post">

	<table class='tabelaForm'>
		<tr><td class="textoNegrito">${grupoComponenteMissa.nome}</td></tr>
	</table>
	<table class='tabelaFormFundo' cellspacing='1'>
	   <tr class='linhaFundo'>
			<td width="30%">Chave</td>
			<td><input type="text" size="70" id="chave" name="chave" value="${grupoComponenteMissa.chave}"></td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Descrição</td>
			<td>${grupoComponenteMissa.descricao}</td>
		</tr>
		<c:if test="${grupoComponenteMissa.campoChaveDescricaoChaveRequerido}">
			<tr class='linhaFundo'>
				<td>Descrição da Chave</td>
				<td><input type="text" size="70" id="descricaoChave" name="descricaoChave" value="${grupoComponenteMissa.descricaoChave}"></td>
			</tr>
		</c:if>
		<c:if test="${grupoComponenteMissa.campoChaveFestaMovelRequerido}">
			<tr class='linhaFundo'>
				<td>Festa Móvel</td>
				<td>
					<select name="idFestaMovel">
						<option />
						<c:forEach var="elemento" items="${festasMoveis}">
							<option value="${elemento.id}" ${elemento.id eq grupoComponenteMissa.festaMovel.id ? "selected='selected'" : ""}>${elemento.descricao}</option> 
						</c:forEach>
			        </select>
				</td>
			</tr>
		</c:if>
		<c:if test="${grupoComponenteMissa.campoChaveOracaoVotivaRequerido}">
			<tr class='linhaFundo'>
				<td>Oração Votiva</td>
				<td>
					<select name="idOracaoVotiva">
						<option />
						<c:forEach var="elemento" items="${oracoesVotivas}">
							<option value="${elemento.id}" ${elemento.id eq grupoComponenteMissa.oracaoVotiva.id ? "selected='selected'" : ""}>${elemento.descricao}</option> 
						</c:forEach>
			        </select>
				</td>
			</tr>
		</c:if>
	</table>

	<c:if test="${grupoComponenteMissa.salvo}">
		<table class='tabelaForm'>
			<tr><td>&nbsp;</td></tr>
		   <tr><td>Cadastrar por linha de comando</td></tr>
		   <tr><td><input type="text" size="125" id="linhaComandoCadastro" name="linhaComandoCadastro" value="${grupoComponenteMissa.linhaComandoCadastro}"></td></tr>
		</table>
		<table class='tabelaForm'>
			<tr><td>&nbsp;</td></tr>
			<c:if test="${empty grupoComponenteMissa.oracaoVotiva}">
				<tr><td><input type="button" value="Editar" class="botao" onclick="submete('manterComponenteMissa.action?method=editarPre&idComponente=${grupoComponenteMissa.introito.id}&tipoComponente=6')"></td></tr>
			</c:if>
		</table>
		<table class='tabelaFormFundo' cellspacing='1'>
			<tr class='linhaFundoTitulo'>
				<td>Intróito - Páginas nos missais</td>
			</tr>
		   <tr class='linhaFundo'>
				<td>${grupoComponenteMissa.introitoRelativo.paginasMissais}</td>
			</tr>
		</table>
	
		<table class='tabelaForm'>
			<tr><td>&nbsp;</td></tr>
			<c:if test="${empty grupoComponenteMissa.oracaoVotiva}">
				<tr><td><input type="button" value="Editar" class="botao" onclick="submete('manterComponenteMissa.action?method=editarPre&idComponente=${grupoComponenteMissa.prefacio.id}&tipoComponente=7')"></td></tr>
			</c:if>
		</table>
		<table class='tabelaFormFundo' cellspacing='1'>
			<tr class='linhaFundoTitulo'>
				<td>Prefácio: ${grupoComponenteMissa.prefacioRelativo.descricao} - Páginas nos missais</td>
			</tr>
		   <tr class='linhaFundo'>
				<td>${grupoComponenteMissa.prefacioRelativo.paginasMissais}</td>
			</tr>
		</table>

		<table class='tabelaForm'>
			<tr><td>&nbsp;</td></tr>
			<c:if test="${empty grupoComponenteMissa.oracaoVotiva}">
				<tr><td><input type="button" value="Editar" class="botao" onclick="submete('manterComponenteMissa.action?method=editarPre&idComponente=${grupoComponenteMissa.antifonaComunhao.id}&tipoComponente=8')"></td></tr>
			</c:if>
		</table>
		<table class='tabelaFormFundo' cellspacing='1'>
			<tr class='linhaFundoTitulo'>
				<td>Antífona de Comunhão - Páginas nos missais</td>
			</tr>
		   <tr class='linhaFundo'>
				<td>${grupoComponenteMissa.antifonaComunhaoRelativo.paginasMissais}</td>
			</tr>
		</table>
	</c:if>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr class='linhaBotao'>
			<c:if test="${!grupoComponenteMissa.salvo}">
				<td><input type="button" value="Inserir" class="botao" onclick="submete('manterGrupoComponenteMissa.action?method=inserir')"></td>
			</c:if>
			<c:if test="${grupoComponenteMissa.salvo}">
				<td><input type="button" value="Validar" class="botao" onclick="submete('manterGrupoComponenteMissa.action?method=validar')"></td>
				<td><input type="button" value="Salvar" class="botao" onclick="submete('manterGrupoComponenteMissa.action?method=editar')"></td>
			</c:if>
			<td><input type="button" value="Excluir" class="botao" onclick="submete('manterGrupoComponenteMissa.action?method=excluir')"></td>
			<td><input type="button" value="Voltar" class="botao" onclick="submete('manterGrupoComponenteMissa.action?method=pesquisarPre')"></td>
			<td><input type="hidden" name="id" value="${grupoComponenteMissa.id}"></td>
			<td><input type="hidden" name="tipo" value="${tipo}"></td>
		</tr>
	</table>

</form>