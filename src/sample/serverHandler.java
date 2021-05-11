package sample;

import sample.Interfaces.Operations;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Hashtable;
import java.util.Set;

public class serverHandler extends UnicastRemoteObject implements Operations, Serializable {
    //We make use of a Hashtable for its efficiency in searching and due to the fact that each flight is unique so
    //a Hashtable is the perfect data structure for our problem
    private Hashtable<String, Flight> flights = new Hashtable<String, Flight>();

    public serverHandler() throws RemoteException {

        super();
        //addFlightLikeANoob();
        //serializeFlights();
        deserializeFlights();
    }

    //Simple method that adds a person to the given seat and flight based on the flights Id
    @Override
    public void addPersontoFlight(String flightId, int x, int y, Person p) throws RemoteException {
        flights.get(flightId).setpersonto(x, y, p); //we use the flight Id and to that flight we add the person
        p.setSeat(flightId, Integer.toString(x) + "-" + Integer.toString(y)); //then we give the number seat to the seat
        serializeFlights();
    }

    //Simple method that returns the flights number Id
    @Override
    public Flight getFlightId(String id) throws RemoteException {
        return flights.get(id);
    }

    //Method that returns the list with the desired destination and date that the user wishes to travel
    @Override
    public ArrayList<Flight> getFlightWith(String cityfrom, String cityto, LocalDate date) throws RemoteException {
        ArrayList<Flight> tempList = new ArrayList<Flight>();  //we make a new list to send
        Set<String> ids = flights.keySet();   //we create a set of Strings and match it to he flihts keys

        //here we iterate through the whole hashtable to find the flight with the requirements that the user has asked
        //like the date of the flight and the destination and starting city

        for (String id : ids) {
            System.out.println("in while");
            Flight tempFlight = flights.get(id);
            if (tempFlight.getfrom().equals(cityfrom) && tempFlight.getTo().equals(cityto) && tempFlight.getDepart_date().equals(date)) {
                tempList.add(tempFlight);

            }

        }
        return tempList;

    }

    //We use this method to check if the selected seats by the user are available
    @Override
    public Boolean checkAvailability(String flightId, ArrayList<String> list) throws RemoteException {
        for (String s : list) {
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
    public Boolean bookTemporarily(String flightId, ArrayList<String> wishlist) throws RemoteException {
        if (!wishlist.isEmpty()) {
            return flights.get(flightId).bookTemporarily(wishlist);
        } else {
            return false;
        }

    }

    //Simple method that allows us to find a user in a flight
    @Override
    public Person getPersoninfo(String id, String name) throws RemoteException {
        return flights.get(id).checkreservation(name);
    }

    //Method that returns to the user the occupied seats but from all the users not just himself
    @Override
    public ArrayList<String> occupiedSeats(String id) throws RemoteException {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 4; j++) {
                if (!flights.get(id).checkseat(i, j)) {
                    list.add(Integer.toString(i + 1) + "-" + Integer.toString(j + 1));
                }
            }
        }

        return list;
    }

    //method that returns the booked seats not the temporarily occupied
    @Override
    public ArrayList<String> tempOccupiedSeats(String id) throws RemoteException {
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
            flights.get(flightId).setpersonto(x, y, person); //We set the person to the flight
            person.setSeat(flightId, seat);   //We give to the person his numbered seat
            serializeFlights();
            return true;
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        //and now add the person the right seat in the flight id
        return false;
    }

    //method that returns the flightinfo and sends them to the user UI to be displayed
    @Override
    public ArrayList<String> flightinfo(String name, String flightid) throws RemoteException {   //this is for testing kai kala
        ArrayList<String> info = new ArrayList<>();
        //top info
        info.add(flights.get(flightid).checkreservation(name).getName());
        info.add(seatconvertor(flights.get(flightid).checkreservation(name).getSeat()));
        info.add(flights.get(flightid).checkreservation(name).getFlightid());
        //Departure info
        info.add("" + flights.get(flightid).getDepart_date());
        info.add("" + flights.get(flightid).getDepart_time());
        info.add(flights.get(flightid).getfrom());
        //Arrival info
        info.add("" + flights.get(flightid).getArrival_date());
        info.add("" + flights.get(flightid).getArrival_time());
        info.add(flights.get(flightid).getTo());

        return info;
    }

    @Override
    public void removeThread(String flightID ,ArrayList<String> wishlist) throws RemoteException {
        flights.get(flightID).removeThread(wishlist);
    }

    private String seatconvertor(String seat) {

        String[] parts = seat.split("-");
        int temp = Integer.parseInt(parts[1]) - 1;
        parts[1] = temp + "";
        System.out.println(parts[1]);
        switch (parts[1]) {
            case "0":
                parts[1] = "A";
                break;
            case "1":
                parts[1] = "B";
                break;
            case "2":
                parts[1] = "C";
                break;
            case "3":
                parts[1] = "D";
                break;
        }

        return parts[0] + "-" + parts[1];

    }

    //used only in case we want to add more flights and this need to be turned to public due to continuity
    private void serializeFlights() {
        try {

            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("flights.dat"));
            flights.forEach((key,value)->value.clearTempOccupied() );
            out.writeObject(flights);
            out.close();
            System.out.println("Serialized data is saved in flights.dat");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    //Method that adds any newly created flight to our list
    private void addflight(Flight f) {
        flights.put(f.getId(), f);
    }

    //with this method we add the flights of our dat file to the hashtable
    public void deserializeFlights() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("flights.dat"));
            flights = (Hashtable<String, Flight>) in.readObject();
            System.out.println(flights);
            in.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Flight class not found");
            c.printStackTrace();
        }
    }

    private void addFlightLikeANoob() {
        Flight temp = new Flight("081", "Samos", "Thessaloniki", LocalTime.parse("09:15:45"), LocalDate.parse("2021-05-02"), LocalTime.parse("11:15:45"), LocalDate.parse("2021-05-05"), 80);
        Flight temp1 = new Flight("105", "Chios", "Graz", LocalTime.parse("08:15:45"), LocalDate.parse("2021-05-04"), LocalTime.parse("10:15:45"), LocalDate.parse("2021-05-05"), 75);
        Flight temp2 = new Flight("123", "Athens", "Samos", LocalTime.parse("12:15:45"), LocalDate.parse("2021-05-06"), LocalTime.parse("13:15:45"), LocalDate.parse("2021-05-05"), 20);
        Flight temp3 = new Flight("321", "Samos", "Chios", LocalTime.parse("11:15:45"), LocalDate.parse("2021-05-28"), LocalTime.parse("13:15:45"), LocalDate.parse("2021-05-05"), 100);
        Flight temp4 = new Flight("122", "Graz", "Samos", LocalTime.parse("13:15:45"), LocalDate.parse("2021-05-26"), LocalTime.parse("14:15:45"), LocalDate.parse("2021-05-05"), 200);
        Flight temp5 = new Flight("109", "Athens", "Cairo", LocalTime.parse("15:15:45"), LocalDate.parse("2021-05-27"), LocalTime.parse("17:15:45"), LocalDate.parse("2021-05-05"), 40);
        Flight temp6 = new Flight("251", "Athens", "Chania", LocalTime.parse("16:15:45"), LocalDate.parse("2021-05-07"), LocalTime.parse("18:15:45"), LocalDate.parse("2021-05-05"), 100);
        Flight temp7 = new Flight("001", "Athens", "Rome", LocalTime.parse("19:15:45"), LocalDate.parse("2021-05-07"), LocalTime.parse("21:15:45"), LocalDate.parse("2021-05-05"), 101);
        Flight temp8 = new Flight("002", "Rhodes", "Chios", LocalTime.parse("22:15:45"), LocalDate.parse("2021-05-08"), LocalTime.parse("00:15:45"), LocalDate.parse("2021-06-05"), 90);
        Flight temp9 = new Flight("021", "Chios", "Moon", LocalTime.parse("21:15:45"), LocalDate.parse("2021-05-05"), LocalTime.parse("23:15:45"), LocalDate.parse("2021-05-05"), 1000001);
        Flight temp11 = new Flight("112", "Samos", "Athens", LocalTime.parse("11:15:45"), LocalDate.parse("2021-05-05"), LocalTime.parse("13:15:45"), LocalDate.parse("2021-05-05"), 50);
        Flight temp12 = new Flight("213", "Athens", "Samos", LocalTime.parse("07:15:45"), LocalDate.parse("2021-05-05"), LocalTime.parse("09:15:45"), LocalDate.parse("2021-05-05"), 60);
        Flight temp13 = new Flight("212", "Athens", "Samos", LocalTime.parse("08:15:45"), LocalDate.parse("2021-05-05"), LocalTime.parse("10:15:45"), LocalDate.parse("2021-05-05"), 90);
        Flight temp14 = new Flight("214", "Athens", "Samos", LocalTime.parse("08:15:45"), LocalDate.parse("2021-05-05"), LocalTime.parse("10:15:45"), LocalDate.parse("2021-05-05"), 90);
        Flight temp15 = new Flight("215", "Athens", "Samos", LocalTime.parse("08:15:45"), LocalDate.parse("2021-05-05"), LocalTime.parse("10:15:45"), LocalDate.parse("2021-05-05"), 90);
        Flight temp16 = new Flight("216", "Athens", "Samos", LocalTime.parse("08:15:45"), LocalDate.parse("2021-05-05"), LocalTime.parse("10:15:45"), LocalDate.parse("2021-05-05"), 90);
        Flight temp17 = new Flight("217", "Athens", "Samos", LocalTime.parse("08:15:45"), LocalDate.parse("2021-05-05"), LocalTime.parse("10:15:45"), LocalDate.parse("2021-05-05"), 90);
        Flight temp18 = new Flight("219", "Athens", "Samos", LocalTime.parse("08:15:45"), LocalDate.parse("2021-05-05"), LocalTime.parse("10:15:45"), LocalDate.parse("2021-05-05"), 90);
        addflight(temp);
        addflight(temp1);
        addflight(temp2);
        addflight(temp3);
        addflight(temp4);
        addflight(temp5);
        addflight(temp6);
        addflight(temp7);
        addflight(temp8);
        addflight(temp9);
        addflight(temp11);
        addflight(temp12);
        addflight(temp13);
        addflight(temp14);
        addflight(temp15);
        addflight(temp16);
        addflight(temp17);
        addflight(temp18);
    }



}
