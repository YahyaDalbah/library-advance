package edu.najah.library.controllers;
import edu.najah.library.models.services.BookDAOImp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import java.io.IOException;

public class AllBooksPageController {

    @FXML
    public TilePane BooksTilePane;

    public void initialize() {
        //new BookDAOImp().loadBooks(this);
    }


    @FXML
    private void navigateToSearch(MouseEvent event) {
        handleSearchButtonClick(event, "searchPage.fxml");
    }

    @FXML
    public void navigateToBookDetails(MouseEvent event, int bookId, String title, String author, byte[] imageBytes, double rating, String description, String type, int year) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/najah/library/BookDetailsPage.fxml"));
        Parent bookDetailsPage = loader.load();

        // Get the controller of the Book Details Page
        BookDetailsPageController controller = loader.getController();

        // Pass the book details to the controller
        controller.setBookDetails(bookId, title, author, imageBytes, rating, description, type, year);

        // Navigate to the Book Details Page
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(bookDetailsPage));
        stage.show();
    }


    @FXML
    private void navigateToLogin(MouseEvent event) {
        handleSearchButtonClick(event, "login.fxml");
    }

    public void handleSearchButtonClick(MouseEvent event, String fxmlPath) {
        try {
            // Load the FXML for the search page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/najah/library/" + fxmlPath));
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
