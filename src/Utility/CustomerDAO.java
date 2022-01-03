/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import Model.Customer;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 *
 * @author Koala
 */
public interface CustomerDAO {

    /**
     *Get All Customers Observable List
     * @return All Customers
     * @throws SQLException
     */
    ObservableList<Customer> getAllCustomers() throws SQLException;

    /**
     * Add Customer Method
     * @param name
     * @param Address
     * @param postalCode
     * @param phone
     * @param divID
     * @throws SQLException
     */
    void addCustomer(String name, String Address, String postalCode, String phone, int divID) throws SQLException;

    /**
     * Update Customer Method
     * @param cusID
     * @param name
     * @param address
     * @param postalCode
     * @param phone
     * @param divID
     * @throws SQLException
     */
    void updateCustomer(int cusID, String name, String address, String postalCode, String phone, int divID) throws SQLException;

    /**
     *Delete Customer Method
     * @param cusID
     * @throws SQLException
     */
    void deleteCustomer(int cusID) throws SQLException;
}
