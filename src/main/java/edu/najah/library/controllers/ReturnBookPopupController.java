package edu.najah.library.controllers;

import edu.najah.library.models.Reservation;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ReturnBookPopupController {
    @FXML
    private TextField bookTitleField;
    @FXML
    private TextField bookIdField;
    @FXML
    private TextField reservedByNameField;
    @FXML
    private TextField reservedByUserIdField;
    @FXML
    private TextField pickupDateField;
    @FXML
    private TextField returnDateField;
    @FXML
    private TextField statusField;
    @FXML
    private TextField billField;

    private Reservation reservation;
    private SessionFactory sessionFactory;
    private double lateFee = 0.0;

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
        populateFields();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private void populateFields() {
        if (reservation != null) {
            bookTitleField.setText(reservation.getBook().getTitle());
            bookIdField.setText(String.valueOf(reservation.getBook().getId()));
            reservedByNameField.setText(reservation.getName());
            reservedByUserIdField.setText(String.valueOf(reservation.getMembershipId()));
            pickupDateField.setText(reservation.getPickupDate().toString());
            returnDateField.setText(reservation.getReturnDate().toString());
            statusField.setText(reservation.getStatus().toString());

            // Calculate late fees if applicable
            if (reservation.getStatus() == Reservation.ReservationStatus.OVERDUE) {
                long daysLate = ChronoUnit.DAYS.between(reservation.getReturnDate(), LocalDate.now());
                lateFee = daysLate * 1.5; // $1.5 per day late
                billField.setText(String.format("$%.2f", lateFee));
            } else {
                billField.setText("$0.00");
            }
        }
    }

    @FXML
    private void handleReturn() {
        if (reservation != null && sessionFactory != null) {
            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();
                try {
                    // Update the book's availability
                    reservation.getBook().setAvailability("Available");
                    session.update(reservation.getBook());

                    // Update the reservation status
                    reservation.setStatus(Reservation.ReservationStatus.RETURNED);
                    session.update(reservation);

                    transaction.commit();

                    // Show success message
                    showAlert(Alert.AlertType.INFORMATION, "Success",
                            "Book returned successfully" +
                                    (lateFee > 0 ? "\nLate fee: $" + String.format("%.2f", lateFee) : ""));

                    // Close the popup
                    ((Stage) bookTitleField.getScene().getWindow()).close();

                } catch (Exception e) {
                    transaction.rollback();
                    showAlert(Alert.AlertType.ERROR, "Error",
                            "Failed to return book: " + e.getMessage());
                }
            }
        }
    }

    @FXML
    private void handleCancel() {
        ((Stage) bookTitleField.getScene().getWindow()).close();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}