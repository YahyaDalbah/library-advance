package edu.najah.library.controllers;

import edu.najah.library.models.Reservation;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class BookReturnController implements Initializable, AutoCloseable {
    @FXML
    private TextField searchField;

    @FXML
    private TableView<Reservation> reservationTable;

    @FXML
    private TableColumn<Reservation, String> bookTitleColumn;

    @FXML
    private TableColumn<Reservation, LocalDate> pickupDateColumn;

    @FXML
    private TableColumn<Reservation, Reservation.ReservationStatus> statusColumn;

    @FXML
    private TableColumn<Reservation, Void> controlColumn;

    private SessionFactory sessionFactory;
    private ObservableList<Reservation> reservations;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize Hibernate
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
            reservations = FXCollections.observableArrayList();

            // Initialize table columns
            setupTableColumns();

            // Add search listener
            setupSearch();
        } catch (Exception e) {
            showErrorAlert("Database Error", "Failed to connect to database: " + e.getMessage());
        }
    }

    private void setupTableColumns() {
        // Book title column
        bookTitleColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getBook().getTitle()));

        // Pickup date column
        pickupDateColumn.setCellValueFactory(new PropertyValueFactory<>("pickupDate"));

        // Status column with custom coloring
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusColumn.setCellFactory(column -> new TableCell<Reservation, Reservation.ReservationStatus>() {
            @Override
            protected void updateItem(Reservation.ReservationStatus status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(status.toString());
                    if (status == Reservation.ReservationStatus.OVERDUE) {
                        setStyle("-fx-text-fill: red;");
                    } else {
                        setStyle("-fx-text-fill: green;");
                    }
                }
            }
        });

        // Control column with return button
        controlColumn.setCellFactory(column -> new TableCell<Reservation, Void>() {
            private final Button returnButton = new Button("Return");

            {
                returnButton.setOnAction(event -> {
                    Reservation reservation = getTableView().getItems().get(getIndex());
                    handleReturnBook(event, reservation);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(returnButton);
                }
            }
        });
    }

    private void setupSearch() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                try {
                    searchReservations(Integer.parseInt(newValue));
                } catch (NumberFormatException e) {
                    showErrorAlert("Invalid Input", "Please enter a valid membership ID");
                }
            } else {
                reservationTable.getItems().clear();
            }
        });
    }

    private void searchReservations(int membershipId) {
        try (Session session = sessionFactory.openSession()) {
            List<Reservation> results = session.createQuery(
                            "FROM Reservation r WHERE r.membershipId = :membershipId",
                            Reservation.class)
                    .setParameter("membershipId", membershipId)
                    .list();

            Platform.runLater(() -> {
                reservations.setAll(results);
                reservationTable.setItems(reservations);
            });
        } catch (Exception e) {
            showErrorAlert("Search Error", "Error searching for reservations: " + e.getMessage());
        }
    }

    private void handleReturnBook(ActionEvent event, Reservation reservation) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/najah/library/book-return-popup.fxml"));
            Parent root = loader.load();

            ReturnBookPopupController popupController = loader.getController();
            popupController.setReservation(reservation);
            popupController.setSessionFactory(sessionFactory); // Pass the SessionFactory to popup

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initOwner(((Node) event.getSource()).getScene().getWindow());
            popupStage.setScene(new Scene(root));
            popupStage.showAndWait();

            // Refresh the table after return
            if (reservation.getMembershipId() > 0) {
                searchReservations(reservation.getMembershipId());
            }
        } catch (IOException e) {
            showErrorAlert("Error", "Failed to load return book popup: " + e.getMessage());
        }
    }

    @FXML
    private void handelBack(MouseEvent event) {
        loadFullScene(event, "dashboard-view.fxml", "Dashboard");
    }

    private void loadPopupScene(MouseEvent event, String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/najah/library/" + fxmlFile));
            Parent root = loader.load();

            Stage popupStage = new Stage();
            popupStage.setTitle(title);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initOwner(((Node) event.getSource()).getScene().getWindow());


            Scene scene = new Scene(root);
            popupStage.setScene(scene);

            popupStage.showAndWait();
        } catch (IOException e) {
            showErrorAlert("Error", "Failed to load the scene: " + fxmlFile);
            e.printStackTrace();
        }
    }

    private void loadFullScene(MouseEvent event, String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/najah/library/" + fxmlFile));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showErrorAlert("Error", "Failed to load the scene: " + fxmlFile);
            e.printStackTrace();
        }
    }

    private void showErrorAlert(String header, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

}
