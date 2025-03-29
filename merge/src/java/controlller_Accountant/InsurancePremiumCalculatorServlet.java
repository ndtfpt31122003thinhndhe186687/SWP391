package controlller_Accountant;

import dal.InsurancePremiumCalculatorDAO;
import model.Insurance_policy;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;

@WebServlet(name = "InsurancePremiumCalculatorServlet", urlPatterns = {"/insuranceCalculator"})
public class InsurancePremiumCalculatorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Hiển thị trang calculator ban đầu
        request.getRequestDispatcher("insuranceCalculator.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            double loanAmount = Double.parseDouble(request.getParameter("loanAmount"));
            // loanTerm is optional; nếu bạn cho phép thay đổi, lấy từ form, nhưng theo yêu cầu là 1 năm.
            // int loanTerm = Integer.parseInt(request.getParameter("loanTerm"));
            int policyId = Integer.parseInt(request.getParameter("policyId"));
            
            InsurancePremiumCalculatorDAO dao = new InsurancePremiumCalculatorDAO();
            Insurance_policy policy = dao.getPolicyById(policyId);
            double premium = 0;
            if (policy != null) {
                premium = dao.calculatePremium(loanAmount, policy);
            } else {
                request.setAttribute("error", "Invalid policy selected.");
            }
            
            // Định dạng kết quả với 3 chữ số sau dấu phẩy
            DecimalFormat df = new DecimalFormat("#,##0.000");
            String formattedPremium = df.format(premium);
            
            request.setAttribute("premium", formattedPremium);
        } catch (Exception ex) {
            request.setAttribute("error", "Invalid input: " + ex.getMessage());
        }
        
        request.getRequestDispatcher("insuranceCalculator.jsp").forward(request, response);
    }
}
