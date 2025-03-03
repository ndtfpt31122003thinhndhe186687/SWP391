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
<<<<<<< HEAD
=======
import model.Services;
import model.Term;
>>>>>>> origin/phong

/**
 *
 * @author Acer Nitro Tiger
 */
@WebServlet(name = "UpdateServiceTermServlet", urlPatterns = {"/updateServiceTerm"})
public class UpdateServiceTermServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdateServiceTermServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateServiceTermServlet at " + request.getContextPath() + "</h1>");
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
<<<<<<< HEAD
        String termId_raw = request.getParameter("term_id");
        DAO_Admin d = new DAO_Admin();
        try {
            int termId = Integer.parseInt(termId_raw);
        //    ServiceTerms s = d.getServiceTermByTermId(termId);
        //    request.setAttribute("serviceTerm", s);
=======
        String termId_raw = request.getParameter("serviceTerm_id");
        DAO_Admin d = new DAO_Admin();
        try {
            int termId = Integer.parseInt(termId_raw);
            ServiceTerms s = d.getServiceTermById(termId);
            request.setAttribute("serviceTerm", s);
            List<Term> listTerms = d.getAllTerm();
            request.setAttribute("listTerms", listTerms);
>>>>>>> origin/phong
            request.getRequestDispatcher("updateServiceTerm.jsp").forward(request, response);
        } catch (Exception e) {
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
<<<<<<< HEAD
        String termId_raw = request.getParameter("term_id");
        String termName = request.getParameter("term_name");
        String description = request.getParameter("description");
        String contractTerms = request.getParameter("contract_terms");
        String maxTermMonths_raw = request.getParameter("max_term_months");
=======
        String serviceId_raw = request.getParameter("serviceTerm_id");
        String termName = request.getParameter("term_name");
        String description = request.getParameter("description");
        String contractTerms = request.getParameter("contract_terms");
        String termId_raw = request.getParameter("term_id");
>>>>>>> origin/phong
        String paymentPen_raw = request.getParameter("early_payment_penalty");
        String interestRate_raw = request.getParameter("interest_rate");
        String minPayment_raw = request.getParameter("min_payment");
        String minDeposit_raw = request.getParameter("min_deposit");
        String status = request.getParameter("status");
<<<<<<< HEAD
        try {
            int termId = Integer.parseInt(termId_raw);
            int maxTermMonths = (maxTermMonths_raw != null && !maxTermMonths_raw.isEmpty()) ? Integer.parseInt(maxTermMonths_raw) : 0;
            double paymentPen = (paymentPen_raw != null && !paymentPen_raw.isEmpty()) ? Double.parseDouble(paymentPen_raw) : 0.0;
            double interestRate = (interestRate_raw != null && !interestRate_raw.isEmpty()) ? Double.parseDouble(interestRate_raw) : 0.0;
            double minPayment = (minPayment_raw != null && !minPayment_raw.isEmpty()) ? Double.parseDouble(minPayment_raw) : 0.0;
            double minDeposit = (minDeposit_raw != null && !minDeposit_raw.isEmpty()) ? Double.parseDouble(minDeposit_raw) : 0.0;
            ServiceTerms s = new ServiceTerms(termId, maxTermMonths, termName, description, contractTerms, status, paymentPen, interestRate, minPayment, minDeposit);
            DAO_Admin d = new DAO_Admin();
        //    d.updateServiceTerm(s);
            response.sendRedirect("serviceTermManagement");
=======
        DAO_Admin d = new DAO_Admin();
        int serviceTermId = Integer.parseInt(serviceId_raw);
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
            ServiceTerms s = d.getServiceTermById(serviceTermId);
            request.setAttribute("serviceTerm", s);
            List<Term> listTerms = d.getAllTerm();
            request.setAttribute("listTerms", listTerms);
            request.getRequestDispatcher("updateServiceTerm.jsp").forward(request, response);
            return;
        }
        try {
            Integer termId = (termId_raw == null || termId_raw.trim().isEmpty()) ? null : Integer.valueOf(termId_raw);
            double paymentPen = (paymentPen_raw == null || paymentPen_raw.isEmpty()) ? 0.0 : Double.parseDouble(paymentPen_raw);
            double interestRate = (interestRate_raw == null || interestRate_raw.isEmpty()) ? 0.0 : Double.parseDouble(interestRate_raw);
            double minPayment = (minPayment_raw == null || minPayment_raw.isEmpty()) ? 0.0 : Double.parseDouble(minPayment_raw);
            double minDeposit = (minDeposit_raw == null || minDeposit_raw.isEmpty()) ? 0.0 : Double.parseDouble(minDeposit_raw);
            ServiceTerms s = new ServiceTerms(serviceTermId, termId, termName, description, contractTerms, status, 
                    paymentPen, interestRate, minPayment, minDeposit);
            d.updateServiceTerm(s);
            response.sendRedirect("serviceTermManagement?serviceName=all&sort=all&page=1&pageSize=4");
>>>>>>> origin/phong
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
