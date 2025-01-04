package edu.najah.library.models.services;

import edu.najah.library.models.Reservation;
import edu.najah.library.models.interfaces.ReservationDAO;
import edu.najah.library.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;


public class ReservationDAOImpl implements ReservationDAO {

    @Override
    public List<Reservation> getLatestReservations(int limit) {
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM Reservation r ORDER BY r.pickupDate DESC";
            return session.createQuery(hql, Reservation.class)
                    .setMaxResults(limit)
                    .getResultList();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Reservation> getReservationsByDateRange(LocalDate startDate, LocalDate endDate) {
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM Reservation r WHERE r.pickupDate BETWEEN :startDate AND :endDate";
            Query<Reservation> query = session.createQuery(hql, Reservation.class);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            return query.getResultList();
        } finally {
            session.close();
        }
    }


    @Override
    public void saveReservation(Reservation reservation) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(reservation);
            session.getTransaction().commit();
        }
    }

}
