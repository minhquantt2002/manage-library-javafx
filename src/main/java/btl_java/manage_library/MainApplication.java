package btl_java.manage_library;

import btl_java.manage_library.controllers.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception{
//        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login-app.fxml"));
//        System.out.println(fxmlLoader);
//        Scene scene = new Scene(fxmlLoader.load(), 700, 700);
//        stage.setScene(scene);
//        stage.show();
        new LoginController().launchLogin(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}