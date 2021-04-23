package sample;

import sample.Interfaces.Operations;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class serverHandler extends UnicastRemoteObject implements Operations
{

    private Hashtable<String, Flight> flights = new Hashtable<String, Flight>();


    public serverHandler() throws RemoteException
    {
        super();
        deserializeFlights();
        System.out.println(flights.get("123"));
    }

    @Override
    public void receiveSearchRequest(String cityfrom, String cityto) throws RemoteException
    {   //todo delete this
        Flight fly = new Flight("13","athens","chios",LocalTime.parse("08:20"),LocalDate.parse("2007-05-05"));
       flights.put("13",fly);
        // getting keySet() into Set
        Set<String> setOfCountries = flights.keySet();

        // Collection Iterator
        Iterator<String> iterator = setOfCountries.iterator();
        //here we iterate through the whole hashtable to find the flight with the requirements that the user has asked
        //like the date of the flight and the destination and starting city
        //todo delete the prints and make it actually work with the rest of the program
        while(iterator.hasNext()) {
            System.out.println("in while");
            String key = iterator.next();

            if (flights.get(key).getfrom() == cityfrom && flights.get(key).getTo()== cityto)
            {
                System.out.println(flights.get(key).getfrom() + flights.get(key).getTo());
                System.out.println("true");
            }

        }

    }

    @Override
    public void addPersontoFlight(String flightId, int x, int y, Person p) throws RemoteException
    {
        flights.get(flightId).setpersonto(x, y, p);
    }

    @Override
    public Flight getFlightId(String id) throws RemoteException
    {
        return flights.get(id);
    }

    //used only in case we want to add more flights
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
    //with this method we add the flights of our dat file to the hashtable
    private void deserializeFlights(){
        try {

            ObjectInputStream in = new ObjectInputStream(new FileInputStream("flights.dat"));
            flights = (Hashtable<String, Flight>) in.readObject();
            in.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Flight class not found");
            c.printStackTrace();
            return;
        }
    }


}
