package controller_Admin;

import dal.DAO_Admin;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Services;

@WebServlet(name = "SearchServiceServlet", urlPatterns = {"/searchService"})
public class SearchServiceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String search = request.getParameter("searchName");

        DAO_Admin d = new DAO_Admin();

        List<Services> services;
        if (search != null && !search.trim().isEmpty()) {
            String search_raw;
            search_raw = search.trim().toLowerCase().replaceAll("\\s+", " ");
            search_raw = "%" + search_raw.replace(" ", "%") + "%";
            services = d.searchServiceByName(search_raw);

            request.setAttribute("ListByName", services);
            request.setAttribute("searchName", search);
        } else {
            services = d.getAllServices();
        }

        // Always set the service attribute for displaying the table
        request.setAttribute("service", services);

        request.getRequestDispatcher("service management.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
