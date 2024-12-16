package edu.najah.library.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardViewController {

    @FXML
    private void handleAddNewBook(MouseEvent event) {
        loadPopupScene(event, "add-book.fxml", "Add New Book");
    }

    @FXML
    private void handleBookStatusManagement(MouseEvent event) {
        loadPopupScene(event, "Book-status-management.fxml", "Book Status Management");
    }

    @FXML
    private void handleBookReservation(MouseEvent event) {
        loadPopupScene(event, "Reservation-Book.fxml", "Book Reservation");
    }
    @FXML
    private void handleLogout(MouseEvent event) {
        loadFullScene(event, "login.fxml", "Login");
    }

    @FXML
    private void handleSearchPage(MouseEvent event) {
        loadFullScene(event, "AllbooksPage.fxml", "Library7");
    }

    @FXML
    private void handleBookReturn(MouseEvent event) {
        loadFullScene(event, "return-book.fxml", "Book Return");
    }

    private void loadPopupScene(MouseEvent event, String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/najah/library/" + fxmlFile));
            Parent root = loader.load();

            Stage popupStage = new Stage();
            popupStage.setTitle(title);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initOwner(((Node) event.getSource()).getScene().getWindow());


            Scene scene = new Scene(root);
            popupStage.setScene(scene);

            popupStage.showAndWait();
        } catch (IOException e) {
            showErrorAlert("Error", "Failed to load the scene: " + fxmlFile);
            e.printStackTrace();
        }
    }

    private void loadFullScene(MouseEvent event, String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/najah/library/" + fxmlFile));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showErrorAlert("Error", "Failed to load the scene: " + fxmlFile);
            e.printStackTrace();
        }
    }

    private void showErrorAlert(String header, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
