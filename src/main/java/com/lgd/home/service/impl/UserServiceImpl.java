package com.lgd.home.service.impl;

import com.lgd.home.dao.UserDao;
import com.lgd.home.model.User;
import com.lgd.home.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LGD on 2019/08/21.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Override
    public List<User> selectUsers() {
        return userDao.selectUsers();

    }
}
