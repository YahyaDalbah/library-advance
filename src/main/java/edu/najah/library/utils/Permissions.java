package edu.najah.library.utils;

public class Permissions {
    public static boolean canViewDashboard(){
        Register register = Register.getInstance();
        if(register.isLoggedIn()){
            return register.getCurrentUser().getRole() != Role.student;
        }
        return false;

    }
}
