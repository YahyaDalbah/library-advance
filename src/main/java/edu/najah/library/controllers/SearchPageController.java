package edu.najah.library.controllers;

import edu.najah.library.models.Book;
import edu.najah.library.models.services.BookDAOImp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class SearchPageController {

    @FXML
    public Button back;

    @FXML
    public Button currentPageButton;

    @FXML
    public Button next;
    @FXML
    private TextField searchField;

    @FXML
    private VBox searchResultsVBox;

    @FXML
    private Label errorLabel;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private Button anyButton;

    @FXML
    private Button titleButton;

    @FXML
    private Button authorButton;

    private BookDAOImp bookDAO;

    private String searchBy = "Any"; // Default search category is "Any"

    private int currentPage = 1;
    private static final int BOOKS_PER_PAGE = 6;
    private List<Book> allBooks;


    @FXML
    public void initialize() {
        bookDAO = new BookDAOImp();

        // Initialize ComboBox
        typeComboBox.getItems().addAll("Fiction", "Non-Fiction", "Science", "History");
        typeComboBox.setValue("Any");

        // Set default filter to "Any"
        anyButton.setStyle("-fx-background-color: #A9A9A9;");

        // Set button actions
        anyButton.setOnAction(event -> setSearchFilter("Any"));
        titleButton.setOnAction(event -> setSearchFilter("Title"));
        authorButton.setOnAction(event -> setSearchFilter("Author"));
    }

    private void setSearchFilter(String filter) {
        // Reset button styles
        anyButton.setStyle("-fx-background-color: white;");
        titleButton.setStyle("-fx-background-color: white;");
        authorButton.setStyle("-fx-background-color: white;");

        // Set the chosen search filter
        searchBy = filter;

        // Highlight the selected button
        switch (filter) {
            case "Any":
                anyButton.setStyle("-fx-background-color: #A9A9A9;");
                break;
            case "Title":
                titleButton.setStyle("-fx-background-color: #A9A9A9;");
                break;
            case "Author":
                authorButton.setStyle("-fx-background-color: #A9A9A9;");
                break;
        }
    }

    @FXML
    public void handleSearch(ActionEvent event) {
        String query = searchField.getText();  // Get the search query from the TextField
        String selectedType = typeComboBox.getValue();  // Get selected type from ComboBox

        // Clear previous error messages
        errorLabel.setText("");

        if (query.isEmpty() && searchBy.equals("Any")) {
            errorLabel.setText("Please enter a search term.");
            return;
        }


        List<Book> books = null;

        // Perform the search based on selected filter
        switch (searchBy) {
            case "Title":
                books = bookDAO.searchBooks(query);
                break;
            case "Author":
                books = bookDAO.searchBooks(query);
                break;
            case "Any":
                books = bookDAO.searchBooks(query);
                break;
        }

        System.out.println("Number of books found: " + (books != null ? books.size() : 0));  // Debug statement for results
        this.allBooks = books;
        displaySearchResults();
    }

    private void displaySearchResults() {
        searchResultsVBox.getChildren().clear();

        // Check if no books are found
        if (allBooks == null || allBooks.isEmpty()) {
            errorLabel.setText("No books found matching your search.");
            errorLabel.setStyle("-fx-text-fill: red;");
            System.out.println("No books found for the query.");
            return;
        }

        // Loop through the books and create entries for each
        int startIndex = (currentPage - 1) * BOOKS_PER_PAGE;
        int endIndex = Math.min(startIndex + BOOKS_PER_PAGE, allBooks.size());
        for (int i = startIndex; i < endIndex; i++) {
            createBookEntry(allBooks.get(i));
        }

        currentPageButton.setText(currentPage +"");
        back.setDisable(currentPage == 1); // Disable 'back' button on first page
        next.setDisable(currentPage * BOOKS_PER_PAGE >= allBooks.size()); // Disable 'next' button on last page


    }

    private void createBookEntry(Book book) {
        // HBox for the book entry
        HBox bookEntry = new HBox();
        bookEntry.setSpacing(10);
        bookEntry.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-border-radius: 10; -fx-padding: 10; -fx-background-color: #F5F5F5;");
        bookEntry.setMaxWidth(780.0);

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

        // ImageView for the book's image
        ImageView imageView = new ImageView();
        String imagePath = "/images/" + book.getImageUrl();  // Adjust the path relative to resources

        // Try to load the image from the resources
        URL resource = getClass().getResource(imagePath);
        if (resource != null) {
            // Image found, set it
            imageView.setImage(new Image(resource.toExternalForm()));
            imageView.setFitHeight(79.0);
            imageView.setFitWidth(59.0);
            imageView.setPreserveRatio(true);
        } else {
            // Image not found, show error label
            Label imageErrorLabel = new Label("Image not found");
            imageErrorLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;"); // Style the error message
            bookDetails.getChildren().add(imageErrorLabel);
        }

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
                        book.getImageUrl(),
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


    private void navigateToBookDetails(MouseEvent event, int id, String title, String author, String imageUrl, String rating, String description, String type, int year) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/najah/library/BookDetailsPage.fxml"));
        Parent bookDetailsPage = loader.load();

        // Get the controller of the Book Details Page
        BookDetailsPageController controller = loader.getController();

        // Pass the book details to the controller
        controller.setBookDetails(id, title, author, imageUrl, Double.parseDouble(rating), description, type, year);

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

    public void back(MouseEvent mouseEvent) {
        if (currentPage > 1) {
            currentPage--;
            displaySearchResults();
        }
    }

    public void next(MouseEvent mouseEvent) {
        if (currentPage * BOOKS_PER_PAGE < allBooks.size()) {
            currentPage++;
            displaySearchResults();
        }
    }
}
