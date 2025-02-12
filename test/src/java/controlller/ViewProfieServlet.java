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
import java.util.ArrayList;
import java.util.List;
import model.Customer;
import java.sql.*;
import model.Asset;
import dal.DBContext;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author default
 */
@WebServlet(name="ViewProfieServlet", urlPatterns={"/viewprofie"})
public class ViewProfieServlet extends HttpServlet {
   
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
    HttpSession session = request.getSession();
    Customer c =(Customer) session.getAttribute("account");

    DBContext db = new DBContext();
    Customer customer = null;
    Asset a = new Asset();
    //String debtStatus = null;
    //List<String> description = new ArrayList<>();
    List<String> assetStatuses = new ArrayList<>();
    try {
        Connection conn = db.getConnection();
        
        // Lấy thông tin khách hàng và trạng thái nợ
        String sql = "SELECT c.*, a.description,a.[status] FROM customer c " +
                     "JOIN asset a ON c.customer_id = a.customer_id " +
                     "WHERE c.customer_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, c.getCustomer_id());
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
            //debtStatus = rs.getString("debt_status");
            a.setDescription(rs.getString("description"));
            
        }
        String assetSql = "SELECT [status] FROM asset WHERE customer_id = ?";
        PreparedStatement assetPstmt = conn.prepareStatement(assetSql);
        ResultSet assetRs = assetPstmt.executeQuery();
        while (assetRs.next()) {
            assetStatuses.add(assetRs.getString("status"));
        }

        // Lấy trạng thái tài sản
        //String assetSql = "SELECT description FROM asset WHERE customer_id = ?";
        //PreparedStatement assetPstmt = conn.prepareStatement(assetSql);
        //assetPstmt.setInt(1, customerId);
        //ResultSet assetRs = assetPstmt.executeQuery();
        //while (assetRs.next()) {
          //  description.add(assetRs.getString("description"));
       // }

        

    } catch (Exception e) {
        e.printStackTrace();
    }

    request.setAttribute("customer", customer);
    request.setAttribute("a", a);
    request.setAttribute("assetStatuses", assetStatuses);
    //request.setAttribute("description", description);
    request.getRequestDispatcher("viewprofile.jsp").forward(request, response);
}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
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