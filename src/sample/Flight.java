package sample;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

public class Flight implements Serializable {

    private static final long serialVersionUID = -1234567L;

    private Person seats[][] = {null}; //an double array that represents the seats of the plane
    private int seatsAvailable;
    private String id;
    private String from;
    private String to;
    private LocalTime depart_time;
    private LocalDate depart_date;

    Flight(String id, String from, String to, LocalTime depart_time, LocalDate depart_date){
        this.id = id;
        this.from = from;
        this.to = to;
        this.depart_time = depart_time;
        this.depart_date = depart_date;
        seats = new Person[4][25];
        Arrays.fill(seats,null);
        seatsAvailable = 100; // since all the seats are available at the beginning

    }

    @Override
    public String toString() {

        return "Flight: " + id + " Departure at: " + depart_time + " Available seats: " + seatsAvailable;
    }

    public String getId(){return id;}
}
