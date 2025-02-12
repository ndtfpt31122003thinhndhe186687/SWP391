package controller_Banker;

import dal.DBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Debt_management;

@WebServlet(name = "ListDebtServlet", urlPatterns = {"/listdebt"})
public class ListDebtServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy tham số tìm kiếm từ request (nếu có)
        String searchQuery = request.getParameter("search");
        if (searchQuery == null) {
            searchQuery = "";
        }
        
        // Lấy số trang, mặc định là 1 nếu không có tham số
        int page = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }
        int pageSize = 10;
        int offset = (page - 1) * pageSize;
        
        DBContext db = new DBContext();
        List<Debt_management> debts = new ArrayList<>();
        int totalRecords = 0;
        
        // SQL query có filter theo search nếu được cung cấp
        String baseSql = "SELECT d.debt_id, c.full_name " +
                         "FROM debt_management d " +
                         "INNER JOIN customer c ON d.customer_id = c.customer_id ";
        String whereClause = "";
        if (!searchQuery.isEmpty()) {
            // Sử dụng LIKE cho tên và chuyển debt_id về chuỗi để so sánh
            whereClause = "WHERE c.full_name LIKE ? OR CAST(d.debt_id AS VARCHAR(20)) = ? ";
        }
        String orderClause = "ORDER BY d.debt_id ";
        String paginationClause = "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        String sql = baseSql + whereClause + orderClause + paginationClause;
        
        // Query đếm tổng số bản ghi với filter
        String countSql = baseSql + whereClause;
        
        try (Connection conn = db.getConnection()) {
            // Đếm tổng số bản ghi
            try (PreparedStatement countStmt = conn.prepareStatement(countSql)) {
                if (!searchQuery.isEmpty()) {
                    String pattern = "%" + searchQuery + "%";
                    countStmt.setString(1, pattern);
                    countStmt.setString(2, searchQuery);
                }
                try (ResultSet rsCount = countStmt.executeQuery()) {
                    if (rsCount.next()) {
                        totalRecords = rsCount.getInt("total");
                    }
                }
            } catch (SQLException ex) {
                // Nếu không có filter, chạy truy vấn đếm đơn giản:
                String simpleCountSql = "SELECT COUNT(*) AS total FROM debt_management";
                try (PreparedStatement ps = conn.prepareStatement(simpleCountSql);
                     ResultSet rsSimple = ps.executeQuery()){
                    if(rsSimple.next()){
                        totalRecords = rsSimple.getInt("total");
                    }
                }
            }
            
            // Lấy danh sách với phân trang và filter
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                if (!searchQuery.isEmpty()) {
                    String pattern = "%" + searchQuery + "%";
                    pstmt.setString(1, pattern);
                    pstmt.setString(2, searchQuery);
                    pstmt.setInt(3, offset);
                    pstmt.setInt(4, pageSize);
                } else {
                    pstmt.setInt(1, offset);
                    pstmt.setInt(2, pageSize);
                }
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    Debt_management debt = new Debt_management();
                    debt.setDebt_id(rs.getInt("debt_id"));
                    debt.setCustomerName(rs.getString("full_name"));
                    debts.add(debt);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        int totalPages = (int) Math.ceil(totalRecords / (double) pageSize);
        
        request.setAttribute("listdebt", debts);
        request.setAttribute("search", searchQuery);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        
        request.getRequestDispatcher("listdebt.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Xử lý POST bằng cách gọi doGet
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "ListDebtServlet with pagination and search filter";
    }
}
