/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.GroomingServices;
import Model.User;
import Utility.AppointmentDAO;
import Utility.AppointmentDAOQ;
import Utility.ContactDAO;
import Utility.ContactDAOQ;
import Utility.CustomerDAO;
import Utility.CustomerDAOQ;
import Utility.GroomingServiceDAO;
import Utility.GroomingServiceDAOQ;
import Utility.Time;
import static Utility.Time.getLocalEndTime;
import static Utility.Time.getLocalStartTime;
import Utility.UserDAO;
import Utility.UserDAOQ;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.script.Bindings;

/**
 * FXML Controller class
 *
 * @author Koala
 */
public class AddAppointmentController implements Initializable {

     @FXML
    private TextField aIDTxt;

    @FXML
    private TextField titleTxt;

    @FXML
    private TextField descTxt;

    @FXML
    private ComboBox<Contact> contactTxt;

    @FXML
    private Button addSaveBtn;

    @FXML
    private Button addCancelBtn;

    @FXML
    private TextField locTxt;


    @FXML
    private ComboBox<String> typeCB;

    @FXML
    private DatePicker date;

    @FXML
    private ComboBox<Integer> customer;

    @FXML
    private ComboBox<User> user;

    @FXML
    private ComboBox<LocalTime> endTime;

    @FXML
    private Label error;

    @FXML
    private ComboBox<LocalTime> startTime;
    
    @FXML
    private RadioButton dogServices;

    @FXML
    private ToggleGroup grooomingTG;

    @FXML
    private RadioButton catServices;

    @FXML
    private TableView<GroomingServices> groomingTbl1;

    @FXML
    private TableColumn<GroomingServices, Integer> groomingIDCol1;

    @FXML
    private TableColumn<GroomingServices, String> nameCol1;

    @FXML
    private TableColumn<GroomingServices, String> descCol1;

    @FXML
    private TableColumn<GroomingServices, String> typeCol1;

    @FXML
    private TableColumn<GroomingServices, Double> priceCol1;

    @FXML
    private TableView<GroomingServices> groomingTbl2;

    @FXML
    private TableColumn<GroomingServices, String> groomingIDCol2;

    @FXML
    private TableColumn<GroomingServices, String> nameCol2;

    @FXML
    private TableColumn<GroomingServices, String> descCol2;

    @FXML
    private TableColumn<GroomingServices, String> typeCol2;

    @FXML
    private TableColumn<GroomingServices, Double> priceCol2;

    @FXML
    private RadioButton horseServices;

    @FXML
    private RadioButton allServices;

    AppointmentDAO appointmentDao = new AppointmentDAOQ();
    ContactDAO contactDao = new ContactDAOQ();
    CustomerDAO customerDao = new CustomerDAOQ();
    UserDAO userDao = new UserDAOQ();
    GroomingServiceDAO groomingDao = new GroomingServiceDAOQ();
    
    private GroomingServices selectedService;
    private ObservableList<GroomingServices> serviceToTransfer = FXCollections.observableArrayList();
     
    
    Stage stage;
    Parent scene;
    
    /**
     * Cancel Button. Alerts user that cancelling will lose all unsaved information. 
     * If okay, navigates back to appointment screen. 
     * @param actionEvent
     * @throws IOException 
     */
    @FXML
    public void cancelBtn (ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Your text fields will not be not saved!");
        alert.setHeaderText("Do you wish to continue?");
        alert.setTitle("Cancel");

        Optional<ButtonType> result =  alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/Appointment.fxml"));
            stage.setScene(new Scene(scene));
            stage.setResizable(false);
            stage.show();
        }
    }
    
    
    
    
    /**
     * Add Button. Validates users selections. If valid, adds the appointment to the database. 
     * @param actionEvent
     * @throws IOException
     * @throws SQLException 
     */
    @FXML
    public void addBtn (ActionEvent actionEvent) throws IOException, SQLException {

        //Check if text Fields are empty 
        if (isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("One or more fields are empty.");
            alert.setContentText("Please check fields and try again.");
            alert.showAndWait();
        }
        
        else {
            LocalDate selectedDate = date.getValue();
            LocalTime selectedStart = startTime.getValue();
            LocalTime selectedEnd = endTime.getValue();
            LocalDateTime startDateTime = LocalDateTime.of(selectedDate, selectedStart);
            LocalDateTime endDateTime = LocalDateTime.of(selectedDate, selectedEnd);
            Appointment conflictingAppointment = Appointment.isScheduleConflict(customer.getSelectionModel().getSelectedItem(), startDateTime, endDateTime);

            //Checking if there is a conflicting Appointment 
            if (conflictingAppointment != null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Scheduling Conflict");
                alert.setHeaderText("Appointment Already Scheduled. Please Check details below and make corrections");
                alert.setContentText("Appointment ID: " + conflictingAppointment.getId() + "\n" +
                        "Date: " + conflictingAppointment.getStart().toLocalDate() + "\n" +
                        "Start: " + conflictingAppointment.getStart().toLocalTime() + "\n" +
                        "End: " + conflictingAppointment.getEnd().toLocalTime());
                alert.showAndWait();
                startTime.setValue(conflictingAppointment.getEnd().toLocalTime());
            }
            //Validating if selected time is not a future time. 
            else if (startDateTime.isBefore(LocalDateTime.now()) || endDateTime.isBefore(LocalDateTime.now())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Conflicting Appointment Time/ Date");
                alert.setHeaderText("Conflicting time! Check selection.");
                alert.setContentText("Please select an appointment time and date in the future.");
                alert.showAndWait();
            } else if (serviceToTransfer.isEmpty()) {
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Services are required!");
                alert.setHeaderText("No services added to appointment");
                alert.setContentText("Please select atleast one service for the appointment.");
                alert.showAndWait();
            }
            
            /*else if (endDateTime.isAfter(getLocalEndTime()) || startDateTime.isBefore(getLocalStartTime())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Conflicting Appointment Time/ Date");
                alert.setHeaderText("Conflicting time! Check selection.");
                alert.setContentText("Please select an appointment time and date within Business Hours (8am EST and 10pm EST.)");
                alert.showAndWait();
            }*/
            //Saves textfield information and navigates user back to appointment page. 
            else {
                String title = titleTxt.getText();
                String description = descTxt.getText();
                String location = locTxt.getText();
                Contact contact = contactTxt.getSelectionModel().getSelectedItem();
                int conID = contact.getId();
                String type = typeCB.getValue();
                Timestamp startTimestamp = Timestamp.valueOf(startDateTime);
                Timestamp endTimestamp = Timestamp.valueOf(endDateTime);
                int cusID = customer.getSelectionModel().getSelectedItem();
                int uID = user.getSelectionModel().getSelectedItem().getuID();
               
                int appointmentID = appointmentDao.addAppointment(title, description, location, type, startTimestamp, endTimestamp, cusID, uID, conID);
                
                for (int i = 0; i < serviceToTransfer.size(); i++) {
                int groomingID = serviceToTransfer.get(i).getGroomingID();
                appointmentDao.addSelectedServicestoAppointment(appointmentID, groomingID);
                }

                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/Appointment.fxml"));
                stage.setScene(new Scene(scene));
                stage.setResizable(false);
                stage.show();
            }
        }
    }
    
    @FXML
    void allServicesBtn(ActionEvent event) throws SQLException {
        ObservableList<GroomingServices> allServices = FXCollections.observableArrayList();
        for (GroomingServices service : groomingDao.getAllGroomingServices()) {
            allServices.add(service);
            
            groomingTbl1.setItems(allServices);
            groomingIDCol1.setCellValueFactory(new PropertyValueFactory<>("groomingID"));
            nameCol1.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
            descCol1.setCellValueFactory(new PropertyValueFactory<>("description"));
            priceCol1.setCellValueFactory(new PropertyValueFactory<>("price"));
            typeCol1.setCellValueFactory(new PropertyValueFactory<>("type"));
        }
    
    }
    
    @FXML
    void catServicesBtn(ActionEvent event) throws SQLException {
        ObservableList<GroomingServices> allCatServices = FXCollections.observableArrayList();
        for (GroomingServices service : groomingDao.getAllCatGroomingServices()) {
            allCatServices.add(service);
            
            groomingTbl1.setItems(allCatServices);
            groomingIDCol1.setCellValueFactory(new PropertyValueFactory<>("groomingID"));
            nameCol1.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
            descCol1.setCellValueFactory(new PropertyValueFactory<>("description"));
            priceCol1.setCellValueFactory(new PropertyValueFactory<>("price"));
            typeCol1.setCellValueFactory(new PropertyValueFactory<>("type"));
        }
    }

    @FXML
    void dogServicesBtn(ActionEvent event) throws SQLException {
        ObservableList<GroomingServices> allDogServices = FXCollections.observableArrayList();
        for (GroomingServices service : groomingDao.getAllDogGroomingServices()) {
            allDogServices.add(service);
            
            groomingTbl1.setItems(allDogServices);
            groomingIDCol1.setCellValueFactory(new PropertyValueFactory<>("groomingID"));
            nameCol1.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
            descCol1.setCellValueFactory(new PropertyValueFactory<>("description"));
            priceCol1.setCellValueFactory(new PropertyValueFactory<>("price"));
            typeCol1.setCellValueFactory(new PropertyValueFactory<>("type"));
        }
    }

    @FXML
    void horseServiceBtn(ActionEvent event) throws SQLException {
        ObservableList<GroomingServices> allHorseServices = FXCollections.observableArrayList();
        for (GroomingServices service : groomingDao.getAllHorseGroomingServices()) {
            allHorseServices.add(service);
            
            groomingTbl1.setItems(allHorseServices);
            groomingIDCol1.setCellValueFactory(new PropertyValueFactory<>("groomingID"));
            nameCol1.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
            descCol1.setCellValueFactory(new PropertyValueFactory<>("description"));
            priceCol1.setCellValueFactory(new PropertyValueFactory<>("price"));
            typeCol1.setCellValueFactory(new PropertyValueFactory<>("type"));
        }   
    }
    
    @FXML
    void deleteServiceBtn(ActionEvent event) {
         int selectedItem = groomingTbl2.getSelectionModel().getSelectedIndex();

        if (selectedItem >= 0) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to delete selected item?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                groomingTbl2.getItems().remove(selectedItem);
            }
        } else {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setContentText("No item was selected!");
            alert1.showAndWait();
        }
    }
    
    @FXML
    void addServiceBtn(ActionEvent event) {

        selectedService = groomingTbl1.getSelectionModel().getSelectedItem();
        serviceToTransfer.add(selectedService);
        if (selectedService == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please select a service to add.");
            alert.showAndWait();
        } 
        else {
            groomingTbl2.setItems(serviceToTransfer);
            groomingIDCol2.setCellValueFactory(new PropertyValueFactory<>("groomingID"));
            nameCol2.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
            descCol2.setCellValueFactory(new PropertyValueFactory<>("description"));
            priceCol2.setCellValueFactory(new PropertyValueFactory<>("price"));
            typeCol2.setCellValueFactory(new PropertyValueFactory<>("type"));
        }
    }
    
    /**
     * Initialize
     * @param url
     * @param resourceBundle 
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try { 
            ObservableList<Customer> allCustomers = customerDao.getAllCustomers();
            ObservableList<Integer> allCustomerIds = FXCollections.observableArrayList();
            
            ObservableList<GroomingServices> allServicesInitialLoad = FXCollections.observableArrayList();
            
             for (GroomingServices service : groomingDao.getAllGroomingServices()) {
            allServicesInitialLoad.add(service);
            
            groomingTbl1.setItems(allServicesInitialLoad);
            groomingIDCol1.setCellValueFactory(new PropertyValueFactory<>("groomingID"));
            nameCol1.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
            descCol1.setCellValueFactory(new PropertyValueFactory<>("description"));
            priceCol1.setCellValueFactory(new PropertyValueFactory<>("price"));
            typeCol1.setCellValueFactory(new PropertyValueFactory<>("type"));
        }

            
            for (Customer c : allCustomers){
                if (c.getCusID() != 0){
                    int cusID = c.getCusID();
                    allCustomerIds.add(cusID);
                }
            }
           
            contactTxt.setItems(contactDao.getAllContacts());
            customer.setItems(allCustomerIds);
            user.setItems(userDao.getAllUsers());
            typeCB.setItems(Appointment.getTypes());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        LocalDateTime localStart = Time.getLocalStartTime();
        LocalDateTime localEnd = Time.getLocalEndTime();


        while (localStart.isBefore(localEnd.plusSeconds(1))){
                startTime.getItems().add(LocalTime.from(localStart));
                localStart = localStart.plusMinutes(30);
        }
        
        
    }
    /**startTimeCB. Populates startTime ComboBox and endTime ComboBox. 
     *Sets combo box one hour ahead of start time. Date and startTim must be selected first. 
     * 
     * @param actionEvent 
     */
    
     public void startTimeCB (ActionEvent actionEvent) {
        endTime.getItems().clear();

        LocalDate selectedDate = date.getValue();
        
        LocalTime localEndTime = Time.getLocalEndTime().toLocalTime();
        LocalDateTime localEnd = LocalDateTime.of(selectedDate, localEndTime);

        LocalTime selectedStart = startTime.getSelectionModel().getSelectedItem();
        LocalDateTime selectedStartDate = LocalDateTime.of(selectedDate, selectedStart);

        endTime.setValue(selectedStart.plusHours(1));

        while (selectedStartDate.isBefore(localEnd.plusSeconds(1))) {
            endTime.getItems().add(LocalTime.from(selectedStartDate.plusMinutes(30)));
            selectedStartDate = selectedStartDate.plusMinutes(30);
        }
    }
     /**IsEmpty. Checks if fields are empty. 
      * 
      * @return 
      */
      public boolean isEmpty(){
        return (titleTxt.getText().isEmpty()
                || descTxt.getText().isEmpty()
                || locTxt.getText().isEmpty()
                || contactTxt.getValue() == null
                || typeCB.getValue() == null
                || date.getValue() == null
                || startTime.getValue() == null
                || endTime.getValue() == null
                || customer.getValue() == null
                || user.getValue() == null
                //|| serviceToTransfer.isEmpty()

        );
    }

    
}
