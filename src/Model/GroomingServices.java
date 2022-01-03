/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Koala
 */
public class GroomingServices {
    int GroomingID;
    String serviceName; 
    String description;
    double price; 
    String type;

    public GroomingServices(int GroomingID, String serviceName, String description, double price, String type) {
        this.GroomingID = GroomingID;
        this.serviceName = serviceName;
        this.description = description;
        this.price = price;
        this.type = type;
    }

    public int getGroomingID() {
        return GroomingID;
    }

    public void setGroomingID(int GroomingID) {
        this.GroomingID = GroomingID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
        public static ObservableList<String> getTypes(){
        ObservableList<String> types = FXCollections.observableArrayList();

        types.add("Horse Grooming - Mobile");
        types.add("Dog/ Cat Grooming - Mobile");
        types.add("Cat Grooming");
        types.add("Dog Grooming");
        
        return types;
    }
    
    
}
