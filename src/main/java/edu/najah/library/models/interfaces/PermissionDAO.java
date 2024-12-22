package edu.najah.library.models.interfaces;

import edu.najah.library.models.Permission;

import java.util.List;

public interface PermissionDAO {

    public void save(Permission permission);
    public List<Permission> getAll();

}