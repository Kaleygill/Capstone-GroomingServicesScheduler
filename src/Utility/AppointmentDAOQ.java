/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import Model.Appointment;
import Model.GroomingServices;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Koala
 */
public class AppointmentDAOQ implements AppointmentDAO {
    
    public ObservableList<Appointment> getAppointmentOfMonth() throws SQLException {
        ObservableList<Appointment> AppointmentByMonth = FXCollections.observableArrayList();
        String selectStatement = "select  Appointment_ID, month(start) as MONTH ,Type FROM appointments GROUP BY MONTH, Type, Appointment_ID;";
        
        PreparedStatement ps = DBConnection.startConnection().prepareStatement(selectStatement);

        ps.execute();

        ResultSet rs = ps.getResultSet();

        while (rs.next()){
            int aID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String contactName = rs.getString("Contact_Name");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            //LocalDate createDate = rs.getDate("Create_Date").toLocalDate();
            int cusID = rs.getInt("Customer_ID");
            int uID = rs.getInt("User_ID");
            int conID = rs.getInt("Contact_ID");


            
            Appointment appointmentResults = new Appointment(aID, title, description, location, contactName, type, start, end, cusID, uID, conID);
            AppointmentByMonth.add(appointmentResults);

        }
        return AppointmentByMonth;
    }
    
    /**
     * Get All Appointments method
     * @return All Appointments
     * @throws SQLException
     */
    @Override
    public ObservableList<Appointment> getAllAppointments() throws SQLException {

        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        String selectStatement = "SELECT * FROM appointments\n" +
                "JOIN contacts \n" +
                "ON appointments.Contact_ID = contacts.Contact_ID;";

        PreparedStatement ps = DBConnection.startConnection().prepareStatement(selectStatement);

        ps.execute();

        ResultSet rs = ps.getResultSet();

        while (rs.next()){
            int aID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String contactName = rs.getString("Contact_Name");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            //LocalDate createDate = rs.getDate("Create_Date").toLocalDate();
            int cusID = rs.getInt("Customer_ID");
            int uID = rs.getInt("User_ID");
            int conID = rs.getInt("Contact_ID");


            
            Appointment appointmentResults = new Appointment(aID, title, description, location, contactName, type, start, end, cusID, uID, conID);
            allAppointments.add(appointmentResults);

        }
        return allAppointments;
    }

    /**
     * Add Appointment Method. Inserts Appointment into Database. 
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
    public int addAppointment(String title, String description, String location, String type, Timestamp start, Timestamp end, int cusID, int uID, int conID) throws SQLException {

        String insertStatement = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement ps = DBConnection.startConnection().prepareStatement(insertStatement);

        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, start);
        ps.setTimestamp(6, end);
        ps.setInt(7, cusID);
        ps.setInt(8, uID);
        ps.setInt(9, conID);

        ps.execute();
        
        ResultSet rs = ps.executeQuery("SELECT LAST_INSERT_ID();");
        
        if (rs.next()) {
        int appointmentID = Integer.parseInt(rs.getString(1));
        
        System.out.println(appointmentID);
        return appointmentID;
        
        } 
        return 0;
    } 

    /**
     * Delete Appointment method. Deletes selected appointment from database. 
     * @param aID
     * @throws SQLException
     */
    @Override
    public void deleteAppointment(int aID) throws SQLException {
        String deleteStatement = "DELETE FROM appointments WHERE Appointment_ID = ?";

        PreparedStatement ps = DBConnection.startConnection().prepareStatement(deleteStatement);

        ps.setInt(1, aID);

        ps.execute();
    }

    /**
     * Update Appointment Method. Updates existing database field with updated changes by user. 
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
    @Override
    public void updateAppointment(int aID, String title, String description, String location, String type, Timestamp start, Timestamp end, int cusID, int uID, int conID) throws SQLException {

        String updateStatement = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";

        PreparedStatement ps = DBConnection.startConnection().prepareStatement(updateStatement);

        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, start);
        ps.setTimestamp(6, end);
        ps.setInt(7, cusID);
        ps.setInt(8, uID);
        ps.setInt(9, conID);
        ps.setInt(10, aID);

        ps.execute();
    }  

   
    @Override
    public ObservableList<GroomingServices> getAllAppointmentServices(int aID) throws SQLException {
        ObservableList<GroomingServices> allAppointmentServices = FXCollections.observableArrayList();
        String selectStatement = "SELECT * FROM grooming JOIN appointmentServices ON grooming.Grooming_ID = appointmentServices.GroomingID WHERE appointmentServices.AppointmentID = ?;";
        
        PreparedStatement ps = DBConnection.startConnection().prepareStatement(selectStatement);
        
        ps.setInt(1, aID);
        
        ps.execute();
        
        ResultSet rs = ps.getResultSet();
        
        while(rs.next()) {
            int groomingID  = rs.getInt("GroomingID");
            String serviceName = rs.getString("Name");
            String description = rs.getString("Description");
            Double price = rs.getDouble("Price");
            String type = rs.getString("Type");
            
            GroomingServices appointmentServices = new GroomingServices(groomingID, serviceName, description, price, type );
            allAppointmentServices.add(appointmentServices);
        }
        return allAppointmentServices;
    }
    
   @Override
    public void addSelectedServicestoAppointment(int appointmentID, int groomingID) throws SQLException {
        String insertStatement =  "INSERT INTO appointmentServices(AppointmentID, GroomingID)\n" +
                "VALUES (?, ?);";
        
        PreparedStatement ps = DBConnection.startConnection().prepareStatement(insertStatement); 
        
        ps.setInt(1, appointmentID);
        ps.setInt(2, groomingID);
        
        ps.execute();
    }

    @Override
    public void DeleteServiceUponUpdateIfRemoved(int appointmentID, int groomingID) throws SQLException {
        String deleteStatement = "DELETE FROM appointmentServices WHERE GroomingID = ? AND AppointmentID = ?;";

        PreparedStatement ps = DBConnection.startConnection().prepareStatement(deleteStatement);

        ps.setInt(1, groomingID);
        ps.setInt(2, appointmentID);

        ps.execute();
    }

}
