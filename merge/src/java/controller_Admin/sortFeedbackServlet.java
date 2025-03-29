/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller_Admin;

import dal.DAO_Admin;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.Feedback;

/**
 *
 * @author Windows
 */
@WebServlet(name="sortFeedbackServlet", urlPatterns={"/sortFeedback"})
public class sortFeedbackServlet extends HttpServlet {
   
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
            out.println("<title>Servlet sortFeedbackServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet sortFeedbackServlet at " + request.getContextPath () + "</h1>");
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
                DAO_Admin dao = new DAO_Admin();
        List<Feedback> listF = new ArrayList<>();
        
        List<Feedback> listS = new ArrayList<>();
        listS = dao.getListServiceByFeedback();
        String sort = request.getParameter("sortFeedback");
        String service_id_raw = request.getParameter("service_id");
        String quantity_raw = request.getParameter("quantity");
        String offset_raw = request.getParameter("offset");
        int offset = 1;
        int quantity = 5;
        int service_id = 0;

        try {
            if (offset_raw != null) {
                offset = Integer.parseInt(offset_raw);
            }
            if (quantity_raw != null) {
                quantity = Integer.parseInt(quantity_raw);
            }
            if (service_id_raw != null) {
                service_id = Integer.parseInt(service_id_raw);
            }
        } catch (NumberFormatException e) {

            return;
        }
        int count = 0;
        if (service_id == 0) {
            count = dao.getTotalFeedback();
        } else {
            count = dao.getTotalFeedbackByServiceID(service_id);
        }
        int endPage = count / quantity;
        if (count % quantity != 0) {
            endPage++;
        }
        if (sort.equals("none")) {
            if (service_id == 0) {
                listF = dao.getAllFeedback(offset, quantity);
            } else {
                listF = dao.getAllFeedbackByServiceID(service_id, offset, quantity);
            }

        } else {
            switch (sort) {
                case "feedback_date":
                    if (service_id == 0) {
                        listF = dao.sortFeedbackByFeedbackDate(offset, quantity);
                    } else {
                        listF = dao.sortFeedbackByFeedbackDateAndServiceID(service_id, offset, quantity);
                    }
                    break;
                case "feedback_rate":
                    if (service_id == 0) {
                        listF = dao.sortFeedbackByFeedbackRate(offset, quantity);
                    } else {
                        listF = dao.sortFeedbackByFeedbackRateAndServiceID(service_id, offset, quantity);
                    }
                    break;
                default:
                    listF = dao.getAllFeedback(offset, quantity);
            }
        }
        request.setAttribute("listF", listF);
        request.setAttribute("listS", listS);
        request.setAttribute("sortInsuranceFeedback", sort);
        request.setAttribute("service_id", service_id);
        request.setAttribute("quantity", quantity);
        request.setAttribute("endP", endPage);
        request.getRequestDispatcher("feedbackManagement.jsp").forward(request, response);
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
