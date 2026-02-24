package ru.kata.spring.boot_security.demo.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.DAO.UserDAO;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userDAO.findAll();
    }


    @Override
    @Transactional
    public void save(User user) {
        userDAO.save(user);

    }

    @Override
    @Transactional(readOnly = true)
    public User findById(int id) {
        return userDAO.findById(id);
    }


    @Override
    @Transactional
    public void delete(int id) {
        userDAO.delete(id);

    }
}
