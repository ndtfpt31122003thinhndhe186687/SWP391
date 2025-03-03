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
    
    public List<Transaction> getAllTransactions() throws Exception {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT t.*, c.full_name AS customer_name, s.service_name " +
                    "FROM transactions t " +
                    "INNER JOIN customer c ON t.customer_id = c.customer_id " +
                    "INNER JOIN services s ON t.service_id = s.service_id " +
                    "ORDER BY t.transaction_date DESC";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransaction_id(rs.getInt("transaction_id"));
                transaction.setCustomer_id(rs.getInt("customer_id"));
                transaction.setService_id(rs.getInt("service_id"));
                transaction.setAmount(rs.getDouble("amount"));
                transaction.setTransaction_date(rs.getTimestamp("transaction_date"));
                transaction.setTransaction_type(rs.getString("transaction_type"));
                transactions.add(transaction);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransactionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return transactions;
    }
}