/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import Model.Division;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Koala
 */
public class DivisionDAOQ implements DivisionDAO{

    /**
     *Get All Divisions Method. Gets all Division from the Database. 
     * @return All Divisions
     * @throws SQLException
     */
    @Override
    public ObservableList<Division> getAllDivisions() throws SQLException {
        ObservableList<Division> allDivisions = FXCollections.observableArrayList();

        String selectStatement = "SELECT * FROM first_level_divisions";

        PreparedStatement ps = DBConnection.startConnection().prepareStatement(selectStatement);
        ps.execute();
        ResultSet rs = ps.getResultSet();

        while (rs.next()) {
            int divID = rs.getInt( "Division_ID" );
            String division = rs.getString( "Division" );
            int countryID = rs.getInt( "COUNTY_ID" );

            Division divisionResult = new Division( divID, division, countryID );
            allDivisions.add( divisionResult );
        }

        return allDivisions;
    }
}
