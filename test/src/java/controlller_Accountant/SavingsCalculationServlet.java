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
        ServiceTerms selectedTerm = null;

        try {
            if (depositAmountStr != null) {
                depositAmountStr = depositAmountStr.replaceAll(",", ""); // Loại bỏ dấu phẩy nếu có
                depositAmount = Double.parseDouble(depositAmountStr);
            }
            
            if (savingDurationStr != null) {
                savingDuration = Integer.parseInt(savingDurationStr);
            }
            
            // Kiểm tra điều khoản tiết kiệm
            int termId = Integer.parseInt(termIdStr);
            selectedTerm = serviceTermDAO.getTermById(termId);
            
            // Kiểm tra tính hợp lệ của dữ liệu nhập vào
            if (depositAmount <= 0 || savingDuration <= 0 || selectedTerm == null) {
                errorMsg = "Please enter valid parameters.";
            }
        } catch (NumberFormatException e) {
            errorMsg = "Invalid input format. Please check your values.";
        } catch (Exception e) {
            errorMsg = "Input error. Please check again.";
        }

        if (errorMsg == null) {
            // Tính toán lãi suất và số tiền cuối cùng
            double annualRate = selectedTerm.getInterest_rate();
            double years = savingDuration / 12.0; // Chuyển đổi số tháng sang năm
            double interestEarned = depositAmount * (annualRate / 100) * years;
            double finalAmount = depositAmount + interestEarned;

            // Lưu kết quả tính toán vào request
            request.setAttribute("savingInterestEarned", interestEarned);
            request.setAttribute("savingFinalAmount", finalAmount);
        } else {
            // Thông báo lỗi nếu có
            request.setAttribute("savingError", errorMsg);
        }

        // Lấy lại danh sách điều khoản tiết kiệm để hiển thị lại form
        List<ServiceTerms> savingTerms = serviceTermDAO.getSavingTerms();
        request.setAttribute("savingTerms", savingTerms);
        
        // Chuyển tiếp dữ liệu đến trang JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("savingsCalculator.jsp");
        dispatcher.forward(request, response);
    }
}
