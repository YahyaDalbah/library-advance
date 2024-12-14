package edu.najah.library;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    public void login(ActionEvent event) throws IOException {
        goToHomeScene(event,"dashboard-view.fxml");
    }
    public void loginAsStudent(MouseEvent event) throws IOException {
        goToHomeScene(event,"dashboard-view.fxml");
    }

    private void goToHomeScene(Event event, String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
