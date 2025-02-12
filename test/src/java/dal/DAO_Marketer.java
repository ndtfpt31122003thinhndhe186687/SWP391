package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Customer;
import model.News;
import model.NewsView;
import model.Staff;

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

    //get all news that is approved
    public List<News> getAllNews() {
        List<News> listNews = new ArrayList<>();
        String sql = "select * from news where status='approved'";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                News n = new News();
                n.setNews_id(rs.getInt("news_id"));
                n.setTitle(rs.getString("title"));
                n.setContent(rs.getString("content"));
                n.setStaff_id(rs.getInt("staff_id"));
                n.setCreated_at(rs.getDate("created_at"));
                listNews.add(n);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return listNews;
    }

    //news detail
    public News getNewsDetail(int news_id) {
        String sql = "select title,content from news where news_id=?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, news_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                News news = new News();
                news.setTitle(rs.getString("title"));
                news.setContent(rs.getString("content"));
                return news;
            }
        } catch (Exception e) {
        }
        return null;
    }

    //insert news
    public void addNews(String title, String content, int staff_id) {
        String sql = "insert into news(title,content,staff_id) values (?,?,?)";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, title);
            pre.setString(2, content);
            pre.setInt(3, staff_id);
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //Get news to crud
    public List<News> getNewsCRUD(int staff_id) {
        List<News> listNews = new ArrayList<>();
        String sql = "select * from news where staff_id=? ";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, staff_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                News n = new News();
                n.setNews_id(rs.getInt("news_id"));
                n.setTitle(rs.getString("title"));
                n.setContent(rs.getString("content"));
                n.setStaff_id(rs.getInt("staff_id"));
                n.setCreated_at(rs.getDate("created_at"));
                n.setUpdated_at(rs.getDate("updated_at"));
                n.setStatus(rs.getString("status"));
                listNews.add(n);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return listNews;
    }

    //delete news
    public void deleteNews(int news_id) {
        String sql = "DELETE FROM news WHERE news_id = ? AND status not in ('approved','pending')";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, news_id);
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //edit news
    public void editNews(String title, String content, int news_id, int staff_id) {
        String sql = "UPDATE news\n"
                + "SET title = ?, \n"
                + "    content = ?, \n"
                + "    updated_at = GETDATE()\n"
                + "WHERE news_id = ? AND staff_id = ? AND status IN ('draft');";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, title);
            pre.setString(2, content);
            pre.setInt(3, news_id);
            pre.setInt(4, staff_id);
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //get news by id
    public News getNewsByID(int news_id) {
        String sql = "select * from news where news_id=?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, news_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                News news = new News();
                news.setNews_id(rs.getInt("news_id"));
                news.setTitle(rs.getString("title"));
                news.setContent(rs.getString("content"));
                news.setStaff_id(rs.getInt("staff_id"));
                news.setCreated_at(rs.getDate("created_at"));
                news.setUpdated_at(rs.getDate("updated_at"));
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

    //search news by title
    public List<News> getSearchNewsByTitle(String title, int staff_id) {
        List<News> list = new ArrayList<>();
        String sql = "select * from news where title like ? and staff_id=?";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, "%" + title + "%");
            st.setInt(2, staff_id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                News n = new News();
                n.setNews_id(rs.getInt("news_id"));
                n.setStaff_id(rs.getInt("staff_id"));
                n.setTitle(rs.getString("title"));
                n.setContent(rs.getString("content"));
                n.setCreated_at(rs.getDate("created_at"));
                n.setUpdated_at(rs.getDate("updated_at"));
                n.setStatus(rs.getString("status"));
                list.add(n);
            }
        } catch (SQLException e) {
            System.out.println(e);
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

    //Get all news sorted
    public List<News> getAllNewsSorted(int staff_id, String sortBy) {
        List<News> list = new ArrayList<>();
        String sql = "select* from news where staff_id=? order by " + sortBy + " asc";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, staff_id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                News n = new News();
                n.setNews_id(rs.getInt("news_id"));
                n.setTitle(rs.getString("title"));
                n.setContent(rs.getString("content"));
                n.setCreated_at(rs.getDate("created_at"));
                n.setUpdated_at(rs.getDate("updated_at"));
                n.setStatus(rs.getString("status"));
                list.add(n);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    //Get news sorted by status
    public List<News> getNewsByStatusSorted(int staff_id, String status, String sortBy) {
        List<News> list = new ArrayList<>();
        String sql = "select* from news where staff_id=? and status=? order by " + sortBy + " asc";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, staff_id);
            st.setString(2, status);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                News n = new News();
                n.setNews_id(rs.getInt("news_id"));
                n.setTitle(rs.getString("title"));
                n.setContent(rs.getString("content"));
                n.setCreated_at(rs.getDate("created_at"));
                n.setUpdated_at(rs.getDate("updated_at"));
                n.setStatus(rs.getString("status"));
                list.add(n);
            }
        } catch (Exception e) {
            System.out.println(e);
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

}
