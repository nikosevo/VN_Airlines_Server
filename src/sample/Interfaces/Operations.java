package sample.Interfaces;

import sample.Flight;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Operations extends Remote {
    public Flight getFlightId(String id) throws RemoteException;

}
