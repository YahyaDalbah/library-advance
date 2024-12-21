package edu.najah.library.models.user;
import edu.najah.library.utils.Role;

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
    @Override
    public Role getRole() {
        return Role.admin;
    }
}
