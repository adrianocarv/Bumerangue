package br.org.ceu.bumerangue.plugins.livroCultural.controller.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import br.org.ceu.bumerangue.controller.web.BaseRelatorioEmprestimoController;
import br.org.ceu.bumerangue.entity.Emprestimo;
import br.org.ceu.bumerangue.entity.LivroCultural;
import br.org.ceu.bumerangue.entity.Permissao;
import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.entity.UsuarioBibliotecaCultural;
import br.org.ceu.bumerangue.entity.criterias.PesquisaHistoricoLivroCulturalCriteria;
import br.org.ceu.bumerangue.entity.suporte.deploy.Deploy;
import br.org.ceu.bumerangue.service.exceptions.BumerangueAlertRuntimeException;
import br.org.ceu.bumerangue.util.SessionContainer;
import br.org.ceu.bumerangue.util.ValidationRules;

public class RelatorioLivroCulturalController extends BaseRelatorioEmprestimoController {
	
	public boolean isPertencenteModuloDisponivel(){
		return Deploy.isModuloDisponivel(Deploy.MODULO_LIVRO_CULTURAL);
	}

	public boolean isMetodoAutorizado(){
		return true;
	}

	public ModelAndView validateMulti(HttpServletRequest request, HttpServletResponse response) {
		if (getParam("method").equals("pesquisarHistoricoEmprestimos")) {
	    	if(ValidationRules.isInformed(getParam("codigo"))) isInteger("codigo","Código");
	    	if(ValidationRules.isInformed(getParam("titulo"))) isValidRange("titulo",3,-1,"Título");
	    	if(ValidationRules.isInformed(getParam("autor"))) isValidRange("autor", 3, -1, "Autor");
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
			PesquisaHistoricoLivroCulturalCriteria pesquisaHistoricoLivroCulturalCriteria = new PesquisaHistoricoLivroCulturalCriteria();

			//verifica se o objeto deve ser atualizado com os dados do form
			if(request.getAttribute(super.UPDATE_FROM_FORM) != null) updateFromForm(pesquisaHistoricoLivroCulturalCriteria) ;		
			
			List usuarios = new ArrayList();
			if(usuario.isInRole(Permissao.BASICO_LIVRO_CULTURAL))
				usuarios.add(usuario);
			else{
				usuarios = bumerangueService.findUsuarios(usuario,UsuarioBibliotecaCultural.class,true,Permissao.BASICO_LIVRO_CULTURAL);
				usuarios.addAll(bumerangueService.findUsuarios(usuario,UsuarioBibliotecaCultural.class,true,Permissao.AVANCADO_LIVRO_CULTURAL));
			}

			//limpa a listagem, se for o caso.
			if(!getParamAsBoolean(SessionContainer.REQUEST_ATTRIBUTE_PAGINACAO).booleanValue()){
				getSessionContainer().setEmprestimos(null);
			}

			addObject("usuarios",usuarios);
			addObject("pesquisaHistoricoLivroCulturalCriteria",pesquisaHistoricoLivroCulturalCriteria);

			return new ModelAndView("livroCulturalHistoricoEmprestimos");
		}else if(tipo.equals("estatistica")){
			//carrega as combos, etc
			return new ModelAndView("livroCulturalEstatisticaEmprestimos");
		}
		
		return new ModelAndView("livroCulturalRelatorios");
	}

	public ModelAndView pesquisarHistoricoEmprestimos(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		PesquisaHistoricoLivroCulturalCriteria pesquisaHistoricoLivroCulturalCriteria = new PesquisaHistoricoLivroCulturalCriteria();
		this.updateFromForm(pesquisaHistoricoLivroCulturalCriteria);

		if(pesquisaHistoricoLivroCulturalCriteria.isEmpty()) throw new BumerangueAlertRuntimeException("� necess�rio informar pelos menos 1 campo.");
		
		List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();
		//evita ir novamente no banco.
		if(!getParamAsBoolean(SessionContainer.REQUEST_ATTRIBUTE_PAGINACAO).booleanValue()){
			emprestimos = bumerangueService.pesquisarHistoricoEmprestimosLivroCultural(usuario,pesquisaHistoricoLivroCulturalCriteria);
			getSessionContainer().setEmprestimos(emprestimos);
		}

		addObject("pesquisaHistoricoLivroCulturalCriteria",pesquisaHistoricoLivroCulturalCriteria);

		return new ModelAndView("livroCulturalHistoricoEmprestimos");
	}

	public ModelAndView detalharEmprestimo(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		String id = getParam("id");
	
		Emprestimo emprestimo = (Emprestimo)bumerangueService.getEmprestimo(usuario,id);
		LivroCultural livroCultual = (LivroCultural)bumerangueService.getObjetoBumerangue(usuario,emprestimo.getObjetoBumerangue().getId());
	
		addObject("emprestimo",emprestimo);
		addObject("livroCultural",livroCultual);
	
		return new ModelAndView("livroCulturalDetalharEmprestimo");
	}

	private void updateFromForm(PesquisaHistoricoLivroCulturalCriteria pesquisaHistoricoLivroCulturalCriteria) {
		pesquisaHistoricoLivroCulturalCriteria.setCodigo(getParam("codigo"));
		pesquisaHistoricoLivroCulturalCriteria.setAutor(getParam("autor"));
		pesquisaHistoricoLivroCulturalCriteria.setDataEmprestimoIni(getParam("dataEmprestimoIni"));
		pesquisaHistoricoLivroCulturalCriteria.setDataEmprestimoFim(getParam("dataEmprestimoFim"));
		pesquisaHistoricoLivroCulturalCriteria.setObservacoesEmprestimo(getParam("observacoesEmprestimo"));
		pesquisaHistoricoLivroCulturalCriteria.setDataDevolucaoIni(getParam("dataDevolucaoIni"));
		pesquisaHistoricoLivroCulturalCriteria.setDataDevolucaoFim(getParam("dataDevolucaoFim"));
		pesquisaHistoricoLivroCulturalCriteria.setObservacoesDevolucao(getParam("observacoesDevolucao"));
		pesquisaHistoricoLivroCulturalCriteria.setSituacao(getParam("idSituacao"));
		pesquisaHistoricoLivroCulturalCriteria.setUsuarioEmprestimo(getParam("idUsuarioEmprestimo"));
	}
	
}