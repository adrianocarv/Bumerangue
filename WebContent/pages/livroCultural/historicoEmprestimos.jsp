<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b"%>
<%@ taglib uri="http://displaytag.sf.net/el" prefix="display"%>

<script type="text/javascript">window.onload=function(){focusField("codigo");}</script>

<form action="relatorioLivroCultural.action?method=pesquisarHistoricoEmprestimos" method="post">
	<table class='tabelaFormFundo' cellspacing='1'>
	   <tr class='linhaFundo'>
			<td width="30%">C�digo</td>
			<td><input type="text" maxlength="5" id="codigo" name="codigo" value="${pesquisaHistoricoLivroCulturalCriteria.codigo}" onkeypress="somenteNumeros()"></td>
		</tr>

	   <tr class='linhaFundo'>
			<td>Autor</td>
			<td><input type="text" maxlength="10" name="autor" value="${pesquisaHistoricoLivroCulturalCriteria.autor}"></td>
		</tr>
	   						
	   <tr class='linhaFundo'>
			<td>Data do empr�stimo a partir de</td>
			<td><input type="text" maxlength="10" name="dataEmprestimoIni" value="${pesquisaHistoricoLivroCulturalCriteria.dataEmprestimoIni}" onkeypress="fmtKeyMascara('DD/MM/YYYY',dataEmprestimoIni,dataEmprestimoIni,this)" ></td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Data do empr�stimo at�</td>
			<td><input type="text" maxlength="10" name="dataEmprestimoFim" value="${pesquisaHistoricoLivroCulturalCriteria.dataEmprestimoFim}" onkeypress="fmtKeyMascara('DD/MM/YYYY',dataEmprestimoFim,dataEmprestimoFim,this)" ></td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Observa��es do empr�stimo</td>
			<td><input type="text" size="70" name="observacoesEmprestimo" value="${pesquisaHistoricoLivroCulturalCriteria.observacoesEmprestimo}" ></td>
		</tr>															
	   <tr class='linhaFundo'>
			<td>Data da devolu��o a partir de</td>
			<td><input type="text" maxlength="10" name="dataDevolucaoIni" value="${pesquisaHistoricoLivroCulturalCriteria.dataDevolucaoIni}" onkeypress="fmtKeyMascara('DD/MM/YYYY',dataDevolucaoIni,dataDevolucaoIni,this)" ></td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Data da devolu��o at�</td>
			<td><input type="text" maxlength="10" name="dataDevolucaoFim" value="${pesquisaHistoricoLivroCulturalCriteria.dataDevolucaoFim}" onkeypress="fmtKeyMascara('DD/MM/YYYY',dataDevolucaoFim,dataDevolucaoFim,this)" ></td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Observa��es da devolu��o</td>
			<td><input type="text" size="70" name="observacoesDevolucao" value="${pesquisaHistoricoLivroCulturalCriteria.observacoesDevolucao}" ></td>
		</tr>															
	   <tr class='linhaFundo'>
			<td>Situa��o</td>
			<td>
				<select name="idSituacao">
					<option />
					<c:if test="${pesquisaHistoricoLivroCulturalCriteria.situacao eq '1'}" >  
						<option value="1" selected="selected">Devolu��o</option>
					</c:if>
					<c:if test="${pesquisaHistoricoLivroCulturalCriteria.situacao ne '1'}" >  
						<option value="1" >Devolu��o</option>
					</c:if>

					<c:if test="${pesquisaHistoricoLivroCulturalCriteria.situacao eq '3'}" >  
						<option value="3" selected="selected">Empr�stimo</option>
					</c:if>
					<c:if test="${pesquisaHistoricoLivroCulturalCriteria.situacao ne '3'}" >  
						<option value="3" >Empr�stimo</option>
					</c:if>
					<c:if test="${pesquisaHistoricoLivroCulturalCriteria.situacao eq '0'}" >  
						<option value="0" selected="selected">Todos</option>
					</c:if>
					<c:if test="${pesquisaHistoricoLivroCulturalCriteria.situacao ne '0'}" >  
						<option value="0" >Todos</option>
					</c:if>
		        </select>
			</td>
		</tr>		
	   <tr class='linhaFundo'>
			<td>Usu�rios</td>
			<td>
				<select name="idUsuarioEmprestimo">
					<option />
					<c:forEach var="elemento" items="${usuarios}">
						<c:if test="${elemento.id eq pesquisaHistoricoLivroCulturalCriteria.usuarioEmprestimo}" >  
							<option value="${elemento.id}" selected="selected">${elemento.nomeFormatado}</option> 
						</c:if>
						<c:if test="${elemento.id ne pesquisaHistoricoLivroCulturalCriteria.usuarioEmprestimo}" >  
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
		</tr>
		<tr><td>&nbsp;</td></tr>
	</table>

	<display:table cellspacing='1' id="emprestimo" name="sessionScope.S_C.emprestimos" requestURI="/relatorioLivroCultural.action?paginacao=true" export="true" pagesize="${b:getTamanhoPaginaListagem()}" >
		<c:if test="${S_C.emprestimos != null}" >
			<display:setProperty name="basic.msg.empty_list" value="${b:getMensagemListagemVazia()}" />
		</c:if>
		<display:column headerClass="linhaFundoTitulo" property="objetoBumerangue.codigo" title="C�digo" sortable="true" url="/relatorioLivroCultural.action?method=detalharEmprestimo" paramId="id" paramProperty="id" />
		<display:column headerClass="linhaFundoTitulo" property="objetoBumerangue.titulo" title="T�tulo" sortable="true" />
	   	<!-- avan�ado -->
		<c:if test="${!b:isUserInRole(S_C.usuarioLogado,'6') || S_C.usuarioLogado.id == emprestimo.usuarioEmprestimo.id}" >
			<td>${emprestimo.usuarioEmprestimo.nomeFormatado}</td>
			<display:column headerClass="linhaFundoTitulo" property="usuarioEmprestimo.nomeFormatado" title="Usu�rio" sortable="true" />
		</c:if>				
		<display:column headerClass="linhaFundoTitulo" value="${b:getFormatado(emprestimo.dataEmprestimo)}" title="Data do empr�stimo" />
		<display:column headerClass="linhaFundoTitulo" value="${b:getFormatado(emprestimo.dataDevolucao)}" title="Data da devolu��o" />
		<display:column headerClass="linhaFundoTitulo" property="situacao" title="Situa��o" sortable="true" />
	</display:table>

</form>