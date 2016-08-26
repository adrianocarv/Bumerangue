package br.org.ceu.bumerangue.plugins.video.controller.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import br.org.ceu.bumerangue.controller.web.BaseRelatorioEmprestimoController;
import br.org.ceu.bumerangue.entity.Centro;
import br.org.ceu.bumerangue.entity.Emprestimo;
import br.org.ceu.bumerangue.entity.Permissao;
import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.entity.Video;
import br.org.ceu.bumerangue.entity.criterias.PesquisaHistoricoVideoCriteria;
import br.org.ceu.bumerangue.entity.suporte.deploy.Deploy;
import br.org.ceu.bumerangue.service.exceptions.BumerangueAlertRuntimeException;
import br.org.ceu.bumerangue.util.SessionContainer;
import br.org.ceu.bumerangue.util.ValidationRules;

public class RelatorioVideoController extends BaseRelatorioEmprestimoController {
	
	public boolean isPertencenteModuloDisponivel(){
		return Deploy.isModuloDisponivel(Deploy.MODULO_VIDEO);
	}

	public boolean isMetodoAutorizado(){
		return true;
	}

	public ModelAndView validateMulti(HttpServletRequest request, HttpServletResponse response) {
		if (getParam("method").equals("pesquisarHistoricoEmprestimos")) {
	    	if(ValidationRules.isInformed(getParam("codigo"))) isInteger("codigo","Código");
	    	if(ValidationRules.isInformed(getParam("titulo"))) isValidRange("titulo",3,-1,"Título");
	    	if(ValidationRules.isInformed(getParam("dataVideoIni"))) isDateDDMMYYYY("dataVideoIni","Data do vídeo a partir de");
	    	if(ValidationRules.isInformed(getParam("dataVideoFim"))) isDateDDMMYYYY("dataVideoFim","Data do vídeo até");
	    	if(ValidationRules.isInformed(getParam("dataReservaIni"))) isDateDDMMYYYY("dataReservaIni","Data da reserva a partir de");
	    	if(ValidationRules.isInformed(getParam("dataReservaFim"))) isDateDDMMYYYY("dataReservaFim","Data da reserva até");
	    	if(ValidationRules.isInformed(getParam("dataEmprestimoIni"))) isDateDDMMYYYY("dataEmprestimoIni","Data do empréstimo a partir de");
	    	if(ValidationRules.isInformed(getParam("dataEmprestimoFim"))) isDateDDMMYYYY("dataEmprestimoFim","Data do empréstimo até");
	    	if(ValidationRules.isInformed(getParam("dataDevolucaoIni"))) isDateDDMMYYYY("dataDevolucaoIni","Data da devolução a partir de");
	    	if(ValidationRules.isInformed(getParam("dataDevolucaoFim"))) isDateDDMMYYYY("dataDevolucaoFim","Data da devolução até");

			//carrrega o form s� se existir erro
			if (!getErrorMessages().isEmpty()){
				request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			}
			request.setAttribute("tipo","historico");
			return this.pesquisarPre(request,response);			
		}
		return null;
	}

	public ModelAndView menu(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("videoRelatorios");
	}

	public ModelAndView pesquisarPre(HttpServletRequest request, HttpServletResponse response) {
		String tipo = request.getAttribute("tipo") != null ? request.getAttribute("tipo")+"" : getParam("tipo");
		
		if(tipo.equals("historico")){
			Usuario usuario = getSessionContainer().getUsuarioLogado();
			PesquisaHistoricoVideoCriteria pesquisaHistoricoVideoCriteria = new PesquisaHistoricoVideoCriteria();

			//verifica se o objeto deve ser atualizado com os dados do form
			if(request.getAttribute(super.UPDATE_FROM_FORM) != null) updateFromForm(pesquisaHistoricoVideoCriteria) ;		
			
			List centros = new ArrayList();
			if(usuario.isInRole(Permissao.BASICO_VIDEO))
				centros.add(usuario);
			else{
				centros = bumerangueService.findUsuarios(usuario,Centro.class,true,Permissao.BASICO_VIDEO);
				centros.addAll(bumerangueService.findUsuarios(usuario,Centro.class,true,Permissao.AVANCADO_VIDEO));
			}
			
			//limpa a listagem, se for o caso.
			if(!getParamAsBoolean(SessionContainer.REQUEST_ATTRIBUTE_PAGINACAO).booleanValue()){
				getSessionContainer().setEmprestimos(null);
			}

			addObject("centros",centros);
			addObject("pesquisaHistoricoVideoCriteria",pesquisaHistoricoVideoCriteria);

			return new ModelAndView("videoHistoricoEmprestimos");
		}else if(tipo.equals("estatistica")){
			//carrega as combos, etc
			return new ModelAndView("videoEstatisticaEmprestimos");
		}
		
		return new ModelAndView("videoRelatorios");
	}

	public ModelAndView pesquisarHistoricoEmprestimos(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		PesquisaHistoricoVideoCriteria pesquisaHistoricoVideoCriteria = new PesquisaHistoricoVideoCriteria();
		this.updateFromForm(pesquisaHistoricoVideoCriteria);

		if(pesquisaHistoricoVideoCriteria.isEmpty()) throw new BumerangueAlertRuntimeException("é necessário informar pelos menos 1 campo.");
		
		List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();
		//evita ir novamente no banco.
		if(!getParamAsBoolean(SessionContainer.REQUEST_ATTRIBUTE_PAGINACAO).booleanValue()){
			emprestimos = bumerangueService.pesquisarHistoricoEmprestimosVideo(usuario,pesquisaHistoricoVideoCriteria);
			getSessionContainer().setEmprestimos(emprestimos);
		}

		addObject("pesquisaHistoricoVideoCriteria",pesquisaHistoricoVideoCriteria);

		return new ModelAndView("videoHistoricoEmprestimos");
	}

	public ModelAndView detalharEmprestimo(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		String id = getParam("id");
	
		Emprestimo emprestimo = (Emprestimo)bumerangueService.getEmprestimo(usuario,id);
		Video video = (Video)bumerangueService.getObjetoBumerangue(usuario,emprestimo.getObjetoBumerangue().getId());
	
		addObject("emprestimo",emprestimo);
		addObject("video",video);
	
		return new ModelAndView("videoDetalharEmprestimo");
	}

	private void updateFromForm(PesquisaHistoricoVideoCriteria pesquisaHistoricoVideoCriteria) {
		pesquisaHistoricoVideoCriteria.setCodigo(getParam("codigo"));
		pesquisaHistoricoVideoCriteria.setDataVideoIni(getParam("dataVideoIni"));
		pesquisaHistoricoVideoCriteria.setDataVideoFim(getParam("dataVideoFim"));
		pesquisaHistoricoVideoCriteria.setDataReservaIni(getParam("dataReservaIni"));
		pesquisaHistoricoVideoCriteria.setDataReservaFim(getParam("dataReservaFim"));
		pesquisaHistoricoVideoCriteria.setObservacoesReserva(getParam("observacoesReserva"));
		pesquisaHistoricoVideoCriteria.setDataEmprestimoIni(getParam("dataEmprestimoIni"));
		pesquisaHistoricoVideoCriteria.setDataEmprestimoFim(getParam("dataEmprestimoFim"));
		pesquisaHistoricoVideoCriteria.setObservacoesEmprestimo(getParam("observacoesEmprestimo"));
		pesquisaHistoricoVideoCriteria.setDataDevolucaoIni(getParam("dataDevolucaoIni"));
		pesquisaHistoricoVideoCriteria.setDataDevolucaoFim(getParam("dataDevolucaoFim"));
		pesquisaHistoricoVideoCriteria.setObservacoesDevolucao(getParam("observacoesDevolucao"));
		pesquisaHistoricoVideoCriteria.setSituacao(getParam("idSituacao"));
		pesquisaHistoricoVideoCriteria.setUsuarioEmprestimo(getParam("idUsuarioEmprestimo"));
	}
	
}