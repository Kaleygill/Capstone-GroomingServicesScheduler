/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Utility.AppointmentDAO;
import Utility.AppointmentDAOQ;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Koala
 */
public class Appointment {
    private int id;

    public Appointment() {
    }

    /**
     *
     * @return Appointment ID
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }
    private int cusID;
    private int uID;
    private int conID;
    private String title;
    private String description;
    private String location;
    private String type;
    private String contactName;
    private LocalDateTime start;
    private LocalDateTime end;
    

    /**
     *
     * @param id
     * @param title
     * @param description
     * @param location
     * @param contactName
     * @param type
     * @param start
     * @param end
     * @param cusID
     * @param uID
     * @param conID
     */
    public Appointment(int id, String title, String description, String location, String contactName, String type, LocalDateTime start, LocalDateTime end, int cusID, int uID, int conID) 
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        //this.username = username;
        this.contactName = contactName;
        this.cusID = cusID;
        this.uID = uID;
        this.conID = conID;  
    }
    
    

    /**
     *
     * @return Customer ID
     */
    public int getCusID() {
        return cusID;
    }

    /**
     *
     * @param cusID
     */
    public void setCusID(int cusID) {
        this.cusID = cusID;
    }

    /**
     *
     * @return User ID
     */
    public int getuID() {
        return uID;
    }

    /**
     *
     * @param uID
     */
    public void setuID(int uID) {
        this.uID = uID;
    }

    /**
     *
     * @return Contact ID
     */
    public int getConID() {
        return conID;
    }

    /**
     *
     * @param conID
     */
    public void setConID(int conID) {
        this.conID = conID;
    }

    /**
     *
     * @return Title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return Description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return Location
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @return Type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return Contact Name
     */


    public String getContactName() {
        return contactName;
    }

    /**
     *
     * @param contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     *
     * @return Start DateTime
     */
    public LocalDateTime getStart() {
        ZonedDateTime zonedStart = ZonedDateTime.of(start, ZoneId.of(TimeZone.getDefault().getID()));

        return zonedStart.toLocalDateTime();
    }

    /**
     *
     * @param start
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     *
     * @return End DateTime
     */
    public LocalDateTime getEnd() {
     ZonedDateTime zonedEnd = ZonedDateTime.of(end, ZoneId.of(TimeZone.getDefault().getID()));

    return zonedEnd.toLocalDateTime();
    }

    /**
     *
     * @param end
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     *
     * @return Formatted Start DateTime
     */
    public String getFormattedStart() {

        ZonedDateTime zonedStart = ZonedDateTime.of(start, ZoneId.of(TimeZone.getDefault().getID()));

        LocalDateTime localStart = zonedStart.toLocalDateTime();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm - MM/dd/yyyy");

        return dtf.format(localStart);
    }
     
    /**
     *
     * @return Formatted End DateTime
     */
    public String getFormattedEnd() {

        ZonedDateTime zonedEnd = ZonedDateTime.of(end, ZoneId.of(TimeZone.getDefault().getID()));

        LocalDateTime localEnd = zonedEnd.toLocalDateTime();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm - MM/dd/yyyy");

        return dtf.format(localEnd);
    }
      
    /**
     * Scheduling Conflict Method. Checking if Appointment already scheduled. 
     * @param cusID
     * @param start
     * @param end
     * @return
     * @throws SQLException
     */
    public static Appointment isScheduleConflict(int cusID, LocalDateTime start, LocalDateTime end) throws SQLException {
        Appointment conflictingAppointment = null; 

        ObservableList<Appointment> allCustomerAppointments = Customer.getAllCustomerAppointments(cusID);

        for (Appointment a : allCustomerAppointments) {
            if ((start.isAfter(a.getStart()) && start.isBefore(a.getEnd())) || //(PS > AS && PS < AE)
                    (end.isAfter(a.getStart()) && end.isBefore(a.getEnd())) || //(PE > AS && PE < AE)
                    (start.isBefore(a.getStart()) && end.isAfter(a.getEnd())) || //(PS < AS && PE > AE)
                    (start.isEqual(a.getStart()) && end.isEqual(a.getEnd())) || //(PS == AS && PE == AE)
                    (start.isEqual(a.getStart()) || end.isEqual(a.getEnd()))) { //(PS == AS || PE == AE)
                conflictingAppointment = a;
                
            }
        }
        return conflictingAppointment;
    }
      
    /**
     *Get All Contact Appointments List. 
     * @param conID
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAllContactAppointments(int conID) throws SQLException {
        AppointmentDAO appointmentDao = new AppointmentDAOQ();
        ObservableList<Appointment> allAppointments = appointmentDao.getAllAppointments();
        ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();

        for (Appointment a : allAppointments) {
            if (a.getConID() == conID) {
                contactAppointments.add(a);
            }
        }
        return contactAppointments;
    }
      
    /**
     * Types Observable list. Used to Populate Type ComboBox. 
     * @return
     */
    public static ObservableList<String> getTypes(){
        ObservableList<String> types = FXCollections.observableArrayList();

        types.add("Horse Grooming - Mobile");
        types.add("Dog/ Cat Grooming - Mobile");
        types.add("Cat Grooming");
        types.add("Dog Grooming");
        
        return types;
    }
      
    /**
     *Appointment By Type. Used for Reports screen to display all appointments by type. 
     * Lambda Expression used here to reduce and simplify code. 
     * @param type
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> appointmentByType(String type) throws SQLException {
        AppointmentDAO appointmentDao = new AppointmentDAOQ();
        

        ObservableList<Appointment> appointmentsOfType = appointmentDao.getAllAppointments().filtered(appointment -> {
            if (appointment.getType().equals(type))
                return true;
            return false;
        });
        return appointmentsOfType;
    }
    
    /**
     *Appointment By Month. Used by the reports screen to populate Appointments by month.
     * @param month
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> appointmentByMonth(int month) throws SQLException {
        AppointmentDAO appointmentDao = new AppointmentDAOQ();
        ObservableList<Appointment> AppointmentByMonth = appointmentDao.getAllAppointments();
        ObservableList<Appointment> monthlyAppointments = FXCollections.observableArrayList();

        for (Appointment a : AppointmentByMonth){
            LocalDateTime start = a.getStart();
            LocalDate date = start.toLocalDate();

            if (date.getMonth().getValue() == month){
                monthlyAppointments.add(a);
            }
        }
        return monthlyAppointments;
    }


}

