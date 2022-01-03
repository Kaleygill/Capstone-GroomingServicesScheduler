/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.*;
import Utility.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Modify Controller class. Allows user to update a selected appointment.
 *
 * @author Koala
 */
public class ModifyAppointmentController  {

    @FXML
    private TextField aIDTxt;

    @FXML
    private TextField titleTxt;

    @FXML
    private TextField descTxt;

    @FXML
    private ComboBox<Contact> contactNameCB;

    @FXML
    private Button modSaveBtn;

    @FXML
    private Button addCancelBtn;

    @FXML
    private TextField locTxt;

    @FXML
    private ComboBox<String> typeCB;

    @FXML
    private DatePicker dateTxt;

    @FXML
    private ComboBox<Integer> customerCB;

    @FXML
    private ComboBox<User> userCB;

    @FXML
    private ComboBox<LocalTime> endTimeCB;

    @FXML
    private Label error;

    @FXML
    private ComboBox<LocalTime> startTimeCB;
    
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
    private TableColumn<GroomingServices, Integer> groomingIDCol2;

    @FXML
    private TableColumn<GroomingServices, String> nameCol2;

    @FXML
    private TableColumn<GroomingServices, String> descCol2;

    @FXML
    private TableColumn<GroomingServices, String> typeCol2;

    @FXML
    private TableColumn<GroomingServices, Double> priceCol2;
    
    AppointmentDAO appointmentDao = new AppointmentDAOQ();
    CustomerDAO customerDao = new CustomerDAOQ();
    UserDAO userDao = new UserDAOQ();
    ContactDAO contactDao = new ContactDAOQ();
    GroomingServiceDAO groomingDao = new GroomingServiceDAOQ();
    GroomingServices selectedService; 
    Appointment selectedAppointment;

    private ObservableList<GroomingServices> serviceToTransfer = FXCollections.observableArrayList();
    
    

    Stage stage;
    Parent scene;
    
    /**
     * Cancel Button. Navigates 
     * @param actionEvent
     * @throws IOException 
     */
    @FXML
    public void cancelBtn (ActionEvent actionEvent) throws IOException {

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Your text fields will not be not saved!");
        alert.setHeaderText("Do you wish to continue?");
        alert.setTitle("Cancel");
   
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/View/Appointment.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.setResizable(false);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    @FXML
    void addServiceButton(ActionEvent event) throws SQLException {
        selectedService = groomingTbl1.getSelectionModel().getSelectedItem();
        serviceToTransfer.add(selectedService);
        if (selectedService == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please select part to add.");
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
    void deleteServiceButton(ActionEvent event) throws SQLException {
        int selectedItem = groomingTbl2.getSelectionModel().getSelectedIndex();
        selectedService = groomingTbl2.getSelectionModel().getSelectedItem();
        //serviceToDelete.add(selectedService);
        int appointmentID = Integer.parseInt(aIDTxt.getText());

        int groomingID = selectedService.getGroomingID();

        if (selectedItem >= 0) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to delete selected item?/n This will remove the service from the DataBase for this Appointment.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                groomingTbl2.getItems().remove(selectedItem);
                appointmentDao.DeleteServiceUponUpdateIfRemoved(appointmentID, groomingID);
            }
        } else {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setContentText("No item was selected!");
            alert1.showAndWait();
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
    void horseServicesBtn(ActionEvent event) throws SQLException {
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

    
    /**
     * Save Button. Saves changes made to appointment. Checks if any fields are empty or conflicting appointments. 
     * If valid, appointment is saved tot he database and the customer is returned to Appointment screen. 
     * @param actionEvent
     * @throws IOException
     * @throws SQLException 
     */
    @FXML
    public void saveBtn (ActionEvent actionEvent) throws IOException, SQLException {
        
        if (isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("One or more fields are empty.");
            alert.setContentText("Please check fields and try again.");
            alert.showAndWait();
        }
         else {
           
            LocalDate date = dateTxt.getValue();
            LocalTime startTime = startTimeCB.getValue();
            LocalTime endTime = endTimeCB.getValue();
            LocalDateTime start = LocalDateTime.of(date, startTime);
            LocalDateTime end = LocalDateTime.of(date, endTime);
            Timestamp startTimestamp = Timestamp.valueOf(start);
            Timestamp endTimestamp = Timestamp.valueOf(end);
            Appointment conflictingAppointment = Appointment.isScheduleConflict(customerCB.getSelectionModel().getSelectedItem(), start, end);

            
            if (conflictingAppointment != null && conflictingAppointment.getId() != Integer.parseInt(aIDTxt.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Conflicting Appointment Date/ Time");
                alert.setHeaderText("Conflicting Time! Please check selection.");
                alert.setContentText("Appointment ID: " + conflictingAppointment.getId() + "\n" +
                        "Date: " + conflictingAppointment.getStart().toLocalDate() + "\n" +
                        "Start: " + conflictingAppointment.getStart().toLocalTime() + "\n" +
                        "End: " + conflictingAppointment.getEnd().toLocalTime());
                alert.showAndWait();
                
                if (conflictingAppointment.getEnd().equals(Time.getLocalEndTime())){
                    startTimeCB.getSelectionModel().clearSelection();
                }
                else {
                    startTimeCB.setValue(conflictingAppointment.getEnd().toLocalTime());
                }
            }
            //Validating appointment is in the future. 
            else if (start.isBefore(LocalDateTime.now()) || end.isBefore(LocalDateTime.now())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Conflicting Appointment Time/ Date");
                alert.setHeaderText("Conflicting time! Check selection.");
                alert.setContentText("Please select an appointment time and date in the future.");
                alert.showAndWait();
            }
            
            else {
                
                int aID = Integer.parseInt(aIDTxt.getText());
                String title = titleTxt.getText();
                String description = descTxt.getText();
                String location = locTxt.getText();
                int conID = contactNameCB.getSelectionModel().getSelectedItem().getId();
                String type = typeCB.getValue();
                int cusID = customerCB.getValue();
                int uID = userCB.getValue().getuID();

                
                appointmentDao.updateAppointment(aID, title, description, location, type, startTimestamp, endTimestamp, cusID, uID, conID);

                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/Appointment.fxml"));
                stage.setScene(new Scene(scene));
                stage.setResizable(false);
                stage.show();
            }
        }
    }
    

    /**
     *Selected Appointment. Gets selected appointment from appointment screen, and populates textFields in modifyApppointment screen. 
     * @param appointment
     * @throws SQLException
     */
        public void selectedAppointment(Appointment appointment) throws SQLException {
        LocalDate date = appointment.getStart().toLocalDate();
        LocalTime start = appointment.getStart().toLocalTime();
        LocalTime end = appointment.getEnd().toLocalTime();
        Contact selectedContact = null;
        int selectedCustomerId = 0;
        User selectedUser = null;

        ObservableList<Contact> allContacts = contactDao.getAllContacts();
        ObservableList<Customer> allCustomers = customerDao.getAllCustomers();
        ObservableList<User> allUsers = userDao.getAllUsers();
        ObservableList<Integer> allCustomerIds = FXCollections.observableArrayList();
        ObservableList<String> types = Appointment.getTypes();
        ObservableList<GroomingServices> allServicesAssociatedWithAppointment = appointmentDao.getAllAppointmentServices(appointment.getId());
        String selectedType = null;


        LocalDateTime startHours = Time.getLocalStartTime();
        LocalDateTime endHours = Time.getLocalEndTime();

        while (startHours.isBefore(endHours.plusSeconds(1))){
            startTimeCB.getItems().add(LocalTime.from(startHours));
            startHours = startHours.plusHours(1);
        }

        for (Customer cus : allCustomers) {
            if (cus.getCusID() == appointment.getCusID()){
                selectedCustomerId = cus.getCusID();
            }
        }

        for (User user : allUsers) {
            if (user.getuID() == appointment.getuID()){
                selectedUser = user;
            }
        }

        for (Contact contact : allContacts)
            if (contact.getId() == appointment.getConID()) {
                selectedContact = contact;
            }

        for (Customer cus : allCustomers){
            if (cus.getCusID() != 0){
                int customerId = cus.getCusID();
                allCustomerIds.add(customerId);
            }
        }

        for (String type : types){
            if (type.equals(appointment.getType())){
                selectedType = type;
            }
        }

        aIDTxt.setText(Integer.toString(appointment.getId() ));
        titleTxt.setText(appointment.getTitle());
        descTxt.setText(appointment.getDescription());
        locTxt.setText(appointment.getLocation());
        typeCB.setValue(selectedType);
        typeCB.setItems(types);
        contactNameCB.setItems(allContacts);
        contactNameCB.setValue(selectedContact);
        dateTxt.setValue(date);
        startTimeCB.setValue(start);
        endTimeCB.setValue(end);
        customerCB.setItems(allCustomerIds);
        customerCB.setValue(selectedCustomerId);
        userCB.setItems(allUsers);
        userCB.setValue(selectedUser);
        groomingTbl2.setItems(allServicesAssociatedWithAppointment);
        
        groomingIDCol2.setCellValueFactory(new PropertyValueFactory<>("groomingID"));
        nameCol2.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        descCol2.setCellValueFactory(new PropertyValueFactory<>("description"));
        priceCol2.setCellValueFactory(new PropertyValueFactory<>("price"));
        typeCol2.setCellValueFactory(new PropertyValueFactory<>("type"));

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
    }
    /**
     * Populates start and end time Combo Boxes
     * @param actionEvent 
     */    
    public void startTimeAction (ActionEvent actionEvent) {
        endTimeCB.getItems().clear();
        LocalDate selectedDate = dateTxt.getValue();
        LocalTime localEndTime = Time.getLocalEndTime().toLocalTime();
        LocalDateTime localEnd = LocalDateTime.of(selectedDate, localEndTime);
        LocalTime selectedStart = startTimeCB.getSelectionModel().getSelectedItem();
        LocalDateTime selectedStartDate = LocalDateTime.of(selectedDate, selectedStart);
        endTimeCB.setValue(selectedStart.plusHours(1));

        while (selectedStart.isBefore(localEndTime.plusSeconds(1))) {
            endTimeCB.getItems().add(LocalTime.from(selectedStart.plusHours(1)));
            selectedStart = selectedStart.plusHours(1);
        }
    }
    /**
     * Field Validation
     * @return 
     */
     public boolean isEmpty(){
        return (titleTxt.getText().isEmpty()
                || descTxt.getText().isEmpty()
                || locTxt.getText().isEmpty()
                || contactNameCB.getSelectionModel().getSelectedItem() == null
                || typeCB.getValue() == null
                || dateTxt.getValue() == null
                || startTimeCB.getSelectionModel().getSelectedItem() == null
                || endTimeCB.getSelectionModel().getSelectedItem() == null
                || customerCB.getSelectionModel().getSelectedItem() == null
                || userCB.getSelectionModel().getSelectedItem() == null
        );
    }
     
    /**
     *
     * @param url
     * @param resourceBundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
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
        } catch (SQLException ex) {
            Logger.getLogger(ModifyAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
}

    
