package edu.najah.library.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class SearchPageController {

    @FXML
    private TextField searchField;

    @FXML
    private ListView<String> resultsList;

    @FXML
    private void handleSearch() {
        String query = searchField.getText();
        // Example: Add search logic here (e.g., call an API or search a list)
        resultsList.getItems().clear();
        resultsList.getItems().add("Example Book 1: " + query);
        resultsList.getItems().add("Example Book 2: " + query);
    }
}
