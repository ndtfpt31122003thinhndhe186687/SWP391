package dal;

import java.sql.*;
import java.time.LocalDate;
import model.Customer;
import model.User;

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

    public void register(User us) {
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
            ps.setDate(7, us.getDate_of_birth());
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

    public User check(String username, String password) {
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
                    return new User(rs.getInt("user_id"), rs.getString("full_name"), rs.getString("email"),
                            rs.getString("password"), rs.getString("phone_number"), rs.getString("address"),
                            rs.getString("created_at"), rs.getString("gender"), rs.getDate("date_of_birth"), rs.getString("profile_picture"));
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

    public Customer login(String email, String pass) {
        String sql = "select * from users u join customer c on u.user_id=c.customer_id\n"
                + "where u.email = ?\n"
                + "and u.password = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, email);
            pre.setString(2, pass);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                return new Customer(rs.getInt("customer_id"), rs.getString("user_type"),
                        email, pass, rs.getString("full_name"));
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static void main(String[] args) {
        DAO d = new DAO();
        Date sqlDate = Date.valueOf("2004-03-03"); // Yêu cầu định dạng chuỗi "yyyy-MM-dd"
        User u =new User(0, "a", "a", "a", "a", "a", "a", "male", sqlDate, "a");
        d.register(u);
    }
}
