/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controlller;

import dal.DAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Customer;
import utils.Password;
/**
 *
 * @author default
 */
@WebServlet(name="ChangePassServlet", urlPatterns={"/changepass"})
public class ChangePassServlet extends HttpServlet {
   
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
            out.println("<title>Servlet ChangePassServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChangePassServlet at " + request.getContextPath () + "</h1>");
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
           request.getRequestDispatcher("changepass.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    String oldPass = request.getParameter("opass");
    String newPass = request.getParameter("newpass");
    String confirmPass = request.getParameter("confirmpass");

    

    // Kiểm tra các trường có trống không
    if (oldPass == null || oldPass.isEmpty() || newPass == null || newPass.isEmpty()) {
        request.setAttribute("error", "Please fill all fields!");
        request.getRequestDispatcher("changepass.jsp").forward(request, response);
        return;
    }

    // Kiểm tra độ dài và định dạng mật khẩu mới
    String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z]).{6,}$";
    if (!newPass.matches(passwordPattern)) {
        request.setAttribute("error", "New password must be at least 6 characters long, include at least 1 lowercase letter and 1 uppercase letter.");
        request.getRequestDispatcher("changepass.jsp").forward(request, response);
        return;
    }else{
    oldPass = Password.toSHA1(oldPass);
    newPass = Password.toSHA1(newPass);
    confirmPass=Password.toSHA1(confirmPass);
    }

    // Kiểm tra mật khẩu xác nhận có khớp không
    if (!newPass.equals(confirmPass)) {
        request.setAttribute("error", "Passwords do not match!");
        request.getRequestDispatcher("changepass.jsp").forward(request, response);
        return;
    }

    HttpSession session = request.getSession();
    Customer c = (Customer) session.getAttribute("account");
    DAO dao = new DAO();

    // Kiểm tra mật khẩu cũ
    if (dao.login(c.getUsername(), oldPass) == null) {
        request.setAttribute("error", "Incorrect old password!");
        request.getRequestDispatcher("changepass.jsp").forward(request, response);
        return;
    } else {
        // Thay đổi mật khẩu
        c.setPassword(newPass);
        dao.changePassword(c.getCustomer_id(), newPass);
        request.setAttribute("ms1", "Successfully changed password!");
        response.sendRedirect("login");
    }
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
