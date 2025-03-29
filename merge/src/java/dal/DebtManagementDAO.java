package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Debt_management;

public class DebtManagementDAO extends DAO {

    // Lấy danh sách Debt Customers với filter (theo debt_id hoặc customer_name) và phân trang
    public List<Debt_management> getDebtCustomers(String search, int offset, int pageSize) throws SQLException, Exception {
        List<Debt_management> debtCustomers = new ArrayList<>();
        String sql = "SELECT dm.*, c.full_name, c.email, c.card_type " +
                     "FROM debt_management dm " +
                     "INNER JOIN customer c ON dm.customer_id = c.customer_id ";
        if (search != null && !search.isEmpty()) {
            // Lọc theo tên khách hàng hoặc debt_id (chuyển đổi debt_id sang chuỗi để so sánh)
            sql += "WHERE c.full_name LIKE ? OR CAST(dm.debt_id AS VARCHAR(20)) = ? ";
        }
        sql += "ORDER BY dm.debt_id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            int paramIndex = 1;
            if (search != null && !search.isEmpty()) {
                String pattern = "%" + search + "%";
                stmt.setString(paramIndex++, pattern);
                stmt.setString(paramIndex++, search);
            }
            stmt.setInt(paramIndex++, offset);
            stmt.setInt(paramIndex++, pageSize);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Debt_management debt = new Debt_management();
                    debt.setDebt_id(rs.getInt("debt_id"));
                    debt.setCustomer_id(rs.getInt("customer_id"));
                    debt.setLoan_id(rs.getInt("loan_id"));
                    debt.setDebt_status(rs.getString("debt_status"));
                    debt.setOverdue_days(rs.getInt("overdue_days"));
                    debt.setNotes(rs.getString("notes"));
                    debt.setCustomerName(rs.getString("full_name"));
                    debt.setCustomerEmail(rs.getString("email"));
                    // Nếu cần, bạn có thể set thêm card_type nếu model có thuộc tính đó
                    // debt.setCard_type(rs.getString("card_type"));
                    debtCustomers.add(debt);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DebtManagementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return debtCustomers;
    }
    
    // Lấy tổng số bản ghi có filter (để tính số trang)
    public int getDebtCustomersCount(String search) throws SQLException, Exception {
        int total = 0;
        String sql = "SELECT COUNT(*) AS total " +
                     "FROM debt_management dm " +
                     "INNER JOIN customer c ON dm.customer_id = c.customer_id ";
        if (search != null && !search.isEmpty()) {
            sql += "WHERE c.full_name LIKE ? OR CAST(dm.debt_id AS VARCHAR(20)) = ? ";
        }
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (search != null && !search.isEmpty()) {
                String pattern = "%" + search + "%";
                stmt.setString(1, pattern);
                stmt.setString(2, search);
            }
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    total = rs.getInt("total");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DebtManagementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }
}
