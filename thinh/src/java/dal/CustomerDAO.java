package dal;

import model.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO extends DBContext {

    // Lấy thông tin customer theo customer_id
    public Customer getCustomerById(int customerId) throws Exception {
        Customer customer = null;
        String sql = "SELECT * FROM customer WHERE customer_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    customer = new Customer();
                    customer.setCustomer_id(rs.getInt("customer_id"));
                    customer.setFull_name(rs.getString("full_name"));
                    customer.setEmail(rs.getString("email"));
                    customer.setUsername(rs.getString("username"));
                    customer.setPassword(rs.getString("password"));
                    customer.setPhone_number(rs.getString("phone_number"));
                    customer.setAddress(rs.getString("address"));
                    customer.setCard_type(rs.getString("card_type"));
                    customer.setAmount(rs.getDouble("amount"));
                    customer.setCredit_limit(rs.getDouble("credit_limit"));
                    customer.setStatus(rs.getString("status"));
                    customer.setGender(rs.getString("gender"));
                    customer.setDate_of_birth(rs.getDate("date_of_birth"));
                    customer.setRole_id(rs.getInt("role_id"));
                    customer.setCreated_at(rs.getTimestamp("created_at"));
                    customer.setProfile_picture(rs.getString("profile_picture"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return customer;
    }

    // Deposit cho tài khoản debit (update trường amount)
    public boolean depositDebit(int customerId, double amount) throws SQLException, Exception {
        String sql = "UPDATE customer SET amount = amount + ? WHERE customer_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, amount);
            ps.setInt(2, customerId);
            return ps.executeUpdate() > 0;
        }
    }

    // Deposit cho tài khoản credit (update trường credit_limit)
    public boolean depositCredit(int customerId, double amount) throws SQLException, Exception {
        String sql = "UPDATE customer SET credit_limit = credit_limit + ? WHERE customer_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, amount);
            ps.setInt(2, customerId);
            return ps.executeUpdate() > 0;
        }
    }

    // Rút tiền cho tài khoản debit (update trường amount)
    public boolean withdrawDebit(int customerId, double amount) throws SQLException, Exception {
        double balance = getBalanceDebit(customerId);
        if (balance < amount) {
            return false;
        }
        String sql = "UPDATE customer SET amount = amount - ? WHERE customer_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, amount);
            ps.setInt(2, customerId);
            return ps.executeUpdate() > 0;
        }
    }

    // Rút tiền cho tài khoản credit (update trường credit_limit)
    public boolean withdrawCredit(int customerId, double amount) throws SQLException, Exception {
        double balance = getBalanceCredit(customerId);
        if (balance < amount) {
            return false;
        }
        String sql = "UPDATE customer SET credit_limit = credit_limit - ? WHERE customer_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, amount);
            ps.setInt(2, customerId);
            return ps.executeUpdate() > 0;
        }
    }

    // Lấy số dư cho tài khoản debit
    public double getBalanceDebit(int customerId) throws SQLException, Exception {
        String sql = "SELECT amount FROM customer WHERE customer_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("amount");
                }
            }
        }
        return 0;
    }

    // Lấy số dư cho tài khoản credit
    public double getBalanceCredit(int customerId) throws SQLException, Exception {
        String sql = "SELECT credit_limit FROM customer WHERE customer_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("credit_limit");
                }
            }
        }
        return 0;
    }

    // Áp dụng lãi suất hàng tháng cho tài khoản:
    // Nếu card_type là debit, áp dụng lãi trên amount; nếu credit, áp dụng lãi trên credit_limit.
    public boolean applyMonthlyInterest(int customerId, String cardType) throws SQLException, Exception {
        double balance = 0;
        if ("credit".equalsIgnoreCase(cardType)) {
            balance = getBalanceCredit(customerId);
        } else {
            balance = getBalanceDebit(customerId);
        }
        // Giả sử lãi suất hàng tháng là 0.1%
        double interest = balance * 0.0001;
        String sql;
        if ("credit".equalsIgnoreCase(cardType)) {
            sql = "UPDATE customer SET credit_limit = credit_limit + ? WHERE customer_id = ?";
        } else {
            sql = "UPDATE customer SET amount = amount + ? WHERE customer_id = ?";
        }
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, interest);
            ps.setInt(2, customerId);
            return ps.executeUpdate() > 0;
        }
    }

    // Lưu giao dịch vào bảng transactions
    private void logTransaction(int customerId, double amount, String transactionType) throws SQLException, Exception {
        String sql = "INSERT INTO transactions (customer_id, amount, transaction_date, transaction_type, service_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ps.setDouble(2, amount);
            ps.setObject(3, java.time.LocalDateTime.now());
            ps.setString(4, transactionType);
            ps.setInt(5, 1); // Giả sử service_id là 1
            ps.executeUpdate();
        }
    }

    // Kiểm tra và thực hiện nạp tiền
    public boolean deposit(int customerId, double amount, String cardType) throws SQLException, Exception {
        String sql;
        if ("credit".equalsIgnoreCase(cardType)) {
            sql = "UPDATE customer SET credit_limit = credit_limit + ? WHERE customer_id = ?";
        } else {
            sql = "UPDATE customer SET amount = amount + ? WHERE customer_id = ?";
        }

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, amount);
            ps.setInt(2, customerId);
            boolean success = ps.executeUpdate() > 0;
            if (success) {
                logTransaction(customerId, amount, "deposit");
            }
            return success;
        }
    }

    // Kiểm tra và thực hiện rút tiền
    public boolean withdraw(int customerId, double amount, String cardType) throws SQLException, Exception {
        double balance = ("credit".equalsIgnoreCase(cardType)) ? getBalanceCredit(customerId) : getBalanceDebit(customerId);

        if (balance < amount) {
            return false; // Kiểm tra số dư
        }
        String sql = "credit".equalsIgnoreCase(cardType)
                ? "UPDATE customer SET credit_limit = credit_limit - ? WHERE customer_id = ?"
                : "UPDATE customer SET amount = amount - ? WHERE customer_id = ?";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, amount);
            ps.setInt(2, customerId);
            boolean success = ps.executeUpdate() > 0;
            if (success) {
                logTransaction(customerId, amount, "withdrawal");
            }
            return success;
        }
    }

    // Lấy lịch sử giao dịch của khách hàng
    public List<String> getTransactionHistory(int customerId) throws SQLException, Exception {
        List<String> transactions = new ArrayList<>();
        String sql = "SELECT transaction_date, transaction_type, amount FROM transactions WHERE customer_id = ? ORDER BY transaction_date DESC";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String record = rs.getTimestamp("transaction_date") + " - "
                            + rs.getString("transaction_type") + " - $"
                            + rs.getDouble("amount");
                    transactions.add(record);
                }
            }
        }
        return transactions;
    }

    public boolean registerCreditCard(int customerId, double salary, int dueDate) throws SQLException, Exception {
        double creditLimit = salary * 2; // Hạn mức = 2 lần lương
        String sql = "UPDATE customer SET credit_limit = ?, credit_due_date = ?, salary = ? WHERE customer_id = ?";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, creditLimit);
            ps.setInt(2, dueDate);
            ps.setDouble(3, salary);
            ps.setInt(4, customerId);
            return ps.executeUpdate() > 0;
        }
    }

}
