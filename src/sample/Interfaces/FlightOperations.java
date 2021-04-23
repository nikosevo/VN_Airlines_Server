package sample.Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FlightOperations extends Remote {
    public String getFlightId() throws RemoteException;
}
