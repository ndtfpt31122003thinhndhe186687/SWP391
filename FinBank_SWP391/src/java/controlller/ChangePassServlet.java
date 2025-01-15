package controlller;

import dal.DAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

@WebServlet(name = "ChangePassServlet", urlPatterns = {"/ChangePassServlet"})
public class ChangePassServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (java.io.PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ChangePassServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChangePassServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
           request.getRequestDispatcher("changeInfor.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
           response.setContentType("text/html;charset=UTF-8");
        String email = request.getParameter("email");
        String oldPass = request.getParameter("opass");
        String newPass = request.getParameter("pass");

        // Validate Input
          if (email == null || email.isEmpty() || oldPass == null || oldPass.isEmpty() || newPass == null || newPass.isEmpty()) {
              request.setAttribute("error", "Please fill all fields!");
               request.getRequestDispatcher("changeInfor.jsp").forward(request, response);
              return;
        }


        DAO dao = new DAO();
        User user = dao.check(email, oldPass);

        if (user == null) {
            //Incorrect User or Old password
             request.setAttribute("error", "Incorrect email or old password!");
            request.getRequestDispatcher("changeInfor.jsp").forward(request, response);
            return;
        } else {
             //Change the password
             user.setPassword(newPass);
            dao.change(user);
             request.setAttribute("ms1", "Successfully changed password!");
             HttpSession session = request.getSession();
             session.setAttribute("account", user);
             response.sendRedirect("login.jsp");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}