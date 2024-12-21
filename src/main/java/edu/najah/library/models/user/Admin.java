package edu.najah.library.models.user;
import edu.najah.library.utils.Role;

import javax.persistence.*;

@Entity
@Table(name = "admins")
public class Admin extends User {
    //added `id` and no-args constructor to solve hibernate errors
    @Column
    private String password;

    public Admin() {
        super();
    }
    public Admin(String name, String email, String password) {
        super(name, email);
        this.password = password;

    }
    @Override
    public Role getRole() {
        return Role.admin;
    }

    public String getPassword() {
        return password;
    }
}
