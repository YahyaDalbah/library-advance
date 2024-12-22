package edu.najah.library.utils;

import edu.najah.library.models.user.User;

public class LoggedInUser {

    private static LoggedInUser instance = null;

    private User currentUser = null;

    // Private constructor to prevent instantiation
    private LoggedInUser() {}

    // Method to get the singleton instance
    public static LoggedInUser getInstance() {
        if (instance == null) {
            instance = new LoggedInUser();
        }
        return instance;
    }

    public void setUser(User user) {
        this.currentUser = user;
    }

    public User getUser() {
        if (this.currentUser == null) {
            return null;
        }
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    // Clear the logged-in user (logout functionality)
    public void clearUser() {
        this.currentUser = null;
    }
}
