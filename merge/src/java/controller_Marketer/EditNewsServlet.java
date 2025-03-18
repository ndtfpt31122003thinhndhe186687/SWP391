/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_Marketer;

import dal.DAO_Marketer;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.nio.file.Paths;
import java.util.List;
import model.News;

/**
 *
 * @author Acer Nitro Tiger
 */
@WebServlet(name = "EditNewsServlet", urlPatterns = {"/marketer/editNews"})
@MultipartConfig

public class EditNewsServlet extends HttpServlet {

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
            out.println("<title>Servlet EditNewsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditNewsServlet at " + request.getContextPath() + "</h1>");
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
        String newsId_raw = request.getParameter("news_id");
        String categoryId_raw = request.getParameter("category_id");
        DAO_Marketer d = new DAO_Marketer();
        int news_id, category_id;
        try {
            news_id = Integer.parseInt(newsId_raw);
            category_id = Integer.parseInt(categoryId_raw);
            News n = d.getNewsByID(news_id, category_id);
            request.setAttribute("news", n);
            request.getRequestDispatcher("editNews.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            System.out.println(e);
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
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String newsId_raw = request.getParameter("news_id");
        String staffId_raw = request.getParameter("staff_id");
        String categoryId_raw = request.getParameter("category_id");
        int news_id = Integer.parseInt(newsId_raw);
        int staff_id = Integer.parseInt(staffId_raw);
        int categoryId = Integer.parseInt(categoryId_raw);

        DAO_Marketer d = new DAO_Marketer();
            News oldNews = d.getNewsByID(news_id, categoryId);

        if (title != null) {
            title = title.trim();
        }else{
            title = oldNews.getTitle(); 
        }
        if (content != null) {
            content = content.trim();
        }else{
            content=oldNews.getContent();
        }
        if (title.isEmpty() || content.isEmpty() || title.matches(".*\\s{2,}.*") || content.matches(".*\\s{2,}.*")) {
            request.setAttribute("error", "Please enter again!");
            News n = d.getNewsByID(news_id, categoryId);
            request.setAttribute("news", n);
            request.getRequestDispatcher("editNews.jsp").forward(request, response);
            return;
        }
        // Lấy file ảnh
        String oldImage = request.getParameter("oldImage");
        Part filePart = request.getPart("image");
        String imagePath;
        if (filePart != null && filePart.getSize() > 0) {
            imagePath = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            //check duoi png or jpg
            if ((!imagePath.endsWith(".png") && !imagePath.endsWith(".jpg"))) {
                request.setAttribute("err", "Only .jpg or .png images are allowed!");
                request.getRequestDispatcher("editNews.jsp").forward(request, response);
                return;
            }
            String uploadPath = getServletContext().getRealPath("/imageNews") + "/" + imagePath;
            filePart.write(uploadPath);
        } else {
            imagePath = oldImage;
        }

        d.editNews(title, content, news_id, staff_id, categoryId, imagePath);
        String redirectUrl = "newsManage?staff_id=" + staff_id + "&categoryId=0&status=all&sort=all&page=1&pageSize=4";
        response.sendRedirect(redirectUrl);
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
