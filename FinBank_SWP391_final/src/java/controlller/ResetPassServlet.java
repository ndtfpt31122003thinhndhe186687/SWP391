/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controlller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


/**
 *
 * @author AD
 */
@WebServlet(name = "ResetPassServlet", urlPatterns = {"/resetpass"})
public class ResetPassServlet extends HttpServlet {

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
            out.println("<title>Servlet ResetPassServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ResetPassServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    private String maskPassword(String password) {
        return "*".repeat(password.length());
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
        processRequest(request, response);
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
        String newPassword = request.getParameter("password");
        String confPassword = request.getParameter("confPassword");
        RequestDispatcher dispatcher = null;

        // Kiểm tra các trường không được để trống
        if (newPassword == null || newPassword.isEmpty() || confPassword == null || confPassword.isEmpty()) {
            request.setAttribute("error", "Please fill in all the fields.");
            dispatcher = request.getRequestDispatcher("resetpass.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Kiểm tra mật khẩu có khớp nhau không
        if (!newPassword.equals(confPassword)) {
            request.setAttribute("error", "Passwords do not match.");
            dispatcher = request.getRequestDispatcher("resetpass.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Biểu thức chính quy để kiểm tra mật khẩu ít nhất 6 ký tự, 1 ký tự viết thường, 1 ký tự viết hoa
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z]).{6,}$";
        if (!newPassword.matches(passwordPattern)) {
            request.setAttribute("error", "Password must be at least 6 characters long and contain at least 1 lowercase and 1 uppercase letter.");
            dispatcher = request.getRequestDispatcher("resetpass.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=FinBank_SWP391", "sa", "123");

            // Cập nhật mật khẩu mới vào cơ sở dữ liệu
            PreparedStatement pst = con.prepareStatement("UPDATE customer SET password = ? WHERE email = ?");
            pst.setString(1, newPassword);
            pst.setString(2, (String) session.getAttribute("email"));

            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                request.setAttribute("status", "resetSuccess");
                dispatcher = request.getRequestDispatcher("login.jsp");
            } else {
                request.setAttribute("status", "resetFailed");
                dispatcher = request.getRequestDispatcher("resetpass.jsp");
            }
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Phương thức để lấy mật khẩu từ database và ẩn đi khi hiển thị
    private String getMaskedPassword(String email) {
        String maskedPassword = "";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=FinBank_SWP391", "sa", "123");

            // Lấy mật khẩu từ cơ sở dữ liệu
            PreparedStatement pst = con.prepareStatement("SELECT password FROM customer WHERE email = ?");
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String password = rs.getString("password");
                // Tạo chuỗi `*` dựa trên độ dài mật khẩu
                maskedPassword = "*".repeat(password.length());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maskedPassword;
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
