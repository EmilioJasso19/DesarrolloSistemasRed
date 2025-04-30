package org.example.actividad_16.rmi;

import org.example.actividad_16.interfaz.NamesList;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class NamesRMI extends UnicastRemoteObject implements NamesList {
    private ArrayList<String> names;

    public NamesRMI() throws RemoteException {
        super();
        names = new ArrayList<>();
    }

    @Override
    public void addName(String name) throws RemoteException {
        System.out.println("Se agregó a: " + name);
        names.add(name);
    }

    @Override
    public void removeName(String name) throws RemoteException {
        System.out.println("Se eliminó a: " + name);
        names.remove(name);
    }

    @Override
    public String[] getNames() throws RemoteException {
        if (names.isEmpty()) {
            System.out.println("No hay nombres en la lista");
            return new String[0];
        }
        System.out.println("Nombres en la lista: " + names);
        return names.toArray(new String[0]);
    }
}
