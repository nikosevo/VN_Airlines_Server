//Icsd18174 Chrysovalantis Pateiniotis
//Icsd18218 Nikos Tzekas
package sample;

import java.rmi.Naming;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.time.LocalTime;

public class Main  //Main class that initializes the program
{

    public static void main(String[] args)
    {
        //Person per = new Person("val", "@test", "12", "chios","578415698"); //test subject
        //System.out.println(per.toString());

        try
        {
            //We activate the server and initiate it we use the 1099 for our port
            Registry r = java.rmi.registry.LocateRegistry.createRegistry(1099);

            serverHandler server = new serverHandler();
            Naming.rebind("//localhost:1099/valnik", server);
            System.out.println("Server Ready!");
//////////////////test
       /*     Flight temp = new Flight("081", "Samos", "Thessaloniki", LocalTime.parse("09:15:45"), LocalDate.parse("2021-05-02"),LocalTime.parse("11:15:45"), LocalDate.parse("2021-05-05"));
            Flight temp1 = new Flight("105", "Chios", "Graz", LocalTime.parse("08:15:45"), LocalDate.parse("2021-05-04"),LocalTime.parse("10:15:45"), LocalDate.parse("2021-05-05"));
            Flight temp2 = new Flight("123", "Athens", "Samos", LocalTime.parse("12:15:45"), LocalDate.parse("2021-05-06"),LocalTime.parse("13:15:45"), LocalDate.parse("2021-05-05"));
            Flight temp3 = new Flight("321", "Samos", "Chios", LocalTime.parse("11:15:45"), LocalDate.parse("2021-05-28"),LocalTime.parse("13:15:45"), LocalDate.parse("2021-05-05"));
            Flight temp4 = new Flight("122", "Graz", "Samos", LocalTime.parse("13:15:45"), LocalDate.parse("2021-05-26"),LocalTime.parse("14:15:45"), LocalDate.parse("2021-05-05"));
            Flight temp5 = new Flight("109", "Athens", "Cairo", LocalTime.parse("15:15:45"), LocalDate.parse("2021-05-27"),LocalTime.parse("17:15:45"), LocalDate.parse("2021-05-05"));
            Flight temp6 = new Flight("251", "Athens", "Chania", LocalTime.parse("16:15:45"), LocalDate.parse("2021-05-07"),LocalTime.parse("18:15:45"), LocalDate.parse("2021-05-05"));
            Flight temp7 = new Flight("001", "Athens", "Rome", LocalTime.parse("19:15:45"), LocalDate.parse("2021-05-07"),LocalTime.parse("21:15:45"), LocalDate.parse("2021-05-05"));
            Flight temp8 = new Flight("002", "Rhodes", "Chios", LocalTime.parse("22:15:45"), LocalDate.parse("2021-05-08"),LocalTime.parse("00:15:45"), LocalDate.parse("2021-06-05"));
            Flight temp9 = new Flight("021", "Chios", "Moon", LocalTime.parse("21:15:45"), LocalDate.parse("2021-05-05"),LocalTime.parse("23:15:45"), LocalDate.parse("2021-05-05"));
            Flight temp11 = new Flight("112", "Samos", "Athens", LocalTime.parse("11:15:45"), LocalDate.parse("2021-05-05"),LocalTime.parse("13:15:45"), LocalDate.parse("2021-05-05"));
            Flight temp12 = new Flight("213", "Athens", "Samos", LocalTime.parse("07:15:45"), LocalDate.parse("2021-05-05"),LocalTime.parse("09:15:45"), LocalDate.parse("2021-05-05"));
            Flight temp13 = new Flight("212", "Athens", "Samos", LocalTime.parse("08:15:45"), LocalDate.parse("2021-05-05"),LocalTime.parse("10:15:45"), LocalDate.parse("2021-05-05"));
            server.addflight(temp);
            server.addflight(temp1);
            server.addflight(temp2);
            server.addflight(temp3);
            server.addflight(temp4);
            server.addflight(temp5);
            server.addflight(temp6);
            server.addflight(temp7);
            server.addflight(temp8);
            server.addflight(temp9);
            server.addflight(temp11);
            server.addflight(temp12);
            server.addflight(temp13);*/
            //server.serializeFlights();
            server.deserializeFlights();

            //server.addPersontoFlight("144", 22, 2, per);
           // System.out.println(server.getPersoninfo("144", "val"));
           // System.out.println(server.occupiedSeats("144"));
            /////////////test
        } catch (Exception e)
        {
            System.out.println(e);
        }

    }
}
