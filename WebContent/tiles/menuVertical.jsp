<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglib/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b"%>

<table width="95" border="0" cellpadding="0" cellspacing="0" id="menu">

<bean:define id="moduloAtual" type="java.lang.String" value="${MODULO_ATUAL}" ></bean:define>
<bean:define id="ucAtual" type="java.lang.String" value="${UC_ATUAL}" ></bean:define>

	<!-- comum -->
	<tr>
	  <td>&nbsp;</td>
	  <td height="30"><a href="index.jsp" class="menuVFonte">Início</a></td>
	  <c:choose> 
	  	<c:when test="${'comum_1' == ucAtual}">
		  <td width="1" background="resources/img/corner-bk-sel.gif">&nbsp;</td>
		</c:when>
	    <c:otherwise>
		  <td width="1" background="resources/img/corner-bk.gif">&nbsp;</td>
	    </c:otherwise>
	  </c:choose>
	</tr>
	<tr>
	  <td background="resources/img/bk-line.jpg"><img src="resources/img/bk-line.jpg" width="1" height="2"></td>
	  <td background="resources/img/bk-line.jpg"><img src="resources/img/bk-line.jpg" width="1" height="2"></td>
	  <td background="resources/img/corner-bk.gif"><img src="resources/img/dot.gif" width="1" height="1"></td>
	</tr>

	<!-- video -->
	<c:if test="${moduloAtual == '1' && b:isUserInRole(S_C.usuarioLogado,'1-3')}" >
		<tr>
		  <td>&nbsp;</td>
		  <td height="30"><a href="manterVideo.action?method=pesquisarPre" class="menuVFonte">Vídeos</a></td>
		  <c:choose> 
		  	<c:when test="${'1' == ucAtual}">
			  <td width="1" background="resources/img/corner-bk-sel.gif">&nbsp;</td>
			</c:when>
		    <c:otherwise>
			  <td width="1" background="resources/img/corner-bk.gif">&nbsp;</td>
		    </c:otherwise>
		  </c:choose>
		</tr>
		<tr>
		  <td background="resources/img/bk-line.jpg"><img src="resources/img/bk-line.jpg" width="1" height="2"></td>
		  <td background="resources/img/bk-line.jpg"><img src="resources/img/bk-line.jpg" width="1" height="2"></td>
		  <td background="resources/img/corner-bk.gif"><img src="resources/img/dot.gif" width="1" height="1"></td>
		</tr>

		<tr>
		  <td>&nbsp;</td>
		  <!-- td height="30"><a href="relatorioVideo.action?method=menu" class="menuVFonte">Relat?rios</a></td -->
		  <td height="30"><a href="relatorioVideo.action?method=pesquisarPre&tipo=historico" class="menuVFonte">Relatórios</a></td>
		  <c:choose> 
		  	<c:when test="${'2' == ucAtual}">
			  <td width="1" background="resources/img/corner-bk-sel.gif">&nbsp;</td>
			</c:when>
		    <c:otherwise>
			  <td width="1" background="resources/img/corner-bk.gif">&nbsp;</td>
		    </c:otherwise>
		  </c:choose>
		</tr>
		<tr>
		  <td background="resources/img/bk-line.jpg"><img src="resources/img/bk-line.jpg" width="1" height="2"></td>
		  <td background="resources/img/bk-line.jpg"><img src="resources/img/bk-line.jpg" width="1" height="2"></td>
		  <td background="resources/img/corner-bk.gif"><img src="resources/img/dot.gif" width="1" height="1"></td>
		</tr>
	</c:if>

	<!-- livroCultural -->
	<c:if test="${moduloAtual == '2' && b:isUserInRole(S_C.usuarioLogado,'4-6')}" >
		<tr>
		  <td>&nbsp;</td>
		  <td height="30"><a href="manterLivroCultural.action?method=pesquisarPre" class="menuVFonte">Livros Culturais</a></td>
		  <c:choose> 
		  	<c:when test="${'1' == ucAtual}">
			  <td width="1" background="resources/img/corner-bk-sel.gif">&nbsp;</td>
			</c:when>
		    <c:otherwise>
			  <td width="1" background="resources/img/corner-bk.gif">&nbsp;</td>
		    </c:otherwise>
		  </c:choose>
		</tr>
		<tr>
		  <td background="resources/img/bk-line.jpg"><img src="resources/img/bk-line.jpg" width="1" height="2"></td>
		  <td background="resources/img/bk-line.jpg"><img src="resources/img/bk-line.jpg" width="1" height="2"></td>
		  <td background="resources/img/corner-bk.gif"><img src="resources/img/dot.gif" width="1" height="1"></td>
		</tr>

		<tr>
		  <td>&nbsp;</td>
		  <!-- td height="30"><a href="relatorioLivroCultural.action?method=menu" class="menuVFonte">Relat?rios</a></td -->
		  <td height="30"><a href="relatorioLivroCultural.action?method=pesquisarPre&tipo=historico" class="menuVFonte">Relatórios</a></td>
		  <c:choose> 
		  	<c:when test="${'2' == ucAtual}">
			  <td width="1" background="resources/img/corner-bk-sel.gif">&nbsp;</td>
			</c:when>
		    <c:otherwise>
			  <td width="1" background="resources/img/corner-bk.gif">&nbsp;</td>
		    </c:otherwise>
		  </c:choose>
		</tr>
		<tr>
		  <td background="resources/img/bk-line.jpg"><img src="resources/img/bk-line.jpg" width="1" height="2"></td>
		  <td background="resources/img/bk-line.jpg"><img src="resources/img/bk-line.jpg" width="1" height="2"></td>
		  <td background="resources/img/corner-bk.gif"><img src="resources/img/dot.gif" width="1" height="1"></td>
		</tr>
	</c:if>

	<!-- filmeComercial -->
	<c:if test="${moduloAtual == '3'}" >
		<tr>
		  <td>&nbsp;</td>
		  <td height="30"><a href="manterFilmeComercial.action?method=pesquisarPre" class="menuVFonte">Cinematografia</a></td>
		  <c:choose> 
		  	<c:when test="${'1' == ucAtual}">
			  <td width="1" background="resources/img/corner-bk-sel.gif">&nbsp;</td>
			</c:when>
		    <c:otherwise>
			  <td width="1" background="resources/img/corner-bk.gif">&nbsp;</td>
		    </c:otherwise>
		  </c:choose>
		</tr>
		<tr>
		  <td background="resources/img/bk-line.jpg"><img src="resources/img/bk-line.jpg" width="1" height="2"></td>
		  <td background="resources/img/bk-line.jpg"><img src="resources/img/bk-line.jpg" width="1" height="2"></td>
		  <td background="resources/img/corner-bk.gif"><img src="resources/img/dot.gif" width="1" height="1"></td>
		</tr>
	</c:if>

	<!-- fichaMissa -->
	<c:if test="${moduloAtual == '4' && b:isUserInRole(S_C.usuarioLogado,'10')}" >
		<tr>
		  <td>&nbsp;</td>
		  <td height="30"><a href="manterGrupoComponenteMissa.action?method=pesquisarPre&tipo=1" class="menuVFonte">Liturgia da Palavra</a></td>
		  <c:choose> 
		  	<c:when test="${'1' == ucAtual || '1' == tipo}">
			  <td width="1" background="resources/img/corner-bk-sel.gif">&nbsp;</td>
			</c:when>
		    <c:otherwise>
			  <td width="1" background="resources/img/corner-bk.gif">&nbsp;</td>
		    </c:otherwise>
		  </c:choose>
		</tr>
		<tr>
		  <td background="resources/img/bk-line.jpg"><img src="resources/img/bk-line.jpg" width="1" height="2"></td>
		  <td background="resources/img/bk-line.jpg"><img src="resources/img/bk-line.jpg" width="1" height="2"></td>
		  <td background="resources/img/corner-bk.gif"><img src="resources/img/dot.gif" width="1" height="1"></td>
		</tr>
	</c:if>

	<c:if test="${moduloAtual == '4' && b:isUserInRole(S_C.usuarioLogado,'10')}" >
		<tr>
		  <td>&nbsp;</td>
		  <td height="30"><a href="manterGrupoComponenteMissa.action?method=pesquisarPre&tipo=2" class="menuVFonte">Orações</a></td>
		  <c:choose> 
		  	<c:when test="${'2' == ucAtual || '2' == tipo}">
			  <td width="1" background="resources/img/corner-bk-sel.gif">&nbsp;</td>
			</c:when>
		    <c:otherwise>
			  <td width="1" background="resources/img/corner-bk.gif">&nbsp;</td>
		    </c:otherwise>
		  </c:choose>
		</tr>
		<tr>
		  <td background="resources/img/bk-line.jpg"><img src="resources/img/bk-line.jpg" width="1" height="2"></td>
		  <td background="resources/img/bk-line.jpg"><img src="resources/img/bk-line.jpg" width="1" height="2"></td>
		  <td background="resources/img/corner-bk.gif"><img src="resources/img/dot.gif" width="1" height="1"></td>
		</tr>
	</c:if>

	<c:if test="${moduloAtual == '4' && b:isUserInRole(S_C.usuarioLogado,'10-12')}" >
		<tr>
		  <td>&nbsp;</td>
		  <td height="30"><a href="manterPlanoMissa.action?method=pesquisarPre" class="menuVFonte">Plano de Missa</a></td>
		  <c:choose> 
		  	<c:when test="${'3' == ucAtual}">
			  <td width="1" background="resources/img/corner-bk-sel.gif">&nbsp;</td>
			</c:when>
		    <c:otherwise>
			  <td width="1" background="resources/img/corner-bk.gif">&nbsp;</td>
		    </c:otherwise>
		  </c:choose>
		</tr>
		<tr>
		  <td background="resources/img/bk-line.jpg"><img src="resources/img/bk-line.jpg" width="1" height="2"></td>
		  <td background="resources/img/bk-line.jpg"><img src="resources/img/bk-line.jpg" width="1" height="2"></td>
		  <td background="resources/img/corner-bk.gif"><img src="resources/img/dot.gif" width="1" height="1"></td>
		</tr>
	</c:if>

	<!-- comum -->
	<c:if test="${not empty S_C.usuarioLogado}" >
		<tr>
		  <td>&nbsp;</td>
		  <td height="30"><a href="manterSeguranca.action?method=trocarSenhaPre" class="menuVFonte">Trocar Senha</a></td>
		  <c:choose> 
		  	<c:when test="${'comum_2' == ucAtual}">
			  <td width="1" background="resources/img/corner-bk-sel.gif">&nbsp;</td>
			</c:when>
		    <c:otherwise>
			  <td width="1" background="resources/img/corner-bk.gif">&nbsp;</td>
		    </c:otherwise>
		  </c:choose>
		</tr>
		<tr>
		  <td background="resources/img/bk-line.jpg"><img src="resources/img/bk-line.jpg" width="1" height="2"></td>
		  <td background="resources/img/bk-line.jpg"><img src="resources/img/bk-line.jpg" width="1" height="2"></td>
		  <td background="resources/img/corner-bk.gif"><img src="resources/img/dot.gif" width="1" height="1"></td>
		</tr>
	</c:if>

	<c:if test="${b:isUserInRole(S_C.usuarioLogado,'1,4,7,10')}" >
		<tr>
		  <td>&nbsp;</td>
		  <td height="30"><a href="manterSeguranca.action?method=menuAdministracao" class="menuVFonte">Administração</a></td>
		  <c:choose> 
		  	<c:when test="${'comum_3' == ucAtual}">
			  <td width="1" background="resources/img/corner-bk-sel.gif">&nbsp;</td>
			</c:when>
		    <c:otherwise>
			  <td width="1" background="resources/img/corner-bk.gif">&nbsp;</td>
		    </c:otherwise>
		  </c:choose>
		</tr>
		<tr>
		  <td background="resources/img/bk-line.jpg"><img src="resources/img/bk-line.jpg" width="1" height="2"></td>
		  <td background="resources/img/bk-line.jpg"><img src="resources/img/bk-line.jpg" width="1" height="2"></td>
		  <td background="resources/img/corner-bk.gif"><img src="resources/img/dot.gif" width="1" height="1"></td>
		</tr>

		<tr>
		  <td>&nbsp;</td>
		  <td height="30"><a href="manterUsuario.action?method=pesquisarPre" class="menuVFonte">Usuários</a></td>
		  <c:choose> 
		  	<c:when test="${'comum_4' == ucAtual}">
			  <td width="1" background="resources/img/corner-bk-sel.gif">&nbsp;</td>
			</c:when>
		    <c:otherwise>
			  <td width="1" background="resources/img/corner-bk.gif">&nbsp;</td>
		    </c:otherwise>
		  </c:choose>
		</tr>
		<tr>
		  <td background="resources/img/bk-line.jpg"><img src="resources/img/bk-line.jpg" width="1" height="2"></td>
		  <td background="resources/img/bk-line.jpg"><img src="resources/img/bk-line.jpg" width="1" height="2"></td>
		  <td background="resources/img/corner-bk.gif"><img src="resources/img/dot.gif" width="1" height="1"></td>
		</tr>
	</c:if>

	<c:if test="${not empty S_C.usuarioLogado}" >
		<tr>
		  <td>&nbsp;</td>
		  <td height="30"><a href="manterArquivo.action?method=listar" class="menuVFonte">Arquivos</a></td>
		  <c:choose> 
		  	<c:when test="${'comum_5' == ucAtual}">
			  <td width="1" background="resources/img/corner-bk-sel.gif">&nbsp;</td>
			</c:when>
		    <c:otherwise>
			  <td width="1" background="resources/img/corner-bk.gif">&nbsp;</td>
		    </c:otherwise>
		  </c:choose>
		</tr>
		<tr>
		  <td background="resources/img/bk-line.jpg"><img src="resources/img/bk-line.jpg" width="1" height="2"></td>
		  <td background="resources/img/bk-line.jpg"><img src="resources/img/bk-line.jpg" width="1" height="2"></td>
		  <td background="resources/img/corner-bk.gif"><img src="resources/img/dot.gif" width="1" height="1"></td>
		</tr>

		<tr>
		  <td>&nbsp;</td>
		  <td height="30"><a href="manterSeguranca.action?method=sair" class="menuVFonte">Sair</a></td>
		  <td width="1" background="resources/img/corner-bk.gif">&nbsp;</td>
		</tr>
		<tr>
		  <td background="resources/img/bk-line.jpg"><img src="resources/img/bk-line.jpg" width="1" height="2"></td>
		  <td background="resources/img/bk-line.jpg"><img src="resources/img/bk-line.jpg" width="1" height="2"></td>
		  <td background="resources/img/corner-bk.gif"><img src="resources/img/dot.gif" width="1" height="1"></td>
		</tr>
	</c:if>

	<tr>
	  <td>&nbsp;</td>
	  <td height="30"><a href="manterSegurancaLivreAcesso.action?method=comentarSugerirPre" class="menuVFonte">Fale conosco</a></td>
	  <c:choose> 
	  	<c:when test="${'comum_6' == ucAtual}">
		  <td width="1" background="resources/img/corner-bk-sel.gif">&nbsp;</td>
		</c:when>
	    <c:otherwise>
		  <td width="1" background="resources/img/corner-bk.gif">&nbsp;</td>
	    </c:otherwise>
	  </c:choose>
	</tr>
	<tr>
	  <td background="resources/img/bk-line.jpg"><img src="resources/img/bk-line.jpg" width="1" height="2"></td>
	  <td background="resources/img/bk-line.jpg"><img src="resources/img/bk-line.jpg" width="1" height="2"></td>
	  <td background="resources/img/corner-bk.gif"><img src="resources/img/dot.gif" width="1" height="1"></td>
	</tr>

</table>
