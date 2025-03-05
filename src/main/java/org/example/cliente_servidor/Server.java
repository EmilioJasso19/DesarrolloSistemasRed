package org.example.cliente_servidor;

import java.net.*;
import java.io.*;

public class Server {

    private Socket s = null;
    private ServerSocket ss = null;
    private DataInputStream in = null;
    private DataOutputStream out = null; // Agregamos un DataOutputStream

    public Server(int port) {
        try {
            ss = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");

            s = ss.accept();
            System.out.println("Client accepted");

            in = new DataInputStream(new BufferedInputStream(s.getInputStream()));
            out = new DataOutputStream(s.getOutputStream()); // Inicializamos el output stream

            String m = "";

            while (!m.equals("Over")) {
                try {
                    m = in.readUTF();
                    System.out.println("Client says: " + m);

                    // Enviar respuesta al cliente
                    out.writeUTF("Server received: " + m);
                } catch (IOException i) {
                    System.out.println(i);
                }
            }

            System.out.println("Closing connection");

            s.close();
            in.close();
            out.close(); // Cerrar el output stream
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        Server s = new Server(5000);
    }
}
