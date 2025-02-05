/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author default
 */
public class Services {
    public int service_id;
    public String service_name,description,service_type,status;

    public Services() {
    }

    public Services(String service_name, String description, String service_type, String status) {
        this.service_name = service_name;
        this.description = description;
        this.service_type = service_type;
        this.status = status;
    }

    public Services(int service_id, String service_name, String description, String service_type, String status) {
        this.service_id = service_id;
        this.service_name = service_name;
        this.description = description;
        this.service_type = service_type;
        this.status = status;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Services{" + "service_id=" + service_id + ", service_name=" + service_name + ", description=" + description + ", service_type=" + service_type + ", status=" + status + '}';
    }
}
