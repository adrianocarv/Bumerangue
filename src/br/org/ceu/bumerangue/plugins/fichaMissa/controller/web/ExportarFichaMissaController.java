package br.org.ceu.bumerangue.plugins.fichaMissa.controller.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.web.servlet.ModelAndView;

import br.org.ceu.bumerangue.controller.web.BaseRelatorioController;
import br.org.ceu.bumerangue.entity.Missa;
import br.org.ceu.bumerangue.entity.PlanoMissa;
import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.entity.suporte.deploy.Deploy;
import br.org.ceu.bumerangue.service.FichaMissaService;
import br.org.ceu.bumerangue.service.exceptions.BumerangueErrorRuntimeException;
import br.org.ceu.bumerangue.util.ExportaRelatorio;
import br.org.ceu.bumerangue.util.SessionContainer;
import br.org.ceu.bumerangue.util.Utils;

public class ExportarFichaMissaController extends BaseRelatorioController {
	
	/** Service */
	private FichaMissaService fichaMissaService;
	public void setFichaMissaService(FichaMissaService fichaMissaService){ this.fichaMissaService = fichaMissaService; }

	public boolean isPertencenteModuloDisponivel(){
		return Deploy.isModuloDisponivel(Deploy.MODULO_VIDEO);
	}

	public boolean isMetodoAutorizado(){
		return true;
	}

	public ModelAndView validateMulti(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	public ModelAndView exportarFichasMissa(HttpServletRequest request, HttpServletResponse response) {
		//recupera do banco
		Missa missa = fichaMissaService.getMissa(getSessionContainer().getUsuarioLogado(),getParam("idMissa"));
		PlanoMissa planoMissa = fichaMissaService.getPlanoMissa(getSessionContainer().getUsuarioLogado(),getParam("id"));

		//ao exportar, valida o plano de missa, pois mesmo já aprovado, os grupo referenciados podem ter sido mudados.
		//para o caso de uma missa apenas, valida a mesma.
		boolean valido = missa != null ? missa.isValida() : planoMissa.isValido();
		if( !valido ){
			List<String> messages = new ArrayList<String>();

			if(missa != null)
				messages.add(missa.getErrosValidacao());
			else
				messages.add(planoMissa.getErrosValidacao());

			request.setAttribute(SessionContainer.REQUEST_ATTRIBUTE_MESSAGES,messages);
        	request.setAttribute(SessionContainer.REQUEST_ATTRIBUTE_MESSAGE_ICO_FILE_NAME, SessionContainer.REQUEST_ATTRIBUTE_MESSAGE_ICO_ERROR);
			try {
				if(missa != null)
					request.getRequestDispatcher("manterPlanoMissa.action?method=detalharMissa&id="+getParam("idMissa")).forward(request,response);
				else
					request.getRequestDispatcher("manterPlanoMissa.action?method=detalhar&id="+getParam("id")).forward(request,response);
			} catch (Exception e) {
				throw new BumerangueErrorRuntimeException(e.getMessage());
			}
		}	
		
		//cria o JRDataSource
		List<Missa> missas = new ArrayList<Missa>();
		if(missa != null)
			missas.add(missa);
		else
			missas = planoMissa.getMissas();
		
		JRDataSource ds = new JRBeanCollectionDataSource(missas);
		
		//recupera o Jasper
		String nomeRelatorio = "planoMissa"+getParam("formato");

		JasperReport jasperReport = super.getJasperReport(request,nomeRelatorio);
		
		ExportaRelatorio.exporta(response,getParamAsInteger("tipoExportacao"),ds,jasperReport,null);

		return new ModelAndView("apresentacao");
	
	}
	
}