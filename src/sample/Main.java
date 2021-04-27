package sample;

import java.rmi.Naming;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.time.LocalTime;

public class Main
{

    public static void main(String[] args)
    {
        Person per = new Person("val", "@test", "12", "chios"); //test subject
        System.out.println(per.toString());

        try
        {
            Registry r = java.rmi.registry.LocateRegistry.createRegistry(1099);

            serverHandler server = new serverHandler();
            Naming.rebind("//localhost:1099/valnik", server);
            System.out.println("serber up and renning");
//////////////////test
            Flight temp = new Flight("144", "test", "test2", LocalTime.parse("10:15:45"), LocalDate.parse("2021-05-05"));
            server.addflight(temp);
            server.addPersontoFlight("144", 22, 2, per);
            System.out.println(server.getPersoninfo("144", "val"));
            System.out.println(server.occupiedSeats("144"));
            /////////////test
        } catch (Exception e)
        {
            System.out.println(e);
        }

    }
}
