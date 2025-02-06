package controlller;

import dal.DebtManagementDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import model.Debt_management;

@WebServlet(name = "ListDebtCustomersServlet", value = "/list-debt-customers")
public class ListDebtCustomersServlet extends HttpServlet {
    private DebtManagementDAO debtManagementDAO;

    @Override
    public void init() {
        debtManagementDAO = new DebtManagementDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Debt_management> debtCustomers = debtManagementDAO.getDebtCustomers();
            request.setAttribute("debtCustomers", debtCustomers);
            request.getRequestDispatcher("/list-debt-customers.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}