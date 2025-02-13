package dal;

import model.Feedback;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FeedbackDAO extends DAO {

    /**
     * Lấy danh sách Feedback theo filter (customer name và service name) và phân trang.
     */
    public List<Feedback> getAllFeedback(String searchCustomer, String searchService, int offset, int pageSize) throws Exception {
        List<Feedback> feedbackList = new ArrayList<>();
        String sql = "SELECT f.*, c.full_name AS customer_name, s.service_name " +
                     "FROM feedback f " +
                     "INNER JOIN customer c ON f.customer_id = c.customer_id " +
                     "LEFT JOIN services s ON f.service_id = s.service_id ";
        
        boolean hasCustomerFilter = (searchCustomer != null && !searchCustomer.trim().isEmpty());
        boolean hasServiceFilter = (searchService != null && !searchService.trim().isEmpty());
        
        if (hasCustomerFilter || hasServiceFilter) {
            sql += "WHERE ";
            boolean addAnd = false;
            if (hasCustomerFilter) {
                sql += "c.full_name LIKE ? ";
                addAnd = true;
            }
            if (hasServiceFilter) {
                if (addAnd) {
                    sql += "AND ";
                }
                sql += "s.service_name LIKE ? ";
            }
        }
        
        sql += "ORDER BY f.feedback_date DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            int idx = 1;
            if (hasCustomerFilter) {
                stmt.setString(idx++, "%" + searchCustomer + "%");
            }
            if (hasServiceFilter) {
                stmt.setString(idx++, "%" + searchService + "%");
            }
            stmt.setInt(idx++, offset);
            stmt.setInt(idx++, pageSize);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Feedback fb = new Feedback();
                    fb.setFeedback_id(rs.getString("feedback_id"));
                    fb.setCustomer_id(rs.getString("customer_id"));
                    fb.setService_id(rs.getString("service_id"));
                    fb.setFeedback_content(rs.getString("feedback_content"));
                    fb.setFeedback_date(rs.getTimestamp("feedback_date"));
                    // Các trường mở rộng
                    fb.setCustomer_name(rs.getString("customer_name"));
                    fb.setService_name(rs.getString("service_name"));
                    feedbackList.add(fb);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return feedbackList;
    }
    
    public int getTotalFeedbackCount(String searchCustomer, String searchService) throws Exception {
        int total = 0;
        String sql = "SELECT COUNT(*) AS total " +
                     "FROM feedback f " +
                     "INNER JOIN customer c ON f.customer_id = c.customer_id " +
                     "LEFT JOIN services s ON f.service_id = s.service_id ";
        
        boolean hasCustomerFilter = (searchCustomer != null && !searchCustomer.trim().isEmpty());
        boolean hasServiceFilter = (searchService != null && !searchService.trim().isEmpty());
        
        if (hasCustomerFilter || hasServiceFilter) {
            sql += "WHERE ";
            boolean addAnd = false;
            if (hasCustomerFilter) {
                sql += "c.full_name LIKE ? ";
                addAnd = true;
            }
            if (hasServiceFilter) {
                if (addAnd) {
                    sql += "AND ";
                }
                sql += "s.service_name LIKE ? ";
            }
        }
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            int idx = 1;
            if (hasCustomerFilter) {
                stmt.setString(idx++, "%" + searchCustomer + "%");
            }
            if (hasServiceFilter) {
                stmt.setString(idx++, "%" + searchService + "%");
            }
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    total = rs.getInt("total");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return total;
    }
    
    /**
     * Lấy tên dịch vụ nhận được nhiều phản hồi nhất (dựa trên toàn bộ dữ liệu).
     */
    public String getTopServiceFeedback() throws Exception {
        String topService = null;
        String sql = "SELECT TOP 1 s.service_name, COUNT(*) AS feedbackCount " +
                     "FROM feedback f " +
                     "INNER JOIN services s ON f.service_id = s.service_id " +
                     "GROUP BY s.service_name " +
                     "ORDER BY feedbackCount DESC";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                topService = rs.getString("service_name");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return topService;
    }
}
