
package com.dulcejosefina.dulcejosefinaadmin.server;

import com.dulcejosefina.dulcejosefinaadmin.reporte.Reportes;
import com.dulcejosefina.ejb.ServiceProducto;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;
import org.w3c.dom.Document;

@WebServlet(name = "ShowReportStockMinimo", urlPatterns = {"/ShowReportStockMinimo"})
public class ShowReportStockMinimo extends HttpServlet {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/ServiceProducto/wsProducto.wsdl")
    private ServiceProducto service;

 
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0L);
    response.setContentType("application/pdf");
    
    String xml = null;
        Reportes reporte = new Reportes();
        
        try { // Call Web Service Operation
            com.dulcejosefina.ejb.WsProducto port = service.getWsProductoPort();
            // TODO process result here
            xml = port.seleccionarProductosSinStock();
                 ServletOutputStream servletOutputStream = response.getOutputStream();
Document doc = reporte.obtenerDocumentoParseado(xml);
    byte[] bytes = reporte.obtenerReporteJasper(doc, "/Lista/item", reporte.obtenerReporteStockMinimo());
    response.setContentType("application/pdf");
    response.setContentLength(bytes.length);
    servletOutputStream.write(bytes, 0, bytes.length);
    servletOutputStream.flush();
        } catch (IOException ex) {
            // TODO handle custom exceptions here
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
