

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.rmi.*;

public interface NotifyEventInterface extends Remote {

    //Metodo Invocato dal server per notificare un evento ad un client remoto
    public void notifyEvent(ConcurrentHashMap <String,Boolean> value ) throws RemoteException;
   
    public  ArrayList <String> listOnlineUsers () throws RemoteException;

    public  ArrayList <String> listUsers () throws RemoteException;

    
}
