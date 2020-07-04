package application.boot.dao;

import application.boot.model.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByName(String name);

    void addUser(User user);

    void editUser(User user);

    void deleteUser(User user);

}
