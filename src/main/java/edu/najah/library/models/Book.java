package edu.najah.library.models;

import javax.persistence.*;


@Entity
@Table(name = "bookreservation")
public class Book {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Book_id;

    @Column(name = "Book_Title")
    private String title;
    @Column(name = "Author")
    private String author;
    @Column(name = "Availability")
    private boolean availability;

    // Getters and Setters


    public int getId() {
        return Book_id;
    }

    public void setId(int id) {
        this.Book_id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}

