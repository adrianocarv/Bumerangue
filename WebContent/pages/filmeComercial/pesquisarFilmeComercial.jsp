<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b"%>
<%@ taglib uri="http://displaytag.sf.net/el" prefix="display" %>

<form action="manterFilmeComercial.action?method=pesquisar" method="post">

	<c:if test="${empty escondeFiltroPesquisa}" >
		<script type="text/javascript">window.onload=function(){focusField("titulo");}</script>
		<div id="filtroPesquisa" style="display:block;">
	</c:if>
	<c:if test="${not empty escondeFiltroPesquisa}" >
		<div id="filtroPesquisa" style="display:none;">
	</c:if>
	<table class='tabelaFormFundo' cellspacing='1'>
		<tr class='linhaFundo'>
			<td width="30%">Novidades (mais recentes inclu�dos)</td>
			<td>
				<select name="numeroMaisRecentes">
					<option />
					<c:if test="${pesquisaFilmeComercialCriteria.numeroMaisRecentes eq '5'}" >  
						<option value="5" selected="selected">5 �ltimos</option>
					</c:if>
					<c:if test="${pesquisaFilmeComercialCriteria.numeroMaisRecentes ne '5'}" >  
						<option value="5" >5 �ltimos</option>
					</c:if>
					<c:if test="${pesquisaFilmeComercialCriteria.numeroMaisRecentes eq '10'}" >  
						<option value="10" selected="selected">10 �ltimos</option>
					</c:if>
					<c:if test="${pesquisaFilmeComercialCriteria.numeroMaisRecentes ne '10'}" >  
						<option value="10" >10 �ltimos</option>
					</c:if>
					<c:if test="${pesquisaFilmeComercialCriteria.numeroMaisRecentes eq '20'}" >  
						<option value="20" selected="selected">20 �ltimos</option>
					</c:if>
					<c:if test="${pesquisaFilmeComercialCriteria.numeroMaisRecentes ne '20'}" >  
						<option value="20" >20 �ltimos</option>
					</c:if>
					<c:if test="${pesquisaFilmeComercialCriteria.numeroMaisRecentes eq '30'}" >  
						<option value="30" selected="selected">30 �ltimos</option>
					</c:if>
					<c:if test="${pesquisaFilmeComercialCriteria.numeroMaisRecentes ne '30'}" >  
						<option value="30" >30 �ltimos</option>
					</c:if>
		        </select>
			</td>
		</tr>
		<tr class='linhaFundo'>
			<td>T�tulo</td>
			<td><input type="text" size="70" name="titulo" id="titulo" value="${pesquisaFilmeComercialCriteria.titulo}" ></td>
		</tr>
		<tr class='linhaFundo'>
			<td>P�blico</td>
			<td>
				<select name="idPublico">
					<option />
					<c:forEach var="elemento" items="${publicos}">
						<c:if test="${elemento.id eq pesquisaFilmeComercialCriteria.publico}" >  
							<option value="${elemento.id}" selected="selected">${elemento.descricao}</option> 
						</c:if>
						<c:if test="${elemento.id ne pesquisaFilmeComercialCriteria.publico}" >
							<option value="${elemento.id}" >${elemento.descricao}</option> 
						</c:if>
					</c:forEach>
		        </select>
			</td>
		</tr>
		<tr class='linhaFundo'>
			<td>Dura��o de (minutos)</td>
			<td><input type="text" maxlength="3" name="duracaoMinutosIni" onkeypress="somenteNumeros()" value="${pesquisaFilmeComercialCriteria.duracaoMinutosIni}"></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Dura��o at� (minutos)</td>
			<td><input type="text" maxlength="3" name="duracaoMinutosFim" onkeypress="somenteNumeros()" value="${pesquisaFilmeComercialCriteria.duracaoMinutosFim}"></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Ano de</td>
			<td><input type="text" maxlength="4" name="anoIni" onkeypress="somenteNumeros()" value="${pesquisaFilmeComercialCriteria.anoIni}"></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Ano at�</td>
			<td><input type="text" maxlength="4" name="anoFim" onkeypress="somenteNumeros()" value="${pesquisaFilmeComercialCriteria.anoFim}"></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Categoria</td>
			<td>
				<select name="idCategoria">
					<option />
					<c:forEach var="elemento" items="${categorias}">
						<c:if test="${elemento.id eq pesquisaFilmeComercialCriteria.categoria}" >  
							<option value="${elemento.id}" selected="selected">${elemento.descricao}</option> 
						</c:if>
						<c:if test="${elemento.id ne pesquisaFilmeComercialCriteria.categoria}" >
							<option value="${elemento.id}" >${elemento.descricao}</option> 
						</c:if>
					</c:forEach>
		        </select>
			</td>
		</tr>
		<tr class='linhaFundo'>
			<td>Diretor</td>
			<td><input type="text" size="70" name="diretor" value="${pesquisaFilmeComercialCriteria.diretor}" ></td>
		</tr>
		<tr class='linhaFundo'>
			<td>Atores / Observa��es</td>
			<td><input type="text" size="70" name="atoresObservacoes" value="${pesquisaFilmeComercialCriteria.atoresObservacoes}" ></td>
		</tr>
		<c:if test="${b:isUserInRole(S_C.usuarioLogado,'7-9')}" >
		   	<tr class='linhaFundo'>
				<td>Dispon�vel para download</td>
				<c:if test="${pesquisaFilmeComercialCriteria.disponivelDownload}" >
					<td><input type="checkbox" name="disponivelDownload" value="true" checked="checked"></td>
				</c:if>
				<c:if test="${!pesquisaFilmeComercialCriteria.disponivelDownload}" >
					<td><input type="checkbox" name="disponivelDownload" value="true"></td>
				</c:if>
			</tr>
		</c:if>
	</table>
	</div>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr class='linhaBotao'>
			<td><input type="submit" value="Pesquisar" class="botao"></td>
			<c:if test="${not empty escondeFiltroPesquisa}" >
				<td><input type="button" value="Nova pesquisa" class="botao" onclick="exibeDiv('filtroPesquisa',true)"></td>
			</c:if>
			<c:if test="${b:isUserInRole(S_C.usuarioLogado,'7')}" >
				<td><input type="button" value="Novo" class="botao" onclick="submete('manterFilmeComercial.action?method=editarPre')"></td>
			</c:if>
		</tr>
		<tr><td>&nbsp;</td></tr>
	</table>

	<display:table cellspacing='1' id="filmeComercial" name="sessionScope.S_C.objetosBumerangue" requestURI="/manterFilmeComercial.action?paginacao=false" export="true" pagesize="${b:getTamanhoPaginaListagem()}" decorator="br.org.ceu.bumerangue.util.DisplaytagDecorator">
		<c:if test="${S_C.objetosBumerangue != null}" >
			<display:setProperty name="basic.msg.empty_list" value="${b:getMensagemListagemVazia()}" />
		</c:if>
		<display:column headerClass="linhaFundoTitulo" property="titulo" title="T�tulo" sortable="true" url="/manterFilmeComercial.action?method=detalhar" paramId="id" paramProperty="id" />
		<display:column headerClass="linhaFundoTitulo" property="tituloOriginal" title="T�tulo original" sortable="true" />
		<display:column headerClass="linhaFundoTitulo" property="publico.descricao" title="P�blico" />
		<display:column headerClass="linhaFundoTitulo" property="duracaoMinutos" title="Dura��o"  />
		<display:column headerClass="linhaFundoTitulo" property="ano" title="Ano" />
		<display:column headerClass="linhaFundoTitulo" property="categoria.descricao" title="Categoria" />
		<display:column headerClass="linhaFundoTitulo" property="diretor" title="Diretor" />
		<display:column headerClass="linhaFundoTitulo" property="atoresObservacoes" title="Atores / Observa��es" />
		<display:column headerClass="linhaFundoTitulo" property="linkSinopseDecorator" title="Link da sinopse" />
	</display:table>

	<table class='tabelaForm'>
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;Rela��o de filmes cinematogr�ficos que se distingue simultaneamente pelo crit�rio da qualidade art�stica e dos conte�dos limpos: ou seja, s� inclui os filmes rec�m-lan�ados mais bem cotados que, com garantia, n�o cont�m cenas inconvenientes.</td></tr>
		<tr><td>&nbsp;</td></tr>
	</table>

	<!-- Mural -->
	<c:if test="${b:isUserInRole(S_C.usuarioLogado,'7-9')}" >
		<table class='tabelaForm'>
			<tr><td class="linhaFaixa">Mural</td></tr>
			<tr><td>&nbsp;</td></tr>
			<tr><td class="linhaFundoTitulo">
				<b>NOVIDADE IMPORTANTE</b>
			</td></tr>
			<tr><td class="linhaFundo">
				</br>A partir de agora o Bumerangue manter� para download apenas os filmes inclu�dos nos �ltimos dois anos.
				</br>A primeira exclus�o de filmes ser� realizada no <u><b>final de junho de 2017</b></u>, incluindo os seguintes t�tulos:
				<ul>
				  <li>127 Horas</li>
				  <li>O escritor fantasma</li>
				  <li>O garoto da biciclete</li>
				  <li>Grandes Olhos</li>
				  <li>Jane Eyre</li>
				  <li>Maktub</li>
				  <li>No</li>
				  <li>Noe</li>
				  <li>Sherlok 2</li>
				  <li>Win win</li>
				</ul>
			</td></tr>
		</table>
	</c:if>

</form>
