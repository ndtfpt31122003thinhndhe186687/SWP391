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
        DAO_Insurance dao = new DAO_Insurance();
        List<Insurance_contract> list = new ArrayList<>();
        String sort = request.getParameter("sortInsuranceContract");
        String status = request.getParameter("status");
         HttpSession session = request.getSession();
        Insurance i = (Insurance) session.getAttribute("account");
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
        if(status.equals("all")){
         count = dao.getTotalInsuranceContract(i.getInsurance_id());
        }
        else{
            count = dao.getTotalInsuranceContractByStatus(i.getInsurance_id(), status);
        }
        int endPage = count / quantity;
        if (count % quantity != 0) {
            endPage++;
        }
        if(sort.equals("none")){
            if(status.equals("all")){
                list = dao.paginationInsuranceContract(i.getInsurance_id(), offset, quantity);
            }
            else{
                list = dao.getAllInsuranceContractByInsuranceIdAndStatus(i.getInsurance_id(), status, offset, quantity);
            }
        }
        else{
            switch (sort) {
                case "start_date":
                    if(status.equals("all")){
                        list = dao.sortInsuranceContractByStartDate(i.getInsurance_id(), offset, quantity);
                    }
                    else{
                        list = dao.sortInsuranceContractByStartDateAndStatus(i.getInsurance_id(), status, offset, quantity);
                    }
                    break;
                case "created_at":
                    if(status.equals("all")){
                        list = dao.sortInsuranceContractByCreatedAt(i.getInsurance_id(), offset, quantity);
                    }
                    else{
                        list = dao.sortInsuranceContractByCreatedAtAndStatus(i.getInsurance_id(), status, offset, quantity);
                    }
                    break;
                default:
                    list = dao.paginationInsuranceContract(i.getInsurance_id(), offset, quantity);
                    break;
            }
        }
               
        request.setAttribute("quantity", quantity);
        request.setAttribute("endP", endPage);
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
