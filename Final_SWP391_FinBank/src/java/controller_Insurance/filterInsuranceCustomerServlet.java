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
import model.Insurance;

/**
 *
 * @author Windows
 */
@WebServlet(name="filterInsuranceCustomerServlet", urlPatterns={"/filterInsuranceCustomer"})
public class filterInsuranceCustomerServlet extends HttpServlet {
   
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
            out.println("<title>Servlet filterInsuranceCustomerServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet filterInsuranceCustomerServlet at " + request.getContextPath () + "</h1>");
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
        List<Customer> list = new ArrayList<>();
         HttpSession session = request.getSession();
         Insurance i = (Insurance) session.getAttribute("account");
         if (i == null) {
            response.sendRedirect("login.jsp"); // Nếu session bị mất, chuyển về trang đăng nhập
            return;
        }
         String gender = request.getParameter("gender");      
        String quantity_raw = request.getParameter("quantity");
        String offset_raw = request.getParameter("offset");
        int offset = 1;
        int quantity = 5;

        try {
            if (offset_raw != null) {
                offset = Integer.parseInt(offset_raw);
            }
            if (quantity_raw != null) {
                quantity = Integer.parseInt(quantity_raw);
            }
        } catch (NumberFormatException e) {

            return;
        }
        int count = 0;
        if(gender.equals("all")){
         count = dao.getTotalInsuranceCustomer(i.getInsurance_id());
        }
        else{
            count = dao.getTotalInsuranceCustomerByGender(i.getInsurance_id(), gender);
        }
        int endPage = count / quantity;
        if (count % quantity != 0) {
            endPage++;
        }
       
        if(gender.equals("all")){
           list = dao.getInsuranceCustomerByInsuranceId(i.getInsurance_id());
        }
        else if(gender.equals("female")){
           list = dao.filterInsuranceCustomerByGender(i.getInsurance_id(), gender);
           
        }
        else if(gender.equals("male")){
           list = dao.filterInsuranceCustomerByGender(i.getInsurance_id(), gender);
        }
      
        request.setAttribute("quantity", quantity);
        request.setAttribute("endP", endPage);
        request.setAttribute("listC", list);
        request.setAttribute("gender", gender);
        request.getRequestDispatcher("managerInsuranceCustomer.jsp").forward(request, response);
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
