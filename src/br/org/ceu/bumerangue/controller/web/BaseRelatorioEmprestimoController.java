package br.org.ceu.bumerangue.controller.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.web.servlet.ModelAndView;

import br.org.ceu.bumerangue.entity.ObjetoBumerangue;
import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.entity.Video;
import br.org.ceu.bumerangue.service.BumerangueService;
import br.org.ceu.bumerangue.util.ExportaRelatorio;
import br.org.ceu.bumerangue.util.Utils;

public abstract class BaseRelatorioEmprestimoController extends BaseRelatorioController {
	
	/** Service */
	protected BumerangueService bumerangueService;
	public void setBumerangueService(BumerangueService bumerangueService){ this.bumerangueService = bumerangueService; }
	
	protected abstract boolean isMetodoAutorizado();

	protected abstract ModelAndView validateMulti(HttpServletRequest request, HttpServletResponse response);

	protected abstract ModelAndView menu(HttpServletRequest request, HttpServletResponse response);

	protected abstract ModelAndView pesquisarPre(HttpServletRequest request, HttpServletResponse response);

	protected abstract ModelAndView pesquisarHistoricoEmprestimos(HttpServletRequest request, HttpServletResponse response);

	protected abstract ModelAndView detalharEmprestimo(HttpServletRequest request, HttpServletResponse response);

	public ModelAndView emitirFichaEmprestimo(HttpServletRequest request, HttpServletResponse response){
		Usuario usuario = getSessionContainer().getUsuarioLogado();
		String id = request.getAttribute("id") != null ? request.getAttribute("id")+"" : getParam("id");
		String nomeModulo = request.getParameter("nomeModulo");
		
		ObjetoBumerangue objetoBumerangue = bumerangueService.getObjetoBumerangue(usuario,id);

		//cria o JRDataSource
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add("valor");
		JRDataSource ds = new JRBeanCollectionDataSource(arrayList);
		
		//recupera o Jasper
		String nomeRelatorio = "emitirFichaEmprestimo";
		if(objetoBumerangue != null) nomeRelatorio += objetoBumerangue.getClass().getSimpleName();
		else if ("VIDEO".equalsIgnoreCase(nomeModulo))nomeRelatorio += new Video().getClass().getSimpleName()+"Nova";

		JasperReport jasperReport = super.getJasperReport(request,nomeRelatorio);
		
		//parametros
		Map<String,Object> parametros = new HashMap<String,Object>();
		parametros.put("logotipo", Utils.getRealPath(request, "resources/img/logo0.gif"));
		parametros.put("url", super.getResourceMessage("bmg.apresentacao.url"));
		if(objetoBumerangue != null)parametros.put("objetoBumerangue", objetoBumerangue);

		ExportaRelatorio.exporta(response,ExportaRelatorio.PDF,ds,jasperReport,parametros);

		return new ModelAndView("apresentacao");
	}
}