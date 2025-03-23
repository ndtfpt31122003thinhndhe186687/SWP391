/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_Service;

import dal.DAO_Loan;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Customer;
import model.Loan;
import model.Loan_payments;

/**
 *
 * @author DELL
 */
@WebServlet(name = "LoansListServlet", urlPatterns = {"/loanList"})
public class LoansListServlet extends HttpServlet {

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
            out.println("<title>Servlet LoansListServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoansListServlet at " + request.getContextPath() + "</h1>");
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
        double totalLoan = 0;
        DAO_Loan d = new DAO_Loan();
        HttpSession session = request.getSession();
        Customer c = (Customer) session.getAttribute("account");
        List<Loan> loanList = d.getListLoanByCustomerId(c.getCustomer_id());
        for (Loan loan : loanList) {
            totalLoan += loan.getAmount();
        }
        request.setAttribute("loanList", loanList);
        request.setAttribute("totalLoan", totalLoan);
        request.getRequestDispatcher("loanCustomer.jsp").forward(request, response);
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
        DAO_Loan d = new DAO_Loan();
        HttpSession session = request.getSession();
        Customer c = (Customer) session.getAttribute("account");
        String loan_id_raw = request.getParameter("id");
        String status = request.getParameter("status");
        int loan_id;
        double totalLoan = 0;
        try {

            loan_id = Integer.parseInt(loan_id_raw);
            List<Loan> loanList = d.getListLoanByCustomerId(c.getCustomer_id());
            for (Loan loan : loanList) {
                totalLoan += loan.getAmount();
            }
            request.setAttribute("totalLoan", totalLoan);
            if ("pending".equals(status)) {
                request.setAttribute("message", "Khoản vay của bạn chưa được chấp nhận !");
                request.getRequestDispatcher("loanCustomer.jsp").forward(request, response);
                return;
            }

            List<Loan_payments> list = d.getPaymentsByLoanIdandCustomerId(loan_id, c.getCustomer_id());
            request.setAttribute("list", list);
            request.getRequestDispatcher("loanPaymentCustomer.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            System.out.println(e);
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
