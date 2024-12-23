package edu.najah.library.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class ReservationBook {


    @FXML
    private TextField Book_id;

    @FXML
    private TextField Book_Title;

    @FXML
    private TextField Author;

    @FXML
    private TextField Availability;

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
    private ImageView Image_Book;

    @FXML
    private Button Cancel_btn;

    @FXML
    private Button Reservation_btn;

    @FXML
    private ListView<String> listView;


    @FXML
    void handleCancel(ActionEvent event) {
        First_Name.clear();
        Last_Name.clear();
        Membership_ID.clear();
        Pickup_Date.setValue(null);
        Return_Date.setValue(null);
        System.out.println("All fields have been cleared.");
    }


    @FXML
    void handleReservation(ActionEvent event) {

        System.out.println("Reservation Details:");
        System.out.println("First Name: " + First_Name.getText());
        System.out.println("Last Name: " + Last_Name.getText());
        System.out.println("Membership ID: " + Membership_ID.getText());
        System.out.println("Pickup Date: " + Pickup_Date.getValue());
        System.out.println("Return Date: " + Return_Date.getValue());
        System.out.println("Book ID: " + Book_id.getText());
        System.out.println("Book Title: " + Book_Title.getText());
        System.out.println("Author: " + Author.getText());
        System.out.println("Availability: " + Availability.getText());
    }
}
