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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import model.Customer;
import model.Insurance;
import model.Insurance_contract_detail;
import model.Insurance_transactions;
import java.time.LocalDate;

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

    int insurance_id = Integer.parseInt(insurance_id_raw);
    int contract_id = Integer.parseInt(contract_id_raw);
    int policy_id = Integer.parseInt(policy_id_raw);

    Customer customer = (Customer) session.getAttribute("account");

    // Lấy ngày thanh toán gần nhất
    Date lastPaymentDate = d.getLastPaymentDateTransaction(customer.getCustomer_id(), contract_id);
    LocalDate currentDate = LocalDate.now();

    // Lấy thông tin hợp đồng bảo hiểm
    Insurance_contract_detail cd = d.getInsuranceContractDetailByContractid(contract_id, insurance_id);
    boolean hasPaid = false;

    if (lastPaymentDate != null) {
        LocalDate lastPaid = ((java.sql.Date) lastPaymentDate).toLocalDate();
        String frequency = cd.getPayment_frequency();

        LocalDate periodEnd = lastPaid; // Ngày cuối cùng của kỳ thanh toán

        switch (frequency) {
            case "monthly":
                // Nếu giao dịch gần nhất trong cùng tháng và năm
                hasPaid = (lastPaid.getMonthValue() == currentDate.getMonthValue()) &&
                          (lastPaid.getYear() == currentDate.getYear());
                periodEnd = lastPaid.plusMonths(1).minusDays(1);
                break;
            case "quarterly":
                // Kết thúc kỳ hạn sau 3 tháng kể từ lần thanh toán gần nhất
                periodEnd = lastPaid.plusMonths(3).minusDays(1);
                hasPaid = !currentDate.isBefore(lastPaid) && !currentDate.isAfter(periodEnd);
                break;
            case "annually":
                // Kết thúc kỳ hạn sau 12 tháng kể từ lần thanh toán gần nhất
                periodEnd = lastPaid.plusYears(1).minusDays(1);
                hasPaid = !currentDate.isBefore(lastPaid) && !currentDate.isAfter(periodEnd);
                break;
        }

        // Nếu ngày hiện tại đã qua kỳ hạn, vẫn cho phép thanh toán
        if (currentDate.isAfter(periodEnd)) {
            hasPaid = false;
        }
    }

    double result = 0;
    int durationPay = 0;
    int timePay = 0;
    if (!hasPaid) {
        int countTransaction = d.countTransactionByCustomerIDAndContractID(customer.getCustomer_id(), contract_id);
        countTransaction = countTransaction - 1;
        switch (cd.getPayment_frequency()) {
            case "monthly":
                durationPay = cd.getDuration() - countTransaction;
                break;
            case "quarterly":
                timePay = cd.getDuration() / 3;
                if(cd.getDuration() % 3 != 0){
                    timePay++;
                }
                durationPay = timePay - countTransaction;
                break;
            case "annually":
                timePay = cd.getDuration() / 12;
                if(cd.getDuration() % 12 != 0){
                    timePay++;
                }
                durationPay = (cd.getDuration() / 12) - countTransaction;
                break;
        }

        if (durationPay > 0) {
            result = (cd.getPremiumAmount() - cd.getPaidAmount()) / durationPay;
        }
    }

    request.setAttribute("contract_id", contract_id);
    request.setAttribute("insurance_id", insurance_id);
    request.setAttribute("policy_id", policy_id);
    request.setAttribute("money", hasPaid ? 0 : result); // Nếu đã thanh toán kỳ này, đặt số tiền về 0
    request.setAttribute("cd", cd);
    request.setAttribute("hasPaid", hasPaid);
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
