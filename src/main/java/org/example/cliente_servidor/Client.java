package org.example.cliente_servidor;

import java.io.*;
import java.net.*;

public class Client {

    private Socket s = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    private DataInputStream serverResponse = null; // Para leer la respuesta del servidor

    public Client(String addr, int port) throws IOException {
        try {
            s = new Socket(addr, port);
            System.out.println("Connected");

            in = new DataInputStream(System.in);
            out = new DataOutputStream(s.getOutputStream());
            serverResponse = new DataInputStream(s.getInputStream()); // Inicializamos el stream de entrada

            String m = "";

            while (!m.equals("Over")) {
                try {
                    // Enviar mensaje al servidor
                    m = in.readLine();
                    out.writeUTF(m);

                    // Recibir respuesta del servidor
                    String response = serverResponse.readUTF();
                    System.out.println(response);
                } catch (IOException i) {
                    System.out.println(i);
                }
            }

            in.close();
            out.close();
            serverResponse.close(); // Cerrar el stream de respuesta
            s.close();
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
