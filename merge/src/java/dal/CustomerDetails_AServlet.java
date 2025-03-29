/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package dal;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.Customer;
import java.sql.*;

/**
 *
 * @author default
 */
@WebServlet(name="CustomerDetails_AServlet", urlPatterns={"/CustomerDetails_AServlet"})
public class CustomerDetails_AServlet extends HttpServlet {
   
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
            out.println("<title>Servlet CustomerDetails_AServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CustomerDetails_AServlet at " + request.getContextPath () + "</h1>");
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
      

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerIdParam = request.getParameter("id");
        int customerId = Integer.parseInt(customerIdParam);

        DBContext db = new DBContext();
        Customer customer = null;

        try {
            Connection conn = (Connection) db.getConnection();

            // Lấy thông tin khách hàng và trạng thái nợ
            String sql = "SELECT*FROM customer WHERE customer_id =?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                customer = new Customer();
                customer.setCustomer_id(rs.getInt("customer_id"));
                customer.setFull_name(rs.getString("full_name"));
                customer.setEmail(rs.getString("email"));
                customer.setUsername(rs.getString("username"));
                customer.setPhone_number(rs.getString("phone_number"));
                customer.setAddress(rs.getString("address"));
                customer.setCard_type(rs.getString("card_type"));
                customer.setStatus(rs.getString("status"));
                customer.setGender(rs.getString("gender"));
                customer.setProfile_picture(rs.getString("profile_picture"));
                customer.setAmount(rs.getDouble("amount"));
                customer.setCredit_limit(rs.getDouble("credit_limit"));
                customer.setDate_of_birth(rs.getDate("date_of_birth"));
                customer.setCreated_at(rs.getTimestamp("created_at"));
            }
        } catch (Exception ex) {
        }
        request.setAttribute("customer", customer);
        request.getRequestDispatcher("customerDetails_A.jsp").forward(request, response);
    }
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
