package edu.najah.library.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public class UpdateStudentController {
    @FXML
    private Button closeButton;

    @FXML
    private void handleCloseButtonAction() {
        Stage currentStage = (Stage) closeButton.getScene().getWindow();
        currentStage.close();
    }
}
