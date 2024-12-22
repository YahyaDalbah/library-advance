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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class SearchPageController {
    @FXML
    private TextField searchField;


    @FXML
    private VBox searchResultsVBox;


    @FXML
    private Label errorLabel;


    @FXML
    public void handleSearch(ActionEvent event) {
        String query = searchField.getText();  // Get the search query from the TextField

        // Clear previous error messages
        errorLabel.setText("");

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

    private void displaySearchResults(List<Book> books) {
        // Clear any previous search results
        searchResultsVBox.getChildren().clear();

        // Check if no books are found
        if (books == null || books.isEmpty()) {
            errorLabel.setText("No books found matching your search.");
            errorLabel.setStyle("-fx-text-fill: red;");  // Optional: Set the error label color to red
            System.out.println("No books found for the query.");  // Debug statement
            return;
        }

        // Loop through the books and create entries for each
        for (Book book : books) {
            createBookEntry(book);
        }
    }





    private void createBookEntry(Book book) {
        // HBox for the book entry
        HBox bookEntry = new HBox();
        bookEntry.setSpacing(10);
        bookEntry.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-border-radius: 10; -fx-padding: 10; -fx-background-color: #F5F5F5;");
        bookEntry.setMaxWidth(780.0);


        // ImageView for the book's image
        ImageView imageView = new ImageView();
        imageView.setFitHeight(79.0);
        imageView.setFitWidth(59.0);
        imageView.setPreserveRatio(true);


        // Retrieve the image URL from the book object
        String imageUrl = book.getImageUrl();


        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                // Check if the image URL is a local file path
                File imageFile = new File(imageUrl);
                if (imageFile.exists()) {
                    // Convert the file to an Image and set it on the ImageView
                    Image image = new Image(imageFile.toURI().toString());
                    imageView.setImage(image); // Set the valid image


                    // Log that the image is set
                    System.out.println("Image set for book: " + book.getTitle());
                } else {
                    System.out.println("Image file does not exist: " + imageUrl);
                }
            } catch (Exception e) {
                System.out.println("Error loading image from URL for book: " + book.getTitle());
                e.printStackTrace();
            }
        } else {
            System.out.println("No image URL available for book: " + book.getTitle());
        }


        // VBox for book details
        VBox bookDetails = new VBox();
        bookDetails.setSpacing(5);


        // Title
        javafx.scene.text.Text titleText = new javafx.scene.text.Text(book.getTitle());
        titleText.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        bookDetails.getChildren().add(titleText);


        // HBox for author, rating, and year
        HBox authorRatingYearHBox = new HBox();
        authorRatingYearHBox.setAlignment(javafx.geometry.Pos.CENTER);
        authorRatingYearHBox.setSpacing(40);


        javafx.scene.text.Text authorText = new javafx.scene.text.Text(book.getAuthor());
        authorText.setStyle("-fx-font-size: 14px;");


        javafx.scene.text.Text ratingText = new javafx.scene.text.Text("Rating: " + book.getRating());
        ratingText.setStyle("-fx-font-size: 14px;");


        authorRatingYearHBox.getChildren().addAll(authorText, ratingText);


        // Add all details to the book details VBox
        bookDetails.getChildren().add(authorRatingYearHBox);


        // Add ImageView and details VBox to the main HBox
        bookEntry.getChildren().addAll(imageView, bookDetails);


        // Add the book entry to the search results VBox
        searchResultsVBox.getChildren().add(bookEntry);


        System.out.println("Added book entry for: " + book.getTitle()); // Debug statement
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
