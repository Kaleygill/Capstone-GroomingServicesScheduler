/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Appointment;
import Model.Contact;
import Model.Count;
import Model.Customer;
import Model.Division;
import Utility.AppointmentDAO;
import Utility.AppointmentDAOQ;
import Utility.ContactDAO;
import Utility.ContactDAOQ;
import Utility.DBConnection;
import static Utility.DBConnection.stmt;
import Utility.DivisionDAO;
import Utility.DivisionDAOQ;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Koala
 */
public class ReportsMainController implements Initializable {
    
    @FXML
    private ComboBox<String> monthCB;

    @FXML
    private ComboBox<String> typeCB;

    @FXML
    private TextArea appointmentByMonth;

    @FXML
    private TextArea appointmentByType;

    @FXML
    private TextArea customersByDivision;

    @FXML
    private TextArea contactSchedules;

    AppointmentDAO appointmentDao = new AppointmentDAOQ();
    
    //Remove below 
      @FXML
    private TableView<Count> typeTable;

    @FXML
    private TableColumn<Count, String> typeCol;

    @FXML
    private TableColumn<Count, Integer> appCol;

    @FXML
    private TableView<Count> monthTable;

    @FXML
    private TableColumn<Count,String> monthCol;

    @FXML
    private TableColumn<Count, Integer> monthAppCol;

    Stage stage;
    Parent scene;

    @FXML
    void appointmentsBtn (ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Appointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.setResizable(false);
        stage.show();
    }
    
    /**
     *
     * @return
     * @throws SQLException
     */
    public String contactScheduleReport() throws SQLException {
        ContactDAO contactDao = new ContactDAOQ();
        String contactSchedule = "Appointment Schedule By Contact: \n";
        ObservableList<Contact> allContacts = contactDao.getAllContacts();
        ObservableList<Appointment> contactsApts = FXCollections.observableArrayList();

        for (Contact c : allContacts){
            contactsApts = Appointment.getAllContactAppointments(c.getId());
            contactSchedule = contactSchedule + "\n" + c.getName() + ": \n";

            for (Appointment a : contactsApts){
                contactSchedule = contactSchedule + "Appointment ID: " + a.getId() + "\n" +
                        " Title: " + a.getTitle() + "\n" +
                        " Type: " + a.getType() + "\n" +
                        " Description: " + a.getDescription() + "\n" +
                        " Start: " + a.getStart() + "\n" +
                        " End: " + a.getEnd() + "\n" +
                        " Customer ID: " + a.getCusID() + "\n" + "\n";
            }
        }
        return contactSchedule;
    }
    
    /**
     * Appointment by Type Report
     * @return
     * @throws SQLException 
     */
    
    
    public String appointmentByTypeReport() throws SQLException {
        ObservableList<String> types = Appointment.getTypes();
        ObservableList<Appointment> aptsByType = FXCollections.observableArrayList();
        String appointmentString = "Number of Appointments by Type: \n";

        for (String s: types){
            aptsByType = Appointment.appointmentByType(s);
            appointmentString = appointmentString + "\n" + s + ": \n" + aptsByType.size() + "\n";

        }
        return appointmentString;
    }
    
    /**
     * Appointment By Month Report.
     *java
     * @throws SQLException 
     */
    public void AppointmentType() throws SQLException {
        
         //"SELECT type, MONTHNAME(start) as 'MONTH', COUNT(*) as 'TOTAL' FROM appointment" +
                     //"GROUP BY type, MONTHNAME(start)";
       
        String month = monthCB.getValue(); 
        String type = typeCB.getValue(); 
        if(month == null || type == null) {
            return;
        }
        String count = "SELECT COUNT(*) FROM appointments WHERE MONTHNAME(start) = ? AND type = ?";
        PreparedStatement ps = DBConnection.ConnectionObject().prepareStatement(count);
        ps.setString(1, month); 
        ps.setString(2, type); 
        ResultSet rs = ps.executeQuery(); 
        rs.next();
        int total = rs.getInt(1); 
       appointmentByMonth.setText("Appointment Count for " + month + ": " + "\n" + type + " = " + total);
       
       System.out.println("Count for " + month + ":" + type + " = " + total);
    }
    
    @FXML
    void onMonth(ActionEvent event) throws SQLException {
        AppointmentType();
    }

    @FXML
    void onType(ActionEvent event) throws SQLException {
        AppointmentType(); 
    }
    

     /**
      * Customers By Division Report. 
      * @return
      * @throws SQLException 
      */
    public String customersByDivisionReport() throws SQLException {
        DivisionDAO divisionDao = new DivisionDAOQ();
        String customersPerDivision = "Customers Within Each Division: \n";
        ObservableList<Division> allDivisions = divisionDao.getAllDivisions();
        ObservableList<Customer> customersInDiv = FXCollections.observableArrayList();

        for (Division d : allDivisions){
            customersInDiv = Customer.getAllCustomersByDivision(d.getDivID());
            customersPerDivision = customersPerDivision + "\n" + d.getDivision() + ": \n";

            for (Customer c : customersInDiv){
                customersPerDivision = customersPerDivision +
                        "Customer ID: " + c.getCusID() + "\n" +
                        " Name: " + c.getName() + "\n" +
                        " Address: " + c.getAddress() + "\n" +
                        " Postal Code: " + c.getPostalCode() + "\n" +
                        " Phone: " + c.getPhone() + "\n" +"\n";
            }
        }
        return customersPerDivision;
    }

    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ObservableList month = FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"); 
        ObservableList types = FXCollections.observableArrayList("Horse Grooming - Mobile", "Dog/ Cat Grooming - Mobile", "Cat Grooming", "Dog Grooming");
        
        monthCB.setItems(month); 
        typeCB.setItems(types); 
       try {
            contactSchedules.setText(contactScheduleReport());
            customersByDivision.setText(customersByDivisionReport());
            appointmentByType.setText(appointmentByTypeReport());
            //AppointmentType(appointmentByMonth);
            //AppointmentType(); 
            //monthTable(); 
            //appointmentByMonth.setText(appointmentByMonthReport());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    
    
}
