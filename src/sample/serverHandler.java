package sample;

import sample.Interfaces.Operations;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Hashtable;

public class serverHandler extends UnicastRemoteObject implements Operations
{

    private Hashtable<String, Flight> flights = new Hashtable<String, Flight>();


    public serverHandler() throws RemoteException
    {
        super();
        desializeFlights();
        System.out.println(flights.get("123"));
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


    private void serializeFlights(){
        try {

            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("flights.dat"));
            out.writeObject(flights);
            out.close();
            System.out.printf("Serialized data is saved in flights.dat");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    private void desializeFlights(){
        try {

            ObjectInputStream in = new ObjectInputStream(new FileInputStream("flights.dat"));
            flights = (Hashtable<String, Flight>) in.readObject();
            in.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return;
        }
    }


}
