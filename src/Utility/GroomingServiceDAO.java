/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import Model.GroomingServices;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 *
 * @author Koala
 */
public interface GroomingServiceDAO {
    
    ObservableList<GroomingServices> getAllGroomingServices() throws SQLException;
    
    ObservableList<GroomingServices> getAllCatGroomingServices() throws SQLException;
    
    ObservableList<GroomingServices> getAllHorseGroomingServices() throws SQLException;
    
    ObservableList<GroomingServices>getAllDogGroomingServices() throws SQLException;
    
    
    
    void addGroomingService(String serviceName, String description, Double price, String Type) throws SQLException;
    
    void deleteGroomingService(int groomingID) throws SQLException;
    
    void updateGroomingService(int groomingID, String serviceName, String description, Double price, String Type) throws SQLException;
    
    
    
    
}
