package application.boot.dao;

import application.boot.model.Role;

import java.util.List;

public interface RoleDao {
    Role getByName(String name);

    List<Role> getAll();

    void add(Role role);
}
