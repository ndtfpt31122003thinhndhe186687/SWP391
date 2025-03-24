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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import model.VipTerm;
import java.sql.*;

/**
 *
 * @author AD
 */
@WebServlet(name = "MembershipServlet", urlPatterns = {"/membership"})
public class MembershipServlet extends HttpServlet {

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
            out.println("<title>Servlet MembershipServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MembershipServlet at " + request.getContextPath() + "</h1>");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<VipTerm> vipTerms = new ArrayList<>();
        DBContext db = new DBContext();

        try (Connection conn = db.getConnection()) {
            String sql = """
                SELECT vt.vipTerm_id, vt.vip_type, vt.term_name, t.duration, vt.loan_rate, vt.savings_rate, vt.price
                FROM vip_term vt
                JOIN term t ON vt.term_id = t.term_id
                WHERE vt.status = 'active'
            """;

            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    VipTerm term = new VipTerm(
                        rs.getInt("vipTerm_id"),
                        rs.getString("vip_type"),
                        rs.getString("term_name"),
                        rs.getInt("duration"),
                        rs.getDouble("loan_rate"),
                        rs.getDouble("savings_rate"),
                        rs.getDouble("price")
                    );
                    vipTerms.add(term);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("vipTerms", vipTerms);
        RequestDispatcher dispatcher = request.getRequestDispatcher("membership.jsp");
        dispatcher.forward(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // Lấy dữ liệu từ form
    int customerId = Integer.parseInt(request.getParameter("customer_id"));
    String vipType = request.getParameter("vip_type");
    String termName = request.getParameter("term_name");
    int duration = Integer.parseInt(request.getParameter("duration"));
    double price = Double.parseDouble(request.getParameter("price")); // Lấy giá gói VIP từ form
    int serviceId = 3; // Giả sử service_id của dịch vụ VIP là 3

    DBContext db = new DBContext();

    try (Connection conn = db.getConnection()) {
        conn.setAutoCommit(false); // Bắt đầu transaction

        // 1️⃣ Lấy số dư hiện tại của người dùng
        double currentAmount = 0;
        String getAmountSQL = "SELECT amount FROM customer WHERE customer_id = ?";
        try (PreparedStatement amountStmt = conn.prepareStatement(getAmountSQL)) {
            amountStmt.setInt(1, customerId);
            try (ResultSet rs = amountStmt.executeQuery()) {
                if (rs.next()) {
                    currentAmount = rs.getDouble("amount");
                } else {
                    throw new SQLException("Không tìm thấy tài khoản khách hàng.");
                }
            }
        }

        // 2️⃣ Kiểm tra nếu số dư đủ để đăng ký VIP
        if (currentAmount < price) {
            request.setAttribute("error", "Số dư tài khoản không đủ để đăng ký VIP.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("membership.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // 3️⃣ Trừ giá VIP khỏi số dư tài khoản
        double newAmount = currentAmount - price;
        String updateAmountSQL = "UPDATE customer SET amount = ? WHERE customer_id = ?";
        try (PreparedStatement updateStmt = conn.prepareStatement(updateAmountSQL)) {
            updateStmt.setDouble(1, newAmount);
            updateStmt.setInt(2, customerId);
            updateStmt.executeUpdate();
        }

        // 4️⃣ Tạo giao dịch (transaction) trong bảng `transactions`
        String insertTransactionSQL = """
            INSERT INTO transactions (customer_id, service_id, amount, transaction_type) 
            VALUES (?, ?, ?, 'withdrawal')
        """;
        try (PreparedStatement transactionStmt = conn.prepareStatement(insertTransactionSQL)) {
            transactionStmt.setInt(1, customerId);
            transactionStmt.setInt(2, serviceId);
            transactionStmt.setDouble(3, price);
            transactionStmt.executeUpdate();
        }

        // 5️⃣ Lấy vipTerm_id từ vip_term dựa trên vip_type và term_name
        int vipTermId = 0;
        String getVipTermIdSQL = """
            SELECT vipTerm_id FROM vip_term 
            WHERE vip_type = ? AND term_name = ? AND status = 'active'
        """;
        try (PreparedStatement vipTermStmt = conn.prepareStatement(getVipTermIdSQL)) {
            vipTermStmt.setString(1, vipType);
            vipTermStmt.setString(2, termName);
            try (ResultSet rs = vipTermStmt.executeQuery()) {
                if (rs.next()) {
                    vipTermId = rs.getInt("vipTerm_id");
                } else {
                    throw new SQLException("Không tìm thấy gói VIP phù hợp.");
                }
            }
        }

        // 6️⃣ Thêm bản ghi vào bảng `vip`
        String insertVipSQL = """
            INSERT INTO vip (customer_id, vipTerm_id, start_date, end_date, status) 
            VALUES (?, ?, GETDATE(), DATEADD(MONTH, ?, GETDATE()), 'active')
        """;
        try (PreparedStatement insertVipStmt = conn.prepareStatement(insertVipSQL)) {
            insertVipStmt.setInt(1, customerId);
            insertVipStmt.setInt(2, vipTermId);
            insertVipStmt.setInt(3, duration);
            insertVipStmt.executeUpdate();
        }

        conn.commit(); // Xác nhận transaction
        response.sendRedirect("success.jsp"); // Chuyển hướng khi thành công
    } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute("error", "Lỗi khi đăng ký VIP: " + e.getMessage());
        RequestDispatcher dispatcher = request.getRequestDispatcher("membership.jsp");
        dispatcher.forward(request, response);
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
