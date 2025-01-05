package edu.najah.library.controllers;

import edu.najah.library.models.Book;
import edu.najah.library.models.User;
import edu.najah.library.models.services.BookDAOImp;
import edu.najah.library.utils.Register;
import edu.najah.library.utils.UtilFunctions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AllBooksPageController implements Initializable {

    @FXML
    public TilePane BooksTilePane;
    @FXML
    public Button back;
    @FXML
    public Button next;
    @FXML
    public Button currentPageButton;
    @FXML
    private Button goToDashboardBtn;

    private int currentPage = 1;
    private static final int BOOKS_PER_PAGE = 10; // Number of books per page
    private List<Book> allBooks;

    public void initialize(URL location, ResourceBundle resources) {
        User user = Register.getInstance().getCurrentUser();

        goToDashboardBtn.setVisible(user != null && user.getRole().hasPermission("canViewDashboard"));
        try {
            // Fetch all books using getAllBooks()
            allBooks = new BookDAOImp().getAllBooks();

            // Load books for the current page
            loadBooks();

        } catch (Exception e) {
            // Handle any exceptions that occur while fetching books
            System.err.println("Error loading books: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void goToDashboard(ActionEvent event) throws IOException {
        UtilFunctions.switchScene(event,"dashboard-view.fxml");
    }

    private void loadBooks() {
        BooksTilePane.getChildren().clear(); // Clear existing books

        int startIndex = (currentPage - 1) * BOOKS_PER_PAGE;
        int endIndex = Math.min(startIndex + BOOKS_PER_PAGE, allBooks.size());

        // Add books to the UI
        for (int i = startIndex; i < endIndex; i++) {
            Book book = allBooks.get(i);
            VBox bookVBox = new VBox(10);
            bookVBox.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-border-color: white; " +
                    "-fx-border-width: 1px; -fx-border-radius: 5px;");

            // Book details
            int bookId = book.getId();
            String title = book.getTitle();
            String author = book.getAuthor();
            String imageName = book.getImageUrl();
            double rating = Double.parseDouble(book.getRating());
            String description = book.getDescription();
            String type = book.getType();
            int year = book.getYear();

            ImageView imageView = new ImageView();
            String imagePath = "/images/" + imageName;

            // Try to load the image from the resources
            URL resource = getClass().getResource(imagePath);
            if (resource != null) {
                // Image found, set it
                imageView.setImage(new Image(resource.toExternalForm()));
                imageView.setFitHeight(200);
                imageView.setPreserveRatio(true);
                bookVBox.getChildren().add(imageView);
            } else {
                // Image not found, show error label
                Label imageErrorLabel = new Label("Image not found");
                imageErrorLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;"); // Style the error message
                bookVBox.getChildren().add(imageErrorLabel);
            }

            Label titleLabel = new Label(title);
            titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            bookVBox.getChildren().add(titleLabel);

            Label authorLabel = new Label(author);
            authorLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");
            bookVBox.getChildren().add(authorLabel);

            // Add click event handler
            bookVBox.setOnMouseClicked(event -> {
                try {
                    navigateToBookDetails(event, bookId, title, author, imageName, rating, description, type, year);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            BooksTilePane.getChildren().add(bookVBox);
        }

        // Update the current page button text
        currentPageButton.setText(currentPage +"");

        // Enable/Disable navigation buttons
        back.setDisable(currentPage == 1); // Disable 'back' button on first page
        next.setDisable(currentPage * BOOKS_PER_PAGE >= allBooks.size()); // Disable 'next' button on last page
    }

    @FXML
    private void navigateToSearch(MouseEvent event) {
        handleSearchButtonClick(event, "searchPage.fxml");
    }

    @FXML
    public void navigateToBookDetails(MouseEvent event, int bookId, String title, String author, String imageUrl, double rating, String description, String type, int year) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/najah/library/BookDetailsPage.fxml"));
        Parent bookDetailsPage = loader.load();

        // Get the controller of the Book Details Page
        BookDetailsPageController controller = loader.getController();

        // Pass the book details to the controller
        controller.setBookDetails(bookId, title, author, imageUrl, rating, description, type, year);

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
    public void back(MouseEvent mouseEvent) {
        if (currentPage > 1) {
            currentPage--;
            loadBooks();
        }
    }

    @FXML
    public void next(MouseEvent mouseEvent) {
        if (currentPage * BOOKS_PER_PAGE < allBooks.size()) {
            currentPage++;
            loadBooks();
        }
    }


}
