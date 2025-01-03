package edu.najah.library.models;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservation_table")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "name")
    private String name;

    @Column(name = "Membership_ID")
    private int membershipId;
    @Column(name = "Pickup_Date")
    private LocalDate pickupDate;
    @Column(name = "Return_Date")
    private LocalDate returnDate;

    @Column(name = "Status")
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    public enum ReservationStatus {
        PENDING,
        OVERDUE,
        RETURNED,
    }

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(int membershipId) {
        this.membershipId = membershipId;
    }

    public LocalDate getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public ReservationStatus getStatus() {
        if (this.status == ReservationStatus.RETURNED) {
            return ReservationStatus.RETURNED;
        }
        if (returnDate != null) {
            return LocalDate.now().isAfter(returnDate) ?
                    ReservationStatus.OVERDUE :
                    ReservationStatus.PENDING;
        }
        return ReservationStatus.PENDING;
    }
}
