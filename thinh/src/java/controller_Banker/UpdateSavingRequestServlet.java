/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_Banker;

import dal.DBContext;
import dal.SavingDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;
import model.Savings;

/**
 *
 * @author AD
 */
@WebServlet(name = "UpdateSavingRequestServlet", urlPatterns = {"/updatesavings"})
public class UpdateSavingRequestServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdateRequestCustomerServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateRequestCustomerServlet at " + request.getContextPath() + "</h1>");
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
        String savingsId = request.getParameter("savings_id");
        DBContext db = new DBContext();

        try (Connection conn = db.getConnection()) {
            // Kiểm tra xem yêu cầu có status là pending hay không
            String sql = "SELECT status FROM savings WHERE savings_id = ? AND status = 'pending'";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, savingsId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                request.setAttribute("savings_id", savingsId);
                request.getRequestDispatcher("updaterequest.jsp").forward(request, response);
            } else {
                // Nếu không tìm thấy yêu cầu pending thì chuyển hướng về trang danh sách
                response.sendRedirect("requestsaving");
            }
        } catch (Exception e) {
            e.printStackTrace();
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String savingsId = request.getParameter("savings_id");
        String status = request.getParameter("status");
        String customerId = request.getParameter("customer_id");
        String savingId_raw = request.getParameter("saving_id");
        DBContext db = new DBContext();

        // Cập nhật chỉ khi status là approved hoặc rejected
        String sql = "UPDATE savings SET status = ? WHERE savings_id = ? AND status = 'pending'";
        try (Connection conn = db.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, status);
            pstmt.setString(2, savingsId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (status.equals("approved")) {
            SavingDAO sd = new SavingDAO();
            Savings sa = sd.getSavingsById(Integer.parseInt(customerId), Integer.parseInt(savingId_raw));
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = sdf.format(sa.getStart_date());
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
            symbols.setGroupingSeparator('.');
            DecimalFormat df = new DecimalFormat("###,###", symbols);
            String formattedAmount = df.format(sa.getAmount());
            sd.insertNotification(Integer.parseInt(customerId), Integer.parseInt(savingId_raw),
                    "Gửi tiết kiệm", "Bạn đã gửi thành công số tiền " + formattedAmount + " VNĐ trong kì hạn " + sa.getDuration() + " tháng bắt đầu từ " + formattedDate);
        }

        response.sendRedirect("requestsaving");
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
