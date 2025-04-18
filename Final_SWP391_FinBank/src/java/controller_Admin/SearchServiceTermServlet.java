/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_Admin;

import dal.DAO_Admin;
import dal.DAO_Marketer;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.News;
import model.ServiceTerms;
import model.Services;
import model.Staff;

/**
 *
 * @author Acer Nitro Tiger
 */
@WebServlet(name = "SearchServiceTermServlet", urlPatterns = {"/searchServiceTerm"})
public class SearchServiceTermServlet extends HttpServlet {

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
            out.println("<title>Servlet SearchServiceTermServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SearchServiceTermServlet at " + request.getContextPath() + "</h1>");
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
        List<Services> listS = d.getAllServices();
        request.setAttribute("listS", listS);
        String searchName = request.getParameter("searchName");
        String page_raw = request.getParameter("page");
        String pageSize_raw = request.getParameter("pageSize");

        if (searchName == null && searchName.trim().isEmpty()) {
            searchName = "%";
        } else {
            searchName = searchName.trim().replaceAll("\\s+", " ");
            if (searchName.contains(" ")) {
                searchName = searchName.replace(" ", "");
            }
            searchName = "%" + searchName + "%";
        }

        List<ServiceTerms> listSt = d.getServiceTermByName(searchName);
        request.setAttribute("searchName", searchName);
        //paging
        int pageSize = Integer.parseInt(pageSize_raw);
        int totalNews = listSt.size();
        int totalPage = totalNews % pageSize == 0 ? (totalNews / pageSize) : ((totalNews / pageSize) + 1);
        int page = (page_raw == null) ? 1 : Integer.parseInt(page_raw);
        int start = (page - 1) * pageSize;
        int end = Math.min(page * pageSize, totalNews);
        List<ServiceTerms> list = d.getListByPage(listSt, start, end);
        request.setAttribute("listSt", list);
        request.setAttribute("page", page);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("pageSize", pageSize);
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
        DAO_Admin d = new DAO_Admin();
        List<Services> listS = d.getAllServices();
        request.setAttribute("listS", listS);
        String searchName = request.getParameter("search");
        String page_raw = request.getParameter("page");
        String pageSize_raw = request.getParameter("pageSize");

        if (searchName == null && searchName.trim().isEmpty()) {
            searchName = "%";
        } else {
            searchName = searchName.trim().replaceAll("\\s+", " ");
            if (searchName.contains(" ")) {
                searchName = searchName.replace(" ", "");
            }
            searchName = "%" + searchName + "%";
        }

        List<ServiceTerms> listSt = d.getServiceTermByName(searchName);
        //paging
        int pageSize = Integer.parseInt(pageSize_raw);
        int totalNews = listSt.size();
        int totalPage = totalNews % pageSize == 0 ? (totalNews / pageSize) : ((totalNews / pageSize) + 1);
        int page = (page_raw == null) ? 1 : Integer.parseInt(page_raw);
        int start = (page - 1) * pageSize;
        int end = Math.min(page * pageSize, totalNews);
        List<ServiceTerms> list = d.getListByPage(listSt, start, end);
        request.setAttribute("listSt", list);
        request.setAttribute("page", page);
        request.setAttribute("searchName", searchName);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("pageSize", pageSize);
        request.getRequestDispatcher("serviceTermManagement.jsp").forward(request, response);
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
