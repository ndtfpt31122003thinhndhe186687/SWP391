package controller_Banker;

import dal.DBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Customer;

@WebServlet(name = "CustomerListServlet", urlPatterns = {"/customerList"})
public class CustomerListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    DBContext db = new DBContext();
    List<Customer> customers = new ArrayList<>();
    String search = request.getParameter("search");
    
    if (search == null) {
        search = "";
    } else {
        // Trim whitespace and replace multiple spaces with a single space
        search = search.trim().replaceAll("\\s+", " ");
    }

    String cardType = request.getParameter("cardType");
    if (cardType == null) {
        cardType = "";
    }

    // Pagination: default current page = 1, pageSize = 10
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

    int totalRecords = 0;
    String sql, countSql;

    try (Connection conn = db.getConnection()) {
        if (!search.isEmpty() || !cardType.isEmpty()) {
            sql = "SELECT customer_id, full_name, card_type FROM customer WHERE (full_name LIKE ? OR ? = '') AND (card_type = ? OR ? = '') ORDER BY customer_id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
            countSql = "SELECT COUNT(*) AS total FROM customer WHERE (full_name LIKE ? OR ? = '') AND (card_type = ? OR ? = '')";
        } else {
            sql = "SELECT customer_id, full_name, card_type FROM customer ORDER BY customer_id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
            countSql = "SELECT COUNT(*) AS total FROM customer";
        }

        // Get total record count
        try (PreparedStatement countStmt = conn.prepareStatement(countSql)) {
            if (!search.isEmpty() || !cardType.isEmpty()) {
                countStmt.setString(1, "%" + search + "%");
                countStmt.setString(2, search);
                countStmt.setString(3, cardType);
                countStmt.setString(4, cardType);
            }
            try (ResultSet rsCount = countStmt.executeQuery()) {
                if (rsCount.next()) {
                    totalRecords = rsCount.getInt("total");
                }
            }
        }

        // Get customer list with pagination
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            int idx = 1;
            if (!search.isEmpty() || !cardType.isEmpty()) {
                pstmt.setString(idx++, "%" + search + "%");
                pstmt.setString(idx++, search);
                pstmt.setString(idx++, cardType);
                pstmt.setString(idx++, cardType);
            }
            pstmt.setInt(idx++, offset);
            pstmt.setInt(idx++, pageSize);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Customer customer = new Customer();
                    customer.setCustomer_id(rs.getInt("customer_id"));
                    customer.setFull_name(rs.getString("full_name"));
                    customer.setCard_type(rs.getString("card_type"));
                    customers.add(customer);
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    int totalPages = (int) Math.ceil(totalRecords / (double) pageSize);

    request.setAttribute("customerList", customers);
    request.setAttribute("search", search);
    request.setAttribute("currentPage", page);
    request.setAttribute("totalPages", totalPages);
    request.setAttribute("cardType", cardType);
    request.getRequestDispatcher("customerList.jsp").forward(request, response);
}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
