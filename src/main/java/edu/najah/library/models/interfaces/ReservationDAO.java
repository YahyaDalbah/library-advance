package edu.najah.library.models.interfaces;

import edu.najah.library.models.Reservation;
import java.time.LocalDate;
import java.util.List;


public interface ReservationDAO {
    void saveReservation(Reservation reservation);
    List<Reservation> getReservationsByDateRange(LocalDate startDate, LocalDate endDate);
    List<Reservation> getLatestReservations(int limit);
    int getBooksReservedByStudentId(int StudentId);
    int getBooksReturnedByStudentId(int StudentId);
    int getOverdueBooksByStudentId(int StudentId);
    List<Reservation> getAllReservations(int studentId);

}
