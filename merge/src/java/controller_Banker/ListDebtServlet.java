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
        // Get search value from request (if any)
        String searchQuery = request.getParameter("search");
        if (searchQuery == null) {
            searchQuery = "";
        } else {
            searchQuery = searchQuery.trim().replaceAll("\\s+", " ");
        }

        // Get card type from request (if any)
        String cardType = request.getParameter("card_type");
        if (cardType == null) {
            cardType = "";
        }

        // Get page number from request, default is 1
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
                         "INNER JOIN customer c ON d.customer_id = c.customer_id " +
                         "WHERE 1=1 ";

        boolean hasSearch = !searchQuery.isEmpty();
        boolean hasCardTypeFilter = !cardType.isEmpty();

        // Add search condition if applicable
        if (hasSearch) {
            baseSql += "AND (c.full_name LIKE ? OR CAST(d.debt_id AS VARCHAR(20)) = ?) ";
        }
        
        // Add card type filter if applicable
        if (hasCardTypeFilter) {
            baseSql += "AND LOWER(c.card_type) = LOWER(?) ";
        }

        // Add ORDER BY and pagination (OFFSET-FETCH for SQL Server)
        String sql = baseSql + "ORDER BY d.debt_id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        String countSql = "SELECT COUNT(*) AS total FROM (" + baseSql + ") AS filtered_results";

        try (Connection conn = db.getConnection()) {
            // Get total record count
            try (PreparedStatement countStmt = conn.prepareStatement(countSql)) {
                int idx = 1;
                if (hasSearch) {
                    String pattern = "%" + searchQuery + "%";
                    countStmt.setString(idx++, pattern);
                    countStmt.setString(idx++, searchQuery);
                }
                if (hasCardTypeFilter) {
                    countStmt.setString(idx++, cardType.toLowerCase());
                }
                try (ResultSet rsCount = countStmt.executeQuery()) {
                    if (rsCount.next()) {
                        totalRecords = rsCount.getInt("total");
                    }
                }
            }

            // Get debt list with pagination
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                int idx = 1;
                if (hasSearch) {
                    String pattern = "%" + searchQuery + "%";
                    pstmt.setString(idx++, pattern);
                    pstmt.setString(idx++, searchQuery);
                }
                if (hasCardTypeFilter) {
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
        } catch (Exception e) {
            e.printStackTrace();
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
}
