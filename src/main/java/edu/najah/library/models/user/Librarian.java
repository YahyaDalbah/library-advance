package edu.najah.library.models.user;
import edu.najah.library.utils.Roles;

import javax.persistence.*;

@Entity
@DiscriminatorValue("librarian")
public class Librarian extends User {


    public Librarian() {
        super();
    }
    public Librarian(String name, String email, String password) {
        super(name, email, password);


    }
}
