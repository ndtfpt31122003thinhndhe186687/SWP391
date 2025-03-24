/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_Banker;

import dal.DBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Debt_management;

/**
 *
 * @author AD
 */
@WebServlet(name = "UpdateDebtServlet", urlPatterns = {"/updatedebtmanagement"})
public class UpdateDebtServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdateDebtServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateDebtServlet at " + request.getContextPath() + "</h1>");
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
        int debtId = Integer.parseInt(request.getParameter("id"));
        DBContext db = new DBContext();
        Debt_management debt = null;

        try (Connection conn = db.getConnection()) {
            String sql = "SELECT debt_status, overdue_days, notes FROM debt_management WHERE debt_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, debtId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                debt = new Debt_management();
                debt.setDebt_id(debtId);
                debt.setDebt_status(rs.getString("debt_status"));
                debt.setOverdue_days(rs.getInt("overdue_days"));
                debt.setNotes(rs.getString("notes"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("debt", debt);
        request.getRequestDispatcher("updatedebtmanagement.jsp").forward(request, response);
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
        int debtId = Integer.parseInt(request.getParameter("debt_id"));
        String debtStatus = request.getParameter("debt_status");
        int overdueDays = Integer.parseInt(request.getParameter("overdue_days"));
        String notes = request.getParameter("notes");

        DBContext db = new DBContext();

        try (Connection conn = db.getConnection()) {
            String sql = "UPDATE debt_management SET debt_status = ?, overdue_days = ?, notes = ? WHERE debt_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, debtStatus);
            pstmt.setInt(2, overdueDays);
            pstmt.setString(3, notes);
            pstmt.setInt(4, debtId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("listdebt");
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
