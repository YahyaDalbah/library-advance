package edu.najah.library.models.services;

import edu.najah.library.models.user.Book;
import edu.najah.library.models.interfaces.BookDAO;
import edu.najah.library.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
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

}