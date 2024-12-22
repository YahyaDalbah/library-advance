package edu.najah.library.models.services;

import edu.najah.library.models.interfaces.RoleDAO;
import edu.najah.library.models.Role;

import edu.najah.library.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class RoleDAOImp implements RoleDAO {

    @Override
    public void save(Role user) {
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }
    @Override
    public List<Role> getAll() {
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        return session.createQuery("from Role", Role.class).getResultList();
    }
}
