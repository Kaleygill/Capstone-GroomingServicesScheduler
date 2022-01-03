/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import Model.Appointment;
import Model.GroomingServices;
import java.sql.SQLException;
import java.sql.Timestamp;
import javafx.collections.ObservableList;

/**
 *
 * @author Koala
 */
public interface AppointmentDAO {

    /**
     *
     * @return Appointment of month
     */
    ObservableList<Appointment> getAppointmentOfMonth() throws SQLException;
    /**
     *Get All Appointments Observable List
     * @return
     * @throws SQLException
     */
    ObservableList<Appointment> getAllAppointments() throws SQLException;

    ObservableList<GroomingServices> getAllAppointmentServices(int aID) throws SQLException;
    /**
     * Add Appointment Method
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param cusID
     * @param uID
     * @param conID
     * @throws SQLException
     */
    public int addAppointment(String title, String description, String location, String type, Timestamp start, Timestamp end, int cusID, int uID, int conID) throws SQLException;

    /**
     *Delete Appointment Method
     * @param aID
     * @throws SQLException
     */
    void deleteAppointment(int aID) throws SQLException;

    /**
     *Update Appointment Method
     * @param aID
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param cusID
     * @param uID
     * @param conID
     * @throws SQLException
     */
    void updateAppointment(int aID, String title, String description, String location, String type, Timestamp start, Timestamp end, int cusID, int uID, int conID) throws SQLException;
    
    //ObservableList<GroomingServices> getAllAppointmentServices(int aID) throws SQLException;
    //ObservableList<GroomingServices> setService(int aID) throws SQLException;
    
    
    void addSelectedServicestoAppointment(int appointmentID, int groomingID) throws SQLException;
    
    void DeleteServiceUponUpdateIfRemoved(int appointmentID, int groomingID) throws SQLException;
    
 
    
    
    
   

}

