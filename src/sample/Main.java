//Icsd18174 Chrysovalantis Pateiniotis
//Icsd18218 Nikos Tzekas
package sample;

import java.rmi.Naming;
import java.rmi.registry.Registry;


public class Main  //Main class that initializes the program
{

    public static void main(String[] args) {
        try {
            //We activate the server and initiate it we use the 1099 for our port
            Registry r = java.rmi.registry.LocateRegistry.createRegistry(1099);

            serverHandler server = new serverHandler();
            Naming.rebind("//localhost:1099/valnik", server);
            System.out.println("Server Ready!");

        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
