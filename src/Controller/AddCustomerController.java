/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.County;
import Model.Division;
import Utility.CountyDAOQ;
import Utility.CustomerDAO;
import Utility.CustomerDAOQ;
import Utility.DivisionDAO;
import Utility.DivisionDAOQ;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Utility.CountyDAO;

/**Add Customer controller. Add customer if validations are passed. 
 * Saved to Database and navigated to customers Screen. 
 * FXML Controller class
 *
 * @author Koala
 */
public class AddCustomerController implements Initializable {

    @FXML
    private Button addBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField addCusID;

    @FXML
    private TextField addNameTxt;

    @FXML
    private TextField addAddressTxt;

    @FXML
    private TextField addPostalTxt;

    @FXML
    private TextField addPhoneTxt;

    @FXML
    private ComboBox<Division> customerDivisionCB;

    @FXML
    private ComboBox<County> customerCountryCB;
    
    @FXML
    private Label error;
    
    Stage stage;
    Parent scene;
    
    /**
     * CancelBtn. Lambda Expression here! This Expression reduced the amount of code needed and better presents the show and wait expression. 
     * @param actionEvent
     * @throws IOException 
     */
    @FXML
    public void cancelBtn (ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Your text fields will not be not saved!");
        alert.setHeaderText("Do you wish to continue?");
        alert.setTitle("Cancel");
//Navigates User to customer screen.   
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/View/Customer.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.setResizable(false);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     * Save Button. Validates textFields. If valid, saves information to database and navigated to customer screen.
     * @param actionEvent
     * @throws IOException
     * @throws SQLException 
     */
    @FXML
    public void saveBtn(ActionEvent actionEvent) throws IOException, SQLException {
        
        if (
                addNameTxt.getText().isEmpty()
                || addAddressTxt.getText().isEmpty()
                || addPostalTxt.getText().isEmpty()
                || addPhoneTxt.getText().isEmpty()
                || customerDivisionCB.getSelectionModel().getSelectedItem() == null
                || customerCountryCB.getSelectionModel().getSelectedItem() == null
        ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("One or more fields are empty.");
            alert.setContentText("Please check fields and try again.");
            alert.showAndWait();
        }
        else {
            
            CustomerDAO customerDao = new CustomerDAOQ();
            String name = addNameTxt.getText();
            String address = addAddressTxt.getText();
            String postalCode = addPostalTxt.getText();
            String phone = addPhoneTxt.getText();
            int divID = customerDivisionCB.getValue().getDivID();

            
            customerDao.addCustomer(name, address, postalCode, phone, divID);

            //Navigates user to Customer Screen
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/Customer.fxml"));
            stage.setScene(new Scene(scene));
            stage.setResizable(false);
            stage.show();
        }
    }
    
    /**
     * County comboBox.
     * @param actionEvent
     * @throws SQLException 
     */
    @FXML
    public void countryCB(ActionEvent actionEvent) throws SQLException {
        DivisionDAO divisionDao = new DivisionDAOQ();
        County selectedCountry = customerCountryCB.getSelectionModel().getSelectedItem();

        ObservableList<Division> divsInCountry = divisionDao.getAllDivisions().filtered(division -> {
            if (division.getCountryID() == selectedCountry.getId())
                return true;
            return false;
        });

        customerDivisionCB.setItems(divsInCountry);
    }
    
    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        CountyDAO countyDao = new CountyDAOQ();

        try {
            customerCountryCB.setItems(countyDao.getAllCounties());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
 
    
}
