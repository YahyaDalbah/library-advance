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
    requires java.desktop;

    exports edu.najah.library;
    exports edu.najah.library.controllers;
    opens edu.najah.library to javafx.fxml;
    opens edu.najah.library.controllers to javafx.fxml;
    exports edu.najah.library.models.user;
    //opens edu.najah.library.models.user to javafx.fxml;
   // opens edu.najah.library.models.user to org.hibernate.orm.core;
    opens edu.najah.library.models.user to javafx.fxml, org.hibernate.orm.core;





}