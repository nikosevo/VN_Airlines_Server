package sample.Intefaces;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface PersonOperations extends Remote
{
    public String getName() throws RemoteException; // interface method


}
