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
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
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
import model.Insurance;
import utils.Password;

/**
 *
 * @author DELL
 */
@WebServlet(name = "addInsuranceServlet", urlPatterns = {"/addInsurance"})
public class addInsuranceServlet extends HttpServlet {

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
            out.println("<title>Servlet addInsuranceServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet addInsuranceServlet at " + request.getContextPath() + "</h1>");
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
        String insurance_name = request.getParameter("insurance_name");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String phone_number = request.getParameter("phone_number");
        String address = request.getParameter("address");
        String status = request.getParameter("status");
        DAO_Admin dao = new DAO_Admin();
        try {
            Insurance Username = dao.getInsuranceByUserName(username);
            Insurance Email = dao.getInsuranceByEmail(email);
            Insurance Phone = dao.getInsuranceByPhone(phone_number);
            Insurance Name = dao.getInsuranceByName(insurance_name);
            if (Username != null) {
                request.setAttribute("error", "Username " + username + " existed!!");
                request.getRequestDispatcher("addInsurance.jsp").forward(request, response);
            } else if (Email != null) {
                request.setAttribute("error", "Emai " + email + " existed!!");
                request.getRequestDispatcher("addInsurance.jsp").forward(request, response);
            } else if (Phone != null) {
                request.setAttribute("error", "Phone number " + phone_number + " existed!!");
                request.getRequestDispatcher("addInsurance.jsp").forward(request, response);
            } else if (Name != null) {
                request.setAttribute("error", "Insurance name " + insurance_name + " existed!!");
                request.getRequestDispatcher("addInsurance.jsp").forward(request, response);
            } else {

                String randonPassword = generateRandomPassword(10);
                String hashedPassword = Password.toSHA1(randonPassword);
                Insurance i = new Insurance(5, username, hashedPassword,
                        insurance_name, email, phone_number, address, status);
                dao.insertInsurance(i);
                sendEMailThread(email, randonPassword, username);
                response.sendRedirect("insurance_management");
            }
        } catch (Exception e) {
        }
    }

    private String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }

    public void sendEmail(String recipientEmail, String password, String username) throws MessagingException, UnsupportedEncodingException {
        String host = "smtp.gmail.com";
        String senderEmail = "ducthinh20032003@gmail.com";
        String senderPassword = "fjuk kgua lvis rzkq";

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
        message.setSubject("Your Account");
        message.setSubject(MimeUtility.encodeText("Tài khoản của bạn", "UTF-8", "B"));
        message.setContent("<p>Chào đối tác bảo hiểm,</p>"
                + "<p>Tên đăng nhập của bạn là: <b>" + username + "</b></p>"
                + "<p>Mật khẩu của bạn là: <b>" + password + "</b></p>"
                + "<p>Vui lòng đổi mật khẩu sau khi đăng nhập.</p>"
                + "<p>Nhấn vào <a href='http://localhost:9999/test/login'>đây</a> để đăng nhập.</p>"
                + "<hr>"
                + "<p>Trân trọng,</p>"
                + "<p>Đội ngũ hỗ trợ YourBank</p>"
                + "<p>Email: support@yourbank.com | Điện thoại: +123 456 789</p>",
                "text/html; charset=UTF-8");

        Transport.send(message);
    }

    private void sendEMailThread(String recipientEmail, String password, String username) {
        Thread emailThread = new Thread(() -> {  // thread gửi mail khác luồng
            try {
                System.out.println("Sending email to: " + recipientEmail);
                sendEmail(recipientEmail, password, username);

            } catch (Exception e) {
                e.printStackTrace();  // Log lỗi nếu có
            }
        });
        emailThread.start();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
