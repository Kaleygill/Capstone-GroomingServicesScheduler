/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Customer;
import Model.GroomingServices;
import Model.Horse;
import Model.Pets;
import Utility.PetsDAO;
import Utility.PetsDAOQ;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

   

/**
 * FXML Controller class
 *
 * @author Koala
 */
public class PetsController implements Initializable {
    
    @FXML
    private TableView<Pets> petsTbl;

    @FXML
    private TableColumn<Pets, Integer> petIDCol;

    @FXML
    private TableColumn<Pets, String> petNameCol;

    @FXML
    private TableColumn<Pets, String> breedCol;

    @FXML
    private TableColumn<Pets, String> colorCol;

    @FXML
    private TableColumn<Pets, String> weightCol;

    @FXML
    private TableColumn<Pets, Integer> cusIDCol;

    @FXML
    private TableColumn<Pets, String> notesCol;
    
    @FXML
    private TableColumn<Horse, String> horseCol;

    @FXML
    private RadioButton petRadio;

    @FXML
    private ToggleGroup tg;

    @FXML
    private RadioButton horseRadio;
    
    @FXML 
    private Label customerLabel;
    
    PetsDAO petDao = new PetsDAOQ();
    Customer customer;
    
    Stage stage;
    Parent scene;
    
    @FXML
    void addBtn(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddPet.fxml"));
        stage.setScene(new Scene(scene));
        stage.setResizable(false);
        stage.show();
    }
    @FXML
    void backBtn(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Customer.fxml"));
        stage.setScene(new Scene(scene));
        stage.setResizable(false);
        stage.show();
    }
    

    @FXML
    void deleteBtn(ActionEvent event) throws SQLException {

        if (petsTbl.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION); 
            alert.setContentText("Pet entry will be deleted. This cannot be restored."); 
            alert.setHeaderText("Do you wish to continue?"); 
            alert.setTitle("Delete Pet Entry"); 
            
            Optional<ButtonType> result = alert.showAndWait(); 
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    Pets selectedPet = petsTbl.getSelectionModel().getSelectedItem(); 
                    petDao.deletePet(selectedPet.getPetID()); 
                    Alert alert2 = new Alert(AlertType.INFORMATION);
                    alert2.setTitle("Pet Entry Deleted Sucessfully");
                    alert2.setHeaderText("The following Pet Entry was deleted from the database:");
                    alert2.setContentText("ID: " + selectedPet.getPetID());
                    alert2.showAndWait(); 
                    
                    ObservableList<Pets> allPets = petDao.getAllPets(); 
                    
                    petsTbl.setItems(allPets);
                    petIDCol.setCellValueFactory(new PropertyValueFactory<>("petID"));
                    petNameCol.setCellValueFactory(new PropertyValueFactory<>("petName")); 
                    breedCol.setCellValueFactory(new PropertyValueFactory<>("breed"));
                    weightCol.setCellValueFactory(new PropertyValueFactory<>("weight")); 
                    notesCol.setCellValueFactory(new PropertyValueFactory<>("notes"));
                    colorCol.setCellValueFactory(new PropertyValueFactory<>("color")); 
                    cusIDCol.setCellValueFactory(new PropertyValueFactory<>("cusID")); 
                    
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    void updateBtn(ActionEvent event) throws IOException, SQLException {

        if (petsTbl.getSelectionModel().getSelectedItem() != null) {
            Stage stage; 
            Parent root; 
            stage = (Stage)((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ModifyPet.fxml"));
            root = loader.load(); 
            Scene scene = new Scene(root); 
            stage.setScene(scene); 
            stage.show(); 
            stage.setResizable(false); 
            ModifyPetController controller = loader.getController(); 
            Pets pets =  petsTbl.getSelectionModel().getSelectedItem(); 
            controller.getPet(pets);

            System.out.println("Update Button Clicked! " + pets);
        } 
        else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Customer Pet Selection Conflict"); 
            alert.setHeaderText("No pet selected!"); 
            alert.setContentText("Please select a pet to update.");
            alert.showAndWait(); 
        }
    }
    
    public void getSelectedCustomersPets(Customer customer) throws SQLException {
        this.customer = customer;
        int cusID = customer.getCusID();
        System.out.println(cusID);
        ObservableList<Pets> customersPets = petDao.getSelectedCustomersPets(customer.getCusID());
        petsTbl.setItems(customersPets);
            petIDCol.setCellValueFactory(new PropertyValueFactory<>("petID"));
            petNameCol.setCellValueFactory(new PropertyValueFactory<>("petName"));
            breedCol.setCellValueFactory(new PropertyValueFactory<>("breed"));
            colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));
            weightCol.setCellValueFactory(new PropertyValueFactory<>("weight"));
            cusIDCol.setCellValueFactory(new PropertyValueFactory<>("cusID"));
            notesCol.setCellValueFactory(new PropertyValueFactory<>("notes"));  
            
            customerLabel.setText(customer.getName()+ "'s Pets");
    }
    
    @FXML
     void horseRadio(ActionEvent event) throws SQLException {
        ObservableList<Pets> allHorses = FXCollections.observableArrayList();
       for (Horse horse : petDao.getSelectedCustomersHorses(customer.getCusID())) {
           allHorses.add(horse);
    
            petsTbl.setItems(allHorses);
            petIDCol.setCellValueFactory(new PropertyValueFactory<>("petID"));
            petNameCol.setCellValueFactory(new PropertyValueFactory<>("petName"));
            breedCol.setCellValueFactory(new PropertyValueFactory<>("breed"));
            colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));
            weightCol.setCellValueFactory(new PropertyValueFactory<>("weight"));
            cusIDCol.setCellValueFactory(new PropertyValueFactory<>("cusID"));
            notesCol.setCellValueFactory(new PropertyValueFactory<>("notes"));
            horseCol.setCellValueFactory(new PropertyValueFactory<>("hasShoes"));
            
            horseCol.setVisible(true);
       }
        
    }
    
     

    @FXML
    void petRadio(ActionEvent event) throws SQLException {
            ObservableList<Pets> allPets = FXCollections.observableArrayList();
            for (Pets pet : petDao.getSelectedCustomersPets(customer.getCusID())) {
            allPets.add(pet);
    
            petsTbl.setItems(allPets);
            petIDCol.setCellValueFactory(new PropertyValueFactory<>("petID"));
            petNameCol.setCellValueFactory(new PropertyValueFactory<>("petName"));
            breedCol.setCellValueFactory(new PropertyValueFactory<>("breed"));
            colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));
            weightCol.setCellValueFactory(new PropertyValueFactory<>("weight"));
            cusIDCol.setCellValueFactory(new PropertyValueFactory<>("cusID"));
            notesCol.setCellValueFactory(new PropertyValueFactory<>("notes"));
            //horseCol.setCellValueFactory(new PropertyValueFactory<>("hasShoes"));
            
            horseCol.setVisible(false);
        }
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        try {
            
            ObservableList<Pets> allPets = petDao.getAllPets();
            petsTbl.setItems(allPets);
            petIDCol.setCellValueFactory(new PropertyValueFactory<>("petID"));
            petNameCol.setCellValueFactory(new PropertyValueFactory<>("petName"));
            breedCol.setCellValueFactory(new PropertyValueFactory<>("breed"));
            colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));
            weightCol.setCellValueFactory(new PropertyValueFactory<>("weight"));
            cusIDCol.setCellValueFactory(new PropertyValueFactory<>("cusID"));
            notesCol.setCellValueFactory(new PropertyValueFactory<>("notes"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }   
   
}
