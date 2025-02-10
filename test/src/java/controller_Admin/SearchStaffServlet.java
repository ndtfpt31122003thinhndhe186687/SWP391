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
import model.Staff;

/**
 *
 * @author DELL
 */
@WebServlet(name="searchStaffServlet", urlPatterns={"/searchStaff"})
public class SearchStaffServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       
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
        String search = request.getParameter("searchName");
        String type = request.getParameter("type");
        
        // Determine role_id based on staff type
        int role_id;
        switch(type != null ? type : "bankers") {
            case "marketers":
                role_id = 3;
                break;
            case "accountants":
                role_id = 4;
                break;
            default: // bankers
                role_id = 2;
                break;
        }
        
        DAO_Admin d = new DAO_Admin();
        List<Staff> ListByName = d.searchStaffByFullName(search, role_id);
        List<Staff> ListByPhone = d.searchStaffByPhone(search, role_id);
        
        request.setAttribute("ListByName", ListByName);
        request.setAttribute("ListByPhone", ListByPhone);
        request.setAttribute("type", type); // Keep the current type for tab highlighting
        
        request.getRequestDispatcher("staff management.jsp").forward(request, response);
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
    }// </editor-fold>

}
