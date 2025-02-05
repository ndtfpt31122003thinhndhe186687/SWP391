/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author Acer Nitro Tiger
 */
public class NewsView {
    public int id,news_id;
    public Date view_date;
    public String title;
    public int newsAmount;
    public NewsView() {
    }

    public NewsView(int news_id, String title) {
        this.news_id = news_id;
        this.title = title;
    }

    public NewsView(int id, int news_id, Date view_date, String title, int newsAmount) {
        this.id = id;
        this.news_id = news_id;
        this.view_date = view_date;
        this.title = title;
        this.newsAmount = newsAmount;
    }

   

    public int getNewsAmount() {
        return newsAmount;
    }

    public void setNewsAmount(int newsAmount) {
        this.newsAmount = newsAmount;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNews_id() {
        return news_id;
    }

    public void setNews_id(int news_id) {
        this.news_id = news_id;
    }

    public Date getView_date() {
        return view_date;
    }

    public void setView_date(Date view_date) {
        this.view_date = view_date;
    }

    @Override
    public String toString() {
        return "NewsView{" + "id=" + id + ", news_id=" + news_id + ", view_date=" + view_date + ", title=" + title + ", newsAmount=" + newsAmount + '}';
    }

   
}
