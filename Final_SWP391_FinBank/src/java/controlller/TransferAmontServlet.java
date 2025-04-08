/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controlller;

import dal.DBContext;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import model.Customer;
import java.sql.*;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author AD
 */
@WebServlet(name = "TransferAmontServlet", urlPatterns = {"/transferAmount"})
public class TransferAmontServlet extends HttpServlet {

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
            out.println("<title>Servlet TransferAmontServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TransferAmontServlet at " + request.getContextPath() + "</h1>");
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
          request.getRequestDispatcher("transfer.jsp").forward(request, response);
    }
    private void sendOtpEmail(String toEmail, String otp) throws MessagingException {
        // Set up the mail server        // SMTP server (use SMTP server that you have access to)
        String from = "hoangv494@gmail.com"; // Email gửi
        String password = "hxiv uflp mghw lopl"; // Mật khẩu email

        Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        // Compose the message
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        message.setSubject("Your OTP Code for Transfer");
        message.setText("Your OTP code is: " + otp);

        // Send the email
        Transport.send(message);
    }

    // Method to generate a random OTP
    private String generateOtp() {
        Random rand = new Random();
        int otp = rand.nextInt(999999);
        return String.format("%06d", otp); // Ensure it's 6 digits
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

        String toPhoneNumber = request.getParameter("toPhoneNumber");
        double amount;

        try {
            amount = Double.parseDouble(request.getParameter("amount"));
        } catch (NumberFormatException e) {
            request.setAttribute("mess", "Invalid amount format.");
            request.getRequestDispatcher("transfer.jsp").forward(request, response);
            return;
        }

                    if (amount <= 0) {
                        request.setAttribute("mess", "The amount must be greater than 0.");
            request.getRequestDispatcher("transfer.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("account");

        if (customer == null) {
            response.sendRedirect("login");
            return;
        }

        try (Connection conn = new DBContext().getConnection()) {            

            // Kiểm tra số dư người gửi
            PreparedStatement stmtFrom = conn.prepareStatement("SELECT * FROM customer WHERE customer_id = ?");
            stmtFrom.setInt(1, customer.getCustomer_id());
            ResultSet rsFrom = stmtFrom.executeQuery();

            if (!rsFrom.next()) {
                request.setAttribute("mess", "Không tìm thấy người gửi.");
                request.getRequestDispatcher("transfer.jsp").forward(request, response);
                return;
            }

            double senderAmount = rsFrom.getDouble("amount");

            if (amount > senderAmount) {
                request.setAttribute("mess", "Số dư tài khoản không đủ để thực hiện giao dịch.");
                request.getRequestDispatcher("transfer.jsp").forward(request, response);
                return;
            }

            // Kiểm tra người nhận
            PreparedStatement stmtTo = conn.prepareStatement("SELECT * FROM customer WHERE phone_number = ?");
            stmtTo.setString(1, toPhoneNumber);
            ResultSet rsTo = stmtTo.executeQuery();

            if (!rsTo.next()) {
                request.setAttribute("mess", "Không tìm thấy người nhận.");
                request.getRequestDispatcher("transfer.jsp").forward(request, response);
                return;
            }

            // Gửi OTP
            String otp = generateOtp();
            String senderEmail = rsFrom.getString("email");
            sendOtpEmail(senderEmail, otp);

            // Lưu vào session
            session.setAttribute("otp", otp);
            session.setAttribute("transferAmount", amount);
            session.setAttribute("transferToPhone", toPhoneNumber);

            response.sendRedirect("EnterOtps.jsp");

        } catch (SQLException | MessagingException e) {
            e.printStackTrace();
            request.setAttribute("mess", "Error: " + e.getMessage());
            request.getRequestDispatcher("transfer.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(TransferAmontServlet.class.getName()).log(Level.SEVERE, null, ex);
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
