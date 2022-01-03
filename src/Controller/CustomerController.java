/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Appointment;
import Model.Customer;
import Model.Pets;
import Utility.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Customer Controller class. Displays Customers in a TableView. 
 * User has the options to exit, add customer, update customer, and delete customer. 
 *
 * @author Koala
 */
public class CustomerController implements Initializable{

    @FXML
    private TableView<Customer> customersTbl;

    @FXML
    private TableColumn<Customer, Integer> cusIDCol;

    @FXML
    private TableColumn<Customer, String> cusNameCol;

    @FXML
    private TableColumn<Customer, String> cusAddressCol;

    @FXML
    private TableColumn<Customer, String> cusPostalCol;

    @FXML
    private TableColumn<Customer, String> cusPhoneCol;

    @FXML
    private TableColumn<Customer, String> cusDivCol;

    @FXML
    private TableColumn<Customer, String> cusCountryCol;
    
    @FXML
    private Label error;

    @FXML
    private Button addBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button exitBtn;
    
    @FXML
    private TextField searchField;

    PetsDAO petDao = new PetsDAOQ();
    Customer customer;

    Stage stage;
    Parent scene;

    
    CustomerDAO customerDao = new CustomerDAOQ();
    AppointmentDAO appointmentDao = new AppointmentDAOQ();
    
    /**
     * Exit button. Returns User to appointment screen.
     * @param actionEvent
     * @throws IOException 
     */
    @FXML
    public void exitBtn(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/View/Appointment.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.setResizable(false);
                    stage.show();
    }
    
    /**
     * Add Customer Button. Navigates user to add button screen. 
     * @param actionEvent
     * @throws IOException 
     */
    @FXML
    public void addBtn (ActionEvent actionEvent) throws IOException {

        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.setResizable(false);
        stage.show();
    }
    
    /**
     * Update Customer button. Navigates user to Update customer screen if a customer is selected. 
     * Displays alert if no customer is selected. 
     * @param actionEvent
     * @throws IOException
     * @throws SQLException 
     */
    @FXML
    public void updateBtn (ActionEvent actionEvent) throws IOException, SQLException {

        if (customersTbl.getSelectionModel().getSelectedItem() != null){
            Stage stage;
            Parent root;
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ModifyCustomer.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);
            ModifyCustomerController controller = loader.getController();
            Customer customer = customersTbl.getSelectionModel().getSelectedItem();
            controller.getCustomer(customer);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Customer Selection Conflict");
            alert.setHeaderText("No Customer Selected!");
            alert.setContentText("Please Select a Customer to Update");
            alert.showAndWait();
        }
    }
    
    
    @FXML
    void viewPetsBtn(ActionEvent event) throws IOException, SQLException {
        
        if (customersTbl.getSelectionModel().getSelectedItem() != null) {
            Stage stage; 
            Parent root; 
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Pets.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);
            PetsController controller = loader.getController();
            Customer pet = customersTbl.getSelectionModel().getSelectedItem();
            controller.getSelectedCustomersPets(pet);

            System.out.println("View pets button clicked! " + pet);
            
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Customer Selection Conflict");
            alert.setHeaderText("No Customer Selected!");
            alert.setContentText("Please Select a customer to view Pets");
            alert.showAndWait();
        }
    }
    
    /**
     * Delete Customer Button. Deletes selected customer upon confirmation. Alerts user if no customer is selected. 
     * @param actionEvent
     * @throws SQLException 
     */
    @FXML
    public void deleteBtn (ActionEvent actionEvent) {
        try {
            if (customersTbl.getSelectionModel().getSelectedItem() != null) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Customer will be deleted!");
                alert.setHeaderText("Do you wish to continue?");
                alert.setTitle("Delete Customer");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {

                    Customer selectedCustomer = customersTbl.getSelectionModel().getSelectedItem();
                    ObservableList<Appointment> allAppointments = appointmentDao.getAllAppointments();


                    for (Appointment a : allAppointments) {
                        if (a.getCusID() == selectedCustomer.getCusID()) {
                            appointmentDao.deleteAppointment(a.getId());
                        }
                    }

                    customerDao.deleteCustomer(selectedCustomer.getCusID());

                    customersTbl.setItems(customerDao.getAllCustomers());

                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Customer Deleted Successfully");
                    alert1.setHeaderText("The following customer was deleted from the database:");
                    alert1.setContentText("ID: " + selectedCustomer.getCusID() + "\n" +
                            "Name: " + selectedCustomer.getName() + "\n" +
                            "Address: " + selectedCustomer.getAddress() + "\n");
                    alert1.showAndWait();

                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Customer Selection Conflict");
                    errorAlert.setHeaderText("No Customer Selected");
                    errorAlert.setContentText("Please Select a Customer to Delete");
                    errorAlert.showAndWait();
                }
            }

        } catch (SQLException throwables) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Customer Deletion Conflict");
            errorAlert.setHeaderText("Customer has pets registered in the database.");
            errorAlert.setContentText("Please delete all customers pets and try again.");
            errorAlert.showAndWait();
            throwables.printStackTrace();
        }
    }

        /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            ObservableList<Customer> allCustomers = customerDao.getAllCustomers();
            FilteredList<Customer> filteredData = new FilteredList<>(allCustomers, b -> true);
            
            customersTbl.setItems(customerDao.getAllCustomers());
            cusIDCol.setCellValueFactory(new PropertyValueFactory<>("cusID"));
            cusNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            cusAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
            cusPostalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            cusPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
            cusDivCol.setCellValueFactory(new PropertyValueFactory<>("divID"));
            cusCountryCol.setCellValueFactory(new PropertyValueFactory<>("division"));
            
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(customer -> {
                
            if (newValue == null ||newValue.isEmpty()) {
                return true; 
            } 
            String lowerCaseFilter = newValue.toLowerCase(); 
            
            //Search Contact Name
            if (customer.getName().toLowerCase().contains(lowerCaseFilter)) {
                return true; 
                
            //Search Appointment Type
            } else if (String.valueOf(customer.getDivID()).contains(lowerCaseFilter)) {
                return true;
            //Search Appointment ID
            } else if (customer.getPostalCode().contains(lowerCaseFilter)) {
                return true; 
            //Search Customer ID
            } else if (String.valueOf(customer.getCusID()).contains(lowerCaseFilter)) {
                return true;
            } else if (customer.getDivision().contains(lowerCaseFilter)) {
                return true;
            } else return false;
            });
        });
            
            SortedList<Customer>sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(customersTbl.comparatorProperty());
            customersTbl.setItems(sortedData);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
