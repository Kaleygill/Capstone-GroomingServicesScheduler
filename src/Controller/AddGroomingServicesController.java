/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

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
public class AddGroomingServicesController implements Initializable {

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
    
    GroomingServiceDAO groomingDao = new GroomingServiceDAOQ();

    @FXML
    void addBtn(ActionEvent event) throws SQLException, IOException {

        if(isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("One or more fields are empty.");
            alert.setContentText("Please check fields and try again.");
            alert.showAndWait();
        } else {
            String serviceName = serviceNameTxt.getText();
            String description = descriptionTxt.getText(); 
            Double price = Double.parseDouble(priceTxt.getText());
            String type = typeCB.getValue();
            
            groomingDao.addGroomingService(serviceName,description, price, type);
            
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/GroomingServices.fxml"));
            stage.setScene(new Scene(scene));
            stage.setResizable(false);
            stage.show();
        }
        
        
    }

    @FXML
    void cancelBtn(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Do you wish to continue?");
        alert.setHeaderText("Your text fields will not be not saved!");
        alert.setTitle("Cancel");

        Optional<ButtonType> result =  alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/GroomingServices.fxml"));
            stage.setScene(new Scene(scene));
            stage.setResizable(false);
            stage.show();
        }
    }
    
    public boolean isEmpty(){
        return (serviceNameTxt.getText().isEmpty()
                || descriptionTxt.getText().isEmpty()
                //|| priceTxt.getText().isEmpty()
                || typeCB.getValue() == null
        );
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        typeCB.setItems(GroomingServices.getTypes());
    }    
    
}
