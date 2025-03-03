package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        String sql = "set nocount on; "
                + "delete from news where staff_id= ?;"
                + "delete from request where staff_id = ?;"
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

    // Main method for testing
    public static void main(String[] args) {
        DAO_Admin d = new DAO_Admin();
//        String date ="2000-12-31";
//        Date sqlDate = java.sql.Date.valueOf(date);
        d.deleteBanker(10);
        List<Staff> list = d.getAllBanker(2);
        for (Staff staff : list) {
            System.out.println(staff);
        }
    }
}
