/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import Model.Contact;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 *
 * @author Koala
 */
public interface ContactDAO {

    /**
     * Get All Contacts Observable list. 
     * @return All contacts
     * @throws SQLException
     */
    ObservableList<Contact> getAllContacts() throws SQLException;
}
