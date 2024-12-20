package edu.najah.library.controllers;

import edu.najah.library.utils.LoggedInUser;
import edu.najah.library.utils.Permissions;
import edu.najah.library.utils.Role;
import edu.najah.library.utils.utilFunctions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AllBooksPageController implements Initializable {

    @FXML
    private void navigateToSearch(MouseEvent event) {
        handleSearchButtonClick(event, "searchPage.fxml");
    }
    @FXML
    private void navigateToBookDetails(MouseEvent event) {
        handleSearchButtonClick(event, "BookDetailsPage.fxml");
    }
    @FXML
    private void navigateToLogin(MouseEvent event) {
        handleSearchButtonClick(event, "login.fxml");
    }

    @FXML
    private Button goToDashboardBtn;

    public void handleSearchButtonClick(MouseEvent event, String fxmlPath) {
        try {
            // Load the FXML for the search page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/najah/library/"+ fxmlPath));
            Parent searchPage = loader.load();

            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the scene for the new page
            Scene scene = new Scene(searchPage);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void goToDashboard(ActionEvent event) throws IOException {
        utilFunctions.switchScene(event, "dashboard-view.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        goToDashboardBtn.setVisible(Permissions.canViewDashboard());
    }
}
