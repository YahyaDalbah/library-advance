package edu.najah.library.controllers;

import edu.najah.library.models.services.UserDAOImp;
import edu.najah.library.models.user.User;
import edu.najah.library.utils.Register;
import edu.najah.library.utils.Roles;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static edu.najah.library.utils.utilFunctions.switchScene;

public class LoginController {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorMessage;
    @FXML
    private Label resetPasswordLabel;


    public void login(ActionEvent event) throws IOException {
        String email = emailField.getText();
        String password = passwordField.getText();
        UserDAOImp userDAOImp = new UserDAOImp();
        List<User> librarians = userDAOImp.getAllByRole(Roles.librarian);
        List<User> admins = userDAOImp.getAllByRole(Roles.admin);

        User matchedUser = Stream.concat(librarians.stream(), admins.stream())
                .filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if(matchedUser != null && matchedUser.getRole().hasPermission("canViewDashboard")) {
            errorMessage.setVisible(false);
            Register register = Register.getInstance();
            register.setCurrentUser(matchedUser);
            switchScene(event,"dashboard-view.fxml");
        } else {
            errorMessage.setVisible(true);
        }

    }
    public void loginAsStudent(MouseEvent event) throws IOException {
        switchScene(event,"AllbooksPage.fxml");
    }

    public void goToResetPassword(MouseEvent event) throws IOException {
        switchScene(event,"reset-password-form.fxml");
    }

}
