package controlller_Accountant;

import dal.InvoiceDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ElectricityInvoice;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ElectricityInvoiceServlet", urlPatterns = {"/electricityInvoice"})
public class ElectricityInvoiceServlet extends HttpServlet {
    private InvoiceDAO invoiceDAO;

    @Override
    public void init() {
        invoiceDAO = new InvoiceDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "list":
            {
                try {
                    listInvoices(request, response);
                } catch (Exception ex) {
                    Logger.getLogger(ElectricityInvoiceServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                break;

            case "delete":
            {
                try {
                    deleteInvoice(request, response);
                } catch (Exception ex) {
                    Logger.getLogger(ElectricityInvoiceServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                break;

            default:
                response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "create":
            {
                try {
                    createInvoice(request, response);
                } catch (Exception ex) {
                    Logger.getLogger(ElectricityInvoiceServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                break;

            case "update":
            {
                try {
                    updateInvoice(request, response);
                } catch (Exception ex) {
                    Logger.getLogger(ElectricityInvoiceServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                break;

            default:
                response.sendRedirect("error.jsp");
        }
    }

    private void listInvoices(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception {
        List<ElectricityInvoice> invoices = invoiceDAO.getAllElectricityInvoices();
        request.setAttribute("electricityInvoices", invoices);
        request.getRequestDispatcher("electricityInvoice.jsp").forward(request, response);
    }

    private void createInvoice(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
        int providerId = Integer.parseInt(request.getParameter("providerId"));
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        double consumptionKWh = Double.parseDouble(request.getParameter("consumptionKWh"));
        double amount = Double.parseDouble(request.getParameter("amount"));
        Date dueDate = Date.valueOf(request.getParameter("dueDate"));
        String status = "pending";

        ElectricityInvoice invoice = new ElectricityInvoice(0, providerId, customerId, consumptionKWh, amount, dueDate, status);
        boolean success = invoiceDAO.createElectricityInvoice(invoice);

        response.sendRedirect(success ? "electricityInvoice?action=list" : "error.jsp");
    }

    private void updateInvoice(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
        int invoiceId = Integer.parseInt(request.getParameter("invoiceId"));
        int providerId = Integer.parseInt(request.getParameter("providerId"));
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        double consumptionKWh = Double.parseDouble(request.getParameter("consumptionKWh"));
        double amount = Double.parseDouble(request.getParameter("amount"));
        Date dueDate = Date.valueOf(request.getParameter("dueDate"));
        String status = request.getParameter("status");

        ElectricityInvoice invoice = new ElectricityInvoice(invoiceId, providerId, customerId, consumptionKWh, amount, dueDate, status);
        boolean success = invoiceDAO.updateElectricityInvoice(invoice);

        response.sendRedirect(success ? "electricityInvoice?action=list" : "error.jsp");
    }

    private void deleteInvoice(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
        int invoiceId = Integer.parseInt(request.getParameter("invoiceId"));
        boolean success = invoiceDAO.deleteElectricityInvoice(invoiceId);
        response.sendRedirect(success ? "electricityInvoice?action=list" : "error.jsp");
    }

}
