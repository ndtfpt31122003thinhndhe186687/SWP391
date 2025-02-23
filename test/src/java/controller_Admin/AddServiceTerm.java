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
import model.Term;

/**
 *
 * @author Acer Nitro Tiger
 */
@WebServlet(name = "AddServiceTerm", urlPatterns = {"/addServiceTerm"})
public class AddServiceTerm extends HttpServlet {

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
            out.println("<title>Servlet AddServiceTerm</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddServiceTerm at " + request.getContextPath() + "</h1>");
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
        List<Term> listTerms = d.getAllTerm();
        request.setAttribute("listTerms", listTerms);
        request.getRequestDispatcher("addServiceTerm.jsp").forward(request, response);
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
        String serviceId_raw = request.getParameter("service_id");
        String termName = request.getParameter("term_name");
        String description = request.getParameter("description");
        String contractTerms = request.getParameter("contract_terms");
        String termId_raw = request.getParameter("term_id");
        String paymentPen_raw = request.getParameter("early_payment_penalty");
        String interestRate_raw = request.getParameter("interest_rate");
        String minPayment_raw = request.getParameter("min_payment");
        String minDeposit_raw = request.getParameter("min_deposit");
        DAO_Admin d = new DAO_Admin();
        if (termName != null) {
            termName = termName.trim();
        }
        if (description != null) {
            description = description.trim();
        }
        if (contractTerms != null) {
            contractTerms = contractTerms.trim();
        }
        if (termName == null || description == null || contractTerms == null
                || termName.matches(".*\\s{2,}.*") || description.matches(".*\\s{2,}.*") || contractTerms.matches(".*\\s{2,}.*")) {
            request.setAttribute("err", "Please enter again. The space between words only needs 1 space!");
            List<Services> listS = d.getAllServices();
            request.setAttribute("listS", listS);
            List<Term> listTerms = d.getAllTerm();
            request.setAttribute("listTerms", listTerms);
            request.getRequestDispatcher("addServiceTerm.jsp").forward(request, response);
            return;
        }
        try {
            Integer termId = (termId_raw == null || termId_raw.trim().isEmpty()) ? null : Integer.valueOf(termId_raw);
            int serviceId=Integer.parseInt(serviceId_raw);
            double paymentPen = (paymentPen_raw == null || paymentPen_raw.isEmpty()) ? 0.0 : Double.parseDouble(paymentPen_raw);
            double interestRate = (interestRate_raw == null || interestRate_raw.isEmpty()) ? 0.0 : Double.parseDouble(interestRate_raw);
            double minPayment = (minPayment_raw == null || minPayment_raw.isEmpty()) ? 0.0 : Double.parseDouble(minPayment_raw);
            double minDeposit = (minDeposit_raw == null || minDeposit_raw.isEmpty()) ? 0.0 : Double.parseDouble(minDeposit_raw);
            ServiceTerms s = new ServiceTerms(termId, serviceId, termName, description, contractTerms, 
                    paymentPen, interestRate, minPayment, minDeposit);
            d.addServiceTerm(s);
            response.sendRedirect("serviceTermManagement?serviceName=all&sort=all&page=1&pageSize=4");
        } catch (Exception e) {
        }

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
