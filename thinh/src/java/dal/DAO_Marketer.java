package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.News;
import model.NewsView;
import model.ServiceTerms;
import model.NewsCategory;

public class DAO_Marketer extends DBContext {

    public static DAO_Marketer INSTANCE = new DAO_Marketer();
    private Connection con;
    private String status = "OK";

    public DAO_Marketer() {
        try {
            con = new DBContext().getConnection();
        } catch (Exception e) {
            status = "Error at connection: " + e.getMessage();
        }
    }

    public static DAO_Marketer getINSTANCE() {
        return INSTANCE;
    }

    public static void setINSTANCE(DAO_Marketer INSTANCE) {
        DAO_Marketer.INSTANCE = INSTANCE;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<NewsCategory> getAllNewsCategory() {
        List<NewsCategory> list = new ArrayList<>();
        String sql = "select * from news_category";

        try (PreparedStatement st = con.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                NewsCategory n = new NewsCategory();
                n.setCategory_id(rs.getInt("category_id"));
                n.setCategory_name(rs.getString("category_name"));
                list.add(n);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<News> getAllNews() {
        List<News> listNews = new ArrayList<>();
        String sql = "SELECT n.*, c.category_name FROM news n "
                + "JOIN news_category c ON n.category_id = c.category_id "
                + "WHERE n.status = 'approved'";

        try (PreparedStatement st = con.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                News n = new News();
                n.setNews_id(rs.getInt("news_id"));
                n.setTitle(rs.getString("title"));
                n.setContent(rs.getString("content"));
                n.setStaff_id(rs.getInt("staff_id"));
                n.setCreated_at(rs.getDate("created_at"));
                n.setCategory_name(rs.getString("category_name"));
                n.setCategory_id(rs.getInt("category_id"));
                n.setPicture(rs.getString("picture"));
                listNews.add(n);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return listNews;
    }

    public List<News> getAllNewsByCategory(int category_id) {
        List<News> listNews = new ArrayList<>();
        String sql = "SELECT n.*, c.category_name FROM news n "
                + "JOIN news_category c ON n.category_id = c.category_id "
                + "WHERE n.status = 'approved'and n.category_id=?";

        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, category_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                News n = new News();
                n.setNews_id(rs.getInt("news_id"));
                n.setTitle(rs.getString("title"));
                n.setContent(rs.getString("content"));
                n.setStaff_id(rs.getInt("staff_id"));
                n.setCreated_at(rs.getDate("created_at"));
                n.setCategory_name(rs.getString("category_name"));
                n.setCategory_id(rs.getInt("category_id"));
                n.setPicture(rs.getString("picture"));
                listNews.add(n);
            }
        } catch (Exception e) {
        }
        return listNews;
    }

    //news detail
    public News getNewsDetail(int news_id) {
        String sql = "select title,content,picture from news where news_id=?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, news_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                News news = new News();
                news.setTitle(rs.getString("title"));
                news.setContent(rs.getString("content"));
                news.setPicture(rs.getString("picture"));
                return news;
            }
        } catch (Exception e) {
        }
        return null;
    }

    //insert news
    public void addNews(int category_id, String title, String content, int staff_id, String picture) {
        String sql = "insert into news(category_id,title,content,staff_id,picture) values (?,?,?,?,?)";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, category_id);
            pre.setString(2, title);
            pre.setString(3, content);
            pre.setInt(4, staff_id);
            pre.setString(5, picture);
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //delete news
    public void deleteNews(int news_id, int category_id) {
        String sql = "DELETE FROM news WHERE news_id = ? and category_id=? AND status not in ('approved','pending')";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, news_id);
            pre.setInt(2, category_id);
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //edit news
    public void editNews(String title, String content, int news_id, int staff_id, int category_id, String picture) {
        String sql = "UPDATE news\n"
                + "SET title = ?, \n"
                + "    content = ?, \n"
                + "    picture = ?, \n"
                + "    updated_at = GETDATE()\n"
                + "WHERE news_id = ? AND staff_id = ? and category_id=? AND status IN ('draft');";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, title);
            pre.setString(2, content);
            pre.setString(3, picture);
            pre.setInt(4, news_id);
            pre.setInt(5, staff_id);
            pre.setInt(6, category_id);
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //get news by id
    public News getNewsByID(int news_id, int category_id) {
        String sql = "select n.*,c.category_name from news n join news_category "
                + "c on n.category_id=c.category_id\n"
                + "where n.news_id=? and c.category_id=?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, news_id);
            pre.setInt(2, category_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                News news = new News();
                news.setNews_id(rs.getInt("news_id"));
                news.setCategory_id(rs.getInt("category_id"));
                news.setTitle(rs.getString("title"));
                news.setContent(rs.getString("content"));
                news.setStaff_id(rs.getInt("staff_id"));
                news.setCreated_at(rs.getDate("created_at"));
                news.setUpdated_at(rs.getDate("updated_at"));
                news.setPicture(rs.getString("picture"));
                news.setCategory_name(rs.getString("category_name"));
                return news;
            }
        } catch (Exception e) {
        }
        return null;
    }

    //send news to adm in(update status to pending)
    public void sendNews(int news_id) {
        String sql = "update news set status='pending' where news_id=?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, news_id);
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //cancel sending
    public void cancelSend(int news_id) {
        String sql = "update news set status='draft' where news_id=?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, news_id);
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //get views when view news detail
    public void getView(int news_id) {
        String sql = "insert into news_views(news_id) values (?)";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, news_id);
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // Search news by title
    public List<News> getSearchNewsByTitle(String title, int staff_id) {
        List<News> list = new ArrayList<>();
        String sql = "SELECT n.*, c.category_name FROM news n "
                + "JOIN news_category c ON n.category_id = c.category_id "
                + "WHERE n.title LIKE ? AND n.staff_id = ?";

        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, "%" + title + "%");
            st.setInt(2, staff_id);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    News n = new News();
                    n.setNews_id(rs.getInt("news_id"));
                    n.setStaff_id(rs.getInt("staff_id"));
                    n.setTitle(rs.getString("title"));
                    n.setContent(rs.getString("content"));
                    n.setCreated_at(rs.getDate("created_at"));
                    n.setUpdated_at(rs.getDate("updated_at"));
                    n.setStatus(rs.getString("status"));
                    n.setCategory_id(rs.getInt("category_id"));
                    n.setCategory_name(rs.getString("category_name"));
                    n.setPicture(rs.getString("picture"));
                    list.add(n);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    //view new statistic
    public List<NewsView> countNews() {
        List<NewsView> list = new ArrayList<>();
        String sql = "select n.title,nv.news_id,count(*) as newsAmount from news_views nv join news n on n.news_id=nv.news_id \n"
                + "group by n.title,nv.news_id ";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                NewsView n = new NewsView();
                n.setNews_id(rs.getInt("news_id"));
                n.setTitle(rs.getString("title"));
                n.setNewsAmount(rs.getInt("newsAmount"));
                list.add(n);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    //Total article
    public int totalArticle() {
        String sql = "select count(*) as totalArticle from news where status='approved'";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return rs.getInt("totalArticle");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    //Total views
    public int totalView() {
        String sql = "select count(*) as totalView from news_views";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return rs.getInt("totalView");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    // Get news filter
    public List<News> getNewsFilter(int category_id, int staff_id, String status, String sortBy) {
        List<News> list = new ArrayList<>();
        String sql = "SELECT n.*, c.category_name FROM news n "
                + "JOIN news_category c ON n.category_id = c.category_id "
                + "WHERE 1=1";
        if (category_id > 0) {
            sql += " AND n.category_id = ?";
        }
        if (staff_id > 0) {
            sql += " AND n.staff_id = ?";
        }
        if (!status.isEmpty() && !status.equals("all") && status != null) {
            sql += " AND n.status = ?";
        }
        // Sắp xếp theo ngày tạo giảm dần (tin mới nhất trước)
        if (sortBy != null && !sortBy.equals("all") && !sortBy.isEmpty()) {
            sql += " ORDER BY n.created_at DESC, n." + sortBy;
        } else {
            sql += " ORDER BY n.created_at DESC"; 
        }
        try {
            PreparedStatement st = con.prepareStatement(sql);
            int index = 1;
            if (category_id > 0) {
                st.setInt(index++, category_id);
            }
            if (staff_id > 0) {
                st.setInt(index++, staff_id);
            }
            if (!status.isEmpty() && !status.equals("all") && status != null) {
                st.setString(index++, status);
            }
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                News n = new News();
                n.setNews_id(rs.getInt("news_id"));
                n.setTitle(rs.getString("title"));
                n.setContent(rs.getString("content"));
                n.setCreated_at(rs.getDate("created_at"));
                n.setUpdated_at(rs.getDate("updated_at"));
                n.setStatus(rs.getString("status"));
                n.setCategory_name(rs.getString("category_name"));
                n.setCategory_id(rs.getInt("category_id"));
                n.setPicture(rs.getString("picture"));
                list.add(n);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    // Lấy danh sách lãi suất theo loại (loan hoặc saving)
    public List<ServiceTerms> getInterestRates(String type) {
        List<ServiceTerms> list = new ArrayList<>();
        String sql = "SELECT st.*, t.duration FROM service_terms st "
                + "JOIN term t ON st.term_id = t.term_id "
                + "WHERE st.status = 'active'";
        if ("loan".equalsIgnoreCase(type)) {
            sql += " AND st.min_payment > 0";
        } else if ("saving".equalsIgnoreCase(type)) {
            sql += " AND st.min_deposit > 0";
        }

        try (PreparedStatement st = con.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                ServiceTerms s = new ServiceTerms();
                s.setServiceTerm_id(rs.getInt("serviceTerm_id"));
                s.setTerm_id(rs.getInt("term_id"));
                s.setTerm_name(rs.getString("term_name"));
                s.setService_id(rs.getInt("service_id"));
                s.setDescription(rs.getString("description"));
                s.setContract_terms(rs.getString("contract_terms"));
                s.setDuration(rs.getInt("duration")); // Lấy duration từ bảng term
                s.setEarly_payment_penalty(rs.getDouble("early_payment_penalty"));
                s.setInterest_rate(rs.getDouble("interest_rate"));
                s.setMin_payment(rs.getDouble("min_payment"));
                s.setMin_deposit(rs.getDouble("min_deposit"));
                s.setStatus(rs.getString("status"));
                s.setCreated_at(rs.getTimestamp("created_at"));
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    //get related news
    public List<News> getRelatedNews(int news_id) {
        List<News> list = new ArrayList<>();
        String sql = "SELECT Top 4 news_id, title FROM news \n"
                + "WHERE category_id = (SELECT category_id FROM news WHERE news_id = ?)\n"
                + "AND news_id <> ? \n"
                + "AND status = 'approved' \n"
                + "ORDER BY news_id ";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, news_id);
            ps.setInt(2, news_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                News n = new News();
                n.setNews_id(rs.getInt("news_id"));
                n.setTitle(rs.getString("title"));
                list.add(n);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<News> getListByPage(List<News> list, int start, int end) {
        List<News> arr = new ArrayList<>();
        for (int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }
    
    //Thống kê số lượng bài viết theo tháng
    public int getNewsCountByMonth() {
        String query = "SELECT COUNT(news_id) AS total_news " +
                       "FROM news GROUP BY YEAR(created_at), MONTH(created_at) " +
                       "ORDER BY year DESC, month DESC";
        try (
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                return rs.getInt("total_news");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    //Thống kê số lượng bài viết theo danh mục
    public int getNewsAmountByCategory(int category_id){
        String sql="select count(*) as total from news where category_id=? and status='approved'";
        try {
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, category_id);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                return rs.getInt("total");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

   
    public static void main(String[] args) {
        DAO_Marketer d = new DAO_Marketer();
        List<News> list = d.getAllNewsByCategory(1);
        for (News serviceTerms : list) {
            System.out.println(serviceTerms);
        }
    }

}
