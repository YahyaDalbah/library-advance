package edu.najah.library.models.services;

import edu.najah.library.models.user.Book;
import edu.najah.library.models.interfaces.BookDAO;
import edu.najah.library.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;

public class BookDAOImp implements BookDAO {
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

}
