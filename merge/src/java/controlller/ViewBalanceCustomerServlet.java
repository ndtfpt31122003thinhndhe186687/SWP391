/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controlller;

import dal.DAO;
import dal.DBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Customer;
import java.sql.*;
import java.util.List;
import model.Notifications;
import model.Transaction;

/**
 *
 * @author Acer Nitro Tiger
 */
@WebServlet(name = "ViewBalanceCustomerServlet", urlPatterns = {"/balanceCustomer"})
public class ViewBalanceCustomerServlet extends HttpServlet {

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
            out.println("<title>Servlet ViewBalanceCustomerServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewBalanceCustomerServlet at " + request.getContextPath() + "</h1>");
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
        DAO d = new DAO();
        HttpSession session = request.getSession();
        Customer c = (Customer) session.getAttribute("account");
        if (c == null) {
            response.sendRedirect("login.jsp");
            return;
        } else {
            Customer customer = d.getInforById(c.getCustomer_id());
            request.setAttribute("customer", customer);
        }

        // Kiểm tra xem khách hàng có VIP không
        String vipStatusMessage = null;
        String sqlVip = """
        SELECT vt.vip_type FROM vip v
        JOIN vip_term vt ON v.vipTerm_id = vt.vipTerm_id
        WHERE v.customer_id = ? AND v.status = 'active'
    """;

        try (Connection conn = new DBContext().getConnection(); PreparedStatement pstmt = conn.prepareStatement(sqlVip)) {
            pstmt.setInt(1, c.getCustomer_id());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String vipType = rs.getString("vip_type");
                switch (vipType) {
                    case "silver":
                        vipStatusMessage = "Bạn hiện đang là khách hàng Bạc";
                        break;
                    case "gold":
                        vipStatusMessage = "Bạn hiện đang là khách hàng Vàng";
                        break;
                    case "diamond":
                        vipStatusMessage = "Bạn hiện đang là khách hàng Kim Cương";
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("vipStatusMessage", vipStatusMessage);
        List<Notifications> listNotify = d.getAllNotificationsByCustomerId(c.getCustomer_id());
        request.setAttribute("listNotify", listNotify);
        List<Transaction> listT = d.getAllTransactionsByCustomerId(c.getCustomer_id());
        request.setAttribute("listT", listT);
        request.getRequestDispatcher("balanceCustomer.jsp").forward(request, response);
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
        processRequest(request, response);
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
