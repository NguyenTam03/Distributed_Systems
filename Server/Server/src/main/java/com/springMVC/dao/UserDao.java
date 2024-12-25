package com.springMVC.dao;

import com.springMVC.entity.User;

import java.util.List;

public interface UserDao {
    boolean addUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(int id);
    List<User> getListUser();
}

