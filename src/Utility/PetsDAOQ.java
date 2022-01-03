/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import Model.Horse;
import Model.Pets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Koala
 */
public class PetsDAOQ implements PetsDAO{

    @Override
    public ObservableList<Pets> getAllPets() throws SQLException {
        ObservableList<Pets> allPets = FXCollections.observableArrayList(); 
    
        String selectStatement = "SELECT * FROM Pets";
    
        PreparedStatement ps = DBConnection.startConnection().prepareStatement(selectStatement); 
    
        ps.execute(); 
    
        ResultSet rs = ps.getResultSet(); 
    
        while (rs.next()) {
            int petID = rs.getInt("Pet_ID"); 
            String petName = rs.getString("Pet_Name"); 
            String breed = rs.getString("Pet_Breed");
            String color = rs.getString("Pet_Color"); 
            String weight = rs.getString("Pet_Weight"); 
            int cusID = rs.getInt("Customer_ID"); 
            String notes = rs.getString("Pet_Notes");      
            Pets petsResults = new Pets(petID, petName, breed, notes, cusID, color, weight); 
            allPets.add(petsResults); 
        }
    return allPets;
    }

    @Override
    public void deletePet(int petID) throws SQLException {
        String deleteStatement = "DELETE FROM Pets WHERE Pet_ID = ?";
        
        PreparedStatement ps = DBConnection.startConnection().prepareStatement(deleteStatement);
        
        ps.setInt(1, petID);
        
        ps.execute();
    }

    @Override
    public void updatePet(int petID, String petName, String breed, String color, String weight, int cusID, String notes) throws SQLException {
        String updateStatement = "UPDATE Pets SET Pet_Name = ?, Pet_Color = ?, Pet_Breed = ?, pet_Weight = ?,Pet_Notes = ?, Customer_ID = ? WHERE Pet_ID = ?";
        
        PreparedStatement ps = DBConnection.startConnection().prepareStatement(updateStatement);
        
        ps.setString(1, petName);
        ps.setString(2, color);
        ps.setString(3, breed);
        ps.setString(4, weight);
        ps.setString(5, notes);
        ps.setInt(6, cusID);
        ps.setInt(7, petID);

        
        ps.execute();
        System.out.println("Pet updated" + ps);
    }

    @Override
    public void addPet(String petName, String breed, String notes, int cusID, String color, String weight) throws SQLException {
        String insertStatement = "INSERT INTO Pets(Pet_Name, Pet_Breed, Pet_Weight, Customer_ID, Pet_Color, Pet_Notes)\n" + 
            "VALUES (?, ?, ?, ?, ?, ?);";
        
        PreparedStatement ps = DBConnection.startConnection().prepareStatement(insertStatement);
        
        ps.setString(1, petName);
        ps.setString(2, breed);
        ps.setString(3, weight);
        ps.setInt(4, cusID);
        ps.setString(5, notes); 
        ps.setString(6, color);
        
        ps.execute();
    }
    
    @Override
    public ObservableList<Pets> getSelectedCustomersPets(int cusID) throws SQLException {
        ObservableList<Pets> allCusPets = FXCollections.observableArrayList(); 
        String selectStatement = "select Pet_ID, Pet_Name, Pet_Breed, Pet_Notes, Customer_ID, Pet_Color, Pet_Weight FROM Pets where Customer_ID = ? AND Has_Hooves IS NULL group by Pet_ID;";
        
        
        PreparedStatement ps = DBConnection.startConnection().prepareStatement(selectStatement); 
        ps.setInt(1, cusID);
        System.out.println(ps.toString());
    
        ps.execute(); 
    
        ResultSet rs = ps.getResultSet(); 
        allCusPets.clear();
        while (rs.next()) {

            int petID = rs.getInt("Pet_ID"); 
            String petName = rs.getString("Pet_Name"); 
            String breed = rs.getString("Pet_Breed");
            String color = rs.getString("Pet_Color"); 
            String weight = rs.getString("Pet_Weight"); 
            cusID = rs.getInt("Customer_ID"); 
            String notes = rs.getString("Pet_Notes");      
            Pets petsResults = new Pets(petID, petName, breed, notes, cusID, color, weight); 
            allCusPets.add(petsResults); 
        }
    return allCusPets;
    }

    @Override
    public ObservableList<Horse> getSelectedCustomersHorses(int cusID) throws SQLException {
        ObservableList<Horse> allHorses = FXCollections.observableArrayList(); 
    
        String selectStatement = "SELECT Pet_ID, Pet_Name, Pet_Breed, Pet_Notes, Customer_ID, Pet_Color, Pet_Weight, Has_Hooves FROM Pets WHERE Customer_ID = ? AND Has_Hooves IS NOT NULL GROUP BY Pet_ID;";
        System.out.println(selectStatement);
        PreparedStatement ps = DBConnection.startConnection().prepareStatement(selectStatement); 
        ps.setInt(1, cusID);
        ps.executeQuery(); 
    
        ResultSet rs = ps.getResultSet(); 
    
        while (rs.next()) {
            int petID = rs.getInt("Pet_ID"); 
            String petName = rs.getString("Pet_Name"); 
            String breed = rs.getString("Pet_Breed");
            String color = rs.getString("Pet_Color"); 
            String weight = rs.getString("Pet_Weight"); 
            cusID = rs.getInt("Customer_ID"); 
            String notes = rs.getString("Pet_Notes");  
            String haveHorseShoe = rs.getString("Has_Hooves");
            Horse horseResults = new Horse(petID, petName, breed, notes, cusID, color, weight, haveHorseShoe); 
            allHorses.add(horseResults); 
        }
    return allHorses;
        
    }

    @Override
    public void addHorse(String petName, String breed, String notes, int cusID, String color, String weight, String haveHorseShoe) throws SQLException {
        String insertStatement = "INSERT INTO Pets(Pet_Name, Pet_Breed, Pet_Weight, Customer_ID, Pet_Color, Pet_Notes, Has_Hooves)\n" + 
            "VALUES (?, ?, ?, ?, ?, ?, ?);";
        
        PreparedStatement ps = DBConnection.startConnection().prepareStatement(insertStatement);
        
        ps.setString(1, petName);
        ps.setString(2, breed);
        ps.setString(3, weight);
        ps.setInt(4, cusID);
        ps.setString(5, notes); 
        ps.setString(6, color);
        ps.setString(7, haveHorseShoe);
        
        ps.execute();
    }

    @Override
    public void updateHorse(int petID, String petName, String breed, String color, String weight, int cusID, String notes, String haveHorseShoe) throws SQLException {
        String updateStatement = "UPDATE Pets SET Pet_Name = ?, Pet_Color = ?, Pet_Breed = ?, pet_Weight = ?,Pet_Notes = ?, Customer_ID = ?, Has_Hooves = ? WHERE Pet_ID = ?";
        
        PreparedStatement ps = DBConnection.startConnection().prepareStatement(updateStatement);
        
        ps.setString(1, petName);
        ps.setString(2, breed);
        ps.setString(3, weight);
        ps.setInt(6, cusID);
        ps.setString(5, notes); 
        ps.setString(4, color);
        ps.setString(7, haveHorseShoe);
        ps.setInt(8, petID);
        
        ps.execute();
    }

}
