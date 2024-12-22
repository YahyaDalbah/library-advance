package edu.najah.library.controllers;

import edu.najah.library.models.user.Book;
import edu.najah.library.models.services.BookDAOImp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class SearchPageController {
    @FXML
    private TextField searchField;

    @FXML
    private VBox searchResultsVBox; // VBox to display search results

    @FXML
    private Label errorLabel; // Label to display error if no books are found

    // This method will be triggered when the search button is clicked
    @FXML
    public void handleSearch(ActionEvent event) {
        String query = searchField.getText();  // Get the search query from the TextField

        if (query.isEmpty()) {
            errorLabel.setText("Please enter a search term.");
            return;
        }

        System.out.println("Search query: " + query);  // Debug statement for query

        BookDAOImp bookDAO = new BookDAOImp();
        List<Book> books = bookDAO.searchBooks(query);

        System.out.println("Number of books found: " + (books != null ? books.size() : 0));  // Debug statement for results

        displaySearchResults(books);
    }

    // Method to display the search results
    private void displaySearchResults(List<Book> books) {
        // Clear any previous search results
        searchResultsVBox.getChildren().clear();

        // Check if no books are found
        if (books == null || books.isEmpty()) {
            errorLabel.setText("No books found.");
            System.out.println("No books found for the query.");  // Debug statement
            return;
        }

        // Loop through the books and create entries for each
        for (Book book : books) {
            createBookEntry(book);
        }
    }

    // Method to create a book entry UI for each result
    private void createBookEntry(Book book) {
        VBox bookEntry = new VBox();
        bookEntry.setSpacing(5);  // Adding some spacing between elements

        // Title
        Label titleLabel = new Label("Title: " + book.getTitle());
        bookEntry.getChildren().add(titleLabel);

        // Author
        Label authorLabel = new Label("Author: " + book.getAuthor());
        bookEntry.getChildren().add(authorLabel);

        // Description
        Label descriptionLabel = new Label("Description: " + book.getDescription());
        bookEntry.getChildren().add(descriptionLabel);

        // Image (if available)
        String imageUrl = book.getImageUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(new javafx.scene.image.Image(imageUrl));
            imageView.setFitHeight(150);
            imageView.setFitWidth(100);
            bookEntry.getChildren().add(imageView);
        } else {
            Label noImageLabel = new Label("No image available");
            bookEntry.getChildren().add(noImageLabel);
        }

        // Add the book entry to the VBox
        searchResultsVBox.getChildren().add(bookEntry);
        System.out.println("Added book: " + book.getTitle());  // Debug statement for added book
    }


    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/najah/library/AllbooksPage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
