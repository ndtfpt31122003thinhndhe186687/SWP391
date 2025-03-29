package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import model.Customer;
import model.Loan;
import model.Loan_payments;
import model.ServiceTerms;
import model.Services;
import model.Staff;
import model.Term;
import model.Transaction;

public class DAO_Loan extends DBContext {

    public static DAO_Loan INSTANCE = new DAO_Loan();
    private Connection con;
    private String status = "OK";

    public DAO_Loan() {
        try {
            con = new DBContext().getConnection();
        } catch (Exception e) {
            status = "Error at connection: " + e.getMessage();
        }
    }

    public static DAO_Loan getINSTANCE() {
        return INSTANCE;
    }

    public static void setINSTANCE(DAO_Loan INSTANCE) {
        DAO_Loan.INSTANCE = INSTANCE;
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

    // Loan request
    public List<ServiceTerms> getLoanServiceTerms() {
        List<ServiceTerms> list = new ArrayList<>();
        String sql = "SELECT st.*, s.service_name, t.duration FROM service_terms st "
                + "LEFT JOIN services s ON st.service_id = s.service_id "
                + "LEFT JOIN term t ON st.term_id = t.term_id "
                + "WHERE st.service_id=2 and st.status='active'";
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
                s.setMin_payment(rs.getDouble("min_payment"));
                s.setStatus(rs.getString("status"));
                s.setCreated_at(rs.getTimestamp("created_at"));
                list.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public int InsertLoanRequest(Loan l) {
        int generatedId = -1;
        String sql = "insert into loan (customer_id, serviceTerm_id, amount,"
                + " start_date, end_date, loan_type, value_asset,"
                + " notes,[image],[status])"
                + "values (?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pre = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pre.setInt(1, l.getCustomer_id());
            pre.setInt(2, l.getServiceTerm_id());
            pre.setDouble(3, l.getAmount());
            pre.setDate(4, new java.sql.Date(l.getStart_date().getTime()));
            pre.setDate(5, new java.sql.Date(l.getEnd_date().getTime()));
            pre.setString(6, l.getLoan_type());
            pre.setDouble(7, l.getValue_asset());
            pre.setString(8, l.getNotes());
            pre.setString(9, l.getAsset_image());
            pre.setString(10, l.getStatus());
            int rowsInserted = pre.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet rs = pre.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return generatedId;
    }

    public List<Loan> getListLoanByCustomerId(int id) {
        String sql = "select l.amount,l.start_date,l.end_date,l.image,l.value_asset,"
                + "s.service_name,l.loan_id,l.status,l.loan_type from loan l join service_terms st on"
                + " l.serviceTerm_id=st.serviceTerm_id join services s on"
                + " st.service_id = s.service_id where l.customer_id = ?";
        List<Loan> list = new ArrayList<>();
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Loan l = new Loan();
                l.setAmount(rs.getDouble(1));
                l.setStart_date(rs.getDate(2));
                l.setEnd_date(rs.getDate(3));
                l.setAsset_image(rs.getString(4));
                l.setValue_asset(rs.getDouble(5));
                l.setServiceName(rs.getString(6));
                l.setLoan_id(rs.getInt(7));
                l.setStatus(rs.getString(8));
                l.setLoan_type(rs.getString(9));
                list.add(l);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public Loan getLoanByLoanId(int id) {
        String sql = "select l.loan_id,l.amount,t.duration,st.interest_rate "
                + "from loan l join service_terms st on "
                + "l.serviceTerm_id=st.serviceTerm_id \n"
                + "join term t on st.term_id=t.term_id where l.loan_id=?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                Loan l = new Loan();
                l.setLoan_id(rs.getInt(1));
                l.setAmount(rs.getDouble(2));
                l.setDuration(rs.getInt(3));
                l.setInterest_rate(rs.getDouble(4));
                return l;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public void insertLoanPayment(Loan_payments lp) {
        String sql = "INSERT INTO loan_payments (loan_id, payment_date, "
                + "payment_amount, principal_amount, interest_amount, "
                + "remaining_balance, payment_status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, lp.getLoan_id());
            pre.setDate(2, new java.sql.Date(lp.getPayment_date().getTime()));
            pre.setDouble(3, lp.getPayment_amount());
            pre.setDouble(4, lp.getPrincipal_amount());
            pre.setDouble(5, lp.getInterest_amount());
            pre.setDouble(6, lp.getRemaining_amount());
            pre.setString(7, lp.getPayment_status());
            pre.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<Loan_payments> getPaymentsByLoanIdandCustomerId(int loanId, int CustomerId) {
        List<Loan_payments> payments = new ArrayList<>();
        String sql = "select lp.loan_payments_id,lp.loan_id,lp.payment_date,lp.payment_amount,"
                + "lp.principal_amount,lp.interest_amount,lp.remaining_balance,"
                + "lp.payment_status,lp.payDate,lp.penaltyfee "
                + "from loan_payments lp join loan l on "
                + "lp.loan_id=l.loan_id where l.loan_id = ? and l.customer_id = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, loanId);
            pre.setInt(2, CustomerId);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Loan_payments lp = new Loan_payments();
                lp.setLoan_payments_id(rs.getInt(1));
                lp.setLoan_id(rs.getInt(2));
                lp.setPayment_date(rs.getDate(3));
                lp.setPayment_amount(rs.getDouble(4));
                lp.setPrincipal_amount(rs.getDouble(5));
                lp.setInterest_amount(rs.getDouble(6));
                lp.setRemaining_amount(rs.getDouble(7));
                lp.setPayment_status(rs.getString(8));
                lp.setPaydate(rs.getDate(9));
                lp.setPenaltyfee(rs.getDouble(10));
                payments.add(lp);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return payments;
    }

    public List<Loan_payments> getPaymentsByLoanIdandCustomerIdandStatus(int loanId, int CustomerId) {
        List<Loan_payments> payments = new ArrayList<>();
        String sql = "select lp.loan_payments_id,lp.loan_id,lp.payment_date,lp.payment_amount,"
                + "lp.principal_amount,lp.interest_amount,lp.remaining_balance,"
                + "lp.payment_status,lp.payDate,lp.penaltyfee "
                + "from loan_payments lp join loan l on "
                + "lp.loan_id=l.loan_id where l.loan_id = ? and l.customer_id = ? and lp.payment_status= 'pending'";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, loanId);
            pre.setInt(2, CustomerId);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Loan_payments lp = new Loan_payments();
                lp.setLoan_payments_id(rs.getInt(1));
                lp.setLoan_id(rs.getInt(2));
                lp.setPayment_date(rs.getDate(3));
                lp.setPayment_amount(rs.getDouble(4));
                lp.setPrincipal_amount(rs.getDouble(5));
                lp.setInterest_amount(rs.getDouble(6));
                lp.setRemaining_amount(rs.getDouble(7));
                lp.setPayment_status(rs.getString(8));
                lp.setPaydate(rs.getDate(9));
                lp.setPenaltyfee(rs.getDouble(10));
                payments.add(lp);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return payments;
    }

    public List<Loan_payments> getDuePayments() {
        List<Loan_payments> payments = new ArrayList<>();
        String sql = "SELECT lp.loan_payments_id, lp.loan_id, l.customer_id, c.email, lp.payment_amount, lp.payment_date "
                + "FROM loan_payments lp "
                + "JOIN loan l ON lp.loan_id = l.loan_id "
                + "JOIN customer c ON l.customer_id = c.customer_id "
                + "WHERE lp.payment_date = CAST(GETDATE() AS DATE) AND lp.payment_status = 'pending'";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Loan_payments l = new Loan_payments();
                l.setLoan_payments_id(rs.getInt(1));
                l.setLoan_id(rs.getInt(2));
                l.setCustomer_id(rs.getInt(3));
                l.setEmail(rs.getString(4));
                l.setPayment_amount(rs.getDouble(5));
                l.setPayment_date(rs.getDate(6));
                payments.add(l);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return payments;
    }

    public Loan_payments getLoanPaymentById(int id) {
        String sql = "Select * from loan_payments where loan_payments_id=?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                Loan_payments lp = new Loan_payments();
                lp.setLoan_payments_id(rs.getInt(1));
                lp.setLoan_id(rs.getInt(2));
                lp.setPayment_date(rs.getDate(3));
                return lp;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public void UpdateCustomerAmount(double amount, int id) {
        String sql = "update customer set amount =? where customer_id =?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setDouble(1, amount);
            pre.setInt(2, id);
            pre.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Customer getCustomerById(int id) {
        String sql = "select * from customer where customer_id=?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                Customer c = new Customer();
                c.setAmount(rs.getDouble("amount"));
                c.setFull_name(rs.getString("full_name"));
                c.setEmail(rs.getString("email"));
                return c;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public boolean UpdateLoanPayment(int id, String status, Date paymentDate, double fee) {
        String sql = "update loan_payments set payment_status = ?,payDate=?, penaltyfee=? where loan_payments_id =?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, status);
            pre.setDate(2, new java.sql.Date(paymentDate.getTime()));
            pre.setDouble(3, fee);
            pre.setInt(4, id);
            return pre.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean UpdateLoanPaymentAll(int id, Date paymentDate, double fee) {
        String sql = "update loan_payments set payment_status = 'complete',payDate=?, penaltyfee=? where loan_id=? and payment_status != 'complete'";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(3, id);
            pre.setDate(1, new java.sql.Date(paymentDate.getTime()));
            pre.setDouble(2, fee);
            return pre.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public double getAmountPayment(int id) {
        String sql = "select payment_amount from loan_payments where loan_payments_id=?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (Exception e) {
        }
        return 0;
    }

    // son
    public Loan getLoanByLoanIdAndDate(int id) {
        String sql = "select loan_id, start_date,end_date from loan\n"
                + "where loan_id = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                Loan l = new Loan();
                l.setLoan_id(rs.getInt(1));
                l.setStart_date(rs.getDate(2));
                l.setEnd_date(rs.getDate(3));
                return l;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public Loan getLoanByLoanIdAndInsuranceID(int loan_id, int insurance_id, int policy_id) {
        String sql = "select loan_id, insurance_id, policy_id from loan \n"
                + "join insurance_contract on loan.customer_id = insurance_contract.customer_id\n"
                + "join insurance_contract_detail on insurance_contract.contract_id = insurance_contract_detail.contract_id\n"
                + "where loan.customer_id = ? and insurance_contract_detail.insurance_id = ? and insurance_contract.policy_id = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, loan_id);
            pre.setInt(2, insurance_id);
            pre.setInt(3, policy_id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                Loan l = new Loan();
                l.setLoan_id(rs.getInt(1));
                l.setInsurance_id(rs.getInt(2));
                l.setPolicy_id(rs.getInt(3));
                return l;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public List<Loan> getListLoanByCustomerIdAndNote(int id) {
        String sql = "select l.amount,l.start_date,l.end_date,l.image,l.value_asset,"
                + "s.service_name,l.loan_id,l.status,l.notes from loan l join service_terms st on"
                + " l.serviceTerm_id=st.serviceTerm_id join services s on"
                + " st.service_id = s.service_id where l.customer_id = ?";
        List<Loan> list = new ArrayList<>();
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Loan l = new Loan();
                l.setAmount(rs.getDouble(1));
                l.setStart_date(rs.getDate(2));
                l.setEnd_date(rs.getDate(3));
                l.setAsset_image(rs.getString(4));
                l.setValue_asset(rs.getDouble(5));
                l.setServiceName(rs.getString(6));
                l.setLoan_id(rs.getInt(7));
                l.setStatus(rs.getString(8));
                l.setNotes(rs.getString(9));
                list.add(l);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public void InsertTransaction(int cid, int sid, double amount, String type) {
        String sql = "insert into transactions(customer_id,service_id,amount,transaction_type) values (?,?,?,?)";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, cid);
            pre.setInt(2, sid);
            pre.setDouble(3, amount);
            pre.setString(4, type);
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        DAO_Loan d = new DAO_Loan();
        String date = "2025-03-05";
        Date sqlDate = java.sql.Date.valueOf(date);

//        Calendar cal = Calendar.getInstance(); // khi nào accept thì mới lấy ngày
//        cal.setTime(new Date());
//        double amount = 1000000; // lấy từ loan
//        int month = 12; // lấy từ loan
//        double interest = 10.0; // lấy từ loan
//        double monthlyinterest = interest / 12 / 100;
//        double principal = amount / month;
//        double remaining = amount;
//        for (int i = 0; i < month; i++) {
//            cal.add(Calendar.MONTH, 1);
//            Date paymentDate = cal.getTime();
//            double interestAmount = remaining * monthlyinterest;
//            double paymentAmount = principal + interestAmount;
//            remaining -= principal;
//            Loan_payments payment = new Loan_payments(1,
//                    paymentDate, paymentAmount,
//                    principal, interestAmount,
//                    Math.max(remaining, 0), "pending");
//            d.insertLoanPayment(payment);
//        }
//        List<Loan_payments> list = d.getPaymentsByLoanIdandCustomerId(1, 1);
//        for (Loan_payments loan_payments : list) {
//            System.out.println(loan_payments);
//        }
//        
        List<Loan_payments> l = d.getPaymentsByLoanIdandCustomerId(8, 2);
        for (Loan_payments loan_payments : l) {
            System.out.println(loan_payments);
        }
    }

}
