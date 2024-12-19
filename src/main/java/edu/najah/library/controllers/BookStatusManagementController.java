package edu.najah.library.controllers;

import edu.najah.library.models.interfaces.BookDAO;
import edu.najah.library.models.services.BookDAOImp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import edu.najah.library.models.user.Book;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class BookStatusManagementController {
    @FXML
    private Button addNewBook;
    @FXML
    private TilePane booksContainer;
    @FXML
    private ScrollPane scrollPane;

    @FXML
    public void initialize() {
        addNewBook.setOnAction(event -> {
            try {
                AddBookController addBookController = new AddBookController();
                addBookController.openAddBookModal();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        BookDAO bookDAO = new BookDAOImp();
        List<Object[]> results = bookDAO.getAllBooks();
        for (Object[] row : results) {
            int id = (Integer) row[0];
            String title = (String) row[1];
            String author = (String) row[2];
            String imageUrl = (String) row[3];
            Book book = new Book.Builder()
                    .setId(id)
                    .setTitle(title)
                    .setAuthor(author)
                    .setImageUrl(imageUrl)
                    .build();

            displayBook(book);
        }
       // scrollPane.setContent(booksContainer);
    }

    private void displayBook(Book book) {
        VBox bookCard = new VBox();
        bookCard.setSpacing(10);
        bookCard.setStyle("-fx-background-color: #fff; -fx-padding: 10; -fx-border-color: #ccc; -fx-border-radius: 5;");

        ImageView imageView = new ImageView();
        String imageName = book.getImageUrl();
        try {
            String imagePath = "/images/BookCovers/" + imageName;
            URL resource = getClass().getResource(imagePath);
            if (resource != null) {
                imageView.setImage(new Image(resource.toExternalForm()));
            } else {
                System.out.println("Image not found at: " + imagePath);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }


        imageView.setFitHeight(190);
        imageView.setFitWidth(130);

        Label titleLabel = new Label(book.getTitle());
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label authorLabel = new Label("By: " + book.getAuthor());
        authorLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #444;");

        Label idLabel = new Label("ID: " + book.getId());
        idLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #444;");

        HBox buttonsBox = new HBox(24);
        Button updateButton = new Button("Update");
        updateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: #fff; -fx-font-size: 12px;");

        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: #fff; -fx-font-size: 12px;");

        buttonsBox.getChildren().addAll(updateButton, deleteButton);

        bookCard.getChildren().addAll(imageView, titleLabel, authorLabel, idLabel, buttonsBox);

        booksContainer.getChildren().add(bookCard);
    }
}
