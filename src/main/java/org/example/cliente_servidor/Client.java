package org.example.cliente_servidor;

import java.io.*;
import java.net.*;

public class Client {

    private Socket socket = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    private DataInputStream serverResponse = null;

    public Client(String addr, int port) throws IOException {
        try {
            socket = new Socket(addr, port);
            System.out.println("Connected");

            in = new DataInputStream(System.in);
            out = new DataOutputStream(socket.getOutputStream());
            serverResponse = new DataInputStream(socket.getInputStream());

            String m = "";

            while (true) {
                try {
                    m = in.readLine();
                    out.writeUTF(m);

                    if (m.equals("Over")) {
                        break;
                    }

                    String response = serverResponse.readUTF();
                    System.out.println("Server says: " + response);
                } catch (IOException i) {
                    System.out.println(i);
                    break;
                }
            }


            in.close();
            out.close();
            serverResponse.close();
            socket.close();
        } catch (IOException i) {
            throw i;
        }
    }

    public static void main(String[] args) {
        try {
            Client c = new Client("127.0.0.1", 5000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
