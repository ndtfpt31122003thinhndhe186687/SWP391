/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import model.Savings;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import model.ServiceTerms;

/**
 *
 * @author Acer Nitro Tiger
 */
public class SavingDAO extends DBContext {

    public static SavingDAO INSTANCE = new SavingDAO();
    private Connection con;
    private String status = "OK";

    public SavingDAO() {
        try {
            con = new DBContext().getConnection();
        } catch (Exception e) {
            status = "Error at connection: " + e.getMessage();
        }
    }

    public static SavingDAO getINSTANCE() {
        return INSTANCE;
    }

    public static void setINSTANCE(SavingDAO INSTANCE) {
        SavingDAO.INSTANCE = INSTANCE;
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

    //get all service term that service is saving deposit
    public List<ServiceTerms> getDepositService() {
        List<ServiceTerms> list = new ArrayList<>();
        String sql = "SELECT st.*, s.service_name, t.duration FROM service_terms st "
                + "LEFT JOIN services s ON st.service_id = s.service_id "
                + "LEFT JOIN term t ON st.term_id = t.term_id "
                + "WHERE st.service_id=1 and st.status='active'";
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

    //lay ra end_date khi chon serviceTermId
    public int getDurationByServiceTermId(int serviceTermId) {
        String sql = "select t.duration from term t join service_terms st on st.term_id=t.term_id where st.serviceTerm_id=?";
        try (PreparedStatement pre = con.prepareStatement(sql)) {
            pre.setInt(1, serviceTermId);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                return rs.getInt("duration");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Trả về 0 nếu không tìm thấy
    }

    //INSERT SAVING(create savings deposit)
    public int insertSavings(Savings s) {
        int generatedId = -1;
        String sql = "INSERT INTO savings (customer_id, serviceTerm_id, amount, notes, start_date, end_date) "
                + "SELECT ?, ?, ?, ?, ?, ? "
                + "FROM customer WHERE customer_id = ? AND card_type = 'debit'";
        try (PreparedStatement pre = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pre.setInt(1, s.getCustomer_id());
            pre.setInt(2, s.getServiceTerm_id());
            pre.setDouble(3, s.getAmount());
            pre.setString(4, s.getNotes());
            pre.setTimestamp(5, new java.sql.Timestamp(s.getStart_date().getTime()));
            pre.setTimestamp(6, new java.sql.Timestamp(s.getEnd_date().getTime()));
            pre.setInt(7, s.getCustomer_id());

            int rowsInserted = pre.executeUpdate();
            if (rowsInserted > 0) {
                updateAmount(s.getCustomer_id(), s.getAmount());
                try (ResultSet rs = pre.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return generatedId;

    }

    //If the application is accepted, the account money will be deducted to add to the savings deposit
    //update amount in customer when the application is sending
    public void updateAmount(int customer_id, double amount) {
        String sql = "UPDATE customer SET amount = amount - ? WHERE customer_id = ? and card_type='debit'";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, amount);
            ps.setInt(2, customer_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //lay lai tien khi bi rejected
    public void getAmountAgain(int customer_id, double amount) {
        String sql = "UPDATE customer SET amount = amount + ? WHERE customer_id = ? and card_type='debit'";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, amount);
            ps.setInt(2, customer_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //get total saving deposit
    public double getSavingDeposit(int saving_id) {
        String sql = "SELECT s.amount, st.interest_rate, t.duration, "
                + "COALESCE(vt.savings_rate, 0) AS vip_bonus_rate, "
                + "v.start_date AS vip_start_date, s.start_date AS saving_start_date "
                + "FROM savings s "
                + "JOIN service_terms st ON s.serviceTerm_id = st.serviceTerm_id "
                + "JOIN term t ON st.term_id = t.term_id "
                + "LEFT JOIN vip v ON s.customer_id = v.customer_id AND v.status = 'active' "
                + "LEFT JOIN vip_term vt ON v.vipTerm_id = vt.vipTerm_id "
                + "WHERE s.savings_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, saving_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                double amount = rs.getDouble("amount");
                double baseInterestRate = rs.getDouble("interest_rate");
                double vipBonusRate = rs.getDouble("vip_bonus_rate");
                int duration = rs.getInt("duration");
                Timestamp vipStartDate = rs.getTimestamp("vip_start_date");
                Timestamp savingStartDate = rs.getTimestamp("saving_start_date");

                double finalInterestRate = baseInterestRate;

                // Chỉ cộng thêm nếu đơn được tạo sau khi đăng ký VIP
                if (vipStartDate != null && savingStartDate.after(vipStartDate)) {
                    finalInterestRate += vipBonusRate;
                }

                // Tính tổng tiền sau lãi suất
                double totalWithInterest = amount + (amount * (finalInterestRate / 100) * (duration / 12.0));
                return totalWithInterest;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //get list deposit
    public List<Savings> getAllDepositSavingsOfUser(int customer_id) {
        List<Savings> list = new ArrayList<>();
        String sql = "select s.savings_id,s.amount,s.start_date,s.end_date,st.term_name,st.interest_rate,t.duration,s.status\n"
                + "FROM savings s\n"
                + "JOIN service_terms st ON s.serviceTerm_id = st.serviceTerm_id\n"
                + "JOIN term t ON st.term_id = t.term_id where s.status = 'approved' and s.customer_id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, customer_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Savings s = new Savings();
                s.setSavings_id(rs.getInt("savings_id"));
                s.setAmount(rs.getDouble("amount"));
                s.setStart_date(rs.getTimestamp("start_date"));
                s.setEnd_date(rs.getDate("end_date"));
                s.setTerm_name(rs.getString("term_name"));
                s.setInterest_rate(rs.getDouble("interest_rate"));
                s.setDuration(rs.getInt("duration"));
                s.setStatus(rs.getString("status"));
                list.add(s);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    //check is maturity or not
    public boolean checkMaturity(int savingId) {
        String sql = "SELECT COUNT(*) FROM savings WHERE savings_id = ? AND end_date <= GETDATE() AND status = 'approved'";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, savingId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    //calculate interest
    public void getInterest(int savings_id) {
        String sql = "UPDATE savings \n"
                + "SET amount = amount + (amount * (\n"
                + "    SELECT st.interest_rate / 100 * (t.duration / 12.0) \n"
                + "    FROM service_terms st\n"
                + "    JOIN term t ON st.term_id = t.term_id\n"
                + "    WHERE st.serviceTerm_id = savings.serviceTerm_id\n"
                + ")),status='completed'\n"
                + "WHERE status = 'approved' and savings_id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, savings_id);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    

    //rut tien
    public void withdrawSavings(int saving_id) {
        String sql = "update customer \n"
                + "set amount=c.amount+s.amount\n"
                + "from customer c join savings s on s.customer_id=c.customer_id\n"
                + "where s.status='completed' and s.savings_id=? and c.card_type='debit'";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, saving_id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //rut tien truoc ki han
    public void withdrawSavingsEarly(int saving_id) {
        String sql = "update customer \n"
                + "set amount=c.amount+s.amount\n"
                + "from customer c join savings s on s.customer_id=c.customer_id\n"
                + "where s.status='approved' and s.savings_id=? and c.card_type='debit'";
        String updateStatusSql = "UPDATE savings SET status = 'completed' WHERE savings_id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, saving_id);
            ps.executeUpdate();

            PreparedStatement psStatus = con.prepareStatement(updateStatusSql);
            psStatus.setInt(1, saving_id);
            psStatus.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public double getSavingDepositWithRate(int savingsId, double interestRate) {
        double totalWithInterest = 0;
        String sql = """
        SELECT s.amount, t.duration, s.start_date 
        FROM savings s 
        JOIN service_terms st ON s.serviceTerm_id = st.serviceTerm_id 
        JOIN term t ON st.term_id = t.term_id 
        WHERE s.savings_id = ?
    """;

        try (Connection conn = new DBContext().getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, savingsId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                double amount = rs.getDouble("amount");
                int duration = rs.getInt("duration");

                System.out.println("Calculating Saving ID: " + savingsId);
                System.out.println("Amount: " + amount);
                System.out.println("Duration: " + duration);
                System.out.println("Final Interest Rate: " + interestRate);

                // Tính tổng tiền với lãi suất mới (nếu có)
                totalWithInterest = amount * Math.pow(1 + (interestRate / 100), duration / 12.0);

                System.out.println("Total With Interest: " + totalWithInterest);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalWithInterest;
    }

    //phong
    public void insertNotification(int customerId, int referenceId, String type, String message) {
        String sql = "INSERT INTO notifications (customer_id, reference_id, notification_type, message) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            pstmt.setInt(2, referenceId);
            pstmt.setString(3, type);
            pstmt.setString(4, message);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }
    //get savings by id

    public Savings getSavingsById(int customer_id, int saving_id) {
        String sql = "select s.savings_id,s.amount,s.start_date,s.end_date,st.term_name,st.interest_rate,t.duration,s.status\n"
                + "FROM savings s\n"
                + "JOIN service_terms st ON s.serviceTerm_id = st.serviceTerm_id\n"
                + "JOIN term t ON st.term_id = t.term_id and s.customer_id=? and s.savings_id=?";;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, customer_id);
            ps.setInt(2, saving_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new Savings(rs.getInt("savings_id"), rs.getInt("duration"),
                        rs.getDouble("amount"), rs.getDouble("interest_rate"), rs.getDate("start_date"), rs.getDate("end_date"), rs.getString("term_name"), rs.getString("status"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    //ghi lai lich su giao dich
    public void insertTransactionSaving(int customer_id, int service_id, double amount, String transaction_type) {
        String sql = "insert into transactions (customer_id,service_id,amount,transaction_type) values (?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, customer_id);
            ps.setInt(2, service_id);
            ps.setDouble(3, amount);
            ps.setString(4, transaction_type);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    //lay ra tien rur de luu vao giao dich
    public double getMoney(int savings_id){
        String sql="select amount from savings where savings_id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, savings_id);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                return rs.getDouble("amount");
            }
        } catch (Exception e) {
        }
        return 0;
    }

    public static void main(String[] args) {
        SavingDAO s = new SavingDAO();
        double k = s.getSavingDeposit(1);
        System.out.println(k);
    }

}
