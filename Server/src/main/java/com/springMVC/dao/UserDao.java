package com.springMVC.dao;

import com.springMVC.entity.User;

import java.util.List;

public interface UserDao {
    public boolean addUser(User user);
    public boolean updateUser(User user);
    public boolean deleteUser(User user);
    public List<User> getListUser();
}

