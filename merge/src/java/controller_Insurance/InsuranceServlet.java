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
import java.util.ArrayList;
import java.util.List;
import model.Customer;
import model.Insurance;
import model.Insurance_feedback;
import model.Insurance_policy;
import model.Insurance_term;

/**
 *
 * @author Windows
 */
@WebServlet(name="InsuranceServlet", urlPatterns={"/Insurance"})
public class InsuranceServlet extends HttpServlet {
   
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
            out.println("<title>Servlet InsuranceServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet InsuranceServlet at " + request.getContextPath () + "</h1>");
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
        List<Insurance> list = new ArrayList<>();     
        String action = request.getParameter("action");
                if ("details".equals(action)) {
            String insurance_id_raw = request.getParameter("insurance_id");
            try {
                int insurance_id = Integer.parseInt(insurance_id_raw);
                Insurance insurance = dao.getInsuranceByID(insurance_id);
                List<Customer> listC = dao.getInsuranceCustomerByInsuranceId(insurance_id);
                List<Insurance_feedback> listF = dao.getListFeedbackByInsuranceID(insurance_id);
                
                int count = dao.getTotalInsuranceFeedback(insurance_id);
                int point = 0, star = 0;
                for (Insurance_feedback insurance_feedback : listF) {
                    point += insurance_feedback.getFeedback_rate();
                }
                if(point != 0){
                star = point / count;
                }

                if (insurance != null) {
                    request.setAttribute("star", star);
                    request.setAttribute("listC", listC);
                    request.setAttribute("insurance", insurance);
                    request.getRequestDispatcher("insuranceDetails.jsp").forward(request, response);
                } else {
                    response.sendRedirect("Insurance");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect("Insurance");
            }
        } else {
            try {
                list = dao.getInsuranceByStatus();
                request.setAttribute("ListInsurance", list);
            } catch (Exception e) {
                System.out.println(e);
            }
            request.getRequestDispatcher("insurance.jsp").forward(request, response);
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
