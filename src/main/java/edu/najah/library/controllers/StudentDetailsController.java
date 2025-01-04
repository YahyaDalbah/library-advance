package edu.najah.library.controllers;

import edu.najah.library.models.Reservation;
import edu.najah.library.models.User;
import edu.najah.library.models.interfaces.ReservationDAO;
import edu.najah.library.models.services.ReservationDAOImpl;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;

public class StudentDetailsController {
    @FXML
    private Label studentName;
    @FXML
    private Label studentEmail;
    @FXML
    private Label booksReserved;
    @FXML
    private Label booksReturned;
    @FXML
    private Label overdueBooks;
    @FXML
    private Button EditProfile;
    @FXML
    private TableView<Reservation>  allReservationsTable;
    @FXML
    private TableColumn<Reservation, String> bookColumn;

    @FXML
    private TableColumn<Reservation, String> startDateColumn;

    @FXML
    private TableColumn<Reservation, String> endDateColumn;

    @FXML
    private void handelBack(MouseEvent event) {
        loadFullScene(event, "studentTable.fxml", "studentTable");
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
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEdit(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/najah/library/update-student.fxml"));
            Parent updateStudentRoot = loader.load();
            UpdateStudentController controller = loader.getController();
            controller.setStudentDetailsController(this);
            controller.setStudent(student);
            Stage popupStage = new Stage();
            popupStage.setTitle("Update Student");
            popupStage.setScene(new Scene(updateStudentRoot));
            popupStage.setResizable(false);
            popupStage.initOwner(EditProfile.getScene().getWindow());
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.initStyle(StageStyle.UTILITY);
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void updateStudentDetails(User updatedStudent) {
        this.student = updatedStudent;
        loadStudentDetails();
        loadStudentTables();
    }

    private User student;
    ReservationDAO reservation = new ReservationDAOImpl();

    public void setStudent(User student) {
        this.student = student;
        loadStudentDetails();
        loadStudentTables();
    }

    private void loadStudentDetails() {
        if (student != null) {
            studentName.setText(student.getName());
            studentEmail.setText(student.getEmail());
            booksReserved.setText(String.valueOf(reservation.getBooksReservedByStudentId(student.getId())));
            booksReturned.setText(String.valueOf(reservation.getBooksReturnedByStudentId(student.getId())));
            overdueBooks.setText(String.valueOf(reservation.getOverdueBooksByStudentId(student.getId())));
        }
    }

    @FXML
    private void loadStudentTables() {
        bookColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getBook() != null ? cellData.getValue().getBook().getTitle() : "N/A");
        });

        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("pickupDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        List<Reservation> allReservations = reservation.getAllReservations(student.getId());

        ObservableList<Reservation> allObservableList = FXCollections.observableArrayList(allReservations);

        allReservationsTable.setItems(allObservableList);
    }

}
