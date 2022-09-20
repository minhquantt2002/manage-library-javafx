package btl_java.manage_library.controllers;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
public class HomeController {
    private Stage stage;
    private Scene scene;


    public HomeController (){
        // TODO: Load fxml cho giao diện của home
    }

    public void displayHome(Stage stage){
        this.stage = stage;
        stage.setScene(scene);
        stage.show();
    }
}