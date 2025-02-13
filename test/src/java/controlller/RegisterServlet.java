package controlller;

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
import utils.Password;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        // Kiểm tra dữ liệu đầu vào (đảm bảo tất cả các trường đều được nhập)
        if (fullName == null || fullName.isEmpty() ||
            username == null || username.isEmpty() ||
            phone == null || phone.isEmpty() ||
            password == null || password.isEmpty() ||
            address == null || address.isEmpty() ||
            dobString == null || dobString.isEmpty() ||
            gender == null || gender.isEmpty() ||
            profilePicture == null || profilePicture.isEmpty() ||
            cardtype == null || cardtype.isEmpty() ||
            email == null || email.isEmpty()) {
            
            request.setAttribute("error", "Please fill all fields!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Validate password theo yêu cầu:
        // - Ít nhất 8 ký tự
        // - Ít nhất 1 chữ hoa
        // - Ít nhất 1 chữ thường
        // - Ít nhất 1 số
        // - Ít nhất 1 ký tự đặc biệt (những ký tự: !@#$%^&*()_+{}[]:;<>,.?~\-)
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z]).{6,}$";
    if (!password.matches(passwordPattern)) {
        request.setAttribute("error", "Password must be at least 6 characters long, include at least 1 lowercase letter and 1 uppercase letter.");
        request.getRequestDispatcher("register.jsp").forward(request, response);
        return;
    } else {
            // Mã hóa mật khẩu (ví dụ bằng SHA-1)
            password = Password.toSHA1(password);
        }

        // Chuyển đổi chuỗi ngày sinh sang java.sql.Date
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

        // Khởi tạo DAO để xử lý logic (ví dụ đăng ký, kiểm tra số điện thoại đã tồn tại, ...)
        DAO dao = new DAO();

        // Kiểm tra số điện thoại đã tồn tại chưa
        if (dao.existedPhoneNum(phone)) {
            request.setAttribute("error", "Phone number already exists!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Tạo đối tượng Customer mới
        // Lưu ý: Các tham số trong constructor của Customer cần phù hợp với định nghĩa của lớp.
        Customer c = new Customer(fullName, email, username, password, phone, address, cardtype, email, gender, profilePicture, 0, 6, 0, 0, dob, dob);
        
        // Đăng ký tài khoản
        dao.register(c);

        // Sau khi đăng ký thành công, chuyển hướng tới trang đăng nhập và thông báo thành công
        request.setAttribute("message", "Account successfully created! Please log in.");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for user registration";
    }

    // Hàm kiểm tra mật khẩu với yêu cầu: trên 8 ký tự, chứa ít nhất 1 chữ hoa, 1 chữ thường, 1 số và 1 ký tự đặc biệt.
    public static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+{}\\[\\]:;<>,.?~\\-]).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
