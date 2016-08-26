package br.org.ceu.bumerangue.plugins.fichaMissa.controller.web;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import br.org.ceu.bumerangue.controller.web.MultiActionBaseController;
import br.org.ceu.bumerangue.entity.Dominio;
import br.org.ceu.bumerangue.entity.LiturgiaPalavra;
import br.org.ceu.bumerangue.entity.Missa;
import br.org.ceu.bumerangue.entity.Oracao;
import br.org.ceu.bumerangue.entity.Permissao;
import br.org.ceu.bumerangue.entity.PlanoMissa;
import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.entity.suporte.deploy.Deploy;
import br.org.ceu.bumerangue.service.FichaMissaService;
import br.org.ceu.bumerangue.service.exceptions.BumerangueRuntimeException;
import br.org.ceu.bumerangue.service.exceptions.BumerangueSuccessRuntimeException;

public class ManterPlanoMissaController extends MultiActionBaseController {
	
	/** Service */
	private FichaMissaService fichaMissaService;
	public void setFichaMissaService(FichaMissaService fichaMissaService){ this.fichaMissaService = fichaMissaService; }
	
	public boolean isPertencenteModuloDisponivel(){
		return Deploy.isModuloDisponivel(Deploy.MODULO_FICHA_MISSA);
	}

	public boolean isMetodoAutorizado(){
		Usuario usuario = getSessionContainer().getUsuarioLogado();

		//admin acessa todos os métodos
		if(usuario.isInRole(Permissao.ADMIN_FICHA_MISSA))
			return true;
		
		//métodos autorizados para usuários não admin
		String metodosAutorizados = ",pesquisarPre,pesquisar,detalhar,detalharMissa,";

		//métodos autorizados para usuários não admin
		String metodo = ","+getParam("method")+",";
		return metodosAutorizados.indexOf(metodo) != -1;
	}

	public ModelAndView validateMulti(HttpServletRequest request, HttpServletResponse response) {
		if (getParam("method").equals("inserir")) {
	    	isInformed("anoMes","Ano/Mês");

			//carrrega o form só se existir erro
			if (!getErrorMessages().isEmpty()){
				request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			}
	    	return this.pesquisarPre(request,response);			
		}
		return null;
	}

	public ModelAndView pesquisarPre(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();

		//coloca os objetos no request
		Dominio situacoesPlanoMissa = fichaMissaService.getDominio(usuario,Dominio.FICHA_MISSA_SITUACAO_PLANO_MISSA.getCodigo());

		addObject("situacoesPlanoMissa",situacoesPlanoMissa.getElementosDominio());

		return new ModelAndView("fichaMissaPesquisarPlanoMissa");
	}

	public ModelAndView pesquisar(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		String anoMes = StringUtils.replace(getParam("anoMes"),"/","");
		String idSituacao = getParam("idSituacao");

		List<PlanoMissa> planosMissa = fichaMissaService.findPlanosMissa(usuario, anoMes, idSituacao);

		addObject("planosMissa",planosMissa);
		addObject("anoMes",anoMes);
		addObject("idSituacao",idSituacao);
		return pesquisarPre(request,response);
	}

	public ModelAndView detalhar(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		String id = request.getAttribute("id") != null ? request.getAttribute("id")+"" : getParam("id");

		//recupera do banco
		PlanoMissa planoMissa = fichaMissaService.getPlanoMissa(usuario,id);
		
		//se o plano não estiver aprovado e o usuário não for admin, não detalha
		if(!planoMissa.isAprovado() && !usuario.isInRole(Permissao.ADMIN_FICHA_MISSA)){
			addAlert("Quando for aprovado, o plano "+planoMissa.getAnoMesFormatado()+" poderá ser visualizado.");
			return pesquisarPre(request, response);
		}
		
		//coloca os objetos no request
		addObject("planoMissa",planoMissa);
		
		if( fichaMissaService.existeArquivoPlanoMissa(planoMissa) ){
			String pathRelativoArquivoPlanoMissa = fichaMissaService.getPathRelativoArquivoPlanoMissa(planoMissa);
			addObject("pathRelativoArquivoPlanoMissa",pathRelativoArquivoPlanoMissa);
		}

		return new ModelAndView("fichaMissaDetalharPlanoMissa");
	}

	public ModelAndView editarPre(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		String id = request.getAttribute("id") != null ? request.getAttribute("id")+"" : getParam("id");

		//recupera do banco
		PlanoMissa planoMissa = fichaMissaService.getPlanoMissa(usuario,id);
		List<LiturgiaPalavra> liturgiasPalavra = fichaMissaService.findLiturgiasPalavra(usuario,null);
		List<Oracao> oracoes = fichaMissaService.findOracoes(usuario,null);

		//coloca os objetos no request
		addObject("planoMissa",planoMissa);
		getSessionContainer().setListaAutoCompletar1(liturgiasPalavra);
		getSessionContainer().setListaAutoCompletar2(oracoes);

		return new ModelAndView("fichaMissaEditarPlanoMissa");
	}

	public ModelAndView inserir(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();

		PlanoMissa planoMissa = new PlanoMissa();
		planoMissa.setAnoMes(StringUtils.replace(getParam("anoMes"),"/",""));
		
		try{
			fichaMissaService.inserirPlanoMissa(usuario,planoMissa);
		}catch (BumerangueRuntimeException e) {
			super.addError(e.getMessage());
			request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			return pesquisarPre(request,response);
		}

		super.addSuccess(BumerangueSuccessRuntimeException.INSERCAO_REALIZADA);
		request.setAttribute("id",planoMissa.getId());
		return this.editarPre(request, response);
	}

	public ModelAndView editar(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		
		//carrega a listaIdMissa_chaveLiturgiaPalavra
		List<String> listaIdMissa_chaveLiturgiaPalavra = new ArrayList<String>();
		Enumeration enumeration = request.getParameterNames();
		while(enumeration.hasMoreElements()){
			String nomeParametro = enumeration.nextElement()+"";
			if(nomeParametro.endsWith("_chaveLiturgiaPalavra")){
				String idMissa = nomeParametro.split("_")[0];
				listaIdMissa_chaveLiturgiaPalavra.add(idMissa+"_"+getParam(nomeParametro));
			}
		}
		
		//carrega a listaIdMissa_chaveOracao
		List<String> listaIdMissa_chaveOracao = new ArrayList<String>();
		enumeration = request.getParameterNames();
		while(enumeration.hasMoreElements()){
			String nomeParametro = enumeration.nextElement()+"";
			if(nomeParametro.endsWith("_chaveOracao")){
				String idMissa = nomeParametro.split("_")[0];
				listaIdMissa_chaveOracao.add(idMissa+"_"+getParam(nomeParametro));
			}
		}

		try{
			fichaMissaService.editarPlanoMissa(usuario,listaIdMissa_chaveLiturgiaPalavra, listaIdMissa_chaveOracao);
		}catch (BumerangueRuntimeException e) {
			super.addError(e.getMessage());
			request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			return editarPre(request,response);
		}
		
		super.addSuccess(BumerangueSuccessRuntimeException.ALTERACAO_REALIZADA);
		return this.detalhar(request, response);
	}

	public ModelAndView excluir(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		
		PlanoMissa planoMissa  = new PlanoMissa(getParam("id"));
		fichaMissaService.excluirPlanoMissa(usuario,planoMissa);

		super.addSuccess(BumerangueSuccessRuntimeException.EXCLUSAO_REALIZADA);
    	return this.pesquisarPre(request, response);
	}

	public ModelAndView detalharMissa(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();

		//recupera do banco
		Missa missa = fichaMissaService.getMissa(usuario,getParam("id"));
		
		//coloca os objetos no request
		addObject("missa",missa);

		return new ModelAndView("fichaMissaDetalharMissa");
	}

	public ModelAndView validar(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		
		//recupera do banco
		PlanoMissa planoMissa = fichaMissaService.getPlanoMissa(usuario,getParam("id"));
		
		//validar
		if(!planoMissa.isValido()){
			super.addError(planoMissa.getErrosValidacao());
		}else{
			super.addSuccess("O Plano de Missas está válido.");
		}

		return this.detalhar(request, response);
	}

	public ModelAndView aprovar(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		
		try{
			fichaMissaService.aprovarPlanoMissa(usuario,new PlanoMissa(getParam("id")));
		}catch (BumerangueRuntimeException e) {
			super.addError(e.getMessage());
			return detalhar(request,response);
		}
		
		super.addSuccess(BumerangueSuccessRuntimeException.APROVACAO_REALIZADA);
		return this.detalhar(request, response);
	}

	public ModelAndView alterarParaEmAprovacao(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		
		try{
			fichaMissaService.alterarPlanoMissaParaEmAprovacao(usuario,new PlanoMissa(getParam("id")));
		}catch (BumerangueRuntimeException e) {
			super.addError(e.getMessage());
			return detalhar(request,response);
		}
		
		super.addSuccess(BumerangueSuccessRuntimeException.ALTERACAO_REALIZADA);
		return this.detalhar(request, response);
	}
}