package org.example.carreras;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Carrera extends JFrame {
    private JProgressBar progressBar1, progressBar2, progressBar3;
    private JButton button1;
    private final int  MAX_VALUE = 1000;
    private final List<String> positions = new ArrayList<>();

    public Carrera(){
        setTitle("Carrerita");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1));
        progressBar1 = new JProgressBar(0,MAX_VALUE);
        progressBar2 = new JProgressBar(0,MAX_VALUE);
        progressBar3 = new JProgressBar(0,MAX_VALUE);


        progressBar1.setStringPainted(true);
        progressBar2.setStringPainted(true);
        progressBar3.setStringPainted(true);
        button1 = new JButton("Iniciar");

        add(progressBar1);
        add(progressBar2);
        add(progressBar3);
        add(button1);

        button1.addActionListener(e -> startRace());
    }
    private void startRace(){
        this.progressBar1.setValue(0);
        this.progressBar2.setValue(0);
        this.progressBar3.setValue(0);

        new Thread(()-> updateBar(progressBar1, "Corredor1")).start();
        new Thread(()-> updateBar(progressBar2, "Corredor2")).start();
        new Thread(()-> updateBar(progressBar3, "Corredor3")).start();

    }
    public void updateBar(JProgressBar progressBar, String name){
        int progress = 0;
        while(progress < MAX_VALUE){
            progress+=(int)(Math.random() * 25 + 10);
            if(progress>MAX_VALUE){
                progress = MAX_VALUE;
            }

            int finalProgress = progress;
            SwingUtilities.invokeLater(()->progressBar.setValue(finalProgress));

            if(progress >= MAX_VALUE){
                synchronized (positions){
                    positions.add(name);
                    if(positions.size() ==3){
                        SwingUtilities.invokeLater(this::showResults);
                    }
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void showResults(){
        JFrame results = new JFrame("Resultados de la carrera");
        results.setSize(300, 200);
        results.setLocationRelativeTo(this);
        JPanel panel = new JPanel(new GridLayout(4,1));
        panel.add(new JLabel("Posiciones:"));

        for(int i=0; i<positions.size();i++){
            panel.add(new JLabel((i+1) + "." + positions.get(i)));
        }
        results.add(panel);
        results.setVisible(true);
        positions.clear();

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->new Carrera().setVisible(true));
    }
}