/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import Model.User;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 *
 * @author Koala
 */
public interface UserDAO {

    /**
     *Get All Users Method
     * @return
     * @throws SQLException
     */
    ObservableList<User> getAllUsers() throws SQLException;
}
