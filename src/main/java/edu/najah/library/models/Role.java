package edu.najah.library.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements Serializable {
    @Id
    @Column(name = "role")
    private String role;
    @ManyToMany
    @JoinTable(
            name = "role_permissions", // Join table
            joinColumns = @JoinColumn(name = "role"), // Foreign key to Role
            inverseJoinColumns = @JoinColumn(name = "permission") // Foreign key to Permission
    )
    private Set<Permission> permissions; // Many-to-Many relation with Permission

    public Role() {
        //empty
    }
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public boolean hasPermission(String permission) {
        for (Permission permissionObj : permissions) {
            if (permissionObj.getPermission().equals(permission)) {
                return true;
            }
        }
        return false;
    }
}