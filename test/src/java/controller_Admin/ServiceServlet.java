/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller_Admin;

import dal.DAO_Admin;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Customer;
import model.Feedback;
import model.Services;

/**
 *
 * @author DELL
 */
@WebServlet(name="ServiceServlet", urlPatterns={"/Service"})
public class ServiceServlet extends HttpServlet {
   
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
        String action = request.getParameter("action");
        DAO_Admin dao = new DAO_Admin();
        
        if ("details".equals(action)) {
            // Xử lý xem chi tiết dịch vụ
            String id = request.getParameter("id");
            try {
               int serviceId = Integer.parseInt(id);
                Services service = dao.getServiceById(serviceId);
                List<Customer> listC = dao.getCustomerByServiceID(serviceId);
                List<Feedback> listF = dao.getListFeedbackByServiceID(serviceId);
                 int count = dao.getTotalFeedbackByServiceID(serviceId);
                int point = 0, star = 0;
                for (Feedback feedback : listF) {
                    point += feedback.getFeedback_rate();
                }
                if(point != 0){
                star = point / count;
                }


                if (service != null) {
                     request.setAttribute("star", star);
                    request.setAttribute("listC", listC);
                    request.setAttribute("service", service);
                    request.getRequestDispatcher("service_details.jsp").forward(request, response);
                } else {
                    response.sendRedirect("Service");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect("Service");
            }
        } else {
            // Hiển thị danh sách dịch vụ
            try {
                List<Services> services = dao.getAllServices();
                request.setAttribute("listServices", services);
            } catch (Exception e) {
                System.out.println(e);
            }
            request.getRequestDispatcher("service.jsp").forward(request, response);
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
        doGet(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
