package org.example.actividad_16.servidor;

import org.example.actividad_16.rmi.NamesRMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor {
    public static void main(String[] args) {
        try {
            NamesRMI namesRMI = new NamesRMI();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("names", namesRMI);
            System.out.println("Servidor listo");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
