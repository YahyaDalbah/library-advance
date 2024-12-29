package edu.najah.library.utils;

import edu.najah.library.models.User;

public class Register {

    private static Register instance = null;

    private User currentUser = null;

    // Private constructor to prevent instantiation
    private Register() {}

    // Method to get the singleton instance
    public static Register getInstance() {
        if (instance == null) {
            instance = new Register();
        }
        return instance;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        if (this.currentUser == null) {
            return null;
        }
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    // logout functionality
    public void clearUser() {
        this.currentUser = null;
    }
}
