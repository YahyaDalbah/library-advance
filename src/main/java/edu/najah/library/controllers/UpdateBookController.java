package edu.najah.library.controllers;

import edu.najah.library.models.Book;
import edu.najah.library.models.services.BookDAOImp;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

public class UpdateBookController {

    @FXML
    private RadioButton available;
    @FXML
    private RadioButton unavailable;
    @FXML
    private Button close;
    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private TextField title;
    @FXML
    private TextField author;
    @FXML
    private TextArea description;
    @FXML
    private TextField cover;
    @FXML
    private TextField rating;
    @FXML
    private TextField year;

    private Book book;

    @FXML
    public void initialize() {
        ToggleGroup availabilityGroup = new ToggleGroup();
        available.setToggleGroup(availabilityGroup);
        unavailable.setToggleGroup(availabilityGroup);


        typeComboBox.getItems().addAll(
                "Fiction",
                "Non-Fiction",
                "Science",
                "History",
                "Biography",
                "Fantasy",
                "Mystery",
                "Romance",
                "Horror",
                "Self-Help",
                "Philosophy",
                "Psychology",
                "Art",
                "Technology",
                "Business",
                "Health",
                "Cooking",
                "Travel",
                "Poetry",
                "Children's Books",
                "Education"
        );
    }


    @FXML
    private void handleAttach() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp");
        fileChooser.getExtensionFilters().add(imageFilter);
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            try {
                long timestamp = System.currentTimeMillis();
                String newFileName = timestamp + "_" + selectedFile.getName();
                File folder = new File("src/main/resources/images/");
                Path targetPath = Paths.get(folder.toPath().toString(), newFileName);
                Files.copy(selectedFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                cover.setText(newFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onUpdateButtonClick() {
        String title = this.title.getText();
        String author = this.author.getText();
        String description = this.description.getText();
        String coverImageUrl = cover.getText();
        String yearStr = year.getText();
        String type = typeComboBox.getValue();
        String availability = available.isSelected() ? "Available" : "Unavailable";
        String rating = this.rating.getText();
//        String quantityStr = quantity.getText();

        if (title.isEmpty() || author.isEmpty() || description.isEmpty() || rating.isEmpty() || coverImageUrl.isEmpty() || yearStr.isEmpty() ||
                type == null || (!available.isSelected() && !unavailable.isSelected())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
            return;
        }

        try {
            int year = Integer.parseInt(yearStr);
//            int quantity = Integer.parseInt(quantityStr);
            book.setId(book.getId());
            book.setTitle(title);
            book.setAuthor(author);
            book.setDescription(description);
            book.setYear(year);
            book.setType(type);
            book.setAvailability(availability);
            book.setImageUrl(coverImageUrl);
            book.setRating(rating);
//            book.setQuantity(quantity);

            BookDAOImp addBookDAO = new BookDAOImp();
            addBookDAO.updateBook(book);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Update book");
            alert.setContentText("The book has been updated successfully.");
            alert.showAndWait();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Year Format");
            alert.setContentText("Please enter a valid year.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleCancel() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Cancel Confirmation");
        confirmationAlert.setHeaderText("Are you sure you want to discard all changes?");
        confirmationAlert.setContentText("If you press Yes, all entered data will be cleared.");

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
//                clearFields();
            }
        });
    }

    @FXML
    private void handleCloseButtonAction() {
        boolean hasData =
                (title.getText() != null && !title.getText().trim().isEmpty()) ||
                        (author.getText() != null && !author.getText().trim().isEmpty()) ||
                        (description.getText() != null && !description.getText().trim().isEmpty()) ||
                        (cover.getText() != null && !cover.getText().trim().isEmpty()) ||
                        (year.getText() != null && !cover.getText().trim().isEmpty()) ||
                        (typeComboBox.getValue() != null) ||
                        (available.isSelected() || unavailable.isSelected());

        if (hasData) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirm Exit");
            confirmationAlert.setHeaderText("Unsaved Data Warning");
            confirmationAlert.setContentText("Are you sure you want to close this page? Unsaved data will be lost.");

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Stage currentStage = (Stage) close.getScene().getWindow();
                currentStage.close();
            }
        } else {
            Stage currentStage = (Stage) close.getScene().getWindow();
            currentStage.close();
        }
    }



    public void setBook(Book book) {
        this.book = book;
        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        description.setText(book.getDescription());
        rating.setText(book.getRating());
        cover.setText(book.getImageUrl());
        year.setText(book.getYear()+"");
        typeComboBox.setValue(book.getType());

        if(book.getAvailability().equalsIgnoreCase("Available")) {
            available.setSelected(true);
        } else {
            unavailable.setSelected(true);
        }

    }
}
