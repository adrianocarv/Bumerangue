package br.org.ceu.bumerangue.plugins.livroCultural.controller.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import br.org.ceu.bumerangue.controller.web.MultiActionBaseController;
import br.org.ceu.bumerangue.entity.Dominio;
import br.org.ceu.bumerangue.entity.ElementoDominio;
import br.org.ceu.bumerangue.entity.Emprestimo;
import br.org.ceu.bumerangue.entity.LivroCultural;
import br.org.ceu.bumerangue.entity.Permissao;
import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.entity.Video;
import br.org.ceu.bumerangue.entity.criterias.PesquisaLivroCulturalCriteria;
import br.org.ceu.bumerangue.entity.suporte.deploy.Deploy;
import br.org.ceu.bumerangue.service.BumerangueService;
import br.org.ceu.bumerangue.service.exceptions.BumerangueAlertRuntimeException;
import br.org.ceu.bumerangue.service.exceptions.BumerangueErrorRuntimeException;
import br.org.ceu.bumerangue.service.exceptions.BumerangueSuccessRuntimeException;
import br.org.ceu.bumerangue.util.SessionContainer;
import br.org.ceu.bumerangue.util.ValidationRules;

public class ManterLivroCulturalController extends MultiActionBaseController {
	
	/** Service */
	private BumerangueService bumerangueService;
	public void setBumerangueService(BumerangueService bumerangueService){ this.bumerangueService = bumerangueService; }
	
	public boolean isPertencenteModuloDisponivel(){
		return Deploy.isModuloDisponivel(Deploy.MODULO_LIVRO_CULTURAL);
	}

	public boolean isMetodoAutorizado(){
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		if(!usuario.isInRole(Permissao.ADMIN_LIVRO_CULTURAL)){
			//m�todos autorizados para usu�rios avan�ados e b�sicos
			String metodosAutorizados = ",detalhar,pesquisar,pesquisarPre,";
			String metodo = ","+getParam("method")+",";
			return metodosAutorizados.indexOf(metodo) != -1;
		}
		return true;
	}

	public ModelAndView validateMulti(HttpServletRequest request, HttpServletResponse response) {
		if (getParam("method").equals("pesquisar")) {
	    	if(ValidationRules.isInformed(getParam("dataIni"))) isDateDDMMYYYY("dataIni","Data a partir de");
	    	if(ValidationRules.isInformed(getParam("dataFim"))) isDateDDMMYYYY("dataFim","Data até");

			//carrrega o form s� se existir erro
			if (!getErrorMessages().isEmpty()){
				request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			}
	    	return this.pesquisarPre(request,response);			
		}else if (getParam("method").equals("editar") || getParam("method").equals("inserir")) {
			super.isInformed("codigo","Código");
			super.isInformed("titulo","Título");
			super.isInformed("autor", "Autor");
			super.isInformed("idIdioma", "Idioma");
			
			//carrrega o form s� se existir erro
			if (!getErrorMessages().isEmpty()){
				request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			}
			return this.editarPre(request,response);			
		}else if (getParam("method").equals("emprestar")) {
	    	super.isInformed("idUsuarioBibliotecaCultural","Usuário");
	    	if(super.isInformed("dataEmprestimo","Data do Empréstimo"))
				super.isDateDDMMYYYY("dataEmprestimo","Data do Empréstimo");
			
			//carrrega o form s� se existir erro
			if (!getErrorMessages().isEmpty()){
				request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			}
			return this.emprestarPre(request,response);			
		}else if (getParam("method").equals("devolver")) {
			if(super.isInformed("dataDevolucao","Data da Devolução"))
				super.isDateDDMMYYYY("dataDevolucao","Data da Devolução");

			//carrrega o form s� se existir erro
			if (!getErrorMessages().isEmpty()){
				request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			}
			return this.devolverPre(request,response);			
		}else if (getParam("method").equals("editarDevolucao")) {
	    	super.setValidationViewName("livroCulturalEditarDevolucao");
			if(super.isInformed("dataDevolucao","Data da Devolução"))
				super.isDateDDMMYYYY("dataDevolucao","Data da Devolução");
			if(ValidationRules.isInformed(getParam("numeroAssistentesVideo")))super.isInteger("numeroAssistentesVideo","Número de assistentes");

			//carrrega o form s� se existir erro
			if (!getErrorMessages().isEmpty()){
				request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			}
			return this.editarDevolucaoPre(request,response);			
		}
		return null;
	}

	public ModelAndView pesquisarPre(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		PesquisaLivroCulturalCriteria pesquisaLivroCulturalCriteria = new PesquisaLivroCulturalCriteria();

		//verifica se o objeto deve ser atualizado com os dados do form
		if(request.getAttribute(super.UPDATE_FROM_FORM) != null) this.updateFromForm(pesquisaLivroCulturalCriteria) ;		

		Dominio dominioIdioma = bumerangueService.getDominio(usuario,Dominio.LIVRO_CULTURAL_IDIOMA.getCodigo());
		List usuariosBibliotecaCultura = new ArrayList();
		if(usuario.isInRole(Permissao.BASICO_LIVRO_CULTURAL))
			usuariosBibliotecaCultura.add(usuario);
		else{
			usuariosBibliotecaCultura = bumerangueService.findUsuarios(usuario,null,true,Permissao.BASICO_LIVRO_CULTURAL);
			usuariosBibliotecaCultura.addAll(bumerangueService.findUsuarios(usuario,null,true,Permissao.AVANCADO_LIVRO_CULTURAL));
			usuariosBibliotecaCultura.addAll(bumerangueService.findUsuarios(usuario,null,true,Permissao.ADMIN_LIVRO_CULTURAL));
		}

		//limpa a listagem, se for o caso.
		if(!getParamAsBoolean(SessionContainer.REQUEST_ATTRIBUTE_PAGINACAO).booleanValue()){
			getSessionContainer().setObjetosBumerangue(null);
		}

		addObject("idiomas",dominioIdioma.getElementosDominio());
		addObject("usuariosBibliotecaCultura",usuariosBibliotecaCultura);
		addObject("pesquisaLivroCulturalCriteria",pesquisaLivroCulturalCriteria);

		return new ModelAndView("livroCulturalPesquisarLivroCultural");
	}

	public ModelAndView pesquisar(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		PesquisaLivroCulturalCriteria pesquisaLivroCulturalCriteria = new PesquisaLivroCulturalCriteria();
		this.updateFromForm(pesquisaLivroCulturalCriteria);		

		if(pesquisaLivroCulturalCriteria.isEmpty()) throw new BumerangueAlertRuntimeException("é necessário informar pelos menos 1 campo.");
		
		List livrosCulturais = new ArrayList();
		//evita ir novamente no banco.
		if(!getParamAsBoolean(SessionContainer.REQUEST_ATTRIBUTE_PAGINACAO).booleanValue()){
			livrosCulturais = bumerangueService.pesquisarLivrosCulturais(usuario,pesquisaLivroCulturalCriteria);
			getSessionContainer().setObjetosBumerangue(livrosCulturais);
		}

		//se retornar apenas um elemento, vai direto para tela de detalhes
		if(livrosCulturais.size() == 1){
			LivroCultural LivroCultural = (LivroCultural)bumerangueService.getObjetoBumerangue(usuario,((LivroCultural)livrosCulturais.get(0)).getId());
			request.setAttribute("id",LivroCultural.getId());
			return detalhar(request,response);
		}
		
		addObject("pesquisaLivroCulturalCriteria",pesquisaLivroCulturalCriteria);

		return new ModelAndView("livroCulturalPesquisarLivroCultural");
	}

	public ModelAndView paginar(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
		return pesquisarPre(request,response);
	}

	public ModelAndView inserir(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();

		LivroCultural livroCultural = new LivroCultural();
		updateFromForm(livroCultural);		
		try{
			bumerangueService.inserirObjetoBumerangue(usuario,livroCultural);
		}catch(BumerangueErrorRuntimeException e){
			super.addError(e.getMessage());
			request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			return this.editarPre(request, response);
		}
		
		
		super.addSuccess(BumerangueSuccessRuntimeException.INSERCAO_REALIZADA);
		request.setAttribute("id",livroCultural.getId());
		return this.detalhar(request, response);
	}

	public ModelAndView copiar(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();

		LivroCultural livroCultural = new LivroCultural();
		livroCultural.setId(getParam("id"));
		livroCultural = (LivroCultural)bumerangueService.copiarObjetoBumerangue(usuario, livroCultural);

		super.addSuccess(BumerangueSuccessRuntimeException.COPIA_REALIZADA);
		request.setAttribute("id",livroCultural.getId());
		return editarPre(request,response);
	}

	public ModelAndView editarPre(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		String id = request.getAttribute("id") != null ? request.getAttribute("id")+"" : getParam("id");

		//recupera do banco
		LivroCultural livroCultural = (LivroCultural)bumerangueService.getObjetoBumerangue(usuario,id);
		
		//se ainda n�o tiver no banco, instancia um objeto novo
		if(livroCultural == null){
			livroCultural = new LivroCultural();
		}

		//verifica se o objeto deve ser atualizado com os dados do form
		if(request.getAttribute(super.UPDATE_FROM_FORM) != null) updateFromForm(livroCultural) ;		
		
		//coloca os objetos no request
		Dominio dominioIdioma = bumerangueService.getDominio(usuario,Dominio.LIVRO_CULTURAL_IDIOMA.getCodigo());
		
		addObject("livroCultural",livroCultural);
		addObject("idiomas",dominioIdioma.getElementosDominio());

		return new ModelAndView("livroCulturalEditarLivroCultural");
	}

	public ModelAndView editar(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		
		LivroCultural livroCultural = new LivroCultural();
		this.updateFromForm(livroCultural);
		
		try{
			bumerangueService.editarObjetoBumerangue(usuario,livroCultural);
		} catch (BumerangueErrorRuntimeException e){
			super.addError(e.getMessage());
			request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			return editarPre(request, response);
		}
		super.addSuccess(BumerangueSuccessRuntimeException.ALTERACAO_REALIZADA);
		return this.detalhar(request, response);
	}

	public ModelAndView detalhar(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		String id = request.getAttribute("id") != null ? request.getAttribute("id")+"" : getParam("id");

		LivroCultural livroCultural = (LivroCultural)bumerangueService.getObjetoBumerangue(usuario,id);
		addObject("livroCultural",livroCultural);

		return new ModelAndView("livroCulturalDetalharLivroCultural");
	}

	public ModelAndView excluir(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		
		LivroCultural livroCultural = new LivroCultural();
		livroCultural.setId(getParam("id"));
		
		bumerangueService.excluirObjetoBumerangue(usuario,livroCultural);

		//verifica se foi exclu�do, ou ficou fora de uso
    	livroCultural = (LivroCultural)bumerangueService.getObjetoBumerangue(usuario,livroCultural.getId());
    	if(livroCultural != null) throw new BumerangueAlertRuntimeException("O livro foi colocado como fora de uso, por possuir empréstimos.");

		super.addSuccess(BumerangueSuccessRuntimeException.EXCLUSAO_REALIZADA);
		return pesquisarPre(request,response);
	}

	public ModelAndView emprestarPre(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		String id = getParam("id");
		Emprestimo emprestimo = new Emprestimo();

		//verifica se o objeto deve ser atualizado com os dados do form
		if(request.getAttribute(super.UPDATE_FROM_FORM) != null){
			this.updateFromForm(emprestimo) ;		
		}else{
			emprestimo.setDataEmprestimo(new Date());
		}

		LivroCultural livroCultural = (LivroCultural)bumerangueService.getObjetoBumerangue(usuario,id);
		List usuarios = bumerangueService.findUsuarios(usuario,null,true,Permissao.BASICO_LIVRO_CULTURAL);
		usuarios.addAll(bumerangueService.findUsuarios(usuario,null,true,Permissao.AVANCADO_LIVRO_CULTURAL));
		usuarios.addAll(bumerangueService.findUsuarios(usuario,null,true,Permissao.ADMIN_LIVRO_CULTURAL));

		addObject("livroCultural",livroCultural);
		addObject("usuarios",usuarios);
		addObject("emprestimo",emprestimo);

		return new ModelAndView("livroCulturalEmprestarLivroCultural");
	}

	public ModelAndView emprestar(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		
		LivroCultural livroCultural = new LivroCultural();
		livroCultural.setId(getParam("id"));

		Emprestimo emprestimo = new Emprestimo();
		this.updateFromForm(emprestimo);
		
		bumerangueService.emprestarObjetoBumerangue(usuario,livroCultural,emprestimo);
		
		super.addSuccess(BumerangueSuccessRuntimeException.EMPRESTIMO_REALIZADO);
		return detalhar(request,response);
	}

	public ModelAndView devolverPre(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		String id = getParam("id");
		Emprestimo devolucao = new Emprestimo();

		LivroCultural livroCultural = (LivroCultural)bumerangueService.getObjetoBumerangue(usuario,id);

		//verifica se o objeto deve ser atualizado com os dados do form
		if(request.getAttribute(super.UPDATE_FROM_FORM) != null){
			this.updateFromForm(devolucao) ;		
			livroCultural.setPalavrasChaves(getParam("palavrasChaves"));
			livroCultural.setObservacoesGerais(getParam("observacoesGerais"));
		}else{
			devolucao.setDataDevolucao(new Date());
		}

		addObject("livroCultural",livroCultural);
		addObject("devolucao",devolucao);
		return new ModelAndView("livroCulturalDevolverLivroCultural");
	}

	public ModelAndView devolver(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		
		LivroCultural livroCultural = new LivroCultural();
		this.updateFromForm(livroCultural);

		Emprestimo devolucao = new Emprestimo();
		this.updateFromForm(devolucao);
		
		try{
			bumerangueService.devolverObjetoBumerangue(usuario,livroCultural,devolucao);
		}catch (BumerangueErrorRuntimeException e) {
			super.addError(e.getMessage());
			request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			return devolverPre(request, response);
		}
		
		super.addSuccess(BumerangueSuccessRuntimeException.DEVOLUCAO_REALIZADA);
		return detalhar(request,response);
	}

	public ModelAndView cancelarEmprestimo(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		
		LivroCultural livroCultural = new LivroCultural();
		livroCultural.setId(getParam("id"));

		bumerangueService.cancelarEmprestimoObjetoBumerangue(usuario,livroCultural);
		
		super.addSuccess(BumerangueSuccessRuntimeException.CANCELAMENTO_REALIZADO);
		return detalhar(request,response);
	}

	public ModelAndView editarDevolucaoPre(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		String id = getParam("id");

		Emprestimo emprestimo = (Emprestimo)bumerangueService.getEmprestimo(usuario,id);
		Video video = (Video)bumerangueService.getObjetoBumerangue(usuario,emprestimo.getObjetoBumerangue().getId());

		//verifica se o objeto deve ser atualizado com os dados do form
		if(request.getAttribute(super.UPDATE_FROM_FORM) != null) this.updateFromForm(emprestimo) ;		

		addObject("emprestimo",emprestimo);
		addObject("video",video);

		return new ModelAndView("videoEditarDevolucao");
	}

	public ModelAndView editarDevolucao(HttpServletRequest request, HttpServletResponse response) {
		
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		
		Emprestimo devolucao = new Emprestimo();
		devolucao.setId(getParam("id"));
		devolucao.setDataDevolucao(getParamAsDate("dataDevolucao"));
		devolucao.setObservacoesDevolucao(getParam("observacoesDevolucao"));
		devolucao.setTipoAtividadeVideo(getParam("tipoAtividadeVideo"));
		devolucao.setPublicoVideo(getParam("publicoVideo"));
		devolucao.setNumeroAssistentesVideo(getParamAsInteger("numeroAssistentesVideo"));
		
		bumerangueService.editarDevolucaoObjetoBumerangue(usuario,devolucao);
		
		//ManterVideoEditarDevolucaoPreController
		Emprestimo emprestimo = (Emprestimo)bumerangueService.getEmprestimo(usuario,getParam("id"));
		Video video = (Video)bumerangueService.getObjetoBumerangue(usuario,emprestimo.getObjetoBumerangue().getId());
		addObject("emprestimo",emprestimo);
		addObject("video",video);

		super.addSuccess(BumerangueSuccessRuntimeException.ALTERACAO_REALIZADA);
		return new ModelAndView("videoDetalharEmprestimo");
	}

	public ModelAndView cancelarDevolucao(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		
		Emprestimo devolucao = new Emprestimo();
		devolucao.setId(getParam("id"));

		bumerangueService.cancelarDevolucaoObjetoBumerangue(usuario,devolucao);
		
		//ManterVideoEditarDevolucaoPreController
		Emprestimo emprestimo = (Emprestimo)bumerangueService.getEmprestimo(usuario,getParam("id"));
		LivroCultural livroCultural = (LivroCultural)bumerangueService.getObjetoBumerangue(usuario,emprestimo.getObjetoBumerangue().getId());
		addObject("emprestimo",emprestimo);
		addObject("livroCultural",livroCultural);

		super.addSuccess(BumerangueSuccessRuntimeException.CANCELAMENTO_REALIZADO);
		return new ModelAndView("livroCulturalDetalharEmprestimo");
	}
		
	private void updateFromForm(PesquisaLivroCulturalCriteria pesquisaLivroCulturalCriteria) {
		pesquisaLivroCulturalCriteria.setCodigo(getParam("codigo"));
		pesquisaLivroCulturalCriteria.setTitulo(getParam("titulo"));
		pesquisaLivroCulturalCriteria.setAutor(getParam("autor"));
		pesquisaLivroCulturalCriteria.setIdioma(getParam("idIdioma"));
		pesquisaLivroCulturalCriteria.setPalavrasChaves(getParam("palavrasChaves"));
		pesquisaLivroCulturalCriteria.setObservacoesGerais(getParam("observacoesGerais"));
		pesquisaLivroCulturalCriteria.setSituacao(getParam("idSituacao"));
		pesquisaLivroCulturalCriteria.setUsuarioEmprestimo(getParam("idUsuarioEmprestimo"));
	}
	

	private void updateFromForm(LivroCultural livroCultural) {
		livroCultural.setId(getParam("id"));
		livroCultural.setTitulo(getParam("titulo"));
		livroCultural.setCodigo(getParam("codigo"));
		livroCultural.setAutor(getParam("autor"));
		livroCultural.setIdioma(new ElementoDominio(getParam("idIdioma")));
		livroCultural.setPalavrasChaves(getParam("palavrasChaves"));
		livroCultural.setObservacoesGerais(getParam("observacoesGerais"));
		livroCultural.setForaUso(getParamAsBoolean("foraUso"));
	}
	
	private void updateFromForm(Emprestimo emprestimo) {
		//emprestimo
		emprestimo.setUsuarioEmprestimo(new Usuario(getParam("idUsuarioBibliotecaCultural")));
		emprestimo.setDataEmprestimo(getParamAsDate("dataEmprestimo"));
		emprestimo.setObservacoesEmprestimo(getParam("observacoesEmprestimo"));

		//devolucao
		emprestimo.setDataDevolucao(getParamAsDate("dataDevolucao"));
		emprestimo.setObservacoesDevolucao(getParam("observacoesDevolucao"));
	}
	

}