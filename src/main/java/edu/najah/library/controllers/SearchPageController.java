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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
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
                File imageFile = new File(imageUrl);
                if (imageFile.exists()) {
                    Image image = new Image(imageFile.toURI().toString());
                    imageView.setImage(image); // Set the valid image
                } else {
                    System.out.println("Image file does not exist: " + imageUrl);
                }
            } catch (Exception e) {
                System.out.println("Error loading image for book: " + book.getTitle());
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

        // HBox for author and rating
        HBox authorRatingHBox = new HBox();
        authorRatingHBox.setAlignment(javafx.geometry.Pos.CENTER);
        authorRatingHBox.setSpacing(40);

        javafx.scene.text.Text authorText = new javafx.scene.text.Text(book.getAuthor());
        authorText.setStyle("-fx-font-size: 14px;");

        javafx.scene.text.Text ratingText = new javafx.scene.text.Text("Rating: " + book.getRating());
        ratingText.setStyle("-fx-font-size: 14px;");

        authorRatingHBox.getChildren().addAll(authorText, ratingText);
        bookDetails.getChildren().add(authorRatingHBox);

        // Add ImageView and details VBox to the main HBox
        bookEntry.getChildren().addAll(imageView, bookDetails);

        // Add event handler to navigate to book details page on click
        bookEntry.setOnMouseClicked(event -> {
            try {
                navigateToBookDetails(
                        event,
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getImage(),
                        book.getRating(),
                        book.getDescription(),
                        book.getType(),
                        book.getYear()
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Add the book entry to the search results VBox
        searchResultsVBox.getChildren().add(bookEntry);
    }

    private void navigateToBookDetails(MouseEvent event, int id, String title, String author, byte[] image, String rating, String description, String type, int year) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/najah/library/BookDetailsPage.fxml"));
            Parent bookDetailsPage = loader.load();

            // Get the controller of the Book Details Page
            BookDetailsPageController controller = loader.getController();

            // Pass the book details to the controller
            controller.setBookDetails( id, title, author, image, Double.parseDouble(rating), description, type, year);

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