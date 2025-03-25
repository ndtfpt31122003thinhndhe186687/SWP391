/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controlller;

import dal.DBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import model.Transaction;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import model.Customer;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dal.DAO;
import model.Notifications;
/**
 *
 * @author AD
 */
@WebServlet(name = "WalletServlet", urlPatterns = {"/wallet"})
public class WalletServlet extends HttpServlet {
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet WalletServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet WalletServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    Customer c = (Customer) session.getAttribute("account");
    DBContext db = new DBContext();
    Customer customer = null;
    List<Transaction> transactions = new ArrayList<>();

    // Lấy tham số
    String startDateStr = request.getParameter("startDate");
    String endDateStr = request.getParameter("endDate");
    int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
    int limit = 5;
    int offset = (page - 1) * limit;

    // Định dạng ngày
    SimpleDateFormat displayFormat = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd");
    displayFormat.setLenient(false);
    java.sql.Date startDate = null;
    java.sql.Date endDate = null;
    
    boolean hasDateError = false;

    try {
        if (startDateStr != null && !startDateStr.isEmpty()) {
            Date startD = displayFormat.parse(startDateStr);
            startDate = new java.sql.Date(startD.getTime());
        }
        if (endDateStr != null && !endDateStr.isEmpty()) {
            Date endD = displayFormat.parse(endDateStr);
            endDate = new java.sql.Date(endD.getTime());
        }
    } catch (ParseException ex) {
        request.setAttribute("err", "Invalid date format! Please use dd-MM-yyyy format.");
        hasDateError = true;
        // Don't return here - continue processing to show transactions
    }

    try (Connection conn = db.getConnection()) {
        // Lấy thông tin khách hàng
        String sqlCustomer = "SELECT * FROM customer WHERE customer_id = ?";
        PreparedStatement pstmtCustomer = conn.prepareStatement(sqlCustomer);
        pstmtCustomer.setInt(1, c.getCustomer_id());
        ResultSet rsCustomer = pstmtCustomer.executeQuery();
        if (rsCustomer.next()) {
            customer = new Customer();
            customer.setCustomer_id(rsCustomer.getInt("customer_id"));
            customer.setFull_name(rsCustomer.getString("full_name"));
        }

        // Lấy danh sách giao dịch
        String transactionSql = "SELECT * FROM transactions WHERE customer_id = ?";
        if (startDate != null && endDate != null && !hasDateError) {
            transactionSql += " AND transaction_date BETWEEN ? AND ?";
        }
        transactionSql += " ORDER BY transaction_date DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        PreparedStatement pstmtTransaction = conn.prepareStatement(transactionSql);
        pstmtTransaction.setInt(1, c.getCustomer_id());
        int paramIndex = 2;
        if (startDate != null && endDate != null && !hasDateError) {
            pstmtTransaction.setDate(paramIndex++, startDate);
            pstmtTransaction.setDate(paramIndex++, endDate);
        }
        pstmtTransaction.setInt(paramIndex++, offset);
        pstmtTransaction.setInt(paramIndex, limit);

        ResultSet rsTransaction = pstmtTransaction.executeQuery();
        while (rsTransaction.next()) {
            Transaction ts = new Transaction();
            ts.setTransaction_id(rsTransaction.getInt("transaction_id"));
            ts.setService_id(rsTransaction.getInt("service_id"));
            ts.setAmount(rsTransaction.getDouble("amount"));
            Date sqlDate = rsTransaction.getDate("transaction_date");
            String formattedDate = displayFormat.format(sqlDate);
            ts.setTransaction_date(sqlDate);
            ts.setFormatted_transaction_date(formattedDate);
            ts.setCustomer_id(rsTransaction.getInt("customer_id"));
            ts.setTransaction_type(rsTransaction.getString("transaction_type"));
            transactions.add(ts);
        }

        // Tính tổng số giao dịch
        String countSql = "SELECT COUNT(*) FROM transactions WHERE customer_id = ?";
        if (startDate != null && endDate != null && !hasDateError) {
            countSql += " AND transaction_date BETWEEN ? AND ?";
        }
        PreparedStatement pstmtCount = conn.prepareStatement(countSql);
        pstmtCount.setInt(1, c.getCustomer_id());
        if (startDate != null && endDate != null && !hasDateError) {
            pstmtCount.setDate(2, startDate);
            pstmtCount.setDate(3, endDate);
        }
        ResultSet rsCount = pstmtCount.executeQuery();
        int totalTransactions = 0;
        if (rsCount.next()) {
            totalTransactions = rsCount.getInt(1);
        }
        int totalPages = (int) Math.ceil((double) totalTransactions / limit);

        // Gửi dữ liệu đến JSP
        request.setAttribute("customer", customer);
        request.setAttribute("transactions", transactions);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        //Thong bao
        DAO d = new DAO();
        List<Notifications> listNotify = d.getAllNotificationsByCustomerId(c.getCustomer_id());
        request.setAttribute("listNotify", listNotify);
        request.getRequestDispatcher("wallet.jsp").forward(request, response);

    } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute("err", "Lỗi hệ thống!");
        request.getRequestDispatcher("wallet.jsp").forward(request, response);
    }
}

    
   

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("exportPDF".equals(action)) {
            exportToPDF(request, response);
        } else {
            processRequest(request, response);
        }
    }

    private void exportToPDF(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Customer c = (Customer) session.getAttribute("account");

        // Thiết lập response để xuất file PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"Transaction_History.pdf\"");

        // Lấy tham số ngày
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");

        SimpleDateFormat displayFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd");
        displayFormat.setLenient(false);
        java.sql.Date startDate = null;
        java.sql.Date endDate = null;

        try {
            if (startDateStr != null && !startDateStr.isEmpty()) {
                Date startD = displayFormat.parse(startDateStr);
                startDate = new java.sql.Date(startD.getTime());
            }
            if (endDateStr != null && !endDateStr.isEmpty()) {
                Date endD = displayFormat.parse(endDateStr);
                endDate = new java.sql.Date(endD.getTime());
            }
        } catch (ParseException ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date format");
            return;
        }

        DBContext db = new DBContext();
        Customer customer = null;
        List<Transaction> transactions = new ArrayList<>();

        try (Connection conn = db.getConnection()) {
            // Lấy thông tin khách hàng
            String sqlCustomer = "SELECT * FROM customer WHERE customer_id = ?";
            PreparedStatement pstmtCustomer = conn.prepareStatement(sqlCustomer);
            pstmtCustomer.setInt(1, c.getCustomer_id());
            ResultSet rsCustomer = pstmtCustomer.executeQuery();
            if (rsCustomer.next()) {
                customer = new Customer();
                customer.setCustomer_id(rsCustomer.getInt("customer_id"));
                customer.setFull_name(rsCustomer.getString("full_name"));
            }

            // Lấy danh sách giao dịch (không phân trang trong PDF)
            String transactionSql = "SELECT * FROM transactions WHERE customer_id = ?";
            if (startDate != null && endDate != null) {
                transactionSql += " AND transaction_date BETWEEN ? AND ?";
            }
            transactionSql += " ORDER BY transaction_date DESC";

            PreparedStatement pstmtTransaction = conn.prepareStatement(transactionSql);
            pstmtTransaction.setInt(1, c.getCustomer_id());
            int paramIndex = 2;
            if (startDate != null && endDate != null) {
                pstmtTransaction.setDate(paramIndex++, startDate);
                pstmtTransaction.setDate(paramIndex++, endDate);
            }

            ResultSet rsTransaction = pstmtTransaction.executeQuery();
            while (rsTransaction.next()) {
                Transaction ts = new Transaction();
                ts.setTransaction_id(rsTransaction.getInt("transaction_id"));
                ts.setService_id(rsTransaction.getInt("service_id"));
                ts.setAmount(rsTransaction.getDouble("amount"));
                Date sqlDate = rsTransaction.getDate("transaction_date");
                String formattedDate = displayFormat.format(sqlDate);
                ts.setTransaction_date(sqlDate);
                ts.setFormatted_transaction_date(formattedDate);
                ts.setCustomer_id(rsTransaction.getInt("customer_id"));
                ts.setTransaction_type(rsTransaction.getString("transaction_type"));
                transactions.add(ts);
            }

            // Tạo file PDF
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // Tiêu đề
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Transaction History", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Thông tin khách hàng
            Font infoFont = new Font(Font.FontFamily.HELVETICA, 12);
            document.add(new Paragraph("Customer ID: " + customer.getCustomer_id(), infoFont));
            document.add(new Paragraph("Customer Name: " + customer.getFull_name(), infoFont));
            document.add(new Paragraph("From: " + (startDateStr != null ? startDateStr : "N/A"), infoFont));
            document.add(new Paragraph("To: " + (endDateStr != null ? endDateStr : "N/A"), infoFont));
            document.add(new Paragraph(" ")); // Khoảng trống

            // Bảng giao dịch
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1.5f, 2f, 2f, 1.5f, 1.5f});

            // Tiêu đề bảng
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            table.addCell(new PdfPCell(new Phrase("Date", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Type", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Description", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Amount", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Time", headerFont)));

            // Dữ liệu giao dịch
            Font cellFont = new Font(Font.FontFamily.HELVETICA, 10);
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            for (Transaction ts : transactions) {
                table.addCell(new PdfPCell(new Phrase(ts.getFormatted_transaction_date(), cellFont)));
                table.addCell(new PdfPCell(new Phrase(ts.getTransaction_type(), cellFont)));
                table.addCell(new PdfPCell(new Phrase("Transaction ID: " + ts.getTransaction_id(), cellFont)));
                String amount = ts.getTransaction_type().equals("withdrawal") ?
                        "-" + Math.abs(ts.getAmount()) + " $" : "+" + Math.abs(ts.getAmount()) + " $";
                table.addCell(new PdfPCell(new Phrase(amount, cellFont)));
                table.addCell(new PdfPCell(new Phrase(timeFormat.format(ts.getTransaction_date()), cellFont)));
            }

            document.add(table);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating PDF");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
