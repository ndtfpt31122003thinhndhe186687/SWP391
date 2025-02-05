/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.DAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.Customer;

/**
 *
 * @author Acer Nitro Tiger
 */
@WebServlet(name = "ChangeInfor", urlPatterns = {"/changeInfor"})
public class ChangeInforServlet extends HttpServlet {

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
            out.println("<title>Servlet ChangeInfor</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChangeInfor at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("changeInfor.jsp").forward(request, response);
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
        String fullname = request.getParameter("profile-name");
        String email = request.getParameter("profile-email");
        String phone = request.getParameter("profile-phone");
        String address = request.getParameter("profile-address");
        String url_image = request.getParameter("profile-image");
        String dob_raw = request.getParameter("dob");
        DAO d = new DAO();
        Date dob = null;
        java.sql.Date sqlDob = null;

        try {
            //check email duplicate
            if (email == null || d.existedEmail(email)) {
                throw new Exception("Email has been already exists.");
            }
            //check phone number duplicate
            if (phone == null || d.existedPhoneNum(phone)) {
                throw new Exception("Phone number has been already exists.");
            }
            //check day month invalid or not
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");   
                dateFormat.setLenient(false); // Bật kiểm tra giá trị chặt chẽ
                dob = dateFormat.parse(dob_raw);
                sqlDob = new java.sql.Date(dob.getTime());
            } catch (ParseException e) {
                throw new Exception("Invalid date format. Please use yyyy-MM-dd.");
            }
            //check if the file is image or not
            if (url_image == null || (!url_image.endsWith(".png") && !url_image.endsWith(".jpg"))) {
                throw new Exception("Image must be in .png or .jpg format.");
            }
            HttpSession session = request.getSession();
            Customer c = (Customer) session.getAttribute("account");
            d.changeInfor(fullname, email, phone, address, sqlDob, url_image, c.getCustomer_id());
            response.sendRedirect("changeInfor");
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("changeInfor.jsp").forward(request, response);
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
