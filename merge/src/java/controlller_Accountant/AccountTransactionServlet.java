package controlller_Accountant;

import dal.CustomerDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Customer;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "AccountTransactionServlet", urlPatterns = {"/accountTransaction"})
public class AccountTransactionServlet extends HttpServlet {

    private CustomerDAO customerDAO;

    @Override
    public void init() throws ServletException {
        customerDAO = new CustomerDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String actionType = request.getParameter("actionType");
        String amountStr = request.getParameter("amount");

        // Kiểm tra nếu amountStr là null hoặc rỗng trước khi gọi trim()
        if (amountStr == null || amountStr.trim().isEmpty()) {
            request.setAttribute("error", "Amount is required.");
            request.getRequestDispatcher("depositWithdraw.jsp").forward(request, response);
            return;
        }

        // Loại bỏ khoảng trắng và dấu phẩy
        amountStr = amountStr.trim().replaceAll(",", "");

        double amount = 0;
        try {
            amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                throw new NumberFormatException("Amount must be positive.");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid amount entered.");
            request.getRequestDispatcher("depositWithdraw.jsp").forward(request, response);
            return;
        }

        // Lấy đối tượng Customer từ session
        Customer account = (Customer) request.getSession().getAttribute("account");
        if (account == null) {
            request.setAttribute("error", "Customer not logged in.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        int accountId = account.getCustomer_id();
        String cardType = account.getCard_type().toLowerCase(); // "credit" or "debit"
        try {
            boolean success = false;
            if ("deposit".equalsIgnoreCase(actionType)) {
                success = customerDAO.deposit(accountId, amount, cardType);
                if (success) {
                    request.setAttribute("message", "Deposit successful.");
                }
            } else if ("withdraw".equalsIgnoreCase(actionType)) {
                success = customerDAO.withdraw(accountId, amount, cardType);
                if (!success) {
                    request.setAttribute("error", "Không đủ tiền để rút");
                } else {
                    request.setAttribute("message", "Withdrawal successful.");
                }
            }
        } catch (Exception ex) {
            request.setAttribute("error", "Transaction error: " + ex.getMessage());
        }

        // Cập nhật lại thông tin Customer từ CSDL và lưu vào session
        Customer updatedAccount = null;
        try {
            updatedAccount = customerDAO.getCustomerById(accountId);
            List<String> transactionHistory = customerDAO.getTransactionHistory(accountId);
            request.setAttribute("transactionHistory", transactionHistory); // Gửi lịch sử giao dịch đến JSP
        } catch (Exception ex) {
            Logger.getLogger(AccountTransactionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        request.getSession().setAttribute("account", updatedAccount);

        request.getRequestDispatcher("depositWithdraw.jsp").forward(request, response);
    }

}
