package edu.najah.library.models.interfaces;

import edu.najah.library.models.user.Librarian;
import edu.najah.library.models.user.User;
import edu.najah.library.utils.Role;

import java.util.List;

public interface UserDAO {

    public void save(User user);
    public List<User> getAll(Role role);

}
