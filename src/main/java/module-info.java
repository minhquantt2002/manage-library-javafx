module btl_java.manage_library {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    opens images;
    opens btl_java.manage_library to javafx.fxml;
    exports btl_java.manage_library;
    exports btl_java.manage_library.controllers;
    opens btl_java.manage_library.controllers to javafx.fxml;
}