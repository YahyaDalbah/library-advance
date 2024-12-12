package edu.najah.library.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddBookController {

    @FXML
    private RadioButton availableRadio;
    @FXML
    private RadioButton unavailableRadio;
    private ToggleGroup availabilityGroup;
    @FXML
    private Button closeButton;

    @FXML
    public void initialize() {
        closeButton.setOnAction(event -> {
            Stage currentStage = (Stage) closeButton.getScene().getWindow();
            currentStage.close();
        });

        availabilityGroup = new ToggleGroup();
        availableRadio.setToggleGroup(availabilityGroup);
        unavailableRadio.setToggleGroup(availabilityGroup);
    }

    public void openAddBookModal() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/najah/library/add-book.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Add Book");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
}
