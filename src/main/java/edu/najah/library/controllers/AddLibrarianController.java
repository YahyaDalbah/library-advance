package edu.najah.library.controllers;

import edu.najah.library.models.Role;
import edu.najah.library.models.User;
import edu.najah.library.models.services.UserDAOImp;
import edu.najah.library.utils.Roles;
import edu.najah.library.utils.UtilFunctions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.ResourceBundle;

public class AddLibrarianController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label message;

    public void addLibrarian(ActionEvent actionEvent) {
        String email = emailField.getText();
        String name = nameField.getText();
        String password = passwordField.getText();
        if(UtilFunctions.isValidEmail(email) && !name.isEmpty() && !password.isEmpty()){
            UserDAOImp userDAOImp = new UserDAOImp();
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            Role role = new Role();
            role.setRole(Roles.librarian.name());
            user.setRole(role);
            userDAOImp.save(user);
            message.setText("Success");
            message.setTextFill(Paint.valueOf("green"));
            nameField.clear();
            emailField.clear();
            passwordField.clear();
        }else{
            if(!UtilFunctions.isValidEmail(email)){
                message.setText("must be a valid email");
            }else if(name.isEmpty()){
                message.setText("name is empty");
            }else if(password.isEmpty()){
                message.setText("password is empty");
            }else{
                message.setText("fail");
            }
            message.setTextFill(Paint.valueOf("red"));
        }

    }
}
