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
import model.ServiceTerms;
import model.Services;

/**
 *
 * @author Acer Nitro Tiger
 */
@WebServlet(name = "ServiceTermManagement", urlPatterns = {"/serviceTermManagement"})
public class ServiceTermManagement extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
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
            out.println("<title>Servlet ServiceTermManagement</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServiceTermManagement at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        DAO_Admin d = new DAO_Admin();
        String page_raw = request.getParameter("page");
        String serviceName = request.getParameter("serviceName");
        String sort = request.getParameter("sort");
        String pageSize_raw=request.getParameter("pageSize");
        if (serviceName == null || serviceName.isEmpty()) {
            serviceName = "all";
        }

        if (sort == null || sort.isEmpty()) {
            sort = "all";
        }
        List<ServiceTerms> listSt;
        if (serviceName.equals("all")) {
            listSt = d.getFilteredServiceTerms("all", sort);
        } else {
            listSt = d.getFilteredServiceTerms(serviceName, sort);
        }
        request.setAttribute("serviceName", serviceName);
        request.setAttribute("sort", sort);
        int pageSize=Integer.parseInt(pageSize_raw);
        int totalServiceTerm=listSt.size();
        int totalPage=(totalServiceTerm%pageSize==0)?(totalServiceTerm/pageSize) :(totalServiceTerm/pageSize+1);
        int page=(page_raw==null)?1:Integer.parseInt(page_raw);
        int start=(page-1)*pageSize;
        int end=Math.min(page*pageSize, totalServiceTerm);
        List<ServiceTerms> list=d.getListByPage(listSt, start, end);
        request.setAttribute("listSt", list);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("page", page);
        request.setAttribute("pageSize", pageSize);
        List<Services> listS = d.getAllServices();
        request.setAttribute("listS", listS);
        request.getRequestDispatcher("serviceTermManagement.jsp").forward(request, response);
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
