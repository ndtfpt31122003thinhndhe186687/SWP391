package dal;

import model.Insurance_transactions;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InsuranceTransactionDAO extends DAO {
    
    public List<Insurance_transactions> getAllInsuranceTransactions() throws Exception {
        List<Insurance_transactions> transactions = new ArrayList<>();
        String sql = "SELECT it.*, c.full_name AS customer_name, " +
                     "ic.policy_id, ip.policy_name " +
                     "FROM insurance_transactions it " +
                     "INNER JOIN customer c ON it.customer_id = c.customer_id " +
                     "INNER JOIN insurance_contract ic ON it.contract_id = ic.contract_id " +
                     "INNER JOIN insurance_policy ip ON ic.policy_id = ip.policy_id " +
                     "ORDER BY it.transaction_date DESC";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Insurance_transactions transaction = new Insurance_transactions();
                transaction.setTransaction_id(rs.getInt("transaction_id"));
                transaction.setContract_id(rs.getInt("contract_id"));
                transaction.setCustomer_id(rs.getInt("customer_id"));
                transaction.setTransaction_date(rs.getTimestamp("transaction_date"));
                transaction.setAmount(rs.getDouble("amount"));
                transaction.setTransaction_type(rs.getString("transaction_type"));
                transaction.setNotes(rs.getString("notes"));
                
                // Set additional display fields
                transaction.setCustomerName(rs.getString("customer_name"));
                transaction.setContractInfo(rs.getString("policy_name") + " (ID: " + rs.getInt("policy_id") + ")");
                
                transactions.add(transaction);
            }
        } catch (SQLException ex) {
            Logger.getLogger(InsuranceTransactionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return transactions;
    }
}