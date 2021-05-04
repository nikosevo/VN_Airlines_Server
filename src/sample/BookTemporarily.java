package sample;

import java.util.ArrayList;

public class BookTemporarily implements Runnable
{
    private ArrayList<String> wishlist;
    private Flight flight;
    private int time;

    public BookTemporarily(ArrayList<String> wishlist, Flight flight)
    {
            this.wishlist = wishlist;
            this.flight = flight;
            Thread t = new Thread(this);
            t.start();
            time = 3;
            System.out.println("Empty list bruh!");

    }

    @Override
    public void run()
    {

        System.out.println("seats: " + wishlist + "booked for 10 sec");
        try
        {               //milli * seconds * minutes
            Thread.sleep(1000 * 10 * time);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println("seats : " + wishlist + " are now free");
        flight.removeThread(this);

    }

    public ArrayList<String> getWishlist()
    {
        return wishlist;
    }
    public void stop(){flight.removeThread(this);}

}
