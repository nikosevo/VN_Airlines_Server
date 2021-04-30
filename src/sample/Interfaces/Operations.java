package sample.Interfaces;

import sample.Flight;
import sample.Person;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ArrayList;

public interface Operations extends Remote
{
    public Flight getFlightId(String id) throws RemoteException;

    public void addPersontoFlight(String flightId, int x, int y, Person p) throws RemoteException;

    public ArrayList<Flight> getFlightWith(String cityfrom, String cityto, LocalDate date) throws RemoteException;

    public Boolean checkAvailability(String flightId,ArrayList<String> list) throws RemoteException;

    public Boolean bookTemporarily(String flightId,ArrayList<String> wishlist) throws  RemoteException;

    public Person getPersoninfo(String id, String name) throws RemoteException;

    public ArrayList<String> occupiedSeats(String id) throws RemoteException;

    public ArrayList<String> tempOccupiedSeats(String id) throws RemoteException;

    public Boolean booknow(String flightId , String seat , Person person) throws RemoteException;
}
