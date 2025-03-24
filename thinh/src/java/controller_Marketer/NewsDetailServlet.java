/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
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
import model.Customer;
import model.News;
import model.Notifications;
import model.Staff;

/**
 *
 * @author Acer Nitro Tiger
 */
@WebServlet(name = "NewsDetailServlet", urlPatterns = {"/newsDetail"})
public class NewsDetailServlet extends HttpServlet {

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
            out.println("<title>Servlet NewsDetailServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NewsDetailServlet at " + request.getContextPath() + "</h1>");
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
        DAO_Marketer d = new DAO_Marketer();
        String news_raw = request.getParameter("news_id");
        try {
            int news_id = Integer.parseInt(news_raw);
            //get view
            d.getView(news_id);
            //get view detail
            News newsDetail = d.getNewsDetail(news_id);
            request.setAttribute("newsDetail", newsDetail);
            //gte related news
            List<News> listRelatedNews = d.getRelatedNews(news_id);
            request.setAttribute("listRelatedNews", listRelatedNews);

        } catch (Exception e) {
        }
        DAO dao = new DAO();
        HttpSession session = request.getSession(false);
        String username = "";
        if (session != null) {
            Object account = session.getAttribute("account");
            if (account != null) {
                if (account instanceof Staff) {
                    username = ((Staff) account).getUsername();
                } else if (account instanceof Customer) {
                    username = ((Customer) account).getUsername();
                }
                List<Notifications> listNotify = dao.getAllNotificationsByCustomerId(username);
                request.setAttribute("listNotify", listNotify);
                int countNotify = dao.getTotalNotifyById(username);
                request.setAttribute("countNotify", countNotify);
            }
        }
        request.getRequestDispatcher("newsDetail.jsp").forward(request, response);

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
