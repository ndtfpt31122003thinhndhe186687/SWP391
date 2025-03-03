/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller_Insurance;

import dal.DAO;
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
import model.Insurance;
import model.Insurance_contract;

/**
 *
 * @author Windows
 */
@WebServlet(name="sortInsuranceContractServlet", urlPatterns={"/sortInsuranceContract"})
public class sortInsuranceContractServlet extends HttpServlet {
   
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
            out.println("<title>Servlet sortInsuranceContractServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet sortInsuranceContractServlet at " + request.getContextPath () + "</h1>");
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
        String sort = request.getParameter("sortInsuranceContract");
        String status = request.getParameter("status");
         HttpSession session = request.getSession();
        Insurance i = (Insurance) session.getAttribute("account");
        DAO_Insurance dao = new DAO_Insurance();
        List<Insurance_contract> list = new ArrayList<>();
        if(sort.equals("none") && status.equals("all")){
            list = dao.getAllInsuranceContractByInsuranceId(i.getInsurance_id());
        }
        else if(sort.equals("none") && status.equals("active")){
            list = dao.getAllInsuranceContractByInsuranceIdAndStatus(i.getInsurance_id(),status);
        }
        else if(sort.equals("none") && status.equals("expired")){
            list = dao.getAllInsuranceContractByInsuranceIdAndStatus(i.getInsurance_id(),status);
        }
        else if(sort.equals("none") && status.equals("cancelled")){
            list = dao.getAllInsuranceContractByInsuranceIdAndStatus(i.getInsurance_id(),status);
        }
        else if(sort.equals("start_date") && status.equals("all")){
            list = dao.sortInsuranceContractByStartDate(i.getInsurance_id());
        }
        else if(sort.equals("start_date") && status.equals("active")){
            list = dao.sortInsuranceContractByStartDateAndStatus(i.getInsurance_id(), status);
        }
        else if(sort.equals("start_date") && status.equals("expired")){
            list = dao.sortInsuranceContractByStartDateAndStatus(i.getInsurance_id(), status);
        }
        else if(sort.equals("start_date") && status.equals("cancelled")){
            list = dao.sortInsuranceContractByStartDateAndStatus(i.getInsurance_id(), status);
        }
        else if(sort.equals("created_at") && status.equals("all")){
            list = dao.sortInsuranceContractByCreatedAt(i.getInsurance_id());
        }
         else if(sort.equals("created_at") && status.equals("active")){
            list = dao.sortInsuranceContractByCreatedAtAndStatus(i.getInsurance_id(), status);
        }
        else if(sort.equals("created_at") && status.equals("expired")){
            list = dao.sortInsuranceContractByCreatedAtAndStatus(i.getInsurance_id(), status);
        }
        else if(sort.equals("created_at") && status.equals("cancelled")){
            list = dao.sortInsuranceContractByCreatedAtAndStatus(i.getInsurance_id(), status);
        }
        request.setAttribute("listC", list);
        request.setAttribute("status", status);
        request.setAttribute("sort", sort);
        request.getRequestDispatcher("managerInsuranceContract.jsp").forward(request, response);
        

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
