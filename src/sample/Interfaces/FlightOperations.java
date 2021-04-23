package sample.Interfaces;

import sample.Flight;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FlightOperations extends Remote {
    public Flight getFlightId(String id) throws RemoteException;
}
