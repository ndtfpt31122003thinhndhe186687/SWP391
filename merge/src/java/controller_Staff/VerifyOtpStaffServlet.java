/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_Staff;

import dal.DAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import model.Customer;
import model.Staff;

/**
 *
 * @author Acer Nitro Tiger
 */
@WebServlet(name = "VerifyOtpStaffServlet", urlPatterns = {"/verifyOtpStaff"})
public class VerifyOtpStaffServlet extends HttpServlet {

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
            out.println("<title>Servlet VerifyOtpStaffServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VerifyOtpStaffServlet at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("otp-verification.jsp").forward(request, response);
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
        HttpSession session = request.getSession();
        // Lấy OTP từ session và từ người dùng nhập vào
        String userOtp = request.getParameter("otp");
        String sessionOtp = (String) session.getAttribute("otp");
        Staff c = (Staff) session.getAttribute("account");

        if (sessionOtp != null && sessionOtp.equals(userOtp)) {
            // OTP đúng -> Cập nhật thông tin trong database
            String fullname = (String) session.getAttribute("fullname");
            String email = (String) session.getAttribute("email");
            String phone = (String) session.getAttribute("phone");
            String address = (String) session.getAttribute("address");
            java.sql.Date dob = (java.sql.Date) session.getAttribute("dob");

            DAO dao = new DAO();
            dao.changeInforStaff(fullname, email, phone, address, dob, c.getStaff_id());

            // Cập nhật thông tin trong session
            c.setFull_name(fullname);
            c.setEmail(email);
            c.setPhone_number(phone);
            c.setAddress(address);
            c.setDate_of_birth(dob);
            session.setAttribute("account", c);

            //gui thong bao den email moi
            try {
                sendConfirmationEmail(email);
            } catch (Exception e) {
                request.setAttribute("errorMessage", "Đã cập nhật thông tin nhưng không thể gửi email xác nhận.");
                request.getRequestDispatcher("viewInforStaff.jsp").forward(request, response);
                return;
            }
            // Xóa OTP khỏi session
            session.removeAttribute("otp");

            session.setAttribute("successMessage", "Cập nhật thông tin thành công!");
            response.sendRedirect("viewInforStaff");
        } else {
            // Nếu OTP sai, hiển thị lỗi
            request.setAttribute("errorMessage", "Mã OTP không đúng, vui lòng nhập lại.");
            request.getRequestDispatcher("otp-verification.jsp").forward(request, response);
        }
    }

    private void sendConfirmationEmail(String recipientEmail) throws MessagingException, UnsupportedEncodingException {
        String senderEmail = "ducthinh20032003@gmail.com";
        String senderPassword = "fjuk kgua lvis rzkq"; // Cần thay bằng App Password

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderEmail, "Hỗ trợ YourBank", "UTF-8"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));

        message.setSubject(MimeUtility.encodeText("Xác nhận cập nhật thông tin", "UTF-8", "B"));

        String emailContent = "<p><strong>Chúc mừng!</strong></p>"
                + "<p>Bạn đã cập nhật thông tin tài khoản thành công.</p>"
                + "<p>Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi.</p>"
                + "<hr>"
                + "<p><strong>Trân trọng,</strong></p>"
                + "<p>Đội ngũ hỗ trợ YourBank</p>"
                + "<p>Email: support@yourbank.com | Điện thoại: +123 456 789</p>";

        message.setContent(emailContent, "text/html; charset=UTF-8");

        Transport.send(message);
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
