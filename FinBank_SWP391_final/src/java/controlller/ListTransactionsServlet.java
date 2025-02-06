package controlller;

import dal.TransactionDAO;
import model.Transaction;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ListTransactionsServlet", urlPatterns = {"/list-transactions"})
public class ListTransactionsServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        TransactionDAO transactionDAO = new TransactionDAO();
        List<Transaction> transactions = null;
        try {
            transactions = transactionDAO.getAllTransactions();
        } catch (Exception ex) {
            Logger.getLogger(ListTransactionsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.setAttribute("transactions", transactions);
        request.getRequestDispatcher("/list-transactions.jsp").forward(request, response);
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