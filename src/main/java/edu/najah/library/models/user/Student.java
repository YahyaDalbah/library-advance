package edu.najah.library.models.user;

import edu.najah.library.utils.Roles;

public class Student extends User {

    public Student(String name,String email) {
        super(name, email);
    }

    @Override
    public String getPassword() {
        throw new UnsupportedOperationException("students don't have passwords");
    }
}