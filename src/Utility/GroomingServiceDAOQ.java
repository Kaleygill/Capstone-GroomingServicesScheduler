/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import Model.GroomingServices;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Koala
 */
public class GroomingServiceDAOQ implements GroomingServiceDAO {

    @Override
    public ObservableList<GroomingServices> getAllGroomingServices() throws SQLException {
        ObservableList<GroomingServices> allGroomingServices = FXCollections.observableArrayList(); 
        String selectedStatement = "SELECT * FROM grooming;";
        
        PreparedStatement ps = DBConnection.startConnection().prepareStatement(selectedStatement);
        
        ps.execute();
        
        ResultSet rs = ps.getResultSet();
        
        while (rs.next()) {
            int groomingID = rs.getInt("Grooming_ID");
            String serviceName = rs.getString("Name");
            String description = rs.getString("Description");
            Double price = rs.getDouble("Price");
            String type = rs.getString("Type");
            
            GroomingServices groomingResults = new GroomingServices(groomingID, serviceName, description, price, type);
            allGroomingServices.add(groomingResults);
        }
        return allGroomingServices;
    
    }

    @Override
    public ObservableList<GroomingServices> getAllCatGroomingServices() throws SQLException {
        ObservableList<GroomingServices> allCatGroomingServices = FXCollections.observableArrayList();
        String selectStatement = "SELECT Grooming_ID, Name, Description, Price,  Type FROM grooming WHERE Type = \"Cat Grooming\" GROUP BY Type, Grooming_ID;";
        
        PreparedStatement ps = DBConnection.startConnection().prepareStatement(selectStatement);
        
        ps.execute();
        
        ResultSet rs = ps.getResultSet();
        
        while (rs.next()) {
            int groomingID = rs.getInt("Grooming_ID");
            String serviceName = rs.getString("Name");
            String description = rs.getString("Description");
            Double price = rs.getDouble("Price");
            String type = rs.getString("Type");
            
            GroomingServices groomingResults = new GroomingServices(groomingID, serviceName, description, price, type);
            allCatGroomingServices.add(groomingResults);
        }
        return allCatGroomingServices;
    }

    @Override
    public ObservableList<GroomingServices> getAllHorseGroomingServices() throws SQLException {
        ObservableList<GroomingServices> allHorseGroomingServices = FXCollections.observableArrayList();
        String selectStatement = "SELECT Grooming_ID,Name, Description, Price,  Type FROM grooming WHERE Type = \"Horse Grooming - Mobile\" GROUP BY Type, Grooming_ID;";
        
        PreparedStatement ps = DBConnection.startConnection().prepareStatement(selectStatement);
        
        ps.execute();
        
        ResultSet rs = ps.getResultSet();
        
        while (rs.next()) {
            int groomingID = rs.getInt("Grooming_ID");
            String serviceName = rs.getString("Name");
            String description = rs.getString("Description");
            Double price = rs.getDouble("Price");
            String type = rs.getString("Type");
            
            GroomingServices groomingResults = new GroomingServices(groomingID, serviceName, description, price, type);
            allHorseGroomingServices.add(groomingResults);
        }
        return allHorseGroomingServices;
    }

    @Override
    public ObservableList<GroomingServices> getAllDogGroomingServices() throws SQLException {
        ObservableList<GroomingServices> allDogGroomingServices = FXCollections.observableArrayList();
        String selectStatement = "SELECT Grooming_ID,Name, Description, Price,  Type FROM grooming WHERE Type = \"Dog Grooming\" GROUP BY Type, Grooming_ID;";
        
        PreparedStatement ps = DBConnection.startConnection().prepareStatement(selectStatement);
        
        ps.execute();
        
        ResultSet rs = ps.getResultSet();
        
        while (rs.next()) {
            int groomingID = rs.getInt("Grooming_ID");
            String serviceName = rs.getString("Name");
            String description = rs.getString("Description");
            Double price = rs.getDouble("Price");
            String type = rs.getString("Type");
            
            GroomingServices groomingResults = new GroomingServices(groomingID, serviceName, description, price, type);
            allDogGroomingServices.add(groomingResults);
        }
        return allDogGroomingServices;
    }

    @Override
    public void addGroomingService(String serviceName, String description, Double price, String type) throws SQLException {
        String insertStatement = "INSERT INTO grooming(Name, Description, Type, Price)\n" +
                "VALUES (?, ?, ?, ?);";

        PreparedStatement ps = DBConnection.startConnection().prepareStatement(insertStatement);

        ps.setString(1, serviceName);
        ps.setString(2, description);
        ps.setString(3, type);
        ps.setDouble(4, price);

        ps.execute();
    }

    @Override
    public void deleteGroomingService(int groomingID) throws SQLException {
        String deleteStatement = "DELETE FROM grooming WHERE Grooming_ID = ?";

        PreparedStatement ps = DBConnection.startConnection().prepareStatement(deleteStatement);

        ps.setInt(1, groomingID);

        ps.execute();
    }
    @Override
    public void updateGroomingService(int groomingID, String serviceName, String description, Double price, String type) throws SQLException {
        String insertStatement = "UPDATE grooming SET Name = ?, Description = ?, Type = ?,  Price = ? WHERE Grooming_ID = ?";

        PreparedStatement ps = DBConnection.startConnection().prepareStatement(insertStatement);

        ps.setString(1, serviceName);
        ps.setString(2, description);
        ps.setString(3, type);
        ps.setDouble(4, price);
        ps.setInt(5, groomingID);

        ps.execute();
    }
    
    
}
