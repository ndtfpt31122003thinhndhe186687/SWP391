package servlet;

import dal.InvoiceDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ElectricityInvoice;
import model.WaterInvoice;
import model.InternetInvoice;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/AutoInvoiceServlet")
public class AutoInvoiceServlet extends HttpServlet {
    private final InvoiceDAO invoiceDAO = new InvoiceDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Lấy ngày hiện tại
            LocalDate today = LocalDate.now();
            
            // Chỉ gửi hóa đơn từ ngày 1 - 5 hàng tháng
            if (today.getDayOfMonth() >= 1 && today.getDayOfMonth() <= 5) {
                Date startDate = Date.valueOf(today.withDayOfMonth(1));
                Date endDate = Date.valueOf(today.withDayOfMonth(5));

                // Gửi hóa đơn cho khách hàng
                sendInvoices(startDate, endDate);

                response.getWriter().write("Invoices sent successfully!");
            } else {
                response.getWriter().write("Not in the allowed date range (1-5). No invoices sent.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error sending invoices: " + e.getMessage());
        }
    }

    private void sendInvoices(Date startDate, Date endDate) throws Exception {
        // Lấy danh sách hóa đơn điện chưa thanh toán
        List<ElectricityInvoice> electricityInvoices = invoiceDAO.getAllElectricityInvoices();
        for (ElectricityInvoice invoice : electricityInvoices) {
            if (invoice.getDueDate().after(startDate) && invoice.getDueDate().before(endDate)) {
                invoiceDAO.updateElectricityInvoice(invoice);
            }
        }

        // Lấy danh sách hóa đơn nước chưa thanh toán
        List<WaterInvoice> waterInvoices = invoiceDAO.getAllWaterInvoices();
        for (WaterInvoice invoice : waterInvoices) {
            if (invoice.getDueDate().after(startDate) && invoice.getDueDate().before(endDate)) {
                invoiceDAO.updateWaterInvoice(invoice);
            }
        }

        // Lấy danh sách hóa đơn internet chưa thanh toán
        List<InternetInvoice> internetInvoices = invoiceDAO.getAllInternetInvoices();
        for (InternetInvoice invoice : internetInvoices) {
            if (invoice.getDueDate().after(startDate) && invoice.getDueDate().before(endDate)) {
                invoiceDAO.updateInternetInvoice(invoice);
            }
        }
    }
}
