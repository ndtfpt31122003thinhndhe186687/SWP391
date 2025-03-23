/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_Insurance;

import dal.DAO_Insurance;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import model.Insurance;
import model.Insurance_policy;
import model.Insurance_term;

/**
 *
 * @author Windows
 */
@WebServlet(name = "addInsuranceTermServlet", urlPatterns = {"/addInsuranceTerm"})
public class AddInsuranceTermServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddInsuranceTermServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddInsuranceTermServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DAO_Insurance dao = new DAO_Insurance();
        HttpSession session = request.getSession();
        Insurance i = (Insurance) session.getAttribute("account");
        List<Insurance_policy> list = dao.getPolicyByInsuranceIDAndActive(i.getInsurance_id(), "active");
        request.setAttribute("listPolicy", list);
        request.getRequestDispatcher("managerInsuranceTerm.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        DAO_Insurance dao = new DAO_Insurance();
        HttpSession session = request.getSession();
        Insurance i = (Insurance) session.getAttribute("account");
        List<Insurance_term> listT = dao.getInsuranceTermByInsuranceID(i.getInsurance_id());
        List<Insurance_policy> list = dao.getPolicyByInsuranceIDAndActive(i.getInsurance_id(), "active");
        String policy_id_raw = request.getParameter("policy_id");
        String term_name = request.getParameter("term_name");
        String term_description = request.getParameter("term_description");
        String start_date_raw = request.getParameter("start_date");
        String end_date_raw = request.getParameter("end_date");
        String status = request.getParameter("status");
        int policy_id = Integer.parseInt(policy_id_raw);
        Date start_date = null, end_date = null;
        java.sql.Date sqlStart_date = null, sqlEnd_date = null;
        term_description = term_description.replaceAll("&nbsp;", "").trim();
        if (term_name.trim().isEmpty()) {
            request.setAttribute("error", "Tên không được để trống");
            request.setAttribute("listPolicy", list);
            request.setAttribute("listTerm", listT);
            request.getRequestDispatcher("managerInsuranceTerm.jsp").forward(request, response);
            return;
        }
        if (term_description.trim().isEmpty()) {
            request.setAttribute("error", "Mô tả không được để trống");
            request.setAttribute("listPolicy", list);
            request.setAttribute("listTerm", listT);
            request.getRequestDispatcher("managerInsuranceTerm.jsp").forward(request, response);
            return;
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false); // Bật kiểm tra giá trị chặt chẽ
            start_date = dateFormat.parse(start_date_raw);
            end_date = dateFormat.parse(end_date_raw);
            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, 0);
            today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0);
            today.set(Calendar.MILLISECOND, 0);
            Date todayDate = today.getTime();
            if (start_date.before(todayDate)) {
                request.setAttribute("error", "Ngày bắt đầu phải từ hôm nay trở đi.");
                request.setAttribute("listPolicy", list);
                request.setAttribute("listTerm", listT);
                request.getRequestDispatcher("managerInsuranceTerm.jsp").forward(request, response);
                return;
            }

            if (!start_date.before(end_date)) {
                request.setAttribute("error", "Ngày bắt đầu phải trước ngày kết thúc");
                request.setAttribute("listPolicy", list);
                request.setAttribute("listTerm", listT);
                request.getRequestDispatcher("managerInsuranceTerm.jsp").forward(request, response);
                return;
            }
            sqlStart_date = new java.sql.Date(start_date.getTime());
            sqlEnd_date = new java.sql.Date(end_date.getTime());
        } catch (ParseException e) {
            request.setAttribute("error", "Sai định dạng. Định dạng đúng: yyyy-MM-dd.");
            request.setAttribute("listPolicy", list);
            request.setAttribute("listTerm", listT);
            request.getRequestDispatcher("managerInsuranceTerm.jsp").forward(request, response);
            return;
        }
        try {
            term_name = term_name.trim().replaceAll("\\s+", " ");
            Insurance_term termName = dao.getInsuranceTermByName(term_name);
            if (termName != null) {
                request.setAttribute("error", "Tên : " + term_name + " đã tồn tại!");
                request.setAttribute("listPolicy", list);
                request.setAttribute("listTerm", listT);
                request.getRequestDispatcher("managerInsuranceTerm.jsp").forward(request, response);
                return;
            } else {
                Insurance_term t = new Insurance_term(i.getInsurance_id(),
                        policy_id, term_name, term_description, status, start_date, end_date);
                dao.insertInsuranceTerm(t);
                session.setAttribute("showSuccessModal", true);
                session.setAttribute("successMessage", "Điều khoản " + term_name + " đã được thêm thành công!");
                String url = "sortInsuranceTerm?sortInsuranceTerm=none&status=all&quantity=5";
                response.sendRedirect(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
