package edu.najah.library.controllers;

import edu.najah.library.models.Book;
import edu.najah.library.models.services.BookDAOImp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

public class AddBookController {

    @FXML
    private RadioButton availableRadio;
    @FXML
    private RadioButton unavailableRadio;
    @FXML
    private Button closeButton;
    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private TextField coverField;
    @FXML
    private TextField ratingField;
    @FXML
    private TextField yearField;

    @FXML
    public void initialize() {
        ToggleGroup availabilityGroup = new ToggleGroup();
        availableRadio.setToggleGroup(availabilityGroup);
        unavailableRadio.setToggleGroup(availabilityGroup);

        // Add book types
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
                coverField.setText(newFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onAddButtonClick() {
        String title = titleField.getText();
        String author = authorField.getText();
        String description = descriptionArea.getText();
        String coverImageUrl = coverField.getText();
        String yearStr = yearField.getText();
        String type = typeComboBox.getValue();
        String availability = availableRadio.isSelected() ? "Available" : "Unavailable";
        String rating = ratingField.getText();

        if (title.isEmpty() || author.isEmpty() || description.isEmpty() || rating.isEmpty() || coverImageUrl.isEmpty() || yearStr.isEmpty() ||
                type == null || (!availableRadio.isSelected() && !unavailableRadio.isSelected())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
            return;
        }

        try {
            int year = Integer.parseInt(yearStr);
            Book newBook = new Book();
            newBook.setTitle(title);
            newBook.setAuthor(author);
            newBook.setDescription(description);
            newBook.setYear(year);
            newBook.setType(type);
            newBook.setAvailability(availability);
            newBook.setImageUrl(coverImageUrl);
            newBook.setRating(rating);

            BookDAOImp addBookDAO = new BookDAOImp();
            addBookDAO.insert(newBook);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Book Added");
            alert.setContentText("The book has been added successfully.");
            alert.showAndWait();

            clearFields();
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
                clearFields();
            }
        });
    }

    @FXML
    private void handleCloseButtonAction() {
        boolean hasData =
                (titleField.getText() != null && !titleField.getText().trim().isEmpty()) ||
                        (authorField.getText() != null && !authorField.getText().trim().isEmpty()) ||
                        (descriptionArea.getText() != null && !descriptionArea.getText().trim().isEmpty()) ||
                        (coverField.getText() != null && !coverField.getText().trim().isEmpty()) ||
                        (yearField.getText() != null && !coverField.getText().trim().isEmpty()) ||
                        (typeComboBox.getValue() != null) ||
                        (availableRadio.isSelected() || unavailableRadio.isSelected());

        if (hasData) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirm Exit");
            confirmationAlert.setHeaderText("Unsaved Data Warning");
            confirmationAlert.setContentText("Are you sure you want to close this page? Unsaved data will be lost.");

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Stage currentStage = (Stage) closeButton.getScene().getWindow();
                currentStage.close();
            }
        } else {
            Stage currentStage = (Stage) closeButton.getScene().getWindow();
            currentStage.close();
        }
    }

    private void clearFields() {
        titleField.clear();
        authorField.clear();
        descriptionArea.clear();
        ratingField.clear();
        coverField.clear();
        yearField.clear();
        typeComboBox.setValue(null);
        availableRadio.setSelected(false);
        unavailableRadio.setSelected(false);
    }
}
