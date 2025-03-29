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
import model.Insurance;
import model.ServiceProvider;

/**
 *
 * @author DELL
 */
@WebServlet(name="updateServiceProviderServlet", urlPatterns={"/updateServiceProvider"})
public class updateServiceProviderServlet extends HttpServlet {
   
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
            out.println("<title>Servlet updateServiceProviderServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet updateServiceProviderServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String id_raw = request.getParameter("id");
        int id;
        try {
            id = Integer.parseInt(id_raw);
            DAO_Admin d = new DAO_Admin();
            ServiceProvider sp = d.getServiceProviderById(id);
            request.setAttribute("serviceprovider", sp);
            request.getRequestDispatcher("updateServiceProvider.jsp").forward(request, response);
        } catch (Exception e) {
        }
    } 

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        DAO_Admin dao = new DAO_Admin();
        String id_raw = request.getParameter("id");
        String serviceprovider_name = request.getParameter("serviceprovider_name");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String phone_number = request.getParameter("phone_number");
        String address = request.getParameter("address");
        String servicetype = request.getParameter("servicetype");
        String status = request.getParameter("status");
        int id;
        try {
            id  = Integer.parseInt(id_raw);
            ServiceProvider Username = dao.getServiceProviderByUserName(username);
            ServiceProvider Email = dao.getServiceProviderByEmail(email);
            ServiceProvider Phone = dao.getServiceProviderByPhone(phone_number);
            ServiceProvider Name = dao.getServiceProviderByName(serviceprovider_name);
            ServiceProvider serviceprovider = dao.getServiceProviderById(id);
            if (Username != null && !serviceprovider.getUsername().equals(username)) {
                request.setAttribute("serviceprovider", serviceprovider);
                request.setAttribute("error", "Username " + username + " existed!!");
                request.getRequestDispatcher("updateServiceProvider.jsp").forward(request, response);
            } else if (Email != null && !serviceprovider.getEmail().equals(email)) {
                request.setAttribute("serviceprovider", serviceprovider);
                request.setAttribute("error", "Emai " + email + " existed!!");
                request.getRequestDispatcher("updateServiceProvider.jsp").forward(request, response);
            } else if (Phone != null && !serviceprovider.getPhone_number().equals(phone_number)) {
                request.setAttribute("serviceprovider", serviceprovider);
                request.setAttribute("error", "Phone number " + phone_number + " existed!!");
                request.getRequestDispatcher("updateServiceProvider.jsp").forward(request, response);
            } else if (Name != null && !serviceprovider.getName().equals(serviceprovider_name)) {
                request.setAttribute("serviceprovider", serviceprovider);
                request.setAttribute("error", "Service provider name " + serviceprovider_name + " existed!!");
                request.getRequestDispatcher("updateServiceProvider.jsp").forward(request, response);
            } else {
                ServiceProvider s = new ServiceProvider(id, serviceprovider_name, 
                        username, servicetype, email, phone_number, address, status);
                dao.updateServiceProvider(s);
                response.sendRedirect("serviceprovider_management");
            }
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
