/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Utility.AppointmentDAO;
import Utility.AppointmentDAOQ;
import Utility.CustomerDAO;
import Utility.CustomerDAOQ;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Koala
 */
public class Customer {
    private int cusID;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private String division;
    //private String country;
    private int divID;
    
    /**
     *
     * @param cusID
     * @param name
     * @param address
     * @param postalCode
     * @param phone
     * @param division
     * @param divID
     */
    public Customer(int cusID, String name, String address, String postalCode, String phone, String division, int divID) {
        this.cusID = cusID;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.division = division;
        //this.country = country;
        this.divID = divID;
    }

    /**
     *
     * @return Customer ID
     */
    public int getCusID() {
        return cusID;
    }

    /**
     *
     * @param cusID
     */
    public void setCusID(int cusID) {
        this.cusID = cusID;
    }

    /**
     *
     * @return Customer Name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return Customer Address
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return Customer Postal Code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     *
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     *
     * @return Customer Phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *
     * @return Customer Division
     */
    public String getDivision() {
        return division;
    }

    /**
     *
     * @param division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     *
     * @return Division ID
     */


    public int getDivID() {
        return divID;
    }

    /**
     *
     * @param divID
     */
    public void setDivID(int divID) {
        this.divID = divID;
    }
    
    /**
     *
     * @param cusID
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAllCustomerAppointments(int cusID) throws SQLException {
        ObservableList<Appointment> allCustomerApts = FXCollections.observableArrayList();
        AppointmentDAO appointmentDao = new AppointmentDAOQ();

        for (Appointment a : appointmentDao.getAllAppointments()){
            if (a.getCusID() == cusID){
                allCustomerApts.add(a);
            }
        }
        return allCustomerApts;
    }
    
    /**
     *
     * @param divID
     * @return
     * @throws SQLException
     */
    public static ObservableList<Customer> getAllCustomersByDivision(int divID) throws SQLException {
        ObservableList<Customer> allCustomersDivision = FXCollections.observableArrayList();
        CustomerDAO customerDao = new CustomerDAOQ();

        for (Customer c : customerDao.getAllCustomers()){
            if (c.getDivID() == divID){
                allCustomersDivision.add(c);
            }
        }
        return allCustomersDivision;
    }
    
    /**
     *
     * @return
     */
    @Override
    public String toString(){
        return (getName() + " [" + getCusID() + "]");
    }
}
