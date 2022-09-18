module com.example.btl_java {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.btl_java to javafx.fxml;
    exports com.example.btl_java;
    exports com.example.btl_java.controllers;
    opens com.example.btl_java.controllers to javafx.fxml;
}