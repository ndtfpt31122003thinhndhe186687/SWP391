/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_Marketer;

import dal.DAO;
import dal.DAO_Marketer;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.News;
import model.NewsCategory;

/**
 *
 * @author Acer Nitro Tiger
 */
@WebServlet(name = "NewsManageServlet", urlPatterns = {"/newsManage"})
public class NewsManageServlet extends HttpServlet {

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
            out.println("<title>Servlet NewsManageServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NewsManageServlet at " + request.getContextPath() + "</h1>");
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
        String staffId_raw = request.getParameter("staff_id");
        String status = request.getParameter("status");
        String sortBy = request.getParameter("sort");
        String page_raw = request.getParameter("page");
        String pageSize_raw=request.getParameter("pageSize");
        String categoryId_raw = request.getParameter("categoryId");
        List<News> list;         
        DAO_Marketer d = new DAO_Marketer();
        status = (status == null) ? "all" : status;
        sortBy = (sortBy == null) ? "all" : sortBy;
        try {
            int staff_id = Integer.parseInt(staffId_raw);
            int categoryId=Integer.parseInt(categoryId_raw);
            list = d.getNewsFilter(categoryId, staff_id, status, sortBy);
            request.setAttribute("sort", sortBy);
            request.setAttribute("status", status);
            request.setAttribute("categoryId", categoryId);

            int pageSize = Integer.parseInt(pageSize_raw);
            int totalNews = list.size();
            int totalPage = totalNews % pageSize == 0 ? (totalNews / pageSize) : ((totalNews / pageSize) + 1);
            int page = (page_raw == null) ? 1 : Integer.parseInt(page_raw);
            int start = (page - 1) * pageSize;
            int end = Math.min(page * pageSize, totalNews);
            List<News> listN = d.getListByPage(list, start, end);
            request.setAttribute("listN", listN);
            request.setAttribute("page", page);
            request.setAttribute("totalPage", totalPage);
            request.setAttribute("pageSize", pageSize);
            List<NewsCategory> listNc=d.getAllNewsCategory();
            request.setAttribute("listNc", listNc);
            request.getRequestDispatcher("newsManagement.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            System.out.println(e);
        }

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
