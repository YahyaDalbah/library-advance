package edu.najah.library.utils;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class utilFunctions {
    public static void switchScene(Event event, String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(utilFunctions.class.getResource("/edu/najah/library/" + fxml));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
