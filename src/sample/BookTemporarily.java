package sample;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.logging.Handler;

public class BookTemporarily implements Runnable{
    private ArrayList<String> wishlist;
    private Flight flight;
    public BookTemporarily(ArrayList<String> wishlist,Flight flight){
        this.wishlist = wishlist;
        this.flight = flight;
        Thread t = new Thread(this);
        t.start();
    }
    @Override
    public void run() {
        System.out.println("seats: " + wishlist + "booked for 10 sec");
        try {
            Thread.sleep(1000*10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("seats : " + wishlist + " are now free");
        flight.removeThread(this);

    }

    public ArrayList<String> getWishlist(){return wishlist;}
}
