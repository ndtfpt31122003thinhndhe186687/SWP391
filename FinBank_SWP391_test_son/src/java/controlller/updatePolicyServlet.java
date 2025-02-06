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
import model.Insurance_policy;

/**
 *
 * @author Windows
 */
@WebServlet(name="updatePolicyServlet", urlPatterns={"/updatePolicy"})
public class updatePolicyServlet extends HttpServlet {
   
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
            out.println("<title>Servlet updatePolicyServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet updatePolicyServlet at " + request.getContextPath () + "</h1>");
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
        String policy_id = request.getParameter("policy_id");
        int id;
        try {
            id=Integer.parseInt(policy_id);
            DAO dao = new DAO();
            Insurance_policy p = dao.getPolicyById(id);
            request.setAttribute("policy", p);
            request.getRequestDispatcher("updatePolicy.jsp").forward(request, response);
        } catch (Exception e) {
        }
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
        DAO dao = new DAO();
        HttpSession session = request.getSession();
        Insurance i = (Insurance) session.getAttribute("account");
        int insurance_Id = i.getInsurance_id();
        String policy_id = request.getParameter("policy_id");
        String policy_name = request.getParameter("policy_name");
        String description = request.getParameter("description");
        String coverage_amount = request.getParameter("coverage_amount");
        String premium_amount = request.getParameter("premium_amount");
        String status = request.getParameter("status");
        double Coverage_Amount,Preminum_Amount;
        int id;
        try {
            id = Integer.parseInt(policy_id);
            Coverage_Amount = Double.parseDouble(coverage_amount);
            Preminum_Amount = Double.parseDouble(premium_amount);
            dao.updatePolicy(id, policy_name, description, Coverage_Amount, Preminum_Amount, status);
            String url = "managerPolicy?insurance_id=" + i.getInsurance_id(); 
        response.sendRedirect(url);
        } catch (Exception e) {
        }
        
        
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
