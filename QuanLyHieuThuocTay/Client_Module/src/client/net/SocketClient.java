package client.net;

import java.io.*;
import java.net.Socket;
import shared.net.Request;
import shared.net.Response;

public class SocketClient {
    private static final String HOST = "localhost";
    private static final int PORT = 12345;
    private static Socket socket;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;

    public static synchronized Response sendRequest(Request request) {
        try {
            if (socket == null || socket.isClosed()) {
                socket = new Socket(HOST, PORT);
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());
            }

            out.writeObject(request);
            out.flush();
            return (Response) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new Response("ERROR", null, e.getMessage());
        }
    }

    public static void close() {
        try {
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
