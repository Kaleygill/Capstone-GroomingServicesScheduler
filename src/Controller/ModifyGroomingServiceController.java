/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Appointment;
import Model.GroomingServices;
import Utility.GroomingServiceDAO;
import Utility.GroomingServiceDAOQ;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Koala
 */
public class ModifyGroomingServiceController implements Initializable {

        @FXML
    private TextField groomingIDTxt;

    @FXML
    private TextField serviceNameTxt;

    @FXML
    private TextField priceTxt;

    @FXML
    private TextArea descriptionTxt;

    @FXML
    private ComboBox<String> typeCB;
    
    Stage stage;
    Parent scene;

    @FXML
    void addBtn(ActionEvent event) throws SQLException, IOException {
        if (serviceNameTxt.getText().isEmpty() 
            || priceTxt.getText().isEmpty()
            || descriptionTxt.getText().isEmpty()
            || typeCB.getSelectionModel().getSelectedItem() == null ) {
            
            Alert alert = new Alert(Alert.AlertType.ERROR); 
            alert.setTitle("Empty Fields"); 
            alert.setHeaderText("One or more fields are empty."); 
            alert.setContentText("Please check required* fields and try again");
            
        } else {
            GroomingServiceDAO groomingDao = new GroomingServiceDAOQ();
            int groomingID = Integer.parseInt(groomingIDTxt.getText());
            String serviceName = serviceNameTxt.getText();
            String description = descriptionTxt.getText(); 
            Double price = Double.parseDouble(priceTxt.getText());
            String type = typeCB.getSelectionModel().getSelectedItem();
            
            groomingDao.updateGroomingService(groomingID, serviceName, description, price, type);
            
           stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
           scene = FXMLLoader.load(getClass().getResource("/View/GroomingServices.fxml"));
           stage.setScene(new Scene(scene));
           stage.setResizable(false);
           stage.show();
        }
    }

    @FXML
    void cancelBtn(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); 
        alert.setContentText("Do you wish to continue?");
        alert.setHeaderText("Your text fields will not be not saved!");
        alert.setTitle("Cancel");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK){
                try {
                    stage = (Stage)((Button) event.getSource()).getScene().getWindow(); 
                    scene = FXMLLoader.load(getClass().getResource("/View/GroomingServices.fxml")); 
                    stage.setScene(new Scene(scene)); 
                    stage.setResizable(false); 
                    stage.show(); 
                    
                } catch (IOException ex) {
                    Logger.getLogger(AddPetController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public void getService(GroomingServices service) throws SQLException {
        ObservableList<String> types = GroomingServices.getTypes();
        String selectedType = null;
        for (String type : types){
            if (type.equals(service.getType())){
                selectedType = type;
            }
        }
        
        groomingIDTxt.setText(Integer.toString(service.getGroomingID()));
        serviceNameTxt.setText(service.getServiceName());
        descriptionTxt.setText(service.getDescription());
        priceTxt.setText(Double.toString(service.getPrice()));
        typeCB.setValue(selectedType);
        typeCB.setItems(types);
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
