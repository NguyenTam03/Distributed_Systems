package com.springMVC;

import com.springMVC.dao.UserDao;
import com.springMVC.dao.impl.UserImpl;
import com.springMVC.entity.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserImpl userDao = new UserImpl();
        List<User> users = userDao.getListUser();
        for (User user : users) {
            System.out.println(user.toString());
        }
    }
}