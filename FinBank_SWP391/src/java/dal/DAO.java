/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.*;

import model.User;
/**
 *
 * @author Adim
 */
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

    public int getTotalUsers() throws SQLException {
        String query = "SELECT COUNT(*) AS total_users FROM Customers";
        try (
                PreparedStatement ps = con.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total_users");
            }
        }
        return 0;
    }

    public void register(User us) {
        String sql = "INSERT INTO [dbo].[Users]\n"
                + "           ([FullName]\n"
                + "           ,[Email]\n"
                + "           ,[PhoneNumber]\n"
                + "           ,[Username]\n"
                + "           ,[Password]\n"
                + "           ,[Address]\n"
                + "           ,[Role])\n"
                + "     VALUES(?, ?, ?, ?, ?, ?, 0)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, us.getFullName());
            ps.setString(2, us.getEmail());
            ps.setInt(3, us.getPhoneNumber());
            ps.setString(4, us.getUser());
            ps.setString(5, us.getPass());
            ps.setString(6, us.getAddress());
            ps.executeUpdate();
        } catch (SQLException e) {
            status = "Error at register " + e.getMessage();
        }
    }

    public boolean existedAcc(String username) {
        String sql = "SELECT [CustomerID]\n"
                + "      ,[FullName]\n"
                + "      ,[Email]\n"
                + "      ,[PhoneNumber]\n"
                + "      ,[Username]\n"
                + "      ,[Password]\n"
                + "      ,[Address]\n"
                + "      ,[Role]\n"
                + "  FROM [dbo].[Customers] WHERE Username = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            status = "Error at existedAcc " + e.getMessage();
        }
        return false;
    }


    public boolean existedEmail(String email) {
        String sql = "SELECT [CustomerID]\n"
                + "      ,[FullName]\n"
                + "      ,[Email]\n"
                + "      ,[PhoneNumber]\n"
                + "      ,[Username]\n"
                + "      ,[Password]\n"
                + "      ,[Address]\n"
                + "      ,[Role]\n"
                + "  FROM [dbo].[Customers] WHERE Email = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            status = "Error at existedEmail " + e.getMessage();
        }
        return false;
    }

    public boolean existedPhoneNum(String phoneNum) {
        String sql = "SELECT [CustomerID]\n"
                + "      ,[FullName]\n"
                + "      ,[Email]\n"
                + "      ,[PhoneNumber]\n"
                + "      ,[Username]\n"
                + "      ,[Password]\n"
                + "      ,[Address]\n"
                + "      ,[Role]\n"
                + "  FROM [dbo].[Customers] WHERE PhoneNumber = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, phoneNum);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            status = "Error at existedPhoneNum " + e.getMessage();
        }
        return false;
    }
}
