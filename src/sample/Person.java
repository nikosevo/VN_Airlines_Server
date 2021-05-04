package sample;

import java.io.Serializable;
import java.util.Arrays;

public class Person implements Serializable
{
    private static final long serialVersionUID = 1234567L;  //This is our serialization Id this must be the same on both ends in order to have proper encoding

    private String tele;
    private String name;
    private String email;
    private String age;
    //private String startcity;    //this is going to be the city that we will use to filter the results of the search
    private String reservation[] = new String[2];   //This is going to be used to store the data of the flight Id and the number of the seat in the form of string

    //Here is our constructor
    public Person(String name, String email, String age, String tele)
    {
        this.tele = tele;
        this.age = age;
        this.email = email;
        this.name = name;    //name + surname

    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return "Person{" + "name='" + this.getName() + '\'' + ", email='" + email + '\'' + ", age='" + age + '\''  + ", reservation=" + Arrays.toString(reservation) + '}';
    }

    public String getFlightid()
    {
        return this.reservation[0];
    }

    public String getSeat()
    {
        return this.reservation[1];
    }


    //With this method we give the number of the flight and the seat to the user
    public void setSeat(String flightId, String seat)
    {
        this.reservation[0] = flightId;
        this.reservation[1] = seat;
    }
}
