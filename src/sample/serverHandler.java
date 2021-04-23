package sample;

import sample.Interfaces.Operations;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalTime;
import java.util.ArrayList;

public class serverHandler  extends UnicastRemoteObject implements Operations
{

    private ArrayList<Flight> flights = new ArrayList<Flight>();
    public serverHandler() throws RemoteException {
        super();
        flights.add(new Flight("123","athens","to", LocalTime.parse("08:20")));

    }


    @Override
    public Flight getFlightId(String id) throws RemoteException {
        for(Flight f : flights)
            if(f.getId().equals(id))
                return f;

        return null;
    }


}
