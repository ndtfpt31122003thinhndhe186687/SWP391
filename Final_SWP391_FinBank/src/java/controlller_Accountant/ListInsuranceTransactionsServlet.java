package controlller_Accountant;

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy tham số tìm kiếm (mặc định là rỗng nếu không có)
        String search = request.getParameter("search");
        if (search == null) {
            search = "";
        } else {
            // Loại bỏ khoảng trắng đầu cuối và thay thế nhiều khoảng trắng liên tiếp bằng 1 khoảng trắng
            search = search.trim().replaceAll("\\s+", " ");
        }
        
        // Lấy số trang, mặc định là 1
        int page = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }
        int pageSize = 10;
        int offset = (page - 1) * pageSize;
        
        InsuranceTransactionDAO transactionDAO = new InsuranceTransactionDAO();
        List<Insurance_transactions> transactions = null;
        int totalRecords = 0;
        try {
            transactions = transactionDAO.getAllInsuranceTransactions(search, offset, pageSize);
            totalRecords = transactionDAO.getTotalInsuranceTransactionsCount(search);
        } catch (Exception ex) {
            Logger.getLogger(ListInsuranceTransactionsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int totalPages = (int) Math.ceil(totalRecords / (double) pageSize);
        
        request.setAttribute("transactions", transactions);
        request.setAttribute("search", search);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        
        request.getRequestDispatcher("/list-insurance-transactions.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
