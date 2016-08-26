package br.org.ceu.bumerangue.service;

import java.util.List;
import java.util.Map;

import br.org.ceu.bumerangue.entity.Dominio;
import br.org.ceu.bumerangue.entity.ElementoDominio;
import br.org.ceu.bumerangue.entity.ObjetoBumerangue;
import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.entity.criterias.PesquisaObjetoBumerangueCriteria;
import br.org.ceu.bumerangue.entity.criterias.PesquisaUsuarioCriteria;
import br.org.ceu.bumerangue.entity.suporte.LocalizacaoFisicaInfo;
import br.org.ceu.bumerangue.entity.suporte.ResultadoVerificacaoCodigo;



public interface AdministracaoService extends BaseService {

	public List<Usuario> pesquisarUsuariosParaCompartilhar(Usuario usuarioLogado, PesquisaUsuarioCriteria pesquisaUsuarioCriteria);

	public List<Usuario> pesquisarUsuariosModulo(Usuario usuarioLogado, PesquisaUsuarioCriteria pesquisaUsuarioCriteria);
	
	public void compartilharUsuarios(Usuario usuarioLogado, String[] arrayIdUsuario_codigoTipoPermissao, String nomeModuloSelecionado);
	
	public Dominio getDominio(Usuario usuarioLogado, Integer codigo);
	
	public Dominio getDominio(Usuario usuarioLogado, String idDominio);
	
	public Usuario getUsuario(Usuario usuarioLogado, String idUsuario);
	
	public ElementoDominio getElementoDominio(Usuario usuarioLogado, String idElementoDominio);
	
	public void inserirElementoDominio(Usuario usuarioLogado, ElementoDominio elementoDominio);
	
	public void editarElementoDominio(Usuario usuarioLogado, ElementoDominio elementoDominio);
	
	public void excluirElementoDominio(Usuario usuarioLogado, ElementoDominio elementoDominio);
	
	public Map<String,String> findConfiguracoes(Usuario usuarioLogado, String nomeModulo);
	
	public void salvarConfiguracoes(Usuario usuarioLogado, String nomeModulo, String[] chaves, String[] valores);

    /**
     * Altera a posição de um elemento na lista pertencente ao dominio.<br>
     * O uso dos parâmetros direction e posicaoDestino são excludentes entre si. Caso ambos sejam usados, o parâmetro direcao terá prioridade.
     * @author Adriano Carvalho
     * @param usuarioLogado
     * @param elementoDominio
     * @param direcao
	 *            1 = up; 2 = down; 3 = upFirst; 4 = downLast
     * @param posicaoDestino
	 *            a posição, para a qual irá o elemento
     */
    public void trocarPosicaoElementoDominio(Usuario usuarioLogado, ElementoDominio elementoDominio, Dominio dominio, Integer direcao, Integer posicaoDestino);

	public void editarDominio(Usuario usuarioLogado, Dominio dominio);

	public void atualizarLocalizacoesFisicas(Usuario usuarioLogado, String nomeModulo);
    
    public LocalizacaoFisicaInfo getLocalizacaoFisicaInfo(Usuario usuarioLogado, String nomeModulo);

    public List<ObjetoBumerangue> pesquisarObjetosBumerangue(Usuario usuarioLogado, PesquisaObjetoBumerangueCriteria pesquisaObjetoBumerangueCriteria);

    public ResultadoVerificacaoCodigo verificarCodigos(Usuario usuarioLogado, PesquisaObjetoBumerangueCriteria pesquisaObjetoBumerangueCriteria);

    public ResultadoVerificacaoCodigo reordenarCodigosInformados(Usuario usuarioLogado, PesquisaObjetoBumerangueCriteria pesquisaObjetoBumerangueCriteria);
}
