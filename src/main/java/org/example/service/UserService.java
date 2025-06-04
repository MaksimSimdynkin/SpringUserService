package org.example.service;

import org.example.dto.UserDao;
import org.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDao {
    private final UserDao userDao;
    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }
    @Override
    public Iterable<User> findAll() {
        return userDao.findAll();
    }

    public void getById(Long id) {

    }

}
