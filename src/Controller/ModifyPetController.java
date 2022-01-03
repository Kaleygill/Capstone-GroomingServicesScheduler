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
public class ModifyPetController implements Initializable {

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField petIDTxt;

    @FXML
    private TextField petNameTxt;

    @FXML
    private TextField petBreedTxt;

    @FXML
    private TextField petColorText;

    @FXML
    private TextField petWeightTxt;

    @FXML
    private ComboBox<Customer> customerCB;

    @FXML
    private Label error;

    @FXML
    private TextArea petNotesTxt;

    @FXML
    private ComboBox<String> hasHorseShoesCB;

    @FXML
    private RadioButton petRadioBtn;

    @FXML
    private Label hasHorseShoesLabel;

    @FXML
    private ToggleGroup tg;

    @FXML
    private RadioButton horseRadioBtn;

    Stage stage;
    Parent scene;
    CustomerDAO customerDao = new CustomerDAOQ();
    Pets pet;

    Customer customer;

    public int getCusID(Customer customer) {
        int cusID = customerCB.getSelectionModel().getSelectedItem().getCusID();
        return cusID;
    }

    @FXML
    void cancelBtn(ActionEvent event) throws SQLException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Your text fields will not be not saved!");
        alert.setHeaderText("Do you wish to continue?");
        alert.setTitle("Cancel");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
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
                    Customer pet = customerCB.getSelectionModel().getSelectedItem();
                    controller.getSelectedCustomersPets(pet);
                } catch (IOException e) {
                } catch (SQLException ex) {
                    Logger.getLogger(ModifyPetController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    @FXML
    void saveBtn(ActionEvent event) throws IOException, SQLException {
        if (isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty Fields");
            alert.setHeaderText("One or more fields are empty.");
            alert.setContentText("Please check required fields and try again");
            alert.showAndWait();
            System.out.println("Method Ran");

    } else if(horseRadioBtn.isSelected()) {
        PetsDAO petDao = new PetsDAOQ();
        int petID = Integer.parseInt(petIDTxt.getText());
        String petName = petNameTxt.getText();
        String breed = petBreedTxt.getText();
        String color = petColorText.getText();
        String weight = petWeightTxt.getText();
        String notes = petNotesTxt.getText();
        Customer customer = customerCB.getSelectionModel().getSelectedItem();
        String horse = hasHorseShoesCB.getSelectionModel().getSelectedItem();
        int id = customer.getCusID();

        petDao.updateHorse(petID, petName, breed, color, weight, id, notes, horse);

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
        Customer pet = customerCB.getSelectionModel().getSelectedItem();
        controller.getSelectedCustomersPets(pet);
        System.out.println("Method 1 Used");
    } else if(petRadioBtn.isSelected()) {
            System.out.println("Method 2 Used");
        PetsDAO petDao = new PetsDAOQ();
        int petID = Integer.parseInt(petIDTxt.getText());
        String petName = petNameTxt.getText();
        String breed = petBreedTxt.getText();
        String color = petColorText.getText();
        String weight = petWeightTxt.getText();
        String notes = petNotesTxt.getText();
        Customer customer = customerCB.getSelectionModel().getSelectedItem();
        int id = customer.getCusID();

        petDao.updatePet(petID, petName, breed, color, weight, id, notes);
        System.out.println("Method used");

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
        Customer pet = customerCB.getSelectionModel().getSelectedItem();
        controller.getSelectedCustomersPets(pet);
    } else {
            System.out.println("SKIPPED");
        }

}

    
    @FXML
    void petRadioBtn(ActionEvent event) {
        hasHorseShoesLabel.setVisible(false);
        hasHorseShoesCB.setVisible(false);
    }

    
    @FXML
    void horseRadioBtn(ActionEvent event) {
        hasHorseShoesLabel.setVisible(true);
        hasHorseShoesCB.setVisible(true);
    }
    
    public void getPet(Pets pet) throws SQLException {
        ObservableList<Customer> allCustomers = customerDao.getAllCustomers();
        Customer selectedCustomer = null;
        
        for(Customer cus : allCustomers) {
            if (cus.getCusID() == pet.getCusID()) {
                selectedCustomer = cus;
            }

            System.out.println("Customer pet retrieved " + cus);
        }

        petIDTxt.setText(Integer.toString(pet.getPetID()));
        petNameTxt.setText(pet.getPetName());
        customerCB.setItems(allCustomers);
        customerCB.setValue(selectedCustomer);
        petBreedTxt.setText(pet.getBreed());
        petColorText.setText(pet.getColor());
        petWeightTxt.setText(pet.getWeight());
        petNotesTxt.setText(pet.getNotes());
        hasHorseShoesCB.setValue(pet.getHasShoes());
        
        if (hasHorseShoesCB.getValue() != null) {
            horseRadioBtn.setSelected(true);
            hasHorseShoesLabel.setVisible(true);
            hasHorseShoesCB.setVisible(true);
        } else {
            petRadioBtn.setSelected(true);
        }
        

    }

    public boolean isEmpty() {
        return (petNameTxt.getText().isEmpty()
                || petBreedTxt.getText().isEmpty()
                || petColorText.getText().isEmpty()
                || customerCB.getValue() == null
        );
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
