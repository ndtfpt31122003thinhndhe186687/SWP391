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
import java.util.List;
import model.Customer;
import model.Insurance;
import model.Insurance_contract_detail;

/**
 *
 * @author Windows
 */
@WebServlet(name="calculatorInsuranceServlet", urlPatterns={"/calculatorInsurance"})
public class calculatorInsuranceServlet extends HttpServlet {
   
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
            out.println("<title>Servlet calculatorInsuranceServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet calculatorInsuranceServlet at " + request.getContextPath () + "</h1>");
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
        DAO_Insurance d = new DAO_Insurance();       
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("account");
        String insurance_id_raw = request.getParameter("insurance_id");
        int insurace_id = Integer.parseInt(insurance_id_raw);
        List<Insurance_contract_detail> cd = d.getInsuranceContractDetailByCustomerIDAndInsuranceID(customer.getCustomer_id(), insurace_id);
        request.setAttribute("listCD", cd);
        request.getRequestDispatcher("chooseInsurancePolicy.jsp").forward(request, response);
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
        DAO_Insurance d = new DAO_Insurance();
        HttpSession session = request.getSession();
        String insurance_id_raw = request.getParameter("insurance_id");
        String contract_id_raw = request.getParameter("contract_id");
        int insurace_id = Integer.parseInt(insurance_id_raw);
        int contract_id = Integer.parseInt(contract_id_raw);
        Customer customer = (Customer) session.getAttribute("account");
        Insurance_contract_detail cd = d.getInsuranceContractDetailByContractid(contract_id,insurace_id);
        double result = 0;
         String frequency = cd.getPayment_frequency(); 
        if ("monthly".equals(frequency)) {
            result = (cd.getPremiumAmount() - cd.getPaidAmount()) / cd.getDuration();
        } else if ("quarterly".equals(frequency)) {
            result = (cd.getPremiumAmount() - cd.getPaidAmount()) / cd.getDuration() * 3;
        } else if ("annually".equals(frequency)) {
            result = (cd.getPremiumAmount() - cd.getPaidAmount()) / cd.getDuration() * 12;
        }
        request.setAttribute("money", result);
        request.setAttribute("cd", cd);
        request.getRequestDispatcher("payInsurancePolicy.jsp").forward(request, response);
        
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
