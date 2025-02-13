package controller_Admin;

import dal.FeedbackDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Feedback;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "FeedbackManagementServlet", urlPatterns = {"/feedbackmanagement"})
public class FeedbackManagementServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy tham số tìm kiếm
        String searchCustomer = request.getParameter("searchCustomer");
        if (searchCustomer == null) {
            searchCustomer = "";
        }
        String searchService = request.getParameter("searchService");
        if (searchService == null) {
            searchService = "";
        }
        
        // Lấy số trang, mặc định là 1
        int page = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }
        int pageSize = 10;
        int offset = (page - 1) * pageSize;
        
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        List<Feedback> feedbackList = null;
        int totalFeedback = 0;
        String topService = null;
        
        try {
            feedbackList = feedbackDAO.getAllFeedback(searchCustomer, searchService, offset, pageSize);
            totalFeedback = feedbackDAO.getTotalFeedbackCount(searchCustomer, searchService);
            topService = feedbackDAO.getTopServiceFeedback();
        } catch (Exception ex) {
            Logger.getLogger(FeedbackManagementServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int totalPages = (int) Math.ceil(totalFeedback / (double) pageSize);
        
        request.setAttribute("feedbackList", feedbackList);
        request.setAttribute("searchCustomer", searchCustomer);
        request.setAttribute("searchService", searchService);
        request.setAttribute("totalFeedback", totalFeedback);
        request.setAttribute("topService", topService);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        
        request.getRequestDispatcher("feedback management.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
