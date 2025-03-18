/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import dal.DBContext;
import model.ElectricityInvoice;
import model.WaterInvoice;
import model.InternetInvoice;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import model.Customer;

public class InvoiceDAO extends DBContext {

    public static InvoiceDAO INSTANCE = new InvoiceDAO();
    private Connection con;
    private String status = "OK";

    public InvoiceDAO() {
        try {
            con = new DBContext().getConnection();
        } catch (Exception e) {
            status = "Error at connection: " + e.getMessage();
        }
    }

    // ---------------------- Lấy số dư tài khoản khách hàng ----------------------
    public double getCustomerBalance(int customerId) throws Exception {
        String sql = "SELECT card_type, amount, credit_limit FROM customer WHERE customer_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String cardType = rs.getString("card_type");
                if ("debit".equalsIgnoreCase(cardType)) {
                    return rs.getDouble("amount");
                } else if ("credit".equalsIgnoreCase(cardType)) {
                    return rs.getDouble("credit_limit");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Lỗi
    }

    // Cập nhật số dư tài khoản khách hàng
    public boolean updateCustomerBalance(int customerId, double newBalance) throws Exception {
        String sql = "UPDATE customer SET amount = ? WHERE customer_id = ? AND card_type = 'debit'";
        String sqlCredit = "UPDATE customer SET credit_limit = ? WHERE customer_id = ? AND card_type = 'credit'";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql); PreparedStatement psCredit = conn.prepareStatement(sqlCredit)) {

            ps.setDouble(1, newBalance);
            ps.setInt(2, customerId);
            int debitUpdate = ps.executeUpdate();

            psCredit.setDouble(1, newBalance);
            psCredit.setInt(2, customerId);
            int creditUpdate = psCredit.executeUpdate();

            return (debitUpdate > 0 || creditUpdate > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ---------------------- CRUD Hóa đơn điện ----------------------
    public boolean createElectricityInvoice(ElectricityInvoice invoice) throws Exception {
        String sql = "INSERT INTO electricityinvoice (provider_id, customer_id, ConsumptionKWh, Amount, Duedate, status) VALUES (?, ?, ?, ?, ?, 'pending')";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, invoice.getProviderId());
            ps.setInt(2, invoice.getCustomerId());
            ps.setDouble(3, invoice.getConsumptionKWh());
            ps.setDouble(4, invoice.getAmount());
            ps.setDate(5, (Date) invoice.getDueDate());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<ElectricityInvoice> getAllElectricityInvoices() throws Exception {
        List<ElectricityInvoice> invoices = new ArrayList<>();
        String sql = "SELECT * FROM electricityinvoice";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                invoices.add(new ElectricityInvoice(
                        rs.getInt("invoice_id"),
                        rs.getInt("provider_id"),
                        rs.getInt("customer_id"),
                        rs.getDouble("ConsumptionKWh"),
                        rs.getDouble("Amount"),
                        rs.getDate("Duedate"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoices;
    }

    public boolean updateElectricityInvoice(ElectricityInvoice invoice) throws Exception {
        String sql = "UPDATE electricityinvoice SET ConsumptionKWh = ?, Amount = ?, Duedate = ?, status = ? WHERE invoice_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, invoice.getConsumptionKWh());
            ps.setDouble(2, invoice.getAmount());
            ps.setDate(3, (Date) invoice.getDueDate());
            ps.setString(4, invoice.getStatus());
            ps.setInt(5, invoice.getInvoiceId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteElectricityInvoice(int invoiceId) throws Exception {
        String sql = "DELETE FROM electricityinvoice WHERE invoice_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, invoiceId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ---------------------- CRUD Hóa đơn nước ----------------------
    public boolean createWaterInvoice(WaterInvoice invoice) throws Exception {
        String sql = "INSERT INTO waterinvoice (provider_id, customer_id, ConsumptionM3, Amount, Duedate, status) VALUES (?, ?, ?, ?, ?, 'pending')";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, invoice.getProviderId());
            ps.setInt(2, invoice.getCustomerId());
            ps.setDouble(3, invoice.getConsumptionM3());
            ps.setDouble(4, invoice.getAmount());
            ps.setDate(5, (Date) invoice.getDueDate());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<WaterInvoice> getAllWaterInvoices() throws Exception {
        List<WaterInvoice> invoices = new ArrayList<>();
        String sql = "SELECT * FROM electricityinvoice";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                invoices.add(new WaterInvoice(
                        rs.getInt("invoice_id"),
                        rs.getInt("provider_id"),
                        rs.getInt("customer_id"),
                        rs.getDouble("ConsumptionM3"),
                        rs.getDouble("Amount"),
                        rs.getDate("Duedate"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoices;
    }

    public boolean updateWaterInvoice(WaterInvoice invoice) throws Exception {
        String sql = "UPDATE electricityinvoice SET ConsumptionKWh = ?, Amount = ?, Duedate = ?, status = ? WHERE invoice_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, invoice.getConsumptionM3());
            ps.setDouble(2, invoice.getAmount());
            ps.setDate(3, (Date) invoice.getDueDate());
            ps.setString(4, invoice.getStatus());
            ps.setInt(5, invoice.getInvoiceId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteWaterInvoice(int invoiceId) throws Exception {
        String sql = "DELETE FROM waterinvoice WHERE invoice_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, invoiceId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ---------------------- CRUD Hóa đơn internet ----------------------
    public boolean createInternetInvoice(InternetInvoice invoice) throws Exception {
        String sql = "INSERT INTO internetinvoice (provider_id, customer_id, Package, Amount, Duedate, status) VALUES (?, ?, ?, ?, ?, 'pending')";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, invoice.getProviderId());
            ps.setInt(2, invoice.getCustomerId());
            ps.setString(3, invoice.getPackage());
            ps.setDouble(4, invoice.getAmount());
            ps.setDate(5, (Date) invoice.getDueDate());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<InternetInvoice> getAllInternetInvoices() throws Exception {
        List<InternetInvoice> invoices = new ArrayList<>();
        String sql = "SELECT * FROM internetinvoice";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                invoices.add(new InternetInvoice(
                        rs.getInt("invoice_id"),
                        rs.getInt("provider_id"),
                        rs.getInt("customer_id"),
                        rs.getString("Package"),
                        rs.getDouble("Amount"),
                        rs.getDate("Duedate"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoices;
    }

    public boolean updateInternetInvoice(InternetInvoice invoice) throws Exception {
        String sql = "UPDATE internetinvoice SET Package = ?, Amount = ?, Duedate = ?, status = ? WHERE invoice_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, invoice.getPackage());
            ps.setDouble(2, invoice.getAmount());
            ps.setDate(3, (Date) invoice.getDueDate());
            ps.setString(4, invoice.getStatus());
            ps.setInt(5, invoice.getInvoiceId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteInternetInvoice(int invoiceId) throws Exception {
        String sql = "DELETE FROM internetinvoice WHERE invoice_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, invoiceId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ---------------------- Thanh toán hóa đơn ----------------------
    public boolean payInvoice(String tableName, int invoiceId, int customerId, double amount) throws Exception {
        double balance = getCustomerBalance(customerId);
        if (balance < amount) {
            return false;
        }

        double newBalance = balance - amount;
        if (updateCustomerBalance(customerId, newBalance)) {
            return updateInvoiceStatus(tableName, invoiceId, "paid");
        }
        return false;
    }

    private boolean updateInvoiceStatus(String tableName, int invoiceId, String status) throws Exception {
        String sql = "UPDATE " + tableName + " SET status = ? WHERE invoice_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, invoiceId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ---------------------- Gửi hóa đơn tự động từ ngày 1-5 hàng tháng ----------------------
    public void sendInvoicesToCustomers(Date currentDate) throws Exception {
        String sql = "UPDATE electricityinvoice SET status = 'pending' WHERE Duedate BETWEEN ? AND ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(currentDate.toLocalDate().withDayOfMonth(1)));
            ps.setDate(2, Date.valueOf(currentDate.toLocalDate().withDayOfMonth(5)));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
