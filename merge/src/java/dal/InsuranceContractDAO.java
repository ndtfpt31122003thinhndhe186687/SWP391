package dal;

import model.Insurance_contract;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InsuranceContractDAO extends DAO {

    /**
     * Lấy danh sách hợp đồng bảo hiểm theo filter search và phân trang.
     */
    public List<Insurance_contract> getAllInsuranceContracts(String search, int offset, int pageSize) throws Exception {
        List<Insurance_contract> contracts = new ArrayList<>();
        // Base query với JOIN để lấy tên khách hàng, tên dịch vụ và tên policy.
        String sql = "SELECT ic.*, c.full_name AS customer_name, s.service_name, ip.policy_name " +
                     "FROM insurance_contract ic " +
                     "INNER JOIN customer c ON ic.customer_id = c.customer_id " +
                     "INNER JOIN services s ON ic.service_id = s.service_id " +
                     "INNER JOIN insurance_policy ip ON ic.policy_id = ip.policy_id ";
        
        // Nếu có tham số search, thêm điều kiện WHERE
        if(search != null && !search.isEmpty()){
            sql += "WHERE c.full_name LIKE ? OR s.service_name LIKE ? OR ip.policy_name LIKE ? ";
        }
        
        sql += "ORDER BY ic.contract_id " +
               "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            int paramIndex = 1;
            if(search != null && !search.isEmpty()){
                String pattern = "%" + search + "%";
                stmt.setString(paramIndex++, pattern);
                stmt.setString(paramIndex++, pattern);
                stmt.setString(paramIndex++, pattern);
            }
            stmt.setInt(paramIndex++, offset);
            stmt.setInt(paramIndex++, pageSize);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while(rs.next()){
                    Insurance_contract contract = extractContractFromResultSet(rs);
                    contracts.add(contract);
                }
            }
        } catch(SQLException ex) {
            Logger.getLogger(InsuranceContractDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return contracts;
    }
    
    /**
     * Đếm tổng số hợp đồng bảo hiểm theo filter search.
     */
    public int getTotalInsuranceContractsCount(String search) throws Exception {
        int total = 0;
        String sql = "SELECT COUNT(*) AS total " +
                     "FROM insurance_contract ic " +
                     "INNER JOIN customer c ON ic.customer_id = c.customer_id " +
                     "INNER JOIN services s ON ic.service_id = s.service_id " +
                     "INNER JOIN insurance_policy ip ON ic.policy_id = ip.policy_id ";
        if(search != null && !search.isEmpty()){
            sql += "WHERE c.full_name LIKE ? OR s.service_name LIKE ? OR ip.policy_name LIKE ? ";
        }
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            if(search != null && !search.isEmpty()){
                String pattern = "%" + search + "%";
                stmt.setString(1, pattern);
                stmt.setString(2, pattern);
                stmt.setString(3, pattern);
            }
            try (ResultSet rs = stmt.executeQuery()) {
                if(rs.next()){
                    total = rs.getInt("total");
                }
            }
        } catch(SQLException ex) {
            Logger.getLogger(InsuranceContractDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }
    
    private Insurance_contract extractContractFromResultSet(ResultSet rs) throws SQLException {
    Insurance_contract contract = new Insurance_contract();
    contract.setContract_id(rs.getInt("contract_id"));
    contract.setCustomer_id(rs.getInt("customer_id"));
    contract.setService_id(rs.getInt("service_id"));
    contract.setPolicy_id(rs.getInt("policy_id"));
    contract.setStart_date(rs.getDate("start_date"));
    contract.setEnd_date(rs.getDate("end_date"));
    contract.setPayment_frequency(rs.getString("payment_frequency"));
    contract.setStatus(rs.getString("status"));
    contract.setCreated_at(rs.getTimestamp("created_at"));
    // Set thêm các trường bổ sung
    contract.setFull_name(rs.getString("full_name"));
    contract.setService_name(rs.getString("service_name"));
    contract.setPolicy_name(rs.getString("policy_name"));
    return contract;
}

}
