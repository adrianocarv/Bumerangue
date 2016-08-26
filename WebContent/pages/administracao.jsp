<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>

<form action="manterVideoPesquisarController.action" method="post">
	<table class='tabelaForm' cellspacing='1'>
		<tr><td class="textoNegrito"><a href="compartilharUsuario.action?method=compartilharPre">Compartilhar Usuários entre módulos</a></td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td class="textoNegrito"><a href="manterDominio.action?method=listarPre">Domínios</a></td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td class="textoNegrito"><a href="manterConfiguracao.action?method=listarPre">Configurações</a></td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td class="textoNegrito"><a href="pesquisarCodigo.action?method=pesquisarPre">Pesquisa com intervalo de códigos</a></td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td class="textoNegrito"><a href="verificarCodigo.action?method=verificarPre">Verificação de códigos, a partir de uma sequência</a></td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td class="textoNegrito"><a href="manterLocalizacaoFisica.action?method=listarPre">Localizações Físicas</a></td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td class="textoNegrito"><a href="manterLocalizacaoFisica.action?method=atualizarPre">Atualizar Localizações Físicas nos objetos</a></td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td class="textoNegrito"><a href="manterSeguranca.action?method=selecionarUsuarioAdmin">Editar o usuário ADMIN</a></td></tr>
	</table>
</form>