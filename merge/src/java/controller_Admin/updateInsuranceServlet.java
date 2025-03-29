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

/**
 *
 * @author DELL
 */
@WebServlet(name = "updateInsuranceServlet", urlPatterns = {"/updateInsurance"})
public class updateInsuranceServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
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
            out.println("<title>Servlet updateInsuranceServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet updateInsuranceServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
            id = Integer.parseInt(id_raw);
            DAO_Admin d = new DAO_Admin();
            Insurance i = d.getInsuranceById(id);
            request.setAttribute("insurance", i);
            request.getRequestDispatcher("updateInsurance.jsp").forward(request, response);
        } catch (Exception e) {
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DAO_Admin dao = new DAO_Admin();
        String id_raw = request.getParameter("id");
        String insurance_name = request.getParameter("insurance_name");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String phone_number = request.getParameter("phone_number");
        String address = request.getParameter("address");
        String status = request.getParameter("status");
        int id;
        try {
            id = Integer.parseInt(id_raw);
            Insurance Username = dao.getInsuranceByUserName(username);
            Insurance Email = dao.getInsuranceByEmail(email);
            Insurance Phone = dao.getInsuranceByPhone(phone_number);
            Insurance Name = dao.getInsuranceByName(insurance_name);
            Insurance insurance = dao.getInsuranceById(id);
            if (Username != null && !insurance.getUsername().equals(username)) {
                request.setAttribute("insurance", insurance);
                request.setAttribute("error", "Username " + username + " existed!!");
                request.getRequestDispatcher("updateInsurance.jsp").forward(request, response);
            } else if (Email != null && !insurance.getEmail().equals(email)) {
                request.setAttribute("insurance", insurance);
                request.setAttribute("error", "Emai " + email + " existed!!");
                request.getRequestDispatcher("updateInsurance.jsp").forward(request, response);
            } else if (Phone != null && !insurance.getPhone_number().equals(phone_number)) {
                request.setAttribute("insurance", insurance);
                request.setAttribute("error", "Phone number " + phone_number + " existed!!");
                request.getRequestDispatcher("updateInsurance.jsp").forward(request, response);
            } else if (Name != null && !insurance.getInsurance_name().equals(insurance_name)) {
                request.setAttribute("insurance", insurance);
                request.setAttribute("error", "Insurance name " + insurance_name + " existed!!");
                request.getRequestDispatcher("updateInsurance.jsp").forward(request, response);
            } else {
                Insurance i = new Insurance(id, username, insurance_name
                        , email, phone_number, address, status);
                dao.updateInsurance(i);
                response.sendRedirect("insurance_management");
            }
        } catch (Exception e) {
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
