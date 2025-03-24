package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Customer;
import model.Feedback;
import model.Insurance;
import model.ServiceTerms;
import model.News;
import model.NewsCategory;
import model.ServiceProvider;
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

    // insurance
    public Insurance login_insurance(String username, String password) {
        String sql = "select * from insurance where username=? and password=?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, username);
            pre.setString(2, password);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                int insurance_id = rs.getInt(1);
                int role_id = rs.getInt(2);
                String insurance_name = rs.getString(5);
                String email = rs.getString(6);
                String phone_number = rs.getString(7);
                String address = rs.getString(8);
                String status = rs.getString(9);
                Insurance i = new Insurance(insurance_id, role_id, username, password, insurance_name, email, phone_number, address, status);
                return i;
            }
        } catch (Exception e) {
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
        String sql = "set nocount on; "
                + "delete from news where staff_id= ?;"
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

    public boolean updateBanker(Staff s) {
        String sql = "update staff set full_name = ?,"
                + "email=?,"
                + "username=?,"
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
            pre.setString(4, s.getPhone_number());
            pre.setString(5, s.getGender());
            pre.setDate(6, new java.sql.Date(s.getDate_of_birth().getTime()));
            pre.setString(7, s.getAddress());
            pre.setInt(8, s.getRole_id());
            pre.setString(9, s.getStatus());
            pre.setInt(10, s.getStaff_id());
            System.out.println("Executing SQL " + pre.toString());
            return pre.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
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

    public List<Staff> getListStaffByConditions(String search, String sortBy, int role_id, String status) {
        List<Staff> list = new ArrayList<>();
        String sql = "select * from staff where (1=1)";

        if (role_id > 0) {
            sql = sql + " and role_id = ?";
        }

        if (status != null && !status.isEmpty() && !status.equals("all")) {
            sql = sql + " and status= ?";
        }
        if (search != null && !search.isEmpty()) {
            sql = sql + " and full_name LIKE ?";
        }

        sql = sql + " order by " + sortBy + " asc";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            int index = 1;
            if (role_id > 0) {
                pre.setInt(index++, role_id);
            }
            if (status != null && !status.isEmpty() && !status.equals("all")) {
                pre.setString(index++, status);
            }
            if (search != null && !search.isEmpty()) {
                pre.setString(index++, "%" + search + "%");
            }

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
            System.out.println(sql);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Staff> getListStaffByConditions(List<Staff> staff, String search, String sortBy, int role_id, String status) {
        List<Staff> list = new ArrayList<>();
        String sql = "select * from staff where (1=1)";

        if (role_id > 0) {
            sql = sql + " and role_id = ?";
        }

        if (status != null && !status.isEmpty() && !status.equals("all")) {
            sql = sql + " and status= ?";
        }
        if (search != null && !search.isEmpty()) {
            sql = sql + " and full_name LIKE ?";
        }

        sql = sql + " order by " + sortBy + " asc";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            int index = 1;
            if (role_id > 0) {
                pre.setInt(index++, role_id);
            }
            if (status != null && !status.isEmpty() && !status.equals("all")) {
                pre.setString(index++, status);
            }
            if (search != null && !search.isEmpty()) {
                pre.setString(index++, "%" + search + "%");
            }

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
            System.out.println(sql);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Services> getAllServices() {
        List<Services> list = new ArrayList<>();
        String sql = "SELECT * FROM services ";
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

    public List<Transaction> getAllTransactionSorted(String sortBy) {
        List<Transaction> list = new ArrayList<>();
        String sql = "select t.transaction_id,c.full_name,s.service_name,t.amount"
                + ",t.transaction_date,t.transaction_type \n"
                + "from transactions t join customer c on "
                + "t.customer_id=c.customer_id join services s on "
                + "t.service_id=s.service_id order by " + sortBy + " asc";
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

    public List<Transaction> getAllTransactionByTypeSorted(String sortBy, String type) {
        List<Transaction> list = new ArrayList<>();
        String sql = "select t.transaction_id,c.full_name,s.service_name,t.amount"
                + ",t.transaction_date,t.transaction_type \n"
                + "from transactions t join customer c on "
                + "t.customer_id=c.customer_id join services s on "
                + "t.service_id=s.service_id where transaction_type = ? order by " + sortBy + " asc";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, type);
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

    public List<Staff> getStaffByPage(List<Staff> list, int start, int end) {
        List<Staff> arr = new ArrayList<>();
        for (int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }

    public List<Services> getServiceByPage(List<Services> list, int start, int end) {
        List<Services> arr = new ArrayList<>();
        for (int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }

    public List<Term> getTermByPage(List<Term> list, int start, int end) {
        List<Term> arr = new ArrayList<>();
        for (int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }

    public List<Transaction> getTransactionByPage(List<Transaction> list, int start, int end) {
        List<Transaction> arr = new ArrayList<>();
        for (int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }

    // servicer Term - phong
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

    //kiem tra trung lap
    public boolean isDuplicateServiceTerm(int termId, String termName, int serviceId, double minDeposit, double interest_rate) {
        String query = "SELECT COUNT(*) FROM service_terms WHERE term_id = ? AND term_name = ? "
                + "AND service_id = ? AND min_deposit = ? and interest_rate=?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, termId);
            ps.setString(2, termName);
            ps.setInt(3, serviceId);
            ps.setDouble(4, minDeposit);
            ps.setDouble(5, interest_rate);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Nếu COUNT(*) > 0, nghĩa là đã tồn tại
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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

    public List<NewsCategory> getAllNewsCategory() {
        List<NewsCategory> list = new ArrayList<>();
        String sql = "select * from news_category";

        try (PreparedStatement st = con.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                NewsCategory n = new NewsCategory();
                n.setCategory_id(rs.getInt("category_id"));
                n.setCategory_name(rs.getString("category_name"));
                list.add(n);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    //get all news to approved or rejected
    public List<News> getNewsFilterPending(int category_id, String sortBy) {
        List<News> list = new ArrayList<>();
        String sql = "SELECT n.*, c.category_name, s.full_name FROM news n "
                + "JOIN news_category c ON n.category_id = c.category_id "
                + "JOIN staff s ON n.staff_id = s.staff_id "
                + "WHERE n.status='pending'";

        if (category_id > 0) {
            sql += " AND n.category_id = ?";
        }

        // Sắp xếp theo ngày tạo giảm dần
        if (sortBy != null && !sortBy.equals("all") && !sortBy.isEmpty()) {
            sql += " ORDER BY n.created_at DESC, n." + sortBy;
        } else {
            sql += " ORDER BY n.created_at DESC";
        }

        try {
            PreparedStatement st = con.prepareStatement(sql);
            int index = 1;
            if (category_id > 0) {
                st.setInt(index++, category_id);
            }
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                News n = new News();
                n.setNews_id(rs.getInt("news_id"));
                n.setTitle(rs.getString("title"));
                n.setContent(rs.getString("content"));
                n.setCreated_at(rs.getDate("created_at"));
                n.setUpdated_at(rs.getDate("updated_at"));
                n.setStatus(rs.getString("status"));
                n.setCategory_name(rs.getString("category_name"));
                n.setCategory_id(rs.getInt("category_id"));
                n.setPicture(rs.getString("picture"));
                n.setStaff_name(rs.getString("full_name"));
                list.add(n);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    //update status 
    public boolean updateNewsStatus(int newsId, String status) {
        String sql = "UPDATE news SET status = ? WHERE news_id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, newsId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu cập nhật thành côn

        } catch (Exception e) {
        }
        return false;
    }

    public List<News> getListNewsByPage(List<News> list, int start, int end) {
        List<News> arr = new ArrayList<>();
        for (int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }

    // Search news by title
    public List<News> getSearchNewsByTitle(String title) {
        List<News> list = new ArrayList<>();
        String sql = "SELECT n.*, c.category_name,s.full_name FROM news n "
                + "JOIN news_category c ON n.category_id = c.category_id "
                + "JOIN staff s ON n.staff_id = s.staff_id "
                + "WHERE n.title LIKE ?";

        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, "%" + title + "%");
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    News n = new News();
                    n.setNews_id(rs.getInt("news_id"));
                    n.setStaff_id(rs.getInt("staff_id"));
                    n.setTitle(rs.getString("title"));
                    n.setContent(rs.getString("content"));
                    n.setCreated_at(rs.getDate("created_at"));
                    n.setUpdated_at(rs.getDate("updated_at"));
                    n.setStatus(rs.getString("status"));
                    n.setCategory_id(rs.getInt("category_id"));
                    n.setCategory_name(rs.getString("category_name"));
                    n.setPicture(rs.getString("picture"));
                    n.setStaff_name(rs.getString("full_name"));
                    list.add(n);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // management insurance
    public void insertInsurance(Insurance i) {
        String sql = "insert into insurance values (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, i.getRole_id());
            pre.setString(2, i.getUsername());
            pre.setString(3, i.getPassword());
            pre.setString(4, i.getInsurance_name());
            pre.setString(5, i.getEmail());
            pre.setString(6, i.getPhone_number());
            pre.setString(7, i.getAddress());
            pre.setString(8, i.getStatus());
            pre.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateInsurance(Insurance i) {
        String sql = "update insurance set username =?,"
                + "insurance_name=?,"
                + "email=?,"
                + "phone_number=?,"
                + "address=?,"
                + "status=? "
                + "where insurance_id=?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, i.getUsername());
            pre.setString(2, i.getInsurance_name());
            pre.setString(3, i.getEmail());
            pre.setString(4, i.getPhone_number());
            pre.setString(5, i.getAddress());
            pre.setString(6, i.getStatus());
            pre.setInt(7, i.getInsurance_id());
            pre.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Insurance getInsuranceById(int id) {
        String sql = "select * from insurance where insurance_id =?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                Insurance i = new Insurance();
                i.setInsurance_id(rs.getInt(1));
                i.setRole_id(rs.getInt(2));
                i.setUsername(rs.getString(3));
                i.setPassword(rs.getString(4));
                i.setInsurance_name(rs.getString(5));
                i.setEmail(rs.getString(6));
                i.setPhone_number(rs.getString(7));
                i.setAddress(rs.getString(8));
                i.setStatus(rs.getString(9));
                return i;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public Insurance getInsuranceByName(String name) {
        String sql = "select * from insurance where insurance_name =?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, name);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                Insurance i = new Insurance();
                i.setInsurance_id(rs.getInt(1));
                i.setRole_id(rs.getInt(2));
                i.setUsername(rs.getString(3));
                i.setPassword(rs.getString(4));
                i.setInsurance_name(rs.getString(5));
                i.setEmail(rs.getString(6));
                i.setPhone_number(rs.getString(7));
                i.setAddress(rs.getString(8));
                i.setStatus(rs.getString(9));
                return i;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public Insurance getInsuranceByUserName(String username) {
        String sql = "select * from insurance where username =?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, username);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                Insurance i = new Insurance();
                i.setInsurance_id(rs.getInt(1));
                i.setRole_id(rs.getInt(2));
                i.setUsername(rs.getString(3));
                i.setPassword(rs.getString(4));
                i.setInsurance_name(rs.getString(5));
                i.setEmail(rs.getString(6));
                i.setPhone_number(rs.getString(7));
                i.setAddress(rs.getString(8));
                i.setStatus(rs.getString(9));
                return i;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public Insurance getInsuranceByEmail(String email) {
        String sql = "select * from insurance where email =?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, email);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                Insurance i = new Insurance();
                i.setInsurance_id(rs.getInt(1));
                i.setRole_id(rs.getInt(2));
                i.setUsername(rs.getString(3));
                i.setPassword(rs.getString(4));
                i.setInsurance_name(rs.getString(5));
                i.setEmail(rs.getString(6));
                i.setPhone_number(rs.getString(7));
                i.setAddress(rs.getString(8));
                i.setStatus(rs.getString(9));
                return i;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public Insurance getInsuranceByPhone(String phone) {
        String sql = "select * from insurance where phone_number =?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, phone);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                Insurance i = new Insurance();
                i.setInsurance_id(rs.getInt(1));
                i.setRole_id(rs.getInt(2));
                i.setUsername(rs.getString(3));
                i.setPassword(rs.getString(4));
                i.setInsurance_name(rs.getString(5));
                i.setEmail(rs.getString(6));
                i.setPhone_number(rs.getString(7));
                i.setAddress(rs.getString(8));
                i.setStatus(rs.getString(9));
                return i;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public List<Insurance> getAllInsurance() {
        List<Insurance> list = new ArrayList<>();
        String sql = "select * from insurance";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Insurance i = new Insurance();
                i.setInsurance_id(rs.getInt(1));
                i.setRole_id(rs.getInt(2));
                i.setUsername(rs.getString(3));
                i.setPassword(rs.getString(4));
                i.setInsurance_name(rs.getString(5));
                i.setEmail(rs.getString(6));
                i.setPhone_number(rs.getString(7));
                i.setAddress(rs.getString(8));
                i.setStatus(rs.getString(9));
                list.add(i);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Insurance> getAllInsuranceSorted(String sortBy) {
        List<Insurance> list = new ArrayList<>();
        String sql = "select * from insurance order by " + sortBy + " asc";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Insurance i = new Insurance();
                i.setInsurance_id(rs.getInt(1));
                i.setRole_id(rs.getInt(2));
                i.setUsername(rs.getString(3));
                i.setPassword(rs.getString(4));
                i.setInsurance_name(rs.getString(5));
                i.setEmail(rs.getString(6));
                i.setPhone_number(rs.getString(7));
                i.setAddress(rs.getString(8));
                i.setStatus(rs.getString(9));
                list.add(i);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Insurance> gettAllInsuranceSortedByStatus(String sortBy, String status) {
        List<Insurance> list = new ArrayList<>();
        String sql = "select * from insurance where status = ? order by " + sortBy + " asc";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, status);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Insurance i = new Insurance();
                i.setInsurance_id(rs.getInt(1));
                i.setRole_id(rs.getInt(2));
                i.setUsername(rs.getString(3));
                i.setPassword(rs.getString(4));
                i.setInsurance_name(rs.getString(5));
                i.setEmail(rs.getString(6));
                i.setPhone_number(rs.getString(7));
                i.setAddress(rs.getString(8));
                i.setStatus(rs.getString(9));
                list.add(i);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Insurance> getAllInsuranceByName(String search) {
        List<Insurance> list = new ArrayList<>();
        String sql = "select * from insurance where insurance_name like ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, "%" + search + "%");
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Insurance i = new Insurance();
                i.setInsurance_id(rs.getInt(1));
                i.setRole_id(rs.getInt(2));
                i.setUsername(rs.getString(3));
                i.setPassword(rs.getString(4));
                i.setInsurance_name(rs.getString(5));
                i.setEmail(rs.getString(6));
                i.setPhone_number(rs.getString(7));
                i.setAddress(rs.getString(8));
                i.setStatus(rs.getString(9));
                list.add(i);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Insurance> getInsuranceByPage(List<Insurance> list, int start, int end) {
        List<Insurance> arr = new ArrayList<>();
        for (int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }

    // management service provider 
    public void insertServiceProvider(ServiceProvider sp) {
        String sql = "insert into serviceprovider values (?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, sp.getRole_id());
            pre.setString(2, sp.getName());
            pre.setString(3, sp.getUsername());
            pre.setString(4, sp.getPassword());
            pre.setString(5, sp.getServicetype());
            pre.setString(6, sp.getEmail());
            pre.setString(7, sp.getPhone_number());
            pre.setString(8, sp.getAddress());
            pre.setString(9, sp.getStatus());
            pre.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateServiceProvider(ServiceProvider sp) {
        String sql = "update serviceprovider set name =?,"
                + "username=?,"
                + "ServiceType=?,"
                + "email=?,"
                + "phone_number=?,"
                + "status=? "
                + "where provider_id=?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, sp.getName());
            pre.setString(2, sp.getUsername());
            pre.setString(3, sp.getServicetype());
            pre.setString(4, sp.getEmail());
            pre.setString(5, sp.getPhone_number());
            pre.setString(6, sp.getStatus());
            pre.setInt(7, sp.getProvider_id());
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public ServiceProvider getServiceProviderById(int id) {
        String sql = "select * from serviceprovider where provider_id =?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                ServiceProvider i = new ServiceProvider();
                i.setProvider_id(rs.getInt(1));
                i.setRole_id(rs.getInt(2));
                i.setName(rs.getString(3));
                i.setUsername(rs.getString(4));
                i.setPassword(rs.getString(5));
                i.setServicetype(rs.getString(6));
                i.setEmail(rs.getString(7));
                i.setPhone_number(rs.getString(8));
                i.setAddress(rs.getString(9));
                i.setStatus(rs.getString(10));
                return i;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public ServiceProvider getServiceProviderByName(String name) {
        String sql = "select * from serviceprovider where Name =?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, name);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                ServiceProvider i = new ServiceProvider();
                i.setProvider_id(rs.getInt(1));
                i.setRole_id(rs.getInt(2));
                i.setName(rs.getString(3));
                i.setUsername(rs.getString(4));
                i.setPassword(rs.getString(5));
                i.setServicetype(rs.getString(6));
                i.setEmail(rs.getString(7));
                i.setPhone_number(rs.getString(8));
                i.setAddress(rs.getString(9));
                i.setStatus(rs.getString(10));
                return i;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public ServiceProvider getServiceProviderByUserName(String username) {
        String sql = "select * from serviceprovider where username =?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, username);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                ServiceProvider i = new ServiceProvider();
                i.setProvider_id(rs.getInt(1));
                i.setRole_id(rs.getInt(2));
                i.setName(rs.getString(3));
                i.setUsername(rs.getString(4));
                i.setPassword(rs.getString(5));
                i.setServicetype(rs.getString(6));
                i.setEmail(rs.getString(7));
                i.setPhone_number(rs.getString(8));
                i.setAddress(rs.getString(9));
                i.setStatus(rs.getString(10));
                return i;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public ServiceProvider getServiceProviderByEmail(String email) {
        String sql = "select * from serviceprovider where email =?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, email);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                ServiceProvider i = new ServiceProvider();
                i.setProvider_id(rs.getInt(1));
                i.setRole_id(rs.getInt(2));
                i.setName(rs.getString(3));
                i.setUsername(rs.getString(4));
                i.setPassword(rs.getString(5));
                i.setServicetype(rs.getString(6));
                i.setEmail(rs.getString(7));
                i.setPhone_number(rs.getString(8));
                i.setAddress(rs.getString(9));
                i.setStatus(rs.getString(10));
                return i;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public ServiceProvider getServiceProviderByPhone(String phone) {
        String sql = "select * from serviceprovider where phone_number =?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, phone);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                ServiceProvider i = new ServiceProvider();
                i.setProvider_id(rs.getInt(1));
                i.setRole_id(rs.getInt(2));
                i.setName(rs.getString(3));
                i.setUsername(rs.getString(4));
                i.setPassword(rs.getString(5));
                i.setServicetype(rs.getString(6));
                i.setEmail(rs.getString(7));
                i.setPhone_number(rs.getString(8));
                i.setAddress(rs.getString(9));
                i.setStatus(rs.getString(10));
                return i;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public List<ServiceProvider> getAllServiceProvider() {
        List<ServiceProvider> list = new ArrayList<>();
        String sql = "select * from serviceprovider";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                ServiceProvider i = new ServiceProvider();
                i.setProvider_id(rs.getInt(1));
                i.setRole_id(rs.getInt(2));
                i.setName(rs.getString(3));
                i.setUsername(rs.getString(4));
                i.setPassword(rs.getString(5));
                i.setServicetype(rs.getString(6));
                i.setEmail(rs.getString(7));
                i.setPhone_number(rs.getString(8));
                i.setAddress(rs.getString(9));
                i.setStatus(rs.getString(10));
                list.add(i);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<ServiceProvider> getAllServiceProviderSorted(String sortBy) {
        List<ServiceProvider> list = new ArrayList<>();
        String sql = "select * from serviceprovider order by " + sortBy + " asc";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                ServiceProvider i = new ServiceProvider();
                i.setProvider_id(rs.getInt(1));
                i.setRole_id(rs.getInt(2));
                i.setName(rs.getString(3));
                i.setUsername(rs.getString(4));
                i.setPassword(rs.getString(5));
                i.setServicetype(rs.getString(6));
                i.setEmail(rs.getString(7));
                i.setPhone_number(rs.getString(8));
                i.setAddress(rs.getString(9));
                i.setStatus(rs.getString(10));
                list.add(i);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<ServiceProvider> gettAllServiceProviderSortedByStatus(String sortBy, String status) {
        List<ServiceProvider> list = new ArrayList<>();
        String sql = "select * from serviceprovider where status = ? order by " + sortBy + " asc";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, status);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                ServiceProvider i = new ServiceProvider();
                i.setProvider_id(rs.getInt(1));
                i.setRole_id(rs.getInt(2));
                i.setName(rs.getString(3));
                i.setUsername(rs.getString(4));
                i.setPassword(rs.getString(5));
                i.setServicetype(rs.getString(6));
                i.setEmail(rs.getString(7));
                i.setPhone_number(rs.getString(8));
                i.setAddress(rs.getString(9));
                i.setStatus(rs.getString(10));
                list.add(i);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<ServiceProvider> getAllServiceProviderByName(String search) {
        List<ServiceProvider> list = new ArrayList<>();
        String sql = "select * from serviceprovider where Name like ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, "%" + search + "%");
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                ServiceProvider i = new ServiceProvider();
                i.setProvider_id(rs.getInt(1));
                i.setRole_id(rs.getInt(2));
                i.setName(rs.getString(3));
                i.setUsername(rs.getString(4));
                i.setPassword(rs.getString(5));
                i.setServicetype(rs.getString(6));
                i.setEmail(rs.getString(7));
                i.setPhone_number(rs.getString(8));
                i.setAddress(rs.getString(9));
                i.setStatus(rs.getString(10));
                list.add(i);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<ServiceProvider> getServiceProviderByPage(List<ServiceProvider> list, int start, int end) {
        List<ServiceProvider> arr = new ArrayList<>();
        for (int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }

    // Sơn 
    //feedback
    public List<Feedback> getListFeedback() {
        List<Feedback> list = new ArrayList<>();
        String sql = "select feedback.*, customer.full_name, services.service_name from feedback\n"
                + "join customer on feedback.customer_id = customer.customer_id\n"
                + "join services on feedback.service_id = services.service_id";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int feedback_id = rs.getInt("feedback_id");
                int customer_id = rs.getInt("customer_id");
                int service_id = rs.getInt("service_id");
                int feedback_rate = rs.getInt("feedback_rate");
                String full_name = rs.getString("full_name");
                String service_name = rs.getString("service_name");
                String feedback_content = rs.getString("feedback_content");
                Date feedback_date = rs.getDate("feedback_date");
                Feedback f = new Feedback(feedback_id, customer_id, service_id,
                        feedback_rate, feedback_date, full_name, service_name, feedback_content);
                list.add(f);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Feedback> getListServiceByFeedback() {
        List<Feedback> list = new ArrayList<>();
        String sql = "select distinct services.service_id, services.service_name from feedback\n"
                + "join customer on feedback.customer_id = customer.customer_id\n"
                + "join services on feedback.service_id = services.service_id";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int service_id = rs.getInt("service_id");
                String service_name = rs.getString("service_name");
                Feedback f = new Feedback(service_id, service_name);
                list.add(f);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Feedback> getAllFeedback(int offset, int next) {
        List<Feedback> list = new ArrayList<>();
        String sql = "select feedback.*, customer.full_name, services.service_name from feedback\n"
                + "join customer on feedback.customer_id = customer.customer_id\n"
                + "join services on feedback.service_id = services.service_id\n"
                + "order by feedback_id\n"
                + "offset ? row fetch next ? row only";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, (offset - 1) * next);
            pre.setInt(2, next);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int feedback_id = rs.getInt("feedback_id");
                int customer_id = rs.getInt("customer_id");
                int service_id = rs.getInt("service_id");
                int feedback_rate = rs.getInt("feedback_rate");
                String full_name = rs.getString("full_name");
                String service_name = rs.getString("service_name");
                String feedback_content = rs.getString("feedback_content");
                Date feedback_date = rs.getDate("feedback_date");
                Feedback f = new Feedback(feedback_id, customer_id, service_id,
                        feedback_rate, feedback_date, full_name, service_name, feedback_content);
                list.add(f);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public void insertFeedback(Feedback f) {
        String sql = "insert into feedback(customer_id,service_id,feedback_content,feedback_rate)\n"
                + "VALUES (?,?,?,?)";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, f.getCustomer_id());
            pre.setInt(2, f.getService_id());
            pre.setString(3, f.getFeedback_content());
            pre.setInt(4, f.getFeedback_rate());
            pre.executeUpdate();
            System.out.println("Insert succesfully");
        } catch (Exception e) {
        }

    }

    public int getTotalFeedback() {
        String sql = "select COUNT(*) from feedback";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return 0;
    }

    public int getTotalFeedbackByServiceID(int service_id) {
        String sql = "select COUNT(*) from feedback\n"
                + "where service_id = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, service_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return 0;
    }

    public List<Feedback> getListFeedbackByServiceID(int service_id) {
        List<Feedback> list = new ArrayList<>();
        String sql = "select feedback.*, customer.full_name, services.service_name from feedback\n"
                + "join customer on feedback.customer_id = customer.customer_id\n"
                + "join services on feedback.service_id = services.service_id\n"
                + "where services.service_id = ?";

        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, service_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int feedback_id = rs.getInt("feedback_id");
                int customer_id = rs.getInt("customer_id");
                service_id = rs.getInt("service_id");
                int feedback_rate = rs.getInt("feedback_rate");
                String full_name = rs.getString("full_name");
                String service_name = rs.getString("service_name");
                String feedback_content = rs.getString("feedback_content");
                Date feedback_date = rs.getDate("feedback_date");
                Feedback f = new Feedback(feedback_id, customer_id, service_id,
                        feedback_rate, feedback_date, full_name, service_name, feedback_content);
                list.add(f);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Feedback> getAllFeedbackByServiceID(int service_id, int offset, int next) {
        List<Feedback> list = new ArrayList<>();
        String sql = "select feedback.*, customer.full_name, services.service_name from feedback\n"
                + "join customer on feedback.customer_id = customer.customer_id\n"
                + "join services on feedback.service_id = services.service_id\n"
                + "where feedback.service_id = ?\n"
                + "order by feedback_id\n"
                + "offset ? row fetch next ? row only";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, service_id);
            pre.setInt(2, (offset - 1) * next);
            pre.setInt(3, next);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int feedback_id = rs.getInt("feedback_id");
                int customer_id = rs.getInt("customer_id");
                service_id = rs.getInt("service_id");
                int feedback_rate = rs.getInt("feedback_rate");
                String full_name = rs.getString("full_name");
                String service_name = rs.getString("service_name");
                String feedback_content = rs.getString("feedback_content");
                Date feedback_date = rs.getDate("feedback_date");
                Feedback f = new Feedback(feedback_id, customer_id, service_id,
                        feedback_rate, feedback_date, full_name, service_name, feedback_content);
                list.add(f);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Feedback> sortFeedbackByFeedbackDate(int offset, int next) {
        List<Feedback> list = new ArrayList<>();
        String sql = "select feedback.*, customer.full_name, services.service_name from feedback\n"
                + "join customer on feedback.customer_id = customer.customer_id\n"
                + "join services on feedback.service_id = services.service_id\n"
                + "order by feedback_date\n"
                + "offset ? row fetch next ? row only";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, (offset - 1) * next);
            pre.setInt(2, next);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int feedback_id = rs.getInt("feedback_id");
                int customer_id = rs.getInt("customer_id");
                int service_id = rs.getInt("service_id");
                int feedback_rate = rs.getInt("feedback_rate");
                String full_name = rs.getString("full_name");
                String service_name = rs.getString("service_name");
                String feedback_content = rs.getString("feedback_content");
                Date feedback_date = rs.getDate("feedback_date");
                Feedback f = new Feedback(feedback_id, customer_id, service_id,
                        feedback_rate, feedback_date, full_name, service_name, feedback_content);
                list.add(f);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Feedback> sortFeedbackByFeedbackDateAndServiceID(int service_id, int offset, int next) {
        List<Feedback> list = new ArrayList<>();
        String sql = "select feedback.*, customer.full_name, services.service_name from feedback\n"
                + "join customer on feedback.customer_id = customer.customer_id\n"
                + "join services on feedback.service_id = services.service_id\n"
                + "where feedback.service_id = ?\n"
                + "order by feedback_date\n"
                + "offset ? row fetch next ? row only";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, service_id);
            pre.setInt(2, (offset - 1) * next);
            pre.setInt(3, next);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int feedback_id = rs.getInt("feedback_id");
                int customer_id = rs.getInt("customer_id");
                service_id = rs.getInt("service_id");
                int feedback_rate = rs.getInt("feedback_rate");
                String full_name = rs.getString("full_name");
                String service_name = rs.getString("service_name");
                String feedback_content = rs.getString("feedback_content");
                Date feedback_date = rs.getDate("feedback_date");
                Feedback f = new Feedback(feedback_id, customer_id, service_id,
                        feedback_rate, feedback_date, full_name, service_name, feedback_content);
                list.add(f);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Feedback> sortFeedbackByFeedbackRate(int offset, int next) {
        List<Feedback> list = new ArrayList<>();
        String sql = "select feedback.*, customer.full_name, services.service_name from feedback\n"
                + "join customer on feedback.customer_id = customer.customer_id\n"
                + "join services on feedback.service_id = services.service_id\n"
                + "order by feedback_rate\n"
                + "offset ? row fetch next ? row only";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, (offset - 1) * next);
            pre.setInt(2, next);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int feedback_id = rs.getInt("feedback_id");
                int customer_id = rs.getInt("customer_id");
                int service_id = rs.getInt("service_id");
                int feedback_rate = rs.getInt("feedback_rate");
                String full_name = rs.getString("full_name");
                String service_name = rs.getString("service_name");
                String feedback_content = rs.getString("feedback_content");
                Date feedback_date = rs.getDate("feedback_date");
                Feedback f = new Feedback(feedback_id, customer_id, service_id,
                        feedback_rate, feedback_date, full_name, service_name, feedback_content);
                list.add(f);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Feedback> sortFeedbackByFeedbackRateAndServiceID(int service_id, int offset, int next) {
        List<Feedback> list = new ArrayList<>();
        String sql = "select feedback.*, customer.full_name, services.service_name from feedback\n"
                + "join customer on feedback.customer_id = customer.customer_id\n"
                + "join services on feedback.service_id = services.service_id\n"
                + "where feedback.service_id = ?\n"
                + "order by feedback_rate\n"
                + "offset ? row fetch next ? row only";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, service_id);
            pre.setInt(2, (offset - 1) * next);
            pre.setInt(3, next);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int feedback_id = rs.getInt("feedback_id");
                int customer_id = rs.getInt("customer_id");
                service_id = rs.getInt("service_id");
                int feedback_rate = rs.getInt("feedback_rate");
                String full_name = rs.getString("full_name");
                String service_name = rs.getString("service_name");
                String feedback_content = rs.getString("feedback_content");
                Date feedback_date = rs.getDate("feedback_date");
                Feedback f = new Feedback(feedback_id, customer_id, service_id,
                        feedback_rate, feedback_date, full_name, service_name, feedback_content);
                list.add(f);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Feedback> searchListFeedbackByCustomerName(String full_name) {
        List<Feedback> list = new ArrayList<>();
        String sql = "select feedback.*, customer.full_name, services.service_name from feedback\n"
                + "join customer on feedback.customer_id = customer.customer_id\n"
                + "join services on feedback.service_id = services.service_id\n"
                + "where full_name like ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, "%" + full_name + "%");
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int feedback_id = rs.getInt("feedback_id");
                int customer_id = rs.getInt("customer_id");
                int service_id = rs.getInt("service_id");
                int feedback_rate = rs.getInt("feedback_rate");
                full_name = rs.getString("full_name");
                String service_name = rs.getString("service_name");
                String feedback_content = rs.getString("feedback_content");
                Date feedback_date = rs.getDate("feedback_date");
                Feedback f = new Feedback(feedback_id, customer_id, service_id,
                        feedback_rate, feedback_date, full_name, service_name, feedback_content);
                list.add(f);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Customer> getCustomerByServiceID(int service_id) {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT DISTINCT c.*, s.service_id\n"
                + "FROM customer c\n"
                + "JOIN transactions t ON c.customer_id = t.customer_id\n"
                + "JOIN services s ON t.service_id = s.service_id\n"
                + "where s.service_id = ?\n"
                + "ORDER BY c.customer_id";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, service_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int customer_id = rs.getInt("customer_id");
                String full_name = rs.getString("full_name");
                String email = rs.getString("email");
                String username = rs.getString("username");
                String phone_number = rs.getString("phone_number");
                String gender = rs.getString("gender");
                String password = rs.getString("password");
                service_id = rs.getInt("service_id");
                String address = rs.getString("address");

                Customer customer = new Customer(customer_id, full_name, email, username, password, phone_number, address, gender, service_id);
                list.add(customer);
            }
        } catch (Exception e) {
        }
        return list;
    }

    // Main method for testing
    public static void main(String[] args) {
        DAO_Admin d = new DAO_Admin();
//        String date = "2000-12-31";
//        Date sqlDate = java.sql.Date.valueOf(date);
//        ServiceProvider s = new ServiceProvider(7, "thinh", "thinh",
//                "thinh", "Electricity", "thinh", "thinh", "thinh", "active");
//        d.insertServiceProvider(s);
        List<Feedback> l = d.getListFeedbackByServiceID(1);
        for (Feedback feedback : l) {
            System.out.println(feedback);
            System.out.println(d.getTotalFeedbackByServiceID(1));
        }
    }
}
