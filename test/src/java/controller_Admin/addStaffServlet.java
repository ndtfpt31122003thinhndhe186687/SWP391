/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller_Admin;

import dal.DAO;
import dal.DAO_Admin;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import model.Staff;
import utils.Password;

/**
 *
 * @author DELL
 */
@WebServlet(name="addBankerServlet", urlPatterns={"/addStaff"})
public class addStaffServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet addBankerServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet addBankerServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {     
        String full_name = request.getParameter("full_name");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String phone_number = request.getParameter("phone_number");
        String gender = request.getParameter("gender");
        String date_of_birth_raw = request.getParameter("date_of_birth");
        String address = request.getParameter("address");
        String role_id_raw = request.getParameter("role_id");
        String status = request.getParameter("status");
        int role_id;
        try {         
            role_id=Integer.parseInt(role_id_raw);
            var date_of_birth = java.sql.Date.valueOf(date_of_birth_raw);
            DAO_Admin dao = new DAO_Admin();
            Staff Username = dao.get_Staff_By_Username(username);
            Staff Email = dao.get_Staff_By_Email(email);
            Staff Phone = dao.get_Staff_By_Phone(phone_number);
            if(Username!= null){
                request.setAttribute("error","username "+ username+" existed!!");
                request.getRequestDispatcher("addStaff.jsp").forward(request, response);
            }else if (Email != null){
                request.setAttribute("error","emai "+ email+" existed!!");
                request.getRequestDispatcher("addStaff.jsp").forward(request, response);
            }else if (Phone != null){
                request.setAttribute("error","phone number "+ phone_number+" existed!!");
                request.getRequestDispatcher("addStaff.jsp").forward(request, response);
            }
            else{
                String randomPassword = generateRandomPassword(10);
                String hashedPassword = Password.toSHA1(randomPassword);
                Staff s = new Staff(full_name, email, username, hashedPassword,
                        phone_number, gender, date_of_birth, address, role_id, status);
                dao.insertBanker(s);
                sendEmail(email, randomPassword,username);
                response.sendRedirect("staff_management?status=all&sort=full_name&type=&page=1&pageSize=2");
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
    private void sendEmail(String recipientEmail, String password,String username) throws MessagingException {
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
        message.setSubject("Your Account Password");       
        message.setContent("<p>Dear Staff,</p>"
        + "<p>Your username is: <b>" + username + "</b></p>"
        + "<p>Your password is: <b>" + password + "</b></p>"
        + "<p>Please change it after logging in.</p>"
        + "<p>Click <a href='http://localhost:9999/test/login'>here</a> to log in.</p>"
        + "<hr>"
        + "<p>Best regards,</p>"
        + "<p>YourBank Support Team</p>"
        + "<p>Email: support@yourbank.com | Phone: +123 456 789</p>",
        "text/html; charset=UTF-8");

        Transport.send(message);
    }


    /** 
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
