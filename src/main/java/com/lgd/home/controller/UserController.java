package com.lgd.home.controller;

import com.lgd.home.dao.UserDao;
import com.lgd.home.model.User;
import com.lgd.home.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LGD on 2019/08/14.111
 */
@Controller
@RequestMapping(value = "test")
@EnableAutoConfiguration
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "user")
    @ResponseBody
    public List<User> getUsers(){
        return userService.selectUsers();
    }

    @RequestMapping(value = "finduser")
    @ResponseBody
    public Object getUserByName(String uname){
        System.out.println(uname);
        Map<String,Object> map=new HashMap<>();
        map.put("uname",uname);
        User user=userDao.selectUserByName(map);
        return user.toString();
    }

    @RequestMapping(value = "findList")
    @ResponseBody
    public Object findUsers(String uname){
        System.out.println(uname);
        Map<String,Object> map=new HashMap<>();
        map.put("uname",uname);
        map.put("currpage",1);
        map.put("pagesize",1);
        List<User> users=userDao.selectUserslistPage(map);
        return users.toString();
    }

    @RequestMapping(value = "log")
    public Object login(){
        System.out.println("121");
        return "views/login";
    }
}
