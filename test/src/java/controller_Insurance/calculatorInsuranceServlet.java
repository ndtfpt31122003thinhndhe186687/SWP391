/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_Insurance;

import dal.DAO_Insurance;
import dal.SavingDAO;
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
import model.Insurance;
import model.Insurance_contract_detail;
import model.Insurance_transactions;

/**
 *
 * @author Windows
 */
@WebServlet(name = "calculatorInsuranceServlet", urlPatterns = {"/calculatorInsurance"})
public class calculatorInsuranceServlet extends HttpServlet {

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
            out.println("<title>Servlet calculatorInsuranceServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet calculatorInsuranceServlet at " + request.getContextPath() + "</h1>");
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
        DAO_Insurance d = new DAO_Insurance();
        HttpSession session = request.getSession();
        String insurance_id_raw = request.getParameter("insurance_id");
        String contract_id_raw = request.getParameter("contract_id");
        String policy_id_raw = request.getParameter("policy_id");
        int insurace_id = Integer.parseInt(insurance_id_raw);
        int contract_id = Integer.parseInt(contract_id_raw);
        int policy_id = Integer.parseInt(policy_id_raw);
        Customer customer = (Customer) session.getAttribute("account");
        Insurance_contract_detail cd = d.getInsuranceContractDetailByContractid(contract_id, insurace_id);
        int countTransaction = d.countTransactionByCustomerIDAndContractID(customer.getCustomer_id(), contract_id);
        int durationPay = 0;
        double result = 0;
        String frequency = cd.getPayment_frequency();
            if (countTransaction == 1) {
        countTransaction--;
    }
            if ("monthly".equals(frequency)) {
                durationPay = cd.getDuration() - countTransaction;
                result = (cd.getPremiumAmount() - cd.getPaidAmount()) / durationPay;
            } else if ("quarterly".equals(frequency)) {
                durationPay = (cd.getDuration() / 3) - countTransaction;
                result = (cd.getPremiumAmount() - cd.getPaidAmount())  / durationPay ;
            } else if ("annually".equals(frequency)) {
                durationPay = (cd.getDuration() / 12) - countTransaction;
                result = (cd.getPremiumAmount() - cd.getPaidAmount())  / durationPay ;
            }
            
        
        request.setAttribute("contract_id", contract_id);
        request.setAttribute("insurance_id", insurace_id);
        request.setAttribute("policy_id", policy_id);
        request.setAttribute("money", result);
        request.setAttribute("cd", cd);
        request.getRequestDispatcher("payInsurancePolicy.jsp").forward(request, response);
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
        DAO_Insurance dao = new DAO_Insurance();
        SavingDAO daoS = new SavingDAO();
        HttpSession session = request.getSession();
        Customer c = (Customer) session.getAttribute("account");
        String insurance_id_raw = request.getParameter("insurance_id");
        String contract_id_raw = request.getParameter("contract_id");
        String amount_raw = request.getParameter("amount");
        int insurance_id = Integer.parseInt(insurance_id_raw);
        int contract_id = Integer.parseInt(contract_id_raw);
        double amount = Double.parseDouble(amount_raw);
        if (amount > c.getAmount()) {
            request.setAttribute("error", "Số tiền cần đóng lớn hơn số tiền bạn đang có trong tài khoản!");
            request.setAttribute("contract_id", contract_id);
            request.setAttribute("insurance_id", insurance_id);
            request.setAttribute("money", amount);
            request.getRequestDispatcher("payInsurancePolicy.jsp").forward(request, response);
            return;
        }
        Insurance_transactions it = new Insurance_transactions(contract_id, c.getCustomer_id(), amount, "premium_payment", "Thanh toán phí bảo hiểm");
        daoS.updateAmount(c.getCustomer_id(), amount);
        dao.updateInsuranceContractDetail(amount, contract_id, insurance_id);
        dao.insertInsuranceTransaction(it);
        session.setAttribute("showSuccessModal", true);
        session.setAttribute("successMessage", "Bạn đã nộp tiền bảo hiểm thành công!");
        response.sendRedirect("home.jsp");

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
