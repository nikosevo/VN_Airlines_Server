package sample;

import java.io.Serializable;
import java.util.Arrays;

public class Person implements Serializable
{
    private static final long serialVersionUID = 1234567L;  //This is our serialization Id this must be the same on both ends in order to have proper encoding



    private String name;
    private String email;
    private String age;
    private String startcity;    //this is going to be the city that we will use to filter the results of the search
    private String reservation[] = new String[2];   //This is going to be used to store the data of the flight Id and the number of the seat in the form of string

    //Here is our constructor
    public Person(String name,String email,String age,String startcity,String flightId,String seat)
    {
        this.age=age;
        this.email=email;
        this.name=name;
        this.startcity=startcity;
        this.reservation[0]=flightId;
        this.reservation[1]=seat;
    }
    public String getName()
    {
        return name;
    }
    @Override
    public String toString()
    {
        return "Person{" + "name='" + this.getName() + '\'' + ", email='" + email + '\'' + ", age='" + age + '\'' + ", startcity='" + startcity + '\'' + ", reservation=" + Arrays.toString(reservation) + '}';
    }
}
