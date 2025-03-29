package controlller_Accountant;

import dal.InsuranceContractDAO;
import model.Insurance_contract;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ListInsuranceContractsServlet", urlPatterns = {"/list-insurance-contracts"})
public class ListInsuranceContractsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy tham số tìm kiếm
        String search = request.getParameter("search");
        if(search == null) {
            search = "";
        } else {
            // Xử lý loại bỏ khoảng trắng dư thừa
            search = search.trim().replaceAll("\\s+", " ");
        }
        
        // Lấy số trang từ request, mặc định là 1
        int page = 1;
        String pageParam = request.getParameter("page");
        if(pageParam != null && !pageParam.isEmpty()){
            try {
                page = Integer.parseInt(pageParam);
            } catch(NumberFormatException e) {
                page = 1;
            }
        }
        int pageSize = 10;
        int offset = (page - 1) * pageSize;
        
        InsuranceContractDAO contractDAO = new InsuranceContractDAO();
        List<Insurance_contract> contracts = null;
        int totalRecords = 0;
        try {
            contracts = contractDAO.getAllInsuranceContracts(search, offset, pageSize);
            totalRecords = contractDAO.getTotalInsuranceContractsCount(search);
        } catch (Exception ex) {
            Logger.getLogger(ListInsuranceContractsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int totalPages = (int)Math.ceil(totalRecords / (double)pageSize);
        request.setAttribute("contracts", contracts);
        request.setAttribute("search", search);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("/list-insurance-contracts.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
