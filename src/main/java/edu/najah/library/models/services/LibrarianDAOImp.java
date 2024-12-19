package edu.najah.library.models.services;

import edu.najah.library.models.interfaces.LibrarianDAO;
import edu.najah.library.models.user.Librarian;
import edu.najah.library.models.user.User;
import edu.najah.library.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class LibrarianDAOImp implements LibrarianDAO {
    @Override
    public List<Librarian> getAllLibrarians() {
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        List<Librarian> librarians = session.createQuery("from Librarian", Librarian.class).getResultList();
        return librarians;
    }

}
