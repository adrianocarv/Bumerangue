<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglib/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b"%>

<table id="topo" width="100%" border="0" cellpadding="0" cellspacing="0" background="resources/img/bk-top1.jpg">
	<tr>
		<td><img src="resources/img/topo-green.png" width="780" height="79"></td>
	</tr>
	<tr>
		<bean:define id="moduloAtual" type="java.lang.String" value="${MODULO_ATUAL}" ></bean:define>

		<bean:define id="descricaoModuloVideo" type="java.lang.String" value="${b:getDescricaoModulo('VIDEO')}" ></bean:define>
		<bean:define id="descricaoModuloLivroCultural" type="java.lang.String" value="${b:getDescricaoModulo('LIVRO_CULTURAL')}" ></bean:define>
		<bean:define id="descricaoModuloFilmeComercial" type="java.lang.String" value="${b:getDescricaoModulo('FILME_COMERCIAL')}" ></bean:define>
		<bean:define id="descricaoModuloFichaMissa" type="java.lang.String" value="${b:getDescricaoModulo('FICHA_MISSA')}" ></bean:define>

		<c:if test="${moduloAtual eq '1'}">
			<bean:define id="descricaoModuloAtual" type="java.lang.String" value="${descricaoModuloVideo}" ></bean:define>
		</c:if>
		<c:if test="${moduloAtual eq '2'}">
			<bean:define id="descricaoModuloAtual" type="java.lang.String" value="${descricaoModuloLivroCultural}" ></bean:define>
		</c:if>
		<c:if test="${moduloAtual eq '3'}">
			<bean:define id="descricaoModuloAtual" type="java.lang.String" value="${descricaoModuloFilmeComercial}" ></bean:define>
		</c:if>
		<c:if test="${moduloAtual eq '4'}">
			<bean:define id="descricaoModuloAtual" type="java.lang.String" value="${descricaoModuloFichaMissa}" ></bean:define>
		</c:if>

		<td height="46" valign="top" class="menuHFundo">

			<table width="780" border="0" cellpadding="0" cellspacing="0">
				<tr valign="center">

					<!-- espaço anterior aos ícones disponívies -->
					<td width="40%" style="padding:6 0 0 0 " align="left" valign="top" class="menuHBranco" >
						<c:if test="${not empty S_C.usuarioLogado}" >
							<span class="textoNegrito">&nbsp;&nbsp;usuário:&nbsp;${S_C.usuarioLogado.nome}</span>
						</c:if>
						<div id="legenda" style="position:absolute; left:5px; top:103px; width:437px; height:12px; z-index:1; visibility: visible;" class="menuHLegenda">${descricaoModuloAtual}</div>
					</td>
					<td><img src="resources/img/splitmenu.jpg" width="2" height="46"></td>
					<td><img src="resources/img/dot.gif" width="15" height="20"></td>

					<!-- ícones disponívies -->
					<td width="100%">
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<!-- video -->
								<c:if test="${b:isUserInRole(S_C.usuarioLogado,'1-3')}" >
									<td title="${descricaoModuloVideo}" >
										<a href="manterVideo.action?method=pesquisarPre" ><img src="resources/img/modulo_1${moduloAtual == '1' ? '' : '_off'}.gif" border="0"></a>&nbsp;&nbsp;
									</td>
								</c:if>
								<!-- livroCultural -->
								<c:if test="${b:isUserInRole(S_C.usuarioLogado,'4-6')}" >
									<td title="${descricaoModuloLivroCultural}" >
										<a href="manterLivroCultural.action?method=pesquisarPre" ><img src="resources/img/modulo_2${moduloAtual == '2' ? '' : '_off'}.gif" border="0"></a>&nbsp;&nbsp;
									</td>
								</c:if>
								<!-- filmeComercial -->
								<c:if test="${b:isModuloDisponivel('FILME_COMERCIAL')}" >
									<td title="${descricaoModuloFilmeComercial}" >
										<a href="manterFilmeComercial.action?method=pesquisarPre" ><img src="resources/img/modulo_3${moduloAtual == '3' ? '' : '_off'}.gif" border="0"></a>&nbsp;&nbsp;
									</td>
								</c:if>
								<!-- fichaMissa -->
								<c:if test="${b:isUserInRole(S_C.usuarioLogado,'10-12')}" >
									<td title="${descricaoModuloFichaMissa}" >
										<a href="manterPlanoMissa.action?method=pesquisarPre" ><img src="resources/img/modulo_4${moduloAtual == '4' ? '' : '_off'}.gif" border="0"></a>&nbsp;&nbsp;
									</td>
								</c:if>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>