<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>

<form action="manterVideoPesquisarController.action" method="post">
	<table class='tabelaForm' cellspacing='1'>
		<tr><td class="textoNegrito"><a href="compartilharUsuario.action?method=compartilharPre">Compartilhar Usu�rios entre m�dulos</a></td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td class="textoNegrito"><a href="manterDominio.action?method=listarPre">Dom�nios</a></td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td class="textoNegrito"><a href="manterConfiguracao.action?method=listarPre">Configura��es</a></td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td class="textoNegrito"><a href="pesquisarCodigo.action?method=pesquisarPre">Pesquisa com intervalo de c�digos</a></td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td class="textoNegrito"><a href="verificarCodigo.action?method=verificarPre">Verifica��o de c�digos, a partir de uma sequ�ncia</a></td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td class="textoNegrito"><a href="manterLocalizacaoFisica.action?method=listarPre">Localiza��es F�sicas</a></td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td class="textoNegrito"><a href="manterLocalizacaoFisica.action?method=atualizarPre">Atualizar Localiza��es F�sicas nos objetos</a></td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td class="textoNegrito"><a href="manterSeguranca.action?method=selecionarUsuarioAdmin">Editar o usu�rio ADMIN</a></td></tr>
	</table>
</form>