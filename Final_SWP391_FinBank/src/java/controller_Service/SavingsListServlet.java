/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_SavingDepositService;

import dal.DAO;
import dal.DBContext;
import dal.SavingDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Customer;
import model.Savings;
import java.sql.*;
import model.Notifications;

/**
 *
 * @author Acer Nitro Tiger
 */
@WebServlet(name = "SavingsListServlet", urlPatterns = {"/savingList"})
public class SavingsListServlet extends HttpServlet {

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
            out.println("<title>Servlet SavingsListServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SavingsListServlet at " + request.getContextPath() + "</h1>");
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
        SavingDAO d = new SavingDAO();
        HttpSession session = request.getSession();
        Customer c = (Customer) session.getAttribute("account");
        List<Savings> savingList = d.getAllDepositSavingsOfUser(c.getCustomer_id());
        request.setAttribute("savingsList", savingList);

        // Lấy thông tin VIP
        double savingsRateBonus = 0.0;
        Timestamp vipStartDate = null;
        String vipSql = "SELECT v.start_date, vt.savings_rate FROM vip v "
                + "JOIN vip_term vt ON v.vipTerm_id = vt.vipTerm_id "
                + "WHERE v.customer_id = ? AND v.status = 'active'";

        try (Connection conn = new DBContext().getConnection(); PreparedStatement pstmt = conn.prepareStatement(vipSql)) {
            pstmt.setInt(1, c.getCustomer_id());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                vipStartDate = rs.getTimestamp("start_date");
                savingsRateBonus = rs.getDouble("savings_rate");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Tính tổng tiền tiết kiệm với lãi suất VIP nếu có
        Map<Integer, Double> accruedInterestMap = new HashMap<>();
        for (Savings s : savingList) {
            double totalWithInterest = d.getSavingDeposit(s.getSavings_id());
            accruedInterestMap.put(s.getSavings_id(), totalWithInterest);
        }

        request.setAttribute("accruedInterestMap", accruedInterestMap);
        request.setAttribute("savingsRateBonus", savingsRateBonus);
        request.setAttribute("vipStartDate", vipStartDate);

        double totalSavings = 0;
        for (Savings saving : savingList) {
            totalSavings += saving.getAmount();
        }
        request.setAttribute("totalSavings", totalSavings);
        DAO dao = new DAO();
        List<Notifications> listNotify = dao.getAllNotificationsByCustomerId(c.getCustomer_id());
        request.setAttribute("listNotify", listNotify);

        request.getRequestDispatcher("depositSavingList.jsp").forward(request, response);
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
        processRequest(request, response);
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
