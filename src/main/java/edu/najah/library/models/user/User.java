package edu.najah.library.models.user;

import edu.najah.library.utils.Role;

import javax.persistence.*;

@MappedSuperclass
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    //maybe remove it
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(){
        //empty constructor for hibernate
    }
    public User(String name, String email, Role role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

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
}

