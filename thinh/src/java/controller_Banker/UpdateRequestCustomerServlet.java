/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_Banker;

import dal.DAO_Loan;
import dal.DBContext;
import dal.SavingDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Calendar;
import model.Loan;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.Customer;
import model.Loan_payments;

/**
 *
 * @author AD
 */
@WebServlet(name = "UpdateRequestCustomerServlet", urlPatterns = {"/updaterequestcustomer"})
public class UpdateRequestCustomerServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdateRequestCustomerServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateRequestCustomerServlet at " + request.getContextPath() + "</h1>");
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
        String loanId = request.getParameter("loan_id");
        DBContext db = new DBContext();

        try (Connection conn = db.getConnection()) {
            // Kiểm tra xem yêu cầu có status là pending hay không
            String sql = "SELECT status FROM loan WHERE loan_id = ? AND status = 'pending'";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, loanId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                request.setAttribute("loan_id", loanId);
            } else {
                // Nếu không tìm thấy yêu cầu pending thì chuyển hướng về trang danh sách
                response.sendRedirect("customerrequest");
            }
        } catch (Exception e) {
            e.printStackTrace();
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
    String customerId = request.getParameter("customer_id");
    String loan_id_raw = request.getParameter("loan_id");
    String status = request.getParameter("status");
    int loan_id, customerid;
    double principal, remaining, amount, interest, monthlyinterest;
    int month;
    DAO_Loan d = new DAO_Loan();
    SavingDAO sd = new SavingDAO();
    DBContext db = new DBContext();

    // Cập nhật chỉ khi status là approved hoặc rejected
    String sql = "UPDATE loan SET status = ? WHERE loan_id = ? AND status = 'pending'";
    try (Connection conn = db.getConnection()) {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, status);
        pstmt.setString(2, loan_id_raw);
        pstmt.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }

    if (status.equals("approved")) {
        try {
            loan_id = Integer.parseInt(loan_id_raw);
            customerid = Integer.parseInt(customerId);
            Loan l = d.getLoanByLoanId(loan_id);
            Customer c = d.getCustomerById(customerid);
            
            // Kiểm tra nếu khách hàng là VIP
            double loanRateDiscount = 0.0;
            String vipSql = "SELECT vt.loan_rate FROM vip v " +
                            "JOIN vip_term vt ON v.vipTerm_id = vt.vipTerm_id " +
                            "WHERE v.customer_id = ? AND v.status = 'active'";
            
            try (Connection conn = db.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(vipSql)) {
                pstmt.setInt(1, customerid);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    loanRateDiscount = rs.getDouble("loan_rate"); // Lấy mức giảm lãi suất
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Calendar cal = Calendar.getInstance();
            cal.setTime(new java.util.Date());
            amount = l.getAmount();
            month = l.getDuration();
            
            // Điều chỉnh lãi suất nếu khách hàng là VIP
            interest = l.getInterest_rate() - loanRateDiscount;
            interest = Math.max(interest, 0); // Đảm bảo không âm
            
            monthlyinterest = interest / month / 100;
            principal = amount / month;
            remaining = amount;

            for (int i = 0; i < month; i++) {
                cal.add(Calendar.MONTH, 1);
                Date paymentDate = cal.getTime();
                double interestAmount = remaining * monthlyinterest;
                double paymentAmount = principal + interestAmount;
                remaining -= principal;
                Loan_payments payment = new Loan_payments(loan_id,
                        paymentDate, paymentAmount,
                        principal, interestAmount,
                        Math.max(remaining, 0), "pending");
                d.insertLoanPayment(payment);
            }
            d.UpdateCustomerAmount(amount + c.getAmount(), customerid);
            sendEMailThread(c.getEmail(), amount, c.getFull_name());
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
                symbols.setGroupingSeparator('.');
                DecimalFormat df = new DecimalFormat("###,###", symbols);
                String formattedAmount = df.format(l.getAmount());
                sd.insertNotification(Integer.parseInt(customerId), loan_id, "Vay",
                        "Bạn đã vay thành công số tiền " + formattedAmount + " VNĐ trong thời hạn " + l.getDuration() + " tháng.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    response.sendRedirect("customerrequest");
}

    public void sendEmail(String recipientEmail, String fullname, double amount) throws MessagingException {
        String host = "smtp.gmail.com";
        String senderEmail = "hoangv494@gmail.com";
        String senderPassword = "hxiv uflp mghw lopl";
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedAmount = formatter.format(amount);
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
        message.setSubject("Thông báo !");
        message.setContent("<p>Phản hồi đơn vay !</p>"
                + "<p>Kính chào khách hàng <b>" + fullname + "</b></p>"
                + "<p>Khoản vay số tiền: <b>" + formattedAmount + "đ</b> đã được chấp nhận</p>"
                + "<hr>"
                + "<p>Trân trọng,</p>"
                + "<p>Đội ngũ hỗ trợ FinBank</p>"
                + "<p>Email: ducthinh20032003@gmail.com | Điện thoại: 0868169332</p>",
                "text/html; charset=UTF-8");

        Transport.send(message);
    }

    private void sendEMailThread(String recipientEmail, double amount, String fullname) {
        Thread emailThread = new Thread(() -> {  // thread gửi mail khác luồng
            try {
                System.out.println("Sending email to: " + recipientEmail);
                sendEmail(recipientEmail, fullname, amount);

            } catch (Exception e) {
                e.printStackTrace();  // Log lỗi nếu có
            }
        });
        emailThread.start();
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
