/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.User;
import javafx.scene.control.*;
import Utility.UserDAO;
import Utility.UserDAOQ;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Optional;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class. Log in Controller. Translates text from english to French when System Language is set to French. 
 * Validates User input, navigates user to Appointments screen when validated. 
 *
 * @author Koala
 */
public class LoginController implements Initializable {

    @FXML
    private Button loginBtn;
    @FXML
    private Button exit;
    @FXML
    private Label mainLabel;
    @FXML
    private TextField usernameTxt;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    private Label locationTxt;
    @FXML
    private Label locationLabel;
    @FXML
    private Label error;
    @FXML
    private Label loginLabel;
     @FXML
    private Label errorLabel;
    
     UserDAO userDao = new UserDAOQ();

    Stage stage;
    Parent scene;
    /** Check User. Validates users log in. 
     * 
    */ 
    
     private Boolean checkUser(String username, String password) throws SQLException {
        Boolean userLogin = false;

        ObservableList<User> allUsers = userDao.getAllUsers();

        for (User u : allUsers){
            if (u.getUsername().equals(username) && u.getPassword().equals(password)){
                userLogin = true;
            }
        }
        return userLogin;
    };
     /**
      * Login Button. When clicked, Validates user. If validated, navigates to appointment page. 
      * If fails, logs in login_activity.txt. 
      * @param event
      * @throws IOException
      * @throws SQLException 
      */
     @FXML
    void loginBtn(ActionEvent event) throws IOException, SQLException {
        ResourceBundle rb = ResourceBundle.getBundle("Properties/Language", Locale.getDefault());
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();

        String filename = "login_activity.txt";
        FileWriter fileWriter = new FileWriter(filename, true);
        PrintWriter outputFile = new PrintWriter(fileWriter);

        if (checkUser(username, password)){
            
            String loginActivity = "Successful Login Attempt: " + username + " Date/Time: " + LocalDateTime.now();
            outputFile.println(loginActivity);
            outputFile.close();

      
            Stage stage;
            Parent root;
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Appointment.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
            stage.setResizable(false);
            AppointmentController controller = loader.getController();
            controller.appointmentWithinFifteenMinutes(username);
        }
        else {

            String loginActivity = "Unsuccessful Login Attempt: " + username + " Date/Time: " + LocalDateTime.now();
            outputFile.println(loginActivity);
            outputFile.close();

            if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")){
                error.setText(rb.getString("Error"));
                //locationLabel.setText(rb.getString("Location"));
                
            }
        }

    }
    
    /**
     *Exit button. Will close program upon approval. 
     * @param event
     */
    @FXML
    public void exitBtn (ActionEvent event) {
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Do you wish to exit the program?");
        alert.setTitle("Exit Program");

        Optional<ButtonType> result =  alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            System.exit(0);
        }
        
    }

    /**
     * Initializes the controller class. Gets Systems Local Location and translates login text when users system is French. 
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String string = ZoneId.systemDefault().toString();
        locationLabel.setText(string);
        
        ResourceBundle rb1 = ResourceBundle.getBundle("Properties/Language", Locale.getDefault());
        
        if(Locale.getDefault().getLanguage().equals("fr")) {
            usernameTxt.setPromptText(rb1.getString("Login"));
            passwordTxt.setPromptText(rb1.getString("Password"));
            locationTxt.setText(rb1.getString("LocationTxt"));
            errorLabel.setText(rb1.getString("ErrorLabel")); 
            mainLabel.setText(rb1.getString("MainLabel")); 
            loginLabel.setText(rb1.getString("Login")); 
            loginBtn.setText(rb1.getString("Login")); 
            exit.setText(rb1.getString("Exit")); 
        }
    }    


}
