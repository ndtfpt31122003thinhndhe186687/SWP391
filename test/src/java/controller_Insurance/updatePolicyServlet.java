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
import model.Insurance;
import model.Insurance_policy;

/**
 *
 * @author Windows
 */
@WebServlet(name = "updatePolicyServlet", urlPatterns = {"/updatePolicy"})
public class updatePolicyServlet extends HttpServlet {

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
            out.println("<title>Servlet updatePolicyServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet updatePolicyServlet at " + request.getContextPath() + "</h1>");
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
        String policy_id_raw = request.getParameter("policy_id");
        int policy_id;
            policy_id= Integer.parseInt(policy_id_raw);
            Insurance_policy p = dao.getPolicyById(policy_id);
            List<String> listStatus = List.of("active", "inactive"); 
            request.setAttribute("listStatus", listStatus);
            request.setAttribute("policy", p);
            request.getRequestDispatcher("updatePolicy.jsp").forward(request, response);
        
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
        DAO_Insurance dao = new DAO_Insurance();
        HttpSession session = request.getSession();
        Insurance i = (Insurance) session.getAttribute("account");
        String policy_id_raw = request.getParameter("policy_id");
        int policy_id = Integer.parseInt(policy_id_raw);
        Insurance_policy iP = dao.getPolicyById(policy_id);
        String policy_name = request.getParameter("policy_name");
        String description = request.getParameter("description");
        String coverage_amount_raw = request.getParameter("coverage_amount");
        String premium_amount_raw = request.getParameter("premium_amount");
        String status = request.getParameter("status");
        List<String> listStatus = List.of("active", "inactive"); 
        double coverage_amount = 0, premium_amount= 0;
        try {
            coverage_amount = Double.parseDouble(coverage_amount_raw);
            premium_amount = Double.parseDouble(premium_amount_raw);
            if (coverage_amount <= 0 || premium_amount <= 0) {
                request.setAttribute("error", "Coverage Amount and Premium Amount must be greater than 0!");
               request.setAttribute("listStatus", listStatus);
                request.setAttribute("policy", iP);
                request.getRequestDispatcher("updatePolicy.jsp").forward(request, response);

            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Coverage Amount and Premium Amount must be valid numbers!");
            request.setAttribute("listStatus", listStatus);
            request.setAttribute("policy", iP);
            request.getRequestDispatcher("updatePolicy.jsp").forward(request, response);
            
        }
        
            
            Insurance_policy in = dao.getPolicyByName(policy_name);
            if (in != null && in.getPolicy_id() != policy_id) {
                request.setAttribute("error", "policy name " + policy_name + " existed!");
                request.setAttribute("listStatus", listStatus);
                request.setAttribute("policy", iP);
                request.getRequestDispatcher("updatePolicy.jsp").forward(request, response);
            } 
           
            
        Insurance_policy p = new Insurance_policy(policy_id, i.getInsurance_id(), policy_name, description, status, coverage_amount, premium_amount);
        dao.updatePolicy(p);
        String url = "managerPolicy?insurance_id=" + i.getInsurance_id();
                response.sendRedirect(url);
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
