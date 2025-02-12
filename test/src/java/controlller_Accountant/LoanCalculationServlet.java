package controlller_Accountant;

import dal.ServiceTermDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ServiceTerms;

import java.io.IOException;
import java.util.List;

@WebServlet("/calculateLoan")
public class LoanCalculationServlet extends HttpServlet {
    private ServiceTermDAO serviceTermDAO;
    
    @Override
    public void init() throws ServletException {
        serviceTermDAO = new ServiceTermDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy danh sách điều khoản vay từ DB
        List<ServiceTerms> loanTerms = serviceTermDAO.getLoanTerms();
        request.setAttribute("loanTerms", loanTerms);
        RequestDispatcher dispatcher = request.getRequestDispatcher("loanCalculator.jsp");
        dispatcher.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy các tham số cho tính toán vay
        String termIdStr = request.getParameter("loanTermId");
        String loanAmountStr = request.getParameter("loanAmount");
        String loanDurationStr = request.getParameter("loanDuration"); // Số tháng vay
        String errorMsg = null;
        double loanAmount = 0;
        int loanDuration = 0;
        int termId = 0;
        ServiceTerms selectedTerm = null;
        try {
            termId = Integer.parseInt(termIdStr);
            loanAmount = Double.parseDouble(loanAmountStr);
            loanDuration = Integer.parseInt(loanDurationStr);
            selectedTerm = serviceTermDAO.getTermById(termId);
            if (loanAmount <= 0 || loanDuration <= 0 || selectedTerm == null) {
                errorMsg = "Please enter valid parameters.";
            }
        } catch (Exception e) {
            errorMsg = "Input error. Please check again.";
        }
        
        if (errorMsg == null) {
            // Lấy lãi suất hàng năm từ điều khoản đã chọn
            double annualRate = selectedTerm.getInterest_rate();
            double monthlyRate = annualRate / 12 / 100;
            // Công thức trả góp định kỳ (annuity)
            double monthlyPayment = loanAmount * monthlyRate * Math.pow(1 + monthlyRate, loanDuration)
                                    / (Math.pow(1 + monthlyRate, loanDuration) - 1);
            double totalPayment = monthlyPayment * loanDuration;
            double totalInterest = totalPayment - loanAmount;
            request.setAttribute("loanMonthlyPayment", monthlyPayment);
            request.setAttribute("loanTotalPayment", totalPayment);
            request.setAttribute("loanTotalInterest", totalInterest);
        } else {
            request.setAttribute("loanError", errorMsg);
        }
        
        // Lấy lại danh sách điều khoản vay để hiển thị lại form
        List<ServiceTerms> loanTerms = serviceTermDAO.getLoanTerms();
        request.setAttribute("loanTerms", loanTerms);
        RequestDispatcher dispatcher = request.getRequestDispatcher("loanCalculator.jsp");
        dispatcher.forward(request, response);
    }
}
