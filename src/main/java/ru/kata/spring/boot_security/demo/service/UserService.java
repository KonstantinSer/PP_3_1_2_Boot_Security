package ru.kata.spring.boot_security.demo.service;




import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> findAll();
    User findById(Long id);
    void delete(Long id);
    User findByUsername(String username);
    void updateUser(User user, List<Long> roleIds);
    void newUser(User user, List<Long> roleIds);
    List<Role> getAllRoles();
    }

