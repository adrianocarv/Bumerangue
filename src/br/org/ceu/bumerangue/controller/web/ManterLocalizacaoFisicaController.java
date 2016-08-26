package br.org.ceu.bumerangue.controller.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import br.org.ceu.bumerangue.entity.Dominio;
import br.org.ceu.bumerangue.entity.ElementoDominio;
import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.entity.suporte.LocalizacaoFisicaInfo;
import br.org.ceu.bumerangue.service.AdministracaoService;
import br.org.ceu.bumerangue.service.exceptions.BumerangueRuntimeException;
import br.org.ceu.bumerangue.service.exceptions.BumerangueSuccessRuntimeException;
import br.org.ceu.bumerangue.util.SessionContainer;

import com.ibm.icu.util.Calendar;

public class ManterLocalizacaoFisicaController extends MultiActionBaseController {
	
	/** Service */
	private AdministracaoService administracaoService;
	public void setAdministracaoService(AdministracaoService administracaoService){ this.administracaoService = administracaoService; }
	
	public boolean isPertencenteModuloDisponivel(){
		return true;
	}

	public boolean isMetodoAutorizado(){
		return true;
	}

	public ModelAndView validateMulti(HttpServletRequest request, HttpServletResponse response) {
		if (getParam("method").equals("editarElementoDominio") || getParam("method").equals("inserirElementoDominio")) {
	    	super.isInformed("descricao","Descrição");
	    	if(super.isInformed("personalizado1","Quantidade")){
		    	super.isInteger("personalizado1","Quantidade");
		    	super.isValidInterval("personalizado1",new Double(1),null,"Quantidade");
	    	}
	    	super.isInformed("personalizado2","Direção");

			//s� retorna, se existir erro
			if (!getErrorMessages().isEmpty()){
				request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
				return this.selecionarElementoDominio(request,response);
			}
		}else if (getParam("method").equals("trocarPosicaoElementoDominio")) {
		    	super.isInformed("id","Elemento");

				//s� retorna, se existir erro
				if (!getErrorMessages().isEmpty()){
					request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
					return this.listarPre(request,response);
				}
			}
		
		return null;
	}

	public ModelAndView listarPre(HttpServletRequest request, HttpServletResponse response) {
		
		String nomeModuloSelecionado = getParam("nomeModuloSelecionado");
		
		Dominio dominio = Dominio.getLocalizacaoFisica(nomeModuloSelecionado);
		if(dominio != null) dominio = administracaoService.getDominio(getSessionContainer().getUsuarioLogado(),dominio.getCodigo());
		addObject("dominio",dominio);

		//verifica se o objeto deve ser atualizado com os dados do form
		if(request.getAttribute(super.UPDATE_FROM_FORM) != null){
			dominio.setPersonalizado2(getParam("personalizado2"));
			dominio.setPersonalizado3(getParam("personalizado3"));
		}

		addObject("nomeModuloSelecionado",nomeModuloSelecionado);
		
		return new ModelAndView("localizacoesFisicas");
	}

	public ModelAndView alterarModuloSelecionado(HttpServletRequest request, HttpServletResponse response) {
		return this.listarPre(request,response);
	}

	public ModelAndView editarDominio(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		
		Dominio dominio = new Dominio(getParam("id"));
		dominio.setPersonalizado2(getParam("personalizado2"));
		dominio.setPersonalizado3(getParam("personalizado3"));

		try{
			administracaoService.editarDominio(usuario,dominio);
		}catch (BumerangueRuntimeException e) {
			super.addError(e.getMessage());
			request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			return alterarModuloSelecionado(request,response);
		}
		
		super.addSuccess(BumerangueSuccessRuntimeException.ALTERACAO_REALIZADA);
		return this.listarPre(request, response);
	}

	public ModelAndView selecionarElementoDominio(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		String id = request.getAttribute("id") != null ? request.getAttribute("id")+"" : getParam("id");

		//recupera do banco
		ElementoDominio elementoDominio = administracaoService.getElementoDominio(usuario,id);
		Dominio direcoes = administracaoService.getDominio(usuario,Dominio.BUMERANGUE_LOCALIZACAO_FISICA_DIRECAO.getCodigo());
		
		//se ainda n�o tiver no banco, instancia um objeto novo
		if(elementoDominio == null){
			Dominio dominio = administracaoService.getDominio(getSessionContainer().getUsuarioLogado(), getParam("idDominio"));
			elementoDominio = new ElementoDominio();
			elementoDominio.setDominio(dominio);
		}

		//verifica se o objeto deve ser atualizado com os dados do form
		if(request.getAttribute(super.UPDATE_FROM_FORM) != null){
			elementoDominio.setDescricao(getParam("descricao"));
			elementoDominio.setForaUso(getParamAsBoolean("foraUso"));
		}
		
		addObject("direcoes",direcoes.getElementosDominioEmUso());
		addObject("elementoDominio",elementoDominio);

		return listarPre(request,response);
	}

	public ModelAndView inserirElementoDominio(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();

		ElementoDominio elementoDominio = new ElementoDominio();
		elementoDominio.setDominio(new Dominio(getParam("idDominio")));
		elementoDominio.setDescricao(getParam("descricao"));
		elementoDominio.setPersonalizado1(getParam("personalizado1"));
		elementoDominio.setPersonalizado2(new ElementoDominio(getParam("personalizado2")));
		elementoDominio.setForaUso(getParamAsBoolean("foraUso"));

		try{
			administracaoService.inserirElementoDominio(usuario,elementoDominio);
		}catch (BumerangueRuntimeException e) {
			super.addError(e.getMessage());
			request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			return selecionarElementoDominio(request,response);
		}

		super.addSuccess(BumerangueSuccessRuntimeException.INSERCAO_REALIZADA);
		return this.listarPre(request, response);
	}

	public ModelAndView editarElementoDominio(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		
		ElementoDominio elementoDominio = new ElementoDominio(getParam("id"));
		elementoDominio.setDominio(new Dominio(getParam("idDominio")));
		elementoDominio.setDescricao(getParam("descricao"));
		elementoDominio.setPersonalizado1(getParam("personalizado1"));
		elementoDominio.setPersonalizado2(new ElementoDominio(getParam("personalizado2")));
		elementoDominio.setForaUso(getParamAsBoolean("foraUso"));

		try{
			administracaoService.editarElementoDominio(usuario,elementoDominio);
		}catch (BumerangueRuntimeException e) {
			super.addError(e.getMessage());
			request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			return selecionarElementoDominio(request,response);
		}
		
		super.addSuccess(BumerangueSuccessRuntimeException.ALTERACAO_REALIZADA);
		return this.listarPre(request, response);
	}
	
	public ModelAndView excluirElementoDominio(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		
		ElementoDominio elementoDominio = new ElementoDominio(getParam("id"));
		elementoDominio.setDominio(new Dominio(getParam("idDominio")));

		try{
			administracaoService.excluirElementoDominio(usuario,elementoDominio);
		}catch (BumerangueRuntimeException e) {
			super.addError(e.getMessage());
			request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			return selecionarElementoDominio(request,response);
		}
		
		super.addSuccess(BumerangueSuccessRuntimeException.EXCLUSAO_REALIZADA);
		return this.listarPre(request, response);
	}
	
	public ModelAndView trocarPosicaoElementoDominio(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();

		ElementoDominio elementoDominio = new ElementoDominio(getParam("id"));
		Dominio dominio = new Dominio(getParam("idDominio"));
		Integer direcao = getParamAsInteger("direcao");
		Integer posicaoDestino = getParamAsInteger("posicaoDestino");

		try{
			administracaoService.trocarPosicaoElementoDominio(usuario,elementoDominio,dominio,direcao,posicaoDestino);
		}catch (BumerangueRuntimeException e) {
			super.addError(e.getMessage());
			request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			return listarPre(request,response);
		}
		
		return this.listarPre(request, response);
	}
	
	public ModelAndView atualizarPre(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		
		String nomeModuloSelecionado = getParam("nomeModuloSelecionado");
		
		LocalizacaoFisicaInfo localizacaoFisicaInfo = administracaoService.getLocalizacaoFisicaInfo(usuario, nomeModuloSelecionado);

		//limpa a listagem, se for o caso.
		if(!getParamAsBoolean(SessionContainer.REQUEST_ATTRIBUTE_PAGINACAO).booleanValue()){
			getSessionContainer().setObjetosBumerangue(null);
		}

		addObject("nomeModuloSelecionado",nomeModuloSelecionado);
		addObject("localizacaoFisicaInfo",localizacaoFisicaInfo);
		
		return new ModelAndView("atualizarLocalizacoesFisicas");
	}
	
	public ModelAndView atualizar(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		
		String nomeModuloSelecionado = getParam("nomeModuloSelecionado");
		LocalizacaoFisicaInfo localizacaoFisicaInfo = administracaoService.getLocalizacaoFisicaInfo(usuario, nomeModuloSelecionado);;

		//evita ir novamente no banco.
		if(!getParamAsBoolean(SessionContainer.REQUEST_ATTRIBUTE_PAGINACAO).booleanValue()){
			try{
				administracaoService.atualizarLocalizacoesFisicas(usuario,nomeModuloSelecionado);
				localizacaoFisicaInfo = administracaoService.getLocalizacaoFisicaInfo(usuario, nomeModuloSelecionado);
				getSessionContainer().setObjetosBumerangue(localizacaoFisicaInfo.getObjetosBumerangueAtualizados());
			}catch (BumerangueRuntimeException e) {
				super.addError(e.getMessage());
				request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
				return atualizarPre(request,response);
			}
		}
		
		addObject("nomeModuloSelecionado",nomeModuloSelecionado);
		addObject("localizacaoFisicaInfo",localizacaoFisicaInfo);
		
		return new ModelAndView("atualizarLocalizacoesFisicas");
	}
}