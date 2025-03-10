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
        // Get search parameter from request (if any)
        String searchQuery = request.getParameter("search");
        if (searchQuery == null) {
            searchQuery = "";
        } else {
            // Trim whitespace and replace multiple spaces with a single space
            searchQuery = searchQuery.trim().replaceAll("\\s+", " ");
        }
        
        // Get card type from request (default to empty string if not present)
        String cardType = request.getParameter("card_type");
        if (cardType == null) {
            cardType = "";
        }
        
        // Get page number from request, default is 1 if not present
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
        
        // Construct base SQL query
        String baseSql = "SELECT d.debt_id, c.full_name, c.card_type " +
                         "FROM debt_management d " +
                         "INNER JOIN customer c ON d.customer_id = c.customer_id ";
        
        // Build WHERE clause
        StringBuilder whereClause = new StringBuilder();
        boolean hasSearch = !searchQuery.isEmpty();
        boolean hasCardFilter = !cardType.isEmpty();
        
        if (hasSearch) {
            whereClause.append("WHERE (c.full_name LIKE ? OR CAST(d.debt_id AS VARCHAR(20)) = ?)");
        }
        if (hasCardFilter) {
            if (whereClause.length() == 0) {
                whereClause.append("WHERE LOWER(c.card_type) = LOWER(?)");
            } else {
                whereClause.append(" AND LOWER(c.card_type) = LOWER(?)");
            }
        }
        
        String orderClause = " ORDER BY d.debt_id ";
        String paginationClause = " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        String sql = baseSql + whereClause + orderClause + paginationClause;
        
        // Query to count total records with filters
        String countSql = "SELECT COUNT(*) AS total FROM (" + baseSql + whereClause + ") AS filtered_results";
        
        try (Connection conn = db.getConnection()) {
            // Count total records
            try (PreparedStatement countStmt = conn.prepareStatement(countSql)) {
                int idx = 1;
                if (hasSearch) {
                    String pattern = "%" + searchQuery + "%";
                    countStmt.setString(idx++, pattern);
                    countStmt.setString(idx++, searchQuery);
                }
                if (hasCardFilter) {
                    countStmt.setString(idx++, cardType.toLowerCase());
                }
                try (ResultSet rsCount = countStmt.executeQuery()) {
                    if (rsCount.next()) {
                        totalRecords = rsCount.getInt("total");
                    }
                }
            }
            
            // Retrieve the list with pagination and filters
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                int idx = 1;
                if (hasSearch) {
                    String pattern = "%" + searchQuery + "%";
                    pstmt.setString(idx++, pattern);
                    pstmt.setString(idx++, searchQuery);
                }
                if (hasCardFilter) {
                    pstmt.setString(idx++, cardType.toLowerCase());
                }
                pstmt.setInt(idx++, offset);
                pstmt.setInt(idx++, pageSize);
                
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        Debt_management debt = new Debt_management();
                        debt.setDebt_id(rs.getInt("debt_id"));
                        debt.setCustomerName(rs.getString("full_name"));
                        debt.setCard_type(rs.getString("card_type"));
                        debts.add(debt);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log SQL exceptions
        } catch (Exception e) {
            e.printStackTrace(); // Log any other exceptions
        }
        
        int totalPages = (int) Math.ceil(totalRecords / (double) pageSize);
        
        request.setAttribute("listdebt", debts);
        request.setAttribute("search", searchQuery);
        request.setAttribute("card_type", cardType);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        
        request.getRequestDispatcher("listdebt.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "ListDebtServlet with pagination, search filter, and card_type filter";
    }
}
