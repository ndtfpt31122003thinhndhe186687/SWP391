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
import java.util.List;
import model.News;
import model.NewsCategory;

/**
 *
 * @author Acer Nitro Tiger
 */
@WebServlet(name = "NewsServlet", urlPatterns = {"/news"})
public class NewsServlet extends HttpServlet {

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
            out.println("<title>Servlet NewsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NewsServlet at " + request.getContextPath() + "</h1>");
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
        String page_raw = request.getParameter("page");
        DAO_Marketer d = new DAO_Marketer();
        List<News> listNews;
        String category = request.getParameter("category");
        int category_id = (category == null) ? 0 : Integer.parseInt(category);
        if (category_id == 0) {
            listNews = d.getAllNews();
        } else {
            listNews = d.getAllNewsByCategory(category_id);
        }
        int pageSize = 6;
        int totalNews = listNews.size();
        int totalPage = totalNews % pageSize == 0 ? (totalNews / pageSize) : ((totalNews / pageSize) + 1);
        int page = (page_raw == null) ? 1 : Integer.parseInt(page_raw);
        int start = (page - 1) * pageSize;
        int end = Math.min(page * pageSize, totalNews);
        List<News> listN = d.getListByPage(listNews, start, end);
        request.setAttribute("listNews", listN);
        request.setAttribute("page", page);
        request.setAttribute("totalPage", totalPage);
        List<NewsCategory> listNc = d.getAllNewsCategory();
        request.setAttribute("listNc", listNc);
        request.setAttribute("category_id", category_id);
        request.getRequestDispatcher("news.jsp").forward(request, response);
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
