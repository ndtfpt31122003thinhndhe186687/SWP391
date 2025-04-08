/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controlller;

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
import java.util.List;
import model.Customer;
import model.News;
import model.Notifications;

/**
 *
 * @author Acer Nitro Tiger
 */
@WebServlet(name = "NotificationListServlet", urlPatterns = {"/notificationsList"})
public class NotificationListServlet extends HttpServlet {

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
            out.println("<title>Servlet NotificationListServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NotificationListServlet at " + request.getContextPath() + "</h1>");
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
        DAO d = new DAO();
        String type = request.getParameter("type");
        String fromDateStr = request.getParameter("fromDate");
        String toDateStr = request.getParameter("toDate");
        String page_raw = request.getParameter("page");
        String pageSize_raw = request.getParameter("pageSize");
        String status=request.getParameter("status");

        Date fromDate = null;
        Date toDate = null;
        SimpleDateFormat displayFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd");
        displayFormat.setLenient(false);
        java.sql.Date startDate = null;
        java.sql.Date endDate = null;
        String errorMessage = null;
        try {
            boolean hasFromDate = fromDateStr != null && !fromDateStr.isEmpty();
            boolean hasToDate = toDateStr != null && !toDateStr.isEmpty();
            if ((hasFromDate && !hasToDate) || (!hasFromDate && hasToDate)) {
                errorMessage = "Vui lòng nhập cả ngày bắt đầu và ngày kết thúc!";
            } else if (hasFromDate && hasToDate) {
                Date startD = displayFormat.parse(fromDateStr);
                startDate = new java.sql.Date(startD.getTime());
                Date endD = displayFormat.parse(toDateStr);
                endDate = new java.sql.Date(endD.getTime());
                if (endDate.before(startDate)) {
                    errorMessage = "Ngày kết thúc phải lớn hơn hoặc bằng ngày bắt đầu!";
                }
                if (endDate.after(new Date())) {
                    errorMessage = "Ngày kết thúc không được lớn hơn ngày hiện tại!";
                }
            }
        } catch (ParseException e) {
            errorMessage = "Vui lòng nhập đúng định dạng ngày (dd-MM-yyyy)!";
        }
        HttpSession session = request.getSession();
        Customer c = (Customer) session.getAttribute("account");
        if (c == null) {
            response.sendRedirect("login");
            return;
        }
        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
        } else {
            List<Notifications> listNotify = d.getNotifyFilter(startDate, endDate, type, c.getCustomer_id(),status);
            int pageSize = (pageSize_raw == null || pageSize_raw.isEmpty()) ? 3 : Integer.parseInt(pageSize_raw);
            int totalNews = listNotify.size();
            int totalPage = totalNews % pageSize == 0 ? (totalNews / pageSize) : ((totalNews / pageSize) + 1);
            int page = (page_raw == null) ? 1 : Integer.parseInt(page_raw);
            int start = (page - 1) * pageSize;
            int end = Math.min(page * pageSize, totalNews);
            List<Notifications> listN = d.getListByPage(listNotify, start, end);
            request.setAttribute("listNotify", listN);
            request.setAttribute("totalPage", totalPage);
            request.setAttribute("page", page);
            request.setAttribute("pageSize", pageSize);
        }

        List<String> listNotifyType = d.getAllNotificationTypes();
        request.setAttribute("listTypes", listNotifyType);
        request.setAttribute("type", type);
        request.setAttribute("status", status);
        request.setAttribute("fromDate", fromDateStr);
        request.setAttribute("toDate", toDateStr);
        request.getRequestDispatcher("notificationsList.jsp").forward(request, response);
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
