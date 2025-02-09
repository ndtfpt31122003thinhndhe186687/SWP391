package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Customer;
import model.Services;
import model.Staff;
import model.Term;
import model.Transaction;

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
                int role_id = rs.getInt("role_id");
                Customer acc = new Customer(fullname, email, userName,
                        password, phone_number, address, card_type, status, gender, profile_picture, customer_id, role_id, amount, credit_limit, date_of_birth, created_at);
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

    public List<Staff> getAllBanker(int role_id) {
        List<Staff> list = new ArrayList<>();
        String sql = "select * from staff where role_id=?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, role_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Staff s = new Staff();
                s.setStaff_id(rs.getInt(1));
                s.setFull_name(rs.getString(2));
                s.setEmail(rs.getString(3));
                s.setUsername(rs.getString(4));
                s.setPassword(rs.getString(5));
                s.setPhone_number(rs.getString(6));
                s.setGender(rs.getString(7));
                s.setDate_of_birth(rs.getDate(8));
                s.setAddress(rs.getString(9));
                s.setRole_id(rs.getInt(10));
                s.setStatus(rs.getString(12));
                list.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public void deleteBanker(int id) {
        String sql = "delete from news where staff_id= ?;"
                + " delete from request where staff_id = ? "
                + "delete from staff where staff_id=?;";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, id);
            pre.setInt(2, id);
            pre.setInt(3, id);
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void insertBanker(Staff s){
        String sql = "insert into staff (full_name, email,username,password,phone_number,gender,date_of_birth,address,role_id,status) "
                + "values (?,?,?,?,?,?,?,?,?,?)";
        
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, s.getFull_name());
            pre.setString(2, s.getEmail());
            pre.setString(3, s.getUsername());
            pre.setString(4, s.getPassword());
            pre.setString(5, s.getPhone_number());
            pre.setString(6, s.getGender());
            pre.setDate(7, new java.sql.Date(s.getDate_of_birth().getTime()));
            pre.setString(8, s.getAddress());
            pre.setInt(9, s.getRole_id());
            pre.setString(10, s.getStatus());
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updateBanker(Staff s) {
        String sql = "update staff set full_name = ?,"
                + "email=?,"
                + "username=?,"
                + "password=?,"
                + "phone_number=?,"
                + "gender=?,"
                + "date_of_birth=?,"
                + "address=?,"
                + "role_id=?,"
                + "status=? "
                + "where staff_id=?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, s.getFull_name());
            pre.setString(2, s.getEmail());
            pre.setString(3, s.getUsername());
            pre.setString(4, s.getPassword());
            pre.setString(5, s.getPhone_number());
            pre.setString(6, s.getGender());
            pre.setDate(7, new java.sql.Date(s.getDate_of_birth().getTime()));
            pre.setString(8, s.getAddress());
            pre.setInt(9, s.getRole_id());
            pre.setString(10, s.getStatus());
            pre.setInt(11, s.getStaff_id());
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public Staff get_Staff_By_StaffId(int id){
        String sql="select * from staff where staff_id=?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            if(rs.next()){
                Staff s = new Staff();
                s.setStaff_id(rs.getInt(1));
                s.setFull_name(rs.getString(2));
                s.setEmail(rs.getString(3));
                s.setUsername(rs.getString(4));
                s.setPassword(rs.getString(5));
                s.setPhone_number(rs.getString(6));
                s.setGender(rs.getString(7));
                s.setDate_of_birth(rs.getDate(8));
                s.setAddress(rs.getString(9));
                s.setRole_id(rs.getInt(10));
                s.setStatus(rs.getString(12));
                return s;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    
    public Staff get_Staff_By_Username(String username){
        String sql="select * from staff where username=?";
        try{
            PreparedStatement pre=con.prepareStatement(sql);
            pre.setString(1, username);
            ResultSet rs = pre.executeQuery();
            if(rs.next()){
                Staff s = new Staff();
                s.setStaff_id(rs.getInt(1));
                s.setFull_name(rs.getString(2));
                s.setEmail(rs.getString(3));
                s.setUsername(rs.getString(4));
                s.setPassword(rs.getString(5));
                s.setPhone_number(rs.getString(6));
                s.setGender(rs.getString(7));
                s.setDate_of_birth(rs.getDate(8));
                s.setAddress(rs.getString(9));
                s.setRole_id(rs.getInt(10));
                s.setStatus(rs.getString(12));
                return s;
            }
        }catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    
    public List<Services> getAllServices(){
        List<Services> list = new ArrayList<>();
        String sql="select * from services ";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while(rs.next()){
                Services s = new Services();
                s.setService_id(rs.getInt(1));
                s.setService_name(rs.getString(2));
                s.setDescription(rs.getString(3));
                s.setService_type(rs.getString(4));
                s.setStatus(rs.getString(5));
                list.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }
    
    public List<Term> getAllTerm(){
        List<Term> list = new ArrayList<>();
        String sql="select * from term";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while(rs.next()){
                Term t = new Term();
                t.setTerm_id(rs.getInt(1));
                t.setTerm_name(rs.getString(2));
                t.setDuration(rs.getInt(3));
                t.setTerm_type(rs.getString(4));
                t.setStatus(rs.getString(5));
                list.add(t);
            }
        } catch (SQLException e) {
        }
        return list;
    }
    
    public List<Transaction> getAllTransaction(){
        List<Transaction> list = new ArrayList<>();
        String sql="select * from transactions";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while(rs.next()){
                Transaction t = new Transaction();
                t.setTransaction_id(rs.getInt(1));
                t.setCustomer_id(rs.getInt(2));
                t.setService_id(rs.getInt(3));
                t.setAmount(rs.getDouble(4));
                t.setTransaction_date(rs.getDate(5));
                t.setTransaction_type(rs.getString(6));
                list.add(t);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }
     
    public void InsertService(Services s){
        String sql="insert into services values(?,?,?,?)";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, s.getService_name());
            pre.setString(2, s.getDescription());
            pre.setString(3, s.getService_type());
            pre.setString(4, s.getStatus());
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void UpdateService(Services s){
        String sql = "update services set service_name=?,"
                + "description=?,"
                + "service_type=?,"
                + "status=? "
                + "where service_id=?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, s.getService_name());
            pre.setString(2, s.getDescription());
            pre.setString(3, s.getService_type());
            pre.setString(4, s.getStatus());
            pre.setInt(5, s.getService_id());
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public Services get_Service_BY_Service_id(int service_id){
        String sql = "select * from services where service_id=?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, service_id);
            ResultSet rs = pre.executeQuery();
            if(rs.next()){
                Services s = new Services();
                s.setService_id(rs.getInt(1));
                s.setService_name(rs.getString(2));
                s.setDescription(rs.getString(3));
                s.setService_type(rs.getString(4));
                s.setStatus(rs.getString(5));
                return s ;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    
    public Services get_Service_BY_Service_name(String service_name){
        String sql = "select * from services where service_name=?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, service_name);
            ResultSet rs = pre.executeQuery();
            if(rs.next()){
                Services s = new Services();
                s.setService_id(rs.getInt(1));
                s.setService_name(rs.getString(2));
                s.setDescription(rs.getString(3));
                s.setService_type(rs.getString(4));
                s.setStatus(rs.getString(5));
                return s ;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    
    public void InsertTerm (Term t) {
        String sql="insert into term values(?,?,?,?)";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, t.getTerm_name());
            pre.setInt(2, t.getDuration());
            pre.setString(3, t.getTerm_type());
            pre.setString(4, t.getStatus());
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void UpdateTerm (Term t){
        String sql ="update term set term_name=?, "
                + "duration=?, "
                + "term_type=?, "
                + "status=? "
                + "where term_id=?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, t.getTerm_name());
            pre.setInt(2, t.getDuration());
            pre.setString(3, t.getTerm_type());
            pre.setString(4, t.getStatus());
            pre.setInt(5, t.getTerm_id());
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public Term get_Term_BY_Term_id(int term_id){
        String sql="select * from term where term_id=?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, term_id);
            ResultSet rs = pre.executeQuery();
            if(rs.next()){
                Term t = new Term();
                t.setTerm_id(rs.getInt(1));
                t.setTerm_name(rs.getString(2));
                t.setDuration(rs.getInt(3));
                t.setTerm_type(rs.getString(4));
                t.setStatus(rs.getString(5));
                return t;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    
    public Term get_Term_BY_Term_name(String term_name){
        String sql="select * from term where term_name=?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, term_name);
            ResultSet rs = pre.executeQuery();
            if(rs.next()){
                Term t = new Term();
                t.setTerm_id(rs.getInt(1));
                t.setTerm_name(rs.getString(2));
                t.setDuration(rs.getInt(3));
                t.setTerm_type(rs.getString(4));
                t.setStatus(rs.getString(5));
                return t;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    
    
     
        
    //admin
    
    
    
    // Main method for testing
    public static void main(String[] args) {
        DAO d = new DAO();
//        String date ="2000-12-31";
//        Date sqlDate = java.sql.Date.valueOf(date);
        Services s =d.get_Service_BY_Service_id(1);
        System.out.println(s);
        Term t =d.get_Term_BY_Term_id(1);
        System.out.println(t);
        
        
        

    }
}
