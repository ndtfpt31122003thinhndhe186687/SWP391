package dal;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.Customer;
import model.Insurance;
import model.Insurance_contract;
import model.Insurance_contract_detail;
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

    //INSURANCE
    public List<Insurance> getInsuranceByStatus() {
        List<Insurance> list = new ArrayList<>();
        String sql = "select * from insurance\n"
                + "where insurance.status = 'active'";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int insurance_id = rs.getInt("insurance_id");
                String insurance_name = rs.getString("insurance_name");
                String email = rs.getString("email");
                String phone_number = rs.getString("phone_number");
                String address = rs.getString("address");
                String status = rs.getString("status");
                Insurance in = new Insurance(insurance_id, insurance_name, email, phone_number, address, status);
                list.add(in);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Insurance getInsuranceByID(int insurance_id) {
        List<Insurance> list = new ArrayList<>();
        String sql = "select * from insurance\n"
                + "where insurance.status = 'active' and insurance_id=?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                insurance_id = rs.getInt("insurance_id");
                String insurance_name = rs.getString("insurance_name");
                String email = rs.getString("email");
                String phone_number = rs.getString("phone_number");
                String address = rs.getString("address");
                String status = rs.getString("status");
                Insurance in = new Insurance(insurance_id, insurance_name, email, phone_number, address, status);
                return in;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //INSURANCE POLICY 
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
                String image = rs.getString("image");
                Insurance_policy policy = new Insurance_policy(policy_id, insurance_id, policy_name, description, status, coverage_amount, premium_amount, created_at, image);
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
                String image = rs.getString("image");
                Insurance_policy policy = new Insurance_policy(policy_id, insurance_id, policy_name, description, status, coverage_amount, premium_amount, created_at, image);
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
                String image = rs.getString("image");
                Insurance_policy policy = new Insurance_policy(policy_id, insurance_id, policy_name, description, status, coverage_amount, premium_amount, created_at, image);
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
                String image = rs.getString("image");
                Insurance_policy policy = new Insurance_policy(policy_id, insurance_id, policy_name, description, status, coverage_amount, premium_amount, created_at, image);
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
                String image = rs.getString("image");
                Insurance_policy policy = new Insurance_policy(policy_id, insurance_id, policy_name, description, status, coverage_amount, premium_amount, created_at, image);
                return policy;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public void insertPolicy(Insurance_policy p) {
        String sql = "INSERT INTO insurance_policy (insurance_id,policy_name, description, coverage_amount, premium_amount, status,image)"
                + "VALUES (?, ?, ?, ?, ?, ?,?)";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, p.getInsurance_id());
            pre.setString(2, p.getPolicy_name());
            pre.setString(3, p.getDescription());
            pre.setDouble(4, p.getCoverage_amount());
            pre.setDouble(5, p.getPremium_amount());
            pre.setString(6, p.getStatus());
            pre.setString(7, p.getImage());

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
                + "status = ?,\n"
                + "image = ?\n"
                + "where policy_id = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, p.getPolicy_name());
            pre.setString(2, p.getDescription());
            pre.setDouble(3, p.getCoverage_amount());
            pre.setDouble(4, p.getPremium_amount());
            pre.setString(5, p.getStatus());
            pre.setString(6, p.getImage());
            pre.setInt(7, p.getPolicy_id());

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
                String image = rs.getString("image");
                Insurance_policy policy = new Insurance_policy(policy_id, insurance_id, policy_name, description, status, coverage_amount, premium_amount, created_at, image);
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
                String image = rs.getString("image");
                Insurance_policy policy = new Insurance_policy(policy_id, insurance_id, policy_name, description, status, coverage_amount, premium_amount, created_at, image);
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
                String image = rs.getString("image");
                Insurance_policy policy = new Insurance_policy(policy_id, insurance_id, policy_name, description, status, coverage_amount, premium_amount, created_at, image);
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
                String image = rs.getString("image");
                Insurance_policy policy = new Insurance_policy(policy_id, insurance_id, policy_name, description, status, coverage_amount, premium_amount, created_at, image);
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
                String image = rs.getString("image");
                Insurance_policy policy = new Insurance_policy(policy_id, insurance_id, policy_name, description, status, coverage_amount, premium_amount, created_at, image);
                list.add(policy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getTotalInsurancePolicy(int insurance_id) {
        String sql = "select COUNT(*) from insurance_policy\n"
                + "where insurance_id = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return 0;
    }

    public List<Insurance_policy> paginationInsurancePolicy(int insurance_id, int offset, int next) {
        List<Insurance_policy> list = new ArrayList<>();
        String sql = "select * from insurance_policy\n"
                + "where insurance_id = ?\n"
                + "order by policy_id\n"
                + "offset ? row fetch next ? row only";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            pre.setInt(2, (offset - 1) * next);
            pre.setInt(3, next);
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
                String image = rs.getString("image");
                Insurance_policy p = new Insurance_policy(policy_id, insurance_id, policy_name,
                        description, status, coverage_amount, premium_amount, created_at, image);
                list.add(p);
            }
        } catch (Exception e) {
        }
        return list;
    }

    // INSURANCE CONTRACT
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
            pre.setInt(2, c.getContract_id());
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

    public int getTotalInsuranceContract(int insurance_id) {
        String sql = "select COUNT(*) from insurance_contract\n"
                + "join insurance_policy on insurance_contract.policy_id = insurance_policy.policy_id\n"
                + "join insurance on insurance.insurance_id = insurance_policy.insurance_id\n"
                + "where insurance.insurance_id = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return 0;
    }

    public List<Insurance_contract> paginationInsuranceContract(int insurance_id, int offset, int next) {
        List<Insurance_contract> list = new ArrayList<>();
        String sql = "select * from insurance_contract\n"
                + "join insurance_policy on insurance_contract.policy_id = insurance_policy.policy_id\n"
                + "join insurance on insurance.insurance_id = insurance_policy.insurance_id\n"
                + "join customer on insurance_contract.customer_id = customer.customer_id\n"
                + "join services on insurance_contract.service_id = services.service_id\n"
                + "where insurance.insurance_id = ?\n"
                + "order by insurance_contract.contract_id\n"
                + "offset ? row fetch next ? row only ";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            pre.setInt(2, (offset - 1) * next);
            pre.setInt(3, next);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int contract_id = rs.getInt("contract_id");
                insurance_id = rs.getInt("insurance_id");
                String payment_frequency = rs.getString("payment_frequency");
                String status = rs.getString("status");
                String full_name = rs.getString("full_name");
                String service_name = rs.getString("service_name");
                String policy_name = rs.getString("policy_name");
                Date start_date = rs.getDate("start_date");
                Date end_date = rs.getDate("end_date");
                Date created_at = rs.getDate("created_at");
                Insurance_contract c = new Insurance_contract(insurance_id, contract_id, start_date,
                        end_date, created_at, payment_frequency, status, full_name, service_name, policy_name);
                list.add(c);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public void insertInsuranceContract(Insurance_contract c) {
        String sql = "INSERT INTO insurance_contract (customer_id, service_id, policy_id, start_date, end_date, payment_frequency, status)\n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, c.getCustomer_id());
            pre.setInt(2, c.getService_id());
            pre.setInt(3, c.getPolicy_id());
            pre.setDate(4, new java.sql.Date(c.getStart_date().getTime()));
            pre.setDate(5, new java.sql.Date(c.getEnd_date().getTime()));
            pre.setString(6, c.getPayment_frequency());
            pre.setString(7, c.getStatus());
            pre.executeUpdate();
            System.out.println("Add successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // INSURANCE TRANSACTION
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

    public int getTotalInsuranceTransaction(int insurance_id) {
        String sql = "select COUNT(*) from insurance_transactions\n"
                + "join insurance_contract on insurance_transactions.contract_id = insurance_contract.contract_id\n"
                + "join insurance_policy on insurance_contract.policy_id = insurance_policy.policy_id\n"
                + "where insurance_id = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return 0;
    }

    public List<Insurance_transactions> paginationInsuranceTransaction(int insurance_id, int offset, int next) {
        List<Insurance_transactions> list = new ArrayList<>();
        String sql = "select * from insurance_transactions\n"
                + "join insurance_contract on insurance_transactions.contract_id = insurance_contract.contract_id\n"
                + "join insurance_policy on insurance_contract.policy_id = insurance_policy.policy_id\n"
                + "join customer on insurance_transactions.customer_id = customer.customer_id\n"
                + "where insurance_id = ?\n"
                + "order by insurance_transactions.transaction_id\n"
                + "offset ? row fetch next ? row only";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            pre.setInt(2, (offset - 1) * next);
            pre.setInt(3, next);
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

    // INSURANCE CUSTOMER
    public List<Customer> getInsuranceCustomerByInsuranceId(int insurance_id) {
        List<Customer> list = new ArrayList<>();
        String sql = "select distinct customer.*, insurance_contract_detail.insurance_id from customer\n"
                + "join insurance_contract on customer.customer_id = insurance_contract.customer_id\n"
                + "join insurance_contract_detail on insurance_contract.contract_id = insurance_contract_detail.contract_id\n"
                + "where insurance_contract_detail.insurance_id = ?";
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

    public int getTotalInsuranceCustomer(int insurance_id) {
        String sql = "select COUNT(*) from customer\n"
                + "join insurance_contract on customer.customer_id = insurance_contract.customer_id\n"
                + "join insurance_contract_detail on insurance_contract.contract_id = insurance_contract_detail.contract_id\n"
                + "where insurance_id = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return 0;
    }

    public List<Customer> paginationInsuranceCustomer(int insurance_id, int offset, int next) {
        List<Customer> list = new ArrayList<>();
        String sql = "select * from customer\n"
                + "join insurance_contract on customer.customer_id = insurance_contract.customer_id\n"
                + "join insurance_contract_detail on insurance_contract.contract_id = insurance_contract_detail.contract_id\n"
                + "where insurance_id = ?\n"
                + "order by customer.customer_id\n"
                + "offset ? row fetch next ? row only";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            pre.setInt(2, (offset - 1) * next);
            pre.setInt(3, next);
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

    // INSURANCE TERM
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
                Insurance_term term = new Insurance_term(term_id, insurance_id, policy_id,
                        term_name, term_description, status, policy_name, start_date, end_date, created_at);
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
                int policy_id = rs.getInt("policy_id");
                term_name = rs.getString("term_name");
                String term_description = rs.getString("term_description");
                String status = rs.getString("status");
                Date start_date = rs.getDate("start_date");
                Date end_date = rs.getDate("end_date");
                Date created_at = rs.getDate("created_at");
                Insurance_term term = new Insurance_term(term_id, insurance_id, policy_id,
                        term_name, term_description, status, term_name, start_date, end_date, created_at);
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
                Insurance_term term = new Insurance_term(term_id, insurance_id, policy_id, term_name, term_description, status, term_name, start_date, end_date, created_at);
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

    public List<Insurance_term> searchInsuranceTermByTermName(int insurance_id, String term_name) {
        List<Insurance_term> list = new ArrayList<>();
        String sql = "select * from insurance_terms\n"
                + "join insurance_policy on insurance_terms.policy_id = insurance_policy.policy_id\n"
                + "where insurance_terms.insurance_id = ? and term_name like ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            pre.setString(2, "%" + term_name + "%");
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int term_id = rs.getInt("term_id");
                insurance_id = rs.getInt("insurance_id");
                int policy_id = rs.getInt("policy_id");
                term_name = rs.getString("term_name");
                String term_description = rs.getString("term_description");
                status = rs.getString("status");
                Date start_date = rs.getDate("start_date");
                Date end_date = rs.getDate("end_date");
                Date created_at = rs.getDate("created_at");
                Insurance_term t = new Insurance_term(term_id, insurance_id, policy_id,
                        term_name, term_description, status, term_name, start_date, end_date, created_at);
                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Insurance_term> getInsuranceTermByStatus(int insurance_id, String status) {
        List<Insurance_term> list = new ArrayList<>();
        String sql = "select * from insurance_terms\n"
                + "join insurance_policy on insurance_terms.policy_id = insurance_policy.policy_id\n"
                + "where insurance_terms.insurance_id = ? and insurance_terms.status = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            pre.setString(2, status);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int term_id = rs.getInt("term_id");
                int policy_id = rs.getInt("policy_id");
                insurance_id = rs.getInt("insurance_id");
                String term_name = rs.getString("term_name");
                String term_description = rs.getString("term_description");
                String policy_name = rs.getString("policy_name");
                status = rs.getString("status");
                Date start_date = rs.getDate("start_date");
                Date end_date = rs.getDate("end_date");
                Date created_at = rs.getDate("created_at");
                Insurance_term t = new Insurance_term(term_id, insurance_id, policy_id,
                        term_name, term_description, status, policy_name, start_date, end_date, created_at);
                list.add(t);

            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Insurance_term> sortInsuranceTermByStartDate(int insurance_id) {
        List<Insurance_term> list = new ArrayList<>();
        String sql = "select * from insurance_terms\n"
                + "join insurance_policy on insurance_terms.policy_id = insurance_policy.policy_id\n"
                + "where insurance_terms.insurance_id = ?\n"
                + "order by start_date";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                insurance_id = rs.getInt("insurance_id");
                int term_id = rs.getInt("term_id");
                int policy_id = rs.getInt("policy_id");
                String term_name = rs.getString("term_name");
                String term_description = rs.getString("term_description");
                String policy_name = rs.getString("policy_name");
                String status = rs.getString("status");
                Date start_date = rs.getDate("start_date");
                Date end_date = rs.getDate("end_date");
                Date created_at = rs.getDate("created_at");
                Insurance_term t = new Insurance_term(term_id, insurance_id, policy_id,
                        term_name, term_description, status, policy_name, start_date, end_date, created_at);
                list.add(t);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Insurance_term> sortInsuranceTermByStartDateAndStatus(int insurance_id, String status) {
        List<Insurance_term> list = new ArrayList<>();
        String sql = "select * from insurance_terms\n"
                + "join insurance_policy on insurance_terms.policy_id = insurance_policy.policy_id\n"
                + "where insurance_terms.insurance_id = ? and insurance_terms.status = ?\n"
                + "order by start_date";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            pre.setString(2, status);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                insurance_id = rs.getInt("insurance_id");
                int term_id = rs.getInt("term_id");
                int policy_id = rs.getInt("policy_id");
                String term_name = rs.getString("term_name");
                String term_description = rs.getString("term_description");
                String policy_name = rs.getString("policy_name");
                status = rs.getString("status");
                Date start_date = rs.getDate("start_date");
                Date end_date = rs.getDate("end_date");
                Date created_at = rs.getDate("created_at");
                Insurance_term t = new Insurance_term(term_id, insurance_id, policy_id,
                        term_name, term_description, status, policy_name, start_date, end_date, created_at);
                list.add(t);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Insurance_term> sortInsuranceTermByCreatedAt(int insurance_id) {
        List<Insurance_term> list = new ArrayList<>();
        String sql = "select * from insurance_terms\n"
                + "join insurance_policy on insurance_terms.policy_id = insurance_policy.policy_id\n"
                + "where insurance_terms.insurance_id = ?\n"
                + "order by insurance_terms.created_at";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                insurance_id = rs.getInt("insurance_id");
                int term_id = rs.getInt("term_id");
                int policy_id = rs.getInt("policy_id");
                String term_name = rs.getString("term_name");
                String term_description = rs.getString("term_description");
                String policy_name = rs.getString("policy_name");
                String status = rs.getString("status");
                Date start_date = rs.getDate("start_date");
                Date end_date = rs.getDate("end_date");
                Date created_at = rs.getDate("created_at");
                Insurance_term t = new Insurance_term(term_id, insurance_id, policy_id,
                        term_name, term_description, status, policy_name, start_date, end_date, created_at);
                list.add(t);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Insurance_term> sortInsuranceTermByCreatedAtAndStatus(int insurance_id, String status) {
        List<Insurance_term> list = new ArrayList<>();
        String sql = "select * from insurance_terms\n"
                + "join insurance_policy on insurance_terms.policy_id = insurance_policy.policy_id\n"
                + "where insurance_terms.insurance_id = ? and insurance_terms.status = ?\n"
                + "order by insurance_terms.created_at";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            pre.setString(2, status);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                insurance_id = rs.getInt("insurance_id");
                int term_id = rs.getInt("term_id");
                int policy_id = rs.getInt("policy_id");
                String term_name = rs.getString("term_name");
                String term_description = rs.getString("term_description");
                String policy_name = rs.getString("policy_name");
                status = rs.getString("status");
                Date start_date = rs.getDate("start_date");
                Date end_date = rs.getDate("end_date");
                Date created_at = rs.getDate("created_at");
                Insurance_term t = new Insurance_term(term_id, insurance_id, policy_id,
                        term_name, term_description, status, policy_name, start_date, end_date, created_at);
                list.add(t);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public int getTotalInsuranceTerm(int insurance_id) {
        String sql = "select COUNT(*) from insurance_terms\n"
                + "join insurance_policy on insurance_terms.policy_id = insurance_policy.policy_id\n"
                + "where insurance_terms.insurance_id = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return 0;
    }

    public List<Insurance_term> paginationInsuranceTerm(int insurance_id, int offset, int next) {
        List<Insurance_term> list = new ArrayList<>();
        String sql = "select * from insurance_terms\n"
                + "join insurance_policy on insurance_terms.policy_id = insurance_policy.policy_id\n"
                + "where insurance_terms.insurance_id = ?\n"
                + "order by insurance_terms.term_id\n"
                + "offset ? row fetch next ? row only";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            pre.setInt(2, (offset - 1) * next);
            pre.setInt(3, next);
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
                Insurance_term term = new Insurance_term(term_id, insurance_id, policy_id,
                        term_name, term_description, status, policy_name, start_date, end_date, created_at);
                list.add(term);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Insurance_term> paginationInsuranceTermAndStartDateAndStatus(int insurance_id, int offset, int next, String status) {
        List<Insurance_term> list = new ArrayList<>();
        String sql = "select * from insurance_terms\n"
                + "join insurance_policy on insurance_terms.policy_id = insurance_policy.policy_id\n"
                + "where insurance_terms.insurance_id = ? and insurance_terms.status = ?\n"
                + "order by insurance_terms.start_date \n"
                + "offset ? row fetch next ? row only";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            pre.setString(2, status);
            pre.setInt(3, (offset - 1) * next);
            pre.setInt(4, next);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int term_id = rs.getInt("term_id");
                insurance_id = rs.getInt("insurance_id");
                int policy_id = rs.getInt("policy_id");
                String term_name = rs.getString("term_name");
                String term_description = rs.getString("term_description");
                status = rs.getString("status");
                String policy_name = rs.getString("policy_name");
                Date start_date = rs.getDate("start_date");
                Date end_date = rs.getDate("end_date");
                Date created_at = rs.getDate("created_at");
                Insurance_term term = new Insurance_term(term_id, insurance_id, policy_id,
                        term_name, term_description, status, policy_name, start_date, end_date, created_at);
                list.add(term);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Insurance_term> paginationInsuranceTermAndCreatedAtAndStatus(int insurance_id, int offset, int next, String status) {
        List<Insurance_term> list = new ArrayList<>();
        String sql = "select * from insurance_terms\n"
                + "join insurance_policy on insurance_terms.policy_id = insurance_policy.policy_id\n"
                + "where insurance_terms.insurance_id = ? and insurance_terms.status = ?\n"
                + "order by insurance_terms.created_at \n"
                + "offset ? row fetch next ? row only";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, insurance_id);
            pre.setString(2, status);
            pre.setInt(3, (offset - 1) * next);
            pre.setInt(4, next);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int term_id = rs.getInt("term_id");
                insurance_id = rs.getInt("insurance_id");
                int policy_id = rs.getInt("policy_id");
                String term_name = rs.getString("term_name");
                String term_description = rs.getString("term_description");
                status = rs.getString("status");
                String policy_name = rs.getString("policy_name");
                Date start_date = rs.getDate("start_date");
                Date end_date = rs.getDate("end_date");
                Date created_at = rs.getDate("created_at");
                Insurance_term term = new Insurance_term(term_id, insurance_id, policy_id,
                        term_name, term_description, status, policy_name, start_date, end_date, created_at);
                list.add(term);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //INSURANCE CONTRACT DETAIL
    public List<Insurance_contract_detail> getInsuranceContractDetailByCustomerIDAndInsuranceID(int customer_id, int insurance_id) {
        List<Insurance_contract_detail> list = new ArrayList<>();
        String sql = "select * from  insurance_contract_detail\n"
                + "join insurance_contract on insurance_contract_detail.contract_id = insurance_contract.contract_id\n"
                + "join insurance_policy on insurance_contract.policy_id = insurance_policy.policy_id\n"
                + "where insurance_contract.customer_id = ? and insurance_contract_detail.insurance_id = ? and insurance_contract.status = 'active'";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, customer_id);
            pre.setInt(2, insurance_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int contract_id = rs.getInt("contract_id");
                insurance_id = rs.getInt("insurance_id");
                customer_id = rs.getInt("customer_id");
                int policy_id = rs.getInt("policy_id");

                String policy_name = rs.getString("policy_name");
                Insurance_contract_detail d = new Insurance_contract_detail(contract_id, insurance_id, customer_id,
                        policy_id, policy_name);
                list.add(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Insurance_contract_detail getInsuranceContractDetailByContractid(int contract_id,int insurance_id) {
        String sql = "select * from  insurance_contract_detail\n"
                + "join insurance_contract  on insurance_contract_detail.contract_id = insurance_contract.contract_id\n"
                + "join loan on insurance_contract.customer_id = loan.customer_id\n"
                + "join service_terms on loan.serviceTerm_id = service_terms.serviceTerm_id\n"
                + "join term on service_terms.term_id = term.term_id\n"
                + "where insurance_contract_detail.contract_id = ? and insurance_contract_detail.insurance_id = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, contract_id);
            pre.setInt(2, insurance_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                contract_id = rs.getInt("contract_id");
                 insurance_id = rs.getInt("insurance_id");
                int customer_id = rs.getInt("customer_id");
                int service_id = rs.getInt("service_id");
                int policy_id = rs.getInt("policy_id");
                int duration = rs.getInt("duration");
                double coverage_amount = rs.getDouble("CoverageAmount");
                double premium_amount = rs.getDouble("PremiumAmount");
                double paid_amount = rs.getDouble("PaidAmount");
                Date start_date = rs.getDate("start_date");
                Date end_date = rs.getDate("end_date");
                String payment_frequency = rs.getString("payment_frequency");
                Insurance_contract_detail d = new Insurance_contract_detail(contract_id, insurance_id, customer_id,
                        service_id, policy_id, coverage_amount, premium_amount, paid_amount, start_date, end_date, payment_frequency, duration);
                return d;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertContractDetail(Insurance_contract_detail d) {
        String sql = "INSERT INTO insurance_contract_detail (contract_id, insurance_id, CoverageAmount, PremiumAmount, PaidAmount,StartDate, EndDate)\n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, d.getContract_id());
            pre.setInt(2, d.getInsurance_id());
            pre.setDouble(3, d.getCoverageAmount());
            pre.setDouble(4, d.getPremiumAmount());
            pre.setDouble(5, d.getPaidAmount());
            pre.setDate(6, new java.sql.Date(d.getStart_date().getTime()));
            pre.setDate(7, new java.sql.Date(d.getEnd_date().getTime()));
            pre.executeUpdate();
            System.out.println("add succesfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DAO_Insurance d = new DAO_Insurance();

//        List<Insurance_contract_detail> c = d.getInsuranceContractDetailByCustomerIDAndInsuranceID(1, 1);
//        for (Insurance_contract_detail insurance_contract_detail : c) {
//            System.out.println(c);
//        }
        Insurance_contract_detail c = d.getInsuranceContractDetailByContractid(5, 1);
        System.out.println(c);

        //        List<Customer> c = d.getInsuranceCustomerByInsuranceId(2);
//        System.out.println(c);
//        int customer_id = 3;
//        int service_id = 3;
//        int policy_id = 4;
//        int insurance_id = 2;
//        int contract_id = 22;
//        double cover = 50000;
//        double pre = 10000;
//        double paid = 1000;
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        java.util.Date utilStartDate = null;
//        java.util.Date utilEndDate = null;
//
//        try {
//            utilStartDate = sdf.parse("2025-03-01");
//            utilEndDate = sdf.parse("2026-03-01");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        // Ensure dates are parsed before converting
//        if (utilStartDate != null && utilEndDate != null) {
//            // Convert java.util.Date to java.sql.Date
//            Date start = new Date(utilStartDate.getTime());
//            Date end = new Date(utilEndDate.getTime());
//            String pay = "monthly";
//            String status = "active";
//            Insurance_contract_detail con = new Insurance_contract_detail(contract_id, insurance_id, cover, pre, paid, utilStartDate, utilEndDate);
//            d.insertContractDetail(con);
//
//        }
    }
}
