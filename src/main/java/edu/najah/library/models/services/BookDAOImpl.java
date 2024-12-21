package edu.najah.library.models.services;

import edu.najah.library.models.Book;
import edu.najah.library.models.interfaces.BookDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class BookDAOImpl implements BookDAO {

    private SessionFactory factory;

    public BookDAOImpl(SessionFactory factory) {
        this.factory = factory;
    }




    @Override
    public List<Book> findBooksByTitle(String title) {
        try (Session session = factory.openSession()) {
            String hql = "FROM Book WHERE title LIKE :title";
            return session.createQuery(hql, Book.class)
                    .setParameter("title", "%" + title + "%")
                    .getResultList();
        }
    }

    @Override
    public Book getBookById(int id) {
        try (Session session = factory.openSession()) {
            return session.get(Book.class, id);
        }
    }
    public void updateBook(Book book) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.update(book);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
