package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Customer;
import model.News;
import model.ServiceTerms;
import model.Services;
import model.Staff;
import model.Term;
import model.Transaction;

public class DAO_Admin extends DBContext {

    public static DAO_Admin INSTANCE = new DAO_Admin();
    private Connection con;
    private String status = "OK";

    public DAO_Admin() {
        try {
            con = new DBContext().getConnection();
        } catch (Exception e) {
            status = "Error at connection: " + e.getMessage();
        }
    }

    public static DAO_Admin getINSTANCE() {
        return INSTANCE;
    }

    public static void setINSTANCE(DAO_Admin INSTANCE) {
        DAO_Admin.INSTANCE = INSTANCE;
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

    public void insertBanker(Staff s) {
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

    public Staff get_Staff_By_StaffId(int id) {
        String sql = "select * from staff where staff_id=?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
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

    public Staff get_Staff_By_Username(String username) {
        String sql = "select * from staff where username=?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, username);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
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

    public Staff get_Staff_By_Email(String email) {
        String sql = "select * from staff where email=?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, email);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
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

    public Staff get_Staff_By_Phone(String phone) {
        String sql = "select * from staff where phone_number=?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, phone);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
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

    // get all staff sorted 
    public List<Staff> get_All_Staff_Sorted(int role_id, String sortBy) {
        List<Staff> list = new ArrayList<>();
        String sql = "select * from staff where role_id =? order by " + sortBy + " asc";
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

    // get staff sorted by status
    public List<Staff> get_Staff_By_Status_Sorted(int role_id, String status, String sortBy) {
        List<Staff> list = new ArrayList<>();
        String sql = "select * from staff where role_id =? and status =? order by " + sortBy + " asc";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, role_id);
            pre.setString(2, status);
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

    public List<Services> getAllServices() {
        List<Services> list = new ArrayList<>();
        String sql = "SELECT * FROM services where status='active'";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Services s = new Services();
                s.setService_id(rs.getInt("service_id"));
                s.setService_name(rs.getString("service_name"));
                s.setService_type(rs.getString("service_type"));
                s.setDescription(rs.getString("description"));
                s.setStatus(rs.getString("status"));
                list.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public Services getServiceById(int serviceId) {
        String sql = "SELECT * FROM services WHERE service_id = ?";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, serviceId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Services s = new Services();
                s.setService_id(rs.getInt("service_id"));
                s.setService_name(rs.getString("service_name"));
                s.setService_type(rs.getString("service_type"));
                s.setDescription(rs.getString("description"));
                s.setStatus(rs.getString("status"));
                return s;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public List<Services> get_All_Service_Sorted(String sortBy) {
        List<Services> list = new ArrayList<>();
        String sql = "select * from services order by " + sortBy + " asc";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
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

    public List<Services> get_All_Service_By_Status_Sorted(String sortBy, String status) {
        List<Services> list = new ArrayList<>();
        String sql = "select * from services where status = ? order by " + sortBy + " asc";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, status);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
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

    public List<Term> getAllTerm() {
        List<Term> list = new ArrayList<>();
        String sql = "select * from term";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
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

    public List<Term> get_All_Term_Sorted(String sortBy) {
        List<Term> list = new ArrayList<>();
        String sql = "select * from term order by " + sortBy + " asc";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
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

    public List<Term> get_All_Term_By_Status_Sorted(String sortBy, String status) {
        List<Term> list = new ArrayList<>();
        String sql = "select * from term where status=? order by " + sortBy + " asc";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, status);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
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

    public List<Transaction> getAllTransaction() {
        List<Transaction> list = new ArrayList<>();
        String sql = "select t.transaction_id,c.full_name,s.service_name,t.amount,t.transaction_date,t.transaction_type \n"
                + "from transactions t join customer c on t.customer_id=c.customer_id join services s on t.service_id=s.service_id";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Transaction t = new Transaction();
                t.setTransaction_id(rs.getInt(1));
                t.setCustomer_name(rs.getString(2));
                t.setService_name(rs.getString(3));
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

    public List<Transaction> getTransactionByCustomerName(String name) {
        List<Transaction> list = new ArrayList<>();
        String sql = "select t.transaction_id,c.full_name,s.service_name,t.amount,t.transaction_date,t.transaction_type \n"
                + "from transactions t join customer c on t.customer_id=c.customer_id join services s on t.service_id=s.service_id where c.full_name like ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, "%" + name + "%");
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Transaction t = new Transaction();
                t.setTransaction_id(rs.getInt(1));
                t.setCustomer_name(rs.getString(2));
                t.setService_name(rs.getString(3));
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

    public void InsertService(Services s) {
        String sql = "insert into services values(?,?,?,?)";
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

    public void UpdateService(Services s) {
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

    public Services get_Service_BY_Service_id(int service_id) {
        String sql = "select * from services where service_id=?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, service_id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                Services s = new Services();
                s.setService_id(rs.getInt(1));
                s.setService_name(rs.getString(2));
                s.setDescription(rs.getString(3));
                s.setService_type(rs.getString(4));
                s.setStatus(rs.getString(5));
                return s;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public Services get_Service_BY_Service_name(String service_name) {
        String sql = "select * from services where service_name=?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, service_name);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                Services s = new Services();
                s.setService_id(rs.getInt(1));
                s.setService_name(rs.getString(2));
                s.setDescription(rs.getString(3));
                s.setService_type(rs.getString(4));
                s.setStatus(rs.getString(5));
                return s;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public void InsertTerm(Term t) {
        String sql = "insert into term values(?,?,?,?)";
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

    public void UpdateTerm(Term t) {
        String sql = "update term set term_name=?, "
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

    public Term get_Term_BY_Term_id(int term_id) {
        String sql = "select * from term where term_id=?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, term_id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
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

    public Term get_Term_BY_Term_name(String term_name) {
        String sql = "select * from term where term_name=?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, term_name);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
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

    // Statistical functions for admin dashboard
    public int get_Total_Customers() {
        String sql = "SELECT COUNT(*) as total FROM customer";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("getTotalCustomers: " + e.getMessage());
        }
        return 0;
    }

    public int get_Total_Staff() {
        String sql = "SELECT COUNT(*) as total FROM staff";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("getTotalStaff: " + e.getMessage());
        }
        return 0;
    }

    public int get_Total_Insurance() {
        String sql = "SELECT COUNT(*) as total FROM insurance WHERE status = 'active'";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("getTotalInsuranceAccounts: " + e.getMessage());
        }
        return 0;
    }

    public int get_Active_Customers() {
        String sql = "SELECT COUNT(*) as total FROM customer WHERE status = 'active'";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("getActiveCustomers: " + e.getMessage());
        }
        return 0;
    }

    public int get_Active_Staff() {
        String sql = "SELECT COUNT(*) as total FROM staff WHERE status = 'active'";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("getActiveStaff: " + e.getMessage());
        }
        return 0;
    }

    public int get_Active_Services() {
        String sql = "SELECT COUNT(*) as total FROM services WHERE status = 'active'";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("getActiveServices: " + e.getMessage());
        }
        return 0;
    }

    public int get_Customers_By_Gender(String gender) {
        String sql = "SELECT COUNT(*) as count FROM customer WHERE gender = ?";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, gender);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            System.out.println("getCustomersByGender: " + e.getMessage());
        }
        return 0;
    }

    public int get_Customer_By_Card_Type(String cardType) {
        String sql = "SELECT COUNT(*) as count FROM customer WHERE card_type = ?";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, cardType);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            System.out.println("getAccountsByType: " + e.getMessage());
        }
        return 0;
    }

    public int get_Requests_By_Status(String status) {
        String sql = "SELECT COUNT(*) as count FROM request WHERE status = ?";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, status);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            System.out.println("getRequestsByStatus: " + e.getMessage());
        }
        return 0;
    }

    public int get_Total_Feedback() {
        String sql = "SELECT COUNT(*) as total FROM feedback";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("getTotalFeedback: " + e.getMessage());
        }
        return 0;
    }

    public List<Staff> search_Staff_By_FullName(String fullName) {
        List<Staff> list = new ArrayList<>();
        String sql = "SELECT * FROM staff WHERE full_name LIKE ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, "%" + fullName + "%");
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
            System.out.println("Error searching staff by full name: " + e.getMessage());
        }
        return list;
    }

    public List<Staff> search_Staff_By_PhoneNumber(String phoneNumber) {
        List<Staff> list = new ArrayList<>();
        String sql = "SELECT * FROM staff WHERE phone_number LIKE ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, "%" + phoneNumber + "%");
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
            System.out.println("Error searching staff by phone: " + e.getMessage());
        }
        return list;
    }

    public List<Staff> searchStaffByFullName(String fullName, int role_id) {
        List<Staff> list = new ArrayList<>();
        String sql = "SELECT * FROM staff WHERE full_name LIKE ? AND role_id = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, "%" + fullName + "%");
            pre.setInt(2, role_id);
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
            System.out.println("Error searching staff by full name: " + e.getMessage());
        }
        return list;
    }

    public List<Staff> searchStaffByPhone(String phoneNumber, int role_id) {
        List<Staff> list = new ArrayList<>();
        String sql = "SELECT * FROM staff WHERE phone_number LIKE ? AND role_id = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, "%" + phoneNumber + "%");
            pre.setInt(2, role_id);
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
            System.out.println("Error searching staff by phone: " + e.getMessage());
        }
        return list;
    }

    public List<Services> searchServiceByName(String search) {
        List<Services> list = new ArrayList<>();
        String sql = "select * from services where LOWER(service_name) like LOWER(?)";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, search);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
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

    public List<Term> searchTermByName(String search) {
        List<Term> list = new ArrayList<>();
        String sql = "select * from term where term_name like ?";
        try {
            PreparedStatement pre = con.prepareCall(sql);
            pre.setString(1, "%" + search + "%");
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Term t = new Term();
                t.setTerm_id(rs.getInt(1));
                t.setTerm_name(rs.getString(2));
                t.setDuration(rs.getInt(3));
                t.setTerm_type(rs.getString(4));
                t.setStatus(rs.getString(5));
                list.add(t);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

//    //delete service_term
//    public void deleteServiceTerm(int term_id) {
//        String sql = "delete from service_terms where term_id=? and status not in ('active')";
//
//        try {
//            PreparedStatement pre = con.prepareStatement(sql);
//            pre.setInt(1, term_id);
//            pre.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
//    }
//
//    //get service term by term_id
//    public ServiceTerms getServiceTermByTermId(int term_id) {
//        String sql = "select st.term_id,st.service_id,s.service_name,st.term_name,st.description,st.contract_terms,\n"
//                + "st.max_term_months,st.early_payment_penalty,st.interest_rate,st.min_payment,st.min_deposit,st.status,st.created_at\n"
//                + "from service_terms st join services s on st.service_id=s.service_id where term_id=?";
//        try {
//            PreparedStatement pre = con.prepareStatement(sql);
//            pre.setInt(1, term_id);
//            ResultSet rs = pre.executeQuery();
//            if (rs.next()) {
//                ServiceTerms s = new ServiceTerms();
//                s.setTerm_id(rs.getInt("term_id"));
//                s.setTerm_name(rs.getString("term_name"));
//                s.setService_id(rs.getInt("service_id"));
//                s.setService_name(rs.getString("service_name"));
//                s.setDescription(rs.getString("description"));
//                s.setContract_terms(rs.getString("contract_terms"));
//                s.setMax_term_months(rs.getInt("max_term_months"));
//                s.setEarly_payment_penalty(rs.getDouble("early_payment_penalty"));
//                s.setInterest_rate(rs.getDouble("interest_rate"));
//                s.setMin_payment(rs.getDouble("min_payment"));
//                s.setMin_deposit(rs.getDouble("min_deposit"));
//                s.setStatus(rs.getString("status"));
//                s.setCreated_at(rs.getDate("created_at"));
//                return s;
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        return null;
//    }
//
//    public void updateServiceTerm(ServiceTerms s) {
//        String sql = "UPDATE service_terms \n"
//                + "SET \n"
//                + "    term_name = ?, \n"
//                + "    description = ?, \n"
//                + "    contract_terms = ?, \n"
//                + "    max_term_months = ?, \n"
//                + "    early_payment_penalty = ?, \n"
//                + "    interest_rate = ?, \n"
//                + "    min_payment = ?, \n"
//                + "    min_deposit = ?,\n"
//                + "    [status] = ?\n"
//                + "WHERE term_id = ?;";
//        try {
//            PreparedStatement pre = con.prepareStatement(sql);
//            pre.setString(1, s.getTerm_name());
//            pre.setString(2, s.getDescription());
//            pre.setString(3, s.getContract_terms());
//            pre.setInt(4, s.getMax_term_months());
//            pre.setDouble(5, s.getEarly_payment_penalty());
//            pre.setDouble(6, s.getInterest_rate());
//            pre.setDouble(7, s.getMin_payment());
//            pre.setDouble(8, s.getMin_deposit());
//            pre.setString(9, s.getStatus());
//            pre.setInt(10, s.getTerm_id());
//            pre.executeUpdate();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
//
//    //search service term by name
//    public List<ServiceTerms> getServiceTermByName(String termName) {
//        List<ServiceTerms> list = new ArrayList<>();
//        String sql = "select st.*,s.service_name from service_terms st join services s on st.service_id=s.service_id where st.term_name like ?";
//        try {
//            PreparedStatement st = con.prepareStatement(sql);
//            st.setString(1, "%" + termName + "%");
//            ResultSet rs = st.executeQuery();
//            while (rs.next()) {
//                ServiceTerms s = new ServiceTerms();
//                s.setTerm_id(rs.getInt("term_id"));
//                s.setTerm_name(rs.getString("term_name"));
//                s.setService_id(rs.getInt("service_id"));
//                s.setService_name(rs.getString("service_name"));
//                s.setDescription(rs.getString("description"));
//                s.setContract_terms(rs.getString("contract_terms"));
//                s.setMax_term_months(rs.getInt("max_term_months"));
//                s.setEarly_payment_penalty(rs.getDouble("early_payment_penalty"));
//                s.setInterest_rate(rs.getDouble("interest_rate"));
//                s.setMin_payment(rs.getDouble("min_payment"));
//                s.setMin_deposit(rs.getDouble("min_deposit"));
//                s.setStatus(rs.getString("status"));
//                s.setCreated_at(rs.getDate("created_at"));
//                list.add(s);
//            }
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
//        return list;
//    }
//
    //filter
    //get list service_term with sort by
//    public List<ServiceTerms> getAllServiceTerms() {
//        List<ServiceTerms> list = new ArrayList<>();
//        String sql = "select st.term_id,st.service_id,s.service_name,st.term_name,st.description,st.contract_terms,\n"
//                + "st.max_term_months,st.early_payment_penalty,st.interest_rate,st.min_payment,st.min_deposit,st.status,st.created_at\n"
//                + "from service_terms st join services s on st.service_id=s.service_id";
//        try {
//            PreparedStatement pre = con.prepareStatement(sql);
//            ResultSet rs = pre.executeQuery();
//            while (rs.next()) {
//                ServiceTerms s = new ServiceTerms();
//                s.setTerm_id(rs.getInt("term_id"));
//                s.setTerm_name(rs.getString("term_name"));
//                s.setService_name(rs.getString("service_name"));
//                s.setService_id(rs.getInt("service_id"));
//                s.setDescription(rs.getString("description"));
//                s.setContract_terms(rs.getString("contract_terms"));
//                s.setMax_term_months(rs.getInt("max_term_months"));
//                s.setEarly_payment_penalty(rs.getDouble("early_payment_penalty"));
//                s.setInterest_rate(rs.getDouble("interest_rate"));
//                s.setMin_payment(rs.getDouble("min_payment"));
//                s.setMin_deposit(rs.getDouble("min_deposit"));
//                s.setStatus(rs.getString("status"));
//                s.setCreated_at(rs.getDate("created_at"));
//                list.add(s);
//            }
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
//        return list;
//    }
    public List<ServiceTerms> getFilteredServiceTerms(String serviceName, String sortBy) {
        List<ServiceTerms> list = new ArrayList<>();
        String sql = "SELECT st.*, s.service_name, t.duration FROM service_terms st "
                + "LEFT JOIN services s ON st.service_id = s.service_id "
                + "LEFT JOIN term t ON st.term_id = t.term_id WHERE 1=1";

        // Điều kiện lọc theo tên dịch vụ
        if (serviceName != null && !serviceName.isEmpty() && !serviceName.equals("all")) {
            sql += " AND s.service_name = ?";
        }

        // Xử lý sắp xếp
        if (sortBy != null && !sortBy.isEmpty() && !sortBy.equals("all")) {
            if (sortBy.equals("service_name")) {
                sql += " ORDER BY s.service_name";
            } else if (sortBy.equals("duration")) {
                sql += " ORDER BY t.duration";
            } else {
                sql += " ORDER BY st." + sortBy; // Các cột khác mặc định trong bảng service_terms
            }
        }

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            if (serviceName != null && !serviceName.isEmpty() && !serviceName.equals("all")) {
                ps.setString(1, serviceName);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ServiceTerms term = new ServiceTerms();
                term.setTerm_id(rs.getInt("term_id"));
                term.setService_name(rs.getString("service_name"));
                term.setService_id(rs.getInt("service_id"));
                term.setTerm_name(rs.getString("term_name"));
                term.setDescription(rs.getString("description"));
                term.setContract_terms(rs.getString("contract_terms"));
                term.setDuration(rs.getInt("duration"));
                term.setEarly_payment_penalty(rs.getDouble("early_payment_penalty"));
                term.setInterest_rate(rs.getDouble("interest_rate"));
                term.setMin_payment(rs.getDouble("min_payment"));
                term.setMin_deposit(rs.getDouble("min_deposit"));
                term.setCreated_at(rs.getTimestamp("created_at"));
                term.setStatus(rs.getString("status"));
                term.setServiceTerm_id(rs.getInt("serviceTerm_id"));
                list.add(term);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
//    //add new service_term
//    public void addServiceTerm(ServiceTerms s) {
//        String sql = "INSERT INTO service_terms (service_id, term_name, description, contract_terms, max_term_months, early_payment_penalty, interest_rate, min_payment, min_deposit)"
//                + "values (?,?,?,?,?,?,?,?,?)";
//        try {
//            PreparedStatement pre = con.prepareStatement(sql);
//            pre.setInt(1, s.getService_id());
//            pre.setString(2, s.getTerm_name());
//            pre.setString(3, s.getDescription());
//            pre.setString(4, s.getContract_terms());
//            pre.setInt(5, s.getMax_term_months());
//            pre.setDouble(6, s.getEarly_payment_penalty());
//            pre.setDouble(7, s.getInterest_rate());
//            pre.setDouble(8, s.getMin_payment());
//            pre.setDouble(9, s.getMin_deposit());
//            pre.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
//    }
//

    public void addServiceTerm(ServiceTerms s) {
        String sql = "INSERT INTO service_terms (service_id, term_name, description, contract_terms, early_payment_penalty, interest_rate, min_payment, min_deposit,term_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, s.getService_id());
            pre.setString(2, s.getTerm_name());
            pre.setString(3, s.getDescription());
            pre.setString(4, s.getContract_terms());
            pre.setDouble(5, s.getEarly_payment_penalty());
            pre.setDouble(6, s.getInterest_rate());
            pre.setDouble(7, s.getMin_payment());
            pre.setDouble(8, s.getMin_deposit());
            if (s.getTerm_id() == null) {
                pre.setNull(9, java.sql.Types.INTEGER);
            } else {
                pre.setInt(9, s.getTerm_id());
            }
            pre.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

// Xóa service_term (chỉ xóa khi không active)
    public void deleteServiceTerm(int serviceTerm_id) {
        String sql = "DELETE FROM service_terms WHERE serviceTerm_id = ? AND status NOT IN ('active')";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, serviceTerm_id);
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

// Lấy thông tin service term bằng term_id
    public ServiceTerms getServiceTermById(int servicetTerm_id) {
        String sql = "SELECT st.*, t.duration, s.service_name FROM service_terms st "
                + "LEFT JOIN services s ON st.service_id = s.service_id "
                + "LEFT JOIN term t ON st.term_id = t.term_id WHERE st.serviceTerm_id = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, servicetTerm_id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
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
                s.setMin_payment(rs.getDouble("min_payment"));
                s.setMin_deposit(rs.getDouble("min_deposit"));
                s.setStatus(rs.getString("status"));
                s.setCreated_at(rs.getTimestamp("created_at"));
                return s;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

// Cập nhật service_term
    public void updateServiceTerm(ServiceTerms s) {
        String sql = "UPDATE service_terms SET "
                + "term_id= ?,term_name = ?, description = ?, contract_terms = ?, "
                + "early_payment_penalty = ?, interest_rate = ?, "
                + "min_payment = ?, min_deposit = ?, status = ? "
                + "WHERE serviceTerm_id = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            if (s.getTerm_id() == null) {
                pre.setNull(1, java.sql.Types.INTEGER);
            } else {
                pre.setInt(1, s.getTerm_id());
            }
            pre.setString(2, s.getTerm_name());
            pre.setString(3, s.getDescription());
            pre.setString(4, s.getContract_terms());
            pre.setDouble(5, s.getEarly_payment_penalty());
            pre.setDouble(6, s.getInterest_rate());
            pre.setDouble(7, s.getMin_payment());
            pre.setDouble(8, s.getMin_deposit());
            pre.setString(9, s.getStatus());
            pre.setInt(10, s.getServiceTerm_id());
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

// Tìm kiếm service_term theo tên
    public List<ServiceTerms> getServiceTermByName(String termName) {
        List<ServiceTerms> list = new ArrayList<>();
        String sql = "SELECT st.*, s.service_name, t.duration FROM service_terms st "
                + "LEFT JOIN services s ON st.service_id = s.service_id "
                + "LEFT JOIN term t ON st.term_id = t.term_id "
                + "WHERE st.term_name LIKE ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, "%" + termName + "%");
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
                s.setMin_payment(rs.getDouble("min_payment"));
                s.setMin_deposit(rs.getDouble("min_deposit"));
                s.setStatus(rs.getString("status"));
                s.setCreated_at(rs.getTimestamp("created_at"));
                list.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

// Lấy tất cả service_terms
    public List<ServiceTerms> getAllServiceTerms() {
        List<ServiceTerms> list = new ArrayList<>();
        String sql = "SELECT st.*, s.service_name, t.duration FROM service_terms st "
                + "LEFT JOIN services s ON st.service_id = s.service_id "
                + "LEFT JOIN term t ON st.term_id = t.term_id";
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
                s.setMin_payment(rs.getDouble("min_payment"));
                s.setMin_deposit(rs.getDouble("min_deposit"));
                s.setStatus(rs.getString("status"));
                s.setCreated_at(rs.getTimestamp("created_at"));
                list.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    //page
    public List<ServiceTerms> getListByPage(List<ServiceTerms> list, int start, int end) {
        List<ServiceTerms> arr = new ArrayList<>();
        for (int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }

    // Main method for testing
    public static void main(String[] args) {
        DAO_Admin d = new DAO_Admin();
//        String date ="2000-12-31";
//        Date sqlDate = java.sql.Date.valueOf(date);
        ServiceTerms s = new ServiceTerms(null, 3, "hhh", "j", "d", 0, 0, 0, 0);
        d.addServiceTerm(s);

    }
}
