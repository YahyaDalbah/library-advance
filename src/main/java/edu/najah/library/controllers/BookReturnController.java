package edu.najah.library.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class BookReturnController {

    @FXML
    private void handelBack(MouseEvent event) {
        loadFullScene(event, "dashboard-view.fxml", "Dashboard");
    }

    private void loadPopupScene(MouseEvent event, String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/najah/library/" + fxmlFile));
            Parent root = loader.load();

            Stage popupStage = new Stage();
            popupStage.setTitle(title);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initOwner(((Node) event.getSource()).getScene().getWindow());


            Scene scene = new Scene(root);
            popupStage.setScene(scene);

            popupStage.showAndWait();
        } catch (IOException e) {
            showErrorAlert("Error", "Failed to load the scene: " + fxmlFile);
            e.printStackTrace();
        }
    }

    private void loadFullScene(MouseEvent event, String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/najah/library/" + fxmlFile));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showErrorAlert("Error", "Failed to load the scene: " + fxmlFile);
            e.printStackTrace();
        }
    }

    private void showErrorAlert(String header, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }



   /* this is a function to make the extent/return buttons appear in the table
        when there is data in the table from the database

    @FXML
    public void initialize() {

        actionsColumn.setCellFactory(column -> new TableCell<>() {
            private final Button extendButton = new Button("Extend Reservation for 2 weeks");
            private final Button returnButton = new Button("Return");

            {

                extendButton.setOnAction(event -> {
                    //logic
                });

                returnButton.setOnAction(event -> {
                    //logic here
                });
            }*/


}
