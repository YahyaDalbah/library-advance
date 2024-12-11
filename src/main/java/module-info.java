module edu.najah.library {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;

    opens edu.najah.library to javafx.fxml;
    exports edu.najah.library;
    exports edu.najah.library.controllers;
    opens edu.najah.library.controllers to javafx.fxml;
}