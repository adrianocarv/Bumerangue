package br.org.ceu.bumerangue.controller.web;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.springframework.web.servlet.ModelAndView;

import br.org.ceu.bumerangue.entity.ObjetoBumerangue;
import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.entity.Video;
import br.org.ceu.bumerangue.service.BumerangueService;
import br.org.ceu.bumerangue.service.exceptions.BumerangueErrorRuntimeException;
import br.org.ceu.bumerangue.util.ExportaRelatorio;
import br.org.ceu.bumerangue.util.Utils;

public abstract class BaseRelatorioController extends MultiActionBaseController {
	
	protected abstract boolean isMetodoAutorizado();

	protected abstract ModelAndView validateMulti(HttpServletRequest request, HttpServletResponse response);

	protected ModelAndView exportarPDF(HttpServletRequest request, HttpServletResponse response) {
    	return this.exportar(ExportaRelatorio.PDF, request, response);
	}
	
	protected ModelAndView exportarXLS(HttpServletRequest request, HttpServletResponse response) {
    	return this.exportar(ExportaRelatorio.XLS, request, response);
	}	

	protected ModelAndView exportarTXT(HttpServletRequest request, HttpServletResponse response) {
    	return this.exportar(ExportaRelatorio.TXT, request, response);
	}	

    private ModelAndView exportar(int tipoExportacao, HttpServletRequest request, HttpServletResponse response) {
        try {
			ExportaRelatorio.exporta(response,tipoExportacao, null,null,null);
			return null;

		} catch (Exception ex) {
		
		}
    	return null;
	}
    
    protected JasperReport getJasperReport(HttpServletRequest request, String reportFileName) {
        final String repName = "reports/" + reportFileName + ".jasper";
    	final String repRealPathName = Utils.getRealPath(request,repName);
    	try {
    	    final FileInputStream stream = new FileInputStream( new File(repRealPathName) );
    	    return (JasperReport)JRLoader.loadObject(stream);
    	}
    	catch ( Exception e ) {
			throw new BumerangueErrorRuntimeException(e.getMessage());
    	}
    }
}