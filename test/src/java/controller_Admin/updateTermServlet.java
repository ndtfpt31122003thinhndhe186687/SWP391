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
import model.Term;

/**
 *
 * @author DELL
 */
@WebServlet(name="updateTermServlet", urlPatterns={"/updateTerm"})
public class updateTermServlet extends HttpServlet {
   
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
            out.println("<title>Servlet updateTermServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet updateTermServlet at " + request.getContextPath () + "</h1>");
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
        String id_raw = request.getParameter("id");
        int id;
        try {
            id=Integer.parseInt(id_raw);
            DAO_Admin d = new DAO_Admin();
            Term t = d.get_Term_BY_Term_id(id);
            request.setAttribute("term", t);
            request.getRequestDispatcher("updateTerm.jsp").forward(request, response);
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
        String term_id_raw = request.getParameter("term_id");
        String term_name = request.getParameter("term_name");
        String duration_raw = request.getParameter("duration");
        String term_type = request.getParameter("term_type");
        String status = request.getParameter("status");
        int term_id,duration;
        DAO_Admin d = new DAO_Admin();
        try {
            term_id=Integer.parseInt(term_id_raw);          
            duration = Integer.parseInt(duration_raw);
            Term term = d.get_Term_BY_Term_id(term_id);
            Term test =d.get_Term_BY_Term_name(term_name);
<<<<<<< HEAD
            if(test != null && !term.getTerm_name().equals(term_name)){
=======
            if(test != null){
>>>>>>> origin/phong
                request.setAttribute("error", "term name "+ term_name+" existed!!");
                request.setAttribute("term", term);
                request.getRequestDispatcher("updateTerm.jsp").forward(request, response);
            }else{                        
            Term t = new Term(term_id, term_name, duration, term_type, status);
            d.UpdateTerm(t);
            response.sendRedirect("service_management");
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
