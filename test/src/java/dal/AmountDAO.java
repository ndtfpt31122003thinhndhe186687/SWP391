package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.AmountDTO;

public class AmountDAO {
    private DBContext db;
    
    public AmountDAO() {
        db = new DBContext();
    }
    
    // Lấy danh sách giao dịch từ UNION ALL (giữ nguyên như cũ)
    public List<AmountDTO> getAllAmounts(String search, int offset, int pageSize) throws Exception {
        List<AmountDTO> amounts = new ArrayList<>();
        String query = """
            SELECT * FROM (
                SELECT 
                    c.amount,
                    c.full_name as customer_name,
                    c.created_at as transaction_date,
                    'So Du Tai Khoan' as source
                FROM customer c
                WHERE c.amount > 0
                
                UNION ALL
                
                SELECT 
                    t.amount,
                    c.full_name as customer_name,
                    t.transaction_date,
                    'Giao Dich Thong Thuong' as source
                FROM transactions t
                JOIN customer c ON t.customer_id = c.customer_id
                
                UNION ALL
                
                SELECT 
                    it.amount,
                    c.full_name as customer_name,
                    it.transaction_date,
                    'Giao Dich Bao Hiem' as source
                FROM insurance_transactions it
                JOIN customer c ON it.customer_id = c.customer_id
                
                UNION ALL
                
                SELECT 
                    l.amount,
                    c.full_name as customer_name,
                    l.start_date as transaction_date,
                    'Khoan Vay' as source
                FROM loan l
                JOIN customer c ON l.customer_id = c.customer_id
                
                UNION ALL
                
                SELECT 
                    ld.amount,
                    c.full_name as customer_name,
                    ld.disbursement_date as transaction_date,
                    'Giai Ngan Khoan Vay' as source
                FROM loan_disbursements ld
                JOIN loan l ON ld.loan_id = l.loan_id
                JOIN customer c ON l.customer_id = c.customer_id
            ) AS combined_amounts
            WHERE (customer_name LIKE ? OR source LIKE ?)
            ORDER BY transaction_date DESC
            OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
            """;
        
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            String searchPattern = "%" + search + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setInt(3, offset);
            ps.setInt(4, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AmountDTO amount = new AmountDTO();
                    amount.setAmount(rs.getBigDecimal("amount"));
                    amount.setSource(rs.getString("source"));
                    amount.setTransactionDate(rs.getTimestamp("transaction_date"));
                    amount.setCustomerName(rs.getString("customer_name"));
                    amounts.add(amount);
                }
            }
        }
        return amounts;
    }
    
    // Tổng số bản ghi (cho phân trang)
    public int getTotalAmountsCount(String search) throws Exception {
        int total = 0;
        String countQuery = """
            SELECT COUNT(*) AS total FROM (
                SELECT 
                    c.amount,
                    c.full_name as customer_name,
                    c.created_at as transaction_date,
                    'So Du Tai Khoan' as source
                FROM customer c
                WHERE c.amount > 0
                
                UNION ALL
                
                SELECT 
                    t.amount,
                    c.full_name as customer_name,
                    t.transaction_date,
                    'Giao Dich Thong Thuong ' as source
                FROM transactions t
                JOIN customer c ON t.customer_id = c.customer_id
                
                UNION ALL
                
                SELECT 
                    it.amount,
                    c.full_name as customer_name,
                    it.transaction_date,
                    'Giao Dich Thong Thuong' as source
                FROM insurance_transactions it
                JOIN customer c ON it.customer_id = c.customer_id
                
                UNION ALL
                
                SELECT 
                    l.amount,
                    c.full_name as customer_name,
                    l.start_date as transaction_date,
                    'Khoan Vay' as source
                FROM loan l
                JOIN customer c ON l.customer_id = c.customer_id
                
                UNION ALL
                
                SELECT 
                    ld.amount,
                    c.full_name as customer_name,
                    ld.disbursement_date as transaction_date,
                    'Giai Ngan Khoan Vay' as source
                FROM loan_disbursements ld
                JOIN loan l ON ld.loan_id = l.loan_id
                JOIN customer c ON l.customer_id = c.customer_id
            ) AS combined_amounts
            WHERE (customer_name LIKE ? OR source LIKE ?)
            """;
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(countQuery)) {
            String searchPattern = "%" + search + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    total = rs.getInt("total");
                }
            }
        }
        return total;
    }
    
    // Thống kê từ bảng customer
    public int getTotalCustomers(String search) throws Exception {
        int totalCustomers = 0;
        String query = "SELECT COUNT(*) AS total_customers FROM customer WHERE full_name LIKE ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            String searchPattern = "%" + search + "%";
            ps.setString(1, searchPattern);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totalCustomers = rs.getInt("total_customers");
                }
            }
        }
        return totalCustomers;
    }
    
    public int getTotalAmount(String search) throws Exception {
        int totalAmount = 0;
        String query = "SELECT COALESCE(SUM(amount), 0) AS total_amount FROM customer WHERE full_name LIKE ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            String searchPattern = "%" + search + "%";
            ps.setString(1, searchPattern);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totalAmount = rs.getInt("total_amount");
                }
            }
        }
        return totalAmount;
    }
    
    // Thống kê tổng amount từ bảng transactions
    public int getTotalAmountTransactions(String search) throws Exception {
        int totalAmount = 0;
        String query = "SELECT COALESCE(SUM(t.amount), 0) AS total_amount FROM transactions t JOIN customer c ON t.customer_id = c.customer_id WHERE c.full_name LIKE ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            String searchPattern = "%" + search + "%";
            ps.setString(1, searchPattern);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()){
                    totalAmount = rs.getInt("total_amount");
                }
            }
        }
        return totalAmount;
    }
    
    // Thống kê tổng amount từ bảng insurance_transactions
    public int getTotalAmountInsuranceTransactions(String search) throws Exception {
        int totalAmount = 0;
        String query = "SELECT COALESCE(SUM(it.amount), 0) AS total_amount FROM insurance_transactions it JOIN customer c ON it.customer_id = c.customer_id WHERE c.full_name LIKE ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            String searchPattern = "%" + search + "%";
            ps.setString(1, searchPattern);
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    totalAmount = rs.getInt("total_amount");
                }
            }
        }
        return totalAmount;
    }
    
    // Thống kê tổng amount từ bảng loan
    public int getTotalAmountLoans(String search) throws Exception {
        int totalAmount = 0;
        String query = "SELECT COALESCE(SUM(l.amount), 0) AS total_amount FROM loan l JOIN customer c ON l.customer_id = c.customer_id WHERE c.full_name LIKE ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            String searchPattern = "%" + search + "%";
            ps.setString(1, searchPattern);
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    totalAmount = rs.getInt("total_amount");
                }
            }
        }
        return totalAmount;
    }
    
    // Thống kê tổng amount từ bảng loan_disbursements
    public int getTotalAmountLoanDisbursements(String search) throws Exception {
        int totalAmount = 0;
        String query = "SELECT COALESCE(SUM(ld.amount), 0) AS total_amount FROM loan_disbursements ld JOIN loan l ON ld.loan_id = l.loan_id JOIN customer c ON l.customer_id = c.customer_id WHERE c.full_name LIKE ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            String searchPattern = "%" + search + "%";
            ps.setString(1, searchPattern);
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    totalAmount = rs.getInt("total_amount");
                }
            }
        }
        return totalAmount;
    }
}
