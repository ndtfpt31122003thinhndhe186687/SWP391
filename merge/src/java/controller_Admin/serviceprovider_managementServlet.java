/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller_Admin;

import dal.DAO_Admin;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.ServiceProvider;

/**
 *
 * @author DELL
 */
@WebServlet(name="serviceprovider_managementServlet", urlPatterns={"/serviceprovider_management"})
public class serviceprovider_managementServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet serviceprovider_managementServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet serviceprovider_managementServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        DAO_Admin d = new DAO_Admin();
        String status = request.getParameter("status");
        String sortBy = request.getParameter("sort");
        String page_raw = request.getParameter("page");
        String pageSize_raw = request.getParameter("pageSize");
        int pageSize;
        List<ServiceProvider> listServiceProvider;
        listServiceProvider = d.getAllServiceProvider();
        status = (status == null) ? "all" : status;
        sortBy = (sortBy == null) ? "Name" : sortBy;
        pageSize_raw = (pageSize_raw == null) ? "2" : pageSize_raw;
        try {
            if (status.equals("all")) {
                listServiceProvider = d.getAllServiceProviderSorted(sortBy);
            } else {
                listServiceProvider = d.gettAllServiceProviderSortedByStatus(sortBy, status);
            }
            pageSize = Integer.parseInt(pageSize_raw);
            int totalServiceProvider = listServiceProvider.size();
            int totalPage = totalServiceProvider % pageSize == 0
                    ? (totalServiceProvider / pageSize) : ((totalServiceProvider / pageSize) + 1);
            int page = (page_raw == null) ? 1 : Integer.parseInt(page_raw);
            int start = (page - 1) * pageSize;
            int end = Math.min(page * pageSize, totalServiceProvider);
            List<ServiceProvider> listServiceProviderPage =
            d.getServiceProviderByPage(listServiceProvider, start, end);
            request.setAttribute("serviceprovider", listServiceProviderPage);
            request.setAttribute("sort", sortBy);
            request.setAttribute("status", status);
            request.setAttribute("page", page);
            request.setAttribute("totalPage", totalPage);
            request.setAttribute("pageSize", pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("serviceprovider management.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
