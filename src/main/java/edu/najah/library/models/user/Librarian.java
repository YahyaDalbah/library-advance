package edu.najah.library.models.user;
import edu.najah.library.utils.Role;

import javax.persistence.*;

@Entity
@Table(name = "librarians")
public class Librarian extends User {
    //added `id` and no-args constructor to solve hibernate errors
    @Column
    private String password;

    public Librarian() {
        super();
    }
    public Librarian(String name, String email, String password) {
        super(name, email);
        this.password = password;

    }
    @Override
    public Role getRole() {
        return Role.librarian;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
