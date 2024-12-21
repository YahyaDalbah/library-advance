package edu.najah.library.models.services;

import edu.najah.library.models.interfaces.UserDAO;
import edu.najah.library.models.user.Librarian;
import edu.najah.library.models.user.Student;
import edu.najah.library.models.user.User;

import edu.najah.library.utils.HibernateUtil;
import edu.najah.library.utils.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDAOImp implements UserDAO {

    @Override
    public void save(User user) {
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }
    @Override
    public List<User> getAll(Role role) {
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        List<User> users = List.of();
        switch (role){
            case student:
//                users = session.createQuery("from Student", User.class).getResultList();
                break;
            case librarian:
                users = session.createQuery("from Librarian", User.class).getResultList();
                break;
            case admin:
                users = session.createQuery("from Admin", User.class).getResultList();
        }
        return users;
    }
}
