/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import Model.Customer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Koala
 */
public class CustomerDAOQ implements CustomerDAO {

    /**
     *Get All Customers Method. Retrieves customers from database.
     * @return
     * @throws SQLException
     */
    @Override
    public ObservableList<Customer> getAllCustomers() throws SQLException {

        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

            
            String selectStatement = "SELECT *\n" +
                    "FROM customers\n" +
                    "JOIN first_level_divisions\n" +
                    "\tON customers.Division_ID = first_level_divisions.Division_ID";
            
            PreparedStatement ps = DBConnection.startConnection().prepareStatement(selectStatement);
            ps.execute();
            ResultSet rs = ps.getResultSet();

            
            while (rs.next()){
                int cusID = rs.getInt("Customer_ID");
                String name = rs.getString( "Customer_Name" );
                String address = rs.getString( "Address" );
                String postalCode = rs.getString( "Postal_Code" );
                String phone = rs.getString( "Phone" );
                int divID = rs.getInt( "Division_ID" );
                String division = rs.getString("Division");

                
                Customer customerResult = new Customer( cusID, name, address, postalCode, phone,  division, divID);
                allCustomers.add( customerResult );
            }

        return allCustomers;
    }

    /**
     *Add Customer Method. Inserts customer into Database.
     * @param name
     * @param address
     * @param postalCode
     * @param phone
     * @param divID
     * @throws SQLException
     */
    @Override
    public void addCustomer(String name, String address, String postalCode, String phone, int divID) throws SQLException {

        String insertString = ("INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Created_By, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, ?, ?, ?)");

        PreparedStatement ps = DBConnection.startConnection().prepareStatement(insertString);

        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setString(5, "admin");
        ps.setString(6, "admin");
        ps.setInt(7, divID);

        ps.execute();
    }

    /**
     *Update Customer Method. Updates Customers fields when updated by user. 
     * @param cusID
     * @param name
     * @param address
     * @param postalCode
     * @param phone
     * @param divID
     * @throws SQLException
     */
    @Override
    public void updateCustomer(int cusID, String name, String address, String postalCode, String phone, int divID) throws SQLException {

        String updateStatement = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";

        PreparedStatement ps = DBConnection.startConnection().prepareStatement(updateStatement);

        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divID);
        ps.setInt(6, cusID);

        ps.execute();
    }

    /**
     *Delete Customer Method. Deletes selected customer from database. 
     * @param cusID
     * @throws SQLException
     */
    @Override
    public void deleteCustomer(int cusID) throws SQLException {
        String deleteStatement = "DELETE FROM customers WHERE Customer_ID = ?";

        PreparedStatement ps = DBConnection.startConnection().prepareStatement(deleteStatement);

        ps.setInt(1, cusID);

        ps.execute();
    }
}
