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
@WebServlet(name = "updatePolicyServlet", urlPatterns = {"/updatePolicy"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class updatePolicyServlet extends HttpServlet {

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
            out.println("<title>Servlet updatePolicyServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet updatePolicyServlet at " + request.getContextPath() + "</h1>");
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
        String policy_id_raw = request.getParameter("policy_id");
        int policy_id;
        policy_id = Integer.parseInt(policy_id_raw);
        Insurance_policy p = dao.getPolicyById(policy_id);
        List<String> listStatus = List.of("active", "inactive");
        request.setAttribute("listStatus", listStatus);
        request.setAttribute("policy", p);
        request.getRequestDispatcher("updatePolicy.jsp").forward(request, response);

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
        String policy_id_raw = request.getParameter("policy_id");
        int policy_id = Integer.parseInt(policy_id_raw);
        Insurance_policy iP = dao.getPolicyById(policy_id);
        String policy_name = request.getParameter("policy_name");
        String description = request.getParameter("description");
        String coverage_amount_raw = request.getParameter("coverage_amount");
        String premium_amount_raw = request.getParameter("premium_amount");
        String status = request.getParameter("status");
        List<String> listStatus = List.of("active", "inactive");      
        List<Insurance_policy> listP = dao.getPolicyByInsuranceID(i.getInsurance_id());
        coverage_amount_raw = coverage_amount_raw.replaceAll(",", "");
        premium_amount_raw = premium_amount_raw.replaceAll(",", "");
        double coverage_amount = 0, premium_amount = 0;
        policy_name = policy_name.replaceAll("<[^>]*>", "").replaceAll("&nbsp;", "").trim();
        description = description.replaceAll("<[^>]*>", "").replaceAll("&nbsp;", "").trim();
        
        if (policy_name.isEmpty()){
            request.setAttribute("error", "Policy Name must not null");
            request.setAttribute("listStatus", listStatus);
            request.setAttribute("policy", iP);
            request.setAttribute("listPolicy", listP);
            request.getRequestDispatcher("updatePolicy.jsp").forward(request, response);
        }
        if (description.trim().isEmpty()) {
            request.setAttribute("error", "Description must not null");
            request.setAttribute("listStatus", listStatus);
            request.setAttribute("policy", iP);
            request.setAttribute("listPolicy", listP);
            request.getRequestDispatcher("updatePolicy.jsp").forward(request, response);
        }
        try {
            coverage_amount = Double.parseDouble(coverage_amount_raw);
            premium_amount = Double.parseDouble(premium_amount_raw);

            if (coverage_amount <= 0 || premium_amount <= 0) {
                request.setAttribute("error", "Coverage Amount and Premium Amount must be greater than 0!");
                request.setAttribute("listStatus", listStatus);
                request.setAttribute("policy", iP);
                request.setAttribute("listPolicy", listP);
                request.getRequestDispatcher("updatePolicy.jsp").forward(request, response);

            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Coverage Amount and Premium Amount must be valid numbers!");
            request.setAttribute("listStatus", listStatus);
            request.setAttribute("policy", iP);
            request.setAttribute("listPolicy", listP);
            request.getRequestDispatcher("updatePolicy.jsp").forward(request, response);

        }

        policy_name = policy_name.trim().replaceAll("\\s+", " ");
        Insurance_policy in = dao.getPolicyByName(policy_name);
        if (in != null && in.getPolicy_id() != policy_id) {
            request.setAttribute("error", "policy name " + policy_name + " existed!");
            request.setAttribute("listStatus", listStatus);
            request.setAttribute("policy", iP);
            request.setAttribute("listPolicy", listP);
            request.getRequestDispatcher("updatePolicy.jsp").forward(request, response);
        }
        
    Part filePart = request.getPart("file");
String fileName = filePart.getSubmittedFileName();
String image = iP.getImage(); // Lấy ảnh cũ nếu không có ảnh mới

if (fileName != null && !fileName.isEmpty()) {
    String fileExt = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
    if (!fileExt.equals(".jpg") && !fileExt.equals(".jpeg") && !fileExt.equals(".png")) {
        request.setAttribute("error", "Only JPG, JPEG, PNG files are allowed!");
        request.setAttribute("policy", iP);
        request.setAttribute("listPolicy", listP);
        request.getRequestDispatcher("updatePolicy.jsp").forward(request, response);
        return;
    }

    // Định nghĩa đường dẫn lưu file
    String uploadDir = getServletContext().getRealPath("") + "images/InsurancePolicy";
    File dir = new File(uploadDir);
    if (!dir.exists()) {
        dir.mkdirs();
    }

    String filePath = uploadDir + File.separator + fileName;
    System.out.println("Saving file to: " + filePath); // Debug đường dẫn ảnh
    filePart.write(filePath);

    image = "images/InsurancePolicy/" + fileName; // Cập nhật ảnh mới
} else {
    System.out.println("No new image uploaded, keeping old image: " + image);
}

        Insurance_policy p = new Insurance_policy(policy_id, policy_name, description, status, image, coverage_amount, premium_amount);
        dao.updatePolicy(p);
        session.setAttribute("showSuccessModal", true);
        session.setAttribute("successMessage", "Policy '" + policy_name + "' update successfully!");
        String url = "paginationInsurancePolicy?quantity=5";
        response.sendRedirect(url);
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
