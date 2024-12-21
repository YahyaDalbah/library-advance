package edu.najah.library.controllers;

import edu.najah.library.models.Book;
import edu.najah.library.models.Reservation;
import edu.najah.library.models.interfaces.BookDAO;
import edu.najah.library.models.interfaces.ReservationDAO;
import edu.najah.library.models.services.BookDAOImpl;
import edu.najah.library.models.services.ReservationDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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
        factory = new Configuration().configure().addAnnotatedClass(Book.class).addAnnotatedClass(Reservation.class).buildSessionFactory();
        bookDAO = new BookDAOImpl(factory);
        reservationDAO = new ReservationDAOImpl(factory);

        Search_Box.textProperty().addListener((observable, oldValue, newValue) -> searchBooks(newValue));
        List_Search.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedItem) -> {
            if (selectedItem != null) fetchBookDetails(selectedItem);
        });
    }
    private void searchBooks(String query) {
        List<Book> books = bookDAO.findBooksByTitle(query);

        // تحديد الحد الأقصى للنتائج الظاهرة إلى 5 كتب فقط
        List_Search.getItems().clear();
        int limit = Math.min(books.size(), 5);
        for (int i = 0; i < limit; i++) {
            List_Search.getItems().add(books.get(i).getTitle());
        }

        // جعل الـ ListView مرئية فقط عند وجود نتائج
        List_Search.setVisible(!List_Search.getItems().isEmpty());
    }
    private void fetchBookDetails(String title) {
        Book book = bookDAO.findBooksByTitle(title).get(0);
        if (book != null) {
            Book_id.setText(String.valueOf(book.getId()));
            Book_Title.setText(book.getTitle());
            Author.setText(book.getAuthor());
            Availability.setText(book.isAvailability() ? "Available" : "Unavailable");
        }
    }

    @FXML
    void handleReservation(ActionEvent event) {
        // التحقق من أن الحقول ليست فارغة
        if (First_Name.getText().isEmpty() || Last_Name.getText().isEmpty() || Membership_ID.getText().isEmpty() ||
                Pickup_Date.getValue() == null || Return_Date.getValue() == null || Book_id.getText().isEmpty()) {
            showAlert("Error", "All fields are required.", Alert.AlertType.ERROR);
            return;
        }

        // تخزين البيانات في الكائنات المناسبة
        Reservation reservation = new Reservation();
        reservation.setFirstName(First_Name.getText());
        reservation.setLastName(Last_Name.getText());
        reservation.setMembershipId(Membership_ID.getText());
        reservation.setPickupDate(Pickup_Date.getValue());
        reservation.setReturnDate(Return_Date.getValue());

        // جلب الكتاب من قاعدة البيانات
        Book book = bookDAO.getBookById(Integer.parseInt(Book_id.getText()));
        if (book == null) {
            showAlert("Error", "Book not found.", Alert.AlertType.ERROR);
            return;
        }
        reservation.setBook(book);

        // تخزين الحجز في قاعدة البيانات
        reservationDAO.saveReservation(reservation);

        // إظهار رسالة نجاح
        showAlert("Success", "Reservation saved successfully!", Alert.AlertType.INFORMATION);

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
        try {

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();


            FXMLLoader loader = new FXMLLoader(getClass().getResource("edu/najah/library/Dashboard.fxml"));
            Parent root = loader.load();

            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("Dashboard-view");
            dashboardStage.setScene(new Scene(root));
            dashboardStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
