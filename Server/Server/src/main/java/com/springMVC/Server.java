package com.springMVC;

//import com.springMVC.dao.impl.UserImpl;
//import com.springMVC.entity.User;

import com.springMVC.dao.impl.UserImpl;
import com.springMVC.entity.User;
import com.springMVC.service.UserService;
import com.springMVC.view.ServerView;
import dto.request.Request;
import dto.response.Response;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Server {
  private static UserService userService;
  private static ServerView serverView;
  public static void main(String[] args) {
    serverView = new ServerView();
    userService = new UserService(new UserImpl());
    serverView.getTxtClient().setLineWrap(true);
    serverView.getTxtClient().setWrapStyleWord(true);
    serverView.getTxtServer().setLineWrap(true);
    serverView.getTxtServer().setWrapStyleWord(true);
//    userService.getListUser().forEach(System.out::println);

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
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        // Read info Coordinator - > Server
        String textServer = "";
        String textClient = "";
        textServer += "Waiting.........." + '\n';
        System.out.println("Waiting..........");
        serverView.getTxtServer().setText(textServer);
        Request request = (Request) in.readObject();
        System.out.println("Title: " + request.getMessage());
        textServer += "Title: " + request.getMessage() +'\n';
        textServer +=  request.getData() == null ? "": request.getData()  +"\n";
        serverView.getTxtServer().setText(textServer);

        // Process request
        String processedRequest = processRequest(request.getMessage());

        Response response = new Response();

        switch (processedRequest) {
          case "GET_LIST":
            textClient += "List of users" + '\n';
            serverView.getTxtClient().setText(textClient);
            List<User> users = userService.getListUser();
            response.setMessage("Get List Success!");
            response.setData(users);
            break;

            case "ADD_USER":
              textClient += "Add user" + '\n';
              serverView.getTxtClient().setText(textClient);
              boolean isAdded = userService.addUser((User) request.getData());
              System.out.println(isAdded);
              response.setMessage("Add User Success!");
              break;
            case "DELETE_USER":
              textClient += "Delete user" + '\n';
              System.out.println("Delete user");
              serverView.getTxtClient().setText(textClient);
              boolean isDeleted = userService.deleteUser(Integer.parseInt(request.getData().toString()));
              System.out.println(isDeleted);
              response.setMessage("Delete User Success!");
              break;
          default:
            return;
        }

        out.writeObject(response);
        textClient += response.getMessage();
        out.flush();
        //List<User> users = userDao.getListUser();
        //out.writeObject(users);
//                out.flush();
        textClient += '\n' + "Send to Coodinator success.........";
        System.out.println("Send to Coodinator success.........");
        serverView.getTxtClient().setText(textClient);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private String processRequest(String request) {
    if (request.startsWith("GET_LIST")) {
      return "GET_LIST";
    } else if (request.startsWith("ADD_USER")) {
      return "ADD_USER";
    } else if (request.startsWith("UPDATE_USER")) {
      return "UPDATE_USER";
    } else if (request.startsWith("DELETE_USER")) {
      return "DELETE_USER";
    }
    return request;
  }

}