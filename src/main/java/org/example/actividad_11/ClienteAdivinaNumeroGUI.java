package org.example.actividad_11;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClienteAdivinaNumeroGUI {

    private Socket socket;
    private DataInputStream serverResponse;
    private DataOutputStream out;
    private JFrame frame;
    private JPanel inputPanel;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JTextField inputField;
    private JButton sendButton;

    public ClienteAdivinaNumeroGUI(String addr, int port) throws IOException {
        try {
            socket = new Socket(addr, port);
            System.out.println("Conectado al servidor");

            serverResponse = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

        } catch (IOException io) {
            throw io;
        }
    }

    public void initClient() {
        frame = new JFrame("Cliente - Adivina el numero");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel label = new JLabel("Tu intento:");
        inputField = new JTextField(10);
        sendButton = new JButton("Enviar");
        sendButton.setEnabled(false);

        inputPanel.add(label);
        inputPanel.add(inputField);
        inputPanel.add(sendButton);

        textArea = new JTextArea();
        textArea.setEditable(false);
        scrollPane = new JScrollPane(textArea);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        inputField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateButtonState(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateButtonState(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateButtonState(); }
            private void updateButtonState() {
                sendButton.setEnabled(!inputField.getText().trim().isEmpty());
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendAttempt();
            }
        });

        frame.setVisible(true);
    }

    private void sendAttempt() {
        try {
            String intento = inputField.getText().trim();

            inputField.setEnabled(false);
            sendButton.setEnabled(false);

            out.writeUTF(intento);
            out.flush();

            String respuesta = serverResponse.readUTF();
            textArea.append("Servidor: " + respuesta + "\n");

            if (respuesta.equals("Correcto!")) {
                inputField.setEnabled(false);
                inputField.setEditable(false);
            } else {
                inputField.setEnabled(true);
                sendButton.setEnabled(true);
            }

            inputField.setText("");

        } catch (IOException ex) {
            textArea.append("Error de conexion con el servidor\n");
        }
    }


    public static void main(String[] args) throws IOException {
        ClienteAdivinaNumeroGUI client = new ClienteAdivinaNumeroGUI("127.0.0.1", 777);
        client.initClient();
    }
}
