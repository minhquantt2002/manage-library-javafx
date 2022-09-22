package btl_java.manage_library;

import btl_java.manage_library.controllers.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;



public class MainApplication extends Application {
    public static Stage primaryStage;
    @Override
    public void start(Stage stage) throws Exception{
        primaryStage = stage;
        new LoginController().launchLogin(stage);
    }
    public static void main(String[] args) {
        launch();
    }
}