package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Loan;
import model.ServiceTerms;
import model.Services;
import model.Staff;
import model.Term;
import model.Transaction;

public class DAO_Loan extends DBContext {

    public static DAO_Loan INSTANCE = new DAO_Loan();
    private Connection con;
    private String status = "OK";

    public DAO_Loan() {
        try {
            con = new DBContext().getConnection();
        } catch (Exception e) {
            status = "Error at connection: " + e.getMessage();
        }
    }

    public static DAO_Loan getINSTANCE() {
        return INSTANCE;
    }

    public static void setINSTANCE(DAO_Loan INSTANCE) {
        DAO_Loan.INSTANCE = INSTANCE;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Loan request
    public List<ServiceTerms> getLoanServiceTerms() {
        List<ServiceTerms> list = new ArrayList<>();
        String sql = "SELECT st.*, s.service_name, t.duration FROM service_terms st "
                + "LEFT JOIN services s ON st.service_id = s.service_id "
                + "LEFT JOIN term t ON st.term_id = t.term_id "
                + "WHERE st.service_id=2 and st.status='active'";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                ServiceTerms s = new ServiceTerms();
                s.setServiceTerm_id(rs.getInt("serviceTerm_id"));
                s.setTerm_id(rs.getInt("term_id"));
                s.setService_id(rs.getInt("service_id"));
                s.setService_name(rs.getString("service_name"));
                s.setTerm_name(rs.getString("term_name"));
                s.setDescription(rs.getString("description"));
                s.setContract_terms(rs.getString("contract_terms"));
                s.setDuration(rs.getInt("duration"));
                s.setEarly_payment_penalty(rs.getDouble("early_payment_penalty"));
                s.setInterest_rate(rs.getDouble("interest_rate"));
                s.setMin_deposit(rs.getDouble("min_payment"));
                s.setStatus(rs.getString("status"));
                s.setCreated_at(rs.getTimestamp("created_at"));
                list.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }
    
    public void InsertLoanRequest(Loan l) {
        String sql = "insert into loan (customer_id, serviceTerm_id, amount,"
                + " start_date, end_date, loan_type, value_asset,"
                + " notes,[image],[status])"
                + "values (?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, l.getCustomer_id());
            pre.setInt(2, l.getServiceTerm_id());        
            pre.setDouble(3, l.getAmount());
            pre.setDate(4, new java.sql.Date(l.getStart_date().getTime()));
            pre.setDate(5, new java.sql.Date(l.getEnd_date().getTime()));
            pre.setString(6, l.getLoan_type());
            pre.setDouble(7, l.getValue_asset());
            pre.setString(8, l.getNotes());
            pre.setString(9, l.getAsset_image());
            pre.setString(10, l.getStatus());
            pre.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        DAO_Loan d = new DAO_Loan();
        String date = "2000-12-31";
        Date sqlDate = java.sql.Date.valueOf(date);
        
        Loan l = new Loan(1, 2, 10, sqlDate, sqlDate,
                "aaa", "thinh", "secured", 10, "pending");
        d.InsertLoanRequest(l);
        
    }
}
