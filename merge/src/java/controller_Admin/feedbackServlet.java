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
import jakarta.servlet.http.HttpSession;
import model.Customer;
import model.Feedback;

/**
 *
 * @author Windows
 */
@WebServlet(name="feedbackServlet", urlPatterns={"/feedback"})
public class feedbackServlet extends HttpServlet {
   
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
            out.println("<title>Servlet feedbackServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet feedbackServlet at " + request.getContextPath () + "</h1>");
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
        String service_id_raw = request.getParameter("service_id");
        int service_id = Integer.parseInt(service_id_raw);
       request.setAttribute("service_id", service_id);
       request.getRequestDispatcher("feedback.jsp").forward(request, response);
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
                 DAO_Admin dao = new DAO_Admin();
        HttpSession session = request.getSession();
        Customer c = (Customer) session.getAttribute("account");
        String service_id_raw = request.getParameter("service_id");
        String feedback_content = request.getParameter("feedback_content");
        String feedback_rate_raw = request.getParameter("feedback_rate");
        int service_id=0, feedback_rate=0;
         service_id = Integer.parseInt(service_id_raw);                   
          feedback_content = feedback_content.replaceAll("<[^>]*>", "").replaceAll("&nbsp;", "").trim();
        if(feedback_content.isEmpty()){
             request.setAttribute("error", "Nội dung không được để trống!");
             request.setAttribute("service_id", service_id);
                request.getRequestDispatcher("feedback.jsp").forward(request, response);
        }   
         if(feedback_rate_raw != null){
                feedback_rate = Integer.parseInt(feedback_rate_raw);
            }
            else{
                request.setAttribute("error", "Bạn phải đánh giá số sao!");
                request.setAttribute("service_id", service_id);
                request.getRequestDispatcher("feedback.jsp").forward(request, response);
            }
        

       
        Feedback f = new Feedback(c.getCustomer_id(), service_id, 
                feedback_rate, feedback_content);
        dao.insertFeedback(f);
        session.setAttribute("showSuccessModal", true);
        session.setAttribute("successMessage", "Bạn đã gửi phản hồi bảo hiểm thành công!");
        response.sendRedirect("home.jsp");
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
