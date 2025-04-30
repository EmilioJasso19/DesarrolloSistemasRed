package org.example.actividad_16.interfaz;

import java.rmi.Remote;

public interface NamesList extends Remote {
    void addName(String name) throws java.rmi.RemoteException;
    void removeName(String name) throws java.rmi.RemoteException;
    String[] getNames() throws java.rmi.RemoteException;
}
