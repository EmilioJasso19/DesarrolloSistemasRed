package org.example;
public class Main {
    public static void main(String[] args) {
        Print5 t1 = new Print5();
        PrintE g2 = new PrintE();
        Thread t2 = new Thread(g2);

        t1.start();
        t2.start();
    }
}

class Print5 extends Thread {
    public void run() {
        for (int i = 1; i <= 5; i++) {
            try {
                Thread.sleep((long) (Math.random() * (2000 -1) + 1));
                System.out.println(i);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

class PrintE implements Runnable {
    private final String[] letters = {"A", "B", "C", "D", "E"};

    public void run() {
        for (String letter : letters) {
           try {
               Thread.sleep((long) (Math.random() * (2000 -1) + 1));
               System.out.println(letter);
           } catch (InterruptedException e) {
               System.out.println(e.getMessage());
           }
        }
    }
}