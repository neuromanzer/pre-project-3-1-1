package application.boot.service;

import application.boot.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAllUsers();

    User getUserById(Long userId);

    User getUserByName(String name);

    void addUser(User user);

    void editUser(User user);

    void deleteUserById(Long id);
}
