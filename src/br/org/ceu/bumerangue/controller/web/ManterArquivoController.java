package br.org.ceu.bumerangue.controller.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.org.ceu.bumerangue.entity.Dominio;
import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.entity.suporte.Arquivo;
import br.org.ceu.bumerangue.entity.suporte.deploy.Deploy;
import br.org.ceu.bumerangue.service.BumerangueService;
import br.org.ceu.bumerangue.service.exceptions.BumerangueSuccessRuntimeException;
import br.org.ceu.bumerangue.util.SessionContainer;

public class ManterArquivoController extends MultiActionBaseController {
	
	/** Service */
	private BumerangueService bumerangueService;
	public void setBumerangueService(BumerangueService bumerangueService){ this.bumerangueService = bumerangueService; }
	
	public boolean isPertencenteModuloDisponivel(){
		return true;
	}

	public boolean isMetodoAutorizado(){
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		if(!usuario.isInRole("1,4,7,10")){

			//métodos autorizados para usuários não admin
			String metodosAutorizados = ",listar,";

			String metodo = ","+getParam("method")+",";
			return metodosAutorizados.indexOf(metodo) != -1;
		}
		return true;
	}

	public ModelAndView validateMulti(HttpServletRequest request, HttpServletResponse response) {
		if (getParam("method").equals("upload")) {
	    	isInformed("nomeModuloSelecionado","Módulo");
	    	if(!Deploy.MODULO_BUMERANGUE.equals(getParam("nomeModuloSelecionado")))
	    		isInformed("codigoTipoPermissao","Acessível para a permissão");
	    	isInformed("nomeArquivo","Nome para o arquivo");
	    	if(getParamAsFile("uploadFile") == null)
	    		super.addError("O campo Arquivo é obrigatório.");

			//carrrega o form s� se existir erro
			if (!getErrorMessages().isEmpty()){
				request.setAttribute(super.UPDATE_FROM_FORM, super.UPDATE_FROM_FORM);
			}
	    	return this.listar(request,response);			
		}
		return null;	}

	public ModelAndView listar(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuarioLogado = getSessionContainer().getUsuarioLogado();

		List<Arquivo> arquivos = new ArrayList<Arquivo>();
		//evita ir novamente no banco.
		if(!getParamAsBoolean(SessionContainer.REQUEST_ATTRIBUTE_PAGINACAO).booleanValue()){
			arquivos = bumerangueService.listarArquivos(usuarioLogado);
			getSessionContainer().setArquivos(arquivos);
		}

		//verifica se o objeto deve ser atualizado com os dados do form
		if(request.getAttribute(super.UPDATE_FROM_FORM) != null){
			addObject("nomeModuloSelecionado",getParam("nomeModuloSelecionado"));
			addObject("codigoTipoPermissao",getParam("codigoTipoPermissao"));
			addObject("nomeArquivo",getParam("nomeArquivo"));
		}
		
		Dominio tipoPermissoes = bumerangueService.getDominio(usuarioLogado,Dominio.BUMERANGUE_TIPO_PERMISSAO.getCodigo());
		addObject("tipoPermissoes",tipoPermissoes.getElementosDominio());
		
		return new ModelAndView("arquivos");
	}

	public ModelAndView upload(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuarioLogado = getSessionContainer().getUsuarioLogado();
		String nomeModulo = getParam("nomeModuloSelecionado");
		Integer codigoTipoPermissao = getParamAsInteger("codigoTipoPermissao");
		String nomeArquivo = getParam("nomeArquivo");
		MultipartFile uploadFile = getParamAsFile("uploadFile");

		bumerangueService.uploadArquivo(usuarioLogado, nomeModulo, codigoTipoPermissao, nomeArquivo, uploadFile);
		
		super.addSuccess(BumerangueSuccessRuntimeException.ENVIO_REALIZADO);
		return this.listar(request, response);
	}

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {
		Usuario usuarioLogado = getSessionContainer().getUsuarioLogado();
		String numeroPermissao = getParam("numeroPermissao");
		String nomeArquivo = getParam("fileName");
		Boolean compartilhadoTodasPermissoes = getParamAsBoolean("compartilhadoTodasPermissoes");
		
		bumerangueService.excluirArquivo(usuarioLogado,numeroPermissao,nomeArquivo, compartilhadoTodasPermissoes.booleanValue());
		
		super.addSuccess(BumerangueSuccessRuntimeException.EXCLUSAO_REALIZADA);
		return this.listar(request, response);
	}

}