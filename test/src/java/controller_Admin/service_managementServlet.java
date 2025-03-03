/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_Admin;

import dal.DAO;
import dal.DAO_Admin;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Services;
import model.Term;

/**
 *
 * @author DELL
 */
@WebServlet(name = "service_managementServlet", urlPatterns = {"/service_management"})
public class service_managementServlet extends HttpServlet {

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
            out.println("<title>Servlet service_managementServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet service_managementServlet at " + request.getContextPath() + "</h1>");
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
        String type = request.getParameter("type");
<<<<<<< HEAD
        String status = request.getParameter("status");
        String sortBy = request.getParameter("sort");
        String page_raw = request.getParameter("page");
        String pageSize_raw = request.getParameter("pageSize");
        int pageSize;
        List<Services> listService;
        List<Term> listTerm;
=======
>>>>>>> origin/phong
        if (type == null) {
            type = "services";
        }
        if ("services".equals(type)) {
<<<<<<< HEAD
            listService = d.getAllServices();
            status = (status == null) ? "all" : status;
            sortBy = (sortBy == null) ? "service_name" : sortBy;
            pageSize_raw = (pageSize_raw ==null) ? "2": pageSize_raw;
            try {
                if (status.equals("all")) {
                    listService = d.get_All_Service_Sorted(sortBy);
                } else {
                    listService = d.get_All_Service_By_Status_Sorted(sortBy, status);
                }
                pageSize = Integer.parseInt(pageSize_raw);
                int totalService = listService.size();
                int totalPage = totalService % pageSize == 0
                        ? (totalService / pageSize) : ((totalService / pageSize) + 1);
                int page = (page_raw == null) ? 1 : Integer.parseInt(page_raw);
                int start = (page - 1) * pageSize;
                int end = Math.min(page * pageSize, totalService);
                List<Services> listServicePage = d.getServiceByPage(listService, start, end);
                request.setAttribute("service", listServicePage);
                request.setAttribute("sort", sortBy);
                request.setAttribute("status", status);
                request.setAttribute("type", type);
                request.setAttribute("page", page);
                request.setAttribute("totalPage", totalPage);
                request.setAttribute("pageSize", pageSize);
            } catch (Exception e) {
            }
        } else if ("term".equals(type)) {
            listTerm = d.getAllTerm();
            status = (status == null) ? "all" : status;
            sortBy = (sortBy == null) ? "term_name" : sortBy;
            pageSize_raw = (pageSize_raw ==null) ? "2": pageSize_raw;
            try {
                if (status.equals("all")) {
                    listTerm = d.get_All_Term_Sorted(sortBy);
                } else {
                    listTerm = d.get_All_Term_By_Status_Sorted(sortBy, status);
                }
                 pageSize = Integer.parseInt(pageSize_raw);
                int totalTerm = listTerm.size();
                int totalPage = totalTerm % pageSize == 0 ? (totalTerm / pageSize)
                        : ((totalTerm / pageSize) + 1);
                int page = (page_raw == null) ? 1 : Integer.parseInt(page_raw);
                int start = (page - 1) * pageSize;
                int end = Math.min(page * pageSize, totalTerm);
                List<Term> listTermPage = d.getTermByPage(listTerm, start, end);
                request.setAttribute("term", listTermPage);
                request.setAttribute("sort", sortBy);
                request.setAttribute("status", status);
                request.setAttribute("type", type);
                request.setAttribute("page", page);
                request.setAttribute("totalPage", totalPage);
                request.setAttribute("pageSize", pageSize);
            } catch (Exception e) {
            }
        }

=======
            List<Services> list = d.getAllServices();
            request.setAttribute("service", list);
        } else if ("term".equals(type)) {
            List<Term> list = d.getAllTerm();
            request.setAttribute("term", list);
        }
        
>>>>>>> origin/phong
        request.getRequestDispatcher("service management.jsp").forward(request, response);
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
