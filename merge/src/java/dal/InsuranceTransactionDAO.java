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
    
    public List<Insurance_transactions> getAllInsuranceTransactions(String search, int offset, int pageSize) throws Exception {
        List<Insurance_transactions> transactions = new ArrayList<>();
        String sql = "SELECT it.*, c.full_name AS customer_name, " +
                     "ic.policy_id, ip.policy_name " +
                     "FROM insurance_transactions it " +
                     "INNER JOIN customer c ON it.customer_id = c.customer_id " +
                     "INNER JOIN insurance_contract ic ON it.contract_id = ic.contract_id " +
                     "INNER JOIN insurance_policy ip ON ic.policy_id = ip.policy_id ";
        
        // Nếu có tham số tìm kiếm, lọc theo Customer Name hoặc Transaction Type
        if (search != null && !search.isEmpty()) {
            sql += "WHERE c.full_name LIKE ? OR it.transaction_type LIKE ? ";
        }
        sql += "ORDER BY it.transaction_date DESC " +
               "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
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
                    Insurance_transactions transaction = new Insurance_transactions();
                    transaction.setTransaction_id(rs.getInt("transaction_id"));
                    transaction.setContract_id(rs.getInt("contract_id"));
                    transaction.setCustomer_id(rs.getInt("customer_id"));
                    transaction.setTransaction_date(rs.getTimestamp("transaction_date"));
                    transaction.setAmount(rs.getDouble("amount"));
                    transaction.setTransaction_type(rs.getString("transaction_type"));
                    transaction.setNotes(rs.getString("notes"));
                    
                    // Các trường hiển thị bổ sung
                    transaction.setCustomerName(rs.getString("customer_name"));
                    // Ví dụ: hiển thị thông tin hợp đồng dưới dạng "PolicyName (ID: policy_id)"
                    int policyId = rs.getInt("policy_id");
                    String policyName = rs.getString("policy_name");
                    transaction.setContractInfo(policyName + " (ID: " + policyId + ")");
                    
                    transactions.add(transaction);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(InsuranceTransactionDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return transactions;
    }
    
    public int getTotalInsuranceTransactionsCount(String search) throws Exception {
        int total = 0;
        String sql = "SELECT COUNT(*) AS total " +
                     "FROM insurance_transactions it " +
                     "INNER JOIN customer c ON it.customer_id = c.customer_id ";
        if (search != null && !search.isEmpty()) {
            sql += "WHERE c.full_name LIKE ? OR it.transaction_type LIKE ? ";
        }
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
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
            Logger.getLogger(InsuranceTransactionDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return total;
    }
}
