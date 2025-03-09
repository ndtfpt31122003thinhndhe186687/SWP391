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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        InsuranceContractDAO contractDAO = new InsuranceContractDAO();
        List<Insurance_contract> contracts = null;
        try {
            contracts = contractDAO.getAllInsuranceContracts();
        } catch (Exception ex) {
            Logger.getLogger(ListInsuranceContractsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.setAttribute("contracts", contracts);
        request.getRequestDispatcher("/list-insurance-contracts.jsp").forward(request, response);
   
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}