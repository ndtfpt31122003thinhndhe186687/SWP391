package controlller;

import dal.DBContext;
import dal.SavingDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import model.Customer;

@WebServlet(name = "VerifyOtpsServlet", urlPatterns = {"/VerifyOtpsServlet"})
public class VerifyOtpsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

       String enteredOtp = request.getParameter("otp");
        HttpSession session = request.getSession();

        String sessionOtp = (String) session.getAttribute("otp");
        Customer customer = (Customer) session.getAttribute("account");
        Double amount = (Double) session.getAttribute("transferAmount");
        String toPhoneNumber = (String) session.getAttribute("transferToPhone");

        if (sessionOtp == null || customer == null || amount == null || toPhoneNumber == null) {
            response.sendRedirect("login");
            return;
        }

        if (!enteredOtp.equals(sessionOtp)) {
            request.setAttribute("mess", "Invalid OTP. Please try again.");
            request.getRequestDispatcher("EnterOtps.jsp").forward(request, response);
            return;
        }

        Connection conn = null;
        PreparedStatement updateFrom = null;
        PreparedStatement updateTo = null;
        PreparedStatement insertSenderStmt = null;
        PreparedStatement insertReceiverStmt = null;
        PreparedStatement getRecipientIdStmt = null;
        ResultSet rsRecipient = null;
        int senderTransactionId = -1;
        int recipientTransactionId = -1;
        int recipientId = -1;

        try {
            DBContext dbContext = new DBContext();
            conn = dbContext.getConnection();
            conn.setAutoCommit(false); // Bắt đầu transaction

            // 1. Trừ tiền người gửi
            String sql1 = "UPDATE customer SET amount = amount - ? WHERE customer_id = ?";
            updateFrom = conn.prepareStatement(sql1);
            updateFrom.setDouble(1, amount);
            updateFrom.setInt(2, customer.getCustomer_id());
            updateFrom.executeUpdate();

            // 2. Cộng tiền người nhận
            String sql2 = "UPDATE customer SET amount = amount + ? WHERE phone_number = ?";
            updateTo = conn.prepareStatement(sql2);
            updateTo.setDouble(1, amount);
            updateTo.setString(2, toPhoneNumber);
            updateTo.executeUpdate();

            // 3. Insert giao dịch người gửi (withdrawal)
            String insertTransactionSql = "INSERT INTO transactions (customer_id, service_id, amount, transaction_type) VALUES (?, ?, ?, ?)";
            insertSenderStmt = conn.prepareStatement(insertTransactionSql, Statement.RETURN_GENERATED_KEYS);
            insertSenderStmt.setInt(1, customer.getCustomer_id());
            insertSenderStmt.setInt(2, 4); // service_id = 4 -> withdrawal
            insertSenderStmt.setDouble(3, amount);
            insertSenderStmt.setString(4, "withdrawal");
            insertSenderStmt.executeUpdate();

            // Lấy transaction_id của người gửi
            ResultSet generatedKeys = insertSenderStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                senderTransactionId = generatedKeys.getInt(1);
            }

            // 4. Lấy customer_id của người nhận
            getRecipientIdStmt = conn.prepareStatement("SELECT customer_id FROM customer WHERE phone_number = ?");
            getRecipientIdStmt.setString(1, toPhoneNumber);
            rsRecipient = getRecipientIdStmt.executeQuery();

            if (rsRecipient.next()) {
                recipientId = rsRecipient.getInt("customer_id");

                // 5. Insert giao dịch người nhận (deposit)
                insertReceiverStmt = conn.prepareStatement(insertTransactionSql, Statement.RETURN_GENERATED_KEYS);
                insertReceiverStmt.setInt(1, recipientId);
                insertReceiverStmt.setInt(2, 3); // service_id = 3 -> deposit
                insertReceiverStmt.setDouble(3, amount);
                insertReceiverStmt.setString(4, "deposit");
                insertReceiverStmt.executeUpdate();

                // Lấy transaction_id của người nhận
                ResultSet generatedKeysReceiver = insertReceiverStmt.getGeneratedKeys();
                if (generatedKeysReceiver.next()) {
                    recipientTransactionId = generatedKeysReceiver.getInt(1);
                }
            }

            conn.commit(); // Hoàn tất giao dịch

            // Chèn thông báo sau khi commit
            SavingDAO sd = new SavingDAO();
            if (senderTransactionId != -1) {
                sd.insertNotification(customer.getCustomer_id(), senderTransactionId,
                        "Giao dịch", "Bạn đã chuyển thành công " + amount + " VNĐ đến số điện thoại " + toPhoneNumber + ". Hãy kiểm tra lại giao dịch!");
            }

            if (recipientTransactionId != -1 && recipientId != -1) {
                sd.insertNotification(recipientId, recipientTransactionId, "Giao dịch",
                        "Bạn đã nhận được " + amount + " VNĐ từ số điện thoại " + customer.getPhone_number() + ". Hãy kiểm tra số dư tài khoản!");
            }

            // Xóa thông tin OTP và tạm thời khỏi session
            session.removeAttribute("otp");
            session.removeAttribute("transferAmount");
            session.removeAttribute("transferToPhone");

            request.setAttribute("mess", "Transfer successful!");
            request.getRequestDispatcher("transfer.jsp").forward(request, response);

        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();
            request.setAttribute("mess", "Transfer failed: " + e.getMessage());
            request.getRequestDispatcher("transfer.jsp").forward(request, response);
        } finally {
            try {
                if (rsRecipient != null) rsRecipient.close();
                if (getRecipientIdStmt != null) getRecipientIdStmt.close();
                if (updateFrom != null) updateFrom.close();
                if (updateTo != null) updateTo.close();
                if (insertSenderStmt != null) insertSenderStmt.close();
                if (insertReceiverStmt != null) insertReceiverStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
