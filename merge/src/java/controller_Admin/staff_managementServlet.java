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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.Staff;

/**
 *
 * @author DELL
 */
@WebServlet(name = "staff_managementServlet", urlPatterns = {"/staff_management"})
public class staff_managementServlet extends HttpServlet {
    List<Staff> list = new ArrayList<>();
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
            out.println("<title>Servlet staff_managementServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet staff_managementServlet at " + request.getContextPath() + "</h1>");
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
        DAO_Admin d = new DAO_Admin();
        String type = request.getParameter("type");
        String page_raw = request.getParameter("page");
        String status = request.getParameter("status");
        String sortBy = request.getParameter("sort");
        String pageSize_raw = request.getParameter("pageSize");
        String search = request.getParameter("searchName");
        int role_id = 2;
        if ("marketers".equals(type)) {
            role_id = 3;
        } else if ("accountants".equals(type)) {
            role_id = 4;
        }
        list = d.getListStaffByConditions("", sortBy, role_id, status);
        status = (status == null) ? "all" : status;
        sortBy = (sortBy == null) ? "full_name" : sortBy;
        pageSize_raw = (pageSize_raw == null) ? "2" : pageSize_raw;

        if (search != null && !search.trim().isEmpty()) {
            search = search.trim().toLowerCase().replaceAll("\\s+", " ");
            search = "%" + search.replace(" ", "%") + "%";
            list = d.getListStaffByConditions(search, sortBy, role_id, status);
        }
//        if (status.equals("all")) {
//            list = d.get_All_Staff_Sorted(role_id, sortBy);
//        } else {
//            list = d.get_Staff_By_Status_Sorted(role_id, status, sortBy);
//        }

        try {
            int pageSize = Integer.parseInt(pageSize_raw);
            int totalStaff = list.size();
            int totalPage = totalStaff % pageSize == 0 ? (totalStaff / pageSize) : ((totalStaff / pageSize) + 1);
            int page = (page_raw == null) ? 1 : Integer.parseInt(page_raw);
            int start = (page - 1) * pageSize;
            int end = Math.min(page * pageSize, totalStaff);
            List<Staff> listStaffPage = d.getStaffByPage(list, start, end);
            request.setAttribute("searchName", search);
            request.setAttribute("sort", sortBy);
            request.setAttribute("status", status);
            request.setAttribute("type", type);
            request.setAttribute("data", listStaffPage);
            request.setAttribute("page", page);
            request.setAttribute("totalPage", totalPage);
            request.setAttribute("pageSize", pageSize);
            
            request.getRequestDispatcher("staff management.jsp").forward(request, response);
        } catch (NumberFormatException e) {
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
