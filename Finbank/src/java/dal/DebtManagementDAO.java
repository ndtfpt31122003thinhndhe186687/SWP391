/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

/**
 *
 * @author default
 */
public class DebtManagementDAO extends DAO {
    
    public List<Debt_management> getDebtCustomers() throws SQLException {
        List<Debt_management> debtCustomers = new ArrayList<>();
        String sql = "SELECT dm.*, c.full_name, c.email " +
                     "FROM debt_management dm " +
                     "INNER JOIN customer c ON dm.customer_id = c.customer_id ";
                     
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
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
                debtCustomers.add(debt);
            }
        } catch (Exception ex) {
            Logger.getLogger(DebtManagementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return debtCustomers;
    }
    
}
