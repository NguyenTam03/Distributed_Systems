package com.springMVC;

import com.springMVC.dao.impl.UserImpl;
import com.springMVC.entity.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
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

        public ClientHandler(Socket socket) {
            super();
            this.socket = socket;
        }

        @Override
        public void run() {

            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                // Read info Coordinator - > Server
                System.out.println("Waiting..........");
                String title = in.readLine();
                System.out.println(title);

                switch (title) {
                    case "a":

                        break;
                    case "b":

                        break;
                    case "c":

                        break;
                    case "getList":
                        UserImpl userDao = new UserImpl();
                        List<User> users = userDao.getListUser();
                        out.writeObject(users);
                        break;
                }
                out.flush();
                System.out.println("Send to Coodinator success.........");


            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

}
