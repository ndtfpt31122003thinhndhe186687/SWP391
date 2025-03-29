package controlller_Accountant;

import dal.InvoiceDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.WaterInvoice;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "WaterInvoiceServlet", urlPatterns = {"/waterInvoice"})
public class WaterInvoiceServlet extends HttpServlet {
    private InvoiceDAO invoiceDAO;

    @Override
    public void init() {
        invoiceDAO = new InvoiceDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<WaterInvoice> invoices = null;
        try {
            invoices = invoiceDAO.getAllWaterInvoices();
        } catch (Exception ex) {
            Logger.getLogger(WaterInvoiceServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("waterInvoices", invoices);
        request.getRequestDispatcher("waterInvoiceList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int providerId = Integer.parseInt(request.getParameter("providerId"));
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        double consumptionM3 = Double.parseDouble(request.getParameter("consumptionM3"));
        double amount = Double.parseDouble(request.getParameter("amount"));
        Date dueDate = Date.valueOf(request.getParameter("dueDate"));
        String status = "pending";

        WaterInvoice invoice = new WaterInvoice(0, providerId, customerId, consumptionM3, amount, dueDate, status);
        boolean success = false;
        try {
            success = invoiceDAO.createWaterInvoice(invoice);
        } catch (Exception ex) {
            Logger.getLogger(WaterInvoiceServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        response.sendRedirect(success ? "waterInvoice" : "error.jsp");
    }

}
