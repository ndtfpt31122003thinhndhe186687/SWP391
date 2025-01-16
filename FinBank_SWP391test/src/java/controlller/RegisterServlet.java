package controlller;

import dal.DAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.Customer;
import model.User;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
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
        String password = request.getParameter("pass");
        String address = request.getParameter("address");
        //String cardType=request.getParameter("cardType");
        String dobString = request.getParameter("dob");
        String genderString = request.getParameter("gender");
        String profilePicture = request.getParameter("profilePicture");

        // Validate input
        if (fname == null || fname.isEmpty() || email == null || email.isEmpty() || phone == null || phone.isEmpty()
                || password == null || password.isEmpty()
                || address == null || address.isEmpty() || dobString == null || dobString.isEmpty()
                || genderString == null || genderString.isEmpty() || profilePicture == null || profilePicture.isEmpty()) {
            request.setAttribute("error", "Please fill all fields!");
            request.getRequestDispatcher("register").forward(request, response);
            return;
        }

        // Convert date string to java.util.Date
        Date dob = null;
        java.sql.Date sqlDob=null;
        try {
            // Chuyển chuỗi thành java.util.Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dob = dateFormat.parse(dobString);

            // Chuyển từ java.util.Date sang java.sql.Date
            sqlDob = new java.sql.Date(dob.getTime());

           
            // Sử dụng sqlDob để lưu vào cơ sở dữ liệu (nếu cần)
        } catch (ParseException e) {
            request.setAttribute("error", "Invalid date format (yyyy-MM-dd)!");
            request.getRequestDispatcher("register").forward(request, response);
            return;
        }
        DAO d = new DAO();
        HttpSession session = request.getSession();
        Customer c = (Customer) session.getAttribute("account");
        //check sdt
        if (d.existedPhoneNum(phone)) {
            request.setAttribute("error", "Phone existed!");
            request.getRequestDispatcher("register").forward(request, response);
        } else {
            User u = new User(fname, email, password, phone, address, genderString, profilePicture, sqlDob);
            d.register(u);
            request.setAttribute("ms1", "Account successfully created!");
            request.getRequestDispatcher("login").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
