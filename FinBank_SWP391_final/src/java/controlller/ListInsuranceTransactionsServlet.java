package controlller;

import dal.InsuranceTransactionDAO;
import model.Insurance_transactions;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ListInsuranceTransactionsServlet", urlPatterns = {"/list-insurance-transactions"})
public class ListInsuranceTransactionsServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        InsuranceTransactionDAO transactionDAO = new InsuranceTransactionDAO();
        List<Insurance_transactions> transactions = null;
        try {
            transactions = transactionDAO.getAllInsuranceTransactions();
        } catch (Exception ex) {
            Logger.getLogger(ListInsuranceTransactionsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.setAttribute("transactions", transactions);
        request.getRequestDispatcher("list-insurance-transactions.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}