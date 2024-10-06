package com.springMVC;

//import com.springMVC.dao.impl.UserImpl;
//import com.springMVC.entity.User;

import com.springMVC.entity.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Date;
import java.util.List;

public class Server {
    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(9090)) {
            System.out.println("Server is running on port 9090");
            while (true) {
                Socket socket = server.accept();
                System.out.println("Coordinator connected");
                System.out.println("Coordinator IP: " + socket.getInetAddress().getHostName());
                Server temp = new Server();
                Thread t = new Thread(temp.new ClientHandler(socket));
                t.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private class ClientHandler implements Runnable {
        private Socket socket;
//        private UserImpl userDao;
        public ClientHandler(Socket socket) {
            super();
            this.socket = socket;
//            userDao = new UserImpl();
        }

        @Override
        public void run() {

            try {
                User user1 = new User("1", "admin", "admin", "admin", "admin", Date.valueOf("2003-08-23"), "1");
                User user2 = new User("2", "user", "user", "user", "user", Date.valueOf("2003-08-23"), "2");
                User user3 = new User("3", "user1", "user1", "user1", "user1", Date.valueOf("2003-08-23"), "3");
                List<User> users = List.of(user1, user2, user3);
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                // Read info Coordinator - > Server
                System.out.println("Waiting..........");
                String title = in.readLine();
                System.out.println("Title: ");
                System.out.println(title);
                out.writeObject(users);
                out.flush();
                //List<User> users = userDao.getListUser();
                //out.writeObject(users);
//                out.flush();
                System.out.println("Send to Coodinator success.........");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

}