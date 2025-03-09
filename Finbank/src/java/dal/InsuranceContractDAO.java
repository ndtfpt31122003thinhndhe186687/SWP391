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

    public List<Insurance_contract> getAllInsuranceContracts() throws Exception {
        List<Insurance_contract> contracts = new ArrayList<>();
        String sql = "SELECT ic.*, c.full_name AS customer_name, s.service_name, ip.policy_name " +
                     "FROM insurance_contract ic " +
                     "INNER JOIN customer c ON ic.customer_id = c.customer_id " +
                     "INNER JOIN services s ON ic.service_id = s.service_id " +
                     "INNER JOIN insurance_policy ip ON ic.policy_id = ip.policy_id";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Insurance_contract contract = extractContractFromResultSet(rs);
                contracts.add(contract);
            }
        } catch (SQLException ex) {
            Logger.getLogger(InsuranceContractDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return contracts;
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

        return contract;
    }
}