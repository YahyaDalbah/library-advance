package edu.najah.library.controllers;

import edu.najah.library.models.User;
import edu.najah.library.models.interfaces.UserDAO;
import edu.najah.library.models.services.UserDAOImp;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ManageProfileController {

    @FXML
    private TextField Name_manage;

    @FXML
    private TextField Email_manage;

    @FXML
    private TextField Password_manage;

    @FXML
    private TextField repeat_password_manage;

    @FXML
    private Button Save_manage;

    @FXML
    private Button update_image_manage;

    @FXML
    private ImageView Image_manage;

    @FXML
    private Button close_btn;

    private byte[] profileImageBytes;

    private User currentUser;

    private final UserDAO userDAO = new UserDAOImp();

    @FXML
    public void initialize() {
        // Load user data (Example user ID = 1)


        // Create a circular clip
        double radius = Math.min(Image_manage.getFitWidth(), Image_manage.getFitHeight()) / 2;
        Circle clip = new Circle(Image_manage.getFitWidth() / 2, Image_manage.getFitHeight() / 2, radius);
        Image_manage.setClip(clip);

// Make sure the image fits within the clip
        Image_manage.setPreserveRatio(true);
        Image_manage.setFitWidth(228); // Adjust to match the size of the Circle's diameter
        Image_manage.setFitHeight(228); // Same as above
    }

    private void loadUserData(int userId) {
        currentUser = userDAO.getUserById(userId);
        if (currentUser != null) {
            Name_manage.setText(currentUser.getName());
            Email_manage.setText(currentUser.getEmail());
            Password_manage.setText(currentUser.getPassword());
            repeat_password_manage.setText(currentUser.getPassword());
        }
    }
    @FXML
    private void handleSave() {
        if (!Password_manage.getText().equals(repeat_password_manage.getText())) {
            showAlert("Error", "Password does not match!", Alert.AlertType.ERROR);
            return;
        }

        if (currentUser != null) {
            currentUser.setName(Name_manage.getText());
            currentUser.setEmail(Email_manage.getText());
            currentUser.setPassword(Password_manage.getText());
            currentUser.setRepeat_password(repeat_password_manage.getText());

            if (profileImageBytes != null) {
                currentUser.setResetToken(new String(profileImageBytes));
            }

            userDAO.updateUser(currentUser);

            showAlert("Success", "Profile updated successfully!", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void handleUpdateImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Image");
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {
                profileImageBytes = file.getAbsolutePath().getBytes();
                Image_manage.setImage(new Image(new FileInputStream(file)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleClose() {
        // Close the current window
        Save_manage.getScene().getWindow().hide();
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
