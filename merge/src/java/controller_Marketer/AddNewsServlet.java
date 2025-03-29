package controller_Marketer;

import dal.DAO;
import dal.DAO_Marketer;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.nio.file.Paths;
import java.util.List;
import model.NewsCategory;
import model.Staff;

/**
 *
 * @author Acer Nitro Tiger
 */
@WebServlet(name = "AddNewsServlet", urlPatterns = {"/marketer/addNews"})
@MultipartConfig
public class AddNewsServlet extends HttpServlet {

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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddNewsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddNewsServlet at " + request.getContextPath() + "</h1>");
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
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DAO_Marketer d = new DAO_Marketer();
        List<NewsCategory> listNc = d.getAllNewsCategory();
        request.setAttribute("listNc", listNc);
        request.getRequestDispatcher("addNews.jsp").forward(request, response);
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
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Lấy dữ liệu từ form
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            String categoryId_raw = request.getParameter("category");

            DAO_Marketer d = new DAO_Marketer();

            // Chuẩn hóa dữ liệu đầu vào
            if (title != null) {
                title = title.trim();
            }
            if (content != null) {
                content = content.trim();
            }
            // Kiểm tra điều kiện hợp lệ
            if (title == null || content == null || title.isEmpty() || content.isEmpty() || title.matches(".*\\s{2,}.*") || content.matches(".*\\s{2,}.*")) {
                request.setAttribute("error", "Hãy nhập lại đúng giá trị!");
                List<NewsCategory> listNc = d.getAllNewsCategory();
                request.setAttribute("listNc", listNc);
                request.getRequestDispatcher("addNews.jsp").forward(request, response);
                return;
            }
            // Kiểm tra ký tự đặc biệt (chỉ cho phép chữ cái, số và một số ký tự cơ bản)
            if (!title.matches("^[a-zA-Z0-9À-ỹ\\s.,!?()-]+$")
                    || !content.matches("^[a-zA-Z0-9À-ỹ\\s.,!?()-]+$")) {
                request.setAttribute("err", "Tiêu đề và nội dung không được chứa ký tự đặc biệt!");
                request.setAttribute("listNc", d.getAllNewsCategory());
                request.getRequestDispatcher("addNews.jsp").forward(request, response);
                return;
            }

            // Kiểm tra tiêu đề đã tồn tại hay chưa
            if (d.isTitleExist(title)) {
                request.setAttribute("err", "Bài viết với tiêu đề này đã tồn tại!");
                request.setAttribute("listNc", d.getAllNewsCategory());
                request.getRequestDispatcher("addNews.jsp").forward(request, response);
                return;
            }

            // Lấy file ảnh
            Part filePart = request.getPart("image");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

            // Kiểm tra nếu không có ảnh
            if (fileName == null || fileName.isEmpty()) {
                request.setAttribute("err", "Please upload an image!");
                List<NewsCategory> listNc = d.getAllNewsCategory();
                request.setAttribute("listNc", listNc);
                request.getRequestDispatcher("addNews.jsp").forward(request, response);
                return;
            }
            if ((!fileName.endsWith(".png") && !fileName.endsWith(".jpg"))) {
                request.setAttribute("err", "Only .jpg or .png images are allowed!");
                List<NewsCategory> listNc = d.getAllNewsCategory();
                request.setAttribute("listNc", listNc);
                request.getRequestDispatcher("addNews.jsp").forward(request, response);
                return;
            }

            // Định dạng đường dẫn lưu ảnh
            String uploadPath = getServletContext().getRealPath("/") + "imageNews/" + fileName;
            filePart.write(uploadPath);

            // Lấy thông tin nhân viên từ session
            HttpSession session = request.getSession();
            Staff staff = (Staff) session.getAttribute("account");

            // Thêm tin tức vào database
            d.addNews(Integer.parseInt(categoryId_raw), title, content, staff.getStaff_id(), fileName);

            // Chuyển hướng sau khi thêm thành công
            String redirectUrl = "newsManage?staff_id=" + staff.getStaff_id() + "&categoryId=0&status=all&sort=all&page=1&pageSize=4";
            response.sendRedirect(redirectUrl);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("err", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("addNews.jsp").forward(request, response);
        }
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
