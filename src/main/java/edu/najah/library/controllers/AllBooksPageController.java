package edu.najah.library.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AllBooksPageController {

    @FXML
    private TilePane BooksTilePane;

    public void initialize() {
        loadBooks();
    }


    private void loadBooks() {
        ObservableList<VBox> books = FXCollections.observableArrayList();

        try {
            // Establishing connection to the database
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/booklibrary", "root", "");
            String query = "SELECT * FROM Book";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                VBox bookVBox = new VBox(10);
                bookVBox.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-border-color: white; -fx-border-width: 1px; -fx-border-radius: 5px;");

                // Get the binary data from the database
                byte[] imageBytes = resultSet.getBytes("image");  // Get the image as a byte array

                // Check if imageBytes is not null
                if (imageBytes != null) {
                    Image image = new Image(new ByteArrayInputStream(imageBytes));
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(200);  // Adjust image size
                    imageView.setPreserveRatio(true);
                    bookVBox.getChildren().add(imageView);
                } else {
                    // Handle missing image or provide a fallback
                    Label imageErrorLabel = new Label("Image not found");
                    bookVBox.getChildren().add(imageErrorLabel);
                }

                // Book Title
                Label titleLabel = new Label(resultSet.getString("title"));
                titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
                bookVBox.getChildren().add(titleLabel);

                // Book Author
                Label authorLabel = new Label(resultSet.getString("author"));
                authorLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");
                bookVBox.getChildren().add(authorLabel);

                // Book Rating
                Label ratingLabel = new Label("Rating: " + resultSet.getDouble("rating") + "/5");
                ratingLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: darkorange;");
                bookVBox.getChildren().add(ratingLabel);

                books.add(bookVBox);
            }

            BooksTilePane.getChildren().setAll(books);

            // Close the resources
            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
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
}
