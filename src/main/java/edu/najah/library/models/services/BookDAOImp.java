package edu.najah.library.models.services;

import edu.najah.library.models.user.Book;
import edu.najah.library.models.interfaces.BookDAO;
import edu.najah.library.utils.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImp implements BookDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/booklibrary";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Establish the connection to the database using JDBC
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public List<Book> searchBooks(String query) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM book WHERE title LIKE ? OR author LIKE ?";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "%" + query + "%");
            statement.setString(2, "%" + query + "%");

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // Retrieving book data from resultSet
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String description = resultSet.getString("description");
                String imageUrl = resultSet.getString("imageUrl");
                String rating = resultSet.getString("rating");

                // Handling BLOB image
                Blob imageBlob = resultSet.getBlob("image");

                // Building the book object
                Book book = new Book.Builder()
                        .setTitle(title)
                        .setAuthor(author)
                        .setDescription(description)
                        .setImageUrl(imageUrl)
                        .setRating(rating)
                        .build();

                // Set the image from the BLOB field (if present)
                book.setImageFromBlob(imageBlob);

                // Now the book object has the image in its ImageView
                books.add(book);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }


        return books;
    }

    // Method to insert a book using Hibernate
    @Override
    public void insert(Book book) {
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(book);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();  // Consider using a logger here
        }
    }

    // Method to retrieve all books using Hibernate
    @Override
    public List<Object[]> getAllBooks() {
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            List<Object[]> results = session.createQuery("select b.id, b.title, b.author, b.imageUrl from Book b", Object[].class).list();
            return results;
        }
    }

}
