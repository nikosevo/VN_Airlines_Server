package sample;

import sample.Interfaces.Operations;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.*;

public class serverHandler extends UnicastRemoteObject implements Operations
{
    //We make use of a Hashtable for its effieciency in searching and due to the fact that each flight is unique so
    //a Hashtable is the perfect data structure for our problem
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

    //Simple method that adds a person to the given seat and flight based on the flights Id
    @Override
    public void addPersontoFlight(String flightId, int x, int y, Person p) throws RemoteException
    {
        flights.get(flightId).setpersonto(x, y, p); //we use the flight Id and to that flight we add the person
        p.setSeat(flightId, Integer.toString(x) + "-" + Integer.toString(y)); //then we give the number seat to the seat
    }

    //Simple method that returns the flights number Id
    @Override
    public Flight getFlightId(String id) throws RemoteException
    {
        return flights.get(id);
    }

    //Method that returns the list with the desired destination and date that the user wishes to travel
    @Override
    public ArrayList<Flight> getFlightWith(String cityfrom, String cityto, LocalDate date) throws RemoteException
    {
        ArrayList<Flight> tempList = new ArrayList<Flight>();  //we make a new list to send
        Set<String> ids = flights.keySet();   //we create a set of Strings and match it to he flihts keys

        //here we iterate through the whole hashtable to find the flight with the requirements that the user has asked
        //like the date of the flight and the destination and starting city

        for (String id : ids)
        {
            System.out.println("in while");
            Flight tempFlight = flights.get(id);
            if (tempFlight.getfrom().equals(cityfrom) && tempFlight.getTo().equals(cityto) && tempFlight.getDepart_date().equals(date))
            {
                tempList.add(tempFlight);

            }

        }
        return tempList;

    }

    //We use this method to check if the selected seats by the user are available
    @Override
    public Boolean checkAvailability(String flightId, ArrayList<String> list) throws RemoteException
    {
        for (String s : list)
        {
            String[] parts = s.split("-");
            int num1 = Integer.parseInt(parts[0]);
            int num2 = Integer.parseInt(parts[1]);
            if (!flights.get(flightId).checkseat(num1 - 1, num2 - 1))  //if this finds any false it returns false
            {
                return false;
            }

        }
        return true;  // at the end of for if not false found returns true

    }

    //This method gets a list from the user with the selected on the user UI seats and reserves them for a short amount of time
    //using the bookTemporarily method
    @Override
    public Boolean bookTemporarily(String flightId, ArrayList<String> wishlist) throws RemoteException
    {
        if (!wishlist.isEmpty())
        {
            return flights.get(flightId).bookTemporarily(wishlist);
        }
        else{
            return false;
        }

    }

    //Simple method that allows us to find a user in a flight
    @Override
    public Person getPersoninfo(String id, String name) throws RemoteException
    {
        return flights.get(id).checkreservation(name);
    }

    //Method that returns to the user the occupied seats but from all the users not just himself
    @Override
    public ArrayList<String> occupiedSeats(String id) throws RemoteException
    {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < 25; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                if (!flights.get(id).checkseat(i, j))
                {
                    list.add(Integer.toString(i + 1) + "-" + Integer.toString(j + 1));
                }
            }
        }

        return list;
    }

    //method that returns the booked seats not the temporarily occupied
    @Override
    public ArrayList<String> tempOccupiedSeats(String id) throws RemoteException
    {
        return flights.get(id).getTempOccupied();
    }

    //Method that actually books the seats and add the user to the flight
    @Override
    public Boolean booknow(String flightId, String seat, Person person) {
        //the wishlist.size and person.size will always be the same so we iterate with the same f
        //this comment is so important that if u delete it the world will go boom

        String[] parts = seat.split("-");

        try {
            int x = Integer.parseInt(parts[0]) - 1;
            int y = Integer.parseInt(parts[1]) - 1;
            flights.get(flightId).setpersonto(x, y,person); //We set the person to the flight
            person.setSeat(flightId, seat);   //We give to the person his numbered seat
            return true;
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        //and now add the person the right seat in the flight id
        return false;
    }

    //used only in case we want to add more flights
    private void serializeFlights()
    {
        try
        {

            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("flights.dat"));
            out.writeObject(flights);
            out.close();
            System.out.println("Serialized data is saved in flights.dat");
        } catch (IOException i)
        {
            i.printStackTrace();
        }
    }

    //Method that adds any newly created flight to our list
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
        } catch (ClassNotFoundException c)
        {
            System.out.println("Flight class not found");
            c.printStackTrace();
        }
    }


}
