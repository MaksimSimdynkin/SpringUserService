package org.example.dao;


import org.example.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> findById(Long id);
    List<User> findAll();
    User save(User user);
    void update(User user);
    void delete(User user);
}