package controlller_Accountant;

import dal.InvoiceDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomerInvoiceServlet extends HttpServlet {

    private InvoiceDAO invoiceDAO = InvoiceDAO.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy danh sách hóa đơn khách hàng chưa thanh toán
        try {
            int customerId = Integer.parseInt(request.getParameter("customerId"));
            String invoiceType = request.getParameter("invoiceType"); // "electricity", "water", "internet"

            request.setAttribute("customerId", customerId);
            request.setAttribute("invoiceType", invoiceType);
            RequestDispatcher dispatcher = request.getRequestDispatcher("customerInvoices.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int invoiceId = Integer.parseInt(request.getParameter("invoiceId"));
            int customerId = Integer.parseInt(request.getParameter("customerId"));
            double amount = Double.parseDouble(request.getParameter("amount"));
            String tableName = getInvoiceTable(request.getParameter("invoiceType"));

            // Kiểm tra số dư trước khi thanh toán
            double balance = invoiceDAO.getCustomerBalance(customerId);
            if (balance < amount) {
                request.setAttribute("message", "Số dư tài khoản không đủ để thanh toán!");
                request.getRequestDispatcher("customerInvoices.jsp").forward(request, response);
                return;
            }

            // Thanh toán hóa đơn
            boolean success = invoiceDAO.payInvoice(tableName, invoiceId, customerId, amount);

            if (success) {
                request.setAttribute("message", "Thanh toán hóa đơn thành công!");
            } else {
                request.setAttribute("message", "Thanh toán thất bại! Vui lòng thử lại.");
            }

            request.getRequestDispatcher("customerInvoices.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    // Xác định bảng hóa đơn tương ứng
    private String getInvoiceTable(String invoiceType) {
        switch (invoiceType.toLowerCase()) {
            case "electricity":
                return "electricityinvoice";
            case "water":
                return "waterinvoice";
            case "internet":
                return "internetinvoice";
            default:
                return "";
        }
    }
}
