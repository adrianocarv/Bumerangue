package br.org.ceu.bumerangue.plugins.video.controller.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import br.org.ceu.bumerangue.controller.web.MultiActionBaseController;
import br.org.ceu.bumerangue.entity.Centro;
import br.org.ceu.bumerangue.entity.Dominio;
import br.org.ceu.bumerangue.entity.ElementoDominio;
import br.org.ceu.bumerangue.entity.Emprestimo;
import br.org.ceu.bumerangue.entity.Permissao;
import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.entity.Video;
import br.org.ceu.bumerangue.entity.criterias.PesquisaVideoCriteria;
import br.org.ceu.bumerangue.entity.suporte.deploy.Deploy;
import br.org.ceu.bumerangue.service.BumerangueService;
import br.org.ceu.bumerangue.service.exceptions.BumerangueAlertRuntimeException;
import br.org.ceu.bumerangue.service.exceptions.BumerangueErrorRuntimeException;
import br.org.ceu.bumerangue.service.exceptions.BumerangueSuccessRuntimeException;
import br.org.ceu.bumerangue.util.SessionContainer;
import br.org.ceu.bumerangue.util.ValidationRules;

public class ManterVideoController extends MultiActionBaseController {
	
	/** Service */
	private BumerangueService bumerangueService;
	public void setBumerangueService(BumerangueService bumerangueService){ this.bumerangueService = bumerangueService; }
	
	public boolean isPertencenteModuloDisponivel(){
		return Deploy.isModuloDisponivel(Deploy.MODULO_VIDEO);
	}

	public boolean isMetodoAutorizado(){
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		if(!usuario.isInRole(Permissao.ADMIN_VIDEO)){
			//m�todos autorizados para usu�rios avan�ados e b�sicos
			String metodosAutorizados = ",cancelarReserva,detalhar,pesquisar,pesquisarPre,reservar,reservarPre,";
			String metodo = ","+getParam("method")+",";
			return metodosAutorizados.indexOf(metodo) != -1;
		}
		return true;
	}

	public ModelAndView validateMulti(HttpServletRequest request, HttpServletResponse response) {
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
			super.isInformed("idCategoria","Categoria");
			super.isInformed("titulo","Título");
			super.isInformed("idMidia","Mídia");
			if(ValidationRules.isInformed(getParam("data")))
				super.isDateDDMMYYYY("data","Data");
			if(ValidationRules.isInformed(getParam("duracaoMinutos")))
				super.isInteger("duracaoMinutos","Duração");
			if(ValidationRules.isInformed(getParam("palavrasChaves")))
				super.isValidRange("palavrasChaves",-1,255,"Palavras-chaves");
			if(ValidationRules.isInformed(getParam("observacoesGerais")))
				super.isValidRange("observacoesGerais",-1,255,"Observações gerais do vídeo");

			//carrrega o form s� se existir erro
			if (!getErrorMessages().isEmpty()){
				request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			}
			return this.editarPre(request,response);			
		}else if (getParam("method").equals("reservar")) {
			super.isInformed("idCentro","Centro");
			if(ValidationRules.isInformed(getParam("observacoesReserva")))
				super.isValidRange("observacoesReserva",-1,255,"Observações da reserva");
			
			//carrrega o form s� se existir erro
			if (!getErrorMessages().isEmpty()){
				request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			}
			return this.reservarPre(request,response);			
		}else if (getParam("method").equals("emprestar")) {
	    	super.isInformed("idCentro","Centro");
	    	if(super.isInformed("dataEmprestimo","Data do Empréstimo"))
				super.isDateDDMMYYYY("dataEmprestimo","Data do Empréstimo");
			if(ValidationRules.isInformed(getParam("observacoesEmprestimo")))
				super.isValidRange("observacoesEmprestimo",-1,255,"Observações do empréstimo");
			
			//carrrega o form s� se existir erro
			if (!getErrorMessages().isEmpty()){
				request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			}
			return this.emprestarPre(request,response);			
		}else if (getParam("method").equals("devolver")) {
			if(super.isInformed("dataDevolucao","Data da Devolução"))
				super.isDateDDMMYYYY("dataDevolucao","Data da Devolução");
			if(ValidationRules.isInformed(getParam("numeroAssistentesVideo")))
				super.isInteger("numeroAssistentesVideo","Número de assistentes");
			if(ValidationRules.isInformed(getParam("observacoesDevolucao")))
				super.isValidRange("observacoesDevolucao",-1,255,"Observações da devolução");
			if(ValidationRules.isInformed(getParam("observacoesGerais")))
				super.isValidRange("observacoesGerais",-1,255,"Observações gerais do vídeo");
			if(ValidationRules.isInformed(getParam("palavrasChaves")))
				super.isValidRange("palavrasChaves",-1,255,"Palavras-chaves");
			
			//carrrega o form s� se existir erro
			if (!getErrorMessages().isEmpty()){
				request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			}
			return this.devolverPre(request,response);			
		}else if (getParam("method").equals("editarDevolucao")) {
	    	super.setValidationViewName("videoEditarDevolucao");
			if(super.isInformed("dataDevolucao","Data da Devolução"))
				super.isDateDDMMYYYY("dataDevolucao","Data da Devolução");
			if(ValidationRules.isInformed(getParam("numeroAssistentesVideo")))
				super.isInteger("numeroAssistentesVideo","Número de assistentes");
			if(ValidationRules.isInformed(getParam("observacoesDevolucao")))
				super.isValidRange("observacoesDevolucao",-1,255,"Observações da devolução");

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
		PesquisaVideoCriteria pesquisaVideoCriteria = new PesquisaVideoCriteria();

		//verifica se o objeto deve ser atualizado com os dados do form
		if(request.getAttribute(super.UPDATE_FROM_FORM) != null) this.updateFromForm(pesquisaVideoCriteria) ;		

		Dominio dominioCategoria = bumerangueService.getDominio(usuario,Dominio.VIDEO_CATEGORIA.getCodigo());
		Dominio dominioMidia = bumerangueService.getDominio(usuario,Dominio.VIDEO_MIDIA.getCodigo());
		Dominio dominioObservacoes = bumerangueService.getDominio(usuario,Dominio.VIDEO_OBSERVACOES.getCodigo());
		List centros = new ArrayList();
		if(usuario.isInRole(Permissao.BASICO_VIDEO))
			centros.add(usuario);
		else{
			centros = bumerangueService.findUsuarios(usuario,Centro.class,true,Permissao.BASICO_VIDEO);
			centros.addAll(bumerangueService.findUsuarios(usuario,Centro.class,true,Permissao.AVANCADO_VIDEO));
		}

		//limpa a listagem, se for o caso.
		if(!getParamAsBoolean(SessionContainer.REQUEST_ATTRIBUTE_PAGINACAO).booleanValue()){
			getSessionContainer().setObjetosBumerangue(null);
		}

		addObject("categorias",dominioCategoria.getElementosDominio());
		addObject("midias",dominioMidia.getElementosDominio());
		addObject("observacoes",dominioObservacoes.getElementosDominio());
		addObject("centros",centros);
		addObject("pesquisaVideoCriteria",pesquisaVideoCriteria);

		return new ModelAndView("videoPesquisarVideo");
	}

	public ModelAndView pesquisar(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		PesquisaVideoCriteria pesquisaVideoCriteria = new PesquisaVideoCriteria();
		this.updateFromForm(pesquisaVideoCriteria);		

		if(pesquisaVideoCriteria.isEmpty()) throw new BumerangueAlertRuntimeException("é necessário informar pelos menos 1 campo.");
		
		List videos = new ArrayList();
		//evita ir novamente no banco.
		if(!getParamAsBoolean(SessionContainer.REQUEST_ATTRIBUTE_PAGINACAO).booleanValue()){
			videos = bumerangueService.pesquisarVideos(usuario,pesquisaVideoCriteria);			
			getSessionContainer().setObjetosBumerangue(videos);
		}

		//se retornar apenas um elemento, vai direto para tela de detalhes
		if(videos.size() == 1){
			Video video = (Video)bumerangueService.getObjetoBumerangue(usuario,((Video)videos.get(0)).getId());
			request.setAttribute("id",video.getId());
			return detalhar(request,response);
		}
		
		addObject("pesquisaVideoCriteria",pesquisaVideoCriteria);

		return new ModelAndView("videoPesquisarVideo");
	}

	public ModelAndView inserir(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();

		Video video = new Video();
		updateFromForm(video);		

		try{
			bumerangueService.inserirObjetoBumerangue(usuario,video);
		}catch (BumerangueErrorRuntimeException e) {
			super.addError(e.getMessage());
			request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			return this.editarPre(request, response);
		}

		super.addSuccess(BumerangueSuccessRuntimeException.INSERCAO_REALIZADA);
		request.setAttribute("id",video.getId());
		return this.detalhar(request, response);
	}

	public ModelAndView copiar(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();

		Video video = new Video();
		video.setId(getParam("id"));
		
		video = (Video)bumerangueService.copiarObjetoBumerangue(usuario,video);

		super.addSuccess(BumerangueSuccessRuntimeException.COPIA_REALIZADA);
		request.setAttribute("id",video.getId());
		return editarPre(request,response);
	}

	public ModelAndView editarPre(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		String id = request.getAttribute("id") != null ? request.getAttribute("id")+"" : getParam("id");

		//recupera do banco
		Video video = (Video)bumerangueService.getObjetoBumerangue(usuario,id);
		
		//se ainda n�o tiver no banco, instancia um objeto novo
		if(video == null){
			video = new Video();
			video.setCodigoFormatado(bumerangueService.getProximoCodigoVideo());
		}

		//verifica se o objeto deve ser atualizado com os dados do form
		if(request.getAttribute(super.UPDATE_FROM_FORM) != null) updateFromForm(video) ;		
		
		//coloca os objetos no request
		Dominio dominioCategoria = bumerangueService.getDominio(usuario,Dominio.VIDEO_CATEGORIA.getCodigo());
		Dominio dominioMidia = bumerangueService.getDominio(usuario,Dominio.VIDEO_MIDIA.getCodigo());
		Dominio dominioObservacoes = bumerangueService.getDominio(usuario,Dominio.VIDEO_OBSERVACOES.getCodigo());

		addObject("video",video);
		addObject("categorias",dominioCategoria.getElementosDominio());
		addObject("midias",dominioMidia.getElementosDominio());
		addObject("observacoes",dominioObservacoes.getElementosDominio());

		return new ModelAndView("videoEditarVideo");
	}

	public ModelAndView editar(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		
		Video video = new Video();
		this.updateFromForm(video);
		
		try{
			bumerangueService.editarObjetoBumerangue(usuario,video);
		}catch (BumerangueErrorRuntimeException e) {
			super.addError(e.getMessage());
			request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			return this.editarPre(request, response);
		}
		
		super.addSuccess(BumerangueSuccessRuntimeException.ALTERACAO_REALIZADA);
		return this.detalhar(request, response);
	}

	public ModelAndView detalhar(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		String id = request.getAttribute("id") != null ? request.getAttribute("id")+"" : getParam("id");

		Video video = (Video)bumerangueService.getObjetoBumerangue(usuario,id);
		addObject("video",video);

		return new ModelAndView("videoDetalharVideo");
	}

	public ModelAndView excluir(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		
		Video video = new Video();
		video.setId(getParam("id"));
		
		bumerangueService.excluirObjetoBumerangue(usuario,video);

		//verifica se foi exclu�do, ou ficou fora de uso
    	video = (Video)bumerangueService.getObjetoBumerangue(usuario,video.getId());
    	if(video != null) throw new BumerangueAlertRuntimeException("O vídeo foi colocado como fora de uso, por possuir empréstimos.");

		super.addSuccess(BumerangueSuccessRuntimeException.EXCLUSAO_REALIZADA);
		return pesquisarPre(request,response);
	}

	public ModelAndView reservarPre(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		String id = getParam("id");
		Emprestimo reserva = new Emprestimo();

		//verifica se o objeto deve ser atualizado com os dados do form
		if(request.getAttribute(super.UPDATE_FROM_FORM) != null) this.updateFromForm(reserva) ;		

		Video video = (Video)bumerangueService.getObjetoBumerangue(usuario,id);

		//se o usu�rio logado n�o for admin, s� pode reservar para o usu�rio logado.
		List centros = new ArrayList();
		if(usuario.isInRole(Permissao.ADMIN_VIDEO)){
			centros = bumerangueService.findUsuarios(usuario,Centro.class,true,Permissao.BASICO_VIDEO);
			centros.addAll(bumerangueService.findUsuarios(usuario,Centro.class,true,Permissao.AVANCADO_VIDEO));
		}
		else centros.add(usuario);

		addObject("video",video);
		addObject("centros",centros);
		addObject("reserva",reserva);

		return new ModelAndView("videoReservarVideo");
	}

	public ModelAndView reservar(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		
		Video video = new Video();
		video.setId(getParam("id"));

		Emprestimo reserva = new Emprestimo();
		this.updateFromForm(reserva);
		
		bumerangueService.reservarObjetoBumerangue(usuario,video,reserva);
		
		super.addSuccess(BumerangueSuccessRuntimeException.RESERVA_REALIZADA);
		return detalhar(request,response);
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

		Video video = (Video)bumerangueService.getObjetoBumerangue(usuario,id);
		List centros = bumerangueService.findUsuarios(usuario,Centro.class,true,Permissao.BASICO_VIDEO);
		centros.addAll(bumerangueService.findUsuarios(usuario,Centro.class,true,Permissao.AVANCADO_VIDEO));

		addObject("video",video);
		addObject("centros",centros);
		addObject("emprestimo",emprestimo);

		return new ModelAndView("videoEmprestarVideo");
	}

	public ModelAndView emprestar(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		
		Video video = new Video();
		video.setId(getParam("id"));

		Emprestimo emprestimo = new Emprestimo();
		this.updateFromForm(emprestimo);
		
		bumerangueService.emprestarObjetoBumerangue(usuario,video,emprestimo);
		
		super.addSuccess(BumerangueSuccessRuntimeException.EMPRESTIMO_REALIZADO);
		return detalhar(request,response);
	}

	public ModelAndView devolverPre(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		String id = getParam("id");
		Emprestimo devolucao = new Emprestimo();

		Video video = (Video)bumerangueService.getObjetoBumerangue(usuario,id);

		//verifica se o objeto deve ser atualizado com os dados do form
		if(request.getAttribute(super.UPDATE_FROM_FORM) != null){
			this.updateFromForm(devolucao) ;		
			video.setPalavrasChaves(getParam("palavrasChaves"));
			video.setObservacoesGerais(getParam("observacoesGerais"));
		}else{
			devolucao.setDataDevolucao(new Date());
		}

		addObject("video",video);
		addObject("devolucao",devolucao);

		return new ModelAndView("videoDevolverVideo");
	}

	public ModelAndView devolver(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		
		Video video = new Video();
		this.updateFromForm(video);

		Emprestimo devolucao = new Emprestimo();
		this.updateFromForm(devolucao);
		
		try{
			bumerangueService.devolverObjetoBumerangue(usuario,video,devolucao);
		}catch (BumerangueErrorRuntimeException e) {
			super.addError(e.getMessage());
			request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			return devolverPre(request, response);
		}
		
		super.addSuccess(BumerangueSuccessRuntimeException.DEVOLUCAO_REALIZADA);
		return detalhar(request,response);
	}

	public ModelAndView cancelarReserva(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		
		Video video = new Video();
		video.setId(getParam("id"));

		bumerangueService.cancelarReservaObjetoBumerangue(usuario,video);
		
		super.addSuccess(BumerangueSuccessRuntimeException.CANCELAMENTO_REALIZADO);
		return detalhar(request,response);
	}

	public ModelAndView cancelarEmprestimo(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		
		Video video = new Video();
		video.setId(getParam("id"));

		bumerangueService.cancelarEmprestimoObjetoBumerangue(usuario,video);
		
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
		Video video = (Video)bumerangueService.getObjetoBumerangue(usuario,emprestimo.getObjetoBumerangue().getId());
		addObject("emprestimo",emprestimo);
		addObject("video",video);

		super.addSuccess(BumerangueSuccessRuntimeException.CANCELAMENTO_REALIZADO);
		return new ModelAndView("videoDetalharEmprestimo");
	}
		
	private void updateFromForm(PesquisaVideoCriteria pesquisaVideoCriteria) {
		pesquisaVideoCriteria.setCodigo(getParam("codigo"));
		pesquisaVideoCriteria.setCategoria(getParam("idCategoria"));
		pesquisaVideoCriteria.setCodigoCaixa(getParam("codigoCaixa"));
		pesquisaVideoCriteria.setTitulo(getParam("titulo"));
		pesquisaVideoCriteria.setLocalidade(getParam("localidade"));
		pesquisaVideoCriteria.setDataIni(getParam("dataIni"));
		pesquisaVideoCriteria.setDataFim(getParam("dataFim"));
		pesquisaVideoCriteria.setLegendado(getParamAsBoolean("legendado"));
		pesquisaVideoCriteria.setMidia(getParam("idMidia"));
		pesquisaVideoCriteria.setPalavrasChaves(getParam("palavrasChaves"));
		pesquisaVideoCriteria.setObservacoesGerais(getParam("observacoesGerais"));
		pesquisaVideoCriteria.setSituacao(getParam("idSituacao"));
		pesquisaVideoCriteria.setUsuarioEmprestimo(getParam("idUsuarioEmprestimo"));
		pesquisaVideoCriteria.setLocalizacaoFisica(getParam("localizacaoFisica"));
	}

	private void updateFromForm(Video video) {
		video.setId(getParam("id"));
		video.setTitulo(getParam("titulo"));
		video.setCodigo(getParam("codigo"));
		video.setCategoria(new ElementoDominio(getParam("idCategoria")));
		video.setCodigoCaixa(getParam("codigoCaixa"));
		video.setLocalidade(getParam("localidade"));
		video.setData(getParamAsDate("data"));
		video.setLegendado(getParamAsBoolean("legendado"));
		video.setDublado(getParamAsBoolean("dublado"));
		video.setPublico(getParam("publico"));
		video.setDuracaoMinutos(getParamAsInteger("duracaoMinutos"));
		video.setMidia(new ElementoDominio(getParam("idMidia")));
		video.setPalavrasChaves(getParam("palavrasChaves"));
		video.setObservacoes(new ElementoDominio(getParam("idObservacoes")));
		video.setObservacoesGerais(getParam("observacoesGerais"));
		video.setLocalizacaoPI(getParam("localizacaoPI"));
		video.setForaUso(getParamAsBoolean("foraUso"));
	}
	
	private void updateFromForm(Emprestimo emprestimo) {
		//reserva
		emprestimo.setUsuarioEmprestimo(new Usuario(getParam("idCentro")));
		emprestimo.setObservacoesReserva(getParam("observacoesReserva"));

		//emprestimo
		emprestimo.setUsuarioEmprestimo(new Usuario(getParam("idCentro")));
		emprestimo.setDataEmprestimo(getParamAsDate("dataEmprestimo"));
		emprestimo.setObservacoesEmprestimo(getParam("observacoesEmprestimo"));

		//devolucao
		emprestimo.setDataDevolucao(getParamAsDate("dataDevolucao"));
		emprestimo.setObservacoesDevolucao(getParam("observacoesDevolucao"));
		emprestimo.setTipoAtividadeVideo(getParam("tipoAtividadeVideo"));
		emprestimo.setPublicoVideo(getParam("publicoVideo"));
		emprestimo.setNumeroAssistentesVideo(getParamAsInteger("numeroAssistentesVideo"));
	}
	

}