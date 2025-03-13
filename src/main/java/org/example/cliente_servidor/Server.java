package org.example.cliente_servidor;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Server {

    private Socket socket = null;
    private ServerSocket serverSocket = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;

    public Server(int port) throws IOException {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");

            socket = serverSocket.accept();
            System.out.println("Client accepted");

            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new DataOutputStream(socket.getOutputStream());

            String m = "";

            while (true) {
                try {
                    m = in.readUTF();
                    System.out.println("Client says: " + m);

                    if (m.equals("Over")) {
                        break;
                    }

                    String serverResponse = new Scanner(System.in).nextLine();
                    if (serverResponse.equals("Over")) {
                        break;
                    } else {
                        out.writeUTF(serverResponse);
                    }
                } catch (IOException i) {
                    System.out.println(i);
                    break;
                }
            }


            System.out.println("Closing connection");

            socket.close();
            in.close();
            out.close();
        } catch (IOException i) {
            throw i;
        }
    }


    public static void main(String[] args) {
        try {
            Server s = new Server(5000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
