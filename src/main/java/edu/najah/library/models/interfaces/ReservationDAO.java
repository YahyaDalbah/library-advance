package edu.najah.library.models.interfaces;

import edu.najah.library.models.Reservation;
import java.time.LocalDate;
import java.util.List;


public interface ReservationDAO {
    void saveReservation(Reservation reservation);
    List<Reservation> getReservationsByDateRange(LocalDate startDate, LocalDate endDate);
    List<Reservation> getLatestReservations(int limit);

}
