package edu.najah.library.controllers;

import edu.najah.library.models.services.LibrarianDAOImp;
import edu.najah.library.models.user.Librarian;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

import static edu.najah.library.utils.utilFunctions.switchScene;

public class LoginController {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorMessage;
    public void login(ActionEvent event) throws IOException {
        String email = emailField.getText();
        String password = passwordField.getText();
        LibrarianDAOImp librarianDAO = new LibrarianDAOImp();
        List<Librarian> librarians = librarianDAO.getAllLibrarians();
        Librarian l = librarians.stream()
                .filter(librarian -> librarian.getEmail().equals(email) && librarian.getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if(l != null) {
            errorMessage.setVisible(false);
            switchScene(event,"dashboard-view.fxml");
        }
        else {
            errorMessage.setVisible(true);
        }

    }
    public void loginAsStudent(MouseEvent event) throws IOException {
        switchScene(event,"AllbooksPage.fxml");
    }



}
