/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import Model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Koala
 */
public class UserDAOQ implements UserDAO{

    /**
     *Get All Users Method. Retrieves all Users from Database. 
     * @return All Users
     * @throws SQLException
     */
    @Override
    public ObservableList<User> getAllUsers() throws SQLException {

        ObservableList<User> allUsers = FXCollections.observableArrayList();

        String selectStatement = "SELECT * FROM users";
        PreparedStatement ps = DBConnection.startConnection().prepareStatement(selectStatement);
        ps.execute();

        ResultSet rs = ps.getResultSet();

        while (rs.next()){
            int id =rs.getInt("User_ID");
            String username = rs.getString("User_Name");
            String password = rs.getString("Password");

            User userResult = new User(id, username, password);
            allUsers.add(userResult);
        }


        return allUsers;
    }
}
