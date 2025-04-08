package controller_Marketer;

import dal.DAO;
import dal.DAO_Marketer;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.NewsView;
import model.Staff;

/**
 *
 * @author Acer Nitro Tiger
 */
@WebServlet(name = "NewsStatistic", urlPatterns = {"/marketer/newsStatistic"})
public class NewsStatisticServlet extends HttpServlet {

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
            out.println("<title>Servlet NewsStatistic</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NewsStatistic at " + request.getContextPath() + "</h1>");
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
        // Lấy staff_id từ session
        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("account");

        if (staff == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int staffId = staff.getStaff_id();
        DAO_Marketer dao = new DAO_Marketer();

        // Lấy danh sách bài viết và lượt xem theo nhân viên
        List<NewsView> list = dao.countNewsByStaff(staffId);
        request.setAttribute("newsView", list);
        // Phân trang
        int pageSize = 5; // Số bài viết trên mỗi trang
        int page = 1; // Mặc định trang đầu tiên
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        int total = list.size();
        int totalPages = (int) Math.ceil((double) total / pageSize);
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, total);

        // Lấy danh sách bài viết theo trang
        List<NewsView> paginatedNews = list.subList(start, end);
        request.setAttribute("newsView", paginatedNews);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        // Lấy tổng số bài viết của nhân viên đó
        int totalArticles = dao.totalArticleByStaff(staffId);
        request.setAttribute("totalArticle", totalArticles);

        // Lấy tổng số lượt xem của nhân viên đó
        int totalViews = dao.totalViewByStaff(staffId);
        request.setAttribute("totalView", totalViews);

        // Chuyển dữ liệu qua trang JSP
        request.getRequestDispatcher("newsStatistic.jsp").forward(request, response);
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
