/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import Model.Appointment;
import Model.User;
import Utility.AppointmentDAO;
import Utility.AppointmentDAOQ;
import Utility.UserDAO;
import Utility.UserDAOQ;
import java.io.IOException;
import java.time.LocalDateTime;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


/**Appointment Controller. Displays a tableview of upcoming appointments from the Database. 
 * The user has the options to navigate to reports, customer, add appointment and update appointments. 
 * TablieView can be changed to show weekly, monthly and all appointments
 * FXML Controller class
 *
 * @author Koala
 */
public class AppointmentController implements Initializable {

    
    @FXML
    private TableView<Appointment> appTable;

    @FXML
    private TableColumn<Appointment, Integer> appIDCol;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, String> DescCol;

    @FXML
    private TableColumn<Appointment, String> locCol;

    @FXML
    private TableColumn<Appointment, Integer> conCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> startCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> endCol;

    @FXML
    private TableColumn<Appointment, Integer> cusIDCol;

    @FXML
    private Button addBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button customersBtn;

    @FXML
    private Button reportsBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private RadioButton allRadio;
    
    @FXML
    private Label error;
    
        @FXML
    private TextField searchField;
    
    private boolean avail;
    
    UserDAO userDao = new UserDAOQ();
    AppointmentDAO appointmentDAO = new AppointmentDAOQ();
    
    

    Stage stage;
    Parent scene;
    
    /**
     * Customers Button. Navigates to customer screen. 
     * @param actionEvent
     * @throws IOException 
     */
    @FXML
    public void customersBtn(ActionEvent actionEvent) throws IOException {

        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Customer.fxml"));
        stage.setScene(new Scene(scene));
        stage.setResizable(false);
        stage.show();
    }
    
    /**
     * Add Appointment Button. Navigates customer to Add Appointment Screen. 
     * @param actionEvent
     * @throws IOException 
     */
    @FXML
    public void addBtn(ActionEvent actionEvent) throws IOException {

        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.setResizable(false);
        stage.show();
    }
    
    /**
     * Update button. Navigates use to Update appointment screen if an appointment is selected. 
     * If not appointment is selected, an alert is shown. 
     * @param actionEvent
     * @throws IOException
     * @throws SQLException 
     */
    @FXML
    public void updateBtn(ActionEvent actionEvent) throws IOException, SQLException {

        if (appTable.getSelectionModel().getSelectedItem() != null){
            Stage stage;
            Parent root;
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ModifyAppointment.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);
            ModifyAppointmentController controller = loader.getController();
            Appointment appointment = appTable.getSelectionModel().getSelectedItem();
            controller.selectedAppointment(appointment);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Selection Conflict");
            alert.setHeaderText("No Appointment Selected!");
            alert.setContentText("Please Select an Appointment to Update");
            alert.showAndWait();
        }
    }
    
    /**
     * Delete Button. Deletes selected appointment. Confirms deletion and warns user is an appointment is not selected.  
     * @param actionEvent
     * @throws SQLException 
     */
    @FXML
    public void deleteBtn(ActionEvent actionEvent) throws SQLException {

        if (appTable.getSelectionModel().getSelectedItem() != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Appointment will be deleted.");
            alert.setHeaderText("Do you wish to continue?");
            alert.setTitle("Delete Appointment");

            Optional<ButtonType> result =  alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                try {
                    Appointment selectedAppointment = appTable.getSelectionModel().getSelectedItem();
                    appointmentDAO.deleteAppointment(selectedAppointment.getId());
                    
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Appointment Deleted Successfully");
                alert1.setHeaderText("The following Appointment was deleted from the database:");
                alert1.setContentText("ID: " + selectedAppointment.getId() + "\n" +
                                        "Type: " + selectedAppointment.getType() + "\n");
                alert1.showAndWait();
                
                
                    ObservableList<Appointment> allAppointments = appointmentDAO.getAllAppointments();
                    ObservableList<Appointment> futureAppointments = FXCollections.observableArrayList();

                    for (Appointment a : allAppointments){
                        //if (a.getEnd().isAfter(LocalDateTime.now())){
                            futureAppointments.add(a);
                        //}
                    }

                    appTable.setItems(futureAppointments);
                    appIDCol.setCellValueFactory(new PropertyValueFactory<>("id") );
                    titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
                    DescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
                    locCol.setCellValueFactory(new PropertyValueFactory<>("location"));
                    conCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
                    typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
                    startCol.setCellValueFactory(new PropertyValueFactory<>("formattedStart"));//Calls formatted version of getter
                    endCol.setCellValueFactory(new PropertyValueFactory<>("formattedEnd"));//Calls formatted version of getter
                    cusIDCol.setCellValueFactory(new PropertyValueFactory<>("cusID"));
                    
                    

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

        } else { 
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Appointment Selection Conflict");
            errorAlert.setHeaderText("No Appointment Selected!");
            errorAlert.setContentText("Please Select an Appointment to Delete");
            errorAlert.showAndWait();
        }


    }
    
    /**
     * Report Button. Navigates user to Reports screen. 
     * @param actionEvent
     * @throws IOException 
     */
    @FXML
    public void reportsBtn(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/ReportsMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.setResizable(false);
        stage.show();
    }
    
    /**
     * Exit button. Closes the application. 
     * @param actionEvent 
     */
    @FXML
    public void exitBtn(ActionEvent actionEvent) {
       Platform.exit();
    }
    
    /**
     * All Appointments Button. Displays appointments that are within a month. 
     * @param actionEvent 
     */
    @FXML
    public void buttonTwo (ActionEvent actionEvent){

        try {
            ObservableList<Appointment> allAppointments = appointmentDAO.getAllAppointments();
            ObservableList<Appointment> futureAppointments = FXCollections.observableArrayList();

            for (Appointment a : allAppointments){
                //if (a.getEnd().isAfter(LocalDateTime.now())){
                    futureAppointments.add(a);
                //}
            }

            appTable.setItems(futureAppointments);
            appIDCol.setCellValueFactory(new PropertyValueFactory<>("id") );
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            DescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            locCol.setCellValueFactory(new PropertyValueFactory<>("location"));
            conCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("formattedStart"));//Calls formatted version of getter
            endCol.setCellValueFactory(new PropertyValueFactory<>("formattedEnd"));//Calls formatted version of getter
            cusIDCol.setCellValueFactory(new PropertyValueFactory<>("cusID"));

        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }
    /**
     * Weekly Button. Displays appointments that are within a week.
     * @param actionEvent
     * @throws SQLException 
     */
    @FXML
    public void buttonOne (ActionEvent actionEvent) throws SQLException {
        ObservableList<Appointment> filteredApts = FXCollections.observableArrayList();

        for (Appointment a : appointmentDAO.getAllAppointments()){
            if (a.getStart().isAfter(LocalDateTime.now()) && a.getStart().isBefore(LocalDateTime.now().plusWeeks(1))){
                filteredApts.add(a);
            }
        }

        appTable.setItems(filteredApts);
        appIDCol.setCellValueFactory(new PropertyValueFactory<>("id") );
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        DescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        conCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("formattedStart"));//Calls formatted version of getter
        endCol.setCellValueFactory(new PropertyValueFactory<>("formattedEnd"));//Calls formatted version of getter
        cusIDCol.setCellValueFactory(new PropertyValueFactory<>("cusID"));

    }
/**
 * All Appointments Button. Displays all appointments. 
 * @param actionEvent
 * @throws SQLException 
 */
@FXML
    public void buttonThree (ActionEvent actionEvent) throws SQLException {
        ObservableList<Appointment> filteredApts = FXCollections.observableArrayList();

        for (Appointment a : appointmentDAO.getAllAppointments()){
            if (a.getStart().isAfter(LocalDateTime.now()) && a.getStart().isBefore(LocalDateTime.now().plusMonths(1))){
                filteredApts.add(a);
            }
        }

        appTable.setItems(filteredApts);
        appIDCol.setCellValueFactory(new PropertyValueFactory<>("id") );
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        DescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        conCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("formattedStart"));//Calls formatted version of getter
        endCol.setCellValueFactory(new PropertyValueFactory<>("formattedEnd"));//Calls formatted version of getter
        cusIDCol.setCellValueFactory(new PropertyValueFactory<>("cusID"));
    }
    
    /**
     * Appointment Check Within 15 Minutes. Checkes for upcoming appointments within 15 minutes. 
     * @param username
     * @throws SQLException 
     */
    public void appointmentWithinFifteenMinutes (String username) throws SQLException {
        ObservableList<Appointment> allAppointments = appointmentDAO.getAllAppointments();
        ObservableList<User> allUsers = userDao.getAllUsers();
        Appointment approachingAppointment = null;
        User user = null;

        
        for (User u : allUsers){
            if (u.getUsername().equals(username)) {
                user = u;
            }
        }

        
        for (Appointment a : allAppointments){
            long timeDifference = ChronoUnit.MINUTES.between(LocalDateTime.now(), a.getStart());
            assert user != null;
            if (a.getuID() == user.getuID() && timeDifference > 0 && timeDifference <= 15){ 
                approachingAppointment = a;
            }
        }

        
        if (approachingAppointment != null){
                LocalDate appointmentDate = approachingAppointment.getStart().toLocalDate();
                LocalTime appointmentTime = approachingAppointment.getStart().toLocalTime();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointment Reminder");
                alert.setHeaderText("You have an upcoming appointment.");
                alert.setContentText("Appointment ID: " + approachingAppointment.getId() + "\n" +
                                    " Date: " + appointmentDate + "\n" +
                                    " Time: " + appointmentTime);
                alert.showAndWait();
        }
        
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Appointment Reminder");
            alert.setHeaderText("You have no upcoming appointments.");
            alert.showAndWait();
        }
    }
    
    @FXML
    void viewGroomingBtn(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/GroomingServices.fxml"));
        stage.setScene(new Scene(scene));
        stage.setResizable(false);
        stage.show();
    }
    
    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        try {

            ObservableList<Appointment> allAppointments = appointmentDAO.getAllAppointments();
            //ObservableList<Appointment> allAppointmentSearch = FXCollections.observableArrayList();

            /*for (Appointment a : allAppointments){
                allAppointmentSearch.add(a);
            } */
            
            FilteredList<Appointment> filteredData = new FilteredList<>(allAppointments, b -> true);

            appTable.setItems(allAppointments);
            appIDCol.setCellValueFactory(new PropertyValueFactory<>("id") );
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            DescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            locCol.setCellValueFactory(new PropertyValueFactory<>("location"));
            conCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("formattedStart"));
            endCol.setCellValueFactory(new PropertyValueFactory<>("formattedEnd"));
            cusIDCol.setCellValueFactory(new PropertyValueFactory<>("cusID"));
            
            searchField.textProperty().addListener((observable, oldValue, newValue) ->{
                filteredData.setPredicate(appointment -> {
            if (newValue == null ||newValue.isEmpty()) {
                return true; 
            } 
            String lowerCaseFilter = newValue.toLowerCase(); 
            
            //Search Contact Name
            if (appointment.getContactName().toLowerCase().contains(lowerCaseFilter)) {
                return true; 
                
            //Search Appointment Type
            } else if (appointment.getType().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            //Search Appointment ID
            } else if (String.valueOf(appointment.getId()).contains(lowerCaseFilter)) {
                return true; 
            //Search Customer ID
            } else if (String.valueOf(appointment.getCusID()).contains(lowerCaseFilter)) {
                return true;
            } else return false;
            });
        });
            
        SortedList<Appointment> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(appTable.comparatorProperty()); 
        appTable.setItems(sortedData);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
