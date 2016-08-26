<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/bumerangue.tld" prefix="b"%>

<table class='tabelaForm'>
	<tr><td class="textoNegrito">Módulos disponíveis:</td></tr>
</table>
<table class='tabelaForm'>
	<tr><td>&nbsp;</td></tr>
	<tr class='linhaBotao'>
		<c:if test="${S_C.usuarioLogado ne null}" >

			<!-- video -->
			<c:if test="${b:isUserInRole(S_C.usuarioLogado,'1-3')}" >
				<td width="20%" title="${descricaoModuloVideo}" class"textoNegrito" >
					<a href="manterVideo.action?method=pesquisarPre"> 
						<img border="0" src="resources/img/modulo_1.gif"><br>${descricaoModuloVideo}
					</a>
				</td>
			</c:if>

			<!-- livroCultural -->
			<c:if test="${b:isUserInRole(S_C.usuarioLogado,'4-6')}" >
				<td width="20%" title="${descricaoModuloLivroCultural}" class"textoNegrito" >
					<a href="manterLivroCultural.action?method=pesquisarPre"> 
						<img border="0" src="resources/img/modulo_2.gif"><br>${descricaoModuloLivroCultural}
					</a>
				</td>
			</c:if>
	
		</c:if>

		<!-- filmeComercial -->
		<c:if test="${b:isModuloDisponivel('FILME_COMERCIAL')}" >
			<td width="20%" title="${descricaoModuloFilmeComercial}" class"textoNegrito" >
				<a href="manterFilmeComercial.action?method=pesquisarPre"> 
					<img border="0" src="resources/img/modulo_3.gif"><br>${descricaoModuloFilmeComercial}
				</a>
			</td>
		</c:if>

		<!-- fichaMissa -->
		<c:if test="${b:isUserInRole(S_C.usuarioLogado,'10-12')}" >
			<td width="20%" title="${descricaoModuloFichaMissa}" class"textoNegrito" >
				<a href="manterPlanoMissa.action?method=pesquisarPre"> 
					<img border="0" src="resources/img/modulo_4.gif"><br>${descricaoModuloFichaMissa}
				</a>
			</td>
		</c:if>
	</tr>
</table>
