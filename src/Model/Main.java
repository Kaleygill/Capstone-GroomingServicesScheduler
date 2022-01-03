package Model;

import Utility.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    static Stage stage;

    public static void main(String[] args) {
        DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();
    }

    /**
     * Start Method. Launch the Login Main screen when application is open.
     *
     * @param stage
     */
    @Override
    public void start(Stage stage) {

        Main.stage = stage;

        //Locale.setDefault(new Locale("fr"));
        //ResourceBundle rb = ResourceBundle.getBundle("Properties/Launguage");


        Parent main = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Login.fxml"));
            main = loader.load();

            Scene scene = new Scene(main);
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
