package main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

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
            try (
                    //Tạo input stream, nối tới Socket
                    BufferedReader clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    //Tạo outputStream, nối tới socket
                    DataOutputStream clientOut = new DataOutputStream(clientSocket.getOutputStream());
            ) {
                // Kết nối tới Server
                try (Socket serverSocket = new Socket("192.168.1.9", 9090);  // Kết nối tới server trên cổng 7878
                     DataOutputStream serverOut = new DataOutputStream(serverSocket.getOutputStream());
                     BufferedReader serverIn = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));) {
                    System.out.println("Loading.........");

                    String title = clientIn.readLine();  // Nhận dữ liệu từ client
                    System.out.println(title);

                    serverOut.writeBytes(title + "\n");  // Gửi tới server
                    serverOut.flush();  // Đảm bảo dữ liệu được gửi đi ngay lập tức
                    System.out.println("Send Server  Success.........");

                    String message = serverIn.readLine();  // Nhận dữ liệu phản hồi từ server
                    System.out.println(message);

                    clientOut.writeBytes(message + "\n");  // Trả kết quả lại cho client
                    clientOut.flush();  // Đảm bảo dữ liệu được gửi đi ngay lập tức
                    System.out.println("Send Client Success.........");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
