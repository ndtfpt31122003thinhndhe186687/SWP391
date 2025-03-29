/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_Service;

import dal.DAO_Admin;
import dal.DAO_Marketer;
import dal.SavingDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Customer;
import model.Feedback;
import model.News;
import model.ServiceTerms;

/**
 *
 * @author Acer Nitro Tiger
 */
@WebServlet(name = "DepositSavingServlet", urlPatterns = {"/depositSaving"})
public class DepositSavingServlet extends HttpServlet {

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
            out.println("<title>Servlet DepositSavingServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DepositSavingServlet at " + request.getContextPath() + "</h1>");
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
        SavingDAO d = new SavingDAO();
        DAO_Admin dao = new DAO_Admin();
        DAO_Marketer dm = new DAO_Marketer();
        List<ServiceTerms> listS = d.getDepositService();
        List<Customer> listC = dao.getCustomerByServiceID(1);
        List<Feedback> listF = dao.getListFeedbackByServiceID(1);
        int count = dao.getTotalFeedbackByServiceID(1);
        int point = 0, star = 0;
        for (Feedback feedback : listF) {
            point += feedback.getFeedback_rate();
        }
        if (point != 0) {
            star = point / count;
        }
        request.setAttribute("star", star);
        request.setAttribute("listF", listF);
        request.setAttribute("listS", listS);
        request.setAttribute("listC", listC);
        List<News> listN = dm.getAllNewsByCategory(4);
        request.setAttribute("listNews", listN);
        request.getRequestDispatcher("depositSaving.jsp").forward(request, response);

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
