/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author default
 */
public class Provider_services {
    String provider_service_id,provider_id,description;
    double price;

    public Provider_services() {
    }

    public Provider_services(String provider_service_id, String provider_id, double price, String description) {
        this.provider_service_id = provider_service_id;
        this.provider_id = provider_id;
        this.description = description;
        this.price = price;
    }

    public String getProvider_service_id() {
        return provider_service_id;
    }

    public void setProvider_service_id(String provider_service_id) {
        this.provider_service_id = provider_service_id;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Provider_services{" + "provider_service_id=" + provider_service_id + ", provider_id=" + provider_id + ", description=" + description + ", price=" + price + '}';
    }
}
