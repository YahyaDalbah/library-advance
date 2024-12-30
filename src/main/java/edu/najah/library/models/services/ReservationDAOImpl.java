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

}
