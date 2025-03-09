package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Customer;
import model.News;
import model.NewsView;
import model.Staff;

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
        String sql = "INSERT INTO [dbo].[customer] "
                + "([full_name],[username],[email] , [phone_number], [password], [address],[card_type], [gender], [date_of_birth],[role_id], [profile_picture]) "
                + "VALUES (?,?, ?, ?, ?, ?,?, ?, ?,?, ?)";
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
            ps.setInt(10, c.getRole_id());
            ps.setString(11, c.getProfile_picture());
            ps.executeUpdate();
        } catch (SQLException e) {
            status = "Error at register: " + e.getMessage();
            e.printStackTrace();
        }
    }

    // Check if a phone number already exists
    public boolean existedPhoneNum(String phoneNum) {
        String sql = "SELECT [customer_id] FROM [dbo].[customer] WHERE phone_number = ?";
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

    // Check if a email already exists
    public boolean existedEmail(String email) {
        String sql = "SELECT [customer_id] FROM [dbo].[customer] WHERE email = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            status = "Error at existedEmail: " + e.getMessage();
        }
        return false;
    }

    // Update password
    public void changePassword(int customer_id, String password) {
        String sql = "UPDATE customer SET password = ? WHERE customer_id = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, password);
            st.setInt(2, customer_id);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error at changePassword: " + e.getMessage());
        }
    }

    // Update customer information
    public void changeInfor(String full_name, String email, String phone_number, String address, java.sql.Date dob, String profilePicture, int customer_id) {
        String sql = "update customer set full_name=?,email=?,phone_number=?,address=?,date_of_birth=?,profile_picture=? where customer_id=?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, full_name);
            st.setString(2, email);
            st.setString(3, phone_number);
            st.setString(4, address);
            st.setDate(5, dob);
            st.setString(6, profilePicture);
            st.setInt(7, customer_id);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // Login method
    public Customer login(String username, String pass) {
        String sql = "select * from customer\n"
                + "where username = ?\n"
                + "and password = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, username);
            pre.setString(2, pass);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int customer_id = rs.getInt("customer_id");
                String fullname = rs.getString("full_name");
                String email = rs.getString("email");
                String userName = rs.getString("username");
                String password = rs.getString("password");
                String phone_number = rs.getString("phone_number");
                String address = rs.getString("address");
                String card_type = rs.getString("card_type");
                String status = rs.getString("status");
                String gender = rs.getString("gender");
                Date created_at = rs.getDate("created_at");
                String profile_picture = rs.getString("profile_picture");
                double amount = rs.getDouble("amount");
                double credit_limit = rs.getDouble("credit_limit");
                Date date_of_birth = rs.getDate("date_of_birth");
                Customer acc = new Customer(fullname, email, userName, password, phone_number, address, card_type, status, gender, profile_picture, customer_id, amount, credit_limit, date_of_birth, created_at);
                return acc;
            }
        } catch (Exception e) {
        }
        return null;
    }

    // admin 
    public Staff login_admin(String username, String password) {
        String sql = "select * from staff where username=? and password=?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, username);
            pre.setString(2, password);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                int staff_id = rs.getInt(1);
                String full_name = rs.getString(2);
                String email = rs.getString(3);
                String phone_number = rs.getString(6);
                String gender = rs.getString(7);
                Date date_of_birth = rs.getDate(8);
                String address = rs.getString(9);
                int role_id = rs.getInt(10);
                String status = rs.getString(12);
                Staff staff = new Staff(staff_id, full_name, email, password,
                        username, phone_number, gender, date_of_birth, address, role_id, status);
                return staff;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public Customer getInforById(int id) {
        String sql = "SELECT * FROM customer WHERE customer_id = ?";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomer_id(rs.getInt("customer_id"));
                customer.setFull_name(rs.getString("full_name"));
                customer.setEmail(rs.getString("email"));
                customer.setUsername(rs.getString("username"));
                customer.setPhone_number(rs.getString("phone_number"));
                customer.setAddress(rs.getString("address"));
                customer.setCard_type(rs.getString("card_type"));
                customer.setStatus(rs.getString("status"));
                customer.setGender(rs.getString("gender"));
                customer.setProfile_picture(rs.getString("profile_picture"));
                customer.setAmount(rs.getDouble("amount"));
                customer.setCredit_limit(rs.getDouble("credit_limit"));
                customer.setDate_of_birth(rs.getDate("date_of_birth"));
                customer.setCreated_at(rs.getTimestamp("created_at"));
                customer.setRole_id(rs.getInt("role_id"));
                return customer;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    // Main method for testing
    public static void main(String[] args) {

    }

}
