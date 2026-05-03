package server.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import server.config.Neo4jConfig;

public class SocketServer {
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running on port " + PORT);
            
            // Đảm bảo Neo4j Driver được khởi tạo
            Neo4jConfig.getDriver();
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Neo4jConfig.close();
        }
    }
}
