package sample;

import sample.Interfaces.Operations;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Hashtable;

public class serverHandler extends UnicastRemoteObject implements Operations
{

    private Hashtable<String, Flight> flights = new Hashtable<String, Flight>();

    public serverHandler() throws RemoteException
    {
        super();

    }

    @Override
    public void addPersontoFlight(String flightId, int x, int y, Person p)
    {
        flights.get(flightId).setpersonto(x, y, p);
    }

    @Override
    public Flight getFlightId(String id) throws RemoteException
    {

        return flights.get(id);

    }


}
