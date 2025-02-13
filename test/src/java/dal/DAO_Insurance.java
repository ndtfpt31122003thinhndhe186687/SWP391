package dal;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.Customer;
import model.Insurance;
import model.Insurance_contract;
import model.Insurance_policy;
import model.Insurance_term;
import model.Insurance_transactions;


public class DAO_Insurance extends DBContext {

    public static DAO_Insurance INSTANCE = new DAO_Insurance();
    private Connection con;
    private String status = "OK";

    public DAO_Insurance() {
        try {
            con = new DBContext().getConnection();
        } catch (Exception e) {
            status = "Error at connection" + e.getMessage();
        }
    }

    public static DAO_Insurance getINSTANCE() {
        return INSTANCE;
    }

    public static void setINSTANCE(DAO_Insurance INSTANCE) {
        DAO_Insurance.INSTANCE = INSTANCE;
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

    public List<Insurance_policy> getPolicyByInsuranceIDAndActive(int insurance_id, String status) {
        List<Insurance_policy> list = new ArrayList<>();
        String sql = "select * from insurance_policy\n"
                + "where insurance_id = ? and status = 'active' ";

        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id); // Đặt giá trị cho tham số ?
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int policy_id = rs.getInt("policy_id");
                insurance_id = rs.getInt("insurance_id");
                String policy_name = rs.getString("policy_name");
                String description = rs.getString("description");
                status = rs.getString("status");
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

    public List<Insurance_policy> getPolicyByInsuranceIDAndStatus(int insurance_id, String status) {
        List<Insurance_policy> list = new ArrayList<>();
        String sql = "select * from insurance_policy\n"
                + "where insurance_id = ? and status = ?";

        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            pre.setString(2, status);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int policy_id = rs.getInt("policy_id");
                insurance_id = rs.getInt("insurance_id");
                String policy_name = rs.getString("policy_name");
                String description = rs.getString("description");
                status = rs.getString("status");
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

    public void insertPolicy(Insurance_policy p) {
        String sql = "INSERT INTO insurance_policy (insurance_id,policy_name, description, coverage_amount, premium_amount, status)"
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, p.getInsurance_id());
            pre.setString(2, p.getPolicy_name());
            pre.setString(3, p.getDescription());
            pre.setDouble(4, p.getCoverage_amount());
            pre.setDouble(5, p.getPremium_amount());
            pre.setString(6, p.getStatus());

            // Correct Date Handling:  Parse the String *within* the method
            pre.executeUpdate();
            System.out.println("Add insurance policy successfully!");
        } catch (Exception e) {
            e.printStackTrace(); // Ghi lại thông báo lỗi để kiểm tra
        }
    }

    public void updatePolicy(Insurance_policy p) {
        String sql = "update insurance_policy\n"
                + "set policy_name = ?,\n"
                + "description = ?,\n"
                + "coverage_amount = ?,\n"
                + "premium_amount = ?,\n"
                + "status = ?\n"
                + "where policy_id = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, p.getPolicy_name());
            pre.setString(2, p.getDescription());
            pre.setDouble(3, p.getCoverage_amount());
            pre.setDouble(4, p.getPremium_amount());
            pre.setString(5, p.getStatus());
            pre.setInt(6, p.getPolicy_id());
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

    public List<Insurance_policy> searchInsurancePolicyByPolicyName(String policy_name, int insurance_id) {
        List<Insurance_policy> list = new ArrayList<>();
        String sql = "select * from insurance_policy \n"
                + "where policy_name like ? and insurance_id = ?";

        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, "%" + policy_name + "%"); // Đặt giá trị cho tham số ?
            pre.setInt(2, insurance_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int policy_id = rs.getInt("policy_id");
                insurance_id = rs.getInt("insurance_id");
                policy_name = rs.getString("policy_name");
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

    public List<Insurance_policy> sortInsurancePolicyByCoverageAmountAndStatus(int insurance_id, String status) {
        List<Insurance_policy> list = new ArrayList<>();
        String sql = "select * from insurance_policy\n"
                + "where insurance_id = ? and status = ?\n"
                + "order by coverage_amount desc";

        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id); // Đặt giá trị cho tham số ?
            pre.setString(2, status);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int policy_id = rs.getInt("policy_id");
                insurance_id = rs.getInt("insurance_id");
                String policy_name = rs.getString("policy_name");
                String description = rs.getString("description");
                status = rs.getString("status");
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

    public List<Insurance_policy> sortInsurancePolicyByCoverageAmount(int insurance_id) {
        List<Insurance_policy> list = new ArrayList<>();
        String sql = "select * from insurance_policy\n"
                + "where insurance_id = ?\n"
                + "order by coverage_amount desc";

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

    public List<Insurance_policy> sortInsurancePolicyByCreatedAt(int insurance_id) {
        List<Insurance_policy> list = new ArrayList<>();
        String sql = "select * from insurance_policy\n"
                + "where insurance_id = ? \n"
                + "order by created_at desc";

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

    public List<Insurance_policy> sortInsurancePolicyByCreatedAtAndStatus(int insurance_id, String status) {
        List<Insurance_policy> list = new ArrayList<>();
        String sql = "select * from insurance_policy\n"
                + "where insurance_id = ? and status = ?\n"
                + "order by created_at desc";

        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id); // Đặt giá trị cho tham số ?
            pre.setString(2, status);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int policy_id = rs.getInt("policy_id");
                insurance_id = rs.getInt("insurance_id");
                String policy_name = rs.getString("policy_name");
                String description = rs.getString("description");
                status = rs.getString("status");
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

    public List<Insurance_contract> getAllInsuranceContractByInsuranceId(int insurance_id) {
        List<Insurance_contract> list = new ArrayList<>();
        String sql = "select * from insurance_contract\n"
                + "join insurance_policy on insurance_contract.policy_id = insurance_policy.policy_id\n"
                + "join insurance on insurance.insurance_id = insurance_policy.insurance_id\n"
                + "join customer on insurance_contract.customer_id = customer.customer_id\n"
                + "join services on insurance_contract.service_id = services.service_id\n"
                + "where insurance.insurance_id = ?";

        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int contract_id = rs.getInt("contract_id");
                String full_name = rs.getString("full_name");
                String service_name = rs.getString("service_name");
                insurance_id = rs.getInt("insurance_id");
                String policy_name = rs.getString("policy_name");
                String payment_frequency = rs.getString("payment_frequency");
                String status = rs.getString("status");
                Date start_date = rs.getDate("start_date");
                Date end_date = rs.getDate("end_date");
                Date created_at = rs.getDate("created_at");
                Insurance_contract contract = new Insurance_contract(insurance_id, contract_id, start_date, end_date, created_at, payment_frequency, status, full_name, service_name, policy_name);
                list.add(contract);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Insurance_contract> getAllInsuranceContractByInsuranceIdAndStatus(int insurance_id, String status) {
        List<Insurance_contract> list = new ArrayList<>();
        String sql = "select * from insurance_contract\n"
                + "join insurance_policy on insurance_contract.policy_id = insurance_policy.policy_id\n"
                + "join insurance on insurance.insurance_id = insurance_policy.insurance_id\n"
                + "join customer on insurance_contract.customer_id = customer.customer_id\n"
                + "join services on insurance_contract.service_id = services.service_id\n"
                + "where insurance.insurance_id = ? and insurance_contract.status = ?";

        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            pre.setString(2, status);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int contract_id = rs.getInt("contract_id");
                String full_name = rs.getString("full_name");
                String service_name = rs.getString("service_name");
                insurance_id = rs.getInt("insurance_id");
                String policy_name = rs.getString("policy_name");
                String payment_frequency = rs.getString("payment_frequency");
                status = rs.getString("status");
                Date start_date = rs.getDate("start_date");
                Date end_date = rs.getDate("end_date");
                Date created_at = rs.getDate("created_at");
                Insurance_contract contract = new Insurance_contract(insurance_id, contract_id, start_date, end_date, created_at, payment_frequency, status, full_name, service_name, policy_name);
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

    public void updateInsuranceContract(Insurance_contract c) {
        String sql = "update insurance_contract\n"
                + "set status = ?\n"
                + "where contract_id = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, c.getStatus());
            pre.setInt(2, c.contract_id);
            pre.executeUpdate();
            System.out.println("Update insurance contract successfully!");
        } catch (Exception e) {
            e.printStackTrace(); // Ghi lại thông báo lỗi để kiểm tra
        }
    }

    public List<Insurance_contract> searchInsuranceContractByCustomerName(int insurance_id, String full_name) {
        List<Insurance_contract> list = new ArrayList<>();
        String sql = "select * from insurance_contract\n"
                + "join insurance_policy on insurance_contract.policy_id = insurance_policy.policy_id\n"
                + "join insurance on insurance.insurance_id = insurance_policy.insurance_id\n"
                + "join customer on insurance_contract.customer_id = customer.customer_id\n"
                + "join services on insurance_contract.service_id = services.service_id\n"
                + "where insurance.insurance_id = ? and customer.full_name like ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            pre.setString(2, "%" + full_name + "%");
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int contract_id = rs.getInt("contract_id");
                full_name = rs.getString("full_name");
                String service_name = rs.getString("service_name");
                insurance_id = rs.getInt("insurance_id");
                String policy_name = rs.getString("policy_name");
                String payment_frequency = rs.getString("payment_frequency");
                String status = rs.getString("status");
                Date start_date = rs.getDate("start_date");
                Date end_date = rs.getDate("end_date");
                Date created_at = rs.getDate("created_at");
                Insurance_contract contract = new Insurance_contract(insurance_id, contract_id, start_date, end_date, created_at, payment_frequency, status, full_name, service_name, policy_name);
                list.add(contract);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Insurance_contract> sortInsuranceContractByStartDateAndStatus(int insurance_id, String status) {
        List<Insurance_contract> list = new ArrayList<>();
        String sql = "select * from insurance_contract\n"
                + "join insurance_policy on insurance_contract.policy_id = insurance_policy.policy_id\n"
                + "join insurance on insurance.insurance_id = insurance_policy.insurance_id\n"
                + "join customer on insurance_contract.customer_id = customer.customer_id\n"
                + "join services on insurance_contract.service_id = services.service_id\n"
                + "where insurance.insurance_id = ? and insurance_contract.status = ?\n"
                + "order by insurance_contract.start_date desc";

        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            pre.setString(2, status);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int contract_id = rs.getInt("contract_id");
                String full_name = rs.getString("full_name");
                String service_name = rs.getString("service_name");
                insurance_id = rs.getInt("insurance_id");
                String policy_name = rs.getString("policy_name");
                String payment_frequency = rs.getString("payment_frequency");
                status = rs.getString("status");
                Date start_date = rs.getDate("start_date");
                Date end_date = rs.getDate("end_date");
                Date created_at = rs.getDate("created_at");
                Insurance_contract contract = new Insurance_contract(insurance_id, contract_id, start_date, end_date, created_at, payment_frequency, status, full_name, service_name, policy_name);
                list.add(contract);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Insurance_contract> sortInsuranceContractByStartDate(int insurance_id) {
        List<Insurance_contract> list = new ArrayList<>();
        String sql = "select * from insurance_contract\n"
                + "join insurance_policy on insurance_contract.policy_id = insurance_policy.policy_id\n"
                + "join insurance on insurance.insurance_id = insurance_policy.insurance_id\n"
                + "join customer on insurance_contract.customer_id = customer.customer_id\n"
                + "join services on insurance_contract.service_id = services.service_id\n"
                + "where insurance.insurance_id = ?\n"
                + "order by insurance_contract.start_date desc";

        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int contract_id = rs.getInt("contract_id");
                String full_name = rs.getString("full_name");
                String service_name = rs.getString("service_name");
                insurance_id = rs.getInt("insurance_id");
                String policy_name = rs.getString("policy_name");
                String payment_frequency = rs.getString("payment_frequency");
                status = rs.getString("status");
                Date start_date = rs.getDate("start_date");
                Date end_date = rs.getDate("end_date");
                Date created_at = rs.getDate("created_at");
                Insurance_contract contract = new Insurance_contract(insurance_id, contract_id, start_date, end_date, created_at, payment_frequency, status, full_name, service_name, policy_name);
                list.add(contract);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Insurance_contract> sortInsuranceContractByCreatedAtAndStatus(int insurance_id, String status) {
        List<Insurance_contract> list = new ArrayList<>();
        String sql = "select * from insurance_contract\n"
                + "join insurance_policy on insurance_contract.policy_id = insurance_policy.policy_id\n"
                + "join insurance on insurance.insurance_id = insurance_policy.insurance_id\n"
                + "join customer on insurance_contract.customer_id = customer.customer_id\n"
                + "join services on insurance_contract.service_id = services.service_id\n"
                + "where insurance.insurance_id = ? and insurance_contract.status = ?\n"
                + "order by insurance_contract.created_at desc";

        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            pre.setString(2, status);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int contract_id = rs.getInt("contract_id");
                String full_name = rs.getString("full_name");
                String service_name = rs.getString("service_name");
                insurance_id = rs.getInt("insurance_id");
                String policy_name = rs.getString("policy_name");
                String payment_frequency = rs.getString("payment_frequency");
                status = rs.getString("status");
                Date start_date = rs.getDate("start_date");
                Date end_date = rs.getDate("end_date");
                Date created_at = rs.getDate("created_at");
                Insurance_contract contract = new Insurance_contract(insurance_id, contract_id, start_date, end_date, created_at, payment_frequency, status, full_name, service_name, policy_name);
                list.add(contract);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Insurance_contract> sortInsuranceContractByCreatedAt(int insurance_id) {
        List<Insurance_contract> list = new ArrayList<>();
        String sql = "select * from insurance_contract\n"
                + "join insurance_policy on insurance_contract.policy_id = insurance_policy.policy_id\n"
                + "join insurance on insurance.insurance_id = insurance_policy.insurance_id\n"
                + "join customer on insurance_contract.customer_id = customer.customer_id\n"
                + "join services on insurance_contract.service_id = services.service_id\n"
                + "where insurance.insurance_id = ?\n"
                + "order by insurance_contract.created_at desc";

        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int contract_id = rs.getInt("contract_id");
                String full_name = rs.getString("full_name");
                String service_name = rs.getString("service_name");
                insurance_id = rs.getInt("insurance_id");
                String policy_name = rs.getString("policy_name");
                String payment_frequency = rs.getString("payment_frequency");
                status = rs.getString("status");
                Date start_date = rs.getDate("start_date");
                Date end_date = rs.getDate("end_date");
                Date created_at = rs.getDate("created_at");
                Insurance_contract contract = new Insurance_contract(insurance_id, contract_id, start_date, end_date, created_at, payment_frequency, status, full_name, service_name, policy_name);
                list.add(contract);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Insurance_transactions> getInsuranceTransactionByInsuranceID(int insurance_id) {
        List<Insurance_transactions> list = new ArrayList<>();
        String sql = "select * from insurance_transactions\n"
                + "join insurance_contract on insurance_transactions.contract_id = insurance_contract.contract_id\n"
                + "join insurance_policy on insurance_contract.policy_id = insurance_policy.policy_id\n"
                + "join customer on insurance_transactions.customer_id = customer.customer_id\n"
                + "where insurance_id = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                insurance_id = rs.getInt("insurance_id");
                int transaction_id = rs.getInt("transaction_id");
                int contract_id = rs.getInt("contract_id");
                String full_name = rs.getString("full_name");
                Date transaction_date = rs.getDate("transaction_date");
                double amount = rs.getDouble("amount");
                String transaction_type = rs.getString("transaction_type");
                String notes = rs.getString("notes");
                Insurance_transactions transactions = new Insurance_transactions(transaction_id, contract_id, 
                        insurance_id, transaction_date, amount, transaction_type, notes, full_name);
                list.add(transactions);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Insurance_transactions> getInsuranceTransactionByInsuranceIDAndTransactionType(int insurance_id, String transaction_type) {
        List<Insurance_transactions> list = new ArrayList<>();
        String sql = "select * from insurance_transactions\n"
                + "join insurance_contract on insurance_transactions.contract_id = insurance_contract.contract_id\n"
                + "join insurance_policy on insurance_contract.policy_id = insurance_policy.policy_id\n"
                + "join customer on insurance_transactions.customer_id = customer.customer_id\n"
                + "where insurance_id = ? and insurance_transactions.transaction_type = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            pre.setString(2, transaction_type);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                insurance_id = rs.getInt("insurance_id");
                int transaction_id = rs.getInt("transaction_id");
                int contract_id = rs.getInt("contract_id");
                String full_name = rs.getString("full_name");
                Date transaction_date = rs.getDate("transaction_date");
                double amount = rs.getDouble("amount");
                transaction_type = rs.getString("transaction_type");
                String notes = rs.getString("notes");
                Insurance_transactions transactions = new Insurance_transactions(transaction_id, contract_id, insurance_id, transaction_date, amount, transaction_type, notes, full_name);
                list.add(transactions);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Insurance_transactions> searchInsuranceTransactionByCustomerName(int insurance_id, String full_name) {
        List<Insurance_transactions> list = new ArrayList<>();
        String sql = "select * from insurance_transactions\n"
                + "join insurance_contract on insurance_transactions.contract_id = insurance_contract.contract_id\n"
                + "join insurance_policy on insurance_contract.policy_id = insurance_policy.policy_id\n"
                + "join customer on insurance_transactions.customer_id = customer.customer_id\n"
                + "where insurance_id = ? and customer.full_name like ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            pre.setString(2, "%" + full_name + "%");
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                insurance_id = rs.getInt("insurance_id");
                int transaction_id = rs.getInt("transaction_id");
                int contract_id = rs.getInt("contract_id");
                full_name = rs.getString("full_name");
                Date transaction_date = rs.getDate("transaction_date");
                double amount = rs.getDouble("amount");
                String transaction_type = rs.getString("transaction_type");
                String notes = rs.getString("notes");
                Insurance_transactions transactions = new Insurance_transactions(transaction_id, contract_id, insurance_id, transaction_date, amount, transaction_type, notes, full_name);
                list.add(transactions);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Insurance_transactions> sortInsuranceTransactionByTransactionDate(int insurance_id) {
        List<Insurance_transactions> list = new ArrayList<>();
        String sql = "select * from insurance_transactions\n"
                + "join insurance_contract on insurance_transactions.contract_id = insurance_contract.contract_id\n"
                + "join insurance_policy on insurance_contract.policy_id = insurance_policy.policy_id\n"
                + "join customer on insurance_transactions.customer_id = customer.customer_id\n"
                + "where insurance_id = ?\n"
                + "order by insurance_transactions.transaction_date desc";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                insurance_id = rs.getInt("insurance_id");
                int transaction_id = rs.getInt("transaction_id");
                int contract_id = rs.getInt("contract_id");
                String full_name = rs.getString("full_name");
                Date transaction_date = rs.getDate("transaction_date");
                double amount = rs.getDouble("amount");
                String transaction_type = rs.getString("transaction_type");
                String notes = rs.getString("notes");
                Insurance_transactions transactions = new Insurance_transactions(transaction_id, contract_id, insurance_id, transaction_date, amount, transaction_type, notes, full_name);
                list.add(transactions);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Insurance_transactions> sortInsuranceTransactionByTransactionDateAndTransactionType(int insurance_id, String transaction_type) {
        List<Insurance_transactions> list = new ArrayList<>();
        String sql = "select * from insurance_transactions\n"
                + "join insurance_contract on insurance_transactions.contract_id = insurance_contract.contract_id\n"
                + "join insurance_policy on insurance_contract.policy_id = insurance_policy.policy_id\n"
                + "join customer on insurance_transactions.customer_id = customer.customer_id\n"
                + "where insurance_id = ? and insurance_transactions.transaction_type = ?\n"
                + "order by insurance_transactions.transaction_date desc";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            pre.setString(2, transaction_type);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                insurance_id = rs.getInt("insurance_id");
                int transaction_id = rs.getInt("transaction_id");
                int contract_id = rs.getInt("contract_id");
                String full_name = rs.getString("full_name");
                Date transaction_date = rs.getDate("transaction_date");
                double amount = rs.getDouble("amount");
                transaction_type = rs.getString("transaction_type");
                String notes = rs.getString("notes");
                Insurance_transactions transactions = new Insurance_transactions(transaction_id, contract_id, insurance_id, transaction_date, amount, transaction_type, notes, full_name);
                list.add(transactions);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Insurance_transactions> sortInsuranceTransactionByAmount(int insurance_id) {
        List<Insurance_transactions> list = new ArrayList<>();
        String sql = "select * from insurance_transactions\n"
                + "join insurance_contract on insurance_transactions.contract_id = insurance_contract.contract_id\n"
                + "join insurance_policy on insurance_contract.policy_id = insurance_policy.policy_id\n"
                + "join customer on insurance_transactions.customer_id = customer.customer_id\n"
                + "where insurance_id = ?\n"
                + "order by insurance_transactions.amount desc";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                insurance_id = rs.getInt("insurance_id");
                int transaction_id = rs.getInt("transaction_id");
                int contract_id = rs.getInt("contract_id");
                String full_name = rs.getString("full_name");
                Date transaction_date = rs.getDate("transaction_date");
                double amount = rs.getDouble("amount");
                String transaction_type = rs.getString("transaction_type");
                String notes = rs.getString("notes");
                Insurance_transactions transactions = new Insurance_transactions(transaction_id, contract_id, insurance_id, transaction_date, amount, transaction_type, notes, full_name);
                list.add(transactions);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Insurance_transactions> sortInsuranceTransactionByAmountAndTransactionType(int insurance_id, String transaction_type) {
        List<Insurance_transactions> list = new ArrayList<>();
        String sql = "select * from insurance_transactions\n"
                + "join insurance_contract on insurance_transactions.contract_id = insurance_contract.contract_id\n"
                + "join insurance_policy on insurance_contract.policy_id = insurance_policy.policy_id\n"
                + "join customer on insurance_transactions.customer_id = customer.customer_id\n"
                + "where insurance_id = ? and insurance_transactions.transaction_type = ?\n"
                + "order by insurance_transactions.amount desc";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            pre.setString(2, transaction_type);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                insurance_id = rs.getInt("insurance_id");
                int transaction_id = rs.getInt("transaction_id");
                int contract_id = rs.getInt("contract_id");
                String full_name = rs.getString("full_name");
                Date transaction_date = rs.getDate("transaction_date");
                double amount = rs.getDouble("amount");
                transaction_type = rs.getString("transaction_type");
                String notes = rs.getString("notes");
                Insurance_transactions transactions = new Insurance_transactions(transaction_id, contract_id, insurance_id, transaction_date, amount, transaction_type, notes, full_name);
                list.add(transactions);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Customer> getInsuranceCustomerByInsuranceId(int insurance_id) {
        List<Customer> list = new ArrayList<>();
        String sql = "select * from customer\n"
                + "join insurance_contract on customer.customer_id = insurance_contract.customer_id\n"
                + "join insurance_contract_detail on insurance_contract.contract_id = insurance_contract_detail.contract_id\n"
                + "where insurance_id = ?";
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

                Customer customer = new Customer(full_name, email, username, phone_number, address, gender, customer_id, insurance_id);
                list.add(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Customer> searchInsuranceCustomerByCustomerName(int insurance_id, String full_name) {
        List<Customer> list = new ArrayList<>();
        String sql = "select * from customer\n"
                + "                join insurance_contract on customer.customer_id = insurance_contract.customer_id\n"
                + "				join insurance_contract_detail on insurance_contract.contract_id = insurance_contract_detail.contract_id\n"
                + "                where insurance_id = ? and customer.full_name like ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            pre.setString(2, "%" + full_name + "%");
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                insurance_id = rs.getInt("insurance_id");
                int customer_id = rs.getInt("customer_id");
                full_name = rs.getString("full_name");
                String email = rs.getString("email");
                String username = rs.getString("username");
                String phone_number = rs.getString("phone_number");
                String address = rs.getString("address");
                String gender = rs.getString("gender");

                Customer customer = new Customer(full_name, email, username, phone_number, address, gender, customer_id, insurance_id);
                list.add(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Customer> filterInsuranceCustomerByGender(int insurance_id, String gender) {
        List<Customer> list = new ArrayList<>();
        String sql = "select * from customer\n"
                + "join insurance_contract on customer.customer_id = insurance_contract.customer_id\n"
                + "join insurance_contract_detail on insurance_contract.contract_id = insurance_contract_detail.contract_id\n"
                + "where insurance_id = ? and gender = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            pre.setString(2, gender);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                insurance_id = rs.getInt("insurance_id");
                int customer_id = rs.getInt("customer_id");
                String full_name = rs.getString("full_name");
                String email = rs.getString("email");
                String username = rs.getString("username");
                String phone_number = rs.getString("phone_number");
                String address = rs.getString("address");
                gender = rs.getString("gender");

                Customer customer = new Customer(full_name, email, username, phone_number, address, gender, customer_id, insurance_id);
                list.add(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Insurance_term> getInsuranceTermByInsuranceID(int insurance_id) {
        List<Insurance_term> list = new ArrayList<>();
        String sql = "select * from insurance_terms\n"
                + "join insurance_policy on insurance_terms.policy_id = insurance_policy.policy_id\n"
                + "where insurance_terms.insurance_id = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int term_id = rs.getInt("term_id");
                insurance_id = rs.getInt("insurance_id");
                int policy_id = rs.getInt("policy_id");
                String term_name = rs.getString("term_name");
                String term_description = rs.getString("term_description");
                String status = rs.getString("status");
                String policy_name = rs.getString("policy_name");
                Date start_date = rs.getDate("start_date");
                Date end_date = rs.getDate("end_date");
                Date created_at = rs.getDate("created_at");
                Insurance_term term = new Insurance_term(term_id, insurance_id, policy_id, term_name, term_description, status, policy_name, start_date, end_date, created_at);
                list.add(term);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Insurance_term getInsuranceTermByName(String term_name) {
        String sql = "select * from insurance_terms\n"
                + "where term_name = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, term_name);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int term_id = rs.getInt("term_id");
                int insurance_id = rs.getInt("insurance_id");
                term_name = rs.getString("term_name");
                String term_description = rs.getString("term_description");
                String status = rs.getString("status");
                Date start_date = rs.getDate("start_date");
                Date end_date = rs.getDate("end_date");
                Date created_at = rs.getDate("created_at");
                Insurance_term term = new Insurance_term(insurance_id, term_id, 
                        term_name, term_description, status, start_date, end_date);
                return term;
            }
        } catch (Exception e) {
        }
        return null;
    }
    
    public Insurance_term getInsuranceTermByID(int term_id) {
        String sql = "select * from insurance_terms\n"
                + "where term_id = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, term_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                 term_id = rs.getInt("term_id");
                 int policy_id = rs.getInt("policy_id");
                int insurance_id = rs.getInt("insurance_id");
                String term_name = rs.getString("term_name");
                String term_description = rs.getString("term_description");
                String status = rs.getString("status");
                Date start_date = rs.getDate("start_date");
                Date end_date = rs.getDate("end_date");
                Date created_at = rs.getDate("created_at");
                Insurance_term term = new Insurance_term( term_id, insurance_id, policy_id, term_name, term_description, status, term_name, start_date, end_date, created_at);
                return term;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public void insertInsuranceTerm(Insurance_term t) {
        String sql = "INSERT INTO insurance_terms (insurance_id, policy_id, term_name, term_description, start_date, end_date, status)\n"
                + "VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, t.getInsurance_id());
            pre.setInt(2, t.getPolicy_id());
            pre.setString(3, t.getTerm_name());
            pre.setString(4, t.getTerm_description());
            pre.setDate(5, new java.sql.Date(t.getStart_date().getTime()));
            pre.setDate(6, new java.sql.Date(t.getEnd_date().getTime()));
            pre.setString(7, t.getStatus());
         
            pre.executeUpdate();
            System.out.println("Add insurance term successfully!");
        } catch (Exception e) {
            e.printStackTrace(); // Ghi lại thông báo lỗi để kiểm tra
        }
    }

    public void deleteInsuranceTerm(int term_id) {
        String sql = "update insurance_terms\n"
                + "set status = 'inactive'\n"
                + "where term_id = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, term_id);
            pre.executeUpdate();
            System.out.println("Delete insurance term succesfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateInsuranceTerm(Insurance_term t) {
        String sql = "update insurance_terms\n"
                + "set term_name = ?,\n"
                + "term_description = ?,\n"
                + "policy_id = ?,\n"
                + "status = ?,\n"
                + "start_date = ?,\n"
                + "end_date = ?,\n"
                + "insurance_id = ?\n"
                + "where term_id = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, t.getTerm_name());
            pre.setString(2, t.getTerm_description());
            pre.setInt(3, t.getPolicy_id());
            pre.setString(4, t.getStatus());
            pre.setDate(5, new java.sql.Date(t.getStart_date().getTime()));
            pre.setDate(6, new java.sql.Date(t.getEnd_date().getTime()));
            pre.setInt(7, t.getInsurance_id());
            pre.setInt(8, t.getTerm_id());
            pre.executeUpdate();
            System.out.println("Update term successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DAO_Insurance d = new DAO_Insurance();
        List<Insurance_policy> listP = d.getPolicyByInsuranceIDAndActive(2, "active");
        Insurance_term t = d.getInsuranceTermByID(4);
        System.out.println(t);
        for (Insurance_policy insurance_policy : listP) {
            System.out.println(insurance_policy);
        }
//        int insurance_id = 2;
//        int policy_id = 2;
//        int term_id = 11;
//        String term_des = "Pre-existing conditions are not covered.";
//        Date start_date = Date.valueOf("2026-01-01");
//        Date end_date = Date.valueOf("2030-01-01");
//        String status = "active";
//String term_name = "Beneficiary";
//        Insurance_term t = new Insurance_term(term_id, insurance_id, policy_id, term_name, term_des, status, start_date, end_date);
//        d.updateInsuranceTerm(t);
//        for (Insurance_term p : list) {
//            System.out.println(p);
//        }

    }
}
