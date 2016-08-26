<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b"%>

<bean:define id="descricaoModuloVideo" type="java.lang.String" value="${b:getDescricaoModulo('VIDEO')}" ></bean:define>
<bean:define id="descricaoModuloLivroCultural" type="java.lang.String" value="${b:getDescricaoModulo('LIVRO_CULTURAL')}" ></bean:define>
<bean:define id="descricaoModuloFilmeComercial" type="java.lang.String" value="${b:getDescricaoModulo('FILME_COMERCIAL')}" ></bean:define>
<bean:define id="descricaoModuloFichaMissa" type="java.lang.String" value="${b:getDescricaoModulo('FICHA_MISSA')}" ></bean:define>

<c:if test="${S_C.usuarioLogado eq null}" >
	<table class='tabelaForm' cellspacing='1'>
		<c:if test="${param.navegadorIncompativel eq true}" >
	   		<tr>
				<td colspan="3" style="font-size: 12; color: red">
					O navegador Internet Explorer � incompat�vel com este aplicativo.<br>
					Por favor, utilize <b>outro browser</b> para acessar o sistema (ex: Firefox, Chrome ou Opera).
				</td>
			</tr>
		</c:if>
		<tr>
			<c:if test="${b:isModuloDisponivel('FILME_COMERCIAL')}" >
				<td width="45%" title="${descricaoModuloFilmeComercial}" >
					<center>
						<a href="manterFilmeComercial.action?method=pesquisarPre" class="textoNegrito" >
							<img border="0" src="resources/img/modulo_3.gif" /><br>${descricaoModuloFilmeComercial}
						</a>
					</center>
					<br><br>
					&nbsp;&nbsp;&nbsp;&nbsp;Rela��o de filmes cinematogr�ficos que se distingue simultaneamente pelo crit�rio da qualidade art�stica e dos conte�dos limpos: ou seja, s� inclui os filmes rec�m-lan�ados mais bem cotados que, com garantia, n�o cont�m cenas inconvenientes.					
				</td>
			</c:if>
			<td width="20%" align="center" >
				<img src="resources/img/logo0.gif" />
				<br>${S_C.versao}
			</td>
			<td width="35%">
				<a href="manterSeguranca.action?method=logar" class="textoNegrito" align="center"><center>Acessar com senha</center></a>
				<br><br>
				&nbsp;&nbsp;&nbsp;&nbsp;Usu�rios cadastrados podem logar no site e acessar outras funcionalidades.
			</td>
		</tr>
	</table>
</c:if>

<c:if test="${S_C.usuarioLogado ne null}" >
	<%@include file="modulos.jsp" %>
	<table class='tabelaForm' cellspacing='1'>
	   <tr>
			<td align="center"><img src="resources/img/${b:getNomeArquivoLogotipo()}" /></td>
		</tr>
	   <tr>
			<td align="center" >Vers�o ${S_C.versao}</td>
		</tr>
	</table>
</c:if>
