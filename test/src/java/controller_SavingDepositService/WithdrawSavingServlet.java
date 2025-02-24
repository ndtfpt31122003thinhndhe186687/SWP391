/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_SavingDepositService;

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

/**
 *
 * @author Acer Nitro Tiger
 */
@WebServlet(name = "WithdrawSavingServlet", urlPatterns = {"/withdrawSaving"})
public class WithdrawSavingServlet extends HttpServlet {

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
            out.println("<title>Servlet WithdrawSavingServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet WithdrawSavingServlet at " + request.getContextPath() + "</h1>");
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
        String savingId_raw = request.getParameter("saving_id");
        int savingId = Integer.parseInt(savingId_raw);
        SavingDAO d = new SavingDAO();
        HttpSession session = request.getSession();
        // Kiểm tra xem khoản tiết kiệm đã đến hạn chưa
        boolean isMatured = d.checkMaturity(savingId);
        if (isMatured) {
            d.getInterest(savingId);
            d.withdrawSavings(savingId);
            session.setAttribute("successMessage", "Rút tiền thành công! Hãy kiểm tra số dư tài khoản!");
            response.sendRedirect("home");
        } else {
            request.setAttribute("error", "Khoản tiết kiệm chưa đến hạn, không thể rút!");
            Customer c = (Customer) session.getAttribute("account");
            List<Savings> savingList = d.getAllDepositSavingsOfUser(c.getCustomer_id());
            request.setAttribute("savingsList", savingList);

            // Tính tổng tiền sau khi có lãi suất
            Map<Integer, Double> accruedInterestMap = new HashMap<>();
            for (Savings s : savingList) {
                // Tính tổng tiền sau khi có lãi suất
                double totalWithInterest = d.getSavingDeposit(s.getSavings_id());
                accruedInterestMap.put(s.getSavings_id(), totalWithInterest);
            }
            request.setAttribute("accruedInterestMap", accruedInterestMap);
            request.getRequestDispatcher("depositSaving.jsp").forward(request, response);
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
