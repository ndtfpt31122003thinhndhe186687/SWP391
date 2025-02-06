/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controlller;

import dal.AmountStatisticsDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author default
 */
@WebServlet(name = "AmountStatisticsServlet", urlPatterns = {"/amountStatistics"})
public class AmountStatisticsServlet extends HttpServlet {

    private final AmountStatisticsDAO amountStatisticsDAO = new AmountStatisticsDAO(); // Instantiate DAO

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<AmountStatisticsDAO.Table> tables = amountStatisticsDAO.getTablesWithAmountColumn(); //Get list of tables

        Map<AmountStatisticsDAO.Table, List<Map<String,Object>>> allTableStatistics = new HashMap<>(); //Map for results

        for(AmountStatisticsDAO.Table table : tables){
            List<Map<String, Object>> statistics = amountStatisticsDAO.getAmountStatistics(table); //Get statistics
            allTableStatistics.put(table, statistics); //Add to map
        }


        request.setAttribute("allTableStatistics", allTableStatistics); // Set data for JSP
        request.getRequestDispatcher("/amountStatistics.jsp").forward(request, response); // Forward to JSP
    }
}
