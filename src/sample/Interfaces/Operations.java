package sample.Interfaces;

import sample.Flight;
import sample.Person;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public interface Operations extends Remote {
    public Flight getFlightId(String id) throws RemoteException;
    public void addPersontoFlight(String flightId, int x , int y , Person p) throws RemoteException;
    public ArrayList<Flight> getFlightWith(String cityfrom , String cityto ) throws RemoteException;  //todo add date as well
}
