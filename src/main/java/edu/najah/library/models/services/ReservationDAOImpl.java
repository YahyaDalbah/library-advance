package edu.najah.library.models.services;

import edu.najah.library.models.Reservation;
import edu.najah.library.models.interfaces.ReservationDAO;
import edu.najah.library.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;


public class ReservationDAOImpl implements ReservationDAO {
    @Override
    public void saveReservation(Reservation reservation) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(reservation);
            session.getTransaction().commit();
        }
    }

    @Override
    public int getBooksReservedByStudentId(int studentId) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try {
            String hql = "SELECT COUNT(r) FROM Reservation r WHERE r.membershipId = :studentId AND r.actualReturnDate IS NULL";
            Long count = (Long) session.createQuery(hql)
                    .setParameter("studentId", studentId)
                    .uniqueResult();

            return count != null ? count.intValue() : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            session.close();
        }
    }

    @Override
    public int getBooksReturnedByStudentId(int studentId) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try {
            String hql = "SELECT COUNT(r) FROM Reservation r WHERE r.membershipId = :studentId AND r.actualReturnDate IS NOT NULL";
            Long count = (Long) session.createQuery(hql)
                    .setParameter("studentId", studentId)
                    .uniqueResult();

            return count != null ? count.intValue() : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            session.close();
        }
    }

    @Override
    public int getOverdueBooksByStudentId(int studentId) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try {
            String hql = "SELECT COUNT(r) FROM Reservation r WHERE r.membershipId = :studentId AND r.actualReturnDate IS NULL AND r.returnDate < CURRENT_DATE ";
            Long count = (Long) session.createQuery(hql)
                    .setParameter("studentId", studentId)
                    .uniqueResult();

            return count != null ? count.intValue() : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            session.close();
        }
    }

@Override
public List<Reservation> getAllReservations(int studentId) {
    Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
    // Query to get all reservations, both current and past
    List<Reservation> reservations = session.createQuery("FROM Reservation r WHERE r.membershipId = :studentId", Reservation.class)
            .setParameter("studentId", studentId)
            .getResultList();
    session.close();
    return reservations;
}


}
