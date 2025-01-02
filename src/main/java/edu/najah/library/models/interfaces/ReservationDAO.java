package edu.najah.library.models.interfaces;

import edu.najah.library.models.Reservation;

public interface ReservationDAO {
    void saveReservation(Reservation reservation);
    int getBooksReservedByUserId(int userId);
    /*int getBooksReturnedByUserId(int userId);
    int getOverdueBooksByUserId(int userId);*/
}
