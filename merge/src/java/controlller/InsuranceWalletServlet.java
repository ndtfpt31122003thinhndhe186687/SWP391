/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controlller;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
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
import java.util.List;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import model.Customer;
import model.Insurance_transactions;

/**
 *
 * @author AD
 */
@WebServlet(name = "InsuranceWalletServlet", urlPatterns = {"/insuranceWallet"})
public class InsuranceWalletServlet extends HttpServlet {

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
            out.println("<title>Servlet InsuranceWalletServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet InsuranceWalletServlet at " + request.getContextPath() + "</h1>");
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
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("account");

        if (customer == null) {
            response.sendRedirect("login");
            return;
        }

        List<Insurance_transactions> transactions = new ArrayList<>();
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        int limit = 5;
        int offset = (page - 1) * limit;

        SimpleDateFormat displayFormat = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd");
    displayFormat.setLenient(false);
    java.sql.Date startDate = null;
    java.sql.Date endDate = null;
    
    boolean hasDateError = false;

    try {
        if (startDateStr != null && !startDateStr.isEmpty()) {
            java.util.Date startD = displayFormat.parse(startDateStr);
            startDate = new java.sql.Date(startD.getTime());
        }
        if (endDateStr != null && !endDateStr.isEmpty()) {
            java.util.Date endD = displayFormat.parse(endDateStr);
            endDate = new java.sql.Date(endD.getTime());
        }
    } catch (ParseException ex) {
        request.setAttribute("err", "Invalid date format! Please use dd-MM-yyyy format.");
        hasDateError = true;
        // Don't return here - continue processing to show transactions
    }

        try (Connection conn = new DBContext().getConnection()) {
            String sqlCustomer = "SELECT * FROM customer WHERE customer_id = ?";
        PreparedStatement pstmtCustomer = conn.prepareStatement(sqlCustomer);
        pstmtCustomer.setInt(1, customer.getCustomer_id());
        ResultSet rsCustomer = pstmtCustomer.executeQuery();
        if (rsCustomer.next()) {
            customer = new Customer();
            customer.setCustomer_id(rsCustomer.getInt("customer_id"));
            customer.setFull_name(rsCustomer.getString("full_name"));
        }
        
            String sql = "SELECT * FROM insurance_transactions WHERE customer_id = ?";
            if (startDate != null && endDate != null) {
                sql += " AND transaction_date BETWEEN ? AND ?";
            }
            sql += " ORDER BY transaction_date DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, customer.getCustomer_id());
            int paramIndex = 2;
            if (startDate != null && endDate != null) {
                stmt.setDate(paramIndex++, startDate);
                stmt.setDate(paramIndex++, endDate);
            }
            stmt.setInt(paramIndex++, offset);
            stmt.setInt(paramIndex, limit);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Insurance_transactions t = new Insurance_transactions();
                t.setTransaction_id(rs.getInt("transaction_id"));
                t.setContract_id(rs.getInt("contract_id"));
                t.setCustomer_id(rs.getInt("customer_id"));
                t.setTransaction_date(rs.getTimestamp("transaction_date"));
                t.setAmount(rs.getDouble("amount"));
                t.setTransaction_type(rs.getString("transaction_type"));
                t.setNotes(rs.getString("notes"));
                transactions.add(t);
            }

            // Tổng số dòng để phân trang
            String countSql = "SELECT COUNT(*) FROM insurance_transactions WHERE customer_id = ?";
            if (startDate != null && endDate != null) {
                countSql += " AND transaction_date BETWEEN ? AND ?";
            }
            PreparedStatement countStmt = conn.prepareStatement(countSql);
            countStmt.setInt(1, customer.getCustomer_id());
            if (startDate != null && endDate != null) {
                countStmt.setDate(2, startDate);
                countStmt.setDate(3, endDate);
            }
            ResultSet rsCount = countStmt.executeQuery();
            int totalRows = 0;
            if (rsCount.next()) {
                totalRows = rsCount.getInt(1);
            }
            int totalPages = (int) Math.ceil((double) totalRows / limit);

            request.setAttribute("insuranceTransactions", transactions);
            request.setAttribute("customer", customer);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("startDate", startDateStr);
            request.setAttribute("endDate", endDateStr);
            request.getRequestDispatcher("insurance_wallet.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("err", "Lỗi khi lấy dữ liệu giao dịch bảo hiểm.");
            request.getRequestDispatcher("insurance_wallet.jsp").forward(request, response);
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
            doGet(request, response);
        }
    }

    private void exportToPDF(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("account");

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Insurance_Transactions.pdf");

        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");

        SimpleDateFormat displayFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd");
        displayFormat.setLenient(false);
        java.sql.Date startDate = null;
        java.sql.Date endDate = null;

        try {
            if (startDateStr != null && !startDateStr.isEmpty()) {
                java.util.Date startD = displayFormat.parse(startDateStr);
                startDate = new java.sql.Date(startD.getTime());
            }
            if (endDateStr != null && !endDateStr.isEmpty()) {
                java.util.Date endD = displayFormat.parse(endDateStr);
                endDate = new java.sql.Date(endD.getTime());
            }
        } catch (ParseException ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date format");
            return;
        }
        List<Insurance_transactions> transactions = new ArrayList<>();

        try (Connection conn = new DBContext().getConnection()) {
            String sql = "SELECT * FROM insurance_transactions WHERE customer_id = ?";
            if (startDate != null && endDate != null) {
                sql += " AND transaction_date BETWEEN ? AND ?";
            }
            sql += " ORDER BY transaction_date DESC";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, customer.getCustomer_id());
            int paramIndex = 2;
            if (startDate != null && endDate != null) {
                stmt.setDate(paramIndex++, startDate);
                stmt.setDate(paramIndex++, endDate);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Insurance_transactions t = new Insurance_transactions();
                t.setTransaction_id(rs.getInt("transaction_id"));
                t.setContract_id(rs.getInt("contract_id"));
                t.setCustomer_id(rs.getInt("customer_id"));
                t.setTransaction_date(rs.getTimestamp("transaction_date"));
                t.setAmount(rs.getDouble("amount"));
                t.setTransaction_type(rs.getString("transaction_type"));
                t.setNotes(rs.getString("notes"));
                transactions.add(t);
            }

            Document doc = new Document();
            PdfWriter.getInstance(doc, response.getOutputStream());
            doc.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            doc.add(new Paragraph("Insurance Transactions", titleFont));
            doc.add(new Paragraph("Customer ID: " + customer.getCustomer_id()));
            doc.add(new Paragraph("Name: " + customer.getFull_name()));
            doc.add(new Paragraph("From: " + (startDateStr != null ? startDateStr : "N/A")));
            doc.add(new Paragraph("To: " + (endDateStr != null ? endDateStr : "N/A")));
            doc.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1.5f, 2f, 2f, 1.5f, 2f});

            table.addCell("Date");
            table.addCell("Type");
            table.addCell("Contract ID");
            table.addCell("Amount");
            table.addCell("Notes");

            for (Insurance_transactions t : transactions) {
                table.addCell(displayFormat.format(t.getTransaction_date()));
                table.addCell(t.getTransaction_type());
                table.addCell("#" + t.getContract_id());
                table.addCell(String.format("%.2f", t.getAmount()));
                table.addCell(t.getNotes() != null ? t.getNotes() : "");
            }

            doc.add(table);
            doc.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tạo PDF");
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
