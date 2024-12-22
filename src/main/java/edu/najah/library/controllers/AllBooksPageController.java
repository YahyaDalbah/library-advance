package edu.najah.library.controllers;

import edu.najah.library.models.user.Book;
import edu.najah.library.utils.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class AllBooksPageController {

    @FXML
    private TilePane BooksTilePane;

    public void initialize() {
        loadBooks();
    }

    // Method to load books and update the UI
    public void loadBooks() {
        ObservableList<VBox> books = FXCollections.observableArrayList();

        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();

        // Hibernate query to retrieve book data
            Query<Book> query = session.createQuery("from Book", Book.class);
            List<Book> resultList = query.list();

            // Loop through the result list and build UI components
            for (Book book : resultList) {
                VBox bookVBox = new VBox(10);
                bookVBox.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-border-color: white; " +
                        "-fx-border-width: 1px; -fx-border-radius: 5px;");

                // Extract book details
                int bookId = book.getId();
                String title = book.getTitle();
                String author = book.getAuthor();
                byte[] imageBytes = book.getImage();
                double rating = Double.parseDouble(book.getRating());
                String description = book.getDescription();
                String type = book.getType();
                Date year = book.getDate("year");


                // Create UI elements
                if (imageBytes != null) {
                    Image image = new Image(new ByteArrayInputStream(imageBytes));
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(200);
                    imageView.setPreserveRatio(true);
                    bookVBox.getChildren().add(imageView);
                } else {
                    Label imageErrorLabel = new Label("Image not found");
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
                        navigateToBookDetails(event, bookId, title, author, imageBytes, rating, description, type, year);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                books.add(bookVBox);
            }

            // Update the UI (assumes you have a BooksTilePane in your scene)
            BooksTilePane.getChildren().setAll(books);

        }

    @FXML
    private void navigateToSearch(MouseEvent event) {
        handleSearchButtonClick(event, "searchPage.fxml");
    }

    @FXML
    private void navigateToBookDetails(MouseEvent event, int bookId, String title, String author, byte[] imageBytes, double rating, String description, String type, Date year) throws IOException {
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
