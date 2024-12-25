package main;


import com.springMVC.entity.User;
import dto.request.Request;
import dto.response.Response;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Coordinator {
    static View coordinator;
    public static void main(String[] args) {
        coordinator = new View();
        coordinator.getClientTa().setLineWrap(true);
        coordinator.getClientTa().setWrapStyleWord(true);
        coordinator.getServer1Ta().setLineWrap(true);
        coordinator.getServer1Ta().setWrapStyleWord(true);
        coordinator.getServer2Ta().setLineWrap(true);
        coordinator.getServer2Ta().setWrapStyleWord(true);
        try (ServerSocket coordinatorServer = new ServerSocket(8899)) {  // Main.Coordinator lắng nghe trên cổng 8888
            System.out.println("Main.Coordinator is running on port 8899");

            while (true) {
                // Nhận kết nối từ client
                Socket clientSocket = coordinatorServer.accept();
                System.out.println("Client connected to Main.Coordinator");
                coordinator.getClientTa().setText("Client connected to Main.Coordinator");
                var ipAddress = clientSocket.getInetAddress().getHostAddress();
                System.out.println("Client IP: " + ipAddress);
                coordinator.getIpTf().setText(ipAddress);
                // Tạo luồng riêng để xử lý từng client
                Thread t = new Thread(new ClientHandler(clientSocket));
                t.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {

            //Tạo input stream, nối tới Socket
            try (
//                    BufferedReader clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    ObjectInputStream clientIn = new ObjectInputStream(clientSocket.getInputStream());
                    ObjectOutputStream clientOut = new ObjectOutputStream(clientSocket.getOutputStream());
            ) {
                Request request = (Request) clientIn.readObject();
                String title = request.getMessage();  // Nhận dữ liệu từ client
                System.out.println(title);

                String ip1 = "192.168.1.11";
                String ip2 = "192.168.1.11";
                Response response = null;
                if (title.contains("CS1")) {
                    response = coordinate(ip1, request);
                    coordinator.getServer1Ta().setText(response.getData() == null ? response.getMessage() : response.getData().toString());
                } else if (title.contains("CS2")) {
                    response = coordinate(ip2, request);
                    coordinator.getServer2Ta().setText(response.getData() == null ? response.getMessage() : response.getData().toString());
                } else {
                    Response response1 = coordinate(ip1, request);
                    Response response2 = coordinate(ip2, request);
                    coordinator.getServer2Ta().setText(response.getData().toString() == null ? "" : response.getData().toString());
                    coordinator.getServer1Ta().setText(response.getData().toString() == null ? "" : response.getData().toString());
                    List<User> users = new ArrayList<>();
                    users.addAll((List<User>)response1.getData());
                    users.addAll((List<User>)response2.getData());
                    response = new Response();
                    response.setMessage(response1.getMessage() + " " + response2.getMessage());
                    response.setData(users);
                }
                clientOut.writeObject(response);  // Trả kết quả lại cho client
//                clientOut.flush();  // Đảm bảo dữ liệu được gửi đi ngay lập tức
                coordinator.getClientTa().setText("Send Client Success.........");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        private Response coordinate(String ip, Request request) {
            // Kết nối tới Server

            try (Socket serverSocket = new Socket(ip, 9090);  // Kết nối tới server trên cổng 7878sout
                 ObjectOutputStream serverOut = new ObjectOutputStream(serverSocket.getOutputStream());
                 ObjectInputStream serverIn = new ObjectInputStream(serverSocket.getInputStream());) {
                System.out.println("Loading.........");

                serverOut.writeObject(request);  // Gửi tới server
                serverOut.flush();  // Đảm bảo dữ liệu được gửi đi ngay lập tức
                System.out.println("Send Server Success.........");

                //                for (User user : users)
//                    System.out.println(user.toString());

                return (Response) serverIn.readObject();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}
