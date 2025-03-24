/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller_Insurance;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Windows
 */
@WebServlet(name="VNPayInsuranceReturnServlet", urlPatterns={"/VNPayInsuranceReturn"})
public class VNPayInsuranceReturnServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet VNPayInsuranceReturnServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VNPayInsuranceReturnServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
         Map<String, String[]> paramMap = request.getParameterMap();
        Map<String, String> vnp_Params = new HashMap<>();
        for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
            vnp_Params.put(entry.getKey(), entry.getValue()[0]);
        }

        String vnp_SecureHash = vnp_Params.remove("vnp_SecureHash");
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        for (String fieldName : fieldNames) {
           hashData.append(fieldName).append("=").append(vnp_Params.get(fieldName));
if (!fieldName.equals(fieldNames.get(fieldNames.size() - 1))) {
    hashData.append("&");
}

        }
        String secureHash = hmacSHA512("G6TMMI8YU5KR900RRF8N3Z92VSBI7766", hashData.substring(0, hashData.length() - 1));

        if (secureHash.equals(vnp_SecureHash)) {
            response.getWriter().write("Giao dịch thành công! Mã đơn hàng: " + vnp_Params.get("vnp_TxnRef"));
        } else {
            response.getWriter().write("Giao dịch không hợp lệ!");
        }
    }

    public static String hmacSHA512(String key, String data) {
    try {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        Mac mac = Mac.getInstance("HmacSHA512");
        mac.init(secretKeySpec);
        byte[] hmacBytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        StringBuilder hash = new StringBuilder();
        for (byte b : hmacBytes) {
            hash.append(String.format("%02x", b));
        }
        return hash.toString();
    } catch (Exception e) {
        throw new RuntimeException("Lỗi tạo HMAC SHA-512", e);
    }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
