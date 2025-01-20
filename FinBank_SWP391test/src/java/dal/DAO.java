package dal;

import java.sql.*;
import java.util.Date;
import model.Customer;

public class DAO extends DBContext {

    public static DAO INSTANCE = new DAO();
    private Connection con;
    private String status = "OK";

    public DAO() {
        try {
            con = new DBContext().getConnection();
        } catch (Exception e) {
            status = "Error at connection: " + e.getMessage();
        }
    }

    public static DAO getINSTANCE() {
        return INSTANCE;
    }

    public static void setINSTANCE(DAO INSTANCE) {
        DAO.INSTANCE = INSTANCE;
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

    // Register a new customer
    public void register(Customer c) {
        String sql = "INSERT INTO [dbo].[customer] " +
                "([full_name],[email], [username], [phone_number], [password], [address],[card_type], [gender], [date_of_birth], [profile_picture]) " +
                "VALUES (?,?, ?, ?, ?, ?,?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, c.getFull_name());
            ps.setString(2, c.getUsername());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getPhone_number());
            ps.setString(5, c.getPassword());
            ps.setString(6, c.getAddress());
            ps.setString(7, c.getCard_type());            
            ps.setString(8, c.getGender());
            ps.setDate(9, new java.sql.Date(c.getDate_of_birth().getTime())); // Convert java.util.Date to java.sql.Date
            ps.setString(10, c.getProfile_picture());
            ps.executeUpdate();
        } catch (SQLException e) {
            status = "Error at register: " + e.getMessage();
            e.printStackTrace();
        }
    }

    // Check if a phone number already exists
    public boolean existedPhoneNum(String phoneNum) {
        String sql = "SELECT [user_id] FROM [dbo].[customer] WHERE phone_number = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, phoneNum);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // If a record exists, return true
            }
        } catch (SQLException e) {
            status = "Error at existedPhoneNum: " + e.getMessage();
        }
        return false;
    }

    // Check login credentials
    public Customer check(String email, String password) {
        String sql = "SELECT [user_id], [full_name], [email], [phone_number], [password], [address], " +
                "[created_at], [gender], [profile_picture], [date_of_birth] " +
                "FROM [dbo].[customer] WHERE email = ? AND password = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, email);
            st.setString(2, password);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new Customer(
                            rs.getString("user_id"),
                            rs.getString("full_name"),
                            rs.getString("email"),
                            rs.getString("email"), // Username is treated as email in this case
                            rs.getString("password"),
                            rs.getString("phone_number"),
                            rs.getString("address"),
                            null, // card_type is not fetched from this query
                            0.0,  // amount is not fetched
                            0.0,  // credit_limit is not fetched
                            null, // status is not fetched
                            rs.getString("gender"),
                            rs.getDate("date_of_birth"),
                            rs.getString("created_at"),
                            rs.getString("profile_picture")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error at check: " + e.getMessage());
        }
        return null;
    }

    // Update password
    public void changePassword(int customer_id, String password) {
        String sql = "UPDATE customer SET password = ? WHERE user_id = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, password);
            st.setInt(2, customer_id);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error at changePassword: " + e.getMessage());
        }
    }

    // Update customer information
    public void changeInfo(String full_name, String email, String phone_number, String address, int customer_id) {
        String sql = "UPDATE customer SET full_name = ?, email = ?, phone_number = ?, address = ? WHERE user_id = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, full_name);
            st.setString(2, email);
            st.setString(3, phone_number);
            st.setString(4, address);
            st.setInt(5, customer_id);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error at changeInfo: " + e.getMessage());
        }
    }

    // Login method
    public Customer login(String email, String password) {
        String sql = "SELECT * FROM Users u JOIN Customer c ON u.user_id = c.customer_id " +
                "WHERE u.email = ? AND u.password = ?";
        try (PreparedStatement pre = con.prepareStatement(sql)) {
            pre.setString(1, email);
            pre.setString(2, password);
            try (ResultSet rs = pre.executeQuery()) {
                if (rs.next()) {
                    return new Customer(
                            rs.getString("customer_id"),
                            rs.getString("full_name"),
                            email,
                            email, // Username is treated as email in this case
                            password,
                            rs.getString("phone_number"),
                            rs.getString("address"),
                            rs.getString("card_type"),
                            rs.getDouble("amount"),
                            rs.getDouble("credit_limit"),
                            rs.getString("status"),
                            rs.getString("gender"),
                            rs.getDate("date_of_birth"),
                            rs.getString("created_at"),
                            rs.getString("profile_picture")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error at login: " + e.getMessage());
        }
        return null;
    }

    // Main method for testing
    public static void main(String[] args) {
        DAO dao = new DAO();
        java.sql.Date sqlDate = java.sql.Date.valueOf("2004-03-03");
        Customer customer = new Customer("abcd", "abcd@gmail.com", "abcd", "1234", "0213456789", "hn", "debit", "male", "profile.jpg", sqlDate);
        dao.register(customer);
    }
}