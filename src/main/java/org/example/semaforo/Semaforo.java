package org.example.semaforo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Semaforo extends JFrame {
    private JLabel lbl1, lbl2;
    private ArrayList<String> states = new ArrayList<>(Arrays.asList("Rojo", "Verde", "Amarillo"));

    public Semaforo() throws HeadlessException {
        this.lbl1 = new JLabel("Rojo");
        this.lbl2 = new JLabel("Verde");
        lbl1.setHorizontalAlignment(JLabel.CENTER);
        lbl2.setHorizontalAlignment(JLabel.CENTER);
        lbl1.setFont(new Font("Arial", Font.BOLD, 20));
        lbl2.setFont(new Font("Arial", Font.BOLD, 20));

        setTitle("Semaforo");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2, 1));

        add(lbl1);
        add(lbl2);
        updateLabel(lbl1, 2);
        updateLabel(lbl2, 1);
    }

    private void updateLabel(JLabel label, int timeInSeconds) {
        final int TIME_IN_MILIS = timeInSeconds * 1000;

        new Thread(() -> {
            int state = states.indexOf(label.getText());
            while (state < states.size()) {
                label.setText(states.get(state));
                try {
                    Thread.sleep(TIME_IN_MILIS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                state++;
                if (state >= states.size()) {
                    state = 0;
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        Semaforo semaforo = new Semaforo();
        semaforo.setVisible(true);
    }
}
