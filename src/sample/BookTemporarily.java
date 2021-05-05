package sample;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class BookTemporarily implements Runnable
{
    private ArrayList<String> wishlist;
    private Flight flight;
    private int time;
    private boolean over_3min;
    private boolean forceStop = false;
    LocalTime start;


    public BookTemporarily(ArrayList<String> wishlist, Flight flight)
    {
        start =  LocalTime.now();
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
        {
            while(!over_3min && !forceStop){
                Thread.sleep(1000);
                LocalTime stop = LocalTime.now();
                float diff = start.until(stop, ChronoUnit.MINUTES);
                if(diff > 3){
                    over_3min = true;
                }
            }
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println("seats : " + wishlist + " are now free");
        flight.removeThread(this);

    }
    public void stop(){
        forceStop = true;
    }
    public ArrayList<String> getWishlist()
    {
        return wishlist;
    }

}
