package edu.najah.library.controllers;

import edu.najah.library.models.Book;
import edu.najah.library.models.Reservation;
import edu.najah.library.models.User;
import edu.najah.library.models.interfaces.ReservationDAO;
import edu.najah.library.models.interfaces.UserDAO;
import edu.najah.library.models.services.BookDAOImp;
import edu.najah.library.models.interfaces.BookDAO;
import edu.najah.library.models.services.ReservationDAOImpl;
import edu.najah.library.models.services.UserDAOImp;
import edu.najah.library.utils.Register;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardViewController implements Initializable {

    @FXML
    private Text addLibrarianBtn;

    @FXML
    private Label bookCount;

    @FXML
    private Text studentCount;

    @FXML
    private Text reservationCount;

    @FXML
    private TableView<Reservation> reservationsTable;

    @FXML
    private TableColumn<Reservation, String> titleColumn;

    @FXML
    private TableColumn<Reservation, LocalDate> pickupDateColumn;

    private BookDAO bookDAO;
    private UserDAO userDAO;
    private ReservationDAO reservationDAO;

    public DashboardViewController () {
        this.bookDAO = new BookDAOImp();
        this.userDAO = new UserDAOImp();
        this.reservationDAO= new ReservationDAOImpl();
    }

    public void updatesDatabase() {
        List<Book> books = bookDAO.getAllBooks();
        int bookCountInt = books.size();

        List<User> users = userDAO.getAllUsers();
        int userCountInt = users.size();

        bookCount.setText("Total Books: " + bookCountInt);
        studentCount.setText("Total Students: " + userCountInt);

        // Calculate last month's date range
        LocalDate now = LocalDate.now();
        LocalDate firstDayLastMonth = now.minusMonths(1).withDayOfMonth(1);
        LocalDate lastDayLastMonth = firstDayLastMonth.plusMonths(1).minusDays(1);

        // Get reservations
        List<Reservation> lastMonthReservations = reservationDAO.getReservationsByDateRange(
                firstDayLastMonth,
                lastDayLastMonth
        );

        // Update the label
        this.reservationCount.setText("Last Month Reservations: " + lastMonthReservations.size());
    }

    private void setupReservationsTable() {
        // Simple cell value factories
        titleColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getBook() != null ?
                        cellData.getValue().getBook().getTitle() : ""));

        pickupDateColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getPickupDate()));

        // Load whatever reservations exist
        List<Reservation> reservations = reservationDAO.getLatestReservations(10);
        reservationsTable.getItems().addAll(reservations);
    }


    @FXML
    private void handleAddNewLibrarian(MouseEvent event) {
        loadPopupScene(event, "add-librarian-page.fxml", "Add Librarian");
    }

    @FXML
    private void handleManageStudents(MouseEvent event) {
        loadFullScene(event, "studentTable.fxml", "Manage Students");
    }

    @FXML
    private void handleManageProfile(MouseEvent event) {
        loadPopupScene(event, "manage-profile.fxml", "Manage Profile");
    }

    @FXML
    private void handleAddNewBook(MouseEvent event) {
        loadPopupScene(event, "add-book.fxml", "Add New Book");
    }

    @FXML
    private void handleBookStatusManagement(MouseEvent event) {
        loadFullScene(event, "Book-status-management.fxml", "Book Status Management");
    }

    @FXML
    private void handleBookReservation(MouseEvent event) {
        loadPopupScene(event, "Reservation-Book.fxml", "Book Reservation");
    }
    @FXML
    private void handleLogout(MouseEvent event) {
        Register.getInstance().clearUser();
        loadFullScene(event, "login.fxml", "Login");
    }

    @FXML
    private void handleSearchPage(MouseEvent event) {
        loadFullScene(event, "AllbooksPage.fxml", "Library7");
    }

    @FXML
    private void handleBookReturn(MouseEvent event) {
        loadFullScene(event, "return-book.fxml", "Book Return");
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
    public void initialize(URL location, ResourceBundle resources) {
        addLibrarianBtn.setVisible(Register.getInstance().getCurrentUser().getRole().hasPermission("canAddLibrarian"));
        updatesDatabase();
        setupReservationsTable();


    }
}
