/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.County;
import Model.Customer;
import Model.Division;
import Utility.CountyDAOQ;
import Utility.CustomerDAO;
import Utility.CustomerDAOQ;
import Utility.DivisionDAO;
import Utility.DivisionDAOQ;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

/**Modify Customer. Allows user to modify an existing customer. 
 * Validation and alerts are present for missing fields and confirmations. 
 * FXML Controller class
 *
 * @author Koala
 */
public class ModifyCustomerController {

    @FXML
    private Button modBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField modCusIDTxt;

    @FXML
    private TextField modNameTxt;

    @FXML
    private TextField modAddressTxt;

    @FXML
    private TextField modPostalTxt;

    @FXML
    private TextField modPhoneTxt;

    @FXML
    private ComboBox<Division> modDivisionCB;

    @FXML
    private ComboBox<County> modCountryCB;

    @FXML
    private Label error;
    
    Stage stage;
    Parent scene;
    DivisionDAO divisionDao = new DivisionDAOQ();
    CountyDAO countryDao = new CountyDAOQ();
    CustomerDAO customerDao = new CustomerDAOQ();

    /**
     * Retrieve division associated with the Division ID. 
     * @param divID
     * @return
     * @throws SQLException 
     */
    public Division getDivision(int divID) throws SQLException {

        ObservableList<Division> allDivisions = divisionDao.getAllDivisions();
        Division selectedDivision = null;

        for (Division d : allDivisions){
            if (divID == d.getDivID()){
                selectedDivision = d;
            }
        }

        return selectedDivision;
    }
    /**
     * Retrieves County associated with Division. 
     * @param countryId
     * @return
     * @throws SQLException 
     */
     public County getCountry(int countryId) throws SQLException {
        ObservableList<County> allCountries = countryDao.getAllCounties();
        County selectedCountry = null;

        for (County c : allCountries){
            if (countryId == c.getId()){
                selectedCountry = c;
            }
        }

        return selectedCountry;
    }
     
     /**
      * Gets Selected Customer and populates in appropriate Text Fields.
      * @param customer
      * @throws SQLException 
      */
    public void getCustomer(Customer customer) throws SQLException {

        
        Division selectedDivision = getDivision(customer.getDivID());
        
        County selectedCountry = getCountry(selectedDivision.getCountryID());

        
        ObservableList<Division> divInSelectedCountry = FXCollections.observableArrayList();

        try {
            
            modCountryCB.setItems(countryDao.getAllCounties());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        modCountryCB.setValue(selectedCountry);
 
        for (Division d : divisionDao.getAllDivisions()) {

            if (selectedCountry.getId() == d.getCountryID()) {
                divInSelectedCountry.add(d);
            }
        }
     
        modDivisionCB.setItems(divInSelectedCountry);
        modDivisionCB.setValue(selectedDivision);

        modCusIDTxt.setText(Integer.toString(customer.getCusID()));
        modNameTxt.setText(customer.getName());
        modAddressTxt.setText(customer.getAddress());
        modPostalTxt.setText(customer.getPostalCode());
        modPhoneTxt.setText(customer.getPhone());
    }
    
    /**
     * Cancel Button. Confirms with user and Returns customer to Customer screen 
     * @param actionEvent
     * @throws IOException 
     */
    @FXML
    public void cancelBtn(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Your text fields will not be not saved!");
        alert.setHeaderText("Do you wish to continue?");
        alert.setTitle("Cancel");

        Optional<ButtonType> result =  alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/Customer.fxml"));
            stage.setScene(new Scene(scene));
            stage.setResizable(false);
            stage.show();
        }
    }
    
    /**
     * Save Button. Validates fields and if none are empty, saves information to Database and returns user to Customer screen. 
     * @param actionEvent
     * @throws IOException
     * @throws SQLException 
     */
    @FXML
    public void saveBtn(ActionEvent actionEvent) throws IOException, SQLException {
       
        if (
                modNameTxt.getText().isEmpty()
                || modAddressTxt.getText().isEmpty()
                || modPostalTxt.getText().isEmpty()
                || modPhoneTxt.getText().isEmpty()
        ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Customer Error");
            alert.setHeaderText("One or more fields are empty.");
            alert.setContentText("Please complete all fields to add customer.");
            alert.showAndWait();
        }
        
        else {
            
            int id = Integer.parseInt(modCusIDTxt.getText());
            String name = String.valueOf(modNameTxt.getText());
            String address = String.valueOf(modAddressTxt.getText());
            String postalCode = String.valueOf(modPostalTxt.getText());
            String phone = String.valueOf(modPhoneTxt.getText());
            int divID = modDivisionCB.getValue().getDivID();

            
            customerDao.updateCustomer(id, name, address, postalCode, phone, divID);

           
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/Customer.fxml"));
            stage.setScene(new Scene(scene));
            stage.setResizable(false);
            stage.show();
        }
    }
    
    /**
     * Populating County Combo Box. 
     * @param actionEvent
     * @throws SQLException 
     */
    @FXML
    public void countryCB(ActionEvent actionEvent) throws SQLException {
        DivisionDAO divisionDao = new DivisionDAOQ();
        County selectedCountry = modCountryCB.getSelectionModel().getSelectedItem();

        ObservableList<Division> divsInCountry = divisionDao.getAllDivisions().filtered(division -> {
            if (division.getDivID() == selectedCountry.getId())
                return true;
            return false;
        });

        modDivisionCB.setItems(divsInCountry);
    }
    
}
