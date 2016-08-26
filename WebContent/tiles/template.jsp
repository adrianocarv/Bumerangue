<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%-- Template da aplicaï¿½ï¿½o, usado pelo template da aplicaï¿½ï¿½o --%>
<%@ taglib uri="/WEB-INF/taglib/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>

<html:html locale="true">
<head>
	<tiles:importAttribute />
	<tiles:useAttribute id="moduloAtual" name="moduloAtual" classname="String" />
	<tiles:useAttribute id="ucAtual" name="ucAtual" classname="String" />
	<c:set var="MODULO_ATUAL" scope="session" value="${moduloAtual}"/>
	<c:set var="UC_ATUAL" scope="session" value="${ucAtual}"/>
	<title><tiles:getAsString name="tituloJanela"/></title>


<c:if test="${S_C.urlCSS == null}">
	<link rel="stylesheet" type="text/css" href="resources/css/padraoFormulario.css">
</c:if>
<c:if test="${S_C.urlCSS != null}">
	<link rel="stylesheet" type="text/css" href="${S_C.urlCSS}">
</c:if>

	<script src="resources/javascript/formata/fmtKeyMascara.js"></script>
	<script src="resources/javascript/formulario/utils.js"></script>
	<script src="resources/javascript/formulario/dwrUtils.js"></script>
	<script language="JavaScript" type="text/JavaScript"></script>
	<link rel="shortcut icon" href="resources/img/logo0.ico" />
</head>

<body>

<c:if test="${!S_C.hideTemplate}">
<tiles:insert attribute="cabecalho" />
<tiles:insert attribute="menuHorizontal" />

<table width="100%" height="329" border="0" cellpadding="0" cellspacing="0" background="resources/img/bk-main.gif" id="main">
	<tr>
		<td valign="top">
			<table width="100%"  border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="100" bgcolor="#979897">
						<table width="100" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td><img src="resources/img/bk-line.jpg" width="1" height="2"></td>
								<td><img src="resources/img/dot.gif" width="1" height="1"></td>
								<td width="1"><img src="resources/img/corner-a.gif" width="9" height="6"></td>
							</tr>
						</table>
					</td>
					<td width="100%"><img src="resources/img/dot.gif" width="1" height="1"></td>
					<td><img src="resources/img/corner-b.gif" width="4" height="6"></td>
				</tr>
			</table>
</c:if>
			<table width="100%" height="317" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<c:if test="${!S_C.hideTemplate}">
					<td width="95" valign="top"><tiles:insert attribute="menuVertical" /></td>
					</c:if>
					<td align="center" valign="top">
						<table width="98%"  border="0" cellspacing="0" cellpadding="2">
							<tr><td>&nbsp;</td></tr>
							<tr><td class="linhaFaixa"><tiles:getAsString name="tituloPagina"/>

							</td></tr>
							<tr><td>&nbsp;</td></tr>
						</table>
			
							<!-- Mensagens -->
							<c:if test="${messages != null}" >
								<table width="400" border="0" cellspacing="0" cellpadding="0" bgcolor="#E7F4EA">
					            	<tr>
					            		<td><img src="resources/img/errCorner-a.jpg" width="6" height="6"></td>
					            		<td width="100%" background="resources/img/errTop.jpg"><img src="resources/img/errTop.jpg" width="5" height="6"></td>
					            		<td><img src="resources/img/errCorner-b.jpg" width="6" height="6"></td>
					            	</tr>
					            	<tr>
					            		<td background="resources/img/errLeft.jpg"><img src="resources/img/errLeft.jpg" width="6" height="5"></td>
					            		<td align="center">
					            			<table width="100%" border="0" cellpadding="0" cellspacing="1">
					            				<tr>
					            					<td valign="middle"><img src="resources/img/${icoMsgFileName}" width="30" height="29" hspace="8"></td>
					            					<td width="100%"><strong>
														<c:forEach var="message" items="${messages}">
															<li>${message}</li>
														</c:forEach>
					            					</strong></td>
					           					</tr>
					            			</table>
					            		</td>
					            		<td background="resources/img/errRight.jpg"><img src="resources/img/errRight.jpg" width="6" height="4"></td>
					            	</tr>
					            	<tr>
					            		<td><img src="resources/img/errCorner-c.jpg" width="6" height="6"></td>
					            		<td background="resources/img/errBottom.jpg"><img src="resources/img/errBottom.jpg" width="4" height="6"></td>
					            		<td><img src="resources/img/errCorner-d.jpg" width="6" height="6"></td>
					            	</tr>
					            </table>
							 </c:if>
				
							<!-- Pula uma linha -->
							<table width="95%"  border="0" cellspacing="0" cellpadding="2">
								<tr>&nbsp</tr>
				            </table>
				
						<table width="95%"  border="0" cellspacing="0" cellpadding="2">
							<tr><td><tiles:insert attribute="corpo" /></td></tr>
							<tr><td><a class="alternaTemplate" href="manterSeguranca.action?method=apresentacaoPre&hideTemplate=${!S_C.hideTemplate}">exibir/esconder o template</a></td></tr>
						</table>
					</td>
				</tr>
			</table>

<c:if test="${!S_C.hideTemplate}">
			<table width="100%"  border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="100" bgcolor="#979897">
						<table width="100" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td><img src="resources/img/bk-line.jpg" width="1" height="2"></td>
								<td><img src="resources/img/dot.gif" width="1" height="1"></td>
								<td width="1"><img src="resources/img/corner-c.gif" width="9" height="6"></td>
							</tr>
						</table>
					</td>
					<td width="100%"><img src="resources/img/dot.gif" width="1" height="1"></td>
					<td><img src="resources/img/corner-d.gif" width="6" height="6"></td>
				</tr>
			</table>
			<tiles:insert attribute="rodape" />
		</td>
	</tr>
</table>
</c:if>

<!-- Google Analytics - bumerangue.org.br -->
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-4292519-3']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>

</body>
</html:html>