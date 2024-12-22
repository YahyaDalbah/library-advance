package edu.najah.library.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.sql.Date;

public class BookDetailsPageController {

    @FXML private Text bookTitle;
    @FXML private Text bookAuthor;
    @FXML private Text bookType;
    @FXML private Text bookYear;
    @FXML private Text bookDescription;
    @FXML private Text bookRating;
    @FXML private ImageView bookImage;

    public void setBookDetails(int bookId, String title, String author, byte[] imageBytes, double rating, String description, String type, Date year) {
         bookTitle.setText(title);
        bookAuthor.setText(author);
        bookType.setText(type);
        bookYear.setText(String.valueOf(year));
        bookDescription.setText(description);
        bookRating.setText(String.format("%.1f/5", rating));

        // Set the book image (if available)
        if (imageBytes != null) {
            Image image = new Image(new ByteArrayInputStream(imageBytes));
            bookImage.setImage(image);
        }
    }

    // Method to handle the back button action
    public void handleBackButtonAction(ActionEvent event) {
        try {
            // Load the AllBooksPage FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/najah/library/AllbooksPage.fxml"));
            AnchorPane allBooksPage = loader.load();

            // Set the scene for the AllBooksPage
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(allBooksPage);
            stage.setScene(scene);

            // Optionally, set the title of the stage
            stage.setTitle("All Books");

            // Show the new scene (AllBooksPage)
            stage.show();
        } catch (Exception e) {
            // Handle any loading errors
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occurred while loading the AllBooksPage.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}

