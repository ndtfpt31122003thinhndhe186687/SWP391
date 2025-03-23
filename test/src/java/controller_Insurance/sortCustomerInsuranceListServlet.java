/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_Insurance;

import dal.DAO_Insurance;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.Customer;

/**
 *
 * @author Windows
 */
@WebServlet(name = "sortCustomerInsuranceListServlet", urlPatterns = {"/sortCustomerInsuranceList"})
public class sortCustomerInsuranceListServlet extends HttpServlet {

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
            out.println("<title>Servlet sortCustomerInsuranceListServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet sortCustomerInsuranceListServlet at " + request.getContextPath() + "</h1>");
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
        DAO_Insurance dao = new DAO_Insurance();
        HttpSession session = request.getSession();
        Customer c = (Customer) session.getAttribute("account");
        if (c == null) {
            response.sendRedirect("login.jsp"); // Nếu session bị mất, chuyển về trang đăng nhập
            return;
        }
        List<Customer> listC = new ArrayList<>();
        List<Customer> listL = dao.getListLoanByCustomerID(c.getCustomer_id());
        List<Customer> listI = dao.getListBuyInsuranceByCustomerID(c.getCustomer_id());
        String sort = request.getParameter("sortCustomerInsurance");
        String loan_id_raw = request.getParameter("loan_id");
        String insurance_id_raw = request.getParameter("insurance_id");
        String quantity_raw = request.getParameter("quantity");
        String offset_raw = request.getParameter("offset");
        if (sort == null) {
            sort = "none";
        }
        if (loan_id_raw == null || loan_id_raw.isEmpty()) {
            loan_id_raw = "0";
        }
        if (insurance_id_raw == null || insurance_id_raw.isEmpty()) {
            insurance_id_raw = "0";
        }
        if (quantity_raw == null || quantity_raw.isEmpty()) {
            quantity_raw = "5";
        }
        if (offset_raw == null || offset_raw.isEmpty()) {
            offset_raw = "1";
        }

        int offset = 1;
        int quantity = 5;
        int loan_id = 0;
        int insurance_id = 0;

        try {
            offset = Integer.parseInt(offset_raw);
            quantity = Integer.parseInt(quantity_raw);
            loan_id = Integer.parseInt(loan_id_raw);
            insurance_id = Integer.parseInt(insurance_id_raw);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Tham số không hợp lệ.");
    request.getRequestDispatcher("customerListInsurance.jsp").forward(request, response);
    return;
        }
        int count = 0;
        if (loan_id == 0 && insurance_id == 0) {
            count = dao.getTotalCustomerInsuranceContract();
        } else if (loan_id != 0 && insurance_id == 0) {
            count = dao.getTotalLoanByCustomerID(c.getCustomer_id(), loan_id);
        } else if (loan_id == 0 && insurance_id != 0) {
            count = dao.getTotalInsuranceByCustomerID(c.getCustomer_id(), insurance_id);
        } else if (loan_id != 0 && insurance_id != 0) {
            count = dao.getTotalInsuranceByCustomerIDAndLoanID(c.getCustomer_id(), insurance_id, loan_id);
        }
        int endPage = count / quantity;
        if (count % quantity != 0) {
            endPage++;
        }
        if (sort.equals("none")) {
            if (loan_id == 0 && insurance_id == 0) {
                listC = dao.getListInsuranceByCustomerIDAndPaganition(c.getCustomer_id(), offset, quantity);
            } else if (loan_id != 0 && insurance_id == 0) {
                listC = dao.getListInsuranceByCustomerIDAndLoanID(c.getCustomer_id(), loan_id, offset, quantity);
            } else if (loan_id == 0 && insurance_id != 0) {
                listC = dao.getListInsuranceByCustomerIDAndInsuranceID(c.getCustomer_id(), insurance_id, offset, quantity);
            } else if (loan_id != 0 && insurance_id != 0) {
                listC = dao.getListInsuranceByCustomerIDAndInsuranceIDAndLoanID(c.getCustomer_id(), insurance_id, loan_id, offset, quantity);
            }
        } else {
            if (loan_id == 0 && insurance_id == 0) {
                listC = dao.sortListInsuranceByDuration(c.getCustomer_id(), offset, quantity);
            } else if (loan_id != 0 && insurance_id == 0) {
                listC = dao.sortListInsuranceByCustomerIDAndLoanID(c.getCustomer_id(), loan_id, offset, quantity);
            } else if (loan_id == 0 && insurance_id != 0) {
                listC = dao.sortListInsuranceByCustomerIDAndInsuranceID(c.getCustomer_id(), insurance_id, offset, quantity);
            } else if (loan_id != 0 && insurance_id != 0) {
                listC = dao.sortListInsuranceByCustomerIDAndInsuranceIDAndLoanID(c.getCustomer_id(), insurance_id, loan_id, offset, quantity);
            }
        }
        request.setAttribute("listC", listC);
        request.setAttribute("listI", listI);
        request.setAttribute("listL", listL);
        request.setAttribute("sortCustomerInsurance", sort);
        request.setAttribute("loan_id", loan_id);
        request.setAttribute("insurance_id", insurance_id);
        request.setAttribute("quantity", quantity);
        request.setAttribute("endP", endPage);
        request.setAttribute("offset", offset);
        request.getRequestDispatcher("customerListInsurance.jsp").forward(request, response);

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
