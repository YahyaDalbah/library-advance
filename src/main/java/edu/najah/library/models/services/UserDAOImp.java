package edu.najah.library.models.services;

import edu.najah.library.models.interfaces.UserDAO;
import edu.najah.library.models.user.User;

import edu.najah.library.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDAOImp implements UserDAO {

    @Override
    public void save(User user) {
        HibernateUtil.getInstance();
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    //needs a LibrarianDAOImp probably
//    @Override
//    public List<User> getAllUsers() {
//        HibernateUtil.getInstance();
//        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//        Session session = sessionFactory.openSession();
//        return session.createQuery("from Librarian", Librarian.class).getResultList();
//    }
}
