/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_Service;

import dal.DAO;
import dal.DAO_Loan;
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
import model.Customer;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import model.Loan;
import model.Loan_payments;

/**
 *
 * @author DELL
 */
@WebServlet(name = "payLoanServlet", urlPatterns = {"/payLoan"})
public class payLoanServlet extends HttpServlet {

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
            out.println("<title>Servlet payLoanServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet payLoanServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DAO dao = new DAO();
        String id_raw = request.getParameter("id");
        String loan_id_raw = request.getParameter("loanid");
        DAO_Loan d = new DAO_Loan();
        int loanPaymentId, loan_id;
        HttpSession session = request.getSession();
        Customer c = (Customer) session.getAttribute("account");
        Customer acc = dao.getInforById(c.getCustomer_id());
        Date paymentDate = Date.valueOf(LocalDate.now()); // date now
        Date currentDate; // date database
        double penaltyfee = 0;
        double totalLoanpayments = 0;
        int lateMonths = 0;
        SavingDAO sd = new SavingDAO();
        // test late payment
//        String date = "2026-07-07";
//        java.util.Date sqlDate = java.sql.Date.valueOf(date);
//        paymentDate = (Date) sqlDate;     
        try {
            loanPaymentId = Integer.parseInt(id_raw);
            loan_id = Integer.parseInt(loan_id_raw);
            currentDate = (Date) d.getLoanPaymentById(loanPaymentId).getPayment_date();
            // Chuyển đổi sang LocalDate để so sánh
            LocalDate currentLocalDate = currentDate.toLocalDate();
            LocalDate paymentLocalDate = paymentDate.toLocalDate();
            double payment = d.getAmountPayment(loanPaymentId);
            if (paymentLocalDate.isBefore(currentLocalDate)) {
                request.setAttribute("message", "Chưa đến thời gian để thanh toán!");
            } else if (currentLocalDate.getYear() == paymentLocalDate.getYear()
                    && currentLocalDate.getMonthValue() == paymentLocalDate.getMonthValue()) {
                if (c.getAmount() < payment) {
                    request.setAttribute("messageAmount", "Bạn không đủ tiền trong tài khoản!");
                } else {
                    boolean success = d.UpdateLoanPayment(loanPaymentId, "complete", paymentDate, penaltyfee);
                    d.UpdateCustomerAmount(acc.getAmount() - payment, c.getCustomer_id());
                    d.InsertTransaction(c.getCustomer_id(), 2, payment, "withdrawal");
//                    if(d.getPaymentsByLoanIdandCustomerIdandStatus(loan_id, c.getCustomer_id()).size()==0){
//                        d.UpdateLoanStatus(loan_id, c.getCustomer_id());
//                    }
                    if (success) {
                        request.setAttribute("message", "Thanh toán thành công !");
                        sd.insertNotification(c.getCustomer_id(), loan_id, "Thông báo",
                                "Giao dịch thanh toán đã hoàn tất. Cảm ơn quý khách đã tin tưởng dịch vụ của chúng tôi!");

                    } else {
                        request.setAttribute("message", "Thanh toán thất bại !");
                    }
                }
            } else if (paymentLocalDate.isAfter(currentLocalDate)) {
                lateMonths = (paymentLocalDate.getYear() - currentLocalDate.getYear()) * 12
                        + (paymentLocalDate.getMonthValue() - currentLocalDate.getMonthValue());

                penaltyfee = lateMonths * 0.02 * payment; // 2% mỗi tháng trễ hạn
                // format tien 1.000.000
                DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
                symbols.setGroupingSeparator('.');
                DecimalFormat formatter = new DecimalFormat("#,###", symbols);
                String formattedAmount = formatter.format(penaltyfee);
                String penaltyMessage = "Bạn thanh toán trễ " + lateMonths + " tháng. Phí phạt: " + formattedAmount + " VND";
                request.setAttribute("messagePenalty", penaltyMessage);
                sd.insertNotification(c.getCustomer_id(), loan_id, "Thông báo", penaltyMessage);

                payment += penaltyfee;
                if (c.getAmount() < payment) {
                    request.setAttribute("messageAmount", "Bạn không đủ tiền trong tài khoản!");
                } else {
                    boolean success = d.UpdateLoanPayment(loanPaymentId, "complete", paymentDate, penaltyfee);
                    d.UpdateCustomerAmount(acc.getAmount() - payment, c.getCustomer_id());
                    d.InsertTransaction(c.getCustomer_id(), 2, payment, "withdrawal");
//                    if(d.getPaymentsByLoanIdandCustomerIdandStatus(loan_id, c.getCustomer_id()).size()==0){
//                        d.UpdateLoanStatus(loan_id, c.getCustomer_id());
//                    }
                    if (success) {
                        request.setAttribute("message", "Thanh toán thành công !");
                        sd.insertNotification(c.getCustomer_id(), loan_id, "Thông báo",
                                "Giao dịch thanh toán đã hoàn tất. Cảm ơn quý khách đã tin tưởng dịch vụ của chúng tôi!");
                    } else {
                        request.setAttribute("message", "Thanh toán thất bại !");
                    }
                }
            }

            List<Loan_payments> loanList = d.getPaymentsByLoanIdandCustomerId(loan_id, c.getCustomer_id());
            for (Loan_payments loan_payments : loanList) {
                totalLoanpayments += loan_payments.getPayment_amount();
            }
            Loan l = d.getLoanByLoanId(loan_id);
            request.setAttribute("loan_id", loan_id);
            request.setAttribute("totalLoan", l.getAmount());
            request.setAttribute("totalLoanpayments", totalLoanpayments);
            List<Loan_payments> list = d.getPaymentsByLoanIdandCustomerId(loan_id, c.getCustomer_id());
            request.setAttribute("list", list);
            request.getRequestDispatcher("loanPaymentCustomer.jsp").forward(request, response);
        } catch (Exception e) {
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DAO_Loan d = new DAO_Loan();
        DAO dao=new DAO();
        String id_raw = request.getParameter("id");
        int id;
        double totalLoanpayments = 0;
        HttpSession session = request.getSession();
        Customer c = (Customer) session.getAttribute("account");
        Customer acc = dao.getInforById(c.getCustomer_id());
        Date paymentDate = Date.valueOf(LocalDate.now());
        SavingDAO sd = new SavingDAO();

        try {
            id = Integer.parseInt(id_raw);
            List<Loan_payments> remainingPayments = d.getPaymentsByLoanIdandCustomerIdandStatus(id, c.getCustomer_id());

            double remainingPrincipal = 0;
            double remainingInterest = 0;

            for (Loan_payments lp : remainingPayments) {
                remainingPrincipal += lp.getPrincipal_amount();
                remainingInterest += lp.getInterest_amount();
            }

            double prepaymentFee = remainingPrincipal * 0.05; // 5% phí tất toán trước hạn
            double totalPrepayment = remainingPrincipal + remainingInterest + prepaymentFee;

            if (c.getAmount() < totalPrepayment) {
                request.setAttribute("message", "Bạn không đủ tiền để tất toán khoản vay!");
            } else if (totalPrepayment == 0) {
                request.setAttribute("message", "Khoản vay đã hoàn thành!");
            } else {
                d.UpdateLoanPaymentAll(id, paymentDate, prepaymentFee / remainingPayments.size());
                d.UpdateCustomerAmount(acc.getAmount() - totalPrepayment, c.getCustomer_id());
                d.InsertTransaction(c.getCustomer_id(), 2, totalPrepayment, "withdrawal");
                //format tiền
                DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
                symbols.setGroupingSeparator('.');
                DecimalFormat formatter = new DecimalFormat("#,###", symbols);
                String formattedAmount = formatter.format(totalPrepayment);
                //thong bao
                String message = "Tất toán thành công! Tổng tiền: " + formattedAmount + " VND";
                request.setAttribute("message", message);
                sd.insertNotification(c.getCustomer_id(), id, "Thông báo", message);

            }
            List<Loan_payments> loanList = d.getPaymentsByLoanIdandCustomerId(id, c.getCustomer_id());
            for (Loan_payments loan_payments : loanList) {
                totalLoanpayments += loan_payments.getPayment_amount();
            }
            Loan l = d.getLoanByLoanId(id);
            request.setAttribute("loan_id", id);
            request.setAttribute("totalLoan", l.getAmount());
            request.setAttribute("totalLoanpayments", totalLoanpayments);
            List<Loan_payments> list = d.getPaymentsByLoanIdandCustomerId(id, c.getCustomer_id());
            request.setAttribute("list", list);
            request.getRequestDispatcher("loanPaymentCustomer.jsp").forward(request, response);
        } catch (Exception e) {
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
