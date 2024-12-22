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
    public List<Object[]> getAllBooks() {
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        List<Object[]> results = session.createQuery("select b.id, b.title, b.author, b.imageUrl from Book b", Object[].class).list();
        return results;
    }

}
