package dal;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Customer;
import model.Insurance;
import model.Insurance_contract;
import model.Insurance_policy;
import model.Insurance_transactions;
import model.Staff;
import static org.apache.tomcat.jni.Buffer.address;

public class DAO extends DBContext {

    public static DAO INSTANCE = new DAO();
    private Connection con;
    private String status = "OK";

    public DAO() {
        try {
            con = new DBContext().getConnection();
        } catch (Exception e) {
            status = "Error at connection" + e.getMessage();
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

    public void register(Customer us) {
        String sql = "INSERT INTO [dbo].[Users]\n"
                + "           ([full_name]\n"
                + "           ,[email]\n"
                + "           ,[phone_number]\n"
                + "           ,[password]\n"
                + "           ,[address]\n"
                + "           ,[gender]\n"
                + "           ,[date_of_birth]\n"
                + "           ,[profile_picture])\n"
                + "     VALUES( ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, us.getFull_name());
            ps.setString(2, us.getEmail());
            ps.setString(3, us.getPhone_number());
            ps.setString(4, us.getPassword());
            ps.setString(5, us.getAddress());
            ps.setString(6, us.getGender());
            ps.setString(8, us.getProfile_picture());
            ps.executeUpdate();
        } catch (SQLException e) {
            status = "Error at register " + e.getMessage();
            e.printStackTrace();
        }

    }

    public boolean existedPhoneNum(String phoneNum) {
        String sql = "SELECT [userId]\n"
                + "      ,[fullName]\n"
                + "      ,[email]\n"
                + "      ,[phoneNumber]\n"
                + "      ,[password]\n"
                + "      ,[address]\n"
                + "      ,[createdAt]\n"
                + "      ,[gender]\n"
                + "      ,[profilePicture]\n"
                + "  FROM [dbo].[Users] WHERE phoneNumber = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, phoneNum);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            status = "Error at existedPhoneNum " + e.getMessage();
        }
        return false;
    }

    public Customer check(String username, String password) {
        String sql = "SELECT [user_id],\n"
                + "      [full_name],\n"
                + "      [email],\n"
                + "      [phone_number],\n"
                + "      [password],\n"
                + "      [address],\n" + "      [created_at],\n"
                + "      [gender],\n"
                + "      [profile_picture],\n"
                + "      [date_of_birth]\n"
                + "  FROM [dbo].[Users] where email = ? and password = ?";

        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, username);
            st.setString(2, password);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new Customer();
                }
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public void change(int Customer_id, String password) {
        String sql = "UPDATE u\n"
                + "                 SET password=?\n"
                + "                 FROM customer c\n"
                + "                 JOIN users u ON c.customer_id = u.user_id\n"
                + "                 WHERE customer_id=?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, password);
            st.setInt(2, Customer_id);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void changeInfor(String full_name, String email, String phone_number, String address, int customer_id) {
        String sql = "UPDATE u\n"
                + "SET full_name=?,email=?,phone_number=?,address=?\n"
                + "FROM customer c\n"
                + "JOIN users u ON c.customer_id = u.user_id\n"
                + "WHERE customer_id=?;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, full_name);
            st.setString(2, email);
            st.setString(3, phone_number);
            st.setString(4, address);
            st.setInt(5, customer_id);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

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
                String created_at = rs.getString("created_at");
                String profile_picture = rs.getString("profile_picture");
                double amount = rs.getDouble("amount");
                double credit_limit = rs.getDouble("credit_limit");
                Date date_of_birth = rs.getDate("date_of_birth");
                Customer acc = new Customer(customer_id, fullname, email, userName, password, phone_number, address, card_type, amount, credit_limit, status, gender, date_of_birth, created_at, profile_picture);
                return acc;
            }
        } catch (Exception e) {
        }
        return null;
    }

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

    public Insurance login_insurance(String username, String password) {
        String sql = "select * from insurance\n"
                + "join insurance_policy on insurance.insurance_id = insurance_policy.insurance_id\n"
                + "where insurance.username = ? and password = ? ";
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
                int policy_id = rs.getInt(10);
                Insurance insurance = new Insurance(insurance_id, role_id, policy_id, username, password, insurance_name, email, phone_number, address, status);
                return insurance;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public List<Insurance> getAllInsurance() {
        List<Insurance> list = new ArrayList<>();
        String sql = "select * from insurance\n"
                + "where status = 'active'";

        try {
            PreparedStatement pre = con.prepareStatement(sql);

            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int insurance_id = rs.getInt("insurance_id");
                int role_id = rs.getInt("role_id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String insurance_name = rs.getString("insurance_name");
                String email = rs.getString("email");
                String phone_number = rs.getString("phone_number");
                String address = rs.getString("address");
                String status = rs.getString("status");
                Insurance insurance = new Insurance(insurance_id, role_id, username, password, insurance_name, email, phone_number, address, status);
                list.add(insurance);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public Insurance getInsuranceByUsername(String username) {
        String sql = "select * from insurance\n"
                + "where username = ? and status = 'active'";

        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, username);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int insurance_id = rs.getInt("insurance_id");
                int role_id = rs.getInt("role_id");
                username = rs.getString("username");
                String password = rs.getString("password");
                String insurance_name = rs.getString("insurance_name");
                String email = rs.getString("email");
                String phone_number = rs.getString("phone_number");
                String address = rs.getString("address");
                String status = rs.getString("status");
                Insurance insurance = new Insurance(insurance_id, role_id, username, password, insurance_name, email, phone_number, address, status);
                return insurance;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public Insurance getInsuranceByID(int insurance_id) {
        String sql = "select * from insurance\n"
                + "where insurance_id = ? and status = 'active'";

        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                insurance_id = rs.getInt("insurance_id");
                int role_id = rs.getInt("role_id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String insurance_name = rs.getString("insurance_name");
                String email = rs.getString("email");
                String phone_number = rs.getString("phone_number");
                String address = rs.getString("address");
                String status = rs.getString("status");
                Insurance insurance = new Insurance(insurance_id, role_id, username, password, insurance_name, email, phone_number, address, status);
                return insurance;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public void insertInsurances(int role_id, String username, String password, String insurance_name, String email, String phone_number, String address, String status) {
        String sql = "INSERT INTO insurance (role_id,username, password, insurance_name, email, phone_number, address, status)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, role_id);
            pre.setString(2, username);
            pre.setString(3, password);
            pre.setString(4, insurance_name);
            pre.setString(5, email);
            pre.setString(6, phone_number);
            pre.setString(7, address);
            pre.setString(8, status);

            // Correct Date Handling:  Parse the String *within* the method
            pre.executeUpdate();
            System.out.println("Add insurance successfully!");
        } catch (Exception e) {
            e.printStackTrace(); // Ghi lại thông báo lỗi để kiểm tra
        }
    }

    public void updateInsurance(String username, String password, String insurance_name, String email, String phone_number, String address, String status) {
        String sql = "update insurance\n"
                + "set username = ?,\n"
                + "password = ?,\n"
                + "insurance_name = ?,\n"
                + "email = ?\n"
                + "phone_number = ?\n"
                + "address = ?\n"
                + "status = ?\n"
                + "where insurance_id = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, username);
            pre.setString(2, password);
            pre.setString(3, insurance_name);
            pre.setString(4, email);
            pre.setString(5, phone_number);
            pre.setString(6, address);
            pre.setString(7, status);
            pre.executeUpdate();
            System.out.println("Update insurance successfully!");
        } catch (Exception e) {
            e.printStackTrace(); // Ghi lại thông báo lỗi để kiểm tra
        }
    }

    public void deleteInsurance(String insurance_id) {
        String sql = "update insurance\n"
                + "set status = 'inactive'\n"
                + "where insurance_id = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, insurance_id);
            pre.executeUpdate();
        } catch (Exception e) {
        }
    }

    public List<Insurance_policy> getPolicyByInsuranceID(int insurance_id) {
        List<Insurance_policy> list = new ArrayList<>();
        String sql = "select * from insurance_policy\n"
                + "where insurance_id = ? ";

        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id); // Đặt giá trị cho tham số ?
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int policy_id = rs.getInt("policy_id");
                insurance_id = rs.getInt("insurance_id");
                String policy_name = rs.getString("policy_name");
                String description = rs.getString("description");
                String status = rs.getString("status");
                double coverage_amount = rs.getDouble("coverage_amount");
                double premium_amount = rs.getDouble("premium_amount");
                Date created_at = rs.getDate("created_at");
                Insurance_policy policy = new Insurance_policy(policy_id, insurance_id, policy_name, description, status, coverage_amount, premium_amount, created_at);
                list.add(policy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Insurance_policy getPolicyByName(String policy_name) {
        String sql = "select * from insurance_policy\n"
                + "where policy_name = ?";

        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, policy_name);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int policy_id = rs.getInt("policy_id");
                int insurance_id = rs.getInt("insurance_id");
                policy_name = rs.getString("policy_name");
                String description = rs.getString("description");
                double coverage_amount = rs.getDouble("coverage_amount");
                double premium_amount = rs.getDouble("premium_amount");
                String status = rs.getString("status");
                Date created_at = rs.getDate("created_at");
                Insurance_policy policy = new Insurance_policy(policy_id, insurance_id, policy_name, description, status, coverage_amount, premium_amount, created_at);
                return policy;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    public Insurance_policy getPolicyById(int policy_id) {
        String sql = "select * from insurance_policy\n"
                + "where policy_id = ?";

        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, policy_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                 policy_id = rs.getInt("policy_id");
                int insurance_id = rs.getInt("insurance_id");
                String policy_name = rs.getString("policy_name");
                String description = rs.getString("description");
                double coverage_amount = rs.getDouble("coverage_amount");
                double premium_amount = rs.getDouble("premium_amount");
                String status = rs.getString("status");
                Date created_at = rs.getDate("created_at");
                Insurance_policy policy = new Insurance_policy(policy_id, insurance_id, policy_name, description, status, coverage_amount, premium_amount, created_at);
                return policy;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public void insertPolicy(int insurance_id, String policy_name, String description, double coverage_amount, double premium_amount, String status) {
        String sql = "INSERT INTO insurance_policy (insurance_id,policy_name, description, coverage_amount, premium_amount, status)"
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            pre.setString(2, policy_name);
            pre.setString(3, description);
            pre.setDouble(4, coverage_amount);
            pre.setDouble(5, premium_amount);
            pre.setString(6, status);

            // Correct Date Handling:  Parse the String *within* the method
            pre.executeUpdate();
            System.out.println("Add insurance policy successfully!");
        } catch (Exception e) {
            e.printStackTrace(); // Ghi lại thông báo lỗi để kiểm tra
        }
    }
    
    public void updatePolicy(int policy_id,String policy_name, String description, double coverage_amount, double premium_amount, String status) {
        String sql = "update insurance_policy\n"
                + "set policy_name = ?,\n"
                + "description = ?,\n"
                + "coverage_amount = ?,\n"
                + "premium_amount = ?,\n"
                + "status = ?\n"
                + "where policy_id = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, policy_name);
            pre.setString(2, description);
            pre.setDouble(3, coverage_amount);
            pre.setDouble(4, premium_amount);
            pre.setString(5, status);
            pre.setInt(6, policy_id);
            pre.executeUpdate();
            System.out.println("Update insurance policy successfully!");
        } catch (Exception e) {
            e.printStackTrace(); // Ghi lại thông báo lỗi để kiểm tra
        }
    }

    public void deletePolicy(int policy_id) {
        String sql = "update insurance_policy\n"
                + "set status = 'inactive'\n"
                + "where policy_id = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, policy_id);
            pre.executeUpdate();
        } catch (Exception e) {
        }
    }

    public List<Insurance_contract> getAllInsuranceContractByPolicyId(int policy_id) {
        List<Insurance_contract> list = new ArrayList<>();
        String sql = "select * from insurance_contract\n"
                + " join insurance_policy on insurance_contract.policy_id = insurance_policy.policy_id\n"
                + " join insurance on insurance.insurance_id = insurance_policy.insurance_id"
                + " where insurance_contract.policy_id = ?";

        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, policy_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int contract_id = rs.getInt("contract_id");
                int customer_id = rs.getInt("customer_id");
                int service_id = rs.getInt("service_id");
                int insurance_id = rs.getInt("insurance_id");
                policy_id = rs.getInt("policy_id");
                String payment_frequency = rs.getString("payment_frequency");
                String status = rs.getString("status");
                Date start_date = rs.getDate("start_date");
                Date end_date = rs.getDate("end_date");
                Date created_at = rs.getDate("created_at");
                Insurance_contract contract = new Insurance_contract(contract_id, customer_id, service_id, policy_id, insurance_id, start_date, end_date, created_at, payment_frequency, status);
                list.add(contract);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<Insurance_contract> getAllInsuranceContractByInsuranceId(int insurance_id) {
        List<Insurance_contract> list = new ArrayList<>();
        String sql = "select * from insurance_contract\n" +
"join insurance_policy on insurance_contract.policy_id = insurance_policy.policy_id\n" +
"join insurance on insurance_policy.insurance_id = insurance.insurance_id\n" +
"where insurance.insurance_id = ?";

        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int contract_id = rs.getInt("contract_id");
                int customer_id = rs.getInt("customer_id");
                int service_id = rs.getInt("service_id");
                 insurance_id = rs.getInt("insurance_id");
                int policy_id = rs.getInt("policy_id");
                String payment_frequency = rs.getString("payment_frequency");
                String status = rs.getString("status");
                Date start_date = rs.getDate("start_date");
                Date end_date = rs.getDate("end_date");
                Date created_at = rs.getDate("created_at");
                Insurance_contract contract = new Insurance_contract(contract_id, customer_id, service_id, policy_id, insurance_id, start_date, end_date, created_at, payment_frequency, status);
                list.add(contract);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    
    
    public Insurance_contract getInsuranceContractById(int contract_id) {
        String sql = "select * from insurance_contract\n"
                + "where contract_id = ?";

        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, contract_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                 contract_id = rs.getInt("contract_id");
                int service_id = rs.getInt("service_id");
                int policy_id = rs.getInt("policy_id");
                String payment_frequency = rs.getString("payment_frequency");
                String status = rs.getString("status");
                Date start_date = rs.getDate("start_date");
                Date end_date = rs.getDate("end_date");
                Date created_at = rs.getDate("created_at");
                Insurance_contract contract = new Insurance_contract(contract_id, contract_id, service_id, policy_id, start_date, end_date, created_at, payment_frequency, status);
                return contract;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public void insertInsuranceContract(int customer_id, int service_id, int policy_id, String start_date, String end_date, String payment_frequency, String status) {
        String sql = "INSERT INTO insurance_contract (customer_id,service_id, policy_id, start_date, end_date, payment_frequency,status)"
                + "VALUES (?, ?, ?, ?, ?, ?,?)";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, customer_id);
            pre.setInt(2, service_id);
            pre.setInt(3, policy_id);
            pre.setString(4, start_date);
            pre.setString(5, end_date);
            pre.setString(6, payment_frequency);
            pre.setString(7, status);

            // Correct Date Handling:  Parse the String *within* the method
            pre.executeUpdate();
            System.out.println("Add insurance contract successfully!");
        } catch (Exception e) {
            e.printStackTrace(); // Ghi lại thông báo lỗi để kiểm tra
        }
    }
    
    public void updateInsuranceContract(int contract_id, int service_id, String payment_frequency, String status, String start_date, String end_date) {
        String sql = "update insurance_contract\n"
                + "set service_id = ?,\n"
                + "payment_frequency = ?,\n"
                + "status = ?,\n"
                + "start_date = ?,\n"
                + "end_date = ?\n"
                + "where contract_id = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, service_id);
            pre.setString(2, payment_frequency);
            pre.setString(3, status);
            pre.setString(4, start_date);
            pre.setString(5, end_date);
            pre.setInt(6, contract_id);
            pre.executeUpdate();
            System.out.println("Update insurance contract successfully!");
        } catch (Exception e) {
            e.printStackTrace(); // Ghi lại thông báo lỗi để kiểm tra
        }
    }

    public void deleteInsuranceContract(int contract_id) {
        String sql = "update insurance_contract\n"
                + "set status = 'cancelled'\n"
                + "where contract_id = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, contract_id);
            pre.executeUpdate();
        } catch (Exception e) {
        }
    }

    public List<Insurance_transactions> getInsuranceTransactionByInsuranceID(int insurance_id) {
        List<Insurance_transactions> list = new ArrayList<>();
        String sql = "select * from insurance_transactions\n" +
"join insurance_contract_detail on insurance_transactions.contract_id = insurance_contract_detail.contract_id\n" +
"where insurance_id = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                insurance_id = rs.getInt("insurance_id");
                int transaction_id = rs.getInt("transaction_id");
                int contract_id = rs.getInt("contract_id");
                int customer_id = rs.getInt("customer_id");
                Date transaction_date = rs.getDate("transaction_date");
                double amount = rs.getDouble("amount");
                String transaction_type = rs.getString("transaction_type");
                String notes = rs.getString("notes");
                Insurance_transactions transactions = new Insurance_transactions(transaction_id, contract_id, customer_id, insurance_id, transaction_date, amount, transaction_type, notes);
                list.add(transactions);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public void insertInsuranceTransaction(int contract_id, int customer_id, double amount, String transaction_type, String notes) {
        String sql = "INSERT INTO insurance_transactions (contract_id,customer_id, amount, transaction_type, notes)"
                + "VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, contract_id);
            pre.setInt(2, customer_id);
            pre.setDouble(3, amount);
            pre.setString(4, transaction_type);
            pre.setString(5, notes);
            // Correct Date Handling:  Parse the String *within* the method
            pre.executeUpdate();
            System.out.println("Add insurance transaction successfully!");
        } catch (Exception e) {
            e.printStackTrace(); // Ghi lại thông báo lỗi để kiểm tra
        }
    }

    

    public List<Customer> getInsuranceCustomerByInsuranceId(int insurance_id) {
        List<Customer> list = new ArrayList<>();
        String sql = "select * from customer\n"
                + "                join insurance_contract on customer.customer_id = insurance_contract.customer_id\n"
                + "				join insurance_contract_detail on insurance_contract.contract_id = insurance_contract_detail.contract_id\n"
                + "                where insurance_id = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                insurance_id = rs.getInt("insurance_id");
                int customer_id = rs.getInt("customer_id");
                String full_name = rs.getString("full_name");
                String email = rs.getString("email");
                String username = rs.getString("username");
                String phone_number = rs.getString("phone_number");
                String address = rs.getString("address");
                String gender = rs.getString("gender");

                Customer customer = new Customer(customer_id, insurance_id, full_name, email, username, phone_number, address, gender);
                list.add(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

   

    public static void main(String[] args) {
        DAO d = new DAO();
        int contract = 8;
        int service = 2;
        String payment = "quarterly";
        String status = "active";
        String stard = "2023-02-01";
        String end = "2024-02-01";
        d.updateInsuranceContract(contract, service, payment, status, stard, end);
        List<Insurance_contract> list = d.getAllInsuranceContractByPolicyId(2);
        for (Insurance_contract p : list) {
            System.out.println(p);
        }

    }
}
