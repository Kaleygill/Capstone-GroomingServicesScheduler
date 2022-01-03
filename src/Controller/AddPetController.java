/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Customer;
import Model.Horse;
import Model.Pets;
import Utility.CustomerDAO;
import Utility.CustomerDAOQ;
import Utility.PetsDAO;
import Utility.PetsDAOQ;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Koala
 */
public class AddPetController implements Initializable {
    @FXML
    private Button addBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField addIDTxt;

    @FXML
    private TextField addNameTxt;

    @FXML
    private TextField addBreedTxt;

    @FXML
    private TextField addColorText;

    @FXML
    private TextField addWeightTxt;

    @FXML
    private ComboBox<Customer> customerCB;
    
    @FXML
    private TextArea addNotesTxt;
    
    @FXML
    private Label hasHorseShoesLabel;

    @FXML
    private ComboBox<String> hasHorseShoesCB;
    
    @FXML 
    private RadioButton petRadioBtn;
    
    @FXML 
    private ToggleGroup petTG;
    
    @FXML 
    private RadioButton horseRadioBtn;


    @FXML
    private Label error;
    
    CustomerDAO customerDao = new CustomerDAOQ();
    PetsDAO petDao = new PetsDAOQ();
    Horse horse;
    
    Stage stage;
    Parent scene;

    @FXML
    void cancelBtn(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); 
        alert.setContentText("Your text fields will not be not saved!");
        alert.setHeaderText("Do you wish to continue?");
        alert.setTitle("Cancel");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK){
                try {
                    stage = (Stage)((Button) event.getSource()).getScene().getWindow(); 
                    scene = FXMLLoader.load(getClass().getResource("/View/Pets.fxml")); 
                    stage.setScene(new Scene(scene)); 
                    stage.setResizable(false); 
                    stage.show(); 
                    
                } catch (IOException ex) {
                    Logger.getLogger(AddPetController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    @FXML
    void customerCB(ActionEvent event) {
        
    }

    @FXML
    void saveBtn(ActionEvent event) throws SQLException, IOException {
        if (
             addNameTxt.getText().isEmpty() 
             || addBreedTxt.getText().isEmpty()
             || addColorText.getText().isEmpty()
             //|| addWeightTxt.getText().isEmpty()
             || customerCB.getSelectionModel().getSelectedItem() == null
           ) {
            Alert alert = new Alert(Alert.AlertType.ERROR); 
            alert.setTitle("Empty Fields"); 
            alert.setHeaderText("One or more fields are empty."); 
            alert.setContentText("Please check required* fields and try again"); 
            alert.showAndWait();
        } else if(horseRadioBtn.isSelected()) {
            if (hasHorseShoesCB.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR); 
            alert.setTitle("Empty Fields"); 
            alert.setHeaderText("The 'Has Horse Shoe' field is empty"); 
            alert.setContentText("Please check field and try again."); 
            alert.showAndWait();
            } else {
            PetsDAO petDao = new PetsDAOQ();
            String petName = addNameTxt.getText();
            String breed = addBreedTxt.getText();
            String color = addColorText.getText();
            String weight = addWeightTxt.getText(); 
            String notes = addNotesTxt.getText();
            Customer customer = customerCB.getSelectionModel().getSelectedItem();
            String horse = hasHorseShoesCB.getSelectionModel().getSelectedItem();
            int id = customer.getCusID();
            
           petDao.addHorse(petName, breed, color, id, notes, weight, horse);
           Parent root; 
           stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Pets.fxml"));
           root = loader.load();
           Scene scene = new Scene(root);
           stage.setScene(scene);
           stage.show();
           stage.setResizable(false);
           PetsController controller = loader.getController();
           Customer pet = customerCB.getSelectionModel().getSelectedItem();
           controller.getSelectedCustomersPets(pet);
            }
        } else {
            PetsDAO petDao = new PetsDAOQ();
            String petName = addNameTxt.getText();
            String breed = addBreedTxt.getText();
            String color = addColorText.getText();
            String weight = addWeightTxt.getText(); 
            String notes = addNotesTxt.getText();
            Customer customer = customerCB.getSelectionModel().getSelectedItem();
            int id = customer.getCusID();
            
           petDao.addPet(petName, breed, color, id, notes, weight);
           
           Parent root; 
           stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Pets.fxml"));
           root = loader.load();
           Scene scene = new Scene(root);
           stage.setScene(scene);
           stage.show();
           stage.setResizable(false);
           PetsController controller = loader.getController();
           Customer pet = customerCB.getSelectionModel().getSelectedItem();
           controller.getSelectedCustomersPets(pet);
        }
    }
    
    @FXML
    void horseRadioBtn(ActionEvent event) {
        hasHorseShoesLabel.setVisible(true);
        hasHorseShoesCB.setVisible(true);
    }

    @FXML
    void petRadioBtn(ActionEvent event) {
        hasHorseShoesLabel.setVisible(false);
        hasHorseShoesCB.setVisible(false);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            customerCB.setItems(customerDao.getAllCustomers());
            hasHorseShoesCB.setItems(Horse.getHorseShoe());
        } catch (SQLException ex) {
            Logger.getLogger(AddPetController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
