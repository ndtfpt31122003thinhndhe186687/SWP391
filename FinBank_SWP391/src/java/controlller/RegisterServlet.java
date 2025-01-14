package controlller;

import dal.DAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.User;

public class RegisterServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RegisterServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String fname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String username = request.getParameter("user");
        String password = request.getParameter("pass");
        String address = request.getParameter("address");
        String dobString = request.getParameter("dob");
        String genderString = request.getParameter("gender");
        String profilePicture = request.getParameter("profilePicture");

        // Validate input
       if (fname == null || fname.isEmpty() || email == null || email.isEmpty() || phone == null || phone.isEmpty()
                || username == null || username.isEmpty() || password == null || password.isEmpty()
                || address == null || address.isEmpty() || dobString == null || dobString.isEmpty()
                 || genderString == null || genderString.isEmpty() || profilePicture == null || profilePicture.isEmpty()) {
             request.setAttribute("error", "Please fill all fields!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Convert date string to java.util.Date
        Date dob = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dob = dateFormat.parse(dobString);
        } catch (ParseException e) {
            request.setAttribute("error", "Invalid date format (yyyy-MM-dd)!");
             request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }
        Date createdAt = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createdAtString = dateFormat.format(createdAt);


        if (DAO.INSTANCE.existedAcc(username)) {
            request.setAttribute("error", "Username existed!");
            request.getRequestDispatcher("Views/register.jsp").forward(request, response);
        } else if (DAO.INSTANCE.existedEmail(email)) {
            request.setAttribute("error", "Email existed!");
             request.getRequestDispatcher("register.jsp").forward(request, response);
        } else if (DAO.INSTANCE.existedPhoneNum(phone)) {
              request.setAttribute("error", "Phone existed!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } else {

            User us = new User(0, fname, email, password, phone, address,createdAtString, genderString, dob, profilePicture);
            DAO.INSTANCE.register(us);
           request.setAttribute("ms1", "Account successfully created!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}