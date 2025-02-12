package dal;

import model.ServiceTerms;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceTermDAO {
    // Cập nhật thông tin kết nối cho phù hợp với môi trường của bạn
    private String jdbcURL = "jdbc:sqlserver://localhost:1433;databaseName=FinBank_SWP391";
    private String jdbcUsername = "sa";
    private String jdbcPassword = "123";

    private static final String SELECT_LOAN_TERMS = "SELECT * FROM service_terms WHERE service_id = 2 AND status = 'active'";
    private static final String SELECT_SAVING_TERMS = "SELECT * FROM service_terms WHERE service_id = 1 AND status = 'active'";
    private static final String SELECT_TERM_BY_ID = "SELECT * FROM service_terms WHERE term_id = ?";

    public ServiceTermDAO() {
        try {
            // Với SQL Server sử dụng driver: com.microsoft.sqlserver.jdbc.SQLServerDriver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }
    
    public List<ServiceTerms> getLoanTerms() {
        List<ServiceTerms> list = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_LOAN_TERMS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ServiceTerms term = extractServiceTermFromResultSet(rs);
                list.add(term);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<ServiceTerms> getSavingTerms() {
        List<ServiceTerms> list = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_SAVING_TERMS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ServiceTerms term = extractServiceTermFromResultSet(rs);
                list.add(term);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public ServiceTerms getTermById(int termId) {
        ServiceTerms term = null;
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_TERM_BY_ID)) {
            ps.setInt(1, termId);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()){
                    term = extractServiceTermFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return term;
    }
    
    private ServiceTerms extractServiceTermFromResultSet(ResultSet rs) throws SQLException {
        ServiceTerms term = new ServiceTerms();
        term.setTerm_id(rs.getInt("term_id"));
        term.setService_id(rs.getInt("service_id"));
        term.setTerm_name(rs.getString("term_name"));
        term.setDescription(rs.getString("description"));
        term.setContract_terms(rs.getString("contract_terms"));
        term.setMax_term_months(rs.getInt("max_term_months"));
        term.setEarly_payment_penalty(rs.getDouble("early_payment_penalty"));
        term.setInterest_rate(rs.getDouble("interest_rate"));
        term.setMin_payment(rs.getDouble("min_payment"));
        term.setMin_deposit(rs.getDouble("min_deposit"));
        term.setStatus(rs.getString("status"));
        term.setCreated_at(rs.getTimestamp("created_at"));
        return term;
    }
}
