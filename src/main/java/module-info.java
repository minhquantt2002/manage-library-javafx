module com.example.btl_java {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens images;
    opens com.example.btl_java.controllers to javafx.fxml;
    exports com.example.btl_java;
    exports com.example.btl_java.controllers;
}