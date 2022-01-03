/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import Model.Division;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 *
 * @author Koala
 */
public interface DivisionDAO {

    /**
     *Get All Divisions Method
     * @return
     * @throws SQLException
     */
    ObservableList<Division> getAllDivisions() throws SQLException;
}


