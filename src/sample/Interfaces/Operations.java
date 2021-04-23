package sample.Interfaces;

import sample.Flight;
import sample.Person;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Operations extends Remote {
    public Flight getFlightId(String id) throws RemoteException;
    public void addPersontoFlight(String flightId, int x , int y , Person p);
}
