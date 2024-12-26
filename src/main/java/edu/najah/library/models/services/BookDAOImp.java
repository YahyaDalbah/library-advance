package edu.najah.library.models.services;
import edu.najah.library.controllers.AllBooksPageController;
import edu.najah.library.models.Book;
import edu.najah.library.models.interfaces.BookDAO;
import edu.najah.library.utils.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImp implements BookDAO {

    @FXML
    private static TilePane BooksTilePane;
    @Override
    public void insert(Book book) {
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(book);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Book> getAllBooks() {
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        return session.createQuery("select b from Book b").list();
    }

    @Override
    public void deleteBookById(int id) {
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Book bookToDelete = session.get(Book.class, id);
        session.delete(bookToDelete);
        session.getTransaction().commit();
        session.close();
    }
    @Override
    public Book getBookById(int id) {
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        Book book = session.get(Book.class, id);
        session.close();
        return book;
    }



    public List<Book> searchBooks(String query) {
        List<Book> books = new ArrayList<>();
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();


            String hql = "FROM Book b WHERE b.title LIKE :query OR b.author LIKE :query";
            Query<Book> queryObj = session.createQuery(hql, Book.class);
            queryObj.setParameter("query", "%" + query + "%");


            // Execute the query and get the result list
            books = queryObj.getResultList();


            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return books;
    }


        public void loadBooks(AllBooksPageController controller) {
            ObservableList<VBox> books = FXCollections.observableArrayList();

            SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
            Session session = sessionFactory.openSession();

            Query<Book> query = session.createQuery("from Book", Book.class);
            List<Book> resultList = query.list();

            for (Book book : resultList) {
                VBox bookVBox = new VBox(10);
                bookVBox.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-border-color: white; " +
                        "-fx-border-width: 1px; -fx-border-radius: 5px;");

                // Extract book details
                int bookId = book.getId();
                String title = book.getTitle();
                String author = book.getAuthor();
                byte[] imageBytes = book.getImage();
                double rating = Double.parseDouble(book.getRating());
                String description = book.getDescription();
                String type = book.getType();
                int year = book.getYear();

                if (imageBytes != null) {
                    Image image = new Image(new ByteArrayInputStream(imageBytes));
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(200);
                    imageView.setPreserveRatio(true);
                    bookVBox.getChildren().add(imageView);
                } else {
                    Label imageErrorLabel = new Label("Image not found");
                    bookVBox.getChildren().add(imageErrorLabel);
                }

                Label titleLabel = new Label(title);
                titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
                bookVBox.getChildren().add(titleLabel);

                Label authorLabel = new Label(author);
                authorLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");
                bookVBox.getChildren().add(authorLabel);

                // Add click event handler
                bookVBox.setOnMouseClicked(event -> {
                    try {
                        controller.navigateToBookDetails(event, bookId, title, author, imageBytes, rating, description, type, year);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                books.add(bookVBox);
            }

            controller.BooksTilePane.getChildren().setAll(books);
        }

}
