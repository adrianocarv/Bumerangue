<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b"%>
<%@ taglib uri="http://displaytag.sf.net/el" prefix="display"%>

<script type="text/javascript">window.onload=function(){focusField("codigo");}</script>

<form action="manterVideo.action?method=pesquisar" method="post">
	<table class='tabelaFormFundo' cellspacing='1'>
	   <tr class='linhaFundo'>
			<td width="30%">Código</td>
			<td><input type="text" maxlength="5" id="codigo" name="codigo" value="${pesquisaVideoCriteria.codigo}" onkeypress="somenteNumeros()"></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Categoria</td>
			<td>
				<select name="idCategoria">
					<option />
					<c:forEach var="elemento" items="${categorias}">
						<c:if test="${elemento.id eq pesquisaVideoCriteria.categoria}" >  
							<option value="${elemento.id}" selected="selected">${elemento.descricao}</option> 
						</c:if>
						<c:if test="${elemento.id ne pesquisaVideoCriteria.categoria}" >
							<option value="${elemento.id}" >${elemento.descricao}</option> 
						</c:if>
					</c:forEach>
		        </select>
			</td>
		</tr>
		<tr class='linhaFundo'>
			<td>Código da caixa</td>
			<td><input type="text" name="codigoCaixa" value="${pesquisaVideoCriteria.codigoCaixa}"></td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Título</td>
			<td><input type="text" size="70" name="titulo" value="${pesquisaVideoCriteria.titulo}"></td>
		</tr>															
	   <tr class='linhaFundo'>
			<td>Localidade</td>
			<td><input type="text" size="70" name="localidade" value="${pesquisaVideoCriteria.localidade}"></td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Data a partir de</td>
			<td><input type="text" maxlength="10" name="dataIni" value="${pesquisaVideoCriteria.dataIni}" onkeypress="fmtKeyMascara('DD/MM/YYYY',dataIni,dataIni,this)" ></td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Data até</td>
			<td><input type="text" maxlength="10" name="dataFim" value="${pesquisaVideoCriteria.dataFim}" onkeypress="fmtKeyMascara('DD/MM/YYYY',dataFim,dataFim,this)" ></td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Legendado</td>
			<c:if test="${pesquisaVideoCriteria.legendado}" >
				<td><input type="checkbox" name="legendado" value="true" checked="checked"></td>
			</c:if>
			<c:if test="${!pesquisaVideoCriteria.legendado}" >
				<td><input type="checkbox" name="legendado" value="true"></td>
			</c:if>
		</tr>
	   <tr class='linhaFundo'>
			<td>Mídia</td>
			<td>
				<select name="idMidia">
					<c:if test="${b:isUserInRole(S_C.usuarioLogado,'1')}" >
						<option />
					</c:if>
					<c:forEach var="elemento" items="${midias}">
						<c:if test="${b:isUserInRole(S_C.usuarioLogado,'1') || elemento.descricao eq 'DVD'}" >
							<c:if test="${elemento.id eq pesquisaVideoCriteria.midia}" >  
								<option value="${elemento.id}"  selected="selected">${elemento.descricao}</option>
							</c:if>
							<c:if test="${elemento.id ne pesquisaVideoCriteria.midia}" >
								<option value="${elemento.id}">${elemento.descricao}</option> 
							</c:if>
						</c:if>
					</c:forEach>
		        </select>
			</td>
		</tr>		
	   <tr class='linhaFundo'>
			<td>Palavras-chaves</td>
			<td><input type="text" size="70" name="palavrasChaves" value="${pesquisaVideoCriteria.palavrasChaves}"></td>
		</tr>		
	   <tr class='linhaFundo'>
			<td>Observações gerais</td>
			<td><input type="text" size="70" name="observacoesGerais" value="${pesquisaVideoCriteria.observacoesGerais}"></td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Situação</td>
			<td>
				<select name="idSituacao">
					<option />
					<c:if test="${pesquisaVideoCriteria.situacao eq '1'}" >  
						<option value="1" selected="selected">Disponível</option>
					</c:if>
					<c:if test="${pesquisaVideoCriteria.situacao ne '1'}" >  
						<option value="1" >Disponível</option>
					</c:if>
					<c:if test="${pesquisaVideoCriteria.situacao eq '2'}" >  
						<option value="2" selected="selected">Reservado</option>
					</c:if>
					<c:if test="${pesquisaVideoCriteria.situacao ne '2'}" >  
						<option value="2" >Reservado</option>
					</c:if>
					<c:if test="${pesquisaVideoCriteria.situacao eq '3'}" >  
						<option value="3" selected="selected">Emprestado</option>
					</c:if>
					<c:if test="${pesquisaVideoCriteria.situacao ne '3'}" >  
						<option value="3" >Emprestado</option>
					</c:if>
					<c:if test="${pesquisaVideoCriteria.situacao eq '0'}" >  
						<option value="0" selected="selected">Todos</option>
					</c:if>
					<c:if test="${pesquisaVideoCriteria.situacao ne '0'}" >  
						<option value="0" >Todos</option>
					</c:if>
		        </select>
			</td>
		</tr>		
	   <!-- admin -->
		<c:if test="${b:isUserInRole(S_C.usuarioLogado,'1')}" >
		   <tr class='linhaFundo'>
				<td>Localização física</td>
				<td><input type="text" size="70" name="localizacaoFisica" value="${pesquisaVideoCriteria.localizacaoFisica}"></td>
			</tr>		
		</c:if>
	   <tr class='linhaFundo'>
			<td>Emprestado/Reservado para</td>
			<td>
				<select name="idUsuarioEmprestimo">
					<option />
					<c:forEach var="elemento" items="${centros}">
						<c:if test="${elemento.id eq pesquisaVideoCriteria.usuarioEmprestimo}" >  
							<option value="${elemento.id}" selected="selected">${elemento.nomeFormatado}</option> 
						</c:if>
						<c:if test="${elemento.id ne pesquisaVideoCriteria.usuarioEmprestimo}" >
							<option value="${elemento.id}" >${elemento.nomeFormatado}</option> 
						</c:if>
					</c:forEach>
		        </select>
			</td>
		</tr>		
	</table>
	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr class='linhaBotao'>
			<td><input type="submit" value="Pesquisar" class="botao"></td>
			<c:if test="${b:isUserInRole(S_C.usuarioLogado,'1')}" >
				<td><input type="button" value="Novo" class="botao" onclick="submete('manterVideo.action?method=editarPre')"></td>
				<td><input type="button" value="Fichas de empréstimo novas" class="botao" onclick="submete('relatorioVideo.action?method=emitirFichaEmprestimo&nomeModulo=VIDEO')"></td>
			</c:if>
		</tr>
		<tr><td>&nbsp;</td></tr>
	</table>

	<display:table cellspacing='1' id="video" name="sessionScope.S_C.objetosBumerangue" requestURI="/manterVideo.action?paginacao=true" export="true" pagesize="${b:getTamanhoPaginaListagem()}" >
		<c:if test="${S_C.objetosBumerangue != null}" >
			<display:setProperty name="basic.msg.empty_list" value="${b:getMensagemListagemVazia()}" />
		</c:if>
		<display:column headerClass="linhaFundoTitulo" property="codigo" title="Código" sortable="true" url="/manterVideo.action?method=detalhar" paramId="id" paramProperty="id" />
		<display:column headerClass="linhaFundoTitulo" property="titulo" title="Título" sortable="true" />
		<display:column headerClass="linhaFundoTitulo" property="localidade" title="Localidade" sortable="true" />
		<display:column headerClass="linhaFundoTitulo" value="${b:getFormatado(video.data)}" title="Data" />
		<display:column headerClass="linhaFundoTitulo" property="publico" title="Público" sortable="true" />
		<display:column headerClass="linhaFundoTitulo" property="duracaoMinutos" title="Duração" sortable="true" />
		<display:column headerClass="linhaFundoTitulo" property="midia.descricao" title="Mídia" sortable="true" />
		<display:column headerClass="linhaFundoTitulo" property="situacao" title="Situação" sortable="true" />
		<!-- admin -->
		<c:if test="${b:isUserInRole(S_C.usuarioLogado,'1')}" >
			<display:column headerClass="linhaFundoTitulo" property="codigoCaixa" title="Código da Caixa" sortable="true" />
			<display:column headerClass="linhaFundoTitulo" property="localizacaoFisica" title="Loc. física" sortable="true" />
			<display:column headerClass="linhaFundoTitulo" property="localizacaoFisicaAnterior" title="Anterior" sortable="true" />
		</c:if>
	</display:table>

</form>