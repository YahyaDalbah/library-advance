package edu.najah.library.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class BookStatusManagementController {
    @FXML
    private Button addNewBook;

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
    }
}
