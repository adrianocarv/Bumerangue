package br.org.ceu.bumerangue.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import br.org.ceu.bumerangue.service.exceptions.BumerangueErrorRuntimeException;

/**
 * Classe utilitária para exportação de relatórios.
 */
public class ExportaRelatorio {

    public static final int PDF = 1;
    public static final int XLS = 2;
    public static final int TXT = 3;
    public static final int RTF = 4;

    /**
     * Exporta para PDF.
     */
    public static void pdf(HttpServletResponse response, JRDataSource jrDataSource, JasperReport jasperReport, Map params) {

        OutputStream oStream = null;
        try {
	    	JasperPrint print = JasperFillManager.fillReport(jasperReport, params, jrDataSource);
		    byte[] buffer = JasperExportManager.exportReportToPdf(print);
	
	        response.setContentType(ExportTool.PDF1_MIME);
	        response.setContentLength(buffer.length);
	        response.setHeader("Content-disposition", "attachment; filename=report.pdf");
	
            oStream = response.getOutputStream();
            oStream.write(buffer, 0, buffer.length);
            oStream.flush();
        }catch (Exception e) {
			throw new BumerangueErrorRuntimeException(e.getMessage());
        } finally {
            if (oStream != null) {
                try{
                	oStream.close();
                }catch (Exception e) {
        			throw new BumerangueErrorRuntimeException(e.getMessage());
				}
            }
        }
    }

    /**
     * Exporta para XLS.
     */
    public static void xls(HttpServletResponse response, JRDataSource jrDataSource, JasperReport jasperReport, Map params){

	    OutputStream oStream = null;
	    try {
	        JasperPrint print = JasperFillManager.fillReport(jasperReport, params, jrDataSource);
			response.setContentType(ExportTool.EXCEL1_MIME);
			response.setHeader( "Content-disposition", "attachment; filename=report.xls");
	
			oStream = response.getOutputStream();

			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporterXLS.setParameter(JRExporterParameter.OUTPUT_STREAM, oStream);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.FALSE);
			exporterXLS.exportReport();

        }catch (Exception e) {
			throw new BumerangueErrorRuntimeException(e.getMessage());
        } finally {
            if (oStream != null) {
                try{
                	oStream.close();
                }catch (Exception e) {
        			throw new BumerangueErrorRuntimeException(e.getMessage());
				}
            }
        }
    }

    /**
     * Exporta para CSV.
     */
    public static void csv(HttpServletResponse response, JRDataSource jrDataSource, JasperReport jasperReport, Map params) {

        OutputStream oStream = null;
        try {
	    	JasperPrint print = JasperFillManager.fillReport(jasperReport, params, jrDataSource);
			response.setContentType(ExportTool.TEXT_MIME);
			response.setHeader( "Content-disposition", "attachment; filename=report.txt");

            oStream = response.getOutputStream();

            JRCsvExporter csvExporter = new JRCsvExporter();
            csvExporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER, "\t");
           	csvExporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
           	csvExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, oStream);
           	csvExporter.exportReport();
        }catch (Exception e) {
			throw new BumerangueErrorRuntimeException(e.getMessage());
        } finally {
            if (oStream != null) {
                try{
                	oStream.close();
                }catch (Exception e) {
        			throw new BumerangueErrorRuntimeException(e.getMessage());
				}
            }
        }
    }

    /**
     * Exporta para RTF.
     */
    public static void rtf(HttpServletResponse response, JRDataSource jrDataSource, JasperReport jasperReport, Map params) {

    	OutputStream oStream = null;
        try {
	    	JasperPrint print = JasperFillManager.fillReport(jasperReport, params, jrDataSource);
			response.setContentType(ExportTool.RTF_MIME);
			response.setHeader( "Content-disposition", "attachment; filename=report.rtf");

            oStream = response.getOutputStream();

            JRRtfExporter exporter = new JRRtfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, oStream);
            
            exporter.exportReport();
        }catch (Exception e) {
			throw new BumerangueErrorRuntimeException(e.getMessage());
        } finally {
            if (oStream != null) {
                try{
                	oStream.close();
                }catch (Exception e) {
        			throw new BumerangueErrorRuntimeException(e.getMessage());
				}
            }
        }
    }

    /**
     * Faz a exportação do relatório.
     *
     * @param response HTTP responde.
     * @param tipoExportacao tipo da exportação (PDF, XLS ou TXT).
     * @param jrDataSource fonte de dados para o relatório.
     * @param jasperReport objeto representando o relatório do JasperReports.
     * @param params parâmetro do relatório.
     *
     * @throws IllegalArgumentException caso o argumento <code>tipoExportacao</code> seja inválido.
     * @throws JRException caso ocorra erro ao montar o relatório.
     * @throws IOException caso ocorra erro ao enviar relatório ao cliente.
     */
    public static void exporta(HttpServletResponse response, int tipoExportacao, JRDataSource jrDataSource, JasperReport jasperReport, Map params) {

        System.out.println("relatório: "+jasperReport.getName());

        switch (tipoExportacao) {
		case PDF:
			pdf(response, jrDataSource, jasperReport, params);
			break;

		case XLS:
			xls(response, jrDataSource, jasperReport, params);
			break;

		case TXT:
			csv(response, jrDataSource, jasperReport, params);
			break;

		case RTF:
			rtf(response, jrDataSource, jasperReport, params);
			break;

		default:
			throw new IllegalArgumentException("Tipo de exportação inválido: " + tipoExportacao);
		}
	}
}
