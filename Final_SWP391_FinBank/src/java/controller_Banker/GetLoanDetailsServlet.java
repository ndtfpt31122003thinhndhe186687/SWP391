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
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Loan;


/**
 *
 * @author AD
 */
@WebServlet(name = "GetLoanDetailsServlet", urlPatterns = {"/getLoanDetails"})
public class GetLoanDetailsServlet extends HttpServlet {

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
            out.println("<title>Servlet GetLoanDetailsServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GetLoanDetailsServlet at " + request.getContextPath() + "</h1>");
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
        response.setCharacterEncoding("UTF-8");

        String loanId = request.getParameter("loan_id");

        if (loanId == null || loanId.isEmpty()) {
            response.getWriter().write("Vui lòng cung cấp loan_id.");
            return;
        }

        DBContext db = new DBContext();
        List<Loan> loanList = new ArrayList<>();

        try (Connection conn = db.getConnection()) {
            String sql = "SELECT loan_id, customer_id, serviceTerm_id, amount, start_date, end_date, " +
                         "image, notes, status, loan_type, value_asset, payment_type " +
                         "FROM loan WHERE loan_id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(loanId));

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Loan loan = new Loan();
                loan.setLoan_id(rs.getInt("loan_id"));
                loan.setCustomer_id(rs.getInt("customer_id"));
                loan.setServiceTerm_id(rs.getInt("serviceTerm_id"));
                loan.setAmount(rs.getDouble("amount"));
                loan.setStart_date(rs.getDate("start_date"));
                loan.setEnd_date(rs.getDate("end_date"));
                loan.setAsset_image(rs.getString("image"));
                loan.setNotes(rs.getString("notes"));
                loan.setStatus(rs.getString("status"));
                loan.setLoan_type(rs.getString("loan_type"));
                loan.setValue_asset(rs.getDouble("value_asset"));
                loan.setTerms(rs.getString("payment_type"));

                loanList.add(loan);
            }

            request.setAttribute("loanList", loanList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("loanDetails.jsp");
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
