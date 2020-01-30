package com.cris.myshop.service.impl;

import com.cris.myshop.dao.UserDao;
import com.cris.myshop.entity.User;
import com.cris.myshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User getUserByUsername(User user) {
        return userDao.getUserByUsername(user);
    }
}
