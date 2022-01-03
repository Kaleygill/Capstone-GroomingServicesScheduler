/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Customer;
import Model.GroomingServices;
import Utility.GroomingServiceDAO;
import Utility.GroomingServiceDAOQ;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Koala
 */
public class GroomingSevicesController implements Initializable {

    @FXML
    private TableColumn<GroomingServices, Integer> groomingIDCol;

    @FXML
    private TableColumn<GroomingServices, String> serviceNameCol;

    @FXML
    private TableColumn<GroomingServices, String> descCol;

    @FXML
    private TableColumn<GroomingServices, Double> priceCol;
    
    @FXML
    private TableColumn<GroomingServices, String> typeCol;
    
    @FXML
    private TableView<GroomingServices> groomingTbl;
    
    @FXML
    private TextField searchField;
    
    Stage stage;
    Parent scene;
    
    
    GroomingServiceDAO groomingDao = new GroomingServiceDAOQ();

    @FXML
    void addBtn(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddGroomingServices.fxml"));
        stage.setScene(new Scene(scene));
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void backBtn(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Appointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void deleteBtn(ActionEvent event) throws SQLException {
        
        if (groomingTbl.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Grooming Service will be deleted.");
            alert.setHeaderText("Do you wish to continue?");
            alert.setTitle("Delete Grooming Service");
            Optional<ButtonType> result =  alert.showAndWait();
            
            if (result.isPresent() && result.get() == ButtonType.OK) {
                GroomingServices selectedService = groomingTbl.getSelectionModel().getSelectedItem();
                groomingDao.deleteGroomingService(selectedService.getGroomingID());
                
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION); 
                alert1.setTitle("Grooming Services Deleted Sucessfully.");
                alert.setHeaderText("The following Grooming Service was deleted from the database:");
                alert1.setContentText("ID: " + selectedService.getGroomingID() + "\n" +
                                        "Type: " + selectedService.getType() + "\n");
                alert1.showAndWait();
            }
            
            ObservableList<GroomingServices> allGroomingServices = groomingDao.getAllGroomingServices();
            
            groomingTbl.setItems(allGroomingServices);
            groomingIDCol.setCellValueFactory(new PropertyValueFactory<>("groomingID"));
            serviceNameCol.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
            descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("A Grooming Service must be selected!");
            alert.setHeaderText("Selection Conflict!");
            alert.setTitle("Delete Grooming Service");
            alert.showAndWait();
        }
    }

    @FXML
    void updateBtn(ActionEvent event) throws IOException, SQLException {
        
        if (groomingTbl.getSelectionModel().getSelectedItem() != null) {
            Stage stage; 
            Parent root; 
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ModifyGroomingService.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);
            ModifyGroomingServiceController controller = loader.getController();
            GroomingServices service = groomingTbl.getSelectionModel().getSelectedItem();
            controller.getService(service);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("A Grooming Service must be selected!");
            alert.setHeaderText("Selection Conflict!");
            alert.setTitle("Update Grooming Service");
            alert.showAndWait();
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          try {
              ObservableList<GroomingServices> allCustomers = groomingDao.getAllGroomingServices();
              FilteredList<GroomingServices> filteredData = new FilteredList<>(allCustomers, b -> true);
              
              ObservableList<GroomingServices>allServices = groomingDao.getAllGroomingServices();
              groomingTbl.setItems(allServices);
              groomingIDCol.setCellValueFactory(new PropertyValueFactory<>("groomingID"));
              serviceNameCol.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
              descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
              priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
              typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
              
              
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(service -> {
                
            if (newValue == null ||newValue.isEmpty()) {
                return true; 
            } 
            String lowerCaseFilter = newValue.toLowerCase(); 
            
            
            if (String.valueOf(service.getGroomingID()).contains(lowerCaseFilter)) {
                return true; 
                
            
            } else if (service.getServiceName().contains(lowerCaseFilter)) {
                return true;
            
            } else if (service.getType().contains(lowerCaseFilter)) {
                return true; 
                
            } else return false;
            });
        });
            
            SortedList<GroomingServices>sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(groomingTbl.comparatorProperty());
            groomingTbl.setItems(sortedData);
            } catch (SQLException ex) {
              Logger.getLogger(GroomingSevicesController.class.getName()).log(Level.SEVERE, null, ex);
          }
    }    
    
}
