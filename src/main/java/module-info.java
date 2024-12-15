module edu.najah.library {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires java.naming;
    requires mysql.connector.java;
    requires java.persistence;

    opens edu.najah.library to javafx.fxml;
    opens edu.najah.library.models.user;
    exports edu.najah.library;
    exports edu.najah.library.controllers;
    opens edu.najah.library.controllers to javafx.fxml;
    exports edu.najah.library.utils;
    opens edu.najah.library.utils to javafx.fxml;
    exports edu.najah.library.models.interfaces;
    opens edu.najah.library.models.interfaces to javafx.fxml;
}