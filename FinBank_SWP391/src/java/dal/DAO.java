package dal;

import java.sql.*;
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
                + "           ([fullName]\n"
                + "           ,[email]\n"
                + "           ,[phoneNumber]\n"
                + "           ,[password]\n"
                + "           ,[address]\n"
                + "           ,[userType]\n"
                + "           ,[status]\n"
                + "           ,[createdAt]\n"
                + "           ,[gender]\n"
                + "           ,[dateOfBirth]\n"
                + "           ,[profilePicture])\n"
                + "     VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, us.getFullName());
            ps.setString(2, us.getEmail());
            ps.setString(3, us.getPhoneNumber());
            ps.setString(4, us.getPassword());
            ps.setString(5, us.getAddress());
            ps.setString(6, us.getUserType());
             ps.setString(7, us.getStatus());
             ps.setString(8, us.getCreatedAt());
             ps.setString(9, us.getGender());
             ps.setDate(10, new Date(us.getDateOfBirth().getTime()));
             ps.setString(11, us.getProfilePicture());
            ps.executeUpdate();
        } catch (SQLException e) {
            status = "Error at register " + e.getMessage();
             e.printStackTrace();
        }
    }
    public boolean existedAcc(String username) {
        String sql = "SELECT [userId]\n"
                + "      ,[fullName]\n"
                + "      ,[email]\n"
                + "      ,[phoneNumber]\n"
                 + "      ,[password]\n"
                + "      ,[address]\n"
                 + "       ,[userType]\n"
                 + "       ,[status]\n"
                 + "      ,[createdAt]\n"
                  + "      ,[gender]\n"
                + "  FROM [dbo].[Users] WHERE email = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                 if (rs.next()) {
                   return true;
                 }
            }
        } catch (SQLException e) {
            status = "Error at existedAcc " + e.getMessage();
        }
        return false;
    }

     public boolean existedEmail(String email) {
        String sql = "SELECT [userId]\n"
                + "      ,[fullName]\n"
                + "      ,[email]\n"
                + "      ,[phoneNumber]\n"
                 + "      ,[password]\n"
                + "      ,[address]\n"
                 + "       ,[userType]\n"
                 + "       ,[status]\n"
                 + "      ,[createdAt]\n"
                  + "      ,[gender]\n"
                + "  FROM [dbo].[Users] WHERE email = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                 if (rs.next()) {
                   return true;
                 }
            }
        } catch (SQLException e) {
            status = "Error at existedEmail " + e.getMessage();
        }
        return false;
    }

   public boolean existedPhoneNum(String phoneNum) {
        String sql = "SELECT [userId]\n"
                 + "      ,[fullName]\n"
                + "      ,[email]\n"
                + "      ,[phoneNumber]\n"
                 + "      ,[password]\n"
                + "      ,[address]\n"
                 + "       ,[userType]\n"
                 + "       ,[status]\n"
                 + "      ,[createdAt]\n"
                  + "      ,[gender]\n"
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
}