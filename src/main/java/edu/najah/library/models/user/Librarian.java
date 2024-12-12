package edu.najah.library.models.user;
import javax.persistence.*;

@Entity
@Table(name = "librarians")
public class Librarian extends User {
    //added `id` and no-args constructor to solve hibernate errors


    public Librarian() {
        super();
    }

    public Librarian(String name) {
        super(name);

    }
}
