package application.boot.service;

import application.boot.dao.RoleDao;
import application.boot.dao.UserDao;
import application.boot.model.Role;
import application.boot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User getUserById(Long userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public User getUserByName(String name) {
        return userDao.getUserByName(name);
    }

    @Override
    public void addUser(User user) {
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        userDao.addUser(user.setRoles(updateRoles(user.getRoles())));
    }

    @Override
    public void editUser(User user) {
        if (!user.getPassword().startsWith("$2a$10$")) {
            String password = user.getPassword();
            user.setPassword(passwordEncoder.encode(password));
        }
        userDao.editUser(user.setRoles(updateRoles(user.getRoles())));
    }

    @Override
    public void deleteUserById(Long id) {
        User user = userDao.getUserById(id);
        userDao.deleteUser(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.getUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    private List<Role> updateRoles(List<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .map(roleDao::getByName)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
