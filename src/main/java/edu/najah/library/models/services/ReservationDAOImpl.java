package edu.najah.library.models.services;

import edu.najah.library.models.Reservation;
import edu.najah.library.models.interfaces.ReservationDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class ReservationDAOImpl implements ReservationDAO {

    private SessionFactory factory;

    public ReservationDAOImpl(SessionFactory factory) {
        this.factory = factory;
    }


    @Override
    public void saveReservation(Reservation reservation) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(reservation);
            transaction.commit();
        }
    }
}
