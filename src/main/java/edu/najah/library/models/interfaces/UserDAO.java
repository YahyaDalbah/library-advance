package edu.najah.library.models.interfaces;

import edu.najah.library.models.user.User;
import edu.najah.library.utils.Roles;

import java.awt.print.Pageable;
import java.util.List;

public interface UserDAO {

    public void save(User user);
    public List<User> getAllByRole(Roles role);
    public List<User> getAllUsers();
}
