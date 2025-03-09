/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_Insurance;

import dal.DAO_Insurance;
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
import java.io.File;
import java.util.List;
import model.Insurance;
import model.Insurance_policy;


/**
 *
 * @author Windows
 */
@WebServlet(name = "AddPolicyServlet", urlPatterns = {"/addPolicy"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class AddPolicyServlet extends HttpServlet {

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
            out.println("<title>Servlet AddPolicyServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddPolicyServlet at " + request.getContextPath() + "</h1>");
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
        DAO_Insurance dao = new DAO_Insurance();
        HttpSession session = request.getSession();
        Insurance i = (Insurance) session.getAttribute("account");
        int insurance_Id = i.getInsurance_id();
        List<Insurance_policy> list = dao.getPolicyByInsuranceID(i.getInsurance_id());
        String policy_name = request.getParameter("policy_name");
        String description = request.getParameter("description");
        String coverage_amount_raw = request.getParameter("coverage_amount");
        String premium_amount_raw = request.getParameter("premium_amount");
        String status = request.getParameter("status");

    Part fileImage = request.getPart("file");
    String fileName = fileImage.getSubmittedFileName();


    if (fileName != null && !fileName.isEmpty()) {
        String fileExt = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        if (!fileExt.equals(".jpg") && !fileExt.equals(".jpeg") && !fileExt.equals(".png")) {
            request.setAttribute("error", "Chỉ JPG, JPEG, PNG được chấp thuận!");
            request.setAttribute("listPolicy", list);
            request.getRequestDispatcher("managerInsurancePolicy.jsp").forward(request, response);
            return;
        }


        String uploadDir = getServletContext().getRealPath("") + "/InsurancePolicy";
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }


        String filePath = uploadDir + File.separator + fileName;
        fileImage.write(filePath);
    }


    String image = fileName;
        coverage_amount_raw = coverage_amount_raw.replaceAll(",", "");
        premium_amount_raw = premium_amount_raw.replaceAll(",", "");
        double coverage_amount, premium_amount;
        
        
        
        description = description.replaceAll("<[^>]*>", "").replaceAll("&nbsp;", "").trim();
        if (policy_name.trim().isEmpty()) {
            request.setAttribute("error", "Tên không được để trống");
            request.setAttribute("listPolicy", list);
            request.getRequestDispatcher("managerInsurancePolicy.jsp").forward(request, response);
            return;
        }

        if (description.trim().isEmpty()) {
            request.setAttribute("error", "Mô tả không được để trống");
            request.setAttribute("listPolicy", list);
            request.getRequestDispatcher("managerInsurancePolicy.jsp").forward(request, response);
            return;
        }

        try {
            coverage_amount = Double.parseDouble(coverage_amount_raw);
            premium_amount = Double.parseDouble(premium_amount_raw);
            if (coverage_amount <= 0 || premium_amount <= 0) {
                request.setAttribute("error", "Tiền được nhận và tiền cần đóng phải lớn hơn 0!");
                request.setAttribute("listPolicy", list);
                request.getRequestDispatcher("managerInsurancePolicy.jsp").forward(request, response);
                return;
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Tiền được nhận và tiền cần đóng phải là số thực");
            request.setAttribute("listPolicy", list);
            request.getRequestDispatcher("managerInsurancePolicy.jsp").forward(request, response);
            return;
        }

        try {
            policy_name = policy_name.trim().replaceAll("\\s+", " ");
            Insurance_policy in = dao.getPolicyByName(policy_name);
            if (in != null) {
                request.setAttribute("error", "Tên " + policy_name + " đã tồn tại!");
                request.setAttribute("listPolicy", list);
                request.getRequestDispatcher("managerInsurancePolicy.jsp").forward(request, response);
            } else {
                Insurance_policy p = new Insurance_policy(i.getInsurance_id(), policy_name, description, status, coverage_amount, premium_amount,image);
                dao.insertPolicy(p);
                session.setAttribute("showSuccessModal", true);
                session.setAttribute("successMessage", "Chính sách '" + policy_name + "' đã được thêm thành công!");
                String url = "paginationInsurancePolicy?quantity=5";
                response.sendRedirect(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
