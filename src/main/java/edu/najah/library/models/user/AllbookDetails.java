package edu.najah.library.models.user;

import javax.persistence.*;


@Entity
@Table(name = "Book")
public class AllbookDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "rating")
    private double rating;

    @Column(name = "year")
    private int year;

    @Column(name = "type")
    private String type;

    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "image")
    private byte[] image; // New field to store image as BLOB

    // Getters and Setters
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}

