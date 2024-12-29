package edu.najah.library.controllers;

import edu.najah.library.models.User;
import edu.najah.library.utils.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

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

    public void initialize() {
        // Set up the columns for the table
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Fetch all users from the database
         List<User> usersList = getAllUsers();
        for (User user : usersList) {
            System.out.println("User: " + user.getName() + ", " + user.getEmail());
        }

        // Convert List<User> to ObservableList
        ObservableList<User> users = FXCollections.observableArrayList(usersList);

        // Set the TableView items
        userTable.setItems(users);

        // Set up the action column to hold "Edit" buttons
        actionColumn.setCellFactory(column -> {
            return new TableCell<User, String>() {
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
            };
        });
    }

    // This method handles the Edit button click event.
    private void handleEdit(User user) {
        if (user != null) {
            // Show a simple dialog with fields for editing name and email.
            TextInputDialog nameDialog = new TextInputDialog(user.getName());
            nameDialog.setTitle("Edit Name");
            nameDialog.setHeaderText("Edit User Name");
            nameDialog.setContentText("New Name:");

            nameDialog.showAndWait().ifPresent(newName -> {
                user.setName(newName);
                updateUserInDatabase(user); // Update in DB
            });

            TextInputDialog emailDialog = new TextInputDialog(user.getEmail());
            emailDialog.setTitle("Edit Email");
            emailDialog.setHeaderText("Edit User Email");
            emailDialog.setContentText("New Email:");

            emailDialog.showAndWait().ifPresent(newEmail -> {
                user.setEmail(newEmail);
                updateUserInDatabase(user); // Update in DB
            });

            // After updating, refresh the table
            userTable.refresh();
        }
    }

    // Fetch all users from the database
    public List<User> getAllUsers() {
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<User> query = session.createQuery("from User", User.class);
            List<User> users = query.getResultList();
            session.getTransaction().commit();
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update the user in the database
    private void updateUserInDatabase(User user) {

    }
}
