package edu.najah.library.controllers;

 import edu.najah.library.models.User;
import edu.najah.library.models.services.UserDAOImp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
 import javafx.event.ActionEvent;
 import javafx.fxml.FXML;
 import javafx.fxml.FXMLLoader;
 import javafx.scene.Scene;
 import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
 import javafx.scene.layout.AnchorPane;
 import javafx.scene.layout.HBox;
 import javafx.stage.Stage;
 import java.lang.reflect.Field;
 import java.io.IOException;
 import java.util.List;
 import java.util.stream.Collectors;
 import java.util.stream.Stream;

public class StudentTableController {

    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, Integer> idColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, String> actionColumn;

    // Use UserDAOImpl to fetch data
    private final UserDAOImp userDAO = new UserDAOImp();

    public void initialize() {
        // Set up the columns for the table
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Fetch all users from the DAO and filter by role "student"
        List<User> usersList = userDAO.getAllUsers();

        // Filter users to include only those with role "student"
        List<User> studentUsers = usersList.stream()
                .filter(user -> "student".equalsIgnoreCase(user.getRole().getRole())) // Check if the role is "student"
                .collect(Collectors.toList());

        // Populate the table if data is available
        if (studentUsers != null && !studentUsers.isEmpty()) {
            ObservableList<User> users = FXCollections.observableArrayList(studentUsers);
            userTable.setItems(users);
        } else {
            System.out.println("No students found.");
        }

        // Set up the action column to hold "Edit" and "Details" buttons
        actionColumn.setCellFactory(column -> new TableCell<User, String>() {
            private final Button editButton = new Button("Edit");
            private final Button detailsButton = new Button("Details");

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    // Create a container to hold both buttons
                    HBox buttonBox = new HBox(5);
                    buttonBox.getChildren().addAll(editButton, detailsButton);

                    setGraphic(buttonBox);

                    // Set actions for both buttons
                    editButton.setOnAction(event -> handleEdit(getTableRow().getItem()));
                    detailsButton.setOnAction(event -> showStudentDetails(getTableRow().getItem()));
                }
            }
        });
    }

    private void showStudentDetails(User user) {
        if (user != null) {
            // Display an alert with the student details (can be customized as needed)
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Student Details");
            alert.setHeaderText("Details for: " + user.getName());
            alert.setContentText("ID: " + user.getId() + "\nEmail: " + user.getEmail());
            alert.showAndWait();
        }
    }






    private void handleEdit(User user) {
        if (user != null) {
            TextInputDialog nameDialog = new TextInputDialog(user.getName());
            nameDialog.setTitle("Edit Name");
            nameDialog.setHeaderText("Edit User Name");
            nameDialog.setContentText("New Name:");

            nameDialog.showAndWait().ifPresent(newName -> user.setName(newName));

            TextInputDialog emailDialog = new TextInputDialog(user.getEmail());
            emailDialog.setTitle("Edit Email");
            emailDialog.setHeaderText("Edit User Email");
            emailDialog.setContentText("New Email:");

            emailDialog.showAndWait().ifPresent(newEmail -> user.setEmail(newEmail));

            userTable.refresh();
        }
    }
    public void handleBackButtonAction(ActionEvent event) {
        try {
            // Load the dashboard-view.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/najah/library/dashboard-view.fxml"));
            AnchorPane dashboardView = loader.load();

            // Set up the new scene with the dashboard view
            Scene scene = new Scene(dashboardView);
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
