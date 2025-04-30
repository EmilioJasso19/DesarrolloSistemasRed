package org.example.actividad_16.cliente;

import org.example.actividad_16.interfaz.NamesList;

import javax.swing.*;
import java.awt.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClienteGUI extends JFrame {

    private JTextField nameField;
    private JButton addButton;
    private JList<String> namesList;
    private JButton deleteButton;
    private JButton updateButton;

    public ClienteGUI() {
        setTitle("Cliente RMI - Lista de Personas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel(new BorderLayout());
        nameField = new JTextField();
        addButton = new JButton("Agregar");
        panelSuperior.add(nameField, BorderLayout.CENTER);
        panelSuperior.add(addButton, BorderLayout.EAST);

        namesList = new JList<>(new DefaultListModel<>());
        JScrollPane scrollPane = new JScrollPane(namesList);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        deleteButton = new JButton("Eliminar seleccionado");
        updateButton = new JButton("Actualizar");
        panelInferior.add(deleteButton);
        panelInferior.add(updateButton);

        addButton.addActionListener(e -> addName());
        deleteButton.addActionListener(e -> removeName());
        updateButton.addActionListener(e -> updateList());

        add(panelSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ClienteGUI cliente = new ClienteGUI();
            cliente.setVisible(true);
        });
    }

    private void addName() {
        String nombre = nameField.getText().trim();
        if (!nombre.isEmpty()) {
            try {
                NamesList remoteObject = getRemoteObject();
                if (remoteObject != null) {
                    remoteObject.addName(nombre);
                    nameField.setText("");
                }
            } catch (Exception e) {
                System.out.println("Error al agregar el nombre: " + e.getMessage());
            }
        }
    }

    private void removeName() {
        String nombre = namesList.getSelectedValue();
        if (nombre != null) {
            try {
                NamesList remoteObject = getRemoteObject();
                if (remoteObject != null) {
                    remoteObject.removeName(nombre);
                    DefaultListModel<String> model = (DefaultListModel<String>) namesList.getModel();
                    model.removeElement(nombre);
                }
            } catch (Exception e) {
                System.out.println("Error al eliminar el nombre: " + e.getMessage());
            }
        }
    }

    private void updateList() {
        try {
            NamesList remoteObject = getRemoteObject();
            if (remoteObject != null) {
                DefaultListModel<String> model = (DefaultListModel<String>) namesList.getModel();
                model.clear();
                for (String nombre : remoteObject.getNames()) {
                    model.addElement(nombre);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar la lista: " + e.getMessage());
        }
    }

    private NamesList getRemoteObject() {
        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            return (NamesList) registry.lookup("names");
        } catch (Exception e) {
            System.out.println("Error al obtener el objeto remoto: " + e.getMessage());
        }
        return null;
    }
}

