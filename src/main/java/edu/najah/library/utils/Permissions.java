package edu.najah.library.utils;

public class Permissions {
    public static boolean canViewDashboard(){
        LoggedInUser loggedInUser = LoggedInUser.getInstance();
        if(loggedInUser.isLoggedIn()){
            return loggedInUser.getUser().getRole() != Role.student;
        }
        return false;

    }
}
