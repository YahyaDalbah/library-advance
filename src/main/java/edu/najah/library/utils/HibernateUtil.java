package edu.najah.library.utils;

import edu.najah.library.models.Book;
import edu.najah.library.models.Permission;
import edu.najah.library.models.Role;
import edu.najah.library.models.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {

    private static HibernateUtil instance = null;
    private static SessionFactory sessionFactory;
    private static StandardServiceRegistry serviceRegistry;

    private HibernateUtil(){
        Configuration configuration = new Configuration();

        //add models to configuration
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Book.class);
        configuration.addAnnotatedClass(Role.class);
        configuration.addAnnotatedClass(Permission.class);

        configuration.configure();
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    public static HibernateUtil getInstance(){
        if(instance == null){
            instance  = new HibernateUtil();
        }
        return instance;
    }

    public synchronized SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}