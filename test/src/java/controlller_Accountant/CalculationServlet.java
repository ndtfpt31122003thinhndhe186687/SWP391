<<<<<<< HEAD
//package controlller_Accountant;
//
//import dal.ServiceTermDAO;
//import jakarta.servlet.RequestDispatcher;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import model.ServiceTerms;
//
//
//import java.io.IOException;
//import java.util.List;
//
//@WebServlet("/calculate")
//public class CalculationServlet extends HttpServlet {
//    private ServiceTermDAO serviceTermDAO;
//    
//    @Override
//    public void init() throws ServletException {
//        serviceTermDAO = new ServiceTermDAO();
//    }
//    
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // Lấy danh sách điều khoản từ DB
//        List<ServiceTerms> loanTerms = serviceTermDAO.getLoanTerms();
//        List<ServiceTerms> savingTerms = serviceTermDAO.getSavingTerms();
//        request.setAttribute("loanTerms", loanTerms);
//        request.setAttribute("savingTerms", savingTerms);
//        RequestDispatcher dispatcher = request.getRequestDispatcher("calculator.jsp");
//        dispatcher.forward(request, response);
//    }
//    
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String action = request.getParameter("action");
//        if("calculateLoan".equals(action)) {
//            calculateLoan(request, response);
//        } else if ("calculateSaving".equals(action)) {
//            calculateSaving(request, response);
//        } else {
//            doGet(request, response);
//        }
//    }
//    
//    private void calculateLoan(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // Lấy các tham số cho tính toán vay
//        String termIdStr = request.getParameter("loanTermId");
//        String loanAmountStr = request.getParameter("loanAmount");
//        String loanDurationStr = request.getParameter("loanDuration"); // Số tháng vay
//        String errorMsg = null;
//        double loanAmount = 0;
//        int loanDuration = 0;
//        int termId = 0;
//        ServiceTerms selectedTerm = null;
//        try {
//            termId = Integer.parseInt(termIdStr);
//            loanAmount = Double.parseDouble(loanAmountStr);
//            loanDuration = Integer.parseInt(loanDurationStr);
//            selectedTerm = serviceTermDAO.getTermById(termId);
//            if(loanAmount <= 0 || loanDuration <= 0 || selectedTerm == null) {
//                errorMsg = "Please enter valid parameters.";
//            }
//        } catch(Exception e) {
//            errorMsg = "Input error. Please check again.";
//        }
//        
//        if(errorMsg == null) {
//            // Lấy lãi suất từ điều khoản được chọn
//            double annualRate = selectedTerm.getInterest_rate();
//            double monthlyRate = annualRate / 12 / 100;
//            // Công thức trả góp định kỳ (annuity):
//            // Payment = P * r * (1 + r)^n / ((1 + r)^n - 1)
//            double monthlyPayment = loanAmount * monthlyRate * Math.pow(1 + monthlyRate, loanDuration) / (Math.pow(1 + monthlyRate, loanDuration) - 1);
//            double totalPayment = monthlyPayment * loanDuration;
//            double totalInterest = totalPayment - loanAmount;
//            request.setAttribute("loanMonthlyPayment", monthlyPayment);
//            request.setAttribute("loanTotalPayment", totalPayment);
//            request.setAttribute("loanTotalInterest", totalInterest);
//        } else {
//            request.setAttribute("loanError", errorMsg);
//        }
//        
//        // Đưa lại danh sách điều khoản
//        List<ServiceTerms> loanTerms = serviceTermDAO.getLoanTerms();
//        List<ServiceTerms> savingTerms = serviceTermDAO.getSavingTerms();
//        request.setAttribute("loanTerms", loanTerms);
//        request.setAttribute("savingTerms", savingTerms);
//        
//        RequestDispatcher dispatcher = request.getRequestDispatcher("calculator.jsp");
//        dispatcher.forward(request, response);
//    }
//    
//    private void calculateSaving(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // Lấy các tham số cho tính toán tiết kiệm
//        String termIdStr = request.getParameter("savingTermId");
//        String depositAmountStr = request.getParameter("savingAmount");
//        String savingDurationStr = request.getParameter("savingDuration");
//        String errorMsg = null;
//        double depositAmount = 0;
//        int savingDuration = 0;
//        int termId = 0;
//        ServiceTerms selectedTerm = null;
//        try {
//            termId = Integer.parseInt(termIdStr);
//            depositAmount = Double.parseDouble(depositAmountStr);
//            savingDuration = Integer.parseInt(savingDurationStr);
//            selectedTerm = serviceTermDAO.getTermById(termId);
//            if(depositAmount <= 0 || savingDuration <= 0 || selectedTerm == null) {
//                errorMsg = "Please enter valid parameters.";
//            }
//        } catch(Exception e) {
//            errorMsg = "Input error. Please check again.";
//        }
//        
//        if(errorMsg == null) {
//            double annualRate = selectedTerm.getInterest_rate();
//            double monthlyRate = annualRate / 12 / 100;
//            // Công thức lãi kép: A = P * (1 + r)^n
//            double finalAmount = depositAmount * Math.pow(1 + monthlyRate, savingDuration);
//            double interestEarned = finalAmount - depositAmount;
//            request.setAttribute("savingInterestEarned", interestEarned);
//            request.setAttribute("savingFinalAmount", finalAmount);
//        } else {
//            request.setAttribute("savingError", errorMsg);
//        }
//        
//        List<ServiceTerms> loanTerms = serviceTermDAO.getLoanTerms();
//        List<ServiceTerms> savingTerms = serviceTermDAO.getSavingTerms();
//        request.setAttribute("loanTerms", loanTerms);
//        request.setAttribute("savingTerms", savingTerms);
//        
//        RequestDispatcher dispatcher = request.getRequestDispatcher("calculator.jsp");
//        dispatcher.forward(request, response);
//    }
//}
=======
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

@WebServlet("/calculate")
public class CalculationServlet extends HttpServlet {
    private ServiceTermDAO serviceTermDAO;
    
    @Override
    public void init() throws ServletException {
        serviceTermDAO = new ServiceTermDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy danh sách điều khoản từ DB
        List<ServiceTerms> loanTerms = serviceTermDAO.getLoanTerms();
        List<ServiceTerms> savingTerms = serviceTermDAO.getSavingTerms();
        request.setAttribute("loanTerms", loanTerms);
        request.setAttribute("savingTerms", savingTerms);
        RequestDispatcher dispatcher = request.getRequestDispatcher("calculator.jsp");
        dispatcher.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if("calculateLoan".equals(action)) {
            calculateLoan(request, response);
        } else if ("calculateSaving".equals(action)) {
            calculateSaving(request, response);
        } else {
            doGet(request, response);
        }
    }
    
    private void calculateLoan(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            if(loanAmount <= 0 || loanDuration <= 0 || selectedTerm == null) {
                errorMsg = "Please enter valid parameters.";
            }
        } catch(Exception e) {
            errorMsg = "Input error. Please check again.";
        }
        
        if(errorMsg == null) {
            // Lấy lãi suất từ điều khoản được chọn
            double annualRate = selectedTerm.getInterest_rate();
            double monthlyRate = annualRate / 12 / 100;
            // Công thức trả góp định kỳ (annuity):
            // Payment = P * r * (1 + r)^n / ((1 + r)^n - 1)
            double monthlyPayment = loanAmount * monthlyRate * Math.pow(1 + monthlyRate, loanDuration) / (Math.pow(1 + monthlyRate, loanDuration) - 1);
            double totalPayment = monthlyPayment * loanDuration;
            double totalInterest = totalPayment - loanAmount;
            request.setAttribute("loanMonthlyPayment", monthlyPayment);
            request.setAttribute("loanTotalPayment", totalPayment);
            request.setAttribute("loanTotalInterest", totalInterest);
        } else {
            request.setAttribute("loanError", errorMsg);
        }
        
        // Đưa lại danh sách điều khoản
        List<ServiceTerms> loanTerms = serviceTermDAO.getLoanTerms();
        List<ServiceTerms> savingTerms = serviceTermDAO.getSavingTerms();
        request.setAttribute("loanTerms", loanTerms);
        request.setAttribute("savingTerms", savingTerms);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("calculator.jsp");
        dispatcher.forward(request, response);
    }
    
    private void calculateSaving(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            if(depositAmount <= 0 || savingDuration <= 0 || selectedTerm == null) {
                errorMsg = "Please enter valid parameters.";
            }
        } catch(Exception e) {
            errorMsg = "Input error. Please check again.";
        }
        
        if(errorMsg == null) {
            double annualRate = selectedTerm.getInterest_rate();
            double monthlyRate = annualRate / 12 / 100;
            // Công thức lãi kép: A = P * (1 + r)^n
            double finalAmount = depositAmount * Math.pow(1 + monthlyRate, savingDuration);
            double interestEarned = finalAmount - depositAmount;
            request.setAttribute("savingInterestEarned", interestEarned);
            request.setAttribute("savingFinalAmount", finalAmount);
        } else {
            request.setAttribute("savingError", errorMsg);
        }
        
        List<ServiceTerms> loanTerms = serviceTermDAO.getLoanTerms();
        List<ServiceTerms> savingTerms = serviceTermDAO.getSavingTerms();
        request.setAttribute("loanTerms", loanTerms);
        request.setAttribute("savingTerms", savingTerms);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("calculator.jsp");
        dispatcher.forward(request, response);
    }
}
>>>>>>> origin/phong
