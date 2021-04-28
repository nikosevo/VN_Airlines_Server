package sample;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

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

    Flight(String id, String from, String to, LocalTime depart_time, LocalDate depart_date)
    {
        this.id = id;
        this.from = from;
        this.to = to;
        this.depart_time = depart_time;
        this.depart_date = depart_date;
        seats = new Person[25][4];
        //Arrays.fill(seats, null);
        seatsAvailable = 100; // since all the seats are available at the beginning

    }

    public boolean checkseat(int x, int y)
    {

        if (seats[x][y] == null)
        {
            return true;
        } else
        {
            return false;
        }
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

    public boolean bookTemporarily(ArrayList<String> wishlist){
        for(BookTemporarily b:tempOccupied)
            for(String s : b.getWishlist())
                for(String s2:wishlist)
                    if(s.equals(s2))
                        return false; //ooofff wtf


        tempOccupied.add(new BookTemporarily(wishlist,this));
        return true;
    }
    public void removeThread(BookTemporarily bt){
        for(BookTemporarily b : tempOccupied){
            if(b.getWishlist().equals(bt.getWishlist()))
                tempOccupied.remove(b);
        }
        System.out.println( bt.getWishlist() + "removed");
    }
    public ArrayList<String> getTempOccupied(){
        ArrayList<String> tmpArray = new ArrayList<>();
        for(BookTemporarily bk : tempOccupied)
            for(String seat : bk.getWishlist())
                tmpArray.add(seat);
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

}
