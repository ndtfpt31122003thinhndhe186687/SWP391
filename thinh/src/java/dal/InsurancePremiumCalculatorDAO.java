package dal;

import model.Insurance_policy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InsurancePremiumCalculatorDAO extends DAO {

    // Lấy thông tin InsurancePolicy theo policy_id
    public Insurance_policy getPolicyById(int policyId) throws SQLException, Exception {
        Insurance_policy policy = null;
        String sql = "SELECT * FROM insurance_policy WHERE policy_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, policyId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    policy = new Insurance_policy();
                    policy.setPolicy_id(rs.getInt("policy_id"));
                    policy.setInsurance_id(rs.getInt("insurance_id"));
                    policy.setPolicy_name(rs.getString("policy_name"));
                    policy.setDescription(rs.getString("description"));
                    policy.setCoverage_amount(rs.getDouble("coverage_amount"));
                    policy.setPremium_amount(rs.getDouble("premium_amount"));
                    policy.setStatus(rs.getString("status"));
                    policy.setCreated_at(rs.getTimestamp("created_at"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(InsurancePremiumCalculatorDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return policy;
    }
    
    // Tính phí bảo hiểm cho 1 năm dựa trên loanAmount và premium_amount (là phần trăm)
    public double calculatePremium(double loanAmount, Insurance_policy policy) {
        // Công thức: premium = loanAmount × (premium_amount/100)
        return loanAmount * (policy.getPremium_amount() / 100);
    }
}
