package ru.kata.spring.boot_security.demo.service;




import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    public void save(User user);

    public User findById(int id);

    public void delete(int id);
}
