package edu.najah.library.controllers;

import edu.najah.library.models.Role;
import edu.najah.library.models.User;
import edu.najah.library.models.services.UserDAOImp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddStudentController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField idField;

    @FXML
    private Button cancelButton;

    @FXML
    private Button addButton;

    @FXML
    private Text closeButton;

    private UserDAOImp userDAO;

    @FXML
    public void initialize() {
        userDAO = new UserDAOImp();

        // Set up close button action
        closeButton.setOnMouseClicked(event -> closeWindow());

        // Set up cancel button action
        cancelButton.setOnAction(event -> closeWindow());

        // Set up add button action
        addButton.setOnAction(event -> handleAddStudent());

        // Make ID field non-editable (as specified in FXML)
        idField.setEditable(false);
    }

    private void handleAddStudent() {
        // Validate input fields
        if (!validateInputs()) {
            return;
        }

        try {
            // Check if email already exists
            if (userDAO.getUserByEmail(emailField.getText()) != null) {
                showError("Email already exists!");
                return;
            }

            // Create new user object
            User student = new User();
            student.setName(nameField.getText().trim());
            student.setEmail(emailField.getText().trim());
            student.setPassword(passwordField.getText());

            // Set role as student
            Role studentRole = new Role();
            studentRole.setRole("student"); // Using "student" as the role name
            student.setRole(studentRole);

            // Save to database
            userDAO.save(student);

            // Show success message
            showSuccess("Student added successfully!");

            // Close the window
            closeWindow();

        } catch (Exception e) {
            showError("Error adding student: " + e.getMessage());
        }
    }

    private boolean validateInputs() {
        // Validate name
        if (nameField.getText().trim().isEmpty()) {
            showError("Please enter student name");
            return false;
        }

        // Validate email

        if (emailField.getText().trim().isEmpty()) {
            showError("Please enter a valid email address");
            return false;
        }

        // Validate password
        if (passwordField.getText().isEmpty()) {
            showError("Please enter a password");
            return false;
        }

        return true;
    }

    private void closeWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private void showError(String message) {
        // Implement error dialog/alert here
        // You can use JavaFX Alert or custom dialog
        System.out.println("Error: " + message);
    }

    private void showSuccess(String message) {
        // Implement success dialog/alert here
        // You can use JavaFX Alert or custom dialog
        System.out.println("Success: " + message);
    }
}