package org.example;

import java.util.Arrays;

class SharedResource {
    private int[] data;
    private boolean available = false;

    public synchronized void produce(int[] values) throws InterruptedException {
        while (available) {
            wait();
        }
        data = values;
        available = true;
        notifyAll();
    }

    public synchronized boolean consume() throws InterruptedException {
        while (!available) {
            wait();
        }

        boolean areEquals;
        do {
            areEquals = false;
            int attempts = 1;
            System.out.println("Array a adivinar: " + Arrays.toString(data));

            while (!areEquals && attempts <= 3) {
                int[] arr = {(int) (Math.random() * 5), (int) (Math.random() * 5)};
                System.out.println("Intento " + attempts + ": " + Arrays.toString(arr));
                areEquals = Arrays.equals(arr, data);
                attempts++;
            }

            if (!areEquals) {
                System.out.println("No se adivinó");
                available = false;
                notifyAll();
                while (!available) {
                    wait();
                }
            }
        } while (!areEquals);

        System.out.println("..:: Array adivinado :D ::..");
        available = false;
        notify();
        return true;
    }
}

public class WaitNotify {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        Thread producer = new Thread(() -> {
            try {
                while (true) {
                    int[] arr = {(int) (Math.random() * 5), (int) (Math.random() * 5)};
                    resource.produce(arr);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                resource.consume();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
    }
}
