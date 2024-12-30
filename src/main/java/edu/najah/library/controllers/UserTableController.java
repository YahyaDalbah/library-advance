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
        // Set up columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Fetch data and populate the table
        List<User> usersList = getAllUsers();
        if (usersList != null && !usersList.isEmpty()) {
            ObservableList<User> users = FXCollections.observableArrayList(usersList);
            userTable.setItems(users);
        } else {
            System.out.println("No users found.");
        }

        // Set up action column with "Edit" button
        actionColumn.setCellFactory(column -> new TableCell<>() {
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


    // Load user data and populate the table
    private void loadUserData() {
        List<User> usersList = getAllUsers();
        if (usersList != null && !usersList.isEmpty()) {
            ObservableList<User> users = FXCollections.observableArrayList(usersList);
            userTable.setItems(users);
        } else {
            System.out.println("No users found or failed to fetch users.");
        }
    }

    // Handle the Edit button click event
    private void handleEdit(User user) {
        if (user != null) {
            // Edit user name
            TextInputDialog nameDialog = new TextInputDialog(user.getName());
            nameDialog.setTitle("Edit Name");
            nameDialog.setHeaderText("Edit User Name");
            nameDialog.setContentText("New Name:");
            nameDialog.showAndWait().ifPresent(newName -> user.setName(newName));

            // Edit user email
            TextInputDialog emailDialog = new TextInputDialog(user.getEmail());
            emailDialog.setTitle("Edit Email");
            emailDialog.setHeaderText("Edit User Email");
            emailDialog.setContentText("New Email:");
            emailDialog.showAndWait().ifPresent(newEmail -> user.setEmail(newEmail));

            // Refresh table to reflect updated user data
            userTable.refresh();
        }
    }

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

}
