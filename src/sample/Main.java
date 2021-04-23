package sample;

import java.rmi.Naming;
import java.rmi.registry.Registry;
import java.time.LocalDate;

public class Main
{

    public static void main(String[] args)
    {
        Person per = new Person("val","@test","12","chios","152","12a"); //test subject
        System.out.println(per.toString());

        try{
            Registry r = java.rmi.registry.LocateRegistry.createRegistry(1099);

            serverHandler server = new serverHandler();
            Naming.rebind("//localhost:1099/valnik", server);
            System.out.println("serber up and renning");
            server.receiveSearchRequest("athens","chios"); //todo add date
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
