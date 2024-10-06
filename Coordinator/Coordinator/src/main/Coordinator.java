package main;

import com.springMVC.entity.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Coordinator {
    public static void main(String[] args) {
        try (ServerSocket coordinatorServer = new ServerSocket(8899)) {  // Main.Coordinator lắng nghe trên cổng 8888
            System.out.println("Main.Coordinator is running on port 8899");

            while (true) {
                // Nhận kết nối từ client
                Socket clientSocket = coordinatorServer.accept();
                System.out.println("Client connected to Main.Coordinator");
                System.out.println("Client IP: " + clientSocket.getInetAddress().getHostName());

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
                String title = clientIn.readLine();  // Nhận dữ liệu từ client
                System.out.println(title);
                List<User> students = null;
                String ip1 = "192.168.1.9";
                String ip2 = "192.168.1.9";
                if (title.contains("CS1")) {
                    System.out.println("CS111111");
                    students = coordinate(ip1, title);
                } else if (title.contains("CS2")) {
                    System.out.println("CS222222222");
                    students = coordinate(ip2, title);
                } else {
                    System.out.println("gettttttttt");
                    students = coordinate(ip1, title);
                    students.addAll(coordinate(ip2, title));
                }
                clientOut.writeObject(students);  // Trả kết quả lại cho client
//                clientOut.flush();  // Đảm bảo dữ liệu được gửi đi ngay lập tức
                System.out.println("Send Client Success.........");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        private List<User> coordinate(String ip, String cmd) {
            // Kết nối tới Server
            System.out.println("At here");
            try (Socket serverSocket = new Socket(ip, 9090);  // Kết nối tới server trên cổng 7878sout
                 ObjectOutputStream serverOut = new ObjectOutputStream(serverSocket.getOutputStream());
                 ObjectInputStream serverIn = new ObjectInputStream(serverSocket.getInputStream());) {
                System.out.println("Loading.........");

                serverOut.writeBytes(cmd.strip()+"\n");  // Gửi tới server
                serverOut.flush();  // Đảm bảo dữ liệu được gửi đi ngay lập tức
                System.out.println("Send Server Success.........");

                List<User> users = (List<User>) serverIn.readObject();// Nhận dữ liệu phản hồi từ server
                for (User user : users)
                    System.out.println(user.toString());

                return users;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}
