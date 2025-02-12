package controlller_Accountant;

import dal.DebtManagementDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Debt_management;

@WebServlet(name = "ListDebtCustomersServlet", urlPatterns = {"/list-debt-customers"})
public class ListDebtCustomersServlet extends HttpServlet {
    private DebtManagementDAO debtManagementDAO;

    @Override
    public void init() {
        debtManagementDAO = new DebtManagementDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy tham số tìm kiếm
        String search = request.getParameter("search");
        if (search == null) {
            search = "";
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
        
        List<Debt_management> debtCustomers = null;
        int totalRecords = 0;
        try {
            debtCustomers = debtManagementDAO.getDebtCustomers(search, offset, pageSize);
            totalRecords = debtManagementDAO.getDebtCustomersCount(search);
        } catch (SQLException e) {
            throw new ServletException(e);
        } catch (Exception ex) {
            Logger.getLogger(ListDebtCustomersServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int totalPages = (int) Math.ceil(totalRecords / (double) pageSize);
        
        request.setAttribute("debtCustomers", debtCustomers);
        request.setAttribute("search", search);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("/list-debt-customers.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
