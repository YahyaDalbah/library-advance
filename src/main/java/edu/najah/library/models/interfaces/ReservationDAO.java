package edu.najah.library.models.interfaces;

import edu.najah.library.models.Reservation;

import java.util.List;

public interface ReservationDAO {
    void saveReservation(Reservation reservation);
    int getBooksReservedByStudentId(int StudentId);
    int getBooksReturnedByStudentId(int StudentId);
    int getOverdueBooksByStudentId(int StudentId);
    List<Reservation> getAllReservations(int studentId);
}
