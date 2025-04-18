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
    import java.text.SimpleDateFormat;
    import java.util.Date;
    import java.util.List;
    import model.Insurance;
    import model.Insurance_policy;
    import model.Insurance_term;

    /**
     *
     * @author Windows
     */
    @WebServlet(name="updateInsuranceTermServlet", urlPatterns={"/updateInsuranceTerm"})
    public class updateInsuranceTermServlet extends HttpServlet {

        /** 
         * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
                out.println("<title>Servlet updateInsuranceTermServlet</title>");  
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Servlet updateInsuranceTermServlet at " + request.getContextPath () + "</h1>");
                out.println("</body>");
                out.println("</html>");
            }
        } 

        // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
        /** 
         * Handles the HTTP <code>GET</code> method.
         * @param request servlet request
         * @param response servlet response
         * @throws ServletException if a servlet-specific error occurs
         * @throws IOException if an I/O error occurs
         */
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            DAO_Insurance dao = new DAO_Insurance();
            String term_id_raw = request.getParameter("term_id");
            try {
                int term_id = Integer.parseInt(term_id_raw);
                Insurance_term t = dao.getInsuranceTermByID(term_id);
                List<Insurance_policy> listPolicy = dao.getPolicyByInsuranceIDAndActive(t.getInsurance_id(), "active");
                List<String> listStatus = List.of("Hoạt động", "Ngừng hoạt động");             
                request.setAttribute("term", t);
                request.setAttribute("listPolicy", listPolicy);
                request.setAttribute("listStatus", listStatus);
                request.getRequestDispatcher("updateInsuranceTerm.jsp").forward(request, response);
            } catch (Exception e) {
            }

        } 


        /** 
         * Handles the HTTP <code>POST</code> method.
         * @param request servlet request
         * @param response servlet response
         * @throws ServletException if a servlet-specific error occurs
         * @throws IOException if an I/O error occurs
         */
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            DAO_Insurance dao = new DAO_Insurance();
            HttpSession session = request.getSession();
            Insurance i = (Insurance) session.getAttribute("account");
            String term_id_raw = request.getParameter("term_id");
            String policy_id_raw = request.getParameter("policy_id");
            String term_name = request.getParameter("term_name");
            String term_description = request.getParameter("term_description");
            String start_date_raw = request.getParameter("start_date");
            String end_date_raw = request.getParameter("end_date");
            String status = request.getParameter("status");
            int term_id = Integer.parseInt(term_id_raw);
            Insurance_term iT = dao.getInsuranceTermByID(term_id);
             Date start_date = null,end_date = null;
            java.sql.Date sqlStart_date = null, sqlEnd_date = null;       
            int policy_id = Integer.parseInt(policy_id_raw);
             List<Insurance_policy> listPolicy = dao.getPolicyByInsuranceIDAndActive(i.getInsurance_id(), "active");
             List<String> listStatus = List.of("Hoạt động", "Ngừng hoạt động"); 
            term_description = term_description.replaceAll("&nbsp;", "").trim();
             if(term_name.trim().isEmpty()){
                request.setAttribute("error", "Tên không được để trống");
                List<Insurance_policy> list = dao.getPolicyByInsuranceIDAndActive(i.getInsurance_id(), "active");
                request.setAttribute("listPolicy", list);
                 request.getRequestDispatcher("managerInsuranceTerm.jsp").forward(request, response);
                return;
            }
                    if(term_description.trim().isEmpty()){
                request.setAttribute("error", "Mô tả không được để trống");
                List<Insurance_policy> list = dao.getPolicyByInsuranceIDAndActive(i.getInsurance_id(), "active");
                request.setAttribute("listPolicy", list);
                 request.getRequestDispatcher("managerInsuranceTerm.jsp").forward(request, response);
                return;
            }

             try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                dateFormat.setLenient(false); // Bật kiểm tra giá trị chặt chẽ
                start_date = dateFormat.parse(start_date_raw);
                end_date = dateFormat.parse(end_date_raw);
                if (!start_date.before(end_date)) {
                    request.setAttribute("listPolicy", listPolicy);
                    request.setAttribute("term", iT);
                    request.setAttribute("listStatus", listStatus);
                    request.setAttribute("error", "Ngày bắt đầu phải trước ngày kết thúc.");
                    request.getRequestDispatcher("updateInsuranceTerm.jsp").forward(request, response);
                    return;

                }
                sqlStart_date = new java.sql.Date(start_date.getTime());
                sqlEnd_date = new java.sql.Date(end_date.getTime());
            } catch (ParseException e) {
                request.setAttribute("listPolicy", listPolicy);
                request.setAttribute("term", iT);
                request.setAttribute("listStatus", listStatus);
                request.setAttribute("error", "Sai định dạng. Định dạng đúng: yyyy-MM-dd.");
                request.getRequestDispatcher("updateInsuranceTerm.jsp").forward(request, response);
                    return;

            }
            term_name = term_name.trim().replaceAll("\\s+", " ");
             Insurance_term iTName = dao.getInsuranceTermByName(term_name);
            if(iTName != null && iTName.getTerm_id() != term_id){
                request.setAttribute("listPolicy", listPolicy);
                request.setAttribute("term", iT);
                request.setAttribute("listStatus", listStatus);
                request.setAttribute("error", "Tên : " + term_name + " đã tồn tại!");
                request.getRequestDispatcher("updateInsuranceTerm.jsp").forward(request, response);
                    return;
            }

            Insurance_term t = new Insurance_term(term_id, i.getInsurance_id(), 
                    policy_id, term_name, term_description, status, start_date, end_date);
            dao.updateInsuranceTerm(t);
            session.setAttribute("showSuccessModal", true);
                session.setAttribute("successMessage", "Điều khoản " + term_name + " đã được sửa     thành công!");
            String url = "managerInsuranceTerm?insurance_id=" +i.getInsurance_id();
            response.sendRedirect(url);
        }

        /** 
         * Returns a short description of the servlet.
         * @return a String containing servlet description
         */
        @Override
        public String getServletInfo() {
            return "Short description";
        }// </editor-fold>

    }
