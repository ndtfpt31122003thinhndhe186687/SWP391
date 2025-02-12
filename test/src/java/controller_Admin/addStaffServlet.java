/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller_Admin;

import dal.DAO;
import dal.DAO_Admin;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Staff;

/**
 *
 * @author DELL
 */
@WebServlet(name="addBankerServlet", urlPatterns={"/addStaff"})
public class addStaffServlet extends HttpServlet {
   
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
            out.println("<title>Servlet addBankerServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet addBankerServlet at " + request.getContextPath () + "</h1>");
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
        String full_name = request.getParameter("full_name");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String phone_number = request.getParameter("phone_number");
        String gender = request.getParameter("gender");
        String date_of_birth_raw = request.getParameter("date_of_birth");
        String address = request.getParameter("address");
        String role_id_raw = request.getParameter("role_id");
        String status = request.getParameter("status");
        int role_id;
        try {         
            role_id=Integer.parseInt(role_id_raw);
            var date_of_birth = java.sql.Date.valueOf(date_of_birth_raw);
            DAO_Admin dao = new DAO_Admin();
            Staff Username = dao.get_Staff_By_Username(username);
            Staff Email = dao.get_Staff_By_Email(email);
            Staff Phone = dao.get_Staff_By_Phone(phone_number);
            if(Username!= null){
                request.setAttribute("error","username "+ username+" existed!!");
                request.getRequestDispatcher("addStaff.jsp").forward(request, response);
            }else if (Email != null){
                request.setAttribute("error","emai "+ email+" existed!!");
                request.getRequestDispatcher("addStaff.jsp").forward(request, response);
            }else if (Phone != null){
                request.setAttribute("error","phone number "+ phone_number+" existed!!");
                request.getRequestDispatcher("addStaff.jsp").forward(request, response);
            }
            else{
                Staff s = new Staff(full_name, email, username, password,
                        phone_number, gender, date_of_birth, address, role_id, status);
                dao.insertBanker(s);
                response.sendRedirect("staff_management");
            }
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
