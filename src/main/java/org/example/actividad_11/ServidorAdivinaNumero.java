package org.example.actividad_11;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class ServidorAdivinaNumero {
    private Socket socket = null;
    private ServerSocket serverSocket = null;
    private DataInputStream in;
    private DataOutputStream out;
    private int number = (int) (Math.random() * 100) + 1;
    private boolean isAttemptCorrect = false;


    public ServidorAdivinaNumero(int port) throws IOException {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Servidor iniciado");
            System.out.println("Esperando conexion...");

            socket = serverSocket.accept();
            System.out.println("Cliente aceptado");

            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new DataOutputStream(socket.getOutputStream());

            String m = "";
            while (!isAttemptCorrect) {
                m = in.readUTF();
                out.writeUTF(checkResponse(Integer.parseInt(m)));
            }
        } catch (IOException io) {
            throw io;
        }

        System.out.println("Closing connection");

        socket.close();
        in.close();
        out.close();
    }

    private String checkResponse(int attempt) {
        if (attempt < number) return "Muy bajo!";
        if (attempt > number) return "Muy alto!";
        return "Correcto!";
    }

    public static void main(String[] args) {
        try {
            ServidorAdivinaNumero s = new ServidorAdivinaNumero(777);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
