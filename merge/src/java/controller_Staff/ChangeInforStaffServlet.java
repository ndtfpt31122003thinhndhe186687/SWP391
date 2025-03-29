/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_Staff;

import static controlller.ChangeInforServlet.sendOtp;
import dal.DAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
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
import model.Notifications;
import model.Staff;

/**
 *
 * @author Acer Nitro Tiger
 */
@WebServlet(name = "ChangeInforStaffServlet", urlPatterns = {"/changeInforStaff"})
public class ChangeInforStaffServlet extends HttpServlet {

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
            out.println("<title>Servlet ChangeInforStaffServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChangeInforStaffServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        Staff s = (Staff) session.getAttribute("account");
        if (s == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        request.setAttribute("staff", s);
        request.getRequestDispatcher("changeInforStaff.jsp").forward(request, response);
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
        Staff c = (Staff) session.getAttribute("account");
        String em = c.getEmail();
        String fullname = request.getParameter("profile-name");
        String email = request.getParameter("profile-email");
        String phone = request.getParameter("profile-phone");
        String address = request.getParameter("profile-address");
        String dob_raw = request.getParameter("dob");

        if (fullname == null || fullname.trim().isEmpty()) {
            fullname = c.getFull_name();
        }
        if (email == null || email.trim().isEmpty()) {
            email = c.getEmail();
        }
        // Kiểm tra số điện thoại hợp lệ (10 chữ số và bắt đầu bằng 0)
        if (phone == null || !phone.matches("0\\d{9}")) {
            request.setAttribute("errorMessage", "Số điện thoại không hợp lệ! Phải có 10 chữ số và bắt đầu bằng số 0.");
            request.getRequestDispatcher("changeInforStaff.jsp").forward(request, response);
            return; // Dừng xử lý nếu không hợp lệ
        }
        if (address == null || address.trim().isEmpty()) {
            address = c.getAddress();
        }

        Date dob = null;
        java.sql.Date sqlDob = null;
        if (dob_raw != null && !dob_raw.trim().isEmpty()) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                dateFormat.setLenient(false);
                dob = dateFormat.parse(dob_raw);
                Date currentDate = new Date();
                if (dob.after(currentDate)) {
                    request.setAttribute("errorMessage", "Ngày sinh không được lớn hơn ngày hiện tại.");
                    session.setAttribute("account", c);
                    request.getRequestDispatcher("changeInforStaff.jsp").forward(request, response);
                    return;
                }

                sqlDob = new java.sql.Date(dob.getTime());
            } catch (ParseException e) {
                request.setAttribute("errorMessage", "Sai định dạng ngày. Hãy nhập theo dd/MM/yyyy!");
                session.setAttribute("account", c);
                request.getRequestDispatcher("changeInforStaff.jsp").forward(request, response);
                return;
            }
        } else {
            if (c.getDate_of_birth() != null) {
                sqlDob = new java.sql.Date(c.getDate_of_birth().getTime());
            }
        }

        session.setAttribute("fullname", fullname);
        session.setAttribute("email", email);
        session.setAttribute("phone", phone);
        session.setAttribute("address", address);
        session.setAttribute("dob", sqlDob);

        // Tạo mã OTP ngẫu nhiên
        String otp = String.format("%06d", new Random().nextInt(999999));

        // Lưu OTP vào session
        session.setAttribute("otp", otp);

        // Gửi OTP qua email
        try {
            sendOtp(em, otp);
            response.sendRedirect("verifyOtpStaff");
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Không thể gửi OTP. Vui lòng thử lại!");
            request.getRequestDispatcher("changeInforStaff.jsp").forward(request, response);
        }
    }

    public static void sendOtp(String recipientEmail, String otp) throws MessagingException, UnsupportedEncodingException {
        String senderEmail = "ducthinh20032003@gmail.com";
        String senderPassword = "fjuk kgua lvis rzkq";

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
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
        message.setSubject(MimeUtility.encodeText("Mã OTP Xác Nhận", "UTF-8", "B"));
        String emailContent = "<p>Xin chào,</p>"
                + "<p>Mã OTP của bạn là: <b>" + otp + "</b></p>"
                + "<p>Vui lòng không chia sẻ mã này với bất kỳ ai.</p>"
                + "<p>Trân trọng,</p>"
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
