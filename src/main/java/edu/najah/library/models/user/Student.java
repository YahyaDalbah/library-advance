package edu.najah.library.models.user;

import edu.najah.library.utils.Role;

public class Student extends User {

    public Student(String name,String email, Role role) {
        super(name, email, role);
    }

}
