/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controlller;

import dal.DAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
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
import model.Customer;
import model.NewsCategory;

/**
 *
 * @author Acer Nitro Tiger
 */
@WebServlet(name = "ChangeInfor", urlPatterns = {"/changeInfor"})
@MultipartConfig

public class ChangeInforServlet extends HttpServlet {

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
            out.println("<title>Servlet ChangeInfor</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChangeInfor at " + request.getContextPath() + "</h1>");
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
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Customer c = (Customer) session.getAttribute("account");
        if (c == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        request.setAttribute("customer", c);
        request.getRequestDispatcher("changeInfor.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private DAO dao;

    public ChangeInforServlet() {
        this.dao = new DAO(); // Mặc định khởi tạo
    }

    public void setDAO(DAO dao) { // Thêm setter để inject DAO mock trong unit test
        this.dao = dao;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Customer c = (Customer) session.getAttribute("account");
        String em = c.getEmail();
        String fullname = request.getParameter("profile-name");
        String email = request.getParameter("profile-email");
        String phone = request.getParameter("profile-phone");
        String address = request.getParameter("profile-address");
        String dob_raw = request.getParameter("dob");
        String oldImage = request.getParameter("oldImage");
        DAO d = new DAO();
        
        // Nếu người dùng nhập email mới, kiểm tra trùng lặp
        if (email != null && !email.trim().isEmpty() && !email.equals(c.getEmail()) && d.existedEmail(email)) {
            request.setAttribute("errorMessage", "Email đã tồn tại.");
            request.getRequestDispatcher("changeInfor.jsp").forward(request, response);
            return;
        } else {
            email = (email == null || email.trim().isEmpty()) ? c.getEmail() : email;
        }

        // Nếu người dùng nhập số điện thoại mới, kiểm tra trùng lặp
        if (phone != null && !phone.trim().isEmpty() && !phone.equals(c.getPhone_number()) && d.existedPhoneNum(phone)) {
            request.setAttribute("errorMessage", "Số điện thoại đã tồn tại.");
            request.getRequestDispatcher("changeInfor.jsp").forward(request, response);
            return;
        } else {
            phone = (phone == null || phone.trim().isEmpty()) ? c.getPhone_number() : phone;
        }

        // Kiểm tra định dạng số điện thoại
        if (!isValidPhoneNumber(phone)) {
            request.setAttribute("errorMessage", "Số điện thoại phải bắt đầu bằng 0 và có 10 chữ số.");
            request.getRequestDispatcher("changeInfor.jsp").forward(request, response);
            return;
        }

        // Giữ nguyên thông tin cũ nếu không nhập
        fullname = (fullname == null || fullname.trim().isEmpty()) ? c.getFull_name() : fullname;
        address = (address == null || address.trim().isEmpty()) ? c.getAddress() : address;
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
                    request.getRequestDispatcher("changeInfor.jsp").forward(request, response);
                    return;
                }

                sqlDob = new java.sql.Date(dob.getTime());
            } catch (ParseException e) {
                request.setAttribute("errorMessage", "Sai định dạng ngày. Hãy điền theo dd-MM-yyyy.");
                session.setAttribute("account", c);
                request.getRequestDispatcher("changeInfor.jsp").forward(request, response);
                return;
            }
        } else {
            if (c.getDate_of_birth() != null) {
                sqlDob = new java.sql.Date(c.getDate_of_birth().getTime());
            }
        }

        Part filePart = request.getPart("profile-image");
        String imagePath;
        if (filePart != null && filePart.getSize() > 0) {
            imagePath = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            if (!isValidImageFormat(imagePath)) {
                request.setAttribute("errorMessage", "Chỉ chấp nhận file có định dạng .png hoặc .jpg!");
                session.setAttribute("account", c);
                request.getRequestDispatcher("changeInfor.jsp").forward(request, response);
                return;
            }
            String uploadPath = getServletContext().getRealPath("/imageCustomer") + "/" + imagePath;
            filePart.write(uploadPath);
        } else {
            imagePath = oldImage;
        }
        session.setAttribute("fullname", fullname);
        session.setAttribute("email", email);
        session.setAttribute("phone", phone);
        session.setAttribute("address", address);
        session.setAttribute("dob", sqlDob);
        session.setAttribute("profilePicture", imagePath);

        // Tạo mã OTP ngẫu nhiên
        String otp = String.format("%06d", new Random().nextInt(999999));

        // Lưu OTP vào session
        session.setAttribute("otp", otp);

        // Gửi OTP qua email
        try {
            sendOtp(em, otp);
            response.sendRedirect("verifyOtp");
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Không thể gửi OTP. Vui lòng thử lại!");
            request.getRequestDispatcher("changeInfor.jsp").forward(request, response);
        }
    }

    public boolean isValidPhoneNumber(String phone) {
        return phone != null && phone.matches("^0\\d{9}$");
    }

    public boolean isValidImageFormat(String fileName) {
        return fileName != null && (fileName.endsWith(".jpg") || fileName.endsWith(".png"));
    }

    public static void sendOtp(String recipientEmail, String otp) throws MessagingException {
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
        message.setSubject("Your OTP Verification Code");
        message.setHeader("Content-Type", "text/plain; charset=UTF-8");

        String emailContent = "Dear user,\n\n"
                + "You have requested a one-time password (OTP) for verification.\n"
                + "Your OTP code is: " + otp + "\n\n"
                + "Please enter this code within 10 minutes to complete your verification process.\n"
                + "Do not share this OTP with anyone for security reasons.\n\n"
                + "If you did not request this verification, please ignore this email.\n\n"
                + "Best regards,\n"
                + "From Fin Bank.";

        message.setText(emailContent);

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
