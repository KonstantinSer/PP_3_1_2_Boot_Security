package ru.kata.spring.boot_security.demo.DAO;




import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDAO {

    List<User> findAll();

    void save(User user);

    void delete(int id);

    User findById(int id);
}
