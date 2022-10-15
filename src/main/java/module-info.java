module btl_java.manage_library {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.java;

    opens images;
    opens fxml to javafx.fxml;

    opens btl_java.manage_library.controllers to javafx.fxml;

    exports btl_java.manage_library;
    exports btl_java.manage_library.controllers;
}