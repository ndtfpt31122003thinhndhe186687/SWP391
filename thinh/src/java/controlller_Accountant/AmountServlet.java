package controlller_Accountant;

import dal.AmountDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.AmountDTO;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AmountServlet", urlPatterns = {"/amounts"})
public class AmountServlet extends HttpServlet {
    private AmountDAO amountDAO;
    
    @Override
    public void init() throws ServletException {
        amountDAO = new AmountDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Lấy tham số tìm kiếm và chuẩn hóa khoảng trắng
        String search = request.getParameter("search");
        if (search == null) {
            search = "";
        } else {
            search = search.trim().replaceAll("\\s+", " ");
        }
        
        // Lấy số trang, mặc định là 1
        String pageParam = request.getParameter("page");
        int page = 1;
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
            } catch(NumberFormatException e) {
                page = 1;
            }
        }
        int pageSize = 10;
        int offset = (page - 1) * pageSize;
        
        try {
            List<AmountDTO> amounts = amountDAO.getAllAmounts(search, offset, pageSize);
            int total = amountDAO.getTotalAmountsCount(search);
            int totalPages = (int)Math.ceil(total / (double) pageSize);
            
            // Thống kê từ bảng customer
            int totalCustomers = amountDAO.getTotalCustomers(search);
            int totalAmountCustomer = amountDAO.getTotalAmount(search);
            // Thống kê từ các bảng khác
            int totalAmountTransactions = amountDAO.getTotalAmountTransactions(search);
            int totalAmountInsurance = amountDAO.getTotalAmountInsuranceTransactions(search);
            int totalAmountLoans = amountDAO.getTotalAmountLoans(search);
            int totalAmountLoanDisbursements = 0;
            
            request.setAttribute("amounts", amounts);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("search", search);
            request.setAttribute("totalCustomers", totalCustomers);
            request.setAttribute("totalAmountCustomer", totalAmountCustomer);
            request.setAttribute("totalAmountTransactions", totalAmountTransactions);
            request.setAttribute("totalAmountInsurance", totalAmountInsurance);
            request.setAttribute("totalAmountLoans", totalAmountLoans);
            request.setAttribute("totalAmountLoanDisbursements", totalAmountLoanDisbursements);
            
            request.getRequestDispatcher("amounts.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
