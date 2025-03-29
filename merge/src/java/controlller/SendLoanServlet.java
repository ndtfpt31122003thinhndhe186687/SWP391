/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controlller;

import dal.DBContext;
import dal.SavingDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import model.Customer;
import model.ServiceTerms;

/**
 *
 * @author AD
 */
@WebServlet(name = "SendLoanServlet", urlPatterns = {"/SendLoanReq"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class SendLoanServlet extends HttpServlet {
    

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
            out.println("<title>Servlet SendLoanRequestServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SendLoanRequestServlet at " + request.getContextPath() + "</h1>");
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
        List<ServiceTerms> loanTerms = new ArrayList<>();
        DBContext db = new DBContext();

        try (Connection conn = db.getConnection()) {
            String sql = """
                SELECT st.serviceTerm_id, st.term_name, t.duration, st.interest_rate
                FROM service_terms st
                JOIN term t ON st.term_id = t.term_id
                WHERE st.service_id = 2 AND st.status = 'active'
            """;

            try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ServiceTerms term = new ServiceTerms(
                        rs.getInt("serviceTerm_id"),
                        rs.getString("term_name"),
                        rs.getDouble("interest_rate"),
                        rs.getInt("duration")
                    );
                    loanTerms.add(term);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("loanTerms", loanTerms);
        RequestDispatcher dispatcher = request.getRequestDispatcher("formunsecuredloan.jsp");
        dispatcher.forward(request, response);
    }


    private boolean isValid(String param) {
        return param != null && !param.trim().isEmpty();
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
    try {
        HttpSession session = request.getSession();
        Customer c = (Customer) session.getAttribute("account");
        if (c == null) {
            request.setAttribute("error", "Không tìm thấy thông tin khách hàng. Vui lòng đăng nhập lại.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("formunsecuredloan.jsp");
            dispatcher.forward(request, response);
            return;
        }

        int customerId = c.getCustomer_id();
        String serviceTermIdStr = request.getParameter("serviceTerm_id");
        String salaryStr = request.getParameter("value");
        Part filePart = request.getPart("pdfFile");
        String amountStr = request.getParameter("amount");

        if (filePart == null || filePart.getSize() == 0) {
            request.setAttribute("error", "Vui lòng tải lên tệp PDF hợp lệ.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("formunsecuredloan.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Kiểm tra loại tệp tải lên (chỉ chấp nhận PDF)
        String fileName = filePart.getSubmittedFileName();
        if (!fileName.endsWith(".pdf")) {
            request.setAttribute("error", "Chỉ chấp nhận tệp PDF.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("formunsecuredloan.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Kiểm tra các trường hợp đầu vào khác (serviceTermId, salary, amount)
        if (!isValid(serviceTermIdStr) || !isValid(salaryStr) || !isValid(amountStr)) {
            request.setAttribute("error", "Dữ liệu nhập vào không hợp lệ. Vui lòng kiểm tra lại.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("formunsecuredloan.jsp");
            dispatcher.forward(request, response);
            return;
        }

        int serviceTermId = Integer.parseInt(serviceTermIdStr);
        double salary = Double.parseDouble(salaryStr);
        double amount = Double.parseDouble(amountStr);
        
        if (salary <= 0) {
            request.setAttribute("error", "Lương phải lớn hơn 0.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("formunsecuredloan.jsp");
            dispatcher.forward(request, response);
            return;
        }

        if (amount <= 0) {
            request.setAttribute("error", "Số tiền vay phải lớn hơn 0.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("formunsecuredloan.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Lấy số tháng vay từ bảng `term`
        int duration = 0;
        DBContext db = new DBContext();
        try (Connection conn = db.getConnection()) {
            String sqlDuration = """
                SELECT t.duration FROM service_terms st
                JOIN term t ON st.term_id = t.term_id
                WHERE st.serviceTerm_id = ?
            """;
            PreparedStatement stmt = conn.prepareStatement(sqlDuration);
            stmt.setInt(1, serviceTermId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                duration = rs.getInt("duration");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Tính số tiền có thể vay tối đa
        double maxLoanAmount = (salary * 0.5) * duration;

        if (amount > maxLoanAmount) {
            request.setAttribute("error", "Số tiền vay không được vượt quá " + maxLoanAmount + " VND.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("formunsecuredloan.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Lưu trữ tệp PDF
        String uploadPath = getServletContext().getRealPath("/uploads");
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();
        String filePath = uploadPath + File.separator + fileName;
        filePart.write(filePath);

        try (Connection conn = db.getConnection()) {
            conn.setAutoCommit(false);

            // Insert loan vào bảng loan
            String sqlLoan = """
                    INSERT INTO loan (customer_id, serviceTerm_id, amount, start_date, end_date, image, status, loan_type, value_asset)
                    VALUES (?, ?, ?, GETDATE(), DATEADD(MONTH, (SELECT duration FROM term WHERE term_id = (SELECT term_id FROM service_terms WHERE serviceTerm_id = ?)), GETDATE()), ?, 'pending', 'unsecured', ?)
                """;
            PreparedStatement loanStmt = conn.prepareStatement(sqlLoan, Statement.RETURN_GENERATED_KEYS);
            loanStmt.setInt(1, customerId);
            loanStmt.setInt(2, serviceTermId);
            loanStmt.setDouble(3, amount);
            loanStmt.setInt(4, serviceTermId);
            loanStmt.setString(5, fileName);
            loanStmt.setDouble(6, salary);
            loanStmt.executeUpdate();

            // Lấy loan_id vừa được tạo
            ResultSet generatedKeys = loanStmt.getGeneratedKeys();
            int loanId = 0;
            if (generatedKeys.next()) {
                loanId = generatedKeys.getInt(1);
            }

            conn.commit();

            request.setAttribute("successMessage", "Đơn vay của bạn đã được gửi thành công!");
            //Thong bao
            SavingDAO sd=new SavingDAO();
            sd.insertNotification(customerId, loanId, "Vay", "Đơn vay tín chấp của bạn đã được gửi thành công!");
            RequestDispatcher dispatcher = request.getRequestDispatcher("formunsecuredloan.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi SQL: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("formunsecuredloan.jsp");
            dispatcher.forward(request, response);
        }
    } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
        RequestDispatcher dispatcher = request.getRequestDispatcher("formunsecuredloan.jsp");
        dispatcher.forward(request, response);
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
