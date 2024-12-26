package edu.najah.library.controllers;

import edu.najah.library.models.services.UserDAOImp;
import edu.najah.library.models.user.User;
import edu.najah.library.utils.EmailService;
import edu.najah.library.utils.Register;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

import static edu.najah.library.utils.utilFunctions.switchScene;
import static edu.najah.library.utils.utilFunctions.generateResetToken;
public class ResetPasswordController {
    @FXML
    private TextField resetPasswordEmailField;
    @FXML
    private TextField tokenField;
    @FXML
    private Label errorMessageConfirmToken;
    @FXML
    private Label errorMessageChangePasswordForm;
    @FXML
    private TextField newPasswordField;
    @FXML
    private TextField confirmNewPasswordField;
    @FXML
    private Label errorMessageResetPasswordEmail;

    public void sendEmail(ActionEvent event) throws IOException {
        String email = resetPasswordEmailField.getText();
        UserDAOImp userDAO = new UserDAOImp();
        User user = userDAO.getUserByEmail(email);
        if(user != null){
            String token = generateResetToken(email);
            User updatedUser = userDAO.getUserByEmail(email);
            Register.getInstance().setCurrentUser(updatedUser);
            EmailService.sendEmail(email, "reset password", token);
            switchScene(event, "confirm-token.fxml");
        }else{
            errorMessageResetPasswordEmail.setVisible(true);
        }
    }
    public void confirmToken(ActionEvent event) throws IOException {
        errorMessageConfirmToken.setVisible(false);
        String token = tokenField.getText();
        String userToken = Register.getInstance().getCurrentUser().getResetToken();
        System.out.println(token);
        System.out.println(userToken);
        if (token.equals(userToken)) {
            switchScene(event, "change-password.fxml");
        }else{
            errorMessageConfirmToken.setVisible(true);
        }
    }
    public void resetPassword(ActionEvent event) throws IOException {
        String newPassword = newPasswordField.getText();
        String confirmedNewPassword = confirmNewPasswordField.getText();
        User user = Register.getInstance().getCurrentUser();
        if(newPassword.isEmpty()){
            errorMessageChangePasswordForm.setText("Passwords are empty");
            errorMessageChangePasswordForm.setVisible(true);
        }
        else if(newPassword.equals(confirmedNewPassword)) {
            user.setPassword(newPassword);
            new UserDAOImp().updateUser(user);
            Register.getInstance().clearUser();
            switchScene(event, "login.fxml");

        }else {
            errorMessageChangePasswordForm.setText("Passwords do not match");
            errorMessageChangePasswordForm.setVisible(true);
        }
    }
    public void goBackToLoginPage(MouseEvent event) throws IOException {
        switchScene(event, "login.fxml");
    }
}
