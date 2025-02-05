package controlller_Marketer;

import dal.DAO;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Customer;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Lấy thông tin từ request
        String fullName = request.getParameter("fullname");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String phone = request.getParameter("phone");
        String password = request.getParameter("pass");
        String address = request.getParameter("address");
        String cardtype = request.getParameter("card_type");        
        String dobString = request.getParameter("dob");
        String gender = request.getParameter("gender");
        String profilePicture = request.getParameter("profilePicture");

        // Kiểm tra dữ liệu đầu vào
        if (fullName == null || fullName.isEmpty() || username == null || username.isEmpty() || phone == null || phone.isEmpty()
                || password == null || password.isEmpty() || address == null || address.isEmpty()
                || dobString == null || dobString.isEmpty() || gender == null || gender.isEmpty()
                || profilePicture == null || profilePicture.isEmpty()|| cardtype==null||cardtype.isEmpty()||email==null||email.isEmpty()) {
            request.setAttribute("error", "Please fill all fields!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Chuyển đổi `dobString` thành java.sql.Date
        Date dob = null;
        java.sql.Date sqlDob = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dob = dateFormat.parse(dobString);
            sqlDob = new java.sql.Date(dob.getTime()); // Chuyển sang java.sql.Date để lưu vào DB
        } catch (ParseException e) {
            request.setAttribute("error", "Invalid date format! Please use yyyy-MM-dd.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Khởi tạo DAO để xử lý logic
        DAO dao = new DAO();

        // Kiểm tra số điện thoại đã tồn tại chưa
        if (dao.existedPhoneNum(phone)) {
            request.setAttribute("error", "Phone number already exists!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Tạo đối tượng Customer mới
        Customer c = new Customer(fullName, email, username, password, phone, address, cardtype, gender, profilePicture, dob);

        // Đăng ký tài khoản
        dao.register(c);

        // Đăng ký thành công, chuyển hướng tới trang đăng nhập
        request.setAttribute("message", "Account successfully created! Please log in.");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for user registration";
    }
}