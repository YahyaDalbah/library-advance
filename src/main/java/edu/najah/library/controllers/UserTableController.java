package edu.najah.library.controllers;

 import edu.najah.library.models.User;
import edu.najah.library.models.services.UserDAOImp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class UserTableController {

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

        // Fetch all users from the DAO and update the TableView
        List<User> usersList = userDAO.getAllUsers();

        // Populate the table if data is available
        if (usersList != null && !usersList.isEmpty()) {
            ObservableList<User> users = FXCollections.observableArrayList(usersList);
            userTable.setItems(users);
        } else {
            System.out.println("No users found or failed to fetch users.");
        }

        // Set up the action column to hold "Edit" buttons
        actionColumn.setCellFactory(column -> new TableCell<User, String>() {
            private final Button editButton = new Button("Edit");

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editButton);
                    editButton.setOnAction(event -> handleEdit(getTableRow().getItem()));
                }
            }
        });
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
}
