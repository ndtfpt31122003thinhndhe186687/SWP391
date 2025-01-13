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
                + "           ,[createdAt]\n"
                + "           ,[gender]\n"
                + "           ,[dateOfBirth]\n"
                + "           ,[profilePicture])\n"
                + "     VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
           ps.setString(1, us.getFullName());
            ps.setString(2, us.getEmail());
            ps.setString(3, us.getPhoneNumber());
            ps.setString(4, us.getPassword());
            ps.setString(5, us.getAddress());
             ps.setString(6, us.getCreatedAt());
             ps.setString(7, us.getGender());
            ps.setDate(8, new Date(us.getDateOfBirth().getTime()));
             ps.setString(9, us.getProfilePicture());

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
                  + "      ,[createdAt]\n"
                  + "      ,[gender]\n"
                 + "      ,[profilePicture]\n"
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
                  + "      ,[createdAt]\n"
                  + "      ,[gender]\n"
                 + "      ,[profilePicture]\n"
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
           String sql = "SELECT [id]\n"
                    + "      ,[name]\n"
                    + "      ,[fullname]\n"
                    + "      ,[email]\n"
                    + "      ,[phonenum]\n"
                      + "      ,[address]\n"
                       + "     ,[gender]\n"
                    + "      ,[password]\n"

                    + "  FROM [dbo].[User] where name = ?";

            try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, username);

            try (ResultSet rs = st.executeQuery()) {
            if (rs.next()) {
              //get the hash password from database
              String hashedDbPassword = rs.getString("password");
             //check if entered pass matches to the hashed one in db
              if (BCrypt.checkpw(password, hashedDbPassword)) {
                   return new User(rs.getInt("id"), rs.getString("fullname"), rs.getString("email"), rs.getString("phonenum"), rs.getString("address"), rs.getString("gender"),hashedDbPassword);


              }
             }

              }
            }
            catch (SQLException e) {
             // log the exception
               System.out.println("Database error occurred during authentication: " + e);
                // You can throw custom exception
                return null;
            }
            return null;
      }

    public void change(User a) {
          String sql = "UPDATE [dbo].[User]\n"
                    + "   SET [password] = ?\n"
                    + " WHERE [email]=?"; // using email to identify the user for update

            try (PreparedStatement st = connection.prepareStatement(sql)) {
                //hash the user provided password before saving to database
                 String hashedPassword = BCrypt.hashpw(a.getPassword(),BCrypt.gensalt());
                st.setString(1, hashedPassword);
                st.setString(2, a.getEmail());//using user email as an identifier for update
                st.executeUpdate();
            } catch (SQLException e) {
                // Log the exception properly
                System.out.println("Database error occurred while changing password: " + e);
               // You can throw custom exception

            }
        }
}