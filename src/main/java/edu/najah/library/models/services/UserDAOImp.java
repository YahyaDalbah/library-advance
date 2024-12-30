package edu.najah.library.models.services;

import edu.najah.library.models.interfaces.UserDAO;
import edu.najah.library.models.User;

import edu.najah.library.utils.HibernateUtil;
import edu.najah.library.utils.Roles;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDAOImp implements UserDAO {

    @Override
    public User getUserById(int id) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            return session.get(User.class, id);
        }
    }

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
    public List<User> getAllByRole(Roles role) {
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        return session.createQuery("from User u WHERE u.role.role = :role", User.class)
                .setParameter("role", role.name())
                .getResultList();
    }

    @Override
    public List<User> getAllUsers() {
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        return session.createQuery("from User", User.class).getResultList();
    }

    @Override
    public User getUserByEmail(String email) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        User user = session.createQuery("FROM User WHERE email = :email", User.class)
                .setParameter("email", email)
                .uniqueResult();
        return user;
    }
    public void updateUser(User user){
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        //change database
        session.update(user);
        tx.commit();
    }
}
