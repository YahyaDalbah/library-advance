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
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

public class AddBookController {

    @FXML
    private RadioButton availableRadio;
    @FXML
    private RadioButton unavailableRadio;
    @FXML
    private Button closeButton;
    @FXML
    private ComboBox typeComboBox;
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private TextField coverField;
    @FXML
    private DatePicker publicationDatePicker;
    @FXML
    private TextField ratingField;
    @FXML
    private TextField yearField;

    @FXML
    public void initialize() {

        ToggleGroup availabilityGroup = new ToggleGroup();
        availableRadio.setToggleGroup(availabilityGroup);
        unavailableRadio.setToggleGroup(availabilityGroup);

        // add books types
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

    public void openAddBookModal() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/najah/library/add-book.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Add Book");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.showAndWait();
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
    public void onAddButtonClick()  {
        String title = titleField.getText();
        String author = authorField.getText();
        String description = descriptionArea.getText();
        String coverImageUrl = coverField.getText();
        String yearStr = yearField.getText();
        LocalDate publicationDate = publicationDatePicker.getValue();
        String type = (String) typeComboBox.getValue();
        String availability = availableRadio.isSelected() ? "Available" : "Unavailable";
        String rating = ratingField.getText();


        if (title.isEmpty() || author.isEmpty() || description.isEmpty() || coverImageUrl.isEmpty() || yearStr.isEmpty() || type == null ||
                (!availableRadio.isSelected() && !unavailableRadio.isSelected())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
            return;
        }

        Date date = Date.valueOf(publicationDate);
        int year = Integer.parseInt(yearStr);
        Book newBook = new Book.Builder()
                .setTitle(title)
                .setAuthor(author)
                .setDescription(description)
                .setYear(year)
                .setType(type)
                .setAvailability(availability)
                .setImageUrl(coverImageUrl)
                .setRating(rating)
                .build();

        BookDAOImp addBookDAO = new BookDAOImp();
        addBookDAO.insert(newBook);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Book Added");
        alert.setContentText("The book has been added successfully.");
        alert.showAndWait();

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

    @FXML
    private void handleCancel() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Cancel Confirmation");
        confirmationAlert.setHeaderText("Are you sure you want to discard all changes?");
        confirmationAlert.setContentText("If you press Yes, all entered data will be cleared.");

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                titleField.clear();
                authorField.clear();
                descriptionArea.clear();
                coverField.clear();
                yearField.clear();
                typeComboBox.setValue(null);
                availableRadio.setSelected(false);
                unavailableRadio.setSelected(false);
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
                        (yearField.getText() != null) ||
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
}
