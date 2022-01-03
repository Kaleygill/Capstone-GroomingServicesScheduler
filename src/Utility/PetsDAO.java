/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import Model.Horse;
import Model.Pets;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 *
 * @author Koala
 */
public interface PetsDAO {
    ObservableList<Pets> getAllPets() throws SQLException;
    
    ObservableList<Pets>getSelectedCustomersPets(int cusID) throws SQLException;
    
    ObservableList<Horse>getSelectedCustomersHorses(int cusID) throws SQLException;
    
    public void addPet(String petName, String breed, String notes, int cusID, String color, String weight) throws SQLException;
    
    public void addHorse(String petName, String breed, String notes, int cusID, String color, String weight, String haveHorseShoe) throws SQLException;
    
    public void deletePet(int petID) throws SQLException;
    
    public void updatePet(int petID, String petName, String breed, String color, String weight, int cusID, String notes) throws SQLException;
    
    public void updateHorse(int petID, String petName, String breed, String color, String weight, int cusID, String notes, String haveHorseShoe) throws SQLException;

}
