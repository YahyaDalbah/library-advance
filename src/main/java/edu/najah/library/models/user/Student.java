package edu.najah.library.models.user;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class Student extends User {

    private int id;
    private StringProperty email;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    // Constructor
    public Student(int id ,String name, String email) {
        super(name, email);  // Call the parent constructor to initialize name and email
        this.email = new SimpleStringProperty(email);  // Initialize StringProperty for email
        this.id=id;
    }

    // Getter for name
    @Override
    public String getName() {
        return super.getName();  // Use the parent class' getName() method
    }



    // Getter for email
    public String getEmail() {
        return email.get();  // Return the value of the email
    }

    // Getter for emailProperty (for JavaFX binding)
    public StringProperty emailProperty() {
        return email;  // Return the StringProperty for email
    }

    // Overriding the getPassword method as students don't have passwords
    @Override
    public String getPassword() {
        throw new UnsupportedOperationException("Students don't have passwords");
    }
}
