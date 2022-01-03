/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import Model.County;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Koala
 */
public class CountyDAOQ implements CountyDAO {

    /**
     * Get All Countries Observable List. Retrieves countries from Database. 
     * @return All Countries
     * @throws SQLException
     */
    @Override
    public ObservableList<County> getAllCounties() throws SQLException {

        ObservableList<County> allCounties = FXCollections.observableArrayList();
       
        String selectStatement = "SELECT * FROM county";
        PreparedStatement ps = DBConnection.startConnection().prepareStatement(selectStatement);
        ps.execute();

        ResultSet rs = ps.getResultSet();


        while (rs.next()){
            int id = rs.getInt("County_ID");
            String name = rs.getString( "County" );
            
            County countyResult = new County( id, name);
            allCounties.add(countyResult);
        }

        return allCounties;
    }
}
