package edu.najah.library.controllers;

import edu.najah.library.models.interfaces.BookDAO;
import edu.najah.library.models.services.BookDAOImp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import edu.najah.library.models.Book;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BookStatusManagementController {
    @FXML
    private TilePane booksContainer;
    @FXML
    private TextField searchById;
    @FXML
    private Label statusLabel;

    private ObservableList<Book> observableBooksList;

    @FXML
    private void handelBack(MouseEvent event) {
        loadFullScene(event, "dashboard-view.fxml", "Dashboard");
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
            e.printStackTrace();
        }
    }
    @FXML
    private void handelAddBookButton(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/najah/library/add-book.fxml"));
            Parent addBookRoot = loader.load();
            Stage popupStage = new Stage();
            popupStage.setTitle("Add Book");
            popupStage.setScene(new Scene(addBookRoot));
            popupStage.initOwner(((Node) event.getSource()).getScene().getWindow());
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.initStyle(StageStyle.UTILITY);
            popupStage.setResizable(false);
            popupStage.setOnHidden(e -> updateBooksList());
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        searchById.setOnKeyReleased(event -> searchBook());
        updateBooksList();
    }
    private void searchBook() {
        String searchId = searchById.getText().trim();
        if (searchId.isEmpty()) {
            statusLabel.setText("");
            updateBooksList();
            return;
        }
        try {
            int id = Integer.parseInt(searchId);
            BookDAO bookDAO = new BookDAOImp();
            Book book = bookDAO.getBookById(id);
            booksContainer.getChildren().clear();
            if (book != null) {
                statusLabel.setText("");
                displayBook(book);
            } else {
                statusLabel.setText("No book found with ID: " + id);
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Invalid ID: Please enter a valid numeric ID.");
        }
    }

    private void updateBooksList() {
        BookDAO bookDAO = new BookDAOImp();
        List<Book> books = bookDAO.getAllBooks();
        observableBooksList = FXCollections.observableArrayList(books);
        booksContainer.getChildren().clear();
        observableBooksList.forEach(this::displayBook);
    }

    private void displayBook(Book book) {
        VBox bookCard = new VBox();
        bookCard.setSpacing(8);
        bookCard.setStyle("-fx-background-color: #fff; -fx-padding: 10; -fx-border-color: #ccc; -fx-border-radius: 5;");

        ImageView imageView = new ImageView();
        String imageName = book.getImageUrl();
        try {
            String imagePath = "/images/" + imageName;
            URL resource = getClass().getResource(imagePath);
            if (resource != null) {
                imageView.setImage(new Image(resource.toExternalForm()));
            } else {
                System.out.println("Image not found at: " + imagePath);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        imageView.setFitHeight(200);
        imageView.setFitWidth(140);

        Label titleLabel = new Label(book.getTitle());
        titleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        Label authorLabel = new Label("By: " + book.getAuthor());
        authorLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #444;");

        Label idLabel = new Label("ID: " + book.getId());
        idLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #444;");

        HBox buttonsBox = new HBox(32);
        Button updateButton = new Button("Update");
        updateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: #fff; -fx-font-size: 12px;");

        updateButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/najah/library/update-book.fxml"));
                Parent updateBookRoot = loader.load();

                 UpdateBookController updateBookController = loader.getController();
                 updateBookController.setBook(book);
              
                 Stage popupStage = new Stage();
                popupStage.setTitle("Update Book");
                popupStage.setScene(new Scene(updateBookRoot));
                popupStage.initOwner(updateButton.getScene().getWindow());
                popupStage.initModality(Modality.WINDOW_MODAL);
                popupStage.initStyle(StageStyle.UTILITY);
                popupStage.showAndWait();
                updateBooksList();

            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failed to open update-book.fxml");
                alert.setContentText("An error occurred while loading the update book page.");
                alert.showAndWait();
            }
        });


        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: #fff; -fx-font-size: 12px;");

        deleteButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setHeaderText("Are you sure you want to delete this book?");
            alert.setContentText("ID: " + book.getId() + ", Title: " + book.getTitle());
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    BookDAO bookDAO = new BookDAOImp();
                    bookDAO.deleteBookById(book.getId());
                    observableBooksList.remove(book);
                    booksContainer.getChildren().remove(bookCard);
                    //Delete image from folder
                    String imageFileName = book.getImageUrl();
                    if (imageFileName != null && !imageFileName.isEmpty()) {
                        File imageFile = new File("src/main/resources/images/" + imageFileName);
                        if (imageFile.exists() && imageFile.isFile()) {
                            imageFile.delete();
                        }
                    }
                }
            });
        });

        buttonsBox.getChildren().addAll(updateButton, deleteButton);

        bookCard.getChildren().addAll(imageView, titleLabel, authorLabel, idLabel, buttonsBox);

        booksContainer.getChildren().add(bookCard);

    }
}
