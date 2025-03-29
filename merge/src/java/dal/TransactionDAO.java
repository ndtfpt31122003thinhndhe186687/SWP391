package dal;

import model.Transaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransactionDAO extends DAO {

    /**
     * Lấy danh sách giao dịch theo điều kiện search và phân trang.
     */
    public List<Transaction> getAllTransactions(String search, int offset, int pageSize) throws Exception {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT t.*, c.full_name AS customer_name, s.service_name "
                + "FROM transactions t "
                + "INNER JOIN customer c ON t.customer_id = c.customer_id "
                + "INNER JOIN services s ON t.service_id = s.service_id ";
        if (search != null && !search.isEmpty()) {
            sql += "WHERE c.full_name LIKE ? OR t.transaction_type LIKE ? ";
        }
        sql += "ORDER BY t.transaction_date DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            int index = 1;
            if (search != null && !search.isEmpty()) {
                String pattern = "%" + search + "%";
                stmt.setString(index++, pattern);
                stmt.setString(index++, pattern);
            }
            stmt.setInt(index++, offset);
            stmt.setInt(index++, pageSize);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setTransaction_id(rs.getInt("transaction_id"));
                    transaction.setCustomer_id(rs.getInt("customer_id"));
                    transaction.setService_id(rs.getInt("service_id"));
                    transaction.setAmount(rs.getDouble("amount"));
                    transaction.setTransaction_date(rs.getTimestamp("transaction_date"));
                    transaction.setTransaction_type(rs.getString("transaction_type"));
                    // Set thêm các trường bổ sung:
                    transaction.setCustomer_name(rs.getString("customer_name"));
                    transaction.setService_name(rs.getString("service_name"));
                    transactions.add(transaction);
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(TransactionDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return transactions;
    }

    /**
     * Lấy tổng số giao dịch theo điều kiện search.
     */
    public int getTotalTransactionsCount(String search) throws Exception {
        int total = 0;
        String sql = "SELECT COUNT(*) AS total "
                + "FROM transactions t "
                + "INNER JOIN customer c ON t.customer_id = c.customer_id ";
        if (search != null && !search.isEmpty()) {
            sql += "WHERE c.full_name LIKE ? OR t.transaction_type LIKE ? ";
        }

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (search != null && !search.isEmpty()) {
                String pattern = "%" + search + "%";
                stmt.setString(1, pattern);
                stmt.setString(2, pattern);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    total = rs.getInt("total");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransactionDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return total;
    }
}
