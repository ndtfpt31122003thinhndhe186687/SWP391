/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controlller_Accountant;

import dal.CustomerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Customer;

/**
 *
 * @author default
 */
@WebServlet(name = "CreditCardServlet", urlPatterns = {"/registerCreditCard"})
public class CreditCardServlet extends HttpServlet {

    private CustomerDAO customerDAO;

    @Override
    public void init() {
        customerDAO = new CustomerDAO();
    }

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
            out.println("<title>Servlet CreditCardServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreditCardServlet at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("registerCredit.jsp").forward(request, response);
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
        Customer account = (Customer) request.getSession().getAttribute("account");
        if (account == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int customerId = account.getCustomer_id();
        String salaryStr = request.getParameter("salary").replace(",", ""); // Loại bỏ dấu "," trước khi parse

        try {
            double salary = Double.parseDouble(salaryStr);
            int dueDate = Integer.parseInt(request.getParameter("credit_due_date"));
            boolean success = customerDAO.registerCreditCard(customerId, salary, dueDate);
            if (success) {
                account.setCredit_limit(salary * 2); // Cập nhật hạn mức ngay lập tức trong session
                account.setCredit_due_date(dueDate);
                account.setSalary(salary);
                request.getSession().setAttribute("account", account);
                request.setAttribute("message", "Thẻ tín dụng đã được cấp với hạn mức VNĐ" + (salary * 2));
            } else {
                request.setAttribute("error", "Có lỗi xảy ra khi đăng ký thẻ tín dụng");
            }
        } catch (Exception ex) {
            request.setAttribute("error", "Lỗi: " + ex.getMessage());
        }

        request.getRequestDispatcher("registerCredit.jsp").forward(request, response);
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
