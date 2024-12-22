package edu.najah.library.models.user;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Blob;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;

@Entity
@Table(name = "Book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "description")
    private String description;

    @Column(name = "date")
    private Date date;

    @Column(name = "type")
    private String type;

    @Column(name = "availability")
    private String availability;

    @Column(name = "imageUrl")
    private String imageUrl;

    @Column(name = "rating")
    private String rating;

    // Added to handle the BLOB image from the database
    @Transient
    private byte[] imageBytes; // Image as byte array

    @Transient
    private ImageView imageView;  // ImageView to hold image for UI display

    // Default constructor
    public Book() {}

    // Builder pattern for Book class
    public static class Builder {
        private int id;
        private String title;
        private String author;
        private String description;
        private Date date;
        private String type;
        private String availability;
        private String imageUrl;
        private String rating;
        private byte[] imageBytes;
        private ImageView imageView;

        // Builder methods to set each field
        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setAuthor(String author) {
            this.author = author;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setDate(Date date) {
            this.date = date;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setAvailability(String availability) {
            this.availability = availability;
            return this;
        }

        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder setRating(String rating) {
            this.rating = rating;
            return this;
        }

        public Builder setImageBytes(byte[] imageBytes) {
            this.imageBytes = imageBytes;
            return this;
        }

        public Builder setImageView(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        // Build method to return the Book object
        public Book build() {
            Book book = new Book();
            book.id = this.id;
            book.title = this.title;
            book.author = this.author;
            book.description = this.description;
            book.date = this.date;
            book.type = this.type;
            book.availability = this.availability;
            book.imageUrl = this.imageUrl;
            book.rating = this.rating;
            book.imageBytes = this.imageBytes;
            book.imageView = this.imageView;
            return book;
        }
    }

    // Getter methods for all fields
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getAvailability() {
        return availability;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getRating() {
        return rating;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public ImageView getImageView() {
        return imageView;
    }

    // Setter methods
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    // Method to convert BLOB to ImageView (if needed)
    public void setImageFromBlob(Blob imageBlob) throws IOException, SQLException {
        if (imageBlob != null) {
            this.imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
            this.imageView = new ImageView(new Image(new ByteArrayInputStream(imageBytes)));
        }
    }
}
