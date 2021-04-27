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
        //we kept those just in case we want to add new flights
        //flights.put("120",new Flight("120","samos","athens",LocalTime.parse("05:50"),LocalDate.parse("2021-04-24")));
        //flights.put("12",new Flight("12","samos","athens",LocalTime.parse("05:20"),LocalDate.parse("2021-04-24")));
        //flights.put("123",new Flight("123","samos","athens",LocalTime.parse("05:20"),LocalDate.parse("2021-04-24")));
        //serializeFlights();

    }


    @Override
    public synchronized void addPersontoFlight(String flightId, int x, int y, Person p) throws RemoteException
    {
        flights.get(flightId).setpersonto(x, y, p);
    }

    @Override
    public synchronized Flight getFlightId(String id) throws RemoteException
    {
        return flights.get(id);
    }

    @Override
    public synchronized ArrayList<Flight> getFlightWith(String cityfrom, String cityto, LocalDate date) throws RemoteException
    {
        ArrayList<Flight> tempList = new ArrayList<Flight>();
        Set<String> ids = flights.keySet();

        // Collection Iterator
        Iterator<String> iterator = ids.iterator();
        //here we iterate through the whole hashtable to find the flight with the requirements that the user has asked
        //like the date of the flight and the destination and starting city

        while (iterator.hasNext())
        {
            System.out.println("in while");
            String key = iterator.next();
            Flight tempFlight = flights.get(key);
            if (tempFlight.getfrom().equals(cityfrom) && tempFlight.getTo().equals(cityto) && tempFlight.getDepart_date().equals(date))
            {
                tempList.add(tempFlight);

            }

        }
        return tempList;

    }

    @Override
    public synchronized Boolean checkAvailability(String flightId, int x, int y, Person p) throws RemoteException
    {
       if(flights.get(flightId).checkseat(x, y))
       {
           flights.get(flightId).setpersonto(x,y,p);
           return true;
       }
        else
       {
           return false;
       }
    }

    @Override
    public synchronized Person getPersoninfo(String id,String name) throws RemoteException
    {
        return flights.get(id).checkreservation(name);
    }

    //used only in case we want to add more flights
    private void serializeFlights()
    {
        try
        {

            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("flights.dat"));
            out.writeObject(flights);
            out.close();
            System.out.printf("Serialized data is saved in flights.dat");
        } catch (IOException i)
        {
            i.printStackTrace();
        }
    }

    //with this method we add the flights of our dat file to the hashtable
    private void deserializeFlights()
    {
        try
        {

            ObjectInputStream in = new ObjectInputStream(new FileInputStream("flights.dat"));
            flights = (Hashtable<String, Flight>) in.readObject();
            in.close();
        } catch (IOException i)
        {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c)
        {
            System.out.println("Flight class not found");
            c.printStackTrace();
            return;
        }
    }


}
