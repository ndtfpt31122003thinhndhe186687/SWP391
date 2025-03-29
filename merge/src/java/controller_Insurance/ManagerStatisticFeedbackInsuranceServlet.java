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
import model.Insurance;

/**
 *
 * @author Windows
 */
@WebServlet(name="ManagerStatisticFeedbackInsuranceServlet", urlPatterns={"/ManagerStatisticFeedbackInsurance"})
public class ManagerStatisticFeedbackInsuranceServlet extends HttpServlet {
   
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
            out.println("<title>Servlet ManagerStatisticFeedbackInsuranceServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManagerStatisticFeedbackInsuranceServlet at " + request.getContextPath () + "</h1>");
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
        DAO_Insurance dao = new DAO_Insurance();
        HttpSession session = request.getSession();
        Insurance i = (Insurance) session.getAttribute("account");
        if (i == null) {
            response.sendRedirect("login.jsp"); // Nếu session bị mất, chuyển về trang đăng nhập
            return;
        }
        request.setAttribute("totalFeedback", dao.getTotalInsuranceFeedback(i.getInsurance_id()));
        request.setAttribute("totalRate", dao.getAvgRate(i.getInsurance_id()));
        request.setAttribute("totalRate5", dao.getTotalInsuranceFeedbackAndRate(i.getInsurance_id(),5));
        request.setAttribute("totalRate4", dao.getTotalInsuranceFeedbackAndRate(i.getInsurance_id(),4));
        request.setAttribute("totalRate3", dao.getTotalInsuranceFeedbackAndRate(i.getInsurance_id(),3));
        request.setAttribute("totalRate2", dao.getTotalInsuranceFeedbackAndRate(i.getInsurance_id(),2));
        request.setAttribute("totalRate1", dao.getTotalInsuranceFeedbackAndRate(i.getInsurance_id(),1));
         request.setAttribute("policyHighest", dao.getHighestPolicyRate(i.getInsurance_id()));
          request.setAttribute("policyLowest", dao.getLowestPolicyRate(i.getInsurance_id()));
          request.setAttribute("policyHighestRate5", dao.getTopPolicyRate(i.getInsurance_id(),5));
          request.setAttribute("policyLowestRate5", dao.getBotPolicyRate(i.getInsurance_id(),5));
          request.setAttribute("policyHighestRate1", dao.getTopPolicyRate(i.getInsurance_id(),1));
          request.setAttribute("policyLowestRate1", dao.getBotPolicyRate(i.getInsurance_id(),1));
          request.getRequestDispatcher("managerInsuranceFeedbackStatistic.jsp").forward(request, response);
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
        processRequest(request, response);
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
