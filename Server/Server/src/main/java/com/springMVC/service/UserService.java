package com.springMVC.service;

import com.springMVC.dao.UserDao;
import com.springMVC.entity.User;
import java.util.List;

public class UserService {

  private UserDao userDao;

  public UserService(UserDao userDao) {
    this.userDao = userDao;
  }

  public boolean addUser(User user) {
    return userDao.addUser(user);
  }

  public boolean updateUser(User user) {
    return userDao.updateUser(user);
  }

  public boolean deleteUser(int id) {
    return userDao.deleteUser(id);
  }


  public List<User> getListUser() {
    return userDao.getListUser();
  }
}
