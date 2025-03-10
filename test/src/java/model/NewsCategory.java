
package model;

public class NewsCategory {
    public int category_id;
    public String category_name;

    public NewsCategory() {
    }

    public NewsCategory(int category_id, String category_name) {
        this.category_id = category_id;
        this.category_name = category_name;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    @Override
    public String toString() {
        return "NewsCategory{" + "category_id=" + category_id + ", category_name=" + category_name + '}';
    }
    
}
