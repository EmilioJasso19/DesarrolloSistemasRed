package org.example.interfaces_contadores;
import javax.swing.*;
import java.awt.*;

public class Contadores {
    public static void main(String[] args) {

        Contador contador = new Contador();
        Contador contador2 = new Contador();
        contador.start();
        contador2.start();

        JFrame frame = new JFrame("Iniciar contadores");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
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

        panel.add(Box.createVerticalStrut(10));
        panel.add(lbl1);
        panel.add(Box.createVerticalStrut(5));
        panel.add(btn1);
        panel.add(Box.createVerticalStrut(10));
        panel.add(lbl2);
        panel.add(Box.createVerticalStrut(5));
        panel.add(btn2);
        panel.add(Box.createVerticalStrut(10));

        btn1.addActionListener(e -> contador.toggleRunning());
        btn2.addActionListener(e -> contador2.toggleRunning());

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}

class Contador extends Thread {
    private int count = 0;
    private boolean running = false;

    public void stopCount() {
        running = false;
    }

    @Override
    public void run() {
        try {
            while (running) {
                System.out.println(count++);
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getCount() {
        return count;
    }

    public void toggleRunning() {
        running = !running;
    }
}

