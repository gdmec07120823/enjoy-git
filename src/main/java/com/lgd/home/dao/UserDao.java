package com.lgd.home.dao;

import com.lgd.home.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created by LGD on 2019/08/14.
 */
public interface UserDao {
    List<User> selectUsers();
    List<User> selectUserslistPage(Map<String,Object> map);

    User selectUserByName(Map<String,Object> map);
}
