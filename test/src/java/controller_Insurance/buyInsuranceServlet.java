/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_Insurance;

import dal.DAO_Insurance;
import dal.DAO_Loan;
import dal.SavingDAO;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import model.Customer;
import model.Insurance;
import model.Insurance_contract;
import model.Insurance_contract_detail;
import model.Insurance_policy;
import model.Insurance_transactions;
import model.Loan;

/**
 *
 * @author Windows
 */
@WebServlet(name = "buyInsuranceServlet", urlPatterns = {"/buyInsurance"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class buyInsuranceServlet extends HttpServlet {

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
            out.println("<title>Servlet buyInsuranceServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet buyInsuranceServlet at " + request.getContextPath() + "</h1>");
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
        DAO_Loan daoL = new DAO_Loan();

        HttpSession session = request.getSession();
        Customer c = (Customer) session.getAttribute("account");
        String insurance_id_raw = request.getParameter("insurance_id");
        int insurance_id = 0;
        try {
            insurance_id = Integer.parseInt(insurance_id_raw);
        } catch (Exception e) {
        }
        List<Loan> listL = daoL.getListLoanByCustomerIdAndNote(c.getCustomer_id());
        List<Insurance_policy> listP = dao.getPolicyByInsuranceIDAndActive(insurance_id, "active");
        request.setAttribute("insurance_id", insurance_id);
        request.setAttribute("listLoan", listL);
        request.setAttribute("listPolicy", listP);
        request.getRequestDispatcher("buyInsurance.jsp").forward(request, response);
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
        DAO_Loan daoL = new DAO_Loan();
        SavingDAO daoS = new SavingDAO();
        HttpSession session = request.getSession();
        Customer c = (Customer) session.getAttribute("account");
        List<Loan> listL = daoL.getListLoanByCustomerIdAndNote(c.getCustomer_id());

        if (c == null) {
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        String contract_name = request.getParameter("contract_name");
        String insurance_id_raw = request.getParameter("insurance_id");
        String policy_id_raw = request.getParameter("policy_id");
        String coverage_amount_raw = request.getParameter("coverage_amount");
        String premium_amount_raw = request.getParameter("premium_amount");
        String paid_amount_raw = request.getParameter("paid_amount");
        String payment_frequency = request.getParameter("payment_frequency");
        String duration_raw = request.getParameter("duration");
        String loan_id_raw = request.getParameter("loan_id");

        coverage_amount_raw = coverage_amount_raw.replaceAll("\\.", "");
        premium_amount_raw = premium_amount_raw.replaceAll("\\.", "");
        paid_amount_raw = paid_amount_raw.replaceAll("\\.", "");
        int policy_id = 0, duration = 0, insurance_id = 0, loan_id = 0;
        int service_id = 2;
        double coverage_amount = 0, premium_amount = 0, paid_amount = 0;
        Date start_date = null, end_date = null;
        insurance_id = Integer.parseInt(insurance_id_raw);
        policy_id = Integer.parseInt(policy_id_raw);
        insurance_id = Integer.parseInt(insurance_id_raw);
        duration = Integer.parseInt(duration_raw);
        loan_id = Integer.parseInt(loan_id_raw);
        coverage_amount = Double.parseDouble(coverage_amount_raw);
        premium_amount = Double.parseDouble(premium_amount_raw);
        List<Insurance_policy> listP = dao.getPolicyByInsuranceIDAndActive(insurance_id, "active");
        Part fileImage = request.getPart("file");
        String fileName = fileImage.getSubmittedFileName();

        if (fileName == null) {
            request.setAttribute("error", "Ảnh không được rỗng!");
            request.setAttribute("insurance_id", insurance_id);
            request.setAttribute("listLoan", listL);
            request.setAttribute("listPolicy", listP);
            request.getRequestDispatcher("buyInsurance.jsp").forward(request, response);
            return;
        }
        if (fileName != null && !fileName.isEmpty()) {
            String fileExt = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
            if (!fileExt.equals(".jpg") && !fileExt.equals(".jpeg") && !fileExt.equals(".png")) {
                request.setAttribute("error", "Chỉ JPG, JPEG, PNG được chấp thuận!");
                request.setAttribute("insurance_id", insurance_id);
                request.setAttribute("listLoan", listL);
                request.setAttribute("listPolicy", listP);
                request.getRequestDispatcher("buyInsurance.jsp").forward(request, response);
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
        Insurance_policy imageP = dao.getPolicyByImage(image);
        if (imageP != null) {
            request.setAttribute("error", "Ảnh " + image + " đã tồn tại");
            request.setAttribute("insurance_id", insurance_id);
            request.setAttribute("listLoan", listL);
            request.setAttribute("listPolicy", listP);
            request.getRequestDispatcher("buyInsurance.jsp").forward(request, response);
            return;
        }

        if (contract_name.trim().isEmpty()) {
            request.setAttribute("error", "Tên không được để trống");
            request.setAttribute("insurance_id", insurance_id);
            request.setAttribute("listLoan", listL);
            request.setAttribute("listPolicy", listP);
            request.getRequestDispatcher("buyInsurance.jsp").forward(request, response);
            return;
        }

        contract_name = contract_name.trim().replaceAll("\\s+", " ");
        Insurance_contract icn = dao.getInsuranceContractByContractName(contract_name);
        if (icn != null) {
            request.setAttribute("error", "Tên " + contract_name + " đã tồn tại!");
            request.setAttribute("insurance_id", insurance_id);
            request.setAttribute("listLoan", listL);
            request.setAttribute("listPolicy", listP);
            request.getRequestDispatcher("buyInsurance.jsp").forward(request, response);
            return;
        }
        try {

            paid_amount = Double.parseDouble(paid_amount_raw);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Số tiền nộp phải là số!");
            request.setAttribute("insurance_id", insurance_id);
            request.setAttribute("listLoan", listL);
            request.setAttribute("listPolicy", listP);
            request.getRequestDispatcher("buyInsurance.jsp").forward(request, response);
            return;
        }

        if (paid_amount < 0) {
            request.setAttribute("error", "Số tiền nộp phải lớn hoặc bằng 0!");
            request.setAttribute("insurance_id", insurance_id);
            request.setAttribute("listLoan", listL);
            request.setAttribute("listPolicy", listP);
            request.getRequestDispatcher("buyInsurance.jsp").forward(request, response);
            return;
        }

        if (paid_amount > premium_amount) {
            request.setAttribute("error", "Tiền cần đóng phải nhỏ hơn tiền cần nộp");
            request.setAttribute("insurance_id", insurance_id);
            request.setAttribute("listLoan", listL);
            request.setAttribute("listPolicy", listP);
            request.getRequestDispatcher("buyInsurance.jsp").forward(request, response);
            return;
        }

        if (paid_amount > c.getAmount()) {
            request.setAttribute("error", "Tài khoản của bạn không đủ tiền để thanh toán");
            request.setAttribute("insurance_id", insurance_id);
            request.setAttribute("listLoan", listL);
            request.setAttribute("listPolicy", listP);
            request.getRequestDispatcher("buyInsurance.jsp").forward(request, response);
            return;
        }

        if (duration <= 0) {
            request.setAttribute("error", "Thời hạn hợp đồng phải là số nguyên lớn hơn 0");
            request.setAttribute("insurance_id", insurance_id);
            request.setAttribute("listLoan", listL);
            request.setAttribute("listPolicy", listP);
            request.getRequestDispatcher("buyInsurance.jsp").forward(request, response);
        }

        Loan loan = daoL.getLoanByLoanIdAndDate(loan_id); // Lấy khoản vay

        // Chuyển `java.util.Date` sang `java.sql.Date`
        start_date = new java.sql.Date(loan.getStart_date().getTime());
        end_date = new java.sql.Date(loan.getEnd_date().getTime());

        // Chuyển đổi ngày sang `LocalDate`
        LocalDate startLocalDate = ((java.sql.Date) start_date).toLocalDate();
        LocalDate endLocalDate = ((java.sql.Date) end_date).toLocalDate();

        // Tính toán ngày kết thúc mới
        LocalDate calculatedEndDate = startLocalDate.plusMonths(duration);

        // Kiểm tra xem ngày kết thúc có hợp lệ không
        if (calculatedEndDate.isAfter(endLocalDate)) {
            request.setAttribute("error", "Thời hạn kết thúc bảo hiểm phải nhỏ hơn thời hạn kết thúc khoản vay.");
            request.setAttribute("insurance_id", insurance_id);
            request.setAttribute("listLoan", listL);
            request.setAttribute("listPolicy", listP);
            request.getRequestDispatcher("buyInsurance.jsp").forward(request, response);
            return;
        } else {
            end_date = java.sql.Date.valueOf(calculatedEndDate);
        }

        if (payment_frequency.equals("quarterly")) {
            if (duration < 3) {
                request.setAttribute("error", "Thời hạn hợp đồng phải lớn hơn hoặc bằng 3 khi chọn trả theo quý");
                request.setAttribute("insurance_id", insurance_id);
                request.setAttribute("listLoan", listL);
                request.setAttribute("listPolicy", listP);
                request.getRequestDispatcher("buyInsurance.jsp").forward(request, response);
                return;
            }
        }
        if (payment_frequency.equals("annually")) {
            if (duration < 12) {
                request.setAttribute("error", "Thời hạn hợp đồng phải lớn hơn hoặc bằng 12 khi chọn trả theo năm");
                request.setAttribute("insurance_id", insurance_id);
                request.setAttribute("listLoan", listL);
                request.setAttribute("listPolicy", listP);
                request.getRequestDispatcher("buyInsurance.jsp").forward(request, response);
                return;
            }
        }

        Insurance_contract ic = new Insurance_contract(loan_id,c.getCustomer_id(), service_id, policy_id,
                duration, start_date, end_date, payment_frequency, "pending", contract_name, image);

        Loan loanNew = daoL.getLoanByLoanIdAndInsuranceID(loan_id, insurance_id, policy_id);
        if (loanNew != null) {
            request.setAttribute("error", "Khoản vay này đã mua chính sách bảo hiểm");
            request.setAttribute("insurance_id", insurance_id);
            request.setAttribute("listLoan", listL);
            request.setAttribute("listPolicy", listP);
            request.getRequestDispatcher("buyInsurance.jsp").forward(request, response);
            return;
        }

        dao.insertInsuranceContract(ic);
        int contract_id = dao.getLastInsertedContractId(c.getCustomer_id());

        Insurance_contract_detail icd = new Insurance_contract_detail(contract_id, insurance_id, coverage_amount, premium_amount, paid_amount,
                start_date, end_date);
//
//        Insurance_transactions it = new Insurance_transactions(contract_id, c.getCustomer_id(), paid_amount,
//                "premium_payment", "Nộp tiền bảo hiểm.");
        dao.insertContractDetail(icd);
//        dao.insertInsuranceTransaction(it);
//        daoS.updateAmount(c.getCustomer_id(), paid_amount);
        session.setAttribute("showSuccessModal", true);
        SavingDAO d = new SavingDAO();
        d.insertNotification(c.getCustomer_id(), contract_id, "Mua bảo hiểm",
                "Yêu cầu mua bảo hiểm đã được gửi!");
        session.setAttribute("successMessage", "Yêu cầu mua bảo hiểm đã được gửi!");
        response.sendRedirect("home.jsp");

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
