/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_Service;

import dal.DAO_Loan;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Paths;
import model.Loan;
import model.ServiceTerms;

/**
 *
 * @author DELL
 */
@MultipartConfig // Xử lý ảnh

@WebServlet(name = "SendLoanRequestServlet", urlPatterns = {"/SendLoanRequest"})
public class SendLoanRequestServlet extends HttpServlet {

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
            out.println("<title>Servlet SendLoanRequestServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SendLoanRequestServlet at " + request.getContextPath() + "</h1>");
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
        DAO_Loan d = new DAO_Loan();
        List<ServiceTerms> list = d.getLoanServiceTerms(); // doi ten
        request.setAttribute("listS", list);
        request.getRequestDispatcher("addLoanRequest.jsp").forward(request, response);
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
        String customer_id_raw = request.getParameter("customer_id");
        String duration_raw = request.getParameter("duration");
        String amount_raw = request.getParameter("amount");
        String loan_type = request.getParameter("loan_type");
        String value_asset_raw = request.getParameter("value_asset");
        String notes = request.getParameter("notes");
        String serviceTerm_id_raw = request.getParameter("service_terms");

        Part asset_image = request.getPart("asset_image");

        DAO_Loan d = new DAO_Loan();
        double amount, value_asset;
        int duration, serviceTerm_id, customer_id;
        try {
            customer_id = Integer.parseInt(customer_id_raw);
            amount = Double.parseDouble(amount_raw.replace(".", ""));
            value_asset = Double.parseDouble(value_asset_raw.replace(".", ""));
            duration = Integer.parseInt(duration_raw.replaceAll("\\D+", "")); 
            serviceTerm_id = Integer.parseInt(serviceTerm_id_raw);

            Date start_date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(start_date);
            calendar.add(Calendar.MONTH, duration);
            Date end_date = calendar.getTime();
            // Xu ly anh 
            String fileName = Paths.get(asset_image.getSubmittedFileName()).getFileName().toString();
            if ((!fileName.endsWith(".png") && !fileName.endsWith(".jpg"))) {
                request.setAttribute("error", "Chỉ được chọn file có đuôi .jpg và .png!");
                List<ServiceTerms> list = d.getLoanServiceTerms(); // doi ten
                request.setAttribute("listS", list);
                request.getRequestDispatcher("addLoanRequest.jsp").forward(request, response);
                return;
            }
            String uploadPath = getServletContext().getRealPath("/") + "imageAsset/" + fileName;
            asset_image.write(uploadPath);

            Loan loan = new Loan(customer_id, serviceTerm_id, amount, start_date,
                    end_date, fileName, notes, loan_type, value_asset, "pending");
            d.InsertLoanRequest(loan);
            request.setAttribute("message", "Gửi đơn thành công !!!");          
            List<ServiceTerms> list = d.getLoanServiceTerms(); // doi ten
            request.setAttribute("listS", list);
            request.getRequestDispatcher("addLoanRequest.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            System.out.println(e);
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
