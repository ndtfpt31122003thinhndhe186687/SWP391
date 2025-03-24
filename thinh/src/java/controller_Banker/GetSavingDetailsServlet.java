/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_Banker;

import dal.DBContext;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Loan;
import model.Savings;

/**
 *
 * @author AD
 */
@WebServlet(name = "GetSavingDetailsServlet", urlPatterns = {"/getsaving"})
public class GetSavingDetailsServlet extends HttpServlet {

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
            out.println("<title>Servlet GetSavingDetailsServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GetSavingDetailsServlet at " + request.getContextPath() + "</h1>");
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
        response.setCharacterEncoding("UTF-8");

        String savingsId = request.getParameter("savings_id");

        if (savingsId == null || savingsId.isEmpty()) {
            response.getWriter().write("Vui lòng cung cấp savings_id.");
            return;
        }

        DBContext db = new DBContext();
        List<Savings> savingsList = new ArrayList<>();

        try (Connection conn = db.getConnection()) {
            String sql = """
                SELECT savings_id, customer_id, serviceTerm_id, amount, start_date, end_date, status, notes 
                FROM savings WHERE savings_id = ?
            """;

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(savingsId));

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Savings savings = new Savings();
                savings.setSavings_id(rs.getInt("savings_id"));
                savings.setCustomer_id(rs.getInt("customer_id"));
                savings.setServiceTerm_id(rs.getInt("serviceTerm_id"));
                savings.setAmount(rs.getDouble("amount"));
                savings.setStart_date(rs.getDate("start_date"));
                savings.setEnd_date(rs.getDate("end_date"));
                savings.setStatus(rs.getString("status"));
                savings.setNotes(rs.getString("notes"));

                savingsList.add(savings);
            }

            request.setAttribute("savingsList", savingsList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("savingsDetails.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Lỗi khi lấy dữ liệu.");
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
