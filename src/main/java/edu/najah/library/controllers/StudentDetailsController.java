package edu.najah.library.controllers;

import edu.najah.library.models.User;
import edu.najah.library.models.interfaces.UserDAO;
import edu.najah.library.models.services.UserDAOImp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

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
    private void handelBack(MouseEvent event) {
        loadFullScene(event, "dashboard-view.fxml", "Dashboard");
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
            Stage popupStage = new Stage();
            popupStage.setTitle("Update Student");
            popupStage.setScene(new Scene(updateStudentRoot));
            popupStage.initOwner(EditProfile.getScene().getWindow());
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.initStyle(StageStyle.UTILITY);
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private UserDAO studentDAO = new UserDAOImp();
    private int studentId;

    public void setStudentId(int id) {
        this.studentId = id;
        loadStudentDetails();
    }

    private void loadStudentDetails() {
        User student = studentDAO.getUserById(studentId);
        if (student != null) {
            studentName.setText(student.getName());
            studentEmail.setText(student.getEmail());
//            booksReserved.setText(String.valueOf(student.getBooksReserved()));
//            booksReturned.setText(String.valueOf(student.getBooksReturned()));
//            overdueBooks.setText(String.valueOf(student.getOverdueBooks()));
        }
    }


}
