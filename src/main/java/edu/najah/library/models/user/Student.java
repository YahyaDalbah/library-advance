package edu.najah.library.models.user;

import edu.najah.library.utils.Role;

public class Student extends User {

    public Student(String name,String email) {
        super(name, email);
    }

    @Override
    public Role getRole() {
        return Role.student;
    }

    //just a placeholder]
    @Override
    public String getPassword() {
        throw new UnsupportedOperationException("students don't have passwords");
    }
}
