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
import model.Transaction;

/**
 *
 * @author DELL
 */
@WebServlet(name = "transaction_managementServlet", urlPatterns = {"/transaction_management"})
public class transaction_managementServlet extends HttpServlet {

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
            out.println("<title>Servlet transaction_managementServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet transaction_managementServlet at " + request.getContextPath() + "</h1>");
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
        List<Transaction> list;
        String page_raw = request.getParameter("page");
        String status = request.getParameter("status");
        String sortBy = request.getParameter("sort");
        String pageSize_raw = request.getParameter("pageSize");
        status = (status == null) ? "all" : status;
        sortBy = (sortBy == null) ? "full_name" : sortBy;
        pageSize_raw = (pageSize_raw ==null) ? "5": pageSize_raw;
        try {
            if (status.equals("all")) {
                list = d.getAllTransactionSorted(sortBy);
            } else {
                list = d.getAllTransactionByTypeSorted(sortBy, status);
            }
            int pageSize = Integer.parseInt(pageSize_raw);
            int totalTransaction = list.size();
            int totalPage = totalTransaction % pageSize == 0
                    ? (totalTransaction / pageSize) : ((totalTransaction / pageSize) + 1);
            int page = (page_raw == null) ? 1 : Integer.parseInt(page_raw);
            int start = (page - 1) * pageSize;
            int end = Math.min(page * pageSize, totalTransaction);
            List<Transaction> listTransactionPage = d.getTransactionByPage(list, start, end);
            request.setAttribute("sort", sortBy);
            request.setAttribute("status", status);
            request.setAttribute("data", listTransactionPage);
            request.setAttribute("page", page);
            request.setAttribute("totalPage", totalPage);
            request.setAttribute("pageSize", pageSize);
        } catch (Exception e) {
        }
        request.getRequestDispatcher("transaction management.jsp").forward(request, response);
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
