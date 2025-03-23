package controlller_Accountant;

import dal.InvoiceDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.InternetInvoice;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "InternetInvoiceServlet", urlPatterns = {"/internetInvoice"})
public class InternetInvoiceServlet extends HttpServlet {
    private InvoiceDAO invoiceDAO;

    @Override
    public void init() {
        invoiceDAO = new InvoiceDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<InternetInvoice> invoices = null;
        try {
            invoices = invoiceDAO.getAllInternetInvoices();
        } catch (Exception ex) {
            Logger.getLogger(InternetInvoiceServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("internetInvoices", invoices);
        request.getRequestDispatcher("internetInvoiceList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int providerId = Integer.parseInt(request.getParameter("providerId"));
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        String packageName = request.getParameter("packageName");
        double amount = Double.parseDouble(request.getParameter("amount"));
        Date dueDate = Date.valueOf(request.getParameter("dueDate"));
        String status = "pending";

        InternetInvoice invoice = new InternetInvoice(0, providerId, customerId, packageName, amount, dueDate, status);
        boolean success = false;
        try {
            success = invoiceDAO.createInternetInvoice(invoice);
        } catch (Exception ex) {
            Logger.getLogger(InternetInvoiceServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        response.sendRedirect(success ? "internetInvoice" : "error.jsp");
    }
}
