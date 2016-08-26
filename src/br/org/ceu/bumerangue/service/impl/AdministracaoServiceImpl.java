package br.org.ceu.bumerangue.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import br.org.ceu.bumerangue.dao.ConfiguracaoDao;
import br.org.ceu.bumerangue.dao.DominioDao;
import br.org.ceu.bumerangue.dao.ObjetoBumerangueDao;
import br.org.ceu.bumerangue.dao.PermissaoDao;
import br.org.ceu.bumerangue.dao.UsuarioDao;
import br.org.ceu.bumerangue.entity.Dominio;
import br.org.ceu.bumerangue.entity.ElementoDominio;
import br.org.ceu.bumerangue.entity.ObjetoBumerangue;
import br.org.ceu.bumerangue.entity.Permissao;
import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.entity.Video;
import br.org.ceu.bumerangue.entity.criterias.PesquisaObjetoBumerangueCriteria;
import br.org.ceu.bumerangue.entity.criterias.PesquisaUsuarioCriteria;
import br.org.ceu.bumerangue.entity.suporte.Configuracao;
import br.org.ceu.bumerangue.entity.suporte.ElementoVerificacaoCodigo;
import br.org.ceu.bumerangue.entity.suporte.LocalizacaoFisicaInfo;
import br.org.ceu.bumerangue.entity.suporte.ResultadoVerificacaoCodigo;
import br.org.ceu.bumerangue.entity.suporte.deploy.Deploy;
import br.org.ceu.bumerangue.service.AdministracaoService;
import br.org.ceu.bumerangue.service.exceptions.BumerangueAlertRuntimeException;
import br.org.ceu.bumerangue.service.exceptions.BumerangueErrorRuntimeException;
import br.org.ceu.bumerangue.util.Utils;
import br.org.ceu.bumerangue.util.ValidationRules;

import com.ibm.icu.util.Calendar;

public class AdministracaoServiceImpl extends BaseServiceImpl implements AdministracaoService {
	
    private UsuarioDao usuarioDao;
    public void setUsuarioDao(UsuarioDao usuarioDao) { this.usuarioDao = usuarioDao; }

    private DominioDao dominioDao;
    public void setDominioDao(DominioDao dominioDao) { this.dominioDao = dominioDao; }

    private PermissaoDao permissaoDao;
    public void setPermissaoDao(PermissaoDao permissaoDao) { this.permissaoDao = permissaoDao; }

    private ConfiguracaoDao configuracaoDao;
    public void setConfiguracaoDao(ConfiguracaoDao configuracaoDao) { this.configuracaoDao = configuracaoDao; }

    private ObjetoBumerangueDao objetoBumerangueDao;
    public void setObjetoBumerangueDao(ObjetoBumerangueDao objetoBumerangueDao) { this.objetoBumerangueDao = objetoBumerangueDao; }

    public List<Usuario> pesquisarUsuariosParaCompartilhar(Usuario usuarioLogado, PesquisaUsuarioCriteria pesquisaUsuarioCriteria){
		List<Usuario> usuariosParaCompartilhar = usuarioDao.findUsuarios(pesquisaUsuarioCriteria);

		Usuario usuarioModuloSelecionado = Usuario.getInstance(pesquisaUsuarioCriteria.getNomeModuloSelecionado());
		//caso o m�dulo selecionado n�o tenha usu�rios especializados, retorna a lista filtrada
		if(usuarioModuloSelecionado == null)
			return usuariosParaCompartilhar;
		
		//remove da lista filtrada todos os usu�rios correspondentes ao m�dulo selecionado
		List<Usuario> usuarios = new ArrayList<Usuario>();
		for(Usuario usuario : usuariosParaCompartilhar){
			if(usuario.getClass().getSimpleName().equals(usuarioModuloSelecionado.getClass().getSimpleName())) continue;
			usuarios.add(usuario);
		}

		return usuarios;
	}

	public List<Usuario> pesquisarUsuariosModulo(Usuario usuarioLogado, PesquisaUsuarioCriteria pesquisaUsuarioCriteria){
		
		Usuario usuarioModuloSelecionado = Usuario.getInstance(pesquisaUsuarioCriteria.getNomeModuloSelecionado());
		//caso o m�dulo selecionado n�o tenha usu�rios especializados, retorna uma lista vazia
		if(usuarioModuloSelecionado == null)
			return new ArrayList<Usuario>();

		//novo crit�ria para n�o alterar as informa��es do parametro
		PesquisaUsuarioCriteria pesquisaUsuarioCriteriaDAO = null;
		try {
			pesquisaUsuarioCriteriaDAO = (PesquisaUsuarioCriteria)BeanUtils.cloneBean(pesquisaUsuarioCriteria);

		} catch (Exception e) {
			throw new BumerangueErrorRuntimeException(e.getMessage());
		}
		pesquisaUsuarioCriteriaDAO.setNomeModulo(pesquisaUsuarioCriteria.getNomeModuloSelecionado());

		return usuarioDao.findUsuarios(pesquisaUsuarioCriteriaDAO);
	}

	public void compartilharUsuarios(Usuario usuarioLogado, String[] arrayIdUsuario_codigoTipoPermissao, String nomeModuloSelecionado){

		//percorre todos os usu�rios
		for(String idUsuario_codigoTipoPermissao : arrayIdUsuario_codigoTipoPermissao){
			//desmembra o array
			String[] membrosArray = idUsuario_codigoTipoPermissao.split("_");
			
			//recupera o usu�rio
			Usuario usuario = usuarioDao.get(membrosArray[0]);
			
			//recupera o c�digo do tipo de permiss�o
			Integer codigoTipoPermissao = membrosArray.length <= 1 ? null : Integer.parseInt(membrosArray[1]);

			//atualiza as permiss�es para o usu�rio
			this.atualizaPermissoes(usuario,codigoTipoPermissao,nomeModuloSelecionado);
		}
	}

	private void atualizaPermissoes(Usuario usuario, Integer codigoTipoPermissao, String nomeModulo){
		
		//n�o atualiza as permiss�es, se o usu�rio for o admin. Uma vez que este recebe todas as permiss�es de admin ao iniciar a aplica��o.
		if(usuario.isAdmin()) return;
		
		if (codigoTipoPermissao == null) codigoTipoPermissao = 0;
		
		Permissao permissaoBasico = null;
		Permissao permissaoAvancado = null;
		Permissao permissaoAdmin = null;

		//carrega as permiss�es, de acordo com o nome do m�dulo
		for(Permissao permissao : Permissao.getPermissoesPorModulo(nomeModulo)){
			if(permissao.isAdmin()) permissaoAdmin = permissao;
			else if(permissao.isAvancado()) permissaoAvancado = permissao;
			else if(permissao.isBasico()) permissaoBasico = permissao;
		}
		
		//remove
		if(permissaoBasico != null) usuario.removePermissao(permissaoBasico);
		if(permissaoAvancado != null) usuario.removePermissao(permissaoAvancado);
		if(permissaoAdmin != null) usuario.removePermissao(permissaoAdmin);

		//atribui
		if(codigoTipoPermissao.intValue() == ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_BASICO.getCodigo().intValue()){
			permissaoBasico = permissaoDao.getPorNome(permissaoBasico.getNome());
			usuario.addPermissao(permissaoBasico);
		} else if(codigoTipoPermissao.intValue() == ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_AVANCADO.getCodigo().intValue()){
			permissaoAvancado = permissaoDao.getPorNome(permissaoAvancado.getNome());
			usuario.addPermissao(permissaoAvancado);
		} else if(codigoTipoPermissao.intValue() == ElementoDominio.BUMERANGUE_TIPO_PERMISSAO_ADMIN.getCodigo().intValue()){
			permissaoAdmin = permissaoDao.getPorNome(permissaoAdmin.getNome());
			usuario.addPermissao(permissaoAdmin);
		}
	}

	public Dominio getDominio(Usuario usuarioLogado, Integer codigo){
		return dominioDao.get(codigo);
	}

	public Dominio getDominio(Usuario usuarioLogado, String idDominio){
		return dominioDao.get(idDominio);
	}

	public Usuario getUsuario(Usuario usuarioLogado, String idUsuario){
		return usuarioDao.get(idUsuario);
	}

	public ElementoDominio getElementoDominio(Usuario usuarioLogado, String idElementoDominio){
		return (ElementoDominio)dominioDao.get(ElementoDominio.class, idElementoDominio);
	}

	public void inserirElementoDominio(Usuario usuarioLogado, ElementoDominio elementoDominio){
		//verifica se existe outro elemento do mesmo dom�nio com a mesma descri��o.
		Dominio dominio = dominioDao.get(elementoDominio.getDominio().getId());
		for(ElementoDominio ed : dominio.getElementosDominio())
			if(ed.getDescricao().equalsIgnoreCase(elementoDominio.getDescricao()))
				throw new BumerangueErrorRuntimeException("Já existe outro elemento com a descrição : "+elementoDominio.getDescricao()+".");

		elementoDominio.setId(null);
		if(elementoDominio.getPersonalizado2() == null || !ValidationRules.isInformed(elementoDominio.getPersonalizado2().getId()) ) elementoDominio.setPersonalizado2(null);
		dominio.getElementosDominio().add(elementoDominio);
		
		dominioDao.saveOrUpdate(dominio);
	}

	public void editarElementoDominio(Usuario usuarioLogado, ElementoDominio elementoDominio){
		//verifica se existe outro elemento do mesmo dom�nio com a mesma descri��o.
		Dominio dominio = dominioDao.get(elementoDominio.getDominio().getId());
		for(ElementoDominio ed : dominio.getElementosDominio()){
			if(!ed.getId().equals(elementoDominio.getId()) && ed.getDescricao().equalsIgnoreCase(elementoDominio.getDescricao()))
				throw new BumerangueErrorRuntimeException("Já existe outro elemento com a descrição : "+elementoDominio.getDescricao()+".");
			
			if(ed.getId().equals(elementoDominio.getId())){
				ed.setDescricao(elementoDominio.getDescricao());
				ed.setPersonalizado1(elementoDominio.getPersonalizado1());
				if(ed.getPersonalizado2() != null && ValidationRules.isInformed(ed.getPersonalizado2().getId()))elementoDominio.setPersonalizado2(ed.getPersonalizado2());
				ed.setPersonalizado3(elementoDominio.getPersonalizado3());
				ed.setForaUso(elementoDominio.getForaUso());
			}
		}
		dominioDao.saveOrUpdate(dominio);
	}

	public void excluirElementoDominio(Usuario usuarioLogado, ElementoDominio elementoDominio){
//		Dominio dominio = dominioDao.get(elementoDominio.getDominio().getId());
//		for(ElementoDominio ed : dominio.getElementosDominio()){
//			if(ed.getId().equals(elementoDominio.getId())){
//				dominio.getElementosDominio().remove(ed);
//				break;
//			}
//		}
		dominioDao.delete(elementoDominio);
	}

	public Map<String,String> findConfiguracoes(Usuario usuarioLogado, String nomeModulo){
		Map<String,String> configuracoes = new TreeMap<String,String>();

		//considera s� as propriedades de configuracao.
		Properties properties = configuracaoDao.findConfiguracoes(nomeModulo, super.getWebRealPath());
		for(Object key : properties.keySet()){
			if(Utils.isPropriedadeConfiguracao(nomeModulo, key.toString()))
				configuracoes.put(key.toString(),properties.get(key)+"");
		}

		return configuracoes;
	}

	public void salvarConfiguracoes(Usuario usuarioLogado, String nomeModulo, String[] chaves, String[] valores){
		
		//Funcionalidade descontinuada para simplificar o processo de deply e instala��es do Bumerangue
		int sai = 1;
		if(sai == 1)
			throw new BumerangueAlertRuntimeException("Funcionalidade descontinuada para simplificar o processo de deply e instalações do Bumerangue.");
		
		Properties configuracoes = configuracaoDao.findConfiguracoes(nomeModulo, super.getWebRealPath());
		
		StringBuffer msgValidacao = new StringBuffer();
		
		//itera nas chaves, setando os valores nas configuracoes
		int i = 0;
		for(String key : chaves){
			String tipoValidacao = configuracoes.getProperty(key+Configuracao.SUFIXO_VALIDACAO_CHAVE);
			StringBuffer msgChave = new StringBuffer();
			boolean isValido = this.isValorConfiguracaoValido(valores[i],tipoValidacao,msgChave);
			
			//caso o valor da chave seja v�lido, atualiza
			if(isValido){
				//notifica sobre a necessidade de boot, se for o caso
				if(isValorConfiguracaoRequerBoot(valores[i],tipoValidacao)){
					//somente se o valor n�o tiver sido alterado
					if(!configuracoes.get(key).equals(valores[i]))
						this.notificarNecessidadeBoot(key);
				}
				configuracoes.setProperty(key,valores[i]);
			}else
				msgValidacao.append(key+":<br>"+msgChave+"<br>");
			i++;
		}
		
		if(!StringUtils.isBlank(msgValidacao+""))
			throw new BumerangueErrorRuntimeException(msgValidacao+"");
		
		configuracaoDao.update(nomeModulo, configuracoes, super.getWebRealPath());
	}
	
	private boolean isValorConfiguracaoValido(String valor, String tipoValidacao, StringBuffer msgValidacao){
		
		//tipo da valida��o requerido
		if(StringUtils.isBlank(tipoValidacao)){
			msgValidacao.append("Tipo de validação incorreto é requerido.<br>");
			return false;
		}

		//valida a tipo da valida��o
		if(!this.isTipoValidacaoValido(tipoValidacao)){
			msgValidacao.append("Tipo de validação incorreto: "+tipoValidacao+"<br>");
			return false;
		}

		//valida requerido
		if(this.isValorConfiguracaoRequerido(valor, tipoValidacao) && StringUtils.isBlank(valor)){
			msgValidacao.append("Deve ser informado.<br>");
			return false;
		}
			
		boolean valido = false;
		//valida numero
		if(",2,22,42,62,".indexOf(","+tipoValidacao+",") != -1 && !StringUtils.isNumeric(valor))
			msgValidacao.append("Deve ser numérico.<br>");

		//valida boolean
		else if(",3,23,43,63,".indexOf(","+tipoValidacao+",") != -1 && ",true,false,".indexOf(","+valor.toLowerCase()+",") == -1)
			msgValidacao.append("Deve ser true ou false.<br>");
		
		//valida e-mail
		else if(",4,24,44,64,".indexOf(","+tipoValidacao+",") != -1 && !ValidationRules.isValidEmail(valor))
			msgValidacao.append("Formato do e-mail inválido.<br>");

		//valida n�mero positivo
		else if(",5,25,45,65,".indexOf(","+tipoValidacao+",") != -1 && !(StringUtils.isNumeric(valor) && Integer.parseInt(valor) > 0) )
			msgValidacao.append("Deve ser numérico e maior que 0(zero).<br>");
		else
			valido = true;

		return valido;
	}
	
	private boolean isTipoValidacaoValido(String tipoValidacao){
		return ",1,2,3,4,5,21,22,23,24,25,41,42,43,44,45,61,62,63,64,65,".indexOf(","+tipoValidacao+",") != -1;
	}
	
	private boolean isValorConfiguracaoRequerido(String valor, String tipoValidacao){
		return ",21,22,23,24,25,61,62,63,64,65,".indexOf(","+tipoValidacao+",") != -1;
	}
	
	private boolean isValorConfiguracaoRequerBoot(String valor, String tipoValidacao){
		return ",61,62,63,64,65,".indexOf(","+tipoValidacao+",") != -1;
	}

	private void notificarNecessidadeBoot(String chave){
		Dominio dominioTipoPermissao = dominioDao.get(Dominio.BUMERANGUE_TIPO_PERMISSAO.getCodigo());
		dominioTipoPermissao.setPersonalizado1(chave);
	}
	
	public void trocarPosicaoElementoDominio(Usuario usuarioLogado, ElementoDominio elementoDominio, Dominio dominio, Integer direcao, Integer posicaoDestino){
    	dominio = dominioDao.get(dominio.getId());

		// seta a lista com o elemento na sua nova posi��o
		List<ElementoDominio> elementosDominio = new ArrayList<ElementoDominio>(dominio.getElementosDominio());
    	boolean isAlterado = this.trocarPosicaoElementoDominio(elementoDominio, direcao, posicaoDestino, elementosDominio);
			if (isAlterado){
				dominio.setElementosDominio(elementosDominio);
				dominioDao.saveOrUpdate(dominio);
			}
	}

    /**
	 * Altera a posi��o do elemento na lista do dominio. O uso dos par�metros direcao e posicaoDestino
	 * s�o excludentes entre si. Caso ambos sejam usados, o par�metro direcao ter� prioridade.
     * @author Adriano Carvalho
     * @param elementoDominio
     * @param direcao
	 *            1 = up; 2 = down; 3 = upFirst; 4 = downLast
     * @param posicaoDestino
	 *            a posi��o, para a qual ir� o elemento
     * @param elementosDominio
	 *            Ser� alterada com a lista com o elemento na nova posi��o, caso o retorno seja TRUE.
	 * @return TRUE no caso de ter havido altera��o na ordem dos elementos de list, FALSE se n�o tiver altera��o.
     */
    private boolean trocarPosicaoElementoDominio(ElementoDominio elementoDominio, Integer direcao, Integer posicaoDestino, List<ElementoDominio> elementosDominio) {
		// localiza o elemento
		ElementoDominio elementoDominioEncontrado = null;
		int posicaoElemento = 0;
		for (int i = 0; i < elementosDominio.size(); i++) {
			ElementoDominio elementoDominioAtual = elementosDominio.get(i);
			boolean equals = elementoDominioAtual.getId().equals(elementoDominio.getId());

			if (equals) {
				elementoDominioEncontrado = elementoDominioAtual;
				posicaoElemento = i;
				break;
			}
		}

		// marca para que posi��o vai o elemento na lista
		int posicaoDestinoNova = -1;
		if (direcao != null) {
			if (direcao.intValue() == 1)
				posicaoDestinoNova = posicaoElemento - 1;
			else if (direcao.intValue() == 2)
				posicaoDestinoNova = posicaoElemento + 1;
			else if (direcao.intValue() == 3)
				posicaoDestinoNova = 0;
			else if (direcao.intValue() == 4)
				posicaoDestinoNova = elementosDominio.size() - 1;
		} else
			posicaoDestinoNova = posicaoDestino.intValue();

		// caso a lista n�o possa sofrer altera��o, retorna FALSE.
		if (elementoDominioEncontrado == null || posicaoElemento == posicaoDestinoNova || posicaoDestinoNova < 0 || posicaoDestinoNova >= elementosDominio.size())
			return false;

		// remove o elemento da lista
		elementosDominio.remove(elementoDominioEncontrado);

		// coloca o elemento na posi��o devida
		elementosDominio.add(posicaoDestinoNova, elementoDominioEncontrado);

		return true;
	}

	public void editarDominio(Usuario usuarioLogado, Dominio dominio){
		Dominio dominioBD = dominioDao.get(dominio.getId());

		//caso o Dom�nio seja de localiza��o f�sica, valida os 'Campos de Regra'
		if(dominioBD.isLocalizacaoFisica()){
			String nomeModulo = dominioBD.getDescricao().substring(0,dominioBD.getDescricao().indexOf("_LOCALIZACAO_FISICA"));
			if(!this.isCamposRegraLocalizacaoFisicaValidos(nomeModulo, dominio))
				throw new BumerangueErrorRuntimeException("Problema na validação dos campos de regra da localização física");
		}
		
		dominioBD.setPersonalizado2(dominio.getPersonalizado2());
		dominioBD.setPersonalizado3(dominio.getPersonalizado3());
		dominioBD.setPersonalizado4(dominio.getPersonalizado4());
		dominioBD.setPersonalizado5(dominio.getPersonalizado5());
		dominioBD.setPersonalizado6(dominio.getPersonalizado6());
	}

	public void atualizarLocalizacoesFisicas(Usuario usuarioLogado, String nomeModulo){
		long inicio = Calendar.getInstance().getTimeInMillis();
		Dominio localizacaoFisica = dominioDao.get(Dominio.getLocalizacaoFisica(nomeModulo).getCodigo());

		//valida os 'Campos de Regra'
		if(!this.isCamposRegraLocalizacaoFisicaValidos(nomeModulo, localizacaoFisica))
			throw new BumerangueErrorRuntimeException("Problema na validação dos campos de regra da localização física");
		
		List<ObjetoBumerangue> objetosBumerangue = objetoBumerangueDao.findObjetosBumerangueOrdenadosPor(new Video(),localizacaoFisica.getPersonalizado2()); 

		ElementoDominio compartimentoAtual = new ElementoDominio();
		int posicaoCompartimentoAtual = -1;
		
		for(ObjetoBumerangue objetoBumerangue : objetosBumerangue){

			ElementoDominio compartimentoNovo = this.getCompartimento(localizacaoFisica, compartimentoAtual, objetoBumerangue, posicaoCompartimentoAtual);

			//caso o compartimento novo seja diferente do atual, ou 'SEM_ESPACO'
			boolean diferente = compartimentoNovo != null && !compartimentoNovo.equals(compartimentoAtual);
			if (diferente || ElementoDominio.SEM_ESPACO_COMPARTIMENTO.equals(compartimentoNovo.getPersonalizado3()) ) {
				compartimentoAtual = compartimentoNovo;
				posicaoCompartimentoAtual = 1;
			}

			this.atualizarLocalizacaoFisica(objetoBumerangue, compartimentoAtual, posicaoCompartimentoAtual);

			posicaoCompartimentoAtual++;
		}
		
		//atualiza a data da �ltima atualiza��o e o tempo de dura��o
		long fim = Calendar.getInstance().getTimeInMillis();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String ultimaAtualizacao = df.format(new Date()) + " (duração: "+(fim-inicio)/1000+" segundos)";

		localizacaoFisica.setPersonalizado1(ultimaAtualizacao);

		dominioDao.saveOrUpdate(localizacaoFisica);
    }
	
	/**
	 * Retorna o compartimento correspondente ao objeto passado como par�metro, baseado no compartimento atual.<br>
	 * - Se a posi��o passada como par�metro couber no compartimento atual,<br>
	 * retorna o mesmo compartimento (compartimentoAtual).
	 * - Se o objetoBumerangue corrente n�o participa do grupo do compartimento atual (� de outro grupo),<br>
	 * ent�o retorna o primeiro correspondente ao grupo de compartimento do objeto ou retorna null
	 * - Se a posi��o passada como par�metro for maior do que a comportada no compartimento atual,<br>
	 * retorna o pr�ximo compartimento ou um compartimento SEM_ESPACO (se n�o tiver espa�o dispon�vel)
	 * @param localizacaoFisica
	 * @param compartimentoAtual
	 * @param objetoBumerangue
	 * @param posicaoCompartimentoAtual
	 * @return null, caso n�o haja compartimento para o objeto.
	 */
	private ElementoDominio getCompartimento(Dominio localizacaoFisica, ElementoDominio compartimentoAtual, ObjetoBumerangue objetoBumerangue, int posicaoCompartimentoAtual){
		boolean objetoParticipaGrupo = objetoBumerangue.isParticipanteGrupoCompartimento(compartimentoAtual);
		
		//Se a posi��o passada como par�metro couber no compartimento atual,
		//retorna o mesmo compartimento (compartimentoAtual)
		//Isto s� se d� se o o 'objetoBumerangue' participa do 'compartimentoAtual'
		boolean compartimentoAtualCarregado = posicaoCompartimentoAtual != -1 && compartimentoAtual != null && !ElementoDominio.SEM_ESPACO_COMPARTIMENTO.equals(compartimentoAtual.getPersonalizado3());
		if (compartimentoAtualCarregado
				&& posicaoCompartimentoAtual <= Integer.parseInt(compartimentoAtual.getPersonalizado1())
				&& objetoParticipaGrupo)
			return compartimentoAtual; 
		
		List<ElementoDominio> compartimentosCorrespondentes = this.getCompartimentosCorrespondentesObjetoBumerangue(localizacaoFisica, objetoBumerangue);
		
		//Se o objetoBumerangue corrente n�o participa do grupo do compartimento atual (� de outro grupo),
		//ent�o retorna o primeiro correspondente ao grupo de compartimento do objeto ou retorna null.<br>
		if(!objetoParticipaGrupo){
			return compartimentosCorrespondentes.isEmpty() ? null : compartimentosCorrespondentes.get(0);
		}

		//se o compartimentoAtual for 'SEM_ESPACO' j� retorna
		if( ElementoDominio.SEM_ESPACO_COMPARTIMENTO.equals(compartimentoAtual.getPersonalizado3()) )
			return compartimentoAtual;
		
		for (int i = 0; i < compartimentosCorrespondentes.size(); i++) {
			ElementoDominio compartimento = (ElementoDominio)compartimentosCorrespondentes.get(i);
			
			//localiza o compartimento atual nos compartimentosCorrespondentes, retorna o pr�ximo, se houver.
			//caso n�o haja mais compartimentos, ou seja, j� foi preenchido at� o �ltimo,
			//retorna o �ltimo compartimento marcado como 'ElementoDominio.SEM_ESPACO_COMPARTIMENTO' 
			if(compartimento.getId().equals(compartimentoAtual.getId())){
				if(compartimentosCorrespondentes.size()-1 == i){
					//instancia um novo evitando alterar o objeto persistente 'compartimento'
					ElementoDominio compartimentoSemEspaco = new ElementoDominio();
					compartimentoSemEspaco.setId(compartimento.getId());
					compartimentoSemEspaco.setDominio(compartimento.getDominio());
					compartimentoSemEspaco.setDescricao(compartimento.getDescricao());
					compartimentoSemEspaco.setPersonalizado1(compartimento.getPersonalizado1());
					compartimentoSemEspaco.setPersonalizado3(ElementoDominio.SEM_ESPACO_COMPARTIMENTO);
					return compartimentoSemEspaco;
				}else
					return compartimentosCorrespondentes.get(i+1);
			}
		}
		return null;
	}
    
	/**
	 * Atualiza os atributos 'localizacaoFisica' e 'localizacaoFisicaAnterior' do 'objetoBumerangue'.<br>
	 * De acordo com os par�metros informados.<br>
	 * Em todas as ocasi�es e antes de tudo, o atributo 'localizacaoFisicaAnterior' receber� o valor de<br>
	 * 'localizacaoFisica'.<br>
	 * Se o 'compartimentoAtual' == null ou tiver marcado como 'SEM_ESPACO', o atributo 'localizacaoFisica'<br>
	 * ser� informado como SEM_ESPACO.<br>
	 * Caso contr�rio, este atributo receber� o valor, conforme o padr�o:<br>
	 * 'descricao do compartimentoAtual' - 'posicao no compartimentoAtual' / 'nr. m�ximo de objetos no compartimento'
	 * @param objetoBumerangue
	 * @param compartimentoAtual
	 * @param posicaoCompartimentoAtual
	 */
	private void atualizarLocalizacaoFisica(ObjetoBumerangue objetoBumerangue, ElementoDominio compartimentoAtual, int posicaoCompartimentoAtual){
		objetoBumerangue.setLocalizacaoFisicaAnterior(objetoBumerangue.getLocalizacaoFisica());

		String localizacaoFisica = "";
		if(compartimentoAtual == null || ElementoDominio.SEM_ESPACO_COMPARTIMENTO.equals(compartimentoAtual.getPersonalizado3()) )
			localizacaoFisica = ElementoDominio.SEM_ESPACO_COMPARTIMENTO;
		else{
			String posicao = posicaoCompartimentoAtual+"";
			posicao = Utils.insertFragmment(posicao,"0",compartimentoAtual.getPersonalizado1().length()-posicao.length(),true);
			localizacaoFisica = compartimentoAtual.getDescricao() +" - "+posicao + " / " + compartimentoAtual.getPersonalizado1();
		}

		objetoBumerangue.setLocalizacaoFisica(localizacaoFisica);
		
		//s� salvar, se tiver tido altera��o
		if( objetoBumerangue.getLocalizacaoFisicaAnterior() != null && !objetoBumerangue.getLocalizacaoFisicaAnterior().equals(objetoBumerangue.getLocalizacaoFisica()) )
			objetoBumerangueDao.saveOrUpdate(objetoBumerangue);
	}

	/**
	 * Retorna uma lista de elementos dom�nio, correspondente ao grupo de compartimentos do objeto bumerangue.
	 * @param localizacaoFisica
	 * @param objetoBumerangue
	 * @return
	 */
	private List<ElementoDominio> getCompartimentosCorrespondentesObjetoBumerangue(Dominio localizacaoFisica, ObjetoBumerangue objetoBumerangue){
		List<ElementoDominio> compartimentos = localizacaoFisica.getElementosDominioEmUso();
		List<ElementoDominio> compartimentosCorrespondentes = new ArrayList<ElementoDominio>();
		for(ElementoDominio compartimento : compartimentos)
			if(objetoBumerangue.isParticipanteGrupoCompartimento(compartimento)){
				compartimentosCorrespondentes.add(compartimento);
		}
		
		return compartimentosCorrespondentes;
	}
	
    public LocalizacaoFisicaInfo getLocalizacaoFisicaInfo(Usuario usuarioLogado, String nomeModulo){
		Dominio localizacaoFisica = Dominio.getLocalizacaoFisica(nomeModulo);
		if(localizacaoFisica == null) return null;
		localizacaoFisica = dominioDao.get(localizacaoFisica.getCodigo());

		ObjetoBumerangue objetoBumerangue = Deploy.getObjetoBumeranguePorNomeModulo(nomeModulo);
		LocalizacaoFisicaInfo localizacaoFisicaInfo = new LocalizacaoFisicaInfo();
		
		localizacaoFisicaInfo.setCamposOrdenacao(localizacaoFisica.getPersonalizado2());
		localizacaoFisicaInfo.setGrupoCompartimentos(localizacaoFisica.getPersonalizado3());
		localizacaoFisicaInfo.setTotalCompartimentos(this.getTotalCompartimentos(localizacaoFisica));
		localizacaoFisicaInfo.setTotalObjetosBumerangue(objetoBumerangueDao.getTotalObjetosBumerangue(objetoBumerangue));
		localizacaoFisicaInfo.setTotalObjetosBumerangueSemLocalizacao(objetoBumerangueDao.getTotalObjetosBumerangueSemLocalizacao(objetoBumerangue));
		localizacaoFisicaInfo.setObjetosBumerangueSemEspacoCompartimentos(objetoBumerangueDao.findObjetosBumerangueSemEspacoCompartimentos(objetoBumerangue));
		localizacaoFisicaInfo.setObjetosBumerangueAtualizados(objetoBumerangueDao.findObjetosBumerangueAtualizados(objetoBumerangue));
		localizacaoFisicaInfo.setDataUltimaAtualizacao(localizacaoFisica.getPersonalizado1());
		
		return localizacaoFisicaInfo;
    }

    public List<ObjetoBumerangue> pesquisarObjetosBumerangue(Usuario usuarioLogado, PesquisaObjetoBumerangueCriteria pesquisaObjetoBumerangueCriteria){
    	List<ObjetoBumerangue> objetosBumerangue = objetoBumerangueDao.findObjetosBumerangue(pesquisaObjetoBumerangueCriteria);
    	
    	if(objetosBumerangue.isEmpty() || pesquisaObjetoBumerangueCriteria.getModo().intValue() == PesquisaObjetoBumerangueCriteria.MODO_CADASTRADO.intValue())
    		return objetosBumerangue;
    	
    	//concatenar com os n�o cadastrados
    	List<ObjetoBumerangue> objetosBumerangueConcatenados = new ArrayList<ObjetoBumerangue>();
    	String intervalosFragmentoSequencial = pesquisaObjetoBumerangueCriteria.getFragmentoSequencial();
    	Long seqAux = objetosBumerangue.get(0).getFragmentoSequencialCodigo(intervalosFragmentoSequencial);
    	if(seqAux == null)
    		throw new BumerangueErrorRuntimeException("Erro ao extrair o fragmento sequêncial do código no objeto: "+objetosBumerangue.get(0).getCodigo()+" - "+objetosBumerangue.get(0).getTitulo()+".");

    	for(ObjetoBumerangue objetoBumerangue : objetosBumerangue){
    		Long fragmentoSequencialCodigo = objetoBumerangue.getFragmentoSequencialCodigo(intervalosFragmentoSequencial);
    		int i = 0;
    		while(seqAux < fragmentoSequencialCodigo){
    			ObjetoBumerangue naoCadastrado = new ObjetoBumerangue();
    			naoCadastrado.setCodigo(seqAux+"");
    			objetosBumerangueConcatenados.add(naoCadastrado);
    			seqAux++;

    			//uma preven��o contro um eventual loop infinito.
    			if(++i > objetosBumerangue.size())
    				throw new BumerangueErrorRuntimeException("Erro ao adicionar os objetos não cadastrados. Loop infinito.");
    		}
    		//adiciona o cadastrado, caso seja o modo seja Todos
    		if(pesquisaObjetoBumerangueCriteria.getModo().intValue() == PesquisaObjetoBumerangueCriteria.MODO_TODOS.intValue()){
    			objetosBumerangueConcatenados.add(objetoBumerangue);
    		}
    		seqAux = fragmentoSequencialCodigo + 1;
    	}
    	
    	return objetosBumerangueConcatenados;
    }

    public ResultadoVerificacaoCodigo verificarCodigos(Usuario usuarioLogado, PesquisaObjetoBumerangueCriteria criteria){
    	//extrai os c�digos informados, como String[]
    	String separadorCodigos = StringUtils.replace(criteria.getSeparadorCodigos(),"\\n","\n");
    	String serieCodigos = StringUtils.replace(criteria.getSerieCodigos(),"\r","");
    	String[] codigosInformados = StringUtils.split(serieCodigos,separadorCodigos);
    	
    	if(codigosInformados.length <= 1)
    		throw new BumerangueErrorRuntimeException("é necessário informar mais de 1 código, ou verifique se o campo 'Separador de códigos' está correto.");

    	//percorre a serie de codigos informados, processando o necess�rio
    	//e carrega a lista de objetosBumerangueInformados 
    	List<ObjetoBumerangue> objetosBumerangueInformados = new ArrayList<ObjetoBumerangue>();
    	String menorCodigo = null;
    	String maiorCodigo = null;
    	for(String codigoInformado : codigosInformados){
    		//substitui caracteres
    		codigoInformado = this.getCodigoSubstituido(codigoInformado,criteria.getFragmentoSubstituicao());
    		
    		//atualiza o maior e o menor c�digos informados
    		if(menorCodigo == null || codigoInformado.compareToIgnoreCase(menorCodigo) <= 0)
    			menorCodigo = codigoInformado;
    		if(maiorCodigo == null || codigoInformado.compareToIgnoreCase(maiorCodigo) >= 0)
    			maiorCodigo = codigoInformado;

    		//adiciona o objetoBumerangueInformado
    		ObjetoBumerangue objetoBumerangue = new ObjetoBumerangue();
    		objetoBumerangue.setCodigo(codigoInformado);
    		objetosBumerangueInformados.add(objetoBumerangue);
    	}

    	//carrega a lista de objetosBumerangueCadastrados
    	criteria.setCodigoIni(menorCodigo);
    	criteria.setCodigoFim(maiorCodigo);
    	List<ObjetoBumerangue> objetosBumerangueCadastrados = objetoBumerangueDao.findObjetosBumerangue(criteria);
    	
    	//verifica os c�digos comparativamente
    	ResultadoVerificacaoCodigo resultadoVerificacaoCodigo = this.verificaCodigos(objetosBumerangueInformados, objetosBumerangueCadastrados);
    	
    	//analisa a ordem dos c�digos informados
    	this.analisarOrdemCodigosInformados(resultadoVerificacaoCodigo);

    	resultadoVerificacaoCodigo.calcularTotais();
    	return resultadoVerificacaoCodigo;
    }

    private String getCodigoSubstituido(String codigo, String fragmentoSubstituicao){
    	String[] partes = StringUtils.split(fragmentoSubstituicao,'*');
    	for(String parte : partes) {
    		parte = parte.trim();
    		if(StringUtils.split(parte,'&').length != 2)
    			continue;
    		String de = StringUtils.split(parte,'&')[0];
    		String para = StringUtils.split(parte,'&')[1];
    		codigo = StringUtils.replace(codigo,de,para);
		}
    	return codigo.trim();
    }
    
    private ResultadoVerificacaoCodigo verificaCodigos(List<ObjetoBumerangue> informados, List<ObjetoBumerangue> cadastrados){
    	ResultadoVerificacaoCodigo resultadoVerificacaoCodigo = new ResultadoVerificacaoCodigo();

    	//percorre os cadastrados, desempilhando e adicionando no resultado comparativemente
    	while(!cadastrados.isEmpty()){
    		String codigoCadastrado = cadastrados.get(0).getCodigo();
    		String codigoInformado = informados.isEmpty() ? "" : informados.get(0).getCodigo();

    		if(informados.size() == 0){
    			resultadoVerificacaoCodigo.addElementoVerificacaoCodigo(cadastrados.get(0),null);
    			cadastrados.remove(0);
    		}else if( codigoCadastrado.equalsIgnoreCase(codigoInformado)){
    			resultadoVerificacaoCodigo.addElementoVerificacaoCodigo(cadastrados.get(0),informados.get(0));
    			cadastrados.remove(0);
    			informados.remove(0);
    		}else if( codigoCadastrado.compareToIgnoreCase(codigoInformado) < 0){
    			resultadoVerificacaoCodigo.addElementoVerificacaoCodigo(cadastrados.get(0),null);
    			cadastrados.remove(0);
    		}else if( codigoCadastrado.compareToIgnoreCase(codigoInformado) > 0){
    			resultadoVerificacaoCodigo.addElementoVerificacaoCodigo(null,informados.get(0));
    			informados.remove(0);
    		}
    	}

    	//percorre os informados, caso haja, desempilhando e adicionando no resultado
    	while(!informados.isEmpty()){
			resultadoVerificacaoCodigo.addElementoVerificacaoCodigo(null,informados.get(0));
			informados.remove(0);
    	}
    	
    	return resultadoVerificacaoCodigo;
    }
    
    private void analisarOrdemCodigosInformados(ResultadoVerificacaoCodigo resultadoVerificacaoCodigo){
    	List<ObjetoBumerangue> informados = resultadoVerificacaoCodigo.getObjetosBumerangueInformados();
    	List<ObjetoBumerangue> informadosOrdenados = resultadoVerificacaoCodigo.getObjetosBumerangueInformadosOrdenados();
    	
    	int i =0;
    	while(i < informados.size()){
    		//verifica se o objeto informado em i n�o corresponde ao ordenado em i
    		ObjetoBumerangue informado = informados.get(i);
    		if(!informado.getCodigo().equalsIgnoreCase(informadosOrdenados.get(i).getCodigo())){
    			//marca o objeto como fora de ordem
    			int posicaoCorreta = this.getPosicaoObjetoBumerangue(informado, informadosOrdenados);
    			resultadoVerificacaoCodigo.setObjetoBumerangueInformado(informado, posicaoCorreta+1);
    			
    			//enquanto o pr�ximo informado for o pr�ximo na ordem, pula um informado
    			while(i+1 < informados.size() && posicaoCorreta+1 < informados.size() && informados.get(i+1).getCodigo().equalsIgnoreCase(informadosOrdenados.get(posicaoCorreta+1).getCodigo())){
    				i++;
    				posicaoCorreta++;
    			}
    		}
    		i++;
    	}
    }
    
    private int getPosicaoObjetoBumerangue(ObjetoBumerangue objetoBumerangueInformado, List<ObjetoBumerangue> objetosBumerangueInformadosOrdenados){
    	if(objetoBumerangueInformado == null || objetosBumerangueInformadosOrdenados == null)
    		return -1;

    	for(int i = 0; i < objetosBumerangueInformadosOrdenados.size(); i++){
    		ObjetoBumerangue objetoBumerangue = objetosBumerangueInformadosOrdenados.get(i);
    		if(objetoBumerangue.getCodigo().equalsIgnoreCase(objetoBumerangueInformado.getCodigo()))
    			return i;
    	}

    	return -1;
    }
    
    public ResultadoVerificacaoCodigo reordenarCodigosInformados(Usuario usuarioLogado, PesquisaObjetoBumerangueCriteria pesquisaObjetoBumerangueCriteria){
    	ResultadoVerificacaoCodigo resultadoVerificacaoCodigo = this.verificarCodigos(usuarioLogado, pesquisaObjetoBumerangueCriteria);

    	List<ObjetoBumerangue> informadosOrdenados = resultadoVerificacaoCodigo.getObjetosBumerangueInformadosOrdenados();
    	List<ObjetoBumerangue> cadastrados = resultadoVerificacaoCodigo.getObjetosBumerangueCadastrados();

    	resultadoVerificacaoCodigo = this.verificaCodigos(informadosOrdenados, cadastrados);
    	
    	resultadoVerificacaoCodigo.calcularTotais();
    	return resultadoVerificacaoCodigo;
	}

    /**
     * Retorna o somat�rio das quantidades dos compartimentos (em uso) da 'localizacaoFisica'.<br>
     * @author Adriano Carvalho
     * @param localizacaoFisica
     * @return
     */
    private Integer getTotalCompartimentos(Dominio localizacaoFisica){
    	int totalCompartimentos = 0;
    	for(ElementoDominio compartimento : localizacaoFisica.getElementosDominioEmUso()){
    		totalCompartimentos += Integer.parseInt(compartimento.getPersonalizado1());
    	}
    	return totalCompartimentos;
    }

	private boolean isCamposRegraLocalizacaoFisicaValidos(String nomeModulo, Dominio localizacaoFisica){
		//os campos s�o requeridos
		if(!ValidationRules.isInformed(localizacaoFisica.getPersonalizado2()))
			throw new BumerangueErrorRuntimeException("O campo 'Campos de ordenação' deve está preenchido.");
		if(!ValidationRules.isInformed(localizacaoFisica.getPersonalizado3()))
			throw new BumerangueErrorRuntimeException("O campo 'Grupo de compartimentos' deve está preenchido.");
		
		//valida o campo 'Campos de ordena��o'
		//Padr�o: localizacaoFisica.getPersonalizado2() = 'atributo objetoBumerangue 1' [desc], 'atributo objetoBumerangue 2' [desc], ...
		String[] campoOrdenacao = localizacaoFisica.getPersonalizado2().split(",");
		ObjetoBumerangue objetoBumerangue = Deploy.getObjetoBumeranguePorNomeModulo(nomeModulo);
		for(String nomeCampoComOrdenacao : campoOrdenacao){
			String nomeCampo = nomeCampoComOrdenacao.trim();
			if(nomeCampo.toLowerCase().endsWith("desc"))
				nomeCampo = nomeCampo.substring(0,nomeCampo.length()-4).trim();
			if( !Utils.isAtributoExistente(objetoBumerangue, nomeCampo) )
				throw new BumerangueErrorRuntimeException("O atributo '"+nomeCampo+"' não existe na classe '"+objetoBumerangue.getClass().getSimpleName()+"'");
		}
		
		//valida o campo 'Grupo de compartimentos'
		//Padr�o: localizacaoFisica.getPersonalizado3() = 'numero ou *'-'atributo objetoBumerangue'
		String grupoCompartimentos = localizacaoFisica.getPersonalizado3();
		String[] partes = grupoCompartimentos.split("-");
		if(partes.length != 2)
			throw new BumerangueErrorRuntimeException("O campo 'Grupo de compartimentos' deve está no padrão: 'quantidade das primeiras letras do atributo ou *' - 'atributo'" );
		String totalPrefixo = partes[0].trim();
		String atributo = partes[1].trim();
		try{
			if(!totalPrefixo.equals("*"))
				new Integer(totalPrefixo);
		}catch (NumberFormatException e) {
			throw new BumerangueErrorRuntimeException("O valor antes do '-' (hífem) deve ser um inteiro ou '*'.");
		}
		if( !Utils.isAtributoExistente(objetoBumerangue, atributo) )
			throw new BumerangueErrorRuntimeException("O atributo '"+atributo+"' não existe na classe '"+objetoBumerangue.getClass().getSimpleName());

		return true; 
	}
}