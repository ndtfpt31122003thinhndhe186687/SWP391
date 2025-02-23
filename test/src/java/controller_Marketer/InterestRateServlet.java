/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_Marketer;

import dal.DAO_Marketer;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;
import model.ServiceTerms;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author Acer Nitro Tiger
 */
@WebServlet(name = "InterestRateServlet", urlPatterns = {"/downloadPDF"})
public class InterestRateServlet extends HttpServlet {

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
            out.println("<title>Servlet InterestRateServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet InterestRateServlet at " + request.getContextPath() + "</h1>");
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
        String type = request.getParameter("type");
        DAO_Marketer dao = new DAO_Marketer();
        List<ServiceTerms> terms = dao.getInterestRates(type);
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.TIMES_BOLD, 8);

                // Thiết lập kích thước bảng
                float margin = 80;
                float yStart = page.getMediaBox().getHeight() - margin;
                float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
                float rowHeight = 20;
                float colWidth = tableWidth / 5;

                float yPosition = yStart;

                // Tiêu đề trang
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("Interest Rate Of - " + type.toUpperCase());
                contentStream.endText();
                yPosition -= 30;

                // Header của bảng
                drawRow(contentStream, margin, yPosition, colWidth, rowHeight, type, true, null);
                yPosition -= rowHeight;

                // Dữ liệu của bảng
                contentStream.setFont(PDType1Font.TIMES_BOLD, 8);
                for (ServiceTerms term : terms) {
                    drawRow(contentStream, margin, yPosition, colWidth, rowHeight, type, false, term);
                    yPosition -= rowHeight;
                }
            }
            // Xuất file PDF
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline");
            OutputStream out = response.getOutputStream();
            document.save(out);
            document.close();
        }
    }

    private void drawRow(PDPageContentStream contentStream, float margin, float yPosition, float colWidth, float rowHeight, String type, boolean isHeader, ServiceTerms t) throws IOException {
        contentStream.setLineWidth(1f);
        contentStream.moveTo(margin, yPosition);
        contentStream.lineTo(margin + colWidth * 5, yPosition);
        contentStream.stroke();

        contentStream.beginText();
        contentStream.newLineAtOffset(margin + 5, yPosition + 5);

        if (isHeader) {
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
            contentStream.showText("Term Name");
            contentStream.newLineAtOffset(colWidth, 0);
            contentStream.showText("Max Term(Months)");
            contentStream.newLineAtOffset(colWidth, 0);
            contentStream.showText("Interest Rate");
            contentStream.newLineAtOffset(colWidth, 0);
            contentStream.showText("Payment Penalty");
            if (type.equals("loan")) {
                contentStream.newLineAtOffset(colWidth, 0);
                contentStream.showText("Min Payment");
            } else {
                contentStream.newLineAtOffset(colWidth, 0);
                contentStream.showText("Min Deposit");
            }
        } else {
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
            contentStream.showText(t.getTerm_name());
            contentStream.newLineAtOffset(colWidth, 0);
            contentStream.showText(String.valueOf(t.getDuration()));
            contentStream.newLineAtOffset(colWidth, 0);
            contentStream.showText(t.getInterest_rate() + "%");
            contentStream.newLineAtOffset(colWidth, 0);
            contentStream.showText(t.getEarly_payment_penalty() + "%");
            if (type.equals("loan")) {
                contentStream.newLineAtOffset(colWidth, 0);
                contentStream.showText(String.valueOf(t.getMin_payment()));
            } else {
                contentStream.newLineAtOffset(colWidth, 0);
                contentStream.showText(String.valueOf(t.getMin_deposit()));
            }
        }
        contentStream.endText();
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
