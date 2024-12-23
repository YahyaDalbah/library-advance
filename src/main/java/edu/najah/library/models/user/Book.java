package edu.najah.library.models.user;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "author")
    private String author;

    @Column(name = "description")
    private String description;

    @Column(name = "date")
    private Date date;

    @Column(name = "year")
    private int year;

    @Column(name = "type")
    private String type;

    @Column(name = "availability")
    private String availability;

    @Column(name = "imageUrl")
    private String imageUrl;

    @Column(name = "rating")
    private String rating;

    public Book() {}

    // Getter and Setter for image
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getYear() {
        return year;
    }

    // Builder class
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
            return book;
        }
    }

    // Getter methods
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

    public Date getDate(String year) {
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
}
