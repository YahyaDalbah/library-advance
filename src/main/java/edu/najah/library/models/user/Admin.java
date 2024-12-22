package edu.najah.library.models.user;
import edu.najah.library.utils.Roles;

import javax.persistence.*;

@Entity
@DiscriminatorValue("admin")
public class Admin extends User {

    public Admin() {
        super();
    }
    public Admin(String name, String email, String password) {
        super(name, email, password);


    }
}
