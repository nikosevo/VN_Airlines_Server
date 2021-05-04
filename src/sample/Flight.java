package sample;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Flight implements Serializable
{

    private static final long serialVersionUID = -1234567L;
    private ArrayList<BookTemporarily> tempOccupied = new ArrayList<>();


    private Person seats[][] = {null}; //an double array that represents the seats of the plane
    private int seatsAvailable;
    private String id;
    private String from;
    private String to;
    private LocalTime depart_time;
    private LocalDate depart_date;
    private LocalTime arrival_time;
    private LocalDate arrival_date;

    Flight(String id, String from, String to, LocalTime depart_time, LocalDate depart_date,LocalTime arrival_time,LocalDate arrival_date)
    {
        this.id = id;
        this.from = from;
        this.to = to;
        this.depart_time = depart_time;
        this.depart_date = depart_date;
        this.arrival_time = arrival_time;
        this.arrival_date= arrival_date;

        seats = new Person[25][4];
        //Arrays.fill(seats, null);
        seatsAvailable = 100; // since all the seats are available at the beginning

    }

    public boolean checkseat(int x, int y)
    {

        return seats[x][y] == null;
    }

    public void setpersonto(int x, int y, Person p)
    {
        this.seats[x][y] = p;
    }

    public Person checkreservation(String name)
    {
        for (int i = 0; i < 25; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                if (seats[i][j] != null)
                {
                    if (seats[i][j].getName().equals(name))
                    {
                        return seats[i][j];
                    }
                }
            }
        }
        return null;
    }

    //This method is used to book the seats that the user selects on the UI
    public boolean bookTemporarily(ArrayList<String> wishlist)
    {
        for (BookTemporarily b : tempOccupied)
            for (String s : b.getWishlist())
                for (String s2 : wishlist)
                    if (s.equals(s2)) return false; //ooofff wtf


        tempOccupied.add(new BookTemporarily(wishlist, this));
        return true;
    }

    public void removeThread(BookTemporarily bt)
    {
        tempOccupied.removeIf(b -> b.getWishlist().equals(bt.getWishlist()));
        System.out.println(bt.getWishlist() + "removed");
    }

    public ArrayList<String> getTempOccupied()
    {
        ArrayList<String> tmpArray = new ArrayList<>();
        for (BookTemporarily bk : tempOccupied)
            tmpArray.addAll(bk.getWishlist());
        return tmpArray;
    }

    @Override
    public String toString()
    {

        return "Flight: " + id + " Departure at: " + depart_time + " Available seats: " + seatsAvailable + " from: " + from + " To: " + to;
    }

    public String getId()
    {
        return id;
    }

    public String getfrom()
    {
        return from;
    }

    public String getTo()
    {
        return to;
    }

    public LocalDate getDepart_date()
    {
        return depart_date;
    }
    public LocalDate getArrival_date()
    {
        return arrival_date;
    }
    public LocalTime getDepart_time()
    {
        return depart_time;
    }
    public LocalTime getArrival_time()
    {
        return arrival_time;
    }
}
