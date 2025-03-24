/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.math.BigDecimal;

public class Asset {
    private int asset_id;
    private String description;
    private BigDecimal value;
    private int customer_id;
    private String status;

    // Constructor
    public Asset(int asset_id, String description, BigDecimal value, int customer_id, String status) {
        this.asset_id = asset_id;
        this.description = description;
        this.value = value;
        this.customer_id = customer_id;
        this.status = status;
    }

    public Asset() {
    }

    public int getAsset_id() {
        return asset_id;
    }

    public void setAsset_id(int asset_id) {
        this.asset_id = asset_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Asset{" + "asset_id=" + asset_id + ", description=" + description + ", value=" + value + ", customer_id=" + customer_id + ", status=" + status + '}';
    }
    
}
