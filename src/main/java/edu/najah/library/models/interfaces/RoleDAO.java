package edu.najah.library.models.interfaces;

import edu.najah.library.models.Role;

import java.util.List;

public interface RoleDAO {

    public void save(Role role);
    public List<Role> getAll();

}