/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dulcejosefina.dulcejosefinaadmin.server;

import com.dulcejosefina.dulcejosefinaadmin.reporte.Reportes;
import com.dulcejosefina.ejb.EJBVentaSucursalService;
import com.dulcejosefina.ejb.Exception_Exception;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author Edgardo
 */
@WebServlet(name = "ShowReportVentasAnuladas", urlPatterns = {"/ShowReportVentasAnuladas"})
public class ShowReportVentasAnuladas extends HttpServlet {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/EJBVentaSucursalService/EJBVentaSucursal.wsdl")
    private EJBVentaSucursalService service;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0L);
    response.setContentType("application/pdf");
    
        Reportes reporte = new Reportes();
        try { // Call Web Service Operation
            com.dulcejosefina.ejb.EJBVentaSucursal port = service.getEJBVentaSucursalPort();
            // TODO process result here
            java.lang.String result = port.getSalesCanceledTodayAndPrevius();
            ServletOutputStream servletOutputStream = response.getOutputStream();
    org.w3c.dom.Document doc = reporte.obtenerDocumentoParseado(result);
    byte[] bytes = reporte.obtenerReporteJasper(doc, "/Lista/venta", reporte.obtenerReporteVentaAnuladas());
    response.setContentType("application/pdf");
    response.setContentLength(bytes.length);
    servletOutputStream.write(bytes, 0, bytes.length);
    servletOutputStream.flush();
        } catch (Exception_Exception | IOException ex) {
            
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
