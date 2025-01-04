package edu.najah.library.controllers;

import edu.najah.library.models.User;
import edu.najah.library.models.interfaces.UserDAO;
import edu.najah.library.models.services.UserDAOImp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateStudentController {
    @FXML
    private Button closeButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;

    private User student;

    private StudentDetailsController studentDetailsController;
    public void setStudentDetailsController(StudentDetailsController studentDetailsController) {
        this.studentDetailsController = studentDetailsController;
    }

    @FXML
    private void handleCloseButtonAction() {
        Stage currentStage = (Stage) closeButton.getScene().getWindow();
        currentStage.close();
    }

    public void setStudent(User student) {
        this.student = student;
        loadStudentData();
    }

    private void loadStudentData() {
        if (student != null) {
            nameField.setText(student.getName());
            emailField.setText(student.getEmail());
        }
    }
    @FXML
    private void handleUpdateButtonAction() {
        String newName = nameField.getText().trim();
        String newEmail = emailField.getText().trim();

        if (newName.isEmpty() || newEmail.isEmpty()) {
            showAlert("Invalid Input", "Name and email cannot be empty.", Alert.AlertType.ERROR);
            return;
        }

        student.setName(newName);
        student.setEmail(newEmail);

        try {
            UserDAO userDAO = new UserDAOImp();
            userDAO.updateUser(student);
            showAlert("Success", "Student details updated successfully.", Alert.AlertType.INFORMATION);
            studentDetailsController.updateStudentDetails(student);
            closeButton.getScene().getWindow().hide();
        } catch (Exception e) {
            showAlert("Error", "An error occurred while updating the student details.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancelButtonAction() {
        nameField.setText(student.getName());
        emailField.setText(student.getEmail());
    }


    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

