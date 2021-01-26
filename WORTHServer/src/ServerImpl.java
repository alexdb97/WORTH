

import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;



public class ServerImpl extends RemoteObject implements ServerInterface {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    // lista dei client già registrati
    private List <NotifyEventInterface> clients;
    //crea un nuovo servente 
    public ServerImpl () throws RemoteException {
        super ();
        clients = new ArrayList<NotifyEventInterface>();
    };

    //registrazione per la callback
    public synchronized void registerForCallback (NotifyEventInterface ClientInterface) throws RemoteException
    {
        if(!clients.contains(ClientInterface))
        {
            clients.add(ClientInterface);
            System.out.println("New client Registered");
        }
    };

    //deregistrazione al servizio 
    public void unregisterForCallback  (NotifyEventInterface Client) throws RemoteException
    {
        if(clients.remove(Client))
            System.out.println("Client unregistered");
        else
            System.out.println("Unable to unregister client");
    }

    //notifica di una variazione della lista quando viene richiamato, fa il callback a tutti i client registrati
    public void update (ConcurrentHashMap <String,Boolean> value) throws RemoteException {

        System.out.println("String callbacks");
        Iterator<NotifyEventInterface> i = clients.iterator();
        while(i.hasNext())
        {
            NotifyEventInterface client =  i.next();
            client.notifyEvent(value);
        }
        System.out.println("Callback complete");
    }

    
}
