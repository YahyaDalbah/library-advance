package edu.najah.library.controllers;

import edu.najah.library.models.Book;
import edu.najah.library.models.Reservation;
import edu.najah.library.models.interfaces.BookDAO;
import edu.najah.library.models.interfaces.ReservationDAO;
import edu.najah.library.models.services.BookDAOImp;
import edu.najah.library.models.services.ReservationDAOImpl;
import edu.najah.library.utils.HibernateUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.List;

public class ReservationBook {
    private SessionFactory factory;
    private BookDAO bookDAO;
    private ReservationDAO reservationDAO;

    @FXML
    private TextField Book_id;

    @FXML
    private TextField Book_Title;

    @FXML
    private TextField Author;

    @FXML
    private TextField Availability;

    @FXML
    private TextField Search_Box;

    @FXML
    private TextField First_Name;

    @FXML
    private TextField Last_Name;

    @FXML
    private TextField Membership_ID;

    @FXML
    private DatePicker Pickup_Date;

    @FXML
    private DatePicker Return_Date;

    @FXML
    private Button close_btn;

    @FXML
    private ImageView Image_Book;

    @FXML
    private Button Cancel_btn;

    @FXML
    private Button Reservation_btn;

    @FXML
    private ListView<String> List_Search;



    @FXML
    public void initialize() {

        bookDAO = new BookDAOImp();
        reservationDAO = new ReservationDAOImpl();

        Image defaultImage = new Image(getClass().getResource("/images/71speD+PGFL.jpg").toExternalForm());

        Image_Book.setImage(defaultImage);


        Search_Box.textProperty().addListener((observable, oldValue, newValue) -> searchBooks(newValue));

        Search_Box.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                List_Search.setVisible(false); // Hide menu when focus is lost
            }
        });

        List_Search.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                List_Search.setVisible(false); // Hide menu when focus is lost
            }
        });

        List_Search.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedItem) -> {
            if (selectedItem != null) {
                fetchBookDetails(selectedItem);
            }
        });

        Pickup_Date.setDayCellFactory(datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });

        Return_Date.setDayCellFactory(datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });

        List_Search.setOnMouseClicked(event -> handleBookSelection()); // Bind the list to the click function

    }
    private void searchBooks(String query) {
        List<Book> books = bookDAO.findBooksByTitle(query);

        // Specify that only five books are displayed at most.
        List_Search.getItems().clear();
        int limit = Math.min(books.size(), 8);
        for (int i = 0; i < limit; i++) {
            List_Search.getItems().add(books.get(i).getTitle());
        }

        // The list view is visible only when there are results.
        List_Search.setVisible(!List_Search.getItems().isEmpty());
    }
    private void fetchBookDetails(String title) {
        Book book = bookDAO.findBooksByTitle(title).get(0);
        if (book != null) {
            Book_id.setText(String.valueOf(book.getId()));
            Book_Title.setText(book.getTitle());
            Author.setText(book.getAuthor());
            Availability.setText(book.getAvailability());
        }
        if (book.getImageUrl() != null && !book.getImageUrl().isEmpty()) {
            // Load image from database
            Image bookImage = new Image(getClass().getResource("/images/" + book.getImageUrl()).toExternalForm(), true);
            Image_Book.setImage(bookImage);
        } else {
            // Set default image if there is no image attached to the book
            Image defaultImage = new Image(getClass().getResource("/images/71speD+PGFL.jpg").toExternalForm());
            Image_Book.setImage(defaultImage);
        }
    }


    @FXML
    private void handleBookSelection() {
        String selectedBook = List_Search.getSelectionModel().getSelectedItem(); // Get the specified item
        if (selectedBook != null) {
            Search_Box.setText(selectedBook);
            List_Search.setVisible(false);
            List_Search.getSelectionModel().clearSelection();
        }
    }


    @FXML
    void handleReservation(ActionEvent event) {
        if (First_Name.getText().isEmpty() || Last_Name.getText().isEmpty() || Membership_ID.getText().isEmpty() ||
                Pickup_Date.getValue() == null || Return_Date.getValue() == null || Book_id.getText().isEmpty()) {
            showAlert("Error", "All fields are required.", Alert.AlertType.ERROR);
            return;
        }
        if (Return_Date.getValue().isBefore(Pickup_Date.getValue())) {
            showAlert("Error", "Return Date must be after Pickup Date.", Alert.AlertType.ERROR);
            return;
        }

        Book book = bookDAO.getBookById(Integer.parseInt(Book_id.getText()));
        if (book == null || !"Available".equalsIgnoreCase(book.getAvailability())) {
            showAlert("Error", "Cannot reserve this book. It is unavailable.", Alert.AlertType.ERROR);
            return;
        }




        bookDAO.updateBook(book);


        Reservation reservation = new Reservation();
        reservation.setFirstName(First_Name.getText());
        reservation.setLastName(Last_Name.getText());
        reservation.setMembershipId(Membership_ID.getText());
        reservation.setPickupDate(Pickup_Date.getValue());
        reservation.setReturnDate(Return_Date.getValue());
        reservation.setBook(book);

        reservationDAO.saveReservation(reservation);

        showAlert("Success", "Reservation saved successfully!", Alert.AlertType.INFORMATION);

        // Reset fields
        First_Name.clear();
        Last_Name.clear();
        Membership_ID.clear();
        Pickup_Date.setValue(null);
        Return_Date.setValue(null);
        Book_id.clear();
        Book_Title.clear();
        Author.clear();
        Availability.clear();
    }
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }



    @FXML
    void handleCancel(ActionEvent event) {
        First_Name.clear();
        Last_Name.clear();
        Membership_ID.clear();
        Pickup_Date.setValue(null);
        Return_Date.setValue(null);
        System.out.println("All fields have been cleared.");
    }

    public void handleClose(ActionEvent event) {

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
    }
}
