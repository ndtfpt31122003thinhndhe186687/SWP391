/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controlller;

import dal.DAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Insurance;

/**
 *
 * @author Windows
 */
@WebServlet(name="AddInsuranceContractServlet", urlPatterns={"/addInsuranceContract"})
public class AddInsuranceContractServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet AddInsuranceContractServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddInsuranceContractServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       String customer_id = request.getParameter("customer_id");
        String service_id = request.getParameter("service_id");
        
        String start_date = request.getParameter("start_date");
        String end_date = request.getParameter("end_date");
        String payment_frequency = request.getParameter("payment_frequency");
        String status = request.getParameter("status");
        int customer_Id = Integer.parseInt(customer_id);
        int service_Id = Integer.parseInt(service_id);
                HttpSession session = request.getSession();
        Insurance i = (Insurance) session.getAttribute("account");
        int policy_Id = i.getPolicy_id();
        DAO dao = new DAO();
        dao.insertInsuranceContract(customer_Id, service_Id, policy_Id, start_date, end_date, payment_frequency, status);

        String url = "managerInsuranceContract?policy_id=" + i.getPolicy_id();
        response.sendRedirect(url);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
