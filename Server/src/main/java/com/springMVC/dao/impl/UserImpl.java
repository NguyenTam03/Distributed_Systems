package com.springMVC.dao.impl;

import com.springMVC.dao.UserDao;
import com.springMVC.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class UserImpl implements UserDao {
    private final SessionFactory sessionFactory;

    public UserImpl() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }
    @Override
    public boolean addUser(User user) {
        boolean isAdded = false;
        try(Session session = sessionFactory.openSession()){
            session.save(user);
            isAdded = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return isAdded;
    }

    @Override
    public boolean updateUser(User user) {
        boolean isUpdated = false;
        try(Session session = sessionFactory.openSession()){
            session.update(user);
            isUpdated = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return isUpdated;
    }

    @Override
    public boolean deleteUser(User user) {
        boolean isDelete = false;
        try(Session session = sessionFactory.openSession()){
            session.delete(user);
            isDelete = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return isDelete;
    }

    @Override
    public List<User> getListUser() {
        List<User> users = null;
        try(Session session = sessionFactory.openSession()){
            users = session.createQuery("FROM User", User.class).list();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return users;
    }
}
