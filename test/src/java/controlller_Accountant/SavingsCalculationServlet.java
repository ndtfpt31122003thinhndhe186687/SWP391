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

@WebServlet("/calculateSaving")
public class SavingsCalculationServlet extends HttpServlet {
    private ServiceTermDAO serviceTermDAO;
    
    @Override
    public void init() throws ServletException {
        serviceTermDAO = new ServiceTermDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy danh sách điều khoản tiết kiệm từ DB
        List<ServiceTerms> savingTerms = serviceTermDAO.getSavingTerms();
        request.setAttribute("savingTerms", savingTerms);
        RequestDispatcher dispatcher = request.getRequestDispatcher("savingsCalculator.jsp");
        dispatcher.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy các tham số cho tính toán tiết kiệm
        String termIdStr = request.getParameter("savingTermId");
        String depositAmountStr = request.getParameter("savingAmount");
        String savingDurationStr = request.getParameter("savingDuration");
        String errorMsg = null;
        double depositAmount = 0;
        int savingDuration = 0;
        int termId = 0;
        ServiceTerms selectedTerm = null;
        try {
            termId = Integer.parseInt(termIdStr);
            depositAmount = Double.parseDouble(depositAmountStr);
            savingDuration = Integer.parseInt(savingDurationStr);
            selectedTerm = serviceTermDAO.getTermById(termId);
            if (depositAmount <= 0 || savingDuration <= 0 || selectedTerm == null) {
                errorMsg = "Please enter valid parameters.";
            }
        } catch (Exception e) {
            errorMsg = "Input error. Please check again.";
        }
        
        if (errorMsg == null) {
            double annualRate = selectedTerm.getInterest_rate();
            double years = savingDuration / 12.0;
            // Sử dụng công thức lãi đơn: finalAmount = principal + (principal * rate * years)
            double interestEarned = depositAmount * (annualRate / 100) * years;
            double finalAmount = depositAmount + interestEarned;
            request.setAttribute("savingInterestEarned", interestEarned);
            request.setAttribute("savingFinalAmount", finalAmount);
        } else {
            request.setAttribute("savingError", errorMsg);
        }
        
        // Lấy lại danh sách điều khoản tiết kiệm để hiển thị lại form
        List<ServiceTerms> savingTerms = serviceTermDAO.getSavingTerms();
        request.setAttribute("savingTerms", savingTerms);
        RequestDispatcher dispatcher = request.getRequestDispatcher("savingsCalculator.jsp");
        dispatcher.forward(request, response);
    }
}
