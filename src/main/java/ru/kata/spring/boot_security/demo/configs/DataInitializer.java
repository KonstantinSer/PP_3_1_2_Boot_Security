package ru.kata.spring.boot_security.demo.configs;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.DAO.RoleRepository;
import ru.kata.spring.boot_security.demo.DAO.UserRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public DataInitializer(UserRepository userRepository,
                           RoleRepository roleRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("========== НАЧАЛО ИНИЦИАЛИЗАЦИИ ДАННЫХ ==========");

        // ШАГ 1: СОЗДАЕМ РОЛИ
        Role roleUser = null;
        Role roleAdmin = null;

        try {
            // Создаем ROLE_USER
            if (!roleRepository.existsByName("ROLE_USER")) {
                roleUser = new Role("ROLE_USER");
                roleRepository.save(roleUser);
                System.out.println("✅ Создана роль: ROLE_USER");
            } else {
                roleUser = roleRepository.findByName("ROLE_USER")
                        .orElseThrow(() -> new RuntimeException("ROLE_USER не найдена"));
                System.out.println("✅ Роль ROLE_USER уже существует");
            }

            // Создаем ROLE_ADMIN
            if (!roleRepository.existsByName("ROLE_ADMIN")) {
                roleAdmin = new Role("ROLE_ADMIN");
                roleRepository.save(roleAdmin);
                System.out.println("✅ Создана роль: ROLE_ADMIN");
            } else {
                roleAdmin = roleRepository.findByName("ROLE_ADMIN")
                        .orElseThrow(() -> new RuntimeException("ROLE_ADMIN не найдена"));
                System.out.println("✅ Роль ROLE_ADMIN уже существует");
            }
        } catch (Exception e) {
            System.err.println("❌ Ошибка при создании ролей: " + e.getMessage());
            e.printStackTrace();
            return;
        }


        try {

            User existingUser = userRepository.findByUsername("user");
            if (existingUser == null) {
                User user = new User();
                user.setUsername("user");
                user.setPassword(bCryptPasswordEncoder.encode("user"));
                user.setEmail("user@mail.com");
                user.setAge(25);

                Set<Role> userRoles = new HashSet<>();
                userRoles.add(roleUser);
                user.setRoles(userRoles);

                userRepository.save(user);
                System.out.println("✅ Создан пользователь: user / user (роль: USER)");
            } else {
                System.out.println("✅ Пользователь 'user' уже существует");
            }

            User existingAdmin = userRepository.findByUsername("admin");
            if (existingAdmin == null) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(bCryptPasswordEncoder.encode("admin"));
                admin.setEmail("admin@mail.com");
                admin.setAge(35);

                Set<Role> adminRoles = new HashSet<>();
                adminRoles.add(roleUser);
                adminRoles.add(roleAdmin);
                admin.setRoles(adminRoles);

                userRepository.save(admin);
                System.out.println("✅ Создан администратор: admin / admin (роли: USER, ADMIN)");
            } else {
                System.out.println("✅ Пользователь 'admin' уже существует");
            }
        } catch (Exception e) {
            System.err.println("❌ Ошибка при создании пользователей: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
