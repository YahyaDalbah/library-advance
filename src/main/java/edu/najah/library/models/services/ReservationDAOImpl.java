package edu.najah.library.models.services;

import edu.najah.library.models.Reservation;
import edu.najah.library.models.interfaces.ReservationDAO;
import edu.najah.library.utils.HibernateUtil;
import org.hibernate.Session;


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
    public int getBooksReservedByUserId(int userId) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        Long count = session.createQuery(
                        "SELECT COUNT(r) FROM Reservation r " +
                                "WHERE User.id = :membershipId " +
                                "AND r.actualReturnDate IS NULL",
                        Long.class)
                .setParameter("membershipId", userId)
                .uniqueResult();
        session.close();
        return count != null ? count.intValue() : 0;
    }
    /*
    @Override
    public int getBooksReturnedByUserId(int userId) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        Long count = session.createQuery(
                        "SELECT COUNT(*) FROM Reservation " +
                                "WHERE User.id = :membershipId AND actualReturnDate IS NOT NULL",
                        Long.class)
                .setParameter("membershipId", userId)
                .uniqueResult();
        session.close();
        return count != null ? count.intValue() : 0;
    }
    @Override
    public int getOverdueBooksByUserId(int userId) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        Long count = session.createQuery(
                        "SELECT COUNT(*) FROM Reservation " +
                                "WHERE membershipId = :userId " +
                                "AND actualReturnDate IS NULL " +
                                "AND returnDate < CURRENT_DATE",
                        Long.class)
                .setParameter("userId", userId)
                .uniqueResult();
        session.close();
        return count != null ? count.intValue() : 0;
    }
*/

}
