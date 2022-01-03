/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import Model.County;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 *
 * @author Koala
 */
public interface CountyDAO {

    /**
     *All Countries Observable List
     * @return All Countries 
     * @throws SQLException
     */
    ObservableList<County> getAllCounties() throws SQLException;
}


