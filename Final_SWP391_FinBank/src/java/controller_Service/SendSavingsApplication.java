/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_Service;

import dal.SavingDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import model.Customer;
import model.Savings;
import model.ServiceTerms;

/**
 *
 * @author Acer Nitro Tiger
 */
@WebServlet(name = "SendSavingsApplication", urlPatterns = {"/sendSavingsApplication"})
public class SendSavingsApplication extends HttpServlet {

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
            out.println("<title>Servlet SendSavingsApplication</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SendSavingsApplication at " + request.getContextPath() + "</h1>");
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
        if (c == null) {
            // Chưa đăng nhập, chuyển hướng đến trang login
            response.sendRedirect("login.jsp");
            return;
        }
        List<ServiceTerms> listS = d.getDepositService();
        request.setAttribute("listS", listS);
        request.getRequestDispatcher("savingsApplication.jsp").forward(request, response);
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
        String customerId_raw = request.getParameter("customer_id");
        String serviceTermId_raw = request.getParameter("serviceTerm_id");
        String amount_raw = request.getParameter("amount");
        String note = request.getParameter("note");
        HttpSession session = request.getSession();
        SavingDAO d = new SavingDAO();
        try {
            int customerId = Integer.parseInt(customerId_raw);
            int serviceTermId = Integer.parseInt(serviceTermId_raw);
            double amount = Double.parseDouble(amount_raw);

            int duration = d.getDurationByServiceTermId(serviceTermId);
            // Lấy thời gian hiện tại làm start_date
            Date startDate = new Date();
            // Tính end_date (start_date + duration tháng)
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            cal.add(Calendar.MONTH, duration);
            Date endDate = cal.getTime();
            Savings s = new Savings(customerId, serviceTermId, amount, startDate, endDate, note);
            int id = d.insertSavings(s);
            if (id > 0) {
                String message = "Bạn đã tạo đơn gửi tiết kiệm thành công! Hãy đợi kiểm duyệt từ phía ngân hàng!";
                d.insertNotification(customerId, id, "Gửi tiết kiệm", message);
                session.setAttribute("successMessage", message);
            }
            response.sendRedirect("home");
        } catch (Exception e) {
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
