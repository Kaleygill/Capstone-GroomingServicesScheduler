/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import Model.Contact;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Koala
 */
public class ContactDAOQ implements ContactDAO {

    /**
     * Get all Contacts Method. Retrieves from Database. 
     * @return All Contacts
     * @throws SQLException
     */
    @Override
    public ObservableList<Contact> getAllContacts() throws SQLException {

        ObservableList<Contact> allContacts = FXCollections.observableArrayList();

        String selectStatement = "SELECT * FROM contacts";

        PreparedStatement ps = DBConnection.startConnection().prepareStatement(selectStatement);

        ps.execute();

        ResultSet rs = ps.getResultSet();

        while (rs.next()) {
            int id = rs.getInt("Contact_ID");
            String name = rs.getString("Contact_Name");
            String email = rs.getString("Email");

            
            Contact contactResult = new Contact(id, name, email);
            allContacts.add(contactResult);
        }


        return allContacts;
    }
}
