package br.org.ceu.bumerangue.plugins.filmeComercial.controller.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import br.org.ceu.bumerangue.controller.web.MultiActionBaseController;
import br.org.ceu.bumerangue.entity.Dominio;
import br.org.ceu.bumerangue.entity.ElementoDominio;
import br.org.ceu.bumerangue.entity.FilmeComercial;
import br.org.ceu.bumerangue.entity.Permissao;
import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.entity.criterias.PesquisaFilmeComercialCriteria;
import br.org.ceu.bumerangue.entity.suporte.deploy.Deploy;
import br.org.ceu.bumerangue.service.BumerangueService;
import br.org.ceu.bumerangue.service.exceptions.BumerangueErrorRuntimeException;
import br.org.ceu.bumerangue.service.exceptions.BumerangueSuccessRuntimeException;
import br.org.ceu.bumerangue.util.SessionContainer;
import br.org.ceu.bumerangue.util.ValidationRules;

public class ManterFilmeComercialController extends MultiActionBaseController {
	
	/** Service */
	private BumerangueService bumerangueService;
	public void setBumerangueService(BumerangueService bumerangueService){ this.bumerangueService = bumerangueService; }
	
	public boolean isPertencenteModuloDisponivel(){
		return Deploy.isModuloDisponivel(Deploy.MODULO_FILME_COMERCIAL);
	}

	public boolean isMetodoAutorizado(){
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		if(usuario == null || !usuario.isInRole(Permissao.ADMIN_FILME_COMERCIAL)){
			//m�todos autorizados para usu�rios avan�ados, b�sicos e n�o logados
			String metodosAutorizados = ",detalhar,pesquisar,pesquisarPre,";

			//s� para usu�rio logado
			if(usuario != null)
				metodosAutorizados += ("baixar,");

			String metodo = ","+getParam("method")+",";
			return metodosAutorizados.indexOf(metodo) != -1;
		}
		return true;
	}

	public ModelAndView validateMulti(HttpServletRequest request, HttpServletResponse response) {
		//realiza a autoriza��o no n�vel de m�todo, desconsidera a autentica��o no web.xml
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		if(usuario == null || !usuario.isInRole(Permissao.ADMIN_FILME_COMERCIAL)){
			//m�todos autorizados para todos os usu�rios
			String metodosAutorizados = ",baixar,detalhar,pesquisar,pesquisarPre,";
			String metodo = ","+getParam("method")+",";
			if(metodosAutorizados.indexOf(metodo) == -1) throw new BumerangueErrorRuntimeException("é necessário ter permissão para acessar a função: "+getParam("method"));
		}

		if (getParam("method").equals("pesquisar")) {
	    	if(ValidationRules.isInformed(getParam("codigo"))) isInteger("codigo","Código");
	    	if(ValidationRules.isInformed(getParam("titulo"))) isValidRange("titulo",3,-1,"Título");
	    	if(ValidationRules.isInformed(getParam("localidade"))) isValidRange("localidade",3,-1,"Localidade");
	    	if(ValidationRules.isInformed(getParam("dataIni"))) isDateDDMMYYYY("dataIni","Data a partir de");
	    	if(ValidationRules.isInformed(getParam("dataFim"))) isDateDDMMYYYY("dataFim","Data até");
	    	if(ValidationRules.isInformed(getParam("palavrasChave"))) isValidRange("palavrasChaves",3,-1,"Palavras-Chaves");

			//carrrega o form s� se existir erro
			if (!getErrorMessages().isEmpty()){
				request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			}
	    	return this.pesquisarPre(request,response);			
		}else if (getParam("method").equals("editar") || getParam("method").equals("inserir")) {
			super.isInformed("codigo","Código");
			super.isInteger("codigo","Código");
			super.isInformed("titulo","T�tulo");
			if(ValidationRules.isInformed(getParam("duracaoMinutos")))super.isInteger("duracaoMinutos","Duração");
			if(ValidationRules.isInformed(getParam("ano")))super.isInteger("ano","Ano");

			//carrrega o form s� se existir erro
			if (!getErrorMessages().isEmpty()){
				request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			}
			return this.editarPre(request,response);			
		}
		return null;
	}

	public ModelAndView pesquisarPre(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		PesquisaFilmeComercialCriteria pesquisaFilmeComercialCriteria = new PesquisaFilmeComercialCriteria();

		//verifica se o objeto deve ser atualizado com os dados do form
		if(request.getAttribute(super.UPDATE_FROM_FORM) != null) this.updateFromForm(pesquisaFilmeComercialCriteria) ;		

		Dominio dominioCategoria = bumerangueService.getDominio(usuario,Dominio.FILME_COMERCIAL_CATEGORIA.getCodigo());
		Dominio dominioPublico = bumerangueService.getDominio(usuario,Dominio.FILME_COMERCIAL_PUBLICO.getCodigo());
		
		//limpa a listagem, se for o caso.
		if(!getParamAsBoolean(SessionContainer.REQUEST_ATTRIBUTE_PAGINACAO).booleanValue()){
			getSessionContainer().setObjetosBumerangue(null);
		}

		addObject("categorias",dominioCategoria.getElementosDominio());
		addObject("publicos",dominioPublico.getElementosDominio());
		addObject("pesquisaFilmeComercialCriteria",pesquisaFilmeComercialCriteria);

		return new ModelAndView("filmeComercialPesquisarFilmeComercial");
	}

	@SuppressWarnings("unchecked")
	public ModelAndView pesquisar(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		PesquisaFilmeComercialCriteria pesquisaFilmeComercialCriteria = new PesquisaFilmeComercialCriteria();
		this.updateFromForm(pesquisaFilmeComercialCriteria);		

		//evita ir novamente no banco.
		if(!getParamAsBoolean(SessionContainer.REQUEST_ATTRIBUTE_PAGINACAO).booleanValue()){
			@SuppressWarnings("rawtypes")
			List filmesComerciais = bumerangueService.pesquisarFilmesComerciais(usuario,pesquisaFilmeComercialCriteria);
			getSessionContainer().setObjetosBumerangue(filmesComerciais);
		}

		addObject("pesquisaFilmeComercialCriteria",pesquisaFilmeComercialCriteria);
		addObject("escondeFiltroPesquisa",Boolean.TRUE);
		return new ModelAndView("filmeComercialPesquisarFilmeComercial");
	}

	public ModelAndView inserir(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();

		FilmeComercial filmeComercial = new FilmeComercial();
		updateFromForm(filmeComercial);

		bumerangueService.inserirObjetoBumerangue(usuario,filmeComercial);

		super.addSuccess(BumerangueSuccessRuntimeException.INSERCAO_REALIZADA);
		request.setAttribute("id",filmeComercial.getId());
		return this.detalhar(request, response);
	}

	public ModelAndView editarPre(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		String id = request.getAttribute("id") != null ? request.getAttribute("id")+"" : getParam("id");

		//recupera do banco
		FilmeComercial filmeComercial = (FilmeComercial)bumerangueService.getObjetoBumerangue(usuario,id);
		
		//se ainda n�o tiver no banco, instancia um objeto novo
		if(filmeComercial == null){
			filmeComercial = new FilmeComercial();
			filmeComercial.setCodigoFormatado(bumerangueService.getProximoCodigoFilmeComercial());
		}

		//verifica se o objeto deve ser atualizado com os dados do form
		if(request.getAttribute(super.UPDATE_FROM_FORM) != null) updateFromForm(filmeComercial) ;		
		
		//coloca os objetos no request
		Dominio dominioCategoria = bumerangueService.getDominio(usuario,Dominio.FILME_COMERCIAL_CATEGORIA.getCodigo());
		Dominio dominioPublico = bumerangueService.getDominio(usuario,Dominio.FILME_COMERCIAL_PUBLICO.getCodigo());

		addObject("filmeComercial",filmeComercial);
		addObject("categorias",dominioCategoria.getElementosDominio());
		addObject("publicos",dominioPublico.getElementosDominio());

		return new ModelAndView("filmeComercialEditarFilmeComercial");
	}

	public ModelAndView editar(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		
		FilmeComercial filmeComercial= new FilmeComercial();
		this.updateFromForm(filmeComercial);
		
		bumerangueService.editarObjetoBumerangue(usuario,filmeComercial);
		
		super.addSuccess(BumerangueSuccessRuntimeException.ALTERACAO_REALIZADA);
		return this.detalhar(request, response);
	}

	public ModelAndView detalhar(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		String id = request.getAttribute("id") != null ? request.getAttribute("id")+"" : getParam("id");

		FilmeComercial filmeComercial= (FilmeComercial)bumerangueService.getObjetoBumerangue(usuario,id);
		addObject("filmeComercial",filmeComercial);

		return new ModelAndView("filmeComercialDetalharFilmeComercial");
	}

	public ModelAndView excluir(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		
		FilmeComercial filmeComercial = new FilmeComercial();
		filmeComercial.setId(getParam("id"));
		
		bumerangueService.excluirObjetoBumerangue(usuario,filmeComercial);

		super.addSuccess(BumerangueSuccessRuntimeException.EXCLUSAO_REALIZADA);
		return pesquisarPre(request,response);
	}

	public ModelAndView baixar(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		String id = getParam("id");
		int parte = getParamAsInteger("parte");

		FilmeComercial filmeComercial= (FilmeComercial)bumerangueService.getObjetoBumerangue(usuario,id);
		addObject("filmeComercial",filmeComercial);
		
		try {
			String urlDownload = filmeComercial.getURLDownload(parte);
			if(urlDownload != null)
				response.sendRedirect(urlDownload);
			else
				super.addAlert("Download n�o realizado.<br>Tente em: " + filmeComercial.getLinksDownloadAsArray()[parte]);

		} catch (Exception e) {
			throw new BumerangueErrorRuntimeException(e.getMessage());
		}

		return new ModelAndView("filmeComercialDetalharFilmeComercial");
	}

	private void updateFromForm(PesquisaFilmeComercialCriteria pesquisaFilmeComercialCriteria) {
		pesquisaFilmeComercialCriteria.setTitulo(getParam("titulo"));
		pesquisaFilmeComercialCriteria.setPublico(getParam("idPublico"));
		pesquisaFilmeComercialCriteria.setDuracaoMinutosIni(getParam("duracaoMinutosIni"));
		pesquisaFilmeComercialCriteria.setDuracaoMinutosFim(getParam("duracaoMinutosFim"));
		pesquisaFilmeComercialCriteria.setAnoIni(getParam("anoIni"));
		pesquisaFilmeComercialCriteria.setAnoFim(getParam("anoFim"));
		pesquisaFilmeComercialCriteria.setCategoria(getParam("idCategoria"));
		pesquisaFilmeComercialCriteria.setDiretor(getParam("diretor"));
		pesquisaFilmeComercialCriteria.setAtoresObservacoes(getParam("atoresObservacoes"));
		pesquisaFilmeComercialCriteria.setNumeroMaisRecentes(getParam("numeroMaisRecentes"));
		pesquisaFilmeComercialCriteria.setDisponivelDownload(getParamAsBoolean("disponivelDownload"));
	}

	private void updateFromForm(FilmeComercial filmeComercial) {
		filmeComercial.setId(getParam("id"));
		filmeComercial.setTitulo(getParam("titulo"));
		filmeComercial.setCodigo(getParam("codigo"));
		filmeComercial.setTituloOriginal(getParam("tituloOriginal"));
		filmeComercial.setPublico(new ElementoDominio(getParam("idPublico")));
		filmeComercial.setDuracaoMinutos(getParamAsInteger("duracaoMinutos"));
		filmeComercial.setAno(getParamAsInteger("ano"));
		filmeComercial.setCategoria(new ElementoDominio(getParam("idCategoria")));
		filmeComercial.setDiretor(getParam("diretor"));
		filmeComercial.setAtoresObservacoes(getParam("atoresObservacoes"));
		filmeComercial.setLinkSinopse(getParam("linkSinopse"));
		filmeComercial.setLinksDownload(getParam("linksDownload"));
	}

}