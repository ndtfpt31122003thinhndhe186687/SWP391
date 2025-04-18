/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_Insurance;

import dal.DAO;
import dal.DAO_Insurance;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Insurance;

/**
 *
 * @author Windows
 */
@WebServlet(name = "managerStatisticInsuranceServlet", urlPatterns = {"/managerStatisticInsurance"})
public class ManagerStatisticInsuranceServlet extends HttpServlet {

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
            out.println("<title>Servlet managerStatisticInsuranceServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet managerStatisticInsuranceServlet at " + request.getContextPath() + "</h1>");
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
        DAO_Insurance dao = new DAO_Insurance();
        HttpSession session = request.getSession();
        Insurance i = (Insurance) session.getAttribute("account");
        if (i == null) {
            response.sendRedirect("login.jsp"); // Nếu session bị mất, chuyển về trang đăng nhập
            return;
        }
        request.setAttribute("totalPolicies", dao.getTotalInsurancePolicy(i.getInsurance_id()));
        request.setAttribute("totalTerms", dao.getTotalInsuranceTerm(i.getInsurance_id()));
        request.setAttribute("totalContract", dao.getTotalInsuranceContract(i.getInsurance_id()));
        request.setAttribute("totalCustomers", dao.getTotalInsuranceCustomer(i.getInsurance_id()));
        request.setAttribute("totalTransaction", dao.getTotalInsuranceTransaction(i.getInsurance_id()));
        request.setAttribute("totalFeedback", dao.getTotalInsuranceFeedback(i.getInsurance_id()));
        request.setAttribute("activePolicys", dao.getTotalInsurancePolicyByStatus(i.getInsurance_id(), "active"));
        request.setAttribute("activeTerms", dao.getTotalInsuranceTermByStatus(i.getInsurance_id(), "active"));
        request.setAttribute("activeContract", dao.getTotalInsuranceContractByStatus(i.getInsurance_id(), "active"));
        request.setAttribute("maleCustomers", dao.getTotalInsuranceCustomerByGender(i.getInsurance_id(), "male"));
        request.setAttribute("femaleCustomers", dao.getTotalInsuranceCustomerByGender(i.getInsurance_id(), "female"));
        request.setAttribute("payment", dao.getTotalInsuranceTransactionByType(i.getInsurance_id(), "premium_payment"));
        request.setAttribute("claim", dao.getTotalInsuranceTransactionByType(i.getInsurance_id(), "claim_payment"));
        request.getRequestDispatcher("managerInsuranceStatistic.jsp").forward(request, response);
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
