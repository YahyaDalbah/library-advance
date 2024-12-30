package edu.najah.library.models.interfaces;

import edu.najah.library.models.User;
import edu.najah.library.utils.Roles;


import java.util.List;

public interface UserDAO {

    public void save(User user);

    public User getUserById(int id) ;
    List<User> getAllByRole(Roles role);

    public List<User> getAllUsers();
    public User getUserByEmail(String email);
    public void updateUser(User user);

}
