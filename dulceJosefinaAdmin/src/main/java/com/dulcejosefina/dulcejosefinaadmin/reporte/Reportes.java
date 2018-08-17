package com.dulcejosefina.dulcejosefinaadmin.reporte;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class Reportes {

    private static final String PATH = ResourceBundle.getBundle("config").getString("PATH_REPORT");

    ;
    
    public String obtenerVenta() {
        return PATH + "Venta.jasper";
    }

    public Document obtenerDocumentoParseado(String xml) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException localParserConfigurationException) {
        }

        InputSource is = new InputSource();
        is.setCharacterStream(new java.io.StringReader(xml));
        Document doc = null;
        try {
            doc = db.parse(is);
        } catch (org.xml.sax.SAXException | java.io.IOException ex) {
            java.util.logging.Logger.getLogger(Reportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return doc;
    }

    public byte[] obtenerReporteJasper(Document doc, String pathReporte, String nombreReporteJasper) {
         JRXmlDataSource xmlDataSource = null;
                            try {
                                xmlDataSource = new JRXmlDataSource(doc, pathReporte);
                            } catch (JRException ex) {
                                    Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);                                
                            }                            
                                byte[] bytes = null;                            
                             try {
                                    Map map = new HashMap();
                                    map.put("SUBREPORT_DIR","");
                                     map.put("LOGO_DULCE", obtenerLogoDulce());
                                        bytes = JasperRunManager.runReportToPdf(nombreReporteJasper, map, xmlDataSource);                                        
                                } catch (JRException e) {                                 
                                        StringWriter stringWriter = new StringWriter();
                                        PrintWriter printWriter = new PrintWriter(stringWriter);
                                        e.printStackTrace(printWriter);                                        
                                }   
       return bytes;
    }

    public String obtenerPresupuesto() {
        return PATH + "presupuestoJosefina.jasper";
    }

    public String obtenerReporteProductosAVencer() {
        return PATH + "productosAVencerDeHoyASieteDias.jasper";
    }

    public String obtenerReporteVentaDia() {
        return PATH + "reportVentaDia.jasper";
    }

    public String obtenerReporteStockMinimo() {
        return PATH + "productosSinStockMinimo.jasper";
    }

    public String obtenerReportFraccionadoSinStockPorProveedor() {
        return PATH + "reportSinStockFraccionadoXProveedor.jasper";
    }

    public String obtenerReporteSinStockPorProveedor() {
        return PATH + "reportProductosSinStockPorProveedor.jasper";
    }

    public String obtenerReportePedidoProveedor() {
        return PATH + "reportPedidoProveedor.jasper";
    }

    private String obtenerLogoDulce() {
        return PATH + "Images" + java.io.File.separator + "dulceJosefina.jpg";
    }
    public String obtenerReporteVentaAnuladas() {
       
        return PATH + "ventasAnuladas2.jasper";
    }
}
