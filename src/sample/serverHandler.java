package sample;

import sample.Interfaces.Operations;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.*;

public class serverHandler extends UnicastRemoteObject implements Operations
{

    private Hashtable<String, Flight> flights = new Hashtable<String, Flight>();
    //private List<Object[][]> tempoccupied = new ArrayList<>(); todo fix this

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
        p.setSeat(flightId, Integer.toString(x) + "-" + Integer.toString(y));
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
    public synchronized Boolean checkAvailability(String flightId, ArrayList<String> list) throws RemoteException
    {
        for (int i=0;i<list.size();i++)
        {
            String[] parts = list.get(i).split("-");
            String part1 = parts[0];
            String part2 = parts[1];
            int num1 =Integer.parseInt(part1);
            int num2 = Integer.parseInt(part2);
            if (!flights.get(flightId).checkseat(num1-1, num2-1))  //if this finds any false it returns false
            {
                return false;
            }

        }
        return true;  // at the end of for if not false found returns true

    }

    @Override
    public synchronized Person getPersoninfo(String id, String name) throws RemoteException
    {
        return flights.get(id).checkreservation(name);
    }

    @Override
    public synchronized ArrayList<String> occupiedSeats(String id) throws RemoteException
    {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < 25; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                if (!flights.get(id).checkseat(i, j))
                {
                    list.add(Integer.toString(i) +"-"+ Integer.toString(j));
                }
            }
        }

        return list;
    }

    /*@Override
    public List<ArrayList> tempoccumpiedSeats(ArrayList<String> seats , String id) throws RemoteException
    {

       // tempoccupied.add((ArrayList)seats);
        //tempoccupied.add(occupiedSeats(id));

        return  null; //todo add return list or whatever
    }
*/
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

    public void addflight(Flight f)
    {
        flights.put(f.getId(), f);
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
