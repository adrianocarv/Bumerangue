<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b"%>
<%@ taglib uri="http://displaytag.sf.net/el" prefix="display"%>

<script type="text/javascript">window.onload=function(){focusField("codigo");}</script>

<form action="relatorioLivroCultural.action?method=pesquisarHistoricoEmprestimos" method="post">
	<table class='tabelaFormFundo' cellspacing='1'>
	   <tr class='linhaFundo'>
			<td width="30%">Código</td>
			<td><input type="text" maxlength="5" id="codigo" name="codigo" value="${pesquisaHistoricoLivroCulturalCriteria.codigo}" onkeypress="somenteNumeros()"></td>
		</tr>

	   <tr class='linhaFundo'>
			<td>Autor</td>
			<td><input type="text" maxlength="10" name="autor" value="${pesquisaHistoricoLivroCulturalCriteria.autor}"></td>
		</tr>
	   						
	   <tr class='linhaFundo'>
			<td>Data do empréstimo a partir de</td>
			<td><input type="text" maxlength="10" name="dataEmprestimoIni" value="${pesquisaHistoricoLivroCulturalCriteria.dataEmprestimoIni}" onkeypress="fmtKeyMascara('DD/MM/YYYY',dataEmprestimoIni,dataEmprestimoIni,this)" ></td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Data do empréstimo até</td>
			<td><input type="text" maxlength="10" name="dataEmprestimoFim" value="${pesquisaHistoricoLivroCulturalCriteria.dataEmprestimoFim}" onkeypress="fmtKeyMascara('DD/MM/YYYY',dataEmprestimoFim,dataEmprestimoFim,this)" ></td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Observações do empréstimo</td>
			<td><input type="text" size="70" name="observacoesEmprestimo" value="${pesquisaHistoricoLivroCulturalCriteria.observacoesEmprestimo}" ></td>
		</tr>															
	   <tr class='linhaFundo'>
			<td>Data da devolução a partir de</td>
			<td><input type="text" maxlength="10" name="dataDevolucaoIni" value="${pesquisaHistoricoLivroCulturalCriteria.dataDevolucaoIni}" onkeypress="fmtKeyMascara('DD/MM/YYYY',dataDevolucaoIni,dataDevolucaoIni,this)" ></td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Data da devolução até</td>
			<td><input type="text" maxlength="10" name="dataDevolucaoFim" value="${pesquisaHistoricoLivroCulturalCriteria.dataDevolucaoFim}" onkeypress="fmtKeyMascara('DD/MM/YYYY',dataDevolucaoFim,dataDevolucaoFim,this)" ></td>
		</tr>
	   <tr class='linhaFundo'>
			<td>Observações da devolução</td>
			<td><input type="text" size="70" name="observacoesDevolucao" value="${pesquisaHistoricoLivroCulturalCriteria.observacoesDevolucao}" ></td>
		</tr>															
	   <tr class='linhaFundo'>
			<td>Situação</td>
			<td>
				<select name="idSituacao">
					<option />
					<c:if test="${pesquisaHistoricoLivroCulturalCriteria.situacao eq '1'}" >  
						<option value="1" selected="selected">Devolução</option>
					</c:if>
					<c:if test="${pesquisaHistoricoLivroCulturalCriteria.situacao ne '1'}" >  
						<option value="1" >Devolução</option>
					</c:if>

					<c:if test="${pesquisaHistoricoLivroCulturalCriteria.situacao eq '3'}" >  
						<option value="3" selected="selected">Empréstimo</option>
					</c:if>
					<c:if test="${pesquisaHistoricoLivroCulturalCriteria.situacao ne '3'}" >  
						<option value="3" >Empréstimo</option>
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
			<td>Usuários</td>
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
		<display:column headerClass="linhaFundoTitulo" property="objetoBumerangue.codigo" title="Código" sortable="true" url="/relatorioLivroCultural.action?method=detalharEmprestimo" paramId="id" paramProperty="id" />
		<display:column headerClass="linhaFundoTitulo" property="objetoBumerangue.titulo" title="Título" sortable="true" />
	   	<!-- avançado -->
		<c:if test="${!b:isUserInRole(S_C.usuarioLogado,'6') || S_C.usuarioLogado.id == emprestimo.usuarioEmprestimo.id}" >
			<td>${emprestimo.usuarioEmprestimo.nomeFormatado}</td>
			<display:column headerClass="linhaFundoTitulo" property="usuarioEmprestimo.nomeFormatado" title="Usuário" sortable="true" />
		</c:if>				
		<display:column headerClass="linhaFundoTitulo" value="${b:getFormatado(emprestimo.dataEmprestimo)}" title="Data do empréstimo" />
		<display:column headerClass="linhaFundoTitulo" value="${b:getFormatado(emprestimo.dataDevolucao)}" title="Data da devolução" />
		<display:column headerClass="linhaFundoTitulo" property="situacao" title="Situação" sortable="true" />
	</display:table>

</form>