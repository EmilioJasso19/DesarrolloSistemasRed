package org.example.interfaces_contadores;

import javax.swing.*;
import java.awt.*;

public class Contadores {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Iniciar contadores :)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel lbl1 = new JLabel("Contador 1");
        JButton btn1 = new JButton("Iniciar Contador 1");
        JLabel lbl2 = new JLabel("Contador 2");
        JButton btn2 = new JButton("Iniciar Contador 2");

        lbl1.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn1.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl2.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn2.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(lbl1);
        panel.add(btn1);
        panel.add(lbl2);
        panel.add(btn2);

        Contador counter1 = new Contador(lbl1, btn1);
        Contador counter2 = new Contador(lbl2, btn2);

        btn1.addActionListener(e -> counter1.toggleRunning());
        btn2.addActionListener(e -> counter2.toggleRunning());


        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}

class Contador extends Thread {
    private int counter = 0;
    private boolean running = false;
    private final JLabel label;
    private final JButton button;

    public Contador(JLabel label, JButton button) {
        this.label = label;
        this.button = button;
        this.start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (running) {
                    counter++;
                    label.setText("Contador: " + counter);
                    button.setText("Detener");
                }

                if (!running && counter > 0) button.setText("Continuar");

                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void toggleRunning() {
        running = !running;
    }
}

