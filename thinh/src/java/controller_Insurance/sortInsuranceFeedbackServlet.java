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
import model.Insurance_feedback;

/**
 *
 * @author Windows
 */
@WebServlet(name = "sortInsuranceFeedbackServlet", urlPatterns = {"/sortInsuranceFeedback"})
public class sortInsuranceFeedbackServlet extends HttpServlet {

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
            out.println("<title>Servlet sortInsuranceFeedbackServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet sortInsuranceFeedbackServlet at " + request.getContextPath() + "</h1>");
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
        DAO_Insurance dao = new DAO_Insurance();
        List<Insurance_feedback> listF = new ArrayList<>();
        List<Insurance_feedback> listP = new ArrayList<>();
        HttpSession session = request.getSession();
        Insurance i = (Insurance) session.getAttribute("account");
        listP = dao.getAllPolicyIDByFeedback(i.getInsurance_id());
        String sort = request.getParameter("sortInsuranceFeedback");
        String policy_id_raw = request.getParameter("policy_id");
        String quantity_raw = request.getParameter("quantity");
        String offset_raw = request.getParameter("offset");
        int offset = 1;
        int quantity = 5;
        int policy_id = 0;

        try {
            if (offset_raw != null) {
                offset = Integer.parseInt(offset_raw);
            }
            if (quantity_raw != null) {
                quantity = Integer.parseInt(quantity_raw);
            }
            if (policy_id_raw != null) {
                policy_id = Integer.parseInt(policy_id_raw);
            }
        } catch (NumberFormatException e) {

            return;
        }
        int count = 0;
        if (policy_id == 0) {
            count = dao.getTotalInsuranceFeedback(i.getInsurance_id());
        } else {
            count = dao.getTotalInsuranceFeedbackByPolicy_id(i.getInsurance_id(), policy_id);
        }
        int endPage = count / quantity;
        if (count % quantity != 0) {
            endPage++;
        }
        if (sort.equals("none")) {
            if (policy_id == 0) {
                listF = dao.getAllFeedbackByInsuranceID(i.getInsurance_id(), offset, quantity);
            } else {
                listF = dao.getAllFeedbackByInsuranceIDAndPolicyID(i.getInsurance_id(), policy_id, offset, quantity);
            }

        } else {
            switch (sort) {
                case "feedback_date":
                    if (policy_id == 0) {
                        listF = dao.sortAllFeedbackByFeedbackDate(i.getInsurance_id(), offset, quantity);
                    } else {
                        listF = dao.sortFeedbackByFeedbackDateAndPolicyID(i.getInsurance_id(), policy_id, offset, quantity);
                    }
                    break;
                case "feedback_rate":
                    if (policy_id == 0) {
                        listF = dao.sortAllFeedbackByFeedbackRate(i.getInsurance_id(), offset, quantity);
                    } else {
                        listF = dao.sortFeedbackByFeedbackRateAndPolicyID(i.getInsurance_id(), policy_id, offset, quantity);
                    }
                    break;
                default:
                    listF = dao.getAllFeedbackByInsuranceID(i.getInsurance_id(), offset, quantity);
            }
        }
        request.setAttribute("listF", listF);
        request.setAttribute("listP", listP);
        request.setAttribute("sortInsuranceFeedback", sort);
        request.setAttribute("policy_id", policy_id);
        request.setAttribute("quantity", quantity);
        request.setAttribute("endP", endPage);
        request.getRequestDispatcher("managerInsuranceFeedback.jsp").forward(request, response);
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
        processRequest(request, response);
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
